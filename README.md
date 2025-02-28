# Interactive student catalogue


This implementation comes with several functions, as a result of using multiple **Design Patterns** in **Java**.

- Users are added via *UserFactory*, a class using the **Factory** design pattern
  * this makes a clearer distinction between the user types of the catalogue: *students*, *teachers*, *assistants* and *parents*.
- The Courses are created using the **Builder** design pattern
  * certain fields are optional, and the Builder design pattern allows us to fill them only when we want to
- Assistants and teachers can add grades; the grade added by an assistant is considered to be the *partial* grade, while the one added by a teacher is *final*.
- As such, we expect different passing criteria and 'preferences' for each course. 
  * Using **inheritance**, we have the *PartialCourse* and *FullCourse* classes that help us deal with the preffered way of grading a teacher may have.
  * Similarly, a **best student** can be picked by specific criteria; we use the **Strategy** design pattern for this.
- Parents can subscribe to the catalogue in order to be notified when their child receives a new grade. This is achieved using the **Observer** design pattern.
  * The *parent* is the **Observer**, who can see when a new grade is added, while the *teacher* and *assistant* are the ones whose actions are being **observed** (the adding of the grades).
- Grades are added using the **Visitor** design pattern.
  * The teacher/assistant first adds all grades of the students in a (student, course, grade) structure, then 'visits' the entire student list in order to add their grades to the course itself. 
  * As a result, when this is done, the parents get **notified**.
- A student can check their grades in a **GUI** that shows the courses they're enrolled in and all information relevant to that course (teacher, assistant/s and grades).

The `.json` file is an example that gets parsed in *Parser.java*.
