package edu.pdx.cs.joy.whitlock;

import edu.pdx.cs.joy.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {
    private final int sum;

    public PhoneCall(int sum) {
        this.sum = sum;
    }

    @Override
    public String getCaller() {
        return "+" + sum + "-123-456-7890";
    }

    @Override
    public String getCallee() {
        return "789-012-3456";
    }

    @Override
    public String getBeginTimeString() {
        return "NOW";
    }

    @Override
    public String getEndTimeString() {
        return "LATER";
    }
}
