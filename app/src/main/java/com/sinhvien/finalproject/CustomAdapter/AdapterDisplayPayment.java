package com.sinhvien.finalproject.CustomAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sinhvien.finalproject.DTO.PayDTO;
import com.sinhvien.finalproject.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterDisplayPayment extends BaseAdapter {

    Context context;
    int layout;
    List<PayDTO> payDTOList;
    ViewHolder viewHolder;

    public AdapterDisplayPayment(Context context, int layout, List<PayDTO> payDTOList){
        this.context = context;
        this.layout = layout;
        this.payDTOList = payDTOList;
    }

    @Override
    public int getCount() {
        return payDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return payDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.img_custompayment_Foodimage = (CircleImageView)view.findViewById(R.id.img_custompayment_Foodimage);
            viewHolder.txt_custompayment_Foodname = (TextView)view.findViewById(R.id.txt_custompayment_Foodname);
            viewHolder.txt_custompayment_Quantity = (TextView)view.findViewById(R.id.txt_custompayment_Quantity);
            viewHolder.txt_custompayment_Price = (TextView)view.findViewById(R.id.txt_custompayment_Price);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        PayDTO payDTO = payDTOList.get(position);

        viewHolder.txt_custompayment_Foodname.setText(payDTO.getFoodname());
        viewHolder.txt_custompayment_Quantity.setText(String.valueOf(payDTO.getQuantity()));
        viewHolder.txt_custompayment_Price.setText(String.valueOf(payDTO.getPrice())+" đ");

        byte[] paymentimg = payDTO.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(paymentimg,0,paymentimg.length);
        viewHolder.img_custompayment_Foodimage.setImageBitmap(bitmap);

        return view;
    }

    public class ViewHolder{
        CircleImageView img_custompayment_Foodimage;
        TextView txt_custompayment_Foodname, txt_custompayment_Quantity, txt_custompayment_Price;
    }
}
