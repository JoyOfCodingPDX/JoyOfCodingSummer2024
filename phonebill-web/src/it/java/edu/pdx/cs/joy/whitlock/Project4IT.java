package edu.pdx.cs.joy.whitlock;

import edu.pdx.cs.joy.InvokeMainTestCase;
import edu.pdx.cs.joy.UncaughtExceptionInMain;
import edu.pdx.cs.joy.web.HttpRequestHelper.RestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.MethodOrderer.MethodName;

/**
 * Tests the {@link Project4} class by invoking its main method with various arguments
 */
@TestMethodOrder(MethodName.class)
class Project4IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    @Test
    void test0RemoveAllPhoneBills() throws IOException {
      PhoneBillRestClient client = new PhoneBillRestClient(HOSTNAME, Integer.parseInt(PORT));
      client.removeAllPhoneBills();
    }

    @Test
    void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project4.class );
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_ARGS));
    }

    @Test
    void test2MissingCustomerName() {
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT );

        assertThat(result.getTextWrittenToStandardError(), containsString("Missing customer name"));

        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, emptyString());
    }

    @Test
    void test3UnknownPhoneBillThrowsAppointmentBookRestException() {
        String customerName = "Customer";
        try {
            invokeMain(Project4.class, HOSTNAME, PORT, customerName);
            fail("Expected a RestException to be thrown");

        } catch (UncaughtExceptionInMain ex) {
            RestException cause = (RestException) ex.getCause();
            assertThat(cause.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_NOT_FOUND));
        }
    }

    @Test
    void test4AddPhoneCall() {
        String customerName = "Customer";
        String callerPhoneNumber = "123-456-7890";

        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, customerName, callerPhoneNumber );

        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(Messages.createdPhoneCall(customerName, callerPhoneNumber)));

        result = invokeMain( Project4.class, HOSTNAME, PORT, customerName );

        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

        out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(PrettyPrinter.formatPhoneCall(customerName, callerPhoneNumber)));
    }
}