package wlsn.programs.com.bts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Leon on 7/31/18.
 */

public class frag_script extends Fragment {
    ExpandableListView script_listings;
    ArrayList<String> buildings;
    HashMap<String, ArrayList<Object>> scripts;
    ArrayList<Object> building_one;
    ArrayList<Object> building_two;
    ArrayList<Object> building_three;
    ArrayList<Object> building_four;

    user current_user;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.frag_script, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(R.string.nav_script);

        current_user = user.getInstance();
        current_user.getData().trimScripts();

        script_listings = view.findViewById(R.id.script_building_list);
        buildings = new ArrayList<>();
        scripts = new HashMap<>();

        building_one = new ArrayList<>();
        building_two = new ArrayList<>();
        building_three = new ArrayList<>();
        building_four = new ArrayList<>();

        for(int i = 1; i < 5; i++)
        {
           buildings.add("building" + String.valueOf(i));
        }
        if (current_user.getData().getScripts().length == 1)
        {
            current_user.getData().Load();
        }
        for(int i = 0; i < current_user.getData().getScripts().length;i++)
        {
            script temp_script = current_user.getData().getScripts()[i];
            int building_number = temp_script.getBuildingNum();
            switch(building_number)
            {
                case 1:
                    building_one.add(temp_script);
                    break;
                case 2:
                    building_two.add(temp_script);
                    break;
                case 3:
                    building_three.add(temp_script);
                    break;
                case 4:
                    building_four.add(temp_script);
                    break;
                default:
                    break;
            }
        }


        for(int i = 0; i < buildings.size(); i++)
        {
            if(i == 0)
                scripts.put(buildings.get(i),building_one);

            if(i == 1)
                scripts.put(buildings.get(i),building_two);

            if(i == 2)
                scripts.put(buildings.get(i),building_three);

            if(i == 3)
                scripts.put(buildings.get(i),building_four);
        }

        ExpandableListAdapter script_adapter = new frag_script_list(this.getContext(),buildings,scripts);
        script_listings.setAdapter(script_adapter);
        script_listings.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Fragment fragment = script_view.getInstance(i, i1);

                if (fragment != null)
                {
                    FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
                    trans.replace(R.id.content_frame,fragment);
                    trans.commit();
                }
                return true;
            }
        });
    }
}
