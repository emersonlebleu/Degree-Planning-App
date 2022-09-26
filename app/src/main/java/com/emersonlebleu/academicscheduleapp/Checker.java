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
}
