package com.android.settings.network;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.net.VpnManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.security.LegacyVpnProfileStore;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.net.LegacyVpnInfo;
import com.android.internal.net.VpnConfig;
import com.android.internal.net.VpnProfile;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.vpn2.VpnInfoPreference;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class VpnPreferenceController extends BasePreferenceController
        implements PreferenceControllerMixin, LifecycleObserver, OnResume, OnPause {
    private static final String KEY_VPN_SETTINGS = "vpn_settings";
    private static final NetworkRequest REQUEST =
            new NetworkRequest.Builder()
                    .removeCapability(15)
                    .removeCapability(13)
                    .removeCapability(14)
                    .build();
    private static final String TAG = "VpnPreferenceController";
    private ConnectivityManager mConnectivityManager;
    private final ConnectivityManager.NetworkCallback mNetworkCallback;
    private Preference mPreference;

    public VpnPreferenceController(Context context) {
        super(context, KEY_VPN_SETTINGS);
        this.mNetworkCallback =
                new ConnectivityManager
                        .NetworkCallback() { // from class:
                                             // com.android.settings.network.VpnPreferenceController.1
                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onAvailable(Network network) {
                        VpnPreferenceController.this.updateSummary();
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onLost(Network network) {
                        VpnPreferenceController.this.updateSummary();
                    }
                };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ VpnProfile lambda$getInsecureVpnCount$3(String str) {
        return VpnProfile.decode(str, LegacyVpnProfileStore.get("VPN_" + str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getInsecureVpnCount$4(VpnProfile vpnProfile) {
        return VpnProfile.isLegacyType(vpnProfile.type);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ VpnConfig lambda$getNumberOfNonLegacyVpn$1(
            VpnManager vpnManager, UserInfo userInfo) {
        return vpnManager.getVpnConfig(userInfo.id);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getNumberOfNonLegacyVpn$2(VpnConfig vpnConfig) {
        return (vpnConfig == null || vpnConfig.legacy) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateSummary$0(String str) {
        this.mPreference.setSummary(str);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = getEffectivePreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (UserHandle.myUserId() != 0
                        || Rune.isShopDemo(this.mContext)
                        || Rune.isLDUModel()
                        || "MTR".equals(Utils.getSalesCode())
                        || RestrictedLockUtilsInternal.hasBaseUserRestriction(
                                this.mContext, UserHandle.myUserId(), "no_config_vpn"))
                ? 4
                : 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public Preference getEffectivePreference(PreferenceScreen preferenceScreen) {
        Preference findPreference = preferenceScreen.findPreference(KEY_VPN_SETTINGS);
        if (findPreference == null) {
            return null;
        }
        String string =
                Settings.Global.getString(
                        this.mContext.getContentResolver(), "airplane_mode_toggleable_radios");
        if (string != null) {
            string.contains(ImsProfile.PDN_WIFI);
        }
        return findPreference;
    }

    public int getInsecureVpnCount(String[] strArr) {
        return (int)
                Arrays.stream(strArr)
                        .map(new VpnPreferenceController$$ExternalSyntheticLambda3())
                        .filter(new VpnPreferenceController$$ExternalSyntheticLambda1(1))
                        .count();
    }

    public String getInsecureVpnSummaryOverride(UserManager userManager, VpnManager vpnManager) {
        if (!(this.mPreference instanceof VpnInfoPreference)) {
            return null;
        }
        String[] list = LegacyVpnProfileStore.list("VPN_");
        int insecureVpnCount = getInsecureVpnCount(list);
        boolean z = insecureVpnCount > 0;
        VpnInfoPreference vpnInfoPreference = (VpnInfoPreference) this.mPreference;
        if (vpnInfoPreference.mIsInsecureVpn != z) {
            vpnInfoPreference.mIsInsecureVpn = z;
            vpnInfoPreference.notifyChanged();
        }
        if (!z) {
            return null;
        }
        int length = list.length;
        return (length > 1 || getNumberOfNonLegacyVpn(userManager, vpnManager) + length != 1)
                ? insecureVpnCount == 1
                        ? this.mContext.getString(
                                R.string.vpn_settings_single_insecure_multiple_total,
                                Integer.valueOf(insecureVpnCount))
                        : this.mContext.getString(
                                R.string.vpn_settings_multiple_insecure_multiple_total,
                                Integer.valueOf(insecureVpnCount))
                : this.mContext.getString(R.string.vpn_settings_insecure_single);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    public String getNameForVpnConfig(VpnConfig vpnConfig, UserHandle userHandle) {
        if (vpnConfig.legacy) {
            return this.mContext.getString(R.string.wifi_display_status_connected);
        }
        String str = vpnConfig.user;
        try {
            Context context = this.mContext;
            return VpnConfig.getVpnLabel(
                            context.createPackageContextAsUser(
                                    context.getPackageName(), 0, userHandle),
                            str)
                    .toString();
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Package " + str + " is not present", e);
            return null;
        }
    }

    public int getNumberOfNonLegacyVpn(UserManager userManager, final VpnManager vpnManager) {
        return (int)
                userManager.getUsers().stream()
                        .map(
                                new Function() { // from class:
                                                 // com.android.settings.network.VpnPreferenceController$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        VpnConfig lambda$getNumberOfNonLegacyVpn$1;
                                        lambda$getNumberOfNonLegacyVpn$1 =
                                                VpnPreferenceController
                                                        .lambda$getNumberOfNonLegacyVpn$1(
                                                                vpnManager, (UserInfo) obj);
                                        return lambda$getNumberOfNonLegacyVpn$1;
                                    }
                                })
                        .filter(new VpnPreferenceController$$ExternalSyntheticLambda1(0))
                        .count();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_VPN_SETTINGS;
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

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        ConnectivityManager connectivityManager = this.mConnectivityManager;
        if (connectivityManager != null) {
            connectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
            this.mConnectivityManager = null;
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        if (!isAvailable()) {
            this.mConnectivityManager = null;
            return;
        }
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class);
        this.mConnectivityManager = connectivityManager;
        connectivityManager.registerNetworkCallback(REQUEST, this.mNetworkCallback);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void updateSummary() {
        LegacyVpnInfo legacyVpnInfo;
        if (this.mPreference == null) {
            return;
        }
        UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
        VpnManager vpnManager = (VpnManager) this.mContext.getSystemService(VpnManager.class);
        final String insecureVpnSummaryOverride =
                getInsecureVpnSummaryOverride(userManager, vpnManager);
        if (insecureVpnSummaryOverride == null) {
            UserInfo userInfo = userManager.getUserInfo(UserHandle.myUserId());
            int i = userInfo.isRestricted() ? userInfo.restrictedProfileParentId : userInfo.id;
            VpnConfig vpnConfig = vpnManager.getVpnConfig(i);
            if (vpnConfig != null
                    && vpnConfig.legacy
                    && ((legacyVpnInfo = vpnManager.getLegacyVpnInfo(i)) == null
                            || legacyVpnInfo.state != 3)) {
                vpnConfig = null;
            }
            insecureVpnSummaryOverride =
                    vpnConfig == null
                            ? this.mContext.getString(R.string.vpn_disconnected_summary)
                            : getNameForVpnConfig(vpnConfig, UserHandle.of(i));
        }
        ThreadUtils.postOnMainThread(
                new Runnable() { // from class:
                                 // com.android.settings.network.VpnPreferenceController$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        VpnPreferenceController.this.lambda$updateSummary$0(
                                insecureVpnSummaryOverride);
                    }
                });
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public VpnPreferenceController(Context context, String str) {
        super(context, str);
        this.mNetworkCallback =
                new ConnectivityManager
                        .NetworkCallback() { // from class:
                                             // com.android.settings.network.VpnPreferenceController.1
                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onAvailable(Network network) {
                        VpnPreferenceController.this.updateSummary();
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onLost(Network network) {
                        VpnPreferenceController.this.updateSummary();
                    }
                };
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
