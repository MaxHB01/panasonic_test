package com.panasonic.toughpad.android.sample.smartcard;

public interface OnTaskCompleteListener {
    /**
     * Listen the completed task.
     *
     * @param taskResponse CONNECTED / TRANSMITTED / DISCONNECTED / ENABLED / DISABLED
     * @param result   result of task.
     */
    void onTaskComplete(SmartCardTestFragment.TaskResponse taskResponse, boolean result, SmartCardTestFragment.ErrorType errorType);
}
