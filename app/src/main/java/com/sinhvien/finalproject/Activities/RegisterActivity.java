package com.sinhvien.finalproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import static com.sinhvien.finalproject.R.*;

public class RegisterActivity extends AppCompatActivity {

    ImageView IMG_signup_back;
    Button BTN_signup_next;
    TextView TXT_signup_title;
    TextInputLayout TXTL_signup_Fullname, TXTL_signup_Username, TXTL_signup_Email, TXTL_signup_Phone, TXTL_signup_Password;
    public static final String BUNDLE = "BUNDLE";
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{6,}" +                // at least 4 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.register_layout);

        //region gets the view object
        IMG_signup_back = (ImageView)findViewById(id.img_signup_back);
        BTN_signup_next = (Button)findViewById(id.btn_signup_next);
        TXT_signup_title = (TextView)findViewById(id.txt_signup_title);
        TXTL_signup_Fullname = (TextInputLayout)findViewById(id.txtl_signup_Fullname);
        TXTL_signup_Username = (TextInputLayout)findViewById(id.txtl_signup_Username);
        TXTL_signup_Email = (TextInputLayout)findViewById(id.txtl_signup_Email);
        TXTL_signup_Phone = (TextInputLayout)findViewById(id.txtl_signup_Phone);
        TXTL_signup_Password = (TextInputLayout)findViewById(id.txtl_signup_Password);
        //endregion

        BTN_signup_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check validate false => must meet validation requirements
                if(!validateFullName() | !validateUserName() | !validateEmail() | !validatePhone() | !validatePassWord()){
                    return;
                }
                String fullname = TXTL_signup_Fullname.getEditText().getText().toString();
                String username = TXTL_signup_Username .getEditText().getText().toString();
                String eMail = TXTL_signup_Email.getEditText().getText().toString();
                String phone = TXTL_signup_Phone.getEditText().getText().toString();
                String password = TXTL_signup_Password.getEditText().getText().toString();

                byBundleNextSignupScreen(fullname,username,eMail,phone,password);
            }
        });

    }

    //Hàm quay về màn hình trước
    public void backFromRegister(View view){

        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(id.layoutRegister),"transition_signup");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this,pairs);
            startActivity(intent,options.toBundle());
        }else {
            startActivity(intent);
        }
    }

    //truyền dữ liệu qua trang đk thứ 2 bằng bundle
    public void byBundleNextSignupScreen(String fullname, String username, String eMail, String phone, String password){

        Intent intent = new Intent(getApplicationContext(),Register2ndActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("fullname",fullname);
        bundle.putString("username",username);
        bundle.putString("email",eMail);
        bundle.putString("phone",phone);
        bundle.putString("password",password);
        intent.putExtra(BUNDLE,bundle);

        startActivity(intent);
        overridePendingTransition(anim.slide_in_right, anim.slide_out_left);
    }

    //region Validate field
    private boolean validateFullName(){
        String val = TXTL_signup_Fullname.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_signup_Fullname.setError(getResources().getString(string.not_empty));
            return false;
        }else {
            TXTL_signup_Fullname.setError(null);
            TXTL_signup_Fullname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUserName(){
        String val = TXTL_signup_Username.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,50}\\z";

        if(val.isEmpty()){
            TXTL_signup_Username.setError(getResources().getString(string.not_empty));
            return false;
        }else if(val.length()>50){
            TXTL_signup_Username.setError("Must be less than 50 characters");
            return false;
        }else if(!val.matches(checkspaces)){
            TXTL_signup_Username.setError("No spacing allowed!");
            return false;
        }
        else {
            TXTL_signup_Username.setError(null);
            TXTL_signup_Username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail(){
        String val = TXTL_signup_Email.getEditText().getText().toString().trim();
        String checkspaces = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if(val.isEmpty()){
            TXTL_signup_Email.setError(getResources().getString(string.not_empty));
            return false;
        }else if(!val.matches(checkspaces)){
            TXTL_signup_Email.setError("Invalid email!");
            return false;
        }
        else {
            TXTL_signup_Email.setError(null);
            TXTL_signup_Email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone(){
        String val = TXTL_signup_Phone.getEditText().getText().toString().trim();


        if(val.isEmpty()){
            TXTL_signup_Phone.setError(getResources().getString(string.not_empty));
            return false;
        }else if(val.length() != 10){
            TXTL_signup_Phone.setError("Invalid phone number!");
            return false;
        }
        else {
            TXTL_signup_Phone.setError(null);
            TXTL_signup_Phone.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord(){
        String val = TXTL_signup_Password.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_signup_Password.setError(getResources().getString(string.not_empty));
            return false;
        }else if(!PASSWORD_PATTERN.matcher(val).matches()){
            TXTL_signup_Password.setError("Password must be at least 6 characters!");
            return false;
        }
        else {
            TXTL_signup_Password.setError(null);
            TXTL_signup_Password.setErrorEnabled(false);
            return true;
        }
    }
    //endregion
}