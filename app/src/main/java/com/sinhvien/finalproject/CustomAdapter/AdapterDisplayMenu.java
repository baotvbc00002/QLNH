package com.sinhvien.finalproject.CustomAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.sinhvien.finalproject.DTO.FoodDTO;
import com.sinhvien.finalproject.R;

import java.util.List;

public class AdapterDisplayMenu extends BaseAdapter {

    Context context;
    int layout;
    List<FoodDTO> foodDTOList;
    Viewholder viewholder;

    //constructor
    public AdapterDisplayMenu(Context context, int layout, List<FoodDTO> foodDTOList){
        this.context = context;
        this.layout = layout;
        this.foodDTOList = foodDTOList;
    }

    public AdapterDisplayMenu(FragmentActivity activity, int customLayoutDisplaymenu, List<FoodDTO> foodDTOList) {
    }

    @Override
    public int getCount() {
        return foodDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return foodDTOList.get(position).getFoodid();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewholder = new Viewholder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewholder.img_custommenu_Foodimage = (ImageView)view.findViewById(R.id.img_custommenu_Foodimage);
            viewholder.txt_custommenu_Foodname = (TextView) view.findViewById(R.id.txt_custommenu_Foodname);
            viewholder.txt_custommenu_Status = (TextView)view.findViewById(R.id.txt_custommenu_Status);
            viewholder.txt_custommenu_Price = (TextView)view.findViewById(R.id.txt_custommenu_Price);
            view.setTag(viewholder);
        }else {
            viewholder = (Viewholder) view.getTag();
        }
        FoodDTO dishDTO = foodDTOList.get(position);
        viewholder.txt_custommenu_Foodname.setText(dishDTO.getFoodname());
        viewholder.txt_custommenu_Price.setText(dishDTO.getPrice()+" VNĐ");

        //hiển thị tình trạng của món
        if(dishDTO.getStatus().equals("true")){
            viewholder.txt_custommenu_Status.setText("In stock");
        }else{
            viewholder.txt_custommenu_Status.setText("Out Food");
        }

        //lấy hình ảnh
        if(dishDTO.getImage() != null){
            byte[] menuimage = dishDTO.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(menuimage,0,menuimage.length);
            viewholder.img_custommenu_Foodimage.setImageBitmap(bitmap);
        }else {
            viewholder.img_custommenu_Foodimage.setImageResource(R.drawable.cafe_americano);
        }

        return view;
    }

    //tạo viewholer lưu trữ component
    public class Viewholder{
        ImageView img_custommenu_Foodimage;
        TextView txt_custommenu_Foodname, txt_custommenu_Price,txt_custommenu_Status;

    }
}
