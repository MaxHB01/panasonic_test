package com.panasonic.toughpad.android.contract;

interface ISmartCardReader {
    byte[] connect(String protocol);
    void disconnect(boolean reset);
    boolean isCardPresent();
    String getProtocol();
    byte[] transmit(in byte[] apdu);
    void beginExclusive();
    void endExclusive();
    void enable();
    void disable();
    String getReaderName();
    boolean isEnabled();
}
