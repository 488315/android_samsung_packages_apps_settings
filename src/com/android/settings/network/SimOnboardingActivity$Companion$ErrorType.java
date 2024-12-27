package com.android.settings.network;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.settings.ImsProfile;

import kotlin.Metadata;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0012\n"
                + "\u0000\n"
                + "\u0002\u0010\u0010\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001R\u0017\u0010\u0004\u001a\u00020\u00038\u0006¢\u0006\f\n"
                + "\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"
        },
        d2 = {
            "com/android/settings/network/SimOnboardingActivity$Companion$ErrorType",
            ApnSettings.MVNO_NONE,
            "Lcom/android/settings/network/SimOnboardingActivity$Companion$ErrorType;",
            ApnSettings.MVNO_NONE,
            "value",
            ImsProfile.TIMER_NAME_I,
            "getValue",
            "()I",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class SimOnboardingActivity$Companion$ErrorType {
    public static final /* synthetic */ SimOnboardingActivity$Companion$ErrorType[] $VALUES;
    public static final SimOnboardingActivity$Companion$ErrorType ERROR_ENABLE_DSDS;
    public static final SimOnboardingActivity$Companion$ErrorType ERROR_EUICC_SLOT;
    public static final SimOnboardingActivity$Companion$ErrorType ERROR_NONE;
    public static final SimOnboardingActivity$Companion$ErrorType ERROR_REMOVABLE_SLOT;
    private final int value;

    static {
        SimOnboardingActivity$Companion$ErrorType simOnboardingActivity$Companion$ErrorType =
                new SimOnboardingActivity$Companion$ErrorType("ERROR_NONE", 0, -1);
        ERROR_NONE = simOnboardingActivity$Companion$ErrorType;
        SimOnboardingActivity$Companion$ErrorType simOnboardingActivity$Companion$ErrorType2 =
                new SimOnboardingActivity$Companion$ErrorType("ERROR_EUICC_SLOT", 1, 1);
        ERROR_EUICC_SLOT = simOnboardingActivity$Companion$ErrorType2;
        SimOnboardingActivity$Companion$ErrorType simOnboardingActivity$Companion$ErrorType3 =
                new SimOnboardingActivity$Companion$ErrorType("ERROR_REMOVABLE_SLOT", 2, 2);
        ERROR_REMOVABLE_SLOT = simOnboardingActivity$Companion$ErrorType3;
        SimOnboardingActivity$Companion$ErrorType simOnboardingActivity$Companion$ErrorType4 =
                new SimOnboardingActivity$Companion$ErrorType("ERROR_ENABLE_DSDS", 3, 3);
        ERROR_ENABLE_DSDS = simOnboardingActivity$Companion$ErrorType4;
        SimOnboardingActivity$Companion$ErrorType[] simOnboardingActivity$Companion$ErrorTypeArr = {
            simOnboardingActivity$Companion$ErrorType,
            simOnboardingActivity$Companion$ErrorType2,
            simOnboardingActivity$Companion$ErrorType3,
            simOnboardingActivity$Companion$ErrorType4
        };
        $VALUES = simOnboardingActivity$Companion$ErrorTypeArr;
        EnumEntriesKt.enumEntries(simOnboardingActivity$Companion$ErrorTypeArr);
    }

    public SimOnboardingActivity$Companion$ErrorType(String str, int i, int i2) {
        this.value = i2;
    }

    public static SimOnboardingActivity$Companion$ErrorType valueOf(String str) {
        return (SimOnboardingActivity$Companion$ErrorType)
                Enum.valueOf(SimOnboardingActivity$Companion$ErrorType.class, str);
    }

    public static SimOnboardingActivity$Companion$ErrorType[] values() {
        return (SimOnboardingActivity$Companion$ErrorType[]) $VALUES.clone();
    }
}
