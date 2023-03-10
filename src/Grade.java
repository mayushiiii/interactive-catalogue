import static java.lang.Double.compare;

public class Grade implements Cloneable, Comparable {

    private Double partialScore, examScore;
    private Student student;
    private String course;

    // chiar sunt necesari toti astia???

    public Grade (Double partialScore, Double examScore, Student student, String course)
    {
        this.partialScore = partialScore;
        this.examScore = examScore;
        this.student = student;
        this.course = course;
    }
    public Double getPartialScore() {
        return partialScore;
    }

    public void setPartialScore(Double partialScore) {
        this.partialScore = partialScore;
    }

    public Double getExamScore() {
        return examScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    // intreaba de formula
    public Double getTotal () {
            return partialScore + examScore;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "partialScore=" + partialScore +
                ", examScore=" + examScore +
                '}';
    }



    @Override
    public int compareTo(Object o) {
        Grade obj = (Grade) o;
        // de reumblat aici nu pare chiar cinstit
        return compare(obj.getTotal(), this.getTotal());
    }

    @Override
    public Grade clone() throws CloneNotSupportedException {
        // TODO: copy mutable state here, so the clone can't change the internals of the original

        return (Grade) super.clone();

    }
}
