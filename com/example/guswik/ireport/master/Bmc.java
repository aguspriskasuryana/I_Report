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

public class Bmc extends Fragment {

    public Bmc(){}
    View rootView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    protected Cursor cursor;
    DataHelper dbHelper;
    Button buttonSave;
    EditText textIdBmc, textBmcKeterangan;

    Spinner dropdownBmcKategori;
    ListView ListViewKeyPartners,ListViewKeyActivities,ListViewValueProposition,ListViewCustomerRelationship,ListViewCustomerSegmen,ListViewKeyResources, ListViewChannels, ListViewCostStructure, ListViewRevenueStreams;
    String[] daftarKeyPartners, daftarKeyActivities, daftarValueProposition, daftarCustomerRelationship, daftarCustomerSegmen, daftarKeyResources, daftarChannels, daftarCostStructure, daftarRevenueStreams;

    Vector dataKeyPartners = null;
    Vector dataKeyActivities = null;
    Vector dataValueProposition = null;
    Vector dataCustomerRelationship = null;
    Vector dataCustomerSegmen = null;
    Vector dataKeyResources = null;
    Vector dataChannels = null;
    Vector dataCostStructure = null;
    Vector dataRevenueStreams = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.bmc, container, false);
        getActivity().setTitle("BMC");

        dbHelper = new DataHelper(getActivity());

        textIdBmc = (EditText) rootView.findViewById(R.id.editTextIdBmc);
        textBmcKeterangan = (EditText) rootView.findViewById(R.id.edittextbmcket);
        dropdownBmcKategori = (Spinner)rootView.findViewById(R.id.spinnerBmcKategori);

        String[] items= new String[9];
        items[0] = "Key Partners" ;
        items[1] = "Key Activities" ;
        items[2] = "Value Proposition" ;
        items[3] = "Customer Relationship" ;
        items[4] = "Customer Segmen" ;
        items[5] = "Key Resources" ;
        items[6] = "Channels" ;
        items[7] = "Cost Structure" ;
        items[8] = "Revenue Streams" ;
        //items[9] = "Customer Relationship" ;

        /*1 = Key Partners ;
        2 = Key Activities;
        3 = Value Proposition;
        4 = Customer Relationship;
        5 = Customer Segmen;
        6 = Key Resources;
        7 = Channels;
        8 = Cost Structure;
        9 = Revenue Streams;*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdownBmcKategori.setAdapter(adapter);

        buttonSave = (Button) rootView.findViewById(R.id.buttontambah);
        final String tIdBmc = textIdBmc.getText().toString();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                long id = 0;
                try{
                    id = Long.parseLong(textIdBmc.getText().toString());
                } catch (Exception e){
                }

                BmcEntity bmcEntity = new BmcEntity();
                bmcEntity.setIdBMC(id);
                bmcEntity.setKeterangan(textBmcKeterangan.getText().toString());
                bmcEntity.setKategoriBMC(dropdownBmcKategori.getSelectedItemPosition());

                if (bmcEntity.getIdBMC() !=0){
                    BmcDAO.update(dbHelper, bmcEntity);
                } else {
                    BmcDAO.add(dbHelper, bmcEntity);
                }

                Toast.makeText(getActivity(), "Berhasil tersimpan "+ textBmcKeterangan.getText().toString(), Toast.LENGTH_LONG).show();

                textIdBmc.setText("");
                textBmcKeterangan.setText("");
                dropdownBmcKategori.setSelection(0);

                RefreshListKeyPartners();
                RefreshListKeyActivities();
                RefreshListValueProposition();
                RefreshListCustomerRelationship();
                RefreshListCustomerSegmen();
                RefreshListKeyResources();
                RefreshListChannels();
                RefreshListCostStructure();
                RefreshListRevenueStreams();

            }
        });


        RefreshListKeyPartners();
        RefreshListKeyActivities();
        RefreshListValueProposition();
        RefreshListCustomerRelationship();
        RefreshListCustomerSegmen();
        RefreshListKeyResources();
        RefreshListChannels();
        RefreshListCostStructure();
        RefreshListRevenueStreams();
        return rootView;
    }

    public void RefreshListKeyPartners(){

        daftarKeyPartners = BmcDAO.getListArrayOnlyKeterangan(dbHelper, 0,0,BmcDAO.FLD_KATEGORI+" = "+0,"");
        dataKeyPartners = BmcDAO.getList(dbHelper, 0, 0, BmcDAO.FLD_KATEGORI+" = "+0, "");

        ArrayAdapter adapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftarKeyPartners);

        ListViewKeyPartners = (ListView)rootView.findViewById(R.id.listviewKeyPartners);
        ListViewKeyPartners.setAdapter(adapter);
        ListViewKeyPartners.setSelected(true);
        ListViewKeyPartners.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftarKeyPartners[arg2]; //.getItemAtPosition(arg2).toString();

                final BmcEntity bmcEntityX = (BmcEntity) dataKeyPartners.get(arg2);

                final CharSequence[] dialogitem = {"Update ", "Delete "};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                textIdBmc.setText(""+bmcEntityX.getIdBMC());
                                textBmcKeterangan.setText(bmcEntityX.getKeterangan());
                                dropdownBmcKategori.setSelection(bmcEntityX.getKategoriBMC());

                                break;
                            case 1:

                                final CharSequence[] dialogitem2 = {"Yes", "No"};

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                builder2.setTitle("Delete Confirm");
                                builder2.setItems(dialogitem2, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        switch (item) {
                                            case 0:
                                                BmcDAO.delete(dbHelper, bmcEntityX);
                                                RefreshListKeyPartners();
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
        ((ArrayAdapter)ListViewKeyPartners.getAdapter()).notifyDataSetInvalidated();
    }


    public void RefreshListKeyActivities(){

        daftarKeyActivities = BmcDAO.getListArrayOnlyKeterangan(dbHelper, 0,0,BmcDAO.FLD_KATEGORI+" = "+1,"");
        dataKeyActivities = BmcDAO.getList(dbHelper, 0, 0, BmcDAO.FLD_KATEGORI+" = "+1, "");

        ArrayAdapter adapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftarKeyActivities);

        ListViewKeyActivities = (ListView)rootView.findViewById(R.id.listviewKeyActivities);
        ListViewKeyActivities.setAdapter(adapter);
        ListViewKeyActivities.setSelected(true);
        ListViewKeyActivities.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftarKeyActivities[arg2]; //.getItemAtPosition(arg2).toString();

                final BmcEntity bmcEntityX = (BmcEntity) dataKeyActivities.get(arg2);

                final CharSequence[] dialogitem = {"Update ", "Delete "};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                textIdBmc.setText(""+bmcEntityX.getIdBMC());
                                textBmcKeterangan.setText(bmcEntityX.getKeterangan());
                                dropdownBmcKategori.setSelection(bmcEntityX.getKategoriBMC());

                                break;
                            case 1:

                                final CharSequence[] dialogitem2 = {"Yes", "No"};

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                builder2.setTitle("Delete Confirm");
                                builder2.setItems(dialogitem2, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        switch (item) {
                                            case 0:
                                                BmcDAO.delete(dbHelper, bmcEntityX);
                                                RefreshListKeyActivities();
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
        ((ArrayAdapter)ListViewKeyActivities.getAdapter()).notifyDataSetInvalidated();
    }



    public void RefreshListValueProposition(){

        daftarValueProposition = BmcDAO.getListArrayOnlyKeterangan(dbHelper, 0,0,BmcDAO.FLD_KATEGORI+" = "+2,"");
        dataValueProposition = BmcDAO.getList(dbHelper, 0, 0, BmcDAO.FLD_KATEGORI+" = "+2, "");

        ArrayAdapter adapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftarValueProposition);

        ListViewValueProposition = (ListView)rootView.findViewById(R.id.listviewValueProposition);
        ListViewValueProposition.setAdapter(adapter);
        ListViewValueProposition.setSelected(true);
        ListViewValueProposition.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftarValueProposition[arg2]; //.getItemAtPosition(arg2).toString();

                final BmcEntity bmcEntityX = (BmcEntity) dataValueProposition.get(arg2);

                final CharSequence[] dialogitem = {"Update ", "Delete "};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                textIdBmc.setText(""+bmcEntityX.getIdBMC());
                                textBmcKeterangan.setText(bmcEntityX.getKeterangan());
                                dropdownBmcKategori.setSelection(bmcEntityX.getKategoriBMC());

                                break;
                            case 1:

                                final CharSequence[] dialogitem2 = {"Yes", "No"};

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                builder2.setTitle("Delete Confirm");
                                builder2.setItems(dialogitem2, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        switch (item) {
                                            case 0:
                                                BmcDAO.delete(dbHelper, bmcEntityX);
                                                RefreshListValueProposition();
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
        ((ArrayAdapter)ListViewValueProposition.getAdapter()).notifyDataSetInvalidated();
    }



    public void RefreshListCustomerRelationship(){

        daftarCustomerRelationship = BmcDAO.getListArrayOnlyKeterangan(dbHelper, 0,0,BmcDAO.FLD_KATEGORI+" = "+3,"");
        dataCustomerRelationship = BmcDAO.getList(dbHelper, 0, 0, BmcDAO.FLD_KATEGORI+" = "+3, "");

        ArrayAdapter adapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftarCustomerRelationship);

        ListViewCustomerRelationship = (ListView)rootView.findViewById(R.id.listviewCustomerRelationship);
        ListViewCustomerRelationship.setAdapter(adapter);
        ListViewCustomerRelationship.setSelected(true);
        ListViewCustomerRelationship.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftarCustomerRelationship[arg2]; //.getItemAtPosition(arg2).toString();

                final BmcEntity bmcEntityX = (BmcEntity) dataCustomerRelationship.get(arg2);

                final CharSequence[] dialogitem = {"Update ", "Delete "};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                textIdBmc.setText(""+bmcEntityX.getIdBMC());
                                textBmcKeterangan.setText(bmcEntityX.getKeterangan());
                                dropdownBmcKategori.setSelection(bmcEntityX.getKategoriBMC());

                                break;
                            case 1:

                                final CharSequence[] dialogitem2 = {"Yes", "No"};

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                builder2.setTitle("Delete Confirm");
                                builder2.setItems(dialogitem2, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        switch (item) {
                                            case 0:
                                                BmcDAO.delete(dbHelper, bmcEntityX);
                                                RefreshListCustomerRelationship();
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
        ((ArrayAdapter)ListViewCustomerRelationship.getAdapter()).notifyDataSetInvalidated();
    }


    public void RefreshListCustomerSegmen(){

        daftarCustomerSegmen = BmcDAO.getListArrayOnlyKeterangan(dbHelper, 0,0,BmcDAO.FLD_KATEGORI+" = "+4,"");
        dataCustomerSegmen = BmcDAO.getList(dbHelper, 0, 0, BmcDAO.FLD_KATEGORI+" = "+4, "");

        ArrayAdapter adapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftarCustomerSegmen);

        ListViewCustomerSegmen = (ListView)rootView.findViewById(R.id.listviewCustomerSegmen);
        ListViewCustomerSegmen.setAdapter(adapter);
        ListViewCustomerSegmen.setSelected(true);
        ListViewCustomerSegmen.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftarCustomerSegmen[arg2]; //.getItemAtPosition(arg2).toString();

                final BmcEntity bmcEntityX = (BmcEntity) dataCustomerSegmen.get(arg2);

                final CharSequence[] dialogitem = {"Update ", "Delete "};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                textIdBmc.setText(""+bmcEntityX.getIdBMC());
                                textBmcKeterangan.setText(bmcEntityX.getKeterangan());
                                dropdownBmcKategori.setSelection(bmcEntityX.getKategoriBMC());

                                break;
                            case 1:

                                final CharSequence[] dialogitem2 = {"Yes", "No"};

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                builder2.setTitle("Delete Confirm");
                                builder2.setItems(dialogitem2, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        switch (item) {
                                            case 0:
                                                BmcDAO.delete(dbHelper, bmcEntityX);
                                                RefreshListCustomerSegmen();
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
        ((ArrayAdapter)ListViewCustomerSegmen.getAdapter()).notifyDataSetInvalidated();
    }

    public void RefreshListKeyResources(){

        daftarKeyResources = BmcDAO.getListArrayOnlyKeterangan(dbHelper, 0,0,BmcDAO.FLD_KATEGORI+" = "+5,"");
        dataKeyResources = BmcDAO.getList(dbHelper, 0, 0, BmcDAO.FLD_KATEGORI+" = "+5, "");

        ArrayAdapter adapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftarKeyResources);

        ListViewKeyResources = (ListView)rootView.findViewById(R.id.listviewKeyResources);
        ListViewKeyResources.setAdapter(adapter);
        ListViewKeyResources.setSelected(true);
        ListViewKeyResources.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftarKeyResources[arg2]; //.getItemAtPosition(arg2).toString();

                final BmcEntity bmcEntityX = (BmcEntity) dataKeyResources.get(arg2);

                final CharSequence[] dialogitem = {"Update ", "Delete "};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                textIdBmc.setText(""+bmcEntityX.getIdBMC());
                                textBmcKeterangan.setText(bmcEntityX.getKeterangan());
                                dropdownBmcKategori.setSelection(bmcEntityX.getKategoriBMC());

                                break;
                            case 1:

                                final CharSequence[] dialogitem2 = {"Yes", "No"};

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                builder2.setTitle("Delete Confirm");
                                builder2.setItems(dialogitem2, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        switch (item) {
                                            case 0:
                                                BmcDAO.delete(dbHelper, bmcEntityX);
                                                RefreshListKeyResources();
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
        ((ArrayAdapter)ListViewKeyResources.getAdapter()).notifyDataSetInvalidated();
    }


    public void RefreshListChannels(){

        daftarChannels = BmcDAO.getListArrayOnlyKeterangan(dbHelper, 0,0,BmcDAO.FLD_KATEGORI+" = "+6,"");
        dataChannels = BmcDAO.getList(dbHelper, 0, 0, BmcDAO.FLD_KATEGORI+" = "+6, "");

        ArrayAdapter adapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftarChannels);

        ListViewChannels = (ListView)rootView.findViewById(R.id.listviewChannels);
        ListViewChannels.setAdapter(adapter);
        ListViewChannels.setSelected(true);
        ListViewChannels.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftarChannels[arg2]; //.getItemAtPosition(arg2).toString();

                final BmcEntity bmcEntityX = (BmcEntity) dataChannels.get(arg2);

                final CharSequence[] dialogitem = {"Update ", "Delete "};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                textIdBmc.setText(""+bmcEntityX.getIdBMC());
                                textBmcKeterangan.setText(bmcEntityX.getKeterangan());
                                dropdownBmcKategori.setSelection(bmcEntityX.getKategoriBMC());

                                break;
                            case 1:

                                final CharSequence[] dialogitem2 = {"Yes", "No"};

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                builder2.setTitle("Delete Confirm");
                                builder2.setItems(dialogitem2, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        switch (item) {
                                            case 0:
                                                BmcDAO.delete(dbHelper, bmcEntityX);
                                                RefreshListChannels();
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
        ((ArrayAdapter)ListViewChannels.getAdapter()).notifyDataSetInvalidated();
    }

    public void RefreshListCostStructure(){

        daftarCostStructure = BmcDAO.getListArrayOnlyKeterangan(dbHelper, 0,0,BmcDAO.FLD_KATEGORI+" = "+7,"");
        dataCostStructure = BmcDAO.getList(dbHelper, 0, 0, BmcDAO.FLD_KATEGORI+" = "+7, "");

        ArrayAdapter adapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftarCostStructure);

        ListViewCostStructure = (ListView)rootView.findViewById(R.id.listviewCostStructure);
        ListViewCostStructure.setAdapter(adapter);
        ListViewCostStructure.setSelected(true);
        ListViewCostStructure.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftarCostStructure[arg2]; //.getItemAtPosition(arg2).toString();

                final BmcEntity bmcEntityX = (BmcEntity) dataCostStructure.get(arg2);

                final CharSequence[] dialogitem = {"Update ", "Delete "};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                textIdBmc.setText(""+bmcEntityX.getIdBMC());
                                textBmcKeterangan.setText(bmcEntityX.getKeterangan());
                                dropdownBmcKategori.setSelection(bmcEntityX.getKategoriBMC());

                                break;
                            case 1:

                                final CharSequence[] dialogitem2 = {"Yes", "No"};

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                builder2.setTitle("Delete Confirm");
                                builder2.setItems(dialogitem2, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        switch (item) {
                                            case 0:
                                                BmcDAO.delete(dbHelper, bmcEntityX);
                                                RefreshListCostStructure();
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
        ((ArrayAdapter)ListViewCostStructure.getAdapter()).notifyDataSetInvalidated();
    }

    public void RefreshListRevenueStreams(){

        daftarRevenueStreams = BmcDAO.getListArrayOnlyKeterangan(dbHelper, 0,0,BmcDAO.FLD_KATEGORI+" = "+8,"");
        dataRevenueStreams = BmcDAO.getList(dbHelper, 0, 0, BmcDAO.FLD_KATEGORI+" = "+8, "");

        ArrayAdapter adapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftarRevenueStreams);

        ListViewRevenueStreams = (ListView)rootView.findViewById(R.id.listviewRevenueStreams);
        ListViewRevenueStreams.setAdapter(adapter);
        ListViewRevenueStreams.setSelected(true);
        ListViewRevenueStreams.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftarRevenueStreams[arg2]; //.getItemAtPosition(arg2).toString();

                final BmcEntity bmcEntityX = (BmcEntity) dataRevenueStreams.get(arg2);

                final CharSequence[] dialogitem = {"Update ", "Delete "};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                textIdBmc.setText(""+bmcEntityX.getIdBMC());
                                textBmcKeterangan.setText(bmcEntityX.getKeterangan());
                                dropdownBmcKategori.setSelection(bmcEntityX.getKategoriBMC());

                                break;
                            case 1:

                                final CharSequence[] dialogitem2 = {"Yes", "No"};

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                builder2.setTitle("Delete Confirm");
                                builder2.setItems(dialogitem2, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        switch (item) {
                                            case 0:
                                                BmcDAO.delete(dbHelper, bmcEntityX);
                                                RefreshListRevenueStreams();
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
        ((ArrayAdapter)ListViewRevenueStreams.getAdapter()).notifyDataSetInvalidated();
    }

        private void callFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}
