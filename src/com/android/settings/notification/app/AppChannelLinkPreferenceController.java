package com.android.settings.notification.app;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppChannelLinkPreferenceController extends NotificationPreferenceController {
    public String mChannelHighlightId;
    public Context mContext;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "app_channel_link";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return (!super.isAvailable()
                        || this.mAppRow.banned
                        || Settings.Secure.getIntForUser(
                                        this.mContext.getContentResolver(),
                                        "show_notification_category_setting",
                                        0,
                                        -2)
                                == 0)
                ? false
                : true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        if (this.mAppRow != null) {
            preference.setOnPreferenceClickListener(
                    new Preference
                            .OnPreferenceClickListener() { // from class:
                                                           // com.android.settings.notification.app.AppChannelLinkPreferenceController$$ExternalSyntheticLambda0
                        @Override // androidx.preference.Preference.OnPreferenceClickListener
                        public final boolean onPreferenceClick(Preference preference2) {
                            AppChannelLinkPreferenceController appChannelLinkPreferenceController =
                                    AppChannelLinkPreferenceController.this;
                            appChannelLinkPreferenceController.getClass();
                            Bundle bundle = new Bundle();
                            bundle.putInt(
                                    NetworkAnalyticsConstants.DataPoints.UID,
                                    appChannelLinkPreferenceController.mAppRow.uid);
                            bundle.putString(
                                    "package", appChannelLinkPreferenceController.mAppRow.pkg);
                            String str = appChannelLinkPreferenceController.mChannelHighlightId;
                            if (str != null) {
                                bundle.putString(":settings:fragment_args_key", str);
                            }
                            SubSettingLauncher subSettingLauncher =
                                    new SubSettingLauncher(
                                            appChannelLinkPreferenceController.mContext);
                            String name = AppChannelListSettings.class.getName();
                            SubSettingLauncher.LaunchRequest launchRequest =
                                    subSettingLauncher.mLaunchRequest;
                            launchRequest.mDestinationName = name;
                            launchRequest.mArguments = bundle;
                            launchRequest.mExtras = bundle;
                            subSettingLauncher.setTitleRes(
                                    R.string.app_settings_channel_link, null);
                            launchRequest.mSourceMetricsCategory = 72;
                            subSettingLauncher.launch();
                            LoggingHelper.insertEventLogging(36024, "NSTE0025");
                            return true;
                        }
                    });
        }
    }
}
