package fs.sdvbs.bts_tour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Leon on 7/14/18.
 */

public class quiz_pop extends Activity {

    private player current_player;
    private int selected_quiz;
    private int total_correct;
    private int total_questions;
    private int builing_num;


    ArrayList<String> questions;
    HashMap<String, List<String>> questions_details;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.quiz_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * 0.8),(int)(height * .75));

        current_player = player.getInstance();
        current_player.current_user = user_json.getInstance();
        current_player.quizzes = current_player.current_user.getQuizzes();

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            selected_quiz = extras.getInt("selected_quiz");
        }

        TextView pop_name = (TextView)findViewById(R.id.pop_quiz_name);
        pop_name.setText(current_player.getQuizzes()[selected_quiz].getName());

        TextView pop_ratio = (TextView)findViewById(R.id.pop_quiz_ratio);
        total_correct = current_player.getQuizzes()[selected_quiz].total_correct;
        total_questions = current_player.getQuizzes()[selected_quiz].total_questions;

        pop_ratio.setText("You have " + total_correct + " out of " + total_questions + " correct.");

        TextView pop_builing_num = (TextView)findViewById(R.id.pop_building_num);
        builing_num = current_player.getQuizzes()[selected_quiz].building_num;
        pop_builing_num.setText("Building " + builing_num);

        ExpandableListView pop_quiz_questions = (ExpandableListView)findViewById(R.id.pop_quiz_questions);
        questions = new ArrayList<>();
        questions_details = new HashMap<>();
        String default_ans = "Answer";
        String default_res = "Good job.";
        List<String> defaults = new ArrayList<String>();
        defaults.add(default_ans);
        defaults.add(default_res);
        for(int i = 0; i < total_questions; i++)
        {
            questions.add(current_player.getQuizzes()[selected_quiz].questions[i].getQuestionText());

            questions_details.put(current_player.getQuizzes()[selected_quiz].questions[i].getQuestionText(),defaults);
        }

        ExpandableListAdapter questions_adapter = new pop_questions(this, questions, questions_details);
        pop_quiz_questions.setAdapter(questions_adapter);
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
        Button start_button = (Button)findViewById(R.id.pop_start_quiz);

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(quiz_pop.this, quiz_main.class);
               intent.putExtra("selected_quiz",selected_quiz);

               startActivity(intent);
            }
        });
    }
}
