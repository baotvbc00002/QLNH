package com.sinhvien.finalproject.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sinhvien.finalproject.CustomAdapter.AdapterDisplayPayment;
import com.sinhvien.finalproject.DAO.DinnertableDAO;
import com.sinhvien.finalproject.DAO.OrderDAO;
import com.sinhvien.finalproject.DAO.PayDAO;
import com.sinhvien.finalproject.DTO.PayDTO;
import com.sinhvien.finalproject.R;

import java.util.List;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView IMG_payment_backbtn;
    TextView TXT_payment_Tablename, TXT_payment_Orderdate, TXT_payment_Total;
    Button BTN_payment_Pay;
    GridView gvDisplayPayment;
    OrderDAO orderDAO;
    DinnertableDAO dinnertableDAO;
    PayDAO payDAO;
    List<PayDTO> payDTOS;
    AdapterDisplayPayment adapterDisplayPayment;
    long total = 0;
    int tableid, orderid;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_layout);

        //region view attribute
        gvDisplayPayment= (GridView)findViewById(R.id.gvDisplayPayment);
        IMG_payment_backbtn = (ImageView)findViewById(R.id.img_payment_backbtn);
        TXT_payment_Tablename = (TextView)findViewById(R.id.txt_payment_Tablename);
        TXT_payment_Orderdate = (TextView)findViewById(R.id.txt_payment_Orderdate);
        TXT_payment_Total= (TextView)findViewById(R.id.txt_payment_Total);
        BTN_payment_Pay = (Button)findViewById(R.id.btn_payment_Pay);
        //endregion

        //Initialize database connection
        orderDAO = new OrderDAO(this);
        payDAO = new PayDAO(this);
        dinnertableDAO = new DinnertableDAO(this);

        fragmentManager = getSupportFragmentManager();

        // Get data from selected table code
        Intent intent = getIntent();
        tableid = intent.getIntExtra("tableid",0);
        String tablename = intent.getStringExtra("tablename");
        String orderdate = intent.getStringExtra("orderdate");

        TXT_payment_Tablename.setText(tablename);
        TXT_payment_Orderdate.setText(orderdate);

        // If the table code exists, it will be displayed
        if(tableid !=0 ){
            ShowPayment();

            for (int i=0;i<payDTOS.size();i++){
                int quantity = payDTOS.get(i).getQuantity();
                int price = payDTOS.get(i).getPrice();

                total += (quantity * price);
            }
            TXT_payment_Total.setText(String.valueOf(total) +" VNĐ");
        }

        BTN_payment_Pay.setOnClickListener(this);
        IMG_payment_backbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_payment_Pay:
                boolean checktable = dinnertableDAO.CapNhatTinhTrangBan(tableid,"false");
                boolean checkorder = orderDAO.UpdateTThaiDonTheoMaBan(tableid,"true");
                boolean checktotal = orderDAO.UpdateTongTienDonDat(orderid,String.valueOf(total));
                if(checktable && checkorder && checktotal){
                    ShowPayment();
                    TXT_payment_Total.setText("0 VNĐ");
                    Toast.makeText(getApplicationContext(),"Payment success!",Toast.LENGTH_SHORT);
                }else{
                    Toast.makeText(getApplicationContext(),"Payment error!",Toast.LENGTH_SHORT);
                }
                break;

            case R.id.img_payment_backbtn:
                finish();
                break;
        }
    }

    //hiển thị data lên gridview
    private void ShowPayment (){
        orderid = (int) orderDAO.LayMaDonTheoMaBan(tableid,"false");
        payDTOS = payDAO.LayDSMonTheoMaDon(orderid);
        adapterDisplayPayment = new AdapterDisplayPayment(this,R.layout.custom_layout_paymentmenu,payDTOS);
        gvDisplayPayment.setAdapter(adapterDisplayPayment);
        adapterDisplayPayment.notifyDataSetChanged();
    }
}