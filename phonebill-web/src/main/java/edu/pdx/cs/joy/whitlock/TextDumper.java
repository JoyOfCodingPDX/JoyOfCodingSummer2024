package edu.pdx.cs.joy.whitlock;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

public class TextDumper {
  private final Writer writer;

  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  public void dump(PhoneBill bill) {
    try (
      PrintWriter pw = new PrintWriter(this.writer)
    ){
      String customer = bill.getCustomer();
      PhoneCall call = bill.getPhoneCalls().iterator().next();
      String caller = call.getCaller();
      pw.println(customer + " : " + caller);

      pw.flush();
    }
  }
}
