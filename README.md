# Navigation Drawer Implementation Guide

This guide provides a complete implementation of a Navigation Drawer in Android, including all necessary layout files and Java classes.

## Project Structure Overview

The project consists of XML layout files and Java classes organized as follows:

### XML Layout Files

```
res/layout/
    ├── activity_main.xml         // Main activity layout with DrawerLayout
    ├── header_layout.xml         // Navigation drawer header
    ├── app_bar_main.xml          // App bar layout
    ├── content_main.xml          // Main content container
    ├── fragment_home.xml         // Home fragment layout
    ├── fragment_notes.xml        // Notes fragment layout
    └── fragment_settings.xml     // Settings fragment layout

res/menu/
 
   ├── navigation_menu.xml       // Navigation menu items
```

### Java Files

```
java/com/example/app/
    ├── MainActivity.java         // Main activity with drawer implementation
    ├── Home.java                 // Home fragment
    ├── Notes.java                // Notes fragment
    └── Settings.java             // Settings fragment
```

## Detailed Implementation

### 1. Layout Files

### activity_main.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.drawerlayout.widget.DrawerLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/drawer_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true">

    <include
        layout="@layout/app_bar_main"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <com.google.android.material.navigation.NavigationView
        android:id="@+id/nav_view"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:fitsSystemWindows="true"
        app:headerLayout="@layout/header_layout"
        app:menu="@menu/navigation_menu" />

</androidx.drawerlayout.widget.DrawerLayout>
```

### header_layout.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="200dp"
    android:background="@color/design_default_color_primary"
    android:orientation="vertical"
    android:padding="16dp"
    android:gravity="start">

    <ImageView
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:src="@mipmap/ic_launcher"/>

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="UDMS user"
        android:textColor="@android:color/white"
        android:textSize="16sp"
        android:layout_marginTop="8dp"/>

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="udms@bamu.ac.in"
        android:textColor="@android:color/white"/>

</LinearLayout>
```

### navigation_menu.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/nav_home"
        android:icon="@drawable/home"
        android:title="Home" />
    <item
        android:id="@+id/nav_notes"
        android:icon="@drawable/notes"
        android:title="Notes" />
    <item
        android:id="@+id/nav_settings"
        android:icon="@drawable/settings"
        android:title="Settings" />
</menu>
```

### app_bar_main.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <com.google.android.material.appbar.AppBarLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <androidx.appcompat.widget.Toolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
        </androidx.appcompat.widget.Toolbar>

    </com.google.android.material.appbar.AppBarLayout>

    <include layout="@layout/content_main" />

</LinearLayout>
```

### content_main.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/fragment_container">
    </FrameLayout>

</LinearLayout>
```

### 2. Java Implementation

### MainActivity.java

```java
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
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    Log.d(TAG, "onNavigationItemSelected: Home");
                    // Handle the home action
                    loadFragment(new Home());
                } else if (id == R.id.nav_notes) {
                    // Handle the notes action
                    Log.d(TAG, "onNavigationItemSelected: Notes");
                    loadFragment(new Notes());
                } else if (id == R.id.nav_settings) {
                    // Handle the slideshow action
                    Log.d(TAG, "onNavigationItemSelected: Settings");
                    loadFragment(new Settings());
                }

                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        // Load the fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }
}
```

### Fragment Classes

Each fragment class (Home.java, Notes.java, Settings.java) follows a similar pattern:

```java
package bamu.udms.navigationdrawer;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Home extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}
```

Make sure to update your theme in `styles.xml` to use a Material theme:

```xml
<style name="AppTheme" parent="Theme.MaterialComponents.Light.NoActionBar">
    <!-- Customize your theme here -->
</style>
```

## Usage Notes

- The navigation drawer can be opened by swiping from the left edge or tapping the hamburger menu icon.
- Each menu item will load its corresponding fragment into the main content area.
- The drawer automatically closes after selecting a menu item.
- Back button handling is implemented to close the drawer if it's open.

## Customization Tips

- Modify the `header_layout.xml` to customize the navigation drawer header.
- Add or remove menu items in `navigation_menu.xml`.
- Customize the appearance using themes and styles.
