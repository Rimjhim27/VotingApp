package com.example.votingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.scottyab.aescrypt.AESCrypt;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class result extends AppCompatActivity {


    List<String> userid,uservote,decvotes;
    String key,val;
    TextView tv;
    Map<String, Integer> hm;
    String TAG = "Result";
    FirebaseDatabase database;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //Initializing arraylists and hashmap
        userid=new ArrayList<String>();
        uservote=new ArrayList<String>();
        decvotes=new ArrayList<String>();
        hm = new HashMap<String, Integer>();
        tv=findViewById(R.id.res);

        database  = FirebaseDatabase.getInstance();
        ref = database.getReference();
        ref.child("Votes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        userid.add(ds.child("userID").getValue().toString());
                        uservote.add(ds.child("vote").getValue().toString());
                    }
                    Log.d(TAG, userid.get(0));
                    Log.d(TAG, uservote.get(0));
                    for (String item : uservote) {
                        Integer j = hm.get(item);
                        hm.put(item, (j == null) ? 1 : j + 1);
                    }
                    Log.d(TAG, String.valueOf(hm.get("Rimjhim Singh")));
                    for(Map.Entry<String, Integer> val : hm.entrySet()){
                        tv.append(val.getKey()+" : "+val.getValue()+"\n");
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public String DecryptVote(String pass, String msg){
        String messageAfterDecrypt="";
        try {
            messageAfterDecrypt = AESCrypt.decrypt(pass,msg);
        }catch (GeneralSecurityException e){
            //handle error - could be due to incorrect password or tampered encryptedMsg
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),messageAfterDecrypt,Toast.LENGTH_SHORT).show();
        Log.d("DECRYPTED",messageAfterDecrypt);
        return messageAfterDecrypt;
    }
}
