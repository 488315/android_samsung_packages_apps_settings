package com.samsung.android.settings.deviceinfo.softwareinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SemSystemProperties;

import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class OmcVersionPreferenceController extends BasePreferenceController {
    private final String PACKAGE_OMC_AGENT;

    public OmcVersionPreferenceController(Context context, String str) {
        super(context, str);
        this.PACKAGE_OMC_AGENT = "com.samsung.android.app.omcagent";
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        float f;
        if (Utils.hasPackage(this.mContext, "com.samsung.android.app.omcagent")) {
            return 0;
        }
        String str = SemSystemProperties.get("persist.sys.omc_support", "false");
        try {
            f =
                    Float.parseFloat(
                            SemFloatingFeature.getInstance()
                                    .getString(
                                            "SEC_FLOATING_FEATURE_COMMON_CONFIG_OMC_VERSION",
                                            DATA.DM_FIELD_INDEX.PCSCF_DOMAIN));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            f = 0.0f;
        }
        return (!"true".equals(str) || f < 5.0f) ? 3 : 0;
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

    /* JADX WARN: Code restructure failed: missing block: B:12:0x004c, code lost:

       r5 = r5.nextText();
    */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00be  */
    @Override // com.android.settingslib.core.AbstractPreferenceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.CharSequence getSummary() {
        /*
            Method dump skipped, instructions count: 310
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.deviceinfo.softwareinfo.OmcVersionPreferenceController.getSummary():java.lang.CharSequence");
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
