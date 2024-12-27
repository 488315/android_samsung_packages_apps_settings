package com.samsung.android.settings.accessibility.advanced.flashnotification;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */
class ScreenFlashNotificationCustomAppsPreferenceController$$ExternalSyntheticLambda0
        implements FlashNotificationUtil.InstalledApplicationsListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScreenFlashNotificationCustomAppsPreferenceController f$0;

    public /* synthetic */
    ScreenFlashNotificationCustomAppsPreferenceController$$ExternalSyntheticLambda0(
            ScreenFlashNotificationCustomAppsPreferenceController
                    screenFlashNotificationCustomAppsPreferenceController,
            int i) {
        this.$r8$classId = i;
        this.f$0 = screenFlashNotificationCustomAppsPreferenceController;
    }

    @Override // com.samsung.android.settings.accessibility.advanced.flashnotification.FlashNotificationUtil.InstalledApplicationsListener
    public final void onGetInstalledPackageNameUnmodifiableSet(Set set) {
        int i = this.$r8$classId;
        ScreenFlashNotificationCustomAppsPreferenceController
                screenFlashNotificationCustomAppsPreferenceController = this.f$0;
        switch (i) {
            case 0:
                screenFlashNotificationCustomAppsPreferenceController.lambda$onStart$0(set);
                break;
            case 1:
                screenFlashNotificationCustomAppsPreferenceController.lambda$updateState$3(set);
                break;
            default:
                screenFlashNotificationCustomAppsPreferenceController.lambda$onDestroy$2(set);
                break;
        }
    }
}
