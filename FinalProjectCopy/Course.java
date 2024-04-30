package FinalProjectDummt;

/**
 * @author Lance Cariaga & Gerard Bernados
 */
public class Course implements Comparable<Course> {
    private byte yearLevel;
    private byte term;
    private String courseNumber;
    private String descriptiveTitle;
    private double units;
    private int grade;
    private String preReq;
    private String coReq;
    private boolean isFinished;
    private boolean isAnElective;

    public Course() {
        yearLevel = 1;
        term = 1;
        courseNumber = "CS000";
        descriptiveTitle = "Course";
        units = 0;
        grade = 75;
        preReq = "";
        coReq = "";
        isFinished= false;
        isAnElective=false;

    }//end of Course constructor

    public Course(byte yL, byte t, String c, String dT, double u, int g, String pR, String cR, boolean fin, boolean el){
        yearLevel = yL;
        term = t;
        courseNumber = c;
        descriptiveTitle = dT;
        units = u;
        grade = g;
        preReq = pR;
        coReq = cR;
        isFinished = fin;
        isAnElective = el;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public String getDescriptiveTitle() {
        return descriptiveTitle;
    }

    public double getUnits() {
        return units;
    }

    public byte getTerm() {
        return term;
    }

    public byte getYearLevel() {
        return yearLevel;
    }

    public int getGrade() {
        return grade;
    }

    public void setCourseNumber(String CourseNumber) {
        courseNumber = CourseNumber;
    }

    public void setDescriptiveTitle(String DescriptiveTitle) {
        descriptiveTitle = DescriptiveTitle;
    }

    public void setUnits(byte Units) {
        units = Units;
    }

    public void setTerm(byte Term) {
        term = Term;
    }

    public void setYearLevel(byte YearLevel) {
        yearLevel = YearLevel;
    }

    public void setGrade(int Grade) {
        grade = Grade;
    }

    public String getPreReq() {
        return preReq;
    }

    public void setPreReq(String preReq) {
        this.preReq = preReq;
    }

    public String getCoReq() {
        return coReq;
    }

    public void setCoReq(String coReq) {
        this.coReq = coReq;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public boolean isAnElective() {
        return isAnElective;
    }

    public void setAnElective(boolean anElective) {
        isAnElective = anElective;
    }
    public String toString() {
        return yearLevel +", "+term+", "+courseNumber+", "+descriptiveTitle+", "+units+", "+grade+", "+preReq+", "+coReq+", "+isFinished+", "+isAnElective;
    }

    @Override
    public int compareTo(Course o) {
        return 0;
    }
}

