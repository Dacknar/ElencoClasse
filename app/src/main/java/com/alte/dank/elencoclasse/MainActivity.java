package com.alte.dank.elencoclasse;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final static String MaterieFragmnet = "FRAGMENT_MAT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Materie");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MaterieFragment(), MaterieFragmnet).commit();
            navigationView.setCheckedItem(R.id.home);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    private void addMateriaDialog(){
        AlertDialog.Builder mBuider = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_add_materia, null);
        final EditText mMateria = (EditText) mView.findViewById(R.id.edtMateria);
        Button mBtnConferma = (Button) mView.findViewById(R.id.btnConfermaMateria);
        Button mBtnAnulla = (Button) mView.findViewById(R.id.btnAnullaMateria);
        mBuider.setView(mView);
        final AlertDialog mDialog = mBuider.create();
        mDialog.show();
        mBtnConferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mMateria.getText().toString().isEmpty()){
                    MaterieFragment fragment = (MaterieFragment) getSupportFragmentManager().findFragmentByTag(MaterieFragmnet);
                    fragment.addMateria(mMateria.getText().toString());
                    mDialog.dismiss();
                }
                else{
                    Toast.makeText(MainActivity.this, "Materia non inserita", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mBtnAnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_materia) {
            addMateriaDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            //Chiudi la tastiera quando si passa a HOME FRAME
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MaterieFragment(), MaterieFragmnet).commit();
        } else if (id == R.id.elenco) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ElencoFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
