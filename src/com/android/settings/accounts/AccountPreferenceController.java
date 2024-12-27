package com.android.settings.accounts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.SearchIndexableData;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.content.PackageMonitor;
import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.AccessiblePreferenceCategory;
import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.accounts.AuthenticatorHelper;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.voiceinput.samsungaccount.contract.SaContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AccountPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin,
                AuthenticatorHelper.OnAccountsUpdateListener,
                Preference.OnPreferenceClickListener,
                LifecycleObserver,
                OnPause,
                OnResume {
    public int mAccountProfileOrder;
    public final String[] mAuthorities;
    public final int mAuthoritiesCount;
    public final DevicePolicyManager mDpm;
    public final DashboardFragment mFragment;
    public final Handler mHandler;
    public final AccountRestrictionHelper mHelper;
    public final ManagedProfileBroadcastReceiver mManagedProfileBroadcastReceiver;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public final SparseArray mProfiles;
    public final AnonymousClass2 mSettingsPackageMonitor;
    public final int mType;
    public final UserManager mUm;
    public final AnonymousClass3 mUpdateRunnable;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.accounts.AccountPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            AccountTypePreference accountTypePreference = (AccountTypePreference) obj;
            AccountTypePreference accountTypePreference2 = (AccountTypePreference) obj2;
            int compareTo =
                    accountTypePreference
                            .mSummary
                            .toString()
                            .compareTo(accountTypePreference2.mSummary.toString());
            return compareTo != 0
                    ? compareTo
                    : accountTypePreference
                            .mTitle
                            .toString()
                            .compareTo(accountTypePreference2.mTitle.toString());
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ManagedProfileBroadcastReceiver extends BroadcastReceiver {
        public boolean mListeningToManagedProfileEvents;

        public ManagedProfileBroadcastReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.v("AccountPrefController", "Received broadcast: " + action);
            if (!action.equals("android.intent.action.MANAGED_PROFILE_REMOVED")
                    && !action.equals("android.intent.action.MANAGED_PROFILE_ADDED")) {
                Log.w(
                        "AccountPrefController",
                        "Cannot handle received broadcast: " + intent.getAction());
                return;
            }
            AccountPreferenceController accountPreferenceController =
                    AccountPreferenceController.this;
            if (accountPreferenceController.mFragment
                    instanceof AccountWorkProfileDashboardFragment) {
                SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
                String name = AccountDashboardFragment.class.getName();
                SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                launchRequest.mDestinationName = name;
                launchRequest.mSourceMetricsCategory =
                        AccountPreferenceController.this.mFragment.getMetricsCategory();
                subSettingLauncher.setTitleRes(-1, null);
                launchRequest.mIsSecondLayerPage = true;
                subSettingLauncher.launch();
                AccountPreferenceController.this.mFragment.getActivity().finish();
                return;
            }
            int size = accountPreferenceController.mProfiles.size();
            for (int i = 0; i < size; i++) {
                AuthenticatorHelper authenticatorHelper =
                        ((ProfileData) accountPreferenceController.mProfiles.valueAt(i))
                                .authenticatorHelper;
                if (authenticatorHelper != null && authenticatorHelper.mListeningToAccountUpdates) {
                    authenticatorHelper.mContext.unregisterReceiver(authenticatorHelper);
                    authenticatorHelper.mListeningToAccountUpdates = false;
                }
            }
            AccountPreferenceController.this.updateUi$1();
            AccountPreferenceController accountPreferenceController2 =
                    AccountPreferenceController.this;
            int size2 = accountPreferenceController2.mProfiles.size();
            for (int i2 = 0; i2 < size2; i2++) {
                AuthenticatorHelper authenticatorHelper2 =
                        ((ProfileData) accountPreferenceController2.mProfiles.valueAt(i2))
                                .authenticatorHelper;
                if (authenticatorHelper2 != null) {
                    authenticatorHelper2.listenToAccountUpdates();
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ProfileData {
        public ArrayMap accountPreferences;
        public RestrictedPreference addAccountPreference;
        public AuthenticatorHelper authenticatorHelper;
        public Preference managedProfilePreference;
        public boolean pendingRemoval;
        public AccessiblePreferenceCategory preferenceGroup;
        public RestrictedPreference removeWorkProfilePreference;
        public UserInfo userInfo;
    }

    /* renamed from: $r8$lambda$FUqZkbIhELOhQOw4z9Z2-iKicRQ, reason: not valid java name */
    public static /* synthetic */ String m690$r8$lambda$FUqZkbIhELOhQOw4z9Z2iKicRQ(
            AccountPreferenceController accountPreferenceController, String str, int i) {
        return str != null
                ? accountPreferenceController.mContext.getString(i, str)
                : accountPreferenceController.mContext.getString(i);
    }

    public AccountPreferenceController(
            Context context, DashboardFragment dashboardFragment, String[] strArr, int i) {
        this(context, dashboardFragment, strArr, new AccountRestrictionHelper(context), i);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        updateUi$1();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return !this.mUm.isManagedProfile()
                || this.mUm.getUserInfo(UserHandle.myUserId()).isSecureFolder()
                || SemPersonaManager.isAppSeparationUserId(UserHandle.myUserId());
    }

    @Override // com.android.settingslib.accounts.AuthenticatorHelper.OnAccountsUpdateListener
    public final void onAccountsUpdate(UserHandle userHandle) {
        ProfileData profileData = (ProfileData) this.mProfiles.get(userHandle.getIdentifier());
        if (profileData != null) {
            updateAccountTypes(profileData);
            return;
        }
        Log.w(
                "AccountPrefController",
                "Missing Settings screen for: " + userHandle.getIdentifier());
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        int size = this.mProfiles.size();
        for (int i = 0; i < size; i++) {
            AuthenticatorHelper authenticatorHelper =
                    ((ProfileData) this.mProfiles.valueAt(i)).authenticatorHelper;
            if (authenticatorHelper != null && authenticatorHelper.mListeningToAccountUpdates) {
                authenticatorHelper.mContext.unregisterReceiver(authenticatorHelper);
                authenticatorHelper.mListeningToAccountUpdates = false;
            }
        }
        Context context = this.mContext;
        ManagedProfileBroadcastReceiver managedProfileBroadcastReceiver =
                this.mManagedProfileBroadcastReceiver;
        if (managedProfileBroadcastReceiver.mListeningToManagedProfileEvents) {
            context.unregisterReceiver(managedProfileBroadcastReceiver);
            managedProfileBroadcastReceiver.mListeningToManagedProfileEvents = false;
        }
        unregister();
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        DashboardFragment dashboardFragment = this.mFragment;
        int metricsCategory = dashboardFragment.getMetricsCategory();
        int size = this.mProfiles.size();
        for (int i = 0; i < size; i++) {
            ProfileData profileData = (ProfileData) this.mProfiles.valueAt(i);
            RestrictedPreference restrictedPreference = profileData.addAccountPreference;
            SettingsMetricsFeatureProvider settingsMetricsFeatureProvider =
                    this.mMetricsFeatureProvider;
            if (preference == restrictedPreference) {
                settingsMetricsFeatureProvider.logClickedPreference(preference, metricsCategory);
                Intent intent = new Intent("android.settings.ADD_ACCOUNT_SETTINGS");
                intent.setClass(this.mContext, AddAccountSettings.class);
                intent.putExtra("android.intent.extra.USER", profileData.userInfo.getUserHandle());
                intent.putExtra("authorities", this.mAuthorities);
                this.mContext.startActivity(intent);
                return true;
            }
            if (preference == profileData.removeWorkProfilePreference) {
                settingsMetricsFeatureProvider.logClickedPreference(preference, metricsCategory);
                int i2 = profileData.userInfo.id;
                Bundle bundle = new Bundle();
                bundle.putInt("userId", i2);
                RemoveUserFragment removeUserFragment = new RemoveUserFragment();
                removeUserFragment.setArguments(bundle);
                removeUserFragment.show(dashboardFragment.getFragmentManager(), "removeUser");
                return true;
            }
            if (preference == profileData.managedProfilePreference) {
                settingsMetricsFeatureProvider.logClickedPreference(preference, metricsCategory);
                Bundle bundle2 = new Bundle();
                bundle2.putParcelable(
                        "android.intent.extra.USER", profileData.userInfo.getUserHandle());
                SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
                SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                launchRequest.mSourceMetricsCategory = metricsCategory;
                launchRequest.mDestinationName = ManagedProfileSettings.class.getName();
                launchRequest.mTitle =
                        this.mDpm
                                .getResources()
                                .getString(
                                        "Settings.MANAGED_PROFILE_SETTINGS_TITLE",
                                        new AccountPreferenceController$$ExternalSyntheticLambda2(
                                                this, 2));
                launchRequest.mArguments = bundle2;
                subSettingLauncher.launch();
                return true;
            }
        }
        return false;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        updateUi$1();
        Context context = this.mContext;
        ManagedProfileBroadcastReceiver managedProfileBroadcastReceiver =
                this.mManagedProfileBroadcastReceiver;
        if (!managedProfileBroadcastReceiver.mListeningToManagedProfileEvents) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.MANAGED_PROFILE_REMOVED");
            intentFilter.addAction("android.intent.action.MANAGED_PROFILE_ADDED");
            context.registerReceiver(managedProfileBroadcastReceiver, intentFilter);
            managedProfileBroadcastReceiver.mListeningToManagedProfileEvents = true;
        }
        int size = this.mProfiles.size();
        for (int i = 0; i < size; i++) {
            AuthenticatorHelper authenticatorHelper =
                    ((ProfileData) this.mProfiles.valueAt(i)).authenticatorHelper;
            if (authenticatorHelper != null) {
                authenticatorHelper.listenToAccountUpdates();
            }
        }
        Context context2 = this.mContext;
        register(context2, context2.getMainLooper(), false);
    }

    public final void setCategoryTitleFromDevicePolicyResource(
            AccessiblePreferenceCategory accessiblePreferenceCategory, String str, final int i) {
        accessiblePreferenceCategory.setTitle(
                this.mDpm
                        .getResources()
                        .getString(
                                str,
                                new Supplier() { // from class:
                                                 // com.android.settings.accounts.AccountPreferenceController$$ExternalSyntheticLambda8
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        String string;
                                        string =
                                                AccountPreferenceController.this.mContext.getString(
                                                        i);
                                        return string;
                                    }
                                }));
    }

    public final void updateAccountTypes(ProfileData profileData) {
        ProfileData profileData2;
        String[] strArr;
        int i;
        ArrayMap arrayMap;
        String str;
        ArrayMap arrayMap2;
        DashboardFragment dashboardFragment = this.mFragment;
        if (dashboardFragment.getPreferenceManager() == null
                || profileData.preferenceGroup.getPreferenceManager() == null) {
            return;
        }
        if (profileData.userInfo.isEnabled()) {
            ArrayMap arrayMap3 = new ArrayMap(profileData.accountPreferences);
            final AuthenticatorHelper authenticatorHelper = profileData.authenticatorHelper;
            UserHandle userHandle = profileData.userInfo.getUserHandle();
            ArrayList arrayList = authenticatorHelper.mEnabledAccountTypes;
            String[] strArr2 = (String[]) arrayList.toArray(new String[arrayList.size()]);
            ArrayList arrayList2 = new ArrayList(strArr2.length);
            int i2 = 0;
            while (i2 < strArr2.length) {
                final String str2 = strArr2[i2];
                String[] strArr3 = this.mAuthorities;
                int i3 = this.mAuthoritiesCount;
                String str3 = "AccountPrefController";
                if (i3 != 0) {
                    ArrayList arrayList3 =
                            (ArrayList) authenticatorHelper.mAccountTypeToAuthorities.get(str2);
                    if (arrayList3 == null) {
                        DialogFragment$$ExternalSyntheticOutline0.m(
                                "No sync authorities for account type: ",
                                str2,
                                "AccountPrefController");
                    } else {
                        for (int i4 = 0; i4 < i3; i4++) {
                            if (!arrayList3.contains(strArr3[i4])) {}
                        }
                    }
                    arrayMap = arrayMap3;
                    strArr = strArr2;
                    i = i2;
                    i2 = i + 1;
                    strArr2 = strArr;
                    arrayMap3 = arrayMap;
                }
                CharSequence labelForType =
                        authenticatorHelper.getLabelForType(this.mContext, str2);
                if (labelForType != null
                        && (!SemCscFeature.getInstance()
                                        .getBoolean("CscFeature_VoiceCall_SupportCallProtect")
                                || str2 == null
                                || !str2.equals("com.att.callprotect.account"))) {
                    String str4 =
                            ((HashMap) authenticatorHelper.mTypeToAuthDescription).containsKey(str2)
                                    ? ((AuthenticatorDescription)
                                                    ((HashMap)
                                                                    authenticatorHelper
                                                                            .mTypeToAuthDescription)
                                                            .get(str2))
                                            .packageName
                                    : null;
                    int i5 =
                            ((HashMap) authenticatorHelper.mTypeToAuthDescription).containsKey(str2)
                                    ? ((AuthenticatorDescription)
                                                    ((HashMap)
                                                                    authenticatorHelper
                                                                            .mTypeToAuthDescription)
                                                            .get(str2))
                                            .labelId
                                    : -1;
                    Account[] accountsByTypeAsUser =
                            AccountManager.get(this.mContext)
                                    .getAccountsByTypeAsUser(str2, userHandle);
                    strArr = strArr2;
                    Drawable drawableForType =
                            authenticatorHelper.getDrawableForType(this.mContext, str2);
                    Context context = dashboardFragment.getPreferenceManager().mContext;
                    i = i2;
                    int length = accountsByTypeAsUser.length;
                    int i6 = 0;
                    while (i6 < length) {
                        int i7 = length;
                        Account account = accountsByTypeAsUser[i6];
                        Account[] accountArr = accountsByTypeAsUser;
                        AccountTypePreference accountTypePreference =
                                (AccountTypePreference)
                                        arrayMap3.remove(String.valueOf(account.hashCode()));
                        if (accountTypePreference != null) {
                            arrayList2.add(accountTypePreference);
                        } else if ("com.samsung.android.coreapps".equals(str2)) {
                            Log.d(str3, "Hide Easysignup account");
                        } else if (SaContract.OLD_PACKAGE_NAME.equals(str2)) {
                            Log.d(str3, "Hide mobileservice account");
                        } else {
                            str = str3;
                            ArrayList arrayList4 =
                                    (ArrayList)
                                            authenticatorHelper.mAccountTypeToAuthorities.get(
                                                    account.type);
                            if (strArr3 == null || arrayList4 == null) {
                                arrayMap2 = arrayMap3;
                            } else {
                                int length2 = strArr3.length;
                                arrayMap2 = arrayMap3;
                                int i8 = 0;
                                while (i8 < length2) {
                                    int i9 = length2;
                                    if (!arrayList4.contains(strArr3[i8])) {
                                        i8++;
                                        length2 = i9;
                                    }
                                }
                                i6++;
                                length = i7;
                                accountsByTypeAsUser = accountArr;
                                str3 = str;
                                arrayMap3 = arrayMap2;
                            }
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("account", account);
                            bundle.putParcelable("user_handle", userHandle);
                            bundle.putString("account_type", str2);
                            bundle.putString("account_label", labelForType.toString());
                            bundle.putInt("account_title_res", i5);
                            bundle.putParcelable("android.intent.extra.USER", userHandle);
                            this.mMetricsFeatureProvider.getClass();
                            arrayList2.add(
                                    new AccountTypePreference(
                                            context,
                                            MetricsFeatureProvider.getMetricsCategory(
                                                    dashboardFragment),
                                            account,
                                            str4,
                                            i5,
                                            labelForType,
                                            AccountDetailDashboardFragment.class.getName(),
                                            bundle,
                                            drawableForType));
                            i6++;
                            length = i7;
                            accountsByTypeAsUser = accountArr;
                            str3 = str;
                            arrayMap3 = arrayMap2;
                        }
                        arrayMap2 = arrayMap3;
                        str = str3;
                        i6++;
                        length = i7;
                        accountsByTypeAsUser = accountArr;
                        str3 = str;
                        arrayMap3 = arrayMap2;
                    }
                    arrayMap = arrayMap3;
                    final Context context2 = this.mContext;
                    new AsyncTask() { // from class:
                                      // com.android.settingslib.accounts.AuthenticatorHelper.1
                        public final /* synthetic */ String val$accountType;
                        public final /* synthetic */ Context val$context;

                        public AnonymousClass1(final Context context22, final String str22) {
                            r2 = context22;
                            r3 = str22;
                        }

                        @Override // android.os.AsyncTask
                        public final Object doInBackground(Object[] objArr) {
                            AuthenticatorHelper.this.getDrawableForType(r2, r3);
                            return null;
                        }
                    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
                    i2 = i + 1;
                    strArr2 = strArr;
                    arrayMap3 = arrayMap;
                }
                arrayMap = arrayMap3;
                strArr = strArr2;
                i = i2;
                i2 = i + 1;
                strArr2 = strArr;
                arrayMap3 = arrayMap;
            }
            ArrayMap arrayMap4 = arrayMap3;
            Collections.sort(arrayList2, new AnonymousClass1());
            int size = arrayList2.size();
            for (int i10 = 0; i10 < size; i10++) {
                AccountTypePreference accountTypePreference2 =
                        (AccountTypePreference) arrayList2.get(i10);
                accountTypePreference2.setOrder(i10);
                String key = accountTypePreference2.getKey();
                if (!profileData.accountPreferences.containsKey(key)) {
                    profileData.preferenceGroup.addPreference(accountTypePreference2);
                    profileData.accountPreferences.put(key, accountTypePreference2);
                }
            }
            profileData2 = profileData;
            RestrictedPreference restrictedPreference = profileData2.addAccountPreference;
            if (restrictedPreference != null) {
                profileData2.preferenceGroup.addPreference(restrictedPreference);
            }
            for (String str5 : arrayMap4.keySet()) {
                profileData2.preferenceGroup.removePreference(
                        (Preference) profileData2.accountPreferences.get(str5));
                profileData2.accountPreferences.remove(str5);
            }
        } else {
            profileData2 = profileData;
            profileData2.preferenceGroup.removeAll();
            Preference preference =
                    new Preference(dashboardFragment.getPreferenceManager().mContext);
            preference.setEnabled(false);
            preference.setIcon(R.drawable.empty_icon);
            preference.setTitle((CharSequence) null);
            preference.setSummary(
                    this.mDpm
                            .getResources()
                            .getString(
                                    "Settings.WORK_PROFILE_NOT_AVAILABLE",
                                    new AccountPreferenceController$$ExternalSyntheticLambda2(
                                            this, 0)));
            profileData2.preferenceGroup.addPreference(preference);
        }
        RestrictedPreference restrictedPreference2 = profileData2.removeWorkProfilePreference;
        if (restrictedPreference2 != null) {
            profileData2.preferenceGroup.addPreference(restrictedPreference2);
        }
        Preference preference2 = profileData2.managedProfilePreference;
        if (preference2 != null) {
            profileData2.preferenceGroup.addPreference(preference2);
        }
    }

    public final void updateAddAccountButtonState() {
        for (int i = 0; i < this.mProfiles.size(); i++) {
            ProfileData profileData = (ProfileData) this.mProfiles.valueAt(i);
            RestrictedPreference restrictedPreference = profileData.addAccountPreference;
            if (restrictedPreference != null) {
                this.mHelper.enforceRestrictionOnPreference(
                        restrictedPreference, "no_modify_accounts", profileData.userInfo.id);
            }
        }
    }

    @Override // com.android.settings.core.PreferenceControllerMixin
    public final void updateDynamicRawDataToIndex(List list) {
        if (isAvailable()) {
            final Resources resources = this.mContext.getResources();
            String string = resources.getString(R.string.sec_account_management);
            for (UserInfo userInfo : this.mUm.getProfiles(UserHandle.myUserId())) {
                if (userInfo.isEnabled()
                        && userInfo.isManagedProfile()
                        && !userInfo.isSecureFolder()
                        && !userInfo.isUserTypeAppSeparation()) {
                    if (!RestrictedLockUtilsInternal.hasBaseUserRestriction(
                            this.mHelper.mContext,
                            UserHandle.myUserId(),
                            "no_remove_managed_profile")) {
                        SearchIndexableRaw searchIndexableRaw =
                                new SearchIndexableRaw(this.mContext);
                        ((SearchIndexableData) searchIndexableRaw).key = "remove_profile";
                        final int i = 0;
                        searchIndexableRaw.title =
                                this.mDpm
                                        .getResources()
                                        .getString(
                                                "Settings.REMOVE_WORK_PROFILE",
                                                new Supplier() { // from class:
                                                                 // com.android.settings.accounts.AccountPreferenceController$$ExternalSyntheticLambda5
                                                    @Override // java.util.function.Supplier
                                                    public final Object get() {
                                                        int i2 = i;
                                                        Resources resources2 = resources;
                                                        switch (i2) {
                                                            case 0:
                                                                return resources2.getString(
                                                                        R.string
                                                                                .remove_managed_profile_label);
                                                            default:
                                                                return resources2.getString(
                                                                        R.string
                                                                                .managed_profile_settings_title);
                                                        }
                                                    }
                                                });
                        searchIndexableRaw.screenTitle = string;
                        ((ArrayList) list).add(searchIndexableRaw);
                    }
                    SearchIndexableRaw searchIndexableRaw2 = new SearchIndexableRaw(this.mContext);
                    ((SearchIndexableData) searchIndexableRaw2).key = "work_profile_setting";
                    final int i2 = 1;
                    searchIndexableRaw2.title =
                            this.mDpm
                                    .getResources()
                                    .getString(
                                            "Settings.MANAGED_PROFILE_SETTINGS_TITLE",
                                            new Supplier() { // from class:
                                                             // com.android.settings.accounts.AccountPreferenceController$$ExternalSyntheticLambda5
                                                @Override // java.util.function.Supplier
                                                public final Object get() {
                                                    int i22 = i2;
                                                    Resources resources2 = resources;
                                                    switch (i22) {
                                                        case 0:
                                                            return resources2.getString(
                                                                    R.string
                                                                            .remove_managed_profile_label);
                                                        default:
                                                            return resources2.getString(
                                                                    R.string
                                                                            .managed_profile_settings_title);
                                                    }
                                                }
                                            });
                    searchIndexableRaw2.screenTitle = string;
                    ((ArrayList) list).add(searchIndexableRaw2);
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00ee, code lost:

       if (com.samsung.android.knox.SemPersonaManager.isKnoxId(android.os.UserHandle.myUserId()) == false) goto L62;
    */
    /* JADX WARN: Removed duplicated region for block: B:57:0x023d  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0248  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateProfileUi(android.content.pm.UserInfo r12) {
        /*
            Method dump skipped, instructions count: 657
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.accounts.AccountPreferenceController.updateProfileUi(android.content.pm.UserInfo):void");
    }

    @Override // com.android.settings.core.PreferenceControllerMixin
    public final void updateRawDataToIndex(List list) {
        SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(this.mContext);
        ((SearchIndexableData) searchIndexableRaw).key = "add_account";
        searchIndexableRaw.title = this.mContext.getString(R.string.add_account_label);
        ((SearchIndexableData) searchIndexableRaw).iconResId = R.drawable.ic_add_24dp;
        ((ArrayList) list).add(searchIndexableRaw);
    }

    public final void updateUi$1() {
        if (!isAvailable()) {
            Log.e(
                    "AccountPrefController",
                    "We should not be showing settings for a managed profile");
            return;
        }
        int size = this.mProfiles.size();
        for (int i = 0; i < size; i++) {
            ((ProfileData) this.mProfiles.valueAt(i)).pendingRemoval = true;
        }
        if (this.mUm.isRestrictedProfile()) {
            updateProfileUi(this.mUm.getUserInfo(UserHandle.myUserId()));
        } else {
            List<UserInfo> profiles = this.mUm.getProfiles(UserHandle.myUserId());
            int i2 = 0;
            while (i2 < profiles.size()) {
                if (SemPersonaManager.isAppSeparationUserId(UserHandle.myUserId())) {
                    if (((UserInfo) profiles.get(i2)).id != UserHandle.myUserId()) {
                        profiles.remove(i2);
                        i2--;
                        i2++;
                    } else {
                        i2++;
                    }
                } else if (SemPersonaManager.isAppSeparationUserId(
                        ((UserInfo) profiles.get(i2)).id)) {
                    profiles.remove(i2);
                    i2--;
                    i2++;
                } else {
                    i2++;
                }
            }
            int i3 = 0;
            while (i3 < profiles.size()) {
                if (SemPersonaManager.isSecureFolderId(UserHandle.myUserId())) {
                    if (((UserInfo) profiles.get(i3)).id != UserHandle.myUserId()) {
                        profiles.remove(i3);
                        i3--;
                        i3++;
                    } else {
                        i3++;
                    }
                } else if (SemPersonaManager.isSecureFolderId(((UserInfo) profiles.get(i3)).id)) {
                    profiles.remove(i3);
                    i3--;
                    i3++;
                } else {
                    i3++;
                }
            }
            int i4 = 0;
            while (i4 < profiles.size()) {
                if (SemDualAppManager.isDualAppId(((UserInfo) profiles.get(i4)).id)) {
                    profiles.remove(i4);
                    i4--;
                }
                i4++;
            }
            for (UserInfo userInfo : profiles) {
                boolean isManagedProfile = userInfo.isManagedProfile();
                int i5 = this.mType;
                if ((!isManagedProfile || (i5 & 2) == 0)
                        && (!Flags.allowPrivateProfile()
                                || !android.multiuser.Flags.enablePrivateSpaceFeatures()
                                || !userInfo.isPrivateProfile()
                                || (i5 & 4) == 0)) {
                    if (!userInfo.isManagedProfile()
                            && (!Flags.allowPrivateProfile()
                                    || !android.multiuser.Flags.enablePrivateSpaceFeatures()
                                    || !userInfo.isPrivateProfile())) {
                        if ((i5 & 1) != 0) {}
                    }
                }
                if (this.mUm.getUserProperties(userInfo.getUserHandle()).getShowInQuietMode() != 1
                        || !userInfo.isQuietModeEnabled()) {
                    updateProfileUi(userInfo);
                }
            }
        }
        DashboardFragment dashboardFragment = this.mFragment;
        PreferenceScreen preferenceScreen = dashboardFragment.getPreferenceScreen();
        if (preferenceScreen != null) {
            for (int size2 = this.mProfiles.size() - 1; size2 >= 0; size2--) {
                ProfileData profileData = (ProfileData) this.mProfiles.valueAt(size2);
                if (profileData.pendingRemoval) {
                    preferenceScreen.removePreference(profileData.preferenceGroup);
                    this.mProfiles.removeAt(size2);
                }
            }
        }
        int size3 = this.mProfiles.size();
        for (int i6 = 0; i6 < size3; i6++) {
            updateAccountTypes((ProfileData) this.mProfiles.valueAt(i6));
        }
        dashboardFragment.forceUpdatePreferences();
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.settings.accounts.AccountPreferenceController$2] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.settings.accounts.AccountPreferenceController$3] */
    public AccountPreferenceController(
            Context context,
            DashboardFragment dashboardFragment,
            String[] strArr,
            AccountRestrictionHelper accountRestrictionHelper,
            int i) {
        super(context);
        this.mProfiles = new SparseArray();
        this.mManagedProfileBroadcastReceiver = new ManagedProfileBroadcastReceiver();
        this.mAuthoritiesCount = 0;
        this.mAccountProfileOrder = 101;
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mSettingsPackageMonitor =
                new PackageMonitor() { // from class:
                                       // com.android.settings.accounts.AccountPreferenceController.2
                    public final void onPackageAdded(String str, int i2) {
                        AccountPreferenceController accountPreferenceController =
                                AccountPreferenceController.this;
                        accountPreferenceController.mHandler.postDelayed(
                                accountPreferenceController.mUpdateRunnable, 1000L);
                    }

                    public final void onPackageModified(String str) {
                        AccountPreferenceController accountPreferenceController =
                                AccountPreferenceController.this;
                        accountPreferenceController.mHandler.postDelayed(
                                accountPreferenceController.mUpdateRunnable, 1000L);
                    }

                    public final void onPackageRemoved(String str, int i2) {
                        AccountPreferenceController accountPreferenceController =
                                AccountPreferenceController.this;
                        accountPreferenceController.mHandler.postDelayed(
                                accountPreferenceController.mUpdateRunnable, 1000L);
                    }
                };
        this.mUpdateRunnable =
                new Runnable() { // from class:
                                 // com.android.settings.accounts.AccountPreferenceController.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        AccountPreferenceController.this.updateUi$1();
                    }
                };
        this.mUm = (UserManager) context.getSystemService("user");
        this.mDpm = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        this.mAuthorities = strArr;
        this.mFragment = dashboardFragment;
        if (strArr != null) {
            this.mAuthoritiesCount = strArr.length;
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        this.mHelper = accountRestrictionHelper;
        this.mType = i;
    }
}
