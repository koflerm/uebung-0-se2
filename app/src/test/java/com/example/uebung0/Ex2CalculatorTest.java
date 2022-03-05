package com.example.uebung0;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class Ex2CalculatorTest extends TestCase {

    @Test
    public void testCalculateQuersumme() {
        Integer sum = Ex2Calculator.calculateQuersumme(11904834);
        Integer expectedSum = 30;

        assertEquals(expectedSum, sum);
    }

    @Test
    public void testCalculateQuersummeAndReturnBinary() {
        String binary = Ex2Calculator.calculateQuersummeAndReturnBinary(11904834);
        String expectedBinary = "11110";

        assertEquals(expectedBinary, binary);
    }
}