package com.android.settings.spa;

import android.content.Context;
import android.util.FeatureFlagUtils;

import com.android.settings.network.apn.ApnEditPageProvider;
import com.android.settings.print.PrintSettingsPageProvider;
import com.android.settings.spa.about.AboutPhonePageProvider;
import com.android.settings.spa.app.AllAppListPageProvider;
import com.android.settings.spa.app.AppsMainPageProvider;
import com.android.settings.spa.app.appcompat.UserAspectRatioAppsPageProvider;
import com.android.settings.spa.app.appinfo.AppInfoSettingsProvider;
import com.android.settings.spa.app.appinfo.CloneAppInfoSettingsProvider;
import com.android.settings.spa.app.backgroundinstall.BackgroundInstalledAppsPageProvider;
import com.android.settings.spa.app.battery.BatteryOptimizationModeAppListPageProvider;
import com.android.settings.spa.app.specialaccess.AlarmsAndRemindersAppListProvider;
import com.android.settings.spa.app.specialaccess.AllFilesAccessAppListProvider;
import com.android.settings.spa.app.specialaccess.DisplayOverOtherAppsAppListProvider;
import com.android.settings.spa.app.specialaccess.InstallUnknownAppsListProvider;
import com.android.settings.spa.app.specialaccess.LongBackgroundTasksAppListProvider;
import com.android.settings.spa.app.specialaccess.MediaManagementAppsAppListProvider;
import com.android.settings.spa.app.specialaccess.MediaRoutingControlAppListProvider;
import com.android.settings.spa.app.specialaccess.ModifySystemSettingsAppListProvider;
import com.android.settings.spa.app.specialaccess.NfcTagAppsSettingsProvider;
import com.android.settings.spa.app.specialaccess.PictureInPictureListProvider;
import com.android.settings.spa.app.specialaccess.SpecialAppAccessPageProvider;
import com.android.settings.spa.app.specialaccess.TurnScreenOnAppsAppListProvider;
import com.android.settings.spa.app.specialaccess.UseFullScreenIntentAppListProvider;
import com.android.settings.spa.app.specialaccess.WifiControlAppListProvider;
import com.android.settings.spa.app.storage.StorageAppListPageProvider;
import com.android.settings.spa.core.instrumentation.SpaLogMetricsProvider;
import com.android.settings.spa.development.UsageStatsPageProvider;
import com.android.settings.spa.development.compat.PlatformCompatAppListPageProvider;
import com.android.settings.spa.home.HomePageProvider;
import com.android.settings.spa.network.NetworkAndInternetPageProvider;
import com.android.settings.spa.network.NetworkCellularGroupProvider;
import com.android.settings.spa.network.SimOnboardingPageProvider;
import com.android.settings.spa.notification.AppListNotificationsPageProvider;
import com.android.settings.spa.notification.NotificationMainPageProvider;
import com.android.settings.spa.system.AppLanguagesPageProvider;
import com.android.settings.spa.system.LanguageAndInputPageProvider;
import com.android.settings.spa.system.SystemMainPageProvider;
import com.android.settings.wifi.details2.WifiPrivacyPageProvider;
import com.android.settingslib.spa.framework.common.SettingsPageProvider;
import com.android.settingslib.spa.framework.common.SettingsPageProviderKt;
import com.android.settingslib.spa.framework.common.SettingsPageProviderRepository;
import com.android.settingslib.spa.framework.common.SpaEnvironment;
import com.android.settingslib.spa.framework.common.SpaLogger;
import com.android.settingslib.spaprivileged.template.app.TogglePermissionAppInfoPageProvider;
import com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListPageProvider;
import com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListProvider;
import com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListTemplate;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

import java.util.Collection;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SettingsSpaEnvironment extends SpaEnvironment {
    public final SpaLogger logger;
    public final Lazy pageProviderRepository;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SettingsSpaEnvironment(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.pageProviderRepository =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.android.settings.spa.SettingsSpaEnvironment$pageProviderRepository$1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                SettingsSpaEnvironment.this.getClass();
                                TogglePermissionAppListTemplate togglePermissionAppListTemplate =
                                        new TogglePermissionAppListTemplate(
                                                CollectionsKt__CollectionsKt.listOf(
                                                        (Object[])
                                                                new TogglePermissionAppListProvider
                                                                        [] {
                                                                    AllFilesAccessAppListProvider
                                                                            .INSTANCE,
                                                                    DisplayOverOtherAppsAppListProvider
                                                                            .INSTANCE,
                                                                    MediaManagementAppsAppListProvider
                                                                            .INSTANCE,
                                                                    MediaRoutingControlAppListProvider
                                                                            .INSTANCE,
                                                                    ModifySystemSettingsAppListProvider
                                                                            .INSTANCE,
                                                                    UseFullScreenIntentAppListProvider
                                                                            .INSTANCE,
                                                                    PictureInPictureListProvider
                                                                            .INSTANCE,
                                                                    InstallUnknownAppsListProvider
                                                                            .INSTANCE,
                                                                    AlarmsAndRemindersAppListProvider
                                                                            .INSTANCE,
                                                                    WifiControlAppListProvider
                                                                            .INSTANCE,
                                                                    NfcTagAppsSettingsProvider
                                                                            .INSTANCE,
                                                                    LongBackgroundTasksAppListProvider
                                                                            .INSTANCE,
                                                                    TurnScreenOnAppsAppListProvider
                                                                            .INSTANCE
                                                                }));
                                SettingsSpaEnvironment.this.getClass();
                                HomePageProvider homePageProvider = HomePageProvider.INSTANCE;
                                AppsMainPageProvider appsMainPageProvider =
                                        AppsMainPageProvider.INSTANCE;
                                AllAppListPageProvider allAppListPageProvider =
                                        AllAppListPageProvider.INSTANCE;
                                AppInfoSettingsProvider appInfoSettingsProvider =
                                        AppInfoSettingsProvider.INSTANCE;
                                SpecialAppAccessPageProvider specialAppAccessPageProvider =
                                        SpecialAppAccessPageProvider.INSTANCE;
                                NotificationMainPageProvider notificationMainPageProvider =
                                        NotificationMainPageProvider.INSTANCE;
                                AppListNotificationsPageProvider appListNotificationsPageProvider =
                                        AppListNotificationsPageProvider.INSTANCE;
                                SystemMainPageProvider systemMainPageProvider =
                                        SystemMainPageProvider.INSTANCE;
                                LanguageAndInputPageProvider languageAndInputPageProvider =
                                        LanguageAndInputPageProvider.INSTANCE;
                                AppLanguagesPageProvider appLanguagesPageProvider =
                                        AppLanguagesPageProvider.INSTANCE;
                                UsageStatsPageProvider usageStatsPageProvider =
                                        UsageStatsPageProvider.INSTANCE;
                                PlatformCompatAppListPageProvider
                                        platformCompatAppListPageProvider =
                                                PlatformCompatAppListPageProvider.INSTANCE;
                                BackgroundInstalledAppsPageProvider
                                        backgroundInstalledAppsPageProvider =
                                                BackgroundInstalledAppsPageProvider.INSTANCE;
                                UserAspectRatioAppsPageProvider userAspectRatioAppsPageProvider =
                                        UserAspectRatioAppsPageProvider.INSTANCE;
                                CloneAppInfoSettingsProvider cloneAppInfoSettingsProvider =
                                        CloneAppInfoSettingsProvider.INSTANCE;
                                NetworkAndInternetPageProvider networkAndInternetPageProvider =
                                        NetworkAndInternetPageProvider.INSTANCE;
                                AboutPhonePageProvider aboutPhonePageProvider =
                                        AboutPhonePageProvider.INSTANCE;
                                StorageAppListPageProvider.Apps apps =
                                        StorageAppListPageProvider.Apps.INSTANCE;
                                StorageAppListPageProvider.Games games =
                                        StorageAppListPageProvider.Games.INSTANCE;
                                ApnEditPageProvider apnEditPageProvider =
                                        ApnEditPageProvider.INSTANCE;
                                SimOnboardingPageProvider simOnboardingPageProvider =
                                        SimOnboardingPageProvider.INSTANCE;
                                BatteryOptimizationModeAppListPageProvider
                                        batteryOptimizationModeAppListPageProvider =
                                                BatteryOptimizationModeAppListPageProvider.INSTANCE;
                                NetworkCellularGroupProvider networkCellularGroupProvider =
                                        new NetworkCellularGroupProvider();
                                SettingsPageProviderKt.createSettingsPage(
                                        networkCellularGroupProvider, null);
                                networkCellularGroupProvider.defaultVoiceSubId = -1;
                                networkCellularGroupProvider.defaultSmsSubId = -1;
                                networkCellularGroupProvider.defaultDataSubId = -1;
                                networkCellularGroupProvider.nonDds = -1;
                                return new SettingsPageProviderRepository(
                                        CollectionsKt___CollectionsKt.plus(
                                                (Iterable)
                                                        CollectionsKt__CollectionsKt.listOf(
                                                                (Object[])
                                                                        new SettingsPageProvider[] {
                                                                            new TogglePermissionAppListPageProvider(
                                                                                    togglePermissionAppListTemplate),
                                                                            new TogglePermissionAppInfoPageProvider(
                                                                                    togglePermissionAppListTemplate)
                                                                        }),
                                                (Collection)
                                                        CollectionsKt__CollectionsKt.listOf(
                                                                (Object[])
                                                                        new SettingsPageProvider[] {
                                                                            homePageProvider,
                                                                            appsMainPageProvider,
                                                                            allAppListPageProvider,
                                                                            appInfoSettingsProvider,
                                                                            specialAppAccessPageProvider,
                                                                            notificationMainPageProvider,
                                                                            appListNotificationsPageProvider,
                                                                            systemMainPageProvider,
                                                                            languageAndInputPageProvider,
                                                                            appLanguagesPageProvider,
                                                                            usageStatsPageProvider,
                                                                            platformCompatAppListPageProvider,
                                                                            backgroundInstalledAppsPageProvider,
                                                                            userAspectRatioAppsPageProvider,
                                                                            cloneAppInfoSettingsProvider,
                                                                            networkAndInternetPageProvider,
                                                                            aboutPhonePageProvider,
                                                                            apps,
                                                                            games,
                                                                            apnEditPageProvider,
                                                                            simOnboardingPageProvider,
                                                                            batteryOptimizationModeAppListPageProvider,
                                                                            networkCellularGroupProvider,
                                                                            WifiPrivacyPageProvider
                                                                                    .INSTANCE,
                                                                            PrintSettingsPageProvider
                                                                                    .INSTANCE
                                                                        })),
                                        CollectionsKt__CollectionsJVMKt.listOf(
                                                SettingsPageProviderKt.createSettingsPage(
                                                        homePageProvider, null)));
                            }
                        });
        this.logger =
                FeatureFlagUtils.isEnabled(context, "settings_enable_spa_metrics")
                        ? SpaLogMetricsProvider.INSTANCE
                        : new SettingsSpaEnvironment$logger$1();
    }
}
