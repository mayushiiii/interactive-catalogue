import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScoreVisitor implements Visitor{
    HashMap<Teacher, ArrayList<Tuple<Student,String,Double>>> examScores;
    HashMap<Assistant, ArrayList<Tuple<Student,String,Double>>> partialScores;

    public ScoreVisitor(){
        examScores = new HashMap<>();
        partialScores = new HashMap<>();
    }

    public void addGradeTeach(Teacher teacher, Student student, String courseName, Double grade) {
        if(!examScores.containsKey(teacher)) {
            examScores.put(teacher, new ArrayList<>());

        }
        examScores.get(teacher).add(new Tuple<>(student,courseName,grade));
    }

    public void addGradeAssist(Assistant assistant, Student student, String courseName, Double grade) {
        if(!partialScores.containsKey(assistant)) {
            partialScores.put(assistant, new ArrayList<>());

        }
        partialScores.get(assistant).add(new Tuple<>(student,courseName,grade));
    }

    public HashMap<Assistant, ArrayList<Tuple<Student, String, Double>>> getPartialScores() {
        return partialScores;
    }

    @Override
    public void visit(Assistant assistant) {
        // se parcurg tuplele din hashmapul de note de la asisatenti, care au notele si toate date de asistenti.
        // se cauta cursul corect din courses. apoi se cauta studentul corect din getallsutudents
        // si cand e gasit se updateaza nota.
        Catalog catalog = Catalog.getInstance();

        ArrayList<Tuple<Student,String,Double>> assignedScores = partialScores.get(assistant);
        for (Tuple<Student,String,Double> tuple: assignedScores) {
            Course course = catalog.getCourse(tuple.getVar2());
            ArrayList<Student> allStudents = course.getAllStudents();
            for (Student student: allStudents) {
                if(student.getFirstName().equals(tuple.getVar1().getFirstName())
                        && student.getLastName().equals(tuple.getVar1().getLastName())) {
                    if (course.getGrade(student) == null) {
                        course.addGrade(new Grade(tuple.getVar3(), 0.00, student, course.courseName));
                       /* System.out.println(student.toString() + " " + course.getGrade(student) + course.courseName);*/
                    }

                    else course.getGrade(student).setPartialScore(tuple.getVar3());
                }
            }

            catalog.notifyObservers(course.getGrade(tuple.getVar1()));
        }

    }

    @Override
    public void visit(Teacher teacher) {
        Catalog catalog = Catalog.getInstance();
        ArrayList<Tuple<Student,String,Double>> assignedScores = examScores.get(teacher);
        for (Tuple<Student,String,Double> tuple: assignedScores) {

            Course course = catalog.getCourse(tuple.getVar2());
            // TODO: compara le ca stringuri
            ArrayList<Student> allStudents = course.getAllStudents();
            for (Student student: allStudents) {
                if(student.getFirstName().equals(tuple.getVar1().getFirstName())
                        && student.getLastName().equals(tuple.getVar1().getLastName())) {
                    if (course.getGrade(student) == null) {
                        course.addGrade(new Grade(0.00, tuple.getVar3(), student, course.courseName));
                        //System.out.println(student.toString() + " " + course.getGrade(student) + course.courseName);
                    }
                    else course.getGrade(student).setExamScore(tuple.getVar3());
                }
            }
            catalog.notifyObservers(course.getGrade(tuple.getVar1()));
        }

    }


    private class Tuple<K,V,W> {
        // ca sa para "generic"
        private final K var1;
        private final V var2;
        private final W var3;

        public Tuple(K var1, V var2, W var3){
            this.var1 = var1;
            this.var2 = var2;
            this.var3 = var3;
        }

        public K getVar1() {
            return var1;
        }

        public V getVar2() {
            return var2;
        }

        public W getVar3() {
            return var3;
        }

        @Override
        public String toString() {
            return "Tuple{" +
                    "var1=" + var1 +
                    ", var2=" + var2 +
                    ", var3=" + var3 +
                    '}';
        }
    }
}
