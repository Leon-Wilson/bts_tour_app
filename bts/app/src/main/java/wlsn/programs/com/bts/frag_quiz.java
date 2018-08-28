package wlsn.programs.com.bts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Leon on 7/31/18.
 */

public class frag_quiz extends Fragment {
    ExpandableListView quiz_listings;
    ArrayList<String> buildings;
    HashMap<String, ArrayList<Object>> quizzes;
    ArrayList<Object> building_one;
    ArrayList<Object> building_two;
    ArrayList<Object> building_three;
    ArrayList<Object> building_four;
    int page_id = 1;
    user current_user;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.frag_quiz, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(R.string.nav_quizzes);

        current_user = user.getInstance();
        current_user.getData().trimQuizzes();

        quiz_listings = view.findViewById(R.id.quiz_building_list);
        buildings = new ArrayList<>();
        quizzes = new HashMap<>();

        building_one = new ArrayList<>();
        building_two = new ArrayList<>();
        building_three = new ArrayList<>();
        building_four = new ArrayList<>();

        for(int i = 1; i < 5; i++)
        {
            buildings.add("building" + String.valueOf(i));
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String user_key = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        DatabaseReference ref = database.getReference("users").child(user_key);


        for(int i = 0; i < current_user.getData().getQuizzes().length;i++)
        {
            quiz temp_quiz = current_user.getData().getQuizzes()[i];
            int building_number = temp_quiz.getBuildingNum();
            switch(building_number)
            {
                case 1:
                    building_one.add(temp_quiz);
                    break;
                case 2:
                    building_two.add(temp_quiz);
                    break;
                case 3:
                    building_three.add(temp_quiz);
                    break;
                case 4:
                    building_four.add(temp_quiz);
                    break;
                default:
                    break;
            }
        }

        for(int i = 0; i < buildings.size(); i++)
        {
            if(i == 0)
                quizzes.put(buildings.get(i),building_one);

            if(i == 1)
                quizzes.put(buildings.get(i),building_two);

            if(i == 2)
                quizzes.put(buildings.get(i),building_three);

            if(i == 3)
                quizzes.put(buildings.get(i),building_four);
        }



        if(current_user.getData().getQuizzes().length > 1)
        {
            for(int i = 0; i  < current_user.getData().getQuizzes().length; i++)
            {
                ref.child("quizzes").child(current_user.getData().getQuizzes()[i].getName()).child("name").setValue(current_user.getData().getQuizzes()[i].getName());
                ref.child("quizzes").child(current_user.getData().getQuizzes()[i].getName()).child("building_number").setValue(current_user.getData().getQuizzes()[i].getBuildingNum());
                ref.child("quizzes").child(current_user.getData().getQuizzes()[i].getName()).child("is_complete").setValue(current_user.getData().getQuizzes()[i].isComplete());
                ref.child("quizzes").child(current_user.getData().getQuizzes()[i].getName()).child("total_correct").setValue(current_user.getData().getQuizzes()[i].getTotal_correct());
                ref.child("quizzes").child(current_user.getData().getQuizzes()[i].getName()).child("previous_total").setValue(current_user.getData().getQuizzes()[i].getPrevious_total());
                ref.child("quizzes").child(current_user.getData().getQuizzes()[i].getName()).child("total_questions").setValue(current_user.getData().getQuizzes()[i].getTotal_questions());

                for(int j = 0; j < current_user.getData().getQuizzes()[i].getQuestions().length; j++)
                {
                    ref.child("quizzes").child(current_user.getData().getQuizzes()[i].getName()).child("questions").child("question_" + String.valueOf(j + 1)).child("type").setValue(current_user.getData().getQuizzes()[i].getQuestions()[j].getType());
                    ref.child("quizzes").child(current_user.getData().getQuizzes()[i].getName()).child("questions").child("question_" + String.valueOf(j + 1)).child("question_text").setValue(current_user.getData().getQuizzes()[i].getQuestions()[j].getQuestionText());
                    ref.child("quizzes").child(current_user.getData().getQuizzes()[i].getName()).child("questions").child("question_" + String.valueOf(j + 1)).child("already_correct").setValue(current_user.getData().getQuizzes()[i].getQuestions()[j].alreadyCorrect());

                    if(current_user.getData().getQuizzes()[i].getQuestions()[j].getType() != question_types.short_answer)
                    {
                        for (int k = 0; k < current_user.getData().getQuizzes()[i].getQuestions()[j].getAnswers().length; k++) {
                            ref.child("quizzes").child(current_user.getData().getQuizzes()[i].getName()).child("questions").child("question_" + String.valueOf(j + 1)).child("answers").child("answer_" + String.valueOf(k + 1)).child("text").setValue(current_user.getData().getQuizzes()[i].getQuestions()[j].getAnswers()[k].getAnswerText().toString());
                        }
                    }
                    else
                    {
                        ref.child("quizzes").child(current_user.getData().getQuizzes()[i].getName()).child("questions").child("question_" + String.valueOf(j + 1)).child("answers").child("answer").child("text").setValue("");
                    }
                }

            }
        }
        ExpandableListAdapter quiz_adapter = new frag_quiz_list(this.getContext(),buildings,quizzes);
        quiz_listings.setAdapter(quiz_adapter);
        quiz_listings.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                //Fragment fragment = quiz_view.getInstance(i, i1);
                if(current_user.getData().getQuizzes().length == 1)
                    return false;

                Intent intent = new Intent(getContext(),quiz_pop.class);
                intent.putExtra("page_id",1);
                intent.putExtra("building",i);
                intent.putExtra("quiz", i1);

//                if (fragment != null)
//                {
//                    FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
//                    trans.replace(R.id.content_frame,fragment);
//                    trans.commit();
//                }
                startActivity(intent);
                return true;
            }
        });
    }

    public void testing()
    {
        current_user = user.getInstance();
        current_user.getData().trimQuizzes();

        quiz_listings = view.findViewById(R.id.quiz_building_list);
        buildings = new ArrayList<>();
        quizzes = new HashMap<>();

        building_one = new ArrayList<>();
        building_two = new ArrayList<>();
        building_three = new ArrayList<>();
        building_four = new ArrayList<>();

        for(int i = 1; i < 5; i++)
        {
            buildings.add("building" + String.valueOf(i));
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String user_key = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        DatabaseReference ref = database.getReference("users").child(user_key);


        for(int i = 0; i < current_user.getData().getQuizzes().length;i++)
        {
            quiz temp_quiz = current_user.getData().getQuizzes()[i];
            int building_number = temp_quiz.getBuildingNum();
            switch(building_number)
            {
                case 1:
                    building_one.add(temp_quiz);
                    break;
                case 2:
                    building_two.add(temp_quiz);
                    break;
                case 3:
                    building_three.add(temp_quiz);
                    break;
                case 4:
                    building_four.add(temp_quiz);
                    break;
                default:
                    break;
            }
        }

        for(int i = 0; i < buildings.size(); i++)
        {
            if(i == 0)
                quizzes.put(buildings.get(i),building_one);

            if(i == 1)
                quizzes.put(buildings.get(i),building_two);

            if(i == 2)
                quizzes.put(buildings.get(i),building_three);

            if(i == 3)
                quizzes.put(buildings.get(i),building_four);
        }



        if(current_user.getData().getQuizzes().length > 1)
        {
            for(int i = 0; i  < current_user.getData().getQuizzes().length; i++)
            {
                ref.child("quizzes").child(current_user.getData().getQuizzes()[i].getName()).child("name").setValue(current_user.getData().getQuizzes()[i].getName());
                ref.child("quizzes").child(current_user.getData().getQuizzes()[i].getName()).child("building_number").setValue(current_user.getData().getQuizzes()[i].getBuildingNum());
                ref.child("quizzes").child(current_user.getData().getQuizzes()[i].getName()).child("is_complete").setValue(current_user.getData().getQuizzes()[i].isComplete());
                ref.child("quizzes").child(current_user.getData().getQuizzes()[i].getName()).child("total_correct").setValue(current_user.getData().getQuizzes()[i].getTotal_correct());
                ref.child("quizzes").child(current_user.getData().getQuizzes()[i].getName()).child("previous_total").setValue(current_user.getData().getQuizzes()[i].getPrevious_total());
                ref.child("quizzes").child(current_user.getData().getQuizzes()[i].getName()).child("total_questions").setValue(current_user.getData().getQuizzes()[i].getTotal_questions());

                for(int j = 0; j < current_user.getData().getQuizzes()[i].getQuestions().length; j++)
                {
                    ref.child("quizzes").child(current_user.getData().getQuizzes()[i].getName()).child("questions").child("question_" + String.valueOf(j + 1)).child("type").setValue(current_user.getData().getQuizzes()[i].getQuestions()[j].getType());
                    ref.child("quizzes").child(current_user.getData().getQuizzes()[i].getName()).child("questions").child("question_" + String.valueOf(j + 1)).child("question_text").setValue(current_user.getData().getQuizzes()[i].getQuestions()[j].getQuestionText());
                    ref.child("quizzes").child(current_user.getData().getQuizzes()[i].getName()).child("questions").child("question_" + String.valueOf(j + 1)).child("already_correct").setValue(current_user.getData().getQuizzes()[i].getQuestions()[j].alreadyCorrect());

                    if(current_user.getData().getQuizzes()[i].getQuestions()[j].getType() != question_types.short_answer)
                    {
                        for (int k = 0; k < current_user.getData().getQuizzes()[i].getQuestions()[j].getAnswers().length; k++) {
                            ref.child("quizzes").child(current_user.getData().getQuizzes()[i].getName()).child("questions").child("question_" + String.valueOf(j + 1)).child("answers").child("answer_" + String.valueOf(k + 1)).child("text").setValue(current_user.getData().getQuizzes()[i].getQuestions()[j].getAnswers()[k].getAnswerText().toString());
                        }
                    }
                    else
                    {
                        ref.child("quizzes").child(current_user.getData().getQuizzes()[i].getName()).child("questions").child("question_" + String.valueOf(j + 1)).child("answers").child("answer").child("text").setValue("");
                    }
                }

            }
        }
        ExpandableListAdapter quiz_adapter = new frag_quiz_list(this.getContext(),buildings,quizzes);
        quiz_listings.setAdapter(quiz_adapter);
        quiz_listings.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                //Fragment fragment = quiz_view.getInstance(i, i1);
                if(current_user.getData().getQuizzes().length == 1)
                    return false;

                Intent intent = new Intent(getContext(),quiz_pop.class);
                intent.putExtra("page_id",1);
                intent.putExtra("building",i);
                intent.putExtra("quiz", i1);

//                if (fragment != null)
//                {
//                    FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
//                    trans.replace(R.id.content_frame,fragment);
//                    trans.commit();
//                }
                startActivity(intent);
                return true;
            }
        });
    }
}
