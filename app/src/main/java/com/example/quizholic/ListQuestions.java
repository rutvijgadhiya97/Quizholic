package com.example.quizholic;

import java.io.Serializable;
import java.util.ArrayList;

public class ListQuestions implements Serializable {
    public boolean QuizTakenFlag;
    public String CourseName;
    public ArrayList<Questions> lstQuestion=new ArrayList<>();
    public String QuizTitle;
    public ListQuestions() {
    }
    public ListQuestions(boolean quizTakenFlag,ArrayList<Questions> lstQuestions,String coursename,String quiztitle) {
        this.QuizTakenFlag=quizTakenFlag;
        this.lstQuestion=lstQuestions;
        this.CourseName=coursename;
        this.QuizTitle=quiztitle;
    }
}
