package com.example.guswik.ireport.master;

/**
 * Created by GUSWIK on 7/16/2017.
 */

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
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

import com.example.guswik.ireport.Formater;
import com.example.guswik.ireport.R;
import com.example.guswik.ireport.database.DataHelper;

import java.util.Vector;

public class Depresiasi extends Fragment {

    public Depresiasi(){}
    View rootView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    protected Cursor cursor;
    DataHelper dbHelper;
    Button buttonS;
    TextView  textNamaTransaction;
    EditText textHargaPerolehan,textNilaiSisa, textUmurEkonomi;
    String tNoTransactionPublic,tNamaTransaction,tDateTransaction ;
    long tIdDepresiasi,tAkunIdx;
    double tHargaPerolehan,tNilaiSisa,tUmurEkonomi;
    Spinner dropdownAkunType;
    ListView ListViewAkun;
    String[] daftar;

    Vector data = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.depresiasi, container, false);
        getActivity().setTitle("Depresiasi");

        dbHelper = new DataHelper(getActivity());
        tNoTransactionPublic = "";
        try{
            tNoTransactionPublic = this.getArguments().getString("tNoTransaction");
        } catch (Exception e){
            System.out.print(e);
        }
        tNamaTransaction = "";
        try{
            tNamaTransaction = this.getArguments().getString("tNamaTransaction");
        } catch (Exception e){
            System.out.print(e);
        }

        tAkunIdx = 0;
        try{
            tAkunIdx = this.getArguments().getLong("tAkunIdx");
        } catch (Exception e){
            System.out.print(e);
        }

        tHargaPerolehan = 0;
        try{
            tHargaPerolehan = this.getArguments().getDouble("transactionHarga");
        } catch (Exception e){
            System.out.print(e);
        }
        tDateTransaction ="";
        try{
            tDateTransaction = this.getArguments().getString("tDateTransaction");
        } catch (Exception e){
            System.out.print(e);
        }

        DepresiasiEntity depresiasiEntity = new DepresiasiEntity();
        depresiasiEntity = DepresiasiDAO.getByNo(dbHelper,tNoTransactionPublic);
        if (depresiasiEntity != null && depresiasiEntity.getIdDepresiasi() > 0){

            //tAkunIdx = depresiasiEntity.getAkunIdx();
            tIdDepresiasi = depresiasiEntity.getIdDepresiasi();
            tNoTransactionPublic = depresiasiEntity.getNoTransaction();
            tNilaiSisa = depresiasiEntity.getNilaiSIsa();
            tHargaPerolehan=depresiasiEntity.getHargaPerolehan();
            tUmurEkonomi=depresiasiEntity.getUmurEkonomi();
        }

        textNilaiSisa = (EditText) rootView.findViewById(R.id.editTextNilaiSisa);
        textNilaiSisa.setText(""+tNilaiSisa);

        textUmurEkonomi = (EditText) rootView.findViewById(R.id.editTextUmurEkonomi);
        textUmurEkonomi.setText(""+tUmurEkonomi);

        textHargaPerolehan = (EditText) rootView.findViewById(R.id.editTextHargaPerolehan);
        textHargaPerolehan.setText(""+tHargaPerolehan);

        textNamaTransaction = (TextView) rootView.findViewById(R.id.textViewdepker);
        textNamaTransaction.setText(""+tNamaTransaction);

        buttonS = (Button) rootView.findViewById(R.id.buttondep);
        buttonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                long id = 0;
                try {
                    id =tIdDepresiasi;
                } catch (Exception e) {
                }

                DepresiasiEntity depresiasiEntity = new DepresiasiEntity();
                depresiasiEntity.setIdDepresiasi(id);
                depresiasiEntity.setAkunIdx(tAkunIdx);
                depresiasiEntity.setNoTransaction(tNoTransactionPublic);
                depresiasiEntity.setHargaPerolehan(Double.parseDouble(textHargaPerolehan.getText().toString()));
                depresiasiEntity.setNilaiSIsa(Double.parseDouble(textNilaiSisa.getText().toString()));
                depresiasiEntity.setUmurEkonomi(Double.parseDouble(textUmurEkonomi.getText().toString()));
                double value1= depresiasiEntity.getHargaPerolehan()-depresiasiEntity.getNilaiSIsa();
                double valueakhir = value1/depresiasiEntity.getUmurEkonomi();
                depresiasiEntity.setValueDepresiasi(valueakhir);

                if (depresiasiEntity.getIdDepresiasi() != 0) {
                    DepresiasiDAO.update(dbHelper, depresiasiEntity);
                    TransactionDAO.deleteByNoTransandAkunId(dbHelper, tNoTransactionPublic,19);
                    TransactionDAO.deleteByNoTransandAkunId(dbHelper, tNoTransactionPublic,18);
                } else {
                    DepresiasiDAO.add(dbHelper, depresiasiEntity);
                }
                    //memasukan ke transaksi
                    TransactionEntity transactionEntityBiaya = new TransactionEntity();
                    transactionEntityBiaya.setNoTransaction(tNoTransactionPublic);
                    transactionEntityBiaya.setNoteTransaction(textNamaTransaction.getText().toString());
                    transactionEntityBiaya.setValueTransaction(valueakhir);
                    transactionEntityBiaya.setTypeTransaction(0);
                    transactionEntityBiaya.setDateTransaction(Formater.getDateFromString(tDateTransaction));
                    transactionEntityBiaya.setIdAkun(19);
                    TransactionDAO.add(dbHelper, transactionEntityBiaya);

                    TransactionEntity transactionEntity = new TransactionEntity();
                    transactionEntity.setNoTransaction(tNoTransactionPublic);
                    transactionEntity.setNoteTransaction(textNamaTransaction.getText().toString());
                    transactionEntity.setValueTransaction(valueakhir);
                    transactionEntity.setTypeTransaction(1);
                    transactionEntity.setDateTransaction(Formater.getDateFromString(tDateTransaction));
                    transactionEntity.setIdAkun(18);
                    TransactionDAO.add(dbHelper, transactionEntity);





                Toast.makeText(getActivity(), "Berhasil tersimpan No " + tNoTransactionPublic, Toast.LENGTH_LONG).show();

                Bundle bundle = new Bundle();
                bundle.putString("transactionNo", ""+tNoTransactionPublic); // Put anything what you want
                bundle.putString("transactionNote", "" + tNamaTransaction);
                bundle.putString("transactionDate", "" + tDateTransaction);
                bundle.putString("tAkunIdx", "" + tAkunIdx);

                TransactionMultiple transactionMultiple = new TransactionMultiple();
                transactionMultiple.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, transactionMultiple)
                        .commit();
            }
        });

        return rootView;
    }

    private void callFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}
