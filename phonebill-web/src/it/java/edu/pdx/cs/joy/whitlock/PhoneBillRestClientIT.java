package edu.pdx.cs.joy.whitlock;

import edu.pdx.cs.joy.ParserException;
import edu.pdx.cs.joy.web.HttpRequestHelper.RestException;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Integration test that tests the REST calls made by {@link PhoneBillRestClient}
 */
@TestMethodOrder(MethodName.class)
class PhoneBillRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private PhoneBillRestClient newPhoneBillRestClient() {
    int port = Integer.parseInt(PORT);
    return new PhoneBillRestClient(HOSTNAME, port);
  }

  @Test
  void test0RemoveAllPhoneBills() throws IOException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    client.removeAllPhoneBills();
  }


  @Test
  void test2AddPhoneCall() throws IOException, ParserException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    String customer = "TEST WORD";
    String caller = "TEST DEFINITION";
    client.addPhoneCall(customer, caller);

    PhoneBill bill = client.getPhoneBill(customer);
    assertThat(bill.getCustomer(), equalTo(customer));
    assertThat(bill.getPhoneCalls().iterator().next().getCaller(), equalTo(caller));
  }

  @Test
  void test4EmptyWordThrowsException() {
    PhoneBillRestClient client = newPhoneBillRestClient();
    String emptyString = "";

    RestException ex =
      assertThrows(RestException.class, () -> client.addPhoneCall(emptyString, emptyString));
    assertThat(ex.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_PRECON_FAILED));
    assertThat(ex.getMessage(), containsString(Messages.missingRequiredParameter(PhoneBillServlet.CUSTOMER_PARAMETER)));
  }

}
