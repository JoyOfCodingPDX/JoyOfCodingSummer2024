package edu.pdx.cs.joy.whitlock;

import edu.pdx.cs.joy.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.StringContains.containsString;

/**
 * Integration tests for the <code>Student</code> class's main method.
 * These tests extend <code>InvokeMainTestCase</code> which allows them
 * to easily invoke the <code>main</code> method of <code>Student</code>.
 */
class StudentIT extends InvokeMainTestCase {

  @Test
  void invokingMainWithNoArgumentsPrintsMissingArgumentsToStandardError() {
    InvokeMainTestCase.MainMethodResult result = invokeMain();
    assertThat(result.getTextWrittenToStandardOut(), containsString(""));
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }

  @Test
  void daveStudentHasExpectedOutput() {
    InvokeMainTestCase.MainMethodResult result = invokeMain("Dave", "male", "3.64", "Algorithms", "Operating Systems", "Java");
    assertExpectedOutput(result, "Dave has a GPA of 3.64 and is taking 3 classes: Algorithms, Operating Systems, and Java.  He says \"This class is too much work\".", "");
  }

  @Test
  void gpaThatIsNotAnNumberWritesMessageToStandardError() {
    String gpa = "Dave";
    MainMethodResult result = invokeMain("Dave", "male", gpa, "Algorithms", "Operating Systems", "Java");
    assertExpectedOutput(result, "", "Invalid GPA: " + gpa);
  }

  private static void assertExpectedOutput(MainMethodResult result, String expectedOut, String expectedErr) {
    assertThat(result.getTextWrittenToStandardOut().trim(), equalTo(expectedOut));
    assertThat(result.getTextWrittenToStandardError().trim(), equalTo(expectedErr));
  }

  @Test
  void missingGpaWritesMessageToStandardError() {
    MainMethodResult result = invokeMain("Dave", "male");
    assertExpectedOutput(result, "", "Missing GPA");
  }

  private MainMethodResult invokeMain(String... args) {
    return invokeMain(Student.class, args);
  }

}
