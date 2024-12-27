package com.samsung.android.settings.eternal.provider.items;

import android.app.ActivityTaskManager;
import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.graphics.Typeface;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.lib.episode.Scene;
import com.samsung.android.lib.episode.SceneResult;
import com.samsung.android.lib.episode.SourceInfo;
import com.samsung.android.util.SemLog;
import com.samsung.android.view.SemWindowManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DisplayItem implements Recoverable {
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0055, code lost:

       if (r1 <= 0) goto L19;
    */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x002b, code lost:

       if (r1 <= 0) goto L19;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void changeFontSize(android.content.Context r10, int r11, int r12) {
        /*
            android.content.res.Resources r0 = r10.getResources()
            r1 = 2130903403(0x7f03016b, float:1.7413623E38)
            java.lang.String[] r0 = r0.getStringArray(r1)
            if (r0 == 0) goto L70
            int r1 = r0.length
            if (r1 != 0) goto L12
            goto L70
        L12:
            int r1 = r0.length
            r2 = 1
            if (r11 < r1) goto L19
            int r1 = r0.length
            int r1 = r1 - r2
            goto L1a
        L19:
            r1 = r11
        L1a:
            r3 = 11
            r4 = 0
            r5 = 7
            r6 = 8
            if (r12 == r3) goto L24
            if (r12 != r6) goto L2f
        L24:
            int r7 = r0.length
            if (r7 != r6) goto L2f
            if (r1 < r5) goto L2b
            r2 = r5
            goto L59
        L2b:
            if (r1 > 0) goto L58
        L2d:
            r2 = r4
            goto L59
        L2f:
            r7 = 2
            r8 = 3
            if (r12 != r8) goto L41
            int r9 = r0.length
            if (r9 != r6) goto L41
            if (r1 != 0) goto L39
            goto L59
        L39:
            if (r1 != r2) goto L3d
            r2 = r8
            goto L59
        L3d:
            if (r1 < r7) goto L58
            r2 = 5
            goto L59
        L41:
            if (r12 == r3) goto L45
            if (r12 != r6) goto L55
        L45:
            int r12 = r0.length
            if (r12 != r8) goto L55
            r12 = 4
            if (r1 >= r12) goto L4c
            goto L2d
        L4c:
            if (r1 < r12) goto L51
            if (r1 >= r5) goto L51
            goto L59
        L51:
            if (r1 < r5) goto L58
            r2 = r7
            goto L59
        L55:
            if (r1 > 0) goto L58
            goto L2d
        L58:
            r2 = r1
        L59:
            float r12 = com.samsung.android.settings.display.SecDisplayUtils.getFontScale(r10, r2)
            android.content.ContentResolver r0 = r10.getContentResolver()
            java.lang.String r1 = "font_size"
            android.provider.Settings.Global.putInt(r0, r1, r11)
            android.content.Intent r11 = com.samsung.android.settings.display.SecDisplayUtils.getFontSizeChangedIntent()
            r10.sendBroadcast(r11)
            com.samsung.android.settings.display.SecDisplayUtils.writeFontScaleDBAllUser(r10, r12)
        L70:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.provider.items.DisplayItem.changeFontSize(android.content.Context,"
                    + " int, int):void");
    }

    public static String getFontFileName(Context context) {
        String[] split = Typeface.semGetFontPathOfCurrentFontStyle(context, 1).split("/");
        String str = (split == null || split.length - 1 <= 0) ? "default" : split[split.length - 1];
        return !str.equals("default") ? str.concat(".xml") : str;
    }

    public static boolean isSameDeviceType(String str) {
        String str2 = SystemProperties.get("ro.build.characteristics");
        if ((str2 != null && str2.contains("tablet") && str.equals("tablet"))
                || str.equals("phone")
                || str.equals("nosdcard")) {
            return true;
        }
        SemLog.d(
                "Eternal/DisplayItem",
                "Device type is not same! backupDeviceType = "
                        + str
                        + " characteristics = "
                        + str2);
        return false;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final boolean followRestoreSkipPolicy(Scene scene) {
        return ("/Settings/Display/NavigationTypes".equals(scene.mSceneKey)
                        || "/Settings/Display/ShowRecentNotificationOnly".equals(scene.mSceneKey))
                ? false
                : true;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final List getTestScenes(Context context, String str) {
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x037f, code lost:

       if (r0[0].endsWith("default") != false) goto L232;
    */
    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.lib.episode.Scene.Builder getValue(
            android.content.Context r20,
            com.samsung.android.lib.episode.SourceInfo r21,
            java.lang.String r22) {
        /*
            Method dump skipped, instructions count: 2484
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.provider.items.DisplayItem.getValue(android.content.Context,"
                    + " com.samsung.android.lib.episode.SourceInfo,"
                    + " java.lang.String):com.samsung.android.lib.episode.Scene$Builder");
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final SceneResult isOpenable(Context context, String str) {
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:468:0x0c57, code lost:

       if (r6 != 3) goto L616;
    */
    /* JADX WARN: Code restructure failed: missing block: B:469:0x0cb6, code lost:

       r13 = r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:533:0x0c66, code lost:

       if (r6 != 2) goto L616;
    */
    /* JADX WARN: Code restructure failed: missing block: B:541:0x0c74, code lost:

       if (r6 != 2) goto L616;
    */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.lib.episode.SceneResult setValue(
            android.content.Context r29,
            java.lang.String r30,
            com.samsung.android.lib.episode.Scene r31,
            com.samsung.android.lib.episode.SourceInfo r32) {
        /*
            Method dump skipped, instructions count: 4196
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.provider.items.DisplayItem.setValue(android.content.Context,"
                    + " java.lang.String, com.samsung.android.lib.episode.Scene,"
                    + " com.samsung.android.lib.episode.SourceInfo):com.samsung.android.lib.episode.SceneResult");
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CameraCutoutBackupAndRestore {
        public static final List EXCLUSION_PACKAGE_LIST =
                List.of(
                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                        "com.sec.android.app.SecSetupWizard",
                        "com.sec.android.easyMover");
        public final /* synthetic */ int $r8$classId;
        public final Context mContext;
        public final ConcurrentHashMap mInfoByPackage;
        public final SourceInfo mSourceInfo;
        public final String mToAgentPrefix;
        public final String mToDevicePrefix;
        public final int mUserId;

        public CameraCutoutBackupAndRestore(Context context, SourceInfo sourceInfo, String str) {
            this.mToAgentPrefix = str.concat("#ToAgent: ");
            this.mToDevicePrefix = str.concat("#ToDevice: ");
            this.mContext = context;
            this.mSourceInfo = sourceInfo;
            this.mUserId = context.getUserId();
            this.mInfoByPackage = new ConcurrentHashMap();
        }

        public final boolean isAvailable() {
            switch (this.$r8$classId) {
                case 0:
                    return (SemWindowManager.getInstance().getFullScreenAppsSupportMode() & 2) != 0;
                default:
                    return (SemWindowManager.getInstance().getFullScreenAppsSupportMode() & 1) != 0;
            }
        }

        public final boolean isValid(int i) {
            switch (this.$r8$classId) {
                case 0:
                    if (i == 0 || i == 1 || i == 2) {}
                    break;
                default:
                    if (i == 0 || i == 1) {}
                    break;
            }
            return true;
        }

        public final void putToAgentFromDevice(Scene.Builder builder) {
            int cutoutPolicy;
            String str = this.mToAgentPrefix;
            SourceInfo sourceInfo = this.mSourceInfo;
            if (sourceInfo == null) {
                SemLog.w("PackageFeatureBackupAndRestore", str + "SourceInfo is null.");
                return;
            }
            if (!isAvailable()) {
                SemLog.d("PackageFeatureBackupAndRestore", str + "Unavailable.");
                return;
            }
            SemLog.d("PackageFeatureBackupAndRestore", str + "Started.");
            try {
                JSONArray jSONArray = new JSONArray();
                JSONArray jSONArray2 = new JSONArray();
                for (LauncherActivityInfo launcherActivityInfo :
                        ((LauncherApps) this.mContext.getSystemService(LauncherApps.class))
                                .getActivityList(null, new UserHandle(this.mUserId))) {
                    try {
                        String str2 = launcherActivityInfo.getApplicationInfo().packageName;
                        this.mInfoByPackage.put(str2, launcherActivityInfo);
                        String packageIndex = sourceInfo.getPackageIndex(str2);
                        if (!EXCLUSION_PACKAGE_LIST.contains(str2)) {
                            switch (this.$r8$classId) {
                                case 0:
                                    cutoutPolicy =
                                            ActivityTaskManager.getService()
                                                    .getCutoutPolicy(this.mUserId, str2);
                                    break;
                                default:
                                    LauncherActivityInfo launcherActivityInfo2 =
                                            (LauncherActivityInfo) this.mInfoByPackage.get(str2);
                                    if (launcherActivityInfo2 == null) {
                                        cutoutPolicy = 0;
                                        break;
                                    } else {
                                        cutoutPolicy =
                                                WindowManagerGlobal.getWindowManagerService()
                                                        .getMaxAspectRatioPolicy(
                                                                str2,
                                                                launcherActivityInfo2
                                                                        .getApplicationInfo()
                                                                        .uid);
                                        break;
                                    }
                            }
                            if (isValid(cutoutPolicy)) {
                                jSONArray.put(packageIndex);
                                jSONArray2.put(cutoutPolicy);
                            }
                        }
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("index", jSONArray);
                jSONObject.put("value", jSONArray2);
                if (jSONObject.length() > 0) {
                    String jSONObject2 = jSONObject.toString();
                    SemLog.d(
                            "PackageFeatureBackupAndRestore",
                            str + "data=" + Integer.valueOf(jSONArray.length()));
                    builder.setValue(jSONObject2, true);
                    builder.setDefault(false);
                    return;
                }
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
            SemLog.w("PackageFeatureBackupAndRestore", str + "Failed.");
        }

        public final void putToDeviceFromAgent(Scene scene) {
            String str = this.mToDevicePrefix;
            SourceInfo sourceInfo = this.mSourceInfo;
            if (sourceInfo == null) {
                SemLog.w("PackageFeatureBackupAndRestore", str + "SourceInfo is null.");
                return;
            }
            if (!isAvailable()) {
                SemLog.d("PackageFeatureBackupAndRestore", str + "Unavailable.");
                return;
            }
            SemLog.d("PackageFeatureBackupAndRestore", str + "Started.");
            try {
                String value = scene.getValue(null, true);
                if (value == null) {
                    return;
                }
                for (LauncherActivityInfo launcherActivityInfo :
                        ((LauncherApps) this.mContext.getSystemService(LauncherApps.class))
                                .getActivityList(null, new UserHandle(this.mUserId))) {
                    this.mInfoByPackage.put(
                            launcherActivityInfo.getApplicationInfo().packageName,
                            launcherActivityInfo);
                }
                JSONObject jSONObject = new JSONObject(value);
                JSONArray jSONArray = jSONObject.getJSONArray("index");
                JSONArray jSONArray2 = jSONObject.getJSONArray("value");
                int length = jSONArray.length();
                SemLog.d("PackageFeatureBackupAndRestore", str + "data=" + Integer.valueOf(length));
                for (int i = 0; i < length; i++) {
                    try {
                        String packageName = sourceInfo.getPackageName(jSONArray.get(i).toString());
                        if (((LauncherActivityInfo) this.mInfoByPackage.get(packageName)) != null
                                && !EXCLUSION_PACKAGE_LIST.contains(packageName)) {
                            int parseInt = Integer.parseInt(jSONArray2.get(i).toString());
                            if (isValid(parseInt)) {
                                switch (this.$r8$classId) {
                                    case 0:
                                        ActivityTaskManager.getService()
                                                .setCutoutPolicy(
                                                        this.mUserId, packageName, parseInt);
                                        break;
                                    default:
                                        LauncherActivityInfo launcherActivityInfo2 =
                                                (LauncherActivityInfo)
                                                        this.mInfoByPackage.get(packageName);
                                        if (launcherActivityInfo2 == null) {
                                            break;
                                        } else {
                                            IWindowManager windowManagerService =
                                                    WindowManagerGlobal.getWindowManagerService();
                                            int i2 = launcherActivityInfo2.getApplicationInfo().uid;
                                            boolean z = true;
                                            if (parseInt != 1) {
                                                z = false;
                                            }
                                            windowManagerService.setMaxAspectRatioPolicy(
                                                    packageName, i2, z, -1);
                                            break;
                                        }
                                }
                            }
                        }
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            } catch (Throwable th2) {
                th2.printStackTrace();
                SemLog.w("PackageFeatureBackupAndRestore", str + "Failed.");
            }
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public CameraCutoutBackupAndRestore(Context context, SourceInfo sourceInfo, int i) {
            this(context, sourceInfo, "CameraCutout");
            this.$r8$classId = i;
            switch (i) {
                case 1:
                    this(context, sourceInfo, "FullScreenApps");
                    break;
                default:
                    break;
            }
        }
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final void open(Context context, String str) {}
}
