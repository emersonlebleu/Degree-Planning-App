package com.emersonlebleu.academicscheduleapp;

import com.emersonlebleu.academicscheduleapp.Entity.Note;

public class Checker {
    public static boolean checkNewNote(String text){
        if (text != ""){
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkNewTerm(String title){
        if (title != ""){
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkNewCourse(String title){
        if (title != ""){
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkNewAssessment(String title){
        if (title != ""){
            return true;
        } else {
            return false;
        }
    }
}
