package com.panasonic.toughpad.android.contract;

interface IAppButtonManager {
    boolean isButtonAvailable(int button);
    boolean hasButtonControl();
}