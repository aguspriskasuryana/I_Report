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

public class DetailPerhitunganInvestasi extends Fragment {

    public DetailPerhitunganInvestasi(){}
    View rootView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    protected Cursor cursor;
    DataHelper dbHelper;
    Button buttonSave;

    Spinner spinnerTahun;
    EditText textIdDetailPerhitunganInvestasi,textRBA, textVolume, textHarga, textTotalHarga;
    TextView textModal,textlasttotal ;
    ListView ListViewDetailPerhitunganInvestasi;
    String[] daftar;
    long nilaitotal = 0;

    Vector data = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.detailperhitunganinvestasi, container, false);
        getActivity().setTitle("Perhitungan Investasi");

        dbHelper = new DataHelper(getActivity());

        textIdDetailPerhitunganInvestasi = (EditText) rootView.findViewById(R.id.editTextIdDetailPerhitunganInvestasi);
        textRBA = (EditText) rootView.findViewById(R.id.editTextRBA);
        textVolume = (EditText) rootView.findViewById(R.id.editTextVolume);
        spinnerTahun = (Spinner)rootView.findViewById(R.id.spinnertahun);
        textHarga = (EditText) rootView.findViewById(R.id.editTextHarga);
        textTotalHarga = (EditText) rootView.findViewById(R.id.editTextTotalHarga);
        textlasttotal = (TextView) rootView.findViewById(R.id.lasttotal);
        buttonSave = (Button) rootView.findViewById(R.id.buttonSave);

        final String tIdDetailPerhitunganInvestasi = textIdDetailPerhitunganInvestasi.getText().toString();

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
                    id = Long.parseLong(textIdDetailPerhitunganInvestasi.getText().toString());
                } catch (Exception e){
                }

                long volume = 0;
                try{
                    volume = Long.parseLong(textVolume.getText().toString());
                } catch (Exception e){
                }

                DetailPerhitunganInvestasiEntity detailperhitunganinvestasiEntity = new DetailPerhitunganInvestasiEntity();
                detailperhitunganinvestasiEntity.setIdDetailPerhitunganInvestasi(id);
                detailperhitunganinvestasiEntity.setRBA(textRBA.getText().toString());

                long tahun = 2022;
                try {
                    tahun = Long.parseLong(spinnerTahun.getItemAtPosition(spinnerTahun.getSelectedItemPosition()).toString());
                } catch (Exception e) {
                    System.out.println("Something went wrong.");
                }
                detailperhitunganinvestasiEntity.setVolume(volume);
                detailperhitunganinvestasiEntity.setTahun(tahun);

                if (detailperhitunganinvestasiEntity.getIdDetailPerhitunganInvestasi() !=0){
                    DetailPerhitunganInvestasiDAO.update(dbHelper, detailperhitunganinvestasiEntity);
                } else {
                    DetailPerhitunganInvestasiDAO.add(dbHelper, detailperhitunganinvestasiEntity);
                }

                Toast.makeText(getActivity(), "Berhasil tersimpan DetailPerhitunganInvestasi "+ textRBA.getText().toString(), Toast.LENGTH_LONG).show();

                textIdDetailPerhitunganInvestasi.setText("");
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
        String whereclause = DetailPerhitunganInvestasiDAO.FLD_TAHUN +" = " + tahun;
        data = DetailPerhitunganInvestasiDAO.getList(dbHelper, 0, 0, whereclause, "");
        daftar = DetailPerhitunganInvestasiDAO.getListArray(dbHelper, 0,0,whereclause,"");

        long totalhhh = 0;
        totalhhh = DetailPerhitunganInvestasiDAO.getTotalHargaByTahun(dbHelper, tahun);
        Locale myIndonesianLocale = new Locale("in", "ID");
        NumberFormat formatKurensi = NumberFormat.getCurrencyInstance(myIndonesianLocale);
        String nilainominal  = formatKurensi.format(totalhhh);
        textlasttotal.setText(nilainominal);

        ListView ListViewDetailPerhitunganInvestasi = (ListView) rootView.findViewById(R.id.listViewDetailP);
        ArrayList<DetailInvestasiItem> arrayOfDetailInvestasiItems = DetailInvestasiItem.getDetailInvestasiItems(dbHelper, tahun);
        // Create the adapter to convert the array to views
        CustomUsersAdapter adapter = new CustomUsersAdapter(getActivity(), arrayOfDetailInvestasiItems);
        // Attach the adapter to a ListView
        ListViewDetailPerhitunganInvestasi.setAdapter(adapter);
        ListViewDetailPerhitunganInvestasi.setSelected(true);
        ListViewDetailPerhitunganInvestasi.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                //final String selection = daftar[arg2]; //.getItemAtPosition(arg2).toString();
                //DetailInvestasiItem detailperhitunganinvestasiEntityX= arrayOfDetailInvestasiItems.get(arg2);

                final DetailPerhitunganInvestasiEntity detailperhitunganinvestasiEntityX = (DetailPerhitunganInvestasiEntity) data.get(arg2);

                final CharSequence[] dialogitem = {"Update perhitunganinvestasi", "Delete perhitunganinvestasi"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                textIdDetailPerhitunganInvestasi.setText(""+detailperhitunganinvestasiEntityX.getIdDetailPerhitunganInvestasi());
                                textRBA.setText(detailperhitunganinvestasiEntityX.getRBA());
                                textVolume.setText(""+detailperhitunganinvestasiEntityX.getVolume());
                                textHarga.setText(""+detailperhitunganinvestasiEntityX.getHarga());
                                textTotalHarga.setText(""+detailperhitunganinvestasiEntityX.getTotalharga());

                                break;
                            case 1:

                                final CharSequence[] dialogitem2 = {"Yes", "No"};

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                builder2.setTitle("Delete Confirm");
                                builder2.setItems(dialogitem2, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        switch (item) {
                                            case 0:
                                                DetailPerhitunganInvestasiDAO.delete(dbHelper, detailperhitunganinvestasiEntityX);
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
