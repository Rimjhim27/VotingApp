package com.example.votingapp;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.Candidate;
import com.example.votingapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

public class PartyAdapter extends FirebaseRecyclerAdapter<Candidate, PartyAdapter.PostViewHolder>{


    DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Candidate");

    public PartyAdapter(@NonNull FirebaseRecyclerOptions<Candidate> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Candidate model) {
        holder.name.setText(model.getName());
        holder.consti.setText(model.getConstituency());
        holder.party.setText(model.getParty());
        holder.link.setText(model.getFile());
        if(model.getImage_path()!=null){
            Log.d("IMAGE F",model.getImage_path());
        }
        Picasso.get().load(model.getImage_path()).into(holder.i);
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }
    public void deleteItem(int position) {
        String name = getItem(position).getName();
        Query myQuery = myref.orderByChild("name").equalTo(name);
        myQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    ds.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item, parent, false);

        return new PostViewHolder(view);

    }

    public class PostViewHolder extends RecyclerView.ViewHolder{

        TextView consti,party,link,name;
        ImageView i;
        RelativeLayout rl;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            consti = itemView.findViewById(R.id.consti);
            name = itemView.findViewById(R.id.name);
            party = itemView.findViewById(R.id.party);
            link = itemView.findViewById(R.id.link);
            i = itemView.findViewById(R.id.img);
            rl = itemView.findViewById(R.id.rl);
        }
    }
}