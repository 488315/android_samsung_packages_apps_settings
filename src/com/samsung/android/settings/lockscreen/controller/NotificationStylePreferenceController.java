package com.samsung.android.settings.lockscreen.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.core.SecSingleChoicePreferenceController;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.lockscreen.LockScreenNotificationSettings;
import com.samsung.android.settings.lockscreen.SecConceptBehavior;
import com.samsung.android.settings.lockscreen.SecConceptControllerBehavior;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.widget.SecHorizontalRadioPreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NotificationStylePreferenceController extends SecSingleChoicePreferenceController
        implements SecConceptControllerBehavior {
    private static final String DB_NAME = "lockscreen_minimizing_notification";
    private static final String NOTIFICATION_DETAILS = "1";
    static final String NOTIFICATION_DETAILS_KEY = "notification_details";
    private static final String NOTIFICATION_ICONS_ONLY = "0";
    static final String NOTIFICATION_ICONS_ONLY_KEY = "notification_icons_only";
    private SecConceptBehavior mContextDashBoard;
    private String mDetailStyleOptionEventLogging;
    private String mIconStyleOptionEventLogging;
    private LockPatternUtils mLockPatternUtils;
    private SecHorizontalRadioPreference mPreference;

    public NotificationStylePreferenceController(
            Context context, SecConceptBehavior secConceptBehavior) {
        super(context, NOTIFICATION_ICONS_ONLY_KEY);
        this.mContextDashBoard = secConceptBehavior;
        this.mLockPatternUtils = new LockPatternUtils(context);
    }

    @Override // com.samsung.android.settings.lockscreen.SecConceptControllerBehavior
    public void accept(String str, Object obj) {
        str.getClass();
        if (str.equals("lock_screen_show_notifications")) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            if (KnoxUtils.isApplicationRestricted(
                    this.mContext, NOTIFICATION_ICONS_ONLY_KEY, "grayout")) {
                booleanValue = false;
            }
            SecHorizontalRadioPreference secHorizontalRadioPreference = this.mPreference;
            if (secHorizontalRadioPreference != null) {
                secHorizontalRadioPreference.setEnabled(booleanValue);
                this.mPreference.setEntryEnabled("0", booleanValue);
                this.mPreference.setEntryEnabled("1", booleanValue);
            }
        }
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecHorizontalRadioPreference secHorizontalRadioPreference =
                (SecHorizontalRadioPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = secHorizontalRadioPreference;
        secHorizontalRadioPreference.mIsDividerEnabled = false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return !this.mLockPatternUtils.isLockScreenDisabled(UserHandle.semGetMyUserId()) ? 0 : 2;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<String> getEntries() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(this.mContext.getString(R.string.sec_lockscreen_view_style_icon_option));
        arrayList.add(this.mContext.getString(R.string.sec_lockscreen_view_style_card_option));
        return arrayList;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<String> getEntryValues() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("0");
        arrayList.add("1");
        return arrayList;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<Integer> getImageEntries() {
        return new ArrayList<>();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public String getSelectedValue() {
        return Settings.System.getInt(this.mContext.getContentResolver(), DB_NAME, 1) == 1
                ? "0"
                : "1";
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<String> getSubEntries() {
        return null;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public boolean setSelectedValue(String str) {
        boolean equals = "0".equals(str);
        Settings.System.putIntForUser(
                this.mContext.getContentResolver(), DB_NAME, equals ? 1 : 0, -2);
        ((LockScreenNotificationSettings) this.mContextDashBoard)
                .notifyChange(Boolean.valueOf(equals), NOTIFICATION_DETAILS_KEY);
        boolean z = ((LockScreenNotificationSettings) this.mContextDashBoard).mNeedRedaction;
        SALogging.insertSALog(
                String.valueOf(4435),
                String.valueOf(4464),
                equals ? this.mIconStyleOptionEventLogging : this.mDetailStyleOptionEventLogging);
        return true;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (this.mIconStyleOptionEventLogging == null) {
            this.mIconStyleOptionEventLogging =
                    this.mContext.getResources().getString(R.string.sec_lockscreen_view_style_icon);
        }
        if (this.mDetailStyleOptionEventLogging == null) {
            this.mDetailStyleOptionEventLogging =
                    this.mContext
                            .getResources()
                            .getString(R.string.sec_lockscreen_view_style_card_option);
        }
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // com.samsung.android.settings.lockscreen.SecConceptControllerBehavior
    public /* bridge */ /* synthetic */ void updateConfigurationChanged(
            Configuration configuration) {}
}
