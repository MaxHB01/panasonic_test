package com.panasonic.toughpad.android.contract;

import com.panasonic.toughpad.android.contract.IBarcodeReader;
import com.panasonic.toughpad.android.api.barcode.BarcodeData;

oneway interface IBarcodeListener {
    void onRead(in IBarcodeReader bsObj, in BarcodeData result);
}