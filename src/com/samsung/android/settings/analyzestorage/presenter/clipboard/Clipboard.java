package com.samsung.android.settings.analyzestorage.presenter.clipboard;

import android.util.SparseArray;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class Clipboard {
    public final SparseArray recommendCloudList = new SparseArray();
    public final SparseArray recommendAppList = new SparseArray();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class ClipboardHolder {
        public static final Clipboard instance = new Clipboard();
    }
}
