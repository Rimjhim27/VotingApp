package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PoliticalParty extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner sp2;
    ArrayAdapter<String> adapter;
    PartyAdapter adapt;
    String state;
    Candidate c2;
    RecyclerView rv;
    DatabaseReference myref1;
    Button cview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_political_party);
        sp2=(Spinner)findViewById(R.id.dropdown);
        rv=(RecyclerView)findViewById(R.id.rView1);
        cview=(Button)findViewById(R.id.pView);
        c2= new Candidate();
        myref1= FirebaseDatabase.getInstance().getReference("Candidate");

        //Spinner Adapter
        adapter=new ArrayAdapter<String>(PoliticalParty.this,R.layout.spinner_item1,getResources().getStringArray(R.array.states));
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        sp2.setAdapter(adapter);
        sp2.setOnItemSelectedListener(PoliticalParty.this);
        //Spinner Adapter Over

        rv.setLayoutManager(new LinearLayoutManager(this));

        cview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseRecyclerOptions<Candidate> options =
                        new FirebaseRecyclerOptions.Builder<Candidate>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("Candidate"), Candidate.class)
                                .build();
                adapt = new PartyAdapter(options);
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

