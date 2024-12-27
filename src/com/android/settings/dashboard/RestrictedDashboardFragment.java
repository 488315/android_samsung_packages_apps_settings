package com.android.settings.dashboard;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.RestrictionsManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.enterprise.ActionDisabledByAdminDialogHelper;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class RestrictedDashboardFragment extends DashboardFragment {
    public AlertDialog mActionDisabledDialog;
    public boolean mChallengeRequested;
    public boolean mChallengeSucceeded;
    public TextView mEmptyTextView;
    public RestrictedLockUtils.EnforcedAdmin mEnforcedAdmin;
    public boolean mIsAdminUser;
    public final String mRestrictionKey;
    public RestrictionsManager mRestrictionsManager;
    public UserManager mUserManager;
    public boolean mOnlyAvailableForAdmins = false;
    public final AnonymousClass1 mScreenOffReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.settings.dashboard.RestrictedDashboardFragment.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    RestrictedDashboardFragment restrictedDashboardFragment =
                            RestrictedDashboardFragment.this;
                    if (restrictedDashboardFragment.mChallengeRequested) {
                        return;
                    }
                    restrictedDashboardFragment.mChallengeSucceeded = false;
                    restrictedDashboardFragment.mChallengeRequested = false;
                }
            };

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.dashboard.RestrictedDashboardFragment$1] */
    public RestrictedDashboardFragment(String str) {
        this.mRestrictionKey = str;
    }

    public final RestrictedLockUtils.EnforcedAdmin getRestrictionEnforcedAdmin() {
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        getActivity(), UserHandle.myUserId(), this.mRestrictionKey);
        this.mEnforcedAdmin = checkIfRestrictionEnforced;
        if (checkIfRestrictionEnforced != null && checkIfRestrictionEnforced.user == null) {
            checkIfRestrictionEnforced.user = UserHandle.of(UserHandle.myUserId());
        }
        return this.mEnforcedAdmin;
    }

    public final boolean isUiRestricted() {
        boolean z;
        String str = this.mRestrictionKey;
        return !(str == null
                        || "restrict_if_overridable".equals(str)
                        || !this.mUserManager.hasUserRestriction(this.mRestrictionKey)
                        || this.mRestrictionsManager.hasRestrictionsProvider())
                || (!((z = this.mChallengeRequested) && this.mChallengeSucceeded) && z)
                || (!this.mIsAdminUser && this.mOnlyAvailableForAdmins);
    }

    public final boolean isUiRestrictedByOnlyAdmin() {
        return isUiRestricted()
                && !this.mUserManager.hasBaseUserRestriction(
                        this.mRestrictionKey, UserHandle.of(UserHandle.myUserId()))
                && (this.mIsAdminUser || !this.mOnlyAvailableForAdmins);
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mEmptyTextView = (TextView) getActivity().findViewById(R.id.empty);
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i != 12309) {
            super.onActivityResult(i, i2, intent);
        } else if (i2 != -1) {
            this.mChallengeSucceeded = false;
        } else {
            this.mChallengeSucceeded = true;
            this.mChallengeRequested = false;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mRestrictionsManager = (RestrictionsManager) getSystemService("restrictions");
        UserManager userManager = (UserManager) getSystemService("user");
        this.mUserManager = userManager;
        this.mIsAdminUser = userManager.isAdminUser();
        if (bundle != null) {
            this.mChallengeSucceeded = bundle.getBoolean("chsc", false);
            this.mChallengeRequested = bundle.getBoolean("chrq", false);
        }
        IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        getActivity().registerReceiver(this.mScreenOffReceiver, intentFilter);
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public void onDataSetChanged() {
        AlertDialog alertDialog;
        highlightPreferenceIfNeeded();
        if (!isUiRestrictedByOnlyAdmin()
                || ((alertDialog = this.mActionDisabledDialog) != null
                        && alertDialog.isShowing())) {
            TextView textView = this.mEmptyTextView;
            if (textView != null) {
                setEmptyView(textView);
            }
        } else {
            AlertDialog.Builder prepareDialogBuilder =
                    new ActionDisabledByAdminDialogHelper(getActivity(), null)
                            .prepareDialogBuilder(
                                    this.mRestrictionKey, getRestrictionEnforcedAdmin());
            prepareDialogBuilder.P.mOnDismissListener =
                    new DialogInterface
                            .OnDismissListener() { // from class:
                                                   // com.android.settings.dashboard.RestrictedDashboardFragment$$ExternalSyntheticLambda0
                        @Override // android.content.DialogInterface.OnDismissListener
                        public final void onDismiss(DialogInterface dialogInterface) {
                            RestrictedDashboardFragment.this.getActivity().finish();
                        }
                    };
            this.mActionDisabledDialog = prepareDialogBuilder.show();
            setEmptyView(new View(getContext()));
        }
        super.onDataSetChanged();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onDestroy() {
        getActivity().unregisterReceiver(this.mScreenOffReceiver);
        super.onDestroy();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onResume() {
        Intent createLocalApprovalIntent;
        super.onResume();
        String str = this.mRestrictionKey;
        if (str == null) {
            return;
        }
        if (("restrict_if_overridable".equals(str)
                        || this.mUserManager.hasUserRestriction(this.mRestrictionKey))
                && this.mRestrictionsManager.hasRestrictionsProvider()
                && !this.mChallengeSucceeded
                && !this.mChallengeRequested
                && this.mRestrictionsManager.hasRestrictionsProvider()
                && (createLocalApprovalIntent =
                                this.mRestrictionsManager.createLocalApprovalIntent())
                        != null) {
            this.mChallengeRequested = true;
            this.mChallengeSucceeded = false;
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putString(
                    "android.request.mesg",
                    getResources()
                            .getString(com.android.settings.R.string.restr_pin_enter_admin_pin));
            createLocalApprovalIntent.putExtra(
                    "android.content.extra.REQUEST_BUNDLE", persistableBundle);
            startActivityForResult(createLocalApprovalIntent, 12309);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (getActivity().isChangingConfigurations()) {
            bundle.putBoolean("chrq", this.mChallengeRequested);
            bundle.putBoolean("chsc", this.mChallengeSucceeded);
        }
    }
}
