package com.example.votingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginOrRegister extends AppCompatActivity {

    TextView t1;
    TextView tname;
    TextView tpd;
    Button btn1;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);
        t1= (TextView)findViewById(R.id.t1);
        tname= (TextView)findViewById(R.id.tname);
        tpd= (TextView)findViewById(R.id.tpd);
        btn1=(Button)findViewById(R.id.btn1);
        mAuth=FirebaseAuth.getInstance();
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=tname.getText().toString();
                String password=tpd.getText().toString();
                mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(LoginOrRegister.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Logged In",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),biometric_verification.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}
