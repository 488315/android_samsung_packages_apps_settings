package com.android.settings.applications.specialaccess.notificationaccess;

import com.android.settings.notification.NotificationBackend;
import com.android.settingslib.core.AbstractPreferenceController;

import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationAccessDetails$$ExternalSyntheticLambda1
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationAccessDetails f$0;
    public final /* synthetic */ NotificationBackend f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ NotificationAccessDetails$$ExternalSyntheticLambda1(
            NotificationAccessDetails notificationAccessDetails,
            NotificationBackend notificationBackend,
            int i,
            int i2) {
        this.$r8$classId = i2;
        this.f$0 = notificationAccessDetails;
        this.f$1 = notificationBackend;
        this.f$2 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                NotificationAccessDetails notificationAccessDetails = this.f$0;
                NotificationBackend notificationBackend = this.f$1;
                int i = this.f$2;
                notificationAccessDetails.getClass();
                ((List) obj)
                        .forEach(
                                new NotificationAccessDetails$$ExternalSyntheticLambda1(
                                        notificationAccessDetails, notificationBackend, i, 1));
                break;
            default:
                NotificationAccessDetails notificationAccessDetails2 = this.f$0;
                NotificationBackend notificationBackend2 = this.f$1;
                int i2 = this.f$2;
                AbstractPreferenceController abstractPreferenceController =
                        (AbstractPreferenceController) obj;
                notificationAccessDetails2.getClass();
                if (abstractPreferenceController instanceof TypeFilterPreferenceController) {
                    ((TypeFilterPreferenceController) abstractPreferenceController)
                            .setNm(notificationBackend2)
                            .setCn(notificationAccessDetails2.mComponentName)
                            .setServiceInfo(notificationAccessDetails2.mServiceInfo)
                            .setUserId(notificationAccessDetails2.mUserId)
                            .setTargetSdk(i2);
                    break;
                }
                break;
        }
    }
}
