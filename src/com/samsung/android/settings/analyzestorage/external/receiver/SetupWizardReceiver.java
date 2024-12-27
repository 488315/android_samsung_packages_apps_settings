package com.samsung.android.settings.analyzestorage.external.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.samsung.android.settings.analyzestorage.domain.log.Log;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/samsung/android/settings/analyzestorage/external/receiver/SetupWizardReceiver;",
            "Landroid/content/BroadcastReceiver;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class SetupWizardReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        long currentTimeMillis = System.currentTimeMillis();
        Log.d("SetupWizardReceiver", "onReceive : " + currentTimeMillis);
        SharedPreferences.Editor edit =
                PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putLong("setup_wizard_complete_time", currentTimeMillis);
        edit.apply();
    }
}
