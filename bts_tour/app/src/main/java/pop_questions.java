import android.content.Context;
import android.graphics.Typeface;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fs.sdvbs.bts_tour.R;

/**
 * Created by Leon on 7/14/18.
 */

public class pop_questions extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<String> questions;
    private HashMap<String,List<String>> responses;

    public pop_questions(Context context_, ArrayList<String> questions_, HashMap<String,List<String>>  responses_)
    {
        context = context_;
        questions = questions_;
        responses = responses_;
    }

    @Override
    public int getGroupCount() {
        return questions.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return responses.get(questions.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return questions.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
       return responses.get(questions.get(i)).get(i1);
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
    public View getGroupView(int i, boolean isExpanded, View view, ViewGroup viewGroup) {
        String question_text = (String) getGroup(i);
        if(view == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.pop_questions_group,null);
        }
        TextView textView = (TextView) view.findViewById(R.id.pop_group_text);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(question_text);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean isLastChild, View view, ViewGroup viewGroup) {
        final String expandedListText = (String) getChild(i,i1);

        if(view == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.pop_questions_child_row,null);
        }
        TextView textView = (TextView) view.findViewById(R.id.pop_child_text);
        textView.setText(expandedListText);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
