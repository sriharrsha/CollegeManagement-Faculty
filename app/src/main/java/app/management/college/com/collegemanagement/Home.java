package app.management.college.com.collegemanagement;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.management.college.com.collegemanagement.util.CredentialManager;
import tabs.hometab;
import tabs.newstab;
import tabs.profiletab;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String DEBUG_TAG = "Home";
    private DrawerLayout drawerLayout;
    private View parent_view;
    private ViewPager mViewPager;
    private CredentialManager credentialManager;
    boolean firstExit = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        parent_view = findViewById(R.id.main_content);
        setSupportActionBar(toolbar);
        setTitle("Home");
        menuSetup();
        setupDrawerLayout();

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
//        mViewPager.setAdapter(mSectionsPagerAdapter);
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setBackgroundColor(Color.parseColor("#253b80"));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#179bd7"));
        tabLayout.getTabAt(1).select();/*
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d(DEBUG_TAG, "onTabChanged: position: " + position);
                if ("0".equals(position)) {
                    setTitle("Profile");
                    //destroy earth
                }
                if ("1".equals(position)) {
                    setTitle("Home");
                    //destroy mars
                }
                if ("2".equals(position)) {
                    setTitle("News");
                    //destroy mars
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                super.onTabUnselected(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void menuSetup() {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            Log.d(DEBUG_TAG,  " onBackPressed");
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(firstExit) {
                firstExit = false;
                Toast.makeText(this, "Press again to exit", Toast.LENGTH_LONG).show();
                return;
            } else {
                firstExit = true;
                this.moveTaskToBack(true);
            }
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_time_table_manager_drawer, menu);
        Log.d(DEBUG_TAG, " created");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Log.d(DEBUG_TAG, id + " clicked");
        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            Log.d(DEBUG_TAG, "Logout button clicked");
            credentialManager = new CredentialManager(Home.this);
//            credentialManager.storeUser(credentialManager.getUserName(),null, false);
            credentialManager.updateStatus(false);
            credentialManager.clearAllCache();
            Intent i = new Intent(Home.this, LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        if (id == R.id.timeTable) {
            Intent i = new Intent(Home.this, TimeTable.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Log.d(DEBUG_TAG, id + " clicked");
        if (id == R.id.logout) {
            Log.d(DEBUG_TAG, "Logout button clicked");
            credentialManager = new CredentialManager(Home.this);
            credentialManager.storeUser(credentialManager.getUserName(),null, false);
            Intent i = new Intent(Home.this, LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        if (id == R.id.timeTable) {
            Intent i = new Intent(Home.this, TimeTable.class);
            startActivity(i);
            finish();
            return true;
        }
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setupDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView view = (NavigationView) findViewById(R.id.nav_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                Snackbar.make(parent_view, menuItem.getTitle() + " Clicked ", Snackbar.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new profiletab(), "Profile");
        adapter.addFragment(new hometab(), "Home");
        adapter.addFragment(new newstab(), "News");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
