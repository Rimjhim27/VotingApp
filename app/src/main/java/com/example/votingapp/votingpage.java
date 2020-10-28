package com.example.votingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class votingpage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String v_cast,venc="";
    FirebaseAuth mAuth;
    String userid="",name;
    Spinner cast;


    List<String> candidateName;
    final String TAG = "DHYAN SE DEKHO IISEE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votingpage);
        //Spinner for candidates
        cast=(Spinner)findViewById(R.id.dropdown);
        candidateName = new ArrayList<String>();

        //Firebase to adapter
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference fDatabaseRoot = database.getReference();
        fDatabaseRoot.child("Candidate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    name = ds.child("name").getValue().toString();
                        candidateName.add(name);
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(votingpage.this,R.layout.spinner_item1,candidateName);
                adapter.setDropDownViewResource(R.layout.spinner_layout);
                adapter.notifyDataSetChanged();
                cast.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Spinner adapter getResources().getStringArray(R.array.candidates)

        cast.setOnItemSelectedListener(this);
        //To store votes in the database
        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentFirebaseUser = mAuth.getInstance().getCurrentUser() ;
                Vote v=new Vote();
                TextView t1= findViewById(R.id.t1);
                userid=currentFirebaseUser.getUid();
                v.setVote(v_cast);
                v.setUserID(currentFirebaseUser.getUid());
                DatabaseReference mDbRef = FirebaseDatabase.getInstance().getReference().child("Votes");
                t1.setText(v_cast);
                mDbRef.child(currentFirebaseUser.getUid()).setValue(v);
                //startActivity(new Intent(getApplicationContext(),ThankYou.class));
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        v_cast=adapterView.getItemAtPosition(i).toString();
        String encryptedMsg="";
        try {
            encryptedMsg  = AESCrypt.encrypt(userid, v_cast);
        }catch (GeneralSecurityException e){
            //handle error
            e.printStackTrace();
        }
        venc=encryptedMsg;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
