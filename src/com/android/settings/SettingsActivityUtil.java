package com.android.settings;

import com.android.settings.applications.appinfo.AlarmsAndRemindersDetails;
import com.android.settings.applications.appinfo.DrawOverlayDetails;
import com.android.settings.applications.appinfo.ExternalSourcesDetails;
import com.android.settings.applications.appinfo.ManageExternalStorageDetails;
import com.android.settings.applications.appinfo.MediaManagementAppsDetails;
import com.android.settings.applications.appinfo.WriteSettingsDetails;
import com.android.settings.applications.specialaccess.pictureinpicture.PictureInPictureDetails;
import com.android.settings.applications.specialaccess.pictureinpicture.PictureInPictureSettings;
import com.android.settings.spa.app.specialaccess.NfcTagAppsSettingsProvider;
import com.android.settings.wifi.ChangeWifiStateDetails;
import com.android.settingslib.spaprivileged.template.app.TogglePermissionAppInfoPageProvider;

import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.ReflectionFactory;

import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public abstract class SettingsActivityUtil {
    public static final Map FRAGMENT_TO_SPA_APP_DESTINATION_PREFIX_MAP;
    public static final Map FRAGMENT_TO_SPA_DESTINATION_MAP;

    static {
        ReflectionFactory reflectionFactory = Reflection.factory;
        FRAGMENT_TO_SPA_DESTINATION_MAP =
                MapsKt__MapsKt.mapOf(
                        new Pair(
                                reflectionFactory
                                        .getOrCreateKotlinClass(PictureInPictureSettings.class)
                                        .getQualifiedName(),
                                "TogglePermissionAppList/".concat("PictureInPicture")));
        String qualifiedName =
                reflectionFactory
                        .getOrCreateKotlinClass(PictureInPictureDetails.class)
                        .getQualifiedName();
        List list = TogglePermissionAppInfoPageProvider.PAGE_PARAMETER;
        FRAGMENT_TO_SPA_APP_DESTINATION_PREFIX_MAP =
                MapsKt__MapsKt.mapOf(
                        new Pair(
                                qualifiedName,
                                "TogglePermissionAppInfoPage/".concat("PictureInPicture")),
                        new Pair(
                                reflectionFactory
                                        .getOrCreateKotlinClass(DrawOverlayDetails.class)
                                        .getQualifiedName(),
                                "TogglePermissionAppInfoPage/".concat("DisplayOverOtherApps")),
                        new Pair(
                                reflectionFactory
                                        .getOrCreateKotlinClass(WriteSettingsDetails.class)
                                        .getQualifiedName(),
                                "TogglePermissionAppInfoPage/".concat("ModifySystemSettings")),
                        new Pair(
                                reflectionFactory
                                        .getOrCreateKotlinClass(AlarmsAndRemindersDetails.class)
                                        .getQualifiedName(),
                                "TogglePermissionAppInfoPage/".concat("AlarmsAndReminders")),
                        new Pair(
                                reflectionFactory
                                        .getOrCreateKotlinClass(ExternalSourcesDetails.class)
                                        .getQualifiedName(),
                                "TogglePermissionAppInfoPage/".concat("InstallUnknownApps")),
                        new Pair(
                                reflectionFactory
                                        .getOrCreateKotlinClass(ManageExternalStorageDetails.class)
                                        .getQualifiedName(),
                                "TogglePermissionAppInfoPage/".concat("AllFilesAccess")),
                        new Pair(
                                reflectionFactory
                                        .getOrCreateKotlinClass(MediaManagementAppsDetails.class)
                                        .getQualifiedName(),
                                "TogglePermissionAppInfoPage/".concat("MediaManagementApps")),
                        new Pair(
                                reflectionFactory
                                        .getOrCreateKotlinClass(ChangeWifiStateDetails.class)
                                        .getQualifiedName(),
                                "TogglePermissionAppInfoPage/".concat("WifiControl")),
                        new Pair(
                                reflectionFactory
                                        .getOrCreateKotlinClass(NfcTagAppsSettingsProvider.class)
                                        .getQualifiedName(),
                                "TogglePermissionAppInfoPage/".concat("NfcTagAppsSettings")));
    }
}
