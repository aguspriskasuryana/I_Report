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
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

public class TransactionMultiple extends Fragment {

    public TransactionMultiple(){}
    View rootView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    protected Cursor cursor;
    DataHelper dbHelper;
    Button buttonSave,buttondepresiasi;
    EditText textDateTransaction,textNoTransaction, textNamaTransaction;
    String tNoTransactionPublic ;

    double hargaDebit =0  ;
    long akunidx =0  ;

    ListView ListViewTransaction;
    String[] daftar;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    Vector data = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.transactionnew, container, false);
        getActivity().setTitle("Transaction");

        dbHelper = new DataHelper(getActivity());

        textDateTransaction = (EditText) rootView.findViewById(R.id.editTextDateTransactionMul);
        textNoTransaction = (EditText) rootView.findViewById(R.id.editTextNoTransactionMul);
        textNamaTransaction = (EditText) rootView.findViewById(R.id.editTextNamaTransactionMul);


        buttondepresiasi = (Button) rootView.findViewById(R.id.buttondepresiasi);

        buttonSave = (Button) rootView.findViewById(R.id.buttonSaveTransactionMul);

        tNoTransactionPublic = "";
        try{
            tNoTransactionPublic = this.getArguments().getString("transactionNo");
        } catch (Exception e){
            System.out.print(e);
        }
        String tNoteTransaction ="";
        try{
            tNoteTransaction = this.getArguments().getString("transactionNote");
        } catch (Exception e){
            System.out.print(e);
        }
        String tDateTransaction ="";
        try{
            tDateTransaction = this.getArguments().getString("transactionDate");
        } catch (Exception e){
            System.out.print(e);
        }
        if (tNoTransactionPublic != null && !tNoTransactionPublic.equals("")) {
            akunidx = TransactionDAO.getAkunIdByNoAndDebit(dbHelper, tNoTransactionPublic,0);
            textNoTransaction.setText(""+tNoTransactionPublic);
            textNamaTransaction.setText(""+tNoteTransaction);
            textDateTransaction.setText(""+tDateTransaction);

        } else {
            long newNo = TransactionDAO.getNewNoTrans(dbHelper);
            textNoTransaction.setText(""+newNo);

            buttondepresiasi.setEnabled(false);
        }

        buttondepresiasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Bundle bundle = new Bundle();

                bundle.putLong("tAkunIdx", akunidx);
                bundle.putString("tNamaTransaction", textNamaTransaction.getText().toString());
                bundle.putString("tDateTransaction", textDateTransaction.getText().toString());
                bundle.putString("tNoTransaction", tNoTransactionPublic);
                bundle.putDouble("transactionHarga", hargaDebit);
                Depresiasi req = new Depresiasi();
                req.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, req)
                        .commit();
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                try{
                    Spinner dropdownTrans1 = (Spinner)rootView.findViewById(R.id.spinnerTrans1);
                    long akunId1 = AkunDAO.getIdByName(dbHelper,dropdownTrans1.getItemAtPosition(dropdownTrans1.getSelectedItemPosition()).toString());
                    EditText textIdTransactionDebit1 = (EditText) rootView.findViewById(R.id.edittextTransDebit1);
                    EditText textIdTransactionCredit1 = (EditText) rootView.findViewById(R.id.edittextTransCredit1);

                    Spinner dropdownTrans2 = (Spinner)rootView.findViewById(R.id.spinnerTrans2);
                    long akunId2 = AkunDAO.getIdByName(dbHelper,dropdownTrans2.getItemAtPosition(dropdownTrans2.getSelectedItemPosition()).toString());
                    EditText textIdTransactionDebit2 = (EditText) rootView.findViewById(R.id.edittextTransDebit2);
                    EditText textIdTransactionCredit2 = (EditText) rootView.findViewById(R.id.edittextTransCredit2);

                    Spinner dropdownTrans3 = (Spinner)rootView.findViewById(R.id.spinnerTrans3);
                    long akunId3 = AkunDAO.getIdByName(dbHelper,dropdownTrans3.getItemAtPosition(dropdownTrans3.getSelectedItemPosition()).toString());
                    EditText textIdTransactionDebit3 = (EditText) rootView.findViewById(R.id.edittextTransDebit3);
                    EditText textIdTransactionCredit3 = (EditText) rootView.findViewById(R.id.edittextTransCredit3);

                    Spinner dropdownTrans4 = (Spinner)rootView.findViewById(R.id.spinnerTrans4);
                    long akunId4 = AkunDAO.getIdByName(dbHelper,dropdownTrans4.getItemAtPosition(dropdownTrans4.getSelectedItemPosition()).toString());
                    EditText textIdTransactionDebit4 = (EditText) rootView.findViewById(R.id.edittextTransDebit4);
                    EditText textIdTransactionCredit4 = (EditText) rootView.findViewById(R.id.edittextTransCredit4);

                    Spinner dropdownTrans5 = (Spinner)rootView.findViewById(R.id.spinnerTrans5);
                    long akunId5 = AkunDAO.getIdByName(dbHelper,dropdownTrans5.getItemAtPosition(dropdownTrans5.getSelectedItemPosition()).toString());
                    EditText textIdTransactionDebit5 = (EditText) rootView.findViewById(R.id.edittextTransDebit5);
                    EditText textIdTransactionCredit5 = (EditText) rootView.findViewById(R.id.edittextTransCredit5);

                    double debitTotal = 0;
                    double creditTotal = 0;

                    double nilaiDebit1 = 0;
                    double nilaiCredit1 = 0;

                    //cari modal
                    double nilaikas = 0;

                    if (akunId1 != 0){
                        try{

                            try { nilaiDebit1 = Double.parseDouble(textIdTransactionDebit1.getText().toString());  }catch (Exception e){System.out.println(e);}
                            try { nilaiCredit1 = Double.parseDouble(textIdTransactionCredit1.getText().toString());  }catch (Exception e){System.out.println(e);}

                            debitTotal += nilaiDebit1;
                            creditTotal += nilaiCredit1;

                            if (akunId1 == 1){
                                nilaikas += nilaiDebit1;
                                nilaikas -= nilaiCredit1;
                            }

                        }catch (Exception e){
                        }
                    }

                    double nilaiDebit2 = 0;
                    double nilaiCredit2 = 0;
                    if (akunId2 != 0){
                        try{

                            try { nilaiDebit2 = Double.parseDouble(textIdTransactionDebit2.getText().toString());  }catch (Exception e){System.out.println(e);}
                            try { nilaiCredit2 = Double.parseDouble(textIdTransactionCredit2.getText().toString());  }catch (Exception e){System.out.println(e);}

                            debitTotal += nilaiDebit2;
                            creditTotal += nilaiCredit2;

                            if (akunId2 == 1){
                                nilaikas += nilaiDebit2;
                                nilaikas -= nilaiCredit2;
                            }
                        }catch (Exception e){
                        }
                    }

                    double nilaiDebit3 = 0;
                    double nilaiCredit3 = 0;
                    if (akunId3 != 0){
                        try{

                            try { nilaiDebit3 = Double.parseDouble(textIdTransactionDebit3.getText().toString());  }catch (Exception e){System.out.println(e);}
                            try { nilaiCredit3 = Double.parseDouble(textIdTransactionCredit3.getText().toString());  }catch (Exception e){System.out.println(e);}

                            debitTotal += nilaiDebit3;
                            creditTotal += nilaiCredit3;

                            if (akunId3 == 1){
                                nilaikas += nilaiDebit3;
                                nilaikas -= nilaiCredit3;
                            }
                        }catch (Exception e){
                        }
                    }


                    double nilaiDebit4 = 0;
                    double nilaiCredit4 = 0;
                    if (akunId4 != 0){
                        try{

                            try { nilaiDebit4 = Double.parseDouble(textIdTransactionDebit4.getText().toString());  }catch (Exception e){System.out.println(e);}
                            try { nilaiCredit4 = Double.parseDouble(textIdTransactionCredit4.getText().toString());  }catch (Exception e){System.out.println(e);}

                            debitTotal += nilaiDebit4;
                            creditTotal += nilaiCredit4;

                            if (akunId4 == 1){
                                nilaikas += nilaiDebit4;
                                nilaikas -= nilaiCredit4;
                            }
                        }catch (Exception e){
                        }
                    }

                    double nilaiDebit5 = 0;
                    double nilaiCredit5 = 0;
                    if (akunId5 != 0){
                        try{

                            try { nilaiDebit5 = Double.parseDouble(textIdTransactionDebit5.getText().toString());  }catch (Exception e){System.out.println(e);}
                            try { nilaiCredit5 = Double.parseDouble(textIdTransactionCredit5.getText().toString());  }catch (Exception e){System.out.println(e);}

                            debitTotal += nilaiDebit5;
                            creditTotal += nilaiCredit5;

                            if (akunId5 == 1){
                                nilaikas += nilaiDebit5;
                                nilaikas -= nilaiCredit5;
                            }
                        }catch (Exception e){
                        }
                    }

                    //cari kas menjadi minus atau tidak
                    boolean kasMinus = TransactionDAO.getKasMinus(dbHelper,nilaikas);

                    //pengecekan balance dan pengecekan no akun tersedia

                    if (debitTotal == 0 || creditTotal == 0){
                        Toast.makeText(getActivity(), " akun not be : null ", Toast.LENGTH_LONG).show();
                    } else if (debitTotal != creditTotal){
                        Toast.makeText(getActivity(), " debit credit not balance : "+ debitTotal +  " != "+ creditTotal, Toast.LENGTH_LONG).show();
                    } else if (kasMinus){
                        Toast.makeText(getActivity(), " kas can't be minus ", Toast.LENGTH_LONG).show();
                    } else if (textNoTransaction.getText().toString().equals("") || textNamaTransaction.getText().toString().equals("") || textDateTransaction.getText().toString().equals("") ){
                        Toast.makeText(getActivity(), " Data Incomplate ", Toast.LENGTH_LONG).show();
                    } else {
                        if (TransactionDAO.getByNoAkunTersedia(dbHelper,textNoTransaction.getText().toString())){

                            Toast.makeText(getActivity(), " No Transaction  : "+ textNoTransaction.getText().toString()+" is replaced ", Toast.LENGTH_LONG).show();
                            TransactionDAO.deleteByNoTrans(dbHelper,(textNoTransaction.getText().toString()));

                        }

                        // lakukan saved
                        if (akunId1 != 0 ){
                            if (nilaiDebit1 !=0){
                                TransactionEntity transactionEntity = new TransactionEntity();
                                transactionEntity.setNoTransaction(textNoTransaction.getText().toString());
                                transactionEntity.setNoteTransaction(textNamaTransaction.getText().toString());
                                transactionEntity.setValueTransaction(nilaiDebit1);
                                transactionEntity.setTypeTransaction(0);
                                transactionEntity.setDateTransaction(Formater.getDateFromString(textDateTransaction.getText().toString()));
                                transactionEntity.setIdAkun(akunId1);
                                TransactionDAO.add(dbHelper, transactionEntity);
                            }

                            if (nilaiCredit1 !=0){
                                TransactionEntity transactionEntity = new TransactionEntity();
                                transactionEntity.setNoTransaction(textNoTransaction.getText().toString());
                                transactionEntity.setNoteTransaction(textNamaTransaction.getText().toString());
                                transactionEntity.setValueTransaction(nilaiCredit1);
                                transactionEntity.setTypeTransaction(1);
                                transactionEntity.setDateTransaction(Formater.getDateFromString(textDateTransaction.getText().toString()));
                                transactionEntity.setIdAkun(akunId1);
                                TransactionDAO.add(dbHelper, transactionEntity);
                            }
                        }


                        if (akunId2 != 0 ){
                            if (nilaiDebit2 !=0){
                                TransactionEntity transactionEntity = new TransactionEntity();
                                transactionEntity.setNoTransaction(textNoTransaction.getText().toString());
                                transactionEntity.setNoteTransaction(textNamaTransaction.getText().toString());
                                transactionEntity.setValueTransaction(nilaiDebit2);
                                transactionEntity.setTypeTransaction(0);
                                transactionEntity.setDateTransaction(Formater.getDateFromString(textDateTransaction.getText().toString()));
                                transactionEntity.setIdAkun(akunId2);
                                TransactionDAO.add(dbHelper, transactionEntity);
                            }

                            if (nilaiCredit2 !=0){
                                TransactionEntity transactionEntity = new TransactionEntity();
                                transactionEntity.setNoTransaction(textNoTransaction.getText().toString());
                                transactionEntity.setNoteTransaction(textNamaTransaction.getText().toString());
                                transactionEntity.setValueTransaction(nilaiCredit2);
                                transactionEntity.setTypeTransaction(1);
                                transactionEntity.setDateTransaction(Formater.getDateFromString(textDateTransaction.getText().toString()));
                                transactionEntity.setIdAkun(akunId2);
                                TransactionDAO.add(dbHelper, transactionEntity);
                            }
                        }

                        if (akunId3 != 0 ){
                            if (nilaiDebit3 !=0){
                                TransactionEntity transactionEntity = new TransactionEntity();
                                transactionEntity.setNoTransaction(textNoTransaction.getText().toString());
                                transactionEntity.setNoteTransaction(textNamaTransaction.getText().toString());
                                transactionEntity.setValueTransaction(nilaiDebit3);
                                transactionEntity.setTypeTransaction(0);
                                transactionEntity.setDateTransaction(Formater.getDateFromString(textDateTransaction.getText().toString()));
                                transactionEntity.setIdAkun(akunId3);
                                TransactionDAO.add(dbHelper, transactionEntity);
                            }

                            if (nilaiCredit3 !=0){
                                TransactionEntity transactionEntity = new TransactionEntity();
                                transactionEntity.setNoTransaction(textNoTransaction.getText().toString());
                                transactionEntity.setNoteTransaction(textNamaTransaction.getText().toString());
                                transactionEntity.setValueTransaction(nilaiCredit3);
                                transactionEntity.setTypeTransaction(1);
                                transactionEntity.setDateTransaction(Formater.getDateFromString(textDateTransaction.getText().toString()));
                                transactionEntity.setIdAkun(akunId3);
                                TransactionDAO.add(dbHelper, transactionEntity);
                            }
                        }

                        if (akunId4 != 0 ){
                            if (nilaiDebit4 !=0){
                                TransactionEntity transactionEntity = new TransactionEntity();
                                transactionEntity.setNoTransaction(textNoTransaction.getText().toString());
                                transactionEntity.setNoteTransaction(textNamaTransaction.getText().toString());
                                transactionEntity.setValueTransaction(nilaiDebit4);
                                transactionEntity.setTypeTransaction(0);
                                transactionEntity.setDateTransaction(Formater.getDateFromString(textDateTransaction.getText().toString()));
                                transactionEntity.setIdAkun(akunId4);
                                TransactionDAO.add(dbHelper, transactionEntity);
                            }

                            if (nilaiCredit4 !=0){
                                TransactionEntity transactionEntity = new TransactionEntity();
                                transactionEntity.setNoTransaction(textNoTransaction.getText().toString());
                                transactionEntity.setNoteTransaction(textNamaTransaction.getText().toString());
                                transactionEntity.setValueTransaction(nilaiCredit4);
                                transactionEntity.setTypeTransaction(1);
                                transactionEntity.setDateTransaction(Formater.getDateFromString(textDateTransaction.getText().toString()));
                                transactionEntity.setIdAkun(akunId4);
                                TransactionDAO.add(dbHelper, transactionEntity);
                            }
                        }

                        if (akunId5 != 0 ){
                            if (nilaiDebit5 !=0){
                                TransactionEntity transactionEntity = new TransactionEntity();
                                transactionEntity.setNoTransaction(textNoTransaction.getText().toString());
                                transactionEntity.setNoteTransaction(textNamaTransaction.getText().toString());
                                transactionEntity.setValueTransaction(nilaiDebit5);
                                transactionEntity.setTypeTransaction(0);
                                transactionEntity.setDateTransaction(Formater.getDateFromString(textDateTransaction.getText().toString()));
                                transactionEntity.setIdAkun(akunId5);
                                TransactionDAO.add(dbHelper, transactionEntity);
                            }

                            if (nilaiCredit5 !=0){
                                TransactionEntity transactionEntity = new TransactionEntity();
                                transactionEntity.setNoTransaction(textNoTransaction.getText().toString());
                                transactionEntity.setNoteTransaction(textNamaTransaction.getText().toString());
                                transactionEntity.setValueTransaction(nilaiCredit5);
                                transactionEntity.setTypeTransaction(1);
                                transactionEntity.setDateTransaction(Formater.getDateFromString(textDateTransaction.getText().toString()));
                                transactionEntity.setIdAkun(akunId5);
                                TransactionDAO.add(dbHelper, transactionEntity);
                            }
                        }
                        Toast.makeText(getActivity(), " Saved ", Toast.LENGTH_LONG).show();
                        Bundle bundle = new Bundle();

                        TransactionMultipleList transactionMultipleList = new TransactionMultipleList();
                        transactionMultipleList.setArguments(bundle);

                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frame_container, transactionMultipleList)
                                .commit();
                    }

                    //Toast.makeText(getActivity(), "spinner ="+ dropdownTrans1.getItemAtPosition(dropdownTrans1.getSelectedItemPosition()).toString() +  "Value ="+ textIdTransactionDebit1.getText().toString() + " ; Value 2 = "+ textIdTransactionCredit1.getText().toString(), Toast.LENGTH_LONG).show();

                }catch (Exception e){}



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
        RefreshListDraf();
        return rootView;
    }


    public void RefreshListDraf(){
        String x= tNoTransactionPublic;
        String[] items = AkunDAO.getListArrayOnlyNameWithSelect(dbHelper, 0, 0, "", "");

        Vector transactionList = TransactionDAO.getList(dbHelper, 0, 0, "" + TransactionDAO.FLD_TRANSACTION_NO +" = '"+ tNoTransactionPublic+"'" + " AND "+TransactionDAO.FLD_AKUN_ID +" != '18'  AND "+TransactionDAO.FLD_AKUN_ID +" != '19'" , "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);

        TableLayout tableLayout = new TableLayout(getActivity());
        tableLayout = (TableLayout) rootView.findViewById(R.id.tableDraf);
        tableLayout.removeAllViews();

        TableLayout.LayoutParams parameterTableLayout = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT);


        //tableLayout.getColumnModel().getColumn(0).setMinWidth(25);
        //tableLayout.setColumnShrinkable(1, true);

        TableRow TRHeader = new TableRow(getActivity());
        TRHeader.setBackgroundColor(Color.DKGRAY);
        TRHeader.setLayoutParams(parameterTableLayout);

        TableRow.LayoutParams parameterTableRowHeader = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT);

        parameterTableRowHeader.setMargins(4, 4, 4, 4);


        TextView headerNo = new TextView(getActivity());
        headerNo.setText(" No ");
        headerNo.setTextColor(Color.BLACK);
        headerNo.setGravity(Gravity.CENTER);
        headerNo.setBackgroundColor(Color.DKGRAY);
        TRHeader.addView(headerNo, parameterTableRowHeader);

        TextView headerNoTransaksi = new TextView(getActivity());
        headerNoTransaksi.setText(" Akun ");
        headerNoTransaksi.setTextColor(Color.BLACK);
        headerNoTransaksi.setGravity(Gravity.CENTER);
        headerNoTransaksi.setBackgroundColor(Color.DKGRAY);
        TRHeader.addView(headerNoTransaksi, parameterTableRowHeader);

        TextView headerDebit = new TextView(getActivity());
        headerDebit.setText(" DEBIT ");
        headerDebit.setTextColor(Color.BLACK);
        headerDebit.setGravity(Gravity.CENTER);
        headerDebit.setBackgroundColor(Color.DKGRAY);
        TRHeader.addView(headerDebit, parameterTableRowHeader);

        TextView headerKredit = new TextView(getActivity());
        headerKredit.setText(" KREDIT ");
        headerKredit.setTextColor(Color.BLACK);
        headerKredit.setGravity(Gravity.CENTER);
        headerKredit.setBackgroundColor(Color.DKGRAY);
        TRHeader.addView(headerKredit, parameterTableRowHeader);


        tableLayout.addView(TRHeader);


        {

            long akunId1 = 0;
            String value1 = "";
            int type1 = 0;
            try{
                if (transactionList != null && transactionList.size()>0){
                    TransactionEntity transaction = new TransactionEntity();
                    transaction = (TransactionEntity) transactionList.get(0);
                    akunId1 = transaction.getIdAkun();
                    value1 = ""+transaction.getValueTransaction();
                    type1 = transaction.getTypeTransaction();
                }
            }catch(Exception e){}

            TableRow TR = new TableRow(getActivity());
            TR.setBackgroundColor(Color.GRAY);
            TR.setLayoutParams(parameterTableLayout);

            TableRow.LayoutParams parameterTableRow = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT);

            parameterTableRow.setMargins(4, 4, 4, 4);

            TextView txtNo = new TextView(getActivity());
            txtNo.setText("1");
            txtNo.setTextColor(Color.DKGRAY);
            txtNo.setGravity(Gravity.CENTER);
            //txtNo.setBackgroundColor(Color.WHITE);
            TR.addView(txtNo, parameterTableRow);


            Spinner dropdown = new Spinner(getActivity());
            dropdown.setId(R.id.spinnerTrans1);
            dropdown.setBackgroundColor(Color.GRAY);
            dropdown.setAdapter(adapter);

            ArrayAdapter myAdap = (ArrayAdapter) dropdown.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition = myAdap.getPosition(""+AkunDAO.getNameByIdForTransactionAdapter(dbHelper, akunId1));
            dropdown.setSelection(spinnerPosition);

            TR.addView(dropdown, parameterTableRow);

            EditText editText = new EditText(getActivity());
            editText.setId(R.id.edittextTransDebit1);
            editText.setBackgroundColor(Color.WHITE);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);

            EditText editText2 = new EditText(getActivity());
            editText2.setId(R.id.edittextTransCredit1);
            editText2.setBackgroundColor(Color.WHITE);
            editText2.setInputType(InputType.TYPE_CLASS_NUMBER);

            if (type1 == 0){
                try{
                    hargaDebit=hargaDebit+ (Double.parseDouble(value1));
                }catch (Exception e){}
                editText.setText(value1);
            } else {
                editText2.setText(value1);
            }

            TR.addView(editText, parameterTableRow);
            TR.addView(editText2, parameterTableRow);

            tableLayout.addView(TR);

        }


        {

            long akunId2 = 0;
            String value2 = "";
            int type2 = 0;
            try{
                if (transactionList != null && transactionList.size()>0){
                    TransactionEntity transaction = new TransactionEntity();
                    transaction = (TransactionEntity) transactionList.get(1);
                    akunId2 = transaction.getIdAkun();
                    value2 = ""+transaction.getValueTransaction();
                    type2 = transaction.getTypeTransaction();
                }
            }catch(Exception e){}

            TableRow TR = new TableRow(getActivity());
            TR.setBackgroundColor(Color.GRAY);
            TR.setLayoutParams(parameterTableLayout);

            TableRow.LayoutParams parameterTableRow = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT);

            parameterTableRow.setMargins(4, 4, 4, 4);

            TextView txtNo = new TextView(getActivity());
            txtNo.setText("2");
            txtNo.setTextColor(Color.DKGRAY);
            txtNo.setGravity(Gravity.CENTER);
            //txtNo.setBackgroundColor(Color.WHITE);
            TR.addView(txtNo, parameterTableRow);


            Spinner dropdown = new Spinner(getActivity());
            dropdown.setId(R.id.spinnerTrans2);
            dropdown.setBackgroundColor(Color.GRAY);
            dropdown.setAdapter(adapter);

            ArrayAdapter myAdap = (ArrayAdapter) dropdown.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition = myAdap.getPosition(""+AkunDAO.getNameByIdForTransactionAdapter(dbHelper, akunId2));
            dropdown.setSelection(spinnerPosition);

            TR.addView(dropdown, parameterTableRow);

            EditText editText = new EditText(getActivity());
            editText.setId(R.id.edittextTransDebit2);
            editText.setBackgroundColor(Color.WHITE);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);

            EditText editText2 = new EditText(getActivity());
            editText2.setId(R.id.edittextTransCredit2);
            editText2.setBackgroundColor(Color.WHITE);
            editText2.setInputType(InputType.TYPE_CLASS_NUMBER);

            if (type2 == 0){
                try{
                    hargaDebit=hargaDebit+ (Double.parseDouble(value2));
                }catch (Exception e){}
                editText.setText(value2);
            } else {
                editText2.setText(value2);
            }

            TR.addView(editText, parameterTableRow);
            TR.addView(editText2, parameterTableRow);

            tableLayout.addView(TR);

        }

        {

            long akunId3 = 0;
            String value3 = "";
            int type3 = 0;
            try{
                if (transactionList != null && transactionList.size()>0){
                    TransactionEntity transaction = new TransactionEntity();
                    transaction = (TransactionEntity) transactionList.get(2);
                    akunId3 = transaction.getIdAkun();
                    value3 = ""+transaction.getValueTransaction();
                    type3 = transaction.getTypeTransaction();
                }
            }catch(Exception e){}

            TableRow TR = new TableRow(getActivity());
            TR.setBackgroundColor(Color.GRAY);
            TR.setLayoutParams(parameterTableLayout);

            TableRow.LayoutParams parameterTableRow = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT);

            parameterTableRow.setMargins(4, 4, 4, 4);

            TextView txtNo = new TextView(getActivity());
            txtNo.setText("3");
            txtNo.setTextColor(Color.DKGRAY);
            txtNo.setGravity(Gravity.CENTER);
            //txtNo.setBackgroundColor(Color.WHITE);
            TR.addView(txtNo, parameterTableRow);


            Spinner dropdown = new Spinner(getActivity());
            dropdown.setId(R.id.spinnerTrans3);
            dropdown.setBackgroundColor(Color.GRAY);
            dropdown.setAdapter(adapter);

            ArrayAdapter myAdap = (ArrayAdapter) dropdown.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition = myAdap.getPosition(""+AkunDAO.getNameByIdForTransactionAdapter(dbHelper, akunId3));
            dropdown.setSelection(spinnerPosition);

            TR.addView(dropdown, parameterTableRow);

            EditText editText = new EditText(getActivity());
            editText.setId(R.id.edittextTransDebit3);
            editText.setBackgroundColor(Color.WHITE);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);

            EditText editText2 = new EditText(getActivity());
            editText2.setId(R.id.edittextTransCredit3);
            editText2.setBackgroundColor(Color.WHITE);
            editText2.setInputType(InputType.TYPE_CLASS_NUMBER);

            if (type3 == 0){
                try{
                    hargaDebit=hargaDebit+ (Double.parseDouble(value3));
                }catch (Exception e){}
                editText.setText(value3);
            } else {
                editText2.setText(value3);
            }

            TR.addView(editText, parameterTableRow);
            TR.addView(editText2, parameterTableRow);

            tableLayout.addView(TR);

        }

        {

            long akunId4 = 0;
            String value4 = "";
            int type4 = 0;
            try{
                if (transactionList != null && transactionList.size()>0){
                    TransactionEntity transaction = new TransactionEntity();
                    transaction = (TransactionEntity) transactionList.get(3);
                    akunId4 = transaction.getIdAkun();
                    value4 = ""+transaction.getValueTransaction();
                    type4 = transaction.getTypeTransaction();
                }
            }catch(Exception e){}

            TableRow TR = new TableRow(getActivity());
            TR.setBackgroundColor(Color.GRAY);
            TR.setLayoutParams(parameterTableLayout);

            TableRow.LayoutParams parameterTableRow = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT);

            parameterTableRow.setMargins(4, 4, 4, 4);

            TextView txtNo = new TextView(getActivity());
            txtNo.setText("4");
            txtNo.setTextColor(Color.DKGRAY);
            txtNo.setGravity(Gravity.CENTER);
            //txtNo.setBackgroundColor(Color.WHITE);
            TR.addView(txtNo, parameterTableRow);


            Spinner dropdown = new Spinner(getActivity());
            dropdown.setId(R.id.spinnerTrans4);
            dropdown.setBackgroundColor(Color.GRAY);
            dropdown.setAdapter(adapter);

            ArrayAdapter myAdap = (ArrayAdapter) dropdown.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition = myAdap.getPosition(""+AkunDAO.getNameByIdForTransactionAdapter(dbHelper, akunId4));
            dropdown.setSelection(spinnerPosition);

            TR.addView(dropdown, parameterTableRow);

            EditText editText = new EditText(getActivity());
            editText.setId(R.id.edittextTransDebit4);
            editText.setBackgroundColor(Color.WHITE);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);

            EditText editText2 = new EditText(getActivity());
            editText2.setId(R.id.edittextTransCredit4);
            editText2.setBackgroundColor(Color.WHITE);
            editText2.setInputType(InputType.TYPE_CLASS_NUMBER);

            if (type4 == 0){
                try{
                    hargaDebit=hargaDebit+ (Double.parseDouble(value4));
                }catch (Exception e){}
                editText.setText(value4);
            } else {
                editText2.setText(value4);
            }

            TR.addView(editText, parameterTableRow);
            TR.addView(editText2, parameterTableRow);

            tableLayout.addView(TR);

        }


        {

            long akunId5 = 0;
            String value5 = "";
            int type5 = 0;
            try{
                if (transactionList != null && transactionList.size()>0){
                    TransactionEntity transaction = new TransactionEntity();
                    transaction = (TransactionEntity) transactionList.get(4);
                    akunId5 = transaction.getIdAkun();
                    value5 = ""+transaction.getValueTransaction();
                    type5 = transaction.getTypeTransaction();
                }
            }catch(Exception e){}

            TableRow TR = new TableRow(getActivity());
            TR.setBackgroundColor(Color.GRAY);
            TR.setLayoutParams(parameterTableLayout);

            TableRow.LayoutParams parameterTableRow = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT);

            parameterTableRow.setMargins(4, 4, 4, 4);

            TextView txtNo = new TextView(getActivity());
            txtNo.setText("5");
            txtNo.setTextColor(Color.DKGRAY);
            txtNo.setGravity(Gravity.CENTER);
            //txtNo.setBackgroundColor(Color.WHITE);
            TR.addView(txtNo, parameterTableRow);


            Spinner dropdown = new Spinner(getActivity());
            dropdown.setId(R.id.spinnerTrans5);
            dropdown.setBackgroundColor(Color.GRAY);
            dropdown.setAdapter(adapter);

            ArrayAdapter myAdap = (ArrayAdapter) dropdown.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition = myAdap.getPosition(""+AkunDAO.getNameByIdForTransactionAdapter(dbHelper,akunId5));
            dropdown.setSelection(spinnerPosition);

            TR.addView(dropdown, parameterTableRow);

            EditText editText = new EditText(getActivity());
            editText.setId(R.id.edittextTransDebit5);
            editText.setBackgroundColor(Color.WHITE);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);

            EditText editText2 = new EditText(getActivity());
            editText2.setId(R.id.edittextTransCredit5);
            editText2.setBackgroundColor(Color.WHITE);
            editText2.setInputType(InputType.TYPE_CLASS_NUMBER);

            if (type5 == 0){
                try{
                    hargaDebit=hargaDebit+ (Double.parseDouble(value5));
                }catch (Exception e){}
                editText.setText(value5);
            } else {
                editText2.setText(value5);
            }

            TR.addView(editText, parameterTableRow);
            TR.addView(editText2, parameterTableRow);

            tableLayout.addView(TR);

        }

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

    private void callFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}
