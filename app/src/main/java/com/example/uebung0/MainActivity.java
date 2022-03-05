package com.example.uebung0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Network;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickSend(View view) throws IOException {
        Context c = this;
        EditText input = (EditText) findViewById(R.id.editTextMatrikelnummer);
        TextView result = (TextView) findViewById(R.id.textView2);
        String matNum = input.getText().toString();
        NetworkManager networkManager = new NetworkManager();

        networkManager.initializeConnection()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(networkManager.initObserver(matNum, result));
    }

    public void onClickQuersumme(View view) {
        EditText input = (EditText) findViewById(R.id.editTextMatrikelnummer);
        TextView resultView = (TextView) findViewById(R.id.textView2);
        String matNumText = input.getText().toString();
        Integer matNum;

        try {
            matNum = Integer.parseInt(matNumText);
            Integer sum = Ex2Calculator.calculateQuersumme(matNum);
            String binarySum = Ex2Calculator.calculateQuersummeAndReturnBinary(matNum);
            resultView.setText("Result: " + sum + " Binary: " + binarySum);

        } catch (NumberFormatException nfe) {
            resultView.setText("Invalid Matrikelnummer");
        }
    }
}