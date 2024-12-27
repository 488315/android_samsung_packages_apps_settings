package com.samsung.android.settings.account;

import com.android.settings.search.BaseSearchIndexProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class CloudAccountSettings$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ CloudAccountSettings$$ExternalSyntheticLambda1(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                BaseSearchIndexProvider baseSearchIndexProvider =
                        CloudAccountSettings.SEARCH_INDEX_DATA_PROVIDER;
                ((CloudAccountSettings) obj).updatePreferenceStates();
                break;
            default:
                CloudAccountSettings cloudAccountSettings =
                        ((CloudAccountSettings.SettingsObserver) obj).this$0;
                BaseSearchIndexProvider baseSearchIndexProvider2 =
                        CloudAccountSettings.SEARCH_INDEX_DATA_PROVIDER;
                cloudAccountSettings.updatePreferenceStates();
                break;
        }
    }
}
