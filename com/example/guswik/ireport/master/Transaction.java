package com.example.guswik.ireport.master;

/**
 * Created by GUSWIK on 7/16/2017.
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
import android.text.InputType;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.guswik.ireport.Formater;
import com.example.guswik.ireport.R;
import com.example.guswik.ireport.database.DataHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;

public class Transaction extends Fragment {

    public Transaction(){}
    View rootView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    protected Cursor cursor;
    DataHelper dbHelper;
    Button buttonSave;
    EditText textIdTransaction,textNoTransaction, textNamaTransaction, textValueTransaction, textDateTransaction;
    Spinner dropdown;
    RadioGroup radioTypeGroup;
    RadioButton radioDebit, radioCredit;

    ListView ListViewTransaction;
    String[] daftar;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    Vector data = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.transaction, container, false);
        getActivity().setTitle("Transaction");

        dbHelper = new DataHelper(getActivity());

        textIdTransaction = (EditText) rootView.findViewById(R.id.editTextIdTransaction);
        textNoTransaction = (EditText) rootView.findViewById(R.id.editTextNoTransaction);

        dropdown = (Spinner)rootView.findViewById(R.id.spinnerNoAkun);
        String[] items = AkunDAO.getListArrayOnlyName(dbHelper,0,0,"","");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        textNamaTransaction = (EditText) rootView.findViewById(R.id.editTextNamaTransaction);
        textValueTransaction = (EditText) rootView.findViewById(R.id.editTextValueTransaction);
        textDateTransaction = (EditText) rootView.findViewById(R.id.editTextDateTrans);
        textDateTransaction.setInputType(InputType.TYPE_NULL);
        textDateTransaction.requestFocus();
        radioTypeGroup = (RadioGroup) rootView.findViewById(R.id.radioTransactionType);
        radioDebit = (RadioButton) rootView.findViewById(R.id.radioDebit);
        radioCredit = (RadioButton) rootView.findViewById(R.id.radioCredit);


        buttonSave = (Button) rootView.findViewById(R.id.buttonSaveTransaction);

        String tIdTransaction ="";
        try{
            tIdTransaction = this.getArguments().getString("transactionId");
        } catch (Exception e){
            System.out.print(e);
        }

        if (tIdTransaction != null && !tIdTransaction.equals("")) {

            long id = 0;
            try{
                 id = Long.parseLong(tIdTransaction);
            }catch (Exception e){}

            TransactionEntity transactionEntity = new TransactionEntity();
            transactionEntity = TransactionDAO.getById(dbHelper, id);

            textIdTransaction.setText(""+transactionEntity.getIdTransaction());
            textNoTransaction.setText(""+transactionEntity.getNoTransaction());

            ArrayAdapter myAdap = (ArrayAdapter) dropdown.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition = myAdap.getPosition(""+AkunDAO.getNameById(dbHelper,transactionEntity.getIdAkun()));
            dropdown.setSelection(spinnerPosition);


            textNamaTransaction.setText(""+transactionEntity.getNoteTransaction());
            textValueTransaction.setText(""+transactionEntity.getValueTransaction());
            textDateTransaction.setText("" + Formater.getStringDate(transactionEntity.getDateTransaction()));

            if (transactionEntity.getTypeTransaction() == 0 ){
                radioDebit.setChecked(true);
            } else {
                radioCredit.setChecked(true);
            }

        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                SQLiteDatabase dbw = dbHelper.getWritableDatabase();

                long id = 0;
                try{
                   id = Long.parseLong(textIdTransaction.getText().toString());
                } catch (Exception e){
                }

                String radiovalue=((RadioButton)rootView.findViewById(radioTypeGroup.getCheckedRadioButtonId())).getText().toString();

                int type = 0;
                if (radiovalue.equals("Debit")){
                    type = 0;
                } else {
                    type = 1;
                }

                TransactionEntity transactionEntity = new TransactionEntity();
                transactionEntity.setIdTransaction(id);
                transactionEntity.setNoTransaction(textNoTransaction.getText().toString());


                long akunId = AkunDAO.getIdByName(dbHelper,dropdown.getItemAtPosition(dropdown.getSelectedItemPosition()).toString());
                transactionEntity.setIdAkun(akunId);

                transactionEntity.setNoteTransaction(textNamaTransaction.getText().toString());
                transactionEntity.setValueTransaction(Double.parseDouble(textValueTransaction.getText().toString()));
                transactionEntity.setTypeTransaction(type);
                transactionEntity.setDateTransaction(Formater.getDateFromString(textDateTransaction.getText().toString()));

                if (transactionEntity.getIdTransaction() !=0){
                    TransactionDAO.update(dbHelper, transactionEntity);
                } else {
                    TransactionDAO.add(dbHelper, transactionEntity);
                }

                Toast.makeText(getActivity(), "Berhasil tersimpan No Transaction "+ textNoTransaction.getText().toString(), Toast.LENGTH_LONG).show();

                textIdTransaction.setText("");
                textNoTransaction.setText("");
                textNamaTransaction.setText("");
                textValueTransaction.setText("");
                textDateTransaction.setText("");

                Bundle bundle = new Bundle();
                //bundle.putString("transactionId", "" + TransactionEntityX.getIdTransaction()); // Put anything what you want

                TransactionList transactionList = new TransactionList();
                transactionList.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, transactionList)
                        .commit();

                RefreshList();

            }
        });


        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        textDateTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showDateDialog();
            }
        });

        //ma = this;
        dbHelper = new DataHelper(getActivity());
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
                textDateTransaction.setText("" + dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }

    public void RefreshList(){

        daftar = TransactionDAO.getListArray(dbHelper, 0,0,"","");
        data = TransactionDAO.getList(dbHelper, 0, 0, "", "");

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
                                textIdTransaction.setText(""+TransactionEntityX.getIdTransaction());
                                textNoTransaction.setText(""+TransactionEntityX.getNoTransaction());

                                ArrayAdapter myAdap = (ArrayAdapter) dropdown.getAdapter(); //cast to an ArrayAdapter
                                int spinnerPosition = myAdap.getPosition(""+ AkunDAO.getNameById(dbHelper,TransactionEntityX.getIdAkun()));
                                dropdown.setSelection(spinnerPosition);

                                textNamaTransaction.setText(""+TransactionEntityX.getNoteTransaction());
                                textValueTransaction.setText(""+TransactionEntityX.getValueTransaction());
                                textDateTransaction.setText("" + Formater.getStringDate(TransactionEntityX.getDateTransaction()));
                                if (TransactionEntityX.getTypeTransaction() == 0 ){
                                    radioDebit.setChecked(true);
                                } else {
                                    radioCredit.setChecked(true);
                                }


                                break;
                            case 1:
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                db.execSQL("delete from trans where idtrans = '" + TransactionEntityX.getIdTransaction() + "'");
                                RefreshList();
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
