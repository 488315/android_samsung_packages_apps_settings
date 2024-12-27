package com.android.settings.slices;

import android.net.Uri;

import com.android.settings.core.BasePreferenceController;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SettingsSliceProvider$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SettingsSliceProvider f$0;
    public final /* synthetic */ Sliceable f$1;
    public final /* synthetic */ Uri f$2;

    public /* synthetic */ SettingsSliceProvider$$ExternalSyntheticLambda0(
            SettingsSliceProvider settingsSliceProvider, Sliceable sliceable, Uri uri, int i) {
        this.$r8$classId = i;
        this.f$0 = settingsSliceProvider;
        this.f$1 = sliceable;
        this.f$2 = uri;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SettingsSliceProvider settingsSliceProvider = this.f$0;
                BasePreferenceController basePreferenceController =
                        (BasePreferenceController) this.f$1;
                Uri uri = this.f$2;
                List list = SettingsSliceProvider.PUBLICLY_SUPPORTED_CUSTOM_SLICE_URIS;
                settingsSliceProvider.startBackgroundWorker(basePreferenceController, uri);
                break;
            default:
                SettingsSliceProvider settingsSliceProvider2 = this.f$0;
                CustomSliceable customSliceable = (CustomSliceable) this.f$1;
                Uri uri2 = this.f$2;
                List list2 = SettingsSliceProvider.PUBLICLY_SUPPORTED_CUSTOM_SLICE_URIS;
                settingsSliceProvider2.startBackgroundWorker(customSliceable, uri2);
                break;
        }
    }
}
