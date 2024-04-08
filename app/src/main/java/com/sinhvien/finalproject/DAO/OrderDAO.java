package com.sinhvien.finalproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sinhvien.finalproject.DTO.OrderDTO;
import com.sinhvien.finalproject.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    SQLiteDatabase database;
    public OrderDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long ThemDonDat(OrderDTO orderDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_ORDER_TABLEID,orderDTO.getTableid());
        contentValues.put(CreateDatabase.TBL_ORDER_STAFFID,orderDTO.getStaffid());
        contentValues.put(CreateDatabase.TBL_ORDER_ORDERDATE,orderDTO.getOrderdate());
        contentValues.put(CreateDatabase.TBL_ORDER_STATUS,orderDTO.getStatus());
        contentValues.put(CreateDatabase.TBL_ORDER_TOTAL,orderDTO.getTotal());

        long orderid = database.insert(CreateDatabase.TBL_ORDER,null,contentValues);

        return orderid;
    }

    public List<OrderDTO> LayDSDonDat(){
        List<OrderDTO> orderDTOS = new ArrayList<OrderDTO>();
        String query = "SELECT * FROM "+CreateDatabase.TBL_ORDER;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderid(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_ORDER_ORDERID)));
            orderDTO.setTableid(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_ORDER_TABLEID)));
            orderDTO.setTotal(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_ORDER_TOTAL)));
            orderDTO.setStatus(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_ORDER_STATUS)));
            orderDTO.setOrderdate(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_ORDER_ORDERDATE)));
            orderDTO.setStaffid(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_ORDER_STAFFID)));
            orderDTOS.add(orderDTO);

            cursor.moveToNext();
        }
        return orderDTOS;
    }

    public List<OrderDTO> LayDSDonDatNgay(String date){
        List<OrderDTO> orderDTOS = new ArrayList<OrderDTO>();
        String query = "SELECT * FROM "+CreateDatabase.TBL_ORDER+" WHERE "+CreateDatabase.TBL_ORDER_ORDERDATE+" like '"+date+"'";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderid(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_ORDER_ORDERID)));
            orderDTO.setTableid(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_ORDER_TABLEID)));
            orderDTO.setTotal(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_ORDER_TOTAL)));
            orderDTO.setStatus(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_ORDER_STATUS)));
            orderDTO.setOrderdate(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_ORDER_ORDERDATE)));
            orderDTO.setStaffid(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_ORDER_STAFFID)));
            orderDTOS.add(orderDTO);

            cursor.moveToNext();
        }
        return orderDTOS;
    }

    public long LayMaDonTheoMaBan(int tableid, String status){
        String query = "SELECT * FROM " +CreateDatabase.TBL_ORDER+ " WHERE " +CreateDatabase.TBL_ORDER_TABLEID+ " = '" +tableid+ "' AND "
                +CreateDatabase.TBL_ORDER_STATUS+ " = '" +status+ "' ";
        long orderingid = 0;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            orderingid = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_ORDER_ORDERID));

            cursor.moveToNext();
        }
        return orderingid;
    }

    public boolean  UpdateTongTienDonDat(int orderid,String total){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_ORDER_TOTAL,total);
        long check  = database.update(CreateDatabase.TBL_ORDER,contentValues,
                CreateDatabase.TBL_ORDER_ORDERID+" = "+orderid,null);
        if(check != 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean UpdateTThaiDonTheoMaBan(int tableid,String status){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_ORDER_STATUS,status);
        long check = database.update(CreateDatabase.TBL_ORDER,contentValues,CreateDatabase.TBL_ORDER_TABLEID+
                " = '"+tableid+"'",null);
        if(check !=0){
            return true;
        }else {
            return false;
        }
    }

}
