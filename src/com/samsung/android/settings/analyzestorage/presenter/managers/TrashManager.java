package com.samsung.android.settings.analyzestorage.presenter.managers;

import androidx.lifecycle.MutableLiveData;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class TrashManager {
    public static long appDataCapacity;
    public static long appTrashAppCloneCapacity;
    public static long appTrashExternalSDCapacity;
    public static long appTrashInternalCapacity;
    public static final MutableLiveData isLoadCompleted = new MutableLiveData();
}
