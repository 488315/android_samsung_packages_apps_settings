package com.android.settings.applications.specialaccess;

import android.app.role.RoleManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.os.UserHandle;
import android.os.UserManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DefaultPaymentSettingsPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    private final NfcAdapter mNfcAdapter;
    private final PackageManager mPackageManager;
    private PaymentSettingsEnabler mPaymentSettingsEnabler;
    private final UserManager mUserManager;

    public DefaultPaymentSettingsPreferenceController(Context context, String str) {
        super(context, str);
        this.mPackageManager = context.getPackageManager();
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mNfcAdapter = NfcAdapter.getDefaultAdapter(this.mContext);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (!isAvailable()) {
            this.mPaymentSettingsEnabler = null;
        } else {
            this.mPaymentSettingsEnabler =
                    new PaymentSettingsEnabler(
                            this.mContext, preferenceScreen.findPreference(getPreferenceKey()));
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!this.mPackageManager.hasSystemFeature("android.hardware.nfc")
                || !this.mPackageManager.hasSystemFeature("android.hardware.nfc.hce")) {
            return 3;
        }
        if (this.mUserManager.isGuestUser()) {
            return 4;
        }
        NfcAdapter nfcAdapter = this.mNfcAdapter;
        if (nfcAdapter == null) {
            return 2;
        }
        return !nfcAdapter.isEnabled() ? 5 : 0;
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
        if (!Flags.walletRoleEnabled()
                || !this.mPreferenceKey.equals(preference.getKey())
                || !((RoleManager) this.mContext.getSystemService(RoleManager.class))
                        .isRoleAvailable("android.app.role.WALLET")) {
            return false;
        }
        this.mContext.startActivity(
                new Intent("android.nfc.cardemulation.action.ACTION_CHANGE_DEFAULT")
                        .setPackage(this.mPackageManager.getPermissionControllerPackageName()));
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

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        PaymentSettingsEnabler paymentSettingsEnabler = this.mPaymentSettingsEnabler;
        if (paymentSettingsEnabler == null || paymentSettingsEnabler.mNfcAdapter == null) {
            return;
        }
        paymentSettingsEnabler.mContext.unregisterReceiver(paymentSettingsEnabler.mReceiver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        PaymentSettingsEnabler paymentSettingsEnabler = this.mPaymentSettingsEnabler;
        if (paymentSettingsEnabler == null || paymentSettingsEnabler.mNfcAdapter == null) {
            return;
        }
        if (paymentSettingsEnabler
                .mCardEmuManager
                .getServices("payment", UserHandle.myUserId())
                .isEmpty()) {
            paymentSettingsEnabler.mIsPaymentAvailable = false;
        } else {
            paymentSettingsEnabler.mIsPaymentAvailable = true;
        }
        NfcAdapter nfcAdapter = paymentSettingsEnabler.mNfcAdapter;
        if (nfcAdapter != null) {
            paymentSettingsEnabler.handleNfcStateChanged(nfcAdapter.getAdapterState());
            paymentSettingsEnabler.mContext.registerReceiver(
                    paymentSettingsEnabler.mReceiver, paymentSettingsEnabler.mIntentFilter, 2);
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

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
