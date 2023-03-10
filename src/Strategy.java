import java.util.ArrayList;

public interface Strategy {
    public Student doOperation(ArrayList <Grade> grades);
}

class BestExamScore implements Strategy {

    @Override
    public Student doOperation(ArrayList<Grade> grades) {
        // e babeste dar lasa asa.
        double max = 0;
        Student bestStudent = null;
        for (Grade grade: grades) {
            if ( grade.getExamScore() > max) {
                max = grade.getExamScore();
                bestStudent = grade.getStudent();
            }
        }
        return bestStudent;
    }
}

class BestTotalScore implements Strategy{

    public Student doOperation(ArrayList<Grade> grades) {
        double max = 0;
        Student bestStudent = null;
        for (Grade grade: grades) {
            if ( grade.getTotal() > max) {
                max = grade.getTotal();
                bestStudent = grade.getStudent();
            }
        }
        return bestStudent;


    }
}

class BestPartialScore implements Strategy {

    public Student doOperation(ArrayList<Grade> grades) {
        double max = 0;
        Student bestStudent = null;
        for (Grade grade: grades) {
            if ( grade.getPartialScore() > max) {
                max = grade.getPartialScore();
                bestStudent = grade.getStudent();
            }
        }
        return bestStudent;
    }

}
