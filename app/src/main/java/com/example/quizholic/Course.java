package com.example.quizholic;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {

    public String CoursesId,UserId;
    public boolean approvalflag;

    public  String StudentName;

    public Course()
    {

    }
    public Course(String courseId,boolean appflag,String studentName, String userId)
    {
        this.CoursesId=courseId;
        this.approvalflag=appflag;
        this.StudentName=studentName;

        this.UserId=userId;
    }

}
