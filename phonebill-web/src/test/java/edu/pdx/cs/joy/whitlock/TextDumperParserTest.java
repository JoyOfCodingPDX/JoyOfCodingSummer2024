package edu.pdx.cs.joy.whitlock;

import edu.pdx.cs.joy.ParserException;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TextDumperParserTest {

  private PhoneBill dumpAndParse(PhoneBill bill) throws ParserException {
    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw);
    dumper.dump(bill);

    String text = sw.toString();

    TextParser parser = new TextParser(new StringReader(text));
    return parser.parse();
  }

  @Test
  void dumpedTextCanBeParsed() throws ParserException {
    PhoneBill bill = new PhoneBill("Customer");
    bill.addPhoneCall(new PhoneCall("123-456-7890"));
    PhoneBill bill2 = dumpAndParse(bill);
    assertThat(bill2, equalTo(bill));
  }
}
