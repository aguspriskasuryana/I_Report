package com.example.guswik.ireport.master;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.guswik.ireport.database.DataHelper;

import java.util.Vector;

/**
 * Created by GUSWIK on 7/20/2022.
 */
public class ProductDAO {


    protected Cursor cursor;

    public static final String TBL_PRODUCT = "product";
    public static final String FLD_PRODUCT_ID = "PRODUCT_ID";
    public static final String FLD_PRODUCT_NO = "PRODUCT_NO";
    public static final String FLD_PRODUCT_NAMA = "PRODUCT_NAMA";
    public static final String FLD_PRODUCT_TYPE_ID = "PRODUCT_TYPE_ID";
    public static final String FLD_PRODUCT_STATUS_BB = "PRODUCT_STATUS_BB";
    public static final String FLD_PRODUCT_STATUS_SDM = "PRODUCT_STATUS_SDM";
    public static final String FLD_PRODUCT_STATUS_PEM = "PRODUCT_STATUS_PEM";
    public static final String FLD_PRODUCT_STATUS_PP = "PRODUCT_STATUS_PP";
    public static final String FLD_PRODUCT_STATUS_NPV = "PRODUCT_STATUS_NPV";
    public static final String FLD_PRODUCT_STATUS_PI = "PRODUCT_STATUS_PI";

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;

    public static ProductEntity getById(DataHelper dbHelper , long oid) {

        ProductEntity productEntity = new ProductEntity();
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM " + TBL_PRODUCT + " WHERE " + FLD_PRODUCT_ID + " = " + oid;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            resultToObject(cursor,productEntity);

            return productEntity;
        } catch (Exception e) {
            System.out.println(e);
        }
        return productEntity;
    }


    public static int add(DataHelper dbHelper ,ProductEntity productEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();
        try {
            String sql = "insert into "+TBL_PRODUCT+" (" +
                    FLD_PRODUCT_NO +", " +
                    FLD_PRODUCT_NAMA +", " +
                    FLD_PRODUCT_TYPE_ID +" " +
                    " ) values ( " +
                    " '" + productEntity.getNoProduct() + "', " +
                    " '" + productEntity.getNamaProduct() + "', " +
                    " '" + productEntity.getIdProductType() + "' " +
                    " )";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int update(DataHelper dbHelper ,ProductEntity productEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = " update "+TBL_PRODUCT+" set " +
                    FLD_PRODUCT_NO +" = '" + productEntity.getNoProduct() + "', "+
                    FLD_PRODUCT_NAMA +" = '" + productEntity.getNamaProduct() +"', "+
                    FLD_PRODUCT_TYPE_ID +" = '" + productEntity.getIdProductType() +"' "+
                    " where " + FLD_PRODUCT_ID + " = '" + productEntity.getIdProduct() + "' ";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int updatestatusproduct(DataHelper dbHelper ,ProductEntity productEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = " update "+TBL_PRODUCT+" set " +
                    FLD_PRODUCT_NO +" = '" + productEntity.getNoProduct() + "', "+
                    FLD_PRODUCT_NAMA +" = '" + productEntity.getNamaProduct() +"', "+
                    FLD_PRODUCT_TYPE_ID +" = '" + productEntity.getIdProductType() +"', "+
                    FLD_PRODUCT_STATUS_BB +" = '" + productEntity.getProductStatusBB() +"', "+
                    FLD_PRODUCT_STATUS_SDM +" = '" + productEntity.getProductStatusSDM() +"', "+
                    FLD_PRODUCT_STATUS_PEM +" = '" + productEntity.getProductStatusPEM() +"', "+
                    FLD_PRODUCT_STATUS_PP +" = '" + productEntity.getProductStatusPP() +"', "+
                    FLD_PRODUCT_STATUS_NPV +" = '" + productEntity.getProductStatusNPV() +"', "+
                    FLD_PRODUCT_STATUS_PI +" = '" + productEntity.getProductStatusPI() +"' "+
                    " where " + FLD_PRODUCT_ID + " = '" + productEntity.getIdProduct() + "' ";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int delete(DataHelper dbHelper ,ProductEntity productEntity){
        int success = RSLT_OK ;
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = "delete from " + TBL_PRODUCT + " where " + FLD_PRODUCT_ID +" = " + productEntity.getIdProduct() + " ";

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

            String sql = "SELECT * FROM " + TBL_PRODUCT;

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
                ProductEntity productEntity = new ProductEntity();
                resultToObject(cursor,productEntity);
                lists.add(productEntity);
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

            String sql = "SELECT * FROM " + TBL_PRODUCT;

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
                daftar[cc] =  cursor.getString(cursor.getColumnIndex(FLD_PRODUCT_NAMA)).toString()  ;
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

            String sql = "SELECT * FROM " + TBL_PRODUCT;

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
                daftar[(cc+1)] =  cursor.getString(cursor.getColumnIndex(FLD_PRODUCT_NAMA)).toString()  ;
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

            String sql = "SELECT * FROM " + TBL_PRODUCT;

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
                daftar[(cc+1)] =  cursor.getString(cursor.getColumnIndex(FLD_PRODUCT_NAMA)).toString()  ;
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

            String sql = "SELECT * FROM " + TBL_PRODUCT;

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
                daftar[cc] =  cursor.getString(cursor.getColumnIndex(FLD_PRODUCT_ID)).toString()  ;
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

            String sql = "SELECT * FROM " + TBL_PRODUCT;

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
                daftar[cc] = ""+ cursor.getString(cursor.getColumnIndex(FLD_PRODUCT_NAMA)).toString()  ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new String[0];
    }



    public static String[]  getListArrayProductWithType(DataHelper dbHelper,  int limitStart, int recordToGet, String whereClause, String order) {

        String[] daftar;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();
            SQLiteDatabase dbw = dbHelper.getWritableDatabase();

            String sql = "SELECT * FROM " + TBL_PRODUCT +" INNER JOIN " + ProductTypeDAO.TBL_PRODUCT_TYPE + " ON "+ProductTypeDAO.TBL_PRODUCT_TYPE+"." +ProductTypeDAO.FLD_PRODUCT_TYPE_ID + " = " +TBL_PRODUCT+"."+ ProductDAO.FLD_PRODUCT_TYPE_ID ;

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
                daftar[cc] = " ID : " + cursor.getString(cursor.getColumnIndex(FLD_PRODUCT_ID)).toString() +" - "+ cursor.getString(cursor.getColumnIndex(FLD_PRODUCT_NAMA)).toString()+" - "+ cursor.getString(cursor.getColumnIndex(ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA)).toString()  ;
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

            String sql = "SELECT * FROM " + TBL_PRODUCT;
            sql = sql + " WHERE " + FLD_PRODUCT_NAMA + " = '" + nama + "'";

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar = cursor.getLong(cursor.getColumnIndex(FLD_PRODUCT_ID)) ;
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

            String sql = "SELECT * FROM " + TBL_PRODUCT;
            sql = sql + " WHERE " + FLD_PRODUCT_ID + " = " + id;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar = "" + cursor.getString(cursor.getColumnIndex(FLD_PRODUCT_NAMA)).toString() ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }

    public static long getProductTypeById(DataHelper dbHelper, long id) {

        long daftar = 0 ;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String sql = "SELECT * FROM " + TBL_PRODUCT;
            sql = sql + " WHERE " + FLD_PRODUCT_ID + " = " + id;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar =  cursor.getLong(cursor.getColumnIndex(FLD_PRODUCT_TYPE_ID)) ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return daftar;
    }

    public static String getProductTypeNameById(DataHelper dbHelper, long id) {

        String daftar = "" ;
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String sql = "SELECT "+ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA + " FROM "+ProductTypeDAO.TBL_PRODUCT_TYPE + " AS pt " +
                    " INNER JOIN "+ProductDAO.TBL_PRODUCT + " AS p ON (pt."+ProductTypeDAO.FLD_PRODUCT_TYPE_ID+" = p."+FLD_PRODUCT_TYPE_ID+" )" ;

            sql = sql + " WHERE " + FLD_PRODUCT_ID + " = " + id;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar =  cursor.getString(cursor.getColumnIndex(ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA)) ;
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

            String sql = "SELECT * FROM " + TBL_PRODUCT;
            sql = sql + " WHERE " + FLD_PRODUCT_ID + " = " + id;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar = "" + cursor.getString(cursor.getColumnIndex(FLD_PRODUCT_NAMA)).toString() ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }

    public static void resultToObject(Cursor cursor, ProductEntity productEntity) {

        try {
            productEntity.setIdProduct(cursor.getLong(cursor.getColumnIndex(FLD_PRODUCT_ID)));
            productEntity.setNoProduct(cursor.getString(cursor.getColumnIndex(FLD_PRODUCT_NO)));
            productEntity.setIdProduct(cursor.getLong(cursor.getColumnIndex(FLD_PRODUCT_ID)));
            productEntity.setNamaProduct(cursor.getString(cursor.getColumnIndex(FLD_PRODUCT_NAMA)));
            productEntity.setIdProductType(cursor.getLong(cursor.getColumnIndex(FLD_PRODUCT_TYPE_ID)));
            productEntity.setProductStatusBB(cursor.getLong(cursor.getColumnIndex(FLD_PRODUCT_STATUS_BB)));
            productEntity.setProductStatusSDM(cursor.getLong(cursor.getColumnIndex(FLD_PRODUCT_STATUS_SDM)));
            productEntity.setProductStatusPEM(cursor.getLong(cursor.getColumnIndex(FLD_PRODUCT_STATUS_PEM)));
            productEntity.setProductStatusPP(cursor.getLong(cursor.getColumnIndex(FLD_PRODUCT_STATUS_PP)));
            productEntity.setProductStatusNPV(cursor.getLong(cursor.getColumnIndex(FLD_PRODUCT_STATUS_NPV)));
            productEntity.setProductStatusPI(cursor.getLong(cursor.getColumnIndex(FLD_PRODUCT_STATUS_PI)));
        } catch (Exception e) {
        }

    }


}