package com.example.guswik.ireport.master;

/**
 * Created by GUSWIK on 7/20/2017.
 */

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.guswik.ireport.Formater;
import com.example.guswik.ireport.R;
import com.example.guswik.ireport.database.DataHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;


public class TransactionMultipleList extends Fragment {

    public TransactionMultipleList(){}
    View rootView, rootViewAd;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    protected Cursor cursor;
    DataHelper dbHelper;
    ListView ListViewTransaction;
    String[] daftar;
    Button buttonAdd;
    EditText editTextDate;
    Button bAdd;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    Vector data = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.transaction_multiple_list, container, false);

        getActivity().setTitle("Transaction");

        dbHelper = new DataHelper(getActivity());

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);


        buttonAdd = (Button) rootView.findViewById(R.id.buttontambah);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Bundle bundle = new Bundle();


                TransactionMultiple req = new TransactionMultiple();
                req.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, req)
                        .commit();


            }
        });
        editTextDate = (EditText) rootView.findViewById(R.id.editTextDateMult);

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showDateDialog();
            }
        });


        RefreshList();
        return rootView;
    }

    private void showDateDialog(){

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
                editTextDate.setText(""+dateFormatter.format(newDate.getTime()));
                RefreshList();
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }

    public void RefreshList(){

        String whereClause = "" ;

        try{
            String dateS = editTextDate.getText().toString();

            if (dateS != null && !dateS.equals("")){
                whereClause += TransactionDAO.FLD_TRANSACTION_DATE + " = '" +dateS+"'";
            }
        }catch (Exception e){
            System.out.print(e);
        }

        daftar = TransactionDAO.getListArray3(dbHelper, 0,0, whereClause,TransactionDAO.FLD_TRANSACTION_DATE + " ASC ");
        data = TransactionDAO.getListDistinctNo(dbHelper, 0, 0, whereClause, TransactionDAO.FLD_TRANSACTION_DATE  + " ASC ");

        ArrayAdapter adapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftar);

        ListViewTransaction = (ListView)rootView.findViewById(R.id.listViewTransactionMult);
        ListViewTransaction.setAdapter(adapter);
        ListViewTransaction.setSelected(true);
        ListViewTransaction.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {

                final TransactionEntity TransactionEntityX = (TransactionEntity) data.get(arg2);

                final CharSequence[] dialogitem = {"Details Transaction", "Delete Transaction"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:

                                Bundle bundle = new Bundle();
                                bundle.putString("transactionNo", ""+TransactionEntityX.getNoTransaction()); // Put anything what you want
                                bundle.putString("transactionNote", "" +TransactionEntityX.getNoteTransaction());
                                bundle.putString("transactionDate", "" + Formater.getStringDate(TransactionEntityX.getDateTransaction()));

                                TransactionMultiple transactionMultiple = new TransactionMultiple();
                                transactionMultiple.setArguments(bundle);

                                getFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.frame_container, transactionMultiple)
                                        .commit();

                                break;
                            case 1:

                                final CharSequence[] dialogitem2 = {"Yes", "No"};

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                builder2.setTitle("Delete Confirm");
                                builder2.setItems(dialogitem2, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        switch (item) {
                                            case 0:
                                                TransactionDAO.deleteByNoTrans(dbHelper, TransactionEntityX.getNoTransaction());
                                                Toast.makeText(getActivity(), " Delete No Transaction : " + TransactionEntityX.getNoTransaction(), Toast.LENGTH_LONG).show();
                                                RefreshList();
                                                break;
                                            case 1:

                                                break;
                                        }
                                    }
                                });
                                builder2.create().show();

                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter)ListViewTransaction.getAdapter()).notifyDataSetInvalidated();
    }

    private void callFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}
