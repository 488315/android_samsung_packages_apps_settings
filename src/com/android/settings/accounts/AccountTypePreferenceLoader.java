package com.android.settings.accounts;

import android.accounts.Account;
import android.accounts.AuthenticatorDescription;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;

import androidx.collection.ArraySet;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceManager;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.location.LocationSettings;
import com.android.settingslib.accounts.AuthenticatorHelper;
import com.android.settingslib.core.instrumentation.Instrumentable;

import java.util.HashMap;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AccountTypePreferenceLoader {
    public final AuthenticatorHelper mAuthenticatorHelper;
    public final PreferenceFragmentCompat mFragment;
    public final UserHandle mUserHandle;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class FragmentStarter implements Preference.OnPreferenceClickListener {
        public final String mClass;

        public FragmentStarter(String str) {
            this.mClass = str;
        }

        @Override // androidx.preference.Preference.OnPreferenceClickListener
        public final boolean onPreferenceClick(Preference preference) {
            AccountTypePreferenceLoader accountTypePreferenceLoader =
                    AccountTypePreferenceLoader.this;
            PreferenceManager.OnPreferenceTreeClickListener onPreferenceTreeClickListener =
                    accountTypePreferenceLoader.mFragment;
            int metricsCategory =
                    onPreferenceTreeClickListener instanceof Instrumentable
                            ? ((Instrumentable) onPreferenceTreeClickListener).getMetricsCategory()
                            : 0;
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(preference.getContext());
            subSettingLauncher.setTitleRes(R.string.location_settings_title, null);
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            String str = this.mClass;
            launchRequest.mDestinationName = str;
            launchRequest.mSourceMetricsCategory = metricsCategory;
            subSettingLauncher.launch();
            if (!str.equals(LocationSettings.class.getName())) {
                return true;
            }
            accountTypePreferenceLoader
                    .mFragment
                    .getActivity()
                    .sendBroadcast(
                            new Intent("com.android.settings.accounts.LAUNCHING_LOCATION_SETTINGS"),
                            "android.permission.WRITE_SECURE_SETTINGS");
            return true;
        }
    }

    public AccountTypePreferenceLoader(
            PreferenceFragmentCompat preferenceFragmentCompat,
            AuthenticatorHelper authenticatorHelper,
            UserHandle userHandle) {
        this.mFragment = preferenceFragmentCompat;
        this.mAuthenticatorHelper = authenticatorHelper;
        this.mUserHandle = userHandle;
    }

    public void filterBlockedFragments(PreferenceGroup preferenceGroup, Set<String> set) {
        if (preferenceGroup == null) {
            return;
        }
        for (int i = 0; i < preferenceGroup.getPreferenceCount(); i++) {
            Preference preference = preferenceGroup.getPreference(i);
            if (preference instanceof PreferenceGroup) {
                filterBlockedFragments((PreferenceGroup) preference, set);
            }
            String fragment = preference.getFragment();
            if (fragment != null && !set.contains(fragment)) {
                preference.setOnPreferenceClickListener(
                        new AccountTypePreferenceLoader$$ExternalSyntheticLambda0());
            }
        }
    }

    public Set<String> generateFragmentAllowlist(PreferenceGroup preferenceGroup) {
        ArraySet arraySet = new ArraySet();
        if (preferenceGroup == null) {
            return arraySet;
        }
        for (int i = 0; i < preferenceGroup.getPreferenceCount(); i++) {
            Preference preference = preferenceGroup.getPreference(i);
            if (preference instanceof PreferenceGroup) {
                arraySet.addAll(generateFragmentAllowlist((PreferenceGroup) preference));
            }
            String fragment = preference.getFragment();
            if (!TextUtils.isEmpty(fragment)) {
                arraySet.add(fragment);
            }
        }
        return arraySet;
    }

    public final void updatePreferenceIntents(
            PreferenceGroup preferenceGroup, final String str, Account account) {
        final PackageManager packageManager = this.mFragment.getActivity().getPackageManager();
        int i = 0;
        while (i < preferenceGroup.getPreferenceCount()) {
            Preference preference = preferenceGroup.getPreference(i);
            if (preference instanceof PreferenceGroup) {
                updatePreferenceIntents((PreferenceGroup) preference, str, account);
            }
            Intent intent = preference.getIntent();
            if (intent != null) {
                if (TextUtils.equals(
                        intent.getAction(), "android.settings.LOCATION_SOURCE_SETTINGS")) {
                    preference.setOnPreferenceClickListener(
                            new FragmentStarter(LocationSettings.class.getName()));
                } else if (packageManager.resolveActivityAsUser(
                                intent, 65536, this.mUserHandle.getIdentifier())
                        == null) {
                    preferenceGroup.removePreference(preference);
                } else {
                    intent.putExtra("account", account);
                    if ("com.osp.app.signin".equals(str)) {
                        intent.setFlags(intent.getFlags());
                    } else {
                        intent.setFlags(intent.getFlags() | 268435456);
                    }
                    preference.setOnPreferenceClickListener(
                            new Preference.OnPreferenceClickListener() { // from class:
                                // com.android.settings.accounts.AccountTypePreferenceLoader.1
                                @Override // androidx.preference.Preference.OnPreferenceClickListener
                                public final boolean onPreferenceClick(Preference preference2) {
                                    Intent intent2 = preference2.getIntent();
                                    PackageManager packageManager2 = packageManager;
                                    AccountTypePreferenceLoader accountTypePreferenceLoader =
                                            AccountTypePreferenceLoader.this;
                                    AuthenticatorDescription authenticatorDescription =
                                            (AuthenticatorDescription)
                                                    ((HashMap)
                                                                    accountTypePreferenceLoader
                                                                            .mAuthenticatorHelper
                                                                            .mTypeToAuthDescription)
                                                            .get(str);
                                    ResolveInfo resolveActivityAsUser =
                                            packageManager2.resolveActivityAsUser(
                                                    intent2,
                                                    0,
                                                    accountTypePreferenceLoader.mUserHandle
                                                            .getIdentifier());
                                    if (resolveActivityAsUser != null) {
                                        try {
                                            if (resolveActivityAsUser
                                                            .activityInfo
                                                            .applicationInfo
                                                            .uid
                                                    == packageManager2.getApplicationInfoAsUser(
                                                                    authenticatorDescription
                                                                            .packageName,
                                                                    0,
                                                                    accountTypePreferenceLoader
                                                                            .mUserHandle)
                                                            .uid) {
                                                intent2.setClipData(
                                                        ClipData.newPlainText(null, null));
                                                accountTypePreferenceLoader
                                                        .mFragment
                                                        .getActivity()
                                                        .startActivityAsUser(
                                                                intent2,
                                                                accountTypePreferenceLoader
                                                                        .mUserHandle);
                                                return true;
                                            }
                                        } catch (PackageManager.NameNotFoundException e) {
                                            Log.e(
                                                    "AccountTypePrefLoader",
                                                    "Intent considered unsafe due to exception.",
                                                    e);
                                        }
                                    }
                                    Log.e(
                                            "AccountTypePrefLoader",
                                            "Refusing to launch authenticator intent becauseit"
                                                + " exploits Settings permissions: "
                                                    + intent2);
                                    return true;
                                }
                            });
                }
            }
            i++;
        }
    }
}
