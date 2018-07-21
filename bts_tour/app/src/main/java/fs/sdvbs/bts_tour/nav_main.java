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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/*
TODO: Research startActivityForResults() to handle data being updated when user presses back button (look into request codes)
TODO: Create Test Cases for loadStart() functionality
//*/
public class nav_main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int i = 0;
    player current_player;
    ProgressBar player_progress;
    ListView mListview;

    DrawerLayout drawer;
    NavigationView nav_view; //= drawer.findViewById(R.id.nav_view);
    View header_layout; //= nav_view.getHeaderView(0);

    TextView player_name;
    TextView level_name;

    TextView next_level;
    TextView curr_level; // = header_layout.findViewById(R.id.nav_draw_current_level);

    ProgressBar progress_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        current_player = player.getInstance();
        current_player.setName("STUDENT");
        current_player.setQuizList();
        player_progress = (ProgressBar) findViewById(R.id.player_progress);

        player_progress.setMax(current_player.stats.points_until_levelup);
        player_progress.setProgress(current_player.stats.current_points);

        drawer = findViewById(R.id.drawer_layout);
        nav_view = drawer.findViewById(R.id.nav_view);
        header_layout = nav_view.getHeaderView(0);

        player_name = header_layout.findViewById(R.id.nav_draw_name);
        level_name = header_layout.findViewById(R.id.nav_draw_level_name);
        next_level = header_layout.findViewById(R.id.nav_draw_next_level);
        curr_level = header_layout.findViewById(R.id.nav_draw_current_level);
        progress_bar = header_layout.findViewById(R.id.nav_draw_progress);

        player_name.setText(current_player.player_name);
        level_name.setText(current_player.getStats().level_name);
        curr_level.setText(String.valueOf(current_player.getStats().current_level));
        next_level.setText(String.valueOf(current_player.getStats().current_level + 1));
        //progress_bar.setMax(6);
        //progress_bar.setProgress(3);
        progress_bar.setMax(current_player.stats.points_until_levelup);
        progress_bar.setProgress(current_player.getStats().current_points);

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
                /*Snackbar.make(view, String.valueOf(current_player.quizzes[i].quiz_name), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if(current_player.quizzes[i + 1] != null)
                {
                    i++;
                }
                else
                {
                    i = 0;
                }*/
                loadList();
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
                //LW_BUG_6 This is the last place it seems to get before it crashes
                //TODO: Create update function for when the user returns to the main screen

                player_progress = (ProgressBar) findViewById(R.id.player_progress);

                player_progress.setMax(current_player.stats.points_until_levelup);
                player_progress.setProgress(current_player.stats.current_points);
                
                player_name.setText(current_player.player_name);
                level_name.setText(current_player.getStats().level_name);
                curr_level.setText(String.valueOf(current_player.getStats().current_level));
                next_level.setText(String.valueOf(current_player.getStats().current_level + 1));

                progress_bar.setMax(current_player.stats.points_until_levelup);
                progress_bar.setProgress(current_player.stats.current_points);

                TextView player_name = (TextView) findViewById(R.id.stat_name);
                player_name.setText(current_player.player_name);
                player_name.setTextSize(20);

                TextView player_level = (TextView) findViewById(R.id.stat_current_level);
                player_level.setText(String.valueOf(current_player.stats.current_level));
                player_level.setTextSize(20);
                current_player = player.getInstance();//extras.getParcelable("current_player");

            }
        }
    }

    public void loadList()
    {
        int quiz_num = 0;
        String[] quiz_names = new String[1];

        for(int i = 0; i < current_player.quizzes.length;i++)
        {
            if(current_player.quizzes[i] == null)
            {
                quiz_num = i;
                break;
                /*else
                {
                    quiz_num = 1;
                    quiz_names = new String[quiz_num];
                    quiz_names[quiz_num - 1] = new String("NO QUIZZES FOUND");
                    break;
                }*/
            }
           /* else
            {
                if((i + 1) < current_player.quizzes.length)
                {
                    continue;
                }
                {
                    quiz_names = new String[1];
                    quiz_names[0] = new String("REACHED MAXIMUM QUIZ LOAD");
                }
            }*/
        }

        if(quiz_num == 0)
        {
            quiz_num = 1;
            quiz_names = new String[quiz_num];
            quiz_names[0] = new String("NO QUIZZES FOUND");
        }
        else if(quiz_num > current_player.quizzes.length)
        {
            quiz_names = new String[1];
            quiz_names[0] = new String("OVER MAXIMUM QUIZ AMOUNT");
        }
        else
        {
            quiz_names = new String[quiz_num];
            for(int j = 0; j < quiz_num; j++)
            {
                quiz_names[j] = current_player.quizzes[j].getName();
            }
        }
        ArrayAdapter<String> name_list = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, quiz_names);

        mListview.setAdapter(name_list);
    }
    @Override
    protected void onStart() {
        super.onStart();
        player_progress.setProgress(current_player.stats.current_points);
        int quiz_num = 0;
        String[] quiz_names = new String[1];
        mListview = (ListView) findViewById(R.id.checklist);


        for(int i = 0; i < current_player.quizzes.length;i++)
        {
            if(current_player.quizzes[i] == null)
            {
                quiz_num = i;
                break;
                /*else
                {
                    quiz_num = 1;
                    quiz_names = new String[quiz_num];
                    quiz_names[quiz_num - 1] = new String("NO QUIZZES FOUND");
                    break;
                }*/
            }
           /* else
            {
                if((i + 1) < current_player.quizzes.length)
                {
                    continue;
                }
                {
                    quiz_names = new String[1];
                    quiz_names[0] = new String("REACHED MAXIMUM QUIZ LOAD");
                }
            }*/
        }

        if(quiz_num == 0)
        {
            quiz_num = 1;
            quiz_names = new String[quiz_num];
            quiz_names[0] = new String("NO QUIZZES FOUND");
        }
        else if(quiz_num > current_player.quizzes.length)
        {
            quiz_names = new String[1];
            quiz_names[0] = new String("OVER MAXIMUM QUIZ AMOUNT");
        }
        else
        {
            quiz_names = new String[quiz_num];
            for(int j = 0; j < quiz_num; j++)
            {
                quiz_names[j] = current_player.quizzes[j].getName();
            }
        }
        ArrayAdapter<String> name_list = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, quiz_names);

        mListview.setAdapter(name_list);
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
        else if (id == R.id.nav_weather) {
            Intent intent = new Intent(this, nav_weather.class);
            startActivityForResult(intent,1);
        }
//        else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        NavigationView nav_view = drawer.findViewById(R.id.nav_view);
//        View header_layout = nav_view.getHeaderView(0);
//
//        TextView curr_level = header_layout.findViewById(R.id.nav_draw_current_level);
//        curr_level.setText("TEST");
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
