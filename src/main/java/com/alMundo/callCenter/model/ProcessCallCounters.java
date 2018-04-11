package com.alMundo.callCenter.model;

/**
 * Created by fscarpa on 11/04/18.
 */
public class ProcessCallCounters {

    private int callProcessOkCout;
    private int callNoProcessCount;

    public void incrementCallProcessOkCout() {
        callProcessOkCout++;
    }

    public void incrementCallNoProcessCount() {
        callNoProcessCount++;
    }

    public int getCallProcessOkCout() {
        return callProcessOkCout;
    }

    public int getCallNoProcessCount() {
        return callNoProcessCount;
    }

    public int getCallCount() {
        return callProcessOkCout + callNoProcessCount;
    }
}
