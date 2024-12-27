package com.samsung.android.settings.deviceinfo.softwareinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SELinux;
import android.os.SystemProperties;
import android.util.Log;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SELinuxStatusPreferenceController extends BasePreferenceController {
    private static final String LOG_TAG = "SELinuxStatusPreferenceController";
    private static final String PROPERTY_SELINUX_POLICY_VERSION = "selinux.policy_version";
    private static final String PROPERTY_SELINUX_STATUS = "ro.build.selinux";

    public SELinuxStatusPreferenceController(Context context, String str) {
        super(context, str);
    }

    private String getSELinuxStatus() {
        if (!SELinux.isSELinuxEnabled()) {
            return this.mContext.getResources().getString(R.string.selinux_status_disabled);
        }
        if (!SELinux.isSELinuxEnforced()) {
            return this.mContext.getResources().getString(R.string.selinux_status_permissive);
        }
        String string = this.mContext.getResources().getString(R.string.selinux_status_enforcing);
        try {
            if (!ApnSettings.MVNO_NONE.equals(SELinux.getSEPolicyVersion())) {
                string = string + "\n" + SELinux.getSEPolicyVersion();
            }
        } catch (Exception e) {
            Log.i(LOG_TAG, "KnoxVersion Exception : " + e.getMessage());
            e.printStackTrace();
        }
        try {
            if (!ApnSettings.MVNO_NONE.equals(SELinux.getSEPolicyBuildDate())) {
                string = string + "\n" + SELinux.getSEPolicyBuildDate();
            }
        } catch (Exception e2) {
            Log.i(LOG_TAG, "KnoxVersion Exception : " + e2.getMessage());
            e2.printStackTrace();
        }
        DialogFragment$$ExternalSyntheticOutline0.m(
                "DeviceInfoSettings seStatus : ", string, LOG_TAG);
        return string;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        String str = SystemProperties.get(PROPERTY_SELINUX_POLICY_VERSION, "No Policy Version");
        return ("GOOGLE_POLICY".equals(str)
                        || "No Policy Version".equals(str)
                        || SystemProperties.get(PROPERTY_SELINUX_STATUS)
                                .equals(ApnSettings.MVNO_NONE)
                        || SystemProperties.getInt("persist.sys.iss.flag_altermodel", 0) == 1)
                ? 3
                : 0;
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
        return getSELinuxStatus();
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
