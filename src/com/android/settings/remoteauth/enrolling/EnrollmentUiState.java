package com.android.settings.remoteauth.enrolling;

import com.samsung.android.knox.foresight.KnoxForesight;
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
            "Lcom/android/settings/remoteauth/enrolling/EnrollmentUiState;",
            ApnSettings.MVNO_NONE,
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class EnrollmentUiState {
    public static final /* synthetic */ EnrollmentUiState[] $VALUES;
    public static final EnrollmentUiState FINDING_DEVICES;
    public static final EnrollmentUiState NONE;

    static {
        EnrollmentUiState enrollmentUiState = new EnrollmentUiState("NONE", 0);
        NONE = enrollmentUiState;
        EnrollmentUiState enrollmentUiState2 = new EnrollmentUiState("FINDING_DEVICES", 1);
        FINDING_DEVICES = enrollmentUiState2;
        EnrollmentUiState[] enrollmentUiStateArr = {
            enrollmentUiState,
            enrollmentUiState2,
            new EnrollmentUiState("ENROLLING", 2),
            new EnrollmentUiState(KnoxForesight.SUCCESS, 3)
        };
        $VALUES = enrollmentUiStateArr;
        EnumEntriesKt.enumEntries(enrollmentUiStateArr);
    }

    public static EnrollmentUiState valueOf(String str) {
        return (EnrollmentUiState) Enum.valueOf(EnrollmentUiState.class, str);
    }

    public static EnrollmentUiState[] values() {
        return (EnrollmentUiState[]) $VALUES.clone();
    }
}
