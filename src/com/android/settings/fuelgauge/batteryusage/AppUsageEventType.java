package com.android.settings.fuelgauge.batteryusage;

import com.google.protobuf.Internal;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public enum AppUsageEventType implements Internal.EnumLite {
    UNKNOWN(0),
    ACTIVITY_RESUMED(1),
    ACTIVITY_STOPPED(2),
    DEVICE_SHUTDOWN(3);

    private final int value;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppUsageEventTypeVerifier implements Internal.EnumVerifier {
        public static final AppUsageEventTypeVerifier INSTANCE = new AppUsageEventTypeVerifier();

        @Override // com.google.protobuf.Internal.EnumVerifier
        public final boolean isInRange(int i) {
            return AppUsageEventType.forNumber(i) != null;
        }
    }

    AppUsageEventType(int i) {
        this.value = i;
    }

    public static AppUsageEventType forNumber(int i) {
        if (i == 0) {
            return UNKNOWN;
        }
        if (i == 1) {
            return ACTIVITY_RESUMED;
        }
        if (i == 2) {
            return ACTIVITY_STOPPED;
        }
        if (i != 3) {
            return null;
        }
        return DEVICE_SHUTDOWN;
    }

    @Override // com.google.protobuf.Internal.EnumLite
    public final int getNumber() {
        return this.value;
    }
}
