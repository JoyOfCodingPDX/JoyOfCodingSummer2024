package edu.pdx.cs.joy.whitlock;

import edu.pdx.cs.joy.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {
  private final String caller;

  public PhoneCall(String caller) {
    this.caller = caller;
  }

  @Override
  public String getCaller() {
    return this.caller;
  }

  @Override
  public String getCallee() {
    return "This method is not implemented yet";
  }

  @Override
  public String getBeginTimeString() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getEndTimeString() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof PhoneCall) {
      PhoneCall other = (PhoneCall) obj;
      return this.caller.equals(other.caller);
    }

    return false;
  }
}
