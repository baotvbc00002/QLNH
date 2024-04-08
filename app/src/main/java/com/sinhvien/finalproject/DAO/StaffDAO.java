package com.sinhvien.finalproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sinhvien.finalproject.DTO.StaffDTO;
import com.sinhvien.finalproject.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class StaffDAO {

    SQLiteDatabase database;
    public StaffDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long ThemNhanVien(StaffDTO staffDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_STAFF_FULLNAME,staffDTO.getFULLNAME());
        contentValues.put(CreateDatabase.TBL_STAFF_USERNAME,staffDTO.getUSERNAME());
        contentValues.put(CreateDatabase.TBL_STAFF_PASSWORD,staffDTO.getPASSWORD());
        contentValues.put(CreateDatabase.TBL_STAFF_EMAIL,staffDTO.getEMAIL());
        contentValues.put(CreateDatabase.TBL_STAFF_PHONE,staffDTO.getPHONE());
        contentValues.put(CreateDatabase.TBL_STAFF_SEX,staffDTO.getSEX());
        contentValues.put(CreateDatabase.TBL_STAFF_DATEOFBIRTH,staffDTO.getDATEOFBIRTH());
        contentValues.put(CreateDatabase.TBL_STAFF_POSITIONID,staffDTO.getPOSITIONID());

        long check = database.insert(CreateDatabase.TBL_STAFF,null,contentValues);
        return check;
    }

    public long SuaNhanVien(StaffDTO staffDTO, int staffid){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_STAFF_FULLNAME,staffDTO.getFULLNAME());
        contentValues.put(CreateDatabase.TBL_STAFF_USERNAME,staffDTO.getUSERNAME());
        contentValues.put(CreateDatabase.TBL_STAFF_PASSWORD,staffDTO.getPASSWORD());
        contentValues.put(CreateDatabase.TBL_STAFF_EMAIL,staffDTO.getEMAIL());
        contentValues.put(CreateDatabase.TBL_STAFF_PHONE,staffDTO.getPHONE());
        contentValues.put(CreateDatabase.TBL_STAFF_SEX,staffDTO.getSEX());
        contentValues.put(CreateDatabase.TBL_STAFF_DATEOFBIRTH,staffDTO.getDATEOFBIRTH());
        contentValues.put(CreateDatabase.TBL_STAFF_POSITIONID,staffDTO.getPOSITIONID());

        long check = database.update(CreateDatabase.TBL_STAFF,contentValues,
                CreateDatabase.TBL_STAFF_STAFFID+" = "+staffid,null);
        return check;
    }

    public long CapNhapMatKhau(StaffDTO staffDTO, int staffid){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_STAFF_PASSWORD,staffDTO.getPASSWORD());

        long check = database.update(CreateDatabase.TBL_STAFF,contentValues,
                CreateDatabase.TBL_STAFF_STAFFID+" = "+staffid,null);
        return check;
    }


    public int KiemTraDN(String username, String password){
        String query = "SELECT * FROM " +CreateDatabase.TBL_STAFF+ " WHERE "
                +CreateDatabase.TBL_STAFF_USERNAME +" = '"+ username+"' AND "+CreateDatabase.TBL_STAFF_PASSWORD +" = '" +password +"'";
        int staffid = 0;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            staffid = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_STAFF_STAFFID)) ;
            cursor.moveToNext();
        }
        return staffid;
    }

    public int KiemTraUser(String username, String password){
        String query = "SELECT * FROM " +CreateDatabase.TBL_STAFF+ " WHERE "
                +CreateDatabase.TBL_STAFF_USERNAME +" = '"+ username+"' AND "+CreateDatabase.TBL_STAFF_PASSWORD +" != '" +password  +"'";
        int staffid = 0;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            staffid = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_STAFF_STAFFID)) ;
            cursor.moveToNext();
        }
        return staffid;
    }







    public boolean KtraTonTaiNV(){
        String query = "SELECT * FROM "+CreateDatabase.TBL_STAFF;
        Cursor cursor =database.rawQuery(query,null);
        if(cursor.getCount() != 0){
            return true;
        }else {
            return false;
        }
    }

    public List<StaffDTO> LayDSNV(){
        List<StaffDTO> staffDTOS = new ArrayList<StaffDTO>();
        String query = "SELECT * FROM "+CreateDatabase.TBL_STAFF;

        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            StaffDTO staffDTO = new StaffDTO();
            staffDTO.setFULLNAME(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_STAFF_FULLNAME)));
            staffDTO.setEMAIL(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_STAFF_EMAIL)));
            staffDTO.setSEX(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_STAFF_SEX)));
            staffDTO.setDATEOFBIRTH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_STAFF_DATEOFBIRTH)));
            staffDTO.setPHONE(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_STAFF_PHONE)));
            staffDTO.setUSERNAME(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_STAFF_USERNAME)));
            staffDTO.setPASSWORD(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_STAFF_PASSWORD)));
            staffDTO.setSTAFFID(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_STAFF_STAFFID)));
            staffDTO.setPOSITIONID(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_STAFF_POSITIONID)));

            staffDTOS.add(staffDTO);
            cursor.moveToNext();
        }
        return staffDTOS;
    }

    public boolean XoaNV(int staffid){
        long ktra = database.delete(CreateDatabase.TBL_STAFF,CreateDatabase.TBL_STAFF_STAFFID+ " = " +staffid
                ,null);
        if(ktra !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public StaffDTO LayNVTheoMa(int staffid){
        StaffDTO staffDTO = new StaffDTO();
        String query = "SELECT * FROM "+CreateDatabase.TBL_STAFF+" WHERE "+CreateDatabase.TBL_STAFF_STAFFID+" = "+staffid;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            staffDTO.setFULLNAME(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_STAFF_FULLNAME)));
            staffDTO.setEMAIL(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_STAFF_EMAIL)));
            staffDTO.setSEX(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_STAFF_SEX)));
            staffDTO.setDATEOFBIRTH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_STAFF_DATEOFBIRTH)));
            staffDTO.setPHONE(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_STAFF_PHONE)));
            staffDTO.setUSERNAME(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_STAFF_USERNAME)));
            staffDTO.setPASSWORD(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_STAFF_PASSWORD)));
            staffDTO.setSTAFFID(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_STAFF_STAFFID)));
            staffDTO.setPOSITIONID(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_STAFF_POSITIONID)));

            cursor.moveToNext();
        }
        return staffDTO;
    }

    public int LayQuyenNV(int staffid){
        int positionid = 0;
        String query = "SELECT * FROM "+CreateDatabase.TBL_STAFF+" WHERE "+CreateDatabase.TBL_STAFF_STAFFID+" = "+staffid;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            positionid = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_STAFF_POSITIONID));

            cursor.moveToNext();
        }
        return positionid;
    }


}
