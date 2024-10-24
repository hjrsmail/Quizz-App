package com.millenialzdev.myquizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private DataBaseHeleperLogin db;
    //shared pref
    public static final String SHARED_PREF_NAME = "myPref";
    private SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register.newInstance().show(getSupportFragmentManager(), Register.TAG);
            }
        });

        db = new DataBaseHeleperLogin(this);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getUsername = etUsername.getText().toString();
                String getPassword = etPassword.getText().toString();

                if (getPassword.isEmpty() && getPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Username atau password salah!", Toast.LENGTH_LONG).show();
                }else{
                    Boolean masuk = db.checkLogin(getUsername, getPassword);
                    if (masuk == true){
                        Boolean updateSession = db.upgradeSession("ada", 1);
                        if (updateSession == true){
                            Toast.makeText(getApplicationContext(), "Berhasil Masuk", Toast.LENGTH_LONG).show();
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putBoolean("masuk", true);
                            editor.apply();
                            Intent dashbord = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(dashbord);
                            finish();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Gagal Masuk", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}
