package com.samsung.android.settings.core;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.android.settings.MainClear$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.applications.AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0;
import com.android.settings.display.AlwaysOnDisplaySlice$$ExternalSyntheticLambda0;
import com.android.settingslib.drawer.ActivityTile;
import com.android.settingslib.drawer.DashboardCategory;
import com.android.settingslib.drawer.ProviderTile;
import com.android.settingslib.drawer.Tile;
import com.android.settingslib.drawer.TileUtils;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.ProKioskManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentService;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.Trace;
import com.sec.ims.volte2.data.VolteConstants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SecTileUtils extends TileUtils {
    public static int mCurrentUserId;
    public static final String mSdkVersionString = Integer.toString(Build.VERSION.SDK_INT);

    public static List getCategories(Context context, Map map) {
        Trace.beginSection("SecTileUtils#getCategories");
        System.currentTimeMillis();
        boolean z =
                Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) != 0;
        mCurrentUserId = ActivityManager.getCurrentUser();
        ArrayList arrayList = new ArrayList();
        for (UserHandle userHandle :
                ((UserManager) context.getSystemService("user")).getUserProfiles()) {
            if (userHandle.getIdentifier() == mCurrentUserId) {
                Trace.beginSection("SecTileUtils#loadTilesForAction_SETETINGS_ACTION");
                Log.i("SecTileUtils", "loadTilesForAction com.android.settings.action.SETTINGS");
                loadTilesForAction(
                        context,
                        userHandle,
                        "com.android.settings.action.SETTINGS",
                        map,
                        arrayList,
                        true);
                Trace.endSection();
                Trace.beginSection("SecTileUtils#loadTilesForAction_OPERATOR_SETTINGS");
                Log.i(
                        "SecTileUtils",
                        "loadTilesForAction com.android.settings.OPERATOR_APPLICATION_SETTING");
                loadTilesForAction(
                        context,
                        userHandle,
                        "com.android.settings.OPERATOR_APPLICATION_SETTING",
                        map,
                        arrayList,
                        false);
                Trace.endSection();
                Trace.beginSection("SecTileUtils#loadTilesForAction_OPERATOR_SETTINGS");
                Log.i(
                        "SecTileUtils",
                        "loadTilesForAction com.android.settings.MANUFACTURER_APPLICATION_SETTING");
                loadTilesForAction(
                        context,
                        userHandle,
                        "com.android.settings.MANUFACTURER_APPLICATION_SETTING",
                        map,
                        arrayList,
                        false);
                Trace.endSection();
            }
            if (z
                    && !SemPersonaManager.isSecureFolderId(userHandle.getIdentifier())
                    && !SemDualAppManager.isDualAppId(userHandle.getIdentifier())) {
                Trace.beginSection("SecTileUtils#loadTilesForAction_EXTRA_SETTINGS_ACTION");
                Log.i(
                        "SecTileUtils",
                        "loadTilesForAction com.android.settings.action.EXTRA_SETTINGS");
                loadTilesForAction(
                        context,
                        userHandle,
                        "com.android.settings.action.EXTRA_SETTINGS",
                        map,
                        arrayList,
                        false);
                Trace.endSection();
                Trace.beginSection("SecTileUtils#loadTilesForAction_IA_SETTINGS_ACTION");
                Log.i("SecTileUtils", "loadTilesForAction com.android.settings.action.IA_SETTINGS");
                loadTilesForAction(
                        context,
                        userHandle,
                        "com.android.settings.action.IA_SETTINGS",
                        map,
                        arrayList,
                        false);
                Trace.endSection();
            }
            Trace.beginSection("SecTileUtils#loadTilesForAction_INTELLIGENCE_SERVICE_SETTINGS");
            Log.i(
                    "SecTileUtils",
                    "loadTilesForAction"
                        + " com.samsung.android.settings.action.INTELLIGENCE_SERVICE_SETTINGS");
            loadTilesForAction(
                    context,
                    userHandle,
                    "com.samsung.android.settings.action.INTELLIGENCE_SERVICE_SETTINGS",
                    map,
                    arrayList,
                    false);
            Trace.endSection();
        }
        boolean z2 =
                Settings.System.getInt(context.getContentResolver(), "minimal_battery_use", 0) == 1;
        ProKioskManager proKioskManager = ProKioskManager.getInstance();
        boolean proKioskState =
                proKioskManager != null ? proKioskManager.getProKioskState() : false;
        String salesCode = Utils.getSalesCode();
        if ("VZW".equals(salesCode)) {
            salesCode = "BETA";
        }
        boolean isSamsungDexMode = Rune.isSamsungDexMode(context);
        HashMap hashMap = new HashMap();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Tile tile = (Tile) it.next();
            String str = tile.mCategory;
            DashboardCategory dashboardCategory = (DashboardCategory) hashMap.get(str);
            if (dashboardCategory == null) {
                dashboardCategory = new DashboardCategory(str);
                DialogFragment$$ExternalSyntheticOutline0.m(
                        "new DashBoardCategory key : ", str, "SecTileUtils");
                hashMap.put(str, dashboardCategory);
            }
            if (proKioskState) {
                StringBuilder sb = new StringBuilder("[ProKioskMode] category.skipTile ");
                sb.append(tile);
                sb.append(" , tileID : ");
                Utils$$ExternalSyntheticOutline0.m(sb, tile.tileId, "SecTileUtils");
            } else if (tile.support != null) {
                StringBuilder sb2 = new StringBuilder("tileID : ");
                sb2.append(tile.tileId);
                sb2.append(" , tile.support ");
                Utils$$ExternalSyntheticOutline0.m(sb2, tile.support, "SecTileUtils");
                Set set =
                        (Set)
                                Arrays.stream(TextUtils.split(tile.support, ","))
                                        .map(new AlwaysOnDisplaySlice$$ExternalSyntheticLambda0())
                                        .collect(Collectors.toSet());
                if (!set.contains("GLOBAL")
                        && !set.contains("Common")
                        && !set.contains(salesCode)) {
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            new StringBuilder(
                                    "no condition about tile.support, skip addTile tileID : "),
                            tile.tileId,
                            "SecTileUtils");
                } else if (set.contains("SamsungDeX") && !isSamsungDexMode) {
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            new StringBuilder("[SamsungDeX] skip addTile , tileID : "),
                            tile.tileId,
                            "SecTileUtils");
                } else if (z2 && set.contains("SkipMinimalBatteryUse")) {
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            new StringBuilder("[MinimalBatteryMode] skip addTile , tileID : "),
                            tile.tileId,
                            "SecTileUtils");
                } else if (isSamsungDexMode && set.contains("SkipDexMode")) {
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            new StringBuilder("[DexMode] skip addTile , tileID : "),
                            tile.tileId,
                            "SecTileUtils");
                } else {
                    Log.d(
                            "SecTileUtils",
                            "[GLOBAL, Common, SalesCode] category.addTile "
                                    + tile.mComponentName
                                    + " , tileID : "
                                    + tile.tileId);
                    dashboardCategory.addTile(tile);
                }
            } else {
                Log.d(
                        "SecTileUtils",
                        "category.addTile " + tile.mComponentName + " , tileID : " + tile.tileId);
                dashboardCategory.addTile(tile);
            }
        }
        ArrayList arrayList2 = new ArrayList(hashMap.values());
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            Collections.sort(((DashboardCategory) it2.next()).mTiles, Tile.TILE_COMPARATOR);
        }
        Trace.endSection();
        return arrayList2;
    }

    public static boolean isAllowListed(Context context, String str) {
        try {
            PackageInfo packageInfo =
                    context.getPackageManager()
                            .getPackageInfo(str, UcmAgentService.ERROR_APPLET_UNKNOWN);
            String str2 = packageInfo.packageName;
            if (TextUtils.equals("com.sec.android.app.shealth", str2)) {
                if (!Build.IS_ENG && !Build.IS_USERDEBUG) {
                    return isSignatureAllowListed(packageInfo);
                }
                return true;
            }
            Log.e("SecTileUtils", "Package name: " + str2 + " is not allow listed.");
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("SecTileUtils", "Could not find package name.", e);
            return false;
        }
    }

    public static boolean isSignatureAllowListed(PackageInfo packageInfo) {
        boolean z;
        if (packageInfo.signingInfo.getApkContentsSigners().length != 1) {
            Log.w("SecTileUtils", "Package has more than one signature.");
            return false;
        }
        byte[] byteArray = packageInfo.signingInfo.getApkContentsSigners()[0].toByteArray();
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(byteArray);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                sb.append(String.format("%02X", Byte.valueOf(digest[i])));
                if (i != digest.length - 1) {
                    sb.append(":");
                }
            }
            z =
                    TextUtils.equals(
                            sb.toString(),
                            "B9:A4:2D:D5:FC:4E:05:48:89:AE:41:27:A6:27:4C:EC:64:E7:5C:41:73:3D:42:F5:99:1E:70:19:F9:EA:5C:AF");
        } catch (NoSuchAlgorithmException e) {
            Log.e("SecTileUtils", "Failed to obtain SHA-256 digest impl.", e);
            z = false;
        }
        if (!z) {
            Log.d(
                    "SecTileUtils",
                    packageInfo.packageName
                            + " cert not in list.  Cert:\n"
                            + Base64.encodeToString(byteArray, 0));
        }
        return z;
    }

    public static void loadTile(
            Context context,
            UserHandle userHandle,
            Map map,
            List list,
            Intent intent,
            Bundle bundle,
            ComponentInfo componentInfo) {
        String str;
        Bundle bundle2;
        Tile activityTile;
        if (userHandle.getIdentifier() == mCurrentUserId
                || !Tile.isPrimaryProfileOnly(componentInfo.metaData)) {
            if (bundle == null || !bundle.containsKey("com.android.settings.category")) {
                StringBuilder sb = new StringBuilder("Found ");
                sb.append(componentInfo.name);
                sb.append(" for intent ");
                sb.append(intent);
                sb.append(" missing metadata ");
                MainClear$$ExternalSyntheticOutline0.m$1(
                        sb,
                        bundle == null ? ApnSettings.MVNO_NONE : "com.android.settings.category",
                        "SecTileUtils");
                str = null;
            } else if (bundle.containsKey("com.samsung.android.settings.category")) {
                if (!componentInfo.name.startsWith(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG)) {
                    Log.i(
                            "SecTileUtils",
                            "Sec Found "
                                    + componentInfo.name
                                    + " for intent "
                                    + intent
                                    + " metadata "
                                    + bundle);
                }
                str = bundle.getString("com.samsung.android.settings.category");
            } else {
                if (componentInfo.name.startsWith(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG)) {
                    Log.i(
                            "SecTileUtils",
                            "Found "
                                    + componentInfo.name
                                    + " for intent "
                                    + intent
                                    + " metadata "
                                    + bundle);
                }
                str = bundle.getString("com.android.settings.category");
            }
            if (str == null) {
                return;
            }
            boolean z = componentInfo instanceof ProviderInfo;
            Pair pair =
                    z
                            ? new Pair(
                                    ((ProviderInfo) componentInfo).authority,
                                    bundle.getString("com.android.settings.keyhint"))
                            : new Pair(componentInfo.packageName, componentInfo.name);
            Tile tile = (Tile) map.get(pair);
            if (tile == null) {
                if (z) {
                    activityTile = new ProviderTile((ProviderInfo) componentInfo, str, bundle);
                } else {
                    ActivityInfo activityInfo = (ActivityInfo) componentInfo;
                    activityTile = new ActivityTile(activityInfo, str, activityInfo.metaData);
                }
                tile = activityTile;
                map.put(pair, tile);
                StringBuilder sb2 = new StringBuilder("addedCache put key : ");
                sb2.append(pair);
                AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                        sb2, " , categoryKey ", str, "SecTileUtils");
            } else {
                Log.d("SecTileUtils", "setMetaData key : " + pair + " , categoryKey " + str);
                tile.mMetaData = bundle;
            }
            if (bundle.containsKey("com.samsung.android.settings.tileid")) {
                String string = bundle.getString("com.samsung.android.settings.tileid");
                tile.tileId = string;
                DialogFragment$$ExternalSyntheticOutline0.m(
                        "META_DATA_SAMSUNG_TILE_ID/META_DATA_TILE_ID tile.tileId : ",
                        string,
                        "SecTileUtils");
            } else if (bundle.containsKey("com.android.settings.tileid")) {
                String string2 = bundle.getString("com.android.settings.tileid");
                tile.tileId = string2;
                DialogFragment$$ExternalSyntheticOutline0.m(
                        "META_DATA_SAMSUNG_TILE_ID/META_DATA_TILE_ID tile.tileId : ",
                        string2,
                        "SecTileUtils");
            }
            if (bundle.containsKey("com.android.settings.support")) {
                String string3 = bundle.getString("com.android.settings.support");
                tile.support = string3;
                DialogFragment$$ExternalSyntheticOutline0.m(
                        "META_DATA_PREFERENCE_SUPPORT tile.support : ", string3, "SecTileUtils");
            }
            StringBuilder sb3 = new StringBuilder("com.android.settings.order_");
            String str2 = mSdkVersionString;
            sb3.append(str2);
            if (bundle.containsKey(sb3.toString())) {
                if (bundle.get("com.android.settings.order_" + str2) instanceof Integer) {
                    tile.mOrderOverride = bundle.getInt("com.android.settings.order_" + str2);
                }
            }
            if ("com.google.android.gms".equals(componentInfo.packageName)) {
                if ("com.android.settings.category.ia.homepage".equals(tile.mCategory)) {
                    tile.support = "GLOBAL";
                    tile.tileId = "google_settings";
                    tile.mIconResIdOverride = R.drawable.sec_ic_settings_google;
                    tile.mTitleResIdOverride = R.string.google_settings_title;
                    tile.mSummaryResIdOverride = R.string.google_settings_summary;
                    tile.mOrderOverride = -120000;
                    tile.mSaLoggingIdOverride = String.valueOf(8700);
                } else if ("com.android.settings.category.ia.security".equals(tile.mCategory)) {
                    if ("security_status_package_verifier".equals(tile.getKey(context))) {
                        tile.mOrderOverride = 1150;
                    } else if ("security_status_find_device".equals(tile.getKey(context))) {
                        tile.mOrderOverride = 1180;
                    } else if ("security_status_security_patch_level"
                            .equals(tile.getKey(context))) {
                        tile.mOrderOverride = 1160;
                    }
                } else if ("com.android.settings.category.ia.privacy".equals(tile.mCategory)) {
                    tile.mSummaryOverride = ApnSettings.MVNO_NONE;
                } else if ("com.android.settings.category.ia.emergency".equals(tile.mCategory)) {
                    tile.mSummaryOverride = ApnSettings.MVNO_NONE;
                    if ("com.google.android.gms.thunderbird.settings.ThunderbirdSettingInjectorPlatformService"
                            .equals(tile.mComponentName)) {
                        tile.mOrderOverride = 800;
                        tile.mIconResIdOverride = -1;
                    } else if ("com.google.android.gms.location.settings.DrivingBehaviorPlatformSettingsActivity"
                            .equals(tile.mComponentName)) {
                        tile.mOrderOverride = 600;
                        tile.mIconResIdOverride = -1;
                    } else if ("com.google.android.gms.location.settings.EAlertPlatformSettingsActivity"
                            .equals(tile.mComponentName)) {
                        tile.mOrderOverride = 1000;
                        tile.mIconResIdOverride = -1;
                    } else if ("com.google.android.personalsafety.settings.BleTagPlatformSettingsActivity"
                            .equals(tile.mComponentName)) {
                        tile.mOrderOverride =
                                VolteConstants.ErrorCode.NON_STANDARD_ERROR_CODE_BASE_CALL;
                        tile.mIconResIdOverride = -1;
                    }
                }
            } else if ("com.google.android.as".equals(componentInfo.packageName)) {
                if ("com.android.settings.category.ia.privacy".equals(tile.mCategory)) {
                    tile.mSummaryOverride = ApnSettings.MVNO_NONE;
                }
            } else if ("com.android.vending".equals(componentInfo.packageName)) {
                if ("security_status_partial_system_updates".equals(tile.getKey(context))) {
                    tile.mOrderOverride = 1170;
                }
            } else if ("com.samsung.android.themestore".equals(componentInfo.packageName)) {
                tile.mIconResIdOverride = R.drawable.sec_ic_settings_themes;
                tile.mSaLoggingIdOverride = String.valueOf(10600);
            } else if ("com.nttdocomo.android.docomoset".equals(componentInfo.packageName)) {
                if ("com.android.settings.category.ia.homepage".equals(tile.mCategory)) {
                    tile.support = "DCM";
                    tile.tileId = "docomoservice_settings";
                    tile.mIconResIdOverride = R.drawable.sec_ic_settings_docomo_au;
                    tile.mOrderOverride = -125000;
                }
            } else if ("com.kddi.android.au_setting_menu".equals(componentInfo.packageName)) {
                if ("com.android.settings.category.ia.homepage".equals(tile.mCategory)) {
                    tile.tileId = "au_settings_menu";
                    tile.mIconResIdOverride = R.drawable.sec_ic_settings_docomo_au;
                    tile.mOrderOverride = -125000;
                }
            } else if ("com.lguplus.lgusetting".equals(componentInfo.packageName)) {
                tile.mIconResIdOverride = R.drawable.sec_ic_settings_lgu;
            } else if ("com.skt.t_smart_charge".equals(componentInfo.packageName)) {
                tile.mOrderOverride = -115000;
                tile.mIconResIdOverride = R.drawable.sec_ic_settings_t_service;
            } else if ("com.samsung.android.privateshare".equals(componentInfo.packageName)) {
                tile.mOrderOverride = 1800;
            }
            if ("com.samsung.android.settings.category.ia.usefulfeatures".equals(tile.mCategory)) {
                if ("com.samsung.android.bixby.settings.dynamicmenu.EntryActivity"
                        .equals(tile.mComponentName)) {
                    tile.mOrderOverride = 110000;
                } else if ("com.samsung.android.smartsuggestions.settings.SmartSuggestionsSettingActivity"
                        .equals(tile.mComponentName)) {
                    tile.mOrderOverride = 120000;
                }
            }
            if ("com.samsung.android.settings.category.ia.intelligenceservice"
                            .equals(tile.mCategory)
                    && (bundle2 = tile.mMetaData) != null
                    && bundle2.containsKey("com.samsung.android.settings.is.order")) {
                tile.mOrderOverride =
                        tile.mMetaData.getInt("com.samsung.android.settings.is.order");
            }
            if (!tile.userHandle.contains(userHandle)) {
                tile.userHandle.add(userHandle);
            }
            ArrayList arrayList = (ArrayList) list;
            if (arrayList.contains(tile)) {
                return;
            }
            arrayList.add(tile);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0157 A[LOOP:2: B:44:0x0151->B:46:0x0157, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0119  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void loadTilesForAction(
            android.content.Context r18,
            android.os.UserHandle r19,
            java.lang.String r20,
            java.util.Map r21,
            java.util.List r22,
            boolean r23) {
        /*
            Method dump skipped, instructions count: 365
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.core.SecTileUtils.loadTilesForAction(android.content.Context,"
                    + " android.os.UserHandle, java.lang.String, java.util.Map, java.util.List,"
                    + " boolean):void");
    }
}
