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
import java.util.Vector;

public class PaybackPeriod extends Fragment {

    public PaybackPeriod(){}

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    DataHelper dbHelper;
    protected Cursor cursor;
    View rootView;
    Spinner spinnerProductId;
    EditText textIdPaybackPeriod,textnilaiInvestasitotal , textnilaiProceedPerTahun, texttahunMaximumPaybackPeriod  ;
    TextView textHasilRumus, textTahunPaybackPeriodActual, textKesimpulan;
    String[] daftar;
    String[] daftaruntukdihitung;
    Button buttonSave;

    Vector data = null;

    ListView ListViewPaybackPeriod;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.payback_period, container, false);
        getActivity().setTitle("Payback Period");


        dbHelper = new DataHelper(getActivity());
        spinnerProductId = (Spinner)rootView.findViewById(R.id.spinnerProductIdPP);
        textIdPaybackPeriod = (EditText) rootView.findViewById(R.id.editTextIdPayback);
        textIdPaybackPeriod.setVisibility(View.INVISIBLE);
        textnilaiInvestasitotal = (EditText) rootView.findViewById(R.id.editTextNilaiInvestasiTotal);
        textnilaiProceedPerTahun = (EditText) rootView.findViewById(R.id.editTextNilaiProceed);
        texttahunMaximumPaybackPeriod = (EditText) rootView.findViewById(R.id.editTextTahunMaxPP);


        textHasilRumus= (TextView) rootView.findViewById(R.id.textViewHasilRumus);
        textTahunPaybackPeriodActual= (TextView) rootView.findViewById(R.id.textViewtahunpaybackperiodactual);
        textKesimpulan = (TextView) rootView.findViewById(R.id.textViewkesimpulanpp);

        String[] products = ProductDAO.getListArray(dbHelper,0,0,"","");
        ArrayAdapter<String> adapterproducts = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, products);
        spinnerProductId.setAdapter(adapterproducts);

        buttonSave = (Button) rootView.findViewById(R.id.buttonSavePayback);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                long id = 0;
                try{
                    id = Long.parseLong(textIdPaybackPeriod.getText().toString());
                } catch (Exception e){
                }

                PaybackPeriodEntity paybackperiodEntity = new PaybackPeriodEntity();
                paybackperiodEntity.setIdPaybackPeriod(id);
                long productIdx = 1;
                try {
                    productIdx = ProductDAO.getIdByName(dbHelper,spinnerProductId.getItemAtPosition(spinnerProductId.getSelectedItemPosition()).toString());
                } catch (Exception e) {
                    System.out.println("Something went wrong.");
                }
                paybackperiodEntity.setIdProduct(productIdx);
                paybackperiodEntity.setNilaiInvestasitotal(Double.parseDouble(textnilaiInvestasitotal.getText().toString()));
                paybackperiodEntity.setTahunMaximumPaybackPeriod(Long.parseLong(texttahunMaximumPaybackPeriod.getText().toString()));
                paybackperiodEntity.setNilaiProceedPerTahun(Double.parseDouble(textnilaiProceedPerTahun.getText().toString()));

                if (paybackperiodEntity.getIdPaybackPeriod() !=0){
                    PaybackPeriodDAO.update(dbHelper, paybackperiodEntity);
                    PaybackPeriodDAO.updatealltotalinvestasidanmaxpayback(dbHelper, paybackperiodEntity);
                } else {
                    PaybackPeriodDAO.add(dbHelper, paybackperiodEntity);
                    PaybackPeriodDAO.updatealltotalinvestasidanmaxpayback(dbHelper, paybackperiodEntity);
                }

                Toast.makeText(getActivity(), "Berhasil tersimpan Nilai "+ textnilaiProceedPerTahun.getText().toString(), Toast.LENGTH_LONG).show();

                textIdPaybackPeriod.setText("");
                texttahunMaximumPaybackPeriod.setText("");
                textnilaiInvestasitotal.setText("");
                textnilaiProceedPerTahun.setText("");



                RefreshList();

            }
        });

        spinnerProductId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        DecimalFormat df1 = new DecimalFormat("@######");
        long productIdx = 1;
        try {
            productIdx = ProductDAO.getIdByName(dbHelper,spinnerProductId.getItemAtPosition(spinnerProductId.getSelectedItemPosition()).toString());
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
        String whereclause = PaybackPeriodDAO.FLD_PRODUCT_ID +" = " + productIdx;
        daftar = PaybackPeriodDAO.getListArrayPaybackPeriode(dbHelper, 0,0,whereclause,"");

        //daftaruntukdihitung = PaybackPeriodDAO.getListArrayPaybackPeriode(dbHelper, 0,0,"","");
        data = PaybackPeriodDAO.getList(dbHelper, 0, 0, whereclause, "");

        double nilaiA = 0;
        double nilaiB = 0;
        double nilaiBTot = 0;
        double nilaiC = 0;
        int nilaiN = 0;
        long tahunMaximumPaybackPeriodx= 0;
        if (data.size() >0) {
            final PaybackPeriodEntity paybackperiodEnt = (PaybackPeriodEntity) data.get(0);
            textnilaiInvestasitotal.setText(df1.format(paybackperiodEnt.getNilaiInvestasitotal()));
            texttahunMaximumPaybackPeriod.setText("" + paybackperiodEnt.getTahunMaximumPaybackPeriod());
            for (int cc=0; cc < data.size(); cc++) {
                final PaybackPeriodEntity paybackperiod = (PaybackPeriodEntity) data.get(cc);
                double nilaiproceed = paybackperiod.getNilaiProceedPerTahun();
                double nilaiinvesttotal = paybackperiod.getNilaiInvestasitotal();
                tahunMaximumPaybackPeriodx =  paybackperiod.getTahunMaximumPaybackPeriod();
                nilaiA = nilaiinvesttotal;

                if ((nilaiBTot+nilaiproceed) <= nilaiinvesttotal){
                    nilaiN =cc+1;
                    nilaiB = nilaiB+nilaiproceed;
                }

                nilaiBTot = nilaiBTot+nilaiproceed;
                if ((nilaiC) < nilaiinvesttotal){
                    nilaiC = nilaiC+nilaiproceed;
                }
            }

        }
        double nilaiatas = nilaiA - nilaiB ;
        double nilaibawah = nilaiC - nilaiB ;
        double nilaibagidikalisatu = nilaiatas/nilaibawah*1;
        double NilaiTahunPaybackPeriodActual = nilaiN+nilaibagidikalisatu;
        textTahunPaybackPeriodActual.setText(" = "+String.format("%.2f",NilaiTahunPaybackPeriodActual)+" Tahun");
        long status = 0;
        if (NilaiTahunPaybackPeriodActual <= tahunMaximumPaybackPeriodx ) {
            textKesimpulan.setText(" PaybackPeriod Actual <= Max PaybackPeriod (Investasi Bisa Diterima)");
            status = 1;
        }else {
            textKesimpulan.setText(" PaybackPeriod Actual > Max PaybackPeriod Investasi Belum Bisa Diterima");
            status = 0;
        }

        textHasilRumus.setText(" = "+nilaiN+" + ("+df1.format(nilaiA)+"-"+df1.format(nilaiB)+") / ("+df1.format(nilaiC)+"-"+df1.format(nilaiB)+") X 1 Tahun");
        PaybackPeriodDAO.updatestatus(dbHelper,productIdx,status);

        ArrayAdapter adapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftar);

        ListViewPaybackPeriod = (ListView)rootView.findViewById(R.id.listViewP);
        ListViewPaybackPeriod.setAdapter(adapter);
        ListViewPaybackPeriod.setSelected(true);
        ListViewPaybackPeriod.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2]; //.getItemAtPosition(arg2).toString();
                final PaybackPeriodEntity paybackperiodEntityX = (PaybackPeriodEntity) data.get(arg2);
                final CharSequence[] dialogitem = {"Update paybackperiod", "Delete paybackperiod"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:


                                textIdPaybackPeriod.setText(""+paybackperiodEntityX.getIdPaybackPeriod());
                                textnilaiInvestasitotal.setText(df1.format(paybackperiodEntityX.getNilaiInvestasitotal()));
                                textnilaiProceedPerTahun.setText(df1.format(paybackperiodEntityX.getNilaiProceedPerTahun()));

                                break;
                            case 1:

                                final CharSequence[] dialogitem2 = {"Yes", "No"};

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                builder2.setTitle("Delete Confirm");
                                builder2.setItems(dialogitem2, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        switch (item) {
                                            case 0:
                                                PaybackPeriodDAO.delete(dbHelper, paybackperiodEntityX);
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
        ((ArrayAdapter)ListViewPaybackPeriod.getAdapter()).notifyDataSetInvalidated();
    }

    private void callFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}
