package com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel;

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
            "Lcom/android/settings/biometrics/fingerprint2/ui/enrollment/viewmodel/FingerprintAction;",
            ApnSettings.MVNO_NONE,
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintAction {
    public static final /* synthetic */ FingerprintAction[] $VALUES;

    static {
        FingerprintAction[] fingerprintActionArr = {
            new FingerprintAction("NEXT", 0),
            new FingerprintAction("PREV", 1),
            new FingerprintAction("CONFIRM_DEVICE_SUCCESS", 2),
            new FingerprintAction("CONFIRM_DEVICE_FAIL", 3),
            new FingerprintAction("TRANSITION_FINISHED", 4),
            new FingerprintAction("DID_GO_TO_BACKGROUND", 5),
            new FingerprintAction("ACTIVITY_CREATED", 6),
            new FingerprintAction("NEGATIVE_BUTTON_PRESSED", 7),
            new FingerprintAction("USER_CLICKED_FINISH", 8),
            new FingerprintAction("ADD_ANOTHER", 9)
        };
        $VALUES = fingerprintActionArr;
        EnumEntriesKt.enumEntries(fingerprintActionArr);
    }

    public static FingerprintAction valueOf(String str) {
        return (FingerprintAction) Enum.valueOf(FingerprintAction.class, str);
    }

    public static FingerprintAction[] values() {
        return (FingerprintAction[]) $VALUES.clone();
    }
}
