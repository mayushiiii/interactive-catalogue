import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;

class StudentPage extends JFrame implements ListSelectionListener {

    JList<Course> listCourses;
    JList<Assistant> assistants;
    JTextField username,assistant, teacher;
    JTable grades;
    JPanel panel;
    JScrollPane scrollPane;

    DefaultTableModel model;
    DefaultListModel<Assistant> assistantDefaultListModel;
    Student loggedStudent;
    public StudentPage(String firstName, String lastName) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1000,1000);
        setBackground(new Color(47,67,99));
        setForeground(Color.white);
        // din privacy reasons un student isi vede doar notele sale.
        // deci hai sa vedem acele cursuri in care e inrolat, si luam datele

        //ArrayList<Course> studentCourses = new ArrayList<>();
        //getContentPane().setLayout(new GridBagLayout());
        DefaultListModel<Course> courseDefaultListModel = new DefaultListModel<>();
        assistantDefaultListModel = new DefaultListModel<>();
        loggedStudent = (Student) new UserFactory().getUser("student", firstName, lastName);
        Catalog catalog = Catalog.getInstance();
        for (Course course: catalog.getAllCourses()) {
            ArrayList<Student> allStudents = course.getAllStudents();
            for (Student student: allStudents) {
                if(student.getFirstName().equals(firstName) && student.getLastName().equals(lastName)){
                    //studentCourses.add(course);
                    courseDefaultListModel.addElement(course);

                }
            }
            //System.out.println(course.courseName + " " + course.getGrade(loggedStudent));
        }

        username = new JTextField(20);
        username.setBackground(new Color (50,78,122));
        username.setForeground(Color.white);
        username.setText(loggedStudent.toString());
        username.setSize(20,5);
        username.setEditable(false);
        add(username,BorderLayout.NORTH);

        /*scrollPane = new JScrollPane();
        scrollPane.setViewportView(listCourses);
        add(scrollPane);*/



        listCourses = new JList<Course>(courseDefaultListModel);
        listCourses.setBackground(new Color(71, 104, 150));
        listCourses.setForeground(Color.white);
        listCourses.setLayoutOrientation(JList.VERTICAL);
        listCourses.addListSelectionListener(this);
        add(new JScrollPane(listCourses),BorderLayout.WEST);

        panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color (50,78,122));
        panel.setForeground(Color.white);
        BorderLayout borderLayout = new BorderLayout();
        Border border = new LineBorder(Color.black);

        GridBagConstraints c = new GridBagConstraints();
        assistants = new JList<Assistant>(assistantDefaultListModel);
        assistants.setBackground(new Color(25, 44, 71));
        assistants.setForeground(Color.white);
        assistants.setBorder(border);
        assistants.setFixedCellHeight(20);
        assistants.setFixedCellWidth(150);
        assistants.setLayoutOrientation(JList.VERTICAL);
        assistant = new JTextField(20);
        assistant.setBackground(new Color(25, 44, 71));
        assistant.setForeground(Color.white);
        assistant.setBorder(border);
        assistant.setHorizontalAlignment(JTextField.CENTER);
        //assistant.setForeground(Color.lightGray);
        teacher = new JTextField(20);
        teacher.setBackground(new Color(25, 44, 71));
        teacher.setForeground(Color.white);
        teacher.setBorder(border);
        teacher.setHorizontalAlignment(JTextField.CENTER);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(teacher,c);
        c.gridy = 2;
        panel.add(assistant,c);
        c.gridy = 3;
        panel.add(new JScrollPane(assistants),c);
        model = new DefaultTableModel();
        model.addColumn("nota examen");
        model.addColumn("nota parcurs");
        grades = new JTable(model);
        grades.setBackground(new Color(12, 26, 46));
        grades.setForeground(Color.lightGray);
        grades.setBorder(border);
        c.gridy = 4;
        panel.setBorder(border);
        panel.add(new JScrollPane(grades),c);

        add(panel,BorderLayout.CENTER);

        setVisible(true);

    }



/*
public static void main(String[] args) {
        StudentPage obj = new StudentPage("Gigel", "Frone");
    }
*/

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(listCourses.isSelectionEmpty())
            return;
        model.setRowCount(0);
        //System.out.println(loggedStudent + " " + listCourses.getSelectedValue().courseName + " "+ listCourses.getSelectedValue().getGrade(loggedStudent));
        JTextField grade = new JTextField();
        model.addRow(new Object[]{listCourses.getSelectedValue().getGrade(loggedStudent).getExamScore(),
                listCourses.getSelectedValue().getGrade(loggedStudent).getPartialScore()});

        assistantDefaultListModel.clear();
        for (Assistant a : listCourses.getSelectedValue().getAssistants()) {
            assistantDefaultListModel.addElement(a);
        }
        assistant.setText("asistent grupa " + listCourses.getSelectedValue().getGroup(loggedStudent).getAssistant());
        teacher.setText("profesor " + listCourses.getSelectedValue().getProf());


            /*grade.setText(listCourses.getSelectedValue().getGrade(loggedStudent).getExamScore().toString());
            grades.add(grade);
            grade.setText(listCourses.getSelectedValue().getGrade(loggedStudent).getPartialScore().toString());
            grades.add(grade);*/
        /*JTable assistant = new JTextField();*/
       /* int i = 0;
        System.out.println(loggedStudent);
        for (Assistant a : listCourses.getSelectedValue().getAssistants()) {
            System.out.println(a);
            model.addRow(new Object[] {a, i});
            i++;
        }*/



        //assistant.setText(listCourses.getSelectedValue().getAssistants().toString());



    }
}
