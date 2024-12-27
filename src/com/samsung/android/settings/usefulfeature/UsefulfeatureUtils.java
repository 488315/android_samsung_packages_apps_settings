package com.samsung.android.settings.usefulfeature;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.UiModeManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.hardware.display.DisplayManager;
import android.media.MediaRouter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityManager;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.internal.util.CollectionUtils;
import com.android.settings.Utils;
import com.android.settingslib.accessibility.AccessibilityUtils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.samsung.android.app.SemMultiWindowManager;
import com.samsung.android.displaysolution.SemDisplaySolutionManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.gesture.SemMotionRecognitionManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.lib.episode.SourceInfo;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.sdk.cover.ScoverManager;
import com.samsung.android.sdk.cover.ScoverState;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.account.AccountUtils;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyUtils;
import com.samsung.android.settings.usefulfeature.functionkey.item.FunctionKeyAction;
import com.samsung.android.settings.usefulfeature.functionkey.item.FunctionKeyApplication;
import com.samsung.android.settings.usefulfeature.functionkey.item.FunctionKeyItem;
import com.samsung.android.view.SemWindowManager;
import com.sec.ims.volte2.data.VolteConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class UsefulfeatureUtils {
    public static final String FUNC_KEY_DOUBLE_SELECTED_ACTIONS_DEFAULT;
    public static final String FUNC_KEY_DOUBLE_SELECTED_ITEM_DEFAULT;
    public static boolean mAccessControlEnabled = false;
    public static boolean mAccessibilityEnabled = false;
    public static boolean mAssistantMenuEnabled = false;
    public static boolean mTalkBackEnabled = false;
    public static boolean mUniversalSwitchEnabled = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class IntentData {

        @SerializedName("intentUri")
        String intentUri;

        @SerializedName("type")
        int type;

        public IntentData(int i, String str) {
            this.type = i;
            this.intentUri = str;
        }
    }

    static {
        Uri.parse("content://com.samsung.android.app.appsedge.data.AppsEdgeDataProvider");
        FUNC_KEY_DOUBLE_SELECTED_ITEM_DEFAULT = "key_camera";
        FUNC_KEY_DOUBLE_SELECTED_ACTIONS_DEFAULT = "{\"key_camera\":\"key_quick_launch_camera\"}";
    }

    public static String getAppSplitViewPackagesInfo(Context context) {
        ArrayList arrayList = new ArrayList();
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            Log.secD("UsefulfeatureUtils", "PackageManager is null");
            return null;
        }
        arrayList.clear();
        arrayList.addAll(getSplitActivityApplications(packageManager));
        if (arrayList.size() == 0) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        JSONArray jSONArray2 = new JSONArray();
        List supportEmbedActivityPackages =
                MultiWindowManager.getInstance().getSupportEmbedActivityPackages();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str = ((ApplicationInfo) it.next()).packageName;
            jSONArray.put(str);
            jSONArray2.put(
                    (supportEmbedActivityPackages == null
                                    || !supportEmbedActivityPackages.contains(str))
                            ? Integer.valueOf(
                                    MultiWindowManager.getInstance()
                                            .getSplitActivityPackageEnabled(
                                                    str, UserHandle.getCallingUserId()))
                            : Boolean.valueOf(
                                    MultiWindowManager.getInstance()
                                            .getEmbedActivityPackageEnabled(
                                                    str, UserHandle.getCallingUserId())));
        }
        try {
            jSONObject.put("package", jSONArray);
            jSONObject.put("mode", jSONArray2);
        } catch (JSONException e) {
            Log.secE(
                    "UsefulfeatureUtils",
                    "Failed to put AppSplitViewPackages in json object. " + e.getMessage());
            e.printStackTrace();
        }
        if (jSONObject.length() == 0) {
            return null;
        }
        return jSONObject.toString();
    }

    public static String getAutoRotatePackagesFlagInfo(Context context, SourceInfo sourceInfo) {
        if (context.getPackageManager() == null) {
            Log.secD("UsefulfeatureUtils", "PackageManager is null");
            return null;
        }
        List<LauncherActivityInfo> activityList =
                ((LauncherApps) context.getSystemService("launcherapps"))
                        .getActivityList(null, new UserHandle(UserHandle.getCallingUserId()));
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        JSONArray jSONArray2 = new JSONArray();
        Iterator<LauncherActivityInfo> it = activityList.iterator();
        while (it.hasNext()) {
            String str = it.next().getApplicationInfo().packageName;
            int packageOrientation = getPackageOrientation(str);
            jSONArray.put(Integer.parseInt(sourceInfo.getPackageIndex(str)));
            jSONArray2.put(packageOrientation);
        }
        try {
            jSONObject.put("package", jSONArray);
            jSONObject.put("mode", jSONArray2);
        } catch (JSONException e) {
            Log.secE(
                    "UsefulfeatureUtils",
                    "Failed to put AutoRotatePackages in json object. " + e.getMessage());
            e.printStackTrace();
        }
        if (jSONObject.length() == 0) {
            return null;
        }
        return jSONObject.toString();
    }

    public static String getDarkModeAppsPackagesInfo(Context context, SourceInfo sourceInfo) {
        UiModeManager uiModeManager = (UiModeManager) context.getSystemService(UiModeManager.class);
        if (uiModeManager == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        JSONArray jSONArray2 = new JSONArray();
        for (String str : uiModeManager.getNightPriorityAllowedPackagesFromScpm()) {
            if (Utils.hasPackage(context, str)) {
                jSONArray.put(Integer.parseInt(sourceInfo.getPackageIndex(str)));
                jSONArray2.put(uiModeManager.getPackageNightMode(str));
            }
        }
        try {
            jSONObject.put("package", jSONArray);
            jSONObject.put("mode", jSONArray2);
        } catch (JSONException e) {
            Log.e(
                    "UsefulfeatureUtils",
                    "getDarkModeAppsPackagesInfo : Failed to put DarkModeAppsInfo in json object. "
                            + e.getMessage());
            e.printStackTrace();
        }
        if (jSONObject.length() == 0) {
            return null;
        }
        return jSONObject.toString();
    }

    public static int getManagedProfileId(Context context) {
        for (UserInfo userInfo :
                ((UserManager) context.getSystemService(UserManager.class))
                        .getProfiles(context.getUserId())) {
            if (userInfo.isManagedProfile() && !Utils.isExcludedManagedProfile(userInfo)) {
                Preference$$ExternalSyntheticOutline0.m(
                        new StringBuilder("getManagedProfileId = "),
                        userInfo.id,
                        "UsefulfeatureUtils");
                return userInfo.id;
            }
        }
        Log.d("UsefulfeatureUtils", "getManagedProfileId is null");
        return -10000;
    }

    public static int getPackageOrientation(String str) {
        try {
            return ActivityTaskManager.getService()
                    .getOrientationControlPolicy(UserHandle.getCallingUserId(), str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getPaywithSamsungPayTitle(Context context) {
        try {
            return context.getContentResolver()
                    .call(
                            Uri.parse("content://com.samsung.android.spay.common.share/global"),
                            "GET_global",
                            "setting_title_side_key_launch",
                            (Bundle) null)
                    .getString("value");
        } catch (NullPointerException e) {
            e.printStackTrace();
            return ApnSettings.MVNO_NONE;
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            return ApnSettings.MVNO_NONE;
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace();
            return ApnSettings.MVNO_NONE;
        }
    }

    public static int getPolicyFromLegacyFlag(int i) {
        if (i == 0 || i == 7 || i == 31 || i == 32) {
            return i;
        }
        if ((i & 7) != 0) {
            return (i & 24) != 0 ? 31 : 7;
        }
        return 0;
    }

    public static String getSamsungAccountTermsURI(int i) {
        String str =
                (Rune.isChinaModel()
                                ? new String(
                                        Base64.decode(
                                                "aHR0cHM6Ly9wb2xpY2llcy5zYW1zdW5nLmNuL3Rlcm1zPw==",
                                                2))
                                : "https://policies.account.samsung.com/terms?")
                        + "appKey=j5p7ll8g33"
                        + AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                                "&applicationRegion=", Locale.getDefault().getISO3Country())
                        + ("&language=" + Locale.getDefault().getISO3Language())
                        + AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                                "&region=", Locale.getDefault().getISO3Country())
                        + (i == 0
                                ? "&type=TC"
                                : (i == 1 && "KR".equalsIgnoreCase(Utils.readCountryCode()))
                                        ? "&type=PDP"
                                        : "&type=PN");
        DialogFragment$$ExternalSyntheticOutline0.m(
                "Samsung account terms uri = ", str, "UsefulfeatureUtils");
        return str;
    }

    public static List getSplitActivityApplications(PackageManager packageManager) {
        List splitActivityAllowPackages =
                MultiWindowManager.getInstance().getSplitActivityAllowPackages();
        final List supportEmbedActivityPackages =
                MultiWindowManager.getInstance().getSupportEmbedActivityPackages();
        final List list =
                (List)
                        splitActivityAllowPackages.stream()
                                .filter(new UsefulfeatureUtils$$ExternalSyntheticLambda1())
                                .collect(Collectors.toList());
        list.addAll(list.size(), supportEmbedActivityPackages);
        return (List)
                packageManager
                        .getInstalledApplicationsAsUser(0, UserHandle.getCallingUserId())
                        .stream()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.samsung.android.settings.usefulfeature.UsefulfeatureUtils$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        List list2 = supportEmbedActivityPackages;
                                        List list3 = list;
                                        ApplicationInfo applicationInfo = (ApplicationInfo) obj;
                                        if (!Utils.isTablet()) {
                                            list2 = list3;
                                        }
                                        return list2.contains(applicationInfo.packageName);
                                    }
                                })
                        .collect(Collectors.toList());
    }

    public static int getTypeOfCover(Context context) {
        int i = 2;
        try {
            ScoverState coverState = new ScoverManager(context).getCoverState();
            if (coverState != null) {
                i = coverState.type;
            } else {
                Log.secD("Utils", "Scover getTypeOfCover ScoverState is null");
            }
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
        Log.secD("Utils", "Scover getTypeOfCover type : " + i);
        return i;
    }

    public static String getVideoEnhanceAppInfo(Context context) {
        SemDisplaySolutionManager semDisplaySolutionManager =
                (SemDisplaySolutionManager) context.getSystemService("DisplaySolution");
        String[] stringArray =
                context.getResources().getStringArray(R.array.config_integrityRuleProviderPackages);
        String[] stringArray2 =
                context.getResources().getStringArray(R.array.config_ephemeralResolverPackage);
        int length = stringArray.length + stringArray2.length;
        String[] strArr = new String[length];
        System.arraycopy(stringArray2, 0, strArr, 0, stringArray2.length);
        System.arraycopy(stringArray, 0, strArr, stringArray2.length, stringArray.length);
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        JSONArray jSONArray2 = new JSONArray();
        for (int i = 0; i < length; i++) {
            String str = strArr[i];
            if (semDisplaySolutionManager.getVideoEnhancerSettingState(str) == 0) {
                jSONArray.put(str);
            } else if (semDisplaySolutionManager.getVideoEnhancerSettingState(str) == 1) {
                jSONArray2.put(str);
            }
        }
        try {
            jSONObject.put("false_names", jSONArray);
            jSONObject.put("true_names", jSONArray2);
        } catch (JSONException e) {
            Log.secD(
                    "UsefulfeatureUtils",
                    "Failed to put FrontScreenAppsInfo in json object. " + e.getMessage());
            e.printStackTrace();
        }
        if (jSONObject.length() == 0) {
            return null;
        }
        return jSONObject.toString();
    }

    public static boolean hasActiveKey() {
        return !TextUtils.isEmpty(ApnSettings.MVNO_NONE);
    }

    public static boolean hasBMCPttEnable(Context context) {
        if (!Utils.hasPackage(context, "com.bell.ptt")) {
            return false;
        }
        Intent intent = new Intent("com.kodiak.intent.action.PTT_BUTTON");
        intent.setPackage("com.bell.ptt");
        if (context.getPackageManager().queryBroadcastReceivers(intent, 0).size() <= 0) {
            return false;
        }
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return "BMC".equals(Utils.getSalesCode());
    }

    public static boolean hasDedicatedAppEnable$1(Context context) {
        if (hasActiveKey() && !KnoxUtils.isRuggedFeatureEnabled()) {
            if (!hasVzwPttEnable(context)
                    && !hasBMCPttEnable(context)
                    && !hasTMOPttEnable(context)
                    && !hasEPttEnable(context)) {
                try {
                    Iterator<ApplicationInfo> it =
                            context.getPackageManager().getInstalledApplications(128).iterator();
                    while (it.hasNext()) {
                        Bundle bundle = it.next().metaData;
                        if (bundle != null
                                && bundle.getBoolean(
                                        "com.samsung.android.knox.intent.action.HARD_KEY_PRESS",
                                        false)) {
                            Log.d("UsefulfeatureUtils", "knox HARD_KEY_PRESS true");
                        }
                    }
                } catch (Exception unused) {
                }
            }
            return true;
        }
        return false;
    }

    public static boolean hasDockSettings(Context context) {
        boolean z;
        boolean z2;
        if (!SemFloatingFeature.getInstance()
                .getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_ACCESSORY")) {
            return false;
        }
        if (Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) != 0) {
            z2 = isCoverVerified(context);
            z =
                    Settings.Secure.getInt(
                                    context.getContentResolver(), "mate_setting_activation", 0)
                            == 1;
        } else {
            z = false;
            z2 = false;
        }
        Log.secD("Utils", "hasDockSettings : " + z2);
        Log.secD("Utils", "MateSettingActivation : " + z);
        return z2 || z;
    }

    public static boolean hasEPttEnable(Context context) {
        if (Utils.hasPackage(context, "com.att.eptt")) {
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            if ("ATT".equals(Utils.getSalesCode()) || "AIO".equals(Utils.getSalesCode())) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasFeatureAutoScreenTurnOn(Context context) {
        return context.getPackageManager().hasSystemFeature("com.sec.feature.cover.autoscreenon");
    }

    public static boolean hasSideKeyDedicatedAppEnable(Context context) {
        if (!KnoxUtils.isRuggedFeatureEnabled()) {
            try {
                Iterator<ApplicationInfo> it =
                        context.getPackageManager().getInstalledApplications(128).iterator();
                while (it.hasNext()) {
                    Bundle bundle = it.next().metaData;
                    if (bundle != null
                            && bundle.getBoolean(
                                    "com.samsung.android.knox.intent.action.addon.SIDE_KEY_DELTA",
                                    false)) {
                        Log.d("UsefulfeatureUtils", "knox HARD_KEY_PRESS true");
                        return true;
                    }
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }

    public static boolean hasTMOPttEnable(Context context) {
        if (Utils.hasPackage(context, "com.sprint.sdcplus")) {
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            if ("TMB".equals(Utils.getSalesCode())
                    || "TMK".equals(Utils.getSalesCode())
                    || "ASR".equals(Utils.getSalesCode())) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasVzwPttEnable(Context context) {
        if (Utils.hasPackage(context, "com.verizon.pushtotalkplus")) {
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            if ("VZW".equals(Utils.getSalesCode()) || "VPP".equals(Utils.getSalesCode())) {
                return true;
            }
        }
        return false;
    }

    public static void hasXcoverTopKeySetting() {
        hasActiveKey();
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isCoverVerified(android.content.Context r6) {
        /*
            java.lang.String r0 = "Utils"
            r1 = 0
            r2 = 2
            com.samsung.android.sdk.cover.ScoverManager r3 = new com.samsung.android.sdk.cover.ScoverManager     // Catch: java.lang.NoClassDefFoundError -> L16
            r3.<init>(r6)     // Catch: java.lang.NoClassDefFoundError -> L16
            com.samsung.android.sdk.cover.ScoverState r6 = r3.getCoverState()     // Catch: java.lang.NoClassDefFoundError -> L16
            if (r6 == 0) goto L19
            boolean r3 = r6.attached     // Catch: java.lang.NoClassDefFoundError -> L16
            int r6 = r6.type     // Catch: java.lang.NoClassDefFoundError -> L14
            goto L24
        L14:
            r6 = move-exception
            goto L20
        L16:
            r6 = move-exception
            r3 = r1
            goto L20
        L19:
            java.lang.String r6 = "cover getTypeOfCover ScoverState is null"
            android.util.Log.secD(r0, r6)     // Catch: java.lang.NoClassDefFoundError -> L16
            r3 = r1
            goto L23
        L20:
            r6.printStackTrace()
        L23:
            r6 = r2
        L24:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "cover attached : "
            r4.<init>(r5)
            r4.append(r3)
            java.lang.String r5 = ", type : "
            r4.append(r5)
            r4.append(r6)
            java.lang.String r4 = r4.toString()
            android.util.Log.secD(r0, r4)
            if (r3 == 0) goto L50
            r0 = 9
            if (r6 == r0) goto L50
            r0 = 10
            if (r6 == r0) goto L50
            r0 = 12
            if (r6 == r0) goto L50
            if (r6 != r2) goto L4e
            goto L50
        L4e:
            r6 = 1
            return r6
        L50:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.usefulfeature.UsefulfeatureUtils.isCoverVerified(android.content.Context):boolean");
    }

    public static boolean isEnabledComponent(Context context, ComponentName componentName) {
        try {
            int componentEnabledSetting =
                    context.getPackageManager().getComponentEnabledSetting(componentName);
            if (componentEnabledSetting == 2 || componentEnabledSetting == 3) {
                Log.secD("SETTING_UTILS", componentName + " is diabled");
                return false;
            }
            Log.secD("SETTING_UTILS", componentName + " is enabled");
            return true;
        } catch (IllegalArgumentException unused) {
            Log.secD("SETTING_UTILS", componentName + " is not installed");
            return false;
        }
    }

    public static boolean isEnabledOneHandOperation(Context context) {
        return (Rune.isSamsungDexMode(context) || Utils.isDesktopStandaloneMode(context))
                ? false
                : true;
    }

    public static boolean isEnabledPackage(Context context, String str) {
        try {
            int applicationEnabledSetting =
                    context.getPackageManager().getApplicationEnabledSetting(str);
            if (applicationEnabledSetting == 2 || applicationEnabledSetting == 3) {
                Log.secD("SETTING_UTILS", str + " is diabled");
                return false;
            }
            Log.secD("SETTING_UTILS", str + " is enabled");
            return true;
        } catch (IllegalArgumentException unused) {
            Log.secD("SETTING_UTILS", str + " is not installed");
            return false;
        }
    }

    public static boolean isExistAccessbilitiyPackage(Context context, String str) {
        boolean z = mAccessibilityEnabled;
        if (z) {
            return z;
        }
        boolean hasPackage = Utils.hasPackage(context, "com.samsung.accessibility");
        mAccessibilityEnabled = hasPackage;
        if (hasPackage) {
            return hasPackage;
        }
        if ("com.samsung.android.app.talkback".equals(str)) {
            boolean z2 = mTalkBackEnabled;
            if (z2) {
                return z2;
            }
            boolean hasPackage2 = Utils.hasPackage(context, "com.samsung.android.app.talkback");
            mTalkBackEnabled = hasPackage2;
            return hasPackage2;
        }
        if ("com.samsung.android.universalswitch".equals(str)) {
            boolean z3 = mUniversalSwitchEnabled;
            if (z3) {
                return z3;
            }
            boolean hasPackage3 = Utils.hasPackage(context, "com.samsung.android.universalswitch");
            mUniversalSwitchEnabled = hasPackage3;
            return hasPackage3;
        }
        if ("com.samsung.android.app.accesscontrol".equals(str)) {
            boolean z4 = mAccessControlEnabled;
            if (z4) {
                return z4;
            }
            boolean hasPackage4 =
                    Utils.hasPackage(context, "com.samsung.android.app.accesscontrol");
            mAccessControlEnabled = hasPackage4;
            return hasPackage4;
        }
        if (!"com.samsung.android.app.assistantmenu".equals(str)) {
            return false;
        }
        boolean z5 = mAssistantMenuEnabled;
        if (z5) {
            return z5;
        }
        boolean hasPackage5 = Utils.hasPackage(context, "com.samsung.android.app.assistantmenu");
        mAssistantMenuEnabled = hasPackage5;
        return hasPackage5;
    }

    public static boolean isExistNightPriorityAllowedPackage(Context context) {
        UiModeManager uiModeManager = (UiModeManager) context.getSystemService(UiModeManager.class);
        if (uiModeManager == null) {
            return false;
        }
        Iterator it = uiModeManager.getNightPriorityAllowedPackagesFromScpm().iterator();
        while (it.hasNext()) {
            if (Utils.hasPackage(context, (String) it.next())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPayWithSamsungPayVisible(Context context) {
        try {
            return Integer.parseInt(
                            context.getContentResolver()
                                    .call(
                                            Uri.parse(
                                                    "content://com.samsung.android.spay.common.share/global"),
                                            "GET_global",
                                            "simplepay_side_key_launch",
                                            (Bundle) null)
                                    .getString("value"))
                    == 1;
        } catch (NullPointerException e) {
            Log.e(
                    "UsefulfeatureUtils",
                    "isPayWithSamsungPayVisible NullPointerException : " + e.getMessage());
            return false;
        } catch (NumberFormatException e2) {
            Log.e(
                    "UsefulfeatureUtils",
                    "isPayWithSamsungPayVisible NumberFormatException : " + e2.getMessage());
            return false;
        } catch (IllegalArgumentException e3) {
            Log.e(
                    "UsefulfeatureUtils",
                    "isPayWithSamsungPayVisible IllegalArgumentException : " + e3.getMessage());
            return false;
        }
    }

    public static boolean isReadyOneHandedOperationStatusEnable(Context context) {
        boolean z;
        boolean semIsScreenReaderEnabled =
                ((AccessibilityManager) context.getSystemService("accessibility"))
                        .semIsScreenReaderEnabled();
        TextUtils.SimpleStringSplitter simpleStringSplitter =
                new TextUtils.SimpleStringSplitter(':');
        String string =
                Settings.Secure.getString(
                        context.getContentResolver(), "enabled_accessibility_services");
        if (string == null) {
            string = ApnSettings.MVNO_NONE;
        }
        simpleStringSplitter.setString(string);
        while (simpleStringSplitter.hasNext()) {
            ComponentName unflattenFromString =
                    ComponentName.unflattenFromString(simpleStringSplitter.next());
            if (unflattenFromString != null
                    && "com.google.android.marvin.talkback"
                            .equals(unflattenFromString.getPackageName())
                    && (string.contains(
                                    "com.google.android.marvin.talkback/com.googlecode.eyesfree.switchcontrol.SwitchControlService")
                            || string.contains(
                                    "com.google.android.marvin.talkback/com.android.switchaccess.SwitchAccessService"))) {
                z = true;
                break;
            }
        }
        z = false;
        return (z
                        || semIsScreenReaderEnabled
                        || (context.getPackageManager()
                                        .hasSystemFeature("com.sec.feature.overlaymagnifier")
                                && Settings.System.getInt(
                                                context.getContentResolver(),
                                                "accessibility_magnifier",
                                                0)
                                        == 1)
                        || (Settings.Secure.getInt(
                                                context.getContentResolver(),
                                                "accessibility_display_magnification_enabled",
                                                0)
                                        != 0
                                || Settings.Secure.getInt(
                                                context.getContentResolver(),
                                                "accessibility_display_magnification_navbar_enabled",
                                                0)
                                        != 0
                                || Settings.Secure.getInt(
                                                context.getContentResolver(),
                                                "accessibility_magnification_shortcut",
                                                0)
                                        != 0)
                        || (Settings.System.getInt(
                                        context.getContentResolver(), "finger_magnifier", 0)
                                == 1)
                        || (isUniversalSwitchEnabled(context)
                                && isUniversalSwitchSupportMultiUserKnoxMode(context))
                        || (Settings.System.getInt(
                                                context.getContentResolver(),
                                                "access_control_use",
                                                0)
                                        == 1
                                && Build.VERSION.SEM_PLATFORM_INT < 120000)
                        || (Settings.Secure.getInt(
                                        context.getContentResolver(),
                                        "accessibility_auto_action_type",
                                        0)
                                != 0)
                        || (Settings.Secure.getInt(
                                        context.getContentResolver(),
                                        "accessibility_corner_action_enabled",
                                        0)
                                != 0)
                        || (Settings.System.getInt(context.getContentResolver(), "direct_access", 0)
                                        == 1
                                && Build.VERSION.SEM_PLATFORM_INT < 100500)
                        || (Settings.Secure.getInt(
                                        context.getContentResolver(), "tap_duration_enabled", 0)
                                == 1)
                        || (Settings.Secure.getInt(
                                        context.getContentResolver(), "touch_blocking_enabled", 0)
                                == 1)
                        || (Settings.Secure.getInt(
                                        context.getContentResolver(),
                                        "accessibility_sticky_keys",
                                        0)
                                == 1)
                        || (Settings.Secure.getInt(
                                        context.getContentResolver(), "accessibility_slow_keys", 0)
                                != 0)
                        || (Settings.Secure.getInt(
                                        context.getContentResolver(),
                                        "accessibility_bounce_keys",
                                        0)
                                != 0))
                ? false
                : true;
    }

    public static boolean isSupportMotion(Context context, int i) {
        return ((SemMotionRecognitionManager) context.getSystemService("motion_recognition"))
                .isAvailable(i);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00b0 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isSupportMotionFeature(android.content.Context r4, java.lang.String r5) {
        /*
            r0 = 0
            r5.getClass()
            r1 = 1
            r2 = -1
            int r3 = r5.hashCode()
            switch(r3) {
                case -1641438988: goto L30;
                case -1250129644: goto L25;
                case -164739226: goto L19;
                case 1923536950: goto Le;
                default: goto Ld;
            }
        Ld:
            goto L3a
        Le:
            java.lang.String r3 = "easy_mute"
            boolean r5 = r5.equals(r3)
            if (r5 != 0) goto L17
            goto L3a
        L17:
            r2 = 3
            goto L3a
        L19:
            java.lang.String r3 = "smart_alert"
            boolean r5 = r5.equals(r3)
            if (r5 != 0) goto L23
            goto L3a
        L23:
            r2 = 2
            goto L3a
        L25:
            java.lang.String r3 = "palm_swipe_to_capture"
            boolean r5 = r5.equals(r3)
            if (r5 != 0) goto L2e
            goto L3a
        L2e:
            r2 = r1
            goto L3a
        L30:
            java.lang.String r3 = "direct_call"
            boolean r5 = r5.equals(r3)
            if (r5 != 0) goto L39
            goto L3a
        L39:
            r2 = r0
        L3a:
            switch(r2) {
                case 0: goto L95;
                case 1: goto L80;
                case 2: goto L66;
                case 3: goto L3f;
                default: goto L3d;
            }
        L3d:
            goto Lb0
        L3f:
            r5 = 2097152(0x200000, float:2.938736E-39)
            boolean r5 = isSupportMotion(r4, r5)
            if (r5 == 0) goto L53
            com.samsung.android.feature.SemFloatingFeature r5 = com.samsung.android.feature.SemFloatingFeature.getInstance()
            java.lang.String r2 = "SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_MOTION_PALM_TOUCH"
            boolean r5 = r5.getBoolean(r2)
            if (r5 != 0) goto L65
        L53:
            boolean r4 = isSupportMotion(r4, r1)
            if (r4 == 0) goto Lb0
            com.samsung.android.feature.SemFloatingFeature r4 = com.samsung.android.feature.SemFloatingFeature.getInstance()
            java.lang.String r5 = "SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_MOTION_TRUN_OVER"
            boolean r4 = r4.getBoolean(r5)
            if (r4 == 0) goto Lb0
        L65:
            return r1
        L66:
            r5 = 4
            boolean r5 = isSupportMotion(r4, r5)
            if (r5 == 0) goto Lb0
            com.samsung.android.feature.SemFloatingFeature r5 = com.samsung.android.feature.SemFloatingFeature.getInstance()
            java.lang.String r2 = "SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_MOTION_PICK_UP"
            boolean r5 = r5.getBoolean(r2)
            if (r5 == 0) goto Lb0
            boolean r4 = com.android.settings.Utils.isVoiceCapable(r4)
            if (r4 == 0) goto Lb0
            return r1
        L80:
            r5 = 4194304(0x400000, float:5.877472E-39)
            boolean r4 = isSupportMotion(r4, r5)
            if (r4 == 0) goto Lb0
            com.samsung.android.feature.SemFloatingFeature r4 = com.samsung.android.feature.SemFloatingFeature.getInstance()
            java.lang.String r5 = "SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_MOTION_PALM_SWIPE"
            boolean r4 = r4.getBoolean(r5)
            if (r4 == 0) goto Lb0
            return r1
        L95:
            r5 = 1024(0x400, float:1.435E-42)
            boolean r5 = isSupportMotion(r4, r5)
            if (r5 == 0) goto Lb0
            com.samsung.android.feature.SemFloatingFeature r5 = com.samsung.android.feature.SemFloatingFeature.getInstance()
            java.lang.String r2 = "SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_MOTION_PICK_UP_TO_CALL_OUT"
            boolean r5 = r5.getBoolean(r2)
            if (r5 == 0) goto Lb0
            boolean r4 = com.android.settings.Utils.isVoiceCapable(r4)
            if (r4 == 0) goto Lb0
            return r1
        Lb0:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.usefulfeature.UsefulfeatureUtils.isSupportMotionFeature(android.content.Context,"
                    + " java.lang.String):boolean");
    }

    public static boolean isSupportedIntelligenceService(Context context) {
        if (!Rune.FEATURE_INTELLIGENCE_SERVICE) {
            return false;
        }
        if (AccountUtils.isSamsungAccountExists(context) && AccountUtils.isChildAccount(context)) {
            return "KR".equalsIgnoreCase(Utils.readCountryCode());
        }
        return true;
    }

    public static boolean isSupportedIntelligenceServiceForWork(Context context) {
        return Rune.FEATURE_INTELLIGENCE_SERVICE
                && UserHandle.myUserId() == 0
                && Utils.isManagedProfilePresent(context)
                && !(!"KR".equalsIgnoreCase(Utils.readCountryCode())
                        && AccountUtils.isSamsungAccountExists(context)
                        && AccountUtils.isChildAccount(context));
    }

    public static boolean isUniversalSwitchEnabled(Context context) {
        String string;
        if (isUniversalSwitchSupportMultiUserKnoxMode(context)
                && (string =
                                Settings.Secure.getString(
                                        context.getContentResolver(),
                                        "enabled_accessibility_services"))
                        != null) {
            return string.matches(
                    "(?i).*com.samsung.accessibility.universalswitch.UniversalSwitchService.*");
        }
        return false;
    }

    public static boolean isUniversalSwitchSupportMultiUserKnoxMode(Context context) {
        if (context == null) {
            Log.secD("Utils", "isUniversalSwitchSupportMultiUserKnoxMode  context is Null ");
            return true;
        }
        boolean z = UserHandle.myUserId() == 0;
        Log.secD("Utils", "isUniversalSwitchSupportMultiUserKnoxMode  mainUser is : " + z);
        Log.secD("Utils", "isUniversalSwitchSupportMultiUserKnoxMode KnoxEnabled Value is :false");
        return z;
    }

    public static boolean isVideoBrightnessMenuDisabled(Context context) {
        MediaRouter.RouteInfo selectedRoute;
        boolean z = false;
        for (Display display :
                ((DisplayManager) context.getSystemService("display")).getDisplays()) {
            if (display != null && display.getType() == 2) {
                z = true;
            }
        }
        return z
                || !((selectedRoute =
                                        ((MediaRouter) context.getSystemService("media_router"))
                                                .getSelectedRoute(4))
                                == null
                        || selectedRoute.getPlaybackType() != 1
                        || selectedRoute.getPresentationDisplay() == null)
                || (!(new SemMultiWindowManager().getMode() == 0 || Utils.isNewDexMode(context))
                        || (Rune.supportDesktopMode()
                                && Rune.isSamsungDexMode(context)
                                && !Utils.isNewDexMode(context)));
    }

    public static Intent makeSideKeyCustomizationInfoIntent(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        if (unflattenFromString == null) {
            return intent;
        }
        intent.setComponent(unflattenFromString);
        return intent;
    }

    public static void migrationFunctionKeyDB(Context context, int i) {
        if (context == null) {
            return;
        }
        int i2 = 2;
        if (i != 2) {
            if (i == 1) {
                int i3 =
                        Settings.Global.getInt(
                                context.getContentResolver(),
                                "function_key_config_longpress_type",
                                0);
                if (i3 == 0) {
                    Settings.Global.putString(
                            context.getContentResolver(),
                            "function_key_config_longpress_selected_item",
                            "long_press_wake_bixby");
                    return;
                } else if (i3 == 1) {
                    Settings.Global.putString(
                            context.getContentResolver(),
                            "function_key_config_longpress_selected_item",
                            "long_press_power_off");
                    return;
                } else {
                    if (i3 == 2) {
                        Settings.Global.putString(
                                context.getContentResolver(),
                                "function_key_config_longpress_selected_item",
                                "long_press_ai_agent_app");
                        return;
                    }
                    return;
                }
            }
            return;
        }
        int i4 =
                Settings.Global.getInt(
                        context.getContentResolver(), "function_key_config_doublepress_type", -1);
        String string =
                Settings.Global.getString(
                        context.getContentResolver(), "function_key_config_doublepress_value");
        String string2 =
                Settings.Global.getString(
                        context.getContentResolver(), "function_key_config_doublepress_intent");
        if (i4 == 0) {
            setDefaultFunctionKeyDB(context, i);
            return;
        }
        if (i4 != 2) {
            if (i4 == 3) {
                Settings.Global.putString(
                        context.getContentResolver(),
                        "function_key_config_doublepress_selected_item",
                        "key_secure_folder");
                return;
            } else if (i4 == 4) {
                Settings.Global.putString(
                        context.getContentResolver(),
                        "function_key_config_doublepress_selected_item",
                        "key_samsung_pay_quick_access");
                return;
            } else {
                setDefaultFunctionKeyDB(context, i);
                return;
            }
        }
        if (TextUtils.isEmpty(string2)) {
            if (TextUtils.equals(string, "torch/torch")) {
                Settings.Global.putString(
                        context.getContentResolver(),
                        "function_key_config_doublepress_selected_item",
                        "key_flashlight");
                return;
            } else {
                if (TextUtils.equals(string, "com.sec.android.app.magnifier/.Magnifier")) {
                    Settings.Global.putString(
                            context.getContentResolver(),
                            "function_key_config_doublepress_selected_item",
                            "key_magnifier");
                    return;
                }
                Settings.Global.putString(
                        context.getContentResolver(),
                        "function_key_config_doublepress_selected_item",
                        "key_apps");
                Settings.Global.putString(
                        context.getContentResolver(),
                        "function_key_config_doublepress_selected_actions",
                        "{\"key_apps\":\"key_app\"}");
                Settings.Global.putString(
                        context.getContentResolver(),
                        "function_key_config_doublepress_app_action",
                        string);
                return;
            }
        }
        for (FunctionKeyItem functionKeyItem :
                CollectionUtils.emptyIfNull(
                        FunctionKeyUtils.getFunctionKeyItems(context, 2, null))) {
            if (functionKeyItem instanceof FunctionKeyApplication) {
                for (FunctionKeyItem functionKeyItem2 :
                        CollectionUtils.emptyIfNull(
                                FunctionKeyUtils.getFunctionKeyItems(
                                        context, i2, functionKeyItem.key))) {
                    if (functionKeyItem2 instanceof FunctionKeyAction) {
                        FunctionKeyAction functionKeyAction = (FunctionKeyAction) functionKeyItem2;
                        String str = functionKeyAction.actionValue;
                        String json =
                                new Gson()
                                        .toJson(
                                                new IntentData(
                                                        functionKeyAction.actionIntentType,
                                                        functionKeyAction.actionIntent.toUri(1)));
                        if (TextUtils.equals(string, str) && TextUtils.equals(string2, json)) {
                            ContentResolver contentResolver = context.getContentResolver();
                            String str2 = functionKeyItem.key;
                            Settings.Global.putString(
                                    contentResolver,
                                    "function_key_config_doublepress_selected_item",
                                    str2);
                            ContentResolver contentResolver2 = context.getContentResolver();
                            StringBuilder m =
                                    ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                            "{\"", str2, "\":\"");
                            m.append(functionKeyItem2.key);
                            m.append("\"}");
                            Settings.Global.putString(
                                    contentResolver2,
                                    "function_key_config_doublepress_selected_actions",
                                    m.toString());
                            return;
                        }
                    }
                }
            } else if (functionKeyItem instanceof FunctionKeyAction) {
                FunctionKeyAction functionKeyAction2 = (FunctionKeyAction) functionKeyItem;
                String str3 = functionKeyAction2.actionValue;
                String json2 =
                        new Gson()
                                .toJson(
                                        new IntentData(
                                                functionKeyAction2.actionIntentType,
                                                functionKeyAction2.actionIntent.toUri(1)));
                if (TextUtils.equals(string, str3) && TextUtils.equals(string2, json2)) {
                    Settings.Global.putString(
                            context.getContentResolver(),
                            "function_key_config_doublepress_selected_item",
                            functionKeyItem.key);
                    return;
                }
            } else {
                continue;
            }
            i2 = 2;
        }
        setDefaultFunctionKeyDB(context, i);
    }

    public static String oneHandOperationDisablepopupMessage(Context context) {
        StringBuilder sb = new StringBuilder();
        if (context.getPackageManager().hasSystemFeature("com.sec.feature.overlaymagnifier")
                && ActivityManager.getCurrentUser() == 0) {
            sb.append("• ");
            sb.append(
                    context.getString(com.android.settings.R.string.accessibility_magnifier_title));
            sb.append("\n");
        }
        if (isExistAccessbilitiyPackage(context, "com.samsung.android.app.talkback")) {
            if (Utils.isRTL(context)) {
                sb.append("\u200f ");
            }
            sb.append("• ");
            sb.append(
                    context.getString(
                            com.android.settings.R.string.direct_access_actions_talkback_title));
            sb.append("\n");
        }
        if (Utils.hasPackage(context, "com.google.android.marvin.talkback")) {
            if (Utils.isRTL(context)) {
                sb.append("\u200f ");
            }
            sb.append("• ");
            sb.append(
                    context.getString(
                            com.android.settings.R.string.direct_access_actions_talkback_title));
            sb.append("\n");
        }
        if (isExistAccessbilitiyPackage(context, "com.samsung.android.universalswitch")
                && isUniversalSwitchSupportMultiUserKnoxMode(context)) {
            sb.append("• ");
            sb.append(
                    context.getString(
                            com.android.settings.R.string
                                    .direct_access_actions_universal_switch_title));
            sb.append("\n");
        }
        if (isExistAccessbilitiyPackage(context, "com.samsung.android.app.accesscontrol")) {
            sb.append("• ");
            sb.append(
                    context.getString(
                            com.android.settings.R.string.accessibility_access_control_title));
            sb.append("\n");
        }
        if (Build.VERSION.SEM_PLATFORM_INT < 100500) {
            sb.append("• ");
            sb.append(context.getString(com.android.settings.R.string.direct_access_title));
            sb.append("\n");
        }
        sb.append("• ");
        sb.append(
                context.getString(
                        com.android.settings.R.string.accessibility_screen_magnification_title));
        sb.append("\n• ");
        sb.append(
                context.getString(
                        com.android.settings.R.string.accessibility_auto_action_preference_title));
        sb.append("\n• ");
        sb.append(
                context.getString(
                        com.android.settings.R.string.accessibility_tap_duration_preference_title));
        sb.append("\n• ");
        sb.append(
                context.getString(
                        com.android.settings.R.string
                                .accessibility_ignore_repeated_touches_preference_title));
        sb.append("\n• ");
        sb.append(context.getString(com.android.settings.R.string.sticky_keys));
        sb.append("\n• ");
        sb.append(context.getString(com.android.settings.R.string.slow_keys));
        sb.append("\n• ");
        sb.append(context.getString(com.android.settings.R.string.bounce_keys));
        return sb.toString();
    }

    public static void saveFunctionKeyActionDB(
            Context context, FunctionKeyAction functionKeyAction) {
        ContentResolver contentResolver = context.getContentResolver();
        int i = functionKeyAction.pressType;
        Settings.Global.putInt(
                contentResolver,
                i != 1
                        ? i != 2
                                ? "function_key_config_shortpress_type"
                                : "function_key_config_doublepress_type"
                        : "function_key_config_longpress_type",
                functionKeyAction.actionType);
        Settings.Global.putString(
                context.getContentResolver(),
                i != 1
                        ? i != 2
                                ? "function_key_config_shortpress_value"
                                : "function_key_config_doublepress_value"
                        : "function_key_config_longpress_value",
                functionKeyAction.actionValue);
        Settings.Global.putString(
                context.getContentResolver(),
                i != 1
                        ? i != 2
                                ? "function_key_config_shortpress_intent"
                                : "function_key_config_doublepress_intent"
                        : "function_key_config_longpress_intent",
                new Gson()
                        .toJson(
                                new IntentData(
                                        functionKeyAction.actionIntentType,
                                        functionKeyAction.actionIntent.toUri(1)),
                                IntentData.class));
    }

    public static void setAppSplitViewPackagesInfo(Context context, String str) {
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONArray("package");
            JSONArray jSONArray2 = new JSONObject(str).getJSONArray("mode");
            ArrayList arrayList = new ArrayList();
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                Log.secD("UsefulfeatureUtils", "PackageManager is null");
                return;
            }
            arrayList.clear();
            arrayList.addAll(getSplitActivityApplications(packageManager));
            if (arrayList.size() == 0) {
                return;
            }
            List supportEmbedActivityPackages =
                    MultiWindowManager.getInstance().getSupportEmbedActivityPackages();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String str2 = ((ApplicationInfo) it.next()).packageName;
                int i = 0;
                while (true) {
                    if (i >= jSONArray.length()) {
                        break;
                    }
                    if (str2.equalsIgnoreCase(jSONArray.get(i).toString())) {
                        int parseInt = Integer.parseInt(jSONArray2.get(i).toString());
                        if (supportEmbedActivityPackages == null
                                || !supportEmbedActivityPackages.contains(str2)) {
                            MultiWindowManager.getInstance()
                                    .setSplitActivityPackageEnabled(
                                            str2, parseInt, UserHandle.getCallingUserId());
                        } else {
                            MultiWindowManager.getInstance()
                                    .setEmbedActivityPackageEnabled(
                                            str2, parseInt == 1, UserHandle.getCallingUserId());
                        }
                    } else {
                        i++;
                    }
                }
            }
        } catch (JSONException e) {
            Log.secE(
                    "UsefulfeatureUtils",
                    "Failed to get AppSplitViewPackages from json object. " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e2) {
            Log.secE(
                    "UsefulfeatureUtils", "Failed to set AppSplitViewPackages. " + e2.getMessage());
            e2.printStackTrace();
        }
    }

    public static boolean setAutoRotatePackagesFlagInfo(
            Context context, SourceInfo sourceInfo, String str) {
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONArray("package");
            JSONArray jSONArray2 = new JSONObject(str).getJSONArray("mode");
            if (context.getPackageManager() == null) {
                Log.secD("UsefulfeatureUtils", "PackageManager is null");
                return false;
            }
            HashMap hashMap = new HashMap();
            for (int i = 0; i < jSONArray.length(); i++) {
                hashMap.put(
                        sourceInfo.getPackageName(jSONArray.get(i).toString()),
                        jSONArray2.get(i).toString());
            }
            Iterator<LauncherActivityInfo> it =
                    ((LauncherApps) context.getSystemService("launcherapps"))
                            .getActivityList(null, new UserHandle(UserHandle.getCallingUserId()))
                            .iterator();
            while (it.hasNext()) {
                String str2 = it.next().getApplicationInfo().packageName;
                if (hashMap.containsKey(str2)) {
                    int parseInt = Integer.parseInt((String) hashMap.get(str2));
                    setPackageOrientation(parseInt, str2);
                    if (str2.equalsIgnoreCase("com.android.samsung.utilityapp")) {
                        setPackageOrientation(parseInt, "com.android.samsung.batteryusage");
                        setPackageOrientation(parseInt, "com.samsung.android.statsd");
                        setPackageOrientation(parseInt, "com.samsung.android.appbooster");
                        setPackageOrientation(parseInt, "com.samsung.android.thermalguardian");
                        setPackageOrientation(parseInt, "com.samsung.android.memoryguardian");
                        setPackageOrientation(parseInt, "com.samsung.android.mediaguardian");
                    }
                }
            }
            return true;
        } catch (JSONException e) {
            Log.secE(
                    "UsefulfeatureUtils",
                    "Failed to get AutoRotatePackages from json object. " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e2) {
            Log.secE("UsefulfeatureUtils", "Failed to set AutoRotatePackages. " + e2.getMessage());
            e2.printStackTrace();
            return false;
        }
    }

    public static void setDarkModeAppsPackagesInfo(
            Context context, SourceInfo sourceInfo, String str) {
        UiModeManager uiModeManager = (UiModeManager) context.getSystemService(UiModeManager.class);
        if (uiModeManager == null) {
            return;
        }
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONArray("package");
            JSONArray jSONArray2 = new JSONObject(str).getJSONArray("mode");
            List nightPriorityAllowedPackagesFromScpm =
                    uiModeManager.getNightPriorityAllowedPackagesFromScpm();
            for (int i = 0; i < jSONArray.length(); i++) {
                String packageName = sourceInfo.getPackageName(jSONArray.get(i).toString());
                if (Utils.hasPackage(context, packageName)
                        && nightPriorityAllowedPackagesFromScpm.contains(packageName)) {
                    uiModeManager.setPackageNightMode(
                            packageName, ((Integer) jSONArray2.get(i)).intValue() > 0 ? 2 : 0);
                }
            }
        } catch (JSONException e) {
            Log.e(
                    "UsefulfeatureUtils",
                    "setDarkModeAppsPackagesInfo : Failed to get DarkModeAppsInfo from json object."
                        + " "
                            + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void setDefaultFunctionKeyDB(Context context, int i) {
        if (i == 2) {
            Settings.Global.putString(
                    context.getContentResolver(),
                    "function_key_config_doublepress_selected_item",
                    FUNC_KEY_DOUBLE_SELECTED_ITEM_DEFAULT);
            Settings.Global.putString(
                    context.getContentResolver(),
                    "function_key_config_doublepress_selected_actions",
                    FUNC_KEY_DOUBLE_SELECTED_ACTIONS_DEFAULT);
        }
    }

    public static void setOneHandModeKeyCustomizationInfo(boolean z) {
        if (!z) {
            try {
                WindowManagerGlobal.getWindowManagerService()
                        .removeKeyCustomizationInfo(
                                VolteConstants.ErrorCode
                                        .CALL_STATUS_CONF_REMOVE_USER_FROM_SESSION_FAILURE,
                                8,
                                3);
                return;
            } catch (RemoteException e) {
                Log.e(
                        "UsefulfeatureUtils",
                        "failed to one hand mode removeKeyCustomizationInfo " + e);
                return;
            }
        }
        try {
            Intent intent = new Intent();
            intent.setComponent(ComponentName.unflattenFromString("onehand/onehand"));
            WindowManagerGlobal.getWindowManagerService()
                    .putKeyCustomizationInfo(
                            new SemWindowManager.KeyCustomizationInfo(
                                    8,
                                    VolteConstants.ErrorCode
                                            .CALL_STATUS_CONF_REMOVE_USER_FROM_SESSION_FAILURE,
                                    3,
                                    0,
                                    intent));
        } catch (RemoteException e2) {
            Log.e("UsefulfeatureUtils", "failed to one hand mode KeyCustomizationInfo " + e2);
        }
    }

    public static void setPackageOrientation(int i, String str) {
        try {
            ActivityTaskManager.getService()
                    .setOrientationControlPolicy(UserHandle.getCallingUserId(), str, i);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void setSideKeyCustomizationInfo(Context context, int i, boolean z) {
        String str;
        FunctionKeyAction functionKeyAction;
        int i2 = i == 1 ? 4 : i == 2 ? 8 : -1;
        if (i2 < 0) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(i2, "Invalid press type : ", "UsefulfeatureUtils");
            return;
        }
        if (!z) {
            try {
                WindowManagerGlobal.getWindowManagerService()
                        .removeKeyCustomizationInfo(
                                VolteConstants.ErrorCode.CALL_STATUS_CONF_START_SESSION_FAILURE,
                                i2,
                                26);
                Log.i(
                        "UsefulfeatureUtils",
                        "setSideKeyCustomizationInfo removeKeyCustomizationInfo");
                return;
            } catch (RemoteException e) {
                Log.e("UsefulfeatureUtils", "failed to removeKeyCustomizationInfo " + e);
                return;
            }
        }
        try {
            ContentResolver contentResolver = context.getContentResolver();
            if (i == 1) {
                str = "function_key_config_longpress_selected_item";
            } else {
                if (i != 2) {
                    throw new IllegalStateException("Unexpected value: " + i);
                }
                str = "function_key_config_doublepress_selected_item";
            }
            final String string = Settings.Global.getString(contentResolver, str);
            if (TextUtils.isEmpty(string)) {
                return;
            }
            List functionKeyItems = FunctionKeyUtils.getFunctionKeyItems(context, i, null);
            FunctionKeyItem functionKeyItem =
                    functionKeyItems != null
                            ? (FunctionKeyItem)
                                    functionKeyItems.stream()
                                            .filter(
                                                    new Predicate() { // from class:
                                                                      // com.samsung.android.settings.usefulfeature.UsefulfeatureUtils$$ExternalSyntheticLambda0
                                                        @Override // java.util.function.Predicate
                                                        public final boolean test(Object obj) {
                                                            return TextUtils.equals(
                                                                    ((FunctionKeyItem) obj).key,
                                                                    string);
                                                        }
                                                    })
                                            .findFirst()
                                            .orElse(null)
                            : null;
            if (functionKeyItem instanceof FunctionKeyApplication) {
                functionKeyAction =
                        FunctionKeyUtils.getFunctionKeyAction(
                                context, (FunctionKeyApplication) functionKeyItem);
            } else {
                if (!(functionKeyItem instanceof FunctionKeyAction)) {
                    Log.d("UsefulfeatureUtils", "selected item is null ");
                    return;
                }
                functionKeyAction = (FunctionKeyAction) functionKeyItem;
            }
            if (functionKeyAction == null) {
                Log.d("UsefulfeatureUtils", "selected action is null ");
            } else {
                saveFunctionKeyActionDB(context, functionKeyAction);
                WindowManagerGlobal.getWindowManagerService()
                        .putKeyCustomizationInfo(
                                new SemWindowManager.KeyCustomizationInfo(
                                        i2,
                                        VolteConstants.ErrorCode
                                                .CALL_STATUS_CONF_START_SESSION_FAILURE,
                                        26,
                                        functionKeyAction.actionIntentType,
                                        functionKeyAction.actionIntent));
            }
        } catch (RemoteException e2) {
            Log.e("UsefulfeatureUtils", "failed to putKeyCustomizationInfo " + e2);
        }
    }

    public static int setSwitchOrientationPolicyFlag(int i, boolean z) {
        if (i == 0) {
            if (z) {
                return 31;
            }
            return i;
        }
        if (i == 7) {
            if (z) {
                return i;
            }
            return 32;
        }
        if (i == 31) {
            if (z) {
                return i;
            }
            return 0;
        }
        if (i != 32) {
            return 0;
        }
        if (z) {
            return 7;
        }
        return i;
    }

    public static void setVideoEnhanceAppInfo(Context context, String str) {
        SemDisplaySolutionManager semDisplaySolutionManager =
                (SemDisplaySolutionManager) context.getSystemService("DisplaySolution");
        String[] stringArray =
                context.getResources().getStringArray(R.array.config_integrityRuleProviderPackages);
        String[] stringArray2 =
                context.getResources().getStringArray(R.array.config_ephemeralResolverPackage);
        HashSet hashSet = new HashSet();
        hashSet.addAll(Arrays.asList(stringArray));
        hashSet.addAll(Arrays.asList(stringArray2));
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONArray("false_names");
            for (int i = 0; i < jSONArray.length(); i++) {
                if (hashSet.contains(jSONArray.get(i).toString())
                        && Utils.hasPackage(context, jSONArray.get(i).toString())) {
                    semDisplaySolutionManager.setVideoEnhancerSettingState(
                            jSONArray.get(i).toString(), 0);
                }
            }
            JSONArray jSONArray2 = new JSONObject(str).getJSONArray("true_names");
            for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                if (hashSet.contains(jSONArray2.get(i2).toString())
                        && Utils.hasPackage(context, jSONArray2.get(i2).toString())
                        && semDisplaySolutionManager.getVideoEnhancerSettingState(
                                        jSONArray2.get(i2).toString())
                                != 1) {
                    semDisplaySolutionManager.setVideoEnhancerSettingState(
                            jSONArray2.get(i2).toString(), 1);
                }
            }
        } catch (JSONException e) {
            Log.secD(
                    "UsefulfeatureUtils",
                    "Failed to get VideoEnhanceAppInfo from json object. " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void turnOffFunctionsConflictWithOneHandedMode(Context context) {
        int i;
        int i2;
        if (SemFloatingFeature.getInstance()
                .getBoolean("SEC_FLOATING_FEATURE_ACCESSIBILITY_SUPPORT_MANAGE_EXCLUSIVE_TASK")) {
            return;
        }
        ((AccessibilityManager) context.getSystemService("accessibility"))
                .semSetScreenReaderEnabled(false);
        TextUtils.SimpleStringSplitter simpleStringSplitter =
                new TextUtils.SimpleStringSplitter(':');
        Set enabledServicesFromSettings =
                AccessibilityUtils.getEnabledServicesFromSettings(context);
        if (enabledServicesFromSettings == Collections.emptySet()) {
            enabledServicesFromSettings = new HashSet();
        }
        enabledServicesFromSettings.remove(
                ComponentName.unflattenFromString(
                        "com.google.android.marvin.talkback/com.googlecode.eyesfree.switchcontrol.SwitchControlService"));
        enabledServicesFromSettings.remove(
                ComponentName.unflattenFromString(
                        "com.google.android.marvin.talkback/com.android.switchaccess.SwitchAccessService"));
        HashSet hashSet = new HashSet();
        Iterator it = enabledServicesFromSettings.iterator();
        while (true) {
            i = 1;
            if (!it.hasNext()) {
                i2 = 0;
                break;
            } else if (hashSet.contains((ComponentName) it.next())) {
                i2 = 1;
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        Iterator it2 = enabledServicesFromSettings.iterator();
        while (it2.hasNext()) {
            sb.append(((ComponentName) it2.next()).flattenToString());
            sb.append(':');
        }
        int length = sb.length();
        if (length > 0) {
            sb.deleteCharAt(length - 1);
        }
        String sb2 = sb.toString();
        Settings.Secure.putString(
                context.getContentResolver(), "enabled_accessibility_services", sb2);
        if (sb2 != null) {
            simpleStringSplitter.setString(sb2);
            while (simpleStringSplitter.hasNext()) {
                if (ComponentName.unflattenFromString(simpleStringSplitter.next()) != null) {
                    break;
                }
            }
        }
        i = i2;
        Settings.Secure.putInt(context.getContentResolver(), "accessibility_enabled", i);
        Settings.System.putInt(context.getContentResolver(), "accessibility_magnifier", 0);
        Settings.Secure.putInt(
                context.getContentResolver(), "accessibility_display_magnification_enabled", 0);
        Settings.Secure.putInt(
                context.getContentResolver(),
                "accessibility_display_magnification_navbar_enabled",
                0);
        Settings.System.putInt(context.getContentResolver(), "finger_magnifier", 0);
        turnOffUniversalSwitch(context.getApplicationContext());
        Settings.System.putInt(context.getContentResolver(), "access_control_use", 0);
        Settings.System.putInt(context.getContentResolver(), "access_control_enabled", 0);
        Settings.System.putInt(context.getContentResolver(), "direct_access", 0);
        Settings.Secure.putInt(context.getContentResolver(), "accessibility_auto_action_type", 0);
        Settings.Secure.putInt(
                context.getContentResolver(), "accessibility_corner_action_enabled", 0);
        Settings.Secure.putInt(context.getContentResolver(), "tap_duration_enabled", 0);
        Settings.Secure.putInt(context.getContentResolver(), "touch_blocking_enabled", 0);
        Settings.Secure.putInt(context.getContentResolver(), "accessibility_sticky_keys", 0);
        Settings.Secure.putInt(context.getContentResolver(), "accessibility_slow_keys", 0);
        Settings.Secure.putInt(context.getContentResolver(), "accessibility_bounce_keys", 0);
    }

    public static void turnOffUniversalSwitch(Context context) {
        if (isUniversalSwitchSupportMultiUserKnoxMode(context)) {
            AccessibilityUtils.setAccessibilityServiceState(
                    context,
                    ComponentName.unflattenFromString(
                            "com.samsung.accessibility/.universalswitch.UniversalSwitchService"),
                    false);
            Intent intent = new Intent("com.samsung.settings.action.universalswitch_toggled");
            intent.setPackage("com.samsung.android.SettingsReceiver");
            context.sendBroadcastAsUser(intent, UserHandle.CURRENT);
            Log.secD(
                    "Utils Accessibility",
                    "sent universal switch toggled broadcast from turnOffUniversalSwitch()");
        }
    }
}
