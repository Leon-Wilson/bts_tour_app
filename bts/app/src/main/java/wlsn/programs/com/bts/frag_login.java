package wlsn.programs.com.bts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import org.w3c.dom.Text;

/**
 * Created by Leon on 8/13/18.
 */

public class frag_login extends Fragment{

    FirebaseAuth mAuth;
    EditText email,pass;
    Button sign_in_button;
    TextView sign_up,login_error;
    String user_email, user_pass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.frag_login, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        login_error = view.findViewById(R.id.login_error);
        email = view.findViewById(R.id.email);
        pass = view.findViewById(R.id.password);

        sign_in_button = view.findViewById(R.id.sign_in_button);
        sign_up = view.findViewById(R.id.sign_up);

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder error_string = new StringBuilder("");

                if(email.getText().toString().isEmpty())
                {
                    error_string.append("Please enter your email");
                }
                else
                {
                    user_email = email.getText().toString();
                }
                if(pass.getText().toString().isEmpty())
                {
                    if(error_string.length() != 0)
                    {
                        error_string.append("\n");
                    }
                    error_string.append("Please enter your password");
                }
                else
                {
                    user_pass = pass.getText().toString();
                }

                if(error_string.length() != 0)
                {
                    login_error.setVisibility(View.VISIBLE);
                    login_error.setText(error_string.toString());
                }
                else
                {
                    login_error.setVisibility(View.INVISIBLE);
                    //ATTEMPT LOG IN
                    mAuth.signInWithEmailAndPassword(user_email,user_pass)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {
                                        user current_user = user.getInstance(mAuth.getCurrentUser().getUid());
                                        Fragment fragment = new frag_main();
                                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                                                InputMethodManager.HIDE_NOT_ALWAYS);
                                        if(fragment != null) {
                                            FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
                                            trans.replace(R.id.content_frame, fragment);
                                            trans.commit();
                                        }
                                        Toast.makeText(getContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(getContext(), "FAILURE", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new frag_sign_up();

                if(fragment != null) {
                    FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
                    trans.replace(R.id.content_frame, fragment);
                    trans.commit();
                }

            }
        });
    }
}
