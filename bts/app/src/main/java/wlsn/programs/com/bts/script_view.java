package wlsn.programs.com.bts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;

/**
 * Created by Leon on 8/1/18.
 */

public class script_view extends Fragment {

    user current_user;
    private StorageReference mStorageRef;
    photos mPhotos;


    public static script_view getInstance(int building, int script)
    {
        script_view single_script = new script_view();
        Bundle args = new Bundle();
        args.putInt("building",building);
        args.putInt("script",script);
        single_script.setArguments(args);
        return single_script;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.script_view, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        current_user = user.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mPhotos = photos.getInstance();

        int building = getArguments().getInt("building");
        int script_num = getArguments().getInt("script");
        int starting_point = 0;

        //TODO: FIND BETTER SOLUTION (or at least make data ordered)

                starting_point = current_user.getData().getStartingPos(building,true);

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.script_images);
        script current_script = current_user.getData().getScripts()[starting_point + script_num];

        if(mPhotos.photo_map.containsKey(current_script.getKey()))
        {
            //Toast.makeText(getContext(), "FOUND THE IMAGES", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < mPhotos.photo_map.get(current_script.getKey()).size(); i++) {
                ImageView image = new ImageView(this.getContext());

                new DownloadImageTask(image).execute(mPhotos.photo_map.get(current_script.getKey()).get(i));
                image.setLayoutParams(new android.view.ViewGroup.LayoutParams(1000, ViewGroup.LayoutParams.WRAP_CONTENT));
                //image.setImageResource(R.drawable.fs_logo);
                image.setScaleType(ImageView.ScaleType.FIT_XY);
                image.setPadding(10,0,10,0);
                layout.addView(image);
            }
        }
        else
        {
            Toast.makeText(getContext(), "NO IMAGES FOUND", Toast.LENGTH_SHORT).show();
        }
        //script current_script = current_user.getData().getScripts()[starting_point + script_num];
        TextView script_name = view.findViewById(R.id.script_view_name);
        TextView script_des = view.findViewById(R.id.script_view_description);
        ListView script_important = view.findViewById(R.id.script_view_importants);

        script_name.setText(current_script.getName());
        script_des.setText(current_script.getDescription());

        String[] important_things = new String[current_script.getImportance().length];

        for(int i = 0; i < important_things.length;i++)
        {
            important_things[i] = current_script.getImportance()[i];
        }

        ArrayAdapter<String> important_adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, important_things);
        script_important.setAdapter(important_adapter);

        getActivity().setTitle("Building " + String.valueOf(current_script.getBuildingNum()));

    }

    /*public class DownloadImageTask extends AsyncTask<String, Void, Bitmap>
    {
        ImageView bmImage;

        public DownloadImageTask(ImageView _bmImage)
        {
            bmImage = _bmImage;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String urldisplay = url[0];
            Bitmap mIcon11 = null;
            try
            {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            }catch(Exception e)
            {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return mIcon11;
        }

        protected void onPostExecute(Bitmap result)
        {
            bmImage.setImageBitmap(result);
        }
    }*/

}

