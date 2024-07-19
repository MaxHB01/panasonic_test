package com.panasonic.toughpad.android.contract;

import java.util.List;
import com.panasonic.toughpad.android.contract.IBarcodeReaderManager;
import com.panasonic.toughpad.android.contract.IMagStripeReaderManager;
import com.panasonic.toughpad.android.contract.IPortPowerManager;
import com.panasonic.toughpad.android.contract.IAppButtonManager;
import com.panasonic.toughpad.android.contract.ICradle;
import com.panasonic.toughpad.android.contract.ISerialPortManager;
import com.panasonic.toughpad.android.contract.ISmartCardReaderManager;

interface IToughpadApi {
    int getVersion();
    IBarcodeReaderManager getBarcodeReaderManager();
    IMagStripeReaderManager getMagStripeReaderManager();
    IAppButtonManager getAppButtonManager();
    IPortPowerManager getPortPowerManager();
    ISerialPortManager getSerialPortManager();
    ICradle getCradle();
    ISmartCardReaderManager getSmartCardReaderManager();
}
