package com.android.settings.accounts;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.widget.EntityHeaderController;
import com.android.settingslib.accounts.AuthenticatorHelper;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.widget.LayoutPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AccountHeaderPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, LifecycleObserver, OnResume {
    public final Account mAccount;
    public final Activity mActivity;
    public LayoutPreference mHeaderPreference;
    public final PreferenceFragmentCompat mHost;
    public final CharSequence mSummary;
    public final UserHandle mUserHandle;

    public AccountHeaderPreferenceController(
            Context context,
            Lifecycle lifecycle,
            FragmentActivity fragmentActivity,
            PreferenceFragmentCompat preferenceFragmentCompat,
            Bundle bundle) {
        super(context);
        this.mActivity = fragmentActivity;
        this.mHost = preferenceFragmentCompat;
        if (bundle == null || !bundle.containsKey("account")) {
            this.mAccount = null;
        } else {
            this.mAccount = (Account) bundle.getParcelable("account");
        }
        if (bundle == null || !bundle.containsKey("user_handle")) {
            this.mUserHandle = null;
        } else {
            this.mUserHandle = (UserHandle) bundle.getParcelable("user_handle");
        }
        if (bundle == null || !bundle.containsKey("account_summary")) {
            this.mSummary = null;
        } else {
            this.mSummary = bundle.getCharSequence("account_summary");
        }
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference("account_header");
        this.mHeaderPreference = layoutPreference;
        TextView textView =
                (TextView) layoutPreference.mRootView.findViewById(R.id.entity_header_title);
        if (textView != null) {
            textView.setSingleLine(true);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "account_header";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return (this.mAccount == null || this.mUserHandle == null) ? false : true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        AuthenticatorHelper authenticatorHelper =
                new AuthenticatorHelper(this.mContext, this.mUserHandle, null);
        View findViewById = this.mHeaderPreference.mRootView.findViewById(R.id.entity_header);
        EntityHeaderController entityHeaderController =
                new EntityHeaderController(
                        (FragmentActivity) this.mActivity, this.mHost, findViewById);
        Account account = this.mAccount;
        entityHeaderController.mLabel = account.name;
        entityHeaderController.mSummary = this.mSummary;
        entityHeaderController.setIcon(
                authenticatorHelper.getDrawableForType(this.mContext, account.type));
        entityHeaderController.done(true);
    }
}
