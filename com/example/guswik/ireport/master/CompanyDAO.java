package com.example.guswik.ireport.master;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.guswik.ireport.database.DataHelper;

import java.util.Vector;

/**
 * Created by GUSWIK on 7/20/2017.
 */
public class CompanyDAO {


    protected Cursor cursor;

    public static final String TBL_COMPANY = "company";
    public static final String FLD_COMPANY_ID = "COMPANY_ID";
    public static final String FLD_COMPANY_NAMA = "COMPANY_NAMA";
    public static final String FLD_COMPANY_TELP = "COMPANY_TELP";
    public static final String FLD_COMPANY_ADDRESS = "COMPANY_ADDRESS";
    public static final String FLD_COMPANY_EMAIL = "COMPANY_EMAIL";
    public static final String FLD_COMPANY_DIREKTUR = "COMPANY_DIREKTUR";
    public static final String FLD_COMPANY_SEKRETARIS = "COMPANY_SEKRETARIS";
    public static final String FLD_COMPANY_BENDAHARA = "COMPANY_BENDAHARA";

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;

    public static CompanyEntity getById(DataHelper dbHelper , long oid) {

        CompanyEntity companyEntity = new CompanyEntity();
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM " + TBL_COMPANY + " WHERE " + FLD_COMPANY_ID + " = " + oid;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            resultToObject(cursor,companyEntity);

            return companyEntity;
        } catch (Exception e) {
            System.out.println(e);
        }
        return companyEntity;
    }


    public static int add(DataHelper dbHelper ,CompanyEntity companyEntity){
        int success = RSLT_OK ;
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();
        try {
            String sql = "insert into "+TBL_COMPANY+" (" +
                    FLD_COMPANY_NAMA +", " +
                    FLD_COMPANY_ADDRESS +", " +
                    FLD_COMPANY_EMAIL +", " +
                    FLD_COMPANY_TELP +", " +
                    FLD_COMPANY_DIREKTUR +", " +
                    FLD_COMPANY_SEKRETARIS +", " +
                    FLD_COMPANY_BENDAHARA +" " +
                    " ) values ( " +
                    " '" + companyEntity.getCompanyName() + "', " +
                    " '" + companyEntity.getAlamat() + "', " +
                    " '" + companyEntity.getEmail() + "', " +
                    " '" + companyEntity.getTelp() + "', " +
                    " '" + companyEntity.getDirektur() + "', " +
                    " '" + companyEntity.getSekretaris() + "', " +
                    " '" + companyEntity.getBendahara() + "', " +
                    " )";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int update(DataHelper dbHelper ,CompanyEntity companyEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = " update "+TBL_COMPANY+" set " +
                    FLD_COMPANY_NAMA +" = '" + companyEntity.getCompanyName() +"', "+
                    FLD_COMPANY_ADDRESS +" = '" + companyEntity.getAlamat() +"', "+
                    FLD_COMPANY_TELP +" = '" + companyEntity.getTelp() +"', "+
                    FLD_COMPANY_DIREKTUR +" = '" + companyEntity.getDirektur() +"', "+
                    FLD_COMPANY_SEKRETARIS +" = '" + companyEntity.getSekretaris() +"', "+
                    FLD_COMPANY_BENDAHARA +" = '" + companyEntity.getBendahara() +"' "+
                    " where " + FLD_COMPANY_ID + " = '" + companyEntity.getCompanyId() + "' ";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int delete(DataHelper dbHelper ,CompanyEntity companyEntity){
        int success = RSLT_OK ;
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = "delete from " + TBL_COMPANY + " where " + FLD_COMPANY_ID +" = " + companyEntity.getCompanyId() + " ";

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

            String sql = "SELECT * FROM " + TBL_COMPANY;

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
                CompanyEntity companyEntity = new CompanyEntity();
                resultToObject(cursor,companyEntity);
                lists.add(companyEntity);
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

            String sql = "SELECT * FROM " + TBL_COMPANY;

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
                daftar[cc] = "" + cursor.getString(cursor.getColumnIndex(FLD_COMPANY_NAMA)).toString() ;
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

            String sql = "SELECT * FROM " + TBL_COMPANY;
            sql = sql + " WHERE " + FLD_COMPANY_ID + " = " + id;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar = "" + cursor.getString(cursor.getColumnIndex(FLD_COMPANY_NAMA)).toString() ;
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

            String sql = "SELECT * FROM " + TBL_COMPANY;
            sql = sql + " WHERE " + FLD_COMPANY_NAMA + " = '" + name + "'";

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar = cursor.getLong(cursor.getColumnIndex(FLD_COMPANY_ID)) ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return daftar;
    }





    public static void resultToObject(Cursor cursor, CompanyEntity companyEntity) {

        try {
            companyEntity.setCompanyId(cursor.getLong(cursor.getColumnIndex(FLD_COMPANY_ID)));
            companyEntity.setCompanyName(cursor.getString(cursor.getColumnIndex(FLD_COMPANY_NAMA)));
            companyEntity.setTelp(cursor.getLong(cursor.getColumnIndex(FLD_COMPANY_TELP)));
            companyEntity.setAlamat(cursor.getString(cursor.getColumnIndex(FLD_COMPANY_ADDRESS)));
            companyEntity.setEmail(cursor.getString(cursor.getColumnIndex(FLD_COMPANY_EMAIL)));
            companyEntity.setDirektur(cursor.getString(cursor.getColumnIndex(FLD_COMPANY_DIREKTUR)));
            companyEntity.setSekretaris(cursor.getString(cursor.getColumnIndex(FLD_COMPANY_SEKRETARIS)));
            companyEntity.setBendahara(cursor.getString(cursor.getColumnIndex(FLD_COMPANY_BENDAHARA)));

        } catch (Exception e) {
        }

    }


}