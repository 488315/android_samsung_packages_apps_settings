package com.samsung.android.settings.wifi.develop.nearbywifi.model;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class RadarUnit {
    public final double radian;
    public final double radius;

    public RadarUnit(int i, int i2) {
        if (i >= -40) {
            this.radius = 0.0d;
        } else if (i < -100) {
            this.radius = 5.0d;
        } else {
            this.radius = ((-i) - 40) / 12.0d;
        }
        this.radian = (i2 * 3.141592653589793d) / 180.0d;
    }
}
