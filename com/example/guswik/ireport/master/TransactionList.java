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
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.guswik.ireport.Formater;
import com.example.guswik.ireport.R;
import com.example.guswik.ireport.database.DataHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;


public class TransactionList extends Fragment {

    public TransactionList(){}
    View rootView, rootViewAd;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    protected Cursor cursor;
    DataHelper dbHelper;
    ListView ListViewTransaction;
    String[] daftar;
    EditText editTextDate;
    Button bAdd;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    Vector data = null;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.transaction_list, container, false);

        getActivity().setTitle("Transaction");

        dbHelper = new DataHelper(getActivity());

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        editTextDate = (EditText) rootView.findViewById(R.id.editTextDate);

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showDateDialog();
            }
        });

        RefreshList();
        return rootView;
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

        daftar = TransactionDAO.getListArray(dbHelper, 0,0, whereClause,TransactionDAO.FLD_TRANSACTION_DATE + " ASC ");
        data = TransactionDAO.getList(dbHelper, 0, 0, whereClause, TransactionDAO.FLD_TRANSACTION_DATE  + " ASC ");

        ArrayAdapter adapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftar);

        ListViewTransaction = (ListView)rootView.findViewById(R.id.listViewTransaction);
        ListViewTransaction.setAdapter(adapter);
        ListViewTransaction.setSelected(true);
        ListViewTransaction.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {

                final TransactionEntity TransactionEntityX = (TransactionEntity) data.get(arg2);

                final CharSequence[] dialogitem = {"Update Transaction", "Hapus Transaction"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:

                                Bundle bundle = new Bundle();
                                bundle.putString("transactionId", ""+TransactionEntityX.getIdTransaction()); // Put anything what you want

                                Transaction transaction = new Transaction();
                                transaction.setArguments(bundle);

                                getFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.frame_container, transaction)
                                        .commit();

                                break;
                            case 1:

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
