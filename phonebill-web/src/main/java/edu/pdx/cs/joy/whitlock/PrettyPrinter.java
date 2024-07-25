package edu.pdx.cs.joy.whitlock;

import com.google.common.annotations.VisibleForTesting;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

public class PrettyPrinter {
  private final Writer writer;

  @VisibleForTesting
  static String formatPhoneCall(String customer, String caller )
  {
    return String.format("  %s -> %s", customer, caller);
  }


  public PrettyPrinter(Writer writer) {
    this.writer = writer;
  }

  public void dump(PhoneBill bill) {
    try (
      PrintWriter pw = new PrintWriter(this.writer)
    ) {
      String customer = bill.getCustomer();
      String caller = bill.getPhoneCalls().iterator().next().getCaller();
      pw.println(formatPhoneCall(customer, caller));

      pw.flush();
    }

  }
}
