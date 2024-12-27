package com.android.settings.slices;

import android.net.Uri;
import android.util.Log;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SettingsSliceProvider$$ExternalSyntheticLambda5
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SettingsSliceProvider f$0;
    public final /* synthetic */ Uri f$1;

    public /* synthetic */ SettingsSliceProvider$$ExternalSyntheticLambda5(
            SettingsSliceProvider settingsSliceProvider, Uri uri, int i) {
        this.$r8$classId = i;
        this.f$0 = settingsSliceProvider;
        this.f$1 = uri;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SettingsSliceProvider settingsSliceProvider = this.f$0;
                Uri uri = this.f$1;
                SliceBackgroundWorker sliceBackgroundWorker =
                        settingsSliceProvider.mPinnedWorkers.get(uri);
                if (sliceBackgroundWorker != null) {
                    Log.d("SettingsSliceProvider", "Stopping background worker for: " + uri);
                    sliceBackgroundWorker.onSliceUnpinned();
                    SliceBackgroundWorker.NotifySliceChangeHandler m1006$$Nest$smgetInstance =
                            SliceBackgroundWorker.NotifySliceChangeHandler
                                    .m1006$$Nest$smgetInstance();
                    m1006$$Nest$smgetInstance.removeMessages(1000, sliceBackgroundWorker);
                    m1006$$Nest$smgetInstance.mLastUpdateTimeLookup.remove(
                            sliceBackgroundWorker.mUri);
                    settingsSliceProvider.mPinnedWorkers.remove(uri);
                    break;
                }
                break;
            default:
                SettingsSliceProvider settingsSliceProvider2 = this.f$0;
                Uri uri2 = this.f$1;
                List list = SettingsSliceProvider.PUBLICLY_SUPPORTED_CUSTOM_SLICE_URIS;
                settingsSliceProvider2.loadSlice(uri2);
                break;
        }
    }
}
