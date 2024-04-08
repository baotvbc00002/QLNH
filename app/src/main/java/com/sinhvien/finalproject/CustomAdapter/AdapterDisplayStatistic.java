package com.sinhvien.finalproject.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sinhvien.finalproject.DAO.DinnertableDAO;
import com.sinhvien.finalproject.DAO.StaffDAO;
import com.sinhvien.finalproject.DTO.OrderDTO;
import com.sinhvien.finalproject.DTO.StaffDTO;
import com.sinhvien.finalproject.R;

import java.util.List;

public class AdapterDisplayStatistic extends BaseAdapter {

    Context context;
    int layout;
    List<OrderDTO> orderDTOS;
    ViewHolder viewHolder;
    StaffDAO staffDAO;
    DinnertableDAO dinnertableDAO;

    public AdapterDisplayStatistic(Context context, int layout, List<OrderDTO> orderDTOS){
        this.context = context;
        this.layout = layout;
        this.orderDTOS = orderDTOS;
        staffDAO = new StaffDAO(context);
        dinnertableDAO = new DinnertableDAO(context);
    }

    @Override
    public int getCount() {
        return orderDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return orderDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return orderDTOS.get(position).getOrderid();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.txt_customstatistic_Orderid = (TextView)view.findViewById(R.id.txt_customstatistic_Orderid);
            viewHolder.txt_customstatistic_Orderdate = (TextView)view.findViewById(R.id.txt_customstatistic_Orderdate);
            viewHolder.txt_customstatistic_Staffname = (TextView)view.findViewById(R.id.txt_customstatistic_Staffname);
            viewHolder.txt_customstatistic_Total = (TextView)view.findViewById(R.id.txt_customstatistic_Total);
            viewHolder.txt_customstatistic_Status = (TextView)view.findViewById(R.id.txt_customstatistic_Status);
            viewHolder.txt_customstatistic_Tableset = (TextView)view.findViewById(R.id.txt_customstatistic_Tableset);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        OrderDTO orderDTO = orderDTOS.get(position);

        viewHolder.txt_customstatistic_Orderid.setText("Orderid: "+orderDTO.getOrderid());
        viewHolder.txt_customstatistic_Orderdate.setText(orderDTO.getOrderdate());
        viewHolder.txt_customstatistic_Total.setText(orderDTO.getTotal()+" VNĐ");
        if (orderDTO.getStatus().equals("true"))
        {
            viewHolder.txt_customstatistic_Status.setText("Paid");
        }else {
            viewHolder.txt_customstatistic_Status.setText("Unpaid");
        }
        StaffDTO staffDTO = staffDAO.LayNVTheoMa(orderDTO.getStaffid());
        viewHolder.txt_customstatistic_Staffname.setText(staffDTO.getFULLNAME());
        viewHolder.txt_customstatistic_Tableset.setText(dinnertableDAO.LayTenBanTheoMa(orderDTO.getTableid()));

        return view;
    }
    public class ViewHolder{
        TextView txt_customstatistic_Orderid, txt_customstatistic_Orderdate, txt_customstatistic_Staffname
                ,txt_customstatistic_Total,txt_customstatistic_Status, txt_customstatistic_Tableset;

    }
}
