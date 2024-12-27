package com.android.settings.wifi.calling;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.CarrierConfigManager;
import android.util.Log;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;

import com.android.internal.annotations.VisibleForTesting;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@VisibleForTesting
/* loaded from: classes2.dex */
public abstract class DisclaimerItem {
    public final CarrierConfigManager mCarrierConfigManager;
    public final Context mContext;
    public final int mSubId;

    public DisclaimerItem(FragmentActivity fragmentActivity, int i) {
        this.mContext = fragmentActivity;
        this.mSubId = i;
        this.mCarrierConfigManager =
                (CarrierConfigManager)
                        fragmentActivity.getSystemService(CarrierConfigManager.class);
    }

    public abstract int getMessageId();

    public abstract String getName();

    public abstract String getPrefKey();

    public abstract int getTitleId();

    public final void logd(String str) {
        Log.d(getName(), "[" + this.mSubId + "] " + str);
    }

    public boolean shouldShow() {
        String prefKey = getPrefKey();
        SharedPreferences sharedPreferences =
                this.mContext.getSharedPreferences("wfc_disclaimer_prefs", 0);
        StringBuilder m =
                EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(prefKey);
        m.append(this.mSubId);
        if (sharedPreferences.getBoolean(m.toString(), false)) {
            logd("shouldShow: false due to a user has already agreed.");
            return false;
        }
        logd("shouldShow: true");
        return true;
    }
}
