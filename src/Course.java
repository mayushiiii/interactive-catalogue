import java.util.*;

public abstract class Course {

    // dezbatere daca sa las privat sau nu; daca trebuie si e mai
    // bine privat, vor trb get-set-uri
    String courseName;
    Teacher prof;
    Set<Assistant> assistants; //poate va fi set?
    ArrayList<Grade> grades;
    HashMap<String,Group> groups;
    int creditScore;

    String strategyType;
    Strategy strategy;
    Snapshot snapshot = new Snapshot();

    public Course(CourseBuilder builder) {
        this.courseName = builder.courseName;
        this.prof = builder.prof;
        this.creditScore = builder.creditScore;
        this.assistants = builder.assistants;
        this.grades = builder.grades;
        this.groups = builder.groups;
        this.strategyType = builder.strategyType;
    }


    public abstract static class CourseBuilder {
        String courseName;
        Teacher prof;
        Set<Assistant> assistants; //poate va fi set?
        ArrayList<Grade> grades;
        HashMap<String,Group> groups;
        String strategyType;

        Snapshot clone;
        int creditScore;
        // aici o iau pe interpretate: presupun ca daca facem un curs stim
        // numele si proful, apoi realizam constructori separati pt celelalte?
        // o sa pun si pcte credit.
        public CourseBuilder(String courseName, Teacher prof, String strategyType){
            this.courseName = courseName;
            this.prof = prof;
            this.strategyType = strategyType;
        }

        // de intrebat daca aici se ADAUGA sau daca se INITIALIZEAZA.
        public CourseBuilder initAssistants (){
            this.assistants = new HashSet<>();
            return this;
        }

        public CourseBuilder initGrades(){
            this.grades = new ArrayList<>();
            return this;
        }

        public CourseBuilder initGroups(){
            this.groups = new HashMap<>();
            return this;
        }

        public CourseBuilder addCredit(int creditScore){
            this.creditScore = creditScore;
            return this;
        }

        public abstract Course build() ;

    }

    public Set<Assistant> getAssistants() {
        return assistants;
    }

    public void setAssistants(Set<Assistant> assistants) {
        this.assistants = assistants;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Teacher getProf() {
        return prof;
    }

    public void setProf(Teacher prof) {
        this.prof = prof;
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    public void setGrades(ArrayList<Grade> grades) {
        this.grades = grades;
    }

    public HashMap<String, Group> getGroups() {
        return groups;
    }

    public void setGroups(HashMap<String, Group> groups) {
        this.groups = groups;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public String getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(String strategyType) {
        this.strategyType = strategyType;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    // adauga un asistent la lista de asistenti(echipa de asistenti?)
    // aici nu e nevoie de vreun id.
    public void addAssistant(Assistant assistant) {
        assistants.add(assistant);
    }
    // Seteaza asistentul în grupa cu ID-ul indicat
    // Daca nu exista deja, adauga asistentul si în multimea asistentilor
    public void addAssistant(String ID, Assistant assistant) {
        groups.get(ID).setAssistant(assistant);
        // set nu contine duplicate prin definitie. doar se adauga si atat
        assistants.add(assistant);
    }
    // Adauga studentul în grupa cu ID-ul indicat
    public void addStudent(String ID, Student student)
    {
        groups.get(ID).add(student);
    }
    // Adauga grupa
    public void addGroup(Group group) {
        // ????
        groups.put(group.id, group);
    }
    // Instantiaza o grupa si o adauga
    public void addGroup(String ID, Assistant assistant){
        groups.put(ID, new Group(ID,assistant));
    }
    // Instantiaza o grupa si o adauga
    public void addGroup(String ID, Assistant assist, Comparator<Student> comp){
        groups.put(ID, new Group(ID,assist, comp));
        //List<Map.Entry<String,Group>> list = new LinkedList<Map.Entry<String,Group>>(groups.entrySet());
        for (Group g: groups.values()) {
            g.sort(comp);
        }
    }

    // in ce grupa e studentul?
    public Group getGroup(Student student){
        for (Group group: groups.values()) {
            for (Student i : group) {
                if (i.getFirstName().equals(student.getFirstName()) &&
                        i.getLastName().equals(student.getLastName()))
                    return group;
            }
        }
        return null;
    }
    // Returneaza nota unui student sau null
    public Grade getGrade(Student student){
        // se cauta studentul parcurcand grades, se extrage studentul de fiecare data
        // si se compara pana e egal si se arata nota sa finala.
        for(Grade i : grades){
            if(i.getStudent().getLastName().equals(student.getLastName()) && i.getStudent().getFirstName().equals(student.getFirstName()))
                return i;
        }
        return null;
    }
    // Adauga o nota
    public void addGrade(Grade grade){
        grades.add(grade);
    }
    // Returneaza o lista cu toti studentii
    public ArrayList<Student> getAllStudents(){
        // se parcurg grupele, se baga toti intr un array list si gata?
        ArrayList<Student> allStudents = new ArrayList<>();
        for (Group i : groups.values()) {
            allStudents.addAll(i);
        }
        return allStudents;
    }
    // Returneaza un dictionar cu situatia studentilor
    public HashMap<Student, Grade> getAllStudentGrades(){
        // se face hashmap nou, cheile vor fi studentii din functia de mai sus
        // iar notele ???? sau invers???? se afla notele intai apoi studentii???
        // de asemenea de reverificat, logica e wobbly.
        ArrayList<Student> allStudents = getAllStudents();
        HashMap<Student,Grade> studentGrades = new HashMap<>();
        for (Student student: allStudents) {
            studentGrades.put(student,getGrade(student));
        }
        return studentGrades;
    }
    // Metoda ce o sa fie implementata pentru a determina studentii promovati
    public abstract ArrayList<Student> getGraduatedStudents();
        // array list unde se iau notele si se calculeaza nota finala

    public Student getBestStudent() {
        if (strategyType.equals("BestExamScore")) {
            strategy = new BestExamScore();
        }
        if (strategyType.equals("BestPartialScore")) {
            strategy = new BestPartialScore();
        }
        if (strategyType.equals("BestTotalScore")) {
            strategy = new BestTotalScore();
        }
        return strategy.doOperation(grades);

    }

    @Override
    public String toString() {
        return courseName ;
    }

    private class Snapshot{
        ArrayList<Grade> clonedGrades;

        public Snapshot() {
            clonedGrades = new ArrayList<>();
        }

        public ArrayList<Grade> getClonedGrades() {
            return clonedGrades;
        }
    }

    public void makeBackup() throws CloneNotSupportedException {
        for (Grade g :getGrades()) {
            snapshot.clonedGrades.add(g.clone());
        }
    }

    public void undo(){
        grades= snapshot.clonedGrades;

    }


}
// de revazut daca mi plac numele sau nu, se repeta mult si poate sa sune confuz.
class PartialCourse extends Course{

    public PartialCourse(CourseBuilder builder) {
        super(builder);
    }

    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> studentsPassed = new ArrayList<>();
        HashMap<Student,Grade> studentGrades = getAllStudentGrades();
        for (Grade grade: studentGrades.values()) {
            if (grade.getTotal() >= 5)
                studentsPassed.add(grade.getStudent());
        }
        return studentsPassed;
    }

    public static class PartialCourseBuilder extends CourseBuilder {
        public PartialCourseBuilder(String courseName, Teacher prof, String strategyType) {
            super(courseName, prof, strategyType);
        }

        @Override
        public Course build() {
            return new PartialCourse(this);
        }
    }
}

class FullCourse extends Course{

    public FullCourse(CourseBuilder builder) {
        super(builder);
    }

    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> studentsPassed = new ArrayList<>();
        HashMap<Student,Grade> studentGrades = getAllStudentGrades();
        for (Grade grade: studentGrades.values()) {
            if (grade.getPartialScore() >= 3.00 && grade.getExamScore() >= 2.00)
                studentsPassed.add(grade.getStudent());
        }
        return studentsPassed;
    }

    public static class FullCourseBuilder extends CourseBuilder {

        public FullCourseBuilder(String courseName, Teacher prof, String strategyType) {
            super(courseName, prof, strategyType);
        }

        @Override
        public Course build() {
            return new FullCourse(this);
        }
    }
}