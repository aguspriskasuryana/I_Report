package com.example.guswik.ireport.master;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.guswik.ireport.Formater;
import com.example.guswik.ireport.R;
import com.example.guswik.ireport.database.DataHelper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;


/**
 * Created by GUSWIK on 7/21/2017.
 */
public class BukuBesar extends Fragment {

    public BukuBesar(){}
    View rootView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    protected Cursor cursor;
    DataHelper dbHelper;

    Spinner spinnerBukuBesar;

    EditText editTextDateTo,editTextDateFrom;
    Button buttonBukuBesar;

    private DatePickerDialog datePickerDialog,datePickerDialog2;
    private SimpleDateFormat dateFormatter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.bukubesar, container, false);
        getActivity().setTitle("Buku Besar");

        dbHelper = new DataHelper(getActivity());

        editTextDateFrom = (EditText) rootView.findViewById(R.id.editTextDateBukuBesarFrom);
        editTextDateTo = (EditText) rootView.findViewById(R.id.editTextDateBukuBesarTo);
        editTextDateFrom.setInputType(InputType.TYPE_NULL);
        editTextDateFrom.requestFocus();
        editTextDateTo.setInputType(InputType.TYPE_NULL);
        editTextDateTo.requestFocus();
        buttonBukuBesar = (Button) rootView.findViewById(R.id.buttonSearchBukuBesar);


        if ( editTextDateFrom.getText().toString() == null ||  editTextDateFrom.getText().toString().equals("")){
            editTextDateFrom.setText(Formater.getDateFirstDayOfTheMonth());
        }
        if (editTextDateTo.getText().toString() == null || editTextDateTo.getText().toString().equals("")){
            editTextDateTo.setText(Formater.getDateLastDayOfTheMonth());
        }

        spinnerBukuBesar = (Spinner)rootView.findViewById(R.id.spinnerbukubesar);
        String[] items = AkunDAO.getListArrayOnlyName(dbHelper,0,0,"","");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinnerBukuBesar.setAdapter(adapter);

        spinnerBukuBesar.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                        RefreshListJurnal();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                    //add some code here
                }
        );
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        buttonBukuBesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                RefreshListJurnal();

            }
        });

        editTextDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showDateDialog1();
            }
        });
        editTextDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showDateDialog2();
            }
        });

        RefreshListJurnal();

        return rootView;
    }

    private void showDateDialog1(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                editTextDateFrom.setText("" + dateFormatter.format(newDate.getTime()));
                //RefreshListJurnal();
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }


    private void showDateDialog2(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog2 = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                editTextDateTo.setText("" + dateFormatter.format(newDate.getTime()));
                //RefreshListJurnal();
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog2.show();
    }

    public void RefreshListJurnal(){
        String[] daftar ;
        Vector data ;

        TableLayout tableLayout = new TableLayout(getActivity());
        tableLayout = (TableLayout) rootView.findViewById(R.id.tableIdbukubesar);
        tableLayout.removeAllViews();

        String whereClause = " 1 = 1 " ;


        try{
            long akunId = AkunDAO.getIdByName(dbHelper, spinnerBukuBesar.getItemAtPosition(spinnerBukuBesar.getSelectedItemPosition()).toString());

            if (akunId != 0){
                whereClause += " AND TRANS." + TransactionDAO.FLD_AKUN_ID + " = " + akunId ;
            }

        }catch (Exception e){
            System.out.print(e);
        }


        daftar = new String[0];
        try{
            String dateFrom = editTextDateFrom.getText().toString();
            String dateTo = editTextDateTo.getText().toString();

            if (dateFrom != null && !dateFrom.equals("") && dateTo != null && !dateTo.equals("") ){
                whereClause += " AND ( TRANS." + TransactionDAO.FLD_TRANSACTION_DATE + " BETWEEN '" +dateFrom+"' AND '"+dateTo+"' ) ";
                daftar = TransactionDAO.getListArrayNoTransaksiuntukBukuBesar(dbHelper, 0, 0, whereClause, ""+TransactionDAO.FLD_TRANSACTION_DATE+" , "+TransactionDAO.FLD_TRANSACTION_TYPE+" ASC ");

            }
        }catch (Exception e){
            System.out.print(e);
        }

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);


        TableLayout.LayoutParams parameterTableLayout = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);

        TableRow TRHeader = new TableRow(getActivity());
        TRHeader.setBackgroundColor(Color.DKGRAY);
        TRHeader.setLayoutParams(parameterTableLayout);

        TableRow.LayoutParams parameterTableRowHeader = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);

        parameterTableRowHeader.setMargins(4, 4, 4, 4);

        TextView headerTgl = new TextView(getActivity());
        headerTgl.setText(" No TRANS");
        headerTgl.setTextColor(Color.BLACK);
        //TV.setPadding(2, 25, 2, 25);
        headerTgl.setGravity(Gravity.CENTER);
        headerTgl.setBackgroundColor(Color.DKGRAY);
        TRHeader.addView(headerTgl, parameterTableRowHeader);

        TextView headerKeterangan = new TextView(getActivity());
        headerKeterangan.setText(" KETERANGAN ");
        headerKeterangan.setTextColor(Color.BLACK);
        //TV.setPadding(2, 25, 2, 25);
        headerKeterangan.setGravity(Gravity.CENTER);
        headerKeterangan.setBackgroundColor(Color.DKGRAY);
        TRHeader.addView(headerKeterangan, parameterTableRowHeader);

        TextView headerTglTransaksi = new TextView(getActivity());
        headerTglTransaksi.setText(" TGL TRANS ");
        headerTglTransaksi.setTextColor(Color.BLACK);
        headerTglTransaksi.setGravity(Gravity.CENTER);
        headerTglTransaksi.setBackgroundColor(Color.DKGRAY);
        TRHeader.addView(headerTglTransaksi, parameterTableRowHeader);


        TextView headerNoTransaksi = new TextView(getActivity());
        headerNoTransaksi.setText(" NO AKUN ");
        headerNoTransaksi.setTextColor(Color.BLACK);
        headerNoTransaksi.setGravity(Gravity.CENTER);
        headerNoTransaksi.setBackgroundColor(Color.DKGRAY);
        TRHeader.addView(headerNoTransaksi, parameterTableRowHeader);

        TextView headerDebit = new TextView(getActivity());
        headerDebit.setText(" DEBIT ");
        headerDebit.setTextColor(Color.BLACK);
        //TV.setPadding(2, 25, 2, 25);
        headerDebit.setGravity(Gravity.CENTER);
        headerDebit.setBackgroundColor(Color.DKGRAY);
        TRHeader.addView(headerDebit, parameterTableRowHeader);

        TextView headerKredit = new TextView(getActivity());
        headerKredit.setText(" KREDIT ");
        headerKredit.setTextColor(Color.BLACK);
        //TV.setPadding(2, 25, 2, 25);
        headerKredit.setGravity(Gravity.CENTER);
        headerKredit.setBackgroundColor(Color.DKGRAY);
        TRHeader.addView(headerKredit, parameterTableRowHeader);

        TextView headerSaldoD = new TextView(getActivity());
        headerSaldoD.setText(" SALDO D ");
        headerSaldoD.setTextColor(Color.BLACK);
        //TV.setPadding(2, 25, 2, 25);
        headerSaldoD.setGravity(Gravity.CENTER);
        headerSaldoD.setBackgroundColor(Color.DKGRAY);
        TRHeader.addView(headerSaldoD, parameterTableRowHeader);

        TextView headerSaldoK = new TextView(getActivity());
        headerSaldoK.setText(" SALDO K");
        headerSaldoK.setTextColor(Color.BLACK);
        //TV.setPadding(2, 25, 2, 25);
        headerSaldoK.setGravity(Gravity.CENTER);
        headerSaldoK.setBackgroundColor(Color.DKGRAY);
        TRHeader.addView(headerSaldoK, parameterTableRowHeader);

        double nilaiSaldo = 0;

        tableLayout.addView(TRHeader);


        for (int i =0; i<daftar.length; i++ ){

            String dataString = daftar[i];
            String[] parts = dataString.split(":");
            String transaksiNo = parts[0];
            String transaksiNote = parts[1];
            String transaksiDate = parts[2];
            String akunNo = parts[3];
            String transaksiType = parts[4];
            String transaksiValue = parts[5];


            TableRow TR = new TableRow(getActivity());
            TR.setBackgroundColor(Color.GRAY);
            TR.setLayoutParams(parameterTableLayout);

            TableRow.LayoutParams parameterTableRow = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);

            parameterTableRow.setMargins(4, 4, 4, 4);

            TextView txtNoTransaksi = new TextView(getActivity());
            txtNoTransaksi.setText(transaksiNo);
            txtNoTransaksi.setTextColor(Color.DKGRAY);
            //TV.setPadding(2, 25, 2, 25);
            txtNoTransaksi.setGravity(Gravity.CENTER);
            txtNoTransaksi.setBackgroundColor(Color.GRAY);
            TR.addView(txtNoTransaksi, parameterTableRow);

            TextView txtAkun = new TextView(getActivity());
            txtAkun.setText(transaksiNote);
            txtAkun.setTextColor(Color.DKGRAY);
            txtAkun.setBackgroundColor(Color.GRAY);
            TR.addView(txtAkun, parameterTableRow);


            TextView txtTglTrans = new TextView(getActivity());
            txtTglTrans.setText(transaksiDate);
            txtTglTrans.setTextColor(Color.DKGRAY);
            //TV.setPadding(2, 25, 2, 25);
            txtTglTrans.setGravity(Gravity.CENTER);
            txtTglTrans.setBackgroundColor(Color.GRAY);
            TR.addView(txtTglTrans, parameterTableRow);

            TextView txtAkunNo = new TextView(getActivity());
            txtAkunNo.setText(akunNo);
            txtAkunNo.setTextColor(Color.DKGRAY);
            //TV.setPadding(2, 25, 2, 25);
            txtAkunNo.setGravity(Gravity.CENTER);
            txtAkunNo.setBackgroundColor(Color.GRAY);
            TR.addView(txtAkunNo, parameterTableRow);

            if (transaksiType.equals("0")){
                TextView txtValue1 = new TextView(getActivity());

                txtValue1.setText("" + kursIndonesia.format(Double.parseDouble(transaksiValue)));
                txtValue1.setTextColor(Color.DKGRAY);
                //TV.setPadding(2, 25, 2, 25);
                txtValue1.setGravity(Gravity.CENTER);
                txtValue1.setBackgroundColor(Color.GRAY);
                TR.addView(txtValue1, parameterTableRow);

                TextView txtValue2 = new TextView(getActivity());
                txtValue2.setText("");
                txtValue2.setTextColor(Color.DKGRAY);
                //TV.setPadding(2, 25, 2, 25);
                txtValue2.setGravity(Gravity.CENTER);
                txtValue2.setBackgroundColor(Color.GRAY);
                TR.addView(txtValue2, parameterTableRow);
            } else {
                TextView txtValue1 = new TextView(getActivity());
                txtValue1.setText("");
                txtValue1.setTextColor(Color.DKGRAY);
                //TV.setPadding(2, 25, 2, 25);
                txtValue1.setGravity(Gravity.CENTER);
                txtValue1.setBackgroundColor(Color.GRAY);
                TR.addView(txtValue1, parameterTableRow);

                TextView txtValue2 = new TextView(getActivity());
                txtValue2.setText( ""+kursIndonesia.format(Double.parseDouble(transaksiValue)));
                txtValue2.setTextColor(Color.DKGRAY);
                //TV.setPadding(2, 25, 2, 25);
                txtValue2.setGravity(Gravity.CENTER);
                txtValue2.setBackgroundColor(Color.GRAY);
                TR.addView(txtValue2, parameterTableRow);
            }

            double dTransValue = 0;
            try{
                dTransValue = Double.parseDouble(transaksiValue);
            }catch (Exception e){
            }

            if (transaksiType.equals(""+TransactionDAO.DEBIT)){
                nilaiSaldo = nilaiSaldo + dTransValue;
            } else {
                nilaiSaldo = nilaiSaldo - dTransValue;
            }

            if (nilaiSaldo>=0) {
                TextView txtValueSD = new TextView(getActivity());
                txtValueSD.setText(""+kursIndonesia.format(nilaiSaldo) );
                txtValueSD.setTextColor(Color.DKGRAY);
                txtValueSD.setGravity(Gravity.CENTER);
                txtValueSD.setBackgroundColor(Color.GRAY);
                TR.addView(txtValueSD, parameterTableRow);

                TextView txtValueSK = new TextView(getActivity());
                txtValueSK.setText("");
                txtValueSK.setTextColor(Color.DKGRAY);
                //TV.setPadding(2, 25, 2, 25);
                txtValueSK.setGravity(Gravity.CENTER);
                txtValueSK.setBackgroundColor(Color.GRAY);
                TR.addView(txtValueSK, parameterTableRow);
            } else {
                TextView txtValueSD = new TextView(getActivity());
                txtValueSD.setText("");
                txtValueSD.setTextColor(Color.DKGRAY);
                txtValueSD.setGravity(Gravity.CENTER);
                txtValueSD.setBackgroundColor(Color.GRAY);
                TR.addView(txtValueSD, parameterTableRow);

                TextView txtValueSK = new TextView(getActivity());
                txtValueSK.setText( ""+  kursIndonesia.format((Math.abs(nilaiSaldo)))  );
                txtValueSK.setTextColor(Color.DKGRAY);
                //TV.setPadding(2, 25, 2, 25);
                txtValueSK.setGravity(Gravity.CENTER);
                txtValueSK.setBackgroundColor(Color.GRAY);
                TR.addView(txtValueSK, parameterTableRow);
            }
            tableLayout.addView(TR);

        }

    }

    private void callFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}