package com.samsung.android.settings.notification;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecDropDownPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NotificationSortController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    private static final String NOTIFICATION_SORT_DB_KEY = "notification_sort_order";
    private static final int SORT_BY_PRIORITY = 0;
    private static final int SORT_BY_TIME = 1;
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass1();
    private Context mContext;
    private int mCurrentSort;
    private CharSequence[] mNotifSortType;
    private SecDropDownPreference mPreference;
    private String mPreferenceKey;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.NotificationSortController$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            StatusData statusData;
            StatusData statusData2;
            ArrayList arrayList = new ArrayList();
            int intForUser =
                    Settings.System.getIntForUser(
                            context.getContentResolver(),
                            NotificationSortController.NOTIFICATION_SORT_DB_KEY,
                            1,
                            -2);
            if (intForUser < 0) {
                intForUser = 0;
            }
            if (intForUser == 0) {
                String valueOf = String.valueOf(36115);
                statusData = new StatusData();
                statusData.mStatusValue = "1";
                statusData.mStatusKey = valueOf;
            } else {
                if (intForUser != 1) {
                    statusData2 = null;
                    arrayList.add(statusData2);
                    return arrayList;
                }
                String valueOf2 = String.valueOf(36115);
                statusData = new StatusData();
                statusData.mStatusValue = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
                statusData.mStatusKey = valueOf2;
            }
            statusData2 = statusData;
            arrayList.add(statusData2);
            return arrayList;
        }
    }

    public NotificationSortController(Context context, String str) {
        super(context, str);
        this.mCurrentSort = 1;
        this.mContext = context;
        this.mPreferenceKey = str;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecDropDownPreference secDropDownPreference =
                (SecDropDownPreference) preferenceScreen.findPreference(this.mPreferenceKey);
        this.mPreference = secDropDownPreference;
        if (secDropDownPreference != null) {
            if (this.mNotifSortType == null) {
                this.mNotifSortType =
                        this.mContext.getResources().getStringArray(R.array.notification_sort);
            }
            this.mPreference.setEntries(this.mNotifSortType);
            this.mPreference.mEntryValues =
                    new CharSequence[] {"1", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN};
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

    @Override // com.android.settings.core.BasePreferenceController
    public int getMetricsCategory() {
        return 36115;
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
        String string =
                this.mContext.getString(R.string.sec_notification_sort_notification_sort_by_time);
        int i = this.mCurrentSort;
        return i != 0
                ? i != 1
                        ? string
                        : this.mContext.getString(
                                R.string.sec_notification_sort_notification_sort_by_time)
                : this.mContext.getString(
                        R.string.sec_notification_sort_notification_sort_by_priority);
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
        this.mCurrentSort = Integer.parseInt((String) obj);
        LoggingHelper.insertEventLogging(
                getMetricsCategory(), 36115, this.mCurrentSort == 0 ? 0L : 1L);
        Settings.System.putIntForUser(
                this.mContext.getContentResolver(),
                NOTIFICATION_SORT_DB_KEY,
                this.mCurrentSort,
                -2);
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
        int intForUser =
                Settings.System.getIntForUser(
                        this.mContext.getContentResolver(), NOTIFICATION_SORT_DB_KEY, 1, -2);
        this.mCurrentSort = intForUser;
        if (intForUser < 0 || intForUser >= this.mNotifSortType.length) {
            this.mCurrentSort = 1;
        }
        SecDropDownPreference secDropDownPreference = this.mPreference;
        if (secDropDownPreference != null) {
            int i = this.mCurrentSort;
            if (i == 0) {
                secDropDownPreference.setValueIndex(1);
            } else if (i == 1) {
                secDropDownPreference.setValueIndex(0);
            }
        }
        refreshSummary(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
