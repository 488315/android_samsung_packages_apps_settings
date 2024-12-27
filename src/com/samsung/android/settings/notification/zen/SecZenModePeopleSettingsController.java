package com.samsung.android.settings.notification.zen;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecDropDownPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.notification.zen.ZenModePeopleSettings;
import com.android.settings.notification.zen.ZenModeSettingsBase;

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
public class SecZenModePeopleSettingsController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    private static final int ALLOW_ALL_EXCEPT_SELECTED_CONTACTS = 1;
    private static final int ALLOW_ONLY_SELECTED_CONTACTS = 0;
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass1();
    public static final String ZEN_MODE_PEOPLE_SETTINGS_DB_KEY =
            "zen_selected_exception_contacts_allowed";
    private Context mContext;
    private int mCurrentAllowState;
    private boolean mIsFromDnd;
    private SecDropDownPreference mPreference;
    private String mPreferenceKey;
    private final BixbyRoutineActionHandler mRSHandler;
    private CharSequence[] mZenModePeopleSettingsMenu;
    private ZenModeSettingsBase.ZenModeDependentListener modeDependentListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.zen.SecZenModePeopleSettingsController$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            StatusData statusData;
            StatusData statusData2;
            ArrayList arrayList = new ArrayList();
            int i =
                    Settings.Global.getInt(
                            context.getContentResolver(),
                            "zen_selected_exception_contacts_allowed",
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

    public SecZenModePeopleSettingsController(Context context, String str, boolean z) {
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
            if (this.mZenModePeopleSettingsMenu == null) {
                this.mZenModePeopleSettingsMenu =
                        this.mContext
                                .getResources()
                                .getStringArray(R.array.sec_zen_mode_people_settings_dropdown_menu);
            }
            this.mPreference.setEntries(this.mZenModePeopleSettingsMenu);
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
                                R.string
                                        .sec_zen_mode_people_settings_allow_all_except_selected_contacts)
                : this.mContext.getString(
                        R.string.sec_zen_mode_people_settings_allow_only_selected_contacts);
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
                    "zen_selected_exception_contacts_allowed",
                    this.mCurrentAllowState);
            Context context = this.mContext;
            try {
                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(NotificationManager.class);
                NotificationManager.Policy notificationPolicy =
                        notificationManager.getNotificationPolicy();
                notificationManager.setNotificationPolicy(
                        new NotificationManager.Policy(
                                notificationPolicy.priorityCategories,
                                notificationPolicy.priorityCallSenders,
                                notificationPolicy.priorityMessageSenders,
                                notificationPolicy.suppressedVisualEffects,
                                notificationPolicy.state,
                                notificationPolicy.priorityConversationSenders,
                                Settings.Global.getInt(
                                        context.getContentResolver(),
                                        "zen_selected_exception_contacts_allowed",
                                        0),
                                notificationPolicy.getExceptionContacts(),
                                Settings.Global.getInt(
                                        context.getContentResolver(),
                                        SecZenModeBypassingAppsController
                                                .ZEN_MODE_BYPASSING_APPS_DB_KEY,
                                        0),
                                notificationPolicy.getAppBypassDndList()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            BixbyRoutineActionHandler.setContactFlag(this.mCurrentAllowState);
        }
        ZenModePeopleSettings zenModePeopleSettings =
                (ZenModePeopleSettings) this.modeDependentListener;
        ZenModeAllowAlsoController zenModeAllowAlsoController =
                zenModePeopleSettings.mZenModeAllowAlsoController;
        PreferenceScreen preferenceScreen = zenModePeopleSettings.getPreferenceScreen();
        if (zenModeAllowAlsoController.isAvailable()) {
            String preferenceKey = zenModeAllowAlsoController.getPreferenceKey();
            Preference findPreference = preferenceScreen.findPreference(preferenceKey);
            if (findPreference == null) {
                Log.d(
                        "ZenModeSettings",
                        "Cannot find preference with key "
                                + preferenceKey
                                + " in Controller "
                                + zenModeAllowAlsoController.getClass().getSimpleName());
            } else {
                zenModeAllowAlsoController.updateState(findPreference);
            }
        }
        refreshSummary(preference);
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setModeDependentListener(
            ZenModeSettingsBase.ZenModeDependentListener zenModeDependentListener) {
        this.modeDependentListener = zenModeDependentListener;
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
                            this.mContext.getContentResolver(),
                            "zen_selected_exception_contacts_allowed",
                            0);
        } else {
            this.mCurrentAllowState = (int) BixbyRoutineActionHandler.getContactFlag();
        }
        int i = this.mCurrentAllowState;
        if (i < 0 || i >= this.mZenModePeopleSettingsMenu.length) {
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
