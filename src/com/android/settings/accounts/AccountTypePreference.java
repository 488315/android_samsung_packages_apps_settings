package com.android.settings.accounts;

import android.accounts.Account;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.widget.AppPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AccountTypePreference extends AppPreference
        implements Preference.OnPreferenceClickListener {
    public final String mFragment;
    public final Bundle mFragmentArguments;
    public final int mMetricsCategory;
    public final CharSequence mSummary;
    public final CharSequence mTitle;
    public final int mTitleResId;
    public final String mTitleResPackageName;

    public AccountTypePreference(
            Context context,
            int i,
            Account account,
            String str,
            int i2,
            CharSequence charSequence,
            String str2,
            Bundle bundle,
            Drawable drawable) {
        super(context);
        setLayoutResource(R.layout.sec_preference_account);
        String str3 = account.name;
        this.mTitle = str3;
        this.mTitleResPackageName = str;
        this.mTitleResId = i2;
        this.mSummary = charSequence;
        this.mFragment = str2;
        this.mFragmentArguments = bundle;
        this.mMetricsCategory = i;
        setKey(String.valueOf(account.hashCode()));
        setTitle(str3);
        setSingleLineTitle(true);
        setSummary(charSequence);
        setIcon(drawable);
        setOnPreferenceClickListener(this);
    }

    @Override // androidx.preference.Preference
    public final CharSequence getSummary() {
        return this.mSummary;
    }

    @Override // androidx.preference.Preference
    public final CharSequence getTitle() {
        return this.mTitle;
    }

    @Override // com.android.settingslib.widget.AppPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.appendix);
        if (textView != null) {
            textView.setVisibility(8);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        Intent createConfirmDeviceCredentialIntent;
        if ("com.sec.android.softphone".equals(this.mTitleResPackageName)) {
            Intent intent = new Intent();
            intent.setComponent(
                    new ComponentName(
                            "com.sec.android.softphone",
                            "com.sec.android.softphone.SoftphoneMainActivity"));
            intent.setFlags(268468224);
            getContext().startActivity(intent);
            return true;
        }
        if (this.mFragment == null) {
            return false;
        }
        UserManager userManager = (UserManager) getContext().getSystemService("user");
        UserHandle userHandle =
                (UserHandle) this.mFragmentArguments.getParcelable("android.intent.extra.USER");
        if (userHandle != null
                && Utils.startQuietModeDialogIfNecessary(
                        getContext(), userManager, userHandle.getIdentifier())) {
            return true;
        }
        if (userHandle != null) {
            Context context = getContext();
            int identifier = userHandle.getIdentifier();
            StringBuilder sb = Utils.sBuilder;
            try {
                if (ActivityManager.getService().isUserRunning(identifier, 2)
                        && new LockPatternUtils(context).isSecure(identifier)
                        && (createConfirmDeviceCredentialIntent =
                                        ((KeyguardManager) context.getSystemService("keyguard"))
                                                .createConfirmDeviceCredentialIntent(
                                                        null, null, identifier))
                                != null) {
                    context.startActivity(createConfirmDeviceCredentialIntent);
                    return true;
                }
            } catch (RemoteException unused) {
            }
        }
        this.mFragmentArguments.putCharSequence("account_summary", this.mSummary);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
        String str = this.mFragment;
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = str;
        launchRequest.mArguments = this.mFragmentArguments;
        subSettingLauncher.setTitleRes(this.mTitleResId, this.mTitleResPackageName);
        launchRequest.mSourceMetricsCategory = this.mMetricsCategory;
        subSettingLauncher.launch();
        return true;
    }
}
