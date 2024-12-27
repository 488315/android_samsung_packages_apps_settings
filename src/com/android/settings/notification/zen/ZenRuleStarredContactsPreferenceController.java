package com.android.settings.notification.zen;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.core.lifecycle.Lifecycle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenRuleStarredContactsPreferenceController
        extends AbstractZenCustomRulePreferenceController
        implements Preference.OnPreferenceClickListener {
    public final Intent mFallbackIntent;
    public final PackageManager mPackageManager;
    public final int mPriorityCategory;
    public final Intent mStarredContactsIntent;

    public ZenRuleStarredContactsPreferenceController(Context context, Lifecycle lifecycle) {
        super(context, "zen_mode_starred_contacts_callers", lifecycle);
        this.mPriorityCategory = 3;
        this.mPackageManager = this.mContext.getPackageManager();
        String string = context.getString(R.string.config_contacts_package_name);
        this.mStarredContactsIntent =
                new Intent("com.android.contacts.action.LIST_STARRED")
                        .setPackage(string)
                        .setFlags(268468224);
        Intent intent = new Intent("android.intent.action.MAIN");
        this.mFallbackIntent = intent;
        intent.setPackage(string);
        intent.addCategory("android.intent.category.APP_CONTACTS");
        intent.setFlags(268468224);
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(this.KEY);
        if (findPreference != null) {
            findPreference.setOnPreferenceClickListener(this);
        }
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return this.KEY;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final CharSequence getSummary() {
        return this.mBackend.getStarredContactsSummary();
    }

    @Override // com.android.settings.notification.zen.AbstractZenCustomRulePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (!super.isAvailable() || this.mRule.getZenPolicy() == null) {
            return false;
        }
        if (this.mStarredContactsIntent.resolveActivity(this.mPackageManager) == null
                && this.mFallbackIntent.resolveActivity(this.mPackageManager) == null) {
            return false;
        }
        int i = this.mPriorityCategory;
        return i == 3
                ? this.mRule.getZenPolicy().getPriorityCallSenders() == 3
                : i == 2 && this.mRule.getZenPolicy().getPriorityMessageSenders() == 3;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        if (this.mStarredContactsIntent.resolveActivity(this.mPackageManager) != null) {
            this.mContext.startActivity(this.mStarredContactsIntent);
            return true;
        }
        this.mContext.startActivity(this.mFallbackIntent);
        return true;
    }
}
