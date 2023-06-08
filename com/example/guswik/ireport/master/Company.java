package com.example.guswik.ireport.master;

/**
 * Created by GUSWIK on 7/16/2017.
 */

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.guswik.ireport.MainActivity;
import com.example.guswik.ireport.R;
import com.example.guswik.ireport.database.DataHelper;

import java.util.Vector;

public class Company extends Fragment {

    public Company(){}

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;
    protected Cursor cursor;
    DataHelper dbHelper;
    Button buttonSaveCompany;
    EditText textIdCompany, textNamaCompany, textTelpCompany, textAddressCompany, textEmailCompany, textDirektur, textSekretaris, textBendahara;
     //AkunEntity akunEntityX = null;
    View rootView;
    ListView ListViewCompany;
    String[] daftar;

    Vector data = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_company, container, false);
        getActivity().setTitle("Bumdes");
        dbHelper = new DataHelper(getActivity());

        textIdCompany = (EditText) rootView.findViewById(R.id.editTextIdCompany);
        textNamaCompany = (EditText) rootView.findViewById(R.id.editTextCompanyName);
        textEmailCompany = (EditText) rootView.findViewById(R.id.editTextCompanyEmail);
        textTelpCompany = (EditText) rootView.findViewById(R.id.editTextCompanyTelp);
        textAddressCompany = (EditText) rootView.findViewById(R.id.editTextCompanyAddress);
        textDirektur = (EditText) rootView.findViewById(R.id.editTextCompanyDir);
        textSekretaris = (EditText) rootView.findViewById(R.id.editTextCompanySekretaris);
        textBendahara = (EditText) rootView.findViewById(R.id.editTextCompanyBendahara);
        buttonSaveCompany = (Button) rootView.findViewById(R.id.buttonSaveCompany);


        String tIdCompany ="1";

        if (tIdCompany != null && !tIdCompany.equals("")) {

            long id = 0;
            try{
                id = Long.parseLong(tIdCompany);
            }catch (Exception e){}

            CompanyEntity companyEntity = new CompanyEntity();
            companyEntity = CompanyDAO.getById(dbHelper, id);

            textIdCompany.setText("" + companyEntity.getCompanyId());
            textNamaCompany.setText(""+companyEntity.getCompanyName());
            textTelpCompany.setText(""+companyEntity.getTelp());
            textAddressCompany.setText(""+companyEntity.getAlamat());
            textEmailCompany.setText(""+companyEntity.getEmail());
            textDirektur.setText(""+companyEntity.getDirektur());
            textSekretaris.setText(""+companyEntity.getSekretaris());
            textBendahara.setText(""+companyEntity.getBendahara());

        }

        buttonSaveCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                long id = 0;
                try {
                    id = Long.parseLong(textIdCompany.getText().toString());
                } catch (Exception e) {
                }

                CompanyEntity companyEntity = new CompanyEntity();
                companyEntity.setCompanyId(id);
                companyEntity.setCompanyName(textNamaCompany.getText().toString());
                companyEntity.setEmail(textEmailCompany.getText().toString());
                companyEntity.setAlamat(textAddressCompany.getText().toString());
                companyEntity.setDirektur(textDirektur.getText().toString());
                companyEntity.setSekretaris(textSekretaris.getText().toString());
                companyEntity.setBendahara(textBendahara.getText().toString());
                companyEntity.setTelp(Long.parseLong(textTelpCompany.getText().toString()));

                if (companyEntity.getCompanyId() != 0) {
                    CompanyDAO.update(dbHelper, companyEntity);
                } else {
                    CompanyDAO.add(dbHelper, companyEntity);
                }

                Toast.makeText(getActivity(), "Berhasil tersimpan No " + textNamaCompany.getText().toString(), Toast.LENGTH_LONG).show();


                //fragment = new TransactionMultipleList();
                //callFragment(fragment);


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
