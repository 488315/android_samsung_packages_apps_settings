package com.android.systemui.biometrics.shared.model;

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
            "Lcom/android/systemui/biometrics/shared/model/FingerprintSensorType;",
            ApnSettings.MVNO_NONE,
            "frameworks__base__packages__SystemUI__shared__biometrics__android_common__BiometricsSharedLib"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintSensorType {
    public static final /* synthetic */ FingerprintSensorType[] $VALUES;
    public static final FingerprintSensorType HOME_BUTTON;
    public static final FingerprintSensorType POWER_BUTTON;
    public static final FingerprintSensorType REAR;
    public static final FingerprintSensorType UDFPS_OPTICAL;
    public static final FingerprintSensorType UDFPS_ULTRASONIC;
    public static final FingerprintSensorType UNKNOWN;

    static {
        FingerprintSensorType fingerprintSensorType = new FingerprintSensorType("UNKNOWN", 0);
        UNKNOWN = fingerprintSensorType;
        FingerprintSensorType fingerprintSensorType2 = new FingerprintSensorType("REAR", 1);
        REAR = fingerprintSensorType2;
        FingerprintSensorType fingerprintSensorType3 =
                new FingerprintSensorType("UDFPS_ULTRASONIC", 2);
        UDFPS_ULTRASONIC = fingerprintSensorType3;
        FingerprintSensorType fingerprintSensorType4 =
                new FingerprintSensorType("UDFPS_OPTICAL", 3);
        UDFPS_OPTICAL = fingerprintSensorType4;
        FingerprintSensorType fingerprintSensorType5 = new FingerprintSensorType("POWER_BUTTON", 4);
        POWER_BUTTON = fingerprintSensorType5;
        FingerprintSensorType fingerprintSensorType6 = new FingerprintSensorType("HOME_BUTTON", 5);
        HOME_BUTTON = fingerprintSensorType6;
        FingerprintSensorType[] fingerprintSensorTypeArr = {
            fingerprintSensorType,
            fingerprintSensorType2,
            fingerprintSensorType3,
            fingerprintSensorType4,
            fingerprintSensorType5,
            fingerprintSensorType6
        };
        $VALUES = fingerprintSensorTypeArr;
        EnumEntriesKt.enumEntries(fingerprintSensorTypeArr);
    }

    public static FingerprintSensorType valueOf(String str) {
        return (FingerprintSensorType) Enum.valueOf(FingerprintSensorType.class, str);
    }

    public static FingerprintSensorType[] values() {
        return (FingerprintSensorType[]) $VALUES.clone();
    }
}
