package com.sinhvien.finalproject.CustomAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sinhvien.finalproject.DTO.TypefoodDTO;
import com.sinhvien.finalproject.R;

import java.util.List;

public class AdapterRecycleViewCategory extends RecyclerView.Adapter<AdapterRecycleViewCategory.ViewHolder>{

    Context context;
    int layout;
    List<TypefoodDTO> typefoodDTOList;

    public AdapterRecycleViewCategory(Context context,int layout, List<TypefoodDTO> typefoodDTOList){
        this.context = context;
        this.layout = layout;
        this.typefoodDTOList = typefoodDTOList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRecycleViewCategory.ViewHolder holder, int position) {
        TypefoodDTO typefoodDTO = typefoodDTOList.get(position);
        holder.txt_customcategory_Categoryname.setText(typefoodDTO.getTypename());
        byte[] typeimage = typefoodDTO.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(typeimage,0,typeimage.length);
        holder.img_customcategory_Categoryimage.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return typefoodDTOList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_customcategory_Categoryname;
        ImageView img_customcategory_Categoryimage;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txt_customcategory_Categoryname = itemView.findViewById(R.id.txt_customcategory_Categoryname);
            img_customcategory_Categoryimage = itemView.findViewById(R.id.img_customcategory_Categoryimage);
        }
    }

}
