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

import com.example.guswik.ireport.R;
import com.example.guswik.ireport.database.DataHelper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

public class DetailPerhitunganPendapatan extends Fragment {

    public DetailPerhitunganPendapatan(){}
    View rootView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    protected Cursor cursor;
    DataHelper dbHelper;
    Button buttonSave;

    Spinner spinnerTahun;
    EditText textIdDetailPerhitunganPendapatan,textRBA, textVolume, textHarga, textTotalHarga;
    TextView textModal,textlasttotal ;
    ListView ListViewDetailPerhitunganPendapatan;
    String[] daftar;
    long nilaitotal = 0;

    Vector data = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.detailperhitunganpendapatan, container, false);
        getActivity().setTitle("Perhitungan Pendapatan");

        dbHelper = new DataHelper(getActivity());

        textIdDetailPerhitunganPendapatan = (EditText) rootView.findViewById(R.id.editTextIdDetailPerhitunganPendapatan);
        textRBA = (EditText) rootView.findViewById(R.id.editTextRBA);
        textVolume = (EditText) rootView.findViewById(R.id.editTextVolume);
        spinnerTahun = (Spinner)rootView.findViewById(R.id.spinnertahun);
        textHarga = (EditText) rootView.findViewById(R.id.editTextHarga);
        textTotalHarga = (EditText) rootView.findViewById(R.id.editTextTotalHarga);
        textlasttotal = (TextView) rootView.findViewById(R.id.lasttotal);
        buttonSave = (Button) rootView.findViewById(R.id.buttonSave);

        final String tIdDetailPerhitunganPendapatan = textIdDetailPerhitunganPendapatan.getText().toString();

        String[] tahuns = new String[1];
        String tahun ="";
        try{
            tahun = this.getArguments().getString("tahun");
        } catch (Exception e){
            System.out.print(e);
        }
        if (tahun != null && !tahun.equals("")) {
            tahuns[0] = tahun;
        }
        ArrayAdapter<String> adapterproducts = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, tahuns);
        spinnerTahun.setAdapter(adapterproducts);


        textHarga.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0){

                    long nilaivolume = 0;
                    try{
                        nilaivolume = Long.parseLong(textVolume.getText().toString());
                    } catch (Exception e){
                    }
                    long nilaiharga = 0;
                    try{
                        nilaiharga = Long.parseLong(s.toString());
                    } catch (Exception e){
                    }
                    long totalharga = 0;
                    try{
                        totalharga = nilaivolume*nilaiharga;
                    } catch (Exception e){
                    }

                    Locale myIndonesianLocale = new Locale("in", "ID");
                    NumberFormat formatKurensi = NumberFormat.getCurrencyInstance(myIndonesianLocale);
                    String nilainominal =  formatKurensi.format(totalharga);
                    textTotalHarga.setText(""+nilainominal);
                }

            }
        });


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                long id = 0;
                try{
                    id = Long.parseLong(textIdDetailPerhitunganPendapatan.getText().toString());
                } catch (Exception e){
                }

                long volume = 0;
                try{
                    volume = Long.parseLong(textVolume.getText().toString());
                } catch (Exception e){
                }

                DetailPerhitunganPendapatanEntity detailperhitunganpendapatanEntity = new DetailPerhitunganPendapatanEntity();
                detailperhitunganpendapatanEntity.setIdDetailPerhitunganPendapatan(id);
                detailperhitunganpendapatanEntity.setRBA(textRBA.getText().toString());

                long tahun = 2022;
                try {
                    tahun = Long.parseLong(spinnerTahun.getItemAtPosition(spinnerTahun.getSelectedItemPosition()).toString());
                } catch (Exception e) {
                    System.out.println("Something went wrong.");
                }
                detailperhitunganpendapatanEntity.setVolume(volume);
                detailperhitunganpendapatanEntity.setTahun(tahun);

                if (detailperhitunganpendapatanEntity.getIdDetailPerhitunganPendapatan() !=0){
                    DetailPerhitunganPendapatanDAO.update(dbHelper, detailperhitunganpendapatanEntity);
                } else {
                    DetailPerhitunganPendapatanDAO.add(dbHelper, detailperhitunganpendapatanEntity);
                }

                Toast.makeText(getActivity(), "Berhasil tersimpan DetailPerhitunganPendapatan "+ textRBA.getText().toString(), Toast.LENGTH_LONG).show();

                textIdDetailPerhitunganPendapatan.setText("");
                textRBA.setText("");
                textVolume.setText("");
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
        // Construct the data source
        String whereclause = DetailPerhitunganPendapatanDAO.FLD_TAHUN +" = " + tahun;
        data = DetailPerhitunganPendapatanDAO.getList(dbHelper, 0, 0, whereclause, "");
        daftar = DetailPerhitunganPendapatanDAO.getListArray(dbHelper, 0,0,whereclause,"");

        long totalhhh = 0;
        totalhhh = DetailPerhitunganPendapatanDAO.getTotalHargaByTahun(dbHelper, tahun);
        Locale myIndonesianLocale = new Locale("in", "ID");
        NumberFormat formatKurensi = NumberFormat.getCurrencyInstance(myIndonesianLocale);
        String nilainominal  = formatKurensi.format(totalhhh);
        textlasttotal.setText(nilainominal);

        ListView ListViewDetailPerhitunganPendapatan = (ListView) rootView.findViewById(R.id.listViewDetailP);
        ArrayList<DetailPendapatanItem> arrayOfDetailPendapatanItems = DetailPendapatanItem.getDetailPendapatanItems(dbHelper, tahun);
        // Create the adapter to convert the array to views
        CustomPendapatanAdapter adapter = new CustomPendapatanAdapter(getActivity(), arrayOfDetailPendapatanItems);
        // Attach the adapter to a ListView
        ListViewDetailPerhitunganPendapatan.setAdapter(adapter);
        ListViewDetailPerhitunganPendapatan.setSelected(true);
        ListViewDetailPerhitunganPendapatan.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                //final String selection = daftar[arg2]; //.getItemAtPosition(arg2).toString();

                final DetailPerhitunganPendapatanEntity detailperhitunganpendapatanEntityX = (DetailPerhitunganPendapatanEntity) data.get(arg2);

                final CharSequence[] dialogitem = {"Update perhitunganpendapatan", "Delete perhitunganpendapatan"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                textIdDetailPerhitunganPendapatan.setText(""+detailperhitunganpendapatanEntityX.getIdDetailPerhitunganPendapatan());
                                textRBA.setText(detailperhitunganpendapatanEntityX.getRBA());
                                textVolume.setText(""+detailperhitunganpendapatanEntityX.getVolume());
                                textHarga.setText(""+detailperhitunganpendapatanEntityX.getHarga());
                                textTotalHarga.setText(""+detailperhitunganpendapatanEntityX.getTotalharga());

                                break;
                            case 1:

                                final CharSequence[] dialogitem2 = {"Yes", "No"};

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                builder2.setTitle("Delete Confirm");
                                builder2.setItems(dialogitem2, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        switch (item) {
                                            case 0:
                                                DetailPerhitunganPendapatanDAO.delete(dbHelper, detailperhitunganpendapatanEntityX);
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
    }
    private void callFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}
