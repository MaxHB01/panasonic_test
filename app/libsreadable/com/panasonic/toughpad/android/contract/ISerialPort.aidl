package com.panasonic.toughpad.android.contract;

interface ISerialPort {
    void enable(int baudRate, int dataSize, int parityMode, int stopBits, int flowControl);
    boolean isEnabled();
    ParcelFileDescriptor getReadFd();
    ParcelFileDescriptor getWriteFd();
    void disable();
    String getDeviceName();
}