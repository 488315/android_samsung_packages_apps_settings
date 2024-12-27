package com.android.settings.network.telephony.cdma;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;

import androidx.preference.ListPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CdmaListPreference extends ListPreference {
    public TelephonyManager mTelephonyManager;

    public CdmaListPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // androidx.preference.DialogPreference, androidx.preference.Preference
    public final void onClick() {
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager == null || !telephonyManager.getEmergencyCallbackMode()) {
            super.onClick();
        }
    }
}
