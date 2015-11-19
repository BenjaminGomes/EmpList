
package business;

/*
 * @author Ben Gomes
 */
public class Employee {
    private long empNo;
    private int phone;
    private int payCode;
    private String lastName;
    private String firstName;
    private String middleName;
    private String suffix;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String gender;
    private String status;
    private String hireDate;
    private String termDate;
    
    public Employee() {
        empNo = 0;
        phone = 0;
        payCode = 0;
        String[] fields = { lastName, firstName, middleName, suffix, address1, 
                            address2, city, state, zip, gender, status, 
                            hireDate, termDate };
        for (String s : fields) {
            s = "";
        }
    }

    public long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public int getPayCode() {
        return payCode;
    }

    public void setPayCode(Integer payCode) {
        this.payCode = payCode;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getTermDate() {
        return termDate;
    }

    public void setTermDate(String termDate) {
        this.termDate = termDate;
    }
    
    /*
    public String toString() {
        need to override 2 string method and return csv record of  employee data
        values delimitered by a comma
    }
    */
}
