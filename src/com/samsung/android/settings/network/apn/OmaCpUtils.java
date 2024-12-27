package com.samsung.android.settings.network.apn;

import android.database.ContentObserver;
import android.net.Uri;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class OmaCpUtils {
    public static final Uri CP_URI = Uri.parse("content://com.wsomacp.messagelist");
    public static FragmentActivity sActivity;
    public static AnonymousClass1 sObserver;
    public static int sOmaCpCounts;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.network.apn.OmaCpUtils$1, reason: invalid class name */
    public final class AnonymousClass1 extends ContentObserver {
        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "mOmaCpObserver.onChange(), selfUpdate=", "OmaCpUtils", z);
            FragmentActivity fragmentActivity = OmaCpUtils.sActivity;
            if (fragmentActivity != null) {
                fragmentActivity.invalidateOptionsMenu();
            }
        }
    }
}
