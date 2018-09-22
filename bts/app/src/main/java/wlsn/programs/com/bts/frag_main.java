package wlsn.programs.com.bts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Leon on 7/31/18.
 */

public class frag_main extends Fragment {

    FirebaseAuth mAuth;
    FirebaseUser firebase_user;
    user current_user;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.frag_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Welcome");

        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);

        View nav_header = navigationView.getHeaderView(0);
        TextView user_name = nav_header.findViewById(R.id.nav_user_name);
        TextView user_level_name = nav_header.findViewById(R.id.nav_user_level_name);
        TextView current_level = nav_header.findViewById(R.id.nav_level);
        TextView next_level = nav_header.findViewById(R.id.nav_next_level);
        ProgressBar progressBar = nav_header.findViewById(R.id.nav_user_progress);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth != null)
        {
            firebase_user = mAuth.getCurrentUser();
            if(firebase_user != null) {
                current_user = user.getInstance(firebase_user.getUid());
            }
        }

        if(current_user != null)
        {
            progressBar.setProgress(current_user.getStats().current_points);
            progressBar.setMax(current_user.getStats().points_until_levelup);

            current_level.setText(String.valueOf(current_user.getStats().current_level));

            if(current_user.getStats().current_level < current_user.getStats().level_cap) {
                next_level.setText(String.valueOf(current_user.getStats().current_level + 1));
            }
            else
            {
                next_level.setText("MAX!");
            }

            user_level_name.setText(current_user.getStats().level_name);
        }
    }
}
