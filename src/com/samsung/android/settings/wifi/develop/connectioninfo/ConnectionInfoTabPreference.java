package com.samsung.android.settings.wifi.develop.connectioninfo;

import android.content.Context;
import android.util.AttributeSet;

import com.android.settings.R;

import com.samsung.android.settings.wifi.develop.CommonTabPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ConnectionInfoTabPreference extends CommonTabPreference {
    public ConnectionInfoTabPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ConnectionInfoTabPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setLayoutResource(R.layout.sec_wifi_develop_connection_info_tab_preference);
    }

    public ConnectionInfoTabPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ConnectionInfoTabPreference(Context context) {
        this(context, null);
    }
}
