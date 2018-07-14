package fs.sdvbs.bts_tour;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

public class quiz_main extends AppCompatActivity {

    /*//
    LW_BUG_1: Included layout cause crash when trying to find ID.
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

    LW_BUG_3: Final iteration of loop returns false positive when checking multiple selection question
    When checking the answers for a multi-select question the if statement seems to trigger a false
    on the final iteration of the loop. It may be related to the way I am checking the selected answers
    against the question's answers.
        UPDATE: The bug seems to occur whenever the two booleans are false. It will interpret False && False
        as False instead of True

        SOLUTION: Use XOR (^) instead of AND (&&) when comparing two booleans
        When doing a check to see if two booleans are the same you must be very careful on the operator
        you use to compare. Using && with two False answers will cause the result to return False because
        logically False && False == False. Using XOR and removing the negation from the if check will
        only return true if the answers in the two group are not the same as each other (a = True ^ b = False),
        (a = False, b = True)

    LW_BUG_4: After answering the final question, the quiz does not give enough time to view the result message.
    This could be fixed when the I implement the result screen


    TODO: implement results screen
    //*/

    /*
    TODO: (DONE) Refactor player into quiz system to update their progress
    //*/

    //FrameLayout test = (FrameLayout) findViewById(R.id.quiz_template);
    player current_player;
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
    Button ma_check_button;

    boolean[] selected = new boolean[4];

    //FILL IN
    View fill_in;
    TextView fi_question;
    TextView fi_answer;

    //SHORT ANSWER
    View short_ans;
    TextView sa_question;
    EditText sa_answer;
    Button sa_submit_button;


    int current_question = 0;
    int questions_in_quiz = 0;
    int current_score;

    quiz current_quiz;



    boolean test_bool = false;

    public void results()
    {
        multi_choice.setVisibility(View.GONE);
        multi_answer.setVisibility(View.GONE);
        fill_in.setVisibility(View.GONE);
        short_ans.setVisibility(View.GONE);

        //TODO: Create Results processing function
        current_player.stats.current_points += current_quiz.total_correct - current_quiz.previous_total;
        current_quiz.previous_total = current_quiz.total_correct;

        if(current_player.stats.LEVELUP())
        {
            Snackbar.make(findViewById(R.id.quiz_template), "You have leveled up!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        if(current_quiz.quizComplete())
        {
            Snackbar.make(findViewById(R.id.quiz_template), "You have answered enough to continue!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        onBackPressed();
    }

    public quiz createTestQuiz(boolean quiz_complete, boolean answers_complete)
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
        answer q3_answer_3 = new answer("no",false);
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
        test_questions[0] = new multiple_choice("Q1.This is a test question, please pick 'D':",q1_answers);
        test_questions[1] = new multiple_choice("Q2.This is another question:",q2_answers);
        test_questions[2] = new multi_select("Q3.This is a multi selection question", q3_answers);
        test_questions[3] = new multi_select("Q4.This is another multi selection question", q4_answers);

        test_quiz = new quiz(test_questions);

        if(quiz_complete)
        {
            test_quiz.setComplete(true);
        }

        if(answers_complete)
        {
            test_quiz.questions[1].setAlreadyCorrect(true);
            test_quiz.questions[3].setAlreadyCorrect(true);

            for(int i = 0; i < test_questions.length;i++)
            {
                if(test_quiz.questions[i].alreadyCorrect())
                {
                    test_quiz.total_correct += 1;

                }
            }

            test_quiz.previous_total = test_quiz.total_correct;
        }

        return test_quiz;
    }
    private void startQuiz()
    {
        //LW_BUG_7: System doesn't seem to recognize change between multiple choice and multiple select
        multi_choice.setVisibility(View.GONE);
        multi_answer.setVisibility(View.GONE);
        fill_in.setVisibility(View.GONE);
        short_ans.setVisibility(View.GONE);

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

                //SET QUESTION TEXT
                ma_question.setText(current_quiz.questions[current_question].getQuestionText());

                //SET ANSWER TEXT
                ma_choice_1.setText(current_quiz.questions[current_question].getAnswers()[0].answer_text);
                ma_choice_2.setText(current_quiz.questions[current_question].getAnswers()[1].answer_text);
                ma_choice_3.setText(current_quiz.questions[current_question].getAnswers()[2].answer_text);
                ma_choice_4.setText(current_quiz.questions[current_question].getAnswers()[3].answer_text);
                break;
            case fill_in_blank:
                fill_in.setVisibility(View.VISIBLE);
                break;
            case short_answer:
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
        short_ans.setVisibility(View.GONE);

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
                //SET QUESTION TEXT
                ma_question.setText(current_quiz.questions[current_question].getQuestionText());

                //SET ANSWER TEXT
                ma_choice_1.setText(current_quiz.questions[current_question].getAnswers()[0].answer_text);
                ma_choice_2.setText(current_quiz.questions[current_question].getAnswers()[1].answer_text);
                ma_choice_3.setText(current_quiz.questions[current_question].getAnswers()[2].answer_text);
                ma_choice_4.setText(current_quiz.questions[current_question].getAnswers()[3].answer_text);
                break;
            case fill_in_blank:
                fill_in.setVisibility(View.VISIBLE);
                break;
            case short_answer:
                short_ans.setVisibility(View.VISIBLE);
                sa_question.setText(current_quiz.questions[current_question].getQuestionText());
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //INITIALIZE selected values to (-1) as null case.
        for(int i = 0; i < selected.length; i++)
        {
            selected[i] = false;
        }

        //MULTI_CHOICE
        multi_choice = (View) findViewById(R.id.quiz_multi_choice);
        mc_question = (TextView) multi_choice.findViewById(R.id.mc_question_text);
        mc_choice_1 = (Button) multi_choice.findViewById(R.id.mc_choice_1);
        mc_choice_2 = (Button) multi_choice.findViewById(R.id.mc_choice_2);
        mc_choice_3 = (Button) multi_choice.findViewById(R.id.mc_choice_3);
        mc_choice_4 = (Button) multi_choice.findViewById(R.id.mc_choice_4);

        //MULTI_ANSWER
        multi_answer = (View) findViewById(R.id.quiz_multi_answer);
        ma_question = (TextView) multi_answer.findViewById(R.id.ma_question_text);
        ma_choice_1 = (Button) multi_answer.findViewById(R.id.ma_choice_1);
        ma_choice_2 = (Button) multi_answer.findViewById(R.id.ma_choice_2);
        ma_choice_3 = (Button) multi_answer.findViewById(R.id.ma_choice_3);
        ma_choice_4 = (Button) multi_answer.findViewById(R.id.ma_choice_4);
        ma_check_button = (Button) multi_answer.findViewById((R.id.ma_check_button));

        //FILL_IN
        fill_in = (View) findViewById(R.id.quiz_fill_in);
        fi_answer = (TextView)  fill_in.findViewById(R.id.fi_answer_field);
        fi_question = (TextView)  fill_in.findViewById(R.id.fi_question_field);

        //SHORT_ANSWER
        short_ans = (View) findViewById(R.id.quiz_short_answer);
        sa_question = (TextView) short_ans.findViewById(R.id.sa_question_field);
        sa_answer = (EditText) short_ans.findViewById(R.id.sa_answer_field);
        sa_submit_button = (Button) short_ans.findViewById(R.id.sa_submit_button);

        //multi_choice.setVisibility(View.GONE);
        multi_answer.setVisibility(View.GONE);
        fill_in.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();

        //REMOVE ASSIGNMENT BELOW
//        test_bool = true;

        if(extras != null)
        {
            current_player = player.getInstance();//extras.getParcelable("current_player");
            int selected = extras.getInt("selected_quiz");
            current_quiz = user_json.getInstance().quiz_list[selected];
        }
        else
        {
            //TODO: Error handling for null current player
            current_player = new player("BAD NEWS");
            test_bool = true;
        }

        //TEST QUIZ
        if(test_bool)
        {
            current_quiz = createTestQuiz(false,true);
        }
        else
        {
            // current quiz will be equal to the quiz selected from the list
        }

        //TODO: make this system a secondary check for testing a test quiz
        //TODO: create primary system that checks for completeness before starting the quiz in the quiz list
        if(!current_quiz.isComplete())
        {
            startQuiz();
        }
        else
        {
            //TODO: find work around that displays message of completion
           /*/* Snackbar.make(findViewById(R.id.quiz_template), "You have answered enough to continue!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
                    //*/
            onBackPressed();
        }

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
                    if(!current_quiz.questions[current_question].alreadyCorrect())
                    {
                        current_quiz.total_correct += 1;
                        current_quiz.questions[current_question].setAlreadyCorrect(true);

                        Snackbar.make(view, "NEW CORRECT ANSWER!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else
                    {
                        Snackbar.make(view, "GOOD MEMORY!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    current_score += 1;
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
                else
                {
                    results();
                }
            }
        });

        mc_choice_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(current_quiz.questions[current_question].getAnswers()[1].isCorrect())
                {
                    if(!current_quiz.questions[current_question].alreadyCorrect())
                    {
                        current_quiz.total_correct += 1;
                        current_quiz.questions[current_question].setAlreadyCorrect(true);
                        Snackbar.make(view, "NEW CORRECT ANSWER!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else
                    {
                        Snackbar.make(view, "GOOD MEMORY!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    current_score += 1;
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
                else
                {
                    results();
                }
            }
        });

        mc_choice_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(current_quiz.questions[current_question].getAnswers()[2].isCorrect())
                {
                    if(!current_quiz.questions[current_question].alreadyCorrect())
                    {
                        current_quiz.total_correct += 1;
                        current_quiz.questions[current_question].setAlreadyCorrect(true);
                        Snackbar.make(view, "NEW CORRECT ANSWER!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else
                    {
                        Snackbar.make(view, "GOOD MEMORY!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    current_score += 1;
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
                else
                {
                    results();
                }
            }
        });

        mc_choice_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(current_quiz.questions[current_question].getAnswers()[3].isCorrect())
                {
                    if(!current_quiz.questions[current_question].alreadyCorrect())
                    {
                        current_quiz.total_correct += 1;
                        current_quiz.questions[current_question].setAlreadyCorrect(true);
                        Snackbar.make(view, "NEW CORRECT ANSWER!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else
                    {
                        Snackbar.make(view, "GOOD MEMORY!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    current_score += 1;
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
                else
                {
                    results();
                }
            }
        });


        //MULTIPLE ANSWER
        ma_choice_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selected[0])
                {
                    selected[0] = false;
                }
                else
                {
                    selected[0] = true;
                }

            }
        });

        ma_choice_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selected[1])
                {
                    selected[1] = false;
                }
                else
                {
                    selected[1] = true;
                }

            }
        });

        ma_choice_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selected[2])
                {
                    selected[2] = false;
                }
                else
                {
                    selected[2] = true;
                }

            }
        });

        ma_choice_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selected[3])
                {
                    selected[3] = false;
                }
                else
                {
                    selected[3] = true;
                }

            }
        });

        ma_check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean correct = true;
                for(int i = 0; i < selected.length;i++)
                {
                    //SOMETHING STRANGE IS HAPPENING HERE ON THE FINAL ITERATION
                    if((selected[i] ^ current_quiz.questions[current_question].getAnswers()[i].isCorrect()))
                    {
                        correct = false;
                    }
                }

                if(correct)
                {
                    if(!current_quiz.questions[current_question].alreadyCorrect())
                    {
                        current_quiz.total_correct += 1;
                        current_quiz.questions[current_question].setAlreadyCorrect(true);
                        Snackbar.make(view, "NEW CORRECT ANSWER!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else
                    {
                        Snackbar.make(view, "GOOD MEMORY!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    current_score += 1;
                }
                else
                {
                    Snackbar.make(view, "WRONG", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                if(current_question < current_quiz.questions.length - 1)
                {
                    for(int i = 0; i < selected.length; i++)
                    {
                        selected[i] = false;
                    }
                    current_question += 1;
                    nextQuestion(current_question,current_quiz);
                }
                else
                {
                    results();
                }
            }
        });

        sa_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO : Update DB
                if(current_question < current_quiz.questions.length - 1)
                {
                    current_question += 1;
                    nextQuestion(current_question,current_quiz);
                }
                else
                {
                    results();
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent();
        intent.putExtra("current_player", current_player);
        //setResult(RESULT_OK, intent);
        finish();
    }

}
