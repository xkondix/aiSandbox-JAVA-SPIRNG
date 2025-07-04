package com.kowalczyk.konrad.ai.component;

import dev.langchain4j.agent.tool.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MathToolComponent {

    private static final Logger log = LoggerFactory.getLogger(MathToolComponent.class);

    @Tool("Add two numbers and return the result")
    public int add(int a, int b) {
        int result = a + b;
        log.info("add({}, {}) = {}", a, b, result);
        return result;
    }

    @Tool("Subtract the second number from the first and return the result")
    public int subtract(int a, int b) {
        int result = a - b;
        log.info("subtract({}, {}) = {}", a, b, result);
        return result;
    }

    @Tool("Multiply two numbers and return the result")
    public int multiply(int a, int b) {
        int result = a * b;
        log.info("multiply({}, {}) = {}", a, b, result);
        return result;
    }

    @Tool("Divide the first number by the second and return the result as a decimal")
    public double divide(int a, int b) {
        if (b == 0) {
            log.warn("Attempted to divide {} by zero", a);
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        double result = (double) a / b;
        log.info("divide({}, {}) = {}", a, b, result);
        return result;
    }

    @Tool("Calculate and return the square root of the given number")
    public double sqrt(double x) {
        double result = Math.sqrt(x);
        log.info("sqrt({}) = {}", x, result);
        return result;
    }
}
