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

public class Product extends Fragment {

    public Product(){}
    View rootView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    protected Cursor cursor;
    DataHelper dbHelper;
    Button buttonSave;
    EditText textIdProduct,textNoProduct, textNamaProduct;

    Spinner dropdownProductType;
    ListView ListViewProduct;
    String[] daftar;

    Vector data = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.product, container, false);
        getActivity().setTitle("Product");

        dbHelper = new DataHelper(getActivity());

        textIdProduct = (EditText) rootView.findViewById(R.id.editTextIdProduct);
        textNoProduct = (EditText) rootView.findViewById(R.id.editTextNilaiInvestasiTotal);
        textNamaProduct = (EditText) rootView.findViewById(R.id.editTextNamaProduct);
        dropdownProductType = (Spinner)rootView.findViewById(R.id.spinnerProductType);

        String[] items = ProductTypeDAO.getListArray(dbHelper,0,0,"","");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdownProductType.setAdapter(adapter);


        buttonSave = (Button) rootView.findViewById(R.id.buttonSaveProduct);

        final String tIdProduct = textIdProduct.getText().toString();


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                long id = 0;
                try{
                    id = Long.parseLong(textIdProduct.getText().toString());
                } catch (Exception e){
                }

                ProductEntity productEntity = new ProductEntity();
                productEntity.setIdProduct(id);
                productEntity.setNoProduct(textNoProduct.getText().toString());
                productEntity.setNamaProduct(textNamaProduct.getText().toString());

                long producttypeId = ProductTypeDAO.getIdByName(dbHelper,dropdownProductType.getItemAtPosition(dropdownProductType.getSelectedItemPosition()).toString());
                productEntity.setIdProductType(producttypeId);

                if (productEntity.getIdProduct() !=0){
                    ProductDAO.update(dbHelper, productEntity);
                } else {
                    ProductDAO.add(dbHelper, productEntity);
                }

                Toast.makeText(getActivity(), "Berhasil tersimpan No Product "+ textNoProduct.getText().toString(), Toast.LENGTH_LONG).show();

                textIdProduct.setText("");
                textNoProduct.setText("");
                textNamaProduct.setText("");

                RefreshList();

            }
        });


        RefreshList();
        return rootView;
    }

    public void RefreshList(){

        daftar = ProductDAO.getListArrayProductWithType(dbHelper, 0,0,"","");
        data = ProductDAO.getList(dbHelper, 0, 0, "", "");

        ArrayAdapter adapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftar);

        ListViewProduct = (ListView)rootView.findViewById(R.id.listViewProduct);
        ListViewProduct.setAdapter(adapter);
        ListViewProduct.setSelected(true);
        ListViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2]; //.getItemAtPosition(arg2).toString();

                final ProductEntity productEntityX = (ProductEntity) data.get(arg2);

                final CharSequence[] dialogitem = {"Update product", "Delete product"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                textIdProduct.setText(""+productEntityX.getIdProduct());
                                textNoProduct.setText(productEntityX.getNoProduct());
                                textNamaProduct.setText(productEntityX.getNamaProduct());

                                ArrayAdapter myAdap = (ArrayAdapter) dropdownProductType.getAdapter(); //cast to an ArrayAdapter
                                int spinnerPosition = myAdap.getPosition(""+ ProductTypeDAO.getNameById(dbHelper,productEntityX.getIdProductType()));
                                dropdownProductType.setSelection(spinnerPosition);

                                break;
                            case 1:

                                final CharSequence[] dialogitem2 = {"Yes", "No"};

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                builder2.setTitle("Delete Confirm");
                                builder2.setItems(dialogitem2, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        switch (item) {
                                            case 0:
                                                ProductDAO.delete(dbHelper, productEntityX);
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
        ((ArrayAdapter)ListViewProduct.getAdapter()).notifyDataSetInvalidated();
    }
    private void callFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}
