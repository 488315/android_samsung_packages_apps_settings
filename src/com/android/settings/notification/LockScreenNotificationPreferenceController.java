package com.android.settings.notification;

import android.app.admin.DevicePolicyManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.RestrictedListPreference;
import com.android.settings.Utils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import java.util.ArrayList;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LockScreenNotificationPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin,
                Preference.OnPreferenceChangeListener,
                LifecycleObserver,
                OnResume,
                OnPause {
    public RestrictedListPreference mLockscreen;
    public RestrictedListPreference mLockscreenProfile;
    public int mLockscreenSelectedValue;
    public int mLockscreenSelectedValueProfile;
    public final int mProfileUserId;
    public final boolean mSecure;
    public final boolean mSecureProfile;
    public final String mSettingKey;
    public SettingObserver mSettingObserver;
    public final String mWorkSettingCategoryKey;
    public final String mWorkSettingKey;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingObserver extends ContentObserver {
        public final Uri LOCK_SCREEN_PRIVATE_URI;
        public final Uri LOCK_SCREEN_SHOW_URI;

        public SettingObserver() {
            super(new Handler());
            this.LOCK_SCREEN_PRIVATE_URI =
                    Settings.Secure.getUriFor("lock_screen_allow_private_notifications");
            this.LOCK_SCREEN_SHOW_URI = Settings.Secure.getUriFor("lock_screen_show_notifications");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (this.LOCK_SCREEN_PRIVATE_URI.equals(uri) || this.LOCK_SCREEN_SHOW_URI.equals(uri)) {
                LockScreenNotificationPreferenceController.this.updateLockscreenNotifications();
                LockScreenNotificationPreferenceController
                        lockScreenNotificationPreferenceController =
                                LockScreenNotificationPreferenceController.this;
                if (lockScreenNotificationPreferenceController.mProfileUserId != -10000) {
                    lockScreenNotificationPreferenceController
                            .updateLockscreenNotificationsForProfile();
                }
            }
        }
    }

    public LockScreenNotificationPreferenceController(
            Context context, String str, String str2, String str3) {
        super(context);
        this.mSettingKey = str;
        this.mWorkSettingCategoryKey = str2;
        this.mWorkSettingKey = str3;
        int managedProfileId =
                Utils.getManagedProfileId(UserManager.get(context), UserHandle.myUserId());
        this.mProfileUserId = managedProfileId;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        LockPatternUtils lockPatternUtils =
                featureFactoryImpl.getSecurityFeatureProvider().getLockPatternUtils(context);
        this.mSecure = lockPatternUtils.isSecure(UserHandle.myUserId());
        this.mSecureProfile =
                managedProfileId != -10000 && lockPatternUtils.isSecure(managedProfileId);
    }

    public static int getSummaryResource(Context context) {
        boolean z = true;
        boolean z2 =
                Settings.Secure.getInt(
                                context.getContentResolver(), "lock_screen_show_notifications", 1)
                        != 0;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        if (featureFactoryImpl
                .getSecurityFeatureProvider()
                .getLockPatternUtils(context)
                .isSecure(UserHandle.myUserId())) {
            if (Settings.Secure.getIntForUser(
                            context.getContentResolver(),
                            "lock_screen_allow_private_notifications",
                            0,
                            UserHandle.myUserId())
                    == 0) {
                z = false;
            }
        }
        return !z2
                ? R.string.lock_screen_notifications_summary_disable
                : z
                        ? R.string.lock_screen_notifications_summary_show
                        : R.string.lock_screen_notifications_summary_hide;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        String str = this.mSettingKey;
        RestrictedListPreference restrictedListPreference =
                (RestrictedListPreference) preferenceScreen.findPreference(str);
        this.mLockscreen = restrictedListPreference;
        if (restrictedListPreference == null) {
            Log.i("LockScreenNotifPref", "Preference not found: ".concat(str));
            return;
        }
        int i = this.mProfileUserId;
        String str2 = this.mWorkSettingKey;
        if (i != -10000) {
            RestrictedListPreference restrictedListPreference2 =
                    (RestrictedListPreference) preferenceScreen.findPreference(str2);
            this.mLockscreenProfile = restrictedListPreference2;
            restrictedListPreference2.mRequiresActiveUnlockedProfile = true;
            restrictedListPreference2.mProfileUserId = i;
        } else {
            setVisible(preferenceScreen, str2, false);
            setVisible(preferenceScreen, this.mWorkSettingCategoryKey, false);
        }
        this.mSettingObserver = new SettingObserver();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        String string = this.mContext.getString(R.string.lock_screen_notifications_summary_show);
        String num = Integer.toString(R.string.lock_screen_notifications_summary_show);
        arrayList.add(string);
        arrayList2.add(num);
        setRestrictedIfNotificationFeaturesDisabled(string, num, 12);
        if (this.mSecure) {
            String string2 =
                    this.mContext.getString(R.string.lock_screen_notifications_summary_hide);
            String num2 = Integer.toString(R.string.lock_screen_notifications_summary_hide);
            arrayList.add(string2);
            arrayList2.add(num2);
            setRestrictedIfNotificationFeaturesDisabled(string2, num2, 4);
        }
        arrayList.add(this.mContext.getString(R.string.lock_screen_notifications_summary_disable));
        arrayList2.add(Integer.toString(R.string.lock_screen_notifications_summary_disable));
        this.mLockscreen.mEntries =
                (CharSequence[]) arrayList.toArray(new CharSequence[arrayList.size()]);
        this.mLockscreen.mEntryValues =
                (CharSequence[]) arrayList2.toArray(new CharSequence[arrayList2.size()]);
        updateLockscreenNotifications();
        RestrictedListPreference restrictedListPreference3 = this.mLockscreen;
        if (restrictedListPreference3.mEntries.length > 1) {
            restrictedListPreference3.setOnPreferenceChangeListener(this);
        } else {
            restrictedListPreference3.setEnabled(false);
        }
        if (this.mLockscreenProfile == null) {
            AbsAdapter$$ExternalSyntheticOutline0.m(
                    "Preference not found: ", str2, "LockScreenNotifPref");
            return;
        }
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class);
        final int i2 = 0;
        String string3 =
                devicePolicyManager
                        .getResources()
                        .getString(
                                "Settings.LOCK_SCREEN_SHOW_WORK_NOTIFICATION_CONTENT",
                                new Supplier(
                                        this) { // from class:
                                                // com.android.settings.notification.LockScreenNotificationPreferenceController$$ExternalSyntheticLambda0
                                    public final /* synthetic */
                                    LockScreenNotificationPreferenceController f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        String string4;
                                        String string5;
                                        int i3 = i2;
                                        LockScreenNotificationPreferenceController
                                                lockScreenNotificationPreferenceController =
                                                        this.f$0;
                                        switch (i3) {
                                            case 0:
                                                string4 =
                                                        lockScreenNotificationPreferenceController
                                                                .mContext.getString(
                                                                R.string
                                                                        .lock_screen_notifications_summary_show_profile);
                                                return string4;
                                            default:
                                                string5 =
                                                        lockScreenNotificationPreferenceController
                                                                .mContext.getString(
                                                                R.string
                                                                        .lock_screen_notifications_summary_hide_profile);
                                                return string5;
                                        }
                                    }
                                });
        String num3 = Integer.toString(R.string.lock_screen_notifications_summary_show_profile);
        arrayList3.add(string3);
        arrayList4.add(num3);
        setRestrictedIfNotificationFeaturesDisabled(string3, num3, 12);
        if (this.mSecureProfile) {
            final int i3 = 1;
            String string4 =
                    devicePolicyManager
                            .getResources()
                            .getString(
                                    "Settings.LOCK_SCREEN_HIDE_WORK_NOTIFICATION_CONTENT",
                                    new Supplier(
                                            this) { // from class:
                                                    // com.android.settings.notification.LockScreenNotificationPreferenceController$$ExternalSyntheticLambda0
                                        public final /* synthetic */
                                        LockScreenNotificationPreferenceController f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            String string42;
                                            String string5;
                                            int i32 = i3;
                                            LockScreenNotificationPreferenceController
                                                    lockScreenNotificationPreferenceController =
                                                            this.f$0;
                                            switch (i32) {
                                                case 0:
                                                    string42 =
                                                            lockScreenNotificationPreferenceController
                                                                    .mContext.getString(
                                                                    R.string
                                                                            .lock_screen_notifications_summary_show_profile);
                                                    return string42;
                                                default:
                                                    string5 =
                                                            lockScreenNotificationPreferenceController
                                                                    .mContext.getString(
                                                                    R.string
                                                                            .lock_screen_notifications_summary_hide_profile);
                                                    return string5;
                                            }
                                        }
                                    });
            String num4 = Integer.toString(R.string.lock_screen_notifications_summary_hide_profile);
            arrayList3.add(string4);
            arrayList4.add(num4);
            setRestrictedIfNotificationFeaturesDisabled(string4, num4, 4);
        }
        this.mLockscreenProfile.mEntries =
                (CharSequence[]) arrayList3.toArray(new CharSequence[arrayList3.size()]);
        this.mLockscreenProfile.mEntryValues =
                (CharSequence[]) arrayList4.toArray(new CharSequence[arrayList4.size()]);
        updateLockscreenNotificationsForProfile();
        RestrictedListPreference restrictedListPreference4 = this.mLockscreenProfile;
        if (restrictedListPreference4.mEntries.length > 1) {
            restrictedListPreference4.setOnPreferenceChangeListener(this);
        } else {
            restrictedListPreference4.setEnabled(false);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return false;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        SettingObserver settingObserver = this.mSettingObserver;
        if (settingObserver != null) {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            settingObserver.getClass();
            contentResolver.unregisterContentObserver(settingObserver);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        int parseInt;
        String key = preference.getKey();
        boolean equals = TextUtils.equals(this.mWorkSettingKey, key);
        int i = this.mProfileUserId;
        if (equals) {
            int parseInt2 = Integer.parseInt((String) obj);
            if (parseInt2 == this.mLockscreenSelectedValueProfile) {
                return false;
            }
            Settings.Secure.putIntForUser(
                    this.mContext.getContentResolver(),
                    "lock_screen_allow_private_notifications",
                    parseInt2 == R.string.lock_screen_notifications_summary_show_profile ? 1 : 0,
                    i);
            this.mLockscreenSelectedValueProfile = parseInt2;
            return true;
        }
        if (!TextUtils.equals(this.mSettingKey, key)
                || (parseInt = Integer.parseInt((String) obj)) == this.mLockscreenSelectedValue) {
            return false;
        }
        int i2 = parseInt != R.string.lock_screen_notifications_summary_disable ? 1 : 0;
        Settings.Secure.putIntForUser(
                this.mContext.getContentResolver(),
                "lock_screen_allow_private_notifications",
                parseInt == R.string.lock_screen_notifications_summary_show ? 1 : 0,
                i);
        Settings.Secure.putIntForUser(
                this.mContext.getContentResolver(), "lock_screen_show_notifications", i2, i);
        this.mLockscreenSelectedValue = parseInt;
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        SettingObserver settingObserver = this.mSettingObserver;
        if (settingObserver != null) {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            contentResolver.registerContentObserver(
                    settingObserver.LOCK_SCREEN_PRIVATE_URI, false, settingObserver);
            contentResolver.registerContentObserver(
                    settingObserver.LOCK_SCREEN_SHOW_URI, false, settingObserver);
        }
    }

    public final void setRestrictedIfNotificationFeaturesDisabled(
            CharSequence charSequence, CharSequence charSequence2, int i) {
        RestrictedLockUtils.EnforcedAdmin checkIfKeyguardFeaturesDisabled;
        RestrictedListPreference restrictedListPreference;
        RestrictedListPreference restrictedListPreference2;
        RestrictedLockUtils.EnforcedAdmin checkIfKeyguardFeaturesDisabled2 =
                RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(
                        this.mContext, i, UserHandle.myUserId());
        if (checkIfKeyguardFeaturesDisabled2 != null
                && (restrictedListPreference2 = this.mLockscreen) != null) {
            ((ArrayList) restrictedListPreference2.mRestrictedItems)
                    .add(
                            new RestrictedListPreference.RestrictedItem(
                                    charSequence, charSequence2, checkIfKeyguardFeaturesDisabled2));
        }
        int i2 = this.mProfileUserId;
        if (i2 == -10000
                || (checkIfKeyguardFeaturesDisabled =
                                RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(
                                        this.mContext, i, i2))
                        == null
                || (restrictedListPreference = this.mLockscreenProfile) == null) {
            return;
        }
        ((ArrayList) restrictedListPreference.mRestrictedItems)
                .add(
                        new RestrictedListPreference.RestrictedItem(
                                charSequence, charSequence2, checkIfKeyguardFeaturesDisabled));
    }

    public final void updateLockscreenNotifications() {
        if (this.mLockscreen == null) {
            return;
        }
        this.mLockscreenSelectedValue = getSummaryResource(this.mContext);
        this.mLockscreen.setSummary("%s");
        this.mLockscreen.setValue(Integer.toString(this.mLockscreenSelectedValue));
    }

    public final void updateLockscreenNotificationsForProfile() {
        int i = this.mProfileUserId;
        if (i == -10000 || this.mLockscreenProfile == null) {
            return;
        }
        boolean z = false;
        if ((((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class))
                                        .getKeyguardDisabledFeatures(null, i)
                                & 8)
                        == 0
                && (!this.mSecureProfile
                        || Settings.Secure.getIntForUser(
                                        this.mContext.getContentResolver(),
                                        "lock_screen_allow_private_notifications",
                                        0,
                                        i)
                                != 0)) {
            z = true;
        }
        this.mLockscreenProfile.setSummary("%s");
        int i2 =
                z
                        ? R.string.lock_screen_notifications_summary_show_profile
                        : R.string.lock_screen_notifications_summary_hide_profile;
        this.mLockscreenSelectedValueProfile = i2;
        this.mLockscreenProfile.setValue(Integer.toString(i2));
    }
}
