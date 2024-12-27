package com.android.settings.notification.zen;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.notification.zen.lifestyle.LifeStyleZenFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.notification.app.AppNotificationTypeAdapter;
import com.samsung.android.settings.notification.zen.SecZenModePeopleSettingsController;
import com.samsung.android.settings.notification.zen.ZenModeAllowAlsoController;
import com.samsung.android.settings.notification.zen.ZenModePriorityCallsPreferenceController;
import com.samsung.android.settings.notification.zen.ZenModePriorityMessagesPreferenceController;
import com.samsung.android.settings.widget.SecRecyclerViewPreference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenModePeopleSettings extends ZenModeSettingsBase
        implements ZenModeSettingsBase.ZenModeDependentListener,
                AppNotificationTypeAdapter.ZenModeCallExceptionListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.zen_mode_people_settings);
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass2();
    public static List mContactAllowList;
    public static boolean mIsFromDnd;
    public static BixbyRoutineActionHandler mRSHandler;
    public List mControllers;
    public ZenModeAllowAlsoController mZenModeAllowAlsoController;
    public ZenModeContactExceptionController mZenModeContactExceptionController;
    public final int REQUEST_CODE_OK = 1;
    public final int PHONE_NUMBER = 1;
    public final int CONTACT_ID = 2;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.zen.ZenModePeopleSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return ZenModePeopleSettings.buildPreferenceControllers$3(context, null);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.zen.ZenModePeopleSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            StatusData statusData;
            StatusData statusData2;
            ArrayList arrayList = new ArrayList();
            NotificationManager.Policy notificationPolicy =
                    ((NotificationManager) context.getSystemService(NotificationManager.class))
                            .getNotificationPolicy();
            int i = notificationPolicy.priorityCategories;
            int i2 = notificationPolicy.priorityCallSenders;
            int i3 = i & 8;
            StatusData statusData3 = null;
            if (i3 == 0) {
                String valueOf = String.valueOf(36304);
                statusData = new StatusData();
                statusData.mStatusValue = SignalSeverity.NONE;
                statusData.mStatusKey = valueOf;
            } else if (i2 == 0) {
                String valueOf2 = String.valueOf(36304);
                statusData = new StatusData();
                statusData.mStatusValue = "all";
                statusData.mStatusKey = valueOf2;
            } else if (i2 == 1) {
                String valueOf3 = String.valueOf(36304);
                statusData = new StatusData();
                statusData.mStatusValue = "contacts only";
                statusData.mStatusKey = valueOf3;
            } else if (i2 != 2) {
                statusData = null;
            } else {
                String valueOf4 = String.valueOf(36304);
                statusData = new StatusData();
                statusData.mStatusValue = "favorite contacts only";
                statusData.mStatusKey = valueOf4;
            }
            arrayList.add(statusData);
            if (i3 == 0 || notificationPolicy.priorityCallSenders != 0) {
                String valueOf5 = String.valueOf(36305);
                String valueOf6 =
                        String.valueOf((notificationPolicy.priorityCategories & 16) != 0 ? 1 : 0);
                statusData2 = new StatusData();
                statusData2.mStatusValue = valueOf6;
                statusData2.mStatusKey = valueOf5;
            } else {
                String valueOf7 = String.valueOf(36305);
                String valueOf8 = String.valueOf(1);
                statusData2 = new StatusData();
                statusData2.mStatusValue = valueOf8;
                statusData2.mStatusKey = valueOf7;
            }
            arrayList.add(statusData2);
            int i4 = notificationPolicy.priorityMessageSenders;
            if ((i & 4) == 0) {
                String valueOf9 = String.valueOf(36306);
                statusData3 = new StatusData();
                statusData3.mStatusValue = SignalSeverity.NONE;
                statusData3.mStatusKey = valueOf9;
            } else if (i4 == 0) {
                String valueOf10 = String.valueOf(36306);
                statusData3 = new StatusData();
                statusData3.mStatusValue = "all";
                statusData3.mStatusKey = valueOf10;
            } else if (i4 == 1) {
                String valueOf11 = String.valueOf(36306);
                statusData3 = new StatusData();
                statusData3.mStatusValue = "contacts only";
                statusData3.mStatusKey = valueOf11;
            } else if (i4 == 2) {
                String valueOf12 = String.valueOf(36306);
                statusData3 = new StatusData();
                statusData3.mStatusValue = "favorite contacts only";
                statusData3.mStatusKey = valueOf12;
            }
            arrayList.add(statusData3);
            int size =
                    ZenModeBackend.getInstance(context)
                            .mNotificationManager
                            .getNotificationPolicy()
                            .getExceptionContacts()
                            .size();
            String valueOf13 = String.valueOf(36321);
            String valueOf14 = String.valueOf(size);
            StatusData statusData4 = new StatusData();
            statusData4.mStatusValue = valueOf14;
            statusData4.mStatusKey = valueOf13;
            arrayList.add(statusData4);
            return arrayList;
        }
    }

    public static List buildPreferenceControllers$3(Context context, Lifecycle lifecycle) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ZenModeContactExceptionController(context, lifecycle, mIsFromDnd));
        arrayList.add(
                new ZenModePriorityMessagesPreferenceController(context, lifecycle, mIsFromDnd));
        arrayList.add(new ZenModePriorityCallsPreferenceController(context, lifecycle, mIsFromDnd));
        int integer =
                context.getResources()
                        .getInteger(
                                android.R.integer.leanback_setup_alpha_backward_in_content_delay);
        boolean z = mIsFromDnd;
        ZenModeRepeatCallersPreferenceController zenModeRepeatCallersPreferenceController =
                new ZenModeRepeatCallersPreferenceController(context, lifecycle, integer);
        zenModeRepeatCallersPreferenceController.mIsFromDnd = z;
        arrayList.add(zenModeRepeatCallersPreferenceController);
        arrayList.add(
                new SecZenModePeopleSettingsController(
                        context, "key_sec_zen_mode_people_settings_dropdown_menu", mIsFromDnd));
        arrayList.add(new ZenModeAllowAlsoController(context, "zen_mode_also_allow", mIsFromDnd));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.getApplication();
        }
        Bundle arguments = getArguments();
        if ((arguments != null ? arguments.getString("path") : null) != null) {
            mIsFromDnd = true;
        } else {
            mIsFromDnd = false;
        }
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        getFragmentManager();
        new NotificationBackend();
        List buildPreferenceControllers$3 =
                buildPreferenceControllers$3(context, settingsLifecycle);
        this.mControllers = buildPreferenceControllers$3;
        Iterator it = ((ArrayList) buildPreferenceControllers$3).iterator();
        while (it.hasNext()) {
            AbstractPreferenceController abstractPreferenceController =
                    (AbstractPreferenceController) it.next();
            if (abstractPreferenceController instanceof ZenModeContactExceptionController) {
                this.mZenModeContactExceptionController =
                        (ZenModeContactExceptionController) abstractPreferenceController;
            }
            if (abstractPreferenceController instanceof SecZenModePeopleSettingsController) {
                ((SecZenModePeopleSettingsController) abstractPreferenceController)
                        .setModeDependentListener(this);
            }
            if (abstractPreferenceController instanceof ZenModeAllowAlsoController) {
                this.mZenModeAllowAlsoController =
                        (ZenModeAllowAlsoController) abstractPreferenceController;
            }
        }
        return this.mControllers;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return (mIsFromDnd ? ZenModeSettings.class : LifeStyleZenFragment.class).getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1823;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.zen_mode_people_settings;
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == this.REQUEST_CODE_OK) {
            final ArrayList arrayList = new ArrayList();
            intent.getStringArrayListExtra("result")
                    .forEach(
                            new Consumer() { // from class:
                                             // com.android.settings.notification.zen.ZenModePeopleSettings$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    ZenModePeopleSettings zenModePeopleSettings =
                                            ZenModePeopleSettings.this;
                                    List list = arrayList;
                                    BixbyRoutineActionHandler bixbyRoutineActionHandler =
                                            ZenModePeopleSettings.mRSHandler;
                                    zenModePeopleSettings.getClass();
                                    String[] split = ((String) obj).split(";");
                                    list.add(
                                            split[zenModePeopleSettings.CONTACT_ID]
                                                    + ";"
                                                    + split[zenModePeopleSettings.PHONE_NUMBER]);
                                }
                            });
            if (mIsFromDnd) {
                ZenUtil.setExceptionContact(((ZenModeSettingsBase) this).mContext, arrayList);
            } else {
                mRSHandler.getClass();
                BixbyRoutineActionHandler.setContact_exist_list(arrayList);
            }
        }
    }

    @Override // com.android.settings.notification.zen.ZenModeSettingsBase,
              // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        mRSHandler = BixbyRoutineActionHandler.getInstance();
        Preference findPreference = preferenceScreen.findPreference("zen_contact");
        if (findPreference == null || !(findPreference instanceof SecRecyclerViewPreference)) {
            return;
        }
        ((SecRecyclerViewPreference) findPreference)
                        .mAppNotificationTypeAdapter
                        .mZenModeCallExceptionListener =
                this;
    }
}
