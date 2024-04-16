package com.sinhvien.finalproject.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sinhvien.finalproject.DAO.DinnertableDAO;
import com.sinhvien.finalproject.DAO.StaffDAO;
import com.sinhvien.finalproject.DTO.OrderDTO;
import com.sinhvien.finalproject.DTO.StaffDTO;
import com.sinhvien.finalproject.R;

import java.util.List;

public class AdapterRecycleViewStatistic extends RecyclerView.Adapter<AdapterRecycleViewStatistic.ViewHolder>{

    Context context;
    int layout;
    List<OrderDTO> orderDTOList;
    StaffDAO staffDAO;
    DinnertableDAO dinnertableDAO;

    public AdapterRecycleViewStatistic(Context context, int layout, List<OrderDTO> orderDTOList){

        this.context =context;
        this.layout = layout;
        this.orderDTOList = orderDTOList;
        staffDAO = new StaffDAO(context);
        dinnertableDAO = new DinnertableDAO(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRecycleViewStatistic.ViewHolder holder, int position) {
        OrderDTO orderDTO = orderDTOList.get(position);
        holder.txt_customstatistic_Orderid.setText("Orderid: "+orderDTO.getOrderid());
        holder.txt_customstatistic_Orderdate.setText(orderDTO.getOrderdate());
        if(orderDTO.getTotal().equals("0"))
        {
            holder.txt_customstatistic_Total.setVisibility(View.INVISIBLE);
        }else {
            holder.txt_customstatistic_Total.setVisibility(View.VISIBLE);
        }

        if (orderDTO.getStatus().equals("true"))
        {
            holder.txt_customstatistic_Status.setText("Paid");
        }else {
            holder.txt_customstatistic_Status.setText("Unpaid");
        }
        StaffDTO staffDTO = staffDAO.LayNVTheoMa(orderDTO.getStaffid());
        holder.txt_customstatistic_Staffname.setText(staffDTO.getFULLNAME());
        holder.txt_customstatistic_Tableset.setText(dinnertableDAO.LayTenBanTheoMa(orderDTO.getTableid()));
    }

    @Override
    public int getItemCount() {
        return orderDTOList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_customstatistic_Orderid, txt_customstatistic_Orderdate, txt_customstatistic_Staffname,
                txt_customstatistic_Tableset, txt_customstatistic_Total,txt_customstatistic_Status;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txt_customstatistic_Orderid = itemView.findViewById(R.id.txt_customstatistic_Orderid);
            txt_customstatistic_Orderdate = itemView.findViewById(R.id.txt_customstatistic_Orderdate);
            txt_customstatistic_Staffname = itemView.findViewById(R.id.txt_customstatistic_Staffname);
            txt_customstatistic_Tableset = itemView.findViewById(R.id.txt_customstatistic_Tableset);
            txt_customstatistic_Total = itemView.findViewById(R.id.txt_customstatistic_Total);
            txt_customstatistic_Status = itemView.findViewById(R.id.txt_customstatistic_Status);
        }
    }
}
