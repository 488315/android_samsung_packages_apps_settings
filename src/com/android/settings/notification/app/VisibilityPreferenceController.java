package com.android.settings.notification.app;

import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.RestrictedListPreference;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.notification.NotificationBackend;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class VisibilityPreferenceController extends NotificationPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    public int mHideContentOnLockScreen;

    public final boolean getLockscreenNotificationsEnabled$1() {
        UserInfo profileParent = this.mUm.getProfileParent(UserHandle.myUserId());
        return Settings.Secure.getIntForUser(
                        ((NotificationPreferenceController) this).mContext.getContentResolver(),
                        "lock_screen_show_notifications",
                        0,
                        profileParent != null ? profileParent.id : UserHandle.myUserId())
                != 0;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "visibility_override";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (super.isAvailable()
                && this.mChannel != null
                && !this.mAppRow.banned
                && getLockscreenNotificationsEnabled$1()) {
            NotificationBackend.AppRow appRow = this.mAppRow;
            String str = appRow.pkg;
            int i = appRow.uid;
            this.mBackend.getClass();
            if (NotificationBackend.getLockScreenNotificationVisibilityForPackage(i, str) != -1) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController
    public final boolean isIncludedInFilter() {
        return this.mPreferenceFilter.contains("locked");
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (this.mChannel == null) {
            return true;
        }
        int parseInt = Integer.parseInt((String) obj);
        if (parseInt
                == (!getLockscreenNotificationsEnabled$1()
                        ? -1
                        : (Settings.Secure.getIntForUser(
                                                        ((NotificationPreferenceController) this)
                                                                .mContext.getContentResolver(),
                                                        "lock_screen_allow_private_notifications",
                                                        0,
                                                        -2)
                                                == 0
                                        || this.mHideContentOnLockScreen == 0)
                                ? 0
                                : -1000)) {
            parseInt = -1000;
        }
        this.mChannel.setLockscreenVisibility(parseInt);
        this.mChannel.lockFields(2);
        saveChannel();
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        if (this.mChannel == null || this.mAppRow == null) {
            return;
        }
        RestrictedListPreference restrictedListPreference = (RestrictedListPreference) preference;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        NotificationBackend.AppRow appRow = this.mAppRow;
        String str = appRow.pkg;
        int i = appRow.uid;
        this.mBackend.getClass();
        this.mHideContentOnLockScreen =
                NotificationBackend.getLockScreenNotificationVisibilityForPackage(i, str);
        ((ArrayList) restrictedListPreference.mRestrictedItems).clear();
        int i2 = -1000;
        if (getLockscreenNotificationsEnabled$1()
                && Settings.Secure.getIntForUser(
                                ((NotificationPreferenceController) this)
                                        .mContext.getContentResolver(),
                                "lock_screen_allow_private_notifications",
                                0,
                                -2)
                        != 0
                && this.mHideContentOnLockScreen != 0) {
            String string =
                    ((NotificationPreferenceController) this)
                            .mContext.getString(R.string.sec_lockscreen_notifications_show_content);
            String num = Integer.toString(-1000);
            arrayList.add(string);
            arrayList2.add(num);
            RestrictedLockUtils.EnforcedAdmin checkIfKeyguardFeaturesDisabled =
                    RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(
                            ((NotificationPreferenceController) this).mContext,
                            12,
                            this.mAppRow.userId);
            if (checkIfKeyguardFeaturesDisabled != null) {
                ((ArrayList) restrictedListPreference.mRestrictedItems)
                        .add(
                                new RestrictedListPreference.RestrictedItem(
                                        string, num, checkIfKeyguardFeaturesDisabled));
            }
        }
        if (getLockscreenNotificationsEnabled$1()) {
            String string2 =
                    ((NotificationPreferenceController) this)
                            .mContext.getString(R.string.sec_lockscreen_notifications_hide_content);
            String num2 = Integer.toString(0);
            arrayList.add(string2);
            arrayList2.add(num2);
            RestrictedLockUtils.EnforcedAdmin checkIfKeyguardFeaturesDisabled2 =
                    RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(
                            ((NotificationPreferenceController) this).mContext,
                            4,
                            this.mAppRow.userId);
            if (checkIfKeyguardFeaturesDisabled2 != null) {
                ((ArrayList) restrictedListPreference.mRestrictedItems)
                        .add(
                                new RestrictedListPreference.RestrictedItem(
                                        string2, num2, checkIfKeyguardFeaturesDisabled2));
            }
        }
        arrayList.add(
                ((NotificationPreferenceController) this)
                        .mContext.getString(R.string.lock_screen_notifications_summary_disable));
        arrayList2.add(Integer.toString(-1));
        restrictedListPreference.mEntries =
                (CharSequence[]) arrayList.toArray(new CharSequence[arrayList.size()]);
        restrictedListPreference.mEntryValues =
                (CharSequence[]) arrayList2.toArray(new CharSequence[arrayList2.size()]);
        if (this.mChannel.getLockscreenVisibility() == -1000) {
            if (!getLockscreenNotificationsEnabled$1()) {
                i2 = -1;
            } else if (Settings.Secure.getIntForUser(
                                    ((NotificationPreferenceController) this)
                                            .mContext.getContentResolver(),
                                    "lock_screen_allow_private_notifications",
                                    0,
                                    -2)
                            == 0
                    || this.mHideContentOnLockScreen == 0) {
                i2 = 0;
            }
            restrictedListPreference.setValue(Integer.toString(i2));
        } else {
            restrictedListPreference.setValue(
                    Integer.toString(this.mChannel.getLockscreenVisibility()));
        }
        restrictedListPreference.setSummary("%s");
        SecPreferenceUtils.applySummaryColor(restrictedListPreference, true);
    }
}
