package com.samsung.android.settings.wifi.develop.savednetwork;

import android.content.Context;

import com.android.settings.R;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WeakWifiEntryCategory extends SavedWifiEntryCategoryOption {
    public final Context mContext;

    public WeakWifiEntryCategory(Context context) {
        this.mContext = context;
    }

    @Override // com.samsung.android.settings.wifi.develop.savednetwork.SavedWifiEntryCategoryOption
    public final List getCategoryTitles() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mContext.getString(R.string.sec_wifi_network_information_category_open));
        arrayList.add(this.mContext.getString(R.string.sec_wifi_network_information_category_wep));
        return arrayList;
    }
}
