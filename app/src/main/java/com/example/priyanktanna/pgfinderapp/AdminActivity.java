package com.example.priyanktanna.pgfinderapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import com.example.priyanktanna.pgfinderapp.Admin.AdminAddFacilityFragment;
import com.example.priyanktanna.pgfinderapp.Admin.AdminAddPackageFragment;
import com.example.priyanktanna.pgfinderapp.Admin.AdminFeedbackFragment;
import com.example.priyanktanna.pgfinderapp.Admin.AdminManageFacilityFragment;
import com.example.priyanktanna.pgfinderapp.Admin.AdminManagePackageFragment;
import com.example.priyanktanna.pgfinderapp.Admin.AdminShowFacilityFragment;
import com.example.priyanktanna.pgfinderapp.Admin.AdminShowPackageFragment;

public class AdminActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loadHomepage();
    }



    //@Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.admin, menu);
//        return true;
//    }

    //@Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private void loadHomepage(){

        AdminAddFacilityFragment adminAddFacilityFragment = new AdminAddFacilityFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame,adminAddFacilityFragment);
        ft.commit();

    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (checkNavigationMenuItem() != 0) {
                navigationView.setCheckedItem(R.id.nav_home);
                Fragment fragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
            } else


                super.onBackPressed();
        }
    }


        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected (MenuItem item){
            // Handle navigation view item clicks here.
            int id = item.getItemId();
            Fragment fragment = null;

            if (id == R.id.nav_addfacility) {
                // Handle the camera action
                fragment = new AdminAddFacilityFragment();

            } else if (id == R.id.nav_addpackage) {
                fragment = new AdminAddPackageFragment();

            } else if (id == R.id.nav_feedback) {
                fragment = new AdminFeedbackFragment();

            } else if (id == R.id.nav_managefacility) {
                fragment = new AdminManageFacilityFragment();

            } else if (id == R.id.nav_managepackage) {
                fragment = new AdminManagePackageFragment();

            } else if (id == R.id.nav_showfacility) {
                fragment = new AdminShowFacilityFragment();

            } else if (id == R.id.nav_showpackage) {
                fragment = new AdminShowPackageFragment();

            } else if (id == R.id.nav_logout) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure Logout??");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences1 = getSharedPreferences("PGFINDER", Context.MODE_PRIVATE);
                        SharedPreferences.Editor spreferencesEditor1 = sharedPreferences1.edit();
                        spreferencesEditor1.remove("USER_KEY");
                        spreferencesEditor1.remove("USER_ROLE");
                        spreferencesEditor1.commit();
                        Intent i = new Intent(AdminActivity.this, login_Activity.class);
                        startActivity(i);
                        finish();


                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();


            }
            if (fragment != null) {


                FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.frame, fragment);
                ft1.commit();

            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        private int checkNavigationMenuItem () {
            Menu menu = navigationView.getMenu();
            for (int i = 0; i < menu.size(); i++) {
                if (menu.getItem(i).isChecked())
                    return i;
            }
            return -1;
        }
    }
