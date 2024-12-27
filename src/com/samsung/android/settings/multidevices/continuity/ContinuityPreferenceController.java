package com.samsung.android.settings.multidevices.continuity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;

import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.R;
import com.android.settings.bluetooth.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.sec.ims.settings.ImsProfile;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ContinuityPreferenceController extends TogglePreferenceController {
    public static final int COPY_AND_PASTE = 8;
    public static final int HAND_OFF = 4;
    private static final String KEY_CONTINUITY = "continuity_setting";
    private static final int MCF_CONTINUITY_TYPE =
            SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_MCF_SUPPORT_CONTINUITY");
    public static final String SAMSUNG_ACCOUNT_TYPE = "com.osp.app.signin";
    private LocalBluetoothAdapter mLocalAdapter;
    private SecSwitchPreferenceScreen mPreference;
    private final WifiManager mWifiManager;

    public ContinuityPreferenceController(Context context, String str) {
        super(context, str);
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback);
        if (localBluetoothManager != null) {
            this.mLocalAdapter = localBluetoothManager.mLocalAdapter;
        }
        this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
    }

    private boolean setWifiBtEnabled() {
        boolean z;
        LocalBluetoothAdapter localBluetoothAdapter = this.mLocalAdapter;
        if (localBluetoothAdapter == null || localBluetoothAdapter.mAdapter.isEnabled()) {
            z = false;
        } else {
            this.mLocalAdapter.setBluetoothEnabled(true);
            z = true;
        }
        if (this.mWifiManager.isWifiEnabled()) {
            return z;
        }
        this.mWifiManager.setWifiEnabled(true);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecSwitchPreferenceScreen) preferenceScreen.findPreference(KEY_CONTINUITY);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        int i = MCF_CONTINUITY_TYPE;
        if (i <= 0) {
            return 3;
        }
        return ((i & 4) == 4 || (i & 8) == 8) ? 0 : 3;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    public Account getSamsungAccount() {
        AccountManager accountManager = AccountManager.get(this.mContext);
        if (accountManager == null) {
            return null;
        }
        Account[] accountsByType = accountManager.getAccountsByType("com.osp.app.signin");
        if (accountsByType.length > 0) {
            return accountsByType[0];
        }
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_advanced_features;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return ContinuitySettings.getContinuitySettingValue(this.mContext);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        if (z) {
            if (getSamsungAccount() == null) {
                SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mPreference;
                if (secSwitchPreferenceScreen != null) {
                    secSwitchPreferenceScreen.setChecked(false);
                }
                ContinuitySettings.setContinuitySettingValue(this.mContext, 0);
                startActivityForResult(1);
                return true;
            }
            if (setWifiBtEnabled()) {
                Context context = this.mContext;
                Preference$$ExternalSyntheticOutline0.m(
                        context,
                        R.string.continuity_set_enable_description,
                        new Object[] {context.getString(R.string.continuity_title)},
                        context,
                        1);
            }
        }
        ContinuitySettings.setContinuitySettingValue(this.mContext, z ? 1 : 0);
        return true;
    }

    public void startActivityForResult(int i) {
        Intent intent = new Intent(this.mContext, (Class<?>) ContinuityAddAccountActivity.class);
        intent.putExtra("requestCode", i);
        try {
            this.mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
