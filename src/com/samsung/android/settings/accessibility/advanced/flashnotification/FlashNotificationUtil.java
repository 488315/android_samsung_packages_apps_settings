package com.samsung.android.settings.accessibility.advanced.flashnotification;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.provider.Settings;
import android.util.Log;

import androidx.compose.animation.ChangeSize$$ExternalSyntheticOutline0;
import androidx.core.graphics.ColorUtils;
import androidx.picker.model.AppData;
import androidx.picker.model.AppInfo;

import com.android.settingslib.utils.ColorUtil;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class FlashNotificationUtil {
    public static final Map ALIAS_MAP;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface InstalledApplicationsListener {
        void onGetInstalledPackageNameUnmodifiableSet(Set set);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ScreenFlashInfo {
        public boolean checked;
        public int color;
        public final String packageName;

        public ScreenFlashInfo(String str, int i, boolean z) {
            this.packageName = str;
            this.color = i;
            this.checked = z;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ScreenFlashInfo{packageName='");
            sb.append(this.packageName);
            sb.append("', color= #");
            sb.append(String.format("%08X", Integer.valueOf(this.color)));
            sb.append(", checked=");
            return ChangeSize$$ExternalSyntheticOutline0.m(sb, this.checked, '}');
        }
    }

    static {
        HashMap hashMap = new HashMap();
        ALIAS_MAP = hashMap;
        hashMap.put("com.samsung.android.dialer", new String[] {"com.android.server.telecom"});
        hashMap.put("com.samsung.android.incallui", new String[] {"com.android.server.telecom"});
    }

    public static List getCameraFlashNotiInstalledAppDataList(Context context, Set set) {
        set.clear();
        List<ApplicationInfo> unmodifiableList =
                Collections.unmodifiableList(getInstalledAppList(context));
        ArrayList arrayList = new ArrayList();
        PackageManager packageManager = context.getPackageManager();
        String string =
                Settings.Secure.getString(
                        context.getContentResolver(), "camera_flash_notification_app_list");
        boolean z = string == null || "all".equals(string);
        HashSet hashSet = new HashSet();
        if (string != null) {
            hashSet.addAll(Arrays.asList(string.split(ConstFlashNoti.APP_LIST_SEPARATOR_STRING)));
        }
        for (ApplicationInfo applicationInfo : unmodifiableList) {
            set.add(applicationInfo.packageName);
            String str = applicationInfo.packageName;
            int i = applicationInfo.uid;
            AppInfo.Companion companion = AppInfo.Companion;
            arrayList.add(
                    new AppData.ListSwitchAppDataBuilder(
                                    AppInfo.Companion.obtain(i, str, ApnSettings.MVNO_NONE))
                            .setIcon(applicationInfo.loadIcon(packageManager))
                            .setLabel(applicationInfo.loadLabel(packageManager).toString())
                            .setSelected(z || hashSet.contains(applicationInfo.packageName))
                            .build());
        }
        return arrayList;
    }

    public static ColorStateList getColorChipStateList(Context context, int i) {
        return new ColorStateList(
                new int[][] {new int[] {-16842910}, new int[0]},
                new int[] {
                    ColorUtils.setAlphaComponent(
                            i, (int) (ColorUtil.getDisabledAlpha(context) * 255.0f)),
                    ColorUtils.setAlphaComponent(i, 255)
                });
    }

    public static List getInstalledAppList(Context context) {
        ArrayList arrayList = new ArrayList();
        for (ApplicationInfo applicationInfo :
                context.getPackageManager()
                        .getInstalledApplications(PackageManager.ApplicationInfoFlags.of(128L))) {
            if (context.getPackageManager().getLaunchIntentForPackage(applicationInfo.packageName)
                    != null) {
                arrayList.add(applicationInfo);
            }
        }
        return arrayList;
    }

    public static Set getInstalledPackageNameUnmodifiableSet(Context context) {
        HashSet hashSet = new HashSet();
        Iterator it = ((ArrayList) getInstalledAppList(context)).iterator();
        while (it.hasNext()) {
            hashSet.add(((ApplicationInfo) it.next()).packageName);
        }
        return Collections.unmodifiableSet(hashSet);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(4:9|(2:11|(2:27|22)(1:13))(1:28)|14|15) */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0067, code lost:

       android.util.Log.w("FlashNotificationUtil", "Color parsing failed.");
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List getScreenFlashColorList(android.content.Context r13) {
        /*
            android.content.ContentResolver r13 = r13.getContentResolver()
            java.lang.String r0 = "screen_flash_notification_color_apps"
            java.lang.String r13 = android.provider.Settings.Secure.getString(r13, r0)
            if (r13 == 0) goto Lb4
            java.lang.String r0 = ""
            boolean r1 = r13.equals(r0)
            if (r1 == 0) goto L17
            goto Lb4
        L17:
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r2 = 59
            java.lang.String r2 = java.lang.Character.toString(r2)
            java.lang.String[] r13 = r13.split(r2)
            int r2 = r13.length
            r3 = 0
            r4 = r3
        L29:
            if (r4 >= r2) goto Lb3
            r5 = r13[r4]
            r6 = 35
            java.lang.String r7 = java.lang.Character.toString(r6)
            java.lang.String[] r5 = r5.split(r7)
            int r7 = com.android.settings.accessibility.FlashNotificationsUtil.DEFAULT_SCREEN_FLASH_COLOR
            int r8 = r5.length
            java.lang.String r9 = "FlashNotificationUtil"
            r10 = 1
            if (r8 == r10) goto L6d
            r11 = 2
            if (r8 == r11) goto L50
            r12 = 3
            if (r8 == r12) goto L46
            goto Laf
        L46:
            java.lang.String r8 = "0"
            r11 = r5[r11]
            boolean r8 = r8.equals(r11)
            r8 = r8 ^ r10
            goto L51
        L50:
            r8 = r3
        L51:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch: java.lang.IllegalArgumentException -> L67
            r11.<init>()     // Catch: java.lang.IllegalArgumentException -> L67
            r11.append(r6)     // Catch: java.lang.IllegalArgumentException -> L67
            r6 = r5[r10]     // Catch: java.lang.IllegalArgumentException -> L67
            r11.append(r6)     // Catch: java.lang.IllegalArgumentException -> L67
            java.lang.String r6 = r11.toString()     // Catch: java.lang.IllegalArgumentException -> L67
            int r7 = android.graphics.Color.parseColor(r6)     // Catch: java.lang.IllegalArgumentException -> L67
            goto L6e
        L67:
            java.lang.String r6 = "Color parsing failed."
            android.util.Log.w(r9, r6)
            goto L6e
        L6d:
            r8 = r3
        L6e:
            r6 = r5[r3]
            if (r6 == 0) goto Laf
            boolean r6 = r6.equals(r0)
            if (r6 != 0) goto Laf
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r10 = r5[r3]
            r6.append(r10)
            java.lang.String r10 = "#"
            r6.append(r10)
            java.lang.Integer r11 = java.lang.Integer.valueOf(r7)
            java.lang.Object[] r11 = new java.lang.Object[]{r11}
            java.lang.String r12 = "%08X"
            java.lang.String r11 = java.lang.String.format(r12, r11)
            r6.append(r11)
            r6.append(r10)
            r6.append(r8)
            java.lang.String r6 = r6.toString()
            android.util.Log.d(r9, r6)
            com.samsung.android.settings.accessibility.advanced.flashnotification.FlashNotificationUtil$ScreenFlashInfo r6 = new com.samsung.android.settings.accessibility.advanced.flashnotification.FlashNotificationUtil$ScreenFlashInfo
            r5 = r5[r3]
            r6.<init>(r5, r7, r8)
            r1.add(r6)
        Laf:
            int r4 = r4 + 1
            goto L29
        Lb3:
            return r1
        Lb4:
            java.util.List r13 = java.util.List.of()
            return r13
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.accessibility.advanced.flashnotification.FlashNotificationUtil.getScreenFlashColorList(android.content.Context):java.util.List");
    }

    public static boolean isOffAll(Context context, Set set) {
        String string =
                Settings.Secure.getString(
                        context.getContentResolver(), "camera_flash_notification_app_list");
        if (string == null || string.equals("all")) {
            return set.size() == 0;
        }
        if (string.isEmpty()) {
            return true;
        }
        HashSet hashSet =
                new HashSet(Arrays.asList(string.split(ConstFlashNoti.APP_LIST_SEPARATOR_STRING)));
        hashSet.retainAll(set);
        return hashSet.size() == 0;
    }

    public static boolean putAppPreference(Context context, String str, boolean z, Set set) {
        if (z) {
            String string =
                    Settings.Secure.getString(
                            context.getContentResolver(), "camera_flash_notification_app_list");
            if (string == null || string.equals("all")) {
                return true;
            }
            String str2 = str + ';';
            if (!string.contains(str2)) {
                string = string.concat(str2);
            }
            return Settings.Secure.putString(
                    context.getContentResolver(),
                    "camera_flash_notification_app_list",
                    new HashSet(
                                            Arrays.asList(
                                                    string.split(
                                                            ConstFlashNoti
                                                                    .APP_LIST_SEPARATOR_STRING)))
                                    .containsAll(set)
                            ? "all"
                            : string);
        }
        String string2 =
                Settings.Secure.getString(
                        context.getContentResolver(), "camera_flash_notification_app_list");
        String str3 = str + ';';
        String str4 = ApnSettings.MVNO_NONE;
        if (string2 != null && !string2.equals("all")) {
            String replace = string2.replace(str3, ApnSettings.MVNO_NONE);
            return !replace.isEmpty()
                    ? Settings.Secure.putString(
                            context.getContentResolver(),
                            "camera_flash_notification_app_list",
                            replace)
                    : Settings.Secure.putString(
                            context.getContentResolver(),
                            "camera_flash_notification_app_list",
                            ApnSettings.MVNO_NONE);
        }
        HashSet hashSet = (HashSet) set;
        if (!hashSet.contains(str)) {
            Log.w("FlashNotificationUtil", "packageName is invalid.");
            return false;
        }
        HashSet hashSet2 = new HashSet(hashSet);
        hashSet2.remove(str);
        Iterator it = hashSet2.iterator();
        while (it.hasNext()) {
            String str5 = (String) it.next();
            String concat = str4.concat(str5 + ';');
            String[] strArr = (String[]) ((HashMap) ALIAS_MAP).get(str5);
            if (strArr != null) {
                for (String str6 : strArr) {
                    if (str6 != null) {
                        concat = concat.concat(str6.concat(";"));
                    }
                }
            }
            str4 = concat;
        }
        return Settings.Secure.putString(
                context.getContentResolver(), "camera_flash_notification_app_list", str4);
    }

    public static void putAppPreferenceWithAlias(Context context, String str, boolean z, Set set) {
        if (str.equals("com.samsung.android.dialer")
                || str.equals("com.samsung.android.incallui")) {
            if (z) {
                putAppPreference(context, "com.android.server.telecom", true, set);
            } else {
                String string =
                        Settings.Secure.getString(
                                context.getContentResolver(), "camera_flash_notification_app_list");
                if (string != null
                        && ((string.contains("com.samsung.android.dialer")
                                        && !string.contains("com.samsung.android.incallui"))
                                || (!string.contains("com.samsung.android.dialer")
                                        && string.contains("com.samsung.android.incallui")))) {
                    putAppPreference(context, "com.android.server.telecom", false, set);
                }
            }
        }
        putAppPreference(context, str, z, set);
    }
}
