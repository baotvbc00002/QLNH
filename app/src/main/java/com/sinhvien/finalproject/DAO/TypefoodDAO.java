package com.sinhvien.finalproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sinhvien.finalproject.DTO.TypefoodDTO;
import com.sinhvien.finalproject.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class TypefoodDAO {

    SQLiteDatabase database;
    public TypefoodDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemLoaiMon(TypefoodDTO typefoodDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_TYPEFOOD_TPYENAME,typefoodDTO.getTypename());
        contentValues.put(CreateDatabase.TBL_TYPEFOOD_IMAGE,typefoodDTO.getImage());
        long check = database.insert(CreateDatabase.TBL_TYPEFOOD,null,contentValues);

        if(check != 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean XoaLoaiMon(int typeid){
        long check= database.delete(CreateDatabase.TBL_TYPEFOOD,CreateDatabase.TBL_TYPEFOOD+ " = " +typeid
                ,null);
        if(check !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public boolean SuaLoaiMon(TypefoodDTO typefoodDTO, int typeid){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_TYPEFOOD_TPYENAME,typefoodDTO.getTypename());
        contentValues.put(CreateDatabase.TBL_TYPEFOOD_IMAGE,typefoodDTO.getImage());
        long check  = database.update(CreateDatabase.TBL_TYPEFOOD,contentValues
                ,CreateDatabase.TBL_TYPEFOOD_TYPEID+" = "+typeid,null);
        if(check  != 0){
            return true;
        }else {
            return false;
        }
    }

    public List<TypefoodDTO> LayDSLoaiMon(){
        List<TypefoodDTO> typefoodDTOList = new ArrayList<TypefoodDTO>();
        String query = "SELECT * FROM " +CreateDatabase.TBL_TYPEFOOD;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            TypefoodDTO typefoodDTO = new TypefoodDTO();
            typefoodDTO.setTypeid(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_TYPEFOOD_TYPEID)));
            typefoodDTO.setTypename(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_TYPEFOOD_TPYENAME)));
            typefoodDTO.setImage(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_TYPEFOOD_IMAGE)));
            typefoodDTOList.add(typefoodDTO);

            cursor.moveToNext();
        }
        return typefoodDTOList;
    }

    public TypefoodDTO LayLoaiMonTheoMa(int typeid){
        TypefoodDTO typefoodDTO = new TypefoodDTO();
        String query = "SELECT * FROM " +CreateDatabase.TBL_TYPEFOOD+" WHERE "+CreateDatabase.TBL_TYPEFOOD_TYPEID+" = "+typeid;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            typefoodDTO.setTypeid(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_TYPEFOOD_TYPEID)));
            typefoodDTO.setTypename(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_TYPEFOOD_TPYENAME)));
            typefoodDTO.setImage(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_TYPEFOOD_IMAGE)));

            cursor.moveToNext();
        }
        return typefoodDTO;
    }

}
