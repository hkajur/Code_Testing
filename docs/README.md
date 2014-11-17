# Controller

A PHP Library to access HTTP-based API

## Navigation

- [Login](README.md)
- [Student](StudentReq.md)
- [Professor](ProfessorReq.md)

### login.php 

Request URL: http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/login.php

Input Request Type: POST

Input Variables: username, password

Output:

On Failure: 

```
{
    "Backend_Login" : "Failed",
    "NJIT_Login" : "Failed",
    "Error" : "Due to "
}
```

On Success for Student:

```
{
    "userType" : "Student",
    "userName" : "janedoe",
    "userID" : "1",
    "Backend_Login" : "Success",
    "exams" : [
      {
          "examID"    : "1",
          "examName" : "Exam1",
          "examTaken" : "True"
      },
      {
          "examID"    : "2",
          "examName"  : "Exam2",
          "examTaken" : "False"
      }
    ],
    "NJIT_Login" : "Failed"
}
```

Returns a JSON with a list of exams and each list element containing a JSON which has the examID, examName, and examTaken to check if the exam is taken by the user 


On Success for Professor:

```
{
    "userType" :  "Teacher",
    "userName" : "professor1",
    "userID" : "11",
    "Backend_Login" : "Success",
    "exams" : [
      {
          "examID" : "1",
          "examName" : "Exam1",
          "examReleased" : "True"
      },
      {
          "examID" : "2",
          "examName" : "Exam2",
          "examReleased" : "False"
      }
    ],
    "NJIT_Login" : "Failed"
}
```
Returns a JSON with a list of exams that this teacher has made and each list element of exams contains a JSON which has examID, examName, and if the exam grade is released or not
