package com.android.settings.accounts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.SearchIndexableData;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.users.AutoSyncDataPreferenceController;
import com.android.settings.users.AutoSyncPersonalDataPreferenceController;
import com.android.settings.users.AutoSyncWorkDataPreferenceController;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.account.CloudAccountSettings;
import com.samsung.android.settings.general.BaseResetSettingsData;
import com.samsung.android.settings.general.OnResetSettingsDataListener;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.voiceinput.samsungaccount.contract.SaContract;
import com.samsung.android.settings.widget.SecDividerItemDecorator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AccountDashboardFragment extends DashboardFragment {
    public AccountPreferenceController mAccountPreferenceController;
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass2();
    public static final OnResetSettingsDataListener RESET_SETTINGS_DATA = new AnonymousClass3();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.accounts.AccountDashboardFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            ArrayList arrayList = new ArrayList();
            AccountDashboardFragment.buildAccountPreferenceControllers(
                    context, null, null, arrayList);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getDynamicRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            Iterator it =
                    ((UserManager) context.getSystemService("user"))
                            .getProfiles(UserHandle.myUserId())
                            .iterator();
            while (it.hasNext()) {
                if (((UserInfo) it.next()).isManagedProfile()) {
                    return arrayList;
                }
            }
            for (Account account : AccountManager.get(context).getAccounts()) {
                if ("com.samsung.android.coreapps".equals(account.type)) {
                    Log.d("AccountDashboardFrag", "Hide Easysignup account");
                } else if (SaContract.OLD_PACKAGE_NAME.equals(account.type)) {
                    Log.d("AccountDashboardFrag", "Hide mobileservice account");
                } else {
                    SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
                    ((SearchIndexableData) searchIndexableRaw).key =
                            String.valueOf(account.hashCode());
                    searchIndexableRaw.title = account.name;
                    searchIndexableRaw.screenTitle =
                            context.getResources().getString(R.string.sec_account_management);
                    arrayList.add(searchIndexableRaw);
                }
            }
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            BaseSearchIndexProvider baseSearchIndexProvider =
                    AccountDashboardFragment.SEARCH_INDEX_DATA_PROVIDER;
            searchIndexableResource.xmlResId = R.xml.accounts_dashboard_settings;
            return List.of(searchIndexableResource);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return !Rune.SUPPORT_DISABLE_ACCOUNTS_SETTINGS;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.accounts.AccountDashboardFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            Account[] accounts = AccountManager.get(context).getAccounts();
            int i = 0;
            String str = ApnSettings.MVNO_NONE;
            int i2 = 0;
            for (Account account : accounts) {
                if (RestrictionPolicy.GOOGLE_ACCOUNT_TYPE.equals(account.type)) {
                    i++;
                }
                if (!"com.samsung.android.coreapps".equals(account.type)
                        && !SaContract.OLD_PACKAGE_NAME.equals(account.type)) {
                    i2++;
                    StringBuilder m =
                            PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                                    str, "/");
                    m.append(account.type);
                    str = m.toString();
                }
            }
            ArrayList arrayList = new ArrayList();
            String valueOf = String.valueOf(51101);
            String valueOf2 = String.valueOf(i2);
            StatusData statusData = new StatusData();
            statusData.mStatusValue = valueOf2;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            String valueOf3 = String.valueOf(51102);
            String valueOf4 = String.valueOf(i);
            StatusData statusData2 = new StatusData();
            statusData2.mStatusValue = valueOf4;
            statusData2.mStatusKey = valueOf3;
            arrayList.add(statusData2);
            String valueOf5 = String.valueOf(51103);
            StatusData statusData3 = new StatusData();
            statusData3.mStatusValue = str;
            statusData3.mStatusKey = valueOf5;
            arrayList.add(statusData3);
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.accounts.AccountDashboardFragment$3, reason: invalid class name */
    public final class AnonymousClass3 extends BaseResetSettingsData {
        @Override // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void resetSettings(Context context) {
            ContentResolver contentResolver = context.getContentResolver();
            if (Rune.isChinaModel()) {
                Settings.Global.putInt(contentResolver, "google_core_control", 0);
            }
        }
    }

    public static void buildAccountPreferenceControllers(
            Context context, DashboardFragment dashboardFragment, String[] strArr, List list) {
        AccountPreferenceController accountPreferenceController =
                new AccountPreferenceController(context, dashboardFragment, strArr, 7);
        if (dashboardFragment != null) {
            dashboardFragment.getSettingsLifecycle().addObserver(accountPreferenceController);
        }
        ArrayList arrayList = (ArrayList) list;
        arrayList.add(accountPreferenceController);
        AutoSyncDataPreferenceController autoSyncDataPreferenceController =
                new AutoSyncDataPreferenceController(context, dashboardFragment);
        if (dashboardFragment != null) {
            dashboardFragment.getSettingsLifecycle().addObserver(autoSyncDataPreferenceController);
        }
        arrayList.add(autoSyncDataPreferenceController);
        AutoSyncPersonalDataPreferenceController autoSyncPersonalDataPreferenceController =
                new AutoSyncPersonalDataPreferenceController(context, dashboardFragment);
        if (dashboardFragment != null) {
            dashboardFragment
                    .getSettingsLifecycle()
                    .addObserver(autoSyncPersonalDataPreferenceController);
        }
        arrayList.add(autoSyncPersonalDataPreferenceController);
        AutoSyncWorkDataPreferenceController autoSyncWorkDataPreferenceController =
                new AutoSyncWorkDataPreferenceController(context, dashboardFragment);
        if (dashboardFragment != null) {
            dashboardFragment
                    .getSettingsLifecycle()
                    .addObserver(autoSyncWorkDataPreferenceController);
        }
        arrayList.add(autoSyncWorkDataPreferenceController);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        buildAccountPreferenceControllers(
                context, this, getIntent().getStringArrayExtra("authorities"), arrayList);
        return arrayList;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return CloudAccountSettings.class.getName();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AccountDashboardFrag";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 11;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        getContext();
        return R.xml.accounts_dashboard_settings;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_accounts_backup";
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mAccountPreferenceController =
                (AccountPreferenceController) use(AccountPreferenceController.class);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        updatePreferenceStates();
        this.mAccountPreferenceController.updateAddAccountButtonState();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setDivider(null);
        getListView()
                .addItemDecoration(
                        new SecDividerItemDecorator(
                                (int)
                                        (getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_app_list_item_icon_min_width)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_item_padding_start)),
                                getContext(),
                                getResources().getDrawable(R.drawable.sec_top_level_list_divider)));
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final boolean shouldSkipForInitialSUW() {
        return true;
    }
}
