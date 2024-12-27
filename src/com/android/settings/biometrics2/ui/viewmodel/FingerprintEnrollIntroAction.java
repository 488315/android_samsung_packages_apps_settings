package com.android.settings.biometrics2.ui.viewmodel;

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
            "Lcom/android/settings/biometrics2/ui/viewmodel/FingerprintEnrollIntroAction;",
            ApnSettings.MVNO_NONE,
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintEnrollIntroAction {
    public static final /* synthetic */ FingerprintEnrollIntroAction[] $VALUES;
    public static final FingerprintEnrollIntroAction CONTINUE_ENROLL;
    public static final FingerprintEnrollIntroAction DONE_AND_FINISH;
    public static final FingerprintEnrollIntroAction SKIP_OR_CANCEL;

    static {
        FingerprintEnrollIntroAction fingerprintEnrollIntroAction =
                new FingerprintEnrollIntroAction("DONE_AND_FINISH", 0);
        DONE_AND_FINISH = fingerprintEnrollIntroAction;
        FingerprintEnrollIntroAction fingerprintEnrollIntroAction2 =
                new FingerprintEnrollIntroAction("CONTINUE_ENROLL", 1);
        CONTINUE_ENROLL = fingerprintEnrollIntroAction2;
        FingerprintEnrollIntroAction fingerprintEnrollIntroAction3 =
                new FingerprintEnrollIntroAction("SKIP_OR_CANCEL", 2);
        SKIP_OR_CANCEL = fingerprintEnrollIntroAction3;
        FingerprintEnrollIntroAction[] fingerprintEnrollIntroActionArr = {
            fingerprintEnrollIntroAction,
            fingerprintEnrollIntroAction2,
            fingerprintEnrollIntroAction3
        };
        $VALUES = fingerprintEnrollIntroActionArr;
        EnumEntriesKt.enumEntries(fingerprintEnrollIntroActionArr);
    }

    public static FingerprintEnrollIntroAction valueOf(String str) {
        return (FingerprintEnrollIntroAction) Enum.valueOf(FingerprintEnrollIntroAction.class, str);
    }

    public static FingerprintEnrollIntroAction[] values() {
        return (FingerprintEnrollIntroAction[]) $VALUES.clone();
    }
}
