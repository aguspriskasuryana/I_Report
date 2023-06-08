package com.example.guswik.ireport.master;

/**
 * Created by GUSWIK on 7/16/2017.
 */

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

import com.example.guswik.ireport.R;
import com.example.guswik.ireport.database.DataHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Analysis2 extends Fragment {

    public Analysis2(){}
    View rootView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    protected Cursor cursor;
    DataHelper dbHelper;

    EditText textIdAnalysis;
    TextView textViewBBHasil, textViewSDMHasil, textViewPemHasil;

    Spinner spinnerProductId;
    Spinner spinnerAnalysisBBKetersediaan,spinnerAnalysisBBKemudahan,spinnerAnalysisBBKeberlanjutan;
    Spinner spinnerAnalysisSDMKetersediaan,spinnerAnalysisSDMKompeten;
    Spinner spinnerAnalysisPemKetersediaan,spinnerAnalysisPemTerjangkau,spinnerAnalysisPemKebutuhan;
    ListView ListViewAnalysis;
    String[] daftar;

    Vector data = null;


    // variables for our buttons.
    Button generatePDFbtn;

    // declaring width and height
    // for our PDF file.
    int pageHeight = 1120;
    int pagewidth = 792;

    // creating a bitmap variable
    // for storing our images
    Bitmap bmp, scaledbmp;

    // constant code for runtime permissions
    private static final int PERMISSION_REQUEST_CODE = 200;

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
        generatePDFbtn = (Button) rootView.findViewById(R.id.buttondownload);
        //bmp = BitmapFactory.decodeResource(getResources(), R.drawable.gfgimage);
        bmp = BitmapFactory.decodeResource(getResources(), R.layout.analysis);

        scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false);

        // below code is used for
        // checking our permissions.
        if (checkPermission()) {
            //Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }
        generatePDFbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling method to
                // generate our PDF file.
                generatePDF();
            }
        });

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
        } else {
            textViewBBHasil.setText("Investasi tidak layak dilanjutkan");
            textViewBBHasil.setBackgroundColor(0xfff00000);
        }

        if (analysisEntity.getAnalysissumberdayamanusia_ketersediaan()==1 && analysisEntity.getAnalysissumberdayamanusia_kompeten()==1 ){
            textViewSDMHasil.setText("Investasi layak dilanjutkan");
            textViewSDMHasil.setBackgroundColor(0xff008000);
        } else if  (analysisEntity.getAnalysissumberdayamanusia_ketersediaan()==1 && analysisEntity.getAnalysissumberdayamanusia_kompeten()==0 ) {
            textViewSDMHasil.setText("Investasi Perlu Peningkatan Kompentensi SDM");
            textViewSDMHasil.setBackgroundColor(0xffFFFF00);
        } else {
            textViewSDMHasil.setText("Investasi tidak layak dilanjutkan");
            textViewSDMHasil.setBackgroundColor(0xfff00000);
        }

        if (analysisEntity.getAnalysispemasaran_kebutuhan()>0 && analysisEntity.getAnalysispemasaran_terjangkau()>0 && analysisEntity.getAnalysispemasaran_ketersediaan()>0 ){
            textViewPemHasil.setText("Investasi layak dilanjutkan");
            textViewPemHasil.setBackgroundColor(0xff008000);
        } else {
            textViewPemHasil.setText("Investasi tidak layak dilanjutkan");
            textViewPemHasil.setBackgroundColor(0xfff00000);
        }


        //Toast.makeText(getActivity(), "Berhasil tersimpan Analysis ", Toast.LENGTH_LONG).show();

    }




    private void generatePDF() {
        // creating an object variable
        // for our PDF document.
        PdfDocument pdfDocument = new PdfDocument();

        // two variables for paint "paint" is used
        // for drawing shapes and we will use "title"
        // for adding text in our PDF file.
        Paint paint = new Paint();
        Paint title = new Paint();

        // we are adding page info to our PDF file
        // in which we will be passing our pageWidth,
        // pageHeight and number of pages and after that
        // we are calling it to create our PDF.
        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();

        // below line is used for setting
        // start page for our PDF file.
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        // creating a variable for canvas
        // from our page of PDF.
        Canvas canvas = myPage.getCanvas();

        // below line is used to draw our image on our PDF file.
        // the first parameter of our drawbitmap method is
        // our bitmap
        // second parameter is position from left
        // third parameter is position from top and last
        // one is our variable for paint.
        canvas.drawBitmap(scaledbmp, 56, 40, paint);

        // below line is used for adding typeface for
        // our text which we will be adding in our PDF file.
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // below line is used for setting text size
        // which we will be displaying in our PDF file.
        title.setTextSize(15);

        // below line is sued for setting color
        // of our text inside our PDF file.
        title.setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));

        // below line is used to draw text in our PDF file.
        // the first parameter is our text, second parameter
        // is position from start, third parameter is position from top
        // and then we are passing our variable of paint which is title.
        canvas.drawText("A portal for IT professionals.", 209, 100, title);
        canvas.drawText("Geeks for Geeks", 209, 80, title);

        // similarly we are creating another text and in this
        // we are aligning this text to center of our PDF file.
        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        title.setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        title.setTextSize(15);

        // below line is used for setting
        // our text to center of PDF.
        title.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("This is sample document which we have created.", 396, 560, title);

        // after adding all attributes to our
        // PDF file we will be finishing our page.
        pdfDocument.finishPage(myPage);

        // below line is used to set the name of
        // our PDF file and its path.
        File file = new File(Environment.getExternalStorageDirectory(), "GFG.pdf");

        try {
            // after creating a file name we will
            // write our PDF file to that location.
            pdfDocument.writeTo(new FileOutputStream(file));

            // below line is to print toast message
            // on completion of PDF generation.
            Toast.makeText(getActivity(), "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // below line is used
            // to handle error
            e.printStackTrace();
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close();
    }

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(getActivity(), "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Permission Denied.", Toast.LENGTH_SHORT).show();
                    //finish();
                }
            }
        }
    }

    private void callFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}
