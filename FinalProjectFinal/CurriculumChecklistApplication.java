package samcis.slu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

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
     * This button handler allows the show button in the GUI to show the courses in the main panel.
     * 
     * @author Gerard Bernados
     */
    private class ShowCoursesButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            showGWAIsClicked = false;
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
     * This button handler allows the user to add a course in the file through the GUI.
     * The added course will be automatically saved in the data file.
     * 
     * @author Ravone Ebeng
     */
    private class AddCourseButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            showGWAIsClicked = false;
            SwingUtilities.invokeLater(() -> {
                String yearStr = null, termStr = null, courseNumber = null, descriptiveTitle = null, unitsStr = null;
                byte year = 0, term = 0;
                double units = 0.0;

                while (yearStr == null) {
                    yearStr = JOptionPane.showInputDialog(mainFrame, "Enter the year (1-4 only):");
                    if (yearStr == null) return; // User clicked cancel
                    try {
                        year = Byte.parseByte(yearStr);
                        if (year < 1 || year > 4) throw new NumberFormatException();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(mainFrame, "Year must be a number between 1 and 4.", "Error", JOptionPane.ERROR_MESSAGE);
                        yearStr = null; // Reset the input
                    }
                }

                while (termStr == null) {
                    termStr = JOptionPane.showInputDialog(mainFrame, "Enter the term (1 = 1st sem, 2 = 2nd sem, 3 = short term):");
                    if (termStr == null) return; // User clicked cancel
                    try {
                        term = Byte.parseByte(termStr);
                        if (term < 1 || term > 3) throw new NumberFormatException();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(mainFrame, "Term must be a number between 1 and 3.", "Error", JOptionPane.ERROR_MESSAGE);
                        termStr = null; // Reset the input
                    }
                }

                while (courseNumber == null || courseNumber.isEmpty()) {
                    courseNumber = JOptionPane.showInputDialog(mainFrame, "Enter the course number:");
                    if (courseNumber == null) return; // User clicked cancel
                    if (courseNumber.isEmpty()) {
                        JOptionPane.showMessageDialog(mainFrame, "Course number cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                while (descriptiveTitle == null || descriptiveTitle.isEmpty()) {
                    descriptiveTitle = JOptionPane.showInputDialog(mainFrame, "Enter the descriptive title:");
                    if (descriptiveTitle == null) return; // User clicked cancel
                    if (descriptiveTitle.isEmpty()) {
                        JOptionPane.showMessageDialog(mainFrame, "Descriptive title cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                while (unitsStr == null) {
                    unitsStr = JOptionPane.showInputDialog(mainFrame, "Enter the units (must be a number):");
                    if (unitsStr == null) return; // User clicked cancel
                    try {
                        units = Double.parseDouble(unitsStr);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(mainFrame, "Units must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                        unitsStr = null; // Reset the input
                    }
                }

                ArrayList<samcis.slu.Course> courses = null;

                try {
                    // Create a new Course object
                    samcis.slu.Course course = new samcis.slu.Course(year, term, courseNumber, descriptiveTitle, units, 0, "", "", false, false);
                    // Add the course to the list of courses
                    try {
                        courses = controller.getCourses();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(mainFrame, "Error getting courses: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    courses.add(course);
                    // Save the updated course list to a file
                    controller.saveCourseListToFile(courses, "dynamic_curriculum_checklist.txt");
                    // Notify the user of successful addition
                    JOptionPane.showMessageDialog(mainFrame, "Course added successfully.", "Add Course", JOptionPane.INFORMATION_MESSAGE);
                    // Display the newly added course
                    displayCourses();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(mainFrame, "Error saving course list to file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }

    /**
     * This button handler allows the user to remove a course displayed in the GUI.
     * The removed course will be automatically saved in the data file.
     * 
     * @author Charles Pecson
     */
    private class RemoveCourseButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String courseToRemove = JOptionPane.showInputDialog(mainFrame, "Enter the course number to remove:");

            if (courseToRemove != null && !courseToRemove.isEmpty()) {
                ArrayList<Course> courses = null;
                try {
                    courses = controller.getCourses();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                boolean courseFound = false;

                for (Course course : courses) {
                    if (course.getCourseNumber().equals(courseToRemove)) {
                        courses.remove(course);
                        courseFound = true;
                        break;
                    }
                }

                if (courseFound) {
                    try {
                        controller.saveCourseListToFile(courses, "dynamic_curriculum_checklist.txt");
                        JOptionPane.showMessageDialog(mainFrame, "Course removed successfully.", "Remove Course", JOptionPane.INFORMATION_MESSAGE);
                        displayCourses();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(mainFrame, "Error saving course list to file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Course not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    /**
     * This button handler allows the user to edit a Descriptive title and the units of the course.
     * The edited course will be automatically save in the data file.
     * 
     * @author Joross Burlas
     */
    private class EditCourseButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            showGWAIsClicked = false;
            SwingUtilities.invokeLater(() -> {
                String courseNumber = JOptionPane.showInputDialog(mainFrame, "Enter the course number to edit:");
                if (courseNumber != null && !courseNumber.isEmpty()) {
                    try {
                        ArrayList<Course> courses = controller.getCourses();
                        if (courses != null) {
                            for (Course course : courses) {
                                if (course.getCourseNumber().equals(courseNumber)) {
                                    String newCourseNumber = JOptionPane.showInputDialog(mainFrame, "Enter the new course number:");
                                    if (newCourseNumber != null && !newCourseNumber.isEmpty()) {
                                        String newDescriptiveTitle = JOptionPane.showInputDialog(mainFrame, "Enter the new descriptive title:");
                                        if (newDescriptiveTitle != null && !newDescriptiveTitle.isEmpty()) {
                                            boolean validUnits = false;
                                            double newUnits = 0.0;
                                            while (!validUnits) {
                                                String newUnitsStr = JOptionPane.showInputDialog(mainFrame, "Enter the new units:");
                                                if (newUnitsStr != null && !newUnitsStr.isEmpty()) {
                                                    try {
                                                        newUnits = Double.parseDouble(newUnitsStr);
                                                        validUnits = true;
                                                    } catch (NumberFormatException ex) {
                                                        JOptionPane.showMessageDialog(mainFrame, "Units must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                                                    }
                                                }
                                            }
                                            course.setCourseNumber(newCourseNumber);
                                            course.setDescriptiveTitle(newDescriptiveTitle);
                                            course.setUnits(newUnits);

                                            // Notify user of successful edit
                                            JOptionPane.showMessageDialog(mainFrame, "Course details updated successfully.", "Edit Course", JOptionPane.INFORMATION_MESSAGE);

                                            // Save the updated course list to a file
                                            try {
                                                controller.saveCourseListToFile(courses, "dynamic_curriculum_checklist.txt");
                                            } catch (IOException ex) {
                                                JOptionPane.showMessageDialog(mainFrame, "Error saving course list to file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                            }

                                            // Update the course details directly on the GUI
                                            updateCourseDetailsOnGUI(course);
                                            displayCourses();
                                            return; // Exit the method after successfully updating course
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
     * This button handler allows the user to show the course with the corresponding grades.
     * The data was read from the data file.
     * 
     * @author Gerard Bernados
     */
    private class ShowGradesButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            showGradesIsClicked = true;
            showCoursesIsClicked = false;
            showGWAIsClicked = false;
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
     * This button handler allows the user to enter a grade in a specific course.
     * The entered grade will be automatically saved in the data file.
     * 
     * @author Gerard Alexander C. Bernados
     */
    private class EnterGradeButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(() -> {
                String courseNumber = JOptionPane.showInputDialog(mainFrame, "Enter the course number to edit the grade:");
                if (courseNumber != null && !courseNumber.isEmpty()) {
                    try {
                        ArrayList<Course> courses = controller.getCourses();
                        if (courses != null) {
                            for (Course course : courses) {
                                if (course.getCourseNumber().equals(courseNumber)) {
                                    boolean validGrade = false;
                                    int grade = 0;
                                    while (!validGrade) {
                                        String newGrade = JOptionPane.showInputDialog(mainFrame, "Enter the new grade (must be a number from 65 to 99):");
                                        if (newGrade != null && !newGrade.isEmpty()) {
                                            try {
                                                grade = Integer.parseInt(newGrade);
                                                if (grade >= 65 && grade <= 99) {
                                                    validGrade = true;
                                                } else {
                                                    JOptionPane.showMessageDialog(mainFrame, "Invalid grade. Please enter a number from 65 to 99.", "Error", JOptionPane.ERROR_MESSAGE);
                                                }
                                            } catch (NumberFormatException ex) {
                                                JOptionPane.showMessageDialog(mainFrame, "Invalid grade. Please enter a number from 65 to 99.", "Error", JOptionPane.ERROR_MESSAGE);
                                            }
                                        }
                                    }
                                    course.setGrade(grade);

                                    // Notify user of successful edit
                                    JOptionPane.showMessageDialog(mainFrame, "Grade updated successfully.", "Edit Grade", JOptionPane.INFORMATION_MESSAGE);

                                    // Update the grade directly on the GUI
                                    updateGradeOnGUI(course);

                                    // Save the updated course list to a file
                                    try {
                                        controller.saveCourseListToFile(courses, "dynamic_curriculum_checklist.txt");
                                    } catch (IOException ex) {
                                        JOptionPane.showMessageDialog(mainFrame, "Error saving course list to file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    }

                                    if (showGWAIsClicked) {
                                        coursesPanel.remove(lineLabel);
                                        coursesPanel.remove(gWALabel);
                                        displayGWA();
                                    }
                                    return; // Exit the method after successfully updating grade
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

    /**
     * This button handler allows the user to sort the data according to the preference of the user.
     * It can be sorted either Ascending or Descending and by Course number, Descriptive Title, Grade, and Units.
     * 
     * @author Lance Cariaga
     */
    private class SortButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Options for sorting criteria
            String[] options = {"Course Number", "Descriptive Title", "Grade", "Number of Units"};

            // Show a dialog to let the user choose the sorting criteria
            String sortBy = (String) JOptionPane.showInputDialog(mainFrame, "Choose sorting criteria:", "Sort", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (sortBy != null) {
                // Options for sorting order
                String[] orderOptions = {"Ascending", "Descending"};

                // Show a dialog to let the user choose the sorting order
                String order = (String) JOptionPane.showInputDialog(mainFrame, "Choose sorting order:", "Sort", JOptionPane.QUESTION_MESSAGE, null, orderOptions, orderOptions[0]);

                if (order != null) {
                    // Determines if the sorting order is ascending
                    boolean ascending = order.equals("Ascending");

                    try {
                        // Sorts the courses based on the chosen criteria (by Course Number, Descriptive Tite, Grade, or Number of Units)
                        // and order (Ascending or Descending order)
                        ArrayList<Course> sortedCourses = controller.sortCourses(controller.getCourses(), sortBy, ascending);

                        // Clears the courses panel
                        coursesPanel.removeAll();
                        coursesPanel.revalidate();
                        coursesPanel.repaint();

                        // Displays the sorted courses
                        if (showGradesIsClicked) {
                            displayCoursesWithGrades(sortedCourses);
                        } else {
                            displayCourses();
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
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
            showGWAIsClicked = false;
            if (!showCoursesIsClicked && !showGradesIsClicked) {
                // DOES NOTHING
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
     * This method display courses from the read file. 
     * 
     * @author Gerard Bernados
     */
    private void displayCourses() {
        coursesPanel.removeAll();
        coursesPanel.revalidate();
        coursesPanel.repaint();
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
            for (int i = 0; i< courses.size(); i++){
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
     * this method displays the read courses with grades from a file to the GUI
     *
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
     * This method is used to display the CS elective courses.
     * If the units of the course are set to 2211, it is displayed as "2 lec / 1 lab".
     *
     * @author Mike Fajardo
     */
    private void displayElectiveCourses() {
        JPanel electivePanel = new JPanel(new GridLayout(23, 1));
        ArrayList<Course> courses = null;
        try {
            courses = controller.getCourses();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (Course course : courses) {
            if (course.isAnElective()) {
                String units;
                if (course.getUnits() == 2211) {
                    units = "2 lec / 1 lab";
                } else {
                    units = String.valueOf(course.getUnits());
                }
                JLabel courseLabel = new JLabel(String.format("%-10s%-45s%20s%n", course.getCourseNumber(), course.getDescriptiveTitle(), units));
                courseLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
                electivePanel.add(courseLabel);
            }
        }
        JOptionPane.showMessageDialog(mainFrame, electivePanel, "CS Electives", JOptionPane.INFORMATION_MESSAGE);
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
        mainFrame.setSize(1250, 670);
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
        sideUpperButtonsPanel.setPreferredSize(new Dimension(200, 140));
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
        shifterPanel.setPreferredSize(new Dimension(200, 390));
        shifterPanel.setBorder(BorderFactory.createEmptyBorder(50, 15, 10, 15));
        shifterPanel.setBackground(new Color(229, 229, 234));

        ifShifterLabel = new JLabel("<html><p>If you are shifter from another program, add your " +
                "finished courses by clicking <b>'Show Courses' > 'Add'</b>.</p>" +
                "<br>" +
                "<br>" +
                "<p>To add an elective, click on <b>'Show Courses'</b>, then <b>'Edit'</b>. " +
                "Input 'CSE <i>n</i>' in the course number field, where 'n' is the number of the elective " +
                "(1 for Elective I, 2 for Elective II, and so on), for example, CSE 1 for Elective I. After " +
                "that, modify the corresponding details as needed.<br></p></html>");

        JLabel electiveLink = new JLabel("<html><a href=''>Show list of CS electives</a></html>");
        electiveLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        shifterPanel.add(electiveLink, BorderLayout.SOUTH);
        electiveLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayElectiveCourses();
            }
        });

        shifterPanel.add(ifShifterLabel, BorderLayout.NORTH);
        shifterPanel.add(electiveLink);

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
        sidePanel.add(shifterPanel, BorderLayout.CENTER);
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
        mainPanel.setPreferredSize(new Dimension(1050, 700));
        mainPanel.setBackground(new Color(242, 242, 247));
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(null);

        headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setPreferredSize(new Dimension(1050, 50));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        headerPanel.setBackground(new Color(242, 242, 247));

        yearAndTermLabel = new JLabel("");
        yearAndTermLabel.setFont(new Font(null, Font.BOLD, 16));

        headerPanel.add(yearAndTermLabel);

        coursesPanel = new JPanel(new GridLayout(20, 1));
        coursesPanel.setPreferredSize(new Dimension(1050, 580));
        coursesPanel.setBackground(Color.WHITE);
        coursesPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(1050, 70));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        bottomPanel.setBackground(new Color(242, 242, 247));

        bottomButtonsPanel = new JPanel(new BorderLayout());
        bottomButtonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 0, 15));
        bottomButtonsPanel.setPreferredSize(new Dimension(1050, 40));
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
        actionButtonsPanel.setPreferredSize(new Dimension(330, 30));
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
