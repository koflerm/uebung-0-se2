package com.example.uebung0;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NetworkManager {
    private Socket clientSocket;

    public NetworkManager() {

    }

    public Observer<Socket> initObserver(String matNum, TextView result) {
        return new Observer<Socket>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("NETMANAGER", "Starting to initialize connection...");
            }

            @Override
            public void onNext(Socket s) {
                Log.d("NETMANAGER", "Initialized connection successfully");
                clientSocket = s;
                executeCommunication(matNum)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(executionObserver(result));
            }

            @Override public void onError(Throwable e) {
                Log.e("NETMANAGER", "Failed to initialize connection");
            }

            @Override
            public void onComplete() {
                Log.d("NETMANAGER", "Init process finished");
            }
        };
    }

    private Observer executionObserver(TextView result) {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("NETMANAGER", "Subscribed to communication executor");
            }

            @Override
            public void onNext(String s) {
                Log.d("NETMANAGER", "Received answer");
                result.setText(s);
            }

            @Override public void onError(Throwable e) {
                Log.e("NETMANAGER", "Error occurred during communication");
                result.setText("Something went wrong");
            }

            @Override
            public void onComplete() {

            }
        };
    }

    public Observable<Socket> initializeConnection() throws IOException {
        return new Observable<Socket>() {
            @Override
            protected void subscribeActual(Observer observer) {
                Log.i("NETMANAGER", "Initializing socket...");
                try {
                    Socket socket = new Socket("se2-isys.aau.at", 53212);
                    observer.onNext(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                    observer.onError(e);
                }
            }
        };
    }

    public Observable<String> executeCommunication (String matNum) {
        return new Observable() {
            @Override
            protected void subscribeActual(Observer observer) {
                Log.i("NETMANAGER","Sending message '" + matNum + "' to server...");
                String answer = null;
                try {
                    answer = sendAndRetrieveAnswer(matNum);
                    Log.i("NETMANAGER", "Received answer");
                    observer.onNext(answer);
                } catch (IOException e) {
                    e.printStackTrace();
                    observer.onError(e);
                } finally {
                    observer.onComplete();
                }
            }
        };
    }

    public String sendAndRetrieveAnswer(String matNum) throws IOException {
        Log.i("NM", "Sending message...");
        sendToServer(matNum);
        Log.i("NM", "Message send. Waiting for answer...");
        return waitForServerResponse();
    }

    private void sendToServer(String message) throws IOException {
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        outToServer.writeBytes(message + "\n");
    }

    private String waitForServerResponse() throws IOException {
        BufferedReader inputFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        return inputFromServer.readLine();
    }
}
