package FinalProject;

public class Curriculum implements Comparable<Curriculum>{
    private String courseNumber;
    private String descriptiveTitle;
    private double units;
    private String term;
    private String yearLevel;
    private double grade;
    private boolean isElective;
    private boolean isFinished;
    private String preReq;
    private String coReq;

    public Curriculum(){
        courseNumber = "CS000";
        descriptiveTitle = "Course";
        units = 0;
        term = "1st Semester";
        yearLevel = "1st Year";
        grade = 75.0;
        isElective = false;
        isFinished = false;
        preReq = "None";
        coReq = "None";

    }//end of Course constructor

    public Curriculum(String c, String d, double u, String t, String y, double g, boolean e, boolean f, String p, String co){
        courseNumber = c;
        descriptiveTitle = d;
        units = u;
        term = t;
        yearLevel = y;
        grade = g;
        isElective = e;
        isFinished = f;
        preReq = p;
        coReq = co;
    }

    public String getCourseNumber(){
        return courseNumber;
    }

    public String getDescriptiveTitle(){
        return descriptiveTitle;
    }

    public double getUnits(){
        return units;
    }

    public String getTerm(){
        return term;
    }

    public String getYearLevel(){
        return yearLevel;
    }

    public double getGrade(){
        return grade;
    }

    public String getPreReq(){
        return preReq;
    }

    public String getCoReq() {
        return coReq;
    }

    public boolean getIsElective(){
        return isElective;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setCourseNumber(String CourseNumber){
        courseNumber = CourseNumber;
    }

    public void setDescriptiveTitle(String DescriptiveTitle){
        descriptiveTitle = DescriptiveTitle;
    }
    public void setUnits(double Units){
        units = Units;
    }

    public void setTerm(String Term){
        term = Term;
    }

    public void setYearLevel(String YearLevel){
        yearLevel = YearLevel;
    }

    public void setGrade (double Grade){
        grade = Grade;
    }

    public String toString(){
        return courseNumber+", "+descriptiveTitle+", "+units+", "+grade+", "+yearLevel+", "+term;
    }

    public void setElective(boolean elective) {
        isElective = elective;
    }

    public void setCoReq(String coReq) {
        this.coReq = coReq;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public void setPreReq(String preReq) {
        this.preReq = preReq;
    }

    @Override
    public int compareTo(Curriculum o) {
        return 0;
    }
}//end of Course class
