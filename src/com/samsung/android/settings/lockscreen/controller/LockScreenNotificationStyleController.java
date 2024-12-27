package com.samsung.android.settings.lockscreen.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.core.SecSingleChoicePreferenceController;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.lockscreen.SecConceptControllerBehavior;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.notification.SecNotificationHorizontalRadioPreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LockScreenNotificationStyleController extends SecSingleChoicePreferenceController
        implements SecConceptControllerBehavior {
    private static final String CARDS = "0";
    private static final String DB_NAME = "lockscreen_minimizing_notification";
    private static final String DOT = "2";
    private static final String ICONS = "1";
    private static final String NOTIFICATION_ICONS_ONLY_KEY = "notification_icons_only";
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass1();
    private static final String TAG = "StatusBarNotificationStyle";
    LockPatternUtils mLockPatternUtils;
    private SecNotificationHorizontalRadioPreference mPreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.lockscreen.controller.LockScreenNotificationStyleController$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            int intForUser =
                    Settings.System.getIntForUser(
                            context.getContentResolver(),
                            LockScreenNotificationStyleController.DB_NAME,
                            1,
                            -2);
            String valueOf = String.valueOf(36117);
            String valueOf2 = String.valueOf(intForUser);
            StatusData statusData = new StatusData();
            statusData.mStatusValue = valueOf2;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            return arrayList;
        }
    }

    public LockScreenNotificationStyleController(Context context, String str) {
        super(context, str);
        this.mLockPatternUtils = new LockPatternUtils(context);
    }

    @Override // com.samsung.android.settings.lockscreen.SecConceptControllerBehavior
    public void accept(String str, Object obj) {
        if ("lock_screen_show_notifications".equals(str)) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            if (KnoxUtils.isApplicationRestricted(
                    this.mContext, NOTIFICATION_ICONS_ONLY_KEY, "grayout")) {
                booleanValue = false;
            }
            SecNotificationHorizontalRadioPreference secNotificationHorizontalRadioPreference =
                    this.mPreference;
            if (secNotificationHorizontalRadioPreference != null) {
                secNotificationHorizontalRadioPreference.setEnabled(booleanValue);
                this.mPreference.setEntryEnabled("2", booleanValue);
                this.mPreference.setEntryEnabled("1", booleanValue);
                this.mPreference.setEntryEnabled("0", booleanValue);
            }
        }
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecNotificationHorizontalRadioPreference secNotificationHorizontalRadioPreference =
                (SecNotificationHorizontalRadioPreference)
                        preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = secNotificationHorizontalRadioPreference;
        secNotificationHorizontalRadioPreference.mIsDividerEnabled = false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (KnoxUtils.isApplicationRestricted(
                                this.mContext, NOTIFICATION_ICONS_ONLY_KEY, "hide")
                        || this.mLockPatternUtils.isLockScreenDisabled(UserHandle.semGetMyUserId()))
                ? 2
                : 0;
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
        arrayList.add(this.mContext.getString(R.string.notification_style_on_status_bar_dot));
        arrayList.add(this.mContext.getString(R.string.notification_style_on_status_bar_icons));
        arrayList.add(this.mContext.getString(R.string.notification_style_on_lock_screen_card));
        return arrayList;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<String> getEntryValues() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("2");
        arrayList.add("1");
        arrayList.add("0");
        return arrayList;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<Integer> getImageEntries() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(Integer.valueOf(R.drawable.ic_illust_lock_status));
        arrayList.add(Integer.valueOf(R.drawable.ic_illust_lock_icon));
        arrayList.add(Integer.valueOf(R.drawable.ic_illust_lock_card));
        return arrayList;
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
        int intForUser =
                Settings.System.getIntForUser(this.mContext.getContentResolver(), DB_NAME, 1, -2);
        String str = intForUser == 0 ? "0" : intForUser == 1 ? "1" : "2";
        Log.d(TAG, "getSelectedValue: ".concat(str));
        return str;
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

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<Integer> getTitleColorList() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(
                Integer.valueOf(
                        this.mContext.getColor(R.color.sec_noti_stausbar_style_title_color)));
        arrayList.add(
                Integer.valueOf(
                        this.mContext.getColor(R.color.sec_noti_stausbar_style_title_color)));
        arrayList.add(
                Integer.valueOf(
                        this.mContext.getColor(R.color.sec_noti_stausbar_style_title_color)));
        return arrayList;
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
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public boolean setSelectedValue(String str) {
        boolean z;
        int i = 2;
        str.getClass();
        switch (str.hashCode()) {
            case 48:
                if (str.equals("0")) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case 49:
                if (str.equals("1")) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            case 50:
                if (str.equals("2")) {
                    z = 2;
                    break;
                }
                z = -1;
                break;
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                i = 0;
                break;
            case true:
                i = 1;
                break;
            case true:
                break;
            default:
                i = -1;
                break;
        }
        Settings.System.putIntForUser(this.mContext.getContentResolver(), DB_NAME, i, -2);
        Log.d(TAG, "setSelectedValue: " + str + " value: " + i);
        return true;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        SecNotificationHorizontalRadioPreference secNotificationHorizontalRadioPreference =
                (SecNotificationHorizontalRadioPreference) preference;
        secNotificationHorizontalRadioPreference.setEnabled(true);
        secNotificationHorizontalRadioPreference.mIsIconTitleColorEnabled = false;
        secNotificationHorizontalRadioPreference.setEntryEnabled("2", true);
        secNotificationHorizontalRadioPreference.setEntryEnabled("1", true);
        secNotificationHorizontalRadioPreference.setEntryEnabled("0", true);
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
