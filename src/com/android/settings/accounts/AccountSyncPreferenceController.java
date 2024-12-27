package com.android.settings.accounts;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncAdapterType;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.activityembedding.ActivityEmbeddingUtils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.accounts.AuthenticatorHelper;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.scloud.lib.setting.RPCSyncSettingContract$Method;
import com.samsung.android.scloud.lib.setting.SyncSettingProviderClient;
import com.samsung.android.settings.account.AccountUtils;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AccountSyncPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, AuthenticatorHelper.OnAccountsUpdateListener {
    public Account mAccount;
    public SecInsetCategoryPreference mInsetCategoryPreference;
    public Preference mPreference;
    public UserHandle mUserHandle;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference("account_sync");
        this.mInsetCategoryPreference =
                (SecInsetCategoryPreference) preferenceScreen.findPreference("inset_category");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "account_sync";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final CharSequence getSummary() {
        boolean z;
        Context context = this.mContext;
        Uri uri = RPCSyncSettingContract$Method.CONTENT_URI;
        SyncSettingProviderClient syncSettingProviderClient = new SyncSettingProviderClient();
        syncSettingProviderClient.context = context;
        syncSettingProviderClient.rpcUri = uri;
        syncSettingProviderClient.authority = "setting";
        syncSettingProviderClient.settingProvider = null;
        syncSettingProviderClient.applicationType = 2;
        SyncSettingProviderClient.TAG = "[scsettingstatus][2.0.27.0]";
        Bundle profile = syncSettingProviderClient.getProfile();
        if (profile != null) {
            SemLog.i("AccountSyncController", "SamsungCloudRPCContract result");
            if (profile.getInt("precondition") == 0) {
                SemLog.i("AccountSyncController", "NO_PRECONDITION");
                ArrayList<String> arrayList = new ArrayList<>();
                try {
                    Log.i(SyncSettingProviderClient.TAG, "getSyncableAppList");
                    Bundle bundle = new Bundle();
                    bundle.putString("authority", syncSettingProviderClient.authority);
                    bundle.putInt("application_type", syncSettingProviderClient.applicationType);
                    arrayList =
                            syncSettingProviderClient
                                    .context
                                    .getContentResolver()
                                    .call(
                                            syncSettingProviderClient.rpcUri,
                                            "get_syncable_app_list",
                                            (String) null,
                                            bundle)
                                    .getStringArrayList("syncable_app_list");
                } catch (Exception e) {
                    Log.e(SyncSettingProviderClient.TAG, e.getMessage());
                }
                SemLog.i("AccountSyncController", "getSyncableAppList() : " + arrayList);
                if (arrayList.isEmpty()) {
                    return null;
                }
                int size = arrayList.size();
                SemLog.i("AccountSyncController", "getSyncableAppList() size : " + size);
                Iterator<String> it = arrayList.iterator();
                int i = 0;
                while (it.hasNext()) {
                    String next = it.next();
                    try {
                        Log.i(SyncSettingProviderClient.TAG, "getAutoSync: " + next);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("authority", syncSettingProviderClient.authority);
                        bundle2.putInt(
                                "application_type", syncSettingProviderClient.applicationType);
                        bundle2.putString("queried_authority", next);
                        z =
                                syncSettingProviderClient
                                        .context
                                        .getContentResolver()
                                        .call(
                                                syncSettingProviderClient.rpcUri,
                                                "get_auto_sync",
                                                (String) null,
                                                bundle2)
                                        .getBoolean("auto_sync", false);
                    } catch (Exception e2) {
                        Log.e(SyncSettingProviderClient.TAG, e2.getMessage());
                        z = false;
                    }
                    if (z) {
                        i++;
                    }
                }
                return i == 0
                        ? this.mContext.getString(R.string.account_sync_summary_all_off)
                        : i == size
                                ? this.mContext.getString(R.string.account_sync_summary_all_on)
                                : this.mContext.getString(
                                        R.string.account_sync_summary_some_on,
                                        Integer.valueOf(i),
                                        Integer.valueOf(size));
            }
        }
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean getSummaryOnBackground() {
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        if (!"account_sync".equals(preference.getKey())) {
            return false;
        }
        if ("com.osp.app.signin".equals(this.mAccount.type)
                && Utils.hasPackage(
                        this.mContext,
                        "com.samsung.android.scloud",
                        this.mUserHandle.getIdentifier())
                && !SemPersonaManager.isSecureFolderId(this.mUserHandle.getIdentifier())) {
            boolean z = AccountUtils.SupportTwoPhone;
            Intent intent = new Intent("com.samsung.android.scloud.SYNC_SETTINGS");
            if (!ActivityEmbeddingUtils.isEmbeddingActivityEnabled(this.mContext)) {
                intent.addFlags(268468224);
            }
            this.mContext.startActivityAsUser(intent, this.mUserHandle);
            return true;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("account", this.mAccount);
        bundle.putParcelable("android.intent.extra.USER", this.mUserHandle);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = AccountSyncSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = bundle;
        launchRequest.mSourceMetricsCategory = 8;
        subSettingLauncher.setTitleRes(R.string.account_sync_title, null);
        subSettingLauncher.launch();
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.accounts.AuthenticatorHelper.OnAccountsUpdateListener
    public final void onAccountsUpdate(UserHandle userHandle) {
        updateSummary(this.mPreference);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        Account account;
        if (this.mPreference != null
                && (account = this.mAccount) != null
                && "com.osp.app.signin".equals(account.type)) {
            UserManager userManager = (UserManager) this.mContext.getSystemService("user");
            if (SemPersonaManager.isSecureFolderId(UserHandle.getCallingUserId())
                    || userManager
                            .semGetSemUserInfo(UserHandle.semGetMyUserId())
                            .hasFlags(16777216)) {
                this.mPreference.setVisible(false);
                this.mInsetCategoryPreference.setVisible(false);
            }
        }
        updateSummary(preference);
    }

    public void updateSummary(Preference preference) {
        int i;
        Account account = this.mAccount;
        if (account == null) {
            return;
        }
        if ("com.osp.app.signin".equals(account.type)) {
            SemLog.i("AccountSyncController", "updateSummary - SAMSUNG_ACCOUNT_TYPE");
            refreshSummary(preference);
            return;
        }
        int identifier = this.mUserHandle.getIdentifier();
        SyncAdapterType[] syncAdapterTypesAsUser =
                ContentResolver.getSyncAdapterTypesAsUser(identifier);
        int i2 = 0;
        if (syncAdapterTypesAsUser != null) {
            int length = syncAdapterTypesAsUser.length;
            i = 0;
            int i3 = 0;
            while (i2 < length) {
                SyncAdapterType syncAdapterType = syncAdapterTypesAsUser[i2];
                if (syncAdapterType.accountType.equals(this.mAccount.type)
                        && syncAdapterType.isUserVisible()
                        && ContentResolver.getIsSyncableAsUser(
                                        this.mAccount, syncAdapterType.authority, identifier)
                                > 0) {
                    i++;
                    boolean syncAutomaticallyAsUser =
                            ContentResolver.getSyncAutomaticallyAsUser(
                                    this.mAccount, syncAdapterType.authority, identifier);
                    if ((!ContentResolver.getMasterSyncAutomaticallyAsUser(identifier))
                            || syncAutomaticallyAsUser) {
                        i3++;
                    }
                }
                i2++;
            }
            i2 = i3;
        } else {
            i = 0;
        }
        if (i2 == 0) {
            preference.setSummary(R.string.account_sync_summary_all_off);
        } else if (i2 == i) {
            preference.setSummary(R.string.account_sync_summary_all_on);
        } else {
            preference.setSummary(
                    this.mContext.getString(
                            R.string.account_sync_summary_some_on,
                            Integer.valueOf(i2),
                            Integer.valueOf(i)));
        }
    }
}
