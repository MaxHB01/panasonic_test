package com.panasonic.toughpad.android.sample.smartcard;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.panasonic.toughpad.android.api.ToughpadApi;
import com.panasonic.toughpad.android.api.ToughpadApiListener;
import com.panasonic.toughpad.android.api.smartcard.SmartCardException;
import com.panasonic.toughpad.android.api.smartcard.SmartCardNotPresentException;
import com.panasonic.toughpad.android.api.smartcard.SmartCardReader;
import com.panasonic.toughpad.android.api.smartcard.SmartCardReaderManager;
import com.panasonic.toughpad.android.sample.ApiTestDetailFragment;
import com.panasonic.toughpad.android.sample.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.panasonic.toughpad.android.sample.smartcard.SmartCardTestFragment.TaskResponse.CONNECTED;
import static com.panasonic.toughpad.android.sample.smartcard.SmartCardTestFragment.TaskResponse.DISABLED;
import static com.panasonic.toughpad.android.sample.smartcard.SmartCardTestFragment.TaskResponse.DISCONNECTED;
import static com.panasonic.toughpad.android.sample.smartcard.SmartCardTestFragment.TaskResponse.ENABLED;
import static com.panasonic.toughpad.android.sample.smartcard.SmartCardTestFragment.TaskResponse.TRANSMITTED;

public class SmartCardTestFragment extends ApiTestDetailFragment implements ToughpadApiListener, View.OnClickListener, AdapterView.OnItemSelectedListener, OnTaskCompleteListener {
    private Spinner spnSCRList;
    private CompoundButton btnEnable;
    private Button btnSendCommand;
    private TextView txtSCRLog;
    private ScrollView sclSCRLog;
    private EditText edtAPDUCommand;
    private TextView txtDeviceInfo;

    private List<SmartCardReader> readers;
    private SmartCardReader selectedReader;

    private byte[] mAPDUCommand;
    private String mAPDUResponse = "null";
    private String messageLog = null;
    private byte[] mATRvalue;
    private APDUResponseHandler apduResponseHandler = new APDUResponseHandler();

    public SmartCardTestFragment() {
    }

    /**
     * State of done task
     */
    public enum TaskResponse {
        CONNECTED, TRANSMITTED, DISCONNECTED, ENABLED, DISABLED
    }

    /**
     * Error type when communicate with reader and smart card.
     */
    public enum ErrorType {
        API_RETURN_TYPE, EXCEPTION_RETURN_TYPE
    }

    @Override
    public void onTaskComplete(TaskResponse taskResponse, boolean result, ErrorType errorType) {
        if (!result && errorType == ErrorType.API_RETURN_TYPE && (taskResponse == CONNECTED || taskResponse == TRANSMITTED || taskResponse == DISCONNECTED)) {
            btnSendCommand.setEnabled(true);
            printLogText(getString(R.string.msg_smart_card_not_available));
            showResultDialog(getString(R.string.title_warning), getString(R.string.msg_smart_card_not_available), true);
        } else if (!result && errorType == ErrorType.EXCEPTION_RETURN_TYPE && taskResponse == CONNECTED) {
            btnSendCommand.setEnabled(true);
            printLogText(getString(R.string.msg_smart_card_not_available));
            showResultDialog(getString(R.string.title_warning), getString(R.string.msg_smart_card_not_available), true);
        } else if (!result && (taskResponse == ENABLED || taskResponse == DISABLED)) {
            printLogText(getString(R.string.msg_smart_card_reader_not_found));
        } else {
            switch (taskResponse) {
                case CONNECTED:
                    APDUCommandSender sender = new APDUCommandSender();
                    sender.execute(selectedReader);
                    break;
                case TRANSMITTED:
                    SmartCardDisconnectionHandler smarCardDisconnectionHandler = new SmartCardDisconnectionHandler();
                    smarCardDisconnectionHandler.execute(selectedReader);
                    break;
                case DISCONNECTED:
                    btnSendCommand.setEnabled(true);
                    printLogText("ATR value: " + Utils.byteArrayToHex(mATRvalue));
                    printLogText(getString(R.string.txt_apdu_response) + mAPDUResponse);
                    if (messageLog != null) {
                        printLogText(getString(R.string.txt_apdu_response) + messageLog);
                        showResultDialog(getString(R.string.title_apdu_response), "Protocol value: T = 0 " + "\n" + getString(R.string.txt_atr_response) + Utils.byteArrayToHex(mATRvalue) + "\n" + getString(R.string.txt_apdu_response) + mAPDUResponse);
                        resetValue();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Asynctask connect to the smart card.
     */
    @SuppressLint("StaticFieldLeak")
    private class SmartCardConnectionHandler extends AsyncTask<SmartCardReader, Void, Boolean> {
        private ErrorType errorType;

        @Override
        protected Boolean doInBackground(SmartCardReader... smartCardReaders) {
            try {
                if (smartCardReaders[0].isCardPresent()) {
                    smartCardReaders[0].beginExclusive();
                    mATRvalue = smartCardReaders[0].connect("T=0");
                } else {
                    errorType = ErrorType.API_RETURN_TYPE;
                    return false;
                }
            } catch (SmartCardException | SmartCardNotPresentException e) {
                Log.e("[Smart_Card_Reader_LOG]", e.toString());
                handleError(e);
                errorType = ErrorType.EXCEPTION_RETURN_TYPE;
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            onTaskComplete(CONNECTED, aBoolean, errorType);
        }
    }

    /**
     * Asynctask disconnect to the smart card.
     */
    @SuppressLint("StaticFieldLeak")
    private class SmartCardDisconnectionHandler extends AsyncTask<SmartCardReader, Void, Boolean> {
        private ErrorType errorType;

        @Override
        protected Boolean doInBackground(SmartCardReader... smartCardReaders) {
            try {
                if (smartCardReaders[0].isCardPresent()) {
                    smartCardReaders[0].endExclusive();
                    smartCardReaders[0].disconnect(true);
                } else {
                    errorType = ErrorType.API_RETURN_TYPE;
                    return false;
                }
            } catch (SmartCardException e) {
                Log.e("[Smart_Card_Reader_LOG]", e.toString());
                handleError(e);
                errorType = ErrorType.EXCEPTION_RETURN_TYPE;
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            onTaskComplete(DISCONNECTED, aBoolean, errorType);
        }
    }

    /**
     * Asynctask Sending APDU command from reader to smart card.
     */
    @SuppressLint("StaticFieldLeak")
    private class APDUCommandSender extends AsyncTask<SmartCardReader, Void, Boolean> {
        private byte[] APDUResponse;
        private ErrorType errorType;

        @Override
        protected Boolean doInBackground(SmartCardReader... smartCardReaders) {
            try {
                if (!smartCardReaders[0].isCardPresent()) {
                    errorType = ErrorType.API_RETURN_TYPE;
                    return false;
                }
                if (mAPDUCommand != null) {
                    APDUResponse = smartCardReaders[0].transmit(mAPDUCommand);
                }
                validateAPDUResponse(APDUResponse);
            } catch (IllegalArgumentException | SmartCardException e) {
                Log.e("[Smart_Card_Reader_LOG]", e.toString());
                handleError(e);
                errorType = ErrorType.EXCEPTION_RETURN_TYPE;
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            onTaskComplete(TRANSMITTED, aBoolean, errorType);
        }
    }

    /**
     * Judge the APDU response.
     *
     * @param value value of APDU response
     */
    public void validateAPDUResponse(byte[] value) {
        APDUResponse apduResponse = new APDUResponse(value);
        mAPDUResponse = apduResponse.getHexStringAPDUResponse();
        String sw = apduResponse.getSW();
        String sw1 = apduResponse.getSW1();
        Map<String, HashMap<String, Integer>> list = apduResponseHandler.getListStatusWord();
        if (list.containsKey(sw1)) {
            HashMap<String, Integer> list1 = list.get(sw1);
            if (list1.containsKey(sw)) {
                messageLog = getString(list1.get(sw));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apitest_smart_card, container, false);
        readers = null;
        selectedReader = null;
        spnSCRList = view.findViewById(R.id.spnSCRList);
        btnEnable = view.findViewById(R.id.btnEnable);
        btnSendCommand = view.findViewById(R.id.btnStartReading);
        txtSCRLog = view.findViewById(R.id.txtSCRLog);
        sclSCRLog = view.findViewById(R.id.sclSCRLog);
        txtDeviceInfo = view.findViewById(R.id.txtDeviceInfo);
        edtAPDUCommand = view.findViewById(R.id.edtAPDUCommand);

        spnSCRList.setOnItemSelectedListener(this);
        btnEnable.setOnClickListener(this);
        btnSendCommand.setOnClickListener(this);
        // Initialize th Toughpad API
        Log.d("[Smart_Card_Reader_LOG]", "onCreateView");
        if (getActivity() != null && !ToughpadApi.isAlreadyInitialized()) {
            Log.d("[Smart_Card_Reader_LOG]", "ToughpadApi is initializing");
            isAPIInitialized = true;
            ToughpadApi.initialize(getActivity(), this);
        }
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("[Smart_Card_Reader_LOG]", "onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("[Smart_Card_Reader_LOG]", "onResume");
//        readers = null;
//        selectedReader = null;
        onSmartCardReaderDisabled();
        edtAPDUCommand.setFocusable(false);
        txtSCRLog.setText("");
        if (!ToughpadApi.isAlreadyInitialized() && !isAPIInitialized) {
            ToughpadApi.initialize(getActivity(), this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("[Smart_Card_Reader_LOG]", "onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("[Smart_Card_Reader_LOG]", "onDestroyView");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("[Smart_Card_Reader_LOG]", "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("[Smart_Card_Reader_LOG]", "onStop");
        isAPIInitialized = false;
        ToughpadApi.destroy();
    }

    @Override
    public void onClick(View view) {
        if (view == btnSendCommand) {
            hideKeyboard();
            btnSendCommand.setEnabled(false);
            startReadingCard();
        } else if (view == btnEnable) {
            btnEnable.setEnabled(false);
            if (selectedReader == null) {
                Log.d("[Smart_Card_Reader_LOG]", "SelectedReader == null");

            } else {
                if (!selectedReader.isEnabled()) {
                    try {
                        Log.d("[Smart_Card_Reader_LOG]", "mode = ON");
                        selectedReader.enable();
                    } catch (SmartCardException e) {
                        e.printStackTrace();
                    }
                    if (selectedReader != null && getContext() != null) {
                        printLogText(getString(R.string.lbl_enabled_device) + " " + selectedReader.getReaderName());
                        onSmartCardReaderEnabled();
                        btnEnable.setEnabled(true);
                    }
                } else {
                    try {
                        selectedReader.disable();
                    } catch (SmartCardException e) {
                        e.printStackTrace();
                    }
                    if (selectedReader != null && getContext() != null) {
                        printLogText(getString(R.string.lbl_disabled_device) + " " + selectedReader.getReaderName());
                        onSmartCardReaderDisabled();
                        initReader();

                    }
                }
            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (readers != null) {
            selectedReader = readers.get(position);
            if (selectedReader != null) {
                btnEnable.setEnabled(true);
            }
            Log.d("[Smart_Card_Reader_LOG]", "SelectedReader is : " + selectedReader);
        } else {
            Log.d("[Smart_Card_Reader_LOG]", "Readers is null");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d("[Smart_Card_Reader_LOG]", "onNothingSelected");
    }

    /**
     * Start reading smart card after the user clicks on Start reading card button.
     */
    private void startReadingCard() {
        if (validateAPDUCommand(edtAPDUCommand.getText().toString())) {
            mAPDUCommand = Utils.hexStringToByteArray(edtAPDUCommand.getText().toString());
            SmartCardConnectionHandler task = new SmartCardConnectionHandler();
            task.execute(selectedReader);
        } else {
            showResultDialog(getString(R.string.title_scr_error), getString(R.string.msg_apdu_command_invalid));
            btnSendCommand.setEnabled(true);
        }
    }

    /**
     * Validate the APDU command.
     *
     * @param s APDU command type String.
     * @return true if it's valid, false if it's invalid.
     */
    public boolean validateAPDUCommand(String s) {
        return !TextUtils.isEmpty(s) && s.length() >= 8 && s.length() % 2 == 0 && s.matches ("^[a-fA-F0-9]+$");
    }

    /**
     * Initialize the reader after detect available Smart card readers.
     *
     * @param i server Version.
     */
    @Override
    public void onApiConnected(int i) {
        Log.d("[Smart_Card_Reader_LOG]", "onApiConnected");
        initReader();
    }

    /**
     * Initialize the detected smart card reader.
     */
    private void initReader() {
        btnEnable.setEnabled(false);
        Log.d("[Smart_Card_Reader_LOG]", "initReader");
        try {
            readers = SmartCardReaderManager.getSmartCardReaders();
            Log.d("[Smart_Card_Reader_LOG]", "reader number : " + readers.size());
            List<String> readerNames = new ArrayList<String>();
            for (SmartCardReader reader : readers) {
                readerNames.add(reader.getReaderName());
                Log.d("[Smart_Card_Reader_LOG]", "reader name : " + reader.getReaderName());
            }
            spnSCRList.setAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_activated_1,
                    android.R.id.text1,
                    readerNames));
        } catch (SmartCardException | NullPointerException e) {
            Log.d("[Smart_Card_Reader_LOG]", e.toString());
            showResultDialog(getString(R.string.title_scr_error), getString(R.string.msg_smart_card_reader_not_found));
        }
    }

    @Override
    public void onApiDisconnected() {
        Log.d("[Smart_Card_Reader_LOG]", "onApiDisconnected");
    }

    /**
     * Print the logs text.
     *
     * @param text content logs.
     */
    private void printLogText(final String text) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                txtSCRLog.append(text + "\n");
                sclSCRLog.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    private void onSmartCardReaderEnabled() {
        Log.d("[Smart_Card_Reader_LOG]", "onSmartCardReaderEnabled");
        btnEnable.setChecked(true);
        btnSendCommand.setEnabled(true);
        txtDeviceInfo.setEnabled(true);
        edtAPDUCommand.setEnabled(true);
        edtAPDUCommand.setFocusableInTouchMode(true);
        // Fill in device info
        String sb = getString(R.string.lbl_reader_name, selectedReader.getReaderName()) + "\n" +
                getString(R.string.lbl_enable_device, getString(selectedReader.isEnabled() ? R.string.lbl_is_charging_yes : R.string.lbl_is_charging_no)) + "\n";
        txtDeviceInfo.setText(sb);

    }

    /**
     * Handle the errors, show dialog notification.
     *
     * @param ex exception.
     */
    private void handleError(final Exception ex) {
        final String message = ex.getMessage() != null ? ex.getMessage() : ex.getClass().getSimpleName();
        ex.printStackTrace();
        showResultDialog(ex.getClass().getSimpleName(), message);
    }

    /**
     * Show result dialog.
     *
     * @param title     title of dialog.
     * @param content   content of dialog.
     * @param isWarning distinction of warning and normal dialog.
     */
    public void showResultDialog(final String title, final String content, final boolean isWarning) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(content);
                builder.setTitle(title);
                if (isWarning) {
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setTitle(getString(R.string.title_warning));
                }
                builder.setCancelable(true);
                builder.show();
            }
        });
    }

    /**
     * Show the result dialog.
     *
     * @param title   title of dialog.
     * @param content content of dialog.
     */
    public void showResultDialog(final String title, final String content) {
        showResultDialog(title, content, false);
    }

    /**
     * Disable the smart card reader.
     */
    private void onSmartCardReaderDisabled() {
        Log.d("[Smart_Card_Reader_LOG]", "onSmartCardReaderDisabled");
        if (selectedReader != null && selectedReader.isEnabled()){
            try {
                selectedReader.disable();
            } catch (SmartCardException e) {
                e.printStackTrace();
            }
        }
        btnEnable.setChecked(false);
        btnSendCommand.setEnabled(false);
        edtAPDUCommand.setEnabled(false);
        txtDeviceInfo.setEnabled(false);
        txtDeviceInfo.setText("");
        edtAPDUCommand.setText("");
    }

    /**
     * Reset response value.
     */
    private void resetValue() {
        mAPDUCommand = null;
        mAPDUResponse = "null";
        messageLog = null;
        mATRvalue = null;
    }
}
