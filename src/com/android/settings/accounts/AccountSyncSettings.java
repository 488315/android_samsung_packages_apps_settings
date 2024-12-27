package com.android.settings.accounts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SyncAdapterType;
import android.content.SyncInfo;
import android.content.SyncStatusInfo;
import android.content.SyncStatusObserver;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.compose.ui.text.SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.widget.EntityHeaderController;
import com.android.settingslib.accounts.AuthenticatorHelper;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.widget.FooterPreference;
import com.android.settingslib.widget.LayoutPreference;

import com.google.android.collect.Lists;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AccountSyncSettings extends SettingsPreferenceFragment
        implements AuthenticatorHelper.OnAccountsUpdateListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Account mAccount;
    public AuthenticatorHelper mAuthenticatorHelper;
    public Object mStatusChangeListenerHandle;
    public UserManager mUm;
    public UserHandle mUserHandle;
    public final AccountPreferenceBase$$ExternalSyntheticLambda0 mSyncStatusObserver =
            new SyncStatusObserver() { // from class:
                                       // com.android.settings.accounts.AccountPreferenceBase$$ExternalSyntheticLambda0
                @Override // android.content.SyncStatusObserver
                public final void onStatusChanged(int i) {
                    final AccountSyncSettings accountSyncSettings = AccountSyncSettings.this;
                    int i2 = AccountSyncSettings.$r8$clinit;
                    accountSyncSettings.getClass();
                    ThreadUtils.postOnMainThread(
                            new Runnable() { // from class:
                                             // com.android.settings.accounts.AccountPreferenceBase$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    AccountSyncSettings accountSyncSettings2 =
                                            AccountSyncSettings.this;
                                    int i3 = AccountSyncSettings.$r8$clinit;
                                    accountSyncSettings2.onSyncStateUpdated();
                                }
                            });
                }
            };
    public boolean mSyncIsAdded = true;
    public final ArrayList mInvisibleAdapters = Lists.newArrayList();
    public HashMap mUidRequestCodeMap = new HashMap();

    static {
        Log.isLoggable("AccountPreferenceBase", 2);
    }

    public final boolean accountExists(Account account) {
        if (account == null) {
            return false;
        }
        for (Account account2 :
                AccountManager.get(getActivity())
                        .getAccountsByTypeAsUser(account.type, this.mUserHandle)) {
            if (account2.equals(account)) {
                return true;
            }
        }
        return false;
    }

    public final void addSyncStateSwitch(Account account, String str, String str2, int i) {
        String str3;
        String str4 = account.type;
        if (str4 == null
                || this.mUserHandle.getIdentifier() < 100
                || !str4.equalsIgnoreCase("com.osp.app.signin")) {
            if (str4 == null
                    || !(str4.contains("com.android.ldap")
                            || str4.contains("com.samsung.android.ldap"))) {
                SyncStateSwitchPreference syncStateSwitchPreference =
                        (SyncStateSwitchPreference) getCachedPreference(str);
                int enterprisePolicyEnabled =
                        Utils.getEnterprisePolicyEnabled(
                                getActivity(),
                                "content://com.sec.knox.provider/RestrictionPolicy2",
                                "isGoogleAccountsAutoSyncAllowed");
                boolean z =
                        enterprisePolicyEnabled == -1
                                || enterprisePolicyEnabled == 1
                                || (str3 = account.type) == null
                                || !RestrictionPolicy.GOOGLE_ACCOUNT_TYPE.equals(str3);
                if (syncStateSwitchPreference == null) {
                    syncStateSwitchPreference =
                            new SyncStateSwitchPreference(
                                    getPrefContext(), null, 0, R.style.SyncSwitchPreference);
                    syncStateSwitchPreference.mOneTimeSyncMode = false;
                    syncStateSwitchPreference.mAccount = account;
                    syncStateSwitchPreference.mAuthority = str;
                    syncStateSwitchPreference.mPackageName = str2;
                    syncStateSwitchPreference.mUid = i;
                    syncStateSwitchPreference.setVisible(!TextUtils.isEmpty(str));
                    syncStateSwitchPreference.notifyChanged();
                    getPreferenceScreen().addPreference(syncStateSwitchPreference);
                } else {
                    syncStateSwitchPreference.mAccount = account;
                    syncStateSwitchPreference.mAuthority = str;
                    syncStateSwitchPreference.mPackageName = str2;
                    syncStateSwitchPreference.mUid = i;
                    syncStateSwitchPreference.setVisible(!TextUtils.isEmpty(str));
                    syncStateSwitchPreference.notifyChanged();
                }
                PackageManager packageManager = getPackageManager();
                syncStateSwitchPreference.setEnabled(z);
                syncStateSwitchPreference.setPersistent();
                ProviderInfo resolveContentProviderAsUser =
                        packageManager.resolveContentProviderAsUser(
                                str, 0, this.mUserHandle.getIdentifier());
                if (resolveContentProviderAsUser == null) {
                    return;
                }
                CharSequence loadLabel = resolveContentProviderAsUser.loadLabel(packageManager);
                if (TextUtils.isEmpty(loadLabel)) {
                    SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0.m(
                            "Provider needs a label for authority '",
                            str,
                            "'",
                            "AccountPreferenceBase");
                } else {
                    syncStateSwitchPreference.setTitle(loadLabel);
                    syncStateSwitchPreference.setKey(str);
                }
            }
        }
    }

    public boolean enabledSyncNowMenu() {
        int preferenceCount = getPreferenceScreen().getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            Preference preference = getPreferenceScreen().getPreference(i);
            if ((preference instanceof SyncStateSwitchPreference)
                    && ((SyncStateSwitchPreference) preference).mChecked) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final int getDialogMetricsCategory(int i) {
        return i != 102 ? 0 : 587;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 9;
    }

    @Override // com.android.settingslib.accounts.AuthenticatorHelper.OnAccountsUpdateListener
    public final void onAccountsUpdate(UserHandle userHandle) {
        if (!accountExists(this.mAccount)) {
            finish();
        } else {
            updateAccountSwitches();
            onSyncStateUpdated();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        FragmentActivity activity = getActivity();
        DateFormat.getDateFormat(activity);
        DateFormat.getTimeFormat(activity);
        Bundle arguments = getArguments();
        Intent intent = getActivity().getIntent();
        if (arguments == null) {
            Log.e(
                    "AccountPreferenceBase",
                    "No arguments provided when starting intent. ACCOUNT_KEY needed.");
            finish();
            return;
        }
        Account account =
                arguments.containsKey("account")
                        ? (Account) arguments.getParcelable("account")
                        : (Account) intent.getParcelableExtra("account");
        this.mAccount = account;
        if (!accountExists(account)) {
            Log.e("AccountPreferenceBase", "Account provided does not exist: " + this.mAccount);
            finish();
            return;
        }
        if (Log.isLoggable("AccountPreferenceBase", 2)) {
            Log.v("AccountPreferenceBase", "Got account: " + this.mAccount);
        }
        EntityHeaderController entityHeaderController =
                new EntityHeaderController(getActivity(), this, null);
        entityHeaderController.setIcon(
                this.mAuthenticatorHelper.getDrawableForType(getActivity(), this.mAccount.type));
        Account account2 = this.mAccount;
        entityHeaderController.mLabel = account2.name;
        entityHeaderController.mSummary =
                this.mAuthenticatorHelper.getLabelForType(getActivity(), account2.type);
        LayoutPreference done = entityHeaderController.done(getPrefContext());
        done.setOrder(-2);
        getPreferenceScreen().addPreference(done);
        SecInsetCategoryPreference secInsetCategoryPreference =
                new SecInsetCategoryPreference(getPrefContext());
        secInsetCategoryPreference.seslSetSubheaderRoundedBackground(3);
        secInsetCategoryPreference.setOrder(-1);
        getPreferenceScreen().addPreference(secInsetCategoryPreference);
        if (bundle == null || !bundle.containsKey("uid_request_code")) {
            return;
        }
        this.mUidRequestCodeMap = (HashMap) bundle.getSerializable("uid_request_code");
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1) {
            int preferenceCount = getPreferenceScreen().getPreferenceCount();
            for (int i3 = 0; i3 < preferenceCount; i3++) {
                Preference preference = getPreferenceScreen().getPreference(i3);
                if (preference instanceof SyncStateSwitchPreference) {
                    SyncStateSwitchPreference syncStateSwitchPreference =
                            (SyncStateSwitchPreference) preference;
                    int i4 = syncStateSwitchPreference.mUid;
                    if ((!this.mUidRequestCodeMap.containsKey(Integer.valueOf(i4))
                                    ? -1
                                    : ((Integer) this.mUidRequestCodeMap.get(Integer.valueOf(i4)))
                                            .intValue())
                            == i) {
                        onPreferenceTreeClick(syncStateSwitchPreference);
                        return;
                    }
                }
            }
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        String string;
        super.onCreate(bundle);
        this.mUm = (UserManager) getSystemService("user");
        FragmentActivity activity = getActivity();
        this.mUserHandle =
                Utils.getSecureTargetUser(
                        activity.getActivityToken(),
                        this.mUm,
                        getArguments(),
                        activity.getIntent().getExtras());
        this.mAuthenticatorHelper = new AuthenticatorHelper(activity, this.mUserHandle, this);
        addPreferencesFromResource(R.xml.account_sync_settings);
        UserInfo userInfo =
                ((UserManager) getSystemService("user"))
                        .getUserInfo(this.mUserHandle.getIdentifier());
        boolean isManagedProfile = userInfo != null ? userInfo.isManagedProfile() : false;
        final CharSequence title = getActivity().getTitle();
        if (title == null) {
            title = ApnSettings.MVNO_NONE;
        }
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) getSystemService(DevicePolicyManager.class);
        if (isManagedProfile) {
            final int i = 0;
            string =
                    devicePolicyManager
                            .getResources()
                            .getString(
                                    "Settings.ACCESSIBILITY_WORK_ACCOUNT_TITLE",
                                    new Supplier(
                                            this) { // from class:
                                                    // com.android.settings.accounts.AccountSyncSettings$$ExternalSyntheticLambda1
                                        public final /* synthetic */ AccountSyncSettings f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            switch (i) {
                                                case 0:
                                                    AccountSyncSettings accountSyncSettings =
                                                            this.f$0;
                                                    CharSequence charSequence = title;
                                                    int i2 = AccountSyncSettings.$r8$clinit;
                                                    return accountSyncSettings.getString(
                                                            R.string
                                                                    .accessibility_work_account_title,
                                                            charSequence);
                                                default:
                                                    AccountSyncSettings accountSyncSettings2 =
                                                            this.f$0;
                                                    CharSequence charSequence2 = title;
                                                    int i3 = AccountSyncSettings.$r8$clinit;
                                                    return accountSyncSettings2.getString(
                                                            R.string
                                                                    .accessibility_personal_account_title,
                                                            charSequence2);
                                            }
                                        }
                                    },
                                    title);
        } else {
            final int i2 = 1;
            string =
                    devicePolicyManager
                            .getResources()
                            .getString(
                                    "Settings.ACCESSIBILITY_PERSONAL_ACCOUNT_TITLE",
                                    new Supplier(
                                            this) { // from class:
                                                    // com.android.settings.accounts.AccountSyncSettings$$ExternalSyntheticLambda1
                                        public final /* synthetic */ AccountSyncSettings f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            switch (i2) {
                                                case 0:
                                                    AccountSyncSettings accountSyncSettings =
                                                            this.f$0;
                                                    CharSequence charSequence = title;
                                                    int i22 = AccountSyncSettings.$r8$clinit;
                                                    return accountSyncSettings.getString(
                                                            R.string
                                                                    .accessibility_work_account_title,
                                                            charSequence);
                                                default:
                                                    AccountSyncSettings accountSyncSettings2 =
                                                            this.f$0;
                                                    CharSequence charSequence2 = title;
                                                    int i3 = AccountSyncSettings.$r8$clinit;
                                                    return accountSyncSettings2.getString(
                                                            R.string
                                                                    .accessibility_personal_account_title,
                                                            charSequence2);
                                            }
                                        }
                                    },
                                    title);
        }
        getActivity().setTitle(Utils.createAccessibleSequence(string, title));
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        if (i != 102) {
            return null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.cant_sync_dialog_title);
        builder.setMessage(R.string.cant_sync_dialog_message);
        builder.setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null);
        return builder.create();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.add(0, 1, 0, getString(R.string.sync_now_label));
        menu.add(0, 2, 0, getString(R.string.cancel_sync_label));
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 1) {
            requestOrCancelSyncForEnabledProviders(true);
            FragmentActivity activity = getActivity();
            if (activity != null) {
                activity.invalidateOptionsMenu();
            }
            return true;
        }
        if (itemId != 2) {
            return super.onOptionsItemSelected(menuItem);
        }
        requestOrCancelSyncForEnabledProviders(false);
        FragmentActivity activity2 = getActivity();
        if (activity2 != null) {
            activity2.invalidateOptionsMenu();
        }
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        ContentResolver.removeStatusChangeListener(this.mStatusChangeListenerHandle);
        AuthenticatorHelper authenticatorHelper = this.mAuthenticatorHelper;
        if (authenticatorHelper.mListeningToAccountUpdates) {
            authenticatorHelper.mContext.unregisterReceiver(authenticatorHelper);
            authenticatorHelper.mListeningToAccountUpdates = false;
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (getActivity() == null) {
            return false;
        }
        if (!(preference instanceof SyncStateSwitchPreference)) {
            return super.onPreferenceTreeClick(preference);
        }
        SyncStateSwitchPreference syncStateSwitchPreference =
                (SyncStateSwitchPreference) preference;
        String str = syncStateSwitchPreference.mAuthority;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Account account = syncStateSwitchPreference.mAccount;
        int identifier = this.mUserHandle.getIdentifier();
        String str2 = syncStateSwitchPreference.mPackageName;
        boolean syncAutomaticallyAsUser =
                ContentResolver.getSyncAutomaticallyAsUser(account, str, identifier);
        if (!syncStateSwitchPreference.mOneTimeSyncMode) {
            boolean z = syncStateSwitchPreference.mChecked;
            if (z == syncAutomaticallyAsUser || (z && requestAccountAccessIfNeeded(str2))) {
                return true;
            }
            ContentResolver.setSyncAutomaticallyAsUser(account, str, z, identifier);
            if (!ContentResolver.getMasterSyncAutomaticallyAsUser(identifier) || !z) {
                requestOrCancelSync(account, str, z);
            }
        } else {
            if (requestAccountAccessIfNeeded(str2)) {
                return true;
            }
            requestOrCancelSync(account, str, true);
        }
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPrepareOptionsMenu(Menu menu) {
        boolean z;
        boolean z2;
        boolean z3;
        String str;
        super.onPrepareOptionsMenu(menu);
        int identifier = this.mUserHandle.getIdentifier();
        MenuItem findItem = menu.findItem(1);
        MenuItem findItem2 = menu.findItem(2);
        if (findItem == null || findItem2 == null) {
            return;
        }
        Account account = this.mAccount;
        if (account != null) {
            z = false;
            z2 = false;
            z3 = false;
            for (SyncAdapterType syncAdapterType : ContentResolver.getSyncAdapterTypes()) {
                if (syncAdapterType != null
                        && (str = syncAdapterType.accountType) != null
                        && str.equals(account.type)
                        && syncAdapterType.isUserVisible()
                        && ContentResolver.getIsSyncableAsUser(
                                        account, syncAdapterType.authority, identifier)
                                > 0) {
                    if (ContentResolver.getSyncAutomaticallyAsUser(
                            account, syncAdapterType.authority, identifier)) {
                        z2 = true;
                    }
                    if (ContentResolver.isSyncActive(account, syncAdapterType.authority)) {
                        z3 = true;
                    }
                    String str2 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                    if ("CHM".equals(Utils.getSalesCode())
                            && "com.osp.app.signin".equals(syncAdapterType.accountType)) {
                        z2 = false;
                    }
                    z =
                            (syncAdapterType.accountType.contains("com.android.ldap")
                                            || syncAdapterType.accountType.contains(
                                                    "com.samsung.android.ldap"))
                                    ? false
                                    : true;
                    int enterprisePolicyEnabled =
                            Utils.getEnterprisePolicyEnabled(
                                    getActivity(),
                                    "content://com.sec.knox.provider/RestrictionPolicy2",
                                    "isGoogleAccountsAutoSyncAllowed");
                    if (enterprisePolicyEnabled != -1
                            && enterprisePolicyEnabled != 1
                            && RestrictionPolicy.GOOGLE_ACCOUNT_TYPE.equals(
                                    syncAdapterType.accountType)) {
                        z = false;
                    }
                }
            }
        } else {
            z = false;
            z2 = false;
            z3 = false;
        }
        if (!z || (ContentResolver.getMasterSyncAutomaticallyAsUser(identifier) && !z2)) {
            findItem.setVisible(true);
            findItem2.setVisible(false);
            findItem.setEnabled(false);
            if (findItem.getIcon() != null) {
                findItem.getIcon().setAlpha(102);
                return;
            }
            return;
        }
        findItem.setVisible(!z3);
        findItem2.setVisible(z3);
        findItem.setEnabled(true);
        if (findItem.getIcon() != null) {
            findItem.getIcon().setAlpha(255);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        this.mAuthenticatorHelper.listenToAccountUpdates();
        this.mAuthenticatorHelper.updateAuthDescriptions(getActivity());
        onAccountsUpdate(Binder.getCallingUserHandle());
        super.onResume();
        this.mStatusChangeListenerHandle =
                ContentResolver.addStatusChangeListener(13, this.mSyncStatusObserver);
        onSyncStateUpdated();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.mUidRequestCodeMap.isEmpty()) {
            return;
        }
        bundle.putSerializable("uid_request_code", this.mUidRequestCodeMap);
    }

    public final void onSyncStateUpdated() {
        boolean z;
        List list;
        int i;
        boolean z2;
        boolean z3;
        boolean z4;
        AccountSyncSettings accountSyncSettings = this;
        if (isResumed()) {
            Date date = new Date();
            final int identifier = accountSyncSettings.mUserHandle.getIdentifier();
            List currentSyncsAsUser = ContentResolver.getCurrentSyncsAsUser(identifier);
            updateAccountSwitches();
            int preferenceCount = getPreferenceScreen().getPreferenceCount();
            boolean z5 = false;
            int i2 = 0;
            while (i2 < preferenceCount) {
                Preference preference = getPreferenceScreen().getPreference(i2);
                if (!accountSyncSettings.mSyncIsAdded
                        && (preference instanceof SecInsetCategoryPreference)) {
                    getPreferenceScreen().removePreference(preference);
                }
                if (preference instanceof SyncStateSwitchPreference) {
                    final SyncStateSwitchPreference syncStateSwitchPreference =
                            (SyncStateSwitchPreference) preference;
                    final String str = syncStateSwitchPreference.mAuthority;
                    final Account account = syncStateSwitchPreference.mAccount;
                    SyncStatusInfo syncStatusAsUser =
                            ContentResolver.getSyncStatusAsUser(account, str, identifier);
                    boolean syncAutomaticallyAsUser =
                            ContentResolver.getSyncAutomaticallyAsUser(account, str, identifier);
                    boolean z6 = syncStatusAsUser == null ? false : syncStatusAsUser.pending;
                    if (syncStatusAsUser != null) {
                        boolean z7 = syncStatusAsUser.initialize;
                    }
                    Iterator it = currentSyncsAsUser.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            z = false;
                            break;
                        }
                        SyncInfo syncInfo = (SyncInfo) it.next();
                        if (syncInfo.account.equals(account) && syncInfo.authority.equals(str)) {
                            z = true;
                            break;
                        }
                    }
                    list = currentSyncsAsUser;
                    i = preferenceCount;
                    boolean z8 =
                            (syncStatusAsUser == null
                                            || syncStatusAsUser.lastFailureTime == 0
                                            || syncStatusAsUser.getLastFailureMesgAsInt(0) == 1)
                                    ? false
                                    : true;
                    if (!syncAutomaticallyAsUser) {
                        z8 = false;
                    }
                    boolean z9 = (!z8 || z || z6) ? z5 : true;
                    if (Log.isLoggable("AccountPreferenceBase", 3)) {
                        Log.d(
                                "AccountPreferenceBase",
                                "Update sync status: "
                                        + account
                                        + " "
                                        + str
                                        + " active = "
                                        + z
                                        + " pend ="
                                        + z6);
                    }
                    long j = syncStatusAsUser == null ? 0L : syncStatusAsUser.lastSuccessTime;
                    if (!syncAutomaticallyAsUser) {
                        syncStateSwitchPreference.setSummary(R.string.sync_disabled);
                    } else if (z) {
                        syncStateSwitchPreference.setSummary(R.string.sync_in_progress);
                    } else {
                        if (j != 0) {
                            date.setTime(j);
                            z2 = z9;
                            syncStateSwitchPreference.setSummary(
                                    getResources()
                                            .getString(
                                                    R.string.last_synced,
                                                    DateUtils.formatDateTime(
                                                            getContext(), date.getTime(), 21)));
                        } else {
                            z2 = z9;
                            syncStateSwitchPreference.setSummary(ApnSettings.MVNO_NONE);
                        }
                        ContentResolver.getIsSyncableAsUser(account, str, identifier);
                        syncStateSwitchPreference.notifyChanged();
                        syncStateSwitchPreference.notifyChanged();
                        syncStateSwitchPreference.notifyChanged();
                        z3 = true;
                        z4 = !ContentResolver.getMasterSyncAutomaticallyAsUser(identifier);
                        syncStateSwitchPreference.mOneTimeSyncMode = z4;
                        syncStateSwitchPreference.notifyChanged();
                        if (!z4 && !syncAutomaticallyAsUser) {
                            z3 = false;
                        }
                        syncStateSwitchPreference.setChecked(z3);
                        syncStateSwitchPreference.setOnPreferenceChangeListener(
                                new Preference
                                        .OnPreferenceChangeListener() { // from class:
                                                                        // com.android.settings.accounts.AccountSyncSettings$$ExternalSyntheticLambda0
                                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                                    public final boolean onPreferenceChange(
                                            Preference preference2, Object obj) {
                                        Account account2 = account;
                                        int i3 = AccountSyncSettings.$r8$clinit;
                                        AccountSyncSettings accountSyncSettings2 =
                                                AccountSyncSettings.this;
                                        accountSyncSettings2.getClass();
                                        String str2 = str;
                                        if (TextUtils.isEmpty(str2)) {
                                            return false;
                                        }
                                        SyncStateSwitchPreference syncStateSwitchPreference2 =
                                                syncStateSwitchPreference;
                                        String str3 = syncStateSwitchPreference2.mPackageName;
                                        int i4 = identifier;
                                        boolean syncAutomaticallyAsUser2 =
                                                ContentResolver.getSyncAutomaticallyAsUser(
                                                        account2, str2, i4);
                                        if (!syncStateSwitchPreference2.mOneTimeSyncMode) {
                                            boolean booleanValue = ((Boolean) obj).booleanValue();
                                            if (booleanValue != syncAutomaticallyAsUser2
                                                    && (!booleanValue
                                                            || !accountSyncSettings2
                                                                    .requestAccountAccessIfNeeded(
                                                                            str3))) {
                                                ContentResolver.setSyncAutomaticallyAsUser(
                                                        account2, str2, booleanValue, i4);
                                                if (!ContentResolver
                                                                .getMasterSyncAutomaticallyAsUser(
                                                                        i4)
                                                        || !booleanValue) {
                                                    accountSyncSettings2.requestOrCancelSync(
                                                            account2, str2, booleanValue);
                                                }
                                            }
                                        } else if (!accountSyncSettings2
                                                .requestAccountAccessIfNeeded(str3)) {
                                            accountSyncSettings2.requestOrCancelSync(
                                                    account2, str2, true);
                                        }
                                        return true;
                                    }
                                });
                        z5 = z2;
                    }
                    z2 = z9;
                    ContentResolver.getIsSyncableAsUser(account, str, identifier);
                    syncStateSwitchPreference.notifyChanged();
                    syncStateSwitchPreference.notifyChanged();
                    syncStateSwitchPreference.notifyChanged();
                    z3 = true;
                    z4 = !ContentResolver.getMasterSyncAutomaticallyAsUser(identifier);
                    syncStateSwitchPreference.mOneTimeSyncMode = z4;
                    syncStateSwitchPreference.notifyChanged();
                    if (!z4) {
                        z3 = false;
                    }
                    syncStateSwitchPreference.setChecked(z3);
                    syncStateSwitchPreference.setOnPreferenceChangeListener(
                            new Preference
                                    .OnPreferenceChangeListener() { // from class:
                                                                    // com.android.settings.accounts.AccountSyncSettings$$ExternalSyntheticLambda0
                                @Override // androidx.preference.Preference.OnPreferenceChangeListener
                                public final boolean onPreferenceChange(
                                        Preference preference2, Object obj) {
                                    Account account2 = account;
                                    int i3 = AccountSyncSettings.$r8$clinit;
                                    AccountSyncSettings accountSyncSettings2 =
                                            AccountSyncSettings.this;
                                    accountSyncSettings2.getClass();
                                    String str2 = str;
                                    if (TextUtils.isEmpty(str2)) {
                                        return false;
                                    }
                                    SyncStateSwitchPreference syncStateSwitchPreference2 =
                                            syncStateSwitchPreference;
                                    String str3 = syncStateSwitchPreference2.mPackageName;
                                    int i4 = identifier;
                                    boolean syncAutomaticallyAsUser2 =
                                            ContentResolver.getSyncAutomaticallyAsUser(
                                                    account2, str2, i4);
                                    if (!syncStateSwitchPreference2.mOneTimeSyncMode) {
                                        boolean booleanValue = ((Boolean) obj).booleanValue();
                                        if (booleanValue != syncAutomaticallyAsUser2
                                                && (!booleanValue
                                                        || !accountSyncSettings2
                                                                .requestAccountAccessIfNeeded(
                                                                        str3))) {
                                            ContentResolver.setSyncAutomaticallyAsUser(
                                                    account2, str2, booleanValue, i4);
                                            if (!ContentResolver.getMasterSyncAutomaticallyAsUser(
                                                            i4)
                                                    || !booleanValue) {
                                                accountSyncSettings2.requestOrCancelSync(
                                                        account2, str2, booleanValue);
                                            }
                                        }
                                    } else if (!accountSyncSettings2.requestAccountAccessIfNeeded(
                                            str3)) {
                                        accountSyncSettings2.requestOrCancelSync(
                                                account2, str2, true);
                                    }
                                    return true;
                                }
                            });
                    z5 = z2;
                } else {
                    list = currentSyncsAsUser;
                    i = preferenceCount;
                }
                i2++;
                accountSyncSettings = this;
                currentSyncsAsUser = list;
                preferenceCount = i;
            }
            if (z5) {
                PreferenceScreen preferenceScreen = getPreferenceScreen();
                FragmentActivity activity = getActivity();
                CharSequence text = activity.getText(R.string.sync_is_failing);
                FooterPreference footerPreference = new FooterPreference(activity);
                footerPreference.setSelectable(false);
                if (TextUtils.isEmpty(text)) {
                    throw new IllegalArgumentException("Footer title cannot be empty!");
                }
                footerPreference.setTitle(text);
                if (!TextUtils.isEmpty(null)) {
                    footerPreference.setKey(null);
                }
                if (!TextUtils.isEmpty(null)
                        && !TextUtils.equals(footerPreference.mContentDescription, null)) {
                    footerPreference.mContentDescription = null;
                    footerPreference.notifyChanged();
                }
                if (!TextUtils.isEmpty(null)) {
                    footerPreference.setLearnMoreText(null);
                }
                preferenceScreen.addPreference(footerPreference);
            }
            FragmentActivity activity2 = getActivity();
            if (activity2 != null) {
                activity2.invalidateOptionsMenu();
            }
        }
    }

    public final boolean requestAccountAccessIfNeeded(String str) {
        IntentSender createRequestAccountAccessIntentSenderAsUser;
        int size;
        if (str == null) {
            return false;
        }
        try {
            int packageUidAsUser =
                    getContext()
                            .getPackageManager()
                            .getPackageUidAsUser(str, this.mUserHandle.getIdentifier());
            AccountManager accountManager =
                    (AccountManager) getContext().getSystemService(AccountManager.class);
            if (!accountManager.hasAccountAccess(this.mAccount, str, this.mUserHandle)
                    && (createRequestAccountAccessIntentSenderAsUser =
                                    accountManager.createRequestAccountAccessIntentSenderAsUser(
                                            this.mAccount, str, this.mUserHandle))
                            != null) {
                try {
                    if (this.mUidRequestCodeMap.containsKey(Integer.valueOf(packageUidAsUser))) {
                        size =
                                ((Integer)
                                                this.mUidRequestCodeMap.get(
                                                        Integer.valueOf(packageUidAsUser)))
                                        .intValue();
                    } else {
                        size = this.mUidRequestCodeMap.size() + 1;
                        this.mUidRequestCodeMap.put(
                                Integer.valueOf(packageUidAsUser), Integer.valueOf(size));
                    }
                    startIntentSenderForResult(
                            createRequestAccountAccessIntentSenderAsUser,
                            size,
                            null,
                            0,
                            0,
                            0,
                            null);
                    return true;
                } catch (IntentSender.SendIntentException e) {
                    Log.e("AccountPreferenceBase", "Error requesting account access", e);
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e("AccountPreferenceBase", "Invalid sync ", e2);
            return false;
        }
    }

    public final void requestOrCancelSync(Account account, String str, boolean z) {
        if (!z) {
            ContentResolver.cancelSyncAsUser(account, str, this.mUserHandle.getIdentifier());
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean("force", true);
        ContentResolver.requestSyncAsUser(account, str, this.mUserHandle.getIdentifier(), bundle);
    }

    public final void requestOrCancelSyncForEnabledProviders(boolean z) {
        int preferenceCount = getPreferenceScreen().getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            Preference preference = getPreferenceScreen().getPreference(i);
            if (preference instanceof SyncStateSwitchPreference) {
                SyncStateSwitchPreference syncStateSwitchPreference =
                        (SyncStateSwitchPreference) preference;
                if (syncStateSwitchPreference.mChecked) {
                    requestOrCancelSync(
                            syncStateSwitchPreference.mAccount,
                            syncStateSwitchPreference.mAuthority,
                            z);
                }
            }
        }
        if (this.mAccount != null) {
            Iterator it = this.mInvisibleAdapters.iterator();
            while (it.hasNext()) {
                requestOrCancelSync(this.mAccount, ((SyncAdapterType) it.next()).authority, z);
            }
        }
    }

    public final void updateAccountSwitches() {
        this.mInvisibleAdapters.clear();
        SyncAdapterType[] syncAdapterTypesAsUser =
                ContentResolver.getSyncAdapterTypesAsUser(this.mUserHandle.getIdentifier());
        ArrayList arrayList = new ArrayList();
        for (SyncAdapterType syncAdapterType : syncAdapterTypesAsUser) {
            if (syncAdapterType.accountType.equals(this.mAccount.type)) {
                if (syncAdapterType.isUserVisible()) {
                    if (Log.isLoggable("AccountPreferenceBase", 3)) {
                        StringBuilder sb =
                                new StringBuilder("updateAccountSwitches: added authority ");
                        sb.append(syncAdapterType.authority);
                        sb.append(" to accountType ");
                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                sb, syncAdapterType.accountType, "AccountPreferenceBase");
                    }
                    arrayList.add(syncAdapterType);
                } else {
                    this.mInvisibleAdapters.add(syncAdapterType);
                }
            }
        }
        if (Log.isLoggable("AccountPreferenceBase", 3)) {
            Log.d(
                    "AccountPreferenceBase",
                    "looking for sync adapters that match account " + this.mAccount);
        }
        cacheRemoveAllPrefs(getPreferenceScreen());
        getCachedPreference("pref_app_header");
        int size = arrayList.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            SyncAdapterType syncAdapterType2 = (SyncAdapterType) arrayList.get(i2);
            int isSyncableAsUser =
                    ContentResolver.getIsSyncableAsUser(
                            this.mAccount,
                            syncAdapterType2.authority,
                            this.mUserHandle.getIdentifier());
            if (Log.isLoggable("AccountPreferenceBase", 3)) {
                Log.d(
                        "AccountPreferenceBase",
                        "  found authority " + syncAdapterType2.authority + " " + isSyncableAsUser);
            }
            if (isSyncableAsUser > 0) {
                try {
                    addSyncStateSwitch(
                            this.mAccount,
                            syncAdapterType2.authority,
                            syncAdapterType2.getPackageName(),
                            getContext()
                                    .getPackageManager()
                                    .getPackageUidAsUser(
                                            syncAdapterType2.getPackageName(),
                                            this.mUserHandle.getIdentifier()));
                    i++;
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e(
                            "AccountPreferenceBase",
                            "No uid for package" + syncAdapterType2.getPackageName(),
                            e);
                }
            }
        }
        removeCachedPrefs(getPreferenceScreen());
        if (i == 0) {
            this.mSyncIsAdded = false;
        }
    }
}
