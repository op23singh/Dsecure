package com.op23singh.dsecure;

import java.io.Serializable;

public class User implements Serializable {
    public String FirstName,LastName,Email;
    public String FullName;
    public String Phonenumber;
    public User(){

    }
    public User(String firstName,String lastName,String email,String phonenumber){
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        FullName=firstName+" "+lastName;
        Phonenumber=phonenumber;
    }
}
