package com.example.guswik.ireport;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.guswik.ireport.database.DataHelper;
import com.example.guswik.ireport.master.Akun;
import com.example.guswik.ireport.master.AkunType;
import com.example.guswik.ireport.master.Analysis;
import com.example.guswik.ireport.master.Bmc;
import com.example.guswik.ireport.master.BukuBesar;
import com.example.guswik.ireport.master.Company;
import com.example.guswik.ireport.master.CompanyDAO;
import com.example.guswik.ireport.master.Intro;
import com.example.guswik.ireport.master.Jurnal;
import com.example.guswik.ireport.master.LabaRugi;
import com.example.guswik.ireport.master.Neraca;
import com.example.guswik.ireport.master.NeracaSaldo;
import com.example.guswik.ireport.master.NetPresentValue;
import com.example.guswik.ireport.master.PaybackPeriod;
import com.example.guswik.ireport.master.PaybackPeriodDAO;
import com.example.guswik.ireport.master.PerhitunganInvestasi;
import com.example.guswik.ireport.master.PerubahanModal;
import com.example.guswik.ireport.master.Product;
import com.example.guswik.ireport.master.ProfitabilityIndex;
import com.example.guswik.ireport.master.Swot;
import com.example.guswik.ireport.master.Transaction;
import com.example.guswik.ireport.master.TransactionList;
import com.example.guswik.ireport.master.TransactionMultiple;
import com.example.guswik.ireport.master.TransactionMultipleList;

import java.util.Vector;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    FloatingActionButton fab;


    DataHelper dbcenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //drawer.setDrawerListener(toggle);
        //toggle.syncState();

        ActionBarDrawerToggle  toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                View view = getWindow().getCurrentFocus();
                if (view == null) {
                    View decorView = getWindow().getDecorView();
                    View focusView = decorView.findViewWithTag("keyboardTagView");
                    if (focusView == null) {
                        view = new EditText(getWindow().getContext());
                        view.setTag("keyboardTagView");
                        ((ViewGroup) decorView).addView(view, 0, 0);
                    } else {
                        view = focusView;
                    }
                    view.requestFocus();
                }
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                View view = getWindow().getCurrentFocus();
                if (view == null) {
                    View decorView = getWindow().getDecorView();
                    View focusView = decorView.findViewWithTag("keyboardTagView");
                    if (focusView == null) {
                        view = new EditText(getWindow().getContext());
                        view.setTag("keyboardTagView");
                        ((ViewGroup) decorView).addView(view, 0, 0);
                    } else {
                        view = focusView;
                    }
                    view.requestFocus();
                }
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dbcenter = new DataHelper(this);

        fragmentManager = getFragmentManager();


        // tampilan default awal ketika aplikasii dijalankan
        if (savedInstanceState == null) {
            try{
                Vector vCompany = CompanyDAO.getList(dbcenter, 0, 0, "", "");
                if (vCompany.size()==0){
                    fragment = new Company();
                    callFragment(fragment);
                } else {
                    //fragment = new Root();
                    //fragment = new TransactionMultipleList();
                    //callFragment(fragment);

                    fragment = new Intro();
                    callFragment(fragment);
                }
            } catch (Exception e){
            }


        }


    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            //hideKeyboard(true,this);
            // super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            fragment = new Company();
            callFragment(fragment);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        // Untuk memanggil layout dari menu yang dipilih
         if (id == R.id.nav_jurnal) {
            fragment = new Jurnal();
            callFragment(fragment);
         } else if (id == R.id.nav_akun_type) {
            fragment = new AkunType();
            callFragment(fragment);
         } else if (id == R.id.nav_akun) {
            fragment = new Akun();
            callFragment(fragment);
         } else if (id == R.id.nav_transaction) {
            fragment = new TransactionMultipleList();
            callFragment(fragment);
         }  else if (id == R.id.nav_bukubesar) {
             fragment = new BukuBesar();
             callFragment(fragment);
         } else if (id == R.id.nav_neracasaldo) {
             fragment = new NeracaSaldo();
             callFragment(fragment);
         } else if (id == R.id.nav_labarugi) {
             fragment = new LabaRugi();
             callFragment(fragment);
         } else if (id == R.id.nav_perubahanmodal) {
             fragment = new PerubahanModal();
             callFragment(fragment);
         } else if (id == R.id.nav_neraca) {
             fragment = new Neraca();
             callFragment(fragment);
         } else if (id == R.id.nav_company) {
             fragment = new Company();
             callFragment(fragment);
         }else if (id == R.id.nav_intro) {
             fragment = new Intro();
             callFragment(fragment);
         }else if (id == R.id.nav_paybackperiod) {
             fragment = new PaybackPeriod();
             callFragment(fragment);
         }else if (id == R.id.nav_npv) {
             fragment = new NetPresentValue();
             callFragment(fragment);
         }else if (id == R.id.nav_profitabilityindex) {
             fragment = new ProfitabilityIndex();
             callFragment(fragment);
         }else if (id == R.id.nav_analysis) {
             fragment = new Analysis();
             callFragment(fragment);
         }else if (id == R.id.nav_swot) {
             fragment = new Swot();
             callFragment(fragment);
         }else if (id == R.id.nav_product) {
             fragment = new Product();
             callFragment(fragment);
         }else if (id == R.id.nav_bmc) {
             fragment = new Bmc();
             callFragment(fragment);
         }else if (id == R.id.nav_perhitungan_investasi) {
             fragment = new PerhitunganInvestasi();
             callFragment(fragment);
         }


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // untuk mengganti isi kontainer menu yang dipiih
    private void callFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
    public static void hideKeyboard(boolean val, Activity activity) {
        View view = activity.getWindow().getCurrentFocus();
        if (view == null) {
            View decorView = activity.getWindow().getDecorView();
            View focusView = decorView.findViewWithTag("keyboardTagView");
            if (focusView == null) {
                view = new EditText(activity.getWindow().getContext());
                view.setTag("keyboardTagView");
                ((ViewGroup) decorView).addView(view, 0, 0);
            } else {
                view = focusView;
            }
            view.requestFocus();
        }
        if (val == true) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
