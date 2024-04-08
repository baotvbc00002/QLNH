package com.sinhvien.finalproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sinhvien.finalproject.DAO.PositionDAO;
import com.sinhvien.finalproject.DAO.StaffDAO;
import com.sinhvien.finalproject.DTO.StaffDTO;
import com.sinhvien.finalproject.R;

import java.util.Calendar;

public class Register2ndActivity extends AppCompatActivity {

    RadioGroup RG_signup_Sex;
    DatePicker DT_signup_Dayofbirth;
    Button BTN_signup_next;
    String fullname,Username,eMail,phone,password,sEx,Dateofbirth;
    StaffDAO staffDAO;
    PositionDAO positionDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register2nd_layout);

        //lấy đối tượng view
        RG_signup_Sex = (RadioGroup)findViewById(R.id.rg_signup_Sex);
        DT_signup_Dayofbirth = (DatePicker)findViewById(R.id.dt_signup_Dayofbirth);
        BTN_signup_next = (Button)findViewById(R.id.btn_signup_next);

        //lấy dữ liệu từ bundle của register1
        Bundle bundle = getIntent().getBundleExtra(RegisterActivity.BUNDLE);
        if(bundle != null) {
            fullname = bundle.getString("fullname");
            Username = bundle.getString("Username");
             eMail = bundle.getString("email");
            phone = bundle.getString("phone");
            password = bundle.getString("password");
        }
        //khởi tạo kết nối csdl
        staffDAO = new StaffDAO(this);
        positionDAO = new PositionDAO(this);

        BTN_signup_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateAge() | !validateGender()){
                    return;
                }

                //lấy các thông tin còn lại
                switch (RG_signup_Sex.getCheckedRadioButtonId()){
                    case R.id.rd_signup_Male:
                        sEx = "Male"; break;
                    case R.id.rd_signup_Female:
                        sEx = "Female"; break;
                    case R.id.rd_signup_Orther:
                        sEx = "None"; break;
                }
                String ngaySinh = DT_signup_Dayofbirth.getDayOfMonth() + "/" + (DT_signup_Dayofbirth.getMonth() + 1)
                        +"/"+DT_signup_Dayofbirth.getYear();

                //truyền dữ liệu vào obj nhanvienDTO
                StaffDTO staffDTO = new StaffDTO();
                staffDTO.setFULLNAME(fullname);
                staffDTO.setUSERNAME(Username);
                staffDTO.setEMAIL(eMail);
                staffDTO.setPHONE(phone);
                staffDTO.setPASSWORD(password);
                staffDTO.setSEX(sEx);
                staffDTO.setDATEOFBIRTH(Dateofbirth);

                //nếu nhân viên đầu tiên đăng ký sẽ có quyền quản lý
                if(!staffDAO.KtraTonTaiNV()){
                    positionDAO.ThemQuyen("Manager");
                    positionDAO.ThemQuyen("Staff");
                    staffDTO.setPOSITIONID(1);
                }else {
                    staffDTO.setPOSITIONID(2);
                }

                //Thêm nv dựa theo obj nhanvienDTO
                long check = staffDAO.ThemNhanVien(staffDTO);
                if(check != 0){
                    Toast.makeText(Register2ndActivity.this,getResources().getString(R.string.add_sucessful),Toast.LENGTH_SHORT).show();
                    callLoginFromRegister();
                }else{
                    Toast.makeText(Register2ndActivity.this,getResources().getString(R.string.add_failed),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Hàm quay về màn hình trước
    public void backFromRegister2nd(View view){
        Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    //Hàm chuyển màn hình khi hoàn thành
    public void callLoginFromRegister(){
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    //region Validate field
    private boolean validateGender(){
        if(RG_signup_Sex.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,"Please select gender",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validateAge(){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = DT_signup_Dayofbirth.getYear();
        int isAgeValid = currentYear - userAge;

        if(isAgeValid < 10){
            Toast.makeText(this,"You are not old enough to register!",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
    //endregion
}