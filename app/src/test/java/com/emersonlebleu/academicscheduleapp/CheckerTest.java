package com.emersonlebleu.academicscheduleapp;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CheckerTest {
    @Test
    public void noteIsOkay(){
        String note = "Note content text for testing.";

        boolean result = Checker.checkNewNote(note);
        assertTrue(result);
    }

    @Test
    public void noteIsNotOkay(){
        String note = "";

        boolean result = Checker.checkNewNote(note);
        assertFalse(result);
    }
}