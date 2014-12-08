# Professor Requests

All the requests students should make

## Navigation

- [Login](README.md)
- [Student](StudentReq.md)
- [Professor](ProfessorReq.md)

## Links to own page

* [Insert a question](#insertquestionphp)
* [All Questions](#allquestionsphp)
* [Insert Exam](#insertexamphp)
* [Select Exam to Release](#selectexamreleasephp)
* [Release Exam](#releaseexamphp)
* [Delete Exam](#deleteexamphp)

###insertQuestion.php

Action: Professor clicks on the create question

Request URL: http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/insertQuestion.php

Input Request Type: POST

Input Variables: 

```
question_type, question, correct, points
         
   If question_type == "MultipleChoice"
      Additional Post Variables: correct_reason, wrongAnswer1, wrongReason1, wrongAnswer2, wrongReason2, wrongAnswer3, wrongReason3
   Else if question_type == "TrueFalse"
      Additional Post Variables: correct_reason, wrongAnswer1, wrongReason1
   Else if question_type == "ShortAnswer"
      NO additional post variables required
   Else if question_type == "Programming"
      Additional Post Variables: input1, output1, input2, output2, input3, output3

```

Output:

On success

```JSON
{
   "questionCreated" : "Success"
}
```

On failure

```JSON
{
   "questionCreated" : "Failed",
   "Error" : "mysql error"
}
```

Returns a JSON indicating if the question was successfully inserted into the database

The insert programming question is optional. This will make it so the user can add questions that have more than one argument as input. Main purpose is to accept the partial credit question. I made it so both can work at the same time. 

###insertProgrammingQuestion.php

Action: Professor clicks on the create programming question

Request URL: http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/insertProgrammingQuestion.php

Input Request Type: JSON POST

Input Variables: 

```JSON
{  
   "question_type":"ProgrammingQuestion",
   "question":"Write a function named operation that takes in 3 parameters and gives the result of operation",
   "input1":[  
      "*",
      "3",
      "4"
   ],
   "output1":"12",
   "input2":[  
      "+",
      "7",
      "9"
   ],
   "output2":"16",
   "input3":[  
      "-",
      "9",
      "4"
   ],
   "output3":"5",
   "input4":[  
      "/",
      "10",
      "5"
   ],
   "output4":"2"
}
```

Output:

On success

```JSON
{
   "questionCreated" : "Success"
}
```

On failure

```JSON
{
   "questionCreated" : "Failed",
   "Error" : "mysql error"
}
```

NOTE: The only difference this is from the other one is the fact that input1, input2, input3, input4 are all arrays and this is a JSON POST instead of a regular POSt.

Returns a JSON indicating if the question was successfully inserted into the database

### allQuestions.php

Action: Professor clicks on the quesiton bank

Request URL: http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/allQuestions.php
	
Input Request Type: POST

Input Variables: userID

Output Message:

```JSON
{
      "questions" : 
            [
               {
                  "questionID" : "1",
                  "questionType" : "MC",
                  "question" : "What is an assignment statement?"
               },
               {
                  "questionID" : "16",
                  "questionType" : "FB",
                  "question" : "Another term for _______________ is data hiding."
               }
      
            ]
   }
```

### insertExam.php

Action: Professor clicks on create exam button

Request URL: http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/insertExam.php

Input Request type: JSON POST

Input Message:

```JSON
{ 
      "questionIDs" : [1, 3, 5, 6, 8], 
      "examName" : "Test Exam" 
}
```

Output:

On success

```JSON
{
      "examCreated" : "Success"
}
```

On Failure

```JSON
{
      "examCreated" : "Failed",
      "Error" : "Because of some reason"
}
```

Returns a JSON indicating whether or not if the exam was created successfully

### selectExamRelease.php

Action: Professor clicks on the release exam button to get a list of exams to release

Request URL: http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/selectExamRelease.php

Input Request Type: POST 

Input variables: userID

Output:

On success

```JSON
{
   "exams":[
      {
         "examID":"1",
         "examName":"Exam_1",
         "examReleased":"False"
      },
      {
         "examID":"2",
         "examName":"Exam_2",
         "examReleased":"False"
      }
   ]
}
```

Returns a JSON of exams and their information such as if they are released or not

On Failure

```JSON
{
   "Error" : "Some reason"
}
```

### releaseExam.php

Action: Professor clicks on a specific exam to release

Request URL: http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/releaseExam.php

Input Request Type: POST 

Input variables: userID, examID

Output:

On success

```JSON
{
   "Update" : "Success"
}
```

On failure

```JSON
{
   "Error" : "Some reason"
}
```

Returns a JSON indicating whether or not the release exam was successful or not

Note: Release an exam will grade the exam for all the students that took the exam and students can see their grades now

### deleteExam.php

Action: Professor clicks on a specific exam to delete

Request URL: http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/deleteExam.php

Input Request Type: POST 

Input variables: userID, examID

Output:

On success

```JSON
{
   "Delete" : "Success"
}
```

On failure

```JSON
{
   "Error" : "Some reason"
}
```

Returns a JSON indicating whether or not the deletion of exam was successful or not

Warning: Once delete you will not get the exam or the grades of the students who took the exam
Use this call with caution
