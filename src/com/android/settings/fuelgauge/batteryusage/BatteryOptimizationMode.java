package com.android.settings.fuelgauge.batteryusage;

import com.google.protobuf.Internal;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public enum BatteryOptimizationMode implements Internal.EnumLite {
    MODE_UNKNOWN(0),
    MODE_RESTRICTED(1),
    MODE_UNRESTRICTED(2),
    MODE_OPTIMIZED(3);

    private final int value;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BatteryOptimizationModeVerifier implements Internal.EnumVerifier {
        public static final BatteryOptimizationModeVerifier INSTANCE =
                new BatteryOptimizationModeVerifier();

        @Override // com.google.protobuf.Internal.EnumVerifier
        public final boolean isInRange(int i) {
            return (i != 0
                            ? i != 1
                                    ? i != 2
                                            ? i != 3 ? null : BatteryOptimizationMode.MODE_OPTIMIZED
                                            : BatteryOptimizationMode.MODE_UNRESTRICTED
                                    : BatteryOptimizationMode.MODE_RESTRICTED
                            : BatteryOptimizationMode.MODE_UNKNOWN)
                    != null;
        }
    }

    BatteryOptimizationMode(int i) {
        this.value = i;
    }

    @Override // com.google.protobuf.Internal.EnumLite
    public final int getNumber() {
        return this.value;
    }
}
