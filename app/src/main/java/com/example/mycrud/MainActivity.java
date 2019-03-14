package com.example.mycrud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText Nama;
    private EditText Posisi;
    private EditText Gaji;

    private Button Add;
    private Button View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Nama = (EditText) findViewById(R.id.Nama);
        Posisi = (EditText) findViewById(R.id.Posisi);
        Gaji = (EditText) findViewById(R.id.Gaji);

        Add = (Button) findViewById(R.id.Add);
        View = (Button) findViewById(R.id.View);

        Add.setOnClickListener(this);
        View.setOnClickListener(this);
    }

    private void addEmployee() {

        final String nama = Nama.getText().toString().trim();
        final String posisi = Posisi.getText().toString().trim();
        final String gaji = Gaji.getText().toString().trim();

        class AddEmployee extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Menambahkan...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(konfigurasi.KEY_EMP_NAMA, nama);
                params.put(konfigurasi.KEY_EMP_POSISI, posisi);
                params.put(konfigurasi.KEY_EMP_GAJI, gaji);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    @Override
    public void onClick(View v) {
        if (v == Add) {
            addEmployee();
        }

        if (v == View) {
            startActivity(new Intent(MainActivity.this, TampilAll.class));
        }
    }
}
