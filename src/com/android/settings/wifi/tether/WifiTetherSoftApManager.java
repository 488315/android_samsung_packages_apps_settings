package com.android.settings.wifi.tether;

import android.content.Context;
import android.icu.text.MessageFormat;
import android.net.wifi.WifiManager;
import android.os.Handler;

import com.android.settings.R;
import com.android.settingslib.PrimarySwitchPreference;
import com.android.settingslib.core.AbstractPreferenceController;

import kotlin.jvm.internal.Intrinsics;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiTetherSoftApManager {
    public Handler mHandler;
    public WifiManagerSoftApCallback mSoftApCallback;
    public WifiManager mWifiManager;
    public WifiTetherPreferenceController.AnonymousClass1 mWifiTetherSoftApCallback;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WifiManagerSoftApCallback implements WifiManager.SoftApCallback {
        public WeakReference mMyClass;

        public final void onConnectedClientsChanged(List list) {
            WifiTetherPreferenceController wifiTetherPreferenceController;
            PrimarySwitchPreference primarySwitchPreference;
            Context context;
            WifiTetherSoftApManager wifiTetherSoftApManager =
                    (WifiTetherSoftApManager) this.mMyClass.get();
            if (wifiTetherSoftApManager == null
                    || (primarySwitchPreference =
                                    (wifiTetherPreferenceController =
                                                    WifiTetherPreferenceController.this)
                                            .mPreference)
                            == null
                    || wifiTetherPreferenceController.mSoftApState != 13) {
                return;
            }
            context = ((AbstractPreferenceController) wifiTetherPreferenceController).mContext;
            int size = list.size();
            Intrinsics.checkNotNullParameter(context, "context");
            MessageFormat messageFormat =
                    new MessageFormat(
                            context.getResources()
                                    .getString(R.string.wifi_tether_connected_summary),
                            Locale.getDefault());
            HashMap hashMap = new HashMap();
            hashMap.put("count", Integer.valueOf(size));
            String format = messageFormat.format(hashMap);
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
            primarySwitchPreference.setSummary(format);
        }

        public final void onStateChanged(int i, int i2) {
            WifiTetherSoftApManager wifiTetherSoftApManager =
                    (WifiTetherSoftApManager) this.mMyClass.get();
            if (wifiTetherSoftApManager == null) {
                return;
            }
            WifiTetherPreferenceController wifiTetherPreferenceController =
                    WifiTetherPreferenceController.this;
            wifiTetherPreferenceController.mSoftApState = i;
            wifiTetherPreferenceController.handleWifiApStateChanged(i, i2);
        }
    }
}
