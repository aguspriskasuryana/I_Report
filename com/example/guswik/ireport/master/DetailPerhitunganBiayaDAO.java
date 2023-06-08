package com.example.guswik.ireport.master;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.guswik.ireport.database.DataHelper;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Vector;

/**
 * Created by GUSWIK on 7/20/2022.
 */
public class DetailPerhitunganBiayaDAO {


    protected Cursor cursor;

    public static final String TBL_DETAILPERHITUNGANBIAYA = "detailperhitunganbiaya";
    public static final String FLD_DETAILPERHITUNGANBIAYA_ID = "DETAILPERHITUNGANBIAYA_ID";
    public static final String FLD_RBA = "RBA";
    public static final String FLD_TAHUN = "TAHUN";
    public static final String FLD_VOLUME = "VOLUME";
    public static final String FLD_HARGA = "HARGA";
    public static final String FLD_TOTAL_HARGA = "TOTAL_HARGA";

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;

    public static DetailPerhitunganBiayaEntity getById(DataHelper dbHelper , long oid) {

        DetailPerhitunganBiayaEntity detailperhitunganbiayaEntity = new DetailPerhitunganBiayaEntity();
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM " + TBL_DETAILPERHITUNGANBIAYA + " WHERE " + FLD_DETAILPERHITUNGANBIAYA_ID + " = " + oid;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            resultToObject(cursor,detailperhitunganbiayaEntity);

            return detailperhitunganbiayaEntity;
        } catch (Exception e) {
            System.out.println(e);
        }
        return detailperhitunganbiayaEntity;
    }


    public static int add(DataHelper dbHelper ,DetailPerhitunganBiayaEntity detailperhitunganbiayaEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();
        try {
            String sql = "insert into "+TBL_DETAILPERHITUNGANBIAYA+" (" +
                    FLD_RBA +", " +
                    FLD_TAHUN +", " +
                    FLD_VOLUME +", " +
                    FLD_HARGA +", " +
                    FLD_TOTAL_HARGA +" " +
                    " ) values ( " +
                    " '" + detailperhitunganbiayaEntity.getRBA() + "', " +
                    " '" + detailperhitunganbiayaEntity.getTahun() + "', " +
                    " '" + detailperhitunganbiayaEntity.getVolume() + "', " +
                    " '" + detailperhitunganbiayaEntity.getHarga() + "', " +
                    " '" + detailperhitunganbiayaEntity.getTotalharga() + "' " +
                    " )";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int update(DataHelper dbHelper ,DetailPerhitunganBiayaEntity detailperhitunganbiayaEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = " update "+TBL_DETAILPERHITUNGANBIAYA+" set " +
                    FLD_RBA +" = '" + detailperhitunganbiayaEntity.getRBA() + "', "+
                    FLD_TAHUN +" = '" + detailperhitunganbiayaEntity.getTahun() +"', "+
                    FLD_VOLUME +" = '" + detailperhitunganbiayaEntity.getVolume() +"', "+
                    FLD_HARGA +" = '" + detailperhitunganbiayaEntity.getHarga() +"', "+
                    FLD_TOTAL_HARGA +" = '" + detailperhitunganbiayaEntity.getTotalharga() +"' "+
                    " where " + FLD_DETAILPERHITUNGANBIAYA_ID + " = '" + detailperhitunganbiayaEntity.getIdDetailPerhitunganBiaya() + "' ";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int updateModal(DataHelper dbHelper ,DetailPerhitunganBiayaEntity detailperhitunganbiayaEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = " update "+TBL_DETAILPERHITUNGANBIAYA+" set " +
                    FLD_HARGA +" = '" + detailperhitunganbiayaEntity.getHarga() +"', "+
                    FLD_TOTAL_HARGA +" = '" + detailperhitunganbiayaEntity.getTotalharga() +"' "+
                    " where " + FLD_TAHUN + " = '" + detailperhitunganbiayaEntity.getTahun() + "' ";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }



    public static int delete(DataHelper dbHelper ,DetailPerhitunganBiayaEntity detailperhitunganbiayaEntity){
        int success = RSLT_OK ;
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = "delete from " + TBL_DETAILPERHITUNGANBIAYA + " where " + FLD_DETAILPERHITUNGANBIAYA_ID +" = " + detailperhitunganbiayaEntity.getIdDetailPerhitunganBiaya() + " ";

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

            String sql = "SELECT * FROM " + TBL_DETAILPERHITUNGANBIAYA;

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
                DetailPerhitunganBiayaEntity detailperhitunganbiayaEntity = new DetailPerhitunganBiayaEntity();
                resultToObject(cursor,detailperhitunganbiayaEntity);
                lists.add(detailperhitunganbiayaEntity);
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

            String sql = "SELECT * FROM " + TBL_DETAILPERHITUNGANBIAYA;

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
                daftar[cc] =  cursor.getString(cursor.getColumnIndex(FLD_TAHUN)).toString()  ;
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

            String sql = "SELECT * FROM " + TBL_DETAILPERHITUNGANBIAYA;

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
                daftar[(cc+1)] =  cursor.getString(cursor.getColumnIndex(FLD_TAHUN)).toString()  ;
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

            String sql = "SELECT * FROM " + TBL_DETAILPERHITUNGANBIAYA;

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
                daftar[(cc+1)] =  cursor.getString(cursor.getColumnIndex(FLD_TAHUN)).toString()  ;
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

            String sql = "SELECT * FROM " + TBL_DETAILPERHITUNGANBIAYA;

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
                daftar[cc] =  cursor.getString(cursor.getColumnIndex(FLD_DETAILPERHITUNGANBIAYA_ID)).toString()  ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new String[0];
    }

    public static long getTotalHargaByTahun(DataHelper dbHelper , long tahun) {

        long nilaitotal = 0 ;
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT SUM(" + FLD_TOTAL_HARGA + ")  AS NILAI FROM " + TBL_DETAILPERHITUNGANBIAYA + " WHERE " + FLD_TAHUN + " = '" + tahun+"'";

            cursor = db.rawQuery(sql,null);

            cursor.moveToFirst();
            cursor.moveToPosition(0);
            nilaitotal = cursor.getLong(cursor.getColumnIndex("NILAI"));

            return nilaitotal;
        } catch (Exception e) {
            System.out.println(e);
        }
        return nilaitotal;
    }

    public static String[]  getListArray(DataHelper dbHelper,  int limitStart, int recordToGet, String whereClause, String order) {

        String[] daftar;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();
            SQLiteDatabase dbw = dbHelper.getWritableDatabase();

            String sql = "SELECT * FROM " + TBL_DETAILPERHITUNGANBIAYA;

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
            Locale myIndonesianLocale = new Locale("in", "ID");

            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);


                NumberFormat formatKurensi = NumberFormat.getCurrencyInstance(myIndonesianLocale);
                double nilaiproceed = cursor.getInt(cursor.getColumnIndex(FLD_HARGA));
                String nilaiHarga =  formatKurensi.format(cursor.getInt(cursor.getColumnIndex(FLD_HARGA)));

                //String nilainominal = formatKurensi.format(nilaix);
                daftar[cc] = " " + cursor.getString(cursor.getColumnIndex(FLD_RBA)).toString() +" - "+ nilaiHarga  ;
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

            String sql = "SELECT * FROM " + TBL_DETAILPERHITUNGANBIAYA;
            sql = sql + " WHERE " + FLD_TAHUN + " = '" + nama + "'";

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar = cursor.getLong(cursor.getColumnIndex(FLD_DETAILPERHITUNGANBIAYA_ID)) ;
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

            String sql = "SELECT * FROM " + TBL_DETAILPERHITUNGANBIAYA;
            sql = sql + " WHERE " + FLD_DETAILPERHITUNGANBIAYA_ID + " = " + id;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar = "" + cursor.getString(cursor.getColumnIndex(FLD_TAHUN)).toString() ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }

    public static long getDetailPerhitunganBiayaTypeById(DataHelper dbHelper, long id) {

        long daftar = 0 ;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String sql = "SELECT * FROM " + TBL_DETAILPERHITUNGANBIAYA;
            sql = sql + " WHERE " + FLD_DETAILPERHITUNGANBIAYA_ID + " = " + id;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar =  cursor.getLong(cursor.getColumnIndex(FLD_VOLUME)) ;
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

            String sql = "SELECT * FROM " + TBL_DETAILPERHITUNGANBIAYA;
            sql = sql + " WHERE " + FLD_DETAILPERHITUNGANBIAYA_ID + " = " + id;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar = "" + cursor.getString(cursor.getColumnIndex(FLD_TAHUN)).toString() ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }


    public static long getTotalRBAByTahun(DataHelper dbHelper , long tahun) {

        long nilaitotal = 0 ;
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT SUM(" + FLD_VOLUME + ")  AS NILAI FROM " + TBL_DETAILPERHITUNGANBIAYA + " WHERE " + FLD_TAHUN + " = '" + tahun+"'";

            //String sql = " SELECT SUM("+FLD_TRANSACTION_VALUE+") AS NILAI FROM "+TBL_TRANSACTION+" WHERE "+FLD_AKUN_ID+" = "+akunId+" AND "+FLD_TRANSACTION_TYPE+" = 0 AND ("+FLD_TRANSACTION_DATE+" BETWEEN '" + Formater.getStringDate(startDate)+ "' AND '" +Formater.getStringDate(endDate)+ "' ) ;";

            cursor = db.rawQuery(sql,null);

            cursor.moveToFirst();
            cursor.moveToPosition(0);
            nilaitotal = cursor.getLong(cursor.getColumnIndex("NILAI"));

            return nilaitotal;
        } catch (Exception e) {
            System.out.println(e);
        }
        return nilaitotal;
    }

    public static void resultToObject(Cursor cursor, DetailPerhitunganBiayaEntity detailperhitunganbiayaEntity) {

        try {
            detailperhitunganbiayaEntity.setIdDetailPerhitunganBiaya(cursor.getLong(cursor.getColumnIndex(FLD_DETAILPERHITUNGANBIAYA_ID)));
            detailperhitunganbiayaEntity.setRBA(cursor.getString(cursor.getColumnIndex(FLD_RBA)));
            detailperhitunganbiayaEntity.setTahun(cursor.getLong(cursor.getColumnIndex(FLD_TAHUN)));
            detailperhitunganbiayaEntity.setVolume(cursor.getLong(cursor.getColumnIndex(FLD_VOLUME)));
            detailperhitunganbiayaEntity.setHarga(cursor.getLong(cursor.getColumnIndex(FLD_HARGA)));
            detailperhitunganbiayaEntity.setTotalharga(cursor.getLong(cursor.getColumnIndex(FLD_TOTAL_HARGA)));
        } catch (Exception e) {
        }

    }


}