package com.sinhvien.finalproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.sinhvien.finalproject.DAO.StaffDAO;
import com.sinhvien.finalproject.DTO.StaffDTO;
import com.sinhvien.finalproject.R;

import java.util.Calendar;
import java.util.regex.Pattern;

public class AddStaffActivity extends AppCompatActivity implements View.OnClickListener{

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{6,}" +                // at least 4 characters
                    "$");

    ImageView IMG_addstaff_back;
    TextView TXT_addstaff_title;
    TextInputLayout TXTL_addstaff_Fullname, TXTL_addstaff_Username, TXTL_addstaff_Email, TXTL_addstaff_Phone, TXTL_addstaff_Password;
    RadioGroup RG_addstaff_Sex,rg_addstaff_Position;
    RadioButton RD_addstaff_Female,RD_addstaff_Male,RD_addstaff_Orther,rd_addstaff_Manager,rd_addstaff_Staff;
    DatePicker DT_addstaff_Dateofbirth;
    Button BTN_addstaff_Addstaff;
    StaffDAO staffDAO;
    String fullname,username,eMail,phone,password,sEx,dateOfbirt;
    int staffid = 0,position = 0;
    long check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addstaff_layout);

        //region Lấy đối tượng trong view
        TXT_addstaff_title = (TextView)findViewById(R.id.txt_addstaff_title);
        IMG_addstaff_back = (ImageView)findViewById(R.id.img_addstaff_back);
        TXTL_addstaff_Fullname = (TextInputLayout)findViewById(R.id.txtl_addstaff_Fullname);
        TXTL_addstaff_Username = (TextInputLayout)findViewById(R.id.txtl_addstaff_Username);
        TXTL_addstaff_Email = (TextInputLayout)findViewById(R.id.txtl_addstaff_Email);
        TXTL_addstaff_Phone = (TextInputLayout)findViewById(R.id.txtl_addstaff_Phone);
        TXTL_addstaff_Password = (TextInputLayout)findViewById(R.id.txtl_addstaff_Password);
        RG_addstaff_Sex = (RadioGroup)findViewById(R.id.rg_addstaff_Sex);
        rg_addstaff_Position = (RadioGroup)findViewById(R.id.rg_addstaff_Position);
        RD_addstaff_Female = (RadioButton)findViewById(R.id.rd_addstaff_Female);
        RD_addstaff_Male = (RadioButton)findViewById(R.id.rd_addstaff_Male);
        RD_addstaff_Orther = (RadioButton)findViewById(R.id.rd_addstaff_Orther);
        rd_addstaff_Manager = (RadioButton)findViewById(R.id.rd_addstaff_Manager);
        rd_addstaff_Staff = (RadioButton)findViewById(R.id.rd_addstaff_Staff);
        DT_addstaff_Dateofbirth = (DatePicker)findViewById(R.id.dt_addstaff_Dateofbirth);
        BTN_addstaff_Addstaff = (Button)findViewById(R.id.btn_addstaff_Addstaff);

        //endregion

        staffDAO = new StaffDAO(this);

        //region Hiển thị trang sửa nếu được chọn từ context menu sửa
        staffid = getIntent().getIntExtra("idstaff",0);   //lấy manv từ display staff
        if(staffid != 0){
            TXT_addstaff_title.setText("Edit Staff");
            StaffDTO staffDTO = staffDAO.LayNVTheoMa((staffid));

            //Hiển thị thông tin từ csdl
            TXTL_addstaff_Fullname.getEditText().setText(staffDTO.getFULLNAME());
            TXTL_addstaff_Username.getEditText().setText(staffDTO.getUSERNAME());
            TXTL_addstaff_Email.getEditText().setText(staffDTO.getEMAIL());
            TXTL_addstaff_Phone.getEditText().setText(staffDTO.getPHONE());
            TXTL_addstaff_Password.getEditText().setText(staffDTO.getPASSWORD());

            //Hiển thị giới tính từ csdl
            String dateofbirt = staffDTO.getSEX();
            if(dateofbirt.equals("Femal")){
                RD_addstaff_Female.setChecked(true);
            }else if (dateofbirt.equals("Male")){
                RD_addstaff_Male.setChecked(true);
            }else {
                RD_addstaff_Orther.setChecked(true);
            }

            if(staffDTO.getPOSITIONID() == 1){
                rd_addstaff_Manager.setChecked(true);
            }else {
                rd_addstaff_Staff.setChecked(true);
            }

            //Hiển thị ngày sinh từ csdl
            String date = staffDTO.getDATEOFBIRTH();
            String[] items = date.split("/");
            int day = Integer.parseInt(items[0]);
            int month = Integer.parseInt(items[1]) - 1;
            int year = Integer.parseInt(items[2]);
            DT_addstaff_Dateofbirth.updateDate(year,month,day);
            BTN_addstaff_Addstaff.setText("Edit Staff");
        }
        //endregion

        BTN_addstaff_Addstaff.setOnClickListener(this);
        IMG_addstaff_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String function;
        switch (id){
            case R.id.btn_addstaff_Addstaff:
                if( !validateAge() | !validateEmail() | !validateFullName() | !validateGender() | !validatePassWord() |
                !validatePermission() | !validatePhone() | !validateUserName()){
                    return;
                }
                //Lấy dữ liệu từ view
                fullname = TXTL_addstaff_Fullname.getEditText().getText().toString();
                username = TXTL_addstaff_Username.getEditText().getText().toString();
                eMail = TXTL_addstaff_Email.getEditText().getText().toString();
                phone = TXTL_addstaff_Phone.getEditText().getText().toString();
                password= TXTL_addstaff_Password.getEditText().getText().toString();

                switch (RG_addstaff_Sex.getCheckedRadioButtonId()){
                    case R.id.rd_addstaff_Female: sEx = "Female"; break;
                    case R.id.rd_addstaff_Male: sEx = "Male"; break;
                    case R.id.rd_addstaff_Orther: sEx = "Orther"; break;
                }
                switch (rg_addstaff_Position.getCheckedRadioButtonId()){
                    case R.id.rd_addstaff_Manager: position = 1; break;
                    case R.id.rd_addstaff_Staff: position = 2; break;
                }

                dateOfbirt = DT_addstaff_Dateofbirth.getDayOfMonth() + "/" + (DT_addstaff_Dateofbirth.getMonth() + 1)
                        +"/"+DT_addstaff_Dateofbirth.getYear();

                //truyền dữ liệu vào obj nhanvienDTO
                StaffDTO staffDTO = new StaffDTO();
                staffDTO.setFULLNAME(fullname);
                staffDTO.setUSERNAME(username);
                staffDTO.setEMAIL(eMail);
                staffDTO.setPHONE(phone);
                staffDTO.setPASSWORD(password);
                staffDTO.setSEX(sEx);
                staffDTO.setDATEOFBIRTH(dateOfbirt);
                staffDTO.setPOSITIONID(position);

                if(staffid != 0){
                    check = staffDAO.SuaNhanVien(staffDTO,staffid);
                    function = "editstaff";
                }else {
                    check = staffDAO.ThemNhanVien(staffDTO);
                    function = "addstaf";
                }
                //Thêm, sửa nv dựa theo obj nhanvienDTO
                Intent intent = new Intent();
                intent.putExtra("check",check);
                intent.putExtra("function",function);
                setResult(RESULT_OK,intent);
                finish();
                break;

            case R.id.img_addstaff_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;
        }
    }

    //region validate fields
    private boolean validateFullName(){
        String val = TXTL_addstaff_Fullname.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_addstaff_Fullname.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            TXTL_addstaff_Fullname.setError(null);
            TXTL_addstaff_Fullname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUserName(){
        String val = TXTL_addstaff_Username.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,50}\\z";

        if(val.isEmpty()){
            TXTL_addstaff_Username.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(val.length()>50){
            TXTL_addstaff_Username.setError("Must be less than 50 characters");
            return false;
        }else if(!val.matches(checkspaces)){
            TXTL_addstaff_Username.setError("No spacing allowed!");
            return false;
        }
        else {
            TXTL_addstaff_Username.setError(null);
            TXTL_addstaff_Username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail(){
        String val = TXTL_addstaff_Email.getEditText().getText().toString().trim();
        String checkspaces = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if(val.isEmpty()){
            TXTL_addstaff_Email.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!val.matches(checkspaces)){
            TXTL_addstaff_Email.setError("Invalid email!");
            return false;
        }
        else {
            TXTL_addstaff_Email.setError(null);
            TXTL_addstaff_Email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone(){
        String val = TXTL_addstaff_Phone.getEditText().getText().toString().trim();


        if(val.isEmpty()){
            TXTL_addstaff_Phone.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(val.length() != 10){
            TXTL_addstaff_Phone.setError("invalid phone number!");
            return false;
        }
        else {
            TXTL_addstaff_Phone.setError(null);
            TXTL_addstaff_Phone.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord(){
        String val = TXTL_addstaff_Password.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_addstaff_Password.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!PASSWORD_PATTERN.matcher(val).matches()){
            TXTL_addstaff_Password.setError("Password at least 6 characters!");
            return false;
        }
        else {
            TXTL_addstaff_Password.setError(null);
            TXTL_addstaff_Password.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateGender(){
        if(RG_addstaff_Sex.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,"Please select gender",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validatePermission(){
        if(rg_addstaff_Position.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,"Please choose right",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validateAge(){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = DT_addstaff_Dateofbirth.getYear();
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