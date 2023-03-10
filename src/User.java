

public abstract class User {
    private String firstName, lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String toString() {
        return firstName + " " + lastName;
    }
}

class Parent extends User implements Observer {

    public Parent(String firstName, String lastName) {
        super(firstName, lastName);
    }


    @Override
    public void update(Notification notification) {
        System.out.println(notification);
    }
}

class Student extends User {

    private Parent mother, father;

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public void setMother(Parent mother) {
        this.mother = mother;
    }

    public void setFather(Parent father) {
        this.father = father;
    }

    public Parent getFather() {
        return father;
    }

    public Parent getMother() {
        return mother;
    }
}

class Assistant extends User implements Element {

    public Assistant(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class Teacher extends User implements Element {

    public Teacher(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}