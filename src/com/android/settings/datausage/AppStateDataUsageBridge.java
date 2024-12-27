package com.android.settings.datausage;

import com.android.settings.applications.AppStateBaseBridge;
import com.android.settingslib.applications.ApplicationsState;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppStateDataUsageBridge extends AppStateBaseBridge {
    public final DataSaverBackend mDataSaverBackend;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DataUsageState {
        public boolean isDataSaverAllowlisted;
        public boolean isDataSaverDenylisted;

        public DataUsageState(boolean z, boolean z2) {
            this.isDataSaverAllowlisted = z;
            this.isDataSaverDenylisted = z2;
        }
    }

    public AppStateDataUsageBridge(
            ApplicationsState applicationsState,
            AppStateBaseBridge.Callback callback,
            DataSaverBackend dataSaverBackend) {
        super(applicationsState, callback);
        this.mDataSaverBackend = dataSaverBackend;
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void loadAllExtraInfo() {
        ArrayList allApps = this.mAppSession.getAllApps();
        int size = allApps.size();
        for (int i = 0; i < size; i++) {
            ApplicationsState.AppEntry appEntry = (ApplicationsState.AppEntry) allApps.get(i);
            int i2 = appEntry.info.uid;
            DataSaverBackend dataSaverBackend = this.mDataSaverBackend;
            for (int i3 : dataSaverBackend.mPolicyManager.getUidsWithPolicy(4)) {
                dataSaverBackend.mUidPolicies.put(i3, 4);
            }
            boolean z = true;
            boolean z2 = dataSaverBackend.mUidPolicies.get(i2, 0) == 4;
            int i4 = appEntry.info.uid;
            for (int i5 : dataSaverBackend.mPolicyManager.getUidsWithPolicy(1)) {
                dataSaverBackend.mUidPolicies.put(i5, 1);
            }
            if (dataSaverBackend.mUidPolicies.get(i4, 0) != 1
                    || !dataSaverBackend
                            .mDynamicDenylistManager
                            .getManualDenylistPref()
                            .contains(String.valueOf(i4))) {
                z = false;
            }
            appEntry.extraInfo = new DataUsageState(z2, z);
        }
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void updateExtraInfo(ApplicationsState.AppEntry appEntry, String str, int i) {
        DataSaverBackend dataSaverBackend = this.mDataSaverBackend;
        boolean z = false;
        for (int i2 : dataSaverBackend.mPolicyManager.getUidsWithPolicy(4)) {
            dataSaverBackend.mUidPolicies.put(i2, 4);
        }
        boolean z2 = dataSaverBackend.mUidPolicies.get(i, 0) == 4;
        for (int i3 : dataSaverBackend.mPolicyManager.getUidsWithPolicy(1)) {
            dataSaverBackend.mUidPolicies.put(i3, 1);
        }
        if (dataSaverBackend.mUidPolicies.get(i, 0) == 1
                && dataSaverBackend
                        .mDynamicDenylistManager
                        .getManualDenylistPref()
                        .contains(String.valueOf(i))) {
            z = true;
        }
        appEntry.extraInfo = new DataUsageState(z2, z);
    }
}
