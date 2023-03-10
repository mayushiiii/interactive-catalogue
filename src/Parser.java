import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    public static void main(String[] args) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Catalog catalog = new Catalog();

        try {
            JSONObject obj = (JSONObject) parser.parse(new FileReader("catalog.json"));

            JSONArray array = (JSONArray) obj.get("courses");
            for (Object o : array) {
                JSONObject course = (JSONObject) o;

                String type = (String) course.get("type");
                String strategy = (String) course.get("strategy");
                String name = (String) course.get("name");
                JSONObject teacher = (JSONObject) course.get("teacher");
                UserFactory factory = new UserFactory();
                User teacherUser = factory.getUser("teacher", (String) teacher.get("firstName"), (String) teacher.get("lastName"));
                if(type.equals("FullCourse"))
                    catalog.addCourse(new FullCourse.FullCourseBuilder(name, (Teacher)teacherUser, strategy)
                            .initAssistants()
                            .initGroups()
                            .initGrades().build());
                else if (type.equals("PartialCourse")) {
                    catalog.addCourse(new PartialCourse.PartialCourseBuilder(name, (Teacher)teacherUser, strategy)
                            .initAssistants()
                            .initGroups()
                            .initGrades().build());

                }

                JSONArray assistants = (JSONArray) course.get("assistants");

                //System.out.println("tipul " + type + "\nstrategia " + strategy + "\nnume curs: " + name);
                //System.out.println("profesor " + teacherFirstName + " " + teacherLastName + "\nasistenti");
                for (Object a: assistants) {
                    JSONObject object = (JSONObject) a;
                    Assistant assistant = (Assistant) factory.getUser("assistant",
                            (String) object.get("firstName" ), (String) object.get("lastName"));
                    //n ai tu treaba
                    catalog.getCourse(name).addAssistant(assistant);

                    //System.out.println(assistant.getFirstName() + " " + assistant.getLastName());
                }

                JSONArray groups = (JSONArray) course.get("groups");

                for (Object g: groups) {
                    JSONObject groupJSON = (JSONObject) g;
                    JSONObject a = (JSONObject) groupJSON.get("assistant");
                    String assistantFirstName = (String)a.get("firstName");
                    String assistantLastName = (String)a.get("lastName");
                    // putin cam lungit dar asta e pentru ca sa simt eu ca se formeaza totul bine.
                    Assistant assistant = (Assistant) factory.getUser("assistant", assistantFirstName, assistantLastName);
                    String ID = (String)groupJSON.get("ID");
                    Group group = new Group(ID, assistant);
                    catalog.getCourse(name).addGroup(group);
                    //System.out.println(group.id + " " + group.assistant + "\n");

                    JSONArray students = (JSONArray) groupJSON.get("students");
                    int i = 0;
                    for (Object s: students) {
                        JSONObject studentJSON = (JSONObject) s;
                        String studentFirstName = (String) studentJSON.get("firstName");
                        String studentLastName = (String) studentJSON.get("lastName");
                        Student student = (Student) factory.getUser("student", studentFirstName, studentLastName);

                        JSONObject motherInfo = (JSONObject) studentJSON.get("mother");

                        if (motherInfo != null) {
                            String motherFirstName = (String) motherInfo.get("firstName");
                            String motherLastName = (String) motherInfo.get("lastName");
                            student.setMother((Parent) factory.getUser("parent", motherFirstName, motherLastName));
                            // nu sunt sigura cum sa testez fara sa ma complic, deci voi abona niste mame cu un nume random
                        }



                        JSONObject fatherInfo = (JSONObject) studentJSON.get("father");

                        if (fatherInfo != null) {
                            String fatherFirstName = (String) fatherInfo.get("firstName");
                            String fatherLastName = (String) fatherInfo.get("lastName");
                            student.setFather((Parent) factory.getUser("parent", fatherFirstName, fatherLastName));
                        }

                        //System.out.println(student.getFirstName() + " " + student.getLastName() +
                        //        " cu parintii " + student.getMother() + " " + student.getFather());
                        group.add(student);
                        //System.out.println(group.get(i));
                        i++;

                    }

                    //System.out.println(catalog.getCourse(name).getAllStudents() + "\ngata o grupa\n");
                }

                //System.out.println("\n\n");

                //System.out.println(courses);
            }

            // o sa fac observeri toae mamele unei grupe ca sa arat ca merge. pentru a alege mai
            // clar cine se aboneaza ar trebui sa caut parintii in toate listele si tot asa si nu
            // simt nevoia sa fac asta acum
            for (Student student :catalog.getAllCourses().get(0).getAllStudents()) {
                catalog.addObserver(student.getMother());
            }



            //System.out.println(catalog.getObservingParents());

            array = (JSONArray) obj.get("examScores");
            ScoreVisitor visitor = new ScoreVisitor();
            for (Object o : array) {
                JSONObject examScore = (JSONObject) o;

                JSONObject teacher = (JSONObject) examScore.get("teacher");
                UserFactory factory = new UserFactory();
                Teacher teacherUser = (Teacher) factory.getUser("teacher",
                        (String) teacher.get("firstName"), (String) teacher.get("lastName"));

                JSONObject student = (JSONObject) examScore.get("student");
                Student studentUser = (Student) factory.getUser("student",
                        (String) student.get("firstName"), (String) student.get("lastName"));

                for (Course course : catalog.getAllCourses()) {
                    //System.out.println(course.getProf());
                    if(course.getProf().getFirstName().equals(teacherUser.getFirstName())
                            && course.getProf().getLastName().equals(teacherUser.getLastName())) {
                        teacherUser = course.getProf();
                        //System.out.println(teacherUser);
                    }

                    ArrayList<Student> allStudents = course.getAllStudents();
                    for (Student s: allStudents) {
                        if(s.getFirstName().equals(studentUser.getFirstName())
                                && s.getLastName().equals(studentUser.getLastName())) {
                            studentUser = s;
                    }
                    }

                }

                String course = (String) examScore.get("course");
                Number grade = (Number) examScore.get("grade");

                visitor.addGradeTeach(teacherUser,studentUser,course,grade.doubleValue());

            }
            for (Course course: catalog.getAllCourses()) {
                visitor.visit(course.getProf());
                //System.out.println(visitor.examScores.get(course.getProf()));
                //System.out.println(course.getCourseName() + " " + course.getProf() + " " + course.getAllStudentGrades()+ "\n\n");
            }

            array = (JSONArray) obj.get("partialScores");

            for (Object o : array) {
                JSONObject partialScore = (JSONObject) o;

                JSONObject assistant = (JSONObject) partialScore.get("assistant");
                UserFactory factory = new UserFactory();
                Assistant assistantUser = (Assistant) factory.getUser("assistant",
                        (String) assistant.get("firstName"), (String) assistant.get("lastName"));

                JSONObject student = (JSONObject) partialScore.get("student");
                Student studentUser = (Student) factory.getUser("student",
                        (String) student.get("firstName"), (String) student.get("lastName"));

                for (Course course : catalog.getAllCourses()) {
                    //System.out.println(course.getProf());
                    for (Assistant assist: course.getAssistants()) {
                        if(assist.getFirstName().equals(assistantUser.getFirstName())
                        && assist.getLastName().equals(assistantUser.getLastName())) {
                            //System.out.println(assist.getFirstName() + " " + assist.getLastName());
                            assistantUser = assist;
                        }
                    }

                    ArrayList<Student> allStudents = course.getAllStudents();
                    for (Student s: allStudents) {
                        if(s.getFirstName().equals(studentUser.getFirstName())
                                && s.getLastName().equals(studentUser.getLastName())) {

                            studentUser = s;
                        }
                    }

                }

                String course = (String) partialScore.get("course");
                Number grade = (Number) partialScore.get("grade");

                visitor.addGradeAssist(assistantUser,studentUser,course,grade.doubleValue());

            }
            for (Course course: catalog.getAllCourses()) {
                for (Assistant assistant: course.getAssistants()) {
                    assistant.accept(visitor);
                }
                System.out.println(course.getCourseName() + " " + course.getAllStudentGrades()+ "\n\n");

                //System.out.println(visitor.examScores.get(course.getProf()));

            }

            // similar, incerc memento cu primul curs
            Course c = catalog.getAllCourses().get(0);
            System.out.println("Test memento" + "\n" + c.grades);
            c.makeBackup();
            c.grades.remove(0);
            System.out.println(c.grades);
            c.undo();
            System.out.println(c.grades+ "\n\n");
            // se vede ca a disparut o pereche de note si apoi a reaparut.


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        StudentPage page = new StudentPage("Cristian", "Manole");



    }
}
