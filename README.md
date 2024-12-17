# AI Quizzer Backend

## Description
AI Quizzer is a backend application built using Spring Boot. The application generates quizzes based on user-defined subjects and grade levels and processes responses using AI. It handles quiz creation, submission, hints, and retry functionality. The project uses an H2 database for local data storage.

## Features

**1. Quiz Generation:**

  - Generates a quiz based on the subject and grade level provided by the user by AI.<br>


**2. Submit Quiz:**

  - Accepts the user's answers and calculates a score based on the responses.

**3. Retry Quiz:**

  - Allows users to retry the quiz after submission.

**4. Hint Generation:**

  - Provides hints for individual questions.

**5. Retrieves quiz history & filters:**

  - Retrieves quiz history and scores based on filters provided in the request
  - Filter based on grade, subject, marks, completedDate, etc.
  - Filter Specific date range i.e from=01/09/2024, to=09/09/2024

**6. Send results over email:**

  - individual mail to user for each quiz result.


## Technologies Used

**Backend:**

  - Java
  -	Spring Boot (for building REST APIs)
  -	H2 Database (in-memory database for local development)
  - Jackson (for JSON processing)


**Development Tools:**

  - Maven (for build management)
  - Postman (for API testing)


**AI integration:**

  - Groq API

## Prerequisites

**System Requirements**

  - Java 17 (or higher)
  - Maven (for building project)

**Database Setup**
    The backend uses H2 Database (in-memory) by default, so you don't need to set up an external database. You can access the H2 console at http://localhost:8080/h2-console using the following credentials:
  - JDBC URL: jdbc:h2:mem:testdb
  - Username: sa
  - Password:


## Getting Started

Steps to Run the Backend:

**1.	Install Java 17 or higher:**
  -	If you don't have Java installed, download and install it.

**2.	Clone the Repository:**

   ```bash
   git clone https://github.com/Aryan0097/AIQuizzer.git
   ```

**3.	Navigate to the Backend Directory**
  -   Open your terminal and go to the project folder containing the backend files.
  - 
    ```bash
    cd ai-quizzer-backend
    ```
**4.	Build and Run the Backend:**
  - If you have Maven installed, you can build and run the backend application using the following command:

    ```bash
    mvn spring-boot:run
    ```
  - The backend will start and should be available at http://localhost:8080.

## API Endpoints

**1.	POST /api/quiz/generate**

  - Generate a quiz based on subject and grade level.
  - Request Body:
    ```bash
    {
      "subject": "Mathematics",
      "gradeLevel": "10",
      “numberOfQuestions”: 2
    }
    ```
  - Response: A quiz with questions based on the subject and grade level.
    ```bash
    {
      "subject": "Mathematics",
      "gradeLevel":"10",
      "questions": [
        {
          "questionId": 1,
          "questionText": "What is the value of x in 2x + 5 = 11?",
          "answerOptions": ["2", "3", "4", "5"],
          "correctAnswer": "3",
          "hint": "Solve for x"
        },
        {
          "questionId": 2,
          "questionText": "If sin A = 3/5, what is the value of cos A?",
          "answerOptions": ["4/5", "3/5", "2/5", "1/5"],
          "correctAnswer": "4/5",
          "hint": "Use trig identity"
        }
    }
    ```
    ![WhatsApp Image 2024-12-17 at 18 59 33_09fe4d5f](https://github.com/user-attachments/assets/7f62b792-f134-4223-86ee-0504b42c0c65)



**2.	POST /api/quiz/submit/{quizId}**

  - Submit answers to a quiz.
  - Request Body:
    ```bash
    {
        "userId": 1,
        "answers": {
        "1": "4",
        "2": "16",
        "3": "3"
      }
    }
    ```

**3.	GET /api/quiz/hint/{questionId}**

  - Get a hint for a specific question.
  - Response:
    ```bash
    {
      "hint": "Think about the Pythagorean theorem."
    }
    ```

**4.	GET /api/quiz/retry/{quizId}**

  - Retry a quiz..

**5. GET /api/quiz/history**

  - Retrieves quiz history and scores based on filters provided in the request
  - Filter based on grade, subject, marks, completedDate, etc.
  - Filter Specific date range i.e from=01/09/2024, to=09/09/2024

**6. POST  /notifications/send**

  - email result to user's mail id

    
**7.  POST /api/auth/register**
  - user registration

  ![image](https://github.com/user-attachments/assets/2e3d735e-8f16-47c4-9fac-a96982a19147)


**8.	POST /api/auth/login**
  - user login (for simplicity all user login accepted but actual code commnnted in security file.)
    
  ![image](https://github.com/user-attachments/assets/9cb3a340-0ef6-4257-8c59-a0ec58bbfa3c)



 
