package com.samsung.android.settings.knox;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.app.usage.NetworkStats;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;

import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;
import com.android.settingslib.AppItem;
import com.android.settingslib.applications.RecentAppOpsAccess;

import libcore.util.HexEncoding;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.appconfig.ApplicationRestrictionsManager;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.custom.ProKioskManager;
import com.samsung.android.knox.custom.SettingsManager;
import com.samsung.android.knox.custom.SystemManager;
import com.samsung.android.knox.dar.StreamCipher;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.devicesecurity.PasswordPolicy;
import com.samsung.android.knox.knoxanalyticsproxy.KnoxAnalytics;
import com.samsung.android.knox.knoxanalyticsproxy.KnoxAnalyticsData;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.Trace;
import com.samsung.android.settings.lockscreen.LockUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class KnoxUtils {
    public static final String mDeviceType = SemSystemProperties.get("ro.build.characteristics");
    public static boolean appRestrictionState = false;
    public static long sfTotalBytes = 0;
    public static final int[] lockscreenAttrs = {R.attr.lockscreen_dialog};
    public static boolean mHasChooseLockResult = false;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class InitLockState {
        public static final /* synthetic */ InitLockState[] $VALUES;
        public static final InitLockState NONE;
        public static final InitLockState SA_CHANGED_LOCK;
        public static final InitLockState SA_REMOVED;
        public static final InitLockState VERIFICATION_FAILED;
        public static final InitLockState VERIFIED;

        static {
            InitLockState initLockState = new InitLockState("NONE", 0);
            NONE = initLockState;
            InitLockState initLockState2 = new InitLockState("VERIFIED", 1);
            VERIFIED = initLockState2;
            InitLockState initLockState3 = new InitLockState("SA_CHANGED_LOCK", 2);
            SA_CHANGED_LOCK = initLockState3;
            InitLockState initLockState4 = new InitLockState("SA_REMOVED", 3);
            SA_REMOVED = initLockState4;
            InitLockState initLockState5 = new InitLockState("VERIFICATION_FAILED", 4);
            VERIFICATION_FAILED = initLockState5;
            $VALUES =
                    new InitLockState[] {
                        initLockState,
                        initLockState2,
                        initLockState3,
                        initLockState4,
                        initLockState5
                    };
        }

        public static InitLockState valueOf(String str) {
            return (InitLockState) Enum.valueOf(InitLockState.class, str);
        }

        public static InitLockState[] values() {
            return (InitLockState[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class KnoxPasswordChangePolicy {
        public static final /* synthetic */ KnoxPasswordChangePolicy[] $VALUES;
        public static final KnoxPasswordChangePolicy ENFORCE_POLICY;
        public static final KnoxPasswordChangePolicy RESET_POLICY;

        static {
            KnoxPasswordChangePolicy knoxPasswordChangePolicy =
                    new KnoxPasswordChangePolicy("ENFORCE_POLICY", 0);
            ENFORCE_POLICY = knoxPasswordChangePolicy;
            KnoxPasswordChangePolicy knoxPasswordChangePolicy2 =
                    new KnoxPasswordChangePolicy("RESET_POLICY", 1);
            RESET_POLICY = knoxPasswordChangePolicy2;
            $VALUES =
                    new KnoxPasswordChangePolicy[] {
                        knoxPasswordChangePolicy, knoxPasswordChangePolicy2
                    };
        }

        public static KnoxPasswordChangePolicy valueOf(String str) {
            return (KnoxPasswordChangePolicy) Enum.valueOf(KnoxPasswordChangePolicy.class, str);
        }

        public static KnoxPasswordChangePolicy[] values() {
            return (KnoxPasswordChangePolicy[]) $VALUES.clone();
        }
    }

    public static long accumulate(
            int i,
            SparseArray sparseArray,
            NetworkStats.Bucket bucket,
            ArrayList arrayList,
            long j) {
        int uid = bucket.getUid();
        AppItem appItem = (AppItem) sparseArray.get(i);
        if (appItem == null) {
            appItem = new AppItem(i);
            appItem.category = 2;
            arrayList.add(appItem);
            sparseArray.put(appItem.key, appItem);
        }
        appItem.addUid(uid);
        appItem.total = bucket.getTxBytes() + bucket.getRxBytes() + appItem.total;
        if (SemPersonaManager.isSecureFolderId(UserHandle.myUserId())
                && UserHandle.getUserId(uid) == UserHandle.myUserId()) {
            sfTotalBytes = bucket.getTxBytes() + bucket.getRxBytes() + sfTotalBytes;
        }
        return Math.max(j, appItem.total);
    }

    public static synchronized long bindStats(Context context, NetworkStats networkStats) {
        int i;
        synchronized (KnoxUtils.class) {
            long j = 0;
            sfTotalBytes = 0L;
            if (networkStats == null) {
                Log.e("KnoxUtils", "No network stats data. App list cleared.");
                return 0L;
            }
            ArrayList arrayList = new ArrayList();
            ActivityManager.getCurrentUser();
            UserManager.get(context).getUserProfiles();
            SparseArray sparseArray = new SparseArray();
            try {
                i = context.getPackageManager().getPackageUid("com.att.iqi", 0);
            } catch (Exception unused) {
                i = -1;
            }
            NetworkStats.Bucket bucket = new NetworkStats.Bucket();
            loop0:
            while (true) {
                long j2 = j;
                while (networkStats.hasNextBucket() && networkStats.getNextBucket(bucket)) {
                    int uid = bucket.getUid();
                    int userId = UserHandle.getUserId(uid);
                    if (uid != i
                            && UserHandle.isApp(uid)
                            && SemPersonaManager.isSecureFolderId(UserHandle.myUserId())
                            && userId == UserHandle.myUserId()
                            && uid != -4
                            && uid != -5
                            && uid != 1000
                            && (uid < 2900 || uid > 2999)) {
                        if (uid < 5000 || uid > 5999) {
                            j = accumulate(uid, sparseArray, bucket, arrayList, j2);
                        }
                    }
                }
            }
            networkStats.close();
            return sfTotalBytes;
        }
    }

    public static boolean checkKnoxCustomSettingsHiddenItem(int i) {
        Trace.beginSection("KnoxUtils#checkKnoxCustomSettingsHiddenItem");
        SettingsManager settingsManager = SettingsManager.getInstance();
        boolean z =
                (i & (settingsManager != null ? settingsManager.getSettingsHiddenState() : 0)) != 0;
        Trace.endSection();
        return z;
    }

    public static int getActivityScreenHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window"))
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getActivityScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window"))
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static Bundle getApplicationRestrictions(Context context) {
        Bundle applicationRestrictions;
        ApplicationRestrictionsManager applicationRestrictionsManager =
                context != null ? ApplicationRestrictionsManager.getInstance(context) : null;
        return (applicationRestrictionsManager == null
                        || (applicationRestrictions =
                                        applicationRestrictionsManager.getApplicationRestrictions(
                                                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, 0))
                                == null
                        || applicationRestrictions.isEmpty())
                ? new Bundle()
                : applicationRestrictions;
    }

    public static LockscreenCredential getCipher(
            LockscreenCredential lockscreenCredential, long j) {
        if (lockscreenCredential == null) {
            return null;
        }
        int type = lockscreenCredential.getType();
        if (type == -1) {
            return lockscreenCredential;
        }
        if (type == 1) {
            return lockscreenCredential.duplicate();
        }
        if (type == 6) {
            return lockscreenCredential;
        }
        if (type == 3) {
            return LockscreenCredential.createPin(
                    String.valueOf(
                            HexEncoding.encode(
                                    StreamCipher.getInstance()
                                            .getCipher(lockscreenCredential.getCredential(), j))));
        }
        if (type == 4) {
            return LockscreenCredential.createPassword(
                    String.valueOf(
                            HexEncoding.encode(
                                    StreamCipher.getInstance()
                                            .getCipher(lockscreenCredential.getCredential(), j))));
        }
        throw new IllegalStateException(
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(type, "Unknown type: "));
    }

    public static long getCipherPublicHandle() {
        return StreamCipher.getInstance().getPublicHandle();
    }

    public static int getDisplayRotation(Context context) {
        int rotation =
                ((WindowManager) context.getSystemService("window"))
                        .getDefaultDisplay()
                        .getRotation();
        if (rotation == 0) {
            return 0;
        }
        if (rotation == 1) {
            return 1;
        }
        if (rotation != 2) {
            return rotation != 3 ? -1 : 3;
        }
        return 2;
    }

    public static int getDualDarDoInnerAuthUserId(Context context) {
        if (DualDarManager.isOnDeviceOwnerEnabled()) {
            return new LockPatternUtils(context)
                    .getLockPatternUtilForDualDarDo()
                    .getInnerAuthUserForDo();
        }
        return -1;
    }

    public static int getInflatedLayoutType(Context context) {
        int i = getActivityScreenWidth(context) > getActivityScreenHeight(context) ? 1001 : 1000;
        Log.d("KnoxUtils", "Inflated layout is : ".concat(i == 1000 ? "PORTRAIT" : "LANDSCAPE"));
        return i;
    }

    public static String getKnoxName(Context context, int i) {
        SemPersonaManager semPersonaManager;
        return (context == null
                        || (semPersonaManager =
                                        (SemPersonaManager) context.getSystemService("persona"))
                                == null)
                ? "Knox"
                : SemPersonaManager.isSecureFolderId(i)
                        ? context.getString(R.string.secure_folder_title)
                        : semPersonaManager.getContainerName(i, context);
    }

    public static int getNavigationBarSize(Context context) {
        int identifier =
                context.getResources()
                        .getIdentifier(
                                "navigation_bar_height",
                                "dimen",
                                RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME);
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static PasswordPolicy getPasswordPolicy(Context context, int i) {
        int i2;
        if (!SemPersonaManager.isKnoxId(i)) {
            EnterpriseDeviceManager enterpriseDeviceManager =
                    EnterpriseDeviceManager.getInstance(context);
            if (enterpriseDeviceManager != null) {
                return enterpriseDeviceManager.getPasswordPolicy();
            }
            return null;
        }
        try {
            i2 =
                    context.getPackageManager()
                            .getPackageUidAsUser(
                                    SemPersonaManager.getAdminComponentName(i).getPackageName(), i);
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Error fetching admin uid "), "KnoxUtils");
            i2 = i;
        }
        KnoxContainerManager knoxContainerManager =
                EnterpriseKnoxManager.getInstance()
                        .getKnoxContainerManager(context, new ContextInfo(i2, i));
        if (knoxContainerManager != null) {
            return knoxContainerManager.getPasswordPolicy();
        }
        return null;
    }

    public static int getPasswordType(int i) {
        if (i == 65536) {
            return 2;
        }
        if (i == 131072 || i == 196608) {
            return 3;
        }
        return (i == 262144 || i == 327680 || i == 393216) ? 4 : 1;
    }

    public static boolean getSAccountLock(Context context, int i) {
        int semGetIntForUser =
                Settings.System.semGetIntForUser(
                        context.getContentResolver(), "sfkeyguard_sa_state", -1, i);
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("getSAccountLock : "), semGetIntForUser == 1, "KnoxUtils");
        return semGetIntForUser == 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.drawable.Drawable getSecureFolderLogo(
            android.content.Context r7, int r8) {
        /*
            java.lang.String r0 = "KnoxUtils"
            r1 = 0
            r2 = 0
            java.lang.String r3 = "persona"
            java.lang.Object r3 = r7.getSystemService(r3)     // Catch: java.lang.Exception -> L23
            com.samsung.android.knox.SemPersonaManager r3 = (com.samsung.android.knox.SemPersonaManager) r3     // Catch: java.lang.Exception -> L23
            byte[] r8 = com.samsung.android.knox.SemPersonaManager.getKnoxIcon(r8)     // Catch: java.lang.Exception -> L23
            android.graphics.drawable.BitmapDrawable r3 = new android.graphics.drawable.BitmapDrawable     // Catch: java.lang.Exception -> L21
            android.content.res.Resources r4 = r7.getResources()     // Catch: java.lang.Exception -> L21
            int r5 = r8.length     // Catch: java.lang.Exception -> L21
            android.graphics.Bitmap r5 = android.graphics.BitmapFactory.decodeByteArray(r8, r1, r5)     // Catch: java.lang.Exception -> L21
            r3.<init>(r4, r5)     // Catch: java.lang.Exception -> L21
            r2 = r3
            goto L3a
        L21:
            r3 = move-exception
            goto L25
        L23:
            r3 = move-exception
            r8 = r2
        L25:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "Unable to fetch sf icon : "
            r4.<init>(r5)
            java.lang.String r3 = r3.getMessage()
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            android.util.Log.d(r0, r3)
        L3a:
            if (r8 != 0) goto Lc8
            java.lang.String r3 = "Applying default sf icon"
            android.util.Log.d(r0, r3)
            android.content.res.Resources r0 = r7.getResources()
            r3 = 2131233998(0x7f080cce, float:1.808415E38)
            android.graphics.drawable.Drawable r0 = r0.getDrawable(r3)
            boolean r3 = r0 instanceof android.graphics.drawable.BitmapDrawable
            if (r3 == 0) goto L57
            android.graphics.drawable.BitmapDrawable r0 = (android.graphics.drawable.BitmapDrawable) r0
            android.graphics.Bitmap r0 = r0.getBitmap()
            goto La6
        L57:
            android.graphics.Rect r3 = r0.getBounds()
            boolean r3 = r3.isEmpty()
            if (r3 != 0) goto L6a
            android.graphics.Rect r3 = r0.getBounds()
            int r3 = r3.width()
            goto L6e
        L6a:
            int r3 = r0.getIntrinsicWidth()
        L6e:
            android.graphics.Rect r4 = r0.getBounds()
            boolean r4 = r4.isEmpty()
            if (r4 != 0) goto L81
            android.graphics.Rect r4 = r0.getBounds()
            int r4 = r4.height()
            goto L85
        L81:
            int r4 = r0.getIntrinsicHeight()
        L85:
            r5 = 1
            if (r3 > 0) goto L89
            r3 = r5
        L89:
            if (r4 > 0) goto L8c
            r4 = r5
        L8c:
            android.graphics.Bitmap$Config r5 = android.graphics.Bitmap.Config.ARGB_8888
            android.graphics.Bitmap r3 = android.graphics.Bitmap.createBitmap(r3, r4, r5)
            android.graphics.Canvas r4 = new android.graphics.Canvas
            r4.<init>(r3)
            int r5 = r4.getWidth()
            int r6 = r4.getHeight()
            r0.setBounds(r1, r1, r5, r6)
            r0.draw(r4)
            r0 = r3
        La6:
            if (r0 == 0) goto Lb8
            java.io.ByteArrayOutputStream r8 = new java.io.ByteArrayOutputStream
            r8.<init>()
            android.graphics.Bitmap$CompressFormat r3 = android.graphics.Bitmap.CompressFormat.PNG
            r4 = 100
            r0.compress(r3, r4, r8)
            byte[] r8 = r8.toByteArray()
        Lb8:
            if (r8 == 0) goto Lc8
            android.graphics.drawable.BitmapDrawable r2 = new android.graphics.drawable.BitmapDrawable
            android.content.res.Resources r7 = r7.getResources()
            int r0 = r8.length
            android.graphics.Bitmap r8 = android.graphics.BitmapFactory.decodeByteArray(r8, r1, r0)
            r2.<init>(r7, r8)
        Lc8:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.knox.KnoxUtils.getSecureFolderLogo(android.content.Context,"
                    + " int):android.graphics.drawable.Drawable");
    }

    public static int getTwoFactorValue(Context context, int i) {
        int intForUser =
                Settings.Secure.getIntForUser(
                        context.getContentResolver(), "knox_finger_print_plus", 0, i);
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                "get two factor value : ", " : ", i, intForUser, "KnoxUtils");
        return intForUser;
    }

    public static boolean hasSamsungAccount(Context context) {
        Account[] accountArr;
        try {
            accountArr =
                    AccountManager.get(context)
                            .getAccountsByTypeAsUser("com.osp.app.signin", UserHandle.SEM_OWNER);
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception : "), "KnoxUtils");
            accountArr = null;
        }
        return accountArr != null && accountArr.length > 0;
    }

    public static void insertLocktypeDataForDualDar(int i, int i2, String str, boolean z) {
        Log.d("KnoxUtils", "Bio & Multifact info, enableMultiFact: " + z + ", bioInfo: " + i2);
        if (str.equals("DEVICE_AUTH_TYPE")) {
            KnoxAnalyticsData knoxAnalyticsData =
                    new KnoxAnalyticsData("KNOX_DUALDAR", 1, "DEVICE_AUTH_TYPE");
            knoxAnalyticsData.setProperty("lckTp", i);
            knoxAnalyticsData.setProperty("bioInf", i2);
            knoxAnalyticsData.setProperty("mfaEnb", z ? 1 : 0);
            Log.d("KnoxUtils:DualDARAnalytics", "Payload / " + knoxAnalyticsData.toString());
            KnoxAnalytics.log(knoxAnalyticsData);
            return;
        }
        if (str.equals("DAR_AUTH_TYPE")) {
            KnoxAnalyticsData knoxAnalyticsData2 =
                    new KnoxAnalyticsData("KNOX_DUALDAR", 1, "DAR_AUTH_TYPE");
            knoxAnalyticsData2.setProperty("lckTp", i);
            knoxAnalyticsData2.setProperty("bioInf", i2);
            knoxAnalyticsData2.setProperty("mfaEnb", z ? 1 : 0);
            Log.d("KnoxUtils:DualDARAnalytics", "Payload / " + knoxAnalyticsData2.toString());
            KnoxAnalytics.log(knoxAnalyticsData2);
        }
    }

    public static void insertStatusLogForKnox(Context context, int i, int i2) {
        Log.d("KnoxUtils", "KA broadcast request received- userID: " + i);
        if (SemPersonaManager.isSecureFolderId(i)
                || !(SemPersonaManager.isKnoxId(i) || SemPersonaManager.isDoEnabled(i))) {
            Log.secD("KnoxUtils", "insertLog() is failed : SecureFolder user");
            return;
        }
        if (!isDualDarEnabled(context)) {
            Intent intent = new Intent("samsung.knox.intent.action.CHANGE_LOCK_TYPE");
            intent.addFlags(1342177280);
            intent.putExtra("android.intent.extra.user_handle", i);
            intent.putExtra("lckTp", i2);
            context.sendBroadcastAsUser(intent, new UserHandle(0));
            Log.d("KnoxUtils", "KA broadcast sent");
            return;
        }
        Log.d(
                "KnoxUtils:DualDARAnalytics",
                "DualDAR Auth Type change request received for, UserID: "
                        + i
                        + ", event: CHANGE_LOCK_TYPE, locktype: "
                        + i2);
        LockPatternUtils lockPatternUtils = new LockPatternUtils(context);
        int biometricState = lockPatternUtils.getBiometricState(1, i);
        int biometricState2 = lockPatternUtils.getBiometricState(256, i) << 2;
        boolean z =
                Settings.System.getIntForUser(
                                context.getContentResolver(), "knox_finger_print_plus", 0, i)
                        == 1;
        int i3 = biometricState | biometricState2;
        if (i2 == 0) {
            i2 = getPasswordType(lockPatternUtils.getActivePasswordQuality(i));
        }
        if (i == 0) {
            insertLocktypeDataForDualDar(i2, i3, "DEVICE_AUTH_TYPE", z);
        } else {
            insertLocktypeDataForDualDar(i2, i3, "DAR_AUTH_TYPE", z);
        }
    }

    public static boolean isApplicationRestricted(Context context, String str) {
        if (!appRestrictionState) {
            return false;
        }
        Bundle applicationRestrictions = getApplicationRestrictions(context);
        if (applicationRestrictions.containsKey(str)) {
            return applicationRestrictions.getBundle(str).getBoolean("hide")
                    || applicationRestrictions.getBundle(str).getBoolean("grayout");
        }
        return false;
    }

    public static boolean isAvailableWithMultiWindowForKnox(Activity activity, int i) {
        if (!SemPersonaManager.isKnoxId(i)) {
            return true;
        }
        if (Rune.isSamsungDexMode(activity) && Rune.isSamsungDexOnPCMode(activity)) {
            Log.d("KnoxUtils", "Not available with Dex on pc mode");
            return false;
        }
        if (!LockUtils.isInMultiWindow(activity) || Rune.isSamsungDexMode(activity)) {
            return true;
        }
        Log.d("KnoxUtils", "Not available with multi-window mode");
        return false;
    }

    public static int isChangeRequested(Context context, int i) {
        PasswordPolicy passwordPolicy = getPasswordPolicy(context, i);
        if (passwordPolicy != null) {
            return passwordPolicy.isChangeRequested();
        }
        return 0;
    }

    public static int isChangeRequestedForInner(Context context) {
        PasswordPolicy passwordPolicy;
        EnterpriseDeviceManager enterpriseDeviceManager =
                EnterpriseDeviceManager.getInstance(context);
        if (enterpriseDeviceManager == null
                || (passwordPolicy = enterpriseDeviceManager.getPasswordPolicy()) == null) {
            return 0;
        }
        return passwordPolicy.isChangeRequestedForInner();
    }

    public static boolean isDualDarDoInnerAuthUser(Context context, int i) {
        if (DualDarManager.isOnDeviceOwnerEnabled()) {
            return new LockPatternUtils(context)
                    .getLockPatternUtilForDualDarDo()
                    .isInnerAuthUserForDo(i);
        }
        return false;
    }

    public static boolean isDualDarEnabled(Context context) {
        UserManager userManager = UserManager.get(context);
        if (userManager == null) {
            throw new IllegalStateException("Unable to load UserManager");
        }
        boolean z = false;
        Iterator it = userManager.getUsers(false).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            UserInfo userInfo = (UserInfo) it.next();
            if (userInfo != null && SemPersonaManager.isDarDualEncryptionEnabled(userInfo.id)) {
                z = true;
                break;
            }
        }
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "isDualDAREnabled result : ", "KnoxUtils", z);
        return z;
    }

    public static boolean isFaceEnabled(Context context, int i) {
        if (!isFaceLockSetForUser(context, i)) {
            return false;
        }
        return !((getPasswordPolicy(context, i) != null
                        ? getPasswordPolicy(context, i).isBiometricAuthenticationEnabled(4) ^ true
                        : false)
                || ((((DevicePolicyManager) context.getSystemService("device_policy"))
                                        .getKeyguardDisabledFeatures(null, i)
                                & 128)
                        != 0));
    }

    public static boolean isFaceLockSetForUser(Context context, int i) {
        return new LockPatternUtils(context).getBiometricState(256, i) != 0;
    }

    public static boolean isFingerPrintDisabledByPolicy(Context context, int i) {
        return (getPasswordPolicy(context, i) != null
                        ? getPasswordPolicy(context, i).isBiometricAuthenticationEnabled(1) ^ true
                        : false)
                || ((((DevicePolicyManager) context.getSystemService("device_policy"))
                                        .getKeyguardDisabledFeatures(null, i)
                                & 32)
                        != 0);
    }

    public static boolean isFingerprintEnabled(Context context, int i) {
        return isFingerprintLockSetForUser(context, i)
                && !isFingerPrintDisabledByPolicy(context, i);
    }

    public static boolean isFingerprintLockSetForUser(Context context, int i) {
        return new LockPatternUtils(context).getBiometricState(1, i) != 0;
    }

    public static boolean isFoldableProduct() {
        String trim = SystemProperties.get("ro.product.device", "NONE").trim();
        if (trim.startsWith("winner")
                || trim.startsWith("f2")
                || trim.startsWith("FSG")
                || trim.startsWith("SCG")) {
            return true;
        }
        return SemFloatingFeature.getInstance()
                .getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
    }

    public static boolean isKnoxCustomProKioskEnabledSettingsItem(int i) {
        ProKioskManager proKioskManager = ProKioskManager.getInstance();
        return (i
                        & ((proKioskManager == null || !proKioskManager.getProKioskState())
                                ? 0
                                : proKioskManager.getSettingsEnabledItems()))
                != 0;
    }

    public static boolean isKnoxOrganizationOwnedDevice(Context context, int i) {
        if (i != 0) {
            return false;
        }
        return SemPersonaManager.isDoEnabled(i)
                || ((DevicePolicyManager) context.getSystemService("device_policy"))
                        .isOrganizationOwnedDeviceWithManagedProfile();
    }

    public static boolean isMultifactorAuthEnforced(Context context, int i) {
        PasswordPolicy passwordPolicy;
        if (isDualDarDoInnerAuthUser(context, i)
                || (passwordPolicy = getPasswordPolicy(context, i)) == null) {
            return false;
        }
        return passwordPolicy.isMultifactorAuthenticationEnabled();
    }

    public static boolean isMultifactorEnabledForWork(Context context, int i) {
        return SemPersonaManager.isKnoxId(i) && getTwoFactorValue(context, i) == 1;
    }

    public static boolean isPwdChangeEnforced(Context context, int i) {
        int isChangeRequested = isChangeRequested(context, i);
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                "isChangeRequested = ", " : user", isChangeRequested, i, "KnoxUtils");
        return isChangeRequested >= 1;
    }

    public static boolean isPwdSetupOrChangeRequested(Context context, int i) {
        if (context == null) {
            return false;
        }
        UserManager userManager = UserManager.get(context);
        if (userManager == null) {
            throw new IllegalStateException("Unable to load UserManager");
        }
        UserInfo userInfo = userManager.getUserInfo(i);
        PasswordPolicy passwordPolicy = getPasswordPolicy(context, i);
        if ((passwordPolicy == null || passwordPolicy.isChangeRequested() <= 0)
                && (userInfo == null || !userInfo.needSetupCredential())) {
            return false;
        }
        Log.d("KnoxUtils", "isPwdSetupOrChangeRequested true");
        return true;
    }

    public static boolean isResetWithSamsungAccountEnable(Context context, int i) {
        return Settings.System.getIntForUser(
                        context.getContentResolver(), "sf_reset_with_samsung_account", 1, i)
                == 1;
    }

    public static boolean isRuggedFeatureEnabled() {
        SystemManager systemManager = SystemManager.getInstance();
        if (systemManager != null) {
            return systemManager.getHardKeyIntentState() == 1
                    || systemManager.getHardKeyIntentState(
                                    EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_FAILED, 3)
                            == 1
                    || systemManager.getHardKeyIntentState(1079, 3) == 1
                    || systemManager.getHardKeyBlockState(
                                    EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_FAILED, 3)
                            == 1
                    || systemManager.getHardKeyBlockState(1079, 3) == 1
                    || systemManager.getHardKeyIntentBroadcast(
                                    EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_FAILED, 3)
                            == 1
                    || systemManager.getHardKeyIntentBroadcast(1079, 3) == 1;
        }
        return false;
    }

    public static boolean isTablet() {
        String str = mDeviceType;
        return str != null && str.contains("tablet");
    }

    public static boolean needSetupCredential(Context context, int i) {
        return ((UserManager) context.getSystemService("user"))
                .getUserInfo(i)
                .needSetupCredential();
    }

    public static boolean needUCMLockSetWithoutWC(Context context, int i, boolean z) {
        LockPatternUtils lockPatternUtils = new LockPatternUtils(context);
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
        int keyguardStoredPasswordQuality = lockPatternUtils.getKeyguardStoredPasswordQuality(i);
        boolean isDeviceLocked = keyguardManager.isDeviceLocked(i);
        boolean isEnforcedCredentialStorageExistAsUser =
                UCMUtils.isEnforcedCredentialStorageExistAsUser(i);
        boolean z2 =
                (z
                                || keyguardStoredPasswordQuality == 458752
                                || !isEnforcedCredentialStorageExistAsUser
                                || isDeviceLocked)
                        ? false
                        : true;
        boolean z3 =
                z
                        && keyguardStoredPasswordQuality == 458752
                        && !isEnforcedCredentialStorageExistAsUser
                        && !isDeviceLocked;
        boolean z4 =
                !z
                        && keyguardStoredPasswordQuality == 458752
                        && isEnforcedCredentialStorageExistAsUser
                        && !isDeviceLocked;
        Log.d(
                "KnoxUtils",
                "[Q] = "
                        + keyguardStoredPasswordQuality
                        + ", [L] = "
                        + isDeviceLocked
                        + ", [E] = "
                        + isEnforcedCredentialStorageExistAsUser
                        + ", [C1] = "
                        + z2
                        + ", [C2] = "
                        + z3
                        + ", [C3] = "
                        + z4);
        return z2 || z3 || z4;
    }

    public static void removeBiometricLock(LockPatternUtils lockPatternUtils, int i, int i2) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(i, "removeBiometricLock : ", "KnoxUtils");
        if (lockPatternUtils == null) {
            Log.e("KnoxUtils", "lpu is null!");
        } else {
            lockPatternUtils.setBiometricState(i, 0, i2);
        }
    }

    public static void removeKnoxCustomSettingsHiddenItems(
            SettingsPreferenceFragment settingsPreferenceFragment) {
        SettingsManager settingsManager = SettingsManager.getInstance();
        int settingsHiddenState =
                settingsManager != null ? settingsManager.getSettingsHiddenState() : 0;
        if ((settingsHiddenState & 1) != 0) {
            settingsPreferenceFragment.removePreference("wifi_settings");
        }
        if ((settingsHiddenState & 2) != 0) {
            settingsPreferenceFragment.removePreference("bluetooth_settings");
        }
        if ((settingsHiddenState & 4) != 0) {
            settingsPreferenceFragment.removePreference("toggle_airplane");
        }
        if ((settingsHiddenState & 1024) != 0) {
            settingsPreferenceFragment.removePreference("location_settings");
        }
        if ((settingsHiddenState & 128) != 0) {
            settingsPreferenceFragment.removePreference("user_preference");
        }
        if ((settingsHiddenState & 32) != 0) {
            settingsPreferenceFragment.removePreference("phone_language");
            settingsPreferenceFragment.removePreference("current_input_method");
        }
        if ((settingsHiddenState & 2048) != 0) {
            settingsPreferenceFragment.removePreference("category_samsungservices");
            settingsPreferenceFragment.removePreference("external_storage_transfer");
            settingsPreferenceFragment.removePreference("backup_category");
        }
        if ((settingsHiddenState & 4096) != 0) {
            settingsPreferenceFragment.removePreference("reset_category");
            settingsPreferenceFragment.removePreference("reset_preference");
        }
    }

    public static LockscreenCredential restoreCipherPassword(
            LockscreenCredential lockscreenCredential, long j) {
        char[] cArr;
        if (lockscreenCredential == null) {
            return null;
        }
        int type = lockscreenCredential.getType();
        if (type == -1 || type == 1 || type == 6) {
            return lockscreenCredential;
        }
        if (type != 3 && type != 4) {
            throw new IllegalStateException(
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(type, "Unknown type: "));
        }
        byte[] credential = lockscreenCredential.getCredential();
        if (credential == null) {
            cArr = null;
        } else {
            cArr = new char[credential.length];
            for (int i = 0; i < credential.length; i++) {
                cArr[i] = (char) credential[i];
            }
        }
        byte[] decode = cArr != null ? HexEncoding.decode(cArr, true) : null;
        byte[] restoreCipher = StreamCipher.getInstance().restoreCipher(decode, j);
        try {
            return LockscreenCredential.streamCredential(type, restoreCipher);
        } finally {
            if (cArr != null) {
                Arrays.fill(cArr, (char) 0);
            }
            if (decode != null) {
                Arrays.fill(decode, (byte) 0);
            }
            if (restoreCipher != null) {
                Arrays.fill(restoreCipher, (byte) 0);
            }
        }
    }

    public static void setTwoFactorValue(Context context, int i, int i2) {
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                "set two factor value : ", " : ", i, i2, "KnoxUtils");
        Settings.Secure.putIntForUser(
                context.getContentResolver(), "knox_finger_print_plus", i2, i);
        if (i2 > 0) {
            try {
                if (isFaceLockSetForUser(context, i)) {
                    new LockPatternUtils(context).setBiometricState(256, 0, i);
                }
            } catch (Exception e) {
                CloneBackend$$ExternalSyntheticOutline0.m(
                        e,
                        new StringBuilder("Exception during face lock check : Msg = "),
                        "KnoxUtils");
            }
        }
    }

    public static void showPolicyDialog(
            final FragmentActivity fragmentActivity,
            KnoxPasswordChangePolicy knoxPasswordChangePolicy) {
        String string;
        String string2;
        String string3;
        int ordinal = knoxPasswordChangePolicy.ordinal();
        if (ordinal == 0) {
            string =
                    fragmentActivity
                            .getResources()
                            .getString(R.string.sec_container_change_password_policy_default);
            string2 = fragmentActivity.getResources().getString(R.string.sec_container_change_lock);
            string3 = fragmentActivity.getResources().getString(R.string.common_cancel);
        } else if (ordinal != 1) {
            Log.d("KnoxUtils", "Password policy could not be identified.");
            string = ApnSettings.MVNO_NONE;
            string3 = ApnSettings.MVNO_NONE;
            string2 = string3;
        } else {
            string =
                    fragmentActivity
                            .getResources()
                            .getString(R.string.sec_container_reset_password_policy_default);
            string2 = fragmentActivity.getResources().getString(R.string.sec_container_reset_lock);
            string3 = fragmentActivity.getResources().getString(R.string.common_cancel);
        }
        AlertDialog.Builder builder =
                new AlertDialog.Builder(
                        new ContextThemeWrapper(
                                fragmentActivity,
                                (fragmentActivity.getResources().getConfiguration().uiMode & 48)
                                                == 32
                                        ? 4
                                        : 5));
        builder.setMessage(string)
                .setCancelable(false)
                .setNegativeButton(
                        string3,
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.knox.KnoxUtils$$ExternalSyntheticLambda0
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                Context context = fragmentActivity;
                                dialogInterface.dismiss();
                                ((Activity) context).finish();
                            }
                        })
                .setPositiveButton(string2, new KnoxUtils$$ExternalSyntheticLambda1());
        AlertDialog create = builder.create();
        create.setOnKeyListener(
                new DialogInterface
                        .OnKeyListener() { // from class:
                                           // com.samsung.android.settings.knox.KnoxUtils$$ExternalSyntheticLambda2
                    @Override // android.content.DialogInterface.OnKeyListener
                    public final boolean onKey(
                            DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                        Context context = fragmentActivity;
                        if (i != 4 || keyEvent.getAction() != 1) {
                            return false;
                        }
                        dialogInterface.dismiss();
                        ((Activity) context).finish();
                        return false;
                    }
                });
        create.show();
    }

    public static void updateRestrictionState(Context context) {
        if (appRestrictionState) {
            return;
        }
        appRestrictionState = !getApplicationRestrictions(context, 0).isEmpty();
        if (Utils.isManagedProfilePresent(context)) {
            appRestrictionState =
                    (!getApplicationRestrictions(context, 10).isEmpty()) | appRestrictionState;
        }
    }

    public static Bundle getApplicationRestrictions(Context context, int i) {
        ApplicationRestrictionsManager applicationRestrictionsManager =
                context != null ? ApplicationRestrictionsManager.getInstance(context) : null;
        if (applicationRestrictionsManager != null) {
            Bundle applicationRestrictions =
                    applicationRestrictionsManager.getApplicationRestrictions(
                            KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, i);
            Bundle applicationRestrictions2 =
                    applicationRestrictionsManager.getApplicationRestrictions(
                            "com.samsung.android.knox.galaxyai", i);
            Bundle bundle = new Bundle();
            bundle.putAll(applicationRestrictions);
            bundle.putAll(applicationRestrictions2);
            return bundle;
        }
        return new Bundle();
    }

    public static boolean isApplicationRestricted(Context context, String str, String str2) {
        if (!appRestrictionState) {
            return false;
        }
        Bundle applicationRestrictions = getApplicationRestrictions(context);
        if (applicationRestrictions.isEmpty() || !applicationRestrictions.containsKey(str)) {
            return false;
        }
        return applicationRestrictions.getBundle(str).getBoolean(str2);
    }

    public static boolean isApplicationRestricted(Context context, List list) {
        if (!appRestrictionState) {
            return false;
        }
        Bundle applicationRestrictions = getApplicationRestrictions(context);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (applicationRestrictions.containsKey(str)
                    && (applicationRestrictions.getBundle(str).getBoolean("hide")
                            || applicationRestrictions.getBundle(str).getBoolean("grayout"))) {
                return true;
            }
        }
        return false;
    }
}
