package com.android.settings.fuelgauge.batteryusage;

import com.google.protobuf.Internal;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public enum PowerAnomalyType implements Internal.EnumLite {
    TYPE_SETTINGS_BANNER(0),
    TYPE_APPS_ITEM(1);

    private final int value;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PowerAnomalyTypeVerifier implements Internal.EnumVerifier {
        public static final PowerAnomalyTypeVerifier INSTANCE = new PowerAnomalyTypeVerifier();

        @Override // com.google.protobuf.Internal.EnumVerifier
        public final boolean isInRange(int i) {
            return (i != 0
                            ? i != 1 ? null : PowerAnomalyType.TYPE_APPS_ITEM
                            : PowerAnomalyType.TYPE_SETTINGS_BANNER)
                    != null;
        }
    }

    PowerAnomalyType(int i) {
        this.value = i;
    }

    @Override // com.google.protobuf.Internal.EnumLite
    public final int getNumber() {
        return this.value;
    }
}
