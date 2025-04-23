# PeerEvaluationApp
Application for Purdue students to evaluate their peers after working in a group project, so that final grades can be calculated based on how their peers viewed there individual performance on the project.

## Tools and Technologies
Main application built with Java and Java Spring boot

Javascript for CSR and retrieving data from the server

MySQL Server to store data

## User Guide

## Instructor

### Register and log in
To first start using the application an instructor must sign up at domainName/instructor/SignUp. The instructor will need to enter their purdue email and 9 digit PUID to sign up. Once an instructor has signed up they will be directed to the instructor dashboard where they can create, view, and manage peer evaluations.

After first signing up an instructor can log into their account with the same purdue email and PUID they used to first sign up with at domainName/instructor/login.

### Creating a peer evaluation
Once signed in, an instructor can create a peer evaluation for a project/assignment by clicking the "Create A New Peer Evaluation" button.
On the next screen you will need to fill out some information.

Firstly, you will need to upload your exported brightSpace project data, see below.

Next you will need to fill out the class Code that the evaluation is for. It is recommended to use a class code such as "CS 180", however any input is accepted, and you can write the full class name such as, "intro to computer science", if desired.

You will then choose if you want to use the standard evaluation questions or create your own, if a student can view their feedback from their group members (Student feedback is anonymous and students will only see written feedback and not the grade their group members assigned to them), and a due date for the evaluation. You can edit this due date later.

### Exporting brightSpace data to peer evaluation application
To create a peer evaluation, you will need to upload a csv file that can be downloaded from brightSpace.

To do this go to brightSpace, and navigate to the Grades section of the class you want to make an evaluation for.
Next click the "Export" button in the top left.
Then you will need to select certain fields to export.

For Key Field: select "Org Defined ID"
Sort By: Default
Grade Values: select "Points grade"
User Details: select "Last Name", "First Name", "Email", and "Group Membership"

Note: it is extremely important that you select all of these values, if any of these values are missing it may cause the evaluation to not be made.

Then you will need to choose which grades to export.
Select the individual project or assignment you want to make an evaluation for.

Once you have everything selected, click "Export to CSV" in the bottom left.

### Importing evaluation grades to BrightSpace
To import grades from the evaluation application to brightSpace you will need to login, click the "View and manage evaluations",
then find the evaluation you want export the grades from and click the "Export" button on the right side.
This will download a csv file with the combined grades of what each student achieved on their project and what their group members graded them.

You can then go to brightSpace, navigate to the grades tab of the class you want to import the grades to, 
click "Import" in the top left, select "browse" and select the file csv file that was downloaded from the peer evaluation application.

You should not need to check the "Create new grade item when an unrecognized item is referenced" button.

The project/assignment grade should be replaced with the new grades from the evaluation.


### How grades are calculated
To calculate the final grade that a student receives for a group project, we take the original grade and multiply it by the average peer grade divided by 100, and then we round the result.

Final Grade = Rounded(Project Grade * (Average Evaluation Grade / 100))

Students who don't complete their evaluations are given a 0.

#### Example
Project grade = 80/100
Group member 1 gives a score of 100 (a score of 100 is an average score and means that the student preformed well, but did not go above and beyond)
Group member 2 gives a score of 120 (a score of 120 means that the student who gave the score thinks this student preformed better than the rest of the group)
Group member 3 gives a score of 90 (a score of 90 means that the student who gave the score believes that this student did not preform as well as the rest of the group)

Average evaluation grade = 103.33

Final Project grade = 80 * (103.33 / 100) = 82.66

Final project grade = 83 

### Viewing and managing created evaluations
To view and manage created peer evaluations, you can click the "View and Manage Evaluations" button on the dashboard.
From here you can see all the past evaluations you have made, sorted by newest first.
On this screen you can see the class, project name, number of students who have responded, when the evaluation is due, and a button to export the grades to brightSpace.

For each evaluation you can click into it to view further details.
On this new screen you can update the evaluation due date, extend the due date for specific students, and view the scores and written feedback that students have given to each other.



### Students


#### Register/Sign in
Students do not need to worry about registering, when an instructor creates an evaluation for a student, it will automatically sign the student up for the application.


To sign in students should go to domainName/student/login and enter their purdue email and 9 digit PUID


#### Completing a peer evaluation
To complete an evaluation for your class a student should first log in, and then click the "Complete Evaluation" button on the dashboard, this will lead to a screen where the student can choose which evaluation they want to complete. After selecting the evaluation you move on to actually completeing it.


To complete the peer evaluation the student needs to give a numerical rating to each group member, and fill out the questions that the instructor provided about each student.


#### How scoring group members works
Grading works via a pool system. This means that for each group member you have, there are 100 points you can assign, i.e. (3 group members, 300 points to assign).


You can assign any amount of points up to the maximum amount to any student.


The average grade is 100 points, if all members of your group do equal amounts of work you should assign 100 points to each group member


It is recommended to assign between 75 - 125 points to a student, unless there are special circumstances, where a group member either put in much less, or much more effort in the group project, this should be reflected in the written feedback.




#### Viewing feedback from group members
If the instructor allows you can view the written feedback that your fellow group members give to you. To access the feedback navigate to the dashboard and click the "View Feedback from Past Evaluations", here you will be able to select an evaluation to view the feedback from your peers.


Students can only view the written feedback that their group members give them.


All feedback is annonmous for student. Students will not be able to see who gave the feedback, however instructors are able to see which student gave which feedback.


Students can not view what score their group members gave them.




## API List


### Endpoint list


#### POST /evaluation/submit/feedback
Takes a EvaluationFeedbackFormDTO Object as input and using it adds the feedback response from a student for an evaluation to the DB.


#### POST /evaluation/submit/form
Takes a Multipart CSVFile and EvaluationFormDTO object as input, and creates and saves an evaluation in the DB.


We also create and save a Project, Students, GroupCategory, and MyClass objects during the process of creating an evaluation object.


#### GET /evaluation/details
Note: this should probably be moved to /instructor


Takes an evaluationId integer as an argument and returns a webpage with details about that evaluation


#### POST /evaluation/updateDueDate
Takes a JSON as an argument with a dueDateString (in the form of {YYYY/MM/DD}), and evaluationId


Updates the due date for an evaluation


#### POST /evaluation/extendDeadline




#### POST /evaluation/getResponses




#### GET /instructor/login




#### POST /instructor/login/submit




#### GET /instructor/signUp




#### POST /instructor/signUp/submit




#### GET /instructor/dashboard




#### GET /instructor/viewEvaluations




#### GET /instructor/createEvaluations




#### GET /instructor/downloadCSV/{id}




#### GET /instructor/viewEvaluation/details/{id}




#### GET /student/login




