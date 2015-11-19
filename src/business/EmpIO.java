
package business;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.stream.*;

/*
 * @author Ben Gomes
 */
public class EmpIO {
    private static final String[] getmethods = { "getFirstName", "getLastName", 
                "getMiddleName", "getSuffix", "getAddress1", "getAddress2",
                "getCity", "getState", "getZip", "getPhone", "getGender",
                "getStatus", "getHireDate", "getTermDate", "getPayCode" };
    public static Map<Long, Employee> getEmps(String path) {
        Map<Long, Employee> emplist = new HashMap<>();
        try {
            BufferedReader in = new BufferedReader(
                                new FileReader(path));        
            in.readLine();  // first line is column headings
            String s = in.readLine();
            while (s != null) {
                String[] edata = s.split(",");
                Employee emp = new Employee();
                long empNo = Long.parseLong(edata[0]);
                emp.setEmpNo(empNo);
                if (!edata[1].isEmpty()) { emp.setFirstName(edata[1]); }
                if (!edata[2].isEmpty()) { emp.setLastName(edata[2]); }
                if (!edata[3].isEmpty()) { emp.setMiddleName(edata[3]); }
                if (!edata[4].isEmpty()) { emp.setSuffix(edata[4]); }
                if (!edata[5].isEmpty()) { emp.setAddress1(edata[5]); }
                if (!edata[6].isEmpty()) { emp.setAddress2(edata[6]); }
                if (!edata[7].isEmpty()) { emp.setCity(edata[7]); }
                if (!edata[8].isEmpty()) { emp.setState(edata[8]); }
                if (!edata[9].isEmpty()) { emp.setZip(edata[9]); }
                if (!edata[10].isEmpty())
                    { emp.setPhone(Integer.parseInt(edata[10])); }
                if (!edata[11].isEmpty()) { emp.setGender(edata[11]); }
                if (!edata[12].isEmpty()) { emp.setStatus(edata[12]); }
                if (!edata[13].isEmpty()) { emp.setHireDate(edata[13]); }
                if (!edata[14].isEmpty()) { emp.setTermDate(edata[14]); }
                if (!edata[15].isEmpty())
                    { emp.setPayCode(Integer.parseInt(edata[15])); }
                emplist.put(empNo, emp);
                s = in.readLine();
            }
        } catch (Exception e) {
            System.out.println("Error in EmpIO: " + e.getMessage());
        }
        
        return emplist;
    } // end of getEmps()
    
    public static String setEmpsXML(String path, Map<Long, Employee> emps) {
        String status ="";
        try {
            XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
            
            FileWriter fileWriter = new FileWriter(path);
            XMLStreamWriter writer = 
                        outputFactory.createXMLStreamWriter(fileWriter);
            
            Iterator<Map.Entry<Long, Employee>> it = emps.entrySet().iterator();
            
            writer.writeStartDocument("1.0");
            writer.writeStartElement("Employees");
            
            while (it.hasNext()) {
                Map.Entry<Long, Employee> empentry = it.next();
                Employee emp = empentry.getValue();
                
                Class empclass = emp.getClass();
                Method m;
                
                writer.writeStartElement("Employee");
                writer.writeAttribute("EmpNo", String.valueOf(emp.getEmpNo()));
                
                // reflection code for remainder of employee values...
                for (String getmethod : getmethods) {
                    String val;
                    switch (getmethod) {
                        case "getPhone":
                        case "getPayCode":
                            m = empclass.getMethod(getmethod, null);
                            try {
                                val = String.valueOf(
                                        (int)(m.invoke(emp, null)) );
                            } catch (Exception e) {
                                val = "";
                            }
                            break;
                        default:
                            m = empclass.getMethod(getmethod, null);
                            val = (String) (m.invoke(emp, null));
                            break;
                    } // end of switch (getmethod)
                    writer.writeStartElement(getmethod.substring(3));
                    writer.writeCharacters(val);
                    writer.writeEndElement();
                } // end of for loop Emloyee (singular)
                writer.writeEndElement();
            }
            writer.writeEndElement(); // end element for Employees (plural)
            writer.flush();
            writer.close();
            status = "Employee records saved";
        } catch (IOException | XMLStreamException e) {
            status = "Exception during write: " + e.getMessage();
        } catch (Exception e) {
            status = "Save error: " + e.getMessage();
        }
        return status;
    }
    
    public static String setEmps(String path, Map<Long, Employee> emps) {
        String status = "";
        try {
            PrintWriter out = new PrintWriter(new FileWriter(path));
            Iterator<Map.Entry<Long, Employee>> it = emps.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Long, Employee> empentry = it.next();
                out.println(empentry.getValue().toString());
            }
            out.close();
            status = "Employee records saved";
        } catch (Exception e) {
            status = "Save error: " + e.getMessage();
        }
        return status;
    } // end of setEmps
    
    public static Map<Long, Employee> getEmpsXML (String path) {
        Map<Long, Employee> emplist = new HashMap<>();
        Employee emp = null;
        Class empclass = null;
        Method m = null;
        long empNo = 0; // instructor's is titled empno
        
        /*
            empfields contains the name of all fields in Employee.java
            except empNo.
        */
        ArrayList<String> empfields = new ArrayList<>();
        for (String getmethod : getmethods) {
            empfields.add(getmethod.substring(3));
        }
        
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newFactory();
            FileReader fileReader = new FileReader(path);
            XMLStreamReader reader = 
                    inputFactory.createXMLStreamReader(fileReader);
            while (reader.hasNext()) {
                int eventType = reader.getEventType();
                switch (eventType) {
                    case XMLStreamConstants.START_ELEMENT:
                        String elementName = reader.getLocalName();
                        if (elementName.equals("Employee")) {
                            emp = new Employee();
                            empNo = Long.parseLong(reader.getAttributeValue(0));
                            emp.setEmpNo(empNo);
                            empclass = emp.getClass();
                        } else if (empfields.indexOf(elementName) >= 0) {
                            // xml element is an employee.java property value
                            String val = reader.getElementText();
                            String setmethod = "set" + elementName;
                            switch (setmethod) {
                                case "setPhone":
                                case "setPayCode":
                                    try {
                                        m = empclass.getMethod(setmethod, 
                                                    new Class[]{Integer.class});
                                    
                                        int y = Integer.parseInt(val);
                                        m.invoke(emp, y);
                                    } catch (Exception e) {
                                        // skip
                                    }
                                    break;
                                default:
                                    try {
                                    m = empclass.getMethod(setmethod,
                                                    new Class[]{String.class});
                                    m.invoke(emp, val);
                                    } catch (Exception e) {
                                        // skip...
                                    }
                                    break;
                            } // end of switch (setmethod)
                        } // end of else if
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        elementName = reader.getLocalName();
                        if (elementName.equals("Employee")) {
                            emplist.put(empNo, emp);
                        }
                        break;
                    default:
                        break;
                } // end of switch (eventType)
                reader.next();
            } // end of while
            
        } catch (FileNotFoundException | XMLStreamException e) {
            return null;
        }
        
        return emplist;
    }
}
