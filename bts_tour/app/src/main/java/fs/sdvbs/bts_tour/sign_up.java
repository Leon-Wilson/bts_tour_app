package fs.sdvbs.bts_tour;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class sign_up extends AppCompatActivity {

    FirebaseUser current_user;
    FirebaseAuth mAuth;

    user_json defaults;
    DatabaseReference user;
    EditText fname;
    EditText lname;
    EditText email;
    EditText pass;
    Button signup;
    String uid;

    ArrayList<quiz> quizlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        uid = "001";

        fname = (EditText) findViewById(R.id.sign_up_fname);
        lname = (EditText) findViewById(R.id.sign_up_lname);
        email = (EditText) findViewById(R.id.sign_up_email);
        pass = (EditText) findViewById(R.id.sign_up_pass);
        Button signup = (Button) findViewById(R.id.sign_up_button);

        user = FirebaseDatabase.getInstance().getReference("users");

        defaults = new user_json();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewUser();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void addNewUser()
    {
        //add the current user to database
        /*
        * THINGS TO ADD:
        * first name
        * last name
        * email
        * building sets (scripts & quizzes)
        * */
        player temp = new player();

        HashMap<String, quiz> quiz_hash = new HashMap<String, quiz>();
        int i = 0;
        while(defaults.getQuizzes()[i] != null)
        {
            quiz_hash.put(defaults.getQuizzes()[i].quiz_name,defaults.getQuizzes()[i]);
            i++;
        }

        quizlist = new ArrayList<quiz>(Arrays.asList(defaults.getQuizzes()));
        if(user != null)
        {
            user.child(uid).child("fname").setValue(fname.getText().toString());
            user.child(uid).child("lname").setValue(lname.getText().toString());
            user.child(uid).child("email").setValue(email.getText().toString());
            user.child(uid).child(temp.player_name).setValue(temp);
            user.child(uid).child("quizzes").setValue(quiz_hash);

        }
    }

}
