package com.example.uebung0;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickSend(View view) {
        EditText input = (EditText) findViewById(R.id.editTextMatrikelnummer);
        String matNum = input.getText().toString();

        Toast.makeText(this, matNum, Toast.LENGTH_LONG).show();
    }
}