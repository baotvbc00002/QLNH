package com.sinhvien.finalproject.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.sinhvien.finalproject.DAO.FoodDAO;
import com.sinhvien.finalproject.DTO.FoodDTO;
import com.sinhvien.finalproject.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddMenuActivity extends AppCompatActivity implements View.OnClickListener{

    Button BTN_addmenu_Addfood;
    RelativeLayout layout_statusfood;
    ImageView IMG_addmenu_AddImage, IMG_addmenu_back;
    TextView TXT_addmenu_title;
    TextInputLayout TXTL_addmenu_Foodname,TXTL_addmenu_Price,TXTL_addmenu_Typefood;
    RadioGroup RG_addmenu_Status;
    RadioButton RD_addmenu_Instockfood, RD_addmenu_Outoffood;
    FoodDAO foodDAO;
    String typename, sFoodname,sPrice,sStatus;
    Bitmap bitmapold;
    int typeid;
    int foodid = 0;

    ActivityResultLauncher<Intent> resultLauncherOpenIMG = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                        Uri uri = result.getData().getData();
                        try{
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            IMG_addmenu_AddImage.setImageBitmap(bitmap);
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmenu_layout);

        //region Lấy đối tượng view
        IMG_addmenu_AddImage = (ImageView)findViewById(R.id.img_addmenu_AddImage);
        IMG_addmenu_AddImage.setTag(R.drawable.addattachicon);
        IMG_addmenu_back = (ImageView)findViewById(R.id.img_addmenu_back);
        TXTL_addmenu_Foodname = (TextInputLayout)findViewById(R.id.txtl_addmenu_Foodname);
        TXTL_addmenu_Price = (TextInputLayout)findViewById(R.id.txtl_addmenu_Price);
        TXTL_addmenu_Typefood = (TextInputLayout)findViewById(R.id.txtl_addmenu_Typefood);
        BTN_addmenu_Addfood = (Button)findViewById(R.id.btn_addmenu_Addfood);
        TXT_addmenu_title = (TextView)findViewById(R.id.txt_addmenu_title);
        layout_statusfood = (RelativeLayout)findViewById(R.id.layout_statusfood);
        RG_addmenu_Status = (RadioGroup)findViewById(R.id.rg_addmenu_Status);
        RD_addmenu_Instockfood = (RadioButton)findViewById(R.id.rd_addmenu_Instockfood);
        RD_addmenu_Outoffood = (RadioButton)findViewById(R.id.rd_addmenu_Outoffood);
        //endregion

        Intent intent = getIntent();
        typeid = intent.getIntExtra("typeid",-1);
        typename = intent.getStringExtra("typename");
        foodDAO = new FoodDAO(this);  //khởi tạo đối tượng dao kết nối csdl
        TXTL_addmenu_Typefood.getEditText().setText(typename);

        BitmapDrawable olddrawable = (BitmapDrawable)IMG_addmenu_AddImage.getDrawable();
        bitmapold = olddrawable.getBitmap();

        //region Hiển thị trang sửa nếu được chọn từ context menu sửa
        foodid = getIntent().getIntExtra("foodid",0);
        if(foodid != 0){
            TXT_addmenu_title.setText("Edit menu");
            FoodDTO foodDTO = foodDAO.LayMonTheoMa(foodid);

            TXTL_addmenu_Foodname.getEditText().setText(foodDTO.getFoodname());
            TXTL_addmenu_Price.getEditText().setText(foodDTO.getPrice());

            byte[] menuimage = foodDTO.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(menuimage,0,menuimage.length);
            IMG_addmenu_AddImage.setImageBitmap(bitmap);

            layout_statusfood.setVisibility(View.VISIBLE);
            String status = foodDTO.getStatus();
            if(status .equals("true")){
                RD_addmenu_Instockfood.setChecked(true);
            }else {
                RD_addmenu_Outoffood.setChecked(true);
            }

            BTN_addmenu_Addfood.setText("Edit Food");
        }

        //endregion

        IMG_addmenu_AddImage.setOnClickListener(this);
        BTN_addmenu_Addfood.setOnClickListener(this);
        IMG_addmenu_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        boolean check;
        String function;
        switch (id){
            case R.id.img_addmenu_AddImage:
                Intent iGetIMG = new Intent();
                iGetIMG.setType("image/*");
                iGetIMG.setAction(Intent.ACTION_GET_CONTENT);
                resultLauncherOpenIMG.launch(Intent.createChooser(iGetIMG,getResources().getString(R.string.choseimg)));
                break;

            case R.id.img_addmenu_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;

            case R.id.btn_addmenu_Addfood:
                //ktra validation
                if(!validateImage() | !validateName() | !validatePrice()){
                    return;
                }

                sFoodname = TXTL_addmenu_Foodname.getEditText().getText().toString();
                sPrice = TXTL_addmenu_Price.getEditText().getText().toString();
                switch (RG_addmenu_Status.getCheckedRadioButtonId()){
                    case R.id.rd_addmenu_Instockfood: sStatus = "true";   break;
                    case R.id.rd_addmenu_Outoffood: sStatus = "false";  break;
                }
                FoodDTO foodDTO = new FoodDTO();
                foodDTO.setTypeid(typeid);
                foodDTO.setFoodname(sFoodname);
                foodDTO.setPrice(sPrice);
                foodDTO.setStatus(sStatus);
                foodDTO.setImage(imageViewtoByte(IMG_addmenu_AddImage));
                if(foodid!= 0){
                    check = foodDAO.SuaMon(foodDTO,foodid);
                    function = "editfood";
                }else {
                    check = foodDAO.ThemMon(foodDTO);
                    function = "addfood";
                }

                //Thêm, sửa món dựa theo obj loaimonDTO
                Intent intent = new Intent();
                intent.putExtra("check",check );
                intent.putExtra("function",function);
                setResult(RESULT_OK,intent);
                finish();

                break;
        }
    }

    //Chuyển ảnh bitmap về mảng byte lưu vào csdl
    private byte[] imageViewtoByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    //region Validate field
    private boolean validateImage(){
        BitmapDrawable drawable = (BitmapDrawable)IMG_addmenu_AddImage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        if(bitmap == bitmapold){
            Toast.makeText(getApplicationContext(),"Please select the image",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validateName(){
        String val = TXTL_addmenu_Foodname.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_addmenu_Foodname.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            TXTL_addmenu_Foodname.setError(null);
            TXTL_addmenu_Foodname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePrice(){
        String val = TXTL_addmenu_Price.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_addmenu_Price.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!val.matches(("\\d+(?:\\.\\d+)?"))){
            TXTL_addmenu_Price.setError("Price is not valid");
            return false;
        }else {
            TXTL_addmenu_Price.setError(null);
            TXTL_addmenu_Price.setErrorEnabled(false);
            return true;
        }
    }
    //endregion

}