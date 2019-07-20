package com.example.startscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.HashSet;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {


    FirebaseAuth mAuth;
    DatabaseReference mReference;

    ProgressDialog mDialog;

    EditText musername, mEmail, mPassword;
    TextView mMember;
    Button mRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        initialize();


        mMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

            }
        });


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDialog = new ProgressDialog(RegisterActivity.this);
                mDialog.setMessage("Please wait");
                mDialog.show();

                String username = musername.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();


                if (!username.isEmpty()) {
                    if (!email.isEmpty()) {
                        if (!password.isEmpty()) {
                            if (password.length() >= 6) {
                                register(username, email, password);
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "Minimum password length should be at least 6 characters", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            mDialog.dismiss();
                            mPassword.setError("Password cannot be empty");
                            mPassword.requestFocus();
                        }
                    } else {
                        mDialog.dismiss();
                        mEmail.setError("Email cannot be empty");
                        mEmail.requestFocus();
                    }
                } else {
                    mDialog.dismiss();
                    musername.setError("Username cannot be empty");
                    mPassword.requestFocus();


                }

            }
        });
    }

    private void register(final String username, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();


                            mReference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("userId", userid);
                            hashMap.put("username", username);
                            hashMap.put("bio", "");
                            hashMap.put("imageUrl", "https://firebasestorage.googleapis.com/v0/b/instaclone-c5690.appspot.com/o/account.png?alt=media&token=ef0e38ff-53cd-4499-8ec2-6f3e18c8cc0a");
mReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()) {
            mDialog.dismiss();
            Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterActivity.this, MyHomeActivity.class));
            finish();


        }
    }
});



                        } else{
                            mDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    private void initialize() {


        musername = findViewById(R.id.reg_username);
        mEmail = findViewById(R.id.reg_email);
        mPassword = findViewById(R.id.reg_password);
        mRegister = findViewById(R.id.btnReg);
        mMember = findViewById(R.id.txt_already_a_member);


    }
}

















