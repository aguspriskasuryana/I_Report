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
public class NetPresentValueDAO {


    protected Cursor cursor;

    public static final String TBL_NETPRESENTVALUE = "NETPRESENTVALUE";
    public static final String FLD_NETPRESENTVALUE_ID = "NETPRESENTVALUE_ID";
    public static final String FLD_NILAI_INVESTASI_TOTAL = "NILAI_INVESTASI_TOTAL";
    public static final String FLD_NILAI_PROCEED_PERTAHUN = "NILAI_PROCEED_PERTAHUN";
    public static final String FLD_NILAI_RATE_PERSEN = "NILAI_RATE_PERSEN";
    public static final String FLD_JUMLAH_PERIODE = "JUMLAH_PERIODE";
    public static final String FLD_PRODUCT_ID = "PRODUCT_ID";
    public static final String FLD_PRODUCT_STATUS_NPV = "FLD_PRODUCT_STATUS_NPV";
    public static final String FLD_PRODUCT_STATUS_PI = "FLD_PRODUCT_STATUS_PI";

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;

    public static NetPresentValueEntity getById(DataHelper dbHelper , long oid) {

        NetPresentValueEntity netPresentValueEntity = new NetPresentValueEntity();
        Cursor cursor;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM " + TBL_NETPRESENTVALUE + " WHERE " + FLD_NETPRESENTVALUE_ID + " = " + oid;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            resultToObject(cursor,netPresentValueEntity);

            return netPresentValueEntity;
        } catch (Exception e) {
            System.out.println(e);
        }
        return netPresentValueEntity;
    }

    public static long getStatusNPVById(DataHelper dbHelper, long productid) {

        long status =0;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String sql = "SELECT * FROM " + TBL_NETPRESENTVALUE;
            sql = sql + " WHERE " + FLD_PRODUCT_ID + " = " + productid;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                status = cursor.getLong(cursor.getColumnIndex(FLD_PRODUCT_STATUS_NPV)) ;
            }

            return status;
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public static long getStatusPIById(DataHelper dbHelper, long productid) {

        long status =0;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String sql = "SELECT * FROM " + TBL_NETPRESENTVALUE;
            sql = sql + " WHERE " + FLD_PRODUCT_ID + " = " + productid;

            cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                status = cursor.getLong(cursor.getColumnIndex(FLD_PRODUCT_STATUS_PI)) ;
            }

            return status;
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public static int add(DataHelper dbHelper ,NetPresentValueEntity netpresentvalueEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();
        try {
            String sql = "insert into "+TBL_NETPRESENTVALUE+" (" +
                    FLD_NILAI_INVESTASI_TOTAL +", " +
                    FLD_NILAI_PROCEED_PERTAHUN +", " +
                    FLD_NILAI_RATE_PERSEN +", " +
                    FLD_JUMLAH_PERIODE +", " +
                    FLD_PRODUCT_ID +" " +
                    " ) values ( " +
                    " '" + netpresentvalueEntity.getNilaiInvestasitotal() + "', " +
                    " '" + netpresentvalueEntity.getNilaiProceedPerTahun() + "', " +
                    " '" + netpresentvalueEntity.getNilairatepersen()+ "', " +
                    " '" + netpresentvalueEntity.getJumlahperiode()+ "', " +
                    " '" + netpresentvalueEntity.getProductid()+ "' " +
                    " )";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int update(DataHelper dbHelper ,NetPresentValueEntity netpresentvalueEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = " update "+TBL_NETPRESENTVALUE+" set " +
                    FLD_NILAI_INVESTASI_TOTAL +" = '" + netpresentvalueEntity.getNilaiInvestasitotal() + "', "+
                    FLD_NILAI_PROCEED_PERTAHUN +" = '" + netpresentvalueEntity.getNilaiProceedPerTahun() +"', "+
                    FLD_NILAI_RATE_PERSEN +" = '" + netpresentvalueEntity.getNilairatepersen() +"', "+
                    FLD_JUMLAH_PERIODE +" = '" + netpresentvalueEntity.getJumlahperiode() +"', "+
                    FLD_PRODUCT_ID+" = '" + netpresentvalueEntity.getProductid() +"', "+
                    FLD_PRODUCT_STATUS_NPV+" = '" + netpresentvalueEntity.getProductstatusnpv() +"', "+
                    FLD_PRODUCT_STATUS_PI+" = '" + netpresentvalueEntity.getProductstatuspi() +"' "+
                    " where " + FLD_NETPRESENTVALUE_ID + " = '" + netpresentvalueEntity.getIdNetPresentValue() + "' "+
                    " AND " + FLD_PRODUCT_ID + " = '" + netpresentvalueEntity.getProductid() + "' ";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int updatealltotalinvestasirateperiode(DataHelper dbHelper ,NetPresentValueEntity netpresentvalueEntity){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = " update "+TBL_NETPRESENTVALUE+" set " +
                    FLD_NILAI_INVESTASI_TOTAL +" = '" + netpresentvalueEntity.getNilaiInvestasitotal() + "', "+
                    FLD_JUMLAH_PERIODE +" = '" + netpresentvalueEntity.getJumlahperiode() +"', "+
                    FLD_NILAI_RATE_PERSEN +" = '" + netpresentvalueEntity.getNilairatepersen() +"', "+
                    " where " + FLD_PRODUCT_ID + " = '" + netpresentvalueEntity.getProductid() + "' ";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static int delete(DataHelper dbHelper ,NetPresentValueEntity netpresentvalueEntity){
        int success = RSLT_OK ;
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = "delete from " + TBL_NETPRESENTVALUE + " where " + FLD_NETPRESENTVALUE_ID +" = " + netpresentvalueEntity.getIdNetPresentValue() + " ";

            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }

    public static double getpvifa(double rateval, long jumlahperiod){
        int success = RSLT_OK ;


        //For example, to calculate the PVIFA for interest rate 4% and a period of 15, you would plugin the numbers to the PVIFA formula as follows
        //PVIFA = (1-(1+0.04)^-15)/0.04) = 11.1184
        double valPVIFA =  0;
        double ratevalpersen = rateval/100;
        double nilaipangkat= Math.pow((1+ratevalpersen), (0-jumlahperiod));
        try {
            valPVIFA = (1-nilaipangkat)/ratevalpersen ;

        } catch (SQLiteException e) {
            e.printStackTrace();
            valPVIFA = RSLT_UNKNOWN_ERROR;
        }
        return valPVIFA;
    }

    public static Vector getList(DataHelper dbHelper ,int limitStart, int recordToGet, String whereClause, String order) {

        Vector lists = new Vector();
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String sql = "SELECT * FROM " + TBL_NETPRESENTVALUE;

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
                NetPresentValueEntity netpresentvalueEntity = new NetPresentValueEntity();
                resultToObject(cursor,netpresentvalueEntity);
                lists.add(netpresentvalueEntity);
            }

            return lists;
        } catch (Exception e) {
            System.out.println(e);
        }
        return lists;
    }



    public static String[]  getListArrayNetPresentValue(DataHelper dbHelper,  int limitStart, int recordToGet, String whereClause, String order) {

        String[] daftar;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();
            SQLiteDatabase dbw = dbHelper.getWritableDatabase();

            String sql = "SELECT * FROM " + TBL_NETPRESENTVALUE  ;

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
            double ratepersen=0;
            long jumlahperiodtotal=0;
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                int proceedke = cc+1;

                //mencarinilaipvifa
                jumlahperiodtotal =  cursor.getLong(cursor.getColumnIndex(FLD_JUMLAH_PERIODE)) ;
                ratepersen =  cursor.getDouble(cursor.getColumnIndex(FLD_NILAI_RATE_PERSEN)) ;
                double nilaipvifaperiodeini = getpvifa(ratepersen,proceedke);
                double nilaisukubunga = nilaipvifaperiodeini;
                if (proceedke >1){
                    double nilaipvifaperiodesebelumnya = getpvifa(ratepersen,(proceedke-1));
                    nilaisukubunga = nilaipvifaperiodeini -nilaipvifaperiodesebelumnya;
                }

                Locale myIndonesianLocale = new Locale("in", "ID");
                NumberFormat formatKurensi = NumberFormat.getCurrencyInstance(myIndonesianLocale);
                double nilaiproceed = cursor.getDouble(cursor.getColumnIndex(FLD_NILAI_PROCEED_PERTAHUN));
                String valpro =  formatKurensi.format(cursor.getDouble(cursor.getColumnIndex(FLD_NILAI_PROCEED_PERTAHUN)));
                String valproceedkumulatif =   String.format("%.4f", nilaisukubunga);
                String setelahsukubunga =  formatKurensi.format(nilaiproceed*nilaisukubunga) ;
                daftar[cc] = " Th. " + proceedke +" Arus Kas " + valpro  + "| Sukubunga = "+ valproceedkumulatif+ "| Nilai Setelah Bunga = "+ (setelahsukubunga) ;
            }

            return daftar;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new String[0];
    }

    public static double  getListArrayNetPresentValuetotalpv(DataHelper dbHelper,  int limitStart, int recordToGet, String whereClause, String order) {

        double Valuetotalpv;
        Cursor cursor;
        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();
            SQLiteDatabase dbw = dbHelper.getWritableDatabase();

            String sql = "SELECT * FROM " + TBL_NETPRESENTVALUE  ;

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
            Valuetotalpv = 0;

            cursor.moveToFirst();
            double ratepersen=0;
            long jumlahperiodtotal=0;
            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                int proceedke = cc+1;

                //mencarinilaipvifa
                jumlahperiodtotal =  cursor.getLong(cursor.getColumnIndex(FLD_JUMLAH_PERIODE)) ;
                ratepersen =  cursor.getDouble(cursor.getColumnIndex(FLD_NILAI_RATE_PERSEN)) ;
                double nilaipvifaperiodeini = getpvifa(ratepersen,proceedke);
                double nilaisukubunga = nilaipvifaperiodeini;
                if (proceedke >1){
                    double nilaipvifaperiodesebelumnya = getpvifa(ratepersen,(proceedke-1));
                    nilaisukubunga = nilaipvifaperiodeini -nilaipvifaperiodesebelumnya;
                }

                Locale myIndonesianLocale = new Locale("in", "ID");
                NumberFormat formatKurensi = NumberFormat.getCurrencyInstance(myIndonesianLocale);
                double nilaiproceed = cursor.getDouble(cursor.getColumnIndex(FLD_NILAI_PROCEED_PERTAHUN));
                String valpro =  formatKurensi.format(cursor.getDouble(cursor.getColumnIndex(FLD_NILAI_PROCEED_PERTAHUN)));
                String valproceedkumulatif =   String.format("%.4f", nilaisukubunga);
                String setelahsukubunga =  formatKurensi.format(nilaiproceed*nilaisukubunga) ;
                Valuetotalpv = Valuetotalpv+(nilaiproceed*nilaisukubunga);
            }

            return Valuetotalpv;
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public static int updatestatus(DataHelper dbHelper , long productid, long statusNPV, long statusPI){
        int success = RSLT_OK ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            String sql = " update "+TBL_NETPRESENTVALUE+" set " +
                    FLD_PRODUCT_STATUS_NPV +" = '" + statusNPV +"', "+
                    FLD_PRODUCT_STATUS_PI +" = '" + statusPI +"' "+
                    " where  "+ FLD_PRODUCT_ID + " = '" + productid + "' ";
            dbw.execSQL(sql);
            success = RSLT_OK;
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = RSLT_UNKNOWN_ERROR;
        }
        return success;
    }


    public static void resultToObject(Cursor cursor, NetPresentValueEntity netpresentvalueEntity) {

        try {
            netpresentvalueEntity.setIdNetPresentValue(cursor.getLong(cursor.getColumnIndex(FLD_NETPRESENTVALUE_ID)));
            netpresentvalueEntity.setNilaiInvestasitotal(cursor.getLong(cursor.getColumnIndex(FLD_NILAI_INVESTASI_TOTAL)));
            netpresentvalueEntity.setNilaiProceedPerTahun(cursor.getLong(cursor.getColumnIndex(FLD_NILAI_PROCEED_PERTAHUN)));
            netpresentvalueEntity.setNilairatepersen(cursor.getLong(cursor.getColumnIndex(FLD_NILAI_RATE_PERSEN)));
            netpresentvalueEntity.setJumlahperiode(cursor.getLong(cursor.getColumnIndex(FLD_JUMLAH_PERIODE)));
            netpresentvalueEntity.setProductid(cursor.getLong(cursor.getColumnIndex(FLD_PRODUCT_ID)));
            netpresentvalueEntity.setProductstatusnpv(cursor.getLong(cursor.getColumnIndex(FLD_PRODUCT_STATUS_NPV)));
            netpresentvalueEntity.setProductstatuspi(cursor.getLong(cursor.getColumnIndex(FLD_PRODUCT_STATUS_PI)));

        } catch (Exception e) {
        }

    }


}