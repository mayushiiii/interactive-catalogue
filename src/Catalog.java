
import java.util.ArrayList;

public class Catalog implements Subject{
    ArrayList<Course> courses;

    private static Catalog catalog;
    ArrayList<Observer> observingParents;
    Grade observedGrade;

    Catalog() {
        courses = new ArrayList<>();
        observingParents = new ArrayList<>();
    };



    public static Catalog getInstance() {
        if (catalog == null)
        {
            catalog = new Catalog();

        }
        return catalog;
    }

    public void addCourse(Course course){
        Catalog.getInstance().courses.add(course);
    }

    public void removeCourse(Course course){
        Catalog.getInstance().courses.remove(course);
    }

    public Course getCourse(String name) {
        for (Course course: Catalog.getInstance().courses) {
            if (course.getCourseName().equals(name))
                return course;
        }
        return null;
    }

    public ArrayList<Course> getAllCourses () {
        return Catalog.getInstance().courses;
    }

    public ArrayList<Observer> getObservingParents() {
        return Catalog.getInstance().observingParents;
    }

    @Override
    public void addObserver(Observer observer) {
        if(observer!=null)
            Catalog.getInstance().observingParents.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        Catalog.getInstance().observingParents.remove(observer);
    }

    @Override
    public void notifyObservers(Grade grade) {
        this.observedGrade = grade;
        for (Observer parent: observingParents) {
            if(parent.equals(grade.getStudent().getFather()) || parent.equals(grade.getStudent().getMother())) {
                parent.update(new Notification(grade));
            }
        }
    }
}
