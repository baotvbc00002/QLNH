package com.sinhvien.finalproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sinhvien.finalproject.DTO.DinnertableDTO;
import com.sinhvien.finalproject.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class DinnertableDAO {
    SQLiteDatabase database;
    public DinnertableDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    //Hàm thêm bàn ăn mới
    public boolean ThemBanAn(String tablename){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_TABLE_TABLENAME,tablename);
        contentValues.put(CreateDatabase.TBL_TABLE_STATUS,"false");

        long check = database.insert(CreateDatabase.TBL_TABLE,null,contentValues);
        if(check != 0){
            return true;
        }else {
            return false;
        }
    }

    //Hàm xóa bàn ăn theo mã
    public boolean XoaBanTheoMa(int tableid){
        long check =database.delete(CreateDatabase.TBL_TABLE,CreateDatabase.TBL_TABLE_TABLEID+" = "+tableid,null);
        if(check != 0){
            return true;
        }else {
            return false;
        }
    }

    //Sửa tên bàn
    public boolean CapNhatTenBan(int tableid, String tablename){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_TABLE_TABLENAME,tablename);

        long check = database.update(CreateDatabase.TBL_TABLE,contentValues,CreateDatabase.TBL_TABLE_TABLEID+ " = '"+tableid+"' ",null);
        if(check!= 0){
            return true;
        }else {
            return false;
        }
    }

    //Hàm lấy ds các bàn ăn đổ vào gridview
    public List<DinnertableDTO> LayTatCaBanAn(){
        List<DinnertableDTO> dinnertableDTOList = new ArrayList<DinnertableDTO>();
        String query = "SELECT * FROM "+CreateDatabase.TBL_TABLE;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            DinnertableDTO dinnertableDTO = new DinnertableDTO();
            dinnertableDTO.setTableid(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_TABLE_TABLEID)));
            dinnertableDTO.setTablename(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_TABLE_TABLENAME)));

            dinnertableDTOList.add(dinnertableDTO);
            cursor.moveToNext();
        }
        return dinnertableDTOList;
    }

    public String LayTinhTrangBanTheoMa(int tableid){
        String status="";
        String query = "SELECT * FROM "+CreateDatabase.TBL_TABLE + " WHERE " +CreateDatabase.TBL_TABLE_TABLEID+ " = '" +tableid+ "' ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            status = cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_FOOD_STATUS));
            cursor.moveToNext();
        }

        return status;
    }

    public boolean CapNhatTinhTrangBan(int tableid, String status){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_TABLE_STATUS,status);

        long check = database.update(CreateDatabase.TBL_TABLE,contentValues,CreateDatabase.TBL_TABLE_TABLEID+ " = '"+tableid+"' ",null);
        if(check != 0){
            return true;
        }else {
            return false;
        }
    }

    public String LayTenBanTheoMa(int tableid){
        String tablename ="";
        String query = "SELECT * FROM "+CreateDatabase.TBL_TABLE + " WHERE " +CreateDatabase.TBL_TABLE_TABLEID+ " = '" +tableid+ "' ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tablename = cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_TABLE_TABLENAME));
            cursor.moveToNext();
        }

        return tablename;
    }
}
