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
public class DetailPerhitunganPendapatanDAO {


    protected Cursor cursor;

    public static final String TBL_DETAILPERHITUNGANPENDAPATAN = "detailperhitunganpendapatan";
    public static final String FLD_DETAILPERHITUNGANPENDAPATAN_ID = "DETAILPERHITUNGANPENDAPATAN_ID";
    public static final String FLD_RBA = "RBA";
    public static final String FLD_TAHUN = "TAHUN";
    public static final String FLD_VOLUME = "VOLUME";
    public static final String FLD_HARGA = "HARGA";
    public static final String FLD_TOTAL_HARGA = "TOTAL_HARGA";

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;

    public static DetailPerhitunganPendapatanEntity getById(DataHelper dbHelper , long oid) {

        DetailPerhitunganPendapatanEntity detailperhitunganpendapatanEntity = new DetailPerhitunganPendapatanEntity();
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM " + TBL_DETAILPERHITUNGANPENDAPATAN + " WHERE " + FLD_DETAILPERHITUNGANPENDAPATAN_ID + " = " + oid;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            resultToObject(cursor,detailperhitunganpendapatanEntity);

            return detailperhitunganpendapatanEntity;
        } catch (Exception e) {
            System.out.println(e);
        }
        return detailperhitunganpendapatanEntity;
    }


    public static int add(DataHelper dbHelper ,DetailPerhitunganPendapatanEntity detailperhitunganpendapatanEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();
        try {
            String sql = "insert into "+TBL_DETAILPERHITUNGANPENDAPATAN+" (" +
                    FLD_RBA +", " +
                    FLD_TAHUN +", " +
                    FLD_VOLUME +", " +
                    FLD_HARGA +", " +
                    FLD_TOTAL_HARGA +" " +
                    " ) values ( " +
                    " '" + detailperhitunganpendapatanEntity.getRBA() + "', " +
                    " '" + detailperhitunganpendapatanEntity.getTahun() + "', " +
                    " '" + detailperhitunganpendapatanEntity.getVolume() + "', " +
                    " '" + detailperhitunganpendapatanEntity.getHarga() + "', " +
                    " '" + detailperhitunganpendapatanEntity.getTotalharga() + "' " +
                    " )";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int update(DataHelper dbHelper ,DetailPerhitunganPendapatanEntity detailperhitunganpendapatanEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = " update "+TBL_DETAILPERHITUNGANPENDAPATAN+" set " +
                    FLD_RBA +" = '" + detailperhitunganpendapatanEntity.getRBA() + "', "+
                    FLD_TAHUN +" = '" + detailperhitunganpendapatanEntity.getTahun() +"', "+
                    FLD_VOLUME +" = '" + detailperhitunganpendapatanEntity.getVolume() +"', "+
                    FLD_HARGA +" = '" + detailperhitunganpendapatanEntity.getHarga() +"', "+
                    FLD_TOTAL_HARGA +" = '" + detailperhitunganpendapatanEntity.getTotalharga() +"' "+
                    " where " + FLD_DETAILPERHITUNGANPENDAPATAN_ID + " = '" + detailperhitunganpendapatanEntity.getIdDetailPerhitunganPendapatan() + "' ";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int updateModal(DataHelper dbHelper ,DetailPerhitunganPendapatanEntity detailperhitunganpendapatanEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = " update "+TBL_DETAILPERHITUNGANPENDAPATAN+" set " +
                    FLD_HARGA +" = '" + detailperhitunganpendapatanEntity.getHarga() +"', "+
                    FLD_TOTAL_HARGA +" = '" + detailperhitunganpendapatanEntity.getTotalharga() +"' "+
                    " where " + FLD_TAHUN + " = '" + detailperhitunganpendapatanEntity.getTahun() + "' ";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }



    public static int delete(DataHelper dbHelper ,DetailPerhitunganPendapatanEntity detailperhitunganpendapatanEntity){
        int success = RSLT_OK ;
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = "delete from " + TBL_DETAILPERHITUNGANPENDAPATAN + " where " + FLD_DETAILPERHITUNGANPENDAPATAN_ID +" = " + detailperhitunganpendapatanEntity.getIdDetailPerhitunganPendapatan() + " ";

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

            String sql = "SELECT * FROM " + TBL_DETAILPERHITUNGANPENDAPATAN;

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
                DetailPerhitunganPendapatanEntity detailperhitunganpendapatanEntity = new DetailPerhitunganPendapatanEntity();
                resultToObject(cursor,detailperhitunganpendapatanEntity);
                lists.add(detailperhitunganpendapatanEntity);
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

            String sql = "SELECT * FROM " + TBL_DETAILPERHITUNGANPENDAPATAN;

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

            String sql = "SELECT * FROM " + TBL_DETAILPERHITUNGANPENDAPATAN;

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

            String sql = "SELECT * FROM " + TBL_DETAILPERHITUNGANPENDAPATAN;

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

            String sql = "SELECT * FROM " + TBL_DETAILPERHITUNGANPENDAPATAN;

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
                daftar[cc] =  cursor.getString(cursor.getColumnIndex(FLD_DETAILPERHITUNGANPENDAPATAN_ID)).toString()  ;
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
            String sql = "SELECT SUM(" + FLD_TOTAL_HARGA + ")  AS NILAI FROM " + TBL_DETAILPERHITUNGANPENDAPATAN + " WHERE " + FLD_TAHUN + " = '" + tahun+"'";

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

            String sql = "SELECT * FROM " + TBL_DETAILPERHITUNGANPENDAPATAN;

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

            String sql = "SELECT * FROM " + TBL_DETAILPERHITUNGANPENDAPATAN;
            sql = sql + " WHERE " + FLD_TAHUN + " = '" + nama + "'";

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar = cursor.getLong(cursor.getColumnIndex(FLD_DETAILPERHITUNGANPENDAPATAN_ID)) ;
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

            String sql = "SELECT * FROM " + TBL_DETAILPERHITUNGANPENDAPATAN;
            sql = sql + " WHERE " + FLD_DETAILPERHITUNGANPENDAPATAN_ID + " = " + id;

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

    public static long getDetailPerhitunganPendapatanTypeById(DataHelper dbHelper, long id) {

        long daftar = 0 ;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String sql = "SELECT * FROM " + TBL_DETAILPERHITUNGANPENDAPATAN;
            sql = sql + " WHERE " + FLD_DETAILPERHITUNGANPENDAPATAN_ID + " = " + id;

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

            String sql = "SELECT * FROM " + TBL_DETAILPERHITUNGANPENDAPATAN;
            sql = sql + " WHERE " + FLD_DETAILPERHITUNGANPENDAPATAN_ID + " = " + id;

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
            String sql = "SELECT SUM(" + FLD_VOLUME + ")  AS NILAI FROM " + TBL_DETAILPERHITUNGANPENDAPATAN + " WHERE " + FLD_TAHUN + " = '" + tahun+"'";

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

    public static void resultToObject(Cursor cursor, DetailPerhitunganPendapatanEntity detailperhitunganpendapatanEntity) {

        try {
            detailperhitunganpendapatanEntity.setIdDetailPerhitunganPendapatan(cursor.getLong(cursor.getColumnIndex(FLD_DETAILPERHITUNGANPENDAPATAN_ID)));
            detailperhitunganpendapatanEntity.setRBA(cursor.getString(cursor.getColumnIndex(FLD_RBA)));
            detailperhitunganpendapatanEntity.setTahun(cursor.getLong(cursor.getColumnIndex(FLD_TAHUN)));
            detailperhitunganpendapatanEntity.setVolume(cursor.getLong(cursor.getColumnIndex(FLD_VOLUME)));
            detailperhitunganpendapatanEntity.setHarga(cursor.getLong(cursor.getColumnIndex(FLD_HARGA)));
            detailperhitunganpendapatanEntity.setTotalharga(cursor.getLong(cursor.getColumnIndex(FLD_TOTAL_HARGA)));
        } catch (Exception e) {
        }

    }


}