package com.sinhvien.finalproject.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.sinhvien.finalproject.Activities.AddCategoryActivity;
import com.sinhvien.finalproject.Activities.HomeActivity;
import com.sinhvien.finalproject.CustomAdapter.AdapterRecycleViewCategory;
import com.sinhvien.finalproject.CustomAdapter.AdapterRecycleViewStatistic;
import com.sinhvien.finalproject.DAO.OrderDAO;
import com.sinhvien.finalproject.DAO.TypefoodDAO;
import com.sinhvien.finalproject.DTO.OrderDTO;
import com.sinhvien.finalproject.DTO.TypefoodDTO;
import com.sinhvien.finalproject.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class DisplayHomeFragment extends Fragment implements View.OnClickListener {

    RecyclerView rcv_displayhome_Typefood, rcv_displayhome_Ordersoftheday;
    RelativeLayout layout_displayhome_Statistical,layout_displayhome_Seetable, layout_displayhome_Seemenu, layout_displayhome_Seestaff;
    TextView txt_displayhome_ViewAllCategory, txt_displayhome_ViewAllStatistic, txt_displayhome_Statistical,
            txt_displayhome_Seetable, txt_displayhome_Seemenu, txt_displayhome_Seestaff;
    TypefoodDAO typefoodDAO;
    OrderDAO orderDAO;
    List<TypefoodDTO> typefoodDTOList;
    List<OrderDTO> orderDTOS;
    AdapterRecycleViewCategory adapterRecycleViewCategory;
    AdapterRecycleViewStatistic adapterRecycleViewStatistic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.displayhome_layout,container,false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Function category</font>"));
        setHasOptionsMenu(true);

        //region Lấy dối tượng view
        rcv_displayhome_Typefood = (RecyclerView)view.findViewById(R.id.rcv_displayhome_Typefood);
        rcv_displayhome_Ordersoftheday = (RecyclerView)view.findViewById(R.id.rcv_displayhome_Ordersoftheday);
        layout_displayhome_Statistical = (RelativeLayout)view.findViewById(R.id.layout_displayhome_Statistical);
        layout_displayhome_Seetable = (RelativeLayout)view.findViewById(R.id.layout_displayhome_Seetable);
        layout_displayhome_Seemenu = (RelativeLayout)view.findViewById(R.id.layout_displayhome_Seemenu);
        txt_displayhome_Statistical = (TextView) view.findViewById(R.id.txt_displayhome_Statistical);
        txt_displayhome_Seetable = (TextView) view.findViewById(R.id.txt_displayhome_Seetable);
        txt_displayhome_Seemenu = (TextView) view.findViewById(R.id.txt_displayhome_Seemenu);
        txt_displayhome_ViewAllCategory = (TextView) view.findViewById(R.id.txt_displayhome_ViewAllCategory);
        txt_displayhome_ViewAllStatistic = (TextView) view.findViewById(R.id.txt_displayhome_ViewAllStatistic);
        //endregion

        //khởi tạo kết nối
        typefoodDAO = new TypefoodDAO(getActivity());
        orderDAO = new OrderDAO(getActivity());

        HienThiDSLoai();
        HienThiDonTrongNgay();

        layout_displayhome_Statistical.setOnClickListener(this);
        layout_displayhome_Seetable.setOnClickListener(this);
        layout_displayhome_Seemenu.setOnClickListener(this);
        txt_displayhome_Statistical.setOnClickListener(this);
        txt_displayhome_Seetable.setOnClickListener(this);
        txt_displayhome_Seemenu.setOnClickListener(this);
        txt_displayhome_ViewAllCategory.setOnClickListener(this);
        txt_displayhome_ViewAllStatistic.setOnClickListener(this);

        return view;
    }

    private void HienThiDSLoai(){
        rcv_displayhome_Typefood.setHasFixedSize(true);
        rcv_displayhome_Typefood.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        typefoodDTOList = typefoodDAO.LayDSLoaiMon();
        adapterRecycleViewCategory = new AdapterRecycleViewCategory(getActivity(),R.layout.custom_layout_displaycategory,typefoodDTOList);
        rcv_displayhome_Typefood.setAdapter(adapterRecycleViewCategory);
        adapterRecycleViewCategory.notifyDataSetChanged();
    }

    private void HienThiDonTrongNgay(){
        rcv_displayhome_Ordersoftheday.setHasFixedSize(true);
        rcv_displayhome_Ordersoftheday.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String orderdate = dateFormat.format(calendar.getTime());

        orderDTOS = orderDAO.LayDSDonDatNgay(orderdate);
        adapterRecycleViewStatistic = new AdapterRecycleViewStatistic(getActivity(),R.layout.custom_layout_displaystatistic,orderDTOS);
        rcv_displayhome_Ordersoftheday.setAdapter(adapterRecycleViewStatistic);
        adapterRecycleViewCategory.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        NavigationView navigationView = (NavigationView)getActivity().findViewById(R.id.navigation_view_home);
        switch (id){
            case R.id.layout_displayhome_Statistical:

            case R.id.txt_displayhome_Statistical:

            case R.id.txt_displayhome_ViewAllStatistic:
                FragmentTransaction tranDisplayStatistic = getActivity().getSupportFragmentManager().beginTransaction();
                tranDisplayStatistic.replace(R.id.contentView,new DisplayStatisticFragment());
                tranDisplayStatistic.addToBackStack(null);
                tranDisplayStatistic.commit();

                break;
            case R.id.txt_displayhome_Seetable:
            case R.id.layout_displayhome_Seetable:
                FragmentTransaction tranDisplayTable = getActivity().getSupportFragmentManager().beginTransaction();
                tranDisplayTable.replace(R.id.contentView,new DisplayTableFragment());
                tranDisplayTable.addToBackStack(null);
                tranDisplayTable.commit();

                break;

            case R.id.txt_displayhome_Seemenu:

            case R.id.layout_displayhome_Seemenu:
                Intent iAddCategory = new Intent(getActivity(), AddCategoryActivity.class);
                startActivity(iAddCategory);

                break;


            case R.id.txt_displayhome_ViewAllCategory:
                FragmentTransaction tranDisplayCategory = getActivity().getSupportFragmentManager().beginTransaction();
                tranDisplayCategory.replace(R.id.contentView,new DisplayCategoryFragment());
                tranDisplayCategory.addToBackStack(null);
                tranDisplayCategory.commit();

                break;

        }
    }
}

