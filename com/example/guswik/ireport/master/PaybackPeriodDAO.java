package com.example.guswik.ireport.master;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.guswik.ireport.database.DataHelper;

import java.text.DecimalFormat;
import java.util.Vector;

/**
 * Created by GUSWIK on 7/20/2022.
 */
public class PaybackPeriodDAO {


    protected Cursor cursor;

    public static final String TBL_PAYBACKPERIOD = "PAYBACKPERIOD";
    public static final String FLD_PAYBACKPERIOD_ID = "PAYBACKPERIOD_ID";
    public static final String FLD_NILAI_INVESTASI_TOTAL = "NILAI_INVESTASI_TOTAL";
    public static final String FLD_NILAI_PROCEED_PERTAHUN = "NILAI_PROCEED_PERTAHUN";
    public static final String FLD_KAS_MASUK_BERSIH = "KAS_MASUK_BERSIH";
    public static final String FLD_TAHUN_MAXIMUM_PAYBACK_PERIOD = "TAHUN_MAXIMUM_PAYBACK_PERIOD";
    public static final String FLD_PRODUCT_ID = "PRODUCT_ID";
    public static final String FLD_PRODUCT_STATUS = "FLD_PRODUCT_STATUS";

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;

    public static PaybackPeriodEntity getById(DataHelper dbHelper , long oid) {

        PaybackPeriodEntity paybackPeriodEntity = new PaybackPeriodEntity();
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM " + TBL_PAYBACKPERIOD + " WHERE " + FLD_PAYBACKPERIOD_ID + " = " + oid;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            resultToObject(cursor,paybackPeriodEntity);

            return paybackPeriodEntity;
        } catch (Exception e) {
            System.out.println(e);
        }
        return paybackPeriodEntity;
    }

    public static long getStatusById(DataHelper dbHelper, long productid) {

        long status =0;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String sql = "SELECT * FROM " + TBL_PAYBACKPERIOD;
            sql = sql + " WHERE " + FLD_PRODUCT_ID + " = " + productid;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                status = cursor.getLong(cursor.getColumnIndex(FLD_PRODUCT_STATUS)) ;
            }

            return status;
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }


    public static int add(DataHelper dbHelper ,PaybackPeriodEntity paybackperiodEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();
        try {
            String sql = "insert into "+TBL_PAYBACKPERIOD+" (" +
                    FLD_NILAI_INVESTASI_TOTAL +", " +
                    FLD_NILAI_PROCEED_PERTAHUN +", " +
                    FLD_KAS_MASUK_BERSIH +", " +
                    FLD_TAHUN_MAXIMUM_PAYBACK_PERIOD +", " +
                    FLD_PRODUCT_ID +", " +
                    FLD_PRODUCT_STATUS +" " +
                    " ) values ( " +
                    " '" + paybackperiodEntity.getNilaiInvestasitotal() + "', " +
                    " '" + paybackperiodEntity.getNilaiProceedPerTahun() + "', " +
                    " '" + paybackperiodEntity.getKasMasukBersih()+ "', " +
                    " '" + paybackperiodEntity.getTahunMaximumPaybackPeriod()+ "', " +
                    " '" + paybackperiodEntity.getIdProduct()+ "', " +
                    " '" + paybackperiodEntity.getProductstatus()+ "' " +
                    " )";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int update(DataHelper dbHelper ,PaybackPeriodEntity paybackperiodEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = " update "+TBL_PAYBACKPERIOD+" set " +
                    FLD_NILAI_INVESTASI_TOTAL +" = '" + paybackperiodEntity.getNilaiInvestasitotal() + "', "+
                    FLD_NILAI_PROCEED_PERTAHUN +" = '" + paybackperiodEntity.getNilaiProceedPerTahun() +"', "+
                    FLD_KAS_MASUK_BERSIH +" = '" + paybackperiodEntity.getKasMasukBersih() +"', "+
                    FLD_TAHUN_MAXIMUM_PAYBACK_PERIOD +" = '" + paybackperiodEntity.getTahunMaximumPaybackPeriod() +"', "+
                    FLD_PRODUCT_ID +" = '" + paybackperiodEntity.getIdProduct() +"', "+
                    FLD_PRODUCT_STATUS +" = '" + paybackperiodEntity.getProductstatus() +"' "+
                    " where " + FLD_PAYBACKPERIOD_ID + " = '" + paybackperiodEntity.getIdPaybackPeriod() + "' " +
                    " and " + FLD_PRODUCT_ID + " = '" + paybackperiodEntity.getIdProduct() + "' ";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int updatestatus(DataHelper dbHelper , long productid, long status){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = " update "+TBL_PAYBACKPERIOD+" set " +
                    FLD_PRODUCT_STATUS +" = '" + status +"' "+
                    " where  "+ FLD_PRODUCT_ID + " = '" + productid + "' ";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int updatealltotalinvestasidanmaxpayback(DataHelper dbHelper ,PaybackPeriodEntity paybackperiodEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = " update "+TBL_PAYBACKPERIOD+" set " +
                    FLD_NILAI_INVESTASI_TOTAL +" = '" + paybackperiodEntity.getNilaiInvestasitotal() + "', "+
                    FLD_TAHUN_MAXIMUM_PAYBACK_PERIOD +" = '" + paybackperiodEntity.getTahunMaximumPaybackPeriod() +"' "+
                    " where " + FLD_PRODUCT_ID + " = '" + paybackperiodEntity.getIdProduct() + "' ";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int delete(DataHelper dbHelper ,PaybackPeriodEntity paybackperiodEntity){
        int success = RSLT_OK ;
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = "delete from " + TBL_PAYBACKPERIOD + " where " + FLD_PAYBACKPERIOD_ID +" = " + paybackperiodEntity.getIdPaybackPeriod() + " and " + FLD_PRODUCT_ID +" = " + paybackperiodEntity.getIdProduct() + " ";

            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static String[]  getListArrayPaybackPeriode(DataHelper dbHelper,  int limitStart, int recordToGet, String whereClause, String order) {

        DecimalFormat df1 = new DecimalFormat("@######");
        String[] daftar;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();
            SQLiteDatabase dbw = dbHelper.getWritableDatabase();

            String sql = "SELECT * FROM " + TBL_PAYBACKPERIOD  ;

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
            double proceedkumulatif=0;
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                int proceedke = cc+1;

                double proceednow =  cursor.getDouble(cursor.getColumnIndex(FLD_NILAI_PROCEED_PERTAHUN)) ;
                proceedkumulatif =  proceedkumulatif + proceednow;
                daftar[cc] = " Th. " + proceedke +" Arus Kas: " + df1.format(proceednow)  + " | Total: "+ df1.format(proceedkumulatif) ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new String[0];
    }

    public static Vector getList(DataHelper dbHelper ,int limitStart, int recordToGet, String whereClause, String order) {

        Vector lists = new Vector();
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String sql = "SELECT * FROM " + TBL_PAYBACKPERIOD;

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
                PaybackPeriodEntity paybackperiodEntity = new PaybackPeriodEntity();
                resultToObject(cursor,paybackperiodEntity);
                lists.add(paybackperiodEntity);
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

            String sql = "SELECT * FROM " + TBL_PAYBACKPERIOD;

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
                daftar[cc] =  cursor.getString(cursor.getColumnIndex(FLD_NILAI_INVESTASI_TOTAL)).toString()  ;
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

            String sql = "SELECT * FROM " + TBL_PAYBACKPERIOD;

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
                daftar[cc] =  cursor.getString(cursor.getColumnIndex(FLD_PAYBACKPERIOD_ID)).toString()  ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new String[0];
    }



    public static void resultToObject(Cursor cursor, PaybackPeriodEntity paybackperiodEntity) {

        try {
            paybackperiodEntity.setIdPaybackPeriod(cursor.getLong(cursor.getColumnIndex(FLD_PAYBACKPERIOD_ID)));
            paybackperiodEntity.setNilaiInvestasitotal(cursor.getLong(cursor.getColumnIndex(FLD_NILAI_INVESTASI_TOTAL)));
            paybackperiodEntity.setNilaiProceedPerTahun(cursor.getLong(cursor.getColumnIndex(FLD_NILAI_PROCEED_PERTAHUN)));
            paybackperiodEntity.setKasMasukBersih(cursor.getLong(cursor.getColumnIndex(FLD_KAS_MASUK_BERSIH)));
            paybackperiodEntity.setTahunMaximumPaybackPeriod(cursor.getLong(cursor.getColumnIndex(FLD_TAHUN_MAXIMUM_PAYBACK_PERIOD)));
            paybackperiodEntity.setIdProduct(cursor.getLong(cursor.getColumnIndex(FLD_PRODUCT_ID)));
            paybackperiodEntity.setProductstatus(cursor.getLong(cursor.getColumnIndex(FLD_PRODUCT_STATUS)));

        } catch (Exception e) {
        }

    }


}