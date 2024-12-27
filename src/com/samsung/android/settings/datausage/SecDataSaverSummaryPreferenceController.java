package com.samsung.android.settings.datausage;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecDataSaverSummaryPreferenceController extends BasePreferenceController {
    public SecDataSaverSummaryPreferenceController(Context context, String str) {
        super(context, str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x002b, code lost:

       if (android.provider.Settings.Secure.getInt(r6.mContext.getContentResolver(), "background_data_by_pco", 1) != 1) goto L8;
    */
    @Override // com.android.settings.core.BasePreferenceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getAvailabilityStatus() {
        /*
            r6 = this;
            android.content.Context r0 = r6.mContext
            boolean r0 = com.android.settings.datausage.DataUsageUtils.hasMobileData(r0)
            android.content.Context r1 = r6.mContext
            boolean r1 = com.android.settings.datausage.DataUsageUtils.hasActiveSim(r1)
            android.content.Context r2 = r6.mContext
            java.lang.String r3 = "user"
            java.lang.Object r2 = r2.getSystemService(r3)
            android.os.UserManager r2 = (android.os.UserManager) r2
            boolean r3 = com.samsung.android.settings.connection.ConnectionsUtils.isSupportPco()
            r4 = 0
            if (r3 == 0) goto L2e
            android.content.Context r6 = r6.mContext
            android.content.ContentResolver r6 = r6.getContentResolver()
            java.lang.String r3 = "background_data_by_pco"
            r5 = 1
            int r6 = android.provider.Settings.Secure.getInt(r6, r3, r5)
            if (r6 == r5) goto L2e
            goto L2f
        L2e:
            r5 = r4
        L2f:
            if (r0 == 0) goto L47
            if (r1 == 0) goto L47
            boolean r6 = r2.isAdminUser()
            if (r6 != 0) goto L43
            int r6 = android.os.UserHandle.myUserId()
            boolean r6 = com.samsung.android.knox.SemPersonaManager.isSecureFolderId(r6)
            if (r6 == 0) goto L47
        L43:
            if (r5 == 0) goto L46
            goto L47
        L46:
            return r4
        L47:
            r6 = 2
            return r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.datausage.SecDataSaverSummaryPreferenceController.getAvailabilityStatus():int");
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
