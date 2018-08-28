package wlsn.programs.com.bts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Leon on 8/2/18.
 */

public class quiz_pop extends Activity {

    private user current_user;
    private int selected_quiz;
    private int total_correct;
    private int total_questions;
    private int builing_num;
    private int page_id = 1;
    ArrayList<String> questions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.quiz_pop);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * 0.8),(int)(height * .75));

        current_user = user.getInstance();

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            int building = extras.getInt("building");
            int quiz = extras.getInt("quiz");
            int starting_point = 0;
            //TODO: Redo this to dynamically assign where each section starts or sort the list beforehand
            switch(building)
            {
                case 0:
                    starting_point = 18;
                    break;
                case 1:
                    starting_point = 3;
                    break;
                case 2:
                    starting_point = 11;
                    break;
                case 3:
                    starting_point = 0;
                    break;
                default:
                    break;
            }

            selected_quiz = starting_point + quiz;
        }

        TextView pop_name = (TextView)findViewById(R.id.pop_name);
        pop_name.setText(current_user.getData().getQuizzes()[selected_quiz].getName());

        TextView pop_ratio = (TextView)findViewById(R.id.pop_ratio);
        total_correct = current_user.getData().getQuizzes()[selected_quiz].total_correct;
        total_questions = current_user.getData().getQuizzes()[selected_quiz].total_questions;

        pop_ratio.setText("You have " + total_correct + " out of " + total_questions + " correct.");

        TextView pop_builing_num = (TextView)findViewById(R.id.pop_building);
        builing_num = current_user.getData().getQuizzes()[selected_quiz].building_num;
        pop_builing_num.setText("Building " + builing_num);

        ListView pop_questions = (ListView)findViewById(R.id.pop_questions);
        questions = new ArrayList<>();
        for(int i = 0; i < total_questions; i++)
        {
            questions.add(current_user.getData().getQuizzes()[selected_quiz].getQuestions()[i].getQuestionText());
        }

        //ListAdapter questions_adapter = new pop_questions(this, questions);
        ArrayAdapter<String> questions_adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,questions);

        pop_questions.setAdapter(questions_adapter);
        /*pop_quiz_questions.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {

            }
        });*/

        /*pop_quiz_questions.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {

            }
        });*/
        //pop_quiz.setAdapter(new pop_questions());
        Button pop_button = (Button)findViewById(R.id.pop_button);

        pop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(quiz_pop.this,main.class);
               intent.putExtra("selected_quiz",selected_quiz);
               intent.putExtra("page_id",page_id);
               startActivity(intent);
            }
        });
    }
}