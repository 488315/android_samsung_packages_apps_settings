package com.samsung.android.settings.datausage;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.datausage.DataSaverBackend;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecDataSaverPreferenceController extends TogglePreferenceController {
    private static final String TAG = "SecDataSaverPreferenceController";
    private DataSaverBackend mDataSaverBackend;

    public SecDataSaverPreferenceController(Context context, String str) {
        super(context, str);
        this.mDataSaverBackend = new DataSaverBackend(context);
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x001f, code lost:

       if (android.provider.Settings.Secure.getInt(r5.mContext.getContentResolver(), "background_data_by_pco", 1) != 1) goto L8;
    */
    @Override // com.android.settings.core.BasePreferenceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getAvailabilityStatus() {
        /*
            r5 = this;
            android.content.Context r0 = r5.mContext
            java.lang.String r1 = "user"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.os.UserManager r0 = (android.os.UserManager) r0
            boolean r1 = com.samsung.android.settings.connection.ConnectionsUtils.isSupportPco()
            r2 = 0
            if (r1 == 0) goto L22
            android.content.Context r1 = r5.mContext
            android.content.ContentResolver r1 = r1.getContentResolver()
            java.lang.String r3 = "background_data_by_pco"
            r4 = 1
            int r1 = android.provider.Settings.Secure.getInt(r1, r3, r4)
            if (r1 == r4) goto L22
            goto L23
        L22:
            r4 = r2
        L23:
            android.content.Context r1 = r5.mContext
            boolean r1 = com.android.settingslib.Utils.isWifiOnly(r1)
            if (r1 == 0) goto L2d
            r5 = 3
            return r5
        L2d:
            android.content.Context r1 = r5.mContext
            boolean r1 = com.android.settings.datausage.DataUsageUtils.hasMobileData(r1)
            if (r1 == 0) goto L51
            android.content.Context r1 = r5.mContext
            boolean r1 = com.android.settings.datausage.DataUsageUtils.hasActiveSim(r1)
            if (r1 == 0) goto L51
            boolean r0 = r0.isAdminUser()
            if (r0 != 0) goto L4d
            int r0 = android.os.UserHandle.myUserId()
            boolean r0 = com.samsung.android.knox.SemPersonaManager.isSecureFolderId(r0)
            if (r0 == 0) goto L51
        L4d:
            if (r4 == 0) goto L50
            goto L51
        L50:
            return r2
        L51:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "hasMobileData: "
            r0.<init>(r1)
            android.content.Context r1 = r5.mContext
            boolean r1 = com.android.settings.datausage.DataUsageUtils.hasMobileData(r1)
            r0.append(r1)
            java.lang.String r1 = " hasSim: "
            r0.append(r1)
            android.content.Context r5 = r5.mContext
            boolean r5 = com.android.settings.datausage.DataUsageUtils.hasActiveSim(r5)
            r0.append(r5)
            java.lang.String r5 = " restrictBackgroundByPco: "
            r0.append(r5)
            r0.append(r4)
            java.lang.String r5 = r0.toString()
            java.lang.String r0 = "SecDataSaverPreferenceController"
            android.util.Log.i(r0, r5)
            r5 = 4
            return r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.datausage.SecDataSaverPreferenceController.getAvailabilityStatus():int");
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return this.mDataSaverBackend.mPolicyManager.getRestrictBackground();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m("Data Saver setChecked : ", TAG, z);
        DataSaverBackend dataSaverBackend = this.mDataSaverBackend;
        dataSaverBackend.mPolicyManager.setRestrictBackground(z);
        dataSaverBackend.mMetricsFeatureProvider.action(dataSaverBackend.mContext, 394, z ? 1 : 0);
        LoggingHelper.insertEventLogging(getMetricsCategory(), 7101, !z ? 1L : 0L);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
