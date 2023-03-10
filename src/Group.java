import java.util.*;

// se mosteneste un array de studenti, unde il pun ???
public class Group extends ArrayList<Student> {
    Assistant assistant;
    String id;

    //Collections students;


    public Group(String ID, Assistant assistant, Comparator<Student> comp) {
        this.id = ID;
        this.assistant = assistant;
    }

    public Group(String ID, Assistant assistant) {
        this.id = ID;
        this.assistant = assistant;
    }

    public Assistant getAssistant() {
        return assistant;
    }

    public void setAssistant(Assistant assistant) {
        this.assistant = assistant;
    }
}
