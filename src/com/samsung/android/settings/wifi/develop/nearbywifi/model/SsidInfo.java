package com.samsung.android.settings.wifi.develop.nearbywifi.model;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SsidInfo {
    public final ArrayList bssids = new ArrayList();
    public boolean existBand24;
    public boolean existBand5;
    public boolean existBand6;
    public final String ssid;

    public SsidInfo(String str) {
        this.ssid = str;
    }
}
