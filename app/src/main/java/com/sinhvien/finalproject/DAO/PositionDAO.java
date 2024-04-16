package com.sinhvien.finalproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sinhvien.finalproject.Database.CreateDatabase;

public class PositionDAO {

    SQLiteDatabase database;
    public PositionDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public void ThemQuyen(String positionname){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_POSITION_POSITIONNAME,positionname);
        database.insert(CreateDatabase.TBL_POSITION,null,contentValues);
    }
    

    public String LayTenQuyenTheoMa(int positionid){
        String positionname ="";
        String query = "SELECT * FROM "+CreateDatabase.TBL_POSITION+" WHERE "+CreateDatabase.TBL_POSITION_POSITIONID+" = "+positionid;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            positionname = cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_POSITION_POSITIONNAME));
            cursor.moveToNext();
        }
        return positionname;
//        if(positionid==1)
//            return "Manager";
//        else
//            return "Staff";
    }
}
