package com.android.settingslib;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;

import com.android.settingslib.mobile.TelephonyIcons;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SignalIcon$MobileIconGroup {
    public final int dataContentDescription;
    public final String name;

    public SignalIcon$MobileIconGroup(String str, int i) {
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup =
                TelephonyIcons.CARRIER_NETWORK_CHANGE;
        this.name = str;
        this.dataContentDescription = i;
    }

    public final String toString() {
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(
                new StringBuilder("IconGroup("), this.name, ")");
    }
}
