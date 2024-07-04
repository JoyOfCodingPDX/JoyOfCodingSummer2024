package edu.pdx.cs.joy.whitlock;

import edu.pdx.cs.joy.lang.Human;

import java.util.ArrayList;
                                                                                    
/**                                                                                 
 * This class is represents a <code>Student</code>.                                 
 */                                                                                 
public class Student extends Human {

  private final double gpa;

  /**
   * Creates a new <code>Student</code>                                             
   *                                                                                
   * @param name                                                                    
   *        The student's name                                                      
   * @param classes                                                                 
   *        The names of the classes the student is taking.  A student              
   *        may take zero or more classes.                                          
   * @param gpa                                                                     
   *        The student's grade point average                                       
   * @param gender                                                                  
   *        The student's gender ("male", "female", or "other", case insensitive)
   */                                                                               
  public Student(String name, ArrayList<String> classes, double gpa, String gender) {
    super(name);
    this.gpa = validateGpa(gpa);
  }

  private double validateGpa(double gpa) {
    if (gpa < 0.0 || gpa > 4.0) {
      throw new IllegalArgumentException();
    }

   return gpa;
  }

  /**                                                                               
   * All students say "This class is too much work"
   */
  @Override
  public String says() {                                                            
    return "This class is too much work";
  }
                                                                                    
  /**                                                                               
   * Returns a <code>String</code> that describes this                              
   * <code>Student</code>.                                                          
   */                                                                               
  public String toString() {
    return name + " has a GPA of " + gpa + " and is taking 3 classes: Algorithms, Operating Systems, and Java.  He says \"This class is too much work\".";
  }

  /**
   * Main program that parses the command line, creates a
   * <code>Student</code>, and prints a description of the student to
   * standard out by invoking its <code>toString</code> method.
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      System.err.println("Missing command line arguments");
    }

    ArrayList<String> classes = new ArrayList<>();
    classes.add("Algorithms");
    classes.add("Operating Systems");
    classes.add("Java");
    Student student = new Student("Dave", classes, 3.64, "male");
    System.out.println(student);
  }
}