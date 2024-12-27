package com.samsung.android.settings.privacy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.biometrics.BiometricPrompt;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.mediarouter.media.MediaRoute2Provider$$ExternalSyntheticLambda0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.theftprotection.TheftProtectionUtils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecurityDashboardOfflineLockSwitchController extends TogglePreferenceController {
    final int MANDATORY_BIOMETRICS;
    protected Context mContext;
    protected boolean mIsOfflineLockOnBoarded;
    private final boolean mIsOfflineLockSupported;
    protected boolean mIsScreenLockSet;
    private SecSwitchPreference mPreference;

    public SecurityDashboardOfflineLockSwitchController(Context context, String str) {
        super(context, str);
        this.MANDATORY_BIOMETRICS = 65536;
        this.mContext = context;
        this.mIsOfflineLockSupported = SecurityDashboardUtils.isOfflineLockSupported(context);
    }

    private void authenticate() {
        boolean z =
                Settings.Secure.getInt(
                                this.mContext.getContentResolver(), "mandatory_biometrics", -1)
                        == 1;
        boolean isInTrustedPlace = TheftProtectionUtils.isInTrustedPlace(this.mContext);
        Handler handler = new Handler(Looper.getMainLooper());
        ((!z || isInTrustedPlace)
                        ? new BiometricPrompt.Builder(this.mContext)
                                .setTitle(
                                        this.mContext
                                                .getResources()
                                                .getString(R.string.settings_label))
                                .setSubtitle(
                                        this.mContext
                                                .getResources()
                                                .getString(R.string.sec_biometircs_prompt_title))
                                .setAllowedAuthenticators(32783)
                                .build()
                        : new BiometricPrompt.Builder(this.mContext)
                                .setTitle(
                                        this.mContext
                                                .getResources()
                                                .getString(R.string.settings_label))
                                .setSubtitle(
                                        this.mContext
                                                .getResources()
                                                .getString(R.string.sec_biometircs_prompt_title))
                                .setNegativeButton(
                                        this.mContext.getResources().getString(R.string.cancel),
                                        new MediaRoute2Provider$$ExternalSyntheticLambda0(handler),
                                        new DialogInterface
                                                .OnClickListener() { // from class:
                                                                     // com.samsung.android.settings.privacy.SecurityDashboardOfflineLockSwitchController$$ExternalSyntheticLambda1
                                            @Override // android.content.DialogInterface.OnClickListener
                                            public final void onClick(
                                                    DialogInterface dialogInterface, int i) {
                                                SecurityDashboardOfflineLockSwitchController.this
                                                        .lambda$authenticate$1(dialogInterface, i);
                                            }
                                        })
                                .setAllowedAuthenticators(65551)
                                .build())
                .authenticate(
                        new CancellationSignal(),
                        new MediaRoute2Provider$$ExternalSyntheticLambda0(handler),
                        new BiometricPrompt
                                .AuthenticationCallback() { // from class:
                                                            // com.samsung.android.settings.privacy.SecurityDashboardOfflineLockSwitchController.1
                            @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                            public final void onAuthenticationError(
                                    int i, CharSequence charSequence) {
                                SecurityDashboardOfflineLockSwitchController.this.mPreference
                                        .setChecked(true);
                            }

                            @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                            public final void onAuthenticationSucceeded(
                                    BiometricPrompt.AuthenticationResult authenticationResult) {
                                Settings.Secure.putInt(
                                        SecurityDashboardOfflineLockSwitchController.this.mContext
                                                .getContentResolver(),
                                        "offline_device_lock_setting",
                                        0);
                            }
                        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$authenticate$1(DialogInterface dialogInterface, int i) {
        this.mPreference.setChecked(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$displayPreference$0(Preference preference, Object obj) {
        LoggingHelper.insertEventLogging(54101, 54202, this.mPreference.isChecked() ? 0L : 1L);
        if (this.mPreference.isChecked()) {
            authenticate();
        } else {
            Settings.Secure.putInt(
                    this.mContext.getContentResolver(), "offline_device_lock_setting", 1);
            if (!SecurityDashboardUtils.isScreenLockEnabled(this.mContext)) {
                SecurityDashboardUtils.launchChooseLockScreen(this.mContext);
            }
        }
        return true;
    }

    private void updateParameters() {
        Context context = this.mContext;
        Uri uri = SecurityDashboardUtils.FMM_SUPPORT_URI;
        this.mIsOfflineLockOnBoarded =
                context.getSharedPreferences("key_theft_protection_preference", 0)
                        .getBoolean("key_offline_lock_on_boarding_status", false);
        this.mIsScreenLockSet = SecurityDashboardUtils.isScreenLockEnabled(this.mContext);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecSwitchPreference secSwitchPreference =
                (SecSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = secSwitchPreference;
        secSwitchPreference.setOnPreferenceChangeListener(
                new Preference
                        .OnPreferenceChangeListener() { // from class:
                                                        // com.samsung.android.settings.privacy.SecurityDashboardOfflineLockSwitchController$$ExternalSyntheticLambda0
                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        boolean lambda$displayPreference$0;
                        lambda$displayPreference$0 =
                                SecurityDashboardOfflineLockSwitchController.this
                                        .lambda$displayPreference$0(preference, obj);
                        return lambda$displayPreference$0;
                    }
                });
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!this.mIsOfflineLockSupported) {
            return 3;
        }
        updateParameters();
        boolean z =
                Settings.Secure.getInt(
                                this.mContext.getContentResolver(),
                                "offline_device_lock_setting",
                                0)
                        != 0;
        boolean z2 = this.mIsOfflineLockOnBoarded;
        return ((!z2 || z) && !(z2 && this.mIsScreenLockSet)) ? 2 : 0;
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

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        return super.handlePreferenceTreeClick(preference);
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
        return Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "offline_device_lock_setting", 0)
                != 0;
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
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public void updateNonIndexableKeys(List<String> list) {
        if (getAvailabilityStatus() != 0) {
            list.add(getPreferenceKey());
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
