package com.hodbenor.project.calculatorserver.service;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ServerKeepAliveThread extends Thread {
    private final Socket socket;
    private final CalculatorService calculatorService;

    ServerKeepAliveThread(Socket socket) {
        this.socket = socket;
        this.calculatorService = new CalculatorServiceImpl();
    }

    @Override
    public void run() {
        try (PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true)) {
            while (true) {
                TimeUnit.SECONDS.sleep(10);
                printWriter.println("Calculator Server is alive");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
}
