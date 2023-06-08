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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;


/**
 * Created by GUSWIK on 7/21/2017.
 */
public class LabaRugi extends Fragment {

    public LabaRugi(){}
    View rootView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    protected Cursor cursor;
    DataHelper dbHelper;

    Spinner spinnerLabaRugi;

    EditText editTextDateTo,editTextDateFrom;
    Button buttonLabaRugi;

    private DatePickerDialog datePickerDialog,datePickerDialog2;
    private SimpleDateFormat dateFormatter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.labarugi, container, false);
        getActivity().setTitle("Laba Rugi");

        dbHelper = new DataHelper(getActivity());

        editTextDateFrom = (EditText) rootView.findViewById(R.id.editTextDateLabaRugiFrom);
        editTextDateTo = (EditText) rootView.findViewById(R.id.editTextDateLabaRugiTo);
        buttonLabaRugi = (Button) rootView.findViewById(R.id.buttonSearchLabaRugi);

        if (editTextDateFrom.getText().toString() == null || editTextDateFrom.getText().toString().equals("")){
            editTextDateFrom.setText(Formater.getDateFirstDayOfTheMonth());
        }
        if (editTextDateTo.getText().toString() == null || editTextDateTo.getText().toString().equals("")){
            editTextDateTo.setText(Formater.getDateLastDayOfTheMonth());
        }

        editTextDateFrom.setInputType(InputType.TYPE_NULL);
        editTextDateFrom.requestFocus();
        editTextDateTo.setInputType(InputType.TYPE_NULL);
        editTextDateTo.requestFocus();

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        buttonLabaRugi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                RefreshListLabaRugi();

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


        if (((editTextDateFrom.getText().toString() == null || editTextDateFrom.getText().toString().equals(""))) && ((editTextDateTo.getText().toString() == null || editTextDateTo.getText().toString().equals("")))) {
            RefreshListLabaRugi();
        }

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
                //RefreshListLabaRugi();
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
                //RefreshListLabaRugi();
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog2.show();
    }

    public void RefreshListLabaRugi(){
        String[] daftarPendapatan ;
        String[] daftarBeban ;
        Vector data ;

        TableLayout tableLayout = new TableLayout(getActivity());
        tableLayout = (TableLayout) rootView.findViewById(R.id.tableIdLabaRugi);
        tableLayout.removeAllViews();

        String whereClause = " 1 = 1 " ;


        Date dateFromSearch = new Date();
        Date dateToSearch = new Date();
        try{
            String dateFrom = editTextDateFrom.getText().toString();
            String dateTo = editTextDateTo.getText().toString();

            dateFromSearch = Formater.getDateFromString(dateFrom);
            dateToSearch = Formater.getDateFromString(dateTo);
        }catch (Exception e){
            System.out.print(e);
        }

        daftarPendapatan = AkunDAO.getListArrayOnlyId(dbHelper, 0, 0, ""+AkunDAO.FLD_AKUN_TYPE_ID + " = " + 5, ""+AkunDAO.FLD_AKUN_NO );
        daftarBeban = AkunDAO.getListArrayOnlyId(dbHelper, 0, 0, ""+AkunDAO.FLD_AKUN_TYPE_ID + " = " + 6, ""+AkunDAO.FLD_AKUN_NO);

        TableLayout.LayoutParams parameterTableLayout = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);

        TableRow TRHeader = new TableRow(getActivity());
        TRHeader.setBackgroundColor(Color.DKGRAY);
        TRHeader.setLayoutParams(parameterTableLayout);

        TableRow.LayoutParams parameterTableRowHeader = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);

        parameterTableRowHeader.setMargins(4, 4, 4, 4);

        TextView headerKeterangan = new TextView(getActivity());
        headerKeterangan.setText(" KETERANGAN ");
        headerKeterangan.setTextColor(Color.BLACK);
        //TV.setPadding(2, 25, 2, 25);
        headerKeterangan.setGravity(Gravity.CENTER);
        headerKeterangan.setBackgroundColor(Color.DKGRAY);
        TRHeader.addView(headerKeterangan, parameterTableRowHeader);


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
        double nilaiSaldo = 0;

        tableLayout.addView(TRHeader);

        double totalPendapatan = 0;
        boolean bDebit = false;

        for (int i =0; i<daftarPendapatan.length; i++ ){

            String dataString = daftarPendapatan[i];
            long akunId = 0 ;
            try{
                akunId = Long.parseLong(dataString);
            }catch(Exception e){
            }

            String namaAkun = AkunDAO.getNameById(dbHelper, akunId);

            TableRow TR = new TableRow(getActivity());
            TR.setBackgroundColor(Color.GRAY);
            TR.setLayoutParams(parameterTableLayout);

            TableRow.LayoutParams parameterTableRow = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
            parameterTableRow.setMargins(4, 4, 4, 4);

            TextView txtAkun = new TextView(getActivity());
            txtAkun.setText(namaAkun);
            txtAkun.setTextColor(Color.DKGRAY);
            txtAkun.setBackgroundColor(Color.GRAY);
            TR.addView(txtAkun, parameterTableRow);

            double value = TransactionDAO.getValueNeracaSaldo(dbHelper, akunId, dateFromSearch, dateToSearch);

            totalPendapatan += value;

            if (value >= 0){
                bDebit = true;
                TextView txtValue1 = new TextView(getActivity());
                txtValue1.setText(""+ String.format("%,.2f", (value))  );
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

                bDebit = false;
                TextView txtValue1 = new TextView(getActivity());
                txtValue1.setText("");
                txtValue1.setTextColor(Color.DKGRAY);
                txtValue1.setGravity(Gravity.CENTER);
                txtValue1.setBackgroundColor(Color.GRAY);
                TR.addView(txtValue1, parameterTableRow);

                TextView txtValue2 = new TextView(getActivity());
                txtValue2.setText(""+ String.format("%,.2f", (Math.abs(value))) );
                txtValue2.setTextColor(Color.DKGRAY);
                txtValue2.setGravity(Gravity.CENTER);
                txtValue2.setBackgroundColor(Color.GRAY);
                TR.addView(txtValue2, parameterTableRow);
            }

            tableLayout.addView(TR);

        }

//menghitung total
        {

            TableRow TR = new TableRow(getActivity());
            TR.setBackgroundColor(Color.GRAY);
            TR.setLayoutParams(parameterTableLayout);

            TableRow.LayoutParams parameterTableRow = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
            parameterTableRow.setMargins(4, 4, 4, 4);

            TextView txtAkun = new TextView(getActivity());
            txtAkun.setText("        Total Pendapatan ");
            txtAkun.setTextColor(Color.DKGRAY);
            txtAkun.setBackgroundColor(Color.GRAY);
            TR.addView(txtAkun, parameterTableRow);


            if (bDebit){
                TextView txtValue1 = new TextView(getActivity());
                txtValue1.setText(  ""+ String.format("%,.2f", (totalPendapatan))  );
                txtValue1.setTextColor(Color.BLACK);
                //TV.setPadding(2, 25, 2, 25);
                txtValue1.setGravity(Gravity.CENTER);
                txtValue1.setBackgroundColor(Color.GRAY);
                TR.addView(txtValue1, parameterTableRow);

                TextView txtValue2 = new TextView(getActivity());
                txtValue2.setText("");
                txtValue2.setTextColor(Color.BLACK);
                //TV.setPadding(2, 25, 2, 25);
                txtValue2.setGravity(Gravity.CENTER);
                txtValue2.setBackgroundColor(Color.GRAY);
                TR.addView(txtValue2, parameterTableRow);
            } else {
                TextView txtValue1 = new TextView(getActivity());
                txtValue1.setText("");
                txtValue1.setTextColor(Color.BLACK);
                txtValue1.setGravity(Gravity.CENTER);
                txtValue1.setBackgroundColor(Color.GRAY);
                TR.addView(txtValue1, parameterTableRow);

                TextView txtValue2 = new TextView(getActivity());
                txtValue2.setText( ""+ String.format("%,.2f", (Math.abs(totalPendapatan)))  );
                txtValue2.setTextColor(Color.BLACK);
                txtValue2.setGravity(Gravity.CENTER);
                txtValue2.setBackgroundColor(Color.GRAY);
                TR.addView(txtValue2, parameterTableRow);
            }

            tableLayout.addView(TR);

        }



        double totalBeban = 0;
        boolean bDebitB = false;

        for (int i =0; i<daftarBeban.length; i++ ){

            String dataString = daftarBeban[i];
            long akunId = 0 ;
            try{
                akunId = Long.parseLong(dataString);
            }catch(Exception e){
            }

            String namaAkun = AkunDAO.getNameById(dbHelper, akunId);

            TableRow TR = new TableRow(getActivity());
            TR.setBackgroundColor(Color.GRAY);
            TR.setLayoutParams(parameterTableLayout);

            TableRow.LayoutParams parameterTableRow = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
            parameterTableRow.setMargins(4, 4, 4, 4);

            TextView txtAkun = new TextView(getActivity());
            txtAkun.setText(namaAkun);
            txtAkun.setTextColor(Color.DKGRAY);
            txtAkun.setBackgroundColor(Color.GRAY);
            TR.addView(txtAkun, parameterTableRow);

            double value = TransactionDAO.getValueNeracaSaldo(dbHelper, akunId, dateFromSearch, dateToSearch);

            totalBeban += value;

            if (value >= 0){
                bDebit = true;
                TextView txtValue1 = new TextView(getActivity());
                txtValue1.setText( ""+ String.format("%,.2f", (value))  );
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

                bDebit = false;
                TextView txtValue1 = new TextView(getActivity());
                txtValue1.setText("");
                txtValue1.setTextColor(Color.DKGRAY);
                txtValue1.setGravity(Gravity.CENTER);
                txtValue1.setBackgroundColor(Color.GRAY);
                TR.addView(txtValue1, parameterTableRow);

                TextView txtValue2 = new TextView(getActivity());
                txtValue2.setText( ""+ String.format("%,.2f", (Math.abs(value)))  );
                txtValue2.setTextColor(Color.DKGRAY);
                txtValue2.setGravity(Gravity.CENTER);
                txtValue2.setBackgroundColor(Color.GRAY);
                TR.addView(txtValue2, parameterTableRow);
            }

            tableLayout.addView(TR);

        }

//menghitung total
        {

            TableRow TR = new TableRow(getActivity());
            TR.setBackgroundColor(Color.GRAY);
            TR.setLayoutParams(parameterTableLayout);

            TableRow.LayoutParams parameterTableRow = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
            parameterTableRow.setMargins(4, 4, 4, 4);

            TextView txtAkun = new TextView(getActivity());
            txtAkun.setText("        Total Beban ");
            txtAkun.setTextColor(Color.DKGRAY);
            txtAkun.setBackgroundColor(Color.GRAY);
            TR.addView(txtAkun, parameterTableRow);


            if (bDebit){
                TextView txtValue1 = new TextView(getActivity());
                txtValue1.setText( ""+ String.format("%,.2f", (totalBeban)) );
                txtValue1.setTextColor(Color.BLACK);
                //TV.setPadding(2, 25, 2, 25);
                txtValue1.setGravity(Gravity.CENTER);
                txtValue1.setBackgroundColor(Color.GRAY);
                TR.addView(txtValue1, parameterTableRow);

                TextView txtValue2 = new TextView(getActivity());
                txtValue2.setText("");
                txtValue2.setTextColor(Color.BLACK);
                //TV.setPadding(2, 25, 2, 25);
                txtValue2.setGravity(Gravity.CENTER);
                txtValue2.setBackgroundColor(Color.GRAY);
                TR.addView(txtValue2, parameterTableRow);
            } else {
                TextView txtValue1 = new TextView(getActivity());
                txtValue1.setText("");
                txtValue1.setTextColor(Color.BLACK);
                txtValue1.setGravity(Gravity.CENTER);
                txtValue1.setBackgroundColor(Color.GRAY);
                TR.addView(txtValue1, parameterTableRow);

                TextView txtValue2 = new TextView(getActivity());
                txtValue2.setText(""+ String.format("%,.2f", (Math.abs(totalBeban)))  );
                txtValue2.setTextColor(Color.BLACK);
                txtValue2.setGravity(Gravity.CENTER);
                txtValue2.setBackgroundColor(Color.GRAY);
                TR.addView(txtValue2, parameterTableRow);
            }

            tableLayout.addView(TR);

        }


//menghitung total Semua
        {

            TableRow TR = new TableRow(getActivity());
            TR.setBackgroundColor(Color.GRAY);
            TR.setLayoutParams(parameterTableLayout);

            TableRow.LayoutParams parameterTableRow = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
            parameterTableRow.setMargins(4, 4, 4, 4);

            TextView txtAkun = new TextView(getActivity());
            txtAkun.setText("        Total Laba Rugi ");
            txtAkun.setTextColor(Color.BLACK);
            txtAkun.setBackgroundColor(Color.GRAY);
            TR.addView(txtAkun, parameterTableRow);

            TextView txtValue1 = new TextView(getActivity());
            txtValue1.setText("");
            txtValue1.setTextColor(Color.BLACK);
            txtValue1.setGravity(Gravity.CENTER);
            txtValue1.setBackgroundColor(Color.GRAY);
            TR.addView(txtValue1, parameterTableRow);

            TextView txtValue2 = new TextView(getActivity());
            txtValue2.setText( ""+ String.format("%,.2f", (Math.abs((totalPendapatan+totalBeban))))  );
            txtValue2.setTextColor(Color.BLACK);
            txtValue2.setGravity(Gravity.CENTER);
            txtValue2.setBackgroundColor(Color.GRAY);

            TR.addView(txtValue2, parameterTableRow);

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