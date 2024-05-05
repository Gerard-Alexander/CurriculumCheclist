public class Citizen implements Comparable <Citizen>{
    private String fullName;
    private String email;
    private String address;
    private int age;
    private boolean resident;
    private int district;
    private char gender;
    
    public Citizen (){
        fullName = "";
        email = "";
        address = "";
        age = 0;
        resident = false;
        district = 0;
        gender = '';
        
    }//end of default constructor
    
    public Citizen (String f, String e, String A, int a, boolean r, int d, char g){
        this.fullName = f;
        this.email = e;
        this.address = A;
        this.age = a;
        this.resident = r;
        this.district = d;
        this.gender = g;
        
    }//end of parameterized constructor
    
    public void setFullName(String fullname){
        fullName = fullname;
    }
    
    public void setEmail(String emailAdress){
        email = emailAdress;
    }
    public void setAddress(String Address){
        address = Address;
    }
    
    public void setAge(int Age){
        age = Age;
    }
    
    public void setResident(Boolean Resident){
        resident = Resident;
    }
    
    public void setDistrict(int District){
        district = District;
    }
    
    public void setGender(char Gender){
        gender = Gender;
    }
    
    public String getFullName(){
        return fullName;
    }
    
    public String getEmail(){
        return email;
    }
    
    public String getAddress(){
        return address;
    }
    
    public int setAge(){
    return age;
    }
    
    public boolean setResident(){
        return resident;
    }
    
    public int setDistrict(){
        return district;
    }
    
    public char setGender(){
        return gender;
    }
    
    @Override
    public String toString(){
        return fullName + " " + email + " " + address + " " + age + " "  resident + " " district + " " + gender
    }

    @Override
    public int compareTo(Citizen other Citizen) {
        return this.fullName.compareTo(other Citizen.fullName);
    }

    public boolean equals(Object another){
        return  (this.getId().equals(((Citizen)another).getId()));
    }
    
    

}//end of class

