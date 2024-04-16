package com.sinhvien.finalproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.sinhvien.finalproject.DAO.DinnertableDAO;
import com.sinhvien.finalproject.R;

public class EditTableActivity extends AppCompatActivity {

    TextInputLayout TXTL_edittable_tablename;
    Button BTN_edittable_Edittable;
    DinnertableDAO dinnertableDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edittable_layout);

        //thuộc tính view
        TXTL_edittable_tablename = (TextInputLayout)findViewById(R.id.txtl_edittable_tablename);
        BTN_edittable_Edittable = (Button)findViewById(R.id.btn_edittable_Edittable);

        //khởi tạo dao mở kết nối csdl
        dinnertableDAO = new DinnertableDAO(this);
        int tableid = getIntent().getIntExtra("tableid",0); //lấy maban từ bàn đc chọn

        BTN_edittable_Edittable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tablename  = TXTL_edittable_tablename.getEditText().getText().toString();

                if(tablename  != null || tablename .equals("")){
                    boolean check = dinnertableDAO.CapNhatTenBan(tableid,tablename);
                    Intent intent = new Intent();
                    intent.putExtra("Eidtresult",check);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }
}