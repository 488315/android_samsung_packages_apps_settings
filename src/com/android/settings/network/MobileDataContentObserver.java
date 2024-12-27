package com.android.settings.network;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.android.settings.network.telephony.AutoDataSwitchPreferenceController$$ExternalSyntheticLambda0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class MobileDataContentObserver extends ContentObserver {
    public AutoDataSwitchPreferenceController$$ExternalSyntheticLambda0 mListener;

    public static Uri getObservableUri(Context context, int i) {
        Uri uriFor = Settings.Global.getUriFor("mobile_data");
        if (((TelephonyManager) context.getSystemService(TelephonyManager.class))
                        .getActiveModemCount()
                == 1) {
            return uriFor;
        }
        return Settings.Global.getUriFor("mobile_data" + i);
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        super.onChange(z);
        AutoDataSwitchPreferenceController$$ExternalSyntheticLambda0
                autoDataSwitchPreferenceController$$ExternalSyntheticLambda0 = this.mListener;
        if (autoDataSwitchPreferenceController$$ExternalSyntheticLambda0 != null) {
            autoDataSwitchPreferenceController$$ExternalSyntheticLambda0.f$0.lambda$onResume$0();
        }
    }
}
