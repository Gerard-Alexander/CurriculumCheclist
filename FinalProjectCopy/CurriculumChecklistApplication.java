package FinalProjectDummt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;


/**
 * This class provides a graphical user interface for users to interact with the application.
 */
public class CurriculumChecklistApplication {
    private JFrame mainFrame;

    private JPanel sidePanel, sideUpperButtonsPanel, shifterPanel, sideLowerButtonsPanel;
    private JPanel mainPanel, headerPanel, coursesPanel, bottomPanel;
    private JPanel bottomButtonsPanel, actionButtonsPanel, previousNextButtonsPanel;

    private JButton showCoursesButton, showGradesButton, quitButton;
    private JButton addCourseButton, removeCourseButton, editCourseButton;
    private JButton enterGradeButton, sortButton, showGWAButton;
    private JButton previousButton, nextButton;

    private JLabel yearAndTermLabel, ifShifterLabel, lineLabel, gWALabel;

    private ShowCoursesButtonHandler showCoursesButtonHandler;
    private ShowGradesButtonHandler showGradesButtonHandler;
    private QuitButtonHandler quitButtonHandler;
    private PreviousAndNextButtonsHandler previousAndNextButtonsHandler;
    private AddCourseButtonHandler addCourseButtonHandler;
    private RemoveCourseButtonHandler removeCourseButtonHandler;
    private EditCourseButtonHandler editCourseButtonHandler;
    private EnterGradeButtonHandler enterGradeButtonHandler;
    private SortButtonHandler sortButtonHandler;
    private ShowGWAButtonHandler showGWAButtonHandler;

    private byte currentYear = 1;
    private byte currentTerm = 1;

    private boolean showGradesIsClicked = false;
    private boolean showCoursesIsClicked = false;
    private boolean showGWAIsClicked = false;

    CurriculumChecklistController controller = new CurriculumChecklistController();

    /**
     * Constructor for the CurriculumChecklistApplication class.
     * It initializes the main frame, side panel, and main panel of the application.
     * It also sets the main frame to be visible.
     * 
     * @author Mike Fajardo
     */
    public CurriculumChecklistApplication() {
        setMainFrame();
        setSidePanel();
        setMainPanel();
        mainFrame.setVisible(true);
    }

    /**
     * @author Gerard Bernados
     */
    private class ShowCoursesButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            showCoursesIsClicked = true;
            showGradesIsClicked = false;
            resetYearAndTerm();
            setYearAndTermLabel(currentYear, currentTerm);
            actionButtonsPanel.removeAll();
            actionButtonsPanel.add(addCourseButton);
            actionButtonsPanel.add(removeCourseButton);
            actionButtonsPanel.add(editCourseButton);
            actionButtonsPanel.revalidate();
            actionButtonsPanel.repaint();
            coursesPanel.removeAll();
            coursesPanel.repaint();
            coursesPanel.revalidate();
            displayCourses();
        }
    }

    /**
     * @author Ravone Ebeng
     */
    private class AddCourseButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(() -> {
                String courseNumber = JOptionPane.showInputDialog(mainFrame, "Enter the course number:");
                if (courseNumber != null && !courseNumber.isEmpty()) {
                    String descriptiveTitle = JOptionPane.showInputDialog(mainFrame, "Enter the descriptive title:");
                    if (descriptiveTitle != null && !descriptiveTitle.isEmpty()) {
                        String unitsStr = JOptionPane.showInputDialog(mainFrame, "Enter the units (must be a number):");
                        if (unitsStr != null && !unitsStr.isEmpty()) {
                            try {
                                double units = Double.parseDouble(unitsStr);
                                // Create a new Course object
                                Course course = new Course((byte)1, (byte)1, courseNumber, descriptiveTitle, units, 0, "", "", false, false);
                                // Add the course to the list of courses

                                // Notify the user of successful addition
                                JOptionPane.showMessageDialog(mainFrame, "Course added successfully.", "Add Course", JOptionPane.INFORMATION_MESSAGE);
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(mainFrame, "Units must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            });
        }
    }

    private class RemoveCourseButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // TO-DO
        }
    }

    /**
     * @author Joross Burlas
     */
    private class EditCourseButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(() -> {
                String courseNumber = JOptionPane.showInputDialog(mainFrame, "Enter the course number to edit:");
                if (courseNumber!= null &&!courseNumber.isEmpty()) {
                    try {
                        ArrayList<Course> courses = controller.getCourses();
                        if (courses!= null) {
                            for (Course course : courses) {
                                if (course.getCourseNumber().equals(courseNumber)) {
                                    String newDescriptiveTitle = JOptionPane.showInputDialog(mainFrame, "Enter the new descriptive title:");
                                    if (newDescriptiveTitle!= null &&!newDescriptiveTitle.isEmpty()) {
                                        String unitsStr = JOptionPane.showInputDialog(mainFrame, "Enter the new units (must be a number):");
                                        if (unitsStr!= null &&!unitsStr.isEmpty()) {
                                            try {
                                                byte units = Byte.parseByte(unitsStr);
                                                course.setDescriptiveTitle(newDescriptiveTitle);
                                                course.setUnits(units);

                                                // Notify user of successful edit
                                                JOptionPane.showMessageDialog(mainFrame, "Course details updated successfully.", "Edit Course", JOptionPane.INFORMATION_MESSAGE);

                                                // Update the course details directly on the GUI
                                                updateCourseDetailsOnGUI(course);

                                                // Save the updated course list to a file
                                                try {
                                                    controller.saveCourseListToFile(courses, "curriculum_checklist.txt"); // Pass false to prevent resetting the file
                                                } catch (IOException ex) {
                                                    JOptionPane.showMessageDialog(mainFrame, "Error saving course list to file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                                }

                                                return; // Exit the method after successfully updating course
                                            } catch (NumberFormatException ex) {
                                                // Handle number format exception
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }

        private void updateCourseDetailsOnGUI(Course editedCourse) {
            // Find and update the edited course in the courses panel
            Component[] components = coursesPanel.getComponents();
            for (Component component : components) {
                if (component instanceof JLabel) {
                    JLabel label = (JLabel) component;
                    String text = label.getText();
                    if (text.contains(editedCourse.getCourseNumber())) {
                        String updatedCourseDetails = String.format("%-20s%-90s%20s%n", editedCourse.getCourseNumber(), editedCourse.getDescriptiveTitle(), editedCourse.getUnits());
                        label.setText(updatedCourseDetails);
                        break; // Exit loop after updating the course
                    }
                }
            }
        }
    }

    /**
     * @author Gerard Bernados
     */
    private class ShowGradesButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            showGradesIsClicked = true;
            showCoursesIsClicked = false;
            resetYearAndTerm();
            setYearAndTermLabel(currentYear, currentTerm);
            actionButtonsPanel.removeAll();
            actionButtonsPanel.add(enterGradeButton);
            actionButtonsPanel.add(sortButton);
            actionButtonsPanel.add(showGWAButton);
            actionButtonsPanel.revalidate();
            actionButtonsPanel.repaint();
            coursesPanel.removeAll();
            coursesPanel.repaint();
            coursesPanel.revalidate();
            displayCoursesWithGrades(null);
        }
    }

    /**
     * @author Gerard Alexander C. Bernados
     */
    private class EnterGradeButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(() -> {
                String courseNumber = JOptionPane.showInputDialog(mainFrame, "Enter the course number to edit the grade:");
                if (courseNumber!= null &&!courseNumber.isEmpty()) {
                    try {
                        ArrayList<Course> courses = controller.getCourses();
                        if (courses!= null) {
                            for (Course course : courses) {
                                if (course.getCourseNumber().equals(courseNumber)) {
                                    String newGrade = JOptionPane.showInputDialog(mainFrame, "Enter the new grade (must be a number between 0 and 100):");
                                    if (newGrade!= null &&!newGrade.isEmpty()) {
                                        try {
                                            int grade = Integer.parseInt(newGrade);
                                            if (grade >= 0 && grade <= 100) {
                                                course.setGrade(grade);

                                                // Notify user of successful edit
                                                JOptionPane.showMessageDialog(mainFrame, "Grade updated successfully.", "Edit Grade", JOptionPane.INFORMATION_MESSAGE);

                                                // Update the grade directly on the GUI
                                                updateGradeOnGUI(course);

                                                // Save the updated course list to a file
                                                try {
                                                    controller.saveCourseListToFile(courses, "curriculum_checklist.txt"); // Pass false to prevent resetting the file
                                                } catch (IOException ex) {
                                                    JOptionPane.showMessageDialog(mainFrame, "Error saving course list to file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                                }

                                                if (showGWAIsClicked) {
                                                    coursesPanel.remove(lineLabel);
                                                    coursesPanel.remove(gWALabel);
                                                    displayGWA();
                                                }

                                                return; // Exit the method after successfully updating grade
                                            } else {
                                                JOptionPane.showMessageDialog(mainFrame, "Invalid grade. Please enter a number between 0 and 100.", "Edit Grade", JOptionPane.ERROR_MESSAGE);
                                            }
                                        } catch (NumberFormatException ex) {
                                            // Handle number format exception
                                            JOptionPane.showMessageDialog(mainFrame, "Invalid grade. Please enter a number between 0 and 100.", "Edit Grade", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }

        private void updateGradeOnGUI(Course editedCourse) {
            // Find and update the edited course in the courses panel
            Component[] components = coursesPanel.getComponents();
            for (Component component : components) {
                if (component instanceof JLabel) {
                    JLabel label = (JLabel) component;
                    String text = label.getText();
                    if (text.contains(editedCourse.getCourseNumber())) {
                        String updatedCourseDetails = String.format("%-20s%-90s%s%15s%n", editedCourse.getCourseNumber(), editedCourse.getDescriptiveTitle(), editedCourse.getUnits(), editedCourse.getGrade());
                        label.setText(updatedCourseDetails);
                        break; // Exit loop after updating the grade
                    }
                }
            }
        }
    }

    private class SortButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        }
    }

    /**
     * Handles the Show GWA button.
     *
     * @author Mike Evander Fajardo
     */
    private class ShowGWAButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            showGWAIsClicked = true;
            coursesPanel.removeAll();
            displayCoursesWithGrades(null);
            displayGWA();
        }
    }

    /**
     * Called the computeGWA method and displays the return value on the coursesPanel.
     *
     * @author Mike Evander Fajardo
     */
    public void displayGWA() {
        try {
            String lines = String.format("%-20s%-90s%s%15s%n","———————————","————————————————————————————","—————————","—————————");
            String gWAText = String.format("%-20s%-90s%s%15.2f", "", "",
                    "GWA:", controller.computeGWA(currentYear, currentTerm));
            lineLabel = new JLabel(lines);
            lineLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
            gWALabel = new JLabel(gWAText);
            gWALabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
            coursesPanel.add(lineLabel);
            coursesPanel.add(gWALabel);
            coursesPanel.revalidate();
            coursesPanel.repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private class QuitButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    /**
     * Called the computeGWA method and displays the return value on the coursesPanel.
     *
     * @author Mike Evander Fajardo
     */
    private class PreviousAndNextButtonsHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!showCoursesIsClicked && !showGradesIsClicked) {

            } else {
                coursesPanel.removeAll();
                coursesPanel.revalidate();
                coursesPanel.repaint();
                if (e.getSource().equals(previousButton)) {
                    if (currentYear > 1 || currentTerm > 1) {
                        currentTerm--;
                        if (currentTerm < 1) {
                            currentTerm = 3;
                            currentYear--;
                        }
                        setYearAndTermLabel(currentYear, currentTerm);
                        displayCoursesInfo();
                    }
                } else if (e.getSource().equals(nextButton)) {
                    if (currentYear < 4 || (currentYear == 4 && currentTerm < 2) || currentTerm < 3) {
                        currentTerm++;
                        if (currentYear < 4 && currentTerm > 3) {
                            currentTerm = 1;
                            currentYear++;
                        } else if (currentYear == 4 && currentTerm > 2) {
                            currentTerm = 2;
                        }
                        setYearAndTermLabel(currentYear, currentTerm);
                        displayCoursesInfo();
                    }
                }
            }
        }

        public void displayCoursesInfo() {
            if (!showGradesIsClicked) {
                displayCourses();
            } else {
                displayCoursesWithGrades(null);
            }
        }
    }

    /**
     * @author Gerard Bernados
     */
    private void displayCourses() {
        JLabel labelOfCourse = new JLabel();
        JLabel lines = new JLabel();
        try {
            ArrayList<Course> courses= new ArrayList<Course>(controller.getCourses());
            String stringLabelOfCourse = String.format("%-20s%-90s%20s%n","Course Number","Descriptive Title","Units");
            String stringLines = String.format("%-20s%-90s%20s%n","___________","____________________________","_________");
            labelOfCourse.setText(stringLabelOfCourse);
            lines.setText(stringLines);
            labelOfCourse.setFont(new Font("Monospaced", Font.PLAIN, 12));
            lines.setFont(new Font("Monospaced", Font.PLAIN, 12));
            labelOfCourse.setHorizontalAlignment(JLabel.LEFT);
            lines.setHorizontalAlignment(JLabel.LEFT);
            coursesPanel.add(labelOfCourse);
            coursesPanel.add(lines);
            for (int i =0; i< courses.size(); i++){
                if (currentTerm == courses.get(i).getTerm() && currentYear == courses.get(i).getYearLevel()) {
                    JLabel nameOfCourses = new JLabel();
                    String title = cutOffString(courses.get(i).getDescriptiveTitle());
                    String courseDetails = String.format("%-20s%-90s%20s%n",courses.get(i).getCourseNumber(),
                            title,courses.get(i).getUnits());
                    nameOfCourses.setText(courseDetails);
                    nameOfCourses.setFont(new Font("Monospaced", Font.PLAIN, 12));
                    nameOfCourses.setHorizontalAlignment(JLabel.LEFT);
                    coursesPanel.add(nameOfCourses);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * @author Gerard Bernados
     */
    private void displayCoursesWithGrades(ArrayList<Course> list) {
        ArrayList<Course> courses;
        try {
            if (list == null) {
                courses = new ArrayList<Course>(controller.getCourses());
            } else {
                courses = list;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        JLabel labelOfCourse = new JLabel();
        JLabel lines = new JLabel();
        String stringLabelOfCourse = String.format("%-20s%-90s%s%15s%n","Course Number","Descriptive Title","Units","Grade");
        String stringLines = String.format("%-20s%-90s%s%15s%n","___________","____________________________","_________","_________");
        labelOfCourse.setText(stringLabelOfCourse);
        lines.setText(stringLines);
        labelOfCourse.setFont(new Font("Monospaced", Font.PLAIN, 12));
        lines.setFont(new Font("Monospaced", Font.PLAIN, 12));
        labelOfCourse.setHorizontalAlignment(JLabel.LEFT);
        lines.setHorizontalAlignment(JLabel.LEFT);
        coursesPanel.add(labelOfCourse);
        coursesPanel.add(lines);
        for (int i =0; i< courses.size(); i++){
            if (currentTerm == courses.get(i).getTerm() && currentYear == courses.get(i).getYearLevel()) {
                JLabel nameOfCourses = new JLabel();
                String title = cutOffString(courses.get(i).getDescriptiveTitle());
                String stringNameOfCourses = String.format("%-20s%-90s%s%15s%n",courses.get(i).getCourseNumber(),
                        title,courses.get(i).getUnits(), courses.get(i).getGrade());
                nameOfCourses.setText(stringNameOfCourses);
                nameOfCourses.setFont(new Font("Monospaced", Font.PLAIN, 12));
                nameOfCourses.setHorizontalAlignment(JLabel.LEFT);
                coursesPanel.add(nameOfCourses);
            }
        }
    }

    /**
     * Truncates a string to a maximum length of 80 characters. If the string is longer than 80 characters,
     * it will be cut off at the 77th character and "..." will be appended to the end.
     *
     * @author Mike Evander Fajardo
     */
    public String cutOffString(String str) {
        if (str.length() > 80) {
            return str.substring(0, 77) + "...";
        } else {
            return str;
        }
    }

    /**
     * Returns the year text based on the year number.
     *
     * @author Mike Evander Fajardo
     */
    private String getYearText(byte y) {
        String yearText = "";
        switch (y) {
            case 1 -> yearText = "First Year";
            case 2 -> yearText = "Second Year";
            case 3 -> yearText = "Third Year";
            case 4 -> yearText = "Fourth Year";
        }
        return yearText;
    }

    /**
     * Returns the term text based on the term number.
     *
     * @author Mike Evander Fajardo
     */
    private String getTermText(byte t) {
        String termText = "";
        switch (t) {
            case 1 -> termText = "First Semester";
            case 2 -> termText = "Second Semester";
            case 3 -> termText = "Short Term";
        }
        return termText;
    }

    /**
     * Sets the year and term label text.
     *
     * @author Mike Evander Fajardo
     */
    private void setYearAndTermLabel(byte y, byte t) {
        yearAndTermLabel.setText(getYearText(y) + ", " + getTermText(t));
    }

    /**
     * Resets the year and term to 1.
     *
     * @author Mike Evander Fajardo
     */
    private void resetYearAndTerm() {
        currentYear = 1;
        currentTerm = 1;
    }

    /**
     * Sets up the main frame of the application.
     *
     * @author Mike Evander Fajardo
     */
    private void setMainFrame() {
        mainFrame = new JFrame();
        mainFrame.setLayout(new BorderLayout(0, 0));
        mainFrame.setBackground(new Color(242, 242, 247));
        mainFrame.setSize(1200, 670);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setResizable(true);
    }

    /**
     * Sets up the side panel of the application.
     *
     * @author Mike Evander Fajardo
     */
    private void setSidePanel() {
        sidePanel = new JPanel();
        sidePanel.setLayout(new BorderLayout());
        sidePanel.setPreferredSize(new Dimension(200, 700));
        sidePanel.setBackground(new Color(229, 229, 234));

        sideUpperButtonsPanel = new JPanel();
        sideUpperButtonsPanel.setPreferredSize(new Dimension(200, 300));
        sideUpperButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        sideUpperButtonsPanel.setBackground(new Color(229, 229, 234));

        showCoursesButton = new JButton("Show Courses");
        showCoursesButtonHandler = new ShowCoursesButtonHandler();
        showCoursesButton.addActionListener(showCoursesButtonHandler);
        showGradesButton = new JButton("Show Grades");
        showGradesButtonHandler = new ShowGradesButtonHandler();
        showGradesButton.addActionListener(showGradesButtonHandler);

        showCoursesButton.setPreferredSize(new Dimension(150, 50));
        showGradesButton.setPreferredSize(new Dimension(150, 50));

        sideUpperButtonsPanel.add(Box.createRigidArea(new Dimension(200, 10)));
        sideUpperButtonsPanel.add(showCoursesButton);
        sideUpperButtonsPanel.add(Box.createRigidArea(new Dimension(200, 10)));
        sideUpperButtonsPanel.add(showGradesButton);

        shifterPanel = new JPanel(new BorderLayout());
        shifterPanel.setPreferredSize(new Dimension(200, 330));
        shifterPanel.setBorder(BorderFactory.createEmptyBorder(50, 15, 10, 15));
        shifterPanel.setBackground(new Color(229, 229, 234));

        ifShifterLabel = new JLabel("<html><p>If you are shifter from another program, add your " +
                "finished courses by clicking <b>'Show Courses' > 'Add'</b>.</p></html>");

        shifterPanel.add(ifShifterLabel, BorderLayout.NORTH);

        sideLowerButtonsPanel = new JPanel();
        sideLowerButtonsPanel.setPreferredSize(new Dimension(200, 70));
        sideLowerButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        sideLowerButtonsPanel.setBackground(new Color(229, 229, 234));

        quitButton = new JButton("Quit");
        quitButtonHandler = new QuitButtonHandler();
        quitButton.addActionListener(quitButtonHandler);
        quitButton.setPreferredSize(new Dimension(150, 40));

        sideLowerButtonsPanel.add(quitButton);
        sideLowerButtonsPanel.add(Box.createRigidArea(new Dimension(200, 15)));

        sidePanel.add(sideUpperButtonsPanel, BorderLayout.NORTH);
        sidePanel.add(shifterPanel);
        sidePanel.add(sideLowerButtonsPanel, BorderLayout.SOUTH);
        mainFrame.add(sidePanel, BorderLayout.WEST);
    }

    /**
     * Sets up the main panel of the application.
     *
     * @author Mike Evander Fajardo
     */
    private void setMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(1000, 700));
        mainPanel.setBackground(Color.ORANGE);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(null);

        headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setPreferredSize(new Dimension(1000, 50));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        headerPanel.setBackground(new Color(242, 242, 247));

        yearAndTermLabel = new JLabel("");
        yearAndTermLabel.setFont(new Font(null, Font.BOLD, 16));

        headerPanel.add(yearAndTermLabel);

        coursesPanel = new JPanel(new GridLayout(20, 1));
        coursesPanel.setPreferredSize(new Dimension(1000, 580));
        coursesPanel.setBackground(Color.WHITE);
        coursesPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(1000, 70));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        bottomPanel.setBackground(new Color(242, 242, 247));

        bottomButtonsPanel = new JPanel(new BorderLayout());
        bottomButtonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 0, 15));
        bottomButtonsPanel.setPreferredSize(new Dimension(1000, 40));
        bottomButtonsPanel.setBackground(new Color(242, 242, 247));

        previousNextButtonsPanel = new JPanel(new GridLayout(1, 2));
        previousNextButtonsPanel.setPreferredSize(new Dimension(100, 30));
        previousNextButtonsPanel.setBackground(new Color(242, 242, 247));

        previousAndNextButtonsHandler = new PreviousAndNextButtonsHandler();

        previousButton = new JButton("<");
        previousButton.addActionListener(previousAndNextButtonsHandler);
        nextButton = new JButton(">");
        nextButton.addActionListener(previousAndNextButtonsHandler);

        previousNextButtonsPanel.add(previousButton);
        previousNextButtonsPanel.add(nextButton);

        actionButtonsPanel = new JPanel(new GridLayout(1, 3));
        actionButtonsPanel.setPreferredSize(new Dimension(270, 30));
        actionButtonsPanel.setBackground(new Color(242, 242, 247));

        addCourseButton = new JButton("Add");
        addCourseButtonHandler = new AddCourseButtonHandler();
        addCourseButton.addActionListener(addCourseButtonHandler);

        removeCourseButton = new JButton("Remove");
        removeCourseButtonHandler = new RemoveCourseButtonHandler();
        removeCourseButton.addActionListener(removeCourseButtonHandler);

        editCourseButton = new JButton("Edit");
        editCourseButtonHandler = new EditCourseButtonHandler();
        editCourseButton.addActionListener(editCourseButtonHandler);

        enterGradeButton = new JButton("Enter Grade");
        enterGradeButtonHandler = new EnterGradeButtonHandler();
        enterGradeButton.addActionListener(enterGradeButtonHandler);

        showGWAButton = new JButton("Show GWA");
        showGWAButtonHandler = new ShowGWAButtonHandler();
        showGWAButton.addActionListener(showGWAButtonHandler);

        sortButton = new JButton("Sort");
        sortButtonHandler = new SortButtonHandler();
        sortButton.addActionListener(sortButtonHandler);

        bottomButtonsPanel.add(previousNextButtonsPanel, BorderLayout.WEST);
        bottomButtonsPanel.add(actionButtonsPanel, BorderLayout.EAST);
        bottomPanel.add(bottomButtonsPanel, BorderLayout.NORTH);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(coursesPanel);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainFrame.add(mainPanel, BorderLayout.EAST);
    }
}
