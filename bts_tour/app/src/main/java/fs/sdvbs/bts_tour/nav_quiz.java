package fs.sdvbs.bts_tour;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class nav_quiz extends AppCompatActivity {


    /*

    LW_BUG_6: After completing a quiz, pressing the back button will cause the system to crash
    UPDATE: Crashes on back press regardless of whether a quiz has been taken on not
    UPDATE: Crash happens on any back press
     */
    player current_player;
    ListView quiz_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        quiz_list = (ListView) findViewById(R.id.quiz_list);
        quiz_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int selected_quiz = i;
                Intent intent = new Intent(nav_quiz.this,quiz_pop.class);
                intent.putExtra("selected_quiz", selected_quiz);
                /*
                intent.putExtra("current_player",current_player);
                startActivityForResult(intent,1);*/

                startActivity(intent);
            }
        });
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            current_player = player.getInstance();//extras.getParcelable("current_player");;
            current_player.current_user = user_json.getInstance();
            current_player.quizzes = current_player.current_user.getQuizzes();
            loadList();
        }
        else
        {
            //TODO: Error handling for null current player
            current_player = new player("BAD NEWS");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.test_quiz_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(nav_quiz.this, quiz_main.class);
                intent.putExtra("current_player", current_player);
                //intent.putExtra("CURRENT_PLAYER", current_player);
                startActivityForResult(intent,1);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                current_player = player.getInstance();//extras.getParcelable("current_player");
            }
        }
    }

    //TODO: Create some kind of global function for displaying quizzes
    public void loadList()
    {
        int quiz_num = 0;
        String[] quiz_names;

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

        quiz_list.setAdapter(name_list);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent();
        intent.putExtra("current_player", current_player);
        setResult(RESULT_OK, intent);
        finish();
    }
}
