package com.android.settings.notification.app;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settings.widget.SettingsMainSwitchPreference;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.SecNotificationBlockManager;
import com.android.settingslib.applications.AppUtils;

import com.samsung.android.sdhms.SemAppRestrictionManager;
import com.samsung.android.settings.logging.SALogging;
import com.sec.ims.im.ImIntent;

import java.util.Arrays;
import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BlockPreferenceController extends NotificationPreferenceController
        implements PreferenceControllerMixin, CompoundButton.OnCheckedChangeListener {
    public final NotificationSettings.DependentFieldListener mDependentFieldListener;
    public final boolean mFromChannel;

    public BlockPreferenceController(
            Context context,
            NotificationSettings.DependentFieldListener dependentFieldListener,
            NotificationBackend notificationBackend,
            boolean z) {
        super(context, notificationBackend);
        this.mDependentFieldListener = dependentFieldListener;
        this.mFromChannel = z;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "block";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (this.mAppRow == null) {
            return false;
        }
        return this.mPreferenceFilter == null || isIncludedInFilter();
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController
    public final boolean isIncludedInFilter() {
        return this.mPreferenceFilter.contains("importance");
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        boolean z2 = !z;
        NotificationBackend.AppRow appRow = this.mAppRow;
        if (AppUtils.isDeepSleepingEnable(appRow.uid, appRow.pkg) && !z2) {
            ApplicationInfo applicationInfo = new ApplicationInfo();
            NotificationBackend.AppRow appRow2 = this.mAppRow;
            applicationInfo.packageName = appRow2.pkg;
            applicationInfo.uid = appRow2.uid;
            new SemAppRestrictionManager()
                    .restrict(3, 2, true, applicationInfo.packageName, applicationInfo.uid);
            Context context = ((NotificationPreferenceController) this).mContext;
            Toast.makeText(
                            context,
                            context.getResources()
                                    .getString(
                                            R.string.sec_app_notification_deep_sleeping_toast_text,
                                            this.mAppRow.label),
                            0)
                    .show();
        }
        NotificationChannel notificationChannel = this.mChannel;
        NotificationBackend notificationBackend = this.mBackend;
        if (notificationChannel == null || !this.mFromChannel) {
            NotificationChannelGroup notificationChannelGroup = this.mChannelGroup;
            if (notificationChannelGroup != null) {
                notificationChannelGroup.setBlocked(z2);
                NotificationBackend.AppRow appRow3 = this.mAppRow;
                String str = appRow3.pkg;
                int i = appRow3.uid;
                NotificationChannelGroup notificationChannelGroup2 = this.mChannelGroup;
                notificationBackend.getClass();
                try {
                    NotificationBackend.sINM.updateNotificationChannelGroupForPackage(
                            str, i, notificationChannelGroup2);
                } catch (Exception e) {
                    Log.w("NotificationBackend", "Error calling NoMan", e);
                }
            } else {
                NotificationBackend.AppRow appRow4 = this.mAppRow;
                if (appRow4 != null) {
                    boolean z3 = appRow4.banned;
                    appRow4.banned = z2;
                    String str2 = appRow4.pkg;
                    int i2 = appRow4.uid;
                    notificationBackend.getClass();
                    NotificationBackend.setNotificationsEnabledForPackage(i2, str2, z);
                    if (z3 != z2) {
                        HashMap hashMap = new HashMap();
                        hashMap.put(ImIntent.Extras.EXTRA_FROM, "channel list");
                        StringBuilder sb = new StringBuilder();
                        sb.append(this.mAppRow.pkg);
                        sb.append(z2 ? ";off" : ";on");
                        hashMap.put("turnOnOff", sb.toString());
                        SALogging.insertSALog(Integer.toString(36022), "NSTE0005", hashMap, 0);
                    }
                }
            }
        } else {
            int importance = notificationChannel.getImportance();
            if (z2 || importance == 0) {
                this.mChannel.setImportance(
                        z2
                                ? 0
                                : isDefaultChannel()
                                        ? -1000
                                        : Math.max(this.mChannel.getOriginalImportance(), 2));
                saveChannel();
            }
            NotificationBackend.AppRow appRow5 = this.mAppRow;
            String str3 = appRow5.pkg;
            int i3 = appRow5.uid;
            notificationBackend.getClass();
            if (NotificationBackend.onlyHasDefaultChannel(i3, str3)) {
                NotificationBackend.AppRow appRow6 = this.mAppRow;
                if (appRow6.banned != z2) {
                    appRow6.banned = z2;
                    NotificationBackend.setNotificationsEnabledForPackage(
                            appRow6.uid, appRow6.pkg, z);
                }
            }
            HashMap hashMap2 = new HashMap();
            hashMap2.put(ImIntent.Extras.EXTRA_FROM, "channel settings");
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.mAppRow.pkg);
            sb2.append(z2 ? ";off" : ";on");
            hashMap2.put("turnOnOff", sb2.toString());
            SALogging.insertSALog(Integer.toString(36022), "NSTE0021", hashMap2, 0);
        }
        this.mDependentFieldListener.onFieldValueChanged();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        NotificationBackend.AppRow appRow;
        boolean z;
        SettingsMainSwitchPreference settingsMainSwitchPreference =
                (SettingsMainSwitchPreference) preference;
        if (settingsMainSwitchPreference != null) {
            settingsMainSwitchPreference.mOnTextId = R.string.notification_content_block_title;
            settingsMainSwitchPreference.mOffTextId = R.string.notification_content_block_title;
            settingsMainSwitchPreference.setVisible(true);
            SettingsMainSwitchBar settingsMainSwitchBar =
                    settingsMainSwitchPreference.mMainSwitchBar;
            if (settingsMainSwitchBar != null) {
                settingsMainSwitchBar.show();
            }
            String string =
                    ((NotificationPreferenceController) this)
                            .mContext.getString(R.string.notification_content_block_title);
            settingsMainSwitchPreference.mSessionName = string;
            SettingsMainSwitchBar settingsMainSwitchBar2 =
                    settingsMainSwitchPreference.mMainSwitchBar;
            if (settingsMainSwitchBar2 != null) {
                settingsMainSwitchBar2.setSessionDescription(string);
            }
            try {
                settingsMainSwitchPreference.addOnSwitchChangeListener(this);
            } catch (IllegalStateException unused) {
            }
            RestrictedLockUtils.EnforcedAdmin enforcedAdmin = this.mAdmin;
            settingsMainSwitchPreference.mEnforcedAdmin = enforcedAdmin;
            SettingsMainSwitchBar settingsMainSwitchBar3 =
                    settingsMainSwitchPreference.mMainSwitchBar;
            if (settingsMainSwitchBar3 != null) {
                settingsMainSwitchBar3.setDisabledByAdmin(enforcedAdmin);
            }
            NotificationBackend.AppRow appRow2 = this.mAppRow;
            if (AppUtils.isDeepSleepingEnable(appRow2.uid, appRow2.pkg)) {
                settingsMainSwitchPreference.setChecked(false);
                return;
            }
            settingsMainSwitchPreference.setSwitchBarEnabled$1(true);
            NotificationChannel notificationChannel = this.mChannel;
            if (notificationChannel != null) {
                NotificationBackend.AppRow appRow3 = this.mAppRow;
                if ((!(appRow3 != null
                                        ? SecNotificationBlockManager
                                                .isBlockableNotificationChannel(
                                                        ((NotificationPreferenceController) this)
                                                                .mContext,
                                                        appRow3.pkg,
                                                        notificationChannel)
                                        : false)
                                || !isChannelConfigurable(this.mChannel))
                        && this.mChannel.getImportance() > 0) {
                    settingsMainSwitchPreference.setSwitchBarEnabled$1(false);
                }
            }
            NotificationChannelGroup notificationChannelGroup = this.mChannelGroup;
            if (notificationChannelGroup != null
                    && !isChannelGroupBlockable(notificationChannelGroup)) {
                settingsMainSwitchPreference.setSwitchBarEnabled$1(false);
            }
            if (this.mChannel == null
                    && (appRow = this.mAppRow) != null
                    && (((z = appRow.systemApp) && (!z || !appRow.banned))
                            || appRow.lockedImportance)) {
                settingsMainSwitchPreference.setSwitchBarEnabled$1(false);
            }
            if (this.mChannel == null) {
                NotificationBackend.AppRow appRow4 = this.mAppRow;
                if (!appRow4.packageBlockable && !appRow4.banned) {
                    settingsMainSwitchPreference.setSwitchBarEnabled$1(false);
                }
            }
            NotificationChannel notificationChannel2 = this.mChannel;
            if (notificationChannel2 != null) {
                settingsMainSwitchPreference.setChecked(
                        (this.mAppRow.banned || notificationChannel2.getImportance() == 0)
                                ? false
                                : true);
            } else {
                NotificationChannelGroup notificationChannelGroup2 = this.mChannelGroup;
                if (notificationChannelGroup2 != null) {
                    settingsMainSwitchPreference.setChecked(
                            (this.mAppRow.banned || notificationChannelGroup2.isBlocked())
                                    ? false
                                    : true);
                } else {
                    settingsMainSwitchPreference.setChecked(!this.mAppRow.banned);
                }
            }
            if (this.mChannel == null) {
                try {
                    PackageInfo packageInfo =
                            ((NotificationPreferenceController) this)
                                    .mContext
                                    .getPackageManager()
                                    .getPackageInfo(this.mAppRow.pkg, 4096);
                    if (packageInfo.applicationInfo.targetSdkVersion > 32) {
                        String[] strArr = packageInfo.requestedPermissions;
                        if (strArr != null
                                && !Arrays.stream(strArr)
                                        .noneMatch(
                                                new BlockPreferenceController$$ExternalSyntheticLambda0())) {
                            return;
                        }
                        settingsMainSwitchPreference.setEnabled(false);
                    }
                } catch (Exception e) {
                    Log.e("PrefControllerMixin", "Exception", e);
                }
            }
        }
    }
}
