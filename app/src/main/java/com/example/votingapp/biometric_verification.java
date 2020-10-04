package com.example.votingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.biometric.BiometricPrompt;
import java.util.concurrent.Executor;

public class biometric_verification extends AppCompatActivity {

    Executor executor;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric_verification);
        executor= ContextCompat.getMainExecutor(this);
        biometricPrompt=new BiometricPrompt(this,executor,new BiometricPrompt.AuthenticationCallback(){
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result){
                super.onAuthenticationSucceeded(result);
                Toast.makeText(biometric_verification.this,"Successful Verification",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString){
                super.onAuthenticationError(errorCode,errString);
                Toast.makeText(biometric_verification.this,errString,Toast.LENGTH_SHORT).show();
                biometric_verification.this.finish();
                startActivity(new Intent(getApplicationContext(),LoginOrRegister.class));
            }

            @Override
            public void onAuthenticationFailed(){
                super.onAuthenticationFailed();
                Toast.makeText(biometric_verification.this,"Verfication Failed",Toast.LENGTH_SHORT).show();
            }
        });
        promptInfo=new BiometricPrompt.PromptInfo.Builder().setTitle("Fingerprint Required").setDescription("Place your finger on the sensor").setNegativeButtonText("Exit").build();
        biometricPrompt.authenticate(promptInfo);
    }
    public void auth(View view){
        Intent i=new Intent(getApplicationContext(),Choose.class);
        startActivity(i);
    }
}
