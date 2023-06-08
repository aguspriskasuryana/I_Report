package com.example.guswik.ireport.master;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.guswik.ireport.database.DataHelper;

import java.util.Vector;

/**
 * Created by GUSWIK on 7/20/2022.
 */
public class ProductTypeDAO {


    protected Cursor cursor;

    public static final String TBL_PRODUCT_TYPE = "producttype";
    public static final String FLD_PRODUCT_TYPE_ID = "PRODUCT_TYPE_ID";
    public static final String FLD_PRODUCT_TYPE_NAMA = "PRODUCT_TYPE_NAMA";

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;

    public static ProductTypeEntity getById(DataHelper dbHelper , long oid) {

        ProductTypeEntity productTypeEntity = new ProductTypeEntity();
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM " + TBL_PRODUCT_TYPE + " WHERE " + FLD_PRODUCT_TYPE_ID + " = " + oid;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            resultToObject(cursor,productTypeEntity);

            return productTypeEntity;
        } catch (Exception e) {
            System.out.println(e);
        }
        return productTypeEntity;
    }


    public static int add(DataHelper dbHelper ,ProductTypeEntity productTypeEntity){
        int success = RSLT_OK ;
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();
        try {
            String sql = "insert into "+TBL_PRODUCT_TYPE+" (" +
                    FLD_PRODUCT_TYPE_NAMA +" " +
                    " ) values ( " +
                    " '" + productTypeEntity.getNamaProductType() + "' " +
                    " )";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int update(DataHelper dbHelper ,ProductTypeEntity productTypeEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = " update "+TBL_PRODUCT_TYPE+" set " +
                    FLD_PRODUCT_TYPE_NAMA +" = '" + productTypeEntity.getNamaProductType() +"' "+
                    " where " + FLD_PRODUCT_TYPE_ID + " = '" + productTypeEntity.getIdProductType() + "' ";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int delete(DataHelper dbHelper ,ProductTypeEntity productTypeEntity){
        int success = RSLT_OK ;
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = "delete from " + TBL_PRODUCT_TYPE + " where " + FLD_PRODUCT_TYPE_ID +" = " + productTypeEntity.getIdProductType() + " ";

            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }


    public static Vector getList(DataHelper dbHelper ,int limitStart, int recordToGet, String whereClause, String order) {

        Vector lists = new Vector();
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();
            SQLiteDatabase dbw = dbHelper.getWritableDatabase();

            String sql = "SELECT * FROM " + TBL_PRODUCT_TYPE;

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }


            cursor = db.rawQuery(sql,null);

            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                ProductTypeEntity productTypeEntity = new ProductTypeEntity();
                resultToObject(cursor,productTypeEntity);
                lists.add(productTypeEntity);
            }

            return lists;
        } catch (Exception e) {
            System.out.println(e);
        }
        return lists;
    }

    public static String[]  getListArray(DataHelper dbHelper,  int limitStart, int recordToGet, String whereClause, String order) {

        String[] daftar;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String sql = "SELECT * FROM " + TBL_PRODUCT_TYPE;

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            cursor = db.rawQuery(sql,null);
            daftar = new String[cursor.getCount()];

            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar[cc] = "" + cursor.getString(cursor.getColumnIndex(FLD_PRODUCT_TYPE_NAMA)).toString() ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new String[0];
    }


    public static String getNameById(DataHelper dbHelper, long id) {

        String daftar ="";
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String sql = "SELECT * FROM " + TBL_PRODUCT_TYPE;
            sql = sql + " WHERE " + FLD_PRODUCT_TYPE_ID + " = " + id;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar = "" + cursor.getString(cursor.getColumnIndex(FLD_PRODUCT_TYPE_NAMA)).toString() ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }


    public static long getIdByName(DataHelper dbHelper, String name) {

        long daftar =0;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String sql = "SELECT * FROM " + TBL_PRODUCT_TYPE;
            sql = sql + " WHERE " + FLD_PRODUCT_TYPE_NAMA + " = '" + name + "'";

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar = cursor.getLong(cursor.getColumnIndex(FLD_PRODUCT_TYPE_ID)) ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return daftar;
    }





    public static void resultToObject(Cursor cursor, ProductTypeEntity productTypeEntity) {

        try {
            productTypeEntity.setIdProductType(cursor.getLong(cursor.getColumnIndex(FLD_PRODUCT_TYPE_ID)));
            productTypeEntity.setNamaProductType(cursor.getString(cursor.getColumnIndex(FLD_PRODUCT_TYPE_NAMA)));

        } catch (Exception e) {
        }

    }


}