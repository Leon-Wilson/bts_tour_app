package fs.sdvbs.bts_tour;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Leon on 6/12/18.
 */

/*
The current user's JSON file that will be pulled from the Firebase.
This will be the main way of updating the user on the database and should eliminate
the need for constant pulling and pushing from the database.


    LW_BUG_5: The text for question answers is not being captured by the system.
 */

public class user_json implements Parcelable{

    private static user_json instance = null;
    FirebaseAuth mAuth;
    FirebaseDatabase fDatabase;

    DatabaseReference building_one;
    DatabaseReference building_two;
    DatabaseReference building_three;
    DatabaseReference building_four;
    ArrayList<DatabaseReference> buildings = new ArrayList<>();

    ArrayList<String> question_num = new ArrayList<>();
    ArrayList<String> multi_answers = new ArrayList<>();
    ArrayList<String> importance_num = new ArrayList<>();

    //TODO: Add variable to firebase that tells how many total quizzes there are
    quiz[] quiz_list = new quiz[500];
    int quiz_num = 0;
    int quiz_current_ = 1;

    script[] script_list = new script[500];
    int script_num = 0;
    int script_current = 1;

    public static user_json getInstance()
    {
        if(instance == null)
        {
            instance = new user_json();
        }
        return instance;
    }

    ChildEventListener parser = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            //SCRIPT STUFF
            String script_name = "";
            String script_description = "";
            String[] script_degrees = null;
            String[] script_important = null;


            //QUIZ STUFF
            String quiz_name = dataSnapshot.getKey().toString();
            Boolean quiz_complete = false;
            Boolean quiz_locked = false;
            int quiz_prev_total = 0;
            int quiz_total_correct = 0;
            int quiz_total_questions = 0;
            question[] quiz_questions = null;

            for(DataSnapshot data : dataSnapshot.getChildren())
            {
                if(data.hasChild("name"))
                {
                    //Log.d(dataSnapshot.getKey().toString(), data.child("important").child("important_1").toString());
                    /*
                    PATH SPECIFIC HANDLING TO DIFFERENT AREAS OF DATABASE
                    SCRIPT
                    test.child("name")
                    test.child("description")
                    test.child("degrees")
                    test.child("important")
                    test.child("important").child("important_N")

                     */
                    int important_children = (int) data.child("important").getChildrenCount();

                    script_name = data.child("name").getValue().toString();
                    script_description = data.child("description").getValue().toString();

                    script_important = new String[important_children];
                    for(int i = 0; i < important_children; i++)
                    {
                        script_important[i] = data.child("important").child(importance_num.get(i)).getValue().toString();
                    }
                    //Log.d(dataSnapshot.getKey().toString(), "children " + important_children);
                    //
                }
                else if(data.hasChild("questions"))
                {
                    /*
                    //TODO: Handle type specific parsing
                    PATH SPECIFIC HANDLING TO DIFFERENT AREAS OF DATABASE

                    MULTI ANSWERS
                    test.child("questions").child("qN").child("answers").child("aN").child("text").getValue().toString();
                    test.child("questions").child("qN").child("answers").child("aN").child("correct").getValue().toString();

                    SHORT ANSWER

                    QUESTIONS
                    test.child("questions").child("q1").child("answers");
                    test.child("questions").child("q1").child("already_correct");
                    test.child("questions").child("q1").child("text");
                    test.child("questions").child("q1").child("type");

                    QUIZ
                    test.child("complete");
                    test.child("locked");
                    test.child("previous_total");
                    test.child("questions");
                    test.child("total_correct");
                    test.child("total_questions");*/

                    //TODO: Put into function
                        /*
                        LW_BUG_8: Always chooses multi_choice for each question
                        SOLUTION: Forgot to change child hardcoded assignment to dynamic version using question_num.get(q_num)
                        */
                    quiz_questions = new question[5];

                    for(int q_num = 0; q_num < 5; q_num++)
                    {
                        multiple_choice temp_choice;
                        multi_select temp_select;
                        short_answer temp_short;

                        boolean temp_already_correct = (boolean) data.child("questions").child(question_num.get(q_num)).child("already_correct").getValue();
                        String temp_question_text = data.child("questions").child(question_num.get(q_num)).child("text").getValue().toString();
                        String temp_question_type = data.child("questions").child(question_num.get(q_num)).child("type").getValue().toString();

                        answer[] temp_ans_array = new answer[4];

                        for(int a_num = 0; a_num < 4; a_num++)
                        {
                            if(temp_question_type == "short_answer")
                            {
                                String ans_text = data.child("questions").child(question_num.get(q_num)).child("answers").child(multi_answers.get(0)).child("text").getValue().toString();
                                temp_ans_array[a_num] = new answer(ans_text,false);
                            }
                            else
                            {
                                String ans_text = data.child("questions").child(question_num.get(q_num)).child("answers").child(multi_answers.get(a_num)).child("text").getValue().toString();
                                Boolean ans_correct = (Boolean) data.child("questions").child(question_num.get(q_num)).child("answers").child(multi_answers.get(a_num)).child("correct").getValue();
                                answer temp_ans = new answer(ans_text, ans_correct);
                                temp_ans_array[a_num] = temp_ans;
                            }
                        }

                        switch(temp_question_type)
                        {
                            case "multi_choice":
                                temp_choice = new multiple_choice(temp_question_text,temp_ans_array);
                                temp_choice.setAlreadyCorrect(temp_already_correct);
                                quiz_questions[q_num] = temp_choice;
                                break;
                            case "multi_select":
                                temp_select = new multi_select(temp_question_text,temp_ans_array);
                                temp_select.setAlreadyCorrect(temp_already_correct);
                                quiz_questions[q_num] = temp_select;
                                break;
                            case "short_answer":
                                temp_short = new short_answer(temp_question_text);
                                temp_short.setAlreadyCorrect(temp_already_correct);
                                quiz_questions[q_num] = temp_short;

                        }
                        /*if(temp_question_type == "multi_choice")
                        {
                            temp_choice = new multiple_choice(temp_question_text,temp_ans_array);
                            quiz_questions[q_num] = temp_choice;
                        }
                        else if(temp_question_type == "multi_select")
                        {
                            temp_select = new multi_select(temp_question_text,temp_ans_array);
                            quiz_questions[q_num] = temp_select;
                        }*/

                    }

                    quiz_complete = (boolean) data.child("complete").getValue();
                    quiz_locked = (boolean) data.child("locked").getValue();
                    quiz_prev_total = Integer.valueOf(data.child("previous_total").getValue().toString());
                    quiz_total_correct = Integer.valueOf(data.child("previous_total").getValue().toString());
                    quiz_total_questions = Integer.valueOf(data.child("previous_total").getValue().toString());

                    //Log.d(dataSnapshot.getKey().toString(), data.child("questions").child("q1").child("answers").child("a1").getValue().toString());
                }

                if(script_important != null)
                {
                    script new_script = new script(script_name,script_description,script_degrees,script_important);
                    script_list[script_num] = new_script;
                    script_num++;
                }
                if(quiz_questions != null)
                {

                    quiz new_quiz = new quiz(quiz_name, quiz_questions);
                    if(quiz_num % 2 != 0)
                    {
                        quiz_list[quiz_num - quiz_current_] = new_quiz;
                        quiz_current_++;
                    }


                    quiz_num++;
                }
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public user_json()
    {

        mAuth = FirebaseAuth.getInstance();
        fDatabase = FirebaseDatabase.getInstance();

        building_one = fDatabase.getReference("building_one");
        building_two = fDatabase.getReference("building_two");
        building_three = fDatabase.getReference("building_three");
        building_four = fDatabase.getReference("building_four");

        buildings.add(building_one);
        buildings.add(building_two);
        buildings.add(building_three);
        buildings.add(building_four);

        question_num.add("q1");
        question_num.add("q2");
        question_num.add("q3");
        question_num.add("q4");
        question_num.add("q5");

        multi_answers.add("a1");
        multi_answers.add("a2");
        multi_answers.add("a3");
        multi_answers.add("a4");

        for(int i = 1; i <= 30; i++)
        {
            importance_num.add("important_" + String.valueOf(i));
        }
        //go through each building
        for(int i = 0; i < buildings.size();i++)
        {
            buildings.get(i).addChildEventListener(parser);
            //Log.d("building ",buildings.get(i).child("").toString());
        }
    }




    protected user_json(Parcel in) {
    }

    public quiz[] getQuizzes()
    {
        return quiz_list;
    }
    public script[] getScripts()
    {
        return script_list;
    }

    public static final Creator<user_json> CREATOR = new Creator<user_json>() {
        @Override
        public user_json createFromParcel(Parcel in) {
            return new user_json(in);
        }

        @Override
        public user_json[] newArray(int size) {
            return new user_json[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
