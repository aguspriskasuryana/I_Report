package com.example.guswik.ireport.master;

/**
 * Created by GUSWIK on 1/12/2023.
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

public class DetailPerhitunganAnalysis extends Fragment {

    public DetailPerhitunganAnalysis(){}
    View rootView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    protected Cursor cursor;
    DataHelper dbHelper;
    TextView hitungawal,investasisurplus,hasilanalysis ;

    Vector data = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.detailperhitungananalysis, container, false);
        getActivity().setTitle("Perhitungan Analysis");

        hitungawal = (TextView) rootView.findViewById(R.id.hitungawal);
        investasisurplus = (TextView) rootView.findViewById(R.id.investasisurplus);
        hasilanalysis = (TextView) rootView.findViewById(R.id.hasilanalysis);

        dbHelper = new DataHelper(getActivity());
        

        RefreshList();
        return rootView;
    }

    public void RefreshList(){
        String tahunstring ="";

        long inttahun = 0;
        try{
            tahunstring = this.getArguments().getString("tahun");
        } catch (Exception e){
            System.out.print(e);
        }
        if (tahunstring != null && !tahunstring.equals("")) {
            try{
                inttahun = Long.parseLong(tahunstring);
            }catch (Exception e){}
        }


        Locale myIndonesianLocale = new Locale("in", "ID");
        NumberFormat formatKurensi = NumberFormat.getCurrencyInstance(myIndonesianLocale);

        long totalinvestasi = 0;
        totalinvestasi = DetailPerhitunganInvestasiDAO.getTotalHargaByTahun(dbHelper, inttahun);
        String nilainominaltotalinvestasi  = formatKurensi.format(totalinvestasi);

        long totalbiaya = 0;
        totalbiaya = DetailPerhitunganBiayaDAO.getTotalHargaByTahun(dbHelper, inttahun);
        String nilainominaltotalbiaya  = formatKurensi.format(totalbiaya);

        long totalpendapatan = 0;
        totalpendapatan = DetailPerhitunganPendapatanDAO.getTotalHargaByTahun(dbHelper, inttahun);
        String nilainominaltotalpendapatan  = formatKurensi.format(totalpendapatan);

        long surplusperbulan = 0 ;
        surplusperbulan = totalpendapatan - totalbiaya;
        String nnn = nilainominaltotalinvestasi+" / ("+"12 * " + surplusperbulan+ ")";
        hitungawal.setText(nnn);
        double nilaisurplussetahun = 12*surplusperbulan;
        String nilainominalsurplussetahun  = formatKurensi.format(nilaisurplussetahun);
        investasisurplus.setText(nilainominaltotalinvestasi+" / " +nilainominalsurplussetahun);
        double nilaihasil = 0;
        nilaihasil = totalinvestasi/nilaisurplussetahun;
        String nilainominalhasil  = formatKurensi.format(nilaihasil);
        hasilanalysis.setText(nilainominalhasil + " Tahun");
    }

    private void callFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}
