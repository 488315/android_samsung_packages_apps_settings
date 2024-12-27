package com.samsung.android.settings.deviceinfo.statusinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.picker.widget.SeslNumberPickerSpinnerDelegate$$ExternalSyntheticOutline0;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.Utils;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CTEICodePreferenceController extends BasePreferenceController {
    private static final String LOG_TAG = "CTEICodePreferenceController";

    public CTEICodePreferenceController(Context context, String str) {
        super(context, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0069 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String getCTEICode() {
        /*
            r7 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            r1 = 0
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2c
            java.io.FileReader r3 = new java.io.FileReader     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2c
            java.lang.String r4 = "/efs/FactoryApp/cteicode"
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2c
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2c
        L12:
            java.lang.String r1 = r2.readLine()     // Catch: java.lang.Throwable -> L1c java.lang.Exception -> L1f
            if (r1 == 0) goto L21
            r0.append(r1)     // Catch: java.lang.Throwable -> L1c java.lang.Exception -> L1f
            goto L12
        L1c:
            r7 = move-exception
            r1 = r2
            goto L67
        L1f:
            r1 = move-exception
            goto L30
        L21:
            r2.close()     // Catch: java.io.IOException -> L25
            goto L4f
        L25:
            r1 = move-exception
            r1.printStackTrace()
            goto L4f
        L2a:
            r7 = move-exception
            goto L67
        L2c:
            r2 = move-exception
            r6 = r2
            r2 = r1
            r1 = r6
        L30:
            java.lang.String r3 = "CTEICodePreferenceController"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L1c
            r4.<init>()     // Catch: java.lang.Throwable -> L1c
            java.lang.String r5 = "Read CTEI error:"
            r4.append(r5)     // Catch: java.lang.Throwable -> L1c
            java.lang.String r1 = r1.getMessage()     // Catch: java.lang.Throwable -> L1c
            r4.append(r1)     // Catch: java.lang.Throwable -> L1c
            java.lang.String r1 = r4.toString()     // Catch: java.lang.Throwable -> L1c
            android.util.Log.i(r3, r1)     // Catch: java.lang.Throwable -> L1c
            if (r2 == 0) goto L4f
            r2.close()     // Catch: java.io.IOException -> L25
        L4f:
            java.lang.String r0 = r0.toString()
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto L66
            android.content.Context r7 = r7.mContext
            android.content.res.Resources r7 = r7.getResources()
            r0 = 2132020358(0x7f140c86, float:1.9679077E38)
            java.lang.String r0 = r7.getString(r0)
        L66:
            return r0
        L67:
            if (r1 == 0) goto L71
            r1.close()     // Catch: java.io.IOException -> L6d
            goto L71
        L6d:
            r0 = move-exception
            r0.printStackTrace()
        L71:
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.deviceinfo.statusinfo.CTEICodePreferenceController.getCTEICode():java.lang.String");
    }

    private boolean isSupportCHNDMCTC() {
        return SemCscFeature.getInstance()
                        .getInteger("CscFeature_DeviceManagement_ConfigOperator", 0)
                >= 16;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (Rune.isChinaModel()
                        && Utils.isWifiOnly(this.mContext)
                        && isSupportCHNDMCTC()
                        && SeslNumberPickerSpinnerDelegate$$ExternalSyntheticOutline0.m(
                                "/efs/FactoryApp/cteicode"))
                ? 0
                : 3;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return getCTEICode();
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
