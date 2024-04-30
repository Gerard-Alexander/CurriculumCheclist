package FinalProjectDummt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CurriculumChecklistController {

    public static void main(String[] args) {
        CurriculumChecklistController ourProgram = new CurriculumChecklistController();
        new CurriculumChecklistApplication();
        try {
            ourProgram.run();
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }
    private void run() throws Exception {
        int arraySize = countLines("curriculum_checklist.txt");
        Course[] course = new Course[arraySize];
        populateCourseArray("curriculum_checklist.txt", course);
        ArrayList<Course> courses= convertToArrayList(course);
        showCourses(courses);

    }

    /**
     * Computes the General Weighted Average (GWA) for a specific year and term.
     *
     * @author Mike Fajardo
     */
    public double computeGWA(int year, int term) throws Exception {
        double gWA;
        int sumOfGrades = 0;
        double sumOfUnits = 0.0;

        for (Course c : getCourses()) {
            if (c.getYearLevel() == year && c.getTerm() == term) {
                sumOfGrades += c.getGrade();
                sumOfUnits += c.getUnits();
            }
        }
        gWA = sumOfGrades / sumOfUnits;

        return gWA;
    }

    /**
     * @author Gerard Alexander Bernados
     * @return
     * @throws Exception
     */
    public ArrayList<Course> getCourses() throws Exception {
        int arraySize = countLines("curriculum_checklist.txt");
        Course[] course = new Course[arraySize];
        populateCourseArray("curriculum_checklist.txt", course);
        ArrayList<Course> courses= convertToArrayList(course);
        return courses;
    }

    /**
     *
     * @param filename
     * @return
     * @throws FileNotFoundException
     * @throws Exception
     */
    public int countLines(String filename) throws FileNotFoundException, Exception{
        int count = 0;
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()){
            count += 1;
            scanner.nextLine();
        }
        scanner.close();
        return count;
    } // end of countLines method

    /**
     * @author Gerard Alexander Cristal Bernados
     * @param filename
     * @param curriculaArray
     * @throws FileNotFoundException
     * @throws Exception
     */
    public void populateCourseArray(String filename, Course[] curriculaArray) throws FileNotFoundException, Exception{
        Scanner inputReader = new Scanner(new File(filename));
        String line = "";
        int index = -1;
        Course course = null;
        while (inputReader.hasNextLine()){
            index +=1;
            line = inputReader.nextLine();
            String [] data = line.split(",");
            course = new Course(Byte.parseByte(data[0].trim()),Byte.parseByte(data[1].trim()), data[2].trim(), data[3].trim(), Double.parseDouble(data[4].trim()),Integer.parseInt(data[5].trim()), data[6].trim(), data[7].trim(), Boolean.parseBoolean(data[8].trim()), Boolean.parseBoolean(data[9].trim()));
            curriculaArray [index]= course;
        }
    } // end of populateCourseArray method


    public void showCourses(ArrayList<Course> courses) {
        System.out.printf("%-20s%-60s%20s%n","Course Number","Descriptive Title","Units");
        System.out.printf("%-20s%-60s%20s%n","______________","__________________","______________");
        for (int i =0; i< courses.size(); i++){
            System.out.printf("%-20s%-60s%20s%n",courses.get(i).getCourseNumber(), courses.get(i).getDescriptiveTitle(),courses.get(i).getUnits());
        }
    }

    /**
     * @author Gerard Alexander Cristal Bernados
     * @param course
     */
    private ArrayList<Course> convertToArrayList(Course[] course){
        ArrayList<Course> courses= new ArrayList<Course>();
        for (int i =0; i<course.length; i++){
            courses.add(course[i]);
        }
        return courses;
    }

    public void saveCourseListToFile(List<Course> courseList, String fileName) throws IOException {
        List<String> courseStrings = courseList.stream()
                .map(course -> String.format("%d, %d, %s, %s, %.1f, %d, %s, %s, %b, %b",
                        course.getYearLevel(),
                        course.getTerm(),
                        course.getCourseNumber(),
                        course.getDescriptiveTitle(),
                        course.getUnits(),
                        course.getGrade(),
                        course.getPreReq(),
                        course.getCoReq(),
                        course.isFinished(),
                        course.isAnElective()))
                .collect(Collectors.toList());
        String content = String.join("\n", courseStrings);
        Files.write(Paths.get(fileName), content.getBytes());
    }

public ArrayList<Course> sortCourses(ArrayList<Course> courses, String sortBy, boolean ascending) {
        Comparator<Course> comparator;

        switch (sortBy.toLowerCase()) {
            case "descriptive title":
                comparator = Comparator.comparing(Course::getDescriptiveTitle);
                break;
            case "course number":
                comparator = Comparator.comparing(Course::getCourseNumber);
                break;
            case "number of units":
                comparator = Comparator.comparingDouble(Course::getUnits);
                break;
            case "grade":
                comparator = Comparator.comparingInt(Course::getGrade);
                break;
            default:
                throw new IllegalArgumentException("Invalid sort field: " + sortBy);
        }
        //Sorts in descending order if .reversed() is called
        if (!ascending) {
            comparator = comparator.reversed();
        }

        courses.sort(comparator);//sorts the courses based on the comparator

        //prints the sorted courses
        System.out.println("Sorted courses:");
        for (Course course : courses) {
            System.out.println(course);

        }//end of SortCourses method
        return courses;

    }//end of sortCourses method
}
