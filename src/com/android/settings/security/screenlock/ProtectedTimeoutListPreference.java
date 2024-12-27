package com.android.settings.security.screenlock;

import android.content.Context;
import android.util.AttributeSet;

import com.android.settings.display.TimeoutListPreference;
import com.android.settings.wifi.dpp.WifiDppUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ProtectedTimeoutListPreference extends TimeoutListPreference {
    public ProtectedTimeoutListPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.settings.RestrictedListPreference, androidx.preference.Preference
    public final void performClick() {
        WifiDppUtils.showLockScreen(
                getContext(),
                new Runnable() { // from class:
                                 // com.android.settings.security.screenlock.ProtectedTimeoutListPreference$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        super /*com.android.settings.RestrictedListPreference*/.performClick();
                    }
                });
    }
}
