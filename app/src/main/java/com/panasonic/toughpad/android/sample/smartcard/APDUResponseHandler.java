package com.panasonic.toughpad.android.sample.smartcard;

import com.panasonic.toughpad.android.sample.R;

import java.util.HashMap;
import java.util.Map;

public class APDUResponseHandler {
    // Declare list status word.
    HashMap<String, HashMap<String, Integer>> listStatusWord;

    /**
     * Get list status word.
     *
     * @return value of list status corresponding the first byte of SW value.
     */
    public Map<String, HashMap<String, Integer>> getListStatusWord() {
        return listStatusWord;
    }

    /**
     * Constructor of APDUResponseHandler.
     * Put key = error code, value = error name.
     */
    public APDUResponseHandler() {
        HashMap<String, Integer> value90 = new HashMap<>();
        value90.put("9000", R.string.apdu_response_sw1_sw2_value_9000);

        HashMap<String, Integer> value61 = new HashMap<>();
        value61.put("6100", R.string.apdu_response_sw1_sw2_value_6100);

        HashMap<String, Integer> value62 = new HashMap<>();
        value62.put("6200", R.string.apdu_response_sw1_sw2_value_6200);
        value62.put("6281", R.string.apdu_response_sw1_sw2_value_6281);
        value62.put("6282", R.string.apdu_response_sw1_sw2_value_6282);
        value62.put("6283", R.string.apdu_response_sw1_sw2_value_6283);
        value62.put("6284", R.string.apdu_response_sw1_sw2_value_6284);
        value62.put("6285", R.string.apdu_response_sw1_sw2_value_6285);
        value62.put("6286", R.string.apdu_response_sw1_sw2_value_6286);
        value62.put("6287", R.string.apdu_response_sw1_sw2_value_6287);

        HashMap<String, Integer> value63 = new HashMap<>();
        value63.put("6300", R.string.apdu_response_sw1_sw2_value_6300);
        value63.put("6340", R.string.apdu_response_sw1_sw2_value_6340);
        value63.put("6381", R.string.apdu_response_sw1_sw2_value_6381);

        HashMap<String, Integer> value64 = new HashMap<>();
        value64.put("6400", R.string.apdu_response_sw1_sw2_value_6400);
        value64.put("6401", R.string.apdu_response_sw1_sw2_value_6401);

        HashMap<String, Integer> value65 = new HashMap<>();
        value65.put("6500", R.string.apdu_response_sw1_sw2_value_6500);
        value65.put("6501", R.string.apdu_response_sw1_sw2_value_6501);
        value65.put("6581", R.string.apdu_response_sw1_sw2_value_6581);

        HashMap<String, Integer> value66 = new HashMap<>();
        value66.put("6600", R.string.apdu_response_sw1_sw2_value_6600);

        HashMap<String, Integer> value67 = new HashMap<>();
        value67.put("6700", R.string.apdu_response_sw1_sw2_value_6700);
        value67.put("6701", R.string.apdu_response_sw1_sw2_value_6701);
        value67.put("6702", R.string.apdu_response_sw1_sw2_value_6702);

        HashMap<String, Integer> value68 = new HashMap<>();
        value68.put("6800", R.string.apdu_response_sw1_sw2_value_6800);
        value68.put("6881", R.string.apdu_response_sw1_sw2_value_6881);
        value68.put("6882", R.string.apdu_response_sw1_sw2_value_6882);

        HashMap<String, Integer> value69 = new HashMap<>();
        value69.put("6900", R.string.apdu_response_sw1_sw2_value_6900);
        value69.put("6981", R.string.apdu_response_sw1_sw2_value_6981);
        value69.put("6982", R.string.apdu_response_sw1_sw2_value_6982);
        value69.put("6983", R.string.apdu_response_sw1_sw2_value_6983);
        value69.put("6984", R.string.apdu_response_sw1_sw2_value_6984);
        value69.put("6985", R.string.apdu_response_sw1_sw2_value_6985);
        value69.put("6986", R.string.apdu_response_sw1_sw2_value_6986);
        value69.put("6987", R.string.apdu_response_sw1_sw2_value_6987);
        value69.put("6988", R.string.apdu_response_sw1_sw2_value_6988);

        HashMap<String, Integer> value6A = new HashMap<>();
        value6A.put("6A00", R.string.apdu_response_sw1_sw2_value_6a00);
        value6A.put("6A80", R.string.apdu_response_sw1_sw2_value_6a80);
        value6A.put("6A81", R.string.apdu_response_sw1_sw2_value_6a81);
        value6A.put("6A82", R.string.apdu_response_sw1_sw2_value_6a82);
        value6A.put("6A83", R.string.apdu_response_sw1_sw2_value_6a83);
        value6A.put("6A84", R.string.apdu_response_sw1_sw2_value_6a84);
        value6A.put("6A85", R.string.apdu_response_sw1_sw2_value_6a85);
        value6A.put("6A86", R.string.apdu_response_sw1_sw2_value_6a86);
        value6A.put("6A87", R.string.apdu_response_sw1_sw2_value_6a87);
        value6A.put("6A88", R.string.apdu_response_sw1_sw2_value_6a88);

        HashMap<String, Integer> value6E = new HashMap<>();
        value6E.put("6E00", R.string.apdu_response_sw1_sw2_value_6e00);

        HashMap<String, Integer> value6D = new HashMap<>();
        value6D.put("6D00", R.string.apdu_response_sw1_sw2_value_6d00);

        listStatusWord = new HashMap<>();
        listStatusWord.put("90", value90);
        listStatusWord.put("61", value61);
        listStatusWord.put("62", value62);
        listStatusWord.put("63", value63);
        listStatusWord.put("64", value64);
        listStatusWord.put("65", value65);
        listStatusWord.put("66", value66);
        listStatusWord.put("67", value67);
        listStatusWord.put("68", value68);
        listStatusWord.put("69", value69);
        listStatusWord.put("6A", value6A);
        listStatusWord.put("6E", value6E);
        listStatusWord.put("6D", value6D);
    }
}
