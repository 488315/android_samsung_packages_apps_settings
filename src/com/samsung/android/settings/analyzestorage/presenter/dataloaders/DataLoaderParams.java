package com.samsung.android.settings.analyzestorage.presenter.dataloaders;

import android.os.Bundle;

import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;

import kotlin.collections.EmptyList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DataLoaderParams {
    public final EmptyList dataInfoList;
    public boolean forceUpdate;
    public int groupIndex;
    public int loadExecutionId;
    public PageInfo pageInfo;
    public int sessionId;

    public DataLoaderParams() {
        EmptyList emptyList = EmptyList.INSTANCE;
        this.sessionId = -1;
        this.loadExecutionId = -1;
        new Bundle();
    }
}
