package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.util.Pair;

import com.android.settingslib.media.LocalMediaManager;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaControlHelper$$ExternalSyntheticLambda0
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Pair pair = (Pair) obj;
        ((LocalMediaManager) pair.first).mInfoMediaManager.stopScanOnRouter();
        ((LocalMediaManager) pair.first)
                .unregisterCallback((LocalMediaManager.DeviceCallback) pair.second);
    }
}
