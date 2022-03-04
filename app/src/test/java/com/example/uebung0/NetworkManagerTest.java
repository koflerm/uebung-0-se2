package com.example.uebung0;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.Socket;

import io.reactivex.Observable;
import io.reactivex.Observer;

@RunWith(MockitoJUnitRunner.class)
public class NetworkManagerTest extends TestCase {

    @Mock
    private Observer o;

    @InjectMocks
    private NetworkManager networkManager;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInitializeConnection() throws IOException {
        Observable<Socket> os = networkManager.initializeConnection();
        os.subscribe(o);

        verify(o, times(1)).onNext(any(Socket.class));
    }
}