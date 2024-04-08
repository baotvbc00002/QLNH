package com.sinhvien.finalproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinhvien.finalproject.CustomAdapter.AdapterDisplayPayment;
import com.sinhvien.finalproject.DAO.DinnertableDAO;
import com.sinhvien.finalproject.DAO.PayDAO;
import com.sinhvien.finalproject.DAO.StaffDAO;
import com.sinhvien.finalproject.DTO.StaffDTO;
import com.sinhvien.finalproject.DTO.PayDTO;
import com.sinhvien.finalproject.R;

import java.util.List;

public class DetailStatisticActivity extends AppCompatActivity {

    ImageView img_detailstatistic_backbtn;
    TextView txt_detailstatistic_Orderid,txt_detailstatistic_Orderdate,txt_detailstatistic_Tablename
            ,txt_detailstatistic_Staffname,txt_detailstatistic_Total;
    GridView gvDetailStatistic;
    int orderid, staffid, tableid;
    String orderdate, total;
    StaffDAO staffDAO;
    DinnertableDAO dinnertableDAO;
    List<PayDTO> payDTOList;
    PayDAO payDAO;
    AdapterDisplayPayment adapterDisplayPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailstatistic_layout);

        //Lấy thông tin từ display statistic
        Intent intent = getIntent();
        orderid = intent.getIntExtra("orderid",0);
        staffid = intent.getIntExtra("staffid",0);
        tableid = intent.getIntExtra("tableid",0);
        orderdate = intent.getStringExtra("orderdate");
        total = intent.getStringExtra("total");

        //region Thuộc tính bên view
        img_detailstatistic_backbtn = (ImageView)findViewById(R.id.img_detailstatistic_backbtn);
        txt_detailstatistic_Orderid = (TextView)findViewById(R.id.txt_detailstatistic_Orderid);
        txt_detailstatistic_Orderdate = (TextView)findViewById(R.id.txt_detailstatistic_Orderdate);
        txt_detailstatistic_Tablename = (TextView)findViewById(R.id.txt_detailstatistic_Tablename);
        txt_detailstatistic_Staffname = (TextView)findViewById(R.id.txt_detailstatistic_Staffname );
        txt_detailstatistic_Total = (TextView)findViewById(R.id.txt_detailstatistic_Total);
        gvDetailStatistic = (GridView)findViewById(R.id.gvDetailStatistic);
        //endregion

        //khởi tạo lớp dao mở kết nối csdl
        staffDAO = new StaffDAO(this);
        dinnertableDAO = new DinnertableDAO(this);
        payDAO = new PayDAO(this);

        //chỉ hiển thị nếu lấy đc mã đơn đc chọn
        if (orderid !=0){
            txt_detailstatistic_Orderid.setText("Order id: "+orderid);
            txt_detailstatistic_Orderdate.setText(orderdate);
            txt_detailstatistic_Total.setText(total +"VNĐ");

            StaffDTO staffDTO = staffDAO.LayNVTheoMa(staffid);
            txt_detailstatistic_Staffname.setText(staffDTO.getFULLNAME());
            txt_detailstatistic_Tablename.setText(dinnertableDAO.LayTenBanTheoMa(tableid));

            HienThiDSCTDD();
        }

        img_detailstatistic_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    private void HienThiDSCTDD(){
        payDTOList = payDAO.LayDSMonTheoMaDon(orderid);
        adapterDisplayPayment = new AdapterDisplayPayment(this,R.layout.custom_layout_paymentmenu,payDTOList);
        gvDetailStatistic.setAdapter(adapterDisplayPayment);
        adapterDisplayPayment.notifyDataSetChanged();
    }
}