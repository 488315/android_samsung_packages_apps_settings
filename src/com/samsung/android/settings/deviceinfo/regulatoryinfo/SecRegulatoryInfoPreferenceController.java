package com.samsung.android.settings.deviceinfo.regulatoryinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.Utils;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecRegulatoryInfoPreferenceController extends BasePreferenceController {
    private static final String KEY = "regulatory_info";
    private static String LOG_TAG = "SecRegulatoryInfoPreferenceController";

    public SecRegulatoryInfoPreferenceController(Context context, String str) {
        super(context, str);
    }

    public boolean checkRegulatoryInfoForCHN(Context context) {
        File file = new File("/efs/FactoryApp/model_device_code");
        if (!Utils.isWifiOnly(context) && file.exists()) {
            return true;
        }
        Log.d(LOG_TAG, "wifi only or efs node not exists");
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return supportRegulatoryInformation(this.mContext) ? 0 : 2;
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

    public boolean isCostaRicaSIM(String str) {
        return str != null && str.length() > 2 && Integer.parseInt(str.substring(0, 3)) == 712;
    }

    public boolean isCostaRicaSimInserted() {
        return isCostaRicaSIM(TelephonyManager.getDefault().getSimOperatorNumericForPhone(0))
                || isCostaRicaSIM(
                        TelephonyManager.getDefault().getPhoneCount() == 2
                                ? TelephonyManager.getDefault().getSimOperatorNumericForPhone(1)
                                : null);
    }

    public boolean isGuatemalaSales() {
        String salesCode = com.android.settings.Utils.getSalesCode();
        return "GTO".equals(salesCode) || "GLO".equals(salesCode);
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

    public boolean supportRegulatoryInformation(Context context) {
        boolean z =
                SemCscFeature.getInstance().getBoolean("CscFeature_Setting_SupportRegulatoryInfo");
        return Rune.isChinaModel()
                ? checkRegulatoryInfoForCHN(context)
                : isGuatemalaSales() ? isCostaRicaSimInserted() && z : z;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
