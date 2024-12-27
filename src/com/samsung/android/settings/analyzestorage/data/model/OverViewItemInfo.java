package com.samsung.android.settings.analyzestorage.data.model;

import com.samsung.android.settings.analyzestorage.data.constant.AnalyzeStorageConstants$UiItemType;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class OverViewItemInfo {
    public final Long mSize;
    public final AnalyzeStorageConstants$UiItemType mType;

    public OverViewItemInfo(
            AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType, Long l) {
        this.mType = analyzeStorageConstants$UiItemType;
        this.mSize = l;
    }
}
