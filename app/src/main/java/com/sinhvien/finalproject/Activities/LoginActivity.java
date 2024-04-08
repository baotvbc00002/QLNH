package com.sinhvien.finalproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.sinhvien.finalproject.DAO.StaffDAO;
import com.sinhvien.finalproject.R;

public class LoginActivity extends AppCompatActivity {
    Button BTN_login_Login, BTN_login_Register, BTN_login_Forgotpassword;
    TextInputLayout TXTL_login_Username, TXTL_login_Password;
    StaffDAO staffDAO;
    private View view;
    public static final String BUNDLE = "BUNDLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        //thuộc tính view
        TXTL_login_Username = (TextInputLayout)findViewById(R.id.txtl_login_Username);
        TXTL_login_Password = (TextInputLayout)findViewById(R.id.txtl_login_Password);
        BTN_login_Login = (Button)findViewById(R.id.btn_login_Login);
        BTN_login_Forgotpassword = (Button)findViewById(R.id.btn_login_forgotpassword);


        staffDAO = new StaffDAO(this);    //khởi tạo kết nối csdl

        BTN_login_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateUserName() | !validatePassWord()){
                    return;
                }

                String Username = TXTL_login_Username.getEditText().getText().toString();
                String Password = TXTL_login_Password.getEditText().getText().toString();
                int check = staffDAO.KiemTraDN(Username,Password);
                int positionid = staffDAO.LayQuyenNV(check);
                if(check != 0){
                    // lưu mã quyền vào shareprefer
                    SharedPreferences sharedPreferences = getSharedPreferences("positionsave", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putInt("positionid",positionid);
                    editor.commit();

                    //gửi dữ liệu user qua trang chủ
                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                    intent.putExtra("Username",TXTL_login_Username.getEditText().getText().toString());
                    intent.putExtra("Password",TXTL_login_Password.getEditText().getText().toString());
                    intent.putExtra("staffid",check);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Login failed!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        BTN_login_Forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PasswordActivity.class);
                startActivity(intent);
            }

        });
    }

    //Hàm quay lại màn hình chính
    public void backFromLogin(View view)
    {
        Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
        //tạo animation cho thành phần
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.layoutLogin),"transition_login");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
            startActivity(intent,options.toBundle());
        }
        else {
            startActivity(intent);
        }
    }

    //Hàm chuyển qua trang đăng ký
    public void callRegisterFromLogin(View view)
    {
        this.view = view;
        Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    //region Validate field
    private boolean validateUserName(){
        String val = TXTL_login_Username.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_login_Username.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            TXTL_login_Username.setError(null);
            TXTL_login_Username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord(){
        String val = TXTL_login_Password.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_login_Password.setError(getResources().getString(R.string.not_empty));
            return false;
        }else{
            TXTL_login_Password.setError(null);
            TXTL_login_Password.setErrorEnabled(false);
            return true;
        }
    }
    //endregion
}