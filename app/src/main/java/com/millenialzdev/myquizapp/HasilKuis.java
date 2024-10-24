package com.millenialzdev.myquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HasilKuis extends AppCompatActivity {

    private TextView tvNilai, tvMessage,tvHasil;
    private Button btnUlang;

    private DataBaseHeleperLogin db;
    private Button btnKeluar;

    public static final String SHARED_PREF_NAME = "myPref";

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_kuis);

        tvNilai = findViewById(R.id.tvNilai);
        tvMessage = findViewById(R.id.tvMessage);
        btnUlang = findViewById(R.id.btnUlang);
        tvHasil = findViewById(R.id.tvhasil);

        int nilai = getIntent().getExtras().getInt("nilai");
        int benar = getIntent().getExtras().getInt("benar");
        int salah = getIntent().getExtras().getInt("salah");

        tvMessage.setText("Jawaban Benar: " + benar +" , "+ "Jawaban Salah: " + salah);
        tvNilai.setText(String.valueOf(nilai));

        if (nilai == 100){
            tvHasil.setText("Kamu Mendapatkan Nilai  A, Kamu Lulus!!");
        }else if (nilai >= 80){
            tvHasil.setText("Kamu Mendapatkan Nilai  B, Kamu Lulus!!");
        }else if (nilai >= 60){
            tvHasil.setText("Kamu Mendapatkan Nilai  C, Kamu Remedi!!");
        }else if (nilai >= 40){
            tvHasil.setText("Kamu Mendapatkan Nilai  D, Kamu Remedi!!");
        }else if (nilai >= 20){
            tvHasil.setText("Kamu Mendapatkan Nilai  E, Kamu Remedi!!");
        }

        btnUlang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent back = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(back);
            }
        });

        btnKeluar = findViewById(R.id.btnKeluar);

        db = new DataBaseHeleperLogin(this);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        Boolean checksession = db.checkSession("ada");
        if (checksession == false){
            Intent login = new Intent(getApplicationContext(), Login.class);
            startActivity(login);
            finish();
        }

        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean updateSeesion = db.upgradeSession("kosong", 1);
                if (updateSeesion == true){
                    Toast.makeText(getApplicationContext(), "Berhasil Keluar", Toast.LENGTH_LONG).show();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("masuk", false);
                    editor.apply();

                    Intent keluar = new Intent(getApplicationContext(), Login.class);
                    startActivity(keluar);
                    finish();
                }
            }
        });

    }
}