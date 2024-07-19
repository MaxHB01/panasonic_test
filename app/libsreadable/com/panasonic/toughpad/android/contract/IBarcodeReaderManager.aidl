package com.panasonic.toughpad.android.contract;

import com.panasonic.toughpad.android.contract.IBarcodeReader;

interface IBarcodeReaderManager {
    List<IBinder> getBarcodeReaders();
}