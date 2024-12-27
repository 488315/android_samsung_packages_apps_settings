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
            "Lcom/android/systemui/biometrics/shared/model/SensorStrength;",
            ApnSettings.MVNO_NONE,
            "frameworks__base__packages__SystemUI__shared__biometrics__android_common__BiometricsSharedLib"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class SensorStrength {
    public static final /* synthetic */ SensorStrength[] $VALUES;
    public static final SensorStrength CONVENIENCE;
    public static final SensorStrength STRONG;
    public static final SensorStrength WEAK;

    static {
        SensorStrength sensorStrength = new SensorStrength("CONVENIENCE", 0);
        CONVENIENCE = sensorStrength;
        SensorStrength sensorStrength2 = new SensorStrength("WEAK", 1);
        WEAK = sensorStrength2;
        SensorStrength sensorStrength3 = new SensorStrength("STRONG", 2);
        STRONG = sensorStrength3;
        SensorStrength[] sensorStrengthArr = {sensorStrength, sensorStrength2, sensorStrength3};
        $VALUES = sensorStrengthArr;
        EnumEntriesKt.enumEntries(sensorStrengthArr);
    }

    public static SensorStrength valueOf(String str) {
        return (SensorStrength) Enum.valueOf(SensorStrength.class, str);
    }

    public static SensorStrength[] values() {
        return (SensorStrength[]) $VALUES.clone();
    }
}
