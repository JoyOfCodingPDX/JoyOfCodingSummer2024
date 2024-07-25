package edu.pdx.cs.joy.whitlock;

import edu.pdx.cs.joy.ParserException;
import edu.pdx.cs.joy.web.HttpRequestHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhoneBillRestClientTest {

  @Test
  void getAllPhoneBillForCustomerPerformsHttpGetWithCustomerName() throws ParserException, IOException {
    String customerName = "Customer Name";
    PhoneBill bill = new PhoneBill(customerName);
    String caller = "123-456-7890";
    bill.addPhoneCall(new PhoneCall(caller));

    HttpRequestHelper http = mock(HttpRequestHelper.class);
    when(http.get(eq(Map.of(PhoneBillServlet.CUSTOMER_PARAMETER, customerName)))).thenReturn(phoneBillAsText(bill));

    PhoneBillRestClient client = new PhoneBillRestClient(http);

    assertThat(client.getPhoneBill(customerName), equalTo(bill));
  }

  private HttpRequestHelper.Response phoneBillAsText(PhoneBill bill) {
    StringWriter writer = new StringWriter();
    new TextDumper(writer).dump(bill);

    return new HttpRequestHelper.Response(writer.toString());
  }
}
