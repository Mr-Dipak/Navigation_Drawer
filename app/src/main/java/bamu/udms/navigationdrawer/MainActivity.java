package bamu.udms.navigationdrawer;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private static final String TAG = "MyActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the views
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        // Set the toolbar as the action bar
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        loadFragment(new Home());

        // Set the navigation view item selected listener

        navigationView.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();

                if(id == R.id.nav_home){
                    Log.d(TAG,"onNavigationItemSelected: Home");
                    // Handle the home action
                    loadFragment(new Home());
                } else if(id == R.id.nav_notes){
                    // Handle the notes action
                    Log.d(TAG, "onNavigationItemSelected: Notes");
                    loadFragment(new Notes());


                } else if(id == R.id.nav_settings){
                    // Handle the slideshow action
                    Log.d(TAG, "onNavigationItemSelected: Settings");
                    loadFragment(new Settings());
                };

                drawerLayout.closeDrawer(navigationView);


                return true;
            }

        });



    }


    public void loadFragment(Fragment fragment){
        // Load the fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

}