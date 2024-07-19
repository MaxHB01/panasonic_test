package com.panasonic.toughpad.android.contract;

import com.panasonic.toughpad.android.contract.IMagStripeReader;
import com.panasonic.toughpad.android.api.magstripe.MagStripeData;

oneway interface IMagStripeListener {
    void onRead(in IMagStripeReader msrObj, in MagStripeData result);
}