package com.example.quizholic;

import java.io.Serializable;

public class Questions implements Serializable {
    private String Quiztitle,QuestionText,Option1,Option2,Option3,Option4,Correctans,ScheduleDate,SelectedCourse,Userans,QuizTime,QuestionPts,Score;
    int radioId,Hr,Min;
    public Questions(){

    }
    public Questions(String quiztitle, String question, String option1, String option2 , String option3, String option4, String correctans,String date, String selectedcourse,String userans,int radioID,String quizTime,String questionPts,int hr,int min,String score)
    {
        this.Quiztitle=quiztitle;
        this.QuestionText=question;
        this.Option1=option1;
        this.Option2=option2;
        this.Option3=option3;
        this.Option4=option4;
        this.Correctans=correctans;
        this.ScheduleDate=date;
        this.SelectedCourse=selectedcourse;
        this.Userans=userans;
        this.radioId=radioID;
        this.QuizTime=quizTime;
        this.QuestionPts=questionPts;
        this.Hr=hr;
        this.Min=min;
        this.Score=score;
    }
    public String getQuiztitle() {
        return Quiztitle;
    }
    public void setQuiztitle(String courseId) {
        this.Quiztitle = courseId;
    }
    public void setQuestionText(String courseId) {
        this.QuestionText = courseId;
    }
    public void setScheduleDate(String courseId) {
        this.ScheduleDate = courseId;
    }

    public String getQuestionText() {
        return QuestionText;
    }
    public String getSelectedCourse() {
        return SelectedCourse;
    }
    public void setSelectedCourse(String courseId) {
        this.SelectedCourse = courseId;
    }

    public String getScheduleDate() {
        return ScheduleDate;
    }
    public String getOption1() {
        return Option1;
    }
    public void setOption1(String courseId) {
        this.Option1 = courseId;
    }

    public String getOption2() {
        return Option2;
    }
    public void setOption2(String courseId) {
        this.Option2 = courseId;
    }
    public String getOption3() {
        return Option3;
    }
    public void setOption3(String courseId) {
        this.Option3 = courseId;
    }
    public String getOption4() {
        return Option4;
    }
    public void setOption4(String courseId) {
        this.Option4 = courseId;
    }

    public String getUserans() {
        return Userans;
    }
    public void setUserans(String userans) {
        this.Userans = userans;
    }
    public String getCorrectans() {
        return Correctans;
    }
    public void setCorrectans(String userans) {
        this.Correctans = userans;
    }
    public int getradioId() {
        return radioId;
    }
    public void setradioId(int radioI) {
        this.radioId = radioI;
    }
    public String getQuizTime() {
        return QuizTime;
    }
    public void setQuizTime(String radioI) {
        this.QuizTime = radioI;
    }
    public String getQuestionPts() {
        return QuestionPts;
    }
    public void setQuestionPts(String radioI) {
        this.QuestionPts = radioI;
    }
    public int getHr() {
        return Hr;
    }
    public void setHr(int radioI) {
        this.Hr = radioI;
    }
    public int getMin() {
        return Min;
    }
    public void setMin(int radioI) {
        this.Min = radioI;
    }
    public String getScore() {
        return Score;
    }
    public void setScore(String radioI) {
        this.Score = radioI;
    }

}
