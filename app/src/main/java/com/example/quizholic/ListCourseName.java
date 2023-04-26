package com.example.quizholic;

import java.io.Serializable;

public class ListCourseName implements Serializable {

    private String CourseId;

    public ListCourseName()
    {

    }
    public ListCourseName(String listCourseName)
    {
        this.CourseId=listCourseName;
    }
    public String getCourseId() {
        return CourseId;
    }
    public void setCourseId(String courseId) {
        this.CourseId = courseId;
    }

}
