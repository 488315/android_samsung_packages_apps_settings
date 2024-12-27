package com.android.settings.notification;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationAccessSettings$$ExternalSyntheticLambda0
        implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationAccessSettings f$0;

    public /* synthetic */ NotificationAccessSettings$$ExternalSyntheticLambda0(
            NotificationAccessSettings notificationAccessSettings, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationAccessSettings;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        int i = this.$r8$classId;
        NotificationAccessSettings notificationAccessSettings = this.f$0;
        switch (i) {
            case 0:
                BaseSearchIndexProvider baseSearchIndexProvider =
                        NotificationAccessSettings.SEARCH_INDEX_DATA_PROVIDER;
                return notificationAccessSettings.getString(
                        R.string.work_profile_notification_access_blocked_summary);
            default:
                return notificationAccessSettings.mContext.getString(
                        R.string.notification_settings_work_profile);
        }
    }
}
