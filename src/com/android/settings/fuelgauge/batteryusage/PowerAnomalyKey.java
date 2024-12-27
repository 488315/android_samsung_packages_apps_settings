package com.android.settings.fuelgauge.batteryusage;

import com.google.protobuf.Internal;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public enum PowerAnomalyKey implements Internal.EnumLite {
    KEY_BRIGHTNESS(0),
    KEY_SCREEN_TIMEOUT(1),
    KEY_APP_TOTAL_ALWAYS_HIGH(2),
    KEY_APP_TOTAL_HIGHER_THAN_USUAL(3),
    KEY_APP_BACKGROUND_ALWAYS_HIGH(4),
    KEY_APP_BACKGROUND_HIGHER_THAN_USUAL(5),
    KEY_APP_FOREGROUND_ALWAYS_HIGH(6),
    KEY_APP_FOREGROUND_HIGHER_THAN_USUAL(7);

    private final int value;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PowerAnomalyKeyVerifier implements Internal.EnumVerifier {
        public static final PowerAnomalyKeyVerifier INSTANCE = new PowerAnomalyKeyVerifier();

        @Override // com.google.protobuf.Internal.EnumVerifier
        public final boolean isInRange(int i) {
            return PowerAnomalyKey.forNumber(i) != null;
        }
    }

    PowerAnomalyKey(int i) {
        this.value = i;
    }

    public static PowerAnomalyKey forNumber(int i) {
        switch (i) {
            case 0:
                return KEY_BRIGHTNESS;
            case 1:
                return KEY_SCREEN_TIMEOUT;
            case 2:
                return KEY_APP_TOTAL_ALWAYS_HIGH;
            case 3:
                return KEY_APP_TOTAL_HIGHER_THAN_USUAL;
            case 4:
                return KEY_APP_BACKGROUND_ALWAYS_HIGH;
            case 5:
                return KEY_APP_BACKGROUND_HIGHER_THAN_USUAL;
            case 6:
                return KEY_APP_FOREGROUND_ALWAYS_HIGH;
            case 7:
                return KEY_APP_FOREGROUND_HIGHER_THAN_USUAL;
            default:
                return null;
        }
    }

    @Override // com.google.protobuf.Internal.EnumLite
    public final int getNumber() {
        return this.value;
    }
}
