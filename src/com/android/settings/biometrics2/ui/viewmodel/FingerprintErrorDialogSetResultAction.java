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
            "Lcom/android/settings/biometrics2/ui/viewmodel/FingerprintErrorDialogSetResultAction;",
            ApnSettings.MVNO_NONE,
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintErrorDialogSetResultAction {
    public static final /* synthetic */ FingerprintErrorDialogSetResultAction[] $VALUES;
    public static final FingerprintErrorDialogSetResultAction
            FINGERPRINT_ERROR_DIALOG_ACTION_SET_RESULT_FINISH;
    public static final FingerprintErrorDialogSetResultAction
            FINGERPRINT_ERROR_DIALOG_ACTION_SET_RESULT_TIMEOUT;

    static {
        FingerprintErrorDialogSetResultAction fingerprintErrorDialogSetResultAction =
                new FingerprintErrorDialogSetResultAction(
                        "FINGERPRINT_ERROR_DIALOG_ACTION_SET_RESULT_FINISH", 0);
        FINGERPRINT_ERROR_DIALOG_ACTION_SET_RESULT_FINISH = fingerprintErrorDialogSetResultAction;
        FingerprintErrorDialogSetResultAction fingerprintErrorDialogSetResultAction2 =
                new FingerprintErrorDialogSetResultAction(
                        "FINGERPRINT_ERROR_DIALOG_ACTION_SET_RESULT_TIMEOUT", 1);
        FINGERPRINT_ERROR_DIALOG_ACTION_SET_RESULT_TIMEOUT = fingerprintErrorDialogSetResultAction2;
        FingerprintErrorDialogSetResultAction[] fingerprintErrorDialogSetResultActionArr = {
            fingerprintErrorDialogSetResultAction, fingerprintErrorDialogSetResultAction2
        };
        $VALUES = fingerprintErrorDialogSetResultActionArr;
        EnumEntriesKt.enumEntries(fingerprintErrorDialogSetResultActionArr);
    }

    public static FingerprintErrorDialogSetResultAction valueOf(String str) {
        return (FingerprintErrorDialogSetResultAction)
                Enum.valueOf(FingerprintErrorDialogSetResultAction.class, str);
    }

    public static FingerprintErrorDialogSetResultAction[] values() {
        return (FingerprintErrorDialogSetResultAction[]) $VALUES.clone();
    }
}
