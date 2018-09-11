package wlsn.programs.com.bts;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Leon on 8/7/18.
 */

public class leveling_system {

    public static leveling_system instance = null;
    int current_level;
    int level_cap;
    String level_name = "Ground Crew";

    int current_points;
    int points_until_levelup;

    //TODO: Create test cases for level up system
    public leveling_system()
    {
        //TODO: Add level names
        current_level = 1;
        level_cap = 5;
        current_points = 0;
        points_until_levelup = 7;
        //setLevelName();
    }

    public leveling_system(String user_key)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users").child(user_key).child("progress");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                switch (dataSnapshot.getKey())
                {
                    case "current_points":
                        current_points = Integer.valueOf(dataSnapshot.getValue().toString());
                        break;
                    case "current_level":
                        current_level = Integer.valueOf(dataSnapshot.getValue().toString());
                        break;
                    case "level_cap":
                        level_cap= Integer.valueOf(dataSnapshot.getValue().toString());
                        break;
                    case "level_name":
                        level_name = dataSnapshot.getValue().toString();
                        break;
                    case "points_until_levelup":
                        points_until_levelup = Integer.valueOf(dataSnapshot.getValue().toString());
                        break;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static leveling_system getInstance()
    {
        if(instance == null)
        {
            instance = new leveling_system();
        }
        return instance;
    }

    public static leveling_system getInstance(String user_key)
    {
        if(instance == null)
        {
            instance = new leveling_system(user_key);
        }
        return instance;
    }

    public boolean LEVELUP()
    {
        if(current_points >= points_until_levelup)
        {
            if(current_level < level_cap)
            {
                current_level++;
                setLevelName();
            }
            current_points = 0;
            points_until_levelup += 2;
            return true;
        }
        return false;
    }

    public int getCurrent_level() {
        return current_level;
    }

    public void setCurrentLevel(int level)
    {

    }

    public int getCurrent_points() {
        return current_points;
    }

    public void setCurrentPoints(int level)
    {

    }

    public int getLevel_cap() {
        return level_cap;
    }

    public void setLevelCap(int level)
    {

    }

    public int getPoints_until_levelup() {
        return points_until_levelup;
    }

    public void setPointsUntilLevelup(int level)
    {

    }

    public String getlevel_name() {
        return level_name;
    }

    public void setLevelName()
    {
        switch(current_level)
        {
            case 1:
                if(current_points > 0)
                {
                    level_name = "Nugget";
                }
                break;
            case 2:
                level_name = "Flight Attendant";
                break;
            case 3:
                level_name = "Wingman";
                break;
            case 4:
                level_name = "Co-Pilot";
                break;
            case 5:
                if(current_points > points_until_levelup)
                {
                    level_name = "Air-Boss";
                }
                else
                {
                    level_name = "Pilot";
                }
                break;
        }


    }
}
