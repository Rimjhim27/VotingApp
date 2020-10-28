package com.example.votingapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String v_state;
    FirebaseAuth mAuth;
    EditText n;
    EditText uid;
    EditText eid;
    EditText birth;
    EditText pd;
    EditText pC;
    String name;
    String voterid;
    String pword="";
    String pwordC;
    String emailid="";
    String dob;
    ArrayAdapter<String> adapter;
    Spinner state;
    User user;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        n=(EditText)findViewById(R.id.name);
        uid=(EditText)findViewById(R.id.voterid);
        eid=(EditText)findViewById(R.id.emailid);
        birth=(EditText)findViewById(R.id.birth);
        pd=(EditText)findViewById(R.id.password);
        pC=(EditText)findViewById(R.id.passwordC);
        mAuth=FirebaseAuth.getInstance();
        user=new User();

        Button btn1=(Button)findViewById(R.id.register);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=n.getText().toString();
                voterid=uid.getText().toString();
                pword=pd.getText().toString();
                pwordC=pC.getText().toString();
                emailid=eid.getText().toString();
                dob=birth.getText().toString();

                fUser=mAuth.getCurrentUser();
                user.setUserId(fUser.getUid());

                Voters vote=new Voters();
                vote.setName(name);
                vote.setEmailId(emailid);
                vote.setVoterId(voterid);
                vote.setDOB(dob);
                vote.setState(v_state);
                DatabaseReference mDbRef = FirebaseDatabase.getInstance().getReference();
                mDbRef.child("Users").child(fUser.getUid()).setValue(user);
                mDbRef.child("Voters").child(voterid).setValue(vote);

                mAuth.createUserWithEmailAndPassword(emailid,pword).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Intent i = new Intent(MainActivity.this,LoginOrRegister.class);
                startActivity(i);

            }
        });
        TextView t1=(TextView)findViewById(R.id.t1);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginOrRegister.class));
            }
        });
        state=(Spinner)findViewById(R.id.dropdown);
        adapter=new ArrayAdapter<String>(MainActivity.this,R.layout.spinner_item1,getResources().getStringArray(R.array.states));
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        state.setAdapter(adapter);
        state.setOnItemSelectedListener(MainActivity.this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        v_state=adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
