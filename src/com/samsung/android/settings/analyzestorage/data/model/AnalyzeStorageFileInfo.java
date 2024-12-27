package com.samsung.android.settings.analyzestorage.data.model;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AnalyzeStorageFileInfo extends CommonFileInfo {
    private int mGroupType;
    private boolean mIsGroupHeader;

    public AnalyzeStorageFileInfo() {
        this.mIsGroupHeader = false;
        this.mGroupType = -1;
    }

    public final void setGroupHeader() {
        this.mIsGroupHeader = true;
    }

    public final void setGroupType(int i) {
        this.mGroupType = i;
    }

    public AnalyzeStorageFileInfo(String str, int i) {
        super(str);
        this.mIsGroupHeader = false;
        this.mGroupType = -1;
        setDomainType(i);
    }
}
