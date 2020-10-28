package com.example.votingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class PoliticalParty extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
        rv=(RecyclerView)findViewById(R.id.rView1);
        c2= new Candidate();
        myref1= FirebaseDatabase.getInstance().getReference("Candidate");

        rv.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Candidate> options =
                        new FirebaseRecyclerOptions.Builder<Candidate>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("Candidate"), Candidate.class)
                                .build();
                adapt = new PartyAdapter(options);
                adapt.startListening();
                rv.setAdapter(adapt);
                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        adapt.deleteItem(viewHolder.getAdapterPosition());
                        Toast.makeText(PoliticalParty.this,"Deleted",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                                .addBackgroundColor(ContextCompat.getColor(PoliticalParty.this, R.color.imperialRed))
                                .addActionIcon(R.drawable.ic_delete_black_24dp)
                                .create()
                                .decorate();

                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                }).attachToRecyclerView(rv);
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
