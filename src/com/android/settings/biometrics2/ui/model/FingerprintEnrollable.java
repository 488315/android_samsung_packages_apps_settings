package com.android.settings.biometrics2.ui.model;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0010\n"
                + "\u0000\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001¨\u0006\u0002"
        },
        d2 = {
            "Lcom/android/settings/biometrics2/ui/model/FingerprintEnrollable;",
            ApnSettings.MVNO_NONE,
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintEnrollable {
    public static final /* synthetic */ FingerprintEnrollable[] $VALUES;
    public static final FingerprintEnrollable FINGERPRINT_ENROLLABLE_ERROR_REACH_MAX;
    public static final FingerprintEnrollable FINGERPRINT_ENROLLABLE_OK;

    static {
        FingerprintEnrollable fingerprintEnrollable =
                new FingerprintEnrollable("FINGERPRINT_ENROLLABLE_UNKNOWN", 0);
        FingerprintEnrollable fingerprintEnrollable2 =
                new FingerprintEnrollable("FINGERPRINT_ENROLLABLE_OK", 1);
        FINGERPRINT_ENROLLABLE_OK = fingerprintEnrollable2;
        FingerprintEnrollable fingerprintEnrollable3 =
                new FingerprintEnrollable("FINGERPRINT_ENROLLABLE_ERROR_REACH_MAX", 2);
        FINGERPRINT_ENROLLABLE_ERROR_REACH_MAX = fingerprintEnrollable3;
        FingerprintEnrollable[] fingerprintEnrollableArr = {
            fingerprintEnrollable, fingerprintEnrollable2, fingerprintEnrollable3
        };
        $VALUES = fingerprintEnrollableArr;
        EnumEntriesKt.enumEntries(fingerprintEnrollableArr);
    }

    public static FingerprintEnrollable valueOf(String str) {
        return (FingerprintEnrollable) Enum.valueOf(FingerprintEnrollable.class, str);
    }

    public static FingerprintEnrollable[] values() {
        return (FingerprintEnrollable[]) $VALUES.clone();
    }
}
