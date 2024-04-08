package com.sinhvien.finalproject.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.sinhvien.finalproject.Activities.DetailStatisticActivity;
import com.sinhvien.finalproject.Activities.HomeActivity;
import com.sinhvien.finalproject.CustomAdapter.AdapterDisplayStatistic;
import com.sinhvien.finalproject.DAO.OrderDAO;
import com.sinhvien.finalproject.DTO.OrderDTO;
import com.sinhvien.finalproject.R;

import java.util.List;

public class DisplayStatisticFragment extends Fragment {

    ListView lvStatistic;
    List<OrderDTO> orderDTOS;
    OrderDAO orderDAO;
    AdapterDisplayStatistic adapterDisplayStatistic;
    FragmentManager fragmentManager;
    int orderid, staffid, tableid;
    String orderdate, total;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.displaystatistic_layout,container,false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Statistical</font>"));
        setHasOptionsMenu(true);

        lvStatistic = (ListView)view.findViewById(R.id.lvStatistic);
        orderDAO = new OrderDAO(getActivity());

        orderDTOS = orderDAO.LayDSDonDat();
        adapterDisplayStatistic = new AdapterDisplayStatistic(getActivity(),R.layout.custom_layout_displaystatistic,orderDTOS);
        lvStatistic.setAdapter(adapterDisplayStatistic);
        adapterDisplayStatistic.notifyDataSetChanged();

        lvStatistic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                orderid = orderDTOS.get(position).getOrderid();
                staffid = orderDTOS.get(position).getStaffid();
                tableid = orderDTOS.get(position).getTableid();
                orderdate = orderDTOS.get(position).getOrderdate();
                total = orderDTOS.get(position).getTotal();

                Intent intent = new Intent(getActivity(), DetailStatisticActivity.class);
                intent.putExtra(" orderid",orderid);
                intent.putExtra("staffid",staffid);
                intent.putExtra("tableid",tableid);
                intent.putExtra("orderdate",orderdate);
                intent.putExtra("total",total);
                startActivity(intent);
            }
        });

        return view;
    }
}
