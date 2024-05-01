package samcis.slu;


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

    public byte getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(byte yearLevel) {
        this.yearLevel = yearLevel;
    }

    public byte getTerm() {
        return term;
    }

    public void setTerm(byte term) {
        this.term = term;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getDescriptiveTitle() {
        return descriptiveTitle;
    }

    public void setDescriptiveTitle(String descriptiveTitle) {
        this.descriptiveTitle = descriptiveTitle;
    }

    public double getUnits() {
        return units;
    }

    public void setUnits(double units) {
        this.units = units;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
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

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public boolean isAnElective() {
        return isAnElective;
    }

    public void setAnElective(boolean isAnElective) {
        this.isAnElective = isAnElective;
    }

    public String toString() {
        return yearLevel +", "+term+", "+courseNumber+", "+descriptiveTitle+", "+units+", "+grade+", "+preReq+", "+coReq+", "+isFinished+", "+isAnElective;
    }

    @Override
    public int compareTo(Course o) {
        return 0;
    }
}
