package wlsn.programs.com.bts;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Leon on 8/13/18.
 */

public class frag_sign_up extends Fragment{
    FirebaseAuth mAuth;

    EditText fname,lname,email,pass,conf;
    Button submit;
    TextView error;

    String user_fname,
    user_lname,
    user_email,
    user_pass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.frag_sign_up, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        fname = view.findViewById(R.id.sign_up_fname);
        lname = view.findViewById(R.id.sign_up_lname);
        email = view.findViewById(R.id.sign_up_email);
        pass = view.findViewById(R.id.sign_up_pass);
        conf = view.findViewById(R.id.sign_up_confirm);
        submit = view.findViewById(R.id.submit_new_user);
        error = view.findViewById(R.id.sign_up_error);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder error_string = new StringBuilder("");
                //String error_string = "";
                if(fname.getText().toString().isEmpty())
                {
                    error_string.append("Please enter your first name");
                }
                else
                {
                    user_fname = fname.getText().toString();
                }

                if(lname.getText().toString().isEmpty())
                {
                    if(error_string.length() != 0)
                    {
                        error_string.append("\n");
                    }
                    error_string.append("Please enter your last name");
                }
                else
                {
                    user_lname = lname.getText().toString();
                }

                if(email.getText().toString().isEmpty())
                {
                    if(error_string.length() != 0)
                    {
                        error_string.append("\n");
                    }
                    error_string.append("Please enter your email");
                }
                else
                {
                    user_email = email.getText().toString();
                }

                if(conf.getText().toString().isEmpty())
                {
                    if(error_string.length() != 0)
                    {
                        error_string.append("\n");
                    }
                    error_string.append("Please confirm your password");
                }
                else
                {
                    if(!pass.getText().toString().isEmpty())
                    {
                        if(!conf.getText().toString().equals(pass.getText().toString()))
                        {
                            if(error_string.length() != 0)
                            {
                                error_string.append("\n");
                            }
                            error_string.append("Mismatch passwords");
                        }
                        else
                        {
                            user_pass = pass.getText().toString();
                        }
                    }
                    else
                    {
                        if(error_string.length() != 0)
                        {
                            error_string.append("\n");
                        }
                        error_string.append("Please enter your password");
                    }
                }

                if (error_string.length() != 0)
                {
                    error.setVisibility(View.VISIBLE);
                    error.setText(error_string.toString());
                }
                else
                {
                    error.setVisibility(View.INVISIBLE);
                    mAuth.createUserWithEmailAndPassword(user_email,user_pass)
                            .addOnCompleteListener(getActivity(),new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        //change to main screen
                                        FirebaseUser fUser = mAuth.getCurrentUser();
                                        if(fUser != null)
                                        {
                                            UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setDisplayName(user_fname).build();
                                            fUser.updateProfile(request);

                                        }

                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference ref = database.getReference("users");
                                        ref.child(fUser.getUid()).child("f_name").setValue(user_fname);
                                        ref.child(fUser.getUid()).child("l_name").setValue(user_lname);
                                        ref.child(fUser.getUid()).child("email").setValue(user_email);
                                        ref.child(fUser.getUid()).child("progress").setValue(new leveling_system());
                                        user current_user = user.getInstance(fUser.getUid());

                                        Fragment fragment = new frag_main();
                                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                                                InputMethodManager.HIDE_NOT_ALWAYS);
                                        if(fragment != null) {
                                            FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
                                            trans.replace(R.id.content_frame, fragment);
                                            trans.commit();
                                        }
                                        Toast.makeText(getContext(), "Welcome!", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(getContext(), "Sorry, something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
