package com.samsung.android.settings.security;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.UserHandle;

import androidx.preference.Preference;
import androidx.preference.SecPreferenceScreen;

import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SamsungPassPreferenceController extends BasePreferenceController {
    private static final String KEY_SAMSUNG_PASS = "samsung_pass";
    private static final String PKG_SAMSUNG_PASS = "com.samsung.android.samsungpass";
    private Intent mIntent;
    private SecPreferenceScreen mPreference;

    public SamsungPassPreferenceController(Context context) {
        this(context, KEY_SAMSUNG_PASS);
    }

    private int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(PKG_SAMSUNG_PASS, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (Utils.hasPackage(this.mContext, PKG_SAMSUNG_PASS)
                        && Utils.hasPackage(this.mContext, "com.samsung.android.authfw")
                        && UserHandle.myUserId() == 0
                        && this.mIntent != null
                        && getVersionCode(this.mContext) <= 300101900)
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!preference.getKey().equals(getPreferenceKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        LoggingHelper.insertEventLogging(9031, 4492);
        try {
            this.mContext.startActivity(this.mIntent);
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return true;
        }
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

    public SamsungPassPreferenceController(Context context, String str) {
        super(context, str);
        this.mIntent = context.getPackageManager().getLaunchIntentForPackage(PKG_SAMSUNG_PASS);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
