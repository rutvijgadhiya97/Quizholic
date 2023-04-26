package com.example.quizholic;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    public String FirstName,LastName,Major,Email,Password,Role,UserID,SelectedCourse,Phonenumber,UtaID;

    public ArrayList<Course> CourseInfo;
    public ArrayList<Notify> AnnouncmentList=new ArrayList<>();
    public ArrayList<ListQuestions> LstQuestions =new ArrayList<>();
    public String CourcePostion="";
    public User(){

    }
    public User(String firstName,String lastName,String major,String email,String password,String role,String userId,ArrayList<Course> courseInfo,String selectedcourses, ArrayList<Notify> announcmentlist,String phonenumber,String utaId,ArrayList<ListQuestions> lstquestions)
    {
        this.FirstName=firstName;
        this.LastName=lastName;
        this.Major=major;
        this.Email=email;
        this.Password=password;
        this.Role=role;
        this.UserID=userId;
        this.CourseInfo=courseInfo;
        this.AnnouncmentList=announcmentlist;
        this.SelectedCourse=selectedcourses;

        this.Phonenumber=phonenumber;

        this.UtaID=utaId;
        this.LstQuestions=lstquestions;
    }
}
