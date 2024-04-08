package com.sinhvien.finalproject.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.sinhvien.finalproject.DAO.PositionDAO;
import com.sinhvien.finalproject.DTO.StaffDTO;
import com.sinhvien.finalproject.R;

import java.util.List;

public class AdapterDisplayStaff extends BaseAdapter {

    Context context;
    int layout;
    List<StaffDTO> staffDTOS;
    ViewHolder viewHolder;
    PositionDAO positionDAO;

    public AdapterDisplayStaff(Context context, int layout, List<StaffDTO> staffDTOS){
        this.context = context;
        this.layout = layout;
        this.staffDTOS = staffDTOS;
        positionDAO = new PositionDAO(context);
    }

    public AdapterDisplayStaff(FragmentActivity activity, int customLayoutDisplaystaff, List<StaffDTO> staffDTOS) {
    }

    @Override
    public int getCount() {
        return staffDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return staffDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return staffDTOS.get(position).getSTAFFID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.img_customstaff_Staffimage = (ImageView)view.findViewById(R.id.img_customstaff_Staffimage);
            viewHolder.txt_customstaff_Staffname= (TextView)view.findViewById(R.id.txt_customstaff_Staffname);
            viewHolder.txt_customstaff_Positionname = (TextView)view.findViewById(R.id.txt_customstaff_Positionname);
            viewHolder.txt_customstaff_Phone = (TextView)view.findViewById(R.id.txt_customstaff_Phone);
            viewHolder.txt_customstaff_Email = (TextView)view.findViewById(R.id.txt_customstaff_Email);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        StaffDTO staffDTO = staffDTOS.get(position);

        viewHolder.txt_customstaff_Staffname.setText(staffDTO.getFULLNAME());
        viewHolder.txt_customstaff_Positionname.setText(positionDAO.LayTenQuyenTheoMa(staffDTO.getPOSITIONID()));
        viewHolder.txt_customstaff_Phone.setText(staffDTO.getPHONE());
        viewHolder.txt_customstaff_Email.setText(staffDTO.getEMAIL());

        return view;
    }

    public class ViewHolder{
        ImageView img_customstaff_Staffimage;
        TextView txt_customstaff_Staffname, txt_customstaff_Positionname,txt_customstaff_Phone, txt_customstaff_Email;
    }
}
