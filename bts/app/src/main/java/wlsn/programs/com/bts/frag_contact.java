package wlsn.programs.com.bts;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class frag_contact extends android.support.v4.app.Fragment {
    private FirebaseDatabase fDatabase;
    DatabaseReference databaseReference;
    ExpandableListView contact_list;
    ArrayList<String> types;
    HashMap <String, ArrayList<Object>> contacts;
    ArrayList<Object> full_timerss;
    ArrayList<Object> managerss;
    ArrayList<Object> studentss;
    ArrayList<Object> traineess;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.frag_contact, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Contacts");

        contact_list = view.findViewById(R.id.contact_list);
        types = new ArrayList<>();
        contacts = new HashMap<>();

        full_timerss = new ArrayList<>();
        managerss = new ArrayList<>();
        studentss = new ArrayList<>();
        traineess = new ArrayList<>();

        types.add("Full Time");
        types.add("Students");
        types.add("Trainees");
        types.add("Management");

        fDatabase = FirebaseDatabase.getInstance();

        databaseReference = fDatabase.getReference("contacts");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    if(data.getKey().contains("full_time"))
                    {
                        String fname = "";
                        String lname = "";
                        String degree = "";
                        String email = "";
                        String phone = "";

                        for(DataSnapshot full_timer : data.getChildren())
                        {


                            if(full_timer.getKey().contentEquals("degree"))
                            {
                                degree = full_timer.getValue().toString();
                            }
                            if(full_timer.getKey().contentEquals("email"))
                            {
                                email = full_timer.getValue().toString();

                            }
                            if(full_timer.getKey().contentEquals("first_name"))
                            {
                                fname = full_timer.getValue().toString();

                            }
                            if(full_timer.getKey().contentEquals("last_name"))
                            {
                                lname = full_timer.getValue().toString();

                            }
                            if(full_timer.getKey().contentEquals("phone"))
                            {
                                phone = full_timer.getValue().toString();

                            }

                            if((fname != "") && (degree!= "") && (lname!= "") && (email!= "") && (phone!= ""))
                            {
                                String name = fname + " " + lname;
                                contact temp = new contact(name, degree, phone, email);
                                full_timerss.add(temp);
                            }
                        }
                    }
                    if(data.getKey().contains("management"))
                    {
                        String fname = "";
                        String lname = "";
                        String degree = "";
                        String email = "";
                        String phone = "";

                        for(DataSnapshot managers : data.getChildren())
                        {

                            if(managers.getKey().contentEquals("degree"))
                            {
                                degree = managers.getValue().toString();
                            }
                            if(managers.getKey().contentEquals("email"))
                            {
                                email = managers.getValue().toString();
                            }
                            if(managers.getKey().contentEquals("first_name"))
                            {
                                fname = managers.getValue().toString();
                            }
                            if(managers.getKey().contentEquals("last_name"))
                            {
                                lname = managers.getValue().toString();
                            }
                            if(managers.getKey().contentEquals("phone"))
                            {
                                phone = managers.getValue().toString();
                            }

                            if((fname != "") && (degree!= "") && (lname!= "") && (email!= "") && (phone!= ""))
                            {
                                String name = fname + " " + lname;
                                contact temp = new contact(name, degree, phone, email);
                                managerss.add(temp);
                            }
                        }
                    }
                    if(data.getKey().contains("student"))
                    {
                        String fname = "";
                        String lname = "";
                        String degree = "";
                        String email = "";
                        String phone = "";

                        for(DataSnapshot students : data.getChildren())
                        {

                            if(students.getKey().contentEquals("degree"))
                            {
                                degree = students.getValue().toString();

                            }
                            if(students.getKey().contentEquals("email"))
                            {
                                email = students.getValue().toString();

                            }
                            if(students.getKey().contentEquals("first_name"))
                            {
                                fname = students.getValue().toString();

                            }
                            if(students.getKey().contentEquals("last_name"))
                            {
                                lname = students.getValue().toString();

                            }
                            if(students.getKey().contentEquals("phone"))
                            {
                                phone = students.getValue().toString();

                            }

                            if((fname != "") && (degree!= "") && (lname!= "") && (email!= "") && (phone!= ""))
                            {
                                String name = fname + " " + lname;
                                contact temp = new contact(name, degree, phone, email);
                                studentss.add(temp);
                            }
                        }
                    }
                    if(data.getKey().contains("trainee"))
                    {
                        String fname = "";
                        String lname = "";
                        String degree = "";
                        String email = "";
                        String phone = "";

                        for(DataSnapshot trainees : data.getChildren())
                        {

                            if(trainees.getKey().contentEquals("degree"))
                            {
                                degree = trainees.getValue().toString();

                            }
                            if(trainees.getKey().contentEquals("email"))
                            {
                                email = trainees.getValue().toString();

                            }
                            if(trainees.getKey().contentEquals("first_name"))
                            {
                                fname = trainees.getValue().toString();

                            }
                            if(trainees.getKey().contentEquals("last_name"))
                            {
                                lname = trainees.getValue().toString();

                            }
                            if(trainees.getKey().contentEquals("phone"))
                            {
                                phone = trainees.getValue().toString();

                            }

                            if((fname != "") && (degree!= "") && (lname!= "") && (email!= "") && (phone!= ""))
                            {
                                String name = fname + " " + lname;
                                contact temp = new contact(name, degree, phone, email);
                                traineess.add(temp);
                            }
                        }
                    }
                }
                if(contacts.size() == 0) {
                    for (int i = 0; i < types.size(); i++) {
                        if (types.get(i).contentEquals("Full Time")) {
                            contacts.put(types.get(i), full_timerss);
                        }
                        if (types.get(i).contentEquals("Students")) {
                            contacts.put(types.get(i), studentss);
                        }
                        if (types.get(i).contentEquals("Trainees")) {
                            contacts.put(types.get(i), traineess);
                        }
                        if (types.get(i).contentEquals("Management")) {
                            contacts.put(types.get(i), managerss);
                        }
                    }
                }
                ExpandableListAdapter contact_adapter = new frag_contact_list(getContext(),types,contacts);

                contact_list.setAdapter(contact_adapter);

                contact_list.expandGroup(0);
                contact_list.expandGroup(1);
                contact_list.expandGroup(2);
                contact_list.expandGroup(3);
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

