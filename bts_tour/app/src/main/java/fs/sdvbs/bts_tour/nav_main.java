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
import android.widget.ProgressBar;
import android.widget.TextView;

/*
TODO: Research startActivityForResults() to handle data being updated when user presses back button (look into request codes)

//*/
public class nav_main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int i = 0;
    player current_player;
    ProgressBar player_progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        current_player = new player("Leon");
        current_player.setQuizList();
        player_progress = (ProgressBar) findViewById(R.id.player_progress);

        player_progress.setMax(current_player.stats.points_until_levelup);
        player_progress.setProgress(current_player.stats.current_points);

        TextView player_name = (TextView) findViewById(R.id.stat_name);
        player_name.setText(current_player.player_name);
        player_name.setTextSize(20);

        TextView player_level = (TextView) findViewById(R.id.stat_current_level);
        player_level.setText(String.valueOf(current_player.stats.current_level));
        player_level.setTextSize(20);

        i = 0;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, String.valueOf(current_player.quizzes[i].quiz_name), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if(current_player.quizzes[i + 1] != null)
                {
                    i++;
                }
                else
                {
                    i = 0;
                }
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
                Bundle extras = data.getExtras();
                current_player = extras.getParcelable("current_player");
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        player_progress.setProgress(current_player.stats.current_points);
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
            intent.putExtra("current_player", current_player);
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
