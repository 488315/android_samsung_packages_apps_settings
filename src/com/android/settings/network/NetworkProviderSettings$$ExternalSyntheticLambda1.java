package com.android.settings.network;

import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NetworkProviderSettings$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NetworkProviderSettings f$0;

    public /* synthetic */ NetworkProviderSettings$$ExternalSyntheticLambda1(
            NetworkProviderSettings networkProviderSettings, int i) {
        this.$r8$classId = i;
        this.f$0 = networkProviderSettings;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        NetworkProviderSettings networkProviderSettings = this.f$0;
        switch (i) {
            case 0:
                NetworkProviderSettings.SearchIndexProvider searchIndexProvider =
                        NetworkProviderSettings.SEARCH_INDEX_DATA_PROVIDER;
                networkProviderSettings.onWifiStateChanged();
                break;
            case 1:
                NetworkProviderSettings.SearchIndexProvider searchIndexProvider2 =
                        NetworkProviderSettings.SEARCH_INDEX_DATA_PROVIDER;
                networkProviderSettings.updateWifiEntryPreferences();
                View view = networkProviderSettings.getView();
                if (view != null) {
                    view.postDelayed(networkProviderSettings.mRemoveLoadingRunnable, 10L);
                    break;
                }
                break;
            case 2:
                NetworkProviderSettings.SearchIndexProvider searchIndexProvider3 =
                        NetworkProviderSettings.SEARCH_INDEX_DATA_PROVIDER;
                networkProviderSettings.showPinnedHeader(false);
                break;
            case 3:
                if (networkProviderSettings.mIsViewLoading) {
                    networkProviderSettings.setLoading(false, false);
                    networkProviderSettings.mIsViewLoading = false;
                    break;
                }
                break;
            default:
                NetworkProviderSettings.$r8$lambda$7OlqpiUmxFkDn9fJ9dGTdauOVvw(
                        networkProviderSettings);
                break;
        }
    }
}
