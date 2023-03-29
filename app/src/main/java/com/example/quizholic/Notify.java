package com.example.quizholic;

import java.io.Serializable;

public class Notify implements Serializable {
    private String CourseId;
    private String SubjectLine;
    private String Announce;
    private String Date;


    public Notify(String courseId, String announcmentText, String date,String subjectline) {
        this.CourseId = courseId;
        this.Announce = announcmentText;
        this.Date = date;

        this.SubjectLine=subjectline;
    }
    public Notify()
    {

    }
    public String getCourseId() {
        return CourseId;
    }
    public void setCourseId(String courseId) {
        this.CourseId = courseId;
    }


    public String getAnnounce() {
        return Announce;
    }
    public void setAnnounce(String announce) {
        this.Announce = announce;
    }


    public String getdate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }
    public String getSubjectLine() {
        return SubjectLine;
    }
    public void setSubjectLine(String subjectLine) {
        this.SubjectLine = subjectLine;
    }


}
