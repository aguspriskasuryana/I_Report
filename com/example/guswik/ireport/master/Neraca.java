package com.example.guswik.ireport.master;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;


/**
 * Created by GUSWIK on 7/21/2017.
 */
public class Neraca extends Fragment {

    public Neraca(){}
    View rootView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    protected Cursor cursor;
    DataHelper dbHelper;

    Spinner spinnerNeraca;

    private DatePickerDialog datePickerDialog,datePickerDialog2;
    private SimpleDateFormat dateFormatter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.neraca, container, false);
        getActivity().setTitle("Neraca");

        dbHelper = new DataHelper(getActivity());

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);



            RefreshListNeraca();


        return rootView;
    }




    public void RefreshListNeraca(){
        String[] daftarAsset ;
        String[] daftarKewajiban ;
        Vector data ;

        TableLayout tableLayout = new TableLayout(getActivity());
        tableLayout = (TableLayout) rootView.findViewById(R.id.tableIdNeraca);
        tableLayout.removeAllViews();

        String whereClause = " 1 = 1 " ;

        daftarAsset = AkunDAO.getListArrayOnlyId(dbHelper, 0, 0, " ("+AkunDAO.FLD_AKUN_TYPE_ID + " = " + 1+" OR "+AkunDAO.FLD_AKUN_TYPE_ID + " = " + 2+") AND "+AkunDAO.FLD_AKUN_ID+" != "+ 18, ""+AkunDAO.FLD_AKUN_NO );
        daftarKewajiban = AkunDAO.getListArrayOnlyId(dbHelper, 0, 0, ""+AkunDAO.FLD_AKUN_TYPE_ID + " = " + 3, ""+AkunDAO.FLD_AKUN_NO);

        TableLayout.LayoutParams parameterTableLayout = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);

        TableRow TRHeader = new TableRow(getActivity());
        TRHeader.setBackgroundColor(Color.DKGRAY);
        TRHeader.setLayoutParams(parameterTableLayout);

        TableRow.LayoutParams parameterTableRowHeader = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);

        parameterTableRowHeader.setMargins(4, 4, 4, 4);

        TextView headerKeterangan = new TextView(getActivity());
        headerKeterangan.setText(" ASET ");
        headerKeterangan.setTextColor(Color.BLACK);
        //TV.setPadding(2, 25, 2, 25);
        headerKeterangan.setGravity(Gravity.CENTER);
        headerKeterangan.setBackgroundColor(Color.DKGRAY);
        TRHeader.addView(headerKeterangan, parameterTableRowHeader);


        TextView headerDebit = new TextView(getActivity());
        headerDebit.setText(" NILAI ");
        headerDebit.setTextColor(Color.BLACK);
        //TV.setPadding(2, 25, 2, 25);
        headerDebit.setGravity(Gravity.CENTER);
        headerDebit.setBackgroundColor(Color.DKGRAY);
        TRHeader.addView(headerDebit, parameterTableRowHeader);

        double nilaiSaldo = 0;

        tableLayout.addView(TRHeader);

        double totalAsset = 0;
        boolean bDebit = false;

        for (int i =0; i<daftarAsset.length; i++ ){

            String dataString = daftarAsset[i];
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

            double value = TransactionDAO.getValueNeraca(dbHelper, akunId);

            TextView txtValue1 = new TextView(getActivity());
            txtValue1.setText(String.format("%,.2f", (value)));
            txtValue1.setTextColor(Color.DKGRAY);
            //TV.setPadding(2, 25, 2, 25);
            txtValue1.setGravity(Gravity.CENTER);
            txtValue1.setBackgroundColor(Color.GRAY);
            TR.addView(txtValue1, parameterTableRow);

            tableLayout.addView(TR);

            DepresiasiEntity depresiasiEntity = new DepresiasiEntity();
            depresiasiEntity = DepresiasiDAO.getByAkunId(dbHelper,akunId);
            if(depresiasiEntity.getIdDepresiasi() > 0){

                TableRow TRD = new TableRow(getActivity());
                TRD.setBackgroundColor(Color.GRAY);
                TRD.setLayoutParams(parameterTableLayout);

                TableRow.LayoutParams parameterTableRowD = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
                parameterTableRowD.setMargins(4, 4, 4, 4);

                TextView txtAkunD = new TextView(getActivity());
                txtAkunD.setText("->Depresiasi " + namaAkun);
                txtAkunD.setTextColor(Color.DKGRAY);
                txtAkunD.setBackgroundColor(Color.GRAY);
                TRD.addView(txtAkunD, parameterTableRow);

                TextView txtValueD = new TextView(getActivity());
                txtValueD.setText("("+String.format("%,.2f", (depresiasiEntity.getValueDepresiasi()))+")");
                txtValueD.setTextColor(Color.DKGRAY);
                //TV.setPadding(2, 25, 2, 25);
                txtValueD.setGravity(Gravity.CENTER);
                txtValueD.setBackgroundColor(Color.GRAY);
                TRD.addView(txtValueD, parameterTableRow);

                tableLayout.addView(TRD);
                value = value - depresiasiEntity.getValueDepresiasi();


                TableRow TRV = new TableRow(getActivity());
                TRV.setBackgroundColor(Color.GRAY);
                TRV.setLayoutParams(parameterTableLayout);

                TableRow.LayoutParams parameterTableRowV = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
                parameterTableRowV.setMargins(4, 4, 4, 4);

                TextView txtAkunV = new TextView(getActivity());
                txtAkunV.setText("->Nilai Buku " + namaAkun);
                txtAkunV.setTextColor(Color.DKGRAY);
                txtAkunV.setBackgroundColor(Color.GRAY);
                TRV.addView(txtAkunV, parameterTableRow);

                TextView txtValueV = new TextView(getActivity());
                txtValueV.setText(""+String.format("%,.2f", (value))+"");
                txtValueV.setTextColor(Color.DKGRAY);
                //TV.setPadding(2, 25, 2, 25);
                txtValueV.setGravity(Gravity.CENTER);
                txtValueV.setBackgroundColor(Color.GRAY);
                TRV.addView(txtValueV, parameterTableRow);

                tableLayout.addView(TRV);


            }

            totalAsset += value;

        }

//menghitung total
        {

            TableRow TR = new TableRow(getActivity());
            TR.setBackgroundColor(Color.GRAY);
            TR.setLayoutParams(parameterTableLayout);

            TableRow.LayoutParams parameterTableRow = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
            parameterTableRow.setMargins(4, 4, 4, 4);

            TextView txtAkun = new TextView(getActivity());
            txtAkun.setText("        Total ASET ");
            txtAkun.setTextColor(Color.DKGRAY);
            txtAkun.setBackgroundColor(Color.GRAY);
            TR.addView(txtAkun, parameterTableRow);


            TextView txtValue1 = new TextView(getActivity());
            txtValue1.setText(String.format("%,.2f", totalAsset));
            txtValue1.setTextColor(Color.BLACK);
            //TV.setPadding(2, 25, 2, 25);
            txtValue1.setGravity(Gravity.CENTER);
            txtValue1.setBackgroundColor(Color.GRAY);
            TR.addView(txtValue1, parameterTableRow);


            tableLayout.addView(TR);

        }



        {

            TableRow TR = new TableRow(getActivity());
            TR.setBackgroundColor(Color.DKGRAY);
            TR.setLayoutParams(parameterTableLayout);

            TableRow.LayoutParams parameterTableRow = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
            parameterTableRow.setMargins(4, 4, 4, 4);

            TextView txtAkun = new TextView(getActivity());
            txtAkun.setText("        Total Kewajiban ");
            txtAkun.setTextColor(Color.BLACK);
            txtAkun.setBackgroundColor(Color.DKGRAY);
            TR.addView(txtAkun, parameterTableRow);


            TextView txtValue1 = new TextView(getActivity());
            txtValue1.setText("");
            txtValue1.setTextColor(Color.BLACK);
            //TV.setPadding(2, 25, 2, 25);
            txtValue1.setGravity(Gravity.CENTER);
            txtValue1.setBackgroundColor(Color.DKGRAY);
            TR.addView(txtValue1, parameterTableRow);


            tableLayout.addView(TR);

        }


        double totalKewajiban = 0;
        boolean bDebitB = false;

        for (int i =0; i<daftarKewajiban.length; i++ ){

            String dataString = daftarKewajiban[i];
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

            double value = TransactionDAO.getValueNeraca(dbHelper, akunId);

            totalKewajiban += value;

                TextView txtValue2 = new TextView(getActivity());
                txtValue2.setText(""+ String.format("%,.2f", (Math.abs(value))) );
                txtValue2.setTextColor(Color.DKGRAY);
                txtValue2.setGravity(Gravity.CENTER);
                txtValue2.setBackgroundColor(Color.GRAY);
                TR.addView(txtValue2, parameterTableRow);


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
            txtAkun.setText("        Total Kewajiban ");
            txtAkun.setTextColor(Color.DKGRAY);
            txtAkun.setBackgroundColor(Color.GRAY);
            TR.addView(txtAkun, parameterTableRow);


                TextView txtValue1 = new TextView(getActivity());

                txtValue1.setText(String.format("%,.2f", (Math.abs(totalKewajiban))));
                txtValue1.setTextColor(Color.BLACK);
                //TV.setPadding(2, 25, 2, 25);
                txtValue1.setGravity(Gravity.CENTER);
                txtValue1.setBackgroundColor(Color.GRAY);
                TR.addView(txtValue1, parameterTableRow);

            tableLayout.addView(TR);

        }

        String[] daftarModal ;
        String[] daftarPendapatan ;
        String[] daftarBeban ;
        String[] daftarPrive ;


        daftarModal = AkunDAO.getListArrayOnlyId(dbHelper, 0, 0, ""+AkunDAO.FLD_AKUN_TYPE_ID + " = " + 4, ""+AkunDAO.FLD_AKUN_NO);
        daftarPendapatan = AkunDAO.getListArrayOnlyId(dbHelper, 0, 0, ""+AkunDAO.FLD_AKUN_TYPE_ID + " = " + 5, ""+AkunDAO.FLD_AKUN_NO);
        daftarBeban = AkunDAO.getListArrayOnlyId(dbHelper, 0, 0, ""+AkunDAO.FLD_AKUN_TYPE_ID + " = " + 6, ""+AkunDAO.FLD_AKUN_NO);
        daftarPrive = AkunDAO.getListArrayOnlyId(dbHelper, 0, 0, ""+AkunDAO.FLD_AKUN_TYPE_ID + " = " + 7, ""+AkunDAO.FLD_AKUN_NO);


        double labaRugi =  0 ;

        double totalModal = 0;
        for (int i =0; i<daftarModal.length; i++ ) {

            String dataString = daftarModal[i];
            long akunId = 0;
            try {
                akunId = Long.parseLong(dataString);
            } catch (Exception e) {
            }

            double value = TransactionDAO.getValueNeraca(dbHelper, akunId);

            totalModal += value;
        }


        double totalPendapatan = 0;
        for (int i =0; i<daftarPendapatan.length; i++ ) {

            String dataString = daftarPendapatan[i];
            long akunId = 0;
            try {
                akunId = Long.parseLong(dataString);
            } catch (Exception e) {
            }

            double value = TransactionDAO.getValueNeraca(dbHelper, akunId);

            totalPendapatan += value;
        }

        double totalBeban = 0;

        for (int i =0; i<daftarBeban.length; i++ ) {

            String dataString = daftarBeban[i];
            long akunId = 0;
            try {
                akunId = Long.parseLong(dataString);
            } catch (Exception e) {
            }

            double value = TransactionDAO.getValueNeraca(dbHelper, akunId);

            totalBeban += value;
        }
        labaRugi = (totalPendapatan+totalBeban);


        double totalPrive = 0;

        for (int i =0; i<daftarPrive.length; i++ ) {

            String dataString = daftarPrive[i];
            long akunId = 0;
            try {
                akunId = Long.parseLong(dataString);
            } catch (Exception e) {
            }

            double value = TransactionDAO.getValueNeraca(dbHelper, akunId);

            totalPrive += value;
        }



//menghitung total Modal
        {

            TableRow TR = new TableRow(getActivity());
            TR.setBackgroundColor(Color.GRAY);
            TR.setLayoutParams(parameterTableLayout);

            TableRow.LayoutParams parameterTableRow = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
            parameterTableRow.setMargins(4, 4, 4, 4);

            TextView txtAkun = new TextView(getActivity());
            txtAkun.setText("        Total Modal ");
            txtAkun.setTextColor(Color.BLACK);
            txtAkun.setBackgroundColor(Color.GRAY);
            TR.addView(txtAkun, parameterTableRow);

            TextView txtValue2 = new TextView(getActivity());

            txtValue2.setText(""+String.format("%,.2f", ((Math.abs(((totalModal)+(totalPendapatan+totalBeban))))-(Math.abs((totalPrive))))));
            txtValue2.setTextColor(Color.BLACK);
            txtValue2.setGravity(Gravity.CENTER);
            txtValue2.setBackgroundColor(Color.GRAY);

            TR.addView(txtValue2, parameterTableRow);

            tableLayout.addView(TR);

        }


//menghitung total Modal + kewajiban
        {

            TableRow TR = new TableRow(getActivity());
            TR.setBackgroundColor(Color.DKGRAY);
            TR.setLayoutParams(parameterTableLayout);

            TableRow.LayoutParams parameterTableRow = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
            parameterTableRow.setMargins(4, 4, 4, 4);

            TextView txtAkun = new TextView(getActivity());
            txtAkun.setText("        Total Modal + Kewajiban ");
            txtAkun.setTextColor(Color.BLACK);
            txtAkun.setBackgroundColor(Color.DKGRAY);
            TR.addView(txtAkun, parameterTableRow);


            TextView txtValue2 = new TextView(getActivity());


            txtValue2.setText(            String.format("%,.2f", ((Math.abs(totalKewajiban))+((Math.abs(((totalModal)+(totalPendapatan+totalBeban))))-(Math.abs((totalPrive)))))));
            txtValue2.setTextColor(Color.BLACK);
            txtValue2.setGravity(Gravity.CENTER);
            txtValue2.setBackgroundColor(Color.DKGRAY);

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