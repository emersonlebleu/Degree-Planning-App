package com.emersonlebleu.academicscheduleapp;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CheckerTest {
    //note
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
    //Term
    @Test
    public void termIsOkay(){
        String title = "This is the title";

        boolean result = Checker.checkNewTerm(title);
        assertTrue(result);
    }

    @Test
    public void termIsNotOkay(){
        String title = "";

        boolean result = Checker.checkNewTerm(title);
        assertFalse(result);
    }
    //Course
    @Test
    public void courseIsOkay(){
        String title = "Title for course";

        boolean result = Checker.checkNewCourse(title);
        assertTrue(result);
    }

    @Test
    public void courseIsNotOkay(){
        String title = "";

        boolean result = Checker.checkNewCourse(title);
        assertFalse(result);
    }
}