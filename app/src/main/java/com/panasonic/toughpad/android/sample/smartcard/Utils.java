package com.panasonic.toughpad.android.sample.smartcard;

public class Utils {
    /**
     * Convert the byte array to hex string.
     *
     * @param a byte array value.
     * @return hex string value.
     */
    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x", b));
        return sb.toString().toUpperCase();
    }

    /**
     * Convert the  hex string to byte array.
     *
     * @param s hex string value.
     * @return  byte array value.
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
