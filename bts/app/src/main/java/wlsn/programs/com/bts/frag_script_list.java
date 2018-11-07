package wlsn.programs.com.bts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Leon on 7/31/18.
 */

public class frag_script_list extends BaseExpandableListAdapter {
    Context context;
    ArrayList<String> buildings;
    HashMap<String,ArrayList<Object>> scripts;
    photos mPhotos;

    public frag_script_list(Context context_, ArrayList<String> buildings_, HashMap<String,ArrayList<Object>> scripts_)
    {
        context = context_;
        buildings = buildings_;
        scripts = scripts_;
    }

    @Override
    public int getGroupCount() {
        return buildings.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return scripts.get(buildings.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return buildings.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return scripts.get(buildings.get(i)).get(i1);
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
        script current_script = (script) scripts.get(buildings.get(i)).get(i1);
        if(view == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.script_list_building_child,null);
        }
        /*mPhotos = photos.getInstance();
        if(mPhotos.photo_map.containsKey(current_script.getKey()) && !current_script.script_list_image_set) {
            ImageView script_image = view.findViewById(R.id.script_list_image);
            new DownloadImageTask(script_image).execute(mPhotos.photo_map.get(current_script.getKey()).get(0));
            script_image.setScaleType(ImageView.ScaleType.FIT_XY);
            current_script.script_list_image_set = true;
        }*/
        TextView script_name = view.findViewById(R.id.script_list_name);
        script_name.setText(current_script.getName());

        TextView script_description = view.findViewById(R.id.script_list_description);
        script_description.setText(current_script.description);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
