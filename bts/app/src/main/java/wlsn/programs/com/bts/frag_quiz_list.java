package wlsn.programs.com.bts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Leon on 8/2/18.
 */

public class frag_quiz_list extends BaseExpandableListAdapter {
    Context context;
    ArrayList<String> buildings;
    HashMap<String,ArrayList<Object>> quizzes;

    public frag_quiz_list(Context context_, ArrayList<String> buildings_, HashMap<String,ArrayList<Object>> quizzes_)
    {
        context = context_;
        buildings = buildings_;
        quizzes = quizzes_;
    }

    @Override
    public int getGroupCount() {
        return buildings.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return quizzes.get(buildings.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return buildings.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return quizzes.get(buildings.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_building,null);
        }

        TextView group_text = view.findViewById(R.id.list_group);
        group_text.setText(String.valueOf(i + 1));

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        quiz current_quiz = (quiz) quizzes.get(buildings.get(i)).get(i1); //Can probably just us get child here
        if(view == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.quiz_list_building_child,null);
        }

        TextView quiz_name = view.findViewById(R.id.quiz_list_name);
        quiz_name.setText(current_quiz.getName());

        ProgressBar quiz_progress = view.findViewById(R.id.quiz_list_progress);
        quiz_progress.setProgress(current_quiz.total_correct);
        quiz_progress.setMax(current_quiz.total_questions);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
