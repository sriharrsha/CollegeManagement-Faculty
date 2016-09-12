package app.management.college.com.collegemanagement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

import app.management.college.com.collegemanagement.api.Authentication.RegularAuth.RegularLoginResponse;
import app.management.college.com.collegemanagement.api.CollegeManagementApiService;
import app.management.college.com.collegemanagement.api.FacultyProfile.DataList;
import app.management.college.com.collegemanagement.api.FacultyProfile.FacultyProfileResult;
import app.management.college.com.collegemanagement.api.ServiceGenerator;
import app.management.college.com.collegemanagement.util.CredentialManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tabs.hometab;
import tabs.newstab;
import tabs.profiletab;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String DEBUG_TAG = "Home";
    boolean firstExit = true;
    private DrawerLayout drawerLayout;
    private View parent_view;
    private ViewPager mViewPager;
    private CredentialManager credentialManager;
    private ActionBarDrawerToggle mActionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        parent_view = findViewById(R.id.main_content);
        setSupportActionBar(toolbar);
        setTitle("Home");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        credentialManager = new CredentialManager(this);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        final View headerView = navView.inflateHeaderView(R.layout.nav_menu_header);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Toast.makeText(getApplicationContext(), refreshedToken, Toast.LENGTH_LONG).show();
        Log.i("firebasetoken", refreshedToken);
        ImageView avatar = (ImageView) headerView.findViewById(R.id.avatar);
        final TextView nameLabel = (TextView) headerView.findViewById(R.id.profile_name);
        final TextView departmentLabel = (TextView) headerView.findViewById(R.id.profile_dept);
        final TextView phoneLabel = (TextView) headerView.findViewById(R.id.profile_phone);
        final TextView emailLabel = (TextView) headerView.findViewById(R.id.profile_email);

        byte[] decodedString = Base64.decode(credentialManager.getUniversityLogo(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        Palette.generateAsync(decodedByte, new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                // Do something with colors...
                headerView.setBackgroundColor(palette.getVibrantColor(Color.BLUE));
                //  nameLabel.setTextColor(palette.getMutedColor(Color.WHITE));
                departmentLabel.setTextColor(palette.getMutedColor(Color.BLACK));
                phoneLabel.setTextColor(palette.getMutedColor(Color.BLACK));
                emailLabel.setTextColor(palette.getMutedColor(Color.BLACK));
            }
        });


        final CollegeManagementApiService collegeApiService = ServiceGenerator.createService(CollegeManagementApiService.class);
        Call<RegularLoginResponse> firstcall = collegeApiService.doRegularLogin(credentialManager.getUserName(), credentialManager.getPassword());
        firstcall.enqueue(new Callback<RegularLoginResponse>() {

            public DataList data;

            @Override
            public void onResponse(Call<RegularLoginResponse> call, Response<RegularLoginResponse> response) {
                Log.i("token", response.body().toString());

                final Call<FacultyProfileResult> facultyProfileCall = collegeApiService.getProfileData(response.body().getToken());
                facultyProfileCall.enqueue(new Callback<FacultyProfileResult>() {
                    @Override
                    public void onResponse(Call<FacultyProfileResult> call, Response<FacultyProfileResult> response) {
                        try {
                            Log.i("feed", response.body().toString());
                            data = response.body().getDataList().get(0);
                            nameLabel.setText(data.getFirstName());
                            emailLabel.setText(data.getEmail());
                            departmentLabel.setText(data.getDepartment());
                            phoneLabel.setText(data.getPhone());
                        } catch (NullPointerException e) {
                            Toast.makeText(getApplicationContext(), "No Data from Server", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<FacultyProfileResult> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();

                    }

                });


            }

            @Override
            public void onFailure(Call<RegularLoginResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }


        });


        avatar.setImageBitmap(decodedByte);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
//        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setBackgroundColor(Color.parseColor("#253b80"));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#179bd7"));
        tabLayout.getTabAt(1).select();


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
        });

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

        if (id == R.id.logout) {
            Toast.makeText(getApplicationContext(),
                    "logged out", Toast.LENGTH_LONG).show();
            Intent i = new Intent(Home.this, LoginActivity.class);
            startActivity(i);
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
            Toast.makeText(getApplicationContext(),
                    "logged out", Toast.LENGTH_LONG).show();
            Intent i = new Intent(Home.this, LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        if (id == R.id.nav_camera) {
            // Handle the camera action
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
        adapter.addFragment(new profiletab(),"Profile");
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
