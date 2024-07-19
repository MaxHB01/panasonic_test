package com.panasonic.toughpad.android.contract;

import com.panasonic.toughpad.android.contract.ISerialPort;

interface ISerialPortManager {
    List<IBinder> getSerialPorts();
}
