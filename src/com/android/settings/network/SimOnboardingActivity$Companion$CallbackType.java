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
            "com/android/settings/network/SimOnboardingActivity$Companion$CallbackType",
            ApnSettings.MVNO_NONE,
            "Lcom/android/settings/network/SimOnboardingActivity$Companion$CallbackType;",
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
public final class SimOnboardingActivity$Companion$CallbackType {
    public static final /* synthetic */ SimOnboardingActivity$Companion$CallbackType[] $VALUES;
    public static final SimOnboardingActivity$Companion$CallbackType CALLBACK_ENABLE_DSDS;
    public static final SimOnboardingActivity$Companion$CallbackType CALLBACK_ERROR;
    public static final SimOnboardingActivity$Companion$CallbackType CALLBACK_FINISH;
    public static final SimOnboardingActivity$Companion$CallbackType CALLBACK_ONBOARDING_COMPLETE;
    public static final SimOnboardingActivity$Companion$CallbackType CALLBACK_SETUP_NAME;
    public static final SimOnboardingActivity$Companion$CallbackType CALLBACK_SETUP_PRIMARY_SIM;
    private final int value;

    static {
        SimOnboardingActivity$Companion$CallbackType simOnboardingActivity$Companion$CallbackType =
                new SimOnboardingActivity$Companion$CallbackType("CALLBACK_ERROR", 0, -1);
        CALLBACK_ERROR = simOnboardingActivity$Companion$CallbackType;
        SimOnboardingActivity$Companion$CallbackType simOnboardingActivity$Companion$CallbackType2 =
                new SimOnboardingActivity$Companion$CallbackType(
                        "CALLBACK_ONBOARDING_COMPLETE", 1, 1);
        CALLBACK_ONBOARDING_COMPLETE = simOnboardingActivity$Companion$CallbackType2;
        SimOnboardingActivity$Companion$CallbackType simOnboardingActivity$Companion$CallbackType3 =
                new SimOnboardingActivity$Companion$CallbackType("CALLBACK_ENABLE_DSDS", 2, 2);
        CALLBACK_ENABLE_DSDS = simOnboardingActivity$Companion$CallbackType3;
        SimOnboardingActivity$Companion$CallbackType simOnboardingActivity$Companion$CallbackType4 =
                new SimOnboardingActivity$Companion$CallbackType("CALLBACK_SETUP_NAME", 3, 3);
        CALLBACK_SETUP_NAME = simOnboardingActivity$Companion$CallbackType4;
        SimOnboardingActivity$Companion$CallbackType simOnboardingActivity$Companion$CallbackType5 =
                new SimOnboardingActivity$Companion$CallbackType(
                        "CALLBACK_SETUP_PRIMARY_SIM", 4, 4);
        CALLBACK_SETUP_PRIMARY_SIM = simOnboardingActivity$Companion$CallbackType5;
        SimOnboardingActivity$Companion$CallbackType simOnboardingActivity$Companion$CallbackType6 =
                new SimOnboardingActivity$Companion$CallbackType("CALLBACK_FINISH", 5, 5);
        CALLBACK_FINISH = simOnboardingActivity$Companion$CallbackType6;
        SimOnboardingActivity$Companion$CallbackType[]
                simOnboardingActivity$Companion$CallbackTypeArr = {
            simOnboardingActivity$Companion$CallbackType,
            simOnboardingActivity$Companion$CallbackType2,
            simOnboardingActivity$Companion$CallbackType3,
            simOnboardingActivity$Companion$CallbackType4,
            simOnboardingActivity$Companion$CallbackType5,
            simOnboardingActivity$Companion$CallbackType6
        };
        $VALUES = simOnboardingActivity$Companion$CallbackTypeArr;
        EnumEntriesKt.enumEntries(simOnboardingActivity$Companion$CallbackTypeArr);
    }

    public SimOnboardingActivity$Companion$CallbackType(String str, int i, int i2) {
        this.value = i2;
    }

    public static SimOnboardingActivity$Companion$CallbackType valueOf(String str) {
        return (SimOnboardingActivity$Companion$CallbackType)
                Enum.valueOf(SimOnboardingActivity$Companion$CallbackType.class, str);
    }

    public static SimOnboardingActivity$Companion$CallbackType[] values() {
        return (SimOnboardingActivity$Companion$CallbackType[]) $VALUES.clone();
    }
}
