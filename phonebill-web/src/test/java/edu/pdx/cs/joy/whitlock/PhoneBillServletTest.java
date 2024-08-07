package edu.pdx.cs.joy.whitlock;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link PhoneBillServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
class PhoneBillServletTest {

  @Test
  void missingCustomerNameReturnPreconditionFailedStatus() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    // Nothing is written to the response's PrintWriter
    verify(pw, never()).println(anyString());
    verify(response).sendError(eq(HttpServletResponse.SC_PRECONDITION_FAILED), anyString());
  }

  @Test
  void addPhoneCall() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    String customer = "TEST CUSTOMER";
    String caller = "123-456-2349";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(PhoneBillServlet.CUSTOMER_PARAMETER)).thenReturn(customer);
    when(request.getParameter(PhoneBillServlet.CALLER_PARAMETER)).thenReturn(caller);

    HttpServletResponse response = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);

    assertThat(stringWriter.toString(), containsString(Messages.createdPhoneCall(customer, caller)));

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    verify(response).setStatus(statusCode.capture());

    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));

    PhoneBill bill = servlet.getPhoneBill(customer);
    assertThat(bill, notNullValue());
    assertThat(bill.getPhoneCalls().iterator().next().getCaller(), equalTo(caller));
  }

}
