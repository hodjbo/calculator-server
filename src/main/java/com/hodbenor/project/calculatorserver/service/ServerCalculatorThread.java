package com.hodbenor.project.calculatorserver.service;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

@Slf4j
public class ServerCalculatorThread extends Thread {
    private final Socket socket;
    private final CalculatorService calculatorService;

    ServerCalculatorThread(Socket socket) {
        this.socket = socket;
        this.calculatorService = new CalculatorServiceImpl();
    }

    @Override
    public void run() {
        try (PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true)) {
            Scanner scanner = new Scanner(socket.getInputStream());
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) {
                    break;
                }
                String output = null;
                Double result = calculatorService.calculate(input);
                if (null == result) {
                    output = "Error";
                } else {
                    output = String.valueOf(result);
                }
                printWriter.println(output);
            }
        } catch (IOException e) {
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
