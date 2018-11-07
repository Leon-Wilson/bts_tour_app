package wlsn.programs.com.bts;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class photos {
    HashMap<String, ArrayList<String>> photo_map;
    FirebaseDatabase database;

    static photos instance = null;

    public static photos getInstance() {
        if(instance == null)
        {
            instance = new photos();
        }
        return instance;
    }

    public photos()
    {
        photo_map = new HashMap<>();
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("images");


        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ArrayList<String> location_urls = new ArrayList<>();
                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    String url = data.getValue().toString();
                    location_urls.add(url);
                }

                String key = dataSnapshot.getKey();
                photo_map.put(key,location_urls);
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
        });
    }
}
