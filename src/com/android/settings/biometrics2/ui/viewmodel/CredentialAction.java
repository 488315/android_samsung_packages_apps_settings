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
            "Lcom/android/settings/biometrics2/ui/viewmodel/CredentialAction;",
            ApnSettings.MVNO_NONE,
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class CredentialAction {
    public static final /* synthetic */ CredentialAction[] $VALUES;
    public static final CredentialAction CREDENTIAL_VALID;
    public static final CredentialAction FAIL_NEED_TO_CHOOSE_LOCK;
    public static final CredentialAction FAIL_NEED_TO_CONFIRM_LOCK;
    public static final CredentialAction IS_GENERATING_CHALLENGE;

    static {
        CredentialAction credentialAction = new CredentialAction("CREDENTIAL_VALID", 0);
        CREDENTIAL_VALID = credentialAction;
        CredentialAction credentialAction2 = new CredentialAction("IS_GENERATING_CHALLENGE", 1);
        IS_GENERATING_CHALLENGE = credentialAction2;
        CredentialAction credentialAction3 = new CredentialAction("FAIL_NEED_TO_CHOOSE_LOCK", 2);
        FAIL_NEED_TO_CHOOSE_LOCK = credentialAction3;
        CredentialAction credentialAction4 = new CredentialAction("FAIL_NEED_TO_CONFIRM_LOCK", 3);
        FAIL_NEED_TO_CONFIRM_LOCK = credentialAction4;
        CredentialAction[] credentialActionArr = {
            credentialAction, credentialAction2, credentialAction3, credentialAction4
        };
        $VALUES = credentialActionArr;
        EnumEntriesKt.enumEntries(credentialActionArr);
    }

    public static CredentialAction valueOf(String str) {
        return (CredentialAction) Enum.valueOf(CredentialAction.class, str);
    }

    public static CredentialAction[] values() {
        return (CredentialAction[]) $VALUES.clone();
    }
}
