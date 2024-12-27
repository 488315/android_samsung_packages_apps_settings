package com.samsung.android.settings.usefulfeature.intelligenceservice;

import android.graphics.drawable.Drawable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class IntelligenceServiceAppInfo implements Comparable {
    public final Drawable icon;
    public final String key;
    public final String[] offlineAIFeatures;
    public final String[] onlineAIFeatures;
    public final int order;
    public final String summary;
    public final String supportedMode;
    public final String title;

    public IntelligenceServiceAppInfo(
            String str,
            String str2,
            String str3,
            Drawable drawable,
            int i,
            String str4,
            String[] strArr,
            String[] strArr2) {
        this.key = str;
        this.title = str2;
        this.summary = str3;
        this.icon = drawable;
        this.order = i;
        this.supportedMode = str4;
        this.onlineAIFeatures = strArr;
        this.offlineAIFeatures = strArr2;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        IntelligenceServiceAppInfo intelligenceServiceAppInfo = (IntelligenceServiceAppInfo) obj;
        int i = this.order;
        int i2 = intelligenceServiceAppInfo.order;
        return i == i2 ? this.title.compareTo(intelligenceServiceAppInfo.title) : i - i2;
    }
}
