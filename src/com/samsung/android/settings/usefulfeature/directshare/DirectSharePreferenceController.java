package com.samsung.android.settings.usefulfeature.directshare;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class DirectSharePreferenceController extends SecMotionAndGestureBaseController {
    private SecSwitchPreference mPreference;

    public DirectSharePreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 3;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public Intent getLaunchIntent() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                "com.android.settings.Settings$UsefulFeatureMainActivity");
        intent.addFlags(268468224);
        Bundle bundle = new Bundle();
        bundle.putString(":settings:fragment_args_key", "direct_share");
        intent.putExtra(":settings:show_fragment_args", bundle);
        return intent;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_advanced_features;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController
    public ArrayList<Uri> getUriListRequiringObservation() {
        ArrayList<Uri> arrayList = new ArrayList<>(1);
        arrayList.add(Settings.System.getUriFor("direct_share"));
        return arrayList;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return Settings.System.getInt(this.mContext.getContentResolver(), "direct_share", 1) != 0;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return isAvailable();
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        Settings.System.putInt(this.mContext.getContentResolver(), "direct_share", z ? 1 : 0);
        LoggingHelper.insertEventLogging(4350, 4374, z);
        return true;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController
    public void updatePreference() {
        this.mPreference.setChecked(getThreadEnabled());
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
