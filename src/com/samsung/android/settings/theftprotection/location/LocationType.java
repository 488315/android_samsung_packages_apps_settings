package com.samsung.android.settings.theftprotection.location;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public enum LocationType {
    UNKNOWN(R.string.mandatory_biometric_trusted_places_unknown, R.drawable.ic_location, "UNKNOWN"),
    HOME(R.string.mandatory_biometric_trusted_places_home, R.drawable.ic_location_home, "HOME"),
    WORK(R.string.mandatory_biometric_trusted_places_work, R.drawable.ic_location_work, "WORK"),
    OTHER(R.string.mandatory_biometric_trusted_places_other, R.drawable.ic_location, "OTHER");

    private final int iconResID;
    private final int nameResID;
    private final int value;

    LocationType(int i, int i2, String str) {
        this.value = r2;
        this.nameResID = i;
        this.iconResID = i2;
    }

    public static LocationType fromInt(int i) {
        for (LocationType locationType : values()) {
            if (locationType.value == i) {
                return locationType;
            }
        }
        return UNKNOWN;
    }

    public final int iconResID() {
        return this.iconResID;
    }

    public final int nameResID() {
        return this.nameResID;
    }

    public final int value() {
        return this.value;
    }
}
