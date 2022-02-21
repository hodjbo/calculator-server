package com.hodbenor.project.calculatorserver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Component
@Slf4j
public class CalculatorManager {
    private int calcSocketPort;
    private int keepAliveSocketPort;
    private ServerSocket calcServerSocket;
    private ServerSocket serverSocket;

    public CalculatorManager(@Value("${server.socket.calc.port}") int calcSocketPort,
                             @Value("${server.socket.keepalive.port}") int keepAliveSocketPort) {
        this.calcSocketPort = calcSocketPort;
        this.keepAliveSocketPort = keepAliveSocketPort;
    }

    @PostConstruct
    private void openSocket() {
        openKeepAliveSocket();
        openCalcualtorSocket();
}

    private void openCalcualtorSocket() {
        try {
            calcServerSocket = new ServerSocket(calcSocketPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            while (true) {
                try {
                    Socket socket = calcServerSocket.accept();
                    ServerCalculatorThread serverThread = new ServerCalculatorThread(socket);
                    serverThread.start();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }).start();
    }

    private void openKeepAliveSocket() {
        try {
            serverSocket = new ServerSocket(keepAliveSocketPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    ServerKeepAliveThread serverThread = new ServerKeepAliveThread(socket);
                    serverThread.start();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }).start();
    }
}
