package com.inducesmile.androidpayexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private EditText mob, namepage2;
    private Button bt;
    Firebase url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth  = FirebaseAuth.getInstance();
        Firebase.setAndroidContext(this);
        url = new Firebase("https://starting-be14c.firebaseio.com/");
        progressDialog = new ProgressDialog(this);
        button = (Button) findViewById(R.id.button);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        mob = (EditText) findViewById(R.id.mobile);
        namepage2 = (EditText) findViewById(R.id.Name);
        button.setOnClickListener(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nametext, mtext, etext, pswd;
                nametext = namepage2.getText().toString();
                pswd = editTextPassword.getText().toString();
                mtext = mob.getText().toString();
                etext = editTextEmail.getText().toString();
                Date c = Calendar.getInstance().getTime();
                Firebase firebase = url.child(nametext+"_"+c);
                firebase.child("Name").setValue(nametext);
                firebase.child("mobile no").setValue(mtext);
                firebase.child("Email id").setValue(etext);
                firebase.child("password").setValue(pswd);
                registerUser();
            }
        });
    }



    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(MainActivity.this, "Email field Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(MainActivity.this, "Password field Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering User.");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(MainActivity.this, "Could Not Register", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }



    @Override
    public void onClick(View view)
    {
        if (view==button)
        {
            registerUser();
        }
    }
    public void HERE(View view) {
        Intent intent = new Intent(this, Main3Activity.class);
        startActivity(intent);
    }

}