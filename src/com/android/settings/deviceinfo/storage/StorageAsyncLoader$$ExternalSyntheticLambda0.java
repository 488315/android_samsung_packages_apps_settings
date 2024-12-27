package com.android.settings.deviceinfo.storage;

import android.content.pm.UserInfo;

import java.util.Comparator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class StorageAsyncLoader$$ExternalSyntheticLambda0
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return Integer.compare(((UserInfo) obj).id, ((UserInfo) obj2).id);
    }
}
