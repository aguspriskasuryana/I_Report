package com.example.guswik.ireport.master;

/**
 * Created by GUSWIK on 7/16/2017.
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guswik.ireport.MainActivity;
import com.example.guswik.ireport.R;
import com.example.guswik.ireport.database.DataHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Analysis extends Fragment {

    public Analysis(){}
    View rootView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;
    protected Cursor cursor;
    DataHelper dbHelper;

    EditText textIdAnalysis;
    TextView textViewBBHasil, textViewSDMHasil, textViewPemHasil;
    TextView textViewBBHasilstatus, textViewSDMHasilstatus, textViewPemHasilstatus;

    Spinner spinnerProductId;
    Spinner spinnerAnalysisBBKetersediaan,spinnerAnalysisBBKemudahan,spinnerAnalysisBBKeberlanjutan;
    Spinner spinnerAnalysisSDMKetersediaan,spinnerAnalysisSDMKompeten;
    Spinner spinnerAnalysisPemKetersediaan,spinnerAnalysisPemTerjangkau,spinnerAnalysisPemKebutuhan;
    ListView ListViewAnalysis;
    String[] daftar;

    Vector data = null;


    // variables for our buttons.
    Button generatePDFbtn;
    Date dateTime;
    Bitmap bitmap, scaleBitmap;
    int pageWidth = 1200;
    DateFormat dateFormat;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.analysis, container, false);
        getActivity().setTitle("Analysis");

        dbHelper = new DataHelper(getActivity());

        textIdAnalysis = (EditText) rootView.findViewById(R.id.editTextIdAnalysis);

        textViewBBHasil = (TextView) rootView.findViewById(R.id.textViewBBHasil);
        textViewSDMHasil = (TextView) rootView.findViewById(R.id.textViewSDMHasil);
        textViewPemHasil = (TextView) rootView.findViewById(R.id.textViewPemHasil);

        textViewBBHasilstatus = (TextView) rootView.findViewById(R.id.textViewBBHasilstatus);
        textViewSDMHasilstatus = (TextView) rootView.findViewById(R.id.textViewSDMHasilstatus);
        textViewPemHasilstatus = (TextView) rootView.findViewById(R.id.textViewPemHasilstatus);

        spinnerProductId = (Spinner)rootView.findViewById(R.id.spinnerProduct);
        spinnerAnalysisBBKetersediaan = (Spinner)rootView.findViewById(R.id.spinnerAnalysisBBKetersediaan);
        spinnerAnalysisBBKemudahan = (Spinner)rootView.findViewById(R.id.spinnerAnalysisBBKemudahan);
        spinnerAnalysisBBKeberlanjutan = (Spinner)rootView.findViewById(R.id.spinnerAnalysisBBKeberlanjutan);
        spinnerAnalysisSDMKetersediaan = (Spinner)rootView.findViewById(R.id.spinnerAnalysisSDMKetersediaan);
        spinnerAnalysisSDMKompeten = (Spinner)rootView.findViewById(R.id.spinnerAnalysisSDMKompeten);
        spinnerAnalysisPemKetersediaan = (Spinner)rootView.findViewById(R.id.spinnerAnalysisPemKetersediaan);
        spinnerAnalysisPemTerjangkau = (Spinner)rootView.findViewById(R.id.spinnerAnalysisPemTerjangkau);
        spinnerAnalysisPemKebutuhan = (Spinner)rootView.findViewById(R.id.spinnerAnalysisPemKebutuhan);


        String[] products = ProductDAO.getListArray(dbHelper,0,0,"","");
        ArrayAdapter<String> adapterproducts = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, products);
        spinnerProductId.setAdapter(adapterproducts);

        String[] items= new String[2];
        items[0] = "No" ;
        items[1] = "Yes" ;

        ArrayAdapter<String> adapterAnalysisBBKetersediaan = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinnerAnalysisBBKetersediaan.setAdapter(adapterAnalysisBBKetersediaan);
        ArrayAdapter<String> adapterAnalysisBBKeberlanjutan = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinnerAnalysisBBKeberlanjutan.setAdapter(adapterAnalysisBBKeberlanjutan);
        ArrayAdapter<String> adapterAnalysisBBKemudahan = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinnerAnalysisBBKemudahan.setAdapter(adapterAnalysisBBKemudahan);

        ArrayAdapter<String> adapterAnalysisSDMKetersediaan = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinnerAnalysisSDMKetersediaan.setAdapter(adapterAnalysisSDMKetersediaan);
        ArrayAdapter<String> adapterAnalysisSDMKompeten = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinnerAnalysisSDMKompeten.setAdapter(adapterAnalysisSDMKompeten);

        ArrayAdapter<String> adapterAnalysisPemKebutuhan = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinnerAnalysisPemKebutuhan.setAdapter(adapterAnalysisPemKebutuhan);
        ArrayAdapter<String> adapterAnalysisPemKetersediaan = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinnerAnalysisPemKetersediaan.setAdapter(adapterAnalysisPemKetersediaan);
        ArrayAdapter<String> adapterAnalysisPemTerjangkau = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinnerAnalysisPemTerjangkau.setAdapter(adapterAnalysisPemTerjangkau);





        final String tIdAnalysis = textIdAnalysis.getText().toString();

        spinnerAnalysisBBKetersediaan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                RefreshBBHasil();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerAnalysisBBKemudahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                RefreshBBHasil();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerAnalysisBBKeberlanjutan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                RefreshBBHasil();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerAnalysisSDMKetersediaan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                RefreshBBHasil();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerAnalysisSDMKompeten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                RefreshBBHasil();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerAnalysisPemKetersediaan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                RefreshBBHasil();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerAnalysisPemKebutuhan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                RefreshBBHasil();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerAnalysisPemTerjangkau.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                RefreshBBHasil();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerProductId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                ((TextView) parentView.getChildAt(0)).setTextColor(Color.BLUE);
                ((TextView) parentView.getChildAt(0)).setTextSize(30);
                RefreshData();
                RefreshBBHasil();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // initializing our variables.
        //cover header
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_cover);
        scaleBitmap = Bitmap.createScaledBitmap(bitmap, 1200, 450, false);
        generatePDFbtn = (Button) rootView.findViewById(R.id.buttondownload);

        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        createPDF();
        //RefreshBBHasil();
        return rootView;
    }


    public void RefreshData(){
        long productId = ProductDAO.getIdByName(dbHelper,spinnerProductId.getItemAtPosition(spinnerProductId.getSelectedItemPosition()).toString());

        AnalysisEntity analysisEntityx = new AnalysisEntity();
        analysisEntityx =  AnalysisDAO.getByProductId(dbHelper,productId);

        textIdAnalysis.setText(""+analysisEntityx.getIdAnalysis());

        ArrayAdapter myAdap = (ArrayAdapter) spinnerProductId.getAdapter(); //cast to an ArrayAdapter
        int spinnerPosition = myAdap.getPosition(""+ ProductDAO.getNameById(dbHelper,productId));

        spinnerProductId.setSelection(spinnerPosition);
        spinnerAnalysisBBKetersediaan.setSelection(analysisEntityx.getAnalysisbahanbaku_ketersediaan());
        spinnerAnalysisBBKemudahan.setSelection(analysisEntityx.getAnalysisbahanbaku_kemudahan());
        spinnerAnalysisBBKeberlanjutan.setSelection(analysisEntityx.getAnalysisbahanbaku_keberlanjutan());
        spinnerAnalysisSDMKompeten.setSelection(analysisEntityx.getAnalysissumberdayamanusia_kompeten());
        spinnerAnalysisSDMKetersediaan.setSelection(analysisEntityx.getAnalysissumberdayamanusia_ketersediaan());
        spinnerAnalysisPemKebutuhan.setSelection(analysisEntityx.getAnalysispemasaran_kebutuhan());
        spinnerAnalysisPemTerjangkau.setSelection(analysisEntityx.getAnalysispemasaran_terjangkau());
        spinnerAnalysisPemKetersediaan.setSelection(analysisEntityx.getAnalysispemasaran_ketersediaan());
        Toast.makeText(getActivity(), "Berhasil Refresh Analysis "+ProductDAO.getNameById(dbHelper,productId), Toast.LENGTH_LONG).show();

    }


    public void RefreshBBHasil(){

        AnalysisEntity analysisEntity = new AnalysisEntity();
        long productId = ProductDAO.getIdByName(dbHelper,spinnerProductId.getItemAtPosition(spinnerProductId.getSelectedItemPosition()).toString());

        analysisEntity.setIdProduct(productId);
        analysisEntity.setAnalysisbahanbaku_ketersediaan(spinnerAnalysisBBKetersediaan.getSelectedItemPosition());
        analysisEntity.setAnalysisbahanbaku_kemudahan(spinnerAnalysisBBKemudahan.getSelectedItemPosition());
        analysisEntity.setAnalysisbahanbaku_keberlanjutan(spinnerAnalysisBBKeberlanjutan.getSelectedItemPosition());
        analysisEntity.setAnalysissumberdayamanusia_ketersediaan(spinnerAnalysisSDMKetersediaan.getSelectedItemPosition());
        analysisEntity.setAnalysissumberdayamanusia_kompeten(spinnerAnalysisSDMKompeten.getSelectedItemPosition());
        analysisEntity.setAnalysispemasaran_ketersediaan(spinnerAnalysisPemKetersediaan.getSelectedItemPosition());
        analysisEntity.setAnalysispemasaran_terjangkau(spinnerAnalysisPemTerjangkau.getSelectedItemPosition());
        analysisEntity.setAnalysispemasaran_kebutuhan(spinnerAnalysisPemKebutuhan.getSelectedItemPosition());
        long idx = 1;
        try{
            idx = Long.parseLong(textIdAnalysis.getText().toString());
        } catch (Exception e){
        }
        analysisEntity.setIdAnalysis(idx);

        if (analysisEntity.getIdAnalysis() !=0 && analysisEntity.getIdProduct() !=0){
            AnalysisDAO.update(dbHelper, analysisEntity);
        } else if (analysisEntity.getIdProduct() !=0) {
            AnalysisDAO.add(dbHelper, analysisEntity);
        }
        if (analysisEntity.getAnalysisbahanbaku_ketersediaan()==1 && analysisEntity.getAnalysisbahanbaku_kemudahan()==1 && analysisEntity.getAnalysisbahanbaku_keberlanjutan()==1 ){
            textViewBBHasil.setText("Investasi layak dilanjutkan");
            textViewBBHasil.setBackgroundColor(0xff008000);
            textViewBBHasilstatus.setText("✓");
        } else {
            textViewBBHasil.setText("Investasi tidak layak dilanjutkan");
            textViewBBHasil.setBackgroundColor(0xfff00000);
            textViewBBHasilstatus.setText("X");
        }

        if (analysisEntity.getAnalysissumberdayamanusia_ketersediaan()==1 && analysisEntity.getAnalysissumberdayamanusia_kompeten()==1 ){
            textViewSDMHasil.setText("Investasi layak dilanjutkan");
            textViewSDMHasil.setBackgroundColor(0xff008000);
            textViewSDMHasilstatus.setText("✓");
        } else if  (analysisEntity.getAnalysissumberdayamanusia_ketersediaan()==1 && analysisEntity.getAnalysissumberdayamanusia_kompeten()==0 ) {
            textViewSDMHasil.setText("Investasi Perlu Peningkatan Kompentensi SDM");
            textViewSDMHasil.setBackgroundColor(0xffFFFF00);
            textViewSDMHasilstatus.setText("X");
        } else {
            textViewSDMHasil.setText("Investasi tidak layak dilanjutkan");
            textViewSDMHasil.setBackgroundColor(0xfff00000);
            textViewSDMHasilstatus.setText("X");
        }

        if (analysisEntity.getAnalysispemasaran_kebutuhan()>0 && analysisEntity.getAnalysispemasaran_terjangkau()>0 && analysisEntity.getAnalysispemasaran_ketersediaan()>0 ){
            textViewPemHasil.setText("Investasi layak dilanjutkan");
            textViewPemHasil.setBackgroundColor(0xff008000);
            textViewPemHasilstatus.setText("✓");
        } else {
            textViewPemHasil.setText("Investasi tidak layak dilanjutkan");
            textViewPemHasil.setBackgroundColor(0xfff00000);
            textViewPemHasilstatus.setText("X");
        }


        //Toast.makeText(getActivity(), "Berhasil tersimpan Analysis ", Toast.LENGTH_LONG).show();

    }


    public void createPDF() {
        generatePDFbtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View v) {

                dateTime = new Date();

                {

                    PdfDocument pdfDocument = new PdfDocument();
                    Paint paint = new Paint();
                    Paint titlePaint = new Paint();

                    PdfDocument.PageInfo pageInfo
                            = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                    PdfDocument.Page page = pdfDocument.startPage(pageInfo);

                    Canvas canvas = page.getCanvas();
                    canvas.drawBitmap(scaleBitmap, 0, 0, paint);

                    paint.setColor(Color.WHITE);
                    paint.setTextSize(30f);
                    paint.setTextAlign(Paint.Align.LEFT);
                    String comp = CompanyDAO.getNameById(dbHelper,1);
                    //canvas.drawText(comp, 1160, 40, paint);
                    paint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText("Analysis Uji Kelayakan Usaha          "+comp, 1160, 40, paint);
                    canvas.drawText("Build 1.0", 1160, 80, paint);

                    titlePaint.setTextAlign(Paint.Align.CENTER);
                    titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                    titlePaint.setColor(Color.WHITE);
                    titlePaint.setTextSize(70);
                    canvas.drawText("Hasil Analysis", pageWidth / 2, 500, titlePaint);


                    paint.setTextAlign(Paint.Align.LEFT);
                    paint.setColor(Color.BLACK);
                    paint.setTextSize(35f);

                    long productIdx = ProductDAO.getIdByName(dbHelper,spinnerProductId.getItemAtPosition(spinnerProductId.getSelectedItemPosition()).toString());

                    String prod = spinnerProductId.getSelectedItem().toString();
                    String product_type_name = ProductDAO.getProductTypeNameById(dbHelper,productIdx);
                    canvas.drawText("Nama Produk: " + prod, 20, 590, paint);
                    canvas.drawText("Kategori Produk: " + product_type_name , 20, 640, paint);

                    paint.setTextAlign(Paint.Align.RIGHT);
                    dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                    //dateFormat = new SimpleDateFormat("HH:mm:ss");
                    canvas.drawText("Waktu : " + dateFormat.format(dateTime), pageWidth - 20, 590, paint);
                    //canvas.drawText("Tanggal: " + dateFormat.format(dateTime), pageWidth - 20, 640, paint);
                    //canvas.drawText("Pukul: " + dateFormat.format(dateTime), pageWidth - 20, 690, paint);

                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(2);
                    canvas.drawRect(20, 780, pageWidth - 20, 860, paint);

                    double totalbobot = 100;

                    paint.setTextAlign(Paint.Align.LEFT);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawText("No.", 40, 830, paint);
                    canvas.drawText("Jenis Analysis", 200, 830, paint);
                    canvas.drawText("Status", 700, 830, paint);
                    canvas.drawText("Keterangan", 900, 830, paint);
                    //canvas.drawText("Kesimpulan", 1050, 830, paint);

                    canvas.drawLine(180, 790, 180, 840, paint);
                    canvas.drawLine(680, 790, 680, 840, paint);
                    canvas.drawLine(880, 790, 880, 840, paint);
                    //canvas.drawLine(1030, 790, 1030, 840, paint);

                    //Analysis Pem
                    canvas.drawText("1.", 40, 950, paint);
                    canvas.drawText("Analysis Pemasaran", 200, 950, paint);
                    canvas.drawText(textViewPemHasilstatus.getText().toString(), 700, 950, paint);
                    paint.setTextAlign(Paint.Align.RIGHT);
                    String keteranganPem = "Layak";
                    if (textViewPemHasilstatus.getText().toString() == "X"){
                        keteranganPem = "Tidak Layak";
                        totalbobot = totalbobot -10;
                    }
                    //canvas.drawText(keteranganPem, 900, 1150, paint);
                    canvas.drawText(keteranganPem, pageWidth - 40, 950, paint);

                    paint.setTextAlign(Paint.Align.LEFT);



                    //Analysis SDM
                    canvas.drawText("2.", 40, 1050, paint);
                    canvas.drawText("Analysis SDM", 200, 1050, paint);
                    canvas.drawText(textViewSDMHasilstatus.getText().toString(), 700, 1050, paint);
                    paint.setTextAlign(Paint.Align.RIGHT);
                    //
                    String keteranganSDM = "Layak";
                    if (textViewSDMHasilstatus.getText().toString() == "X"){
                        keteranganSDM = "Tidak Layak";
                        totalbobot = totalbobot -10;
                    }
                    //canvas.drawText(keteranganSDM, 900, 1050, paint);
                    canvas.drawText(keteranganSDM, pageWidth - 40, 1050, paint);

                    paint.setTextAlign(Paint.Align.LEFT);

                    //Analysis Bahan Baku
                    canvas.drawText("3.", 40, 1150, paint);
                    canvas.drawText("Analysis Bahan Baku", 200, 1150, paint);
                    canvas.drawText(textViewBBHasilstatus.getText().toString(), 700, 1150, paint);
                    paint.setTextAlign(Paint.Align.RIGHT);
                    //canvas.drawText(keteranganBB, 900, 950, paint);
                    String keteranganBB = "Layak";
                    if (textViewBBHasilstatus.getText().toString() == "X"){
                        keteranganBB = "Tidak Layak";
                        totalbobot = totalbobot -10;
                    }
                    canvas.drawText(keteranganBB, pageWidth - 40, 1150, paint);

                    paint.setTextAlign(Paint.Align.LEFT);




                    //Analysis Payback Period
                    canvas.drawText("4.", 40, 1250, paint);
                    canvas.drawText("Analysis Payback Period", 200, 1250, paint);
                    long statusPP = PaybackPeriodDAO.getStatusById(dbHelper,productIdx);
                    String statusPPs = "X";
                    String ketPPs = "Tidak Layak";
                    if (statusPP == 1){
                        statusPPs = "✓";
                        ketPPs = "Layak";
                    } else {
                        totalbobot = totalbobot -20;
                    }
                    canvas.drawText(statusPPs, 700, 1250, paint);
                    paint.setTextAlign(Paint.Align.RIGHT);

                    //canvas.drawText(keteranganPem, 900, 1150, paint);
                    canvas.drawText(ketPPs, pageWidth - 40, 1250, paint);

                    paint.setTextAlign(Paint.Align.LEFT);

//Analysis NPV
                    canvas.drawText("5.", 40, 1350, paint);
                    canvas.drawText("Analysis Net Present Value", 200, 1350, paint);
                    long statusNPV = NetPresentValueDAO.getStatusNPVById(dbHelper,productIdx);
                    String statusNPVs = "X";
                    String ketNPVs = "Tidak Layak";
                    if (statusNPV == 1){
                        statusNPVs = "✓";
                        ketNPVs = "Layak";
                    } else {
                        totalbobot = totalbobot -25;
                    }
                    canvas.drawText(statusNPVs, 700, 1350, paint);
                    paint.setTextAlign(Paint.Align.RIGHT);
                    //canvas.drawText(keteranganPem, 900, 1150, paint);
                    canvas.drawText(ketNPVs, pageWidth - 40, 1350, paint);
                    paint.setTextAlign(Paint.Align.LEFT);


                    //Analysis PI
                    canvas.drawText("6.", 40, 1450, paint);
                    canvas.drawText("Analysis Profitability Index", 200, 1450, paint);
                    long statusPI = NetPresentValueDAO.getStatusPIById(dbHelper,productIdx);
                    String statusPIs = "X";
                    String ketPIs = "Tidak Layak";
                    if (statusPI == 1){
                        statusPIs = "✓";
                        ketPIs = "Layak";
                    } else {
                        totalbobot = totalbobot -25;
                    }
                    canvas.drawText(statusPIs, 700, 1450, paint);
                    paint.setTextAlign(Paint.Align.RIGHT);
                    //canvas.drawText(keteranganPem, 900, 1150, paint);
                    canvas.drawText(ketPIs, pageWidth - 40, 1450, paint);
                    paint.setTextAlign(Paint.Align.LEFT);


                    paint.setColor(Color.rgb(247, 147, 30));
                    canvas.drawRect(680, 1550, pageWidth - 20, 1550, paint);

                    paint.setColor(Color.BLACK);
                    paint.setTextSize(50f);
                    paint.setTextAlign(Paint.Align.LEFT);
                    String Kesimpulan = "Tidak Layak Dilanjutkan";
                    if (totalbobot >70){
                        Kesimpulan = "Layak Dilanjutkan";
                    }
                    canvas.drawText(Kesimpulan, 700, 1540, paint);
                    paint.setTextAlign(Paint.Align.RIGHT);
                    //canvas.drawText("", pageWidth - 40, 1415, paint);


                    paint.setColor(Color.BLACK);
                    paint.setTextSize(10f);
                    paint.setTextAlign(Paint.Align.RIGHT);

                    canvas.drawText("Saku Bumdes", 700, 1940, paint);
                    paint.setTextAlign(Paint.Align.RIGHT);

                    pdfDocument.finishPage(page);
                    String sss = Environment.getExternalStorageDirectory().getName().toString();
                    File file = new File(Environment.getExternalStorageDirectory(), "/IReport_Analysis_"+prod+".pdf");
                    try {
                        pdfDocument.writeTo(new FileOutputStream(file));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    pdfDocument.close();
                    Toast.makeText(getActivity(), "PDF sudah dibuat", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void callFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}
