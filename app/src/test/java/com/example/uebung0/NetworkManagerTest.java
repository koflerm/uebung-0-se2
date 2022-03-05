package com.example.uebung0;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.util.Log;
import android.widget.TextView;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.observers.TestObserver;

@RunWith(MockitoJUnitRunner.class)
public class NetworkManagerTest extends TestCase {

    @Mock
    private Observer o;

    @Mock
    private TextView tw;

    @Mock
    private Throwable e;

    @Mock
    private Socket clientSocket;

    @Mock
    private DataOutputStream outToServer;

    @Mock
    private BufferedReader inFromServer;

    @InjectMocks
    private NetworkManager networkManager;

    @Before
    public void init() throws IOException {
        MockitoAnnotations.openMocks(this);

        when(inFromServer.readLine()).thenReturn("Test");
    }

    @Test
    public void testInitializeConnection() throws IOException {
        Observable<Socket> os = networkManager.initializeConnection();
        os.subscribe(o);

        verify(o, times(1)).onNext(any(Socket.class));
    }

    @Test
    public void testExecutionObserverOnNext() {
        Observer obs = networkManager.executionObserver(tw);
        obs.onNext("Test");

        verify(tw, times(1)).setText(anyString());
    }

    @Test
    public void testExecutionObserverOnError() {
        Observer obs = networkManager.executionObserver(tw);
        obs.onError(e);

        verify(tw, times(1)).setText(anyString());
    }

    @Test
    public void testExecuteCommunication() {
        Observable obs = networkManager.executeCommunication("Test");
        TestObserver t = TestObserver.create();
        obs.subscribe(t);

        t.assertValue("Test");
    }

    @Test
    public void testFailedExecuteCommunication() throws IOException {
        IOException ioe = new IOException();
        willThrow(ioe).given(outToServer).writeBytes(anyString());

        Observable obs = networkManager.executeCommunication("Test");
        obs.subscribe(o);

        verify(o, times(1)).onError(any());
    }
}