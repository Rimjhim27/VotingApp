package com.example.votingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewCandidates extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner sp1;
    ArrayAdapter<String> adapter;
    ImageAdapter adapt;
    String state;
    Candidate c1;
    RecyclerView rv;
    DatabaseReference myref1;
    Button cview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_candidates);
        sp1=(Spinner)findViewById(R.id.dropdown);
        rv=(RecyclerView)findViewById(R.id.rView);
        cview=(Button)findViewById(R.id.cView);
        c1= new Candidate();
        myref1= FirebaseDatabase.getInstance().getReference("Candidate");

        //Spinner Adapter
        adapter=new ArrayAdapter<String>(ViewCandidates.this,R.layout.spinner_item1,getResources().getStringArray(R.array.states));
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        sp1.setAdapter(adapter);
        sp1.setOnItemSelectedListener(ViewCandidates.this);
        //Spinner Adapter Over

        rv.setLayoutManager(new LinearLayoutManager(this));

        cview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseRecyclerOptions<Candidate> options =
                        new FirebaseRecyclerOptions.Builder<Candidate>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("Candidate"), Candidate.class)
                                .build();
                adapt = new ImageAdapter(options);
                adapt.startListening();
                rv.setAdapter(adapt);

            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        adapt.stopListening();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        state=adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
