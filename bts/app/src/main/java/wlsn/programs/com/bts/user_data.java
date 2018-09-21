package wlsn.programs.com.bts;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Leon on 7/31/18.
 */


public class user_data {

    private static user_data instance = null;
    public static user_data getInstance()
    {
        if(instance == null)
        {
            instance = new user_data();
        }
        return instance;
    }

    public static user_data getInstance(String user_key)
    {
        if(instance == null)
        {
            instance = new user_data(user_key);
        }
        return instance;
    }


    private FirebaseDatabase fDatabase;
    private ArrayList<DatabaseReference> buildings = new ArrayList<>();
    private DatabaseReference building_one, building_two, building_three, building_four;
    private ArrayList<quiz> b1,b2,b3,b4;
    private HashMap<String, quiz> user_quizzes = null;
    ArrayList<String> user_question_num = new ArrayList<>();
    ArrayList<String> user_multi_answers = new ArrayList<>();

    ArrayList<String> question_num = new ArrayList<>();
    ArrayList<String> multi_answers = new ArrayList<>();
    ArrayList<String> importance_num = new ArrayList<>();

    quiz[] temp_quiz_list = new quiz[100];
    quiz[] quiz_list;
    int quiz_num = 0;
    int quiz_current_ = 1;

    script[] temp_script_list = new script[100];
    script[] script_list;
    int script_num = 0;
    int script_current = 1;

    int b1_start=0;
    int b2_start=0;
    int b3_start=0;
    int b4_start=0;
    int unknown_start = 0;

    private boolean sorted = false;
    private boolean trimmed = false;
    public user_data()
    {
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

    if(quiz_list == null)
    {
        trimQuizzes();
    }

    if(script_list == null)
    {
        trimScripts();
    }
}

    public user_data(String user_key)
    {
        fDatabase = FirebaseDatabase.getInstance();

        if(user_quizzes == null)
        user_quizzes = new HashMap<>();

        user_question_num.add("question_1");
        user_question_num.add("question_2");
        user_question_num.add("question_3");
        user_question_num.add("question_4");
        user_question_num.add("question_5");

        user_multi_answers.add("answer_1");
        user_multi_answers.add("answer_2");
        user_multi_answers.add("answer_3");
        user_multi_answers.add("answer_4");

        DatabaseReference uq = fDatabase.getReference("users/" + user_key+"/quizzes");

        uq.addChildEventListener(user_parser);
    }

    private ChildEventListener user_parser = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            String quiz_name = dataSnapshot.getKey().toString();
            Boolean quiz_complete = false;
            Boolean quiz_locked = false;
            int quiz_prev_total = 0;
            int quiz_total_correct = 0;
            int quiz_total_questions = 0;
            question[] quiz_questions = null;
            int building_num = 0;

            for(DataSnapshot data : dataSnapshot.getChildren())
            {
                if(data.getKey().contentEquals("building_number"))
                {
                    building_num = Integer.valueOf(data.getValue().toString());
                }
                if(data.getKey().contentEquals("is_complete"))
                {
                    quiz_complete = (boolean)data.getValue();
                }
                if(data.getKey().contentEquals("previous_total"))
                {
                    quiz_prev_total = Integer.valueOf(data.getValue().toString());
                }
                if(data.getKey().contentEquals("total_correct"))
                {
                    quiz_total_correct = Integer.valueOf(data.getValue().toString());
                }
                if(data.getKey().contentEquals("total_questions"))
                {
                    quiz_total_questions = Integer.valueOf(data.getValue().toString());
                    if(quiz_questions != null)
                    {

                        quiz new_quiz = new quiz(quiz_name, quiz_questions);
                        new_quiz.setBuildingNum(building_num);
                        new_quiz.setTotalCorrect(quiz_total_correct);
                        user_quizzes.put(new_quiz.getName(),new_quiz);
                        temp_quiz_list[quiz_num] = new_quiz;
                        temp_quiz_list[quiz_num].setBuildingNum(building_num);
                        quiz_num++;
                    }
                }
                if(data.getKey().contentEquals("questions"))
                {
                    quiz_questions = new question[5];

                    for(int q_num = 0; q_num < 5; q_num++)
                    {
                        question_multiple_choice temp_choice;
                        question_multiple_select temp_select;
                        question_short_answer temp_short;

                        boolean temp_already_correct = (boolean) data.child(user_question_num.get(q_num)).child("already_correct").getValue();
                        String temp_question_text = data.child(user_question_num.get(q_num)).child("question_text").getValue().toString();
                        String temp_question_type = data.child(user_question_num.get(q_num)).child("type").getValue().toString();

                        answer[] temp_ans_array;

                        if (temp_question_type.contentEquals("short_answer"))
                        {
                            temp_ans_array = new answer[1];
                        }
                        else
                        {
                            temp_ans_array = new answer[4];
                        }


                        for(int a_num = 0; a_num < 4; a_num++)
                        {
                            if(temp_question_type.contentEquals("short_answer"))
                            {
                                String ans_text = data.child(user_question_num.get(q_num)).child("answers").child("answer").child("text").getValue().toString();
                                temp_ans_array[a_num] = new answer(ans_text,false);
                                break;
                            }
                            else
                            {
                                String ans_text = data.child(user_question_num.get(q_num)).child("answers").child(user_multi_answers.get(a_num)).child("text").getValue().toString();
                                Boolean ans_correct = (Boolean) data.child(user_question_num.get(q_num)).child("answers").child(user_multi_answers.get(a_num)).child("correct").getValue();
                                answer temp_ans = new answer(ans_text, ans_correct);
                                temp_ans_array[a_num] = temp_ans;
                            }
                        }

                        switch(temp_question_type)
                        {
                            case "multiple_choice":
                                temp_choice = new question_multiple_choice(temp_question_text,temp_ans_array);
                                temp_choice.setAlreadyCorrect(temp_already_correct);
                                quiz_questions[q_num] = temp_choice;
                                break;
                            case "multiple_answer":
                                temp_select = new question_multiple_select(temp_question_text,temp_ans_array);
                                temp_select.setAlreadyCorrect(temp_already_correct);
                                quiz_questions[q_num] = temp_select;
                                break;
                            case "short_answer":
                                temp_short = new question_short_answer(temp_question_text);
                                temp_short.setAlreadyCorrect(temp_already_correct);
                                quiz_questions[q_num] = temp_short;
                                break;
                        }
                    }

                    //ADD QUESTIONS
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
    private ChildEventListener parser = new ChildEventListener() {
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
            int building_num = 0;

            for(DataSnapshot data : dataSnapshot.getChildren())
            {
                if(data.hasChild("name"))
                {
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
                        LW_BUG_8: Always chooses multiple_choice for each question
                        SOLUTION: Forgot to change child hardcoded assignment to dynamic version using question_num.get(q_num)
                        */
                    quiz_questions = new question[5];

                    for(int q_num = 0; q_num < 5; q_num++)
                    {
                        question_multiple_choice temp_choice;
                        question_multiple_select temp_select;
                        question_short_answer temp_short;

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
                                temp_choice = new question_multiple_choice(temp_question_text,temp_ans_array);
                                temp_choice.setAlreadyCorrect(temp_already_correct);
                                quiz_questions[q_num] = temp_choice;
                                break;
                            case "multi_select":
                                temp_select = new question_multiple_select(temp_question_text,temp_ans_array);
                                temp_select.setAlreadyCorrect(temp_already_correct);
                                quiz_questions[q_num] = temp_select;
                                break;
                            case "short_answer":
                                temp_short = new question_short_answer(temp_question_text);
                                temp_short.setAlreadyCorrect(temp_already_correct);
                                quiz_questions[q_num] = temp_short;

                        }

                    }

                    quiz_complete = (boolean) data.child("complete").getValue();
                    quiz_locked = (boolean) data.child("locked").getValue();
                    quiz_prev_total = Integer.valueOf(data.child("previous_total").getValue().toString());
                    quiz_total_correct = Integer.valueOf(data.child("previous_total").getValue().toString());
                    quiz_total_questions = Integer.valueOf(data.child("previous_total").getValue().toString());

                    //Log.d(dataSnapshot.getKey().toString(), data.child("questions").child("q1").child("answers").child("a1").getValue().toString());
                }
                else if( data.getKey().contains("building_number"))
                {
                    building_num = Integer.valueOf(data.getValue().toString());
                }

                if(script_important != null)
                {
                    script new_script = new script(script_name,script_description,script_degrees,script_important);
                    new_script.setBuildingNum(building_num);
                    temp_script_list[script_num] = new_script;
                    script_num++;
                }
                if(quiz_questions != null)
                {

                    quiz new_quiz = new quiz(quiz_name, quiz_questions);
                    new_quiz.setBuildingNum(building_num);
                    if(quiz_num % 2 != 0)
                    {
                        temp_quiz_list[quiz_num - quiz_current_] = new_quiz;
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

    public quiz[] getQuizzes()
    {
        return quiz_list;
    }

    public script[] getScripts()
    {
        return script_list;
    }

    public void trimScripts()
    {
        int script_num = 0;

        for(int i = 0; i < temp_script_list.length;i++)
        {
            if(temp_script_list[i] == null)
            {
                script_num = i;
                break;
            }
        }

        if(script_num == 0)
        {
            script_num = 1;
            script_list = new script[script_num];
            script_list[0] = new script("Sorry!","We couldn't find any scripts. Please reload the page",null,null);
            script_list[0].setBuildingNum(1);
        }
        else if(script_num > temp_script_list.length)
        {
            script_num = 1;
            script_list = new script[script_num];;
            script_list[0] = new script("Sorry!","Something went wrong when gathering data. Please reload the page",null,null);
        }
        else
        {
            script_list = new script[script_num];
            for(int j = 0; j < script_num; j++)
            {
                script_list[j] = temp_script_list[j];
            }
        }
    }

    public void trimQuizzes()
    {
        if(!trimmed) {
            int quiz_num = 0;

            for (int i = 0; i < temp_quiz_list.length; i++) {
                if (temp_quiz_list[i] == null) {
                    quiz_num = i;
                    break;
                }
            }

            if (quiz_num == 0) {
                quiz_num = 1;
                quiz_list = new quiz[quiz_num];
                quiz_list[0] = new quiz();
                quiz_list[0].setBuildingNum(1);
            } else if (quiz_num > temp_quiz_list.length) {
                quiz_num = 1;
                quiz_list = new quiz[quiz_num];
                quiz_list[0] = new quiz();
                quiz_list[0].setName("We found more quizzes than expected");
            } else {
                quiz_list = new quiz[quiz_num];
                for (int j = 0; j < quiz_num; j++) {
                    quiz_list[j] = temp_quiz_list[j];
                }
                trimmed = true;
            }
        }
    }

    public void Load()
    {
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

        if(quiz_list == null)
        {
            trimQuizzes();
        }

        if(script_list == null)
        {
            trimScripts();
        }
    }

    public void LoadUserQuizzes(String user_key)
    {
        fDatabase = FirebaseDatabase.getInstance();

        user_quizzes = new HashMap<>();
        DatabaseReference uq = fDatabase.getReference("users/" + user_key+"/quizzes");

        uq.addChildEventListener(user_parser);
    }

    public void sortQuizzes()
    {
        if(!sorted)
        {
            sorted = false;
            int A = 0;
            int B = 1;
            int total_quizzes = quiz_list.length;
            int b1_size = 0;
            int b2_size = 0;
            int b3_size = 0;
            int b4_size = 0;
            int unknown_size = 0;

            ArrayList<quiz> b1_quizzes = new ArrayList<>();
            ArrayList<quiz> b2_quizzes = new ArrayList<>();
            ArrayList<quiz> b3_quizzes = new ArrayList<>();
            ArrayList<quiz> b4_quizzes = new ArrayList<>();
            ArrayList<quiz> unknown_quizzes = new ArrayList<>();
            ArrayList<quiz> total_quiz_list = new ArrayList<>();

            //Sort quizzes by building number
            //LAZY SORT
            for(int i = 0; i < total_quizzes; i++)
            {
                switch (quiz_list[i].getBuildingNum())
                {
                    case 1:
                        b1_quizzes.add(quiz_list[i]);
                        b1_size += 1;
                        continue;
                    case 2:
                        b2_quizzes.add(quiz_list[i]);
                        b2_size += 1;
                        continue;
                    case 3:
                        b3_quizzes.add(quiz_list[i]);
                        b3_size += 1;
                        continue;
                    case 4:
                        b4_quizzes.add(quiz_list[i]);
                        b4_size += 1;
                        continue;
                    default :
                        unknown_quizzes.add(quiz_list[i]);
                        unknown_size += 1;
                        continue;
                }
            }

            b2_start += (b1_start + b1_size);
            b3_start += (b2_start + b2_size);
            b4_start += (b3_start + b3_size);

            total_quiz_list.addAll(b1_quizzes);
            total_quiz_list.addAll(b2_quizzes);
            total_quiz_list.addAll(b3_quizzes);
            total_quiz_list.addAll(b4_quizzes);

            if(unknown_size > 0)
            {
                unknown_start = (b4_start + b4_size);
            }


            quiz_list = new quiz[total_quizzes];
            for(int i = 0; i < total_quizzes; i++)
            {
                quiz_list[i] = total_quiz_list.get(i);
            }

            sorted = true;
        }


    }

    public HashMap<String, quiz> getUserQuizzes() {
        return user_quizzes;
    }

    public int getStartingPos(int building)
    {
        switch(building)
        {
            case 0:
                return b1_start;
            case 1:
                return b2_start;
            case 2:
                return b3_start;
            case 3:
                return b4_start;
            default:
                return 0;
        }
    }
}
