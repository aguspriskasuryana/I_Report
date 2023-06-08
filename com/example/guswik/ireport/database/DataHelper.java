package com.example.guswik.ireport.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.guswik.ireport.master.AkunDAO;
import com.example.guswik.ireport.master.AkunTypeDAO;
import com.example.guswik.ireport.master.AnalysisDAO;
import com.example.guswik.ireport.master.BmcDAO;
import com.example.guswik.ireport.master.CompanyDAO;
import com.example.guswik.ireport.master.DepresiasiDAO;
import com.example.guswik.ireport.master.DetailPerhitunganBiayaDAO;
import com.example.guswik.ireport.master.DetailPerhitunganInvestasiDAO;
import com.example.guswik.ireport.master.DetailPerhitunganPendapatanDAO;
import com.example.guswik.ireport.master.NetPresentValueDAO;
import com.example.guswik.ireport.master.PaybackPeriodDAO;
import com.example.guswik.ireport.master.PerhitunganInvestasiDAO;
import com.example.guswik.ireport.master.ProductDAO;
import com.example.guswik.ireport.master.ProductTypeDAO;
import com.example.guswik.ireport.master.SwotDAO;
import com.example.guswik.ireport.master.TransactionDAO;

/**
 * Created by GUSWIK on 7/11/2018.
 */

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bumdesxxx123.db";
    private static final int DATABASE_VERSION = 1;
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String sql = "";

        sql = "create table " + ProductTypeDAO.TBL_PRODUCT_TYPE + " (" +
                ProductTypeDAO.FLD_PRODUCT_TYPE_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT, " +
                ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA + " text null " +
                ");";

        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);

        sql = "INSERT INTO " + ProductTypeDAO.TBL_PRODUCT_TYPE + " (" + ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA +  ") VALUES ('PERTANIAN, KEHUTANAN DAN PERIKANAN');";
        db.execSQL(sql);

        sql = "INSERT INTO " + ProductTypeDAO.TBL_PRODUCT_TYPE + " (" + ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA +  ") VALUES ('PERTAMBANGAN DAN PENGGALIAN');";
        db.execSQL(sql);

        sql = "INSERT INTO " + ProductTypeDAO.TBL_PRODUCT_TYPE + " (" + ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA +  ") VALUES ('INDUSTRI PENGOLAHAN');";
        db.execSQL(sql);

        sql = "INSERT INTO " + ProductTypeDAO.TBL_PRODUCT_TYPE + " (" + ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA +  ") VALUES ('PENGADAAN LISTRIK, GAS, UAP/AIR PANAS DAN UDARA DINGIN');";
        db.execSQL(sql);

        sql = "INSERT INTO " + ProductTypeDAO.TBL_PRODUCT_TYPE + " (" + ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA +  ") VALUES ('TREATMENT AIR, TREATMENT AIR LIMBAH, TREATMENT DAN PEMULIHAN MATERIAL SAMPAH, DAN AKTIVITAS REMEDIASI');";
        db.execSQL(sql);

        sql = "INSERT INTO " + ProductTypeDAO.TBL_PRODUCT_TYPE + " (" + ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA +  ") VALUES ('KONSTRUKSI');";
        db.execSQL(sql);

        sql = "INSERT INTO " + ProductTypeDAO.TBL_PRODUCT_TYPE + " (" + ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA +  ") VALUES ('PERDAGANGAN BESAR DAN ECERAN; REPARASI DAN PERAWATAN MOBIL DAN SEPEDA MOTOR');";
        db.execSQL(sql);

        sql = "INSERT INTO " + ProductTypeDAO.TBL_PRODUCT_TYPE + " (" + ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA +  ") VALUES ('PENGANGKUTAN DAN PERGUDANGAN');";
        db.execSQL(sql);

        sql = "INSERT INTO " + ProductTypeDAO.TBL_PRODUCT_TYPE + " (" + ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA +  ") VALUES ('PENYEDIAAN AKOMODASI DAN PENYEDIAAN MAKAN MINUM');";
        db.execSQL(sql);

        sql = "INSERT INTO " + ProductTypeDAO.TBL_PRODUCT_TYPE + " (" + ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA +  ") VALUES ('INFORMASI DAN KOMUNIKASI');";
        db.execSQL(sql);

        sql = "INSERT INTO " + ProductTypeDAO.TBL_PRODUCT_TYPE + " (" + ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA +  ") VALUES ('AKTIVITAS KEUANGAN DAN ASURANSI');";
        db.execSQL(sql);

        sql = "INSERT INTO " + ProductTypeDAO.TBL_PRODUCT_TYPE + " (" + ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA +  ") VALUES ('REAL ESTAT');";
        db.execSQL(sql);

        sql = "INSERT INTO " + ProductTypeDAO.TBL_PRODUCT_TYPE + " (" + ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA +  ") VALUES ('AKTIVITAS PROFESIONAL, ILMIAH DAN TEKNIS');";
        db.execSQL(sql);

        sql = "INSERT INTO " + ProductTypeDAO.TBL_PRODUCT_TYPE + " (" + ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA +  ") VALUES ('AKTIVITAS PENYEWAAN DAN SEWA GUNA USAHA TANPA HAK OPSI, KETENAGAKERJAAN, AGEN PERJALANAN DAN PENUNJANG \n" +
                "USAHA LAINNYA');";
        db.execSQL(sql);

        sql = "INSERT INTO " + ProductTypeDAO.TBL_PRODUCT_TYPE + " (" + ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA +  ") VALUES ('ADMINISTRASI PEMERINTAHAN, PERTAHANAN DAN JAMINAN SOSIAL WAJIB');";
        db.execSQL(sql);

        sql = "INSERT INTO " + ProductTypeDAO.TBL_PRODUCT_TYPE + " (" + ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA +  ") VALUES ('PENDIDIKAN');";
        db.execSQL(sql);

        sql = "INSERT INTO " + ProductTypeDAO.TBL_PRODUCT_TYPE + " (" + ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA +  ") VALUES ('AKTIVITAS KESEHATAN MANUSIA DAN AKTIVITAS SOSIAL');";
        db.execSQL(sql);

        sql = "INSERT INTO " + ProductTypeDAO.TBL_PRODUCT_TYPE + " (" + ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA +  ") VALUES ('KESENIAN, HIBURAN DAN REKREASI');";
        db.execSQL(sql);

        sql = "INSERT INTO " + ProductTypeDAO.TBL_PRODUCT_TYPE + " (" + ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA +  ") VALUES ('AKTIVITAS JASA LAINNYA');";
        db.execSQL(sql);

        sql = "INSERT INTO " + ProductTypeDAO.TBL_PRODUCT_TYPE + " (" + ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA +  ") VALUES ('AKTIVITAS RUMAH TANGGA SEBAGAI PEMBERI KERJA; AKTIVITAS YANG MENGHASILKAN BARANG DAN JASA OLEH RUMAH TANGGA YANG DIGUNAKAN UNTUK MEMENUHI KEBUTUHAN SENDIRI');";
        db.execSQL(sql);

        sql = "INSERT INTO " + ProductTypeDAO.TBL_PRODUCT_TYPE + " (" + ProductTypeDAO.FLD_PRODUCT_TYPE_NAMA +  ") VALUES ('PENDIDIKAN');";
        db.execSQL(sql);


        sql = "create table " + ProductDAO.TBL_PRODUCT + " (" +
                ProductDAO.FLD_PRODUCT_ID + " INTEGER PRIMARY KEY   AUTOINCREMENT, " +
                ProductDAO.FLD_PRODUCT_NO + " varchar(20), " +
                ProductDAO.FLD_PRODUCT_NAMA + " text null, " +
                ProductDAO.FLD_PRODUCT_TYPE_ID + " integer, " +
                ProductDAO.FLD_PRODUCT_STATUS_BB + " integer, " +
                ProductDAO.FLD_PRODUCT_STATUS_SDM + " integer, " +
                ProductDAO.FLD_PRODUCT_STATUS_PEM + " integer, " +
                ProductDAO.FLD_PRODUCT_STATUS_PP + " integer, " +
                ProductDAO.FLD_PRODUCT_STATUS_NPV + " integer, " +
                ProductDAO.FLD_PRODUCT_STATUS_PI + " integer " +
                ");";

        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);

        sql = "INSERT INTO  " + ProductDAO.TBL_PRODUCT + " (" + ProductDAO.FLD_PRODUCT_NO + ", " + ProductDAO.FLD_PRODUCT_NAMA + ", " + ProductDAO.FLD_PRODUCT_TYPE_ID + ") " +
                "VALUES ( 01                         , 'Garam'                        , 1);";
        db.execSQL(sql);

        sql = "INSERT INTO  " + ProductDAO.TBL_PRODUCT + " (" + ProductDAO.FLD_PRODUCT_NO + ", " + ProductDAO.FLD_PRODUCT_NAMA + ", " + ProductDAO.FLD_PRODUCT_TYPE_ID + ") " +
                "VALUES ( 02                         , 'Beras'              , 1);";
        db.execSQL(sql);

        sql = "INSERT INTO  " + ProductDAO.TBL_PRODUCT + " (" + ProductDAO.FLD_PRODUCT_NO + ", " + ProductDAO.FLD_PRODUCT_NAMA + ", " + ProductDAO.FLD_PRODUCT_TYPE_ID + ") " +
                "VALUES ( 03                         , 'Keripik Singkong'              , 3);";
        db.execSQL(sql);

        sql = "create table " + CompanyDAO.TBL_COMPANY + " (" +
                CompanyDAO.FLD_COMPANY_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT, " +
                CompanyDAO.FLD_COMPANY_NAMA + " text null, " +
                CompanyDAO.FLD_COMPANY_ADDRESS + " text null, " +
                CompanyDAO.FLD_COMPANY_EMAIL + " text null, " +
                CompanyDAO.FLD_COMPANY_DIREKTUR + " text null, " +
                CompanyDAO.FLD_COMPANY_SEKRETARIS + " text null, " +
                CompanyDAO.FLD_COMPANY_BENDAHARA + " text null, " +
                CompanyDAO.FLD_COMPANY_TELP + " INTEGER " +
                ");";

        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
        sql = "INSERT INTO  " + CompanyDAO.TBL_COMPANY + " (" + CompanyDAO.FLD_COMPANY_NAMA + ", " + CompanyDAO.FLD_COMPANY_ADDRESS + ", " + CompanyDAO.FLD_COMPANY_EMAIL + ", " + CompanyDAO.FLD_COMPANY_TELP+ ", " + CompanyDAO.FLD_COMPANY_DIREKTUR+ ", " + CompanyDAO.FLD_COMPANY_SEKRETARIS + ", " + CompanyDAO.FLD_COMPANY_BENDAHARA + ") " +
                "VALUES ('BUMDES Artha Krama Mandiri' , 'Br. Pengqlon Desa Antiga Kelod, Kecamatan Manggis, Kabupaten Karangasem, Bali' , 'arthakramamandiri@gmail.com', '+6281805590875', 'I Wayan Koatiarta', 'Pande', 'Kadek Sriani');";
        db.execSQL(sql);


        sql = "create table " + AnalysisDAO.TBL_ANALYSIS + " (" +
                AnalysisDAO.FLD_ANALYSIS_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT, " +
                AnalysisDAO.FLD_PRODUCT_ID + " INTEGER, " +
                AnalysisDAO.FLD_ANALYSISBAHANBAKU_KETERSEDIAAN + " INTEGER, " +
                AnalysisDAO.FLD_ANALYSISBAHANBAKU_KEMUDAHAN + " INTEGER, " +
                AnalysisDAO.FLD_ANALYSISBAHANBAKU_KEBERLANJUTAN + " INTEGER, " +
                AnalysisDAO.FLD_ANALYSISSUMBERDAYAMANUSIA_KETERSEDIAAN + " INTEGER, " +
                AnalysisDAO.FLD_ANALYSISSUMBERDAYAMANUSIA_KOMPETEN + " INTEGER, " +
                AnalysisDAO.FLD_ANALYSISPEMASARAN_KETERSEDIAAN + " INTEGER, " +
                AnalysisDAO.FLD_ANALYSISPEMASARAN_TERJANGKAU + " INTEGER, " +
                AnalysisDAO.FLD_ANALYSISPEMASARAN_KEBUTUHAN + " INTEGER " +
                ");";

        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);

        sql = "INSERT INTO  " +  AnalysisDAO.TBL_ANALYSIS +
                " (" +  AnalysisDAO.FLD_ANALYSIS_ID +
                ", " +  AnalysisDAO.FLD_PRODUCT_ID +
                ", " +  AnalysisDAO.FLD_ANALYSISBAHANBAKU_KETERSEDIAAN +
                ", " +  AnalysisDAO.FLD_ANALYSISBAHANBAKU_KEMUDAHAN +
                ", " +  AnalysisDAO.FLD_ANALYSISBAHANBAKU_KEBERLANJUTAN +
                ", " +  AnalysisDAO.FLD_ANALYSISSUMBERDAYAMANUSIA_KETERSEDIAAN +
                ", " +  AnalysisDAO.FLD_ANALYSISSUMBERDAYAMANUSIA_KOMPETEN +
                ", " +  AnalysisDAO.FLD_ANALYSISPEMASARAN_KETERSEDIAAN +
                ", " +  AnalysisDAO.FLD_ANALYSISPEMASARAN_TERJANGKAU +
                ", " +  AnalysisDAO.FLD_ANALYSISPEMASARAN_KEBUTUHAN + ") " +
                "VALUES (1,1,0,0,1,1,0,0,0,0);";
        db.execSQL(sql);

        sql = "INSERT INTO  " +  AnalysisDAO.TBL_ANALYSIS +
                " (" +  AnalysisDAO.FLD_ANALYSIS_ID +
                ", " +  AnalysisDAO.FLD_PRODUCT_ID +
                ", " +  AnalysisDAO.FLD_ANALYSISBAHANBAKU_KETERSEDIAAN +
                ", " +  AnalysisDAO.FLD_ANALYSISBAHANBAKU_KEMUDAHAN +
                ", " +  AnalysisDAO.FLD_ANALYSISBAHANBAKU_KEBERLANJUTAN +
                ", " +  AnalysisDAO.FLD_ANALYSISSUMBERDAYAMANUSIA_KETERSEDIAAN +
                ", " +  AnalysisDAO.FLD_ANALYSISSUMBERDAYAMANUSIA_KOMPETEN +
                ", " +  AnalysisDAO.FLD_ANALYSISPEMASARAN_KETERSEDIAAN +
                ", " +  AnalysisDAO.FLD_ANALYSISPEMASARAN_TERJANGKAU +
                ", " +  AnalysisDAO.FLD_ANALYSISPEMASARAN_KEBUTUHAN + ") " +
                "VALUES (2,2,0,0,0,0,0,0,0,1);";
        db.execSQL(sql);

        sql = "INSERT INTO  " +  AnalysisDAO.TBL_ANALYSIS +
                " (" +  AnalysisDAO.FLD_ANALYSIS_ID +
                ", " +  AnalysisDAO.FLD_PRODUCT_ID +
                ", " +  AnalysisDAO.FLD_ANALYSISBAHANBAKU_KETERSEDIAAN +
                ", " +  AnalysisDAO.FLD_ANALYSISBAHANBAKU_KEMUDAHAN +
                ", " +  AnalysisDAO.FLD_ANALYSISBAHANBAKU_KEBERLANJUTAN +
                ", " +  AnalysisDAO.FLD_ANALYSISSUMBERDAYAMANUSIA_KETERSEDIAAN +
                ", " +  AnalysisDAO.FLD_ANALYSISSUMBERDAYAMANUSIA_KOMPETEN +
                ", " +  AnalysisDAO.FLD_ANALYSISPEMASARAN_KETERSEDIAAN +
                ", " +  AnalysisDAO.FLD_ANALYSISPEMASARAN_TERJANGKAU +
                ", " +  AnalysisDAO.FLD_ANALYSISPEMASARAN_KEBUTUHAN + ") " +
                "VALUES (3,3,1,0,0,0,0,0,0,0);";
        db.execSQL(sql);


        sql = "create table " + SwotDAO.TBL_SWOT + " (" +
                SwotDAO.FLD_SWOT_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT, " +
                SwotDAO.FLD_KATEGORI + " INTEGER, " +
                SwotDAO.FLD_KETERANGAN + " TEXT null" +
                ");";

        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
        sql = "INSERT INTO  " +  SwotDAO.TBL_SWOT + " (" +  SwotDAO.FLD_KATEGORI + ", " +  SwotDAO.FLD_KETERANGAN + ") " +
                "VALUES ('0'  ,'Meningkatkan kekuatan dalam memanfaatkan peluang');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  SwotDAO.TBL_SWOT + " (" +  SwotDAO.FLD_KATEGORI + ", " +  SwotDAO.FLD_KETERANGAN + ") " +
                "VALUES ('0'  ,'Menentukan Bidang Usaha yang Tepat dengan memperhatikan kekuatan yang dimiliki');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  SwotDAO.TBL_SWOT + " (" +  SwotDAO.FLD_KATEGORI + ", " +  SwotDAO.FLD_KETERANGAN + ") " +
                "VALUES ('1'  ,'Strategi yang tidak berfokus pada kelemahan bumdes');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  SwotDAO.TBL_SWOT + " (" +  SwotDAO.FLD_KATEGORI + ", " +  SwotDAO.FLD_KETERANGAN + ") " +
                "VALUES ('1'  ,'Menentukan bidang usaha yang jauh dari faktor kelemahan desa');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  SwotDAO.TBL_SWOT + " (" +  SwotDAO.FLD_KATEGORI + ", " +  SwotDAO.FLD_KETERANGAN + ") " +
                "VALUES ('2'  ,'memanfaatkan keunggulan desa dalam mengatasi ancaman yang mungkin timbul');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  SwotDAO.TBL_SWOT + " (" +  SwotDAO.FLD_KATEGORI + ", " +  SwotDAO.FLD_KETERANGAN + ") " +
                "VALUES ('3'  ,'Tidak berfokus pada usaha dimana desa memiliki banyak kelemahan untuk menghindari ancaman');";
        db.execSQL(sql);


        sql = "create table " + PerhitunganInvestasiDAO.TBL_PERHITUNGANINVESTASI + " (" +
                PerhitunganInvestasiDAO.FLD_PERHITUNGANINVESTASI_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT, " +
                PerhitunganInvestasiDAO.FLD_SUMBERDANA + " TEXT null," +
                PerhitunganInvestasiDAO.FLD_TAHUN + " INTEGER, " +
                PerhitunganInvestasiDAO.FLD_NOMINAL + " bigint(20), " +
                PerhitunganInvestasiDAO.FLD_MODAL_INVESTASI + " bigint(20), " +
                PerhitunganInvestasiDAO.FLD_MODAL_KERJA + " bigint(20) " +
                ");";

        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
        sql = "INSERT INTO  " +  PerhitunganInvestasiDAO.TBL_PERHITUNGANINVESTASI + " (" +  PerhitunganInvestasiDAO.FLD_SUMBERDANA + ", " +  PerhitunganInvestasiDAO.FLD_TAHUN + ", " +  PerhitunganInvestasiDAO.FLD_NOMINAL +", " +  PerhitunganInvestasiDAO.FLD_MODAL_INVESTASI +", " +  PerhitunganInvestasiDAO.FLD_MODAL_KERJA + ") " +
                "VALUES ('Penyertaan Modal' ,'2022','195000000','190000000','60000000');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  PerhitunganInvestasiDAO.TBL_PERHITUNGANINVESTASI + " (" +  PerhitunganInvestasiDAO.FLD_SUMBERDANA + ", " +  PerhitunganInvestasiDAO.FLD_TAHUN + ", " +  PerhitunganInvestasiDAO.FLD_NOMINAL +", " +  PerhitunganInvestasiDAO.FLD_MODAL_INVESTASI +", " +  PerhitunganInvestasiDAO.FLD_MODAL_KERJA + ") " +
                "VALUES ('Penyertaan Masyarakat' ,'2022','5000000','190000000','60000000');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  PerhitunganInvestasiDAO.TBL_PERHITUNGANINVESTASI + " (" +  PerhitunganInvestasiDAO.FLD_SUMBERDANA + ", " +  PerhitunganInvestasiDAO.FLD_TAHUN + ", " +  PerhitunganInvestasiDAO.FLD_NOMINAL +", " +  PerhitunganInvestasiDAO.FLD_MODAL_INVESTASI +", " +  PerhitunganInvestasiDAO.FLD_MODAL_KERJA + ") " +
                "VALUES ('Hibah' ,'2022','25000000','190000000','60000000');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  PerhitunganInvestasiDAO.TBL_PERHITUNGANINVESTASI + " (" +  PerhitunganInvestasiDAO.FLD_SUMBERDANA + ", " +  PerhitunganInvestasiDAO.FLD_TAHUN + ", " +  PerhitunganInvestasiDAO.FLD_NOMINAL +", " +  PerhitunganInvestasiDAO.FLD_MODAL_INVESTASI +", " +  PerhitunganInvestasiDAO.FLD_MODAL_KERJA + ") " +
                "VALUES ('Dana Bumdes Sendiri' ,'2022','15000000','190000000','60000000');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  PerhitunganInvestasiDAO.TBL_PERHITUNGANINVESTASI + " (" +  PerhitunganInvestasiDAO.FLD_SUMBERDANA + ", " +  PerhitunganInvestasiDAO.FLD_TAHUN + ", " +  PerhitunganInvestasiDAO.FLD_NOMINAL +", " +  PerhitunganInvestasiDAO.FLD_MODAL_INVESTASI +", " +  PerhitunganInvestasiDAO.FLD_MODAL_KERJA + ") " +
                "VALUES ('Lainnya' ,'2022','10000000','190000000','60000000');";
        db.execSQL(sql);




        sql = "create table " + DetailPerhitunganInvestasiDAO.TBL_DETAILPERHITUNGANINVESTASI + " (" +
                DetailPerhitunganInvestasiDAO.FLD_DETAILPERHITUNGANINVESTASI_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT, " +
                DetailPerhitunganInvestasiDAO.FLD_RBA + " TEXT null," +
                DetailPerhitunganInvestasiDAO.FLD_TAHUN + " INTEGER, " +
                DetailPerhitunganInvestasiDAO.FLD_VOLUME + " bigint(20), " +
                DetailPerhitunganInvestasiDAO.FLD_HARGA + " bigint(20), " +
                DetailPerhitunganInvestasiDAO.FLD_TOTAL_HARGA + " bigint(20) " +
                ");";

        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
        sql = "INSERT INTO  " +  DetailPerhitunganInvestasiDAO.TBL_DETAILPERHITUNGANINVESTASI + " (" +  DetailPerhitunganInvestasiDAO.FLD_RBA + ", " +  DetailPerhitunganInvestasiDAO.FLD_TAHUN + ", " +  DetailPerhitunganInvestasiDAO.FLD_VOLUME +", " +  DetailPerhitunganInvestasiDAO.FLD_HARGA +", " +  DetailPerhitunganInvestasiDAO.FLD_TOTAL_HARGA + ") " +
                "VALUES ('GnB-Talut' ,'2022','2','5000000','10000000');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  DetailPerhitunganInvestasiDAO.TBL_DETAILPERHITUNGANINVESTASI + " (" +  DetailPerhitunganInvestasiDAO.FLD_RBA + ", " +  DetailPerhitunganInvestasiDAO.FLD_TAHUN + ", " +  DetailPerhitunganInvestasiDAO.FLD_VOLUME +", " +  DetailPerhitunganInvestasiDAO.FLD_HARGA +", " +  DetailPerhitunganInvestasiDAO.FLD_TOTAL_HARGA + ") " +
                "VALUES ('GnB RPS' ,'2022','1','25000000','25000000');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  DetailPerhitunganInvestasiDAO.TBL_DETAILPERHITUNGANINVESTASI + " (" +  DetailPerhitunganInvestasiDAO.FLD_RBA + ", " +  DetailPerhitunganInvestasiDAO.FLD_TAHUN + ", " +  DetailPerhitunganInvestasiDAO.FLD_VOLUME +", " +  DetailPerhitunganInvestasiDAO.FLD_HARGA +", " +  DetailPerhitunganInvestasiDAO.FLD_TOTAL_HARGA + ") " +
                "VALUES ('GnB Kantor' ,'2022','1','100000000','100000000');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  DetailPerhitunganInvestasiDAO.TBL_DETAILPERHITUNGANINVESTASI + " (" +  DetailPerhitunganInvestasiDAO.FLD_RBA + ", " +  DetailPerhitunganInvestasiDAO.FLD_TAHUN + ", " +  DetailPerhitunganInvestasiDAO.FLD_VOLUME +", " +  DetailPerhitunganInvestasiDAO.FLD_HARGA +", " +  DetailPerhitunganInvestasiDAO.FLD_TOTAL_HARGA + ") " +
                "VALUES ('GnB Akses Jalan' ,'2022','1','50000000','50000000');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  DetailPerhitunganInvestasiDAO.TBL_DETAILPERHITUNGANINVESTASI + " (" +  DetailPerhitunganInvestasiDAO.FLD_RBA + ", " +  DetailPerhitunganInvestasiDAO.FLD_TAHUN + ", " +  DetailPerhitunganInvestasiDAO.FLD_VOLUME +", " +  DetailPerhitunganInvestasiDAO.FLD_HARGA +", " +  DetailPerhitunganInvestasiDAO.FLD_TOTAL_HARGA + ") " +
                "VALUES ('Mesin Pencacah' ,'2022','1','5000000','5000000');";
        db.execSQL(sql);

        sql = "create table " + DetailPerhitunganPendapatanDAO.TBL_DETAILPERHITUNGANPENDAPATAN + " (" +
                DetailPerhitunganPendapatanDAO.FLD_DETAILPERHITUNGANPENDAPATAN_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT, " +
                DetailPerhitunganPendapatanDAO.FLD_RBA + " TEXT null," +
                DetailPerhitunganPendapatanDAO.FLD_TAHUN + " INTEGER, " +
                DetailPerhitunganPendapatanDAO.FLD_VOLUME + " bigint(20), " +
                DetailPerhitunganPendapatanDAO.FLD_HARGA + " bigint(20), " +
                DetailPerhitunganPendapatanDAO.FLD_TOTAL_HARGA + " bigint(20) " +
                ");";

        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
        sql = "INSERT INTO  " +  DetailPerhitunganPendapatanDAO.TBL_DETAILPERHITUNGANPENDAPATAN + " (" +  DetailPerhitunganPendapatanDAO.FLD_RBA + ", " +  DetailPerhitunganPendapatanDAO.FLD_TAHUN + ", " +  DetailPerhitunganPendapatanDAO.FLD_VOLUME +", " +  DetailPerhitunganPendapatanDAO.FLD_HARGA +", " +  DetailPerhitunganPendapatanDAO.FLD_TOTAL_HARGA + ") " +
                "VALUES ('Retribusi' ,'2022','60','100000','6000000');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  DetailPerhitunganPendapatanDAO.TBL_DETAILPERHITUNGANPENDAPATAN + " (" +  DetailPerhitunganPendapatanDAO.FLD_RBA + ", " +  DetailPerhitunganPendapatanDAO.FLD_TAHUN + ", " +  DetailPerhitunganPendapatanDAO.FLD_VOLUME +", " +  DetailPerhitunganPendapatanDAO.FLD_HARGA +", " +  DetailPerhitunganPendapatanDAO.FLD_TOTAL_HARGA + ") " +
                "VALUES ('Rosok' ,'2022','1','500000','500000');";
        db.execSQL(sql);

        sql = "create table " + DetailPerhitunganBiayaDAO.TBL_DETAILPERHITUNGANBIAYA + " (" +
                DetailPerhitunganBiayaDAO.FLD_DETAILPERHITUNGANBIAYA_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT, " +
                DetailPerhitunganBiayaDAO.FLD_RBA + " TEXT null," +
                DetailPerhitunganBiayaDAO.FLD_TAHUN + " INTEGER, " +
                DetailPerhitunganBiayaDAO.FLD_VOLUME + " bigint(20), " +
                DetailPerhitunganBiayaDAO.FLD_HARGA + " bigint(20), " +
                DetailPerhitunganBiayaDAO.FLD_TOTAL_HARGA + " bigint(20) " +
                ");";

        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
        sql = "INSERT INTO  " +  DetailPerhitunganBiayaDAO.TBL_DETAILPERHITUNGANBIAYA + " (" +  DetailPerhitunganBiayaDAO.FLD_RBA + ", " +  DetailPerhitunganBiayaDAO.FLD_TAHUN + ", " +  DetailPerhitunganBiayaDAO.FLD_VOLUME +", " +  DetailPerhitunganBiayaDAO.FLD_HARGA +", " +  DetailPerhitunganBiayaDAO.FLD_TOTAL_HARGA + ") " +
                "VALUES ('Panitia' ,'2022','2','1750000','3500000');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  DetailPerhitunganBiayaDAO.TBL_DETAILPERHITUNGANBIAYA + " (" +  DetailPerhitunganBiayaDAO.FLD_RBA + ", " +  DetailPerhitunganBiayaDAO.FLD_TAHUN + ", " +  DetailPerhitunganBiayaDAO.FLD_VOLUME +", " +  DetailPerhitunganBiayaDAO.FLD_HARGA +", " +  DetailPerhitunganBiayaDAO.FLD_TOTAL_HARGA + ") " +
                "VALUES ('Panggung' ,'2022','20','10000','200000');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  DetailPerhitunganBiayaDAO.TBL_DETAILPERHITUNGANBIAYA + " (" +  DetailPerhitunganBiayaDAO.FLD_RBA + ", " +  DetailPerhitunganBiayaDAO.FLD_TAHUN + ", " +  DetailPerhitunganBiayaDAO.FLD_VOLUME +", " +  DetailPerhitunganBiayaDAO.FLD_HARGA +", " +  DetailPerhitunganBiayaDAO.FLD_TOTAL_HARGA + ") " +
                "VALUES ('Listrik' ,'2022','1','800000','800000');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  DetailPerhitunganBiayaDAO.TBL_DETAILPERHITUNGANBIAYA + " (" +  DetailPerhitunganBiayaDAO.FLD_RBA + ", " +  DetailPerhitunganBiayaDAO.FLD_TAHUN + ", " +  DetailPerhitunganBiayaDAO.FLD_VOLUME +", " +  DetailPerhitunganBiayaDAO.FLD_HARGA +", " +  DetailPerhitunganBiayaDAO.FLD_TOTAL_HARGA + ") " +
                "VALUES ('Air' ,'2022','1','300000','300000');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  DetailPerhitunganBiayaDAO.TBL_DETAILPERHITUNGANBIAYA + " (" +  DetailPerhitunganBiayaDAO.FLD_RBA + ", " +  DetailPerhitunganBiayaDAO.FLD_TAHUN + ", " +  DetailPerhitunganBiayaDAO.FLD_VOLUME +", " +  DetailPerhitunganBiayaDAO.FLD_HARGA +", " +  DetailPerhitunganBiayaDAO.FLD_TOTAL_HARGA + ") " +
                "VALUES ('Perlengkapan' ,'2022','1','200000','200000');";
        db.execSQL(sql);

        sql = "create table " + BmcDAO.TBL_BMC + " (" +
                BmcDAO.FLD_BMC_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT, " +
                BmcDAO.FLD_KATEGORI + " INTEGER, " +
                BmcDAO.FLD_KETERANGAN + " TEXT null" +
                ");";

        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
        sql = "INSERT INTO  " +  BmcDAO.TBL_BMC + " (" +  BmcDAO.FLD_KATEGORI + ", " +  BmcDAO.FLD_KETERANGAN + ") " +
                "VALUES ('0'  ,'Vendor yang Berpengalaman');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  BmcDAO.TBL_BMC + " (" +  BmcDAO.FLD_KATEGORI + ", " +  BmcDAO.FLD_KETERANGAN + ") " +
                "VALUES ('1'  ,'Menentukan Bidang Usaha yang Sesuai dengan memperhatikan kekuatan yang dimiliki');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  BmcDAO.TBL_BMC + " (" +  BmcDAO.FLD_KATEGORI + ", " +  BmcDAO.FLD_KETERANGAN + ") " +
                "VALUES ('2'  ,'Memberikan Produk yang inovatif');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  BmcDAO.TBL_BMC + " (" +  BmcDAO.FLD_KATEGORI + ", " +  BmcDAO.FLD_KETERANGAN + ") " +
                "VALUES ('3'  ,'Website, Email, Telp');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  BmcDAO.TBL_BMC + " (" +  BmcDAO.FLD_KATEGORI + ", " +  BmcDAO.FLD_KETERANGAN + ") " +
                "VALUES ('4'  ,'Masyarakat menengah');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  BmcDAO.TBL_BMC + " (" +  BmcDAO.FLD_KATEGORI + ", " +  BmcDAO.FLD_KETERANGAN + ") " +
                "VALUES ('5'  ,'SDM Yang skill full dan memahami kebutuhan masyarakat');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  BmcDAO.TBL_BMC + " (" +  BmcDAO.FLD_KATEGORI + ", " +  BmcDAO.FLD_KETERANGAN + ") " +
                "VALUES ('6'  ,'WebMedia, Youtube, Sarana yang memadai');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  BmcDAO.TBL_BMC + " (" +  BmcDAO.FLD_KATEGORI + ", " +  BmcDAO.FLD_KETERANGAN + ") " +
                "VALUES ('7'  ,'Biaya Marketing, Produksi, dan operasional');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  BmcDAO.TBL_BMC + " (" +  BmcDAO.FLD_KATEGORI + ", " +  BmcDAO.FLD_KETERANGAN + ") " +
                "VALUES ('8'  ,'Penjualan Produk');";
        db.execSQL(sql);


        sql = "create table " + NetPresentValueDAO.TBL_NETPRESENTVALUE + " (" +
                NetPresentValueDAO.FLD_NETPRESENTVALUE_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT, " +
                NetPresentValueDAO.FLD_NILAI_INVESTASI_TOTAL + " DOUBLE, " +
                NetPresentValueDAO.FLD_NILAI_PROCEED_PERTAHUN + " DOUBLE, " +
                NetPresentValueDAO.FLD_NILAI_RATE_PERSEN + " DOUBLE, " +
                NetPresentValueDAO.FLD_JUMLAH_PERIODE + " INTEGER, " +
                NetPresentValueDAO.FLD_PRODUCT_ID + " INTEGER, " +
                NetPresentValueDAO.FLD_PRODUCT_STATUS_NPV + " INTEGER, " +
                NetPresentValueDAO.FLD_PRODUCT_STATUS_PI + " INTEGER " +
                ");";

        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
        sql = "INSERT INTO  " +  NetPresentValueDAO.TBL_NETPRESENTVALUE + " (" +  NetPresentValueDAO.FLD_NILAI_INVESTASI_TOTAL + ", " +  NetPresentValueDAO.FLD_NILAI_PROCEED_PERTAHUN + ", " +  NetPresentValueDAO.FLD_NILAI_RATE_PERSEN + ", " +  NetPresentValueDAO.FLD_JUMLAH_PERIODE + ", " +  NetPresentValueDAO.FLD_PRODUCT_ID + ", " +  NetPresentValueDAO.FLD_PRODUCT_STATUS_NPV + ", " +  NetPresentValueDAO.FLD_PRODUCT_STATUS_PI + ") " +
                "VALUES ('700000000'              ,'300000000'                         , '15', '5', '1', '0', '0');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  NetPresentValueDAO.TBL_NETPRESENTVALUE + " (" +  NetPresentValueDAO.FLD_NILAI_INVESTASI_TOTAL + ", " +  NetPresentValueDAO.FLD_NILAI_PROCEED_PERTAHUN + ", " +  NetPresentValueDAO.FLD_NILAI_RATE_PERSEN + ", " +  NetPresentValueDAO.FLD_JUMLAH_PERIODE + ", " +  NetPresentValueDAO.FLD_PRODUCT_ID + ", " +  NetPresentValueDAO.FLD_PRODUCT_STATUS_NPV + ", " +  NetPresentValueDAO.FLD_PRODUCT_STATUS_PI + ") " +
                "VALUES ('700000000'              ,'250000000'                         , '15', '5', '1', '0', '0');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  NetPresentValueDAO.TBL_NETPRESENTVALUE + " (" +  NetPresentValueDAO.FLD_NILAI_INVESTASI_TOTAL + ", " +  NetPresentValueDAO.FLD_NILAI_PROCEED_PERTAHUN + ", " +  NetPresentValueDAO.FLD_NILAI_RATE_PERSEN + ", " +  NetPresentValueDAO.FLD_JUMLAH_PERIODE + ", " +  NetPresentValueDAO.FLD_PRODUCT_ID + ", " +  NetPresentValueDAO.FLD_PRODUCT_STATUS_NPV + ", " +  NetPresentValueDAO.FLD_PRODUCT_STATUS_PI + ") " +
                "VALUES ('700000000'              ,'200000000'                         , '15', '5', '1', '0', '0');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  NetPresentValueDAO.TBL_NETPRESENTVALUE + " (" +  NetPresentValueDAO.FLD_NILAI_INVESTASI_TOTAL + ", " +  NetPresentValueDAO.FLD_NILAI_PROCEED_PERTAHUN + ", " +  NetPresentValueDAO.FLD_NILAI_RATE_PERSEN + ", " +  NetPresentValueDAO.FLD_JUMLAH_PERIODE + ", " +  NetPresentValueDAO.FLD_PRODUCT_ID + ", " +  NetPresentValueDAO.FLD_PRODUCT_STATUS_NPV + ", " +  NetPresentValueDAO.FLD_PRODUCT_STATUS_PI + ") " +
                "VALUES ('700000000'              ,'150000000'                         , '15', '5', '1', '0', '0');";
        db.execSQL(sql);
        sql = "INSERT INTO  " +  NetPresentValueDAO.TBL_NETPRESENTVALUE + " (" +  NetPresentValueDAO.FLD_NILAI_INVESTASI_TOTAL + ", " +  NetPresentValueDAO.FLD_NILAI_PROCEED_PERTAHUN + ", " +  NetPresentValueDAO.FLD_NILAI_RATE_PERSEN + ", " +  NetPresentValueDAO.FLD_JUMLAH_PERIODE + ", " +  NetPresentValueDAO.FLD_PRODUCT_ID + ", " +  NetPresentValueDAO.FLD_PRODUCT_STATUS_NPV + ", " +  NetPresentValueDAO.FLD_PRODUCT_STATUS_PI + ") " +
                "VALUES ('700000000'              ,'100000000'                         , '15', '5', '1', '0', '0');";
        db.execSQL(sql);



        sql = "create table " + PaybackPeriodDAO.TBL_PAYBACKPERIOD + " (" +
                PaybackPeriodDAO.FLD_PAYBACKPERIOD_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT, " +
                PaybackPeriodDAO.FLD_NILAI_INVESTASI_TOTAL + " DOUBLE, " +
                PaybackPeriodDAO.FLD_NILAI_PROCEED_PERTAHUN + " DOUBLE, " +
                PaybackPeriodDAO.FLD_KAS_MASUK_BERSIH + " DOUBLE, " +
                PaybackPeriodDAO.FLD_TAHUN_MAXIMUM_PAYBACK_PERIOD + " INTEGER, " +
                PaybackPeriodDAO.FLD_PRODUCT_ID + " INTEGER, " +
                PaybackPeriodDAO.FLD_PRODUCT_STATUS + " INTEGER " +
                ");";

        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
        sql = "INSERT INTO  " + PaybackPeriodDAO.TBL_PAYBACKPERIOD + " (" + PaybackPeriodDAO.FLD_NILAI_INVESTASI_TOTAL + ", " + PaybackPeriodDAO.FLD_NILAI_PROCEED_PERTAHUN + ", " + PaybackPeriodDAO.FLD_KAS_MASUK_BERSIH + ", " + PaybackPeriodDAO.FLD_TAHUN_MAXIMUM_PAYBACK_PERIOD + ", " + PaybackPeriodDAO.FLD_PRODUCT_ID + ", " + PaybackPeriodDAO.FLD_PRODUCT_STATUS + ") " +
                "VALUES ('10000000'                         , '5000000'              , '5000000', '3', '1', '0');";
        db.execSQL(sql);
        sql = "INSERT INTO  " + PaybackPeriodDAO.TBL_PAYBACKPERIOD + " (" + PaybackPeriodDAO.FLD_NILAI_INVESTASI_TOTAL + ", " + PaybackPeriodDAO.FLD_NILAI_PROCEED_PERTAHUN + ", " + PaybackPeriodDAO.FLD_KAS_MASUK_BERSIH + ", " + PaybackPeriodDAO.FLD_TAHUN_MAXIMUM_PAYBACK_PERIOD + ", " + PaybackPeriodDAO.FLD_PRODUCT_ID + ", " + PaybackPeriodDAO.FLD_PRODUCT_STATUS + ") " +
                "VALUES ('10000000'                         , '4000000'              , '5000000', '3', '1', '0');";
        db.execSQL(sql);
        sql = "INSERT INTO  " + PaybackPeriodDAO.TBL_PAYBACKPERIOD + " (" + PaybackPeriodDAO.FLD_NILAI_INVESTASI_TOTAL + ", " + PaybackPeriodDAO.FLD_NILAI_PROCEED_PERTAHUN + ", " + PaybackPeriodDAO.FLD_KAS_MASUK_BERSIH + ", " + PaybackPeriodDAO.FLD_TAHUN_MAXIMUM_PAYBACK_PERIOD + ", " + PaybackPeriodDAO.FLD_PRODUCT_ID + ", " + PaybackPeriodDAO.FLD_PRODUCT_STATUS + ") " +
                "VALUES ('10000000'                         , '3000000'              , '5000000', '3', '1', '0');";
        db.execSQL(sql);
        sql = "INSERT INTO  " + PaybackPeriodDAO.TBL_PAYBACKPERIOD + " (" + PaybackPeriodDAO.FLD_NILAI_INVESTASI_TOTAL + ", " + PaybackPeriodDAO.FLD_NILAI_PROCEED_PERTAHUN + ", " + PaybackPeriodDAO.FLD_KAS_MASUK_BERSIH + ", " + PaybackPeriodDAO.FLD_TAHUN_MAXIMUM_PAYBACK_PERIOD + ", " + PaybackPeriodDAO.FLD_PRODUCT_ID + ", " + PaybackPeriodDAO.FLD_PRODUCT_STATUS + ") " +
                "VALUES ('10000000'                         , '2000000'              , '5000000', '3', '1', '0');";
        db.execSQL(sql);


        sql = "create table " + AkunTypeDAO.TBL_AKUN_TYPE + " (" +
                AkunTypeDAO.FLD_AKUN_TYPE_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT, " +
                AkunTypeDAO.FLD_AKUN_TYPE_NAMA + " text null " +
                ");";

        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);

        sql = "INSERT INTO " + AkunTypeDAO.TBL_AKUN_TYPE + " (" + AkunTypeDAO.FLD_AKUN_TYPE_NAMA +  ") VALUES ('Asset Lancar');";
        db.execSQL(sql);

        sql = "INSERT INTO " + AkunTypeDAO.TBL_AKUN_TYPE + " (" + AkunTypeDAO.FLD_AKUN_TYPE_NAMA +  ") VALUES ('Asset Tetap');";
        db.execSQL(sql);

        sql = "INSERT INTO " + AkunTypeDAO.TBL_AKUN_TYPE + " (" + AkunTypeDAO.FLD_AKUN_TYPE_NAMA +  ") VALUES ('Kewajiban');";
        db.execSQL(sql);

        sql = "INSERT INTO " + AkunTypeDAO.TBL_AKUN_TYPE + " (" + AkunTypeDAO.FLD_AKUN_TYPE_NAMA +  ") VALUES ('Modal');";
        db.execSQL(sql);

        sql = "INSERT INTO " + AkunTypeDAO.TBL_AKUN_TYPE + " (" + AkunTypeDAO.FLD_AKUN_TYPE_NAMA +  ") VALUES ('Pendapatan');";
        db.execSQL(sql);

        sql = "INSERT INTO " + AkunTypeDAO.TBL_AKUN_TYPE + " (" + AkunTypeDAO.FLD_AKUN_TYPE_NAMA +  ") VALUES ('Beban');";
        db.execSQL(sql);

        sql = "INSERT INTO " + AkunTypeDAO.TBL_AKUN_TYPE + " (" + AkunTypeDAO.FLD_AKUN_TYPE_NAMA +  ") VALUES ('Prive');";
        db.execSQL(sql);


        sql = "create table " + AkunDAO.TBL_AKUN + " (" +
                AkunDAO.FLD_AKUN_ID + " INTEGER PRIMARY KEY   AUTOINCREMENT, " +
                AkunDAO.FLD_AKUN_NO + " varchar(20), " +
                AkunDAO.FLD_AKUN_NAMA + " text null, " +
                AkunDAO.FLD_AKUN_TYPE_ID + " integer " +
                ");";

        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);

        sql = "INSERT INTO  " + AkunDAO.TBL_AKUN + " (" + AkunDAO.FLD_AKUN_NO + ", " + AkunDAO.FLD_AKUN_NAMA + ", " + AkunDAO.FLD_AKUN_TYPE_ID + ") " +
              "VALUES ( 112                         , 'Kas'                        , 1);";
        db.execSQL(sql);

        sql = "INSERT INTO  " + AkunDAO.TBL_AKUN + " (" + AkunDAO.FLD_AKUN_NO + ", " + AkunDAO.FLD_AKUN_NAMA + ", " + AkunDAO.FLD_AKUN_TYPE_ID + ") " +
              "VALUES ( 113                         , 'Piutang Usaha'              , 1);";
        db.execSQL(sql);

        sql = "INSERT INTO  " + AkunDAO.TBL_AKUN + " (" + AkunDAO.FLD_AKUN_NO + ", " + AkunDAO.FLD_AKUN_NAMA + ", " + AkunDAO.FLD_AKUN_TYPE_ID + ") " +
              "VALUES ( 115                         , 'Perlengkapan'              , 1);";
        db.execSQL(sql);

        sql = "INSERT INTO  " + AkunDAO.TBL_AKUN + " (" + AkunDAO.FLD_AKUN_NO + ", " + AkunDAO.FLD_AKUN_NAMA + ", " + AkunDAO.FLD_AKUN_TYPE_ID + ") " +
              "VALUES ( 116                         , 'Asuransi Dibayar di Muka'              , 1);";
        db.execSQL(sql);

        sql = "INSERT INTO  " + AkunDAO.TBL_AKUN + " (" + AkunDAO.FLD_AKUN_NO + ", " + AkunDAO.FLD_AKUN_NAMA + ", " + AkunDAO.FLD_AKUN_TYPE_ID + ") " +
              "VALUES ( 117                         , 'Mesin'              , 2);";
        db.execSQL(sql);


        sql = "INSERT INTO  " + AkunDAO.TBL_AKUN + " (" + AkunDAO.FLD_AKUN_NO + ", " + AkunDAO.FLD_AKUN_NAMA + ", " + AkunDAO.FLD_AKUN_TYPE_ID + ") " +
              "VALUES ( 215                         , 'Utang Wesel'              , 3);";
        db.execSQL(sql);

        sql = "INSERT INTO  " + AkunDAO.TBL_AKUN + " (" + AkunDAO.FLD_AKUN_NO + ", " + AkunDAO.FLD_AKUN_NAMA + ", " + AkunDAO.FLD_AKUN_TYPE_ID + ") " +
              "VALUES ( 211                         , 'Utang Usaha'              , 3);";
        db.execSQL(sql);

        sql = "INSERT INTO  " + AkunDAO.TBL_AKUN + " (" + AkunDAO.FLD_AKUN_NO + ", " + AkunDAO.FLD_AKUN_NAMA + ", " + AkunDAO.FLD_AKUN_TYPE_ID + ") " +
              "VALUES ( 218                         , 'Pendapatan dIterima di Muka'              , 3);";
        db.execSQL(sql);

        sql = "INSERT INTO  " + AkunDAO.TBL_AKUN + " (" + AkunDAO.FLD_AKUN_NO + ", " + AkunDAO.FLD_AKUN_NAMA + ", " + AkunDAO.FLD_AKUN_TYPE_ID + ") " +
              "VALUES ( 31                         , 'Modal Budiman'              , 4);";
        db.execSQL(sql);

        sql = "INSERT INTO  " + AkunDAO.TBL_AKUN + " (" + AkunDAO.FLD_AKUN_NO + ", " + AkunDAO.FLD_AKUN_NAMA + ", " + AkunDAO.FLD_AKUN_TYPE_ID + ") " +
              "VALUES ( 61                         , 'Prive Budiman'               , 7);";
        db.execSQL(sql);

        sql = "INSERT INTO  " + AkunDAO.TBL_AKUN + " (" + AkunDAO.FLD_AKUN_NO + ", " + AkunDAO.FLD_AKUN_NAMA + ", " + AkunDAO.FLD_AKUN_TYPE_ID + ") " +
              "VALUES ( 4                         , 'Pendapatan Penatu'           , 5);";
        db.execSQL(sql);

        sql = "INSERT INTO  " + AkunDAO.TBL_AKUN + " (" + AkunDAO.FLD_AKUN_NO + ", " + AkunDAO.FLD_AKUN_NAMA + ", " + AkunDAO.FLD_AKUN_TYPE_ID + ") " +
              "VALUES ( 51                         , 'Beban Advertensi'              , 6);";
        db.execSQL(sql);

        sql = "INSERT INTO  " + AkunDAO.TBL_AKUN + " (" + AkunDAO.FLD_AKUN_NO + ", " + AkunDAO.FLD_AKUN_NAMA + ", " + AkunDAO.FLD_AKUN_TYPE_ID + ") " +
              "VALUES ( 52                         , 'Beban Sewa'              , 6);";
        db.execSQL(sql);

        sql = "INSERT INTO  " + AkunDAO.TBL_AKUN + " (" + AkunDAO.FLD_AKUN_NO + ", " + AkunDAO.FLD_AKUN_NAMA + ", " + AkunDAO.FLD_AKUN_TYPE_ID + ") " +
              "VALUES ( 53                         , 'Gaji Pegawai'              , 6);";
        db.execSQL(sql);

        sql = "INSERT INTO  " + AkunDAO.TBL_AKUN + " (" + AkunDAO.FLD_AKUN_NO + ", " + AkunDAO.FLD_AKUN_NAMA + ", " + AkunDAO.FLD_AKUN_TYPE_ID + ") " +
                "VALUES ( 118                         , 'Kendaraan'              , 2);";
        db.execSQL(sql);

        sql = "INSERT INTO  " + AkunDAO.TBL_AKUN + " (" + AkunDAO.FLD_AKUN_NO + ", " + AkunDAO.FLD_AKUN_NAMA + ", " + AkunDAO.FLD_AKUN_TYPE_ID + ") " +
                "VALUES ( 119                         , 'Peralatan'              , 2);";
        db.execSQL(sql);

        sql = "INSERT INTO  " + AkunDAO.TBL_AKUN + " (" + AkunDAO.FLD_AKUN_NO + ", " + AkunDAO.FLD_AKUN_NAMA + ", " + AkunDAO.FLD_AKUN_TYPE_ID + ") " +
                "VALUES ( 120                         , 'Komputer'              , 2);";
        db.execSQL(sql);

        sql = "INSERT INTO  " + AkunDAO.TBL_AKUN + " (" + AkunDAO.FLD_AKUN_NO + ", " + AkunDAO.FLD_AKUN_NAMA + ", " + AkunDAO.FLD_AKUN_TYPE_ID + ") " +
                "VALUES ( 121                         , 'Akumulasi Depresiasi'              , 2);";
        db.execSQL(sql);

        sql = "INSERT INTO  " + AkunDAO.TBL_AKUN + " (" + AkunDAO.FLD_AKUN_NO + ", " + AkunDAO.FLD_AKUN_NAMA + ", " + AkunDAO.FLD_AKUN_TYPE_ID + ") " +
                "VALUES ( 54                         , 'Biaya Depresiasi'              , 6);";
        db.execSQL(sql);


        sql = "create table " + TransactionDAO.TBL_TRANSACTION + " (" +
                TransactionDAO.FLD_TRANSACTION_ID + " INTEGER PRIMARY KEY   AUTOINCREMENT, " +
                TransactionDAO.FLD_TRANSACTION_NO + " varchar(20), " +
                TransactionDAO.FLD_AKUN_ID + " integer, " +
                TransactionDAO.FLD_TRANSACTION_NOTE + " text null, " +
                TransactionDAO.FLD_TRANSACTION_VALUE + " bigint(20), " +
                TransactionDAO.FLD_TRANSACTION_TYPE + " integer, " +
                TransactionDAO.FLD_TRANSACTION_DATE + " date " +
                ");";
        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);


        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION + " ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('1'                                        ,    1                              ,  'Setoran Modal Budiman'                   ,   0                                        , 2000000                                      ,   '2022-08-01');";
        db.execSQL(sql);

        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION + " ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('1'                                        ,    9                              ,  'Setoran Modal Budiman'                   ,   1                                        , 2000000                                     ,   '2022-08-01');";
        db.execSQL(sql);


        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('2'                                        ,    13                             ,  'Pembayaran Sewa Bulan September'         ,   0                                        , 100000                                      ,   '2022-08-02');";
        db.execSQL(sql);

        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('2'                                        ,    1                              ,  'Pembayaran Sewa Bulan September'         ,   1                                        , 100000                                     ,   '2022-08-02');";
        db.execSQL(sql);


        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('3'                                        ,    5                              ,  'Pembelian Mesin Cuci secara Tunai dan dengan wesel 6 bulan, 12%'         ,   0        , 2500000                                      ,   '2022-08-03');";
        db.execSQL(sql);

        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('3'                                        ,    1                              ,  'Pembelian Mesin Cuci secara Tunai dan dengan wesel 6 bulan, 12%'          ,   1       , 1000000                                     ,   '2022-08-03');";
        db.execSQL(sql);

        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('3'                                        ,    6                              ,  'Pembelian Mesin Cuci secara Tunai dan dengan wesel 6 bulan, 12%'          ,   1       , 1500000                                     ,   '2022-08-03');";
        db.execSQL(sql);

        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('4'                                        ,    4                              ,  'Pembayaran premi asuransu untuk satu tahun'         ,   0                             , 120000                                      ,   '2022-08-04');";
        db.execSQL(sql);

        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('4'                                        ,    1                              ,  'Pembayaran premi asuransu untuk satu tahun'         ,   1                                        , 120000                                     ,   '2022-08-04');";
        db.execSQL(sql);


        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('5'                                        ,    3                              ,  'Pembelian Perlengkapan secara kredit'         ,   0                                   , 300000                                      ,   '2022-08-05');";
        db.execSQL(sql);

        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('5'                                        ,    7                              ,  'Pembelian Perlengkapan secara kredit'         ,   1                                   , 300000                                     ,   '2022-08-05');";
        db.execSQL(sql);


        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('10'                                       ,    12                              ,  'Pembayaran Iklan surat'         ,   0                                   , 20000                                      ,   '2022-08-06');";
        db.execSQL(sql);

        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('10'                                        ,    7                              ,  'Pembayaran Iklan surat'         ,   1                                   , 20000                                     ,   '2022-08-06');";
        db.execSQL(sql);



        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('12'                                       ,    1                              ,  'Pendapatan Penatu'         ,   0                                   , 620000                                      ,   '2022-08-07');";
        db.execSQL(sql);

        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('12'                                        ,    11                              ,  'Pendapatan Penatu'         ,   1                                   , 620000                                     ,   '2022-08-07');";
        db.execSQL(sql);


        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('14'                                       ,    14                              ,  'Pembayaran Gaji Pegawai'         ,   0                                   , 30000                                      ,   '2022-08-08');";
        db.execSQL(sql);

        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('14'                                        ,    1                              ,  'Pembayaran Gaji Pegawai'         ,   1                                   , 30000                                     ,   '2022-08-08');";
        db.execSQL(sql);


        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('15'                                       ,    1                              ,  'Penerimaan penatu di muka'         ,   0                                   , 100000                                      ,   '2022-08-09');";
        db.execSQL(sql);

        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('15'                                        ,    8                              ,  'Penerimaan penatu di muka'         ,   1                                   , 100000                                     ,   '2022-08-09');";
        db.execSQL(sql);


        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('20'                                       ,    10                              ,  'Prive Budiman'         ,   0                                   , 70000                                      ,   '2022-08-10');";
        db.execSQL(sql);

        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('20'                                       ,    1                              ,  'Prive Budiman'         ,   1                                   , 70000                                     ,   '2022-08-10');";
        db.execSQL(sql);


        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('25'                                       ,    2                              ,  'Penyerahan cucian secara kredit oleh ibu marini'         ,   0                                   , 45000                                      ,   '2022-08-11');";
        db.execSQL(sql);

        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('25'                                       ,    11                              ,  'Penyerahan cucian secara kredit oleh ibu marini'         ,   1                                   , 45000                                     ,   '2022-08-11');";
        db.execSQL(sql);


        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('27'                                       ,    14                              ,  'Pembayaran Gaji Pegawai'         ,   0                                   , 35000                                      ,   '2022-08-12');";
        db.execSQL(sql);

        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('27'                                       ,    1                              ,  'Pembayaran Gaji Pegawai'         ,   1                                   , 35000                                     ,   '2022-08-12');";
        db.execSQL(sql);

        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('30'                                       ,    1                              ,  'penerimaan kas dari ibu marini'         ,   0                                   , 25000                                      ,   '2022-08-13');";
        db.execSQL(sql);

        sql = "INSERT INTO " + TransactionDAO.TBL_TRANSACTION +
                "       ( " + TransactionDAO.FLD_TRANSACTION_NO + " , " + TransactionDAO.FLD_AKUN_ID + ", " + TransactionDAO.FLD_TRANSACTION_NOTE + ", " + TransactionDAO.FLD_TRANSACTION_TYPE + ", " + TransactionDAO.FLD_TRANSACTION_VALUE + ",  " + TransactionDAO.FLD_TRANSACTION_DATE + ") " +
                "VALUES ('30'                                       ,    2                              ,  'penerimaan kas dari ibu marini'         ,   1                                   , 25000                                     ,   '2022-08-13');";
        db.execSQL(sql);



        sql = "create table " + DepresiasiDAO.TBL_DEPRESIASI + " (" +
                DepresiasiDAO.FLD_DEPRESIASI_ID + " INTEGER PRIMARY KEY   AUTOINCREMENT, " +
                DepresiasiDAO.FLD_TRANSACTION_NO + " varchar(20), " +
                DepresiasiDAO.FLD_HARGA_PEROLEHAN + " bigint(20), " +
                DepresiasiDAO.FLD_AKUN_ID + " integer, " +
                DepresiasiDAO.FLD_NILAI_SISA + " bigint(20), " +
                DepresiasiDAO.FLD_UMUR_EKONOMI + " bigint(20), " +
                DepresiasiDAO.FLD_DEPRESIASI_VALUE + " bigint(20) " +
                ");";
        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);




    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub


    }

}