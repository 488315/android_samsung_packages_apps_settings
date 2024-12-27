package com.samsung.android.settings.backup.controller;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.activityembedding.ActivityEmbeddingUtils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager$$ExternalSyntheticOutline0;
import com.android.settingslib.core.lifecycle.LifecycleObserver;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.account.AccountUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SamsungBackupPreferenceController extends BasePreferenceController
        implements LifecycleObserver, PreferenceManager.OnActivityResultListener {
    public static final int ADD_SAMSUNG_ACCOUNT_BACKUP_REQUEST_CODE = 1002;
    private static final String KEY_SAMSUNG_BACKUP = "pref_backUp";
    private static final String SAMSUNG_BACKUP_PACKAGENAME = "com.samsung.android.scloud";
    private static final String TAG = "SamsungBackupPreferenceController";
    private Intent mIntent;
    private Fragment mParentFragment;
    private SecPreference mPreference;

    public SamsungBackupPreferenceController(Context context) {
        this(context, KEY_SAMSUNG_BACKUP);
    }

    private void onBackUpClicked(Intent intent) {
        if (Utils.hasPackage(this.mContext, SAMSUNG_BACKUP_PACKAGENAME)) {
            try {
                if (AccountUtils.isSamsungAccountExists(this.mContext)) {
                    try {
                        this.mContext.startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    AccountUtils.addSamsungAccount(
                            this.mContext, this.mParentFragment, 1002, 1, UserHandle.myUserId());
                }
            } catch (ActivityNotFoundException e2) {
                Log.secD(TAG, "Activity Not Found Exception" + e2.getMessage());
            }
        } else {
            AccountUtils.showDownloadSamsungCloudDialog(this.mContext);
        }
        LoggingHelper.insertEventLogging(81, 4652);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = (SecPreference) preferenceScreen.findPreference(KEY_SAMSUNG_BACKUP);
        if (Rune.isChinaModel()) {
            this.mPreference.setIcon(R.drawable.sec_backup_data);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!Rune.SUPPORT_DISABLE_ACCOUNTS_SETTINGS
                && UserHandle.myUserId() == 0
                && AccountUtils.checkSamsungBackupAvailble(this.mContext)
                && !AccountUtils.checkIsDeviceOwner(this.mContext)) {
            return (!KnoxUtils.checkKnoxCustomSettingsHiddenItem(2048)
                            && Settings.System.getInt(
                                            this.mContext.getContentResolver(),
                                            "samsung_cloud_switch_china_delta",
                                            1)
                                    == 1)
                    ? 0
                    : 2;
        }
        return 3;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_SAMSUNG_BACKUP;
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
        SecPreference secPreference = this.mPreference;
        if (secPreference != null && preference.equals(secPreference)) {
            if (!ActivityEmbeddingUtils.isEmbeddingActivityEnabled(this.mContext)) {
                this.mIntent.addFlags(268468224);
            }
            onBackUpClicked(this.mIntent);
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void init(Fragment fragment) {
        this.mParentFragment = fragment;
        boolean z = AccountUtils.SupportTwoPhone;
        this.mIntent = new Intent("com.samsung.android.scloud.SCLOUD_BACKUP");
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
    public boolean isSliceable() {
        return isAvailable();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // android.preference.PreferenceManager.OnActivityResultListener
    public boolean onActivityResult(int i, int i2, Intent intent) {
        String str = TAG;
        DynamicDenylistManager$$ExternalSyntheticOutline0.m(
                "onActivityResult: requestCode : ", ", resultCode : ", i, i2, str);
        if (i != 1002) {
            return false;
        }
        if (i2 != -1) {
            if (i2 == 0) {
                Log.secD(str, "Samsung Account Login : cancelled");
                return true;
            }
            Log.secD(str, "Samsung Account Login : other reasons");
            return true;
        }
        Log.secD(str, "Samsung Account added");
        Intent intent2 = this.mIntent;
        if (intent2 == null) {
            return true;
        }
        try {
            this.mContext.startActivity(intent2);
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return true;
        }
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

    public SamsungBackupPreferenceController(Context context, String str) {
        super(context, str);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
