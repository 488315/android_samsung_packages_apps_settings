package com.android.settings.notification.app;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.content.pm.ParceledListSlice;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;

import androidx.core.text.BidiFormatter;
import androidx.lifecycle.LifecycleObserver;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.notification.NotificationBackend;
import com.android.settingslib.PrimarySwitchPreference;
import com.android.settingslib.RestrictedSwitchPreference;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppChannelsBypassingDndPreferenceController
        extends NotificationPreferenceController
        implements PreferenceControllerMixin, LifecycleObserver {
    static final String KEY = "zen_mode_bypassing_app_channels_list";
    public RestrictedSwitchPreference mAllNotificationsToggle;
    public Map mChannelGroupNames;
    public List mChannels;
    public Set mDuplicateChannelNames;
    public PreferenceCategory mPreferenceCategory;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.app.AppChannelsBypassingDndPreferenceController$2, reason: invalid class name */
    public final class AnonymousClass2 extends AsyncTask {
        public AnonymousClass2() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            ParceledListSlice emptyList;
            ArrayList arrayList = new ArrayList();
            AppChannelsBypassingDndPreferenceController
                    appChannelsBypassingDndPreferenceController =
                            AppChannelsBypassingDndPreferenceController.this;
            NotificationBackend notificationBackend =
                    appChannelsBypassingDndPreferenceController.mBackend;
            NotificationBackend.AppRow appRow = appChannelsBypassingDndPreferenceController.mAppRow;
            String str = appRow.pkg;
            int i = appRow.uid;
            notificationBackend.getClass();
            try {
                emptyList =
                        NotificationBackend.sINM.getNotificationChannelGroupsForPackage(
                                str, i, false);
            } catch (Exception e) {
                Log.w("NotificationBackend", "Error calling NoMan", e);
                emptyList = ParceledListSlice.emptyList();
            }
            List<NotificationChannelGroup> list = emptyList.getList();
            HashSet hashSet = new HashSet();
            for (NotificationChannelGroup notificationChannelGroup : list) {
                for (NotificationChannel notificationChannel :
                        notificationChannelGroup.getChannels()) {
                    AppChannelsBypassingDndPreferenceController.this.getClass();
                    if (notificationChannel.getConversationId() == null
                            || notificationChannel.isDemoted()) {
                        arrayList.add(notificationChannel);
                        ((HashMap)
                                        AppChannelsBypassingDndPreferenceController.this
                                                .mChannelGroupNames)
                                .put(
                                        notificationChannel,
                                        notificationChannelGroup.getName().toString());
                        if (hashSet.contains(notificationChannel.getName())) {
                            ((HashSet)
                                            AppChannelsBypassingDndPreferenceController.this
                                                    .mDuplicateChannelNames)
                                    .add(notificationChannel.getName().toString());
                        } else {
                            hashSet.add(notificationChannel.getName().toString());
                        }
                    }
                }
            }
            Collections.sort(arrayList, NotificationPreferenceController.CHANNEL_COMPARATOR);
            AppChannelsBypassingDndPreferenceController.this.mChannels = arrayList;
            return null;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            PreferenceCategory preferenceCategory;
            final AppChannelsBypassingDndPreferenceController
                    appChannelsBypassingDndPreferenceController =
                            AppChannelsBypassingDndPreferenceController.this;
            if (((NotificationPreferenceController) appChannelsBypassingDndPreferenceController)
                                    .mContext
                            == null
                    || (preferenceCategory =
                                    appChannelsBypassingDndPreferenceController.mPreferenceCategory)
                            == null) {
                return;
            }
            preferenceCategory.removeAll();
            appChannelsBypassingDndPreferenceController.mPreferenceCategory.addPreference(
                    appChannelsBypassingDndPreferenceController.mAllNotificationsToggle);
            for (final NotificationChannel notificationChannel :
                    appChannelsBypassingDndPreferenceController.mChannels) {
                PrimarySwitchPreference primarySwitchPreference =
                        new PrimarySwitchPreference(
                                ((NotificationPreferenceController)
                                                appChannelsBypassingDndPreferenceController)
                                        .mContext);
                primarySwitchPreference.setDisabledByAdmin(
                        appChannelsBypassingDndPreferenceController.mAdmin);
                boolean z = false;
                primarySwitchPreference.setSwitchEnabled(
                        (appChannelsBypassingDndPreferenceController.mAdmin == null
                                        || !primarySwitchPreference.mHelper.mDisabledByAdmin)
                                && appChannelsBypassingDndPreferenceController
                                        .isChannelConfigurable(notificationChannel)
                                && appChannelsBypassingDndPreferenceController.showNotification(
                                        notificationChannel));
                BidiFormatter bidiFormatter = BidiFormatter.getInstance();
                primarySwitchPreference.setTitle(
                        bidiFormatter.unicodeWrap(
                                notificationChannel.getName(),
                                bidiFormatter.mDefaultTextDirectionHeuristicCompat));
                if (((HashSet) appChannelsBypassingDndPreferenceController.mDuplicateChannelNames)
                                .contains(notificationChannel.getName().toString())
                        && ((HashMap)
                                        appChannelsBypassingDndPreferenceController
                                                .mChannelGroupNames)
                                .containsKey(notificationChannel)
                        && ((HashMap)
                                                appChannelsBypassingDndPreferenceController
                                                        .mChannelGroupNames)
                                        .get(notificationChannel)
                                != null
                        && !((String)
                                        ((HashMap)
                                                        appChannelsBypassingDndPreferenceController
                                                                .mChannelGroupNames)
                                                .get(notificationChannel))
                                .isEmpty()) {
                    primarySwitchPreference.setSummary(
                            BidiFormatter.getInstance()
                                    .unicodeWrap(
                                            (String)
                                                    ((HashMap)
                                                                    appChannelsBypassingDndPreferenceController
                                                                            .mChannelGroupNames)
                                                            .get(notificationChannel)));
                }
                if (notificationChannel.canBypassDnd()
                        && appChannelsBypassingDndPreferenceController.showNotification(
                                notificationChannel)) {
                    z = true;
                }
                primarySwitchPreference.setChecked(z);
                primarySwitchPreference.setOnPreferenceChangeListener(
                        new Preference
                                .OnPreferenceChangeListener() { // from class:
                                                                // com.android.settings.notification.app.AppChannelsBypassingDndPreferenceController.3
                            @Override // androidx.preference.Preference.OnPreferenceChangeListener
                            public final boolean onPreferenceChange(
                                    Preference preference, Object obj2) {
                                notificationChannel.setBypassDnd(((Boolean) obj2).booleanValue());
                                notificationChannel.lockFields(1);
                                AppChannelsBypassingDndPreferenceController
                                        appChannelsBypassingDndPreferenceController2 =
                                                AppChannelsBypassingDndPreferenceController.this;
                                NotificationBackend notificationBackend =
                                        appChannelsBypassingDndPreferenceController2.mBackend;
                                NotificationBackend.AppRow appRow =
                                        appChannelsBypassingDndPreferenceController2.mAppRow;
                                String str = appRow.pkg;
                                int i = appRow.uid;
                                NotificationChannel notificationChannel2 = notificationChannel;
                                notificationBackend.getClass();
                                NotificationBackend.updateChannel(str, i, notificationChannel2);
                                appChannelsBypassingDndPreferenceController2.mAllNotificationsToggle
                                        .setChecked(
                                                appChannelsBypassingDndPreferenceController2
                                                        .areAllChannelsBypassing());
                                return true;
                            }
                        });
                final Bundle bundle = new Bundle();
                bundle.putInt(
                        NetworkAnalyticsConstants.DataPoints.UID,
                        appChannelsBypassingDndPreferenceController.mAppRow.uid);
                bundle.putString(
                        "package", appChannelsBypassingDndPreferenceController.mAppRow.pkg);
                bundle.putString("android.provider.extra.CHANNEL_ID", notificationChannel.getId());
                bundle.putBoolean("fromSettings", true);
                primarySwitchPreference.setOnPreferenceClickListener(
                        new Preference
                                .OnPreferenceClickListener() { // from class:
                                                               // com.android.settings.notification.app.AppChannelsBypassingDndPreferenceController$$ExternalSyntheticLambda0
                            @Override // androidx.preference.Preference.OnPreferenceClickListener
                            public final boolean onPreferenceClick(Preference preference) {
                                Bundle bundle2 = bundle;
                                AppChannelsBypassingDndPreferenceController
                                        appChannelsBypassingDndPreferenceController2 =
                                                AppChannelsBypassingDndPreferenceController.this;
                                SubSettingLauncher subSettingLauncher =
                                        new SubSettingLauncher(
                                                ((NotificationPreferenceController)
                                                                appChannelsBypassingDndPreferenceController2)
                                                        .mContext);
                                String name = ChannelNotificationSettings.class.getName();
                                SubSettingLauncher.LaunchRequest launchRequest =
                                        subSettingLauncher.mLaunchRequest;
                                launchRequest.mDestinationName = name;
                                launchRequest.mArguments = bundle2;
                                launchRequest.mUserHandle =
                                        UserHandle.of(
                                                appChannelsBypassingDndPreferenceController2
                                                        .mAppRow
                                                        .userId);
                                subSettingLauncher.setTitleRes(
                                        R.string.notification_channel_title, null);
                                launchRequest.mSourceMetricsCategory = 1840;
                                subSettingLauncher.launch();
                                return true;
                            }
                        });
                appChannelsBypassingDndPreferenceController.mPreferenceCategory.addPreference(
                        primarySwitchPreference);
            }
            appChannelsBypassingDndPreferenceController.mAllNotificationsToggle.setChecked(
                    appChannelsBypassingDndPreferenceController.areAllChannelsBypassing());
        }
    }

    public final boolean areAllChannelsBypassing() {
        if (this.mAppRow.banned) {
            return false;
        }
        boolean z = true;
        for (NotificationChannel notificationChannel : this.mChannels) {
            if (showNotification(notificationChannel)) {
                z &= notificationChannel.canBypassDnd() && showNotification(notificationChannel);
            }
        }
        return z;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) preferenceScreen.findPreference(KEY);
        this.mPreferenceCategory = preferenceCategory;
        RestrictedSwitchPreference restrictedSwitchPreference =
                new RestrictedSwitchPreference(preferenceCategory.getContext());
        this.mAllNotificationsToggle = restrictedSwitchPreference;
        restrictedSwitchPreference.setTitle(R.string.zen_mode_bypassing_app_channels_toggle_all);
        this.mAllNotificationsToggle.setDisabledByAdmin(this.mAdmin);
        RestrictedSwitchPreference restrictedSwitchPreference2 = this.mAllNotificationsToggle;
        restrictedSwitchPreference2.setEnabled(
                !this.mAppRow.banned
                        && (this.mAdmin == null
                                || !restrictedSwitchPreference2.mHelper.mDisabledByAdmin));
        this.mAllNotificationsToggle.setOnPreferenceClickListener(
                new Preference
                        .OnPreferenceClickListener() { // from class:
                                                       // com.android.settings.notification.app.AppChannelsBypassingDndPreferenceController.1
                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference) {
                        boolean isChecked = ((TwoStatePreference) preference).isChecked();
                        AppChannelsBypassingDndPreferenceController
                                appChannelsBypassingDndPreferenceController =
                                        AppChannelsBypassingDndPreferenceController.this;
                        for (NotificationChannel notificationChannel :
                                appChannelsBypassingDndPreferenceController.mChannels) {
                            if (appChannelsBypassingDndPreferenceController.showNotification(
                                            notificationChannel)
                                    && appChannelsBypassingDndPreferenceController
                                            .isChannelConfigurable(notificationChannel)) {
                                notificationChannel.setBypassDnd(isChecked);
                                notificationChannel.lockFields(1);
                                NotificationBackend.AppRow appRow =
                                        appChannelsBypassingDndPreferenceController.mAppRow;
                                String str = appRow.pkg;
                                int i = appRow.uid;
                                appChannelsBypassingDndPreferenceController.mBackend.getClass();
                                NotificationBackend.updateChannel(str, i, notificationChannel);
                            }
                        }
                        for (int i2 = 1;
                                i2
                                        < appChannelsBypassingDndPreferenceController
                                                .mPreferenceCategory.getPreferenceCount();
                                i2++) {
                            PrimarySwitchPreference primarySwitchPreference =
                                    (PrimarySwitchPreference)
                                            appChannelsBypassingDndPreferenceController
                                                    .mPreferenceCategory.getPreference(i2);
                            NotificationChannel notificationChannel2 =
                                    (NotificationChannel)
                                            appChannelsBypassingDndPreferenceController.mChannels
                                                    .get(i2 - 1);
                            primarySwitchPreference.setChecked(
                                    notificationChannel2.canBypassDnd()
                                            && appChannelsBypassingDndPreferenceController
                                                    .showNotification(notificationChannel2));
                        }
                        return true;
                    }
                });
        new AnonymousClass2().execute(new Void[0]);
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return KEY;
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mAppRow != null;
    }

    public final boolean showNotification(NotificationChannel notificationChannel) {
        return (this.mAppRow.banned || notificationChannel.getImportance() == 0) ? false : true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        if (this.mAppRow != null) {
            new AnonymousClass2().execute(new Void[0]);
        }
    }
}
