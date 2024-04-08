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

import com.sinhvien.finalproject.DTO.TypefoodDTO;
import com.sinhvien.finalproject.R;

import java.util.List;

public class AdapterDisplayCategory extends BaseAdapter {

    Context context;
    int layout;
    List<TypefoodDTO> typefoodDTOList ;
    ViewHolder viewHolder;

    //constructor
    public AdapterDisplayCategory(Context context, int layout, List<TypefoodDTO> typefoodDTOList){
        this.context = context;
        this.layout = layout;
        this.typefoodDTOList = typefoodDTOList;
    }

    @Override
    public int getCount() {
        return typefoodDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return typefoodDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return typefoodDTOList.get(position).getTypeid();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        //nếu lần đầu gọi view
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            //truyền component vào viewholder để ko gọi findview ở những lần hiển thị khác
            viewHolder.img_customcategory_Categoryimage = (ImageView)view.findViewById(R.id.img_customcategory_Categoryimage);
            viewHolder.txt_customcategory_Categoryname = (TextView)view.findViewById(R.id.txt_customcategory_Categoryname);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        TypefoodDTO typefoodDTO = typefoodDTOList.get(position);

        viewHolder.txt_customcategory_Categoryname.setText(typefoodDTO.getTypename());

        byte[] categoryimage = typefoodDTO.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(categoryimage,0,categoryimage.length);
        viewHolder.img_customcategory_Categoryimage.setImageBitmap(bitmap);

        return view;
    }

    //tạo viewholer lưu trữ component
    public class ViewHolder{
        TextView txt_customcategory_Categoryname;
        ImageView img_customcategory_Categoryimage;
    }
}
