package com.samsung.android.scloud.oem.lib.utils;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.security.MessageDigest;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SCloudUtil {
    public static void makeSHA1Hash(String str) {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
        messageDigest.reset();
        messageDigest.update(str.getBytes("UTF-8"));
        byte[] digest = messageDigest.digest();
        String str2 = ApnSettings.MVNO_NONE;
        for (byte b : digest) {
            StringBuilder m =
                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(str2);
            m.append(Integer.toString((b & 255) + 256, 16).substring(1));
            str2 = m.toString();
        }
    }
}
