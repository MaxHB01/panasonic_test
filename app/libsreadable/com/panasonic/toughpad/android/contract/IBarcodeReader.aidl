package com.panasonic.toughpad.android.contract;

import com.panasonic.toughpad.android.contract.IBarcodeListener;

interface IBarcodeReader {
    void setBarcodeListener(IBarcodeListener listener);
    void enable(long timeout);
    void disable();
    int getBarcodeType();
    boolean isEnabled();
    int getBatteryCharge();
    boolean isBatteryCharging();
    void setHardwareTriggerEnabled(boolean enable);
    boolean isHardwareTriggerEnabled();
    void pressSoftwareTrigger(boolean press);
    String getDeviceName();
    String getDeviceFirmwareVersion();
    String getDeviceSerialNumber();
    boolean isExternal();
    boolean isHardwareTriggerAvailable();
}
