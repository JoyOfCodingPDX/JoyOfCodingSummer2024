package edu.pdx.cs.joy.whitlock;

import edu.pdx.cs.joy.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser {
  private final Reader reader;

  public TextParser(Reader reader) {
    this.reader = reader;
  }

  public PhoneBill parse() throws ParserException {
    Pattern pattern = Pattern.compile("(.*) : (.*)");

    try (
      BufferedReader br = new BufferedReader(this.reader)
    ) {

      for (String line = br.readLine(); line != null; line = br.readLine()) {
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
          throw new ParserException("Unexpected text: " + line);
        }

        String customer = matcher.group(1);
        String caller = matcher.group(2);

        PhoneBill bill = new PhoneBill(customer);
        bill.addPhoneCall(new PhoneCall(caller));
        return bill;
      }

    } catch (IOException e) {
      throw new ParserException("While parsing dictionary", e);
    }

    throw new IllegalStateException("No Phone Bill in text");
  }
}
