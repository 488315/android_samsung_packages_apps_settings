package com.samsung.android.settings.deviceinfo.softwareinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.SystemProperties;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.container.EnterpriseContainerConstants;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class OneUIVersionPreferenceController extends BasePreferenceController {
    private static final double DEFAULT_ONE_UI_VERSION = 7.0d;
    private static final String LOG_TAG = "OneUIVersionPreferenceCtr";
    private static final int ONE_UI_VERSION_SEP_VERSION_GAP = 90000;

    public OneUIVersionPreferenceController(Context context, String str) {
        super(context, str);
    }

    private String getDisplayVersion() {
        double d;
        int oneUiVersion = getOneUiVersion();
        int sepVersion = getSepVersion();
        if (oneUiVersion > 0) {
            int i = oneUiVersion / EnterpriseContainerConstants.SYSTEM_SIGNED_APP;
            int i2 = (oneUiVersion % EnterpriseContainerConstants.SYSTEM_SIGNED_APP) / 100;
            int i3 = oneUiVersion % 100;
            if (i3 == 0 || !isDeviceWithMicroVersion()) {
                d = Double.parseDouble(i + "." + i2);
            } else {
                d = Double.parseDouble(i + "." + i2 + "." + i3);
            }
        } else if (sepVersion >= 160000) {
            int i4 = sepVersion - ONE_UI_VERSION_SEP_VERSION_GAP;
            d =
                    Double.parseDouble(
                            (i4 / EnterpriseContainerConstants.SYSTEM_SIGNED_APP)
                                    + "."
                                    + ((i4 % EnterpriseContainerConstants.SYSTEM_SIGNED_APP)
                                            / 100));
        } else {
            d = DEFAULT_ONE_UI_VERSION;
        }
        return String.valueOf(d);
    }

    private int getOneUiVersion() {
        int i = SystemProperties.getInt("ro.build.version.oneui", 0);
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(i, "property of oneUI version: ", LOG_TAG);
        return i;
    }

    private int getSepVersion() {
        int i = Build.VERSION.SEM_PLATFORM_INT;
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(i, "property of SEP version: ", LOG_TAG);
        return i;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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
        return getDisplayVersion();
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

    public boolean isDeviceWithMicroVersion() {
        return Utils.isTablet();
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
