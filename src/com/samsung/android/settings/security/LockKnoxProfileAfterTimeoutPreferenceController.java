package com.samsung.android.settings.security;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.DisplaySettings$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.RestrictedPreference;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.KnoxUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LockKnoxProfileAfterTimeoutPreferenceController extends BasePreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    private static final String KEY_LOCK_KNOX_PROFILE_AFTER_TIMEOUT =
            "lock_knox_profile_after_timeout";
    private static final String TAG = "KKG::LockKnoxProfileAfterTimeoutPreferenceController";
    private final DevicePolicyManager mDevicePolicyManager;
    private final SettingsPreferenceFragment mHost;
    private final LockPatternUtils mLockPatternUtils;
    protected RestrictedPreference mPreference;
    private final int mProfileChallengeUserId;
    private final int mUserId;

    public LockKnoxProfileAfterTimeoutPreferenceController(
            Context context, SettingsPreferenceFragment settingsPreferenceFragment) {
        this(context, settingsPreferenceFragment, KEY_LOCK_KNOX_PROFILE_AFTER_TIMEOUT);
    }

    private int getIndexFromValue(CharSequence[] charSequenceArr, long j) {
        for (int i = 0; i < charSequenceArr.length; i++) {
            if (charSequenceArr[i].equals(String.valueOf(j))) {
                return i;
            }
        }
        return charSequenceArr.length - 1;
    }

    private boolean startTimeoutKnoxProfileActivity() {
        Intent m =
                DisplaySettings$$ExternalSyntheticOutline0.m(
                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                        "com.samsung.android.settings.security.LockKnoxProfileAfterTimeoutActivity");
        m.putExtra("android.intent.extra.USER_ID", this.mProfileChallengeUserId);
        try {
            this.mContext.startActivity(m);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        RestrictedPreference restrictedPreference =
                (RestrictedPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = restrictedPreference;
        if (restrictedPreference != null) {
            restrictedPreference.setTitle(
                    this.mContext.getString(R.string.knox_security_timeout_custom));
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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
        return KEY_LOCK_KNOX_PROFILE_AFTER_TIMEOUT;
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
        if (KnoxUtils.isAvailableWithMultiWindowForKnox(
                this.mHost.getActivity(), this.mProfileChallengeUserId)) {
            return preference.getKey().equals(getPreferenceKey())
                    ? startTimeoutKnoxProfileActivity()
                    : super.handlePreferenceTreeClick(preference);
        }
        Toast.makeText(
                        this.mHost.getActivity(),
                        this.mContext.getString(
                                R.string.lock_screen_doesnt_support_multi_window_text),
                        0)
                .show();
        return false;
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

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        try {
            Settings.Secure.putInt(
                    this.mContext.getContentResolver(),
                    "lock_screen_lock_after_timeout",
                    Integer.parseInt((String) obj));
            updateState(preference);
            return true;
        } catch (NumberFormatException e) {
            Log.e(TAG, "could not persist lockAfter timeout setting", e);
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        updateSummary(preference);
        if (!this.mLockPatternUtils.isSeparateProfileChallengeEnabled(
                this.mProfileChallengeUserId)) {
            this.mPreference.setSummary(
                    this.mContext.getString(R.string.knox_onelock_settings_lock_summary));
            this.mPreference.setEnabled(false);
        } else if (!this.mLockPatternUtils.isSecure(this.mProfileChallengeUserId)) {
            this.mPreference.setEnabled(false);
        }
        preference.seslSetSummaryColor(
                this.mPreference.isEnabled()
                        ? this.mContext.getResources().getColor(R.color.basic_primary_color)
                        : this.mContext
                                .getResources()
                                .getColor(R.color.basic_secondary_text_color_dimmed));
    }

    public void updateSummary(Preference preference) {
        long longForUser =
                Settings.Secure.getLongForUser(
                        this.mContext.getContentResolver(),
                        "knox_screen_off_timeout",
                        0L,
                        this.mProfileChallengeUserId);
        DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
        long maximumTimeToLock =
                devicePolicyManager != null
                        ? devicePolicyManager.getMaximumTimeToLock(
                                null, this.mProfileChallengeUserId)
                        : 0L;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        LockKnoxProfileAfterTimeoutActivity.setScreenTimeoutAdapter(
                this.mContext, longForUser, maximumTimeToLock, arrayList, arrayList2);
        preference.setSummary(
                ((CharSequence)
                                arrayList.get(
                                        getIndexFromValue(
                                                (CharSequence[])
                                                        arrayList2.toArray(new CharSequence[0]),
                                                longForUser)))
                        .toString());
        this.mPreference.setEnabled(true);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public LockKnoxProfileAfterTimeoutPreferenceController(
            Context context, SettingsPreferenceFragment settingsPreferenceFragment, String str) {
        super(context, str);
        UserManager userManager = (UserManager) context.getSystemService("user");
        this.mLockPatternUtils = new LockPatternUtils(this.mContext);
        this.mDevicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
        int myUserId = UserHandle.myUserId();
        this.mUserId = myUserId;
        this.mProfileChallengeUserId = Utils.getManagedProfileId(userManager, myUserId);
        this.mHost = settingsPreferenceFragment;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
