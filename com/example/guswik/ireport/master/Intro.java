package com.example.guswik.ireport.master;

/**
 * Created by GUSWIK on 7/16/2017.
 */

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.guswik.ireport.R;
import com.example.guswik.ireport.database.DataHelper;

import java.util.Vector;

public class Intro extends Fragment {

    public Intro(){}

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    protected Cursor cursor;
    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_intro, container, false);
        getActivity().setTitle("Metode Penilaian Investasi");


        return rootView;
    }


    private void callFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}
