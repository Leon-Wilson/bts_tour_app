package wlsn.programs.com.bts;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    user current_user;

    private FirebaseAuth mAuth;
    FirebaseUser firebase_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //current_user = user.getInstance();
        mAuth = FirebaseAuth.getInstance();


        if(mAuth != null)
        {
            firebase_user = mAuth.getCurrentUser();
            user t = user.getInstance(firebase_user.getUid());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            int page_id = extras.getInt("page_id");

            Fragment  fragment = null;
            switch(page_id)
            {
                //QUIZ
                case 1:
                    int selected_quiz = extras.getInt("selected_quiz");

                    fragment = quiz_view.getInstance(selected_quiz);
                    break;
                //SIGN_UP
                case 2:
                    fragment = new frag_sign_up();
                    break;
            }

            if(fragment != null)
            {
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                trans.replace(R.id.content_frame, fragment);
                trans.commit();
            }

        }
        else
        {

            if(firebase_user != null)
            {
                current_user = user.getInstance();

                displayScreen(R.id.nav_training);
            }
            else
            {
                Fragment fragment = new frag_login();

                if(fragment != null)
                {
                    FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                    trans.replace(R.id.content_frame,fragment);
                    trans.commit();
                }
            }

            //displayScreen(R.id.nav_training);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displayScreen(id);
        return true;
    }

    public void displayScreen(int id)
    {
        Fragment fragment = null;
        boolean temp = true;
        if(firebase_user == null || mAuth.getCurrentUser() == null)
        {
            if(mAuth != null)
            {
                firebase_user = mAuth.getCurrentUser();
            }
        }

        if(firebase_user != null) {
            if (id == R.id.nav_training) {
                // Handle the camera action
                fragment = new frag_main();
            } else if (id == R.id.nav_schedule) {

            } else if (id == R.id.nav_weather) {
                fragment = new frag_weather();
            } else if (id == R.id.nav_scripts) {
                fragment = new frag_script();
            } else if (id == R.id.nav_maps) {

            } else if (id == R.id.nav_quizzes) {
                fragment = new frag_quiz();
            } else if (id == R.id.nav_progress) {

            } else if (id == R.id.nav_contact) {

            } else if (id == R.id.nav_settings) {

            } else if (id == R.id.nav_help) {

            }
        }

        if (id == R.id.nav_sign_out)
        {
            mAuth.signOut();
            fragment = new frag_login();
        }
        if (fragment != null)
        {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.replace(R.id.content_frame,fragment);
            trans.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
