package com.android.settings.bluetooth;

import android.text.InputFilter;
import android.text.Spanned;

import androidx.annotation.Keep;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class Utf8ByteLengthFilter implements InputFilter {
    public final int mMaxBytes;

    @Keep
    public Utf8ByteLengthFilter(int i) {
        this.mMaxBytes = i;
    }

    @Override // android.text.InputFilter
    public CharSequence filter(
            CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        int i5 = i;
        int i6 = 0;
        while (true) {
            int i7 = 3;
            if (i5 >= i2) {
                break;
            }
            char charAt = charSequence.charAt(i5);
            if (charAt < 128) {
                i7 = 1;
            } else if (charAt < 2048) {
                i7 = 2;
            }
            i6 += i7;
            i5++;
        }
        int length = spanned.length();
        int i8 = 0;
        for (int i9 = 0; i9 < length; i9++) {
            if (i9 < i3 || i9 >= i4) {
                char charAt2 = spanned.charAt(i9);
                i8 += charAt2 < 128 ? 1 : charAt2 < 2048 ? 2 : 3;
            }
        }
        int i10 = this.mMaxBytes - i8;
        if (i10 <= 0) {
            return ApnSettings.MVNO_NONE;
        }
        if (i10 >= i6) {
            return null;
        }
        for (int i11 = i; i11 < i2; i11++) {
            char charAt3 = charSequence.charAt(i11);
            i10 -= charAt3 < 128 ? 1 : charAt3 < 2048 ? 2 : 3;
            if (i10 < 0) {
                return charSequence.subSequence(i, i11);
            }
        }
        return null;
    }
}
