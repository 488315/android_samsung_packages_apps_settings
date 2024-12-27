package com.github.mikephil.charting.data;

import com.sec.ims.settings.ImsProfile;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class DataSet$Rounding {
    public static final /* synthetic */ DataSet$Rounding[] $VALUES;
    public static final DataSet$Rounding CLOSEST;
    public static final DataSet$Rounding DOWN;
    public static final DataSet$Rounding UP;

    static {
        DataSet$Rounding dataSet$Rounding = new DataSet$Rounding(ImsProfile.RCS_PROFILE_UP, 0);
        UP = dataSet$Rounding;
        DataSet$Rounding dataSet$Rounding2 = new DataSet$Rounding("DOWN", 1);
        DOWN = dataSet$Rounding2;
        DataSet$Rounding dataSet$Rounding3 = new DataSet$Rounding("CLOSEST", 2);
        CLOSEST = dataSet$Rounding3;
        $VALUES = new DataSet$Rounding[] {dataSet$Rounding, dataSet$Rounding2, dataSet$Rounding3};
    }

    public static DataSet$Rounding valueOf(String str) {
        return (DataSet$Rounding) Enum.valueOf(DataSet$Rounding.class, str);
    }

    public static DataSet$Rounding[] values() {
        return (DataSet$Rounding[]) $VALUES.clone();
    }
}
