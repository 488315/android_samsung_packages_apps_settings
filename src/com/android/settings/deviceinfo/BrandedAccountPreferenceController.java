package com.android.settings.deviceinfo;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.accounts.AccountDashboardFragment;
import com.android.settings.accounts.AccountFeatureProvider;
import com.android.settings.accounts.AccountFeatureProviderImpl;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BrandedAccountPreferenceController extends BasePreferenceController {
    private final AccountFeatureProvider mAccountFeatureProvider;
    private Account[] mAccounts;

    public BrandedAccountPreferenceController(Context context, String str) {
        super(context, str);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        AccountFeatureProvider accountFeatureProvider =
                (AccountFeatureProvider)
                        featureFactoryImpl.accountFeatureProvider$delegate.getValue();
        this.mAccountFeatureProvider = accountFeatureProvider;
        ((AccountFeatureProviderImpl) accountFeatureProvider).getClass();
        this.mAccounts = new Account[0];
    }

    private String getAccountSummary(int i) {
        return this.mContext
                .getResources()
                .getString(R.string.my_device_info_account_preference_summary, Integer.valueOf(i));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        Account[] accountArr;
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (findPreference != null
                && ((accountArr = this.mAccounts) == null || accountArr.length == 0)) {
            preferenceScreen.removePreference(findPreference);
            return;
        }
        Account[] accountArr2 = this.mAccounts;
        if (accountArr2.length == 1) {
            findPreference.setSummary(accountArr2[0].name);
        } else {
            findPreference.setSummary(getAccountSummary(accountArr2.length));
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!this.mContext
                .getResources()
                .getBoolean(R.bool.config_show_branded_account_in_device_info)) {
            return 3;
        }
        Account[] accountArr = this.mAccounts;
        return (accountArr == null || accountArr.length <= 0) ? 4 : 0;
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
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = AccountDashboardFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        subSettingLauncher.setTitleRes(R.string.account_dashboard_title, null);
        launchRequest.mSourceMetricsCategory = 40;
        subSettingLauncher.launch();
        return true;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        ((AccountFeatureProviderImpl) this.mAccountFeatureProvider).getClass();
        Account[] accountArr = new Account[0];
        this.mAccounts = accountArr;
        if (accountArr.length == 0) {
            preference.setVisible(false);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
