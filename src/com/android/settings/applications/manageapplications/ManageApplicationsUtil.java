package com.android.settings.applications.manageapplications;

import com.android.settings.Settings;
import com.android.settings.applications.appinfo.AppLocaleDetails;

import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.ReflectionFactory;
import kotlin.reflect.KClass;

import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ManageApplicationsUtil {
    public static final Map LIST_TYPE_MAP;

    static {
        ReflectionFactory reflectionFactory = Reflection.factory;
        Map mapOf =
                MapsKt__MapsKt.mapOf(
                        new Pair(
                                reflectionFactory.getOrCreateKotlinClass(
                                        Settings.StorageUseActivity.class),
                                3),
                        new Pair(
                                reflectionFactory.getOrCreateKotlinClass(
                                        Settings.UsageAccessSettingsActivity.class),
                                4),
                        new Pair(
                                reflectionFactory.getOrCreateKotlinClass(
                                        Settings.HighPowerApplicationsActivity.class),
                                5),
                        new Pair(
                                reflectionFactory.getOrCreateKotlinClass(
                                        Settings.OverlaySettingsActivity.class),
                                6),
                        new Pair(
                                reflectionFactory.getOrCreateKotlinClass(
                                        Settings.WriteSettingsActivity.class),
                                7),
                        new Pair(
                                reflectionFactory.getOrCreateKotlinClass(
                                        Settings.ManageExternalSourcesActivity.class),
                                8),
                        new Pair(
                                reflectionFactory.getOrCreateKotlinClass(
                                        Settings.GamesStorageActivity.class),
                                9),
                        new Pair(
                                reflectionFactory.getOrCreateKotlinClass(
                                        Settings.ChangeWifiStateActivity.class),
                                10),
                        new Pair(
                                reflectionFactory.getOrCreateKotlinClass(
                                        Settings.ManageExternalStorageActivity.class),
                                11),
                        new Pair(
                                reflectionFactory.getOrCreateKotlinClass(
                                        Settings.MediaManagementAppsActivity.class),
                                13),
                        new Pair(
                                reflectionFactory.getOrCreateKotlinClass(
                                        Settings.AlarmsAndRemindersActivity.class),
                                12),
                        new Pair(
                                reflectionFactory.getOrCreateKotlinClass(
                                        Settings.NotificationAppListActivity.class),
                                1),
                        new Pair(
                                reflectionFactory.getOrCreateKotlinClass(
                                        Settings.NotificationReviewPermissionsActivity.class),
                                1),
                        new Pair(
                                reflectionFactory.getOrCreateKotlinClass(AppLocaleDetails.class),
                                14),
                        new Pair(
                                reflectionFactory.getOrCreateKotlinClass(
                                        Settings.AppBatteryUsageActivity.class),
                                15),
                        new Pair(
                                reflectionFactory.getOrCreateKotlinClass(
                                        Settings.LongBackgroundTasksActivity.class),
                                16),
                        new Pair(
                                reflectionFactory.getOrCreateKotlinClass(
                                        Settings.ClonedAppsListActivity.class),
                                17),
                        new Pair(
                                reflectionFactory.getOrCreateKotlinClass(
                                        Settings.ChangeNfcTagAppsActivity.class),
                                18),
                        new Pair(
                                reflectionFactory.getOrCreateKotlinClass(
                                        Settings.TurnScreenOnSettingsActivity.class),
                                19),
                        new Pair(
                                reflectionFactory.getOrCreateKotlinClass(
                                        Settings.UserAspectRatioAppListActivity.class),
                                20),
                        new Pair(
                                reflectionFactory.getOrCreateKotlinClass(
                                        Settings.ManageUnknownSourceAppsActivity.class),
                                103),
                        new Pair(
                                reflectionFactory.getOrCreateKotlinClass(
                                        Settings.ManageFullScreenIntentsActivity.class),
                                104),
                        new Pair(
                                reflectionFactory.getOrCreateKotlinClass(
                                        Settings.MediaRoutingActivity.class),
                                105));
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsKt.mapCapacity(mapOf.size()));
        for (Map.Entry entry : mapOf.entrySet()) {
            linkedHashMap.put(
                    JvmClassMappingKt.getJavaClass((KClass) entry.getKey()).getName(),
                    entry.getValue());
        }
        LIST_TYPE_MAP = linkedHashMap;
    }
}
