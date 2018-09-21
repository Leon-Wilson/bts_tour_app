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

public class frag_contact_list extends BaseExpandableListAdapter{

    Context context;
    ArrayList<String> type;
    HashMap<String,ArrayList<Object>> contacts;

    public frag_contact_list(Context context_, ArrayList<String> _type, HashMap<String,ArrayList<Object>> _contacts)
    {
        context = context_;
        type = _type;
        contacts = _contacts;
    }
    @Override
    public int getGroupCount() {
        return type.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return contacts.get(type.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return type.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return contacts.get(type.get(i)).get(i1);
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
        group_text.setText(type.get(i));

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        contact current_contact = (contact) contacts.get(type.get(i)).get(i1); //Can probably just us get child here
        if(view == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.contact_list_child,null);
        }

        TextView contact_name = view.findViewById(R.id.contact_name);
        TextView contact_degree = view.findViewById(R.id.contact_degree);
        TextView contact_email = view.findViewById(R.id.contact_email);
        TextView contact_phone = view.findViewById(R.id.contact_phone);

        contact_name.setText(current_contact.name);
        contact_degree.setText(current_contact.degree);
        contact_email.setText(current_contact.email);
        contact_phone.setText(current_contact.phone);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
