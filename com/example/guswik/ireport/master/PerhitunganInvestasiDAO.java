package com.example.guswik.ireport.master;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.guswik.ireport.Formater;
import com.example.guswik.ireport.database.DataHelper;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Vector;

/**
 * Created by GUSWIK on 7/20/2017.
 */
public class PerhitunganInvestasiDAO {


    protected Cursor cursor;

    public static final String TBL_PERHITUNGANINVESTASI = "perhitunganInvestasi";
    public static final String FLD_PERHITUNGANINVESTASI_ID = "PERHITUNGANINVESTASI_ID";
    public static final String FLD_SUMBERDANA = "SUMBERDANA";
    public static final String FLD_TAHUN = "TAHUN";
    public static final String FLD_NOMINAL = "NOMINAL";
    public static final String FLD_MODAL_INVESTASI = "MODAL_INVESTASI";
    public static final String FLD_MODAL_KERJA = "MODAL_KERJA";

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;

    public static PerhitunganInvestasiEntity getById(DataHelper dbHelper , long oid) {

        PerhitunganInvestasiEntity perhitunganInvestasiEntity = new PerhitunganInvestasiEntity();
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM " + TBL_PERHITUNGANINVESTASI + " WHERE " + FLD_PERHITUNGANINVESTASI_ID + " = " + oid;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            resultToObject(cursor,perhitunganInvestasiEntity);

            return perhitunganInvestasiEntity;
        } catch (Exception e) {
            System.out.println(e);
        }
        return perhitunganInvestasiEntity;
    }


    public static int add(DataHelper dbHelper ,PerhitunganInvestasiEntity perhitunganInvestasiEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();
        try {
            String sql = "insert into "+TBL_PERHITUNGANINVESTASI+" (" +
                    FLD_SUMBERDANA +", " +
                    FLD_TAHUN +", " +
                    FLD_NOMINAL +", " +
                    FLD_MODAL_INVESTASI +", " +
                    FLD_MODAL_KERJA +" " +
                    " ) values ( " +
                    " '" + perhitunganInvestasiEntity.getSumberDana() + "', " +
                    " '" + perhitunganInvestasiEntity.getTahun() + "', " +
                    " '" + perhitunganInvestasiEntity.getNominal() + "', " +
                    " '" + perhitunganInvestasiEntity.getModalinvestasi() + "', " +
                    " '" + perhitunganInvestasiEntity.getModalkerja() + "' " +
                    " )";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int update(DataHelper dbHelper ,PerhitunganInvestasiEntity perhitunganInvestasiEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = " update "+TBL_PERHITUNGANINVESTASI+" set " +
                    FLD_SUMBERDANA +" = '" + perhitunganInvestasiEntity.getSumberDana() + "', "+
                    FLD_TAHUN +" = '" + perhitunganInvestasiEntity.getTahun() +"', "+
                    FLD_NOMINAL +" = '" + perhitunganInvestasiEntity.getNominal() +"', "+
                    FLD_MODAL_INVESTASI +" = '" + perhitunganInvestasiEntity.getModalinvestasi() +"', "+
                    FLD_MODAL_KERJA +" = '" + perhitunganInvestasiEntity.getModalkerja() +"' "+
                    " where " + FLD_PERHITUNGANINVESTASI_ID + " = '" + perhitunganInvestasiEntity.getIdPerhitunganInvestasi() + "' ";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int updateModal(DataHelper dbHelper ,PerhitunganInvestasiEntity perhitunganInvestasiEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = " update "+TBL_PERHITUNGANINVESTASI+" set " +
                    FLD_MODAL_INVESTASI +" = '" + perhitunganInvestasiEntity.getModalinvestasi() +"', "+
                    FLD_MODAL_KERJA +" = '" + perhitunganInvestasiEntity.getModalkerja() +"' "+
                    " where " + FLD_TAHUN + " = '" + perhitunganInvestasiEntity.getTahun() + "' ";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }



    public static int delete(DataHelper dbHelper ,PerhitunganInvestasiEntity perhitunganInvestasiEntity){
        int success = RSLT_OK ;
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = "delete from " + TBL_PERHITUNGANINVESTASI + " where " + FLD_PERHITUNGANINVESTASI_ID +" = " + perhitunganInvestasiEntity.getIdPerhitunganInvestasi() + " ";

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

            String sql = "SELECT * FROM " + TBL_PERHITUNGANINVESTASI;

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
                PerhitunganInvestasiEntity perhitunganInvestasiEntity = new PerhitunganInvestasiEntity();
                resultToObject(cursor,perhitunganInvestasiEntity);
                lists.add(perhitunganInvestasiEntity);
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

            String sql = "SELECT * FROM " + TBL_PERHITUNGANINVESTASI;

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

            String sql = "SELECT * FROM " + TBL_PERHITUNGANINVESTASI;

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

            String sql = "SELECT * FROM " + TBL_PERHITUNGANINVESTASI;

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

            String sql = "SELECT * FROM " + TBL_PERHITUNGANINVESTASI;

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
                daftar[cc] =  cursor.getString(cursor.getColumnIndex(FLD_PERHITUNGANINVESTASI_ID)).toString()  ;
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

            String sql = "SELECT * FROM " + TBL_PERHITUNGANINVESTASI;

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
                double nilaiproceed = cursor.getInt(cursor.getColumnIndex(FLD_NOMINAL));
                String nilainominal =  formatKurensi.format(cursor.getInt(cursor.getColumnIndex(FLD_NOMINAL)));

                //String nilainominal = formatKurensi.format(nilaix);
                daftar[cc] = " " + cursor.getString(cursor.getColumnIndex(FLD_SUMBERDANA)).toString() +" - "+ nilainominal  ;
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

            String sql = "SELECT * FROM " + TBL_PERHITUNGANINVESTASI;
            sql = sql + " WHERE " + FLD_TAHUN + " = '" + nama + "'";

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar = cursor.getLong(cursor.getColumnIndex(FLD_PERHITUNGANINVESTASI_ID)) ;
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

            String sql = "SELECT * FROM " + TBL_PERHITUNGANINVESTASI;
            sql = sql + " WHERE " + FLD_PERHITUNGANINVESTASI_ID + " = " + id;

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

    public static long getPerhitunganInvestasiTypeById(DataHelper dbHelper, long id) {

        long daftar = 0 ;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String sql = "SELECT * FROM " + TBL_PERHITUNGANINVESTASI;
            sql = sql + " WHERE " + FLD_PERHITUNGANINVESTASI_ID + " = " + id;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar =  cursor.getLong(cursor.getColumnIndex(FLD_NOMINAL)) ;
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

            String sql = "SELECT * FROM " + TBL_PERHITUNGANINVESTASI;
            sql = sql + " WHERE " + FLD_PERHITUNGANINVESTASI_ID + " = " + id;

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


    public static long getTotalSumberdanaByTahun(DataHelper dbHelper , long tahun) {

        long nilaitotal = 0 ;
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT SUM(" + FLD_NOMINAL + ")  AS NILAI FROM " + TBL_PERHITUNGANINVESTASI + " WHERE " + FLD_TAHUN + " = '" + tahun+"'";

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

    public static long getAlreadyModalInv(DataHelper dbHelper, long tahun) {

        long nilaitotal = 0 ;
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT " + FLD_MODAL_INVESTASI + "  AS NILAI FROM " + TBL_PERHITUNGANINVESTASI + " WHERE " + FLD_TAHUN + " = '" + tahun+"'";

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
    public static long getAlreadyModalKerja(DataHelper dbHelper, long tahun) {

        long nilaitotal = 0 ;
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT " + FLD_MODAL_KERJA + "  AS NILAI FROM " + TBL_PERHITUNGANINVESTASI + " WHERE " + FLD_TAHUN + " = '" + tahun+"'";

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


    public static void resultToObject(Cursor cursor, PerhitunganInvestasiEntity perhitunganInvestasiEntity) {

        try {
            perhitunganInvestasiEntity.setIdPerhitunganInvestasi(cursor.getLong(cursor.getColumnIndex(FLD_PERHITUNGANINVESTASI_ID)));
            perhitunganInvestasiEntity.setSumberDana(cursor.getString(cursor.getColumnIndex(FLD_SUMBERDANA)));
            perhitunganInvestasiEntity.setIdPerhitunganInvestasi(cursor.getLong(cursor.getColumnIndex(FLD_PERHITUNGANINVESTASI_ID)));
            perhitunganInvestasiEntity.setTahun(cursor.getLong(cursor.getColumnIndex(FLD_TAHUN)));
            perhitunganInvestasiEntity.setNominal(cursor.getLong(cursor.getColumnIndex(FLD_NOMINAL)));
            perhitunganInvestasiEntity.setModalinvestasi(cursor.getLong(cursor.getColumnIndex(FLD_MODAL_INVESTASI)));
            perhitunganInvestasiEntity.setModalkerja(cursor.getLong(cursor.getColumnIndex(FLD_MODAL_KERJA)));

        } catch (Exception e) {
        }

    }


}