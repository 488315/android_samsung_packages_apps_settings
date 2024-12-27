package com.android.settings.fuelgauge.batteryusage;

import com.google.protobuf.Internal;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public enum AppUsageEndPointType implements Internal.EnumLite {
    START(1),
    END(2);

    private final int value;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppUsageEndPointTypeVerifier implements Internal.EnumVerifier {
        public static final AppUsageEndPointTypeVerifier INSTANCE =
                new AppUsageEndPointTypeVerifier();

        @Override // com.google.protobuf.Internal.EnumVerifier
        public final boolean isInRange(int i) {
            return (i != 1 ? i != 2 ? null : AppUsageEndPointType.END : AppUsageEndPointType.START)
                    != null;
        }
    }

    AppUsageEndPointType(int i) {
        this.value = i;
    }

    @Override // com.google.protobuf.Internal.EnumLite
    public final int getNumber() {
        return this.value;
    }
}
