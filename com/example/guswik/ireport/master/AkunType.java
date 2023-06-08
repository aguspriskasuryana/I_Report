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
import android.widget.Toast;

import com.example.guswik.ireport.R;
import com.example.guswik.ireport.database.DataHelper;

import java.util.Vector;

public class AkunType extends Fragment {

    public AkunType(){}
    View rootView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    protected Cursor cursor;
    DataHelper dbHelper;
    Button buttonSaveAkunType;
    EditText textIdAkunType, textNamaAkunType;
    //AkunEntity akunEntityX = null;

    ListView ListViewAkunType;
    String[] daftar;

    Vector data = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.akuntype, container, false);
        getActivity().setTitle("Akun");

        dbHelper = new DataHelper(getActivity());

        textIdAkunType = (EditText) rootView.findViewById(R.id.editTextIdAkunType);
        textNamaAkunType = (EditText) rootView.findViewById(R.id.editTextAkunType);
        buttonSaveAkunType = (Button) rootView.findViewById(R.id.buttonSaveAkunType);

        final String tIdAkun = textIdAkunType.getText().toString();


        buttonSaveAkunType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                long id = 0;
                try{
                    id = Long.parseLong(textIdAkunType.getText().toString());
                } catch (Exception e){
                }

                AkunTypeEntity akunTypeEntity = new AkunTypeEntity();
                akunTypeEntity.setIdAkunType(id);
                akunTypeEntity.setNamaAkunType(textNamaAkunType.getText().toString());

                if (akunTypeEntity.getIdAkunType() !=0){
                    AkunTypeDAO.update(dbHelper, akunTypeEntity);
                } else {
                    AkunTypeDAO.add(dbHelper, akunTypeEntity);
                }

                Toast.makeText(getActivity(), "Berhasil tersimpan No Akun "+ textNamaAkunType.getText().toString(), Toast.LENGTH_LONG).show();

                textIdAkunType.setText("");
                textNamaAkunType.setText("");

                RefreshList();

            }
        });


        RefreshList();
        return rootView;
    }

    public void RefreshList(){

        daftar = AkunTypeDAO.getListArray(dbHelper, 0,0,"","");
        data = AkunTypeDAO.getList(dbHelper, 0, 0, "", "");

        ArrayAdapter adapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftar);

        ListViewAkunType = (ListView)rootView.findViewById(R.id.listViewAkunType);
        ListViewAkunType.setAdapter(adapter);
        ListViewAkunType.setSelected(true);
        /*
        ListViewAkunType.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2]; //.getItemAtPosition(arg2).toString();

                final AkunTypeEntity akunTypeEntity = (AkunTypeEntity) data.get(arg2);

                final CharSequence[] dialogitem = {"Update akun", "Hapus akun"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                textIdAkunType.setText(""+akunTypeEntity.getIdAkunType());
                                textNamaAkunType.setText(akunTypeEntity.getNamaAkunType());
                                break;
                            case 1:
                                AkunTypeDAO.delete(dbHelper,akunTypeEntity);
                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        */

        ((ArrayAdapter)ListViewAkunType.getAdapter()).notifyDataSetInvalidated();
    }

    private void callFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}
