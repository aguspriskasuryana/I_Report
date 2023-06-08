package com.example.guswik.ireport.master;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.guswik.ireport.database.DataHelper;

import java.util.Vector;

/**
 * Created by GUSWIK on 7/20/2022.
 */
public class BmcDAO {


    protected Cursor cursor;

    public static final String TBL_BMC = "bmc";
    public static final String FLD_BMC_ID = "BMC_ID";
    public static final String FLD_KATEGORI = "KATEGORI";
    public static final String FLD_KETERANGAN = "KETERANGAN";

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;

    public static BmcEntity getById(DataHelper dbHelper , long oid) {

        BmcEntity bmcEntity = new BmcEntity();
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM " + TBL_BMC + " WHERE " + FLD_BMC_ID + " = " + oid;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            resultToObject(cursor,bmcEntity);

            return bmcEntity;
        } catch (Exception e) {
            System.out.println(e);
        }
        return bmcEntity;
    }


    public static int add(DataHelper dbHelper ,BmcEntity bmcEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();
        try {
            String sql = "insert into "+TBL_BMC+" (" +
                    FLD_KATEGORI +", " +
                    FLD_KETERANGAN +" " +
                    " ) values ( " +
                    " '" + bmcEntity.getKategoriBMC() + "', " +
                    " '" + bmcEntity.getKeterangan() + "' " +
                    " )";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int update(DataHelper dbHelper ,BmcEntity bmcEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = " update "+TBL_BMC+" set " +
                    FLD_KATEGORI +" = '" + bmcEntity.getKategoriBMC() + "', "+
                    FLD_KETERANGAN +" = '" + bmcEntity.getKeterangan() +"' "+
                    " where " + FLD_BMC_ID + " = '" + bmcEntity.getIdBMC() + "' ";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int delete(DataHelper dbHelper ,BmcEntity bmcEntity){
        int success = RSLT_OK ;
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = "delete from " + TBL_BMC + " where " + FLD_BMC_ID +" = " + bmcEntity.getIdBMC() + " ";

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

            String sql = "SELECT * FROM " + TBL_BMC;

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
                BmcEntity bmcEntity = new BmcEntity();
                resultToObject(cursor,bmcEntity);
                lists.add(bmcEntity);
            }

            return lists;
        } catch (Exception e) {
            System.out.println(e);
        }
        return lists;
    }

    public static String[]  getListArrayOnlyKeterangan(DataHelper dbHelper,  int limitStart, int recordToGet, String whereClause, String order) {

        String[] daftar;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();
            SQLiteDatabase dbw = dbHelper.getWritableDatabase();

            String sql = "SELECT * FROM " + TBL_BMC;

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
                daftar[cc] =  cursor.getString(cursor.getColumnIndex(FLD_KETERANGAN)).toString()  ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new String[0];
    }


    public static String[]  getListArrayOnlyId(DataHelper dbHelper,  int limitStart, int recordToGet, String whereClause, String order) {

        String[] daftar;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();
            SQLiteDatabase dbw = dbHelper.getWritableDatabase();

            String sql = "SELECT * FROM " + TBL_BMC;

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
                daftar[cc] =  cursor.getString(cursor.getColumnIndex(FLD_BMC_ID)).toString()  ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new String[0];
    }

    public static String[]  getListArray(DataHelper dbHelper,  int limitStart, int recordToGet, String whereClause, String order) {

        String[] daftar;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();
            SQLiteDatabase dbw = dbHelper.getWritableDatabase();

            String sql = "SELECT * FROM " + TBL_BMC;

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
                daftar[cc] = " NO : " + cursor.getString(cursor.getColumnIndex(FLD_KETERANGAN)).toString()   ;
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

            String sql = "SELECT * FROM " + TBL_BMC;
            sql = sql + " WHERE " + FLD_BMC_ID + " = " + id;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar = "" + cursor.getString(cursor.getColumnIndex(FLD_KETERANGAN)).toString() ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }

    public static void resultToObject(Cursor cursor, BmcEntity bmcEntity) {

        try {
            bmcEntity.setIdBMC(cursor.getLong(cursor.getColumnIndex(FLD_BMC_ID)));
            bmcEntity.setKategoriBMC(cursor.getInt(cursor.getColumnIndex(FLD_KATEGORI)));
            bmcEntity.setKeterangan(cursor.getString(cursor.getColumnIndex(FLD_KETERANGAN)));

        } catch (Exception e) {
        }

    }

}