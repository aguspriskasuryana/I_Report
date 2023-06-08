package com.example.guswik.ireport.master;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.guswik.ireport.Formater;
import com.example.guswik.ireport.database.DataHelper;

import java.util.Date;
import java.util.Vector;

/**
 * Created by GUSWIK on 7/19/2017.
 */
public class DepresiasiDAO {


    protected Cursor cursor;

    public static final String TBL_DEPRESIASI = "depresiasi";
    public static final String FLD_DEPRESIASI_ID = "DEPRESIASI_ID";
    public static final String FLD_TRANSACTION_NO = "TRANSACTION_NO";
    public static final String FLD_HARGA_PEROLEHAN = "HARGA_PEROLEHAN";
    public static final String FLD_NILAI_SISA = "NILAI_SISA";
    public static final String FLD_UMUR_EKONOMI = "UMUR_EKONOMI";
    public static final String FLD_DEPRESIASI_VALUE = "DEPRESIASI_VALUE";
    public static final String FLD_AKUN_ID = "AKUN_ID";

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;

    public static int DEBIT  = 0;
    public static int KREDIT = 1;

    public static DepresiasiEntity getById(DataHelper dbHelper , long oid) {

        DepresiasiEntity depresiasiEntity = new DepresiasiEntity();
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM " + TBL_DEPRESIASI + " WHERE " + FLD_DEPRESIASI_ID + " = " + oid;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            resultToObject(cursor,depresiasiEntity);

            return depresiasiEntity;
        } catch (Exception e) {
            System.out.println(e);
        }
        return depresiasiEntity;
    }

    public static DepresiasiEntity getByAkunId(DataHelper dbHelper , long oid) {

        DepresiasiEntity depresiasiEntity = new DepresiasiEntity();
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM " + TBL_DEPRESIASI + " WHERE " + FLD_AKUN_ID + " = " + oid;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            resultToObject(cursor,depresiasiEntity);

            return depresiasiEntity;
        } catch (Exception e) {
            System.out.println(e);
        }
        return depresiasiEntity;
    }

    public static DepresiasiEntity getByNo(DataHelper dbHelper , String no) {

        DepresiasiEntity depresiasiEntity = new DepresiasiEntity();
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM " + TBL_DEPRESIASI + " WHERE " + FLD_TRANSACTION_NO + " = '" + no+"'";

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            resultToObject(cursor,depresiasiEntity);

            return depresiasiEntity;
        } catch (Exception e) {
            System.out.println(e);
        }
        return depresiasiEntity;
    }



    public static int add(DataHelper dbHelper ,DepresiasiEntity depresiasiEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();
        try {
            String sql = "insert into "+TBL_DEPRESIASI+" (" +
                    FLD_TRANSACTION_NO +", " +
                    FLD_HARGA_PEROLEHAN +", " +
                    FLD_NILAI_SISA +", " +
                    FLD_UMUR_EKONOMI +", " +
                    FLD_DEPRESIASI_VALUE +", " +
                    FLD_AKUN_ID +" " +
                    " ) values ( " +
                    " '" + depresiasiEntity.getNoTransaction() + "', " +
                    " '" + depresiasiEntity.getHargaPerolehan() + "', " +
                    " '" + depresiasiEntity.getNilaiSIsa() + "', " +
                    " '" + depresiasiEntity.getUmurEkonomi() + "', " +
                    " '" + depresiasiEntity.getValueDepresiasi() + "', " +
                    " '" + depresiasiEntity.getAkunIdx() + "' " +
                    " )";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int update(DataHelper dbHelper ,DepresiasiEntity depresiasiEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = " update "+TBL_DEPRESIASI+" set " +
                    FLD_TRANSACTION_NO +" = '" + depresiasiEntity.getNoTransaction() + "', "+
                    FLD_HARGA_PEROLEHAN +" = '" + depresiasiEntity.getHargaPerolehan() +"', "+
                    FLD_NILAI_SISA +" = '" + depresiasiEntity.getNilaiSIsa() +"', "+
                    FLD_UMUR_EKONOMI +" = '" + depresiasiEntity.getUmurEkonomi() +"', "+
                    FLD_DEPRESIASI_VALUE +" = '" + depresiasiEntity.getValueDepresiasi() +"', "+
                    FLD_AKUN_ID +" = " + depresiasiEntity.getAkunIdx() +" "+
                    " where " + FLD_DEPRESIASI_ID + " = '" + depresiasiEntity.getIdDepresiasi() + "' ";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int delete(DataHelper dbHelper ,DepresiasiEntity depresiasiEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = "delete from " + TBL_DEPRESIASI + " where " + FLD_DEPRESIASI_ID +" = '" + depresiasiEntity.getIdDepresiasi() + " ";

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

            String sql = "SELECT * FROM " + TBL_DEPRESIASI;

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
                DepresiasiEntity depresiasiEntity = new DepresiasiEntity();
                resultToObject(cursor,depresiasiEntity);
                lists.add(depresiasiEntity);
            }

            return lists;
        } catch (Exception e) {
            System.out.println(e);
        }
        return lists;
    }


    public static void resultToObject(Cursor cursor, DepresiasiEntity depresiasiEntity) {

        try {
            depresiasiEntity.setIdDepresiasi(cursor.getLong(cursor.getColumnIndex(FLD_DEPRESIASI_ID)));
            depresiasiEntity.setNoTransaction(cursor.getString(cursor.getColumnIndex(FLD_TRANSACTION_NO)));
            depresiasiEntity.setHargaPerolehan(cursor.getDouble(cursor.getColumnIndex(FLD_HARGA_PEROLEHAN)));
            depresiasiEntity.setNilaiSIsa(cursor.getDouble(cursor.getColumnIndex(FLD_NILAI_SISA)));
            depresiasiEntity.setUmurEkonomi(cursor.getDouble(cursor.getColumnIndex(FLD_UMUR_EKONOMI)));
            depresiasiEntity.setValueDepresiasi(cursor.getDouble(cursor.getColumnIndex(FLD_DEPRESIASI_VALUE)));
            depresiasiEntity.setAkunIdx(cursor.getLong(cursor.getColumnIndex(FLD_AKUN_ID)));

        } catch (Exception e) {
        }

    }


}
