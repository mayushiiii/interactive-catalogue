import java.util.ArrayList;



public class Main {
    public static void main(String[] args) {
        UserFactory factory = new UserFactory();
        User stud1 = factory.getUser("student", "maria", "mov");
        User stud2 = factory.getUser("STudent", "ala", "bala");
        User stud3 = factory.getUser("student", "colegu", "meu");
        User assist = factory.getUser("assistant", "the", "rock");
        User teach1 = factory.getUser("teacher", "mama", "tha");
        User assist1 = factory.getUser("assistant", "dan", "dani");
        if (stud1 instanceof Student)
            System.out.println("bun\n");
        System.out.println("studenti: " + stud1 + ", " + stud2 + ", " + stud3);

        Group clasa1 = new Group("69", (Assistant) assist);
        // ^nu cred ca e ok

        clasa1.add((Student) stud1);
        clasa1.add((Student) stud2);
        clasa1.add((Student) stud3);
        // ^nici asta

        System.out.println(clasa1.getAssistant()+ "\n" + clasa1+ "\n");

        Course course = new FullCourse.FullCourseBuilder("mate", (Teacher) teach1, "Full")
                .initAssistants().initGrades().initGroups().build();

        Grade gradeStud1 = new Grade(5.00, 3.00, (Student) stud1, "mate");
        Grade gradeStud2 = new Grade(4.00, 1.00, (Student) stud2, "mate");
        Grade gradeStud3 = new Grade(3.00, 2.00, (Student) stud3, "mate");

        course.addGroup(clasa1);
        course.addGrade(gradeStud1);
        course.addGrade(gradeStud2);
        course.addGrade(gradeStud3);

        User stud4 = factory.getUser("student", "adi", "adian");
        course.addStudent("69", (Student) stud4);
        Grade gradeStud4 = new Grade(1.00, 3.00, (Student) stud4, "mate");
        course.addGrade(gradeStud4);

        System.out.println("nota lui stud 1: " + course.getGrade((Student) stud1).getTotal());

        System.out.println("elevi din grupa 1: " + course.getAllStudents() + "\n");
        System.out.println(course.getAllStudentGrades().get(stud3).getTotal());

        System.out.println("studenti care trec si notele lor" + course.getGraduatedStudents());

        ScoreVisitor visitor = new ScoreVisitor();
        visitor.addGradeAssist((Assistant) assist, (Student) stud1, course.courseName, 5.00);
        visitor.addGradeAssist((Assistant) assist, (Student) stud2, course.courseName, 2.00);
        visitor.addGradeAssist((Assistant) assist, (Student) stud3, course.courseName, 1.00);
        visitor.addGradeAssist((Assistant) assist1, (Student) stud4, course.courseName, 2.00);
        System.out.println(visitor.getPartialScores());



    }
}