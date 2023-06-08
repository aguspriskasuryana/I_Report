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
import android.widget.Toast;

import com.example.guswik.ireport.R;
import com.example.guswik.ireport.database.DataHelper;

import java.util.Vector;

public class Akun extends Fragment {

    public Akun(){}
    View rootView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    protected Cursor cursor;
    DataHelper dbHelper;
    Button buttonSave;
    EditText textIdAkun,textNoAkun, textNamaAkun;

    Spinner dropdownAkunType;
    ListView ListViewAkun;
    String[] daftar;

    Vector data = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.akun, container, false);
        getActivity().setTitle("Akun");

        dbHelper = new DataHelper(getActivity());

        textIdAkun = (EditText) rootView.findViewById(R.id.editTextIdAkun);
        textNoAkun = (EditText) rootView.findViewById(R.id.editTextNilaiInvestasiTotal);
        textNamaAkun = (EditText) rootView.findViewById(R.id.editTextNamaAkun);
        dropdownAkunType = (Spinner)rootView.findViewById(R.id.spinnerAkunType);

        String[] items = AkunTypeDAO.getListArray(dbHelper,0,0,"","");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdownAkunType.setAdapter(adapter);


        buttonSave = (Button) rootView.findViewById(R.id.buttonSaveAkun);

        final String tIdAkun = textIdAkun.getText().toString();


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                long id = 0;
                try{
                    id = Long.parseLong(textIdAkun.getText().toString());
                } catch (Exception e){
                }

                AkunEntity akunEntity = new AkunEntity();
                akunEntity.setIdAkun(id);
                akunEntity.setNoAkun(textNoAkun.getText().toString());
                akunEntity.setNamaAkun(textNamaAkun.getText().toString());

                long akuntypeId = AkunTypeDAO.getIdByName(dbHelper,dropdownAkunType.getItemAtPosition(dropdownAkunType.getSelectedItemPosition()).toString());
                akunEntity.setIdAkunType(akuntypeId);

                if (akunEntity.getIdAkun() !=0){
                    AkunDAO.update(dbHelper, akunEntity);
                } else {
                    AkunDAO.add(dbHelper, akunEntity);
                }

                Toast.makeText(getActivity(), "Berhasil tersimpan No Akun "+ textNoAkun.getText().toString(), Toast.LENGTH_LONG).show();

                textIdAkun.setText("");
                textNoAkun.setText("");
                textNamaAkun.setText("");

                RefreshList();

            }
        });


        RefreshList();
        return rootView;
    }

    public void RefreshList(){

        daftar = AkunDAO.getListArrayAkunWithType(dbHelper, 0,0,"","");
        data = AkunDAO.getList(dbHelper, 0, 0, "", "");

        ArrayAdapter adapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftar);

        ListViewAkun = (ListView)rootView.findViewById(R.id.listViewAkun);
        ListViewAkun.setAdapter(adapter);
        ListViewAkun.setSelected(true);
        ListViewAkun.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2]; //.getItemAtPosition(arg2).toString();

                final AkunEntity akunEntityX = (AkunEntity) data.get(arg2);

                final CharSequence[] dialogitem = {"Update akun", "Delete akun"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                textIdAkun.setText(""+akunEntityX.getIdAkun());
                                textNoAkun.setText(akunEntityX.getNoAkun());
                                textNamaAkun.setText(akunEntityX.getNamaAkun());

                                ArrayAdapter myAdap = (ArrayAdapter) dropdownAkunType.getAdapter(); //cast to an ArrayAdapter
                                int spinnerPosition = myAdap.getPosition(""+ AkunTypeDAO.getNameById(dbHelper,akunEntityX.getIdAkunType()));
                                dropdownAkunType.setSelection(spinnerPosition);

                                break;
                            case 1:

                                final CharSequence[] dialogitem2 = {"Yes", "No"};

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                builder2.setTitle("Delete Confirm");
                                builder2.setItems(dialogitem2, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        switch (item) {
                                            case 0:
                                                AkunDAO.delete(dbHelper, akunEntityX);
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
        ((ArrayAdapter)ListViewAkun.getAdapter()).notifyDataSetInvalidated();
    }
    private void callFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}
