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
import android.graphics.Color;
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

import com.example.guswik.ireport.R;
import com.example.guswik.ireport.database.DataHelper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Vector;

public class NetPresentValue extends Fragment {

    public NetPresentValue(){}

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    DataHelper dbHelper;
    protected Cursor cursor;
    View rootView;
    Spinner spinnerProductId;

    EditText textIdNetPresentValue,textnilaiInvestasitotal , textnilaiProceedPerTahun, textratepersent, textjumlahperiode  ;
    TextView textHasilRumus, textnilaiakhir, textKesimpulan, textKesimpulanPI;
    String[] daftar;
    Button buttonSave;

    Vector data = null;

    ListView ListViewNetPresentValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.net_present_value, container, false);
        getActivity().setTitle("Net Present Value");


        dbHelper = new DataHelper(getActivity());

        spinnerProductId = (Spinner)rootView.findViewById(R.id.spinnerProductIdNPV);
        textIdNetPresentValue = (EditText) rootView.findViewById(R.id.editTextIdNetPresentValue);
        textIdNetPresentValue.setVisibility(View.INVISIBLE);
        textnilaiInvestasitotal = (EditText) rootView.findViewById(R.id.editTextNilaiInvestasiTotal);
        textnilaiProceedPerTahun = (EditText) rootView.findViewById(R.id.editTextNilaiProceed);
        textratepersent = (EditText) rootView.findViewById(R.id.editTextRatePersen);
        textjumlahperiode = (EditText) rootView.findViewById(R.id.editJumlahPeriod);


        textHasilRumus= (TextView) rootView.findViewById(R.id.textViewHasilRumus);
        textnilaiakhir= (TextView) rootView.findViewById(R.id.textViewhasil);
        textKesimpulan = (TextView) rootView.findViewById(R.id.textViewkesimpulanpp);
        textKesimpulanPI = (TextView) rootView.findViewById(R.id.textViewkesimpulanPI);

        buttonSave = (Button) rootView.findViewById(R.id.buttonSaveNetPresentValue);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                long id = 0;
                try{
                    id = Long.parseLong(textIdNetPresentValue.getText().toString());
                } catch (Exception e){
                }
                long productIdx = 1;
                try {
                    productIdx = ProductDAO.getIdByName(dbHelper,spinnerProductId.getItemAtPosition(spinnerProductId.getSelectedItemPosition()).toString());
                } catch (Exception e) {
                    System.out.println("Something went wrong.");
                }

                NetPresentValueEntity netpresentvalueEntity = new NetPresentValueEntity();
                netpresentvalueEntity.setIdNetPresentValue(id);
                netpresentvalueEntity.setProductid(productIdx);
                netpresentvalueEntity.setNilaiInvestasitotal(Double.parseDouble(textnilaiInvestasitotal.getText().toString()));
                netpresentvalueEntity.setJumlahperiode(Long.parseLong(textjumlahperiode.getText().toString()));
                netpresentvalueEntity.setNilaiProceedPerTahun(Double.parseDouble(textnilaiProceedPerTahun.getText().toString()));
                netpresentvalueEntity.setNilairatepersen(Double.parseDouble(textratepersent.getText().toString()));

                if (netpresentvalueEntity.getIdNetPresentValue() !=0){
                    NetPresentValueDAO.update(dbHelper, netpresentvalueEntity);
                    NetPresentValueDAO.updatealltotalinvestasirateperiode(dbHelper, netpresentvalueEntity);
                } else {
                    NetPresentValueDAO.add(dbHelper, netpresentvalueEntity);
                    NetPresentValueDAO.updatealltotalinvestasirateperiode(dbHelper, netpresentvalueEntity);
                }

                Toast.makeText(getActivity(), "Berhasil tersimpan Nilai "+ textnilaiProceedPerTahun.getText().toString(), Toast.LENGTH_LONG).show();

                textIdNetPresentValue.setText("");
                textratepersent.setText("");
                textjumlahperiode.setText("");
                textnilaiInvestasitotal.setText("");
                textnilaiProceedPerTahun.setText("");

                RefreshList();

            }
        });
        String[] products = ProductDAO.getListArray(dbHelper,0,0,"","");
        ArrayAdapter<String> adapterproducts = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, products);
        spinnerProductId.setAdapter(adapterproducts);
        spinnerProductId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
               // ((TextView) parentView.getChildAt(0)).setTextColor(Color.BLUE);
               // ((TextView) parentView.getChildAt(0)).setTextSize(30);
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
        long productIdx = 1;
        try {
            productIdx = ProductDAO.getIdByName(dbHelper,spinnerProductId.getItemAtPosition(spinnerProductId.getSelectedItemPosition()).toString());
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
        String whereclause = NetPresentValueDAO.FLD_PRODUCT_ID +" = " + productIdx;
        daftar = NetPresentValueDAO.getListArrayNetPresentValue(dbHelper, 0,0,whereclause,"");
        double totalnilaisekarang = NetPresentValueDAO.getListArrayNetPresentValuetotalpv(dbHelper, 0,0,whereclause,"");

        //daftaruntukdihitung = NetPresentValueDAO.getListArrayNetPresentValuee(dbHelper, 0,0,"","");
        data = NetPresentValueDAO.getList(dbHelper, 0, 0, whereclause, "");

        double nilaiA = 0;


        if (data.size() >0) {
            final NetPresentValueEntity netPresentValueEntity = (NetPresentValueEntity) data.get(0);
            int valinv =  (int) netPresentValueEntity.getNilaiInvestasitotal();
            textnilaiInvestasitotal.setText("" + valinv);
            textratepersent.setText("" + netPresentValueEntity.getNilairatepersen());
            textjumlahperiode.setText("" + netPresentValueEntity.getJumlahperiode());
            Locale myIndonesianLocale = new Locale("in", "ID");
            NumberFormat formatKurensi = NumberFormat.getCurrencyInstance(myIndonesianLocale);
            textnilaiakhir.setText("= "+formatKurensi.format(totalnilaisekarang-valinv));

            textHasilRumus.setText("= ("+formatKurensi.format(totalnilaisekarang) +") - "+formatKurensi.format(valinv));

            long statusnpv = 0;
            if ((totalnilaisekarang-valinv)>0){
                textKesimpulan.setText("= Nilai NPV > 0 (Investasi Bisa Diterima)");
                statusnpv = 1;
            }else if ((totalnilaisekarang-valinv) ==0){
                textKesimpulan.setText("= Nilai NPV = 0 (Investasi Perlu Ditinjau kembali)");
                statusnpv = 0;
            }else {
                textKesimpulan.setText("= Nilai NPV < 0 (Investasi ditolak)");
                statusnpv = 0;
            }

            //Profitability index

            long statuspi = 0;
            double valuePI = (totalnilaisekarang/valinv);
            if (valuePI>1){
                textKesimpulanPI.setText("= Nilai PI("+ String.format("%.4f", valuePI)+") > 1 (Investasi Bisa Diterima)");
                statuspi = 1;
            }else {
                textKesimpulanPI.setText("= Nilai PI("+ String.format("%.4f", valuePI)+") <= 1 (Investasi Tidak Menguntungkan)");
                statuspi = 0;
            }

            NetPresentValueDAO.updatestatus(dbHelper,productIdx,statusnpv,statuspi);

        }


        ArrayAdapter adapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftar);

        ListViewNetPresentValue = (ListView)rootView.findViewById(R.id.listViewNPV);
        ListViewNetPresentValue.setAdapter(adapter);
        ListViewNetPresentValue.setSelected(true);
        ListViewNetPresentValue.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2]; //.getItemAtPosition(arg2).toString();
                final NetPresentValueEntity netpresentvalueEntityX = (NetPresentValueEntity) data.get(arg2);
                final CharSequence[] dialogitem = {"Update netpresentvalue", "Delete netpresentvalue"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                DecimalFormat df1 = new DecimalFormat("@######");
                                textIdNetPresentValue.setText(""+netpresentvalueEntityX.getIdNetPresentValue());
                                textnilaiInvestasitotal.setText(df1.format(netpresentvalueEntityX.getNilaiInvestasitotal()));
                                textnilaiProceedPerTahun.setText(df1.format(netpresentvalueEntityX.getNilaiProceedPerTahun()));
                                textratepersent.setText(df1.format(netpresentvalueEntityX.getNilairatepersen()));
                                textjumlahperiode.setText(""+netpresentvalueEntityX.getJumlahperiode());

                                break;
                            case 1:

                                final CharSequence[] dialogitem2 = {"Yes", "No"};

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                builder2.setTitle("Delete Confirm");
                                builder2.setItems(dialogitem2, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        switch (item) {
                                            case 0:
                                                NetPresentValueDAO.delete(dbHelper, netpresentvalueEntityX);
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
        ((ArrayAdapter)ListViewNetPresentValue.getAdapter()).notifyDataSetInvalidated();
    }

    private void callFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}
