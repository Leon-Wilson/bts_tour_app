package fs.sdvbs.bts_tour;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
/*
TODO: Research startActivityForResults() to handle data being updated when user presses back button

//*/
public class nav_main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    player current_player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        current_player = new player("Leon");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, String.valueOf(current_player.stats.current_points), Snackbar.LENGTH_LONG)
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
        getMenuInflater().inflate(R.menu.nav_main, menu);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("editTextValue");
                Bundle extras = data.getExtras();
                current_player = extras.getParcelable("current_player");
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_training) {
            // Handle the camera action
            Intent intent = new Intent(this, nav_training.class);
            intent.putExtra("current_player", current_player);
            startActivityForResult(intent,1);
        } else if (id == R.id.nav_schedule) {
            Intent intent = new Intent(this, nav_schedule.class);
            startActivityForResult(intent,1);
        } else if (id == R.id.nav_help) {
            Intent intent = new Intent(this, nav_help.class);
            startActivityForResult(intent,1);
        } else if (id == R.id.nav_maps) {
            Intent intent = new Intent(this, nav_maps.class);
            startActivityForResult(intent,1);
        } else if (id == R.id.nav_contact) {
            Intent intent = new Intent(this, nav_contact.class);
            startActivityForResult(intent,1);
        } else if (id == R.id.nav_progress) {
            Intent intent = new Intent(this, nav_progress.class);
            startActivityForResult(intent,1);
        } else if (id == R.id.nav_quiz) {
            Intent intent = new Intent(this, nav_quiz.class);
            startActivityForResult(intent,1);
        } else if (id == R.id.nav_script) {
            Intent intent = new Intent(this, nav_script.class);
            startActivityForResult(intent,1);
        }
//        else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
