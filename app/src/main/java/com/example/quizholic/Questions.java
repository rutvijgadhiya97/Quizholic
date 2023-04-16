package com.example.quizholic;

import java.io.Serializable;
import java.util.ArrayList;

public class Questions implements Serializable {
    public String Quiztitle,Question,Option1,Option2,Option3,Option4,Correctans;
    public Questions(){

    }
    public Questions(String quiztitle, String question, String option1, String option2 , String option3, String option4, String correctans)
    {
        this.Quiztitle=quiztitle;
        this.Question=question;
        this.Option1=option1;
        this.Option2=option2;
        this.Option3=option3;
        this.Option4=option4;
        this.Correctans=correctans;
    }
}
