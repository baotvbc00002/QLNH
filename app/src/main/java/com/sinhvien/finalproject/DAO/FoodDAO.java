package com.sinhvien.finalproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sinhvien.finalproject.DTO.FoodDTO;
import com.sinhvien.finalproject.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class FoodDAO {
    SQLiteDatabase database;
    public FoodDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemMon(FoodDTO foodDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_FOOD_TYPEID,foodDTO.getTypeid());
        contentValues.put(CreateDatabase.TBL_FOOD_FOODNAME,foodDTO.getFoodname());
        contentValues.put(CreateDatabase.TBL_FOOD_PRICE,foodDTO.getPrice());
        contentValues.put(CreateDatabase.TBL_FOOD_IMAGE,foodDTO.getImage());
        contentValues.put(CreateDatabase.TBL_FOOD_STATUS,"true");

        long check = database.insert(CreateDatabase.TBL_FOOD,null,contentValues);

        if(check !=0){
            return true;
        }else {
            return false;
        }
    }

    public boolean XoaMon(int foodid){
        long check = database.delete(CreateDatabase.TBL_FOOD,CreateDatabase.TBL_FOOD_FOODID+ " = " +foodid
                ,null);
        if(check !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public boolean SuaMon(FoodDTO foodDTO, int foodid){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_FOOD_TYPEID,foodDTO.getTypeid());
        contentValues.put(CreateDatabase.TBL_FOOD_FOODNAME,foodDTO.getFoodname());
        contentValues.put(CreateDatabase.TBL_FOOD_PRICE,foodDTO.getPrice());
        contentValues.put(CreateDatabase.TBL_FOOD_IMAGE,foodDTO.getImage());
        contentValues.put(CreateDatabase.TBL_FOOD_STATUS,foodDTO.getStatus());

        long check = database.update(CreateDatabase.TBL_FOOD,contentValues,
                CreateDatabase.TBL_FOOD_FOODID+" = "+foodid,null);
        if(check !=0){
            return true;
        }else {
            return false;
        }
    }

    public List<FoodDTO> LayDSMonTheoLoai(int typeid){
        List<FoodDTO> foodDTOList = new ArrayList<FoodDTO>();
        String query = "SELECT * FROM " +CreateDatabase.TBL_FOOD+ " WHERE " +CreateDatabase.TBL_FOOD_TYPEID+ " = '" +typeid+ "' ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            FoodDTO foodDTO = new FoodDTO();
            foodDTO.setImage(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_FOOD_IMAGE)));
            foodDTO.setFoodname(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_FOOD_FOODNAME)));
            foodDTO.setTypeid(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_FOOD_TYPEID)));
            foodDTO.setFoodid(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_FOOD_FOODID)));
            foodDTO.setPrice(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_FOOD_PRICE)));
            foodDTO.setStatus(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_FOOD_STATUS)));
            foodDTOList.add(foodDTO);

            cursor.moveToNext();
        }
        return foodDTOList;
    }

    public FoodDTO LayMonTheoMa(int foodid){
        FoodDTO foodDTO = new FoodDTO();
        String query = "SELECT * FROM "+CreateDatabase.TBL_FOOD+" WHERE "+CreateDatabase.TBL_FOOD_FOODID+" = "+foodid;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            foodDTO.setImage(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_FOOD_IMAGE)));
            foodDTO.setFoodname(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_FOOD_FOODNAME)));
            foodDTO.setTypeid(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_FOOD_TYPEID)));
            foodDTO.setFoodid(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_FOOD_FOODID)));
            foodDTO.setPrice(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_FOOD_PRICE)));
            foodDTO.setStatus(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_FOOD_STATUS)));

            cursor.moveToNext();
        }
        return foodDTO;
    }

}
