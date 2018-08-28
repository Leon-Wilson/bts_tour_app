package wlsn.programs.com.bts;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Leon on 7/31/18.
 */

public class user {
    FirebaseAuth mAuth;
    private static user instance = null;
    public static user getInstance()
    {
        if(instance == null)
        {
            instance = new user();
        }
        return instance;
    }

    public static user getInstance(String user_key)
    {
        instance = new user(user_key);
        return instance;
    }
    private user_data data;

    private leveling_system stats;

    private String name;

    //private quiz[] quizzes

    public user ()
    {
        name = null;
        data = user_data.getInstance();
        stats = leveling_system.getInstance();
    }

    public user(String user_key)
    {
        mAuth = FirebaseAuth.getInstance();
        name = mAuth.getCurrentUser().getDisplayName();
        data = user_data.getInstance(user_key);
        stats = leveling_system.getInstance(user_key);
    }

    public String getName()
    {
        return name;
    }
    public user_data getData()
    {
        return data;
    }

    public leveling_system getStats()
    {
        return stats;
    }
}
