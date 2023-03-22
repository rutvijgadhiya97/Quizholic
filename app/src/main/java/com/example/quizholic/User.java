package com.example.quizholic;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    public String FirstName,LastName,Major,Email,Password,Role,UserID,SelectedCourse;

    public ArrayList<Course> CourseInfo;

    public User(){

    }
    public User(String firstName,String lastName,String major,String email,String password,String role,String userId,ArrayList<Course> courseInfo,String selectedcourses)
    {
        this.FirstName=firstName;
        this.LastName=lastName;
        this.Major=major;
        this.Email=email;
        this.Password=password;
        this.Role=role;
        this.UserID=userId;
        this.CourseInfo=courseInfo;

        this.SelectedCourse=selectedcourses;
    }
}
