package com.example.guswik.ireport.master;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guswik.ireport.Formater;
import com.example.guswik.ireport.R;
import com.example.guswik.ireport.database.DataHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;


/**
 * Created by GUSWIK on 7/21/2017.
 */
public class Jurnal extends Fragment {

    public Jurnal(){}
    View rootView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    Button buttonSearch;
    protected Cursor cursor;
    DataHelper dbHelper;

    EditText editTextDateTo,editTextDateFrom;


    private DatePickerDialog datePickerDialog,datePickerDialog2;
    private SimpleDateFormat dateFormatter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.jurnal, container, false);
        getActivity().setTitle("Jurnal ");

        dbHelper = new DataHelper(getActivity());

        buttonSearch = (Button) rootView.findViewById(R.id.buttonSearchJurnal);
        editTextDateFrom = (EditText) rootView.findViewById(R.id.editTextDateJurnalFrom);
        editTextDateTo = (EditText) rootView.findViewById(R.id.editTextDateJurnalTo);
        editTextDateFrom.setInputType(InputType.TYPE_NULL);
        editTextDateFrom.requestFocus();
        editTextDateTo.setInputType(InputType.TYPE_NULL);
        editTextDateTo.requestFocus();


        if ( editTextDateFrom.getText().toString() == null ||  editTextDateFrom.getText().toString().equals("")){
            editTextDateFrom.setText(Formater.getDateFirstDayOfTheMonth());
        }
        if (editTextDateTo.getText().toString() == null || editTextDateTo.getText().toString().equals("")){
            editTextDateTo.setText(Formater.getDateLastDayOfTheMonth());
        }

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
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
        tableLayout = (TableLayout) rootView.findViewById(R.id.tableId);
        tableLayout.removeAllViews();

        String whereClause = "" ;

        try{
            String dateFrom = editTextDateFrom.getText().toString();
            String dateTo = editTextDateTo.getText().toString();

            if (dateFrom != null && !dateFrom.equals("") && dateTo != null && !dateTo.equals("") ){
                whereClause += "( "+TransactionDAO.FLD_TRANSACTION_DATE + " BETWEEN '" +dateFrom+"' AND '"+dateTo+"' ) AND ";

            }

            whereClause += " ( TRANS."+TransactionDAO.FLD_AKUN_ID +" != '18'  AND TRANS."+TransactionDAO.FLD_AKUN_ID +" != '19' ) ";
        }catch (Exception e){
            System.out.print(e);
        }

        daftar = TransactionDAO.getListArrayNoTransaksiuntukJurnal(dbHelper, 0, 0, whereClause, "");

        TableLayout.LayoutParams parameterTableLayout = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);

        TableRow TRHeader = new TableRow(getActivity());
        TRHeader.setBackgroundColor(Color.DKGRAY);
        TRHeader.setLayoutParams(parameterTableLayout);

        TableRow.LayoutParams parameterTableRowHeader = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);

        parameterTableRowHeader.setMargins(4, 4, 4, 4);

        TextView headerNoTransaksi = new TextView(getActivity());
        headerNoTransaksi.setText(" NO TRANS ");
        headerNoTransaksi.setTextColor(Color.BLACK);
        headerNoTransaksi.setGravity(Gravity.CENTER);
        headerNoTransaksi.setBackgroundColor(Color.DKGRAY);
        TRHeader.addView(headerNoTransaksi, parameterTableRowHeader);


        TextView headerDateTransaksi = new TextView(getActivity());
        headerDateTransaksi.setText(" DATE TRANS ");
        headerDateTransaksi.setTextColor(Color.BLACK);
        headerDateTransaksi.setGravity(Gravity.CENTER);
        headerDateTransaksi.setBackgroundColor(Color.DKGRAY);
        TRHeader.addView(headerDateTransaksi, parameterTableRowHeader);


        TextView headerAkun = new TextView(getActivity());
        headerAkun.setText(" AKUN ");
        headerAkun.setTextColor(Color.BLACK);
        //TV.setPadding(2, 25, 2, 25);
        headerAkun.setGravity(Gravity.CENTER);
        headerAkun.setBackgroundColor(Color.DKGRAY);
        TRHeader.addView(headerAkun, parameterTableRowHeader);

        TextView headerAkunNo = new TextView(getActivity());
        headerAkunNo.setText(" NO AKUN");
        headerAkunNo.setTextColor(Color.BLACK);
        //TV.setPadding(2, 25, 2, 25);
        headerAkunNo.setGravity(Gravity.CENTER);
        headerAkunNo.setBackgroundColor(Color.DKGRAY);
        TRHeader.addView(headerAkunNo, parameterTableRowHeader);

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


        tableLayout.addView(TRHeader);


        for (int i =0; i<daftar.length; i++ ){

            String dataString = daftar[i];
            String[] parts = dataString.split(":");
            String transaksiNo = parts[0];
            String akunNama = parts[1];
            String akunNo = parts[2];
            String transaksiType = parts[3];
            String transaksiValue = parts[4];
            String transaksiDate = parts[5];


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

            TextView txtDateTransaksi = new TextView(getActivity());
            txtDateTransaksi.setText(transaksiDate);
            txtDateTransaksi.setTextColor(Color.DKGRAY);
            //TV.setPadding(2, 25, 2, 25);
            txtDateTransaksi.setGravity(Gravity.CENTER);
            txtDateTransaksi.setBackgroundColor(Color.GRAY);
            TR.addView(txtDateTransaksi, parameterTableRow);

            TextView txtAkun = new TextView(getActivity());
            txtAkun.setText( (transaksiType.equals("0") ? akunNama : "     "+akunNama ));
            txtAkun.setTextColor(Color.DKGRAY);
            txtAkun.setBackgroundColor(Color.GRAY);
            TR.addView(txtAkun, parameterTableRow);

            TextView txtAkunNo = new TextView(getActivity());
            txtAkunNo.setText(akunNo);
            txtAkunNo.setTextColor(Color.DKGRAY);
            //TV.setPadding(2, 25, 2, 25);
            txtAkunNo.setGravity(Gravity.CENTER);
            txtAkunNo.setBackgroundColor(Color.GRAY);
            TR.addView(txtAkunNo, parameterTableRow);

            if (transaksiType.equals("0")){
                TextView txtValue1 = new TextView(getActivity());
                txtValue1.setText( ""+ transaksiValue);
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
                txtValue2.setText(  ""+ transaksiValue );
                txtValue2.setTextColor(Color.DKGRAY);
                //TV.setPadding(2, 25, 2, 25);
                txtValue2.setGravity(Gravity.CENTER);
                txtValue2.setBackgroundColor(Color.GRAY);
                TR.addView(txtValue2, parameterTableRow);
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