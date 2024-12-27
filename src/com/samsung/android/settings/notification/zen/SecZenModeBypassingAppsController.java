package com.samsung.android.settings.notification.zen;

import android.app.NotificationManager;
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
import com.android.settings.notification.zen.ZenModeBackend;

import com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler;
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
public class SecZenModeBypassingAppsController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    private static final int ALLOW_ALL_EXCEPT_SELECTED_CONTACTS = 1;
    private static final int ALLOW_ONLY_SELECTED_CONTACTS = 0;
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass1();
    public static final String ZEN_MODE_BYPASSING_APPS_DB_KEY =
            "zen_selected_apps_bypass_dnd_allowed";
    private Context mContext;
    private int mCurrentAllowState;
    private boolean mIsFromDnd;
    private SecDropDownPreference mPreference;
    private String mPreferenceKey;
    private final BixbyRoutineActionHandler mRSHandler;
    private CharSequence[] mZenModeBypassingAppsMenu;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.zen.SecZenModeBypassingAppsController$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            StatusData statusData;
            StatusData statusData2;
            ArrayList arrayList = new ArrayList();
            int i =
                    Settings.Global.getInt(
                            context.getContentResolver(),
                            SecZenModeBypassingAppsController.ZEN_MODE_BYPASSING_APPS_DB_KEY,
                            0);
            int i2 = i >= 0 ? i : 0;
            if (i2 == 0) {
                String valueOf = String.valueOf(36115);
                statusData = new StatusData();
                statusData.mStatusValue = "1";
                statusData.mStatusKey = valueOf;
            } else {
                if (i2 != 1) {
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

    public SecZenModeBypassingAppsController(Context context, String str, boolean z) {
        super(context, str);
        this.mCurrentAllowState = 0;
        this.mContext = context;
        this.mPreferenceKey = str;
        this.mIsFromDnd = z;
        this.mRSHandler = BixbyRoutineActionHandler.getInstance();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecDropDownPreference secDropDownPreference =
                (SecDropDownPreference) preferenceScreen.findPreference(this.mPreferenceKey);
        this.mPreference = secDropDownPreference;
        if (secDropDownPreference != null) {
            if (this.mZenModeBypassingAppsMenu == null) {
                this.mZenModeBypassingAppsMenu =
                        this.mContext
                                .getResources()
                                .getStringArray(R.array.sec_zen_mode_bypassing_apps_dropdown_menu);
            }
            this.mPreference.setEntries(this.mZenModeBypassingAppsMenu);
            this.mPreference.mEntryValues =
                    new CharSequence[] {DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, "1"};
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
        int i = this.mCurrentAllowState;
        return i != 0
                ? i != 1
                        ? string
                        : this.mContext.getString(
                                R.string.sec_zen_mode_bypassing_apps_allow_all_except_selected_apps)
                : this.mContext.getString(
                        R.string.sec_zen_mode_bypassing_apps_allow_only_selected_apps);
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
        this.mCurrentAllowState = Integer.parseInt((String) obj);
        LoggingHelper.insertEventLogging(
                getMetricsCategory(), 36115, this.mCurrentAllowState == 0 ? 0L : 1L);
        if (this.mIsFromDnd) {
            Settings.Global.putInt(
                    this.mContext.getContentResolver(),
                    ZEN_MODE_BYPASSING_APPS_DB_KEY,
                    this.mCurrentAllowState);
            ZenModeBackend zenModeBackend = ZenModeBackend.getInstance(this.mContext);
            int i = this.mCurrentAllowState;
            NotificationManager.Policy policy = zenModeBackend.mPolicy;
            zenModeBackend.savePolicyWithExceptions(
                    policy.priorityCategories,
                    policy.priorityCallSenders,
                    policy.priorityMessageSenders,
                    policy.suppressedVisualEffects,
                    policy.state,
                    policy.priorityConversationSenders,
                    policy.exceptionContactsFlag,
                    zenModeBackend
                            .mNotificationManager
                            .getNotificationPolicy()
                            .getExceptionContacts(),
                    i,
                    zenModeBackend
                            .mNotificationManager
                            .getNotificationPolicy()
                            .getAppBypassDndList());
        } else {
            BixbyRoutineActionHandler.setAppFlag(this.mCurrentAllowState);
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
        if (this.mIsFromDnd) {
            this.mCurrentAllowState =
                    Settings.Global.getInt(
                            this.mContext.getContentResolver(), ZEN_MODE_BYPASSING_APPS_DB_KEY, 0);
        } else {
            this.mCurrentAllowState = (int) BixbyRoutineActionHandler.getAppFlag();
        }
        int i = this.mCurrentAllowState;
        if (i < 0 || i >= this.mZenModeBypassingAppsMenu.length) {
            this.mCurrentAllowState = 0;
        }
        SecDropDownPreference secDropDownPreference = this.mPreference;
        if (secDropDownPreference != null) {
            int i2 = this.mCurrentAllowState;
            if (i2 == 0) {
                secDropDownPreference.setValueIndex(0);
            } else if (i2 == 1) {
                secDropDownPreference.setValueIndex(1);
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
