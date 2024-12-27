package com.android.settingslib.applications;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.usb.IUsbManager;
import android.net.Uri;
import android.os.Environment;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Log;

import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;

import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settingslib.utils.ThreadUtils;

import com.google.common.util.concurrent.MoreExecutors;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.sdhms.SemAppRestrictionManager;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppUtils {
    public static Boolean mSupportDesktopMode;
    public static CharSequence mSearchKeyword = ApnSettings.MVNO_NONE;
    public static final Intent sBrowserIntent =
            new Intent()
                    .setAction("android.intent.action.VIEW")
                    .addCategory("android.intent.category.BROWSABLE")
                    .setData(Uri.parse("http:"));

    public static BitmapDrawable drawableToBitmapDrawable(Context context, Drawable drawable) {
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicWidth > 192) {
            intrinsicWidth = 192;
        }
        if (intrinsicHeight > 192) {
            intrinsicHeight = 192;
        }
        Bitmap createBitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return new BitmapDrawable(context.getResources(), createBitmap);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0090  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.drawable.Drawable getIcon(
            android.content.Context r7,
            com.android.settingslib.applications.ApplicationsState.AppEntry r8) {
        /*
            r0 = 0
            if (r8 == 0) goto Ldc
            android.content.pm.ApplicationInfo r1 = r8.info
            if (r1 != 0) goto L9
            goto Ldc
        L9:
            com.android.settingslib.applications.AppIconCacheManager r1 = com.android.settingslib.applications.AppIconCacheManager.getInstance()
            android.content.pm.ApplicationInfo r2 = r8.info
            java.lang.String r3 = r2.packageName
            int r4 = r2.uid
            boolean r2 = r2.isArchived
            r1.getClass()
            java.lang.String r5 = com.android.settingslib.applications.AppIconCacheManager.getKey(r4, r3, r2)
            if (r5 != 0) goto L26
            java.lang.String r5 = "AppIconCacheManager"
            java.lang.String r6 = "Invalid key with package or uid."
            android.util.Log.w(r5, r6)
            goto L34
        L26:
            com.android.settingslib.applications.AppIconCacheManager$1 r6 = r1.mDrawableCache
            java.lang.Object r5 = r6.get(r5)
            android.graphics.drawable.Drawable r5 = (android.graphics.drawable.Drawable) r5
            if (r5 == 0) goto L34
            android.graphics.drawable.Drawable r0 = r5.mutate()
        L34:
            r5 = 1
            if (r0 != 0) goto La5
            java.io.File r0 = r8.apkFile
            if (r0 == 0) goto L41
            boolean r0 = r0.exists()
            if (r0 != 0) goto L43
        L41:
            if (r2 == 0) goto L99
        L43:
            if (r7 == 0) goto L74
            java.lang.Boolean r0 = com.android.settingslib.applications.AppUtils.mSupportDesktopMode
            if (r0 != 0) goto L59
            com.samsung.android.feature.SemFloatingFeature r0 = com.samsung.android.feature.SemFloatingFeature.getInstance()
            java.lang.String r6 = "SEC_FLOATING_FEATURE_COMMON_SUPPORT_KNOX_DESKTOP"
            boolean r0 = r0.getBoolean(r6)
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            com.android.settingslib.applications.AppUtils.mSupportDesktopMode = r0
        L59:
            java.lang.Boolean r0 = com.android.settingslib.applications.AppUtils.mSupportDesktopMode
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L74
            android.content.res.Resources r0 = r7.getResources()
            android.content.res.Configuration r0 = r0.getConfiguration()
            int r0 = r0.semDesktopModeEnabled
            if (r5 != r0) goto L74
            android.content.pm.ApplicationInfo r8 = r8.info
            com.android.launcher3.icons.FastBitmapDrawable r8 = com.android.settingslib.Utils.getBadgedIcon(r7, r8)
            goto L86
        L74:
            android.content.Context r0 = r7.getApplicationContext()
            android.app.Application r0 = (android.app.Application) r0
            com.android.settingslib.applications.ApplicationsState r0 = com.android.settingslib.applications.ApplicationsState.getInstance(r0)
            android.util.IconDrawableFactory r0 = r0.mDrawableFactory
            android.content.pm.ApplicationInfo r8 = r8.info
            android.graphics.drawable.Drawable r8 = r0.getBadgedIcon(r8)
        L86:
            boolean r0 = r8 instanceof android.graphics.drawable.BitmapDrawable
            if (r0 != 0) goto L90
            android.graphics.drawable.BitmapDrawable r7 = drawableToBitmapDrawable(r7, r8)
        L8e:
            r0 = r7
            goto L95
        L90:
            android.graphics.drawable.BitmapDrawable r7 = optimizeBitmapSize(r7, r8)
            goto L8e
        L95:
            r1.put(r3, r4, r2, r0)
            goto Ldc
        L99:
            r0 = 0
            setAppEntryMounted(r8, r0)
            r8 = 17304561(0x1080bf1, float:2.4987822E-38)
            android.graphics.drawable.Drawable r0 = r7.getDrawable(r8)
            goto Ldc
        La5:
            boolean r6 = r8.mounted
            if (r6 != 0) goto Ldc
            java.io.File r6 = r8.apkFile
            if (r6 == 0) goto Lb3
            boolean r6 = r6.exists()
            if (r6 != 0) goto Lb5
        Lb3:
            if (r2 == 0) goto Ldc
        Lb5:
            setAppEntryMounted(r8, r5)
            android.content.Context r0 = r7.getApplicationContext()
            android.app.Application r0 = (android.app.Application) r0
            com.android.settingslib.applications.ApplicationsState r0 = com.android.settingslib.applications.ApplicationsState.getInstance(r0)
            android.util.IconDrawableFactory r0 = r0.mDrawableFactory
            android.content.pm.ApplicationInfo r8 = r8.info
            android.graphics.drawable.Drawable r8 = r0.getBadgedIcon(r8)
            boolean r0 = r8 instanceof android.graphics.drawable.BitmapDrawable
            if (r0 != 0) goto Ld4
            android.graphics.drawable.BitmapDrawable r7 = drawableToBitmapDrawable(r7, r8)
        Ld2:
            r0 = r7
            goto Ld9
        Ld4:
            android.graphics.drawable.BitmapDrawable r7 = optimizeBitmapSize(r7, r8)
            goto Ld2
        Ld9:
            r1.put(r3, r4, r2, r0)
        Ldc:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.applications.AppUtils.getIcon(android.content.Context,"
                    + " com.android.settingslib.applications.ApplicationsState$AppEntry):android.graphics.drawable.Drawable");
    }

    public static Drawable getIconFromCache(ApplicationsState.AppEntry appEntry) {
        if (appEntry.info == null) {
            return null;
        }
        AppIconCacheManager appIconCacheManager = AppIconCacheManager.getInstance();
        ApplicationInfo applicationInfo = appEntry.info;
        String str = applicationInfo.packageName;
        int i = applicationInfo.uid;
        boolean z = applicationInfo.isArchived;
        appIconCacheManager.getClass();
        String key = AppIconCacheManager.getKey(i, str, z);
        if (key == null) {
            Log.w("AppIconCacheManager", "Invalid key with package or uid.");
            return null;
        }
        Drawable drawable = (Drawable) appIconCacheManager.mDrawableCache.get(key);
        if (drawable != null) {
            return drawable.mutate();
        }
        return null;
    }

    public static Drawable getIconWithoutCache(
            Context context, ApplicationsState.AppEntry appEntry) {
        if (!isAppInstalled(context, appEntry.info.packageName)
                && !isArchived(context, appEntry.info.packageName)) {
            setAppEntryMounted(appEntry, false);
            return context.getDrawable(17304561);
        }
        Drawable badgedIcon =
                ApplicationsState.getInstance((Application) context.getApplicationContext())
                        .mDrawableFactory
                        .getBadgedIcon(appEntry.info);
        if (badgedIcon.getIntrinsicWidth() <= 500) {
            return badgedIcon;
        }
        StringBuilder sb = new StringBuilder("this package icon is big : ");
        sb.append(appEntry.info.packageName);
        sb.append(" (");
        sb.append(badgedIcon.getIntrinsicWidth());
        sb.append(", ");
        sb.append(badgedIcon.getIntrinsicHeight());
        MainClearConfirm$$ExternalSyntheticOutline0.m(sb, ")", "AppUtils");
        return badgedIcon;
    }

    public static boolean hasPreferredActivities(PackageManager packageManager, String str) {
        ArrayList arrayList = new ArrayList();
        packageManager.getPreferredActivities(new ArrayList<>(), arrayList, str);
        Log.d("AppUtils", "Have " + arrayList.size() + " number of activities in preferred list");
        return arrayList.size() > 0;
    }

    public static boolean hasUsbDefaults(IUsbManager iUsbManager, String str) {
        if (iUsbManager == null) {
            return false;
        }
        try {
            return iUsbManager.hasDefaults(str, UserHandle.myUserId());
        } catch (RemoteException e) {
            Log.e("AppUtils", "mUsbManager.hasDefaults", e);
            return false;
        }
    }

    public static boolean isAppInstalled(ApplicationsState.AppEntry appEntry) {
        ApplicationInfo applicationInfo;
        return (appEntry == null
                        || (applicationInfo = appEntry.info) == null
                        || (applicationInfo.flags & 8388608) == 0)
                ? false
                : true;
    }

    public static boolean isArchived(Context context, String str) {
        PackageInfo packageInfo;
        ApplicationInfo applicationInfo;
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return false;
        }
        try {
            packageInfo =
                    packageManager.getPackageInfo(
                            str,
                            PackageManager.PackageInfoFlags.of(
                                    GoodSettingsContract.PreferenceFlag.FLAG_NEED_TYPE));
        } catch (PackageManager.NameNotFoundException e) {
            StringBuilder m =
                    PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                            str, " is not archived : ");
            m.append(e.getMessage());
            Log.d("AppUtils", m.toString());
            packageInfo = null;
        }
        return (packageInfo == null
                        || (applicationInfo = packageInfo.applicationInfo) == null
                        || !applicationInfo.isArchived)
                ? false
                : true;
    }

    public static boolean isAutoDisabled(ApplicationInfo applicationInfo) {
        SemAppRestrictionManager.RestrictionInfo restrictionInfo =
                new SemAppRestrictionManager()
                        .getRestrictionInfo(0, applicationInfo.packageName, applicationInfo.uid);
        return (restrictionInfo == null
                        || restrictionInfo.getState() != 1
                        || restrictionInfo.isChangedByUser())
                ? false
                : true;
    }

    public static boolean isBrowserApp(Context context, int i, String str) {
        Intent intent = sBrowserIntent;
        intent.setPackage(str);
        for (ResolveInfo resolveInfo :
                context.getPackageManager().queryIntentActivitiesAsUser(intent, 131072, i)) {
            if (resolveInfo.activityInfo != null && resolveInfo.handleAllWebDataURI) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDeepSleepingEnable(int i, String str) {
        SemAppRestrictionManager.RestrictionInfo restrictionInfo =
                new SemAppRestrictionManager().getRestrictionInfo(0, str, i);
        return restrictionInfo != null
                && restrictionInfo.getState() == 1
                && (restrictionInfo.isChangedByUser() || !restrictionInfo.isChangedByUser());
    }

    public static boolean isInstant(ApplicationInfo applicationInfo) {
        String[] split;
        if (applicationInfo.isInstantApp()) {
            return true;
        }
        String str = SystemProperties.get("settingsdebug.instant.packages");
        if (str != null
                && !str.isEmpty()
                && applicationInfo.packageName != null
                && (split = str.split(",")) != null) {
            for (String str2 : split) {
                if (applicationInfo.packageName.contains(str2)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x004d, code lost:

       if (r2.equals(r5) != false) goto L54;
    */
    /* JADX WARN: Removed duplicated region for block: B:55:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isLanguagePackApk(
            android.content.Context r9, android.content.pm.ApplicationInfo r10) {
        /*
            android.os.Bundle r0 = r10.metaData
            r1 = 0
            if (r0 == 0) goto Ld6
            java.lang.String r2 = "settings_langpack_invisible"
            boolean r0 = r0.getBoolean(r2, r1)
            if (r0 == 0) goto Ld6
            java.lang.String r0 = r10.packageName
            android.content.pm.PackageManager r2 = r9.getPackageManager()
            android.content.pm.Signature[] r3 = com.android.settingslib.Utils.sSystemSignature
            r4 = 64
            r5 = 0
            r6 = 1
            if (r3 != 0) goto L34
            android.content.pm.Signature[] r3 = new android.content.pm.Signature[r6]
            java.lang.String r7 = "android"
            android.content.pm.PackageInfo r7 = r2.getPackageInfo(r7, r4)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L2f
            if (r7 == 0) goto L2f
            android.content.pm.Signature[] r7 = r7.signatures     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L2f
            if (r7 == 0) goto L2f
            int r8 = r7.length     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L2f
            if (r8 <= 0) goto L2f
            r7 = r7[r1]     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L2f
            goto L30
        L2f:
            r7 = r5
        L30:
            r3[r1] = r7
            com.android.settingslib.Utils.sSystemSignature = r3
        L34:
            android.content.pm.PackageInfo r0 = r2.getPackageInfo(r0, r4)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L51
            android.content.pm.Signature[] r2 = com.android.settingslib.Utils.sSystemSignature     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L51
            r2 = r2[r1]     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L51
            if (r2 == 0) goto L51
            if (r0 == 0) goto L49
            android.content.pm.Signature[] r0 = r0.signatures     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L51
            if (r0 == 0) goto L49
            int r3 = r0.length     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L51
            if (r3 <= 0) goto L49
            r5 = r0[r1]     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L51
        L49:
            boolean r0 = r2.equals(r5)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L51
            if (r0 == 0) goto L51
            goto Ld5
        L51:
            java.lang.String r0 = "isTTSLanguagePack: "
            java.lang.String r2 = "AppUtils"
            android.content.pm.PackageManager r9 = r9.getPackageManager()     // Catch: java.security.NoSuchAlgorithmException -> La1 android.content.pm.PackageManager.NameNotFoundException -> La3
            java.lang.String r10 = r10.packageName     // Catch: java.security.NoSuchAlgorithmException -> La1 android.content.pm.PackageManager.NameNotFoundException -> La3
            android.content.pm.PackageInfo r9 = r9.getPackageInfo(r10, r4)     // Catch: java.security.NoSuchAlgorithmException -> La1 android.content.pm.PackageManager.NameNotFoundException -> La3
            if (r9 == 0) goto Ld2
            android.content.pm.Signature[] r9 = r9.signatures     // Catch: java.security.NoSuchAlgorithmException -> La1 android.content.pm.PackageManager.NameNotFoundException -> La3
            if (r9 == 0) goto Ld2
            int r10 = r9.length     // Catch: java.security.NoSuchAlgorithmException -> La1 android.content.pm.PackageManager.NameNotFoundException -> La3
            if (r10 <= 0) goto Ld2
            r9 = r9[r1]     // Catch: java.security.NoSuchAlgorithmException -> La1 android.content.pm.PackageManager.NameNotFoundException -> La3
            java.lang.String r10 = "SHA-256"
            java.security.MessageDigest r10 = java.security.MessageDigest.getInstance(r10)     // Catch: java.security.NoSuchAlgorithmException -> La1 android.content.pm.PackageManager.NameNotFoundException -> La3
            byte[] r9 = r9.toByteArray()     // Catch: java.security.NoSuchAlgorithmException -> La1 android.content.pm.PackageManager.NameNotFoundException -> La3
            r10.update(r9)     // Catch: java.security.NoSuchAlgorithmException -> La1 android.content.pm.PackageManager.NameNotFoundException -> La3
            byte[] r9 = r10.digest()     // Catch: java.security.NoSuchAlgorithmException -> La1 android.content.pm.PackageManager.NameNotFoundException -> La3
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.security.NoSuchAlgorithmException -> La1 android.content.pm.PackageManager.NameNotFoundException -> La3
            r10.<init>()     // Catch: java.security.NoSuchAlgorithmException -> La1 android.content.pm.PackageManager.NameNotFoundException -> La3
            r3 = r1
        L81:
            int r4 = r9.length     // Catch: java.security.NoSuchAlgorithmException -> La1 android.content.pm.PackageManager.NameNotFoundException -> La3
            if (r3 >= r4) goto La8
            java.lang.String r4 = "%02X"
            r5 = r9[r3]     // Catch: java.security.NoSuchAlgorithmException -> La1 android.content.pm.PackageManager.NameNotFoundException -> La3
            java.lang.Byte r5 = java.lang.Byte.valueOf(r5)     // Catch: java.security.NoSuchAlgorithmException -> La1 android.content.pm.PackageManager.NameNotFoundException -> La3
            java.lang.Object[] r5 = new java.lang.Object[]{r5}     // Catch: java.security.NoSuchAlgorithmException -> La1 android.content.pm.PackageManager.NameNotFoundException -> La3
            java.lang.String r4 = java.lang.String.format(r4, r5)     // Catch: java.security.NoSuchAlgorithmException -> La1 android.content.pm.PackageManager.NameNotFoundException -> La3
            r10.append(r4)     // Catch: java.security.NoSuchAlgorithmException -> La1 android.content.pm.PackageManager.NameNotFoundException -> La3
            int r4 = r9.length     // Catch: java.security.NoSuchAlgorithmException -> La1 android.content.pm.PackageManager.NameNotFoundException -> La3
            int r4 = r4 - r6
            if (r3 == r4) goto La5
            java.lang.String r4 = ":"
            r10.append(r4)     // Catch: java.security.NoSuchAlgorithmException -> La1 android.content.pm.PackageManager.NameNotFoundException -> La3
            goto La5
        La1:
            r9 = move-exception
            goto Lb3
        La3:
            r9 = move-exception
            goto Lc3
        La5:
            int r3 = r3 + 1
            goto L81
        La8:
            java.lang.String r9 = "06:7B:9C:A8:E1:22:92:71:E9:04:C5:97:6F:EA:8C:B6:1B:6D:C9:C6:20:E3:B3:E4:C8:E4:88:0D:C5:91:E0:E5"
            java.lang.String r10 = r10.toString()     // Catch: java.security.NoSuchAlgorithmException -> La1 android.content.pm.PackageManager.NameNotFoundException -> La3
            boolean r9 = r9.equals(r10)     // Catch: java.security.NoSuchAlgorithmException -> La1 android.content.pm.PackageManager.NameNotFoundException -> La3
            goto Ld3
        Lb3:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>(r0)
            r10.append(r9)
            java.lang.String r9 = r10.toString()
            android.util.Log.e(r2, r9)
            goto Ld2
        Lc3:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>(r0)
            r10.append(r9)
            java.lang.String r9 = r10.toString()
            android.util.Log.e(r2, r9)
        Ld2:
            r9 = r1
        Ld3:
            if (r9 == 0) goto Ld6
        Ld5:
            r1 = r6
        Ld6:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.applications.AppUtils.isLanguagePackApk(android.content.Context,"
                    + " android.content.pm.ApplicationInfo):boolean");
    }

    public static boolean isMainlineModule(PackageManager packageManager, String str) {
        try {
            try {
                packageManager.getModuleInfo(str, 0);
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
                PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
                return Flags.provideInfoOfApkInApex()
                        ? packageInfo.getApexPackageName() != null
                        : packageInfo.applicationInfo.sourceDir.startsWith(
                                Environment.getApexDirectory().getAbsolutePath());
            }
        } catch (PackageManager.NameNotFoundException unused2) {
            return false;
        }
    }

    public static boolean isManualDisabled(ApplicationInfo applicationInfo) {
        SemAppRestrictionManager.RestrictionInfo restrictionInfo =
                new SemAppRestrictionManager()
                        .getRestrictionInfo(0, applicationInfo.packageName, applicationInfo.uid);
        return restrictionInfo != null
                && restrictionInfo.getState() == 1
                && restrictionInfo.isChangedByUser();
    }

    public static boolean isSystemUidApp(int i) {
        Log.d("AppUtils", "isSystemUidApp = uid ( " + i + ")");
        if (i % 100000 == 1000) {
            Log.d("AppUtils", "this app is system uid app");
            return true;
        }
        Log.d("AppUtils", "this app is not system uid app");
        return false;
    }

    public static BitmapDrawable optimizeBitmapSize(Context context, Drawable drawable) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        if (bitmap.getWidth() <= 192) {
            return bitmapDrawable;
        }
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, 192, 192, true);
        Canvas canvas = new Canvas(createScaledBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return new BitmapDrawable(context.getResources(), createScaledBitmap);
    }

    public static void preloadTopIcons(final Context context, ArrayList arrayList, int i) {
        if (arrayList == null || arrayList.isEmpty() || i <= 0) {
            return;
        }
        for (int i2 = 0; i2 < Math.min(arrayList.size(), i); i2++) {
            final ApplicationsState.AppEntry appEntry =
                    (ApplicationsState.AppEntry) arrayList.get(i2);
            ((MoreExecutors.ListeningDecorator) ThreadUtils.getBackgroundExecutor())
                    .submit(
                            new Runnable() { // from class:
                                             // com.android.settingslib.applications.AppUtils$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    AppUtils.getIcon(context, appEntry);
                                }
                            });
        }
    }

    public static void setAppEntryMounted(ApplicationsState.AppEntry appEntry, boolean z) {
        if (appEntry.mounted != z) {
            synchronized (appEntry) {
                appEntry.mounted = z;
            }
        }
    }

    public static boolean isAppInstalled(Context context, String str) {
        PackageInfo packageInfo;
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return false;
        }
        try {
            packageInfo = packageManager.getPackageInfo(str, 131072);
        } catch (PackageManager.NameNotFoundException e) {
            StringBuilder m =
                    PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                            str, " is not installed : ");
            m.append(e.getMessage());
            Log.d("AppUtils", m.toString());
            packageInfo = null;
        }
        return packageInfo != null;
    }

    public static boolean isArchived(Context context, int i, String str) {
        PackageInfo packageInfo;
        ApplicationInfo applicationInfo;
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return false;
        }
        try {
            packageInfo =
                    packageManager.getPackageInfoAsUser(
                            str,
                            PackageManager.PackageInfoFlags.of(
                                    GoodSettingsContract.PreferenceFlag.FLAG_NEED_TYPE),
                            i);
        } catch (PackageManager.NameNotFoundException e) {
            StringBuilder m =
                    PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                            str, " is not archived : ");
            m.append(e.getMessage());
            Log.d("AppUtils", m.toString());
            packageInfo = null;
        }
        return (packageInfo == null
                        || (applicationInfo = packageInfo.applicationInfo) == null
                        || !applicationInfo.isArchived)
                ? false
                : true;
    }
}
