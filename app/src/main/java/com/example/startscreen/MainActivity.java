package com.example.startscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button mLogin,mRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    mLogin= findViewById(R.id.btn_login);
    mRegister = findViewById(R.id.btn_register);

    mLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
//            startActivity(intent);
            startActivity( new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }
    });

    mRegister.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            finish();
        }
    });



    }
}
