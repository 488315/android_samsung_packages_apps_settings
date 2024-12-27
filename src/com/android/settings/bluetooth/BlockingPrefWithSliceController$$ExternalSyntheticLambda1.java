package com.android.settings.bluetooth;

import androidx.preference.Preference;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class BlockingPrefWithSliceController$$ExternalSyntheticLambda1
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BlockingPrefWithSliceController f$0;

    public /* synthetic */ BlockingPrefWithSliceController$$ExternalSyntheticLambda1(
            BlockingPrefWithSliceController blockingPrefWithSliceController, int i) {
        this.$r8$classId = i;
        this.f$0 = blockingPrefWithSliceController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        BlockingPrefWithSliceController blockingPrefWithSliceController = this.f$0;
        Preference preference = (Preference) obj;
        switch (i) {
            case 0:
                blockingPrefWithSliceController.lambda$updatePreferenceListAndPreferenceCategory$3(
                        preference);
                break;
            default:
                blockingPrefWithSliceController.lambda$removePreferenceListFromPreferenceCategory$1(
                        preference);
                break;
        }
    }
}
