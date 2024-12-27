package com.samsung.android.settings.account.controller;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Process;
import android.os.UserHandle;
import android.os.UserManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.bixby.utils.BixbyUtils;
import com.samsung.android.settings.connection.SecSimFeatureProviderImpl;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.view.SemWindowManager;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AutoSyncBaseController extends TogglePreferenceController {
    private static final String EXTRA_CHECK_STATE = "check_state";
    private static final String PREFERENCE_KEY = "auto_sync_account_data";
    private static final String TAG = "AutoSyncBaseController";
    protected UserHandle mUserHandle;
    protected final UserManager mUserManager;

    public AutoSyncBaseController(Context context) {
        super(context, PREFERENCE_KEY);
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mUserHandle = Process.myUserHandle();
    }

    private boolean isBlockedByEDM() {
        TelephonyManager telephonyManager =
                (TelephonyManager) this.mContext.getSystemService("phone");
        int enterprisePolicyEnabled =
                Utils.getEnterprisePolicyEnabled(
                        this.mContext,
                        "content://com.sec.knox.provider/RoamingPolicy",
                        "isRoamingSyncEnabled",
                        new String[] {"false"});
        boolean z = false;
        boolean z2 = enterprisePolicyEnabled != -1 && enterprisePolicyEnabled == 0;
        boolean isNetworkRoaming = telephonyManager.isNetworkRoaming();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        if (((SecSimFeatureProviderImpl) featureFactoryImpl.getSecSimFeatureProvider())
                .isMultiSimModel()) {
            String semGetTelephonyProperty =
                    TelephonyManager.semGetTelephonyProperty(
                            SubscriptionManager.getPhoneId(
                                    SubscriptionManager.getDefaultDataSubscriptionId()),
                            "gsm.operator.isroaming",
                            "false");
            isNetworkRoaming =
                    !TextUtils.isEmpty(semGetTelephonyProperty)
                            && semGetTelephonyProperty.equals("true");
        }
        if (z2 && isNetworkRoaming) {
            z = true;
        }
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m("isBlockedByEDM: ", TAG, z);
        return z;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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
    public Intent getLaunchIntent() {
        Bundle m =
                AbsAdapter$1$$ExternalSyntheticOutline0.m(
                        ":settings:fragment_args_key", PREFERENCE_KEY);
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                "com.android.settings.Settings$AccountDashboardActivity");
        intent.putExtra(":settings:show_fragment_args", m);
        intent.addFlags(268468224);
        return intent;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_accounts_backup;
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
        if (this.mUserHandle == null) {
            this.mUserHandle = Process.myUserHandle();
        }
        return ContentResolver.getMasterSyncAutomaticallyAsUser(this.mUserHandle.getIdentifier());
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
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
        LoggingHelper.insertEventLogging(8, 4651, z);
        if (ActivityManager.isUserAMonkey() || isBlockedByEDM()) {
            Log.d(TAG, "ignoring monkey's attempt to flip sync state");
            return true;
        }
        Intent action =
                new Intent().setAction("com.samsung.settings.account.AutoSyncConfirmDialog");
        if (!(this.mContext instanceof Activity)) {
            action.putExtra(EXTRA_CHECK_STATE, z);
            action.addFlags(268435456);
        }
        if (BixbyUtils.isLargeSubDisplayScreen() && SemWindowManager.getInstance().isFolded()) {
            BixbyUtils.setPendingIntentAfterUnlock(this.mContext, action);
            return true;
        }
        this.mContext.startActivity(action);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
