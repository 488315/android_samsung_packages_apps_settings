package com.samsung.scpm.odm.dos.common;

import android.os.Build;
import android.util.Log;

import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class LOG {
    public static final boolean ENABLED = "eng".equals(Build.TYPE);

    public static void e(String str, String str2) {
        Locale locale = Locale.US;
        Log.e(
                "[E]" + str,
                AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(
                        str2, " ", ApnSettings.MVNO_NONE));
    }
}