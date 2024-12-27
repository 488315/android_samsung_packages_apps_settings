package com.android.settings.security;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.os.UserManager;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SimLockPreferenceController extends BasePreferenceController {
    private final CarrierConfigManager mCarrierConfigManager;
    private String mConfigNetworkTypeCapability;
    private final SubscriptionManager mSubscriptionManager;
    private TelephonyManager mTelephonyManager;
    private final UserManager mUserManager;

    public SimLockPreferenceController(Context context, String str) {
        super(context, str);
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mCarrierConfigManager =
                (CarrierConfigManager) this.mContext.getSystemService("carrier_config");
        this.mSubscriptionManager =
                ((SubscriptionManager) context.getSystemService("telephony_subscription_service"))
                        .createForAllUserProfiles();
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
        this.mConfigNetworkTypeCapability =
                SemCarrierFeature.getInstance()
                        .getString(
                                0,
                                "CarrierFeature_RIL_ConfigNetworkTypeCapability",
                                ApnSettings.MVNO_NONE,
                                true);
    }

    private boolean isCctModel() {
        String str;
        String str2 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return "CCT".equals(Utils.getSalesCode())
                && (str = this.mConfigNetworkTypeCapability) != null
                && str.contains("VZW-CCT");
    }

    private boolean isHideSimLockSetting(List<SubscriptionInfo> list) {
        if (list == null) {
            return true;
        }
        for (SubscriptionInfo subscriptionInfo : list) {
            TelephonyManager createForSubscriptionId =
                    this.mTelephonyManager.createForSubscriptionId(
                            subscriptionInfo.getSubscriptionId());
            PersistableBundle configForSubId =
                    this.mCarrierConfigManager.getConfigForSubId(
                            subscriptionInfo.getSubscriptionId());
            if (createForSubscriptionId.hasIccCard()
                    && configForSubId != null
                    && !configForSubId.getBoolean("hide_sim_lock_settings_bool")) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (findPreference == null) {
            return;
        }
        findPreference.setEnabled(isSimReady());
        findPreference.setTitle(
                replaceSIMString(
                        this.mContext
                                .getResources()
                                .getString(R.string.sim_lock_settings_category)));
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x006e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x006f  */
    @Override // com.android.settings.core.BasePreferenceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getAvailabilityStatus() {
        /*
            r6 = this;
            android.content.Context r0 = r6.mContext
            boolean r0 = com.android.settings.network.SubscriptionUtil.isSimHardwareVisible(r0)
            r1 = 3
            if (r0 != 0) goto La
            return r1
        La:
            android.telephony.SubscriptionManager r0 = r6.mSubscriptionManager
            java.util.List r0 = r0.getActiveSubscriptionInfoList()
            java.lang.String r2 = "gsm.sim.state"
            java.lang.String r2 = android.os.SystemProperties.get(r2)
            java.lang.String r3 = "UNKNOWN"
            if (r2 == 0) goto L2d
            int r4 = r2.length()
            if (r4 != 0) goto L21
            goto L2d
        L21:
            java.lang.String r4 = ","
            boolean r5 = r2.contains(r4)
            if (r5 != 0) goto L2a
            goto L2e
        L2a:
            r2.equals(r4)
        L2d:
            r2 = r3
        L2e:
            boolean r3 = com.samsung.android.settings.Rune.isLDUModel()
            if (r3 != 0) goto L7f
            android.content.Context r3 = r6.mContext
            boolean r3 = com.samsung.android.settings.Rune.isShopDemo(r3)
            if (r3 != 0) goto L7f
            boolean r3 = r6.isCctModel()
            if (r3 != 0) goto L7f
            android.content.Context r3 = r6.mContext
            boolean r3 = com.samsung.android.settings.Rune.isVzwDemoMode(r3)
            if (r3 != 0) goto L7f
            java.lang.String r3 = "NETWORK_LOCKED"
            boolean r3 = r2.equalsIgnoreCase(r3)
            if (r3 != 0) goto L7f
            java.lang.String r3 = "PERSO_LOCKED"
            boolean r3 = r2.equalsIgnoreCase(r3)
            if (r3 != 0) goto L7f
            java.lang.String r3 = "NETWORK_SUBSET_LOCKED"
            boolean r3 = r2.equalsIgnoreCase(r3)
            if (r3 != 0) goto L7f
            java.lang.String r3 = "REGIONAL_LOCKED"
            boolean r2 = r2.equalsIgnoreCase(r3)
            if (r2 == 0) goto L6b
            goto L7f
        L6b:
            r1 = 4
            if (r0 != 0) goto L6f
            return r1
        L6f:
            android.os.UserManager r2 = r6.mUserManager
            boolean r2 = r2.isAdminUser()
            if (r2 == 0) goto L7f
            boolean r6 = r6.isHideSimLockSetting(r0)
            if (r6 != 0) goto L7f
            r6 = 0
            return r6
        L7f:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.security.SimLockPreferenceController.getAvailabilityStatus():int");
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

    public boolean isSimReady() {
        List<SubscriptionInfo> activeSubscriptionInfoList =
                this.mSubscriptionManager.getActiveSubscriptionInfoList();
        if (activeSubscriptionInfoList == null) {
            return false;
        }
        Iterator<SubscriptionInfo> it = activeSubscriptionInfoList.iterator();
        while (it.hasNext()) {
            int simState = this.mTelephonyManager.getSimState(it.next().getSimSlotIndex());
            if (simState != 1 && simState != 0 && simState != 7) {
                return true;
            }
        }
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

    public String replaceSIMString(String str) {
        return !Rune.isChinaCTCModel()
                ? str
                : TelephonyManager.getDefault().getPhoneCount() <= 1
                        ? str.replace(UniversalCredentialManager.APPLET_FORM_FACTOR_SIM, "UIM")
                        : str.replace(UniversalCredentialManager.APPLET_FORM_FACTOR_SIM, "UIM/SIM");
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
