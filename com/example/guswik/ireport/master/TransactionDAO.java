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
public class TransactionDAO {


    protected Cursor cursor;

    public static final String TBL_TRANSACTION = "trans";
    public static final String FLD_TRANSACTION_ID = "TRANSACTION_ID";
    public static final String FLD_TRANSACTION_NO = "TRANSACTION_NO";
    public static final String FLD_AKUN_ID = "AKUN_ID";
    public static final String FLD_TRANSACTION_NOTE = "TRANSACTION_NOTE";
    public static final String FLD_TRANSACTION_VALUE = "TRANSACTION_VALUE";
    public static final String FLD_TRANSACTION_TYPE = "TRANSACTION_TYPE";
    public static final String FLD_TRANSACTION_DATE = "TRANSACTION_DATE";

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;

    public static int DEBIT  = 0;
    public static int KREDIT = 1;

    public static TransactionEntity getById(DataHelper dbHelper , long oid) {

        TransactionEntity transactionEntity = new TransactionEntity();
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM " + TBL_TRANSACTION + " WHERE " + FLD_TRANSACTION_ID + " = " + oid;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            resultToObject(cursor,transactionEntity);

            return transactionEntity;
        } catch (Exception e) {
            System.out.println(e);
        }
        return transactionEntity;
    }

    public static long getAkunIdByNoAndDebit(DataHelper dbHelper , String no, int typetransaction) {

        long akunid = 0;
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT "+FLD_AKUN_ID+" FROM " + TBL_TRANSACTION + " WHERE " + FLD_TRANSACTION_NO + " = '" + no +"' AND "+FLD_TRANSACTION_TYPE+" = "+typetransaction+" AND "+FLD_AKUN_ID+" != 19";

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();

            akunid = cursor.getLong(cursor.getColumnIndex(FLD_AKUN_ID));


            return akunid;
        } catch (Exception e) {
            System.out.println(e);
        }
        return akunid;
    }

    public static TransactionEntity getByNo(DataHelper dbHelper , String no) {

        TransactionEntity transactionEntity = new TransactionEntity();
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM " + TBL_TRANSACTION + " WHERE " + FLD_TRANSACTION_NO + " = '" + no +"' ";

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            resultToObject(cursor,transactionEntity);

            return transactionEntity;
        } catch (Exception e) {
            System.out.println(e);
        }
        return transactionEntity;
    }

    public static boolean getByNoAkunTersedia(DataHelper dbHelper , String noAkun) {

        boolean ada= false;
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM " + TBL_TRANSACTION + " WHERE " + FLD_TRANSACTION_NO + " = '" + noAkun +"' ";

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            if (cursor.getCount()>0){
                ada = true;
            }

            return ada;
        } catch (Exception e) {
            System.out.println(e);
        }
        return ada;
    }

    public static int add(DataHelper dbHelper ,TransactionEntity transactionEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();
        try {
            String sql = "insert into "+TBL_TRANSACTION+" (" +
                    FLD_TRANSACTION_NO +", " +
                    FLD_AKUN_ID +", " +
                    FLD_TRANSACTION_NOTE +", " +
                    FLD_TRANSACTION_VALUE +", " +
                    FLD_TRANSACTION_TYPE +", " +
                    FLD_TRANSACTION_DATE +"  " +
                    " ) values ( " +
                    " '" + transactionEntity.getNoTransaction() + "', " +
                    " '" + transactionEntity.getIdAkun() + "', " +
                    " '" + transactionEntity.getNoteTransaction() + "', " +
                    " '" + transactionEntity.getValueTransaction() + "', " +
                    " '" + transactionEntity.getTypeTransaction() + "', " +
                    " '" + Formater.getStringDate(transactionEntity.getDateTransaction())  + "' " +
                    " )";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int update(DataHelper dbHelper ,TransactionEntity transactionEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = " update "+TBL_TRANSACTION+" set " +
                    FLD_TRANSACTION_NO +" = '" + transactionEntity.getNoTransaction() + "', "+
                    FLD_AKUN_ID +" = '" + transactionEntity.getIdAkun() +"', "+
                    FLD_TRANSACTION_NOTE +" = '" + transactionEntity.getNoteTransaction() +"', "+
                    FLD_TRANSACTION_VALUE +" = '" + transactionEntity.getValueTransaction() +"', "+
                    FLD_TRANSACTION_TYPE +" = '" + transactionEntity.getTypeTransaction() +"', "+
                    FLD_TRANSACTION_DATE +" = '" + Formater.getStringDate(transactionEntity.getDateTransaction()) +"' "+
                    " where " + FLD_TRANSACTION_ID + " = '" + transactionEntity.getIdTransaction() + "' ";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int delete(DataHelper dbHelper ,TransactionEntity transactionEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = "delete from " + TBL_TRANSACTION + " where " + FLD_TRANSACTION_ID +" = '" + transactionEntity.getIdTransaction() + "' ";

            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int deleteByNoTrans(DataHelper dbHelper ,String noTrans){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = "delete from " + TBL_TRANSACTION + " where " + FLD_TRANSACTION_NO +" = '" + noTrans + "' ";

            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int deleteByNoTransandAkunId(DataHelper dbHelper ,String noTrans,long akunId){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = "delete from " + TBL_TRANSACTION + " where " + FLD_TRANSACTION_NO +" = '" + noTrans + "' AND " + FLD_AKUN_ID +" = '" + akunId + "'";

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

            String sql = "SELECT * FROM " + TBL_TRANSACTION;

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
                TransactionEntity transactionEntity = new TransactionEntity();
                resultToObject(cursor,transactionEntity);
                lists.add(transactionEntity);
            }

            return lists;
        } catch (Exception e) {
            System.out.println(e);
        }
        return lists;
    }

    public static Vector getListDistinctNo(DataHelper dbHelper ,int limitStart, int recordToGet, String whereClause, String order) {

        Vector lists = new Vector();
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();
            SQLiteDatabase dbw = dbHelper.getWritableDatabase();

            String sql = "SELECT DISTINCT("+FLD_TRANSACTION_NO+") FROM " + TBL_TRANSACTION;

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

                TransactionEntity transactionEntity = new TransactionEntity();
                try{
                    transactionEntity = TransactionDAO.getByNo(dbHelper,cursor.getString(cursor.getColumnIndex(FLD_TRANSACTION_NO)).toString());
                }catch (Exception e ){}
                lists.add(transactionEntity);
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
            SQLiteDatabase dbw = dbHelper.getWritableDatabase();

            String sql = " SELECT TRANS."+FLD_TRANSACTION_DATE+", TRANS."+FLD_TRANSACTION_VALUE+", AKUN."+AkunDAO.FLD_AKUN_NAMA+" FROM " + TBL_TRANSACTION + " AS TRANS " +
                         " INNER JOIN "+AkunDAO.TBL_AKUN + " AS AKUN ON (TRANS."+FLD_AKUN_ID+" = AKUN."+FLD_AKUN_ID+" )" ;

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
                daftar[cc] = "" + cursor.getString(cursor.getColumnIndex(FLD_TRANSACTION_DATE)).toString() +" : "+ cursor.getString(cursor.getColumnIndex(AkunDAO.FLD_AKUN_NAMA)).toString()+" : "+ cursor.getString(cursor.getColumnIndex(FLD_TRANSACTION_VALUE)).toString()  ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new String[0];
    }


    public static String[]  getListArray2(DataHelper dbHelper,  int limitStart, int recordToGet, String whereClause, String order) {

        String[] daftar;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();
            SQLiteDatabase dbw = dbHelper.getWritableDatabase();

            String sql = " SELECT DISTINCT(TRANS."+FLD_TRANSACTION_NO+"), TRANS."+FLD_TRANSACTION_NO+", TRANS."+FLD_TRANSACTION_DATE+",  TRANS."+FLD_TRANSACTION_NOTE+", AKUN."+AkunDAO.FLD_AKUN_NAMA+" FROM " + TBL_TRANSACTION + " AS TRANS " ;

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
                daftar[cc] = "" + cursor.getString(cursor.getColumnIndex(FLD_TRANSACTION_DATE)).toString() +" | NO : "+ cursor.getString(cursor.getColumnIndex(FLD_TRANSACTION_NO)).toString()+" - "+ cursor.getString(cursor.getColumnIndex(FLD_TRANSACTION_NOTE)).toString()  ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new String[0];
    }

    public static String[]  getListArray3(DataHelper dbHelper,  int limitStart, int recordToGet, String whereClause, String order) {

        String[] daftar;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();
            SQLiteDatabase dbw = dbHelper.getWritableDatabase();

            String sql = " SELECT DISTINCT(TRANS."+FLD_TRANSACTION_NO+") FROM " + TBL_TRANSACTION + " AS TRANS " ;

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
                TransactionEntity transactionEntity = new TransactionEntity();
                try{
                    transactionEntity = TransactionDAO.getByNo(dbHelper,cursor.getString(cursor.getColumnIndex(FLD_TRANSACTION_NO)).toString());
                }catch (Exception e ){}

                daftar[cc] = "" + Formater.getStringDate(transactionEntity.getDateTransaction()) +" | NO : "+ transactionEntity.getNoTransaction()+" - "+ transactionEntity.getNoteTransaction()  ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new String[0];
    }



    public static String[]  getListArrayNoTransaksiuntukJurnal(DataHelper dbHelper,  int limitStart, int recordToGet, String whereClause, String order) {

        String[] daftar;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String sql = " SELECT TRANS."+FLD_TRANSACTION_NO+
                               ", AKUN."+AkunDAO.FLD_AKUN_NAMA+
                               ", AKUN."+AkunDAO.FLD_AKUN_NO+
                               ", TRANS."+FLD_TRANSACTION_TYPE+
                               ", TRANS."+FLD_TRANSACTION_DATE+
                               ", TRANS."+FLD_TRANSACTION_VALUE+
                                ", TRANS."+FLD_AKUN_ID+
                               " FROM " + TBL_TRANSACTION + " AS TRANS " +
                         " INNER JOIN "+AkunDAO.TBL_AKUN + " AS AKUN ON (TRANS."+FLD_AKUN_ID+" = AKUN."+FLD_AKUN_ID+" )" ;

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

                sql = sql + " ORDER BY TRANS."+FLD_TRANSACTION_NO + ", TRANS."+FLD_TRANSACTION_TYPE + " ASC ";

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
                daftar[cc] = " "+ cursor.getString(cursor.getColumnIndex(FLD_TRANSACTION_NO)).toString() +
                             ":"+ cursor.getString(cursor.getColumnIndex(AkunDAO.FLD_AKUN_NAMA)).toString()+
                             ":"+ cursor.getString(cursor.getColumnIndex(AkunDAO.FLD_AKUN_NO)).toString()+
                             ":"+ cursor.getString(cursor.getColumnIndex(FLD_TRANSACTION_TYPE)).toString()+
                             ":"+ cursor.getString(cursor.getColumnIndex(FLD_TRANSACTION_VALUE)).toString()+
                             ":"+ cursor.getString(cursor.getColumnIndex(FLD_TRANSACTION_DATE)).toString()  ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new String[0];
    }

    public static String[]  getListArrayNoTransaksiuntukBukuBesar(DataHelper dbHelper,  int limitStart, int recordToGet, String whereClause, String order) {

        String[] daftar;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String sql = " SELECT TRANS."+FLD_TRANSACTION_NO+
                    ", AKUN."+AkunDAO.FLD_AKUN_NAMA+
                    ", AKUN."+AkunDAO.FLD_AKUN_NO+
                    ", TRANS."+FLD_TRANSACTION_DATE+
                    ", TRANS."+FLD_TRANSACTION_TYPE+
                    ", TRANS."+FLD_TRANSACTION_NOTE+
                    ", TRANS."+FLD_TRANSACTION_VALUE+
                    " FROM " + TBL_TRANSACTION + " AS TRANS " +
                    " INNER JOIN "+AkunDAO.TBL_AKUN + " AS AKUN ON (TRANS."+FLD_AKUN_ID+" = AKUN."+FLD_AKUN_ID+" )" ;

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            sql = sql + " ORDER BY TRANS."+FLD_TRANSACTION_NO + ", TRANS."+FLD_TRANSACTION_TYPE + " ASC ";

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
                daftar[cc] = " "+ cursor.getString(cursor.getColumnIndex(FLD_TRANSACTION_NO)).toString() +
                        ":"+ cursor.getString(cursor.getColumnIndex(FLD_TRANSACTION_NOTE)).toString()+
                        ":"+ cursor.getString(cursor.getColumnIndex(FLD_TRANSACTION_DATE)).toString()+
                        ":"+ cursor.getString(cursor.getColumnIndex(AkunDAO.FLD_AKUN_NO)).toString()+
                        ":"+ cursor.getString(cursor.getColumnIndex(FLD_TRANSACTION_TYPE)).toString()+
                        ":"+ cursor.getString(cursor.getColumnIndex(FLD_TRANSACTION_VALUE)).toString()  ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new String[0];
    }

    public static double  getValueNeracaSaldo(DataHelper dbHelper,long akunId , Date startDate,Date endDate) {

        double daftar = 0;
        Cursor cursor,cursor2;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();

            //String sql = " SELECT ((SELECT SUM("+FLD_TRANSACTION_VALUE+") FROM "+TBL_TRANSACTION+" WHERE "+FLD_TRANSACTION_TYPE+" = 0 AND ("+FLD_TRANSACTION_DATE+" BETWEEN '" +Formater.getStringDate(startDate)+ "' AND '" +Formater.getStringDate(endDate)+ "' ) ) - (SELECT SUM("+FLD_TRANSACTION_VALUE+") FROM "+TBL_TRANSACTION+" WHERE "+FLD_TRANSACTION_TYPE+" = 1 AND ("+FLD_TRANSACTION_DATE+" BETWEEN '" +Formater.getStringDate(startDate)+ "' AND '" +Formater.getStringDate(endDate)+ "' ) )) AS NILAI;";

            String sql = " SELECT SUM("+FLD_TRANSACTION_VALUE+") AS NILAI FROM "+TBL_TRANSACTION+" WHERE "+FLD_AKUN_ID+" = "+akunId+" AND "+FLD_TRANSACTION_TYPE+" = 0 AND ("+FLD_TRANSACTION_DATE+" BETWEEN '" +Formater.getStringDate(startDate)+ "' AND '" +Formater.getStringDate(endDate)+ "' ) ;";

            cursor = db.rawQuery(sql,null);

            cursor.moveToFirst();
            cursor.moveToPosition(0);
            daftar = cursor.getLong(cursor.getColumnIndex("NILAI"));


            String sql2 = " SELECT SUM("+FLD_TRANSACTION_VALUE+") AS NILAI FROM "+TBL_TRANSACTION+" WHERE "+FLD_AKUN_ID+" = "+akunId+" AND "+FLD_TRANSACTION_TYPE+" = 1 AND ("+FLD_TRANSACTION_DATE+" BETWEEN '" +Formater.getStringDate(startDate)+ "' AND '" +Formater.getStringDate(endDate)+ "' ) ;";

            cursor2 = db.rawQuery(sql2,null);

            cursor2.moveToFirst();
            cursor2.moveToPosition(0);

            daftar = daftar - (cursor2.getLong(cursor.getColumnIndex("NILAI")));


            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return daftar;
    }

    public static double  getValueNeraca(DataHelper dbHelper,long akunId) {

        double daftar = 0;
        Cursor cursor,cursor2;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();

            //String sql = " SELECT ((SELECT SUM("+FLD_TRANSACTION_VALUE+") FROM "+TBL_TRANSACTION+" WHERE "+FLD_TRANSACTION_TYPE+" = 0 AND ("+FLD_TRANSACTION_DATE+" BETWEEN '" +Formater.getStringDate(startDate)+ "' AND '" +Formater.getStringDate(endDate)+ "' ) ) - (SELECT SUM("+FLD_TRANSACTION_VALUE+") FROM "+TBL_TRANSACTION+" WHERE "+FLD_TRANSACTION_TYPE+" = 1 AND ("+FLD_TRANSACTION_DATE+" BETWEEN '" +Formater.getStringDate(startDate)+ "' AND '" +Formater.getStringDate(endDate)+ "' ) )) AS NILAI;";

            String sql = " SELECT SUM("+FLD_TRANSACTION_VALUE+") AS NILAI FROM "+TBL_TRANSACTION+" WHERE "+FLD_AKUN_ID+" = "+akunId+" AND "+FLD_TRANSACTION_TYPE+" = 0 ;";

            cursor = db.rawQuery(sql,null);

            cursor.moveToFirst();
            cursor.moveToPosition(0);
            String xx = cursor.getString(cursor.getColumnIndex("NILAI"));
            if (xx != null){
                daftar = Double.parseDouble(xx);
            }
            //daftar = cursor.getDouble(cursor.getColumnIndex("NILAI"));


            String sql2 = " SELECT SUM("+FLD_TRANSACTION_VALUE+") AS NILAI FROM "+TBL_TRANSACTION+" WHERE "+FLD_AKUN_ID+" = "+akunId+" AND "+FLD_TRANSACTION_TYPE+" = 1 ;";

            cursor2 = db.rawQuery(sql2,null);

            cursor2.moveToFirst();
            cursor2.moveToPosition(0);


            String xx2 = cursor2.getString(cursor.getColumnIndex("NILAI"));

            double daftar2 =0;
            if (xx2 != null){
                daftar2 = Double.parseDouble(xx2);
            }
            daftar = daftar - daftar2;


            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return daftar;
    }


    public static boolean  getKasMinus(DataHelper dbHelper, double nilaiKasYangakanMasuk) {

        boolean status = false;
        Cursor cursor;

        String[] daftar = AkunDAO.getListArrayOnlyId(dbHelper, 0, 0, ""+AkunDAO.FLD_AKUN_ID + " = " + 1, ""+AkunDAO.FLD_AKUN_NO);

        try {

            double totalKas = 0;
            for (int i =0; i<daftar.length; i++ ) {

                String dataString = daftar[i];
                long akunId = 0;
                try {
                    akunId = Long.parseLong(dataString);
                } catch (Exception e) {
                }

                double value = TransactionDAO.getValueNeraca(dbHelper, akunId);
                totalKas += value;
            }
            totalKas += nilaiKasYangakanMasuk;

            if (totalKas < 0){

                status = true;
            } else {
                status = false;
            }
            return status;
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }


    public static long  getNewNoTrans(DataHelper dbHelper) {

        long newNo = 0;
        Cursor cursor,cursor2;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String sql = " SELECT MAX("+FLD_TRANSACTION_NO+")+1 AS NEWNO FROM "+TBL_TRANSACTION + " ;";

            cursor = db.rawQuery(sql,null);

            cursor.moveToFirst();
            cursor.moveToPosition(0);
            newNo = cursor.getLong(cursor.getColumnIndex("NEWNO"));


            return newNo;
        } catch (Exception e) {
            System.out.println(e);
        }
        return newNo;
    }


    public static void resultToObject(Cursor cursor, TransactionEntity transactionEntity) {

        try {
            transactionEntity.setIdTransaction(cursor.getLong(cursor.getColumnIndex(FLD_TRANSACTION_ID)));
            transactionEntity.setNoTransaction(cursor.getString(cursor.getColumnIndex(FLD_TRANSACTION_NO)));
            transactionEntity.setIdAkun(cursor.getLong(cursor.getColumnIndex(FLD_AKUN_ID)));
            transactionEntity.setNoteTransaction(cursor.getString(cursor.getColumnIndex(FLD_TRANSACTION_NOTE)));
            transactionEntity.setValueTransaction(cursor.getLong(cursor.getColumnIndex(FLD_TRANSACTION_VALUE)));
            transactionEntity.setTypeTransaction(cursor.getInt(cursor.getColumnIndex(FLD_TRANSACTION_TYPE)));
            transactionEntity.setDateTransaction(Formater.getDateFromString(cursor.getString(cursor.getColumnIndex(FLD_TRANSACTION_DATE))));

        } catch (Exception e) {
        }

    }


}
