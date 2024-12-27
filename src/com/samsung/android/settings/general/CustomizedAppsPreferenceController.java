package com.samsung.android.settings.general;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.privacy.PrivacyUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CustomizedAppsPreferenceController extends BasePreferenceController {
    public static final String BUNDLE_CURRENT_RUBIN_STATE = "currentRubinState";
    public static final String BUNDLE_IS_ENABLED_IN_SUPPORTED_APPS = "isEnabledInSupportedApps";
    public static final int DUMMY_HEIGHT = 12;
    public static final String EXTRA_TARGET_PAGE = "targetPage";
    public static final String METHOD_GET_RUBIN_STATE = "getRubinState";
    public static final int PAUSED = 2;
    public static final String RUBIN_AUTHORITY = "com.samsung.android.rubin.state";
    public static final Uri RUBIN_AUTHORITY_URI =
            Uri.parse("content://com.samsung.android.rubin.state");
    public static final String RUBIN_SETTINGS_ACTION = "com.samsung.android.rubin.CS_SETTINGS";
    public static final String STATE_ACCOUNT_NOT_SIGNED_IN = "ACCOUNT_NOT_SIGNED_IN";
    public static final String STATE_USER_NOT_CONSENT_TO_COLLECT_DATA =
            "USER_NOT_CONSENT_TO_COLLECT_DATA";
    public static final String STATE_USER_NOT_ENABLE_RUBIN_IN_DEVICE =
            "USER_NOT_ENABLE_RUBIN_IN_DEVICE";
    public static final int UNUSED = 1;
    public static final int VALUE_SUPPORTED_APPS_PAGE = 2;
    private SecPreference mPreference;

    public CustomizedAppsPreferenceController(Context context, String str) {
        super(context, str);
    }

    private int getRubinState() {
        try {
            Bundle call =
                    this.mContext
                            .getContentResolver()
                            .call(
                                    RUBIN_AUTHORITY_URI,
                                    METHOD_GET_RUBIN_STATE,
                                    this.mContext.getPackageName(),
                                    (Bundle) null);
            if (call == null) {
                return -1;
            }
            String string = call.getString(BUNDLE_CURRENT_RUBIN_STATE, "NA");
            if (!TextUtils.equals(string, STATE_ACCOUNT_NOT_SIGNED_IN)
                    && !TextUtils.equals(string, STATE_USER_NOT_CONSENT_TO_COLLECT_DATA)) {
                return TextUtils.equals(string, STATE_USER_NOT_ENABLE_RUBIN_IN_DEVICE) ? 2 : -1;
            }
            return 1;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private String getRubinStateSummary() {
        int rubinState = getRubinState();
        String samsungAccountName = getSamsungAccountName(this.mContext);
        Resources resources = this.mContext.getResources();
        return (rubinState == 1 || samsungAccountName == null)
                ? resources.getString(R.string.sec_general_customization_service_not_in_use)
                : rubinState == 2
                        ? resources.getString(R.string.sec_general_customization_service_off)
                        : resources.getString(R.string.sec_general_customization_service_on);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecPreference secPreference =
                (SecPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = secPreference;
        secPreference.getClass();
        SecPreferenceUtils.applySummaryColor(secPreference, true);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (PrivacyUtils.isSupportRuneStonePackage(this.mContext)
                        && Rune.supportBlueLightFilterAdaptiveMode())
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

    public String getSamsungAccountName(Context context) {
        Account[] accountsByType =
                AccountManager.get(context).getAccountsByType("com.osp.app.signin");
        if (accountsByType == null || accountsByType.length <= 0) {
            return null;
        }
        return accountsByType[0].name;
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
        return getRubinStateSummary();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        Intent intent = new Intent(RUBIN_SETTINGS_ACTION);
        intent.putExtra(EXTRA_TARGET_PAGE, 2);
        try {
            this.mContext.startActivity(intent);
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

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
