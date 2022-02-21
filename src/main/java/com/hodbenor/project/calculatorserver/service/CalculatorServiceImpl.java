package com.hodbenor.project.calculatorserver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CalculatorServiceImpl implements CalculatorService {

    @Override
    public Double calculate(String arithmeticOperation) {
        List<String> characters = parse(arithmeticOperation);
        if (characters.size() > 2) {
            Double operand1, operand2;
            try {
                operand1 = Double.valueOf(characters.get(0));
                operand2 = Double.valueOf(characters.get(2));
            } catch (Exception e) {
                log.error("Fail to parse operands from " + characters, e);
                return null;
            }

            System.out.println("Request: "+operand1+characters.get(1)+operand2);
            Double result = null;
            switch (characters.get(1)) {
                case "+":
                    result = operand1 + operand2; break;
                case "-":
                    result = operand1 - operand2; break;
                case "*":
                    result = operand1 * operand2; break;
                case "/":
                    result = operand1 / operand2; break;
            }
            System.out.println("Response: "+operand1+characters.get(1)+operand2
                +"="+result);
            return result;
        }
        return null;
    }

    private List<String> parse(String input) {
        List<String> resultChars = new ArrayList<>();
        try {
            char characters[] = input.toCharArray();
            String num = "";
            for (int i = 0; i < characters.length; ++i) {
                if (isBlackSpace(characters[i])) {
                    continue;
                }

                switch (characters[i]) {
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                        if ("" == num) {
                            num += String.valueOf(characters[i]);
                            break;
                        }
                        resultChars.add(num);
                        num = "";
                        resultChars.add(String.valueOf(characters[i]));
                        break;
                    default:
                        num += characters[i];
                        break;
                }
            }
            resultChars.add(num);
        } catch (Exception e) {
            log.error("Fail to parse input: " + input, e);
        }
        return resultChars;
    }

    private boolean isBlackSpace(char character) {
        return ' ' == character;
    }


}
