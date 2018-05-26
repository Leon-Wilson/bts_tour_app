package fs.sdvbs.bts_tour;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

public class quiz_main extends AppCompatActivity {

    /*//
    LW_BUG_1: Included layout cause crash when trying to find ID
    Can't access the view that were added using the include tag

        SOLUTION: The includes didn't have IDs attached to them
        I gave IDs to the actual layouts but I didn't attach any IDs to the <include> tags
        in the quiz section

    LW_BUG_2: IDs don't hold implementations at runtime
    When adding an id to the nested layout or the include tags it will store the id
    into the R.java file but will lose the reference before the

        SOLUTION: Moving initialization into onCreate() method
        It is not possible(?) or safe to access and initialize a variable that requires an id
        because that id isn't pointing to anything yet.



    //*/
    //FrameLayout test = (FrameLayout) findViewById(R.id.quiz_template);

    //MULTIPLE CHOICE
    View multi_choice;
    TextView mc_question;
    Button mc_choice_1;
    Button mc_choice_2;
    Button mc_choice_3;
    Button mc_choice_4;

    //MULTIPLE ANSWER
    View multi_answer;
    TextView ma_question;
    Button ma_choice_1;
    Button ma_choice_2;
    Button ma_choice_3;
    Button ma_choice_4;

    //FILL IN
    View fill_in;
    TextView fi_question;
    TextView fi_answer;


    int current_question = 0;
    int questions_in_quiz = 0;

    quiz current_quiz;




    boolean test_bool = true;

    public quiz createTestQuiz()
    {
        //TEST QUIZ
        quiz test_quiz;

        question[] test_questions = new question[4];

        answer[] q1_answers = new answer[4];

        answer[] q2_answers = new answer[4];

        answer[] q3_answers = new answer[4];

        answer[] q4_answers = new answer[4];

        //CREATE ANSWERS
        answer q1_answer_1 = new answer("A",false);
        answer q1_answer_2 = new answer("B",false);
        answer q1_answer_3 = new answer("C",false);
        answer q1_answer_4 = new answer("D",true);

        answer q2_answer_1 = new answer("E",false);
        answer q2_answer_2 = new answer("F",false);
        answer q2_answer_3 = new answer("G",true);
        answer q2_answer_4 = new answer("H",false);

        answer q3_answer_1 = new answer("yes",true);
        answer q3_answer_2 = new answer("yes",true);
        answer q3_answer_3 = new answer("yes",true);
        answer q3_answer_4 = new answer("no",false);

        answer q4_answer_1 = new answer("no",false);
        answer q4_answer_2 = new answer("no",false);
        answer q4_answer_3 = new answer("yes",true);
        answer q4_answer_4 = new answer("yes",true);

        //SET ANSWERS
        q1_answers[0] = q1_answer_1;
        q1_answers[1] = q1_answer_2;
        q1_answers[2] = q1_answer_3;
        q1_answers[3] = q1_answer_4;

        q2_answers[0] = q2_answer_1;
        q2_answers[1] = q2_answer_2;
        q2_answers[2] = q2_answer_3;
        q2_answers[3] = q2_answer_4;

        q3_answers[0] = q3_answer_1;
        q3_answers[1] = q3_answer_2;
        q3_answers[2] = q3_answer_3;
        q3_answers[3] = q3_answer_4;

        q4_answers[0] = q4_answer_1;
        q4_answers[1] = q4_answer_2;
        q4_answers[2] = q4_answer_3;
        q4_answers[3] = q4_answer_4;

        //ADD TO QUESTION SET
        test_questions[0] = new multiple_choice("This is a test question, please pick 'D':",q1_answers);
        test_questions[1] = new multiple_choice("This is another question:",q2_answers);
        test_questions[2] = new multi_select("This is a multi selection question", q3_answers);
        test_questions[3] = new multi_select("This is another multi selection question", q4_answers);

        test_quiz = new quiz(test_questions);

        return test_quiz;
    }
    private void startQuiz()
    {
        multi_choice.setVisibility(View.GONE);
        multi_answer.setVisibility(View.GONE);
        fill_in.setVisibility(View.GONE);

        switch(current_quiz.questions[current_question].getType())
        {
            case multiple_choice:
                //SET VISIBILITY
                multi_choice.setVisibility(View.VISIBLE);

                //SET QUESTION TEXT
                mc_question.setText(current_quiz.questions[current_question].getQuestionText());

                //SET ANSWER TEXT
                mc_choice_1.setText(current_quiz.questions[current_question].getAnswers()[0].answer_text);
                mc_choice_2.setText(current_quiz.questions[current_question].getAnswers()[1].answer_text);
                mc_choice_3.setText(current_quiz.questions[current_question].getAnswers()[2].answer_text);
                mc_choice_4.setText(current_quiz.questions[current_question].getAnswers()[3].answer_text);

                //CHECK ANSWER
                break;
            case multiple_answer:
                multi_answer.setVisibility(View.VISIBLE);
                break;
            case fill_in_blank:
                fill_in.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void nextQuestion(int current_, quiz quiz_)
    {
        //remember to increment before calling function

        //HIDE All layouts
        multi_choice.setVisibility(View.GONE);
        multi_answer.setVisibility(View.GONE);
        fill_in.setVisibility(View.GONE);

        //quiz_.questions[current_ + 1].
        switch(quiz_.questions[current_].getType())
        {
            case multiple_choice:
                //SET QUESTION TEXT
                multi_choice.setVisibility(View.VISIBLE);
                mc_question.setText(quiz_.questions[current_].getQuestionText());

                //SET ANSWER
                mc_choice_1.setText(quiz_.questions[current_].getAnswers()[0].answer_text);
                mc_choice_2.setText(quiz_.questions[current_].getAnswers()[1].answer_text);
                mc_choice_3.setText(quiz_.questions[current_].getAnswers()[2].answer_text);
                mc_choice_4.setText(quiz_.questions[current_].getAnswers()[3].answer_text);
                break;
            case multiple_answer:
                multi_answer.setVisibility(View.VISIBLE);
                break;
            case fill_in_blank:
                fill_in.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //MULTI_CHOICE
        multi_choice = (View) findViewById(R.id.quiz_multi_choice);
        mc_question = (TextView) multi_choice.findViewById(R.id.mc_question_text);
        mc_choice_1 = (Button) multi_choice.findViewById(R.id.mc_choice_1);
        mc_choice_2 = (Button) multi_choice.findViewById(R.id.mc_choice_2);
        mc_choice_3 = (Button) multi_choice.findViewById(R.id.mc_choice_3);
        mc_choice_4 = (Button) multi_choice.findViewById(R.id.mc_choice_4);

        //MULTI_ANSWER
        multi_answer = (View) findViewById(R.id.quiz_multi_answer);
        ma_question = (TextView) multi_answer.findViewById(R.id.mc_question_text);
        ma_choice_1 = (Button) multi_answer.findViewById(R.id.ma_choice_1);
        ma_choice_2 = (Button) multi_answer.findViewById(R.id.ma_choice_2);
        ma_choice_3 = (Button) multi_answer.findViewById(R.id.ma_choice_3);
        ma_choice_4 = (Button) multi_answer.findViewById(R.id.ma_choice_4);

        //FILL_IN
        fill_in = (View) findViewById(R.id.quiz_fill_in);
        fi_answer = (TextView)  fill_in.findViewById(R.id.fi_answer_field);
        fi_question = (TextView)  fill_in.findViewById(R.id.fi_question_field);

        //multi_choice.setVisibility(View.GONE);
        multi_answer.setVisibility(View.GONE);
        fill_in.setVisibility(View.GONE);

        //TEST QUIZ
        if(test_bool)
        {
            current_quiz = createTestQuiz();
        }
        else
        {
            // current quiz will be equal to the quiz selected from the list
        }

        startQuiz();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "oof", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mc_choice_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(current_quiz.questions[current_question].getAnswers()[0].isCorrect())
                {
                    Snackbar.make(view, "RIGHT", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else
                {
                    Snackbar.make(view, "WRONG", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                if(current_question < current_quiz.questions.length - 1)
                {
                    current_question += 1;
                    nextQuestion(current_question, current_quiz);
                }
            }
        });

        mc_choice_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(current_quiz.questions[current_question].getAnswers()[1].isCorrect())
                {
                    Snackbar.make(view, "RIGHT", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else
                {
                    Snackbar.make(view, "WRONG", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                if(current_question < current_quiz.questions.length - 1)
                {
                    current_question += 1;
                    nextQuestion(current_question, current_quiz);
                }
            }
        });

        mc_choice_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(current_quiz.questions[current_question].getAnswers()[2].isCorrect())
                {
                    Snackbar.make(view, "RIGHT", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else
                {
                    Snackbar.make(view, "WRONG", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                if(current_question < current_quiz.questions.length - 1)
                {
                    current_question += 1;
                    nextQuestion(current_question, current_quiz);
                }
            }
        });

        mc_choice_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(current_quiz.questions[current_question].getAnswers()[3].isCorrect())
                {
                    Snackbar.make(view, "RIGHT", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else
                {
                    Snackbar.make(view, "WRONG", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                if(current_question < current_quiz.questions.length - 1)
                {
                    current_question += 1;
                    nextQuestion(current_question,current_quiz);
                }
            }
        });
    }

}
