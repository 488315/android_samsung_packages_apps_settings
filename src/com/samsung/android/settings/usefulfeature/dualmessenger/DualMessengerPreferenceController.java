package com.samsung.android.settings.usefulfeature.dualmessenger;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.DisplaySettings$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.enterprise.EnterprisePrivacyFeatureProvider;
import com.android.settings.enterprise.EnterprisePrivacyFeatureProviderImpl;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.MinorModeUtils;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class DualMessengerPreferenceController extends BasePreferenceController {
    private static final String KEY_DUAL_APP = "dual_app";
    private static String TAG =
            "com.samsung.android.settings.usefulfeature.dualmessenger.DualMessengerPreferenceController";
    private final EnterprisePrivacyFeatureProvider mFeatureProvider;

    public DualMessengerPreferenceController(Context context) {
        this(context, KEY_DUAL_APP);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        SemDualAppManager semDualAppManager =
                SemDualAppManager.getInstance(this.mContext.getApplicationContext());
        if (semDualAppManager == null
                || !semDualAppManager.isSupported()
                || ((EnterprisePrivacyFeatureProviderImpl) this.mFeatureProvider).hasDeviceOwner()
                || SemFloatingFeature.getInstance()
                        .getBoolean(
                                "SEC_FLOATING_FEATURE_COMMON_SUPPORT_DISABLED_MENU_K05", false)) {
            return 3;
        }
        Context context = this.mContext;
        Log.d("SettingsLib/MinorModeUtils", "dual messenger restriction:");
        return MinorModeUtils.isCHNMinorModeRestrictionEnabled(
                        context, "get_block_multiple_users", "block_multiple_users")
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
    public Intent getLaunchIntent() {
        Intent m =
                DisplaySettings$$ExternalSyntheticOutline0.m(
                        "com.samsung.android.da.daagent",
                        "com.samsung.android.da.daagent.activity.DualAppActivity");
        m.putExtra(
                "android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_HIGHLIGHT_MENU_KEY",
                this.mContext.getString(R.string.menu_key_advanced_features));
        m.addFlags(268468224);
        return m;
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
        if (KEY_DUAL_APP.equals(preference.getKey())) {
            try {
                Intent intent = new Intent();
                intent.setClassName(
                        "com.samsung.android.da.daagent",
                        "com.samsung.android.da.daagent.activity.DualAppActivity");
                android.util.secutil.Log.secD(TAG, "handlePreferenceTreeClick");
                this.mContext.startActivity(intent);
                return true;
            } catch (ActivityNotFoundException e) {
                android.util.secutil.Log.secD(TAG, "ActivityNotFoundException in DualApp");
                e.printStackTrace();
            }
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return isAvailable();
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

    public DualMessengerPreferenceController(Context context, String str) {
        super(context, str);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mFeatureProvider = featureFactoryImpl.getEnterprisePrivacyFeatureProvider();
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
