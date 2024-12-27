package com.samsung.android.settings.wifi.develop.savednetwork;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class AllWifiEntryCategory extends SavedWifiEntryCategoryOption {
    @Override // com.samsung.android.settings.wifi.develop.savednetwork.SavedWifiEntryCategoryOption
    public final List getCategoryTitles() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(ApnSettings.MVNO_NONE);
        return arrayList;
    }
}
