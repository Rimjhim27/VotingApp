package com.example.votingapp;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class votingpage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String v_cast,venc;
    FirebaseAuth mAuth;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votingpage);
        Spinner cast=(Spinner)findViewById(R.id.dropdown);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(votingpage.this,R.layout.spinner_item1,getResources().getStringArray(R.array.candidates));
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        cast.setAdapter(adapter);
        cast.setOnItemSelectedListener(this);
        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView t1=(TextView)findViewById(R.id.t1);
                t1.setText(v_cast);
                FirebaseUser currentFirebaseUser = mAuth.getInstance().getCurrentUser() ;
                Vote v=new Vote();
                userid=currentFirebaseUser.getUid();
                v.setVote(venc);
                v.setUserID(currentFirebaseUser.getUid());
                DatabaseReference mDbRef = FirebaseDatabase.getInstance().getReference().child("Votes");
                mDbRef.child(currentFirebaseUser.getUid()).setValue(v);

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        v_cast=adapterView.getItemAtPosition(i).toString();
        String keyStr =userid;
        String ivStr = userid;
        String ansBase64="";
        try {
            ansBase64 = votingpage.encryptStrAndToBase64(ivStr, keyStr, v_cast);
        } catch (Exception e) {
            e.printStackTrace();
        }
        venc=ansBase64;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public static byte[] encrypt(String ivStr, String keyStr, byte[] bytes) throws Exception{
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(ivStr.getBytes());
        byte[] ivBytes = md.digest();

        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        sha.update(keyStr.getBytes());
        byte[] keyBytes = sha.digest();

        return encrypt(ivBytes, keyBytes, bytes);
    }

    static byte[] encrypt(byte[] ivBytes, byte[] keyBytes, byte[] bytes) throws Exception{
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
        return cipher.doFinal(bytes);
    }

    public static String encryptStrAndToBase64(String ivStr, String keyStr, String enStr) throws Exception{
        byte[] bytes = encrypt(keyStr, keyStr, enStr.getBytes("UTF-8"));
        return new String(Base64.encode(bytes ,Base64.DEFAULT), "UTF-8");
    }
}
