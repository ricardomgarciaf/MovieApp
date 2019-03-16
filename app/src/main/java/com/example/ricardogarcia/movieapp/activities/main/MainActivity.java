package com.example.ricardogarcia.movieapp.activities.main;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ricardogarcia.movieapp.R;
import com.example.ricardogarcia.movieapp.databinding.ActivityMainBinding;
import com.example.ricardogarcia.movieapp.databinding.NavHeaderUserBinding;
import com.example.ricardogarcia.movieapp.fragments.moviesearch.MovieSearchFragment;
import com.example.ricardogarcia.movieapp.fragments.welcome.WelcomeFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ActivityMainBinding mainBinding;

    private NavHeaderUserBinding userHeaderBinding;

    private int navIndex=0;

    private String currentTag;

    private static final String WELCOME="WELCOME";
    private static final String MOVIE_MENU="MOVIE_MENU";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        userHeaderBinding=DataBindingUtil.inflate(getLayoutInflater(),R.layout.nav_header_user,mainBinding.navUserView,false);
        setSupportActionBar(mainBinding.toolbar);
        ActionBar actionBar= getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        }

        loadUserNavHeader();

        mainBinding.navUserView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            navIndex = 0;
            currentTag = "WELCOME";
            loadHomeFragment();
        }
    }

    private void loadUserNavHeader() {
        mainBinding.navUserView.addHeaderView(userHeaderBinding.viewContainer);
        userHeaderBinding.name.setText("Test");
        Glide.with(this)
                .load("data:image/png;base64,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")
                .apply(RequestOptions.circleCropTransform())
                .into(userHeaderBinding.imgProfile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mainBinding.drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.extra_menu:
                mainBinding.drawerLayout.openDrawer(GravityCompat.END);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.welcome_menu:
                navIndex=0;
                currentTag=WELCOME;
                break;
            case R.id.movie_menu:
                navIndex=1;
                currentTag=MOVIE_MENU;
                break;
             default:
                 navIndex=0;
        }
        item.setChecked(!item.isChecked());
        item.setChecked(true);

        loadHomeFragment();
        return true;
    }

    private void loadHomeFragment() {
        mainBinding.navUserView.getMenu().getItem(navIndex).setChecked(true);

        if(getSupportFragmentManager().findFragmentByTag(currentTag)!=null){
            mainBinding.drawerLayout.closeDrawers();
            return;
        }

        Fragment fragment = getHomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container, fragment, currentTag).commit();

        mainBinding.drawerLayout.closeDrawers();

        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment(){
        switch (navIndex){
            case 0:
                return new WelcomeFragment();
            case 1:
                return new MovieSearchFragment();
            default:
                return new MovieSearchFragment();
        }
    }



}
