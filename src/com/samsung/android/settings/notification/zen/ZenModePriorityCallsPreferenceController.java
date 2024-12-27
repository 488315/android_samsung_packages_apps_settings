package com.samsung.android.settings.notification.zen;

import android.content.Context;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecDropDownPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.notification.zen.AbstractZenModePreferenceController;
import com.android.settings.notification.zen.ZenModeBackend;
import com.android.settings.notification.zen.ZenModeRepeatCallersPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ZenModePriorityCallsPreferenceController
        extends AbstractZenModePreferenceController
        implements Preference.OnPreferenceChangeListener {
    public final boolean mIsFromDnd;
    public final String[] mListValues;
    public SecDropDownPreference mPreference;
    public final BixbyRoutineActionHandler mRSHandler;

    public ZenModePriorityCallsPreferenceController(
            Context context, Lifecycle lifecycle, boolean z) {
        super(context, "zen_mode_calls", lifecycle);
        this.mListValues = context.getResources().getStringArray(R.array.zen_mode_contacts_values);
        BixbyRoutineActionHandler bixbyRoutineActionHandler =
                BixbyRoutineActionHandler.getInstance();
        this.mRSHandler = bixbyRoutineActionHandler;
        bixbyRoutineActionHandler.getClass();
        this.mIsFromDnd = z;
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecDropDownPreference) preferenceScreen.findPreference("zen_mode_calls");
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "zen_mode_calls";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (this.mIsFromDnd) {
            ZenModeBackend.getInstance(this.mContext)
                    .saveSenders(8, ZenModeBackend.getSettingFromPrefKey(obj.toString()));
        } else {
            BixbyRoutineActionHandler.setCallOption(
                    ZenModeBackend.getSettingFromPrefKey(obj.toString()));
        }
        updateFromContactsValue$1(preference);
        return true;
    }

    public final void updateFromContactsValue$1(Preference preference) {
        int i;
        int callOption;
        String[] strArr;
        SecDropDownPreference secDropDownPreference = (SecDropDownPreference) preference;
        this.mPreference = secDropDownPreference;
        secDropDownPreference.getClass();
        SecPreferenceUtils.applySummaryColor(secDropDownPreference, true);
        int zenMode = getZenMode();
        int i2 = 0;
        if (zenMode != 2) {
            int i3 = 3;
            if (zenMode != 3) {
                preference.setEnabled(true);
                boolean z = this.mIsFromDnd;
                BixbyRoutineActionHandler bixbyRoutineActionHandler = this.mRSHandler;
                if (z) {
                    i = ZenModeBackend.getInstance(this.mContext).getContactsSummary(8);
                } else {
                    bixbyRoutineActionHandler.getClass();
                    int callOption2 = (int) BixbyRoutineActionHandler.getCallOption();
                    i =
                            callOption2 != 0
                                    ? callOption2 != 1
                                            ? callOption2 != 2
                                                    ? R.string.zen_mode_from_none_calls
                                                    : R.string.zen_mode_from_starred
                                            : R.string.zen_mode_from_contacts
                                    : R.string.zen_mode_from_anyone;
                }
                preference.setSummary(i);
                if (z) {
                    callOption = ZenModeBackend.getInstance(this.mContext).getPriorityCallSenders();
                } else {
                    bixbyRoutineActionHandler.getClass();
                    callOption = (int) BixbyRoutineActionHandler.getCallOption();
                }
                String keyFromSetting = ZenModeBackend.getKeyFromSetting(callOption);
                SecDropDownPreference secDropDownPreference2 = this.mPreference;
                while (true) {
                    strArr = this.mListValues;
                    if (i2 >= strArr.length) {
                        break;
                    }
                    if (TextUtils.equals(keyFromSetting, strArr[i2])) {
                        i3 = i2;
                        break;
                    }
                    i2++;
                }
                secDropDownPreference2.setValue(strArr[i3]);
                ZenModeRepeatCallersPreferenceController zenModeRepeatCallersPreferenceController =
                        bixbyRoutineActionHandler.zenModeRepeatCallersPreferenceController;
                Preference preference2 = zenModeRepeatCallersPreferenceController.mPreference;
                if (preference2 != null) {
                    zenModeRepeatCallersPreferenceController.updateState(preference2);
                }
                BixbyRoutineActionHandler.setPeoplesummary(this.mContext);
                return;
            }
        }
        this.mPreference.setEnabled(false);
        this.mPreference.setValue(ZenModeBackend.ZEN_MODE_FROM_NONE);
        SecDropDownPreference secDropDownPreference3 = this.mPreference;
        ZenModeBackend.getInstance(this.mContext).getClass();
        secDropDownPreference3.setSummary(R.string.zen_mode_none_calls);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        updateFromContactsValue$1(preference);
    }
}
