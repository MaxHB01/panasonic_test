package com.panasonic.toughpad.android.sample;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.panasonic.toughpad.android.sample.bcr.BarcodeTestFragment;
import com.panasonic.toughpad.android.sample.buttons.ButtonTestFragment;
import com.panasonic.toughpad.android.sample.msr.MagstripeTestFragment;
import com.panasonic.toughpad.android.sample.serial.SerialPortTestFragment;
import com.panasonic.toughpad.android.sample.smartcard.SmartCardTestFragment;

import java.util.HashMap;

public class ApiTestDetailFragment extends Fragment {
    public boolean isAPIInitialized = false;
    private static final String API_TEST_ID = "test_id";
    private static final String NAME_STR_ID = "name_str_id";
    private static final String ICON_RES_ID = "icon_res_id";

    public static final ApiTestDetailFragment[] API_TESTS = new ApiTestDetailFragment[]{
            newInstance(BarcodeTestFragment.class, "bcr", R.drawable.barcode, R.string.sample_barcode),
            newInstance(MagstripeTestFragment.class, "msr", R.drawable.magstripe, R.string.sample_magstripe),
            newInstance(ButtonTestFragment.class, "buttons", R.drawable.button, R.string.sample_appbutton),
            newInstance(SerialPortTestFragment.class, "serialport", R.drawable.serial, R.string.sample_serialport),
            newInstance(SmartCardTestFragment.class, "scr", R.drawable.smartcard, R.string.sample_smart_card)
    };

    public static final HashMap<String, ApiTestDetailFragment> API_TESTS_MAP = new HashMap<String, ApiTestDetailFragment>();

    static {
        for (ApiTestDetailFragment fragment : API_TESTS) {
            API_TESTS_MAP.put(fragment.getApiTestId(), fragment);
        }
    }

    public ApiTestDetailFragment() {
    }

    public static ApiTestDetailFragment newInstance(Class<? extends ApiTestDetailFragment> subclass, String testId, int iconResId, int nameResId) {
        ApiTestDetailFragment instance;
        try {
            instance = subclass.newInstance();
        } catch (Exception ex) {
            // Should never happen.
            Log.e("[Smart_Card_Reader_LOG]", ex.toString());
            throw new RuntimeException(ex);
        }
        Bundle bundle = new Bundle();
        bundle.putString(API_TEST_ID, testId);
        bundle.putInt(ICON_RES_ID, iconResId);
        bundle.putInt(NAME_STR_ID, nameResId);
        instance.setArguments(bundle);


        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView view = new TextView(inflater.getContext());
        view.setText(inflater.getContext().getString(R.string.lbl_under_construction));
        return view;
    }

    public int getNameStringId() {
        return getArguments().getInt(NAME_STR_ID);
    }

    public int getIconResourceId() {
        return getArguments().getInt(ICON_RES_ID);
    }

    public String getApiTestId() {
        return getArguments().getString(API_TEST_ID);
    }

    /**
     * Hide the keyboard.
     */
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getActivity().getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(getActivity());
        }
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
