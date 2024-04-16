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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.sinhvien.finalproject.DAO.TypefoodDAO;
import com.sinhvien.finalproject.DTO.TypefoodDTO;
import com.sinhvien.finalproject.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    Button BTN_addcategory_Addcategory;
    ImageView IMG_addcategory_back, IMG_addcategory_AddImage;
    TextView TXT_addcategory_title;
    TextInputLayout TXTL_addcategory_Namecategory;
    TypefoodDAO typefoodDAO;
    int typeid= 0;
    Bitmap bitmapold;   //Bitmap dạng ảnh theo ma trận các pixel

    //dùng result launcher do activityforresult ko dùng đc nữa
    ActivityResultLauncher<Intent> resultLauncherOpenIMG = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                        Uri uri = result.getData().getData();
                        try{
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            IMG_addcategory_AddImage.setImageBitmap(bitmap);
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcategory_layout);

        typefoodDAO = new TypefoodDAO(this);  //initialize the database connection object

        //region Gets the view object
        BTN_addcategory_Addcategory = (Button)findViewById(R.id.btn_addcategory_Addcategory);
        TXTL_addcategory_Namecategory = (TextInputLayout)findViewById(R.id.txtl_addcategory_Namecategory);
        IMG_addcategory_back = (ImageView)findViewById(R.id.img_addcategory_back);
        IMG_addcategory_AddImage = (ImageView)findViewById(R.id.img_addcategory_AddImage);
        TXT_addcategory_title = (TextView)findViewById(R.id.txt_addcategory_title);
        //endregion

        BitmapDrawable olddrawable = (BitmapDrawable)IMG_addcategory_AddImage.getDrawable();
        bitmapold = olddrawable.getBitmap();

        //region Displays the edit page if selected from the edit context menu
        typeid= getIntent().getIntExtra("idtype",0);
        if(typeid != 0){
            TXT_addcategory_title.setText(getResources().getString(R.string.editcategory));
            TypefoodDTO typefoodDTO = typefoodDAO.LayLoaiMonTheoMa(typeid);

            //Display information again from the database
            TXTL_addcategory_Namecategory.getEditText().setText(typefoodDTO.getTypename());

            byte[] typeimage = typefoodDTO.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray( typeimage,0,typeimage.length);
            IMG_addcategory_AddImage.setImageBitmap(bitmap);

            BTN_addcategory_Addcategory.setText("Edit Category");
        }
        //endregion

        IMG_addcategory_back.setOnClickListener(this);
        IMG_addcategory_AddImage.setOnClickListener(this);
        BTN_addcategory_Addcategory.setOnClickListener(this);
        }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        boolean check;
        String function;
        switch (id){
            case R.id.img_addcategory_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right); //animation
                break;

            case R.id.img_addcategory_AddImage:
                Intent iGetIMG = new Intent();
                iGetIMG.setType("image/*"); //lấy những mục chứa hình ảnh
                iGetIMG.setAction(Intent.ACTION_GET_CONTENT);   //lấy mục hiện tại đang chứa hình
                resultLauncherOpenIMG.launch(Intent.createChooser(iGetIMG,getResources().getString(R.string.choseimg)));    //mở intent chọn hình ảnh
                break;

            case R.id.btn_addcategory_Addcategory:
                if(!validateImage() | !validateName()){
                    return;
                }

                String sTypeName = TXTL_addcategory_Namecategory.getEditText().getText().toString();
                TypefoodDTO typefoodDTO = new TypefoodDTO();
                typefoodDTO.setTypename(sTypeName);
                typefoodDTO .setImage(imageViewtoByte(IMG_addcategory_AddImage));
                if(typeid != 0){
                    check = typefoodDAO.SuaLoaiMon(typefoodDTO,typeid);
                    function = "editcategory";
                }else {
                    check = typefoodDAO.ThemLoaiMon(typefoodDTO);
                    function = "addtypefood";
                }

                //Thêm, sửa loại dựa theo obj loaimonDTO
                Intent intent = new Intent();
                intent.putExtra("check",check);
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

    //region validate fields
    private boolean validateImage(){
        BitmapDrawable drawable = (BitmapDrawable)IMG_addcategory_AddImage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        if(bitmap == bitmapold){
            Toast.makeText(getApplicationContext(),"Please select the image",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validateName(){
        String val = TXTL_addcategory_Namecategory.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_addcategory_Namecategory.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            TXTL_addcategory_Namecategory.setError(null);
            TXTL_addcategory_Namecategory.setErrorEnabled(false);
            return true;
        }
    }
    //endregion

}