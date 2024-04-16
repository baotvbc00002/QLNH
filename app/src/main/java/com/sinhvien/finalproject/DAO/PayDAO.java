package com.sinhvien.finalproject.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sinhvien.finalproject.DTO.PayDTO;
import com.sinhvien.finalproject.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class PayDAO {

    SQLiteDatabase database;
    public PayDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public List<PayDTO> LayDSMonTheoMaDon(int orderid){
        List<PayDTO> payDTOS = new ArrayList<PayDTO>();
        String query = "SELECT * FROM "+CreateDatabase.TBL_ORDERDETAILS+" ctdd,"+CreateDatabase.TBL_FOOD+" food WHERE "
                +"ctdd."+CreateDatabase.TBL_ORDERDETAILS_FOODID+" = food."+CreateDatabase.TBL_FOOD_FOODID+" AND "
                +CreateDatabase.TBL_ORDERDETAILS_ORDERID+" = '"+orderid+"'";

        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            PayDTO payDTO = new PayDTO();
            payDTO.setQuantity(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_ORDERDETAILS_QUANTITY)));
            payDTO.setPrice(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_FOOD_PRICE)));
            payDTO.setFoodname(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_FOOD_FOODNAME)));
            payDTO.setImage(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_FOOD_IMAGE)));
            payDTOS.add(payDTO);

            cursor.moveToNext();
        }

        return payDTOS;
    }
}
