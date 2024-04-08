package com.sinhvien.finalproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.sinhvien.finalproject.DAO.OrderDAO;
import com.sinhvien.finalproject.DAO.OrderdetailDAO;
import com.sinhvien.finalproject.DTO.OrderdetailDTO;
import com.sinhvien.finalproject.R;

public class AmountMenuActivity extends AppCompatActivity {

    TextInputLayout TXTL_amountmenu_Quantity;
    Button BTN_amountmenu_Agree;
    int Tableid, Foodid;
    OrderDAO orderDAO;
    OrderdetailDAO orderdetailDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amount_menu_layout);

        //Lấy đối tượng view
        TXTL_amountmenu_Quantity = (TextInputLayout)findViewById(R.id.txtl_amountmenu_Quantity);
        BTN_amountmenu_Agree = (Button)findViewById(R.id.btn_amountmenu_Agree);

        //khởi tạo kết nối csdl
        orderDAO = new OrderDAO(this);
        orderdetailDAO = new OrderdetailDAO(this);

        //Lấy thông tin từ bàn được chọn
        Intent intent = getIntent();
        Tableid = intent.getIntExtra("Tableid",0);
        Foodid = intent.getIntExtra("Foodid",0);

        BTN_amountmenu_Agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateAmount()){
                    return;
                }

                int orderid = (int) orderDAO.LayMaDonTheoMaBan(Tableid,"false");
                boolean check= orderdetailDAO.KiemTraMonTonTai(orderid,Foodid);
                if(check){
                    //update số lượng món đã chọn
                    int quantityold = orderdetailDAO.LaySLMonTheoMaDon(orderid,Foodid);
                    int quantitynew = Integer.parseInt(TXTL_amountmenu_Quantity.getEditText().getText().toString());
                    int totalquantity = quantityold + quantitynew;

                    OrderdetailDTO  orderdetailDTO  = new OrderdetailDTO ();
                    orderdetailDTO .setFoodid(Foodid);
                    orderdetailDTO .setOrderid(orderid);
                    orderdetailDTO .setQuantity(totalquantity);

                    boolean checkupdate = orderdetailDAO.CapNhatSL(orderdetailDTO);
                    if(checkupdate){
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_sucessful),Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_failed),Toast.LENGTH_SHORT).show();
                    }
                }else {
                    //thêm số lượng món nếu chưa chọn món này
                    int quantity = Integer.parseInt(TXTL_amountmenu_Quantity.getEditText().getText().toString());
                    OrderdetailDTO orderdetailsDTO = new OrderdetailDTO();
                    orderdetailsDTO.setFoodid(Foodid);
                    orderdetailsDTO.setOrderid(orderid);
                    orderdetailsDTO.setQuantity(quantity);

                    boolean checkupdate = orderdetailDAO.ThemChiTietDonDat(orderdetailsDTO);
                    if(checkupdate){
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_sucessful),Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_failed),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //validate số lượng
    private boolean validateAmount(){
        String val = TXTL_amountmenu_Quantity.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_amountmenu_Quantity.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!val.matches(("\\d+(?:\\.\\d+)?"))){
            TXTL_amountmenu_Quantity.setError("Invalid quantity");
            return false;
        }else {
            TXTL_amountmenu_Quantity.setError(null);
            TXTL_amountmenu_Quantity.setErrorEnabled(false);
            return true;
        }
    }
}