package com.example.guswik.ireport.master;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.guswik.ireport.database.DataHelper;

import java.util.Vector;

/**
 * Created by GUSWIK on 7/20/2022.
 */
public class AnalysisDAO {


    protected Cursor cursor;
    

    public static final String TBL_ANALYSIS = "analysis";
    public static final String FLD_ANALYSIS_ID = "ANALYSIS_ID";
    public static final String FLD_PRODUCT_ID = "PRODUCT_ID";
    public static final String FLD_ANALYSISBAHANBAKU_KETERSEDIAAN = "ANALYSISBAHANBAKU_KETERSEDIAAN";
    public static final String FLD_ANALYSISBAHANBAKU_KEMUDAHAN = "ANALYSISBAHANBAKU_KEMUDAHAN";
    public static final String FLD_ANALYSISBAHANBAKU_KEBERLANJUTAN = "ANALYSISBAHANBAKU_KEBERLANJUTAN";
    public static final String FLD_ANALYSISSUMBERDAYAMANUSIA_KETERSEDIAAN = "ANALYSISSUMBERDAYAMANUSIA_KETERSEDIAAN";
    public static final String FLD_ANALYSISSUMBERDAYAMANUSIA_KOMPETEN = "ANALYSISSUMBERDAYAMANUSIA_KOMPETEN";
    public static final String FLD_ANALYSISPEMASARAN_KETERSEDIAAN = "ANALYSISPEMASARAN_KETERSEDIAAN";
    public static final String FLD_ANALYSISPEMASARAN_TERJANGKAU = "ANALYSISPEMASARAN_TERJANGKAU";
    public static final String FLD_ANALYSISPEMASARAN_KEBUTUHAN = "ANALYSISPEMASARAN_KEBUTUHAN";

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;

    public static AnalysisEntity getById(DataHelper dbHelper , long oid) {

        AnalysisEntity analysisEntity = new AnalysisEntity();
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM " + TBL_ANALYSIS + " WHERE " + FLD_ANALYSIS_ID + " = " + oid;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            resultToObject(cursor,analysisEntity);

            return analysisEntity;
        } catch (Exception e) {
            System.out.println(e);
        }
        return analysisEntity;
    }

    public static AnalysisEntity getByProductId(DataHelper dbHelper , long oid) {

        AnalysisEntity analysisEntity = new AnalysisEntity();
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM " + TBL_ANALYSIS + " WHERE " + FLD_PRODUCT_ID + " = " + oid;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            resultToObject(cursor,analysisEntity);

            return analysisEntity;
        } catch (Exception e) {
            System.out.println(e);
        }
        return analysisEntity;
    }


    public static int add(DataHelper dbHelper ,AnalysisEntity analysisEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();
        try {
            String sql = "insert into "+TBL_ANALYSIS+" (" +
                    FLD_PRODUCT_ID +", " +
                    FLD_ANALYSISBAHANBAKU_KETERSEDIAAN +", " +
                    FLD_ANALYSISBAHANBAKU_KEMUDAHAN +", " +
                    FLD_ANALYSISBAHANBAKU_KEBERLANJUTAN +", " +
                    FLD_ANALYSISSUMBERDAYAMANUSIA_KETERSEDIAAN +", " +
                    FLD_ANALYSISSUMBERDAYAMANUSIA_KOMPETEN +", " +
                    FLD_ANALYSISPEMASARAN_KETERSEDIAAN +", " +
                    FLD_ANALYSISPEMASARAN_TERJANGKAU +", " +
                    FLD_ANALYSISPEMASARAN_KEBUTUHAN +" " +
                    " ) values ( " +
                    " '" + analysisEntity.getIdProduct() + "', " +
                    " '" + analysisEntity.getAnalysisbahanbaku_ketersediaan() + "', " +
                    " '" + analysisEntity.getAnalysisbahanbaku_kemudahan() + "', " +
                    " '" + analysisEntity.getAnalysisbahanbaku_keberlanjutan() + "', " +
                    " '" + analysisEntity.getAnalysissumberdayamanusia_ketersediaan() + "', " +
                    " '" + analysisEntity.getAnalysissumberdayamanusia_kompeten() + "', " +
                    " '" + analysisEntity.getAnalysispemasaran_ketersediaan() + "', " +
                    " '" + analysisEntity.getAnalysispemasaran_terjangkau() + "', " +
                    " '" + analysisEntity.getAnalysispemasaran_kebutuhan() + "' " +
                    " )";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int update(DataHelper dbHelper ,AnalysisEntity analysisEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = " update "+TBL_ANALYSIS+" set " +
                    FLD_PRODUCT_ID +" = '" + analysisEntity.getIdProduct()+ "', "+
                    FLD_ANALYSISBAHANBAKU_KETERSEDIAAN +" = '" + analysisEntity.getAnalysisbahanbaku_ketersediaan()+ "', "+
                    FLD_ANALYSISBAHANBAKU_KEMUDAHAN +" = '" + analysisEntity.getAnalysisbahanbaku_kemudahan()+ "', "+
                    FLD_ANALYSISBAHANBAKU_KEBERLANJUTAN +" = '" + analysisEntity.getAnalysisbahanbaku_keberlanjutan()+ "', "+
                    FLD_ANALYSISSUMBERDAYAMANUSIA_KETERSEDIAAN +" = '" + analysisEntity.getAnalysissumberdayamanusia_ketersediaan()+ "', "+
                    FLD_ANALYSISSUMBERDAYAMANUSIA_KOMPETEN +" = '" + analysisEntity.getAnalysissumberdayamanusia_kompeten()+ "', "+
                    FLD_ANALYSISPEMASARAN_KETERSEDIAAN +" = '" + analysisEntity.getAnalysispemasaran_ketersediaan()+ "', "+
                    FLD_ANALYSISPEMASARAN_TERJANGKAU +" = '" + analysisEntity.getAnalysispemasaran_terjangkau()+ "', "+
                    FLD_ANALYSISPEMASARAN_KEBUTUHAN +" = '" + analysisEntity.getAnalysispemasaran_kebutuhan()+ "' "+
                    " where " + FLD_ANALYSIS_ID + " = '" + analysisEntity.getIdAnalysis() + "' ";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int delete(DataHelper dbHelper ,AnalysisEntity analysisEntity){
        int success = RSLT_OK ;
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = "delete from " + TBL_ANALYSIS + " where " + FLD_ANALYSIS_ID +" = " + analysisEntity.getIdAnalysis() + " ";

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

            String sql = "SELECT * FROM " + TBL_ANALYSIS;

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
                AnalysisEntity analysisEntity = new AnalysisEntity();
                resultToObject(cursor,analysisEntity);
                lists.add(analysisEntity);
            }

            return lists;
        } catch (Exception e) {
            System.out.println(e);
        }
        return lists;
    }

    public static String[]  getListArrayOnlybahanbaku(DataHelper dbHelper,  int limitStart, int recordToGet, String whereClause, String order) {

        String[] daftar;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();
            SQLiteDatabase dbw = dbHelper.getWritableDatabase();

            String sql = "SELECT * FROM " + TBL_ANALYSIS;

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
                daftar[cc] =  cursor.getString(cursor.getColumnIndex(FLD_ANALYSISBAHANBAKU_KETERSEDIAAN)).toString()  ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new String[0];
    }

    public static String[]  getListArrayOnlybahanbakuWithNull(DataHelper dbHelper,  int limitStart, int recordToGet, String whereClause, String order) {

        String[] daftar;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();
            SQLiteDatabase dbw = dbHelper.getWritableDatabase();

            String sql = "SELECT * FROM " + TBL_ANALYSIS;

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
                daftar[(cc+1)] =  cursor.getString(cursor.getColumnIndex(FLD_ANALYSISBAHANBAKU_KETERSEDIAAN)).toString()  ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new String[0];
    }


    public static void resultToObject(Cursor cursor, AnalysisEntity analysisEntity) {

        try {
            analysisEntity.setIdAnalysis(cursor.getLong(cursor.getColumnIndex(FLD_ANALYSIS_ID)));
            analysisEntity.setIdProduct(cursor.getLong(cursor.getColumnIndex(FLD_PRODUCT_ID)));
            analysisEntity.setAnalysisbahanbaku_ketersediaan(cursor.getInt(cursor.getColumnIndex(FLD_ANALYSISBAHANBAKU_KETERSEDIAAN)));
            analysisEntity.setAnalysisbahanbaku_kemudahan(cursor.getInt(cursor.getColumnIndex(FLD_ANALYSISBAHANBAKU_KEMUDAHAN)));
            analysisEntity.setAnalysisbahanbaku_keberlanjutan(cursor.getInt(cursor.getColumnIndex(FLD_ANALYSISBAHANBAKU_KEBERLANJUTAN)));
            analysisEntity.setAnalysissumberdayamanusia_ketersediaan(cursor.getInt(cursor.getColumnIndex(FLD_ANALYSISSUMBERDAYAMANUSIA_KETERSEDIAAN)));
            analysisEntity.setAnalysissumberdayamanusia_kompeten(cursor.getInt(cursor.getColumnIndex(FLD_ANALYSISSUMBERDAYAMANUSIA_KOMPETEN)));
            analysisEntity.setAnalysispemasaran_ketersediaan(cursor.getInt(cursor.getColumnIndex(FLD_ANALYSISPEMASARAN_KETERSEDIAAN)));
            analysisEntity.setAnalysispemasaran_terjangkau(cursor.getInt(cursor.getColumnIndex(FLD_ANALYSISPEMASARAN_TERJANGKAU)));
            analysisEntity.setAnalysispemasaran_kebutuhan(cursor.getInt(cursor.getColumnIndex(FLD_ANALYSISPEMASARAN_KEBUTUHAN)));

        } catch (Exception e) {
        }

    }


}