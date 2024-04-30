package FinalProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 @author Mike Fajardo
 Methods for the handlers are done by other members in the Controller class.
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

    private JLabel yearAndTermLabel, ifShifterLabel;

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

    public CurriculumChecklistApplication() {
        setMainFrame();
        setSidePanel();
        setMainPanel();
        mainFrame.setVisible(true);
    }

    private class ShowCoursesButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            resetYearAndTerm();
            setYearAndTermLabel(currentYear, currentTerm);
            actionButtonsPanel.removeAll();
            actionButtonsPanel.add(addCourseButton);
            actionButtonsPanel.add(removeCourseButton);
            actionButtonsPanel.add(editCourseButton);
            actionButtonsPanel.revalidate();
            actionButtonsPanel.repaint();


        }
    }

    private class AddCourseButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // TO-DO
        }
    }

    private class RemoveCourseButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // TO-DO
        }
    }

    private class EditCourseButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // TO-DO
        }
    }

    private class ShowGradesButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            resetYearAndTerm();
            setYearAndTermLabel(currentYear, currentTerm);
            actionButtonsPanel.removeAll();
            actionButtonsPanel.add(enterGradeButton);
            actionButtonsPanel.add(sortButton);
            actionButtonsPanel.add(showGWAButton);
            actionButtonsPanel.revalidate();
            actionButtonsPanel.repaint();

            // TO-DO
        }
    }

    private class EnterGradeButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // TO-DO
        }
    }

    private class SortButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // TO-DO
            /*Gets the list of the Curriculum by using the populateCurriculumArray method.
            replace "datafile name" with the name of the data file that will be used to get the data for
            the curriculum objects later.
            */
            ArrayList<Curriculum> curriculumList = populateCurriculumArray("datafile name");

            //unfinished
            Comparator<Curriculum> byYearLevel = Comparator.comparing(Curriculum::getYearLevel);
            Comparator<Curriculum> byTerm = Comparator.comparing(Curriculum::getTerm);
            Comparator<Curriculum> byNumberOfUnits = Comparator.comparing(Curriculum::getUnits);

        }
    }

    private class ShowGWAButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // TO-DO
        }
    }

    private class QuitButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class PreviousAndNextButtonsHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(previousButton)) {
                if (currentYear > 1 || currentTerm > 1) {
                    currentTerm--;
                    if (currentTerm < 1) {
                        currentTerm = 3;
                        currentYear--;
                    }
                    setYearAndTermLabel(currentYear, currentTerm);
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
                }
            }
        }
    }

    private void populateCourseArrayList() {
        // TO-DO
    }

    private void addGradesToCoursesInArrayList() {
        // TO-DO
    }

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

    private String getTermText(byte t) {
        String termText = "";
        switch (t) {
            case 1 -> termText = "First Semester";
            case 2 -> termText = "Second Semester";
            case 3 -> termText = "Short Term";
        }
        return termText;
    }

    private void setYearAndTermLabel(byte y, byte t) {
        yearAndTermLabel.setText(getYearText(y) + ", " + getTermText(t));
    }

    private void resetYearAndTerm() {
        currentYear = 1;
        currentTerm = 1;
    }

    private void setMainFrame() {
        mainFrame = new JFrame();
        mainFrame.setLayout(new BorderLayout(0, 0));
        mainFrame.setSize(1000, 670);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setResizable(true);
    }

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

    private void setMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(800, 700));
        mainPanel.setBackground(Color.ORANGE);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(null);

        headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setPreferredSize(new Dimension(800, 50));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        headerPanel.setBackground(new Color(242, 242, 247));

        yearAndTermLabel = new JLabel("");
        yearAndTermLabel.setFont(new Font(null, Font.BOLD, 16));

        headerPanel.add(yearAndTermLabel);

        coursesPanel = new JPanel(new GridLayout(1, 20));
        coursesPanel.setPreferredSize(new Dimension(800, 580));
        coursesPanel.setBackground(Color.WHITE);

        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(800, 70));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        bottomPanel.setBackground(new Color(242, 242, 247));

        bottomButtonsPanel = new JPanel(new BorderLayout());
        bottomButtonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        bottomButtonsPanel.setPreferredSize(new Dimension(800, 40));
        bottomButtonsPanel.setBackground(new Color(242, 242, 247));

        previousNextButtonsPanel = new JPanel();
        previousNextButtonsPanel.setPreferredSize(new Dimension(70, 30));
        previousNextButtonsPanel.setBackground(new Color(242, 242, 247));

        previousAndNextButtonsHandler = new PreviousAndNextButtonsHandler();

        previousButton = new JButton("<");
        previousButton.setPreferredSize(new Dimension(30, 30));
        previousButton.addActionListener(previousAndNextButtonsHandler);
        nextButton = new JButton(">");
        nextButton.setPreferredSize(new Dimension(30, 30));
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

    private int countLines(String filename) throws FileNotFoundException, Exception{
        int count = 0;
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()){
            count += 1;
            scanner.nextLine();
        }
        scanner.close();
        return count;
    } // end of countLines method

    private void populateCurriculumArray(String filename, Curriculum[] curriculaArray) throws FileNotFoundException, Exception{
        Scanner inputReader = new Scanner(new File(filename));
        String line = "";
        int index = -1;
        Curriculum curriculum = null;
        while (inputReader.hasNextLine()){
            index +=1;
            line = inputReader.nextLine();
            String [] data = line.split(",");
            curriculum = new Curriculum();
            curriculaArray [index]= curriculum;
        }
    } // end of populateStudentArray method

    /*
    private void populateCurriculumArray(String filename, Curriculum[] curriculaArray) throws FileNotFoundException, Exception{
        Scanner inputReader = new Scanner(new File(filename));
        String line = "";
        int index = -1;
        Curriculum curriculum = null;
        while (inputReader.hasNextLine()){
            index +=1;
            line = inputReader.nextLine();
            String [] data = line.split(",");
            curriculum = new Curriculum(data[0], data[1], Double.parseDouble(data[2]), data[3], data[4], Double.parseDouble(data[5]), Boolean.parseBoolean(data[6]), Boolean.parseBoolean(data[7]), data[8], data[9]);
            curriculaArray [index]= curriculum;
        }
        inputReader.close();
    }
    */

    private void showCurriculumChecklist(Curriculum[] curriculumArray){
        //TO DO
    }

    private void run() throws Exception {
        int arraySize = countLines("inputfile1.txt");
        FinalProject.Curriculum[] curricula = new FinalProject.Curriculum[arraySize];
        populateCurriculumArray("inputfile1.txt", curricula);
    }

    public static void main(String[] args) {
        FinalProject.CurriculumChecklistApplication ourProgram = new FinalProject.CurriculumChecklistApplication();
        try {
            ourProgram.run();
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }
}
