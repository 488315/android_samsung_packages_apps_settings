package com.android.settings.slices;

import android.util.ArrayMap;
import android.util.Log;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SettingsSliceProvider$$ExternalSyntheticLambda2
        implements Runnable {
    @Override // java.lang.Runnable
    public final void run() {
        List list = SettingsSliceProvider.PUBLICLY_SUPPORTED_CUSTOM_SLICE_URIS;
        Iterator it = ((ArrayMap) SliceBackgroundWorker.LIVE_WORKERS).values().iterator();
        while (it.hasNext()) {
            try {
                ((SliceBackgroundWorker) it.next()).close();
            } catch (IOException e) {
                Log.w("SliceBackgroundWorker", "Shutting down worker failed", e);
            }
        }
        ((ArrayMap) SliceBackgroundWorker.LIVE_WORKERS).clear();
    }
}
