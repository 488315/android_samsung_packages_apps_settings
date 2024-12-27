package com.samsung.android.settings.lockscreen.controller;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.RestrictedListPreference;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.lockscreen.SecConceptControllerBehavior;
import com.samsung.android.settings.logging.SALogging;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ShowOnLockScreenNotificationPreferenceController extends BasePreferenceController
        implements PreferenceControllerMixin,
                Preference.OnPreferenceChangeListener,
                SecConceptControllerBehavior {
    private static final String TAG = "LockScreenNotifPref";
    private DevicePolicyManager mDpm;
    LockPatternUtils mLockPatternUtils;
    private RestrictedListPreference mPreference;
    private final String mSettingKey;

    public ShowOnLockScreenNotificationPreferenceController(Context context, String str) {
        super(context, str);
        this.mSettingKey = str;
        this.mDpm = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        this.mLockPatternUtils = new LockPatternUtils(context);
    }

    private boolean adminAllowsNotifications() {
        return (this.mDpm.getKeyguardDisabledFeatures(null) & 4) == 0;
    }

    private boolean getLockscreenNotificationsEnabled() {
        return Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "lock_screen_show_notifications", 0)
                != 0;
    }

    private boolean getLockscreenSilentNotificationsEnabled() {
        return Settings.Secure.getInt(
                        this.mContext.getContentResolver(),
                        "lock_screen_show_silent_notifications",
                        1)
                != 0;
    }

    private void setRestrictedIfNotificationFeaturesDisabled(
            RestrictedListPreference restrictedListPreference,
            CharSequence charSequence,
            CharSequence charSequence2,
            int i) {
        RestrictedLockUtils.EnforcedAdmin checkIfKeyguardFeaturesDisabled =
                RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(
                        this.mContext, i, UserHandle.myUserId());
        if (checkIfKeyguardFeaturesDisabled == null || restrictedListPreference == null) {
            return;
        }
        ((ArrayList) restrictedListPreference.mRestrictedItems)
                .add(
                        new RestrictedListPreference.RestrictedItem(
                                charSequence, charSequence2, checkIfKeyguardFeaturesDisabled));
    }

    @Override // com.samsung.android.settings.lockscreen.SecConceptControllerBehavior
    public void accept(String str, Object obj) {
        str.getClass();
        if (str.equals("lock_screen_show_notifications")) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            RestrictedListPreference restrictedListPreference = this.mPreference;
            if (restrictedListPreference != null) {
                restrictedListPreference.setEnabled(booleanValue);
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (RestrictedListPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return !this.mLockPatternUtils.isLockScreenDisabled(UserHandle.semGetMyUserId()) ? 0 : 2;
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
        return this.mSettingKey;
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
        if (adminAllowsNotifications()) {
            getLockscreenNotificationsEnabled();
        }
        return !getLockscreenSilentNotificationsEnabled()
                ? this.mContext.getString(R.string.lock_screen_notifs_show_alerting_option)
                : this.mContext.getString(R.string.lock_screen_notifs_show_all_option);
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

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        int i =
                Integer.parseInt((String) obj) == R.string.lock_screen_notifs_show_all_option
                        ? 1
                        : 0;
        Settings.Secure.putInt(
                this.mContext.getContentResolver(), "lock_screen_show_silent_notifications", i);
        SALogging.insertSALog(
                String.valueOf(4435),
                "4474",
                i != 0
                        ? this.mContext.getString(
                                R.string.lock_screen_notifs_show_all_event_logging)
                        : this.mContext.getString(
                                R.string.lock_screen_notifs_show_alerting_event_logging));
        refreshSummary(preference);
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setDpm(DevicePolicyManager devicePolicyManager) {
        this.mDpm = devicePolicyManager;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        RestrictedListPreference restrictedListPreference = (RestrictedListPreference) preference;
        this.mPreference = restrictedListPreference;
        ((ArrayList) restrictedListPreference.mRestrictedItems).clear();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        String string = this.mContext.getString(R.string.lock_screen_notifs_show_all_option);
        String num = Integer.toString(R.string.lock_screen_notifs_show_all_option);
        arrayList.add(string);
        arrayList2.add(num);
        setRestrictedIfNotificationFeaturesDisabled(restrictedListPreference, string, num, 4);
        String string2 = this.mContext.getString(R.string.lock_screen_notifs_show_alerting_option);
        String num2 = Integer.toString(R.string.lock_screen_notifs_show_alerting_option);
        arrayList.add(string2);
        arrayList2.add(num2);
        setRestrictedIfNotificationFeaturesDisabled(restrictedListPreference, string2, num2, 4);
        restrictedListPreference.mEntries =
                (CharSequence[]) arrayList.toArray(new CharSequence[arrayList.size()]);
        restrictedListPreference.mEntryValues =
                (CharSequence[]) arrayList2.toArray(new CharSequence[arrayList2.size()]);
        if (adminAllowsNotifications()) {
            getLockscreenNotificationsEnabled();
        }
        if (getLockscreenSilentNotificationsEnabled()) {
            restrictedListPreference.setValue(
                    Integer.toString(R.string.lock_screen_notifs_show_all_option));
        } else {
            restrictedListPreference.setValue(
                    Integer.toString(R.string.lock_screen_notifs_show_alerting_option));
        }
        restrictedListPreference.setOnPreferenceChangeListener(this);
        refreshSummary(preference);
        SecPreferenceUtils.applySummaryColor(restrictedListPreference, true);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // com.samsung.android.settings.lockscreen.SecConceptControllerBehavior
    public /* bridge */ /* synthetic */ void updateConfigurationChanged(
            Configuration configuration) {}
}
