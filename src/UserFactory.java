public class UserFactory {
    public User getUser(String userType, String firstName, String lastName)
    {

        // am gasit asta undeva si imi placea ideea, suna mai permisiv
        // decat sa ti iei mare cearta ca nu ai scris fix cum trebuie
        // daca nu tineam cont de asta lucram cu switch, ar fi aratat mai clean
        if(userType.equalsIgnoreCase("PARENT"))
            return new Parent(firstName, lastName);

        if(userType.equalsIgnoreCase("STUDENT"))
            return new Student(firstName, lastName);

        if(userType.equalsIgnoreCase("ASSISTANT"))
            return new Assistant(firstName, lastName);

        if(userType.equalsIgnoreCase("TEACHER"))
            return new Teacher(firstName, lastName);

        return null;
    }
}
