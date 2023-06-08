package com.example.guswik.ireport.master;

/**
 * Created by GUSWIK on 7/16/2022.
 */

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class PerhitunganInvestasi extends Fragment {

    public PerhitunganInvestasi(){}
    View rootView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    protected Cursor cursor;
    DataHelper dbHelper;
    Button buttonSave,buttonSaveModal,buttondetailinv,buttonpendapatan,buttonbiaya,buttonanalysis;

    Spinner spinnerTahun;
    EditText textIdPerhitunganInvestasi,textSumberDana, textNominal, textmodalinvestasi, textmodalkerja;
    TextView textModal ;
    ListView ListViewPerhitunganInvestasi;
    String[] daftar;
    long nilaitotal = 0;

    Vector data = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.perhitunganinvestasi, container, false);
        getActivity().setTitle("Perhitungan Investasi");

        dbHelper = new DataHelper(getActivity());

        textIdPerhitunganInvestasi = (EditText) rootView.findViewById(R.id.editTextIdPerhitunganInvestasi);
        textSumberDana = (EditText) rootView.findViewById(R.id.editTextSumberDana);
        textNominal = (EditText) rootView.findViewById(R.id.editTextNominal);

        spinnerTahun = (Spinner)rootView.findViewById(R.id.spinnertahun);
        buttonSave = (Button) rootView.findViewById(R.id.buttonSave);

        //modalkerja
        textModal = (TextView) rootView.findViewById(R.id.textModal);
        textmodalinvestasi = (EditText) rootView.findViewById(R.id.editTextModalInv);
        textmodalkerja = (EditText) rootView.findViewById(R.id.editTextHasilModalKerja);
        buttonSaveModal = (Button) rootView.findViewById(R.id.buttonSaveModal);

        buttondetailinv = (Button) rootView.findViewById(R.id.buttondetinv);
        buttonpendapatan = (Button) rootView.findViewById(R.id.buttondetpendapatan);
        buttonbiaya = (Button) rootView.findViewById(R.id.buttondetbiaya);
        buttonanalysis = (Button) rootView.findViewById(R.id.buttonanalysiskelayakan);

        final String tIdPerhitunganInvestasi = textIdPerhitunganInvestasi.getText().toString();

        String[] tahuns = new String[5];
        tahuns[0] = "2022";
        tahuns[1] = "2023";
        tahuns[2] = "2024";
        tahuns[3] = "2025";
        tahuns[4] = "2026";
        ArrayAdapter<String> adapterproducts = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, tahuns);
        spinnerTahun.setAdapter(adapterproducts);


        buttonSaveModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


                long nominal = 0;
                try{
                    nominal = Long.parseLong(textNominal.getText().toString());
                } catch (Exception e){
                }
                long modalinv = 0;
                try{
                    modalinv = Long.parseLong(textmodalinvestasi.getText().toString());
                } catch (Exception e){
                }
                long modalkerja = 0;
                try{
                    modalkerja = Long.parseLong(textmodalkerja.getText().toString());
                } catch (Exception e){
                }
                long tahun = 2022;
                try {
                    tahun = Long.parseLong(spinnerTahun.getItemAtPosition(spinnerTahun.getSelectedItemPosition()).toString());
                } catch (Exception e) {
                    System.out.println("Something went wrong.");
                }
                nilaitotal = PerhitunganInvestasiDAO.getTotalSumberdanaByTahun(dbHelper, tahun);

                if ((modalinv+modalkerja) == nilaitotal){
                    PerhitunganInvestasiEntity perhitunganinvestasiEntity = new PerhitunganInvestasiEntity();
                    perhitunganinvestasiEntity.setTahun(tahun);
                    perhitunganinvestasiEntity.setModalinvestasi(modalinv);
                    perhitunganinvestasiEntity.setModalkerja(modalkerja);
                    try {
                        PerhitunganInvestasiDAO.updateModal(dbHelper, perhitunganinvestasiEntity);
                    } catch (Exception e) {
                        System.out.println("Something went wrong.");
                    }
                    Toast.makeText(getActivity(), "Berhasil tersimpan PerhitunganInvestasi "+ textSumberDana.getText().toString(), Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getActivity(), "Gagal, nilai total melebihi sumberdana"+ textSumberDana.getText().toString(), Toast.LENGTH_LONG).show();
                }

                RefreshList();

            }
        });


        buttondetailinv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                long nominal = 0;
                try{
                    nominal = Long.parseLong(textNominal.getText().toString());
                } catch (Exception e){
                }
                long modalinv = 0;
                try{
                    modalinv = Long.parseLong(textmodalinvestasi.getText().toString());
                } catch (Exception e){
                }
                long modalkerja = 0;
                try{
                    modalkerja = Long.parseLong(textmodalkerja.getText().toString());
                } catch (Exception e){
                }
                long tahun = 2022;
                try {
                    tahun = Long.parseLong(spinnerTahun.getItemAtPosition(spinnerTahun.getSelectedItemPosition()).toString());
                } catch (Exception e) {
                    System.out.println("Something went wrong.");
                }
                nilaitotal = PerhitunganInvestasiDAO.getTotalSumberdanaByTahun(dbHelper, tahun);


                PerhitunganInvestasiEntity perhitunganinvestasiEntity = new PerhitunganInvestasiEntity();
                perhitunganinvestasiEntity.setTahun(tahun);
                perhitunganinvestasiEntity.setModalinvestasi(modalinv);
                perhitunganinvestasiEntity.setModalkerja(modalkerja);

                Bundle bundle = new Bundle();
                bundle.putString("tahun", "" +perhitunganinvestasiEntity.getTahun()); // Put anything what you want
                bundle.putString("modalinv", "" +perhitunganinvestasiEntity.getModalinvestasi());
                bundle.putString("modalkerja", "" +perhitunganinvestasiEntity.getModalkerja());


                DetailPerhitunganInvestasi detailperhitunganinvestasi = new DetailPerhitunganInvestasi();
                detailperhitunganinvestasi.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, detailperhitunganinvestasi)
                        .commit();

            }
        });


        buttonpendapatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                long nominal = 0;
                try{
                    nominal = Long.parseLong(textNominal.getText().toString());
                } catch (Exception e){
                }
                long modalinv = 0;
                try{
                    modalinv = Long.parseLong(textmodalinvestasi.getText().toString());
                } catch (Exception e){
                }
                long modalkerja = 0;
                try{
                    modalkerja = Long.parseLong(textmodalkerja.getText().toString());
                } catch (Exception e){
                }
                long tahun = 2022;
                try {
                    tahun = Long.parseLong(spinnerTahun.getItemAtPosition(spinnerTahun.getSelectedItemPosition()).toString());
                } catch (Exception e) {
                    System.out.println("Something went wrong.");
                }
                nilaitotal = PerhitunganInvestasiDAO.getTotalSumberdanaByTahun(dbHelper, tahun);


                PerhitunganInvestasiEntity perhitunganinvestasiEntity = new PerhitunganInvestasiEntity();
                perhitunganinvestasiEntity.setTahun(tahun);
                perhitunganinvestasiEntity.setModalinvestasi(modalinv);
                perhitunganinvestasiEntity.setModalkerja(modalkerja);

                Bundle bundle = new Bundle();
                bundle.putString("tahun", "" +perhitunganinvestasiEntity.getTahun()); // Put anything what you want
                bundle.putString("modalinv", "" +perhitunganinvestasiEntity.getModalinvestasi());
                bundle.putString("modalkerja", "" +perhitunganinvestasiEntity.getModalkerja());

                DetailPerhitunganPendapatan detailPerhitunganPendapatan = new DetailPerhitunganPendapatan();
                detailPerhitunganPendapatan.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, detailPerhitunganPendapatan)
                        .commit();

            }
        });


        buttonbiaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                long nominal = 0;
                try{
                    nominal = Long.parseLong(textNominal.getText().toString());
                } catch (Exception e){
                }
                long modalinv = 0;
                try{
                    modalinv = Long.parseLong(textmodalinvestasi.getText().toString());
                } catch (Exception e){
                }
                long modalkerja = 0;
                try{
                    modalkerja = Long.parseLong(textmodalkerja.getText().toString());
                } catch (Exception e){
                }
                long tahun = 2022;
                try {
                    tahun = Long.parseLong(spinnerTahun.getItemAtPosition(spinnerTahun.getSelectedItemPosition()).toString());
                } catch (Exception e) {
                    System.out.println("Something went wrong.");
                }
                nilaitotal = PerhitunganInvestasiDAO.getTotalSumberdanaByTahun(dbHelper, tahun);


                PerhitunganInvestasiEntity perhitunganinvestasiEntity = new PerhitunganInvestasiEntity();
                perhitunganinvestasiEntity.setTahun(tahun);
                perhitunganinvestasiEntity.setModalinvestasi(modalinv);
                perhitunganinvestasiEntity.setModalkerja(modalkerja);

                Bundle bundle = new Bundle();
                bundle.putString("tahun", "" +perhitunganinvestasiEntity.getTahun()); // Put anything what you want
                bundle.putString("modalinv", "" +perhitunganinvestasiEntity.getModalinvestasi());
                bundle.putString("modalkerja", "" +perhitunganinvestasiEntity.getModalkerja());

                DetailPerhitunganBiaya detailPerhitunganBiaya = new DetailPerhitunganBiaya();
                detailPerhitunganBiaya.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, detailPerhitunganBiaya)
                        .commit();

            }
        });

        buttonanalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                long nominal = 0;
                try{
                    nominal = Long.parseLong(textNominal.getText().toString());
                } catch (Exception e){
                }
                long modalinv = 0;
                try{
                    modalinv = Long.parseLong(textmodalinvestasi.getText().toString());
                } catch (Exception e){
                }
                long modalkerja = 0;
                try{
                    modalkerja = Long.parseLong(textmodalkerja.getText().toString());
                } catch (Exception e){
                }
                long tahun = 2022;
                try {
                    tahun = Long.parseLong(spinnerTahun.getItemAtPosition(spinnerTahun.getSelectedItemPosition()).toString());
                } catch (Exception e) {
                    System.out.println("Something went wrong.");
                }
                nilaitotal = PerhitunganInvestasiDAO.getTotalSumberdanaByTahun(dbHelper, tahun);


                PerhitunganInvestasiEntity perhitunganinvestasiEntity = new PerhitunganInvestasiEntity();
                perhitunganinvestasiEntity.setTahun(tahun);
                perhitunganinvestasiEntity.setModalinvestasi(modalinv);
                perhitunganinvestasiEntity.setModalkerja(modalkerja);

                Bundle bundle = new Bundle();
                bundle.putString("tahun", "" +perhitunganinvestasiEntity.getTahun()); // Put anything what you want
                bundle.putString("modalinv", "" +perhitunganinvestasiEntity.getModalinvestasi());
                bundle.putString("modalkerja", "" +perhitunganinvestasiEntity.getModalkerja());

                DetailPerhitunganAnalysis detailPerhitunganAnalysis = new DetailPerhitunganAnalysis();
                detailPerhitunganAnalysis.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, detailPerhitunganAnalysis)
                        .commit();

            }
        });

        textmodalinvestasi.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0){
                    //textmodalkerja.setText("");
                }

            }
        });

        textmodalkerja.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0){
                    //textmodalkerja.setText("");
                }

            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                long id = 0;
                try{
                    id = Long.parseLong(textIdPerhitunganInvestasi.getText().toString());
                } catch (Exception e){
                }

                long nominal = 0;
                try{
                    nominal = Long.parseLong(textNominal.getText().toString());
                } catch (Exception e){
                }

                PerhitunganInvestasiEntity perhitunganinvestasiEntity = new PerhitunganInvestasiEntity();
                perhitunganinvestasiEntity.setIdPerhitunganInvestasi(id);
                perhitunganinvestasiEntity.setSumberDana(textSumberDana.getText().toString());

                long tahun = 2022;
                try {
                    tahun = Long.parseLong(spinnerTahun.getItemAtPosition(spinnerTahun.getSelectedItemPosition()).toString());
                } catch (Exception e) {
                    System.out.println("Something went wrong.");
                }
                perhitunganinvestasiEntity.setNominal(nominal);
                perhitunganinvestasiEntity.setTahun(tahun);

                if (perhitunganinvestasiEntity.getIdPerhitunganInvestasi() !=0){
                    PerhitunganInvestasiDAO.update(dbHelper, perhitunganinvestasiEntity);
                } else {
                    PerhitunganInvestasiDAO.add(dbHelper, perhitunganinvestasiEntity);
                }

                Toast.makeText(getActivity(), "Berhasil tersimpan PerhitunganInvestasi "+ textSumberDana.getText().toString(), Toast.LENGTH_LONG).show();

                textIdPerhitunganInvestasi.setText("");
                textSumberDana.setText("");
                textNominal.setText("");
                //textTahun.setText("");

                RefreshList();

            }
        });

        spinnerTahun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                //((TextView) parentView.getChildAt(0)).setTextColor(Color.BLUE);
                //((TextView) parentView.getChildAt(0)).setTextSize(30);
                RefreshList();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        RefreshList();
        return rootView;
    }

    public void RefreshList(){

        long tahun = 2022;
        try{
            tahun = Long.parseLong(spinnerTahun.getItemAtPosition(spinnerTahun.getSelectedItemPosition()).toString());
        } catch (Exception e){
        }

        String whereclause = PerhitunganInvestasiDAO.FLD_TAHUN +" = " + tahun;
        daftar = PerhitunganInvestasiDAO.getListArray(dbHelper, 0,0,whereclause,"");
        data = PerhitunganInvestasiDAO.getList(dbHelper, 0, 0, whereclause, "");

        long nilaimodalinv = 0;
        long nilaimodalkerja = 0;
        try{
            nilaimodalinv = PerhitunganInvestasiDAO.getAlreadyModalInv(dbHelper, tahun);
            textmodalinvestasi.setText(""+nilaimodalinv);
            nilaimodalkerja = PerhitunganInvestasiDAO.getAlreadyModalKerja(dbHelper, tahun);
            textmodalkerja.setText(""+nilaimodalkerja);
        } catch (Exception e){
        }


        ArrayAdapter adapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftar);

        ListViewPerhitunganInvestasi = (ListView)rootView.findViewById(R.id.listViewP);
        ListViewPerhitunganInvestasi.setAdapter(adapter);
        ListViewPerhitunganInvestasi.setSelected(true);
        ListViewPerhitunganInvestasi.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2]; //.getItemAtPosition(arg2).toString();

                final PerhitunganInvestasiEntity perhitunganinvestasiEntityX = (PerhitunganInvestasiEntity) data.get(arg2);

                final CharSequence[] dialogitem = {"Update perhitunganinvestasi", "Delete perhitunganinvestasi"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                textIdPerhitunganInvestasi.setText(""+perhitunganinvestasiEntityX.getIdPerhitunganInvestasi());
                                textSumberDana.setText(perhitunganinvestasiEntityX.getSumberDana());
                                textNominal.setText(""+perhitunganinvestasiEntityX.getNominal());
                                //textTahun.setText(""+perhitunganinvestasiEntityX.getTahun());
                                break;
                            case 1:

                                final CharSequence[] dialogitem2 = {"Yes", "No"};

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                builder2.setTitle("Delete Confirm");
                                builder2.setItems(dialogitem2, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        switch (item) {
                                            case 0:
                                                PerhitunganInvestasiDAO.delete(dbHelper, perhitunganinvestasiEntityX);
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
        ((ArrayAdapter)ListViewPerhitunganInvestasi.getAdapter()).notifyDataSetInvalidated();
    }

    private void callFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}
