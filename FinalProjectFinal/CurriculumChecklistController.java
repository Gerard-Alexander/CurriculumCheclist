package FinalProject;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class CurriculumChecklistController {
    public static void main(String[] args) {
        CurriculumChecklistController ourProgram = new CurriculumChecklistController();
        new FinalProject.CurriculumChecklistApplication();
        try {
            ourProgram.run();
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    private void run() throws Exception {
        int arraySize = countLines("dynamic_curriculum_checklist.txt");
        FinalProject.Course[] course = new FinalProject.Course[arraySize];
        populateCourseArray("dynamic_curriculum_checklist.txt", course);
        ArrayList<FinalProject.Course> courses= convertToArrayList(course);
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

        for (FinalProject.Course c : getCourses()) {
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
     */
    public ArrayList<FinalProject.Course> getCourses() throws Exception {
        int arraySize = countLines("dynamic_curriculum_checklist.txt");
        FinalProject.Course[] course = new FinalProject.Course[arraySize];
        populateCourseArray("dynamic_curriculum_checklist.txt", course);
        ArrayList<FinalProject.Course> courses= convertToArrayList(course);
        return courses;
    }

    /**
     * @author Gerard Alexander Bernados
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
     */
    public void populateCourseArray(String filename, FinalProject.Course[] curriculaArray) throws FileNotFoundException, Exception{
        Scanner inputReader = new Scanner(new File(filename));
        String line = "";
        int index = -1;
        FinalProject.Course course = null;
        while (inputReader.hasNextLine()){
            index +=1;
            line = inputReader.nextLine();
            String [] data = line.split(",");
            course = new FinalProject.Course(Byte.parseByte(data[0].trim()),Byte.parseByte(data[1].trim()), data[2].trim(), data[3].trim(), Double.parseDouble(data[4].trim()),Integer.parseInt(data[5].trim()), data[6].trim(), data[7].trim(), Boolean.parseBoolean(data[8].trim()), Boolean.parseBoolean(data[9].trim()));
            curriculaArray [index]= course;
        }
    } // end of populateCourseArray method

    /**
     * @author Gerard Alexander Cristal Bernados
     */
    private ArrayList<FinalProject.Course> convertToArrayList(FinalProject.Course[] course){
        ArrayList<FinalProject.Course> courses= new ArrayList<FinalProject.Course>();
        for (int i =0; i<course.length; i++){
            courses.add(course[i]);
        }
        return courses;
    }

    public void saveCourseListToFile(List<FinalProject.Course> courseList, String fileName) throws IOException {
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

    /**
     * This method sorts the courses based on the given field and order.
     * @author Lance Kenneth G. Cariaga
     * @param courses   the list of courses to be sorted
     * @param sortBy    the field to sort by
     * @param ascending the order of sorting (true for ascending, false for descending)
     * @return the sorted list of courses
     */
    public ArrayList<FinalProject.Course> sortCourses(ArrayList<FinalProject.Course> courses, String sortBy, boolean ascending) {
        Comparator<FinalProject.Course> comparator;

        switch (sortBy.toLowerCase()) {
            case "descriptive title":
                comparator = Comparator.comparing(FinalProject.Course::getDescriptiveTitle);
                break;
            case "course number":
                comparator = Comparator.comparing(FinalProject.Course::getCourseNumber);
                break;
            case "number of units":
                comparator = Comparator.comparingDouble(FinalProject.Course::getUnits);
                break;
            case "grade":
                comparator = Comparator.comparingInt(FinalProject.Course::getGrade);
                break;
            default:
                throw new IllegalArgumentException("Invalid sort field: " + sortBy);
        }

        if (!ascending) {
            comparator = comparator.reversed();
        }
        courses.sort(comparator);//sorts the courses based on the comparator

        // Prints the sorted courses
        System.out.println("Sorted courses:");
        for (FinalProject.Course course : courses) {
            System.out.println(course);
        }
        return courses;
    } // end of SortCourses method
}
