package com.example.guswik.ireport.master;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.guswik.ireport.Formater;
import com.example.guswik.ireport.database.DataHelper;

import java.util.Vector;

/**
 * Created by GUSWIK on 7/20/2017.
 */
public class  AkunDAO {


    protected Cursor cursor;

    public static final String TBL_AKUN = "akun";
    public static final String FLD_AKUN_ID = "AKUN_ID";
    public static final String FLD_AKUN_NO = "AKUN_NO";
    public static final String FLD_AKUN_NAMA = "AKUN_NAMA";
    public static final String FLD_AKUN_TYPE_ID = "AKUN_TYPE_ID";

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;

    public static AkunEntity getById(DataHelper dbHelper , long oid) {

        AkunEntity akunEntity = new AkunEntity();
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM " + TBL_AKUN + " WHERE " + FLD_AKUN_ID + " = " + oid;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            resultToObject(cursor,akunEntity);

            return akunEntity;
        } catch (Exception e) {
            System.out.println(e);
        }
        return akunEntity;
    }


    public static int add(DataHelper dbHelper ,AkunEntity akunEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();
        try {
            String sql = "insert into "+TBL_AKUN+" (" +
                    FLD_AKUN_NO +", " +
                    FLD_AKUN_NAMA +", " +
                    FLD_AKUN_TYPE_ID +" " +
                    " ) values ( " +
                    " '" + akunEntity.getNoAkun() + "', " +
                    " '" + akunEntity.getNamaAkun() + "', " +
                    " '" + akunEntity.getIdAkunType() + "' " +
                    " )";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int update(DataHelper dbHelper ,AkunEntity akunEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = " update "+TBL_AKUN+" set " +
                    FLD_AKUN_NO +" = '" + akunEntity.getNoAkun() + "', "+
                    FLD_AKUN_NAMA +" = '" + akunEntity.getNamaAkun() +"', "+
                    FLD_AKUN_TYPE_ID +" = '" + akunEntity.getIdAkunType() +"' "+
                    " where " + FLD_AKUN_ID + " = '" + akunEntity.getIdAkun() + "' ";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int delete(DataHelper dbHelper ,AkunEntity akunEntity){
        int success = RSLT_OK ;
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = "delete from " + TBL_AKUN + " where " + FLD_AKUN_ID +" = " + akunEntity.getIdAkun() + " ";

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

            String sql = "SELECT * FROM " + TBL_AKUN;

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
                AkunEntity akunEntity = new AkunEntity();
                resultToObject(cursor,akunEntity);
                lists.add(akunEntity);
            }

            return lists;
        } catch (Exception e) {
            System.out.println(e);
        }
        return lists;
    }

    public static String[]  getListArrayOnlyName(DataHelper dbHelper,  int limitStart, int recordToGet, String whereClause, String order) {

        String[] daftar;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();
            SQLiteDatabase dbw = dbHelper.getWritableDatabase();

            String sql = "SELECT * FROM " + TBL_AKUN;

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
                daftar[cc] =  cursor.getString(cursor.getColumnIndex(FLD_AKUN_NAMA)).toString()  ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new String[0];
    }

    public static String[]  getListArrayOnlyNameWithNull(DataHelper dbHelper,  int limitStart, int recordToGet, String whereClause, String order) {

        String[] daftar;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();
            SQLiteDatabase dbw = dbHelper.getWritableDatabase();

            String sql = "SELECT * FROM " + TBL_AKUN;

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
            daftar = new String[cursor.getCount()+1];

            daftar[0] =  "" ;
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar[(cc+1)] =  cursor.getString(cursor.getColumnIndex(FLD_AKUN_NAMA)).toString()  ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new String[0];
    }

    public static String[]  getListArrayOnlyNameWithSelect(DataHelper dbHelper,  int limitStart, int recordToGet, String whereClause, String order) {

        String[] daftar;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();
            SQLiteDatabase dbw = dbHelper.getWritableDatabase();

            String sql = "SELECT * FROM " + TBL_AKUN;

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
            daftar = new String[cursor.getCount()+1];

            daftar[0] =  "Select" ;
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar[(cc+1)] =  cursor.getString(cursor.getColumnIndex(FLD_AKUN_NAMA)).toString()  ;
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

            String sql = "SELECT * FROM " + TBL_AKUN;

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
                daftar[cc] =  cursor.getString(cursor.getColumnIndex(FLD_AKUN_ID)).toString()  ;
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

            String sql = "SELECT * FROM " + TBL_AKUN;

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
                daftar[cc] = " NO : " + cursor.getString(cursor.getColumnIndex(FLD_AKUN_NO)).toString() +" - "+ cursor.getString(cursor.getColumnIndex(FLD_AKUN_NAMA)).toString()  ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new String[0];
    }



    public static String[]  getListArrayAkunWithType(DataHelper dbHelper,  int limitStart, int recordToGet, String whereClause, String order) {

        String[] daftar;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();
            SQLiteDatabase dbw = dbHelper.getWritableDatabase();

            String sql = "SELECT * FROM " + TBL_AKUN +" INNER JOIN " + AkunTypeDAO.TBL_AKUN_TYPE + " ON "+AkunTypeDAO.TBL_AKUN_TYPE+"." +AkunTypeDAO.FLD_AKUN_TYPE_ID + " = " +TBL_AKUN+"."+AkunDAO.FLD_AKUN_TYPE_ID ;

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
                daftar[cc] = " NO : " + cursor.getString(cursor.getColumnIndex(FLD_AKUN_NO)).toString() +" - "+ cursor.getString(cursor.getColumnIndex(FLD_AKUN_NAMA)).toString()+" - "+ cursor.getString(cursor.getColumnIndex(AkunTypeDAO.FLD_AKUN_TYPE_NAMA)).toString()  ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new String[0];
    }

    public static long getIdByName(DataHelper dbHelper, String nama) {

        long daftar =0;
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String sql = "SELECT * FROM " + TBL_AKUN;
            sql = sql + " WHERE " + FLD_AKUN_NAMA + " = '" + nama + "'";

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar = cursor.getLong(cursor.getColumnIndex(FLD_AKUN_ID)) ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return daftar;
    }

    public static String getNameById(DataHelper dbHelper, long id) {

        String daftar ="";
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String sql = "SELECT * FROM " + TBL_AKUN;
            sql = sql + " WHERE " + FLD_AKUN_ID + " = " + id;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar = "" + cursor.getString(cursor.getColumnIndex(FLD_AKUN_NAMA)).toString() ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }

    public static long getAkunTypeById(DataHelper dbHelper, long id) {

        long daftar = 0 ;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String sql = "SELECT * FROM " + TBL_AKUN;
            sql = sql + " WHERE " + FLD_AKUN_ID + " = " + id;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar =  cursor.getLong(cursor.getColumnIndex(FLD_AKUN_TYPE_ID)) ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return daftar;
    }

    public static String getNameByIdForTransactionAdapter(DataHelper dbHelper, long id) {

        String daftar ="Select";
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String sql = "SELECT * FROM " + TBL_AKUN;
            sql = sql + " WHERE " + FLD_AKUN_ID + " = " + id;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar = "" + cursor.getString(cursor.getColumnIndex(FLD_AKUN_NAMA)).toString() ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }

    public static void resultToObject(Cursor cursor, AkunEntity akunEntity) {

        try {
            akunEntity.setIdAkun(cursor.getLong(cursor.getColumnIndex(FLD_AKUN_ID)));
            akunEntity.setNoAkun(cursor.getString(cursor.getColumnIndex(FLD_AKUN_NO)));
            akunEntity.setIdAkun(cursor.getLong(cursor.getColumnIndex(FLD_AKUN_ID)));
            akunEntity.setNamaAkun(cursor.getString(cursor.getColumnIndex(FLD_AKUN_NAMA)));
            akunEntity.setIdAkunType(cursor.getLong(cursor.getColumnIndex(FLD_AKUN_TYPE_ID)));

        } catch (Exception e) {
        }

    }


}