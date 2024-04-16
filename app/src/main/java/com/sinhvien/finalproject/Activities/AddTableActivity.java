package com.sinhvien.finalproject.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.sinhvien.finalproject.DAO.DinnertableDAO;
import com.sinhvien.finalproject.Fragments.DisplayTableFragment;
import com.sinhvien.finalproject.R;

public class AddTableActivity extends AppCompatActivity {

    TextInputLayout TXTL_addtable_Tablename;
    Button BTN_addtable_Createtable;
    DinnertableDAO dinnertableDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtable_layout);

        //region Gets the object in the view
        TXTL_addtable_Tablename = (TextInputLayout)findViewById(R.id.txtl_addtable_Tablename);
        BTN_addtable_Createtable = (Button)findViewById(R.id.btn_addtable_Createtable);

        dinnertableDAO = new DinnertableDAO(this);
        BTN_addtable_Createtable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sTablename = TXTL_addtable_Tablename.getEditText().getText().toString();
                if(sTablename != null || sTablename.equals("")){
                    boolean check = dinnertableDAO.ThemBanAn(sTablename);
                    //trả về result cho displaytable
                    Intent intent = new Intent();
                    intent.putExtra("extraresults",check);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    //validate dữ liệu
    private boolean validateName(){
        String val = TXTL_addtable_Tablename.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_addtable_Tablename.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            TXTL_addtable_Tablename.setError(null);
            TXTL_addtable_Tablename.setErrorEnabled(false);
            return true;
        }
    }
}