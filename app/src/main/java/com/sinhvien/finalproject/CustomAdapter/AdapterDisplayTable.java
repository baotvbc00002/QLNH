package com.sinhvien.finalproject.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sinhvien.finalproject.Activities.HomeActivity;
import com.sinhvien.finalproject.Activities.PaymentActivity;
import com.sinhvien.finalproject.DAO.DinnertableDAO;
import com.sinhvien.finalproject.DAO.OrderDAO;
import com.sinhvien.finalproject.DTO.OrderDTO;
import com.sinhvien.finalproject.DTO.DinnertableDTO;
import com.sinhvien.finalproject.Fragments.DisplayCategoryFragment;
import com.sinhvien.finalproject.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterDisplayTable extends BaseAdapter implements View.OnClickListener{

    Context context;
    int layout;
    List<DinnertableDTO> dinnertableDTOList;
    ViewHolder viewHolder;
    DinnertableDAO dinnertableDAO;
    OrderDAO orderDAO;
    FragmentManager fragmentManager;

    public AdapterDisplayTable(Context context, int layout, List<DinnertableDTO> banAnDTOList){
        this.context = context;
        this.layout = layout;
        this.dinnertableDTOList = dinnertableDTOList;
        dinnertableDAO = new DinnertableDAO(context);
        orderDAO = new OrderDAO(context);
        fragmentManager = ((HomeActivity)context).getSupportFragmentManager();
    }

    @Override
    public int getCount() {
        return dinnertableDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return dinnertableDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dinnertableDTOList.get(position).getTableid();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();
            view = inflater.inflate(layout,parent,false);

            viewHolder.imgDinnertable = (ImageView) view.findViewById(R.id.img_customtable_Dinnertable);
            viewHolder.imgOrder = (ImageView) view.findViewById(R.id.img_customtable_Order);
            viewHolder.imgPay = (ImageView) view.findViewById(R.id.img_customtable_Pay);
            viewHolder.imgPushthebutton = (ImageView) view.findViewById(R.id.img_customtable_Pushthebutton);
            viewHolder.txtDinnertablename = (TextView)view.findViewById(R.id.txt_customtable_Dinnertablename);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if(dinnertableDTOList.get(position).isSelected()){
            DisplayButton();
        }else {
            AnButton();
        }

        DinnertableDTO dinnertableDTO = dinnertableDTOList.get(position);

        String checkstatus = dinnertableDAO.LayTinhTrangBanTheoMa(dinnertableDTO.getTableid());
        //đổi hình theo tình trạng
        if(checkstatus.equals("true")){
            viewHolder.imgDinnertable.setImageResource(R.drawable.ic_baseline_airline_seat_legroom_normal_40);
        }else {
            viewHolder.imgDinnertable.setImageResource(R.drawable.ic_baseline_event_seat_40);
        }

        viewHolder.txtDinnertablename.setText(dinnertableDTO.getTablename());
        viewHolder.imgDinnertable.setTag(position);

        //sự kiện click
        viewHolder.imgDinnertable.setOnClickListener(this);
        viewHolder.imgOrder.setOnClickListener(this);
        viewHolder.imgPay.setOnClickListener(this);
        viewHolder.imgPushthebutton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        viewHolder = (ViewHolder) ((View) v.getParent()).getTag();

        int location1 = (int) viewHolder.imgDinnertable.getTag();

        int tableid = dinnertableDTOList.get(location1).getTableid();
        String tablename = dinnertableDTOList.get(location1).getTablename();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String orderdate = dateFormat.format(calendar.getTime());

        switch (id){
            case R.id.img_customtable_Dinnertable:
                int vitri = (int)v.getTag();
                dinnertableDTOList.get(vitri).setSelected(true);
                DisplayButton();
                break;

            case R.id.img_customtable_Pushthebutton:
                AnButton();
                break;

            case R.id.img_customtable_Order:
                Intent getIHome = ((HomeActivity)context).getIntent();
                int staffid = getIHome.getIntExtra("staffid",0);
                String status = dinnertableDAO.LayTinhTrangBanTheoMa(tableid);

                if(status.equals("false")){
                    //Thêm bảng gọi món và update tình trạng bàn
                    OrderDTO orderDTO = new OrderDTO();
                    orderDTO.setTableid(tableid);
                    orderDTO.setStaffid(staffid);
                    orderDTO.setOrderdate(orderdate);
                    orderDTO.setStatus("false");
                    orderDTO.setTotal("0");

                    long check = orderDAO.ThemDonDat(orderDTO);
                    dinnertableDAO.CapNhatTinhTrangBan(tableid,"true");
                    if(check == 0){ Toast.makeText(context,context.getResources().getString(R.string.add_failed),Toast.LENGTH_SHORT).show(); }
                }
                //chuyển qua trang category
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                DisplayCategoryFragment displayCategoryFragment = new DisplayCategoryFragment();

                Bundle bDataCategory = new Bundle();
                bDataCategory.putInt("tableid",tableid);
                displayCategoryFragment.setArguments(bDataCategory);

                transaction.replace(R.id.contentView,displayCategoryFragment).addToBackStack("dinnertabledisplay");
                transaction.commit();
                break;

            case R.id.img_customtable_Pay:
                //chuyển dữ liệu qua trang thanh toán
                Intent iPay = new Intent(context, PaymentActivity.class);
                iPay.putExtra("tableid",tableid);
                iPay.putExtra("tablename",tablename);
                iPay.putExtra("orderdate",orderdate);
                context.startActivity(iPay);
                break;
        }
    }

    private void DisplayButton(){
        viewHolder.imgOrder.setVisibility(View.VISIBLE);
        viewHolder.imgPay.setVisibility(View.VISIBLE);
        viewHolder.imgPushthebutton.setVisibility(View.VISIBLE);
    }
    private void AnButton(){
        viewHolder.imgOrder.setVisibility(View.INVISIBLE);
        viewHolder.imgPay.setVisibility(View.INVISIBLE);
        viewHolder.imgPushthebutton.setVisibility(View.INVISIBLE);
    }

    public class ViewHolder{
        ImageView imgDinnertable, imgOrder, imgPay, imgPushthebutton;
        TextView txtDinnertablename;
    }
}
