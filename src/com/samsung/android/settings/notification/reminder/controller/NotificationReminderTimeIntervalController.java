package com.samsung.android.settings.notification.reminder.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecDropDownPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NotificationReminderTimeIntervalController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass1();
    private static final int THREE_MINUTES_ICON = 0;
    public static final int TIME_VALUE_DEFAULT = 180;
    int index;
    private Context mContext;
    private int mCurrentIconStyle;
    private CharSequence[] mNotifIconType;
    private SecDropDownPreference mPreference;
    private String mPreferenceKey;
    private CharSequence[] mReminderIntervalType;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.reminder.controller.NotificationReminderTimeIntervalController$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            StatusData statusData;
            StatusData statusData2;
            ArrayList arrayList = new ArrayList();
            int i = Settings.System.getInt(context.getContentResolver(), "time_key", 180);
            if (i < 0) {
                i = 180;
            }
            if (i == 180) {
                String valueOf = String.valueOf(36416);
                statusData = new StatusData();
                statusData.mStatusValue = "3 minutes";
                statusData.mStatusKey = valueOf;
            } else if (i == 300) {
                String valueOf2 = String.valueOf(36416);
                statusData = new StatusData();
                statusData.mStatusValue = "5 minutes";
                statusData.mStatusKey = valueOf2;
            } else if (i == 600) {
                String valueOf3 = String.valueOf(36416);
                statusData = new StatusData();
                statusData.mStatusValue = "10 minutes";
                statusData.mStatusKey = valueOf3;
            } else {
                if (i != 900) {
                    statusData2 = null;
                    arrayList.add(statusData2);
                    return arrayList;
                }
                String valueOf4 = String.valueOf(36416);
                statusData = new StatusData();
                statusData.mStatusValue = "15 minutes";
                statusData.mStatusKey = valueOf4;
            }
            statusData2 = statusData;
            arrayList.add(statusData2);
            return arrayList;
        }
    }

    public NotificationReminderTimeIntervalController(Context context, String str) {
        super(context, str);
        this.mCurrentIconStyle = 0;
        this.index = 0;
        this.mContext = context;
        this.mPreferenceKey = str;
    }

    private int getDropDownIndex() {
        this.mCurrentIconStyle =
                Settings.System.getInt(this.mContext.getContentResolver(), "time_key", 180);
        int i = 0;
        while (true) {
            if (i >= this.mReminderIntervalType.length) {
                break;
            }
            if (String.valueOf(this.mCurrentIconStyle).equals(this.mReminderIntervalType[i])) {
                this.index = i;
                break;
            }
            i++;
        }
        return this.index;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecDropDownPreference secDropDownPreference =
                (SecDropDownPreference) preferenceScreen.findPreference(this.mPreferenceKey);
        this.mPreference = secDropDownPreference;
        if (secDropDownPreference != null) {
            if (this.mNotifIconType == null) {
                this.mNotifIconType =
                        this.mContext
                                .getResources()
                                .getStringArray(R.array.time_interval_entries_selectable);
            }
            if (this.mReminderIntervalType == null) {
                this.mReminderIntervalType =
                        this.mContext
                                .getResources()
                                .getStringArray(R.array.time_interval_values_selectable);
            }
            this.mPreference.setEntries(this.mNotifIconType);
            this.mPreference.mEntryValues = this.mReminderIntervalType;
            int dropDownIndex = getDropDownIndex();
            this.index = dropDownIndex;
            this.mPreference.setValueIndex(dropDownIndex);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return Utils.isDesktopModeEnabled(this.mContext) ? 5 : 0;
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

    @Override // com.android.settings.core.BasePreferenceController
    public int getMetricsCategory() {
        return 36046;
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
        SecDropDownPreference secDropDownPreference = this.mPreference;
        if (secDropDownPreference != null) {
            secDropDownPreference.getClass();
            SecPreferenceUtils.applySummaryColor(secDropDownPreference, true);
        }
        this.mContext.getString(R.string.reminder_3_munites);
        return this.mNotifIconType[this.index].toString();
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
        Settings.System.putInt(
                this.mContext.getContentResolver(), "time_key", Integer.parseInt((String) obj));
        if (this.mPreference != null) {
            int dropDownIndex = getDropDownIndex();
            this.index = dropDownIndex;
            this.mPreference.setValueIndex(dropDownIndex);
        }
        refreshSummary(preference);
        return true;
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
        super.updateState(preference);
        if (this.mPreference != null) {
            int dropDownIndex = getDropDownIndex();
            this.index = dropDownIndex;
            this.mPreference.setValueIndex(dropDownIndex);
        }
        refreshSummary(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
