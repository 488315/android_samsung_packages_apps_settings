package com.android.settings.accounts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.drawer.Tile;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AccountDetailDashboardFragment extends DashboardFragment {
    Account mAccount;
    public String mAccountLabel;
    public AccountSyncPreferenceController mAccountSynController;
    String mAccountType;
    public RemoveAccountPreferenceController mRemoveAccountController;
    UserHandle mUserHandle;

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        AccountSyncPreferenceController accountSyncPreferenceController =
                new AccountSyncPreferenceController(context);
        this.mAccountSynController = accountSyncPreferenceController;
        arrayList.add(accountSyncPreferenceController);
        RemoveAccountPreferenceController removeAccountPreferenceController =
                new RemoveAccountPreferenceController(context, this);
        this.mRemoveAccountController = removeAccountPreferenceController;
        arrayList.add(removeAccountPreferenceController);
        arrayList.add(
                new AccountHeaderPreferenceController(
                        context, getSettingsLifecycle(), getActivity(), this, getArguments()));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final boolean displayTile(Tile tile) {
        String str;
        Bundle bundle;
        if (!super.displayTile(tile)
                || (str = this.mAccountType) == null
                || (bundle = tile.mMetaData) == null) {
            return false;
        }
        boolean equals = str.equals(bundle.getString("com.android.settings.ia.account"));
        if (equals) {
            if (!tile.userHandle.contains(this.mUserHandle)) {
                tile.userHandle.add(this.mUserHandle);
            }
            Intent intent = tile.mIntent;
            intent.putExtra("extra.accountName", this.mAccount.name);
            intent.putExtra("android.intent.extra.USER", this.mUserHandle);
        }
        return equals;
    }

    public void finishIfAccountMissing() {
        Context context = getContext();
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        AccountManager accountManager =
                (AccountManager) context.getSystemService(AccountManager.class);
        for (UserHandle userHandle : userManager.getUserProfiles()) {
            for (Account account : accountManager.getAccountsAsUser(userHandle.getIdentifier())) {
                if (account.equals(this.mAccount)
                        && this.mUserHandle.getIdentifier() == userHandle.getIdentifier()) {
                    return;
                }
            }
        }
        finish();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AccountDetailDashboard";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2031;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.account_type_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (this.mAccountLabel != null) {
            getActivity().setTitle(this.mAccountLabel);
        }
        updateUi();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Bundle arguments = getArguments();
        FragmentActivity activity = getActivity();
        this.mUserHandle =
                Utils.getSecureTargetUser(
                        activity.getActivityToken(),
                        (UserManager) getSystemService("user"),
                        arguments,
                        activity.getIntent().getExtras());
        if (arguments != null) {
            if (arguments.containsKey("account")) {
                this.mAccount = (Account) arguments.getParcelable("account");
            }
            if (arguments.containsKey("account_label")) {
                this.mAccountLabel = arguments.getString("account_label");
            }
            if (arguments.containsKey("account_type")) {
                this.mAccountType = arguments.getString("account_type");
            }
        }
        super.onCreate(bundle);
        getPreferenceManager().mPreferenceComparisonCallback = null;
        AccountSyncPreferenceController accountSyncPreferenceController =
                this.mAccountSynController;
        Account account = this.mAccount;
        UserHandle userHandle = this.mUserHandle;
        accountSyncPreferenceController.mAccount = account;
        accountSyncPreferenceController.mUserHandle = userHandle;
        RemoveAccountPreferenceController removeAccountPreferenceController =
                this.mRemoveAccountController;
        removeAccountPreferenceController.mAccount = account;
        removeAccountPreferenceController.mUserHandle = userHandle;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        finishIfAccountMissing();
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0167  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateUi() {
        /*
            Method dump skipped, instructions count: 559
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.accounts.AccountDetailDashboardFragment.updateUi():void");
    }
}
