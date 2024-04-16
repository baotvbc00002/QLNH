package com.sinhvien.finalproject.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.sinhvien.finalproject.Activities.HomeActivity;
import com.sinhvien.finalproject.Activities.WelcomeActivity;
import com.sinhvien.finalproject.DAO.StaffDAO;
import com.sinhvien.finalproject.DAO.PositionDAO;
import com.sinhvien.finalproject.DTO.StaffDTO;
import com.sinhvien.finalproject.R;

public class DisplayInformationFragment extends Fragment {
    TextView i4_Fullname, i4_Birth, i4_Gender, i4_Email, i4_Phonenum, i4_Logout, i4_Position;
    FragmentManager fragmentManager;
    StaffDAO staffDAO;
    int staffid;
    PositionDAO positionDAO;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.displayinformation_layout,container,false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Information</font>"));
        setHasOptionsMenu(false);

        i4_Fullname = view.findViewById(R.id.i4_fullname);
        i4_Birth = view.findViewById(R.id.i4_birth);
        i4_Gender = view.findViewById(R.id.i4_gender);
        i4_Email = view.findViewById(R.id.i4_mail);
        i4_Phonenum = view.findViewById(R.id.i4_phonenum);
        i4_Logout = view.findViewById(R.id.i4_logout);
        i4_Position = view.findViewById(R.id.i4_position);


        staffDAO = new StaffDAO(getActivity());
        positionDAO = new PositionDAO(getActivity());
        Intent intent = getActivity().getIntent();
        staffid = intent.getIntExtra("staffid",0);
        StaffDTO nhanVienDTO = staffDAO.LayNVTheoMa(staffid);
        int positionid = staffDAO.LayQuyenNV(staffid);
        String positionname =  positionDAO.LayTenQuyenTheoMa(positionid);
        i4_Fullname.setText(nhanVienDTO.getFULLNAME ());
        i4_Birth.setText(nhanVienDTO.getDATEOFBIRTH());
        i4_Gender.setText(nhanVienDTO.getSEX());
        i4_Position.setText(positionname);
        i4_Email.setText(nhanVienDTO.getEMAIL());
        i4_Phonenum.setText(nhanVienDTO.getPHONE());
        i4_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), WelcomeActivity.class);
                startActivity(intent);
            }
        });



        return  view;
    }


}
