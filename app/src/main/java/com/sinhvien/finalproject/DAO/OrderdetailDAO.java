package com.sinhvien.finalproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sinhvien.finalproject.DTO.OrderdetailDTO;
import com.sinhvien.finalproject.Database.CreateDatabase;

public class OrderdetailDAO {

    SQLiteDatabase database;
    public OrderdetailDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean KiemTraMonTonTai(int orderid, int foodid){
        String query = "SELECT * FROM " +CreateDatabase.TBL_ORDERDETAILS+ " WHERE " +CreateDatabase.TBL_ORDERDETAILS_FOODID+
                " = " +foodid+ " AND " +CreateDatabase.TBL_ORDERDETAILS_ORDERID+ " = "+orderid;
        Cursor cursor = database.rawQuery(query,null);
        if(cursor.getCount() != 0){
            return true;
        }else {
            return false;
        }
    }

    public int LaySLMonTheoMaDon(int orderid, int foodid){
        int quantity = 0;
        String query = "SELECT * FROM " +CreateDatabase.TBL_ORDERDETAILS+ " WHERE " +CreateDatabase.TBL_ORDERDETAILS_FOODID+
                " = " +foodid+ " AND " +CreateDatabase.TBL_ORDERDETAILS_ORDERID+ " = "+orderid;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            quantity = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_ORDERDETAILS_QUANTITY));
            cursor.moveToNext();
        }
        return quantity;
    }

    public boolean CapNhatSL(OrderdetailDTO orderdetailDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_ORDERDETAILS_QUANTITY, orderdetailDTO.getQuantity());

        long check = database.update(CreateDatabase.TBL_ORDERDETAILS,contentValues,CreateDatabase.TBL_ORDERDETAILS_ORDERID+ " = "
                +orderdetailDTO.getOrderid()+ " AND " +CreateDatabase.TBL_ORDERDETAILS_FOODID+ " = "
                +orderdetailDTO.getFoodid(),null);
        if(check !=0){
            return true;
        }else {
            return false;
        }
    }

    public boolean ThemChiTietDonDat(OrderdetailDTO orderdetailDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_ORDERDETAILS_QUANTITY,orderdetailDTO.getQuantity());
        contentValues.put(CreateDatabase.TBL_ORDERDETAILS_ORDERID,orderdetailDTO.getOrderid());
        contentValues.put(CreateDatabase.TBL_ORDERDETAILS_FOODID,orderdetailDTO.getFoodid());

        long check  = database.insert(CreateDatabase.TBL_ORDERDETAILS,null,contentValues);
        if(check  !=0){
            return true;
        }else {
            return false;
        }
    }

}
