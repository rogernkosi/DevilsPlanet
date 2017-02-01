package nkosi.roger.manutdcom.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import nkosi.roger.manutdcom.R;
import nkosi.roger.manutdcom.controller.APIController;
import nkosi.roger.manutdcom.utils.aUtils;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AHBottomNavigation bottomNavigation;
    //    private AHBottomNavigationItem home, profile, settings;

    private FragmentManager manager;
    private FragmentPagerAdapter adapter;
    private TextView teamsAndScore, matchDate, featuredText;
    private ImageView featuredImg;
    private Context context = Home.this;
    private APIController controller;
    private NavigationView navigationView;
    private LinearLayout featureLay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controller = new APIController();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        teamsAndScore = (TextView) header.findViewById(R.id.teamsplayig);
        matchDate = (TextView) header.findViewById(R.id.feature_date);
        featuredImg = (ImageView) header.findViewById(R.id.feature_image);
        featuredText = (TextView) header.findViewById(R.id.feature_text);
        featureLay = (LinearLayout) header.findViewById(R.id.feature_lay);

        featureLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.fetchFeaturedMatch(teamsAndScore, matchDate, featuredImg, featuredText, context);
            }
        });

        controller.fetchFeaturedMatch(teamsAndScore, matchDate, featuredImg, featuredText, context);


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.view_grid, getTheme());
        toggle.setDrawerIndicatorEnabled(false);

        toggle.setHomeAsUpIndicator(drawable);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        FragmentManager manager1 = getSupportFragmentManager();
        FragmentTransaction transaction = manager1.beginTransaction();
        transaction.replace(R.id.content_home, new HomeFragment());
        transaction.commit();

//        setBottomNavigation();


    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    public void setBottomNavigation() {
//        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        final AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_account_box_black_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_home_black_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_list_black_24dp, R.color.colorPrimary);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        // Set background color
        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.colorPrimary));


        // Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(false);

        // Change colors
//        bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));
        // Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);

        // Use colored navigation with circle reveal effect
        bottomNavigation.setColored(true);

        // Set current item programmatically
        bottomNavigation.setCurrentItem(1);

        // Customize notification (title, background, typeface)
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));


        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 1:
                        FragmentManager manager1 = getSupportFragmentManager();
                        FragmentTransaction transaction = manager1.beginTransaction();
                        transaction.replace(R.id.content_home, new HomeFragment());
                        transaction.commit();
                        bottomNavigation.setCurrentItem(position, wasSelected);
                        Log.e("pos", position + "");
                        break;
                    case 0:
                        FragmentManager manager0 = getSupportFragmentManager();
                        FragmentTransaction transaction0 = manager0.beginTransaction();
                        transaction0.replace(R.id.content_home, new ProfileFragment());
                        transaction0.commit();
                        bottomNavigation.setCurrentItem(position, wasSelected);
                        Log.e("pos", position + "");
                        break;
                    case 2:
                        FragmentManager manager2 = getSupportFragmentManager();
                        FragmentTransaction transaction2 = manager2.beginTransaction();
                        transaction2.replace(R.id.content_home, new FindEvents());
                        transaction2.commit();
                        bottomNavigation.setCurrentItem(position, wasSelected);
                        Log.e("pos", position + "");
                        break;
                    default:
                        return false;
                }
                return false;
            }
        });

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
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.help:
                startActivity(new Intent(context, Help.class));
                break;
            case R.id.credits:
                startActivity(new Intent(context, Credits.class));
                break;
            default:
                return false;

        }

        return super.onOptionsItemSelected(item);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {

        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return the Fragment associated with a specified position.
         *
         * @param position
         */
        @Override
        public Fragment getItem(int position) {
            return null;
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        /**
         * This method may be called by the ViewPager to obtain a title string
         * to describe the specified page. This method may return null
         * indicating no title for this page. The default implementation returns
         * null.
         *
         * @param position The position of the title requested
         * @return A title for the requested page
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();
        controller.fetchFeaturedMatch(teamsAndScore, matchDate, featuredImg, featuredText, context);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            FragmentManager manager0 = getSupportFragmentManager();
            FragmentTransaction transaction0 = manager0.beginTransaction();
            transaction0.replace(R.id.content_home, new HomeFragment());
            transaction0.commit();
        } else if (id == R.id.nav_live_matches) {
            FragmentManager manager0 = getSupportFragmentManager();
            FragmentTransaction transaction0 = manager0.beginTransaction();
            transaction0.replace(R.id.content_home, new LiveMatch());
            transaction0.commit();

        } else if (id == R.id.nav_share) {
            String tittle = "Devils Planet";
            String body = "Please download this cool Man Utd fans application" +
                    "on Google Play Store(Devils Planet)";
            aUtils.invokeShare(context, tittle, body);
        } else if (id == R.id.nav_send) {
            FragmentManager manager0 = getSupportFragmentManager();
            FragmentTransaction transaction0 = manager0.beginTransaction();
            transaction0.replace(R.id.content_home, new Contact());
            transaction0.commit();
        } else if (id == R.id.nav_blog) {
            FragmentManager manager0 = getSupportFragmentManager();
            FragmentTransaction transaction0 = manager0.beginTransaction();
            transaction0.replace(R.id.content_home, new MatchBlog());
            transaction0.commit();
        } else if (id == R.id.nav_calendar) {
            FragmentManager manager0 = getSupportFragmentManager();
            FragmentTransaction transaction0 = manager0.beginTransaction();
            transaction0.replace(R.id.content_home, new Calendar());
            transaction0.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

}
