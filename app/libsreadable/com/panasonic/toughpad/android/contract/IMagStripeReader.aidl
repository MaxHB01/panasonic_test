package com.panasonic.toughpad.android.contract;

import com.panasonic.toughpad.android.contract.IMagStripeListener;

interface IMagStripeReader {
    void enable(long timeout);
    void disable();
    boolean isEnabled();
    int getBatteryCharge();
    boolean isBatteryCharging();

    String getDeviceName();
    String getDeviceFirmwareVersion();
    String getDeviceSerialNumber();
    boolean isExternal();

    void setMagStripeListener(IMagStripeListener listener);
}