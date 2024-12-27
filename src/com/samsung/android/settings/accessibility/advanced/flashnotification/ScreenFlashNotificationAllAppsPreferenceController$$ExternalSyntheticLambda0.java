package com.samsung.android.settings.accessibility.advanced.flashnotification;

import android.net.Uri;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */
class ScreenFlashNotificationAllAppsPreferenceController$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScreenFlashNotificationAllAppsPreferenceController f$0;

    public /* synthetic */
    ScreenFlashNotificationAllAppsPreferenceController$$ExternalSyntheticLambda0(
            ScreenFlashNotificationAllAppsPreferenceController
                    screenFlashNotificationAllAppsPreferenceController,
            int i) {
        this.$r8$classId = i;
        this.f$0 = screenFlashNotificationAllAppsPreferenceController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        ScreenFlashNotificationAllAppsPreferenceController
                screenFlashNotificationAllAppsPreferenceController = this.f$0;
        switch (i) {
            case 0:
                screenFlashNotificationAllAppsPreferenceController.lambda$onClick$0((Integer) obj);
                break;
            default:
                screenFlashNotificationAllAppsPreferenceController.lambda$onStart$1((Uri) obj);
                break;
        }
    }
}
