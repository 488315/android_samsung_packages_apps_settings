package com.android.settings.panel;

import android.net.Uri;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PanelSlicesLoaderCountdownLatch {
    public final CountDownLatch mCountDownLatch;
    public boolean slicesReadyToLoad = false;
    public final Set mLoadedSlices = new HashSet();

    public PanelSlicesLoaderCountdownLatch(int i) {
        this.mCountDownLatch = new CountDownLatch(i);
    }

    public final void markSliceLoaded(Uri uri) {
        if (((HashSet) this.mLoadedSlices).contains(uri)) {
            return;
        }
        ((HashSet) this.mLoadedSlices).add(uri);
        this.mCountDownLatch.countDown();
    }
}
