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

public class Swot extends Fragment {

    public Swot(){}
    View rootView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    protected Cursor cursor;
    DataHelper dbHelper;
    Button buttonSave;
    EditText textIdSwot, textSwotKeterangan;

    Spinner dropdownSwotKategori;
    ListView ListViewStrength,ListViewWeakness,ListViewOpportunities,ListViewThreats;
    String[] daftarStrength, daftarWeakness, daftarOpportunities, daftarThreats;

    Vector dataStrength = null;
    Vector dataWeakness = null;
    Vector dataOpportunities = null;
    Vector dataThreats = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.swot, container, false);
        getActivity().setTitle("SWOT");

        dbHelper = new DataHelper(getActivity());

        textIdSwot = (EditText) rootView.findViewById(R.id.editTextIdSwot);
        textSwotKeterangan = (EditText) rootView.findViewById(R.id.edittextswotket);
        dropdownSwotKategori = (Spinner)rootView.findViewById(R.id.spinnerSwotKategori);

        String[] items= new String[4];
        items[0] = "Strength" ;
        items[1] = "Weakness" ;
        items[2] = "Opportunities" ;
        items[3] = "Threats" ;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdownSwotKategori.setAdapter(adapter);

        buttonSave = (Button) rootView.findViewById(R.id.buttontambah);
        final String tIdSwot = textIdSwot.getText().toString();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                long id = 0;
                try{
                    id = Long.parseLong(textIdSwot.getText().toString());
                } catch (Exception e){
                }

                SwotEntity swotEntity = new SwotEntity();
                swotEntity.setIdSwot(id);
                swotEntity.setKeterangan(textSwotKeterangan.getText().toString());
                swotEntity.setKategori(dropdownSwotKategori.getSelectedItemPosition());

                if (swotEntity.getIdSwot() !=0){
                    SwotDAO.update(dbHelper, swotEntity);
                } else {
                    SwotDAO.add(dbHelper, swotEntity);
                }

                Toast.makeText(getActivity(), "Berhasil tersimpan "+ textSwotKeterangan.getText().toString(), Toast.LENGTH_LONG).show();

                textIdSwot.setText("");
                textSwotKeterangan.setText("");
                dropdownSwotKategori.setSelection(0);

                RefreshListStrength();
                RefreshListWeakness();
                RefreshListOpportunities();
                RefreshListThreats();

            }
        });


        RefreshListStrength();
        RefreshListWeakness();
        RefreshListOpportunities();
        RefreshListThreats();
        return rootView;
    }

    public void RefreshListStrength(){

        daftarStrength = SwotDAO.getListArrayOnlyKeterangan(dbHelper, 0,0,SwotDAO.FLD_KATEGORI+" = "+0,"");
        dataStrength = SwotDAO.getList(dbHelper, 0, 0, SwotDAO.FLD_KATEGORI+" = "+0, "");

        ArrayAdapter adapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftarStrength);

        ListViewStrength = (ListView)rootView.findViewById(R.id.listviewStrength);
        ListViewStrength.setAdapter(adapter);
        ListViewStrength.setSelected(true);
        ListViewStrength.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftarStrength[arg2]; //.getItemAtPosition(arg2).toString();

                final SwotEntity swotEntityX = (SwotEntity) dataStrength.get(arg2);

                final CharSequence[] dialogitem = {"Update ", "Delete "};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                textIdSwot.setText(""+swotEntityX.getIdSwot());
                                textSwotKeterangan.setText(swotEntityX.getKeterangan());
                                dropdownSwotKategori.setSelection(swotEntityX.getKategori());

                                break;
                            case 1:

                                final CharSequence[] dialogitem2 = {"Yes", "No"};

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                builder2.setTitle("Delete Confirm");
                                builder2.setItems(dialogitem2, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        switch (item) {
                                            case 0:
                                                SwotDAO.delete(dbHelper, swotEntityX);
                                                RefreshListStrength();
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
        ((ArrayAdapter)ListViewStrength.getAdapter()).notifyDataSetInvalidated();
    }


    public void RefreshListWeakness(){

        daftarWeakness = SwotDAO.getListArrayOnlyKeterangan(dbHelper, 0,0,SwotDAO.FLD_KATEGORI+" = "+1,"");
        dataWeakness = SwotDAO.getList(dbHelper, 0, 0, SwotDAO.FLD_KATEGORI+" = "+1, "");

        ArrayAdapter adapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftarWeakness);

        ListViewWeakness = (ListView)rootView.findViewById(R.id.listviewWeakness);
        ListViewWeakness.setAdapter(adapter);
        ListViewWeakness.setSelected(true);
        ListViewWeakness.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftarWeakness[arg2]; //.getItemAtPosition(arg2).toString();

                final SwotEntity swotEntityX = (SwotEntity) dataWeakness.get(arg2);

                final CharSequence[] dialogitem = {"Update ", "Delete "};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                textIdSwot.setText(""+swotEntityX.getIdSwot());
                                textSwotKeterangan.setText(swotEntityX.getKeterangan());
                                dropdownSwotKategori.setSelection(swotEntityX.getKategori());

                                break;
                            case 1:

                                final CharSequence[] dialogitem2 = {"Yes", "No"};

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                builder2.setTitle("Delete Confirm");
                                builder2.setItems(dialogitem2, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        switch (item) {
                                            case 0:
                                                SwotDAO.delete(dbHelper, swotEntityX);
                                                RefreshListWeakness();
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
        ((ArrayAdapter)ListViewWeakness.getAdapter()).notifyDataSetInvalidated();
    }



    public void RefreshListOpportunities(){

        daftarOpportunities = SwotDAO.getListArrayOnlyKeterangan(dbHelper, 0,0,SwotDAO.FLD_KATEGORI+" = "+2,"");
        dataOpportunities = SwotDAO.getList(dbHelper, 0, 0, SwotDAO.FLD_KATEGORI+" = "+2, "");

        ArrayAdapter adapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftarOpportunities);

        ListViewOpportunities = (ListView)rootView.findViewById(R.id.listviewOpportunities);
        ListViewOpportunities.setAdapter(adapter);
        ListViewOpportunities.setSelected(true);
        ListViewOpportunities.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftarOpportunities[arg2]; //.getItemAtPosition(arg2).toString();

                final SwotEntity swotEntityX = (SwotEntity) dataOpportunities.get(arg2);

                final CharSequence[] dialogitem = {"Update ", "Delete "};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                textIdSwot.setText(""+swotEntityX.getIdSwot());
                                textSwotKeterangan.setText(swotEntityX.getKeterangan());
                                dropdownSwotKategori.setSelection(swotEntityX.getKategori());

                                break;
                            case 1:

                                final CharSequence[] dialogitem2 = {"Yes", "No"};

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                builder2.setTitle("Delete Confirm");
                                builder2.setItems(dialogitem2, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        switch (item) {
                                            case 0:
                                                SwotDAO.delete(dbHelper, swotEntityX);
                                                RefreshListOpportunities();
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
        ((ArrayAdapter)ListViewOpportunities.getAdapter()).notifyDataSetInvalidated();
    }



    public void RefreshListThreats(){

        daftarThreats = SwotDAO.getListArrayOnlyKeterangan(dbHelper, 0,0,SwotDAO.FLD_KATEGORI+" = "+3,"");
        dataThreats = SwotDAO.getList(dbHelper, 0, 0, SwotDAO.FLD_KATEGORI+" = "+3, "");

        ArrayAdapter adapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftarThreats);

        ListViewThreats = (ListView)rootView.findViewById(R.id.listviewThreats);
        ListViewThreats.setAdapter(adapter);
        ListViewThreats.setSelected(true);
        ListViewThreats.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftarThreats[arg2]; //.getItemAtPosition(arg2).toString();

                final SwotEntity swotEntityX = (SwotEntity) dataThreats.get(arg2);

                final CharSequence[] dialogitem = {"Update ", "Delete "};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                textIdSwot.setText(""+swotEntityX.getIdSwot());
                                textSwotKeterangan.setText(swotEntityX.getKeterangan());
                                dropdownSwotKategori.setSelection(swotEntityX.getKategori());

                                break;
                            case 1:

                                final CharSequence[] dialogitem2 = {"Yes", "No"};

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                builder2.setTitle("Delete Confirm");
                                builder2.setItems(dialogitem2, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        switch (item) {
                                            case 0:
                                                SwotDAO.delete(dbHelper, swotEntityX);
                                                RefreshListThreats();
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
        ((ArrayAdapter)ListViewThreats.getAdapter()).notifyDataSetInvalidated();
    }

    private void callFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}
