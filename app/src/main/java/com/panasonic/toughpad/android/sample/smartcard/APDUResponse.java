package com.panasonic.toughpad.android.sample.smartcard;

import java.util.Arrays;

public class APDUResponse {
    private byte[] data;
    private String sw1;
    private String sw2;
    private String sw;
    private String hexStringAPDUResponse;

    public APDUResponse() {
    }

    /**
     * Consructor of APDU response
     * Conver value from byte array to hex => set value to variable.
     * @param apduResponse - value of APDU.
     */
    public APDUResponse(byte[] apduResponse) {
        if (apduResponse != null) {
            this.hexStringAPDUResponse = Utils.byteArrayToHex(apduResponse);
            this.data = Arrays.copyOfRange(apduResponse, 0, apduResponse.length - 2);
            String s = Utils.byteArrayToHex(apduResponse);
            if (s.length() > 4) {
                this.sw = s.substring(s.length() - 4);
            } else {
                this.sw = s;
            }
            this.sw1 = this.sw.substring(0, 2);
            this.sw2 = this.sw.substring(2, 4);
        }
    }

    /**
     * Get hex string APDU response.
     * @return  APDU hex value.
     */
    public String getHexStringAPDUResponse() {
        return hexStringAPDUResponse;
    }

    /**
     * Get byte array of APDU response data field.
     * @return  APDU data field byte array data.
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Get SW1 value.
     * @return hex string SW1 value.
     */
    public String getSW1() {
        return sw1;
    }
    /**vx
     * Get SW2 value.
     * @return hex string SW2 value.
     */
    public String getSW2() {
        return sw2;
    }
    /**
     * Get SW value.
     * @return hex string SW value.
     */
    public String getSW() {
        return sw;
    }
}
