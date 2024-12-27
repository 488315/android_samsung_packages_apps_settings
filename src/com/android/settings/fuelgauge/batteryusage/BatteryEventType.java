package com.android.settings.fuelgauge.batteryusage;

import com.google.protobuf.Internal;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public enum BatteryEventType implements Internal.EnumLite {
    UNKNOWN_EVENT(0),
    POWER_CONNECTED(1),
    POWER_DISCONNECTED(2),
    FULL_CHARGED(3),
    EVEN_HOUR(4);

    private final int value;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BatteryEventTypeVerifier implements Internal.EnumVerifier {
        public static final BatteryEventTypeVerifier INSTANCE = new BatteryEventTypeVerifier();

        @Override // com.google.protobuf.Internal.EnumVerifier
        public final boolean isInRange(int i) {
            return BatteryEventType.forNumber(i) != null;
        }
    }

    BatteryEventType(int i) {
        this.value = i;
    }

    public static BatteryEventType forNumber(int i) {
        if (i == 0) {
            return UNKNOWN_EVENT;
        }
        if (i == 1) {
            return POWER_CONNECTED;
        }
        if (i == 2) {
            return POWER_DISCONNECTED;
        }
        if (i == 3) {
            return FULL_CHARGED;
        }
        if (i != 4) {
            return null;
        }
        return EVEN_HOUR;
    }

    @Override // com.google.protobuf.Internal.EnumLite
    public final int getNumber() {
        return this.value;
    }
}
