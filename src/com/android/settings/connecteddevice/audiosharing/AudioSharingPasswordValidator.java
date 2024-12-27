package com.android.settings.connecteddevice.audiosharing;

import com.android.settings.widget.ValidatedEditTextPreference;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AudioSharingPasswordValidator implements ValidatedEditTextPreference.Validator {
    @Override // com.android.settings.widget.ValidatedEditTextPreference.Validator
    public final boolean isTextValid(String str) {
        if (str == null) {
            return false;
        }
        Charset charset = StandardCharsets.UTF_8;
        if (str.getBytes(charset).length < 4 || str.getBytes(charset).length > 16) {
            return false;
        }
        return str.equals(new String(str.getBytes(charset), charset));
    }
}