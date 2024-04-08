package com.sinhvien.finalproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.sinhvien.finalproject.DAO.StaffDAO;
import com.sinhvien.finalproject.DTO.StaffDTO;
import com.sinhvien.finalproject.R;

import java.util.regex.Pattern;

public class PasswordActivity extends AppCompatActivity {

    TextInputLayout TXTL_REPASSS_Username, TXTL_REPASSS_PASSWORD;
    private Button BTN_REPASS_CONFIRM;
    long checkcheck = 0 ;
    StaffDAO staffDAO;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{6,}" +                // at least 4 characters
                    "$");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        TXTL_REPASSS_Username = (TextInputLayout)findViewById(R.id.txtl_repass_Username);
        TXTL_REPASSS_PASSWORD = (TextInputLayout)findViewById(R.id.txtl_repass_passWord);

        BTN_REPASS_CONFIRM = findViewById(R.id.btn_repass_confirm);

        staffDAO = new StaffDAO(this);


        BTN_REPASS_CONFIRM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validateUserName() | !validatePassWord()) {
                    return;
                }

                String Username = TXTL_REPASSS_Username.getEditText().getText().toString();
                String passWord = TXTL_REPASSS_PASSWORD.getEditText().getText().toString();

                int check = staffDAO.KiemTraUser(Username, passWord);

                if (check  != 0){
                    StaffDTO staffDTO = staffDAO.LayNVTheoMa(check);
                    TXTL_REPASSS_PASSWORD.getEditText().setText(staffDTO.getPASSWORD());

                    staffDTO.setPASSWORD(passWord);
                    checkcheck = staffDAO.CapNhapMatKhau(staffDTO,check);
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.putExtra("checkpassword",checkcheck);
                    setResult(RESULT_OK,intent);
                    finish();
                    Toast.makeText(getApplicationContext(), "Updated Password Successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(), "Username Does Not Exist!", Toast.LENGTH_SHORT).show();

            }
        });



    }

    private boolean validateUserName(){
        String val = TXTL_REPASSS_Username.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_REPASSS_Username.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            TXTL_REPASSS_Username.setError(null);
            TXTL_REPASSS_Username.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePassWord(){
        String val = TXTL_REPASSS_PASSWORD.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_REPASSS_PASSWORD.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!PASSWORD_PATTERN.matcher(val).matches()){
            TXTL_REPASSS_PASSWORD.setError("Password must be at least 6 characters!");
            return false;
        }
        else {
            TXTL_REPASSS_PASSWORD.setError(null);
            TXTL_REPASSS_PASSWORD.setErrorEnabled(false);
            return true;
        }
    }
}
