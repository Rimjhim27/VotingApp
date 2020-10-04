package com.example.votingapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class ImageAdapter extends FirebaseRecyclerAdapter<Candidate, ImageAdapter.PostViewHolder>{


    public ImageAdapter(@NonNull FirebaseRecyclerOptions<Candidate> options) {
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
            consti=itemView.findViewById(R.id.consti);
            name=itemView.findViewById(R.id.name);
            party=itemView.findViewById(R.id.party);
            link=itemView.findViewById(R.id.link);
            i=itemView.findViewById(R.id.img);
            rl=itemView.findViewById(R.id.rl);
        }
    }
}