package edu.pdx.cs.joy.whitlock;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Unit tests for the Student class.  In addition to the JUnit annotations,
 * they also make use of the <a href="http://hamcrest.org/JavaHamcrest/">hamcrest</a>
 * matchers for more readable assertion statements.
 */
public class StudentTest
{

  @Test
  void studentNamedPatIsNamedPat() {
    String name = "Pat";
    var pat = createStudent(name);
    assertThat(pat.getName(), equalTo(name));
  }

  private static Student createStudent(String name) {
    return new Student(name, new ArrayList<>(), 0.0, "Doesn't matter");
  }

  @Test
  void allStudentsSayThisClassIsTooMuchWork() {
    Student student = createStudent("name");
    assertThat(student.says(), equalTo("This class is too much work"));
  }

  @Disabled
  @Test
  void completeDaveStudent() {
    ArrayList<String> classes = new ArrayList<>();
    classes.add("Algorithms");
    classes.add("Operating Systems");
    classes.add("Java");

    Student dave = new Student("Dave", classes, 3.64, "male");
    assertThat(dave.toString(), equalTo("Dave has a GPA of 3.64 and is taking 3 classes: Algorithms, Operating Systems, and Java.  He says \"This class is too much work\"."));
  }

  @Test
  void toStringContainsStudentName() {
    String name = "name";
    Student student = createStudent(name);
    assertThat(student.toString(), containsString(name));
  }

  @Test
  void toStringContainsGPA() {
    double gpa = 3.45;
    Student student = new Student("name", new ArrayList<>(), gpa, "other");
    assertThat(student.toString(), containsString(String.valueOf(gpa)));
  }

  @Test
  void toStringContainsNameHasAGPAOfGPA() {
    double gpa = 3.45;
    String name = "name";
    Student student = new Student(name, new ArrayList<>(), gpa, "other");

    String expectedString = name + " has a GPA of " + gpa;
    assertThat(student.toString(), containsString(expectedString));
  }

}
