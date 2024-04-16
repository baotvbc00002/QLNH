package com.sinhvien.finalproject.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDatabase extends SQLiteOpenHelper {

    public static String TBL_STAFF = "STAFF";
    public static String TBL_FOOD = "FOOD";
    public static String TBL_TYPEFOOD = "TYPEFOOD";
    public static String TBL_TABLE = "TABLES";
    public static String TBL_ORDER = "ORDERS";
    public static String TBL_ORDERDETAILS = "ORDERDETAILS";
    public static String TBL_POSITION= "POSITION";

    //Employee table
    public static String TBL_STAFF_STAFFID = "STAFFID";
    public static String TBL_STAFF_FULLNAME = "FULLNAME";
    public static String TBL_STAFF_USERNAME = "USERNAME";
    public static String TBL_STAFF_PASSWORD = "PASSWORD";
    public static String TBL_STAFF_EMAIL = "EMAIL";
    public static String TBL_STAFF_PHONE = "PHONE";
    public static String TBL_STAFF_SEX = "SEX";
    public static String TBL_STAFF_DATEOFBIRTH = "DATEOFBIRTH";
    public static String TBL_STAFF_POSITIONID = "POSITIONID";

    //position table
    public static String TBL_POSITION_POSITIONID = "POSITIONID";
    public static String TBL_POSITION_POSITIONNAME = "POSITIONNAME";

    //Food table
    public static String TBL_FOOD_FOODID = "FOODID";
    public static String TBL_FOOD_FOODNAME = "FOODNAME";
    public static String TBL_FOOD_PRICE = "PRICE";
    public static String TBL_FOOD_STATUS = "STATUS";
    public static String TBL_FOOD_IMAGE = "IMAGE";
    public static String TBL_FOOD_TYPEID = "TYPEID";

    //Typefood table
    public static String TBL_TYPEFOOD_TYPEID = "TYPEID";
    public static String TBL_TYPEFOOD_TPYENAME = "TPYENAME";
    public static String TBL_TYPEFOOD_IMAGE = "IMAGE";

    //table
    public static String TBL_TABLE_TABLEID = "TABLEID";
    public static String TBL_TABLE_TABLENAME = "TABLENAME";
    public static String TBL_TABLE_STATUS = "STATUS";

    //order table
    public static String TBL_ORDER_ORDERID = "ORDERID";
    public static String TBL_ORDER_STAFFID = "STAFFID";
    public static String TBL_ORDER_ORDERDATE = "ORDERDATE";
    public static String TBL_ORDER_STATUS = "STATUS";
    public static String TBL_ORDER_TOTAL = "TOTAL";
    public static String TBL_ORDER_TABLEID = "TABLEID";

    //orderdetail table
    public static String TBL_ORDERDETAILS_ORDERID = "ORDERID";
    public static String TBL_ORDERDETAILS_FOODID = "FOODID";
    public static String TBL_ORDERDETAILS_QUANTITY = "QUANTITY";


    public CreateDatabase(Context context) {
        super(context, "OrderDrink.db", null, 1);
    }

    //Perform table creation
    @Override
    public void onCreate(SQLiteDatabase db) {
        String tblSTAFF = "CREATE TABLE " +TBL_STAFF+ " ( " +TBL_STAFF_STAFFID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TBL_STAFF_FULLNAME+ " TEXT, " +TBL_STAFF_USERNAME+ " TEXT, " +TBL_STAFF_PASSWORD+ " TEXT, " +TBL_STAFF_EMAIL+ " TEXT, "
                +TBL_STAFF_PHONE+ " TEXT, " +TBL_STAFF_SEX+ " TEXT, " +TBL_STAFF_DATEOFBIRTH+ " TEXT , "+TBL_STAFF_POSITIONID+" INTEGER)";

        String tblPOSITION = "CREATE TABLE " +TBL_POSITION+ " ( " +TBL_POSITION_POSITIONID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TBL_POSITION_POSITIONNAME+ " TEXT)" ;

        String tblTABLE = "CREATE TABLE " + TBL_TABLE + " ( " + TBL_TABLE_TABLEID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TBL_TABLE_TABLENAME + " TEXT, " + TBL_FOOD_STATUS + " TEXT )";

        String tblFOOD = "CREATE TABLE " +TBL_FOOD+ " ( " +TBL_FOOD_FOODID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TBL_FOOD_FOODNAME+ " TEXT, " +TBL_FOOD_PRICE+ " TEXT, " +TBL_FOOD_STATUS+ " TEXT, "
                +TBL_FOOD_IMAGE+ " BLOB, "+TBL_FOOD_TYPEID+ " INTEGER )";

        String tblTYPEFOOD = "CREATE TABLE " +TBL_TYPEFOOD+ " ( " +TBL_TYPEFOOD_TYPEID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TBL_TYPEFOOD_IMAGE+ " BLOB, " +TBL_TYPEFOOD_TPYENAME+ " TEXT)" ;

        String tblORDER = "CREATE TABLE " + TBL_ORDER + " ( " + TBL_ORDER_ORDERID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TBL_ORDER_TABLEID + " INTEGER, " + TBL_ORDER_STAFFID + " INTEGER, " + TBL_ORDER_ORDERDATE + " TEXT, " + TBL_ORDER_TOTAL + " TEXT," + TBL_ORDER_STATUS + " TEXT )";

        String tblORDERDETAILS = "CREATE TABLE " +TBL_ORDERDETAILS+ " ( " +TBL_ORDERDETAILS_ORDERID+ " INTEGER, "
                +TBL_ORDERDETAILS_FOODID+ " INTEGER, " +TBL_ORDERDETAILS_QUANTITY+ " INTEGER, "
                + " PRIMARY KEY ( " +TBL_ORDERDETAILS_ORDERID+ "," +TBL_ORDERDETAILS_FOODID+ "))";

        db.execSQL(tblSTAFF);
        db.execSQL(tblPOSITION);
        db.execSQL(tblTABLE);
        db.execSQL(tblFOOD);
        db.execSQL(tblTYPEFOOD );
        db.execSQL(tblORDER);
        db.execSQL(tblORDERDETAILS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //mở kết nối csdl
    public SQLiteDatabase open(){
        return this.getWritableDatabase();
    }
    public boolean checkTableExists(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?",
                new String[]{"table", tableName});
        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            return count > 0;
        }
        return false;
    }
}
