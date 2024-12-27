package com.android.settings.biometrics.fingerprint2.ui.settings.fragment;

import android.text.InputFilter;
import android.text.Spanned;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintSettingsRenameDialog$getFilters$filter$1 implements InputFilter {
    @Override // android.text.InputFilter
    public final CharSequence filter(
            CharSequence source, int i, int i2, Spanned spanned, int i3, int i4) {
        Intrinsics.checkNotNullParameter(source, "source");
        while (i < i2) {
            if (source.charAt(i) < ' ') {
                return ApnSettings.MVNO_NONE;
            }
            i++;
        }
        return null;
    }
}
