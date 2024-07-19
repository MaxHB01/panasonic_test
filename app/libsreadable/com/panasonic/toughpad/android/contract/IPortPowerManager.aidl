package com.panasonic.toughpad.android.contract;

interface IPortPowerManager {

    boolean isSelectiveSuspend(int port);

    boolean isVBUSSupply(int port);

    void setSelectiveSuspend(int port, boolean setting);

    void setVBUSSupply(int port, boolean setting);

    boolean isPortAvailable(int port);
}
