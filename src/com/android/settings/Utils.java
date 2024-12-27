package com.android.settings;

import android.R;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppGlobals;
import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.IntentFilterVerificationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.hardware.face.Face;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.preference.PreferenceFrameLayout;
import android.provider.Settings;
import android.service.persistentdata.PersistentDataBlockManager;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.style.TtsSpan;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.app.UnlaunchableAppActivity;
import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.internal.telephony.TelephonyFeatures;
import com.android.internal.util.ArrayUtils;
import com.android.internal.widget.LockPatternUtils;
import com.android.settings.Settings;
import com.android.settings.dashboard.profileselector.ProfileFragmentBridge;
import com.android.settingslib.applications.RecentAppOpsAccess;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.widget.ActionBarShadowController;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.application.ApplicationPolicy;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.Trace;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.samsung.android.settings.inputmethod.TouchPadGestureSettingsController;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.wifi.SemWifiApCust;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public abstract class Utils extends com.android.settingslib.Utils {
    public static String CONFIGOPBRANDINGFORMOBILEAP;
    public static final boolean DBG;
    public static final Uri DEX_SETTINGS_URI;
    public static int MAX_CLIENT_4_MOBILEAP;
    public static final boolean MHSDBG;
    public static final boolean SPF_SupportInstantHotspot;
    public static final boolean SPF_SupportMobileApDualAp;
    public static final boolean SPF_SupportMobileApEnhanced;
    public static boolean SUPPORT_MOBILEAP_5G_BASED_ON_COUNTRY;
    public static boolean SUPPORT_MOBILEAP_6G_BASED_ON_COUNTRY;
    public static String SUPPORT_MOBILEAP_REGION;
    public static boolean SUPPORT_MOBILEAP_WIFISHARING;
    public static boolean SUPPORT_MOBILEAP_WIFISHARINGLITE;
    public static boolean USE_BIXBY;
    public static final boolean isSupportMobileWips;
    public static String mCountryCode;
    public static OnlineHelpMenuState mHelpMenuData;
    public static final StringBuilder sBuilder;
    public static final Formatter sFormatter;
    public static Signature[] sSystemSignature;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.Utils$1, reason: invalid class name */
    public final class AnonymousClass1 extends FaceManager.RemovalCallback {
        public final /* synthetic */ int val$userId;

        public AnonymousClass1(int i) {
            this.val$userId = i;
        }

        public final void onRemovalError(Face face, int i, CharSequence charSequence) {
            Log.e("Settings", "Unable to remove face template for user " + this.val$userId + ", error: " + ((Object) charSequence));
        }

        public final void onRemovalSucceeded(Face face, int i) {
            if (i == 0) {
                Preference$$ExternalSyntheticOutline0.m(new StringBuilder("Enrolled face templates removed for user "), this.val$userId, "Settings");
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.Utils$2, reason: invalid class name */
    public final class AnonymousClass2 extends FingerprintManager.RemovalCallback {
        public final /* synthetic */ int val$userId;

        public AnonymousClass2(int i) {
            this.val$userId = i;
        }

        public final void onRemovalError(Fingerprint fingerprint, int i, CharSequence charSequence) {
            Log.e("Settings", "Unable to remove fingerprint for user " + this.val$userId + " , error: " + ((Object) charSequence));
        }

        public final void onRemovalSucceeded(Fingerprint fingerprint, int i) {
            if (i == 0) {
                Preference$$ExternalSyntheticOutline0.m(new StringBuilder("Enrolled fingerprints removed for user "), this.val$userId, "Settings");
            }
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BiometricStatus {
        public static final /* synthetic */ BiometricStatus[] $VALUES;
        public static final BiometricStatus ERROR;
        public static final BiometricStatus LOCKOUT;
        public static final BiometricStatus NOT_ACTIVE;
        public static final BiometricStatus OK;

        static {
            BiometricStatus biometricStatus = new BiometricStatus("OK", 0);
            OK = biometricStatus;
            BiometricStatus biometricStatus2 = new BiometricStatus("NOT_ACTIVE", 1);
            NOT_ACTIVE = biometricStatus2;
            BiometricStatus biometricStatus3 = new BiometricStatus("LOCKOUT", 2);
            LOCKOUT = biometricStatus3;
            BiometricStatus biometricStatus4 = new BiometricStatus("ERROR", 3);
            ERROR = biometricStatus4;
            $VALUES = new BiometricStatus[]{biometricStatus, biometricStatus2, biometricStatus3, biometricStatus4};
        }

        public static BiometricStatus valueOf(String str) {
            return (BiometricStatus) Enum.valueOf(BiometricStatus.class, str);
        }

        public static BiometricStatus[] values() {
            return (BiometricStatus[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EmojiInputFilter implements InputFilter {
        public final Context mContext;

        public EmojiInputFilter(Context context) {
            this.mContext = context;
        }

        /* JADX WARN: Removed duplicated region for block: B:5:0x001e A[FALL_THROUGH] */
        /* JADX WARN: Removed duplicated region for block: B:7:0x007f A[LOOP:0: B:2:0x000c->B:7:0x007f, LOOP_END] */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0064 A[SYNTHETIC] */
        @Override // android.text.InputFilter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.CharSequence filter(java.lang.CharSequence r7, int r8, int r9, android.text.Spanned r10, int r11, int r12) {
            /*
                r6 = this;
                r8 = 1
                android.content.Context r6 = r6.mContext
                java.lang.StringBuilder r9 = com.android.settings.Utils.sBuilder
                int r9 = r7.length()
                r0 = 0
                r1 = r0
                r2 = r1
            Lc:
                if (r1 >= r9) goto L81
                char r3 = r7.charAt(r1)
                char r4 = r7.charAt(r1)
                int r4 = java.lang.Character.getType(r4)
                r5 = 19
                if (r5 != r4) goto L20
            L1e:
                r2 = r8
                goto L62
            L20:
                r4 = 55296(0xd800, float:7.7486E-41)
                if (r3 < r4) goto L2b
                r4 = 56319(0xdbff, float:7.892E-41)
                if (r3 > r4) goto L2b
                goto L1e
            L2b:
                r4 = 9728(0x2600, float:1.3632E-41)
                if (r3 == r4) goto L1e
                r4 = 9729(0x2601, float:1.3633E-41)
                if (r3 == r4) goto L1e
                r4 = 9748(0x2614, float:1.366E-41)
                if (r3 == r4) goto L1e
                r4 = 9749(0x2615, float:1.3661E-41)
                if (r3 == r4) goto L1e
                r4 = 9829(0x2665, float:1.3773E-41)
                if (r3 == r4) goto L1e
                r4 = 9830(0x2666, float:1.3775E-41)
                if (r3 == r4) goto L1e
                r4 = 9888(0x26a0, float:1.3856E-41)
                if (r3 == r4) goto L1e
                r4 = 9889(0x26a1, float:1.3857E-41)
                if (r3 == r4) goto L1e
                r4 = 9898(0x26aa, float:1.387E-41)
                if (r3 == r4) goto L1e
                r4 = 9899(0x26ab, float:1.3871E-41)
                if (r3 == r4) goto L1e
                switch(r3) {
                    case 8265: goto L1e;
                    case 8505: goto L1e;
                    case 8618: goto L1e;
                    case 8987: goto L1e;
                    case 9200: goto L1e;
                    case 9203: goto L1e;
                    case 9410: goto L1e;
                    case 9643: goto L1e;
                    case 9654: goto L1e;
                    case 9664: goto L1e;
                    case 9745: goto L1e;
                    case 9757: goto L1e;
                    case 9800: goto L1e;
                    case 9801: goto L1e;
                    case 9802: goto L1e;
                    case 9803: goto L1e;
                    case 9804: goto L1e;
                    case 9805: goto L1e;
                    case 9806: goto L1e;
                    case 9807: goto L1e;
                    case 9808: goto L1e;
                    case 9809: goto L1e;
                    case 9810: goto L1e;
                    case 9811: goto L1e;
                    case 9824: goto L1e;
                    case 9827: goto L1e;
                    case 9832: goto L1e;
                    case 9851: goto L1e;
                    case 9855: goto L1e;
                    case 9917: goto L1e;
                    case 9918: goto L1e;
                    case 9924: goto L1e;
                    case 9925: goto L1e;
                    case 9934: goto L1e;
                    case 9940: goto L1e;
                    case 9971: goto L1e;
                    case 9973: goto L1e;
                    case 9978: goto L1e;
                    case 9981: goto L1e;
                    case 9986: goto L1e;
                    case 9989: goto L1e;
                    case 9992: goto L1e;
                    case 9994: goto L1e;
                    case 9995: goto L1e;
                    case 9996: goto L1e;
                    case 9997: goto L1e;
                    case 10002: goto L1e;
                    case 10004: goto L1e;
                    case 10006: goto L1e;
                    case 10024: goto L1e;
                    case 10035: goto L1e;
                    case 10036: goto L1e;
                    case 10052: goto L1e;
                    case 10055: goto L1e;
                    case 10060: goto L1e;
                    case 10062: goto L1e;
                    case 10067: goto L1e;
                    case 10068: goto L1e;
                    case 10069: goto L1e;
                    case 10071: goto L1e;
                    case 10083: goto L1e;
                    case 10084: goto L1e;
                    case 10133: goto L1e;
                    case 10134: goto L1e;
                    case 10135: goto L1e;
                    case 10160: goto L1e;
                    case 10175: goto L1e;
                    case 10548: goto L1e;
                    case 10549: goto L1e;
                    case 11013: goto L1e;
                    case 11014: goto L1e;
                    case 11015: goto L1e;
                    case 11035: goto L1e;
                    case 11036: goto L1e;
                    case 11088: goto L1e;
                    case 11093: goto L1e;
                    case 12336: goto L1e;
                    case 12349: goto L1e;
                    case 12951: goto L1e;
                    case 12953: goto L1e;
                    default: goto L56;
                }
            L56:
                switch(r3) {
                    case 8596: goto L1e;
                    case 8597: goto L1e;
                    case 8598: goto L1e;
                    case 8599: goto L1e;
                    case 8600: goto L1e;
                    case 8601: goto L1e;
                    case 8602: goto L1e;
                    default: goto L59;
                }
            L59:
                switch(r3) {
                    case 9193: goto L1e;
                    case 9194: goto L1e;
                    case 9195: goto L1e;
                    case 9196: goto L1e;
                    default: goto L5c;
                }
            L5c:
                switch(r3) {
                    case 9723: goto L1e;
                    case 9724: goto L1e;
                    case 9725: goto L1e;
                    case 9726: goto L1e;
                    default: goto L5f;
                }
            L5f:
                switch(r3) {
                    case 9785: goto L1e;
                    case 9786: goto L1e;
                    case 9787: goto L1e;
                    default: goto L62;
                }
            L62:
                if (r2 == 0) goto L7f
                android.view.ContextThemeWrapper r7 = new android.view.ContextThemeWrapper
                r8 = 16974123(0x103012b, float:2.4061738E-38)
                r7.<init>(r6, r8)
                r8 = 2132026282(0x7f1423aa, float:1.9691092E38)
                java.lang.String r6 = r6.getString(r8)
                android.widget.Toast r6 = android.widget.Toast.makeText(r7, r6, r0)
                r6.show()
                java.lang.CharSequence r7 = r10.subSequence(r11, r12)
                goto L81
            L7f:
                int r1 = r1 + r8
                goto Lc
            L81:
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.settings.Utils.EmojiInputFilter.filter(java.lang.CharSequence, int, int, android.text.Spanned, int, int):java.lang.CharSequence");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class OnlineHelpMenuState {
        public int iconRes;
        public Intent intent;
        public boolean removeTile;
        public int summaryRes;
        public int titleRes;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RoleDescriptionAccessibilityDelegate extends AccessibilityDelegateCompat {
        public final String mRoleDescription;

        public RoleDescriptionAccessibilityDelegate(String str) {
            this.mRoleDescription = str;
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
            accessibilityNodeInfoCompat.setRoleDescription(this.mRoleDescription);
        }
    }

    static {
        StringBuilder sb = new StringBuilder(50);
        sBuilder = sb;
        sFormatter = new Formatter(sb, Locale.getDefault());
        DBG = SystemProperties.getInt("ro.debuggable", 0) == 1;
        DEX_SETTINGS_URI = Uri.parse("content://com.sec.android.desktopmode.uiservice.SettingsProvider/");
        MAX_CLIENT_4_MOBILEAP = 10;
        SUPPORT_MOBILEAP_WIFISHARING = false;
        SemWifiApCust.getInstance();
        CONFIGOPBRANDINGFORMOBILEAP = SemWifiApCust.mMHSCustomer;
        SPF_SupportMobileApEnhanced = true;
        SPF_SupportMobileApDualAp = true;
        SPF_SupportInstantHotspot = true;
        SUPPORT_MOBILEAP_WIFISHARINGLITE = false;
        isSupportMobileWips = isWipsEnabled();
        SUPPORT_MOBILEAP_5G_BASED_ON_COUNTRY = false;
        SUPPORT_MOBILEAP_6G_BASED_ON_COUNTRY = false;
        SUPPORT_MOBILEAP_REGION = ApnSettings.MVNO_NONE;
        USE_BIXBY = false;
        MHSDBG = SemWifiManager.MHSDBG;
    }

    public static void applyLandscapeFullScreen(Context context, Window window) {
        if (context == null || window == null) {
            return;
        }
        Activity activity = (Activity) context;
        Configuration configuration = context.getResources().getConfiguration();
        WindowManager.LayoutParams attributes = window.getAttributes();
        if (activity.isInMultiWindowMode() || configuration.smallestScreenWidthDp >= 420 || configuration.orientation != 2) {
            attributes.flags &= KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN;
        } else {
            attributes.flags |= 1024;
        }
        attributes.semAddExtensionFlags(1);
        window.setAttributes(attributes);
    }

    public static boolean areDreamsAvailableToCurrentUser(Context context) {
        return context.getResources().getBoolean(R.bool.config_earcFeatureDisabled_default) && (!context.getResources().getBoolean(R.bool.config_earcFeatureDisabled_allowed) || canCurrentUserDream(context));
    }

    public static String buildSummaryString(String str, List list, int i) {
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) list;
            if (i2 >= arrayList.size() || i2 >= i) {
                break;
            }
            sb.append((String) arrayList.get(i2));
            if (i2 < Math.min(arrayList.size(), i) - 1) {
                sb.append(str);
            }
            i2++;
        }
        return sb.toString();
    }

    public static boolean canCurrentUserDream(Context context) {
        UserHandle mainUser = ((UserManager) context.getSystemService(UserManager.class)).getMainUser();
        if (mainUser == null) {
            return false;
        }
        return ((UserManager) context.createContextAsUser(mainUser, 0).getSystemService(UserManager.class)).isUserForeground();
    }

    public static int checkUserOwnsFrpCredential(Context context, int i) {
        if (LockPatternUtils.userOwnsFrpCredential(context, ((UserManager) context.getSystemService(UserManager.class)).getUserInfo(UserHandle.myUserId()))) {
            return i;
        }
        throw new SecurityException("Current user id " + UserHandle.myUserId() + " does not own frp credential.");
    }

    public static SpannableString createAccessibleSequence(String str, CharSequence charSequence) {
        SpannableString spannableString = new SpannableString(charSequence);
        spannableString.setSpan(new TtsSpan.TextBuilder(str).build(), 0, charSequence.length(), 18);
        return spannableString;
    }

    public static Bitmap createBitmap(Drawable drawable, int i, int i2) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    public static IconCompat createIconWithDrawable(Drawable drawable) {
        Bitmap createBitmap;
        if (drawable instanceof BitmapDrawable) {
            createBitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicWidth <= 0) {
                intrinsicWidth = 1;
            }
            if (intrinsicHeight <= 0) {
                intrinsicHeight = 1;
            }
            createBitmap = createBitmap(drawable, intrinsicWidth, intrinsicHeight);
        }
        return IconCompat.createWithBitmap(createBitmap);
    }

    public static Locale createLocaleFromString(String str) {
        if (str == null) {
            return Locale.getDefault();
        }
        String[] split = str.split("_", 3);
        return 1 == split.length ? new Locale(split[0]) : 2 == split.length ? new Locale(split[0], split[1]) : new Locale(split[0], split[1], split[2]);
    }

    public static Context createPackageContextAsUser(Context context, int i) {
        try {
            return context.createPackageContextAsUser(context.getPackageName(), 0, UserHandle.of(i));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Settings", "Failed to create user context", e);
            return null;
        }
    }

    public static void disableComponent(Context context, ComponentName componentName) {
        try {
            context.getPackageManager().setComponentEnabledSetting(componentName, 2, 1);
        } catch (Exception unused) {
            Log.e("Settings", "Component not found, not disabling it: " + componentName.toShortString());
        }
    }

    public static void disableComponentsToHideSettings(Context context, PackageManager packageManager) {
        packageManager.setComponentEnabledSetting(new ComponentName(context, (Class<?>) Settings.class), 2, 1);
        packageManager.setComponentEnabledSetting(new ComponentName(context, (Class<?>) Settings.CreateShortcutActivity.class), 2, 1);
    }

    public static void enforceSameOwner(Context context, int i) {
        if (ArrayUtils.contains(((UserManager) context.getSystemService(UserManager.class)).getProfileIdsWithDisabled(UserHandle.myUserId()), i) || KnoxUtils.isDualDarDoInnerAuthUser(context, i)) {
            return;
        }
        StringBuilder m = ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Given user id ", " does not belong to user ");
        m.append(UserHandle.myUserId());
        throw new SecurityException(m.toString());
    }

    public static String formatDateRange(Context context, long j, long j2) {
        String formatter;
        StringBuilder sb = sBuilder;
        synchronized (sb) {
            sb.setLength(0);
            formatter = DateUtils.formatDateRange(context, sFormatter, j, j2, 65552, null).toString();
        }
        return formatter;
    }

    public static ApplicationInfo getAdminApplicationInfo(Context context, int i) {
        ComponentName profileOwnerAsUser = ((DevicePolicyManager) context.getSystemService("device_policy")).getProfileOwnerAsUser(i);
        if (profileOwnerAsUser == null) {
            return null;
        }
        String packageName = profileOwnerAsUser.getPackageName();
        try {
            return AppGlobals.getPackageManager().getApplicationInfo(packageName, 0L, i);
        } catch (RemoteException e) {
            Log.e("Settings", "Error while retrieving application info for package " + packageName + ", userId " + i, e);
            return null;
        }
    }

    public static CharSequence getApplicationLabel(Context context, String str) {
        try {
            return context.getPackageManager().getApplicationInfo(str, 4194816).loadLabel(context.getPackageManager());
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("Settings", "Unable to find info for package: " + str);
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0015, code lost:
    
        if (3 < r3) goto L8;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v13, types: [int] */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v18 */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v2, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r5v1, types: [java.lang.StringBuilder] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getCLFLockState() {
        /*
            java.lang.String r0 = "Settings"
            java.lang.String r1 = "result : "
            r2 = 256(0x100, float:3.59E-43)
            r3 = 0
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3b java.io.FileNotFoundException -> L4b
            java.lang.String r5 = "/efs/sec_efs/FeliCaLock/01"
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3b java.io.FileNotFoundException -> L4b
            int r3 = r4.read()     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L34 java.io.FileNotFoundException -> L37
            if (r3 < 0) goto L17
            r5 = 3
            if (r5 >= r3) goto L18
        L17:
            r3 = r2
        L18:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L34 java.io.FileNotFoundException -> L36
            r5.<init>(r1)     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L34 java.io.FileNotFoundException -> L36
            r5.append(r3)     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L34 java.io.FileNotFoundException -> L36
            java.lang.String r1 = r5.toString()     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L34 java.io.FileNotFoundException -> L36
            android.util.Log.e(r0, r1)     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L34 java.io.FileNotFoundException -> L36
            r4.close()     // Catch: java.io.IOException -> L2b
            goto L2f
        L2b:
            r0 = move-exception
            r0.printStackTrace()
        L2f:
            r2 = r3
            goto L55
        L31:
            r0 = move-exception
            r3 = r4
            goto L56
        L34:
            r3 = r4
            goto L3b
        L36:
            r2 = r3
        L37:
            r3 = r4
            goto L4b
        L39:
            r0 = move-exception
            goto L56
        L3b:
            java.lang.String r1 = "IOException!"
            android.util.Log.e(r0, r1)     // Catch: java.lang.Throwable -> L39
            if (r3 == 0) goto L55
            r3.close()     // Catch: java.io.IOException -> L46
            goto L55
        L46:
            r0 = move-exception
            r0.printStackTrace()
            goto L55
        L4b:
            java.lang.String r1 = "FileNotFoundException!"
            android.util.Log.e(r0, r1)     // Catch: java.lang.Throwable -> L39
            if (r3 == 0) goto L55
            r3.close()     // Catch: java.io.IOException -> L46
        L55:
            return r2
        L56:
            if (r3 == 0) goto L60
            r3.close()     // Catch: java.io.IOException -> L5c
            goto L60
        L5c:
            r1 = move-exception
            r1.printStackTrace()
        L60:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settings.Utils.getCLFLockState():int");
    }

    public static int getCloneUserId(Context context) {
        UserManager userManager = (UserManager) context.getSystemService("user");
        for (UserHandle userHandle : userManager.getUserProfiles()) {
            if (userManager.getUserInfo(userHandle.getIdentifier()).isCloneProfile()) {
                return userHandle.getIdentifier();
            }
        }
        return -1;
    }

    public static String getConfirmCredentialStringForUser(Context context, int i, int i2) {
        if (UserManager.get(context).isManagedProfile(UserManager.get(context).getCredentialOwnerProfile(i))) {
            return null;
        }
        if (i2 == 1) {
            return context.getString(R.string.lockpattern_need_to_unlock);
        }
        if (i2 == 3) {
            return context.getString(R.string.sec_confirm_lock_pin_your_pin_header);
        }
        if (i2 != 4) {
            return null;
        }
        return context.getString(R.string.sec_confirm_lock_password_your_password_generic);
    }

    public static Intent getContactUsIntent(Context context) {
        String packageName = context.getPackageName();
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("voc://view/contactUs"));
        intent.putExtra("packageName", packageName);
        intent.putExtra("appId", "be4f156x1l");
        intent.putExtra("appName", "Settings");
        return intent;
    }

    public static float getContentFrameWidthRatio(Context context) {
        if (context == null) {
            return 1.0f;
        }
        int i = context.getResources().getConfiguration().screenWidthDp;
        int i2 = context.getResources().getConfiguration().screenHeightDp;
        try {
            StringBuilder sb = new StringBuilder("getContentFrameWidthRatio screenWidthDp : ");
            sb.append(i);
            sb.append(" , screenHeightDp : ");
            sb.append(i2);
            sb.append(" , LightTheme : ");
            boolean z = false;
            try {
                TypedValue typedValue = new TypedValue();
                context.getTheme().resolveAttribute(R.attr.isLightTheme, typedValue, true);
                if (typedValue.data != 0) {
                    z = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            sb.append(z);
            sb.append(" , Context : ");
            sb.append(context.toString());
            sb.append(" \ndensityDpi : ");
            sb.append(context.getResources().getConfiguration().densityDpi);
            sb.append(" , smallestScreenWidthDp : ");
            sb.append(context.getResources().getConfiguration().smallestScreenWidthDp);
            sb.append(" , semDesktopModeEnabled : ");
            sb.append(context.getResources().getConfiguration().semDesktopModeEnabled);
            Log.i("Settings", sb.toString());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return getContentFrameWidthRatio(i, i2);
    }

    public static int getCurrentUserIdOfType(UserManager userManager, int i) {
        if (i == 1) {
            return UserHandle.myUserId();
        }
        UserHandle profileOfType = getProfileOfType(userManager, i);
        if (profileOfType != null) {
            return profileOfType.getIdentifier();
        }
        throw new IllegalStateException("User ID of requested profile type is not available.");
    }

    public static int getEnterprisePolicyEnabled(Context context, String str, String str2) {
        Cursor query = context.getContentResolver().query(Uri.parse(str), null, str2, null, null);
        if (query == null) {
            return -1;
        }
        try {
            query.moveToFirst();
            if (query.getString(query.getColumnIndex(str2)).equals("true")) {
                query.close();
                return 1;
            }
            query.close();
            return 0;
        } catch (Exception unused) {
            query.close();
            return -1;
        } catch (Throwable th) {
            query.close();
            throw th;
        }
    }

    public static int getEnterprisePolicyEnabledInt(Context context, String str, String[] strArr) {
        int i = str.equals("getPasswordLockDelay") ? -1 : 0;
        Cursor query = context.getContentResolver().query(Uri.parse("content://com.sec.knox.provider/PasswordPolicy2"), null, str, strArr, null);
        if (query != null) {
            try {
                query.moveToFirst();
                i = query.getInt(query.getColumnIndex(str));
            } catch (Exception unused) {
            } catch (Throwable th) {
                query.close();
                throw th;
            }
            query.close();
        }
        return i;
    }

    public static String getEnterprisePolicyStringValue(Context context) {
        Cursor query = context.getContentResolver().query(Uri.parse("content://com.sec.knox.provider2/KioskMode"), null, "getKioskHomePackage", null, null);
        if (query == null) {
            return null;
        }
        try {
            query.moveToFirst();
            return query.getString(query.getColumnIndex("getKioskHomePackage"));
        } catch (Exception unused) {
            return null;
        } finally {
            query.close();
        }
    }

    public static UserInfo getExistingUser(UserHandle userHandle, UserManager userManager) {
        List<UserInfo> aliveUsers = userManager.getAliveUsers();
        int identifier = userHandle.getIdentifier();
        for (UserInfo userInfo : aliveUsers) {
            if (userInfo.id == identifier) {
                return userInfo;
            }
        }
        return null;
    }

    public static FaceManager getFaceManagerOrNull(Context context) {
        if (context.getPackageManager().hasSystemFeature("android.hardware.biometrics.face")) {
            return (FaceManager) context.getSystemService("face");
        }
        return null;
    }

    public static FingerprintManager getFingerprintManagerOrNull(Context context) {
        if (context.getPackageManager().hasSystemFeature("android.hardware.fingerprint")) {
            return (FingerprintManager) context.getSystemService("fingerprint");
        }
        return null;
    }

    public static Set getHandledDomains(PackageManager packageManager, String str) {
        List intentFilterVerifications = packageManager.getIntentFilterVerifications(str);
        List<IntentFilter> allIntentFilters = packageManager.getAllIntentFilters(str);
        ArraySet arraySet = new ArraySet();
        if (intentFilterVerifications != null && intentFilterVerifications.size() > 0) {
            Iterator it = intentFilterVerifications.iterator();
            while (it.hasNext()) {
                arraySet.addAll(((IntentFilterVerificationInfo) it.next()).getDomains());
            }
        }
        if (allIntentFilters != null && allIntentFilters.size() > 0) {
            for (IntentFilter intentFilter : allIntentFilters) {
                if (intentFilter.hasCategory("android.intent.category.BROWSABLE") && (intentFilter.hasDataScheme("http") || intentFilter.hasDataScheme("https"))) {
                    arraySet.addAll(intentFilter.getHostsList());
                }
            }
        }
        return arraySet;
    }

    public static String getKeywordForSearch(Context context, int i) {
        Locale locale = new Locale("en");
        try {
            Configuration configuration = new Configuration(context.getResources().getConfiguration());
            configuration.setLocale(locale);
            return context.createConfigurationContext(configuration).getString(i);
        } catch (Resources.NotFoundException e) {
            Log.e("Settings", "(Res error)Failed to translate : " + e.toString());
            return ApnSettings.MVNO_NONE;
        } catch (ClassCastException e2) {
            Log.e("Settings", "(Class error)Failed to translate : " + e2.toString());
            return ApnSettings.MVNO_NONE;
        }
    }

    public static int getListHorizontalPadding(Context context) {
        float f;
        WindowManager windowManager = (WindowManager) context.getSystemService(WindowManager.class);
        if (windowManager != null) {
            Rect rect = new Rect(windowManager.getCurrentWindowMetrics().getBounds());
            float width = rect.width() / context.getResources().getDisplayMetrics().density;
            f = (width < 589.0f || width > 959.0f || ((float) rect.height()) / context.getResources().getDisplayMetrics().density < 411.0f) ? width >= 960.0f ? (width - 840.0f) / 2.0f : 10.0f : width * 0.07f;
        } else {
            f = 0.0f;
        }
        return (int) TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
    }

    public static UserHandle getManagedProfile(UserManager userManager) {
        for (UserHandle userHandle : userManager.getUserProfiles()) {
            UserInfo userInfo = userManager.getUserInfo(userHandle.getIdentifier());
            Log.d("Settings", "getManagedProfile: " + userInfo);
            if (userInfo.isManagedProfile()) {
                return userHandle;
            }
        }
        return null;
    }

    public static int getManagedProfileId(UserManager userManager, int i) {
        UserHandle managedProfileWithDisabled = getManagedProfileWithDisabled(userManager, i);
        if (managedProfileWithDisabled != null) {
            return managedProfileWithDisabled.getIdentifier();
        }
        return -10000;
    }

    public static UserHandle getManagedProfileWithDisabled(UserManager userManager, int i) {
        List profiles = userManager.getProfiles(i);
        int size = profiles.size();
        for (int i2 = 0; i2 < size; i2++) {
            UserInfo userInfo = (UserInfo) profiles.get(i2);
            if (userInfo.isManagedProfile() && userInfo.getUserHandle().getIdentifier() != i && !SemDualAppManager.isDualAppId(userInfo.getUserHandle().getIdentifier()) && !SemPersonaManager.isDoEnabled(i) && !SemPersonaManager.isSecureFolderId(userInfo.id)) {
                return userInfo.getUserHandle();
            }
        }
        return null;
    }

    public static List getManagedProfiles(UserManager userManager) {
        ArrayList arrayList = new ArrayList();
        List<UserHandle> userProfiles = userManager.getUserProfiles();
        int size = userProfiles.size();
        for (int i = 0; i < size; i++) {
            UserHandle userHandle = userProfiles.get(i);
            if (userHandle.getIdentifier() != userManager.getUserHandle()) {
                UserInfo userInfo = userManager.getUserInfo(userHandle.getIdentifier());
                if (userInfo.isManagedProfile() && !userInfo.isDualAppProfile() && !userInfo.isSecureFolder()) {
                    arrayList.add(userHandle);
                }
            }
        }
        return arrayList;
    }

    public static OnlineHelpMenuState getOnlineHelpMenuState(Context context) {
        OnlineHelpMenuState onlineHelpMenuState = mHelpMenuData;
        if (onlineHelpMenuState == null) {
            if (onlineHelpMenuState == null) {
                onlineHelpMenuState = new OnlineHelpMenuState();
                onlineHelpMenuState.removeTile = false;
                onlineHelpMenuState.intent = null;
                onlineHelpMenuState.titleRes = 0;
                onlineHelpMenuState.summaryRes = 0;
                onlineHelpMenuState.iconRes = 0;
            }
            Log.d("Settings", "CscFeature_Setting_ConfigTypeHelp: " + SemCscFeature.getInstance().getInteger("CscFeature_Setting_ConfigTypeHelp") + "[0:dont support, 1:online, 2:ondevice]");
            if (SemCscFeature.getInstance().getInteger("CscFeature_Setting_ConfigTypeHelp") == 2) {
                onlineHelpMenuState.titleRes = R.string.help_title;
                onlineHelpMenuState.summaryRes = R.string.help_title;
                onlineHelpMenuState.iconRes = R.drawable.sec_ic_settings_user_manual;
                if (isSupportHelpMenu(context)) {
                    Log.d("Settings", "isSupportOfflineHelpMenu");
                    if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_ACCESSIBILITY_SUPPORT_MANAGE_ACCESSIBILITY")) {
                        Log.d("Settings", "non mass");
                        onlineHelpMenuState.intent = new Intent("com.samsung.helphub.HELP");
                    } else {
                        Log.d("Settings", "mass");
                        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                        String salesCode = getSalesCode();
                        if ("VZW".equals(salesCode)) {
                            Log.d("Settings", "is mass. " + salesCode);
                            onlineHelpMenuState.intent = new Intent("com.samsung.helphub.HELP");
                        } else {
                            DialogFragment$$ExternalSyntheticOutline0.m("remove online help - is mass ", salesCode, "Settings");
                            onlineHelpMenuState.removeTile = true;
                        }
                    }
                } else {
                    String str2 = SystemProperties.get("persist.sys.omc_etcpath");
                    File file = new File(str2 != null ? str2.concat("/usermanual.pdf") : "/system/etc/usermanual.pdf");
                    if (!file.exists() || file.length() == 0) {
                        Log.i("Settings", "Manual file does not exist");
                        Log.d("Settings", "remove help - Nothing is supported.");
                        onlineHelpMenuState.removeTile = true;
                    } else {
                        Log.d("Settings", "Manual PDF is supported for this model");
                        File file2 = new File(context.getCacheDir().getPath() + "/usermanual.pdf");
                        if (!file2.exists() || file2.length() == 0) {
                            Log.d("Settings", "copy PDF file to files folder");
                            try {
                                FileInputStream fileInputStream = new FileInputStream(file);
                                try {
                                    FileOutputStream fileOutputStream = new FileOutputStream(file2);
                                    try {
                                        byte[] bArr = new byte[1024];
                                        while (true) {
                                            int read = fileInputStream.read(bArr);
                                            if (read <= 0) {
                                                break;
                                            }
                                            fileOutputStream.write(bArr, 0, read);
                                        }
                                    } finally {
                                        fileOutputStream.close();
                                    }
                                } finally {
                                    fileInputStream.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        onlineHelpMenuState.titleRes = R.string.user_manual;
                        onlineHelpMenuState.summaryRes = R.string.user_manual;
                        onlineHelpMenuState.iconRes = R.drawable.sec_ic_settings_user_manual;
                        Intent intent = new Intent("android.intent.action.VIEW");
                        Uri uriForFile = FileProvider.getUriForFile(context, file2, "com.android.settings.files");
                        intent.setDataAndType(uriForFile, ApplicationPolicy.DEFAULT_TYPE_PDF);
                        if ("content".equals(uriForFile.getScheme())) {
                            intent.addFlags(1);
                        }
                        intent.putExtra("android.intent.extra.TITLE", context.getResources().getString(onlineHelpMenuState.titleRes));
                        intent.addCategory("android.intent.category.DEFAULT");
                        onlineHelpMenuState.intent = intent;
                    }
                }
            } else if (SemCscFeature.getInstance().getInteger("CscFeature_Setting_ConfigTypeHelp") == 0) {
                Log.d("Settings", "remove online help -  help csc feature is 0");
                onlineHelpMenuState.removeTile = true;
            } else {
                Log.d("Settings", "help csc feature is Default 1");
                onlineHelpMenuState.intent = getUserManualSearchURLIntent(context, null);
                onlineHelpMenuState.titleRes = R.string.user_manual;
                onlineHelpMenuState.summaryRes = R.string.learn_more;
                onlineHelpMenuState.iconRes = R.drawable.sec_ic_settings_user_manual;
                int i = SystemProperties.getInt(TouchPadGestureSettingsController.FIRST_API_LEVEL, 0);
                Log.d("Settings", "firApiLevel : " + i);
                if (Rune.isUSA() && i < 31) {
                    onlineHelpMenuState.titleRes = R.string.help_title;
                    onlineHelpMenuState.summaryRes = R.string.user_manual;
                    onlineHelpMenuState.iconRes = R.drawable.sec_ic_settings_help;
                }
            }
            mHelpMenuData = onlineHelpMenuState;
            Log.d("Settings", "getOnlineHelpMenuState is done");
        }
        return mHelpMenuData;
    }

    public static UserHandle getProfileOfType(UserManager userManager, int i) {
        String str;
        List<UserHandle> userProfiles = userManager.getUserProfiles();
        if (i == 2) {
            str = "android.os.usertype.profile.MANAGED";
        } else if (i == 4) {
            str = "android.os.usertype.profile.PRIVATE";
        } else {
            if (i != 1) {
                throw new IllegalArgumentException("Cannot get user type for ALL types");
            }
            str = "android.os.usertype.full.SYSTEM";
        }
        for (UserHandle userHandle : userProfiles) {
            if (userHandle.getIdentifier() != UserHandle.myUserId() && str.equals(userManager.getUserInfo(userHandle.getIdentifier()).userType)) {
                return userHandle;
            }
        }
        return null;
    }

    public static Drawable getSafeIcon(Drawable drawable) {
        if (drawable == null || (drawable instanceof VectorDrawable)) {
            return drawable;
        }
        int minimumWidth = drawable.getMinimumWidth();
        int minimumHeight = drawable.getMinimumHeight();
        if (minimumWidth <= 600 && minimumHeight <= 600) {
            return drawable;
        }
        float f = 600;
        float f2 = minimumWidth;
        float f3 = minimumHeight;
        float min = Math.min(f / f2, f / f3);
        int i = (int) (f2 * min);
        int i2 = (int) (f3 * min);
        return new BitmapDrawable((Resources) null, drawable instanceof BitmapDrawable ? Bitmap.createScaledBitmap(((BitmapDrawable) drawable).getBitmap(), i, i2, false) : createBitmap(drawable, i, i2));
    }

    public static String getSalesCode() {
        try {
            String str = SystemProperties.get("persist.omc.sales_code");
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            String str2 = SystemProperties.get("ro.csc.sales_code");
            return TextUtils.isEmpty(str2) ? SystemProperties.get("ril.sales_code") : str2;
        } catch (Exception unused) {
            return ApnSettings.MVNO_NONE;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0088 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0064 A[Catch: RemoteException -> 0x0031, TryCatch #0 {RemoteException -> 0x0031, blocks: (B:3:0x000d, B:5:0x002a, B:13:0x0052, B:16:0x005a, B:22:0x007a, B:25:0x0082, B:30:0x0064, B:33:0x006e, B:35:0x0074, B:36:0x003d, B:39:0x0046, B:41:0x004c), top: B:2:0x000d }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.os.UserHandle getSecureTargetUser(android.os.IBinder r6, android.os.UserManager r7, android.os.Bundle r8, android.os.Bundle r9) {
        /*
            android.os.UserHandle r0 = new android.os.UserHandle
            int r1 = android.os.UserHandle.myUserId()
            r0.<init>(r1)
            android.app.IActivityManager r1 = android.app.ActivityManager.getService()
            java.lang.String r2 = r1.getLaunchedFromPackage(r6)     // Catch: android.os.RemoteException -> L31
            java.lang.String r3 = "com.android.settings"
            boolean r2 = r3.equals(r2)     // Catch: android.os.RemoteException -> L31
            android.os.UserHandle r3 = new android.os.UserHandle     // Catch: android.os.RemoteException -> L31
            int r6 = r1.getLaunchedFromUid(r6)     // Catch: android.os.RemoteException -> L31
            int r6 = android.os.UserHandle.getUserId(r6)     // Catch: android.os.RemoteException -> L31
            r3.<init>(r6)     // Catch: android.os.RemoteException -> L31
            boolean r6 = r3.equals(r0)     // Catch: android.os.RemoteException -> L31
            if (r6 != 0) goto L33
            boolean r6 = isProfileOf(r3, r7)     // Catch: android.os.RemoteException -> L31
            if (r6 == 0) goto L33
            return r3
        L31:
            r6 = move-exception
            goto L89
        L33:
            r6 = -1
            java.lang.String r1 = "android.intent.extra.USER_ID"
            java.lang.String r3 = "android.intent.extra.USER"
            r4 = 0
            if (r9 != 0) goto L3d
        L3b:
            r5 = r4
            goto L50
        L3d:
            android.os.Parcelable r5 = r9.getParcelable(r3)     // Catch: android.os.RemoteException -> L31
            android.os.UserHandle r5 = (android.os.UserHandle) r5     // Catch: android.os.RemoteException -> L31
            if (r5 == 0) goto L46
            goto L50
        L46:
            int r9 = r9.getInt(r1, r6)     // Catch: android.os.RemoteException -> L31
            if (r9 == r6) goto L3b
            android.os.UserHandle r5 = android.os.UserHandle.of(r9)     // Catch: android.os.RemoteException -> L31
        L50:
            if (r5 == 0) goto L61
            boolean r9 = r5.equals(r0)     // Catch: android.os.RemoteException -> L31
            if (r9 != 0) goto L61
            if (r2 == 0) goto L61
            boolean r9 = isProfileOf(r5, r7)     // Catch: android.os.RemoteException -> L31
            if (r9 == 0) goto L61
            return r5
        L61:
            if (r8 != 0) goto L64
            goto L78
        L64:
            android.os.Parcelable r9 = r8.getParcelable(r3)     // Catch: android.os.RemoteException -> L31
            android.os.UserHandle r9 = (android.os.UserHandle) r9     // Catch: android.os.RemoteException -> L31
            if (r9 == 0) goto L6e
            r4 = r9
            goto L78
        L6e:
            int r8 = r8.getInt(r1, r6)     // Catch: android.os.RemoteException -> L31
            if (r8 == r6) goto L78
            android.os.UserHandle r4 = android.os.UserHandle.of(r8)     // Catch: android.os.RemoteException -> L31
        L78:
            if (r4 == 0) goto L90
            boolean r6 = r4.equals(r0)     // Catch: android.os.RemoteException -> L31
            if (r6 != 0) goto L90
            if (r2 == 0) goto L90
            boolean r6 = isProfileOf(r4, r7)     // Catch: android.os.RemoteException -> L31
            if (r6 == 0) goto L90
            return r4
        L89:
            java.lang.String r7 = "Settings"
            java.lang.String r8 = "Could not talk to activity manager."
            android.util.Log.v(r7, r8, r6)
        L90:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settings.Utils.getSecureTargetUser(android.os.IBinder, android.os.UserManager, android.os.Bundle, android.os.Bundle):android.os.UserHandle");
    }

    public static String getStringFromDeXSettings(ContentResolver contentResolver, String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString(GoodSettingsContract.EXTRA_NAME.POLICY_KEY, str);
        bundle.putString("def", str2);
        try {
            Bundle call = contentResolver.call(DEX_SETTINGS_URI, "getSettings", (String) null, bundle);
            if (call != null) {
                return call.getString(str);
            }
        } catch (IllegalArgumentException unused) {
            Log.e("Settings", "IllegalArgumentException :: getDeXSettings ".concat(str));
        }
        return str2;
    }

    public static Fragment getTargetFragment(Activity activity, String str, Bundle bundle) {
        boolean z = bundle != null && bundle.getInt(ImsProfile.SERVICE_PROFILE) == 1;
        boolean z2 = bundle != null && bundle.getInt(ImsProfile.SERVICE_PROFILE) == 2;
        try {
            if (isManagedProfilePresent(activity)) {
                Map map = ProfileFragmentBridge.FRAGMENT_MAP;
                if (((ArrayMap) map).get(str) != null && !z2 && !z && !SemPersonaManager.isSecureFolderId(UserHandle.myUserId()) && !SemPersonaManager.isAppSeparationUserId(UserHandle.myUserId())) {
                    return Fragment.instantiate(activity, (String) ((ArrayMap) map).get(str), bundle);
                }
            }
            return Fragment.instantiate(activity, str, bundle);
        } catch (Exception e) {
            Log.e("Settings", "Unable to get target fragment", e);
            return null;
        }
    }

    public static int getTipAndUserManualTitleRes(Context context) {
        return getOnlineHelpMenuState(context).removeTile ? R.string.sec_tips_title : (!Rune.isUSA() || SystemProperties.getInt(TouchPadGestureSettingsController.FIRST_API_LEVEL, 0) >= 31) ? R.string.sec_tips_and_user_manual_title : R.string.sec_tips_and_help_title;
    }

    public static String getTopLevelSummarySeparator(Context context) {
        List asList = Arrays.asList("ja");
        String language = context.getResources().getConfiguration().getLocales().get(0).getLanguage();
        Stream stream = asList.stream();
        Objects.requireNonNull(language);
        return stream.anyMatch(new Utils$$ExternalSyntheticLambda4(language)) ? context.getString(R.string.comma) : "  •  ";
    }

    public static int getUserIdFromBundle(Context context, Bundle bundle, boolean z) {
        if (bundle == null) {
            return ((UserManager) context.getSystemService(UserManager.class)).getCredentialOwnerProfile(UserHandle.myUserId());
        }
        boolean z2 = false;
        if (z && bundle.getBoolean("allow_any_user", false)) {
            z2 = true;
        }
        int i = bundle.getInt("android.intent.extra.USER_ID", UserHandle.myUserId());
        if (i == -9999) {
            return z2 ? i : checkUserOwnsFrpCredential(context, i);
        }
        if (i == -9998) {
            if (LockPatternUtils.isRepairModeActive(context)) {
                return i;
            }
            throw new SecurityException("Repair mode is not active on the device.");
        }
        if (!z2) {
            enforceSameOwner(context, i);
        }
        return i;
    }

    public static Intent getUserManualSearchURLIntent(Context context, String str) {
        String str2;
        Uri parse;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addFlags(268435456);
        ComponentName componentName = new ComponentName("com.sec.android.app.sbrowser", "com.sec.android.app.sbrowser.SBrowserMainActivity");
        if (isPackageEnabled(context, "com.sec.android.app.sbrowser")) {
            intent.setComponent(componentName);
            Log.d("Settings", "s browser is enabled on device.");
        }
        try {
            str2 = Settings.Global.getString(context.getContentResolver(), "online_manual_url");
        } catch (Exception unused) {
            Log.i("Settings", "no online_manual_url value");
            str2 = null;
        }
        if (str2 == null || str2.length() < 1) {
            str2 = "http://www.samsung.com/m-manual/common";
        }
        String trim = str2.trim();
        if (trim.charAt(trim.length() - 1) == '/') {
            trim = trim.substring(0, trim.length() - 1);
        }
        String str3 = trim + '/' + SystemProperties.get("ro.product.model") + "/Android" + SystemProperties.get("ro.build.version.release");
        if (str == null || str.length() <= 0) {
            Log.i("Settings", "No keyword - launch usermanual main");
            parse = Uri.parse(str3);
        } else {
            Log.d("Settings", "Search values : ".concat(str));
            try {
                parse = Uri.parse(str3 + "?appid=app&anchor=" + URLEncoder.encode(str, "UTF-8") + "&pos=find");
            } catch (UnsupportedEncodingException unused2) {
                Log.i("Settings", "UnsupportedEncoding for keyword targetUri. using default manualURL");
                parse = Uri.parse(str3);
            }
        }
        Log.d("Settings", "Uri: " + parse.toString());
        intent.setData(parse);
        return intent;
    }

    public static boolean hasFaceHardware(Context context) {
        FaceManager faceManagerOrNull = getFaceManagerOrNull(context);
        return faceManagerOrNull != null && faceManagerOrNull.isHardwareDetected();
    }

    public static boolean hasFingerprintHardware(Context context) {
        FingerprintManager fingerprintManagerOrNull = getFingerprintManagerOrNull(context);
        return fingerprintManagerOrNull != null && fingerprintManagerOrNull.isHardwareDetected();
    }

    public static boolean hasPackage(Context context, String str) {
        if (context == null) {
            return false;
        }
        try {
            context.getPackageManager().getApplicationInfo(str, 128);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            DialogFragment$$ExternalSyntheticOutline0.m("Package not found : ", str, "Settings");
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x003f, code lost:
    
        if (r0.equals(r1) != false) goto L27;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean hasPlatformKey(android.content.Context r7, java.lang.String r8) {
        /*
            android.content.pm.PackageManager r7 = r7.getPackageManager()
            android.content.pm.Signature[] r0 = com.android.settings.Utils.sSystemSignature
            r1 = 0
            r2 = 64
            r3 = 1
            r4 = 0
            if (r0 != 0) goto L26
            android.content.pm.Signature[] r0 = new android.content.pm.Signature[r3]
            java.lang.String r5 = "android"
            android.content.pm.PackageInfo r5 = r7.getPackageInfo(r5, r2)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L21
            if (r5 == 0) goto L21
            android.content.pm.Signature[] r5 = r5.signatures     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L21
            if (r5 == 0) goto L21
            int r6 = r5.length     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L21
            if (r6 <= 0) goto L21
            r5 = r5[r4]     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L21
            goto L22
        L21:
            r5 = r1
        L22:
            r0[r4] = r5
            com.android.settings.Utils.sSystemSignature = r0
        L26:
            android.content.pm.PackageInfo r7 = r7.getPackageInfo(r8, r2)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L44
            android.content.pm.Signature[] r0 = com.android.settings.Utils.sSystemSignature     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L44
            r0 = r0[r4]     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L44
            if (r0 == 0) goto L42
            if (r7 == 0) goto L3b
            android.content.pm.Signature[] r7 = r7.signatures     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L44
            if (r7 == 0) goto L3b
            int r2 = r7.length     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L44
            if (r2 <= 0) goto L3b
            r1 = r7[r4]     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L44
        L3b:
            boolean r7 = r0.equals(r1)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L44
            if (r7 == 0) goto L42
            goto L43
        L42:
            r3 = r4
        L43:
            r4 = r3
        L44:
            if (r4 != 0) goto L5c
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r8)
            java.lang.String r8 = " is not signed with platform key."
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            java.lang.String r8 = "Settings"
            android.util.Log.i(r8, r7)
        L5c:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settings.Utils.hasPlatformKey(android.content.Context, java.lang.String):boolean");
    }

    public static void initMHSFeature(Context context) {
        SemWifiManager semWifiManager = (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        if (semWifiManager == null) {
            Log.e("Settings", "Can't get SemWifiManager.");
            return;
        }
        MAX_CLIENT_4_MOBILEAP = semWifiManager.getWifiApMaxClient();
        SUPPORT_MOBILEAP_5G_BASED_ON_COUNTRY = semWifiManager.supportWifiAp5GBasedOnCountry();
        SUPPORT_MOBILEAP_6G_BASED_ON_COUNTRY = semWifiManager.supportWifiAp6GBasedOnCountry();
        SUPPORT_MOBILEAP_REGION = null;
        SUPPORT_MOBILEAP_WIFISHARING = semWifiManager.isWifiSharingSupported();
        SUPPORT_MOBILEAP_WIFISHARINGLITE = semWifiManager.isWifiSharingLiteSupported();
        if (MHSDBG) {
            String str = SystemProperties.get("mhs.customer");
            Log.w("Settings", " mhs.carrier:[" + str + "]");
            if (str != null && !str.isEmpty()) {
                CONFIGOPBRANDINGFORMOBILEAP = str;
            }
        }
        Log.i("Settings", "initMHSFeature MAX_CLIENT_4_MOBILEAP :" + MAX_CLIENT_4_MOBILEAP);
        Log.i("Settings", "initMHSFeature SUPPORT_MOBILEAP_5G_BASED_ON_COUNTRY :" + SUPPORT_MOBILEAP_5G_BASED_ON_COUNTRY);
        Log.i("Settings", "initMHSFeature SUPPORT_MOBILEAP_6G_BASED_ON_COUNTRY :" + SUPPORT_MOBILEAP_6G_BASED_ON_COUNTRY);
        Log.i("Settings", "initMHSFeature SPF_SupportMobileApDualAp :" + SPF_SupportMobileApDualAp);
        Log.i("Settings", "initMHSFeature SUPPORT_MOBILEAP_REGION :" + SUPPORT_MOBILEAP_REGION);
        Log.i("Settings", "initMHSFeature SUPPORT_MOBILEAP_WIFISHARING :" + SUPPORT_MOBILEAP_WIFISHARING);
        Log.i("Settings", "initMHSFeature SUPPORT_MOBILEAP_WIFISHARINGLITE :" + SUPPORT_MOBILEAP_WIFISHARINGLITE);
        Utils$$ExternalSyntheticOutline0.m(new StringBuilder("initMHSFeature CONFIGOPBRANDINGFORMOBILEAP :"), CONFIGOPBRANDINGFORMOBILEAP, "Settings");
    }

    public static boolean isAfwProfile(Context context) {
        if (((DevicePolicyManager) context.getSystemService("device_policy")).getDeviceOwner() != null) {
            return true;
        }
        for (UserInfo userInfo : ((UserManager) context.getSystemService("user")).getProfiles(UserHandle.myUserId())) {
            if (userInfo.isManagedProfile() && !userInfo.isSecureFolder()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCMCAvailable(Context context) {
        return hasPackage(context, "com.samsung.android.mdecservice") && UserHandle.myUserId() == 0;
    }

    public static boolean isCurrentThemeSupportNightTheme(Context context) {
        return !(TextUtils.isEmpty(Settings.System.getString(context.getContentResolver(), "current_sec_active_themepackage")) ^ true) || Settings.System.getInt(context.getContentResolver(), "current_theme_support_night_mode", 0) == 1;
    }

    public static boolean isDemoUser(Context context) {
        return UserManager.isDeviceInDemoMode(context) && ((UserManager) context.getSystemService(UserManager.class)).isDemoUser();
    }

    public static boolean isDesktopDualMode(Context context) {
        SemDesktopModeManager semDesktopModeManager = (SemDesktopModeManager) context.getSystemService("desktopmode");
        if (semDesktopModeManager != null) {
            return semDesktopModeManager.getDesktopModeState().getDisplayType() == 102;
        }
        Log.d("Utils", "isDesktopDualMode : desktopModeManager is null");
        return false;
    }

    public static boolean isDesktopModeEnabled(Context context) {
        SemDesktopModeManager semDesktopModeManager = (SemDesktopModeManager) context.getSystemService("desktopmode");
        return semDesktopModeManager != null && semDesktopModeManager.getDesktopModeState().enabled == 4;
    }

    public static boolean isDesktopStandaloneMode(Context context) {
        SemDesktopModeManager semDesktopModeManager = (SemDesktopModeManager) context.getSystemService("desktopmode");
        if (semDesktopModeManager != null) {
            return semDesktopModeManager.getDesktopModeState().getDisplayType() == 101;
        }
        Log.d("Settings", "isDesktopStandaloneMode : desktopModeManager is null");
        return false;
    }

    public static boolean isDeviceProvisioned(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) != 0;
    }

    public static boolean isEasyModeEnabled(Context context) {
        boolean z = Rune.supportDesktopMode() && Rune.isSamsungDexMode(context);
        boolean z2 = Settings.System.getInt(context.getContentResolver(), "minimal_battery_use", 0) == 1;
        if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_EASY_MODE") && UserHandle.myUserId() == 0 && !z && !isGameModeEnabled(context) && !z2) {
            return true;
        }
        Log.d("Settings", "easy mode is not displayed.(" + SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_EASY_MODE") + "," + isGuestMode(context) + "," + z + "," + isGameModeEnabled(context) + "," + z2 + ")");
        return false;
    }

    public static boolean isExcludedManagedProfile(UserInfo userInfo) {
        return userInfo.isSecureFolder() || userInfo.isDualAppProfile() || userInfo.isUserTypeAppSeparation();
    }

    public static boolean isFaceNotConvenienceBiometric(Context context) {
        FaceManager faceManagerOrNull = getFaceManagerOrNull(context);
        if (faceManagerOrNull == null) {
            return false;
        }
        List sensorPropertiesInternal = faceManagerOrNull.getSensorPropertiesInternal();
        return (sensorPropertiesInternal.isEmpty() || ((FaceSensorPropertiesInternal) sensorPropertiesInternal.get(0)).sensorStrength == 0) ? false : true;
    }

    public static boolean isFrpChallengeRequired(Context context) {
        PersistentDataBlockManager persistentDataBlockManager = (PersistentDataBlockManager) context.getSystemService("persistent_data_block");
        if (persistentDataBlockManager != null) {
            return persistentDataBlockManager.isFactoryResetProtectionActive();
        }
        return false;
    }

    public static boolean isGameModeEnabled(Context context) {
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        boolean z = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_HIGH_PERFORMANCE_MODE");
        boolean z2 = !SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_DYN_RESOLUTION_CONTROL").isEmpty();
        Utils$$ExternalSyntheticOutline0.m653m("supportMultiResolution:", z2, " productFeature:true floating:", z2, "Rune");
        boolean z3 = z && z2;
        ActionBarContextView$$ExternalSyntheticOutline0.m(Utils$$ExternalSyntheticOutline0.m("isSupportBoostMode: ", z3, " // highPerfFeature:", z, " resolutionFeature:"), z2, "Rune");
        boolean z4 = z3 && Settings.Secure.getInt(context.getContentResolver(), "sem_perfomance_mode", 0) == 1;
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m("isGameModeEnabled: ", "Settings", z4);
        return z4;
    }

    public static boolean isGuestMode(Context context) {
        return ((UserManager) context.getSystemService("user")).getUserInfo(UserHandle.myUserId()).isRestricted();
    }

    public static boolean isGuestUser(Context context) {
        UserInfo userInfo;
        return (UserHandle.myUserId() == 0 || (userInfo = UserManager.get(context).getUserInfo(UserHandle.myUserId())) == null || !userInfo.isGuest()) ? false : true;
    }

    public static boolean isIntentAvailable(Context context, String str) {
        List<ResolveInfo> queryIntentActivities;
        PackageManager packageManager = context.getPackageManager();
        return (packageManager == null || (queryIntentActivities = packageManager.queryIntentActivities(new Intent(str), 65536)) == null || queryIntentActivities.size() <= 0) ? false : true;
    }

    public static boolean isLaunchModeSingleInstance(Context context, Intent intent) {
        List<ResolveInfo> queryIntentActivities;
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null || (queryIntentActivities = packageManager.queryIntentActivities(intent, 65536)) == null || queryIntentActivities.size() != 1 || queryIntentActivities.get(0).activityInfo.launchMode != 3) {
            return false;
        }
        Log.d("Settings", "launchMode is singleInstance : " + queryIntentActivities.get(0).activityInfo.getComponentName());
        return true;
    }

    public static boolean isManagedProfilePresent(Context context) {
        for (UserInfo userInfo : ((UserManager) context.getSystemService(UserManager.class)).getProfiles(context.getUserId())) {
            if (userInfo.isManagedProfile() && !isExcludedManagedProfile(userInfo)) {
                Log.d("Settings", "isManagedProfilePresent is true");
                return true;
            }
        }
        Log.d("Settings", "isManagedProfilePresent is false");
        return false;
    }

    public static boolean isMediumPowerSavingModeEnabled(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "low_power", -1) == 1;
    }

    public static boolean isMinimalBatteryUseEnabled(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "minimal_battery_use", 0) == 1;
    }

    public static boolean isMultipleBiometricsSupported(Context context) {
        return hasFingerprintHardware(context) && hasFaceHardware(context);
    }

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo[] allNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager != null && (allNetworkInfo = connectivityManager.getAllNetworkInfo()) != null) {
            for (NetworkInfo networkInfo : allNetworkInfo) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isNewDexMode(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "new_dex", 0) != 0;
    }

    public static boolean isNightMode(Context context) {
        return (context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    public static boolean isOnCall(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getCallState() != 0;
    }

    public static boolean isPackageEnabled(Context context, String str) {
        try {
            return context.getPackageManager().getApplicationInfo(str, 0).enabled;
        } catch (Exception e) {
            Log.e("Settings", "Error while retrieving application info for package " + str, e);
            return false;
        }
    }

    public static boolean isPrivateProfile(Context context, int i) {
        return Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures() && ((UserManager) context.getSystemService(UserManager.class)).getUserInfo(i).isPrivateProfile();
    }

    public static boolean isProfileOf(UserHandle userHandle, UserManager userManager) {
        if (userManager != null) {
            return UserHandle.myUserId() == userHandle.getIdentifier() || userManager.getUserProfiles().contains(userHandle);
        }
        return false;
    }

    public static boolean isProfileOrDeviceOwner(UserManager userManager, DevicePolicyManager devicePolicyManager, String str) {
        List users = userManager.getUsers();
        if (devicePolicyManager.isDeviceOwnerAppOnAnyUser(str)) {
            return true;
        }
        int size = users.size();
        for (int i = 0; i < size; i++) {
            ComponentName profileOwnerAsUser = devicePolicyManager.getProfileOwnerAsUser(((UserInfo) users.get(i)).id);
            if (profileOwnerAsUser != null && profileOwnerAsUser.getPackageName().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRTL(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 192) == 128;
    }

    public static boolean isRestrictedProfile(Context context) {
        return ((UserManager) context.getSystemService("user")).hasUserRestriction("no_modify_accounts");
    }

    public static boolean isSettingsIntelligence(Context context) {
        return TextUtils.equals(context.getPackageManager().getPackagesForUid(Binder.getCallingUid())[0], context.getString(R.string.config_settingsintelligence_package_name));
    }

    public static boolean isSupportContactUs(Context context, long j) {
        if (!hasPackage(context, "com.samsung.android.voc")) {
            return false;
        }
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        if (getContactUsIntent(context).resolveActivity(packageManager) == null) {
            return false;
        }
        try {
            return ((long) packageManager.getPackageInfo("com.samsung.android.voc", 0).versionCode) >= j;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static boolean isSupportHelpMenu(Context context) {
        PackageInfo packageInfo;
        if (context != null && !SemPersonaManager.isSecureFolderId(context.getUserId())) {
            try {
                if (hasPackage(context, "com.samsung.helphub") && (packageInfo = context.getPackageManager().getPackageInfo("com.samsung.helphub", 0)) != null && packageInfo.versionCode == 2) {
                    Log.d("Settings", "device support helphub pkg");
                    return true;
                }
            } catch (PackageManager.NameNotFoundException unused) {
                Log.i("Settings", "No HelpHub pkg");
            }
        }
        return false;
    }

    public static boolean isSupportSecureWiFi(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo("com.samsung.android.fast", 0);
            if (packageManager.checkSignatures(RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME, "com.samsung.android.fast") == 0) {
                if (packageInfo.applicationInfo.enabled && UserManager.get(context).isSystemUser() && !SemPersonaManager.isDoEnabled(UserHandle.myUserId())) {
                    return true;
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }

    public static boolean isSystemAlertWindowEnabled(Context context) {
        return !((ActivityManager) context.getSystemService("activity")).isLowRamDevice();
    }

    public static boolean isTablet() {
        String str = SystemProperties.get("ro.build.characteristics");
        return str != null && str.contains("tablet");
    }

    public static boolean isTalkBackEnabled(Context context) {
        return ((AccessibilityManager) context.getSystemService("accessibility")).semIsScreenReaderEnabled();
    }

    public static boolean isUserRemoveCertificateAllowedAsUser(Context context, int i) {
        if (getEnterprisePolicyEnabled(context, "content://com.sec.knox.provider/CertificatePolicy", "isUserRemoveCertificatesAllowed", new String[]{String.valueOf(i)}) != 0) {
            return true;
        }
        Log.d("Settings", "UserRemoveCertificatesAllowedAsUser : not allowed");
        return false;
    }

    public static boolean isVoiceCapable(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        return telephonyManager != null && telephonyManager.isVoiceCapable();
    }

    public static boolean isWipsEnabled() {
        if ("wifi-only".equalsIgnoreCase(SemSystemProperties.get("ro.carrier", "Unknown").trim()) || "yes".equalsIgnoreCase(SemSystemProperties.get("ro.radio.noril", "no").trim())) {
            Log.d("Settings", "WI-Fi only model");
            return true;
        }
        Log.d("Settings", "Carrier Model Operator Name = " + TelephonyFeatures.getSalesCode());
        return ("ATT".equals(TelephonyFeatures.getSalesCode()) || "AIO".equals(TelephonyFeatures.getSalesCode()) || "APP".equals(TelephonyFeatures.getSalesCode())) ? false : true;
    }

    public static void launchIntent(Fragment fragment, Intent intent) {
        try {
            int intExtra = intent.getIntExtra("android.intent.extra.USER_ID", -1);
            if (intExtra == -1) {
                fragment.startActivity(intent);
            } else {
                fragment.getActivity().startActivityAsUser(intent, new UserHandle(intExtra));
            }
        } catch (ActivityNotFoundException unused) {
            Log.w("Settings", "No activity found for " + intent);
        }
    }

    public static int locateSmartNetworkSwitch(Context context) {
        if (context == null) {
            Log.e("WifiSettings/AdvancedWifiSettings/SettingsSearchUtils", "context is null.");
            return -1;
        }
        if (UserHandle.myUserId() != 0) {
            Log.e("WifiSettings/AdvancedWifiSettings/SettingsSearchUtils", "locateSmartNetworkSwitch() - WIFI_SMART_NETWORK_SWITCH_DISABLED(myUserId != USER_OWNER)");
            return 3;
        }
        boolean isWifiOnly = com.android.settingslib.Utils.isWifiOnly(context);
        boolean z = DBG;
        if (isWifiOnly) {
            if (z) {
                Log.d("WifiSettings/AdvancedWifiSettings/SettingsSearchUtils", "locateSmartNetworkSwitch() - WIFI_SMART_NETWORK_SWITCH_DISABLED");
            }
            return 3;
        }
        if (!z) {
            return 2;
        }
        Log.d("WifiSettings/AdvancedWifiSettings/SettingsSearchUtils", "locateSmartNetworkSwitch() - WIFI_SMART_NETWORK_SWITCH_ON_ADVANCED_WIFI_SETTINGS");
        return 2;
    }

    public static void prepareCustomPreferencesList(ViewGroup viewGroup, View view, View view2) {
        if (view2.getScrollBarStyle() == 33554432) {
            int dimensionPixelSize = view2.getResources().getDimensionPixelSize(17105767);
            if (viewGroup instanceof PreferenceFrameLayout) {
                view.getLayoutParams().removeBorders = true;
            }
            view2.setPaddingRelative(0, 0, 0, dimensionPixelSize);
        }
    }

    public static String readCountryCode() {
        String str = mCountryCode;
        if (str != null && !str.isEmpty()) {
            return mCountryCode;
        }
        mCountryCode = SystemProperties.get("ro.csc.countryiso_code");
        MainClearConfirm$$ExternalSyntheticOutline0.m(new StringBuilder("readCountryCode(): country="), mCountryCode, "Settings");
        return mCountryCode;
    }

    public static void setActionBarShadowAnimation(FragmentActivity fragmentActivity, Lifecycle lifecycle, RecyclerView recyclerView) {
        if (fragmentActivity == null) {
            Log.w("Settings", "No activity, cannot style actionbar.");
            return;
        }
        ActionBar actionBar = fragmentActivity.getActionBar();
        if (actionBar == null) {
            Log.w("Settings", "No actionbar, cannot style actionbar.");
            return;
        }
        actionBar.setElevation(0.0f);
        if (lifecycle == null || recyclerView == null) {
            return;
        }
        ActionBarShadowController.attachToView(fragmentActivity, lifecycle, recyclerView);
    }

    public static void setDeXSettings(ContentResolver contentResolver, String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString(GoodSettingsContract.EXTRA_NAME.POLICY_KEY, str);
        bundle.putString("val", str2);
        try {
            contentResolver.call(DEX_SETTINGS_URI, "setSettings", (String) null, bundle);
        } catch (IllegalArgumentException unused) {
            Log.e("Settings", "IllegalArgumentException :: setDeXSettings ".concat(str));
        }
    }

    public static void setMaxFontScale(Context context, TextView textView) {
        float f = context.getResources().getConfiguration().fontScale;
        float textSize = textView.getTextSize() / context.getResources().getDisplayMetrics().scaledDensity;
        if (f > 1.1f) {
            f = 1.1f;
        }
        textView.setTextSize(1, textSize * f);
    }

    public static void setMaxFontScale$1(Context context, TextView textView) {
        float f = context.getResources().getConfiguration().fontScale;
        float textSize = textView.getTextSize() / context.getResources().getDisplayMetrics().scaledDensity;
        if (f > 1.3f) {
            f = 1.3f;
        }
        textView.setTextSize(1, textSize * f);
    }

    public static void setTaskIdToIntent(Intent intent, Integer num) {
        intent.semSetLaunchOverTargetTask(num.intValue(), false);
    }

    public static boolean shouldHideUser(UserHandle userHandle, UserManager userManager) {
        return userManager.getUserProperties(userHandle).getShowInQuietMode() == 1 && userManager.isQuietModeEnabled(userHandle);
    }

    public static void showPinWindowToast(Context context) {
        int i;
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        try {
            boolean hasPermanentMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
            Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
            int integer = context.getResources().getInteger(R.integer.config_pinnerWebviewPinBytes);
            if (hasPermanentMenuKey) {
                i = accessibilityManager.isEnabled() ? R.string.tw_lock_to_app_toast_accessible : R.string.tw_lock_to_app_toast;
            } else if (accessibilityManager.isEnabled()) {
                i = R.string.sem_lock_to_app_toast_accessible;
            } else {
                if (integer != 2 && integer != 3) {
                    i = R.string.sem_lock_to_app_toast;
                }
                i = R.string.screen_pinning_msg_in_gesture;
            }
            Toast.makeText(context, i, 1).show();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static boolean startQuietModeDialogIfNecessary(Context context, UserManager userManager, int i) {
        if (!userManager.isQuietModeEnabled(UserHandle.of(i))) {
            return false;
        }
        context.startActivity(UnlaunchableAppActivity.createInQuietModeDialogIntent(i));
        return true;
    }

    public static boolean supportBridgedApStaConcurrency() {
        if (SemWifiApCust.DBG) {
            r1 = SystemProperties.getInt("vendor.wifiap.debug.BridgedApSta", 0) == 1;
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m("supportBridgedApStaConcurrency() ", "Settings", r1);
        }
        return r1;
    }

    public static int updateSmartNetworkSwitchAvailability(Context context) {
        int simState;
        ServiceState serviceState;
        if (context == null) {
            return -1;
        }
        boolean z = Settings.Global.getInt(context.getContentResolver(), "airplane_mode_on", 0) != 0;
        boolean z2 = Settings.Global.getInt(context.getContentResolver(), "mobile_data", 1) != 0;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        boolean z3 = DBG;
        if (telephonyManager == null) {
            Log.e("Utils", "TelephonyManager is null.");
            simState = 0;
        } else if (TelephonyManager.getDefault().getPhoneCount() > 1) {
            int simState2 = telephonyManager.getSimState(0);
            int simState3 = telephonyManager.getSimState(1);
            simState = (simState2 == 5 || simState3 == 5) ? 5 : 0;
            if (z3) {
                Preference$$ExternalSyntheticOutline0.m(RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m("simState1 = ", ", simState2 = ", simState2, simState3, ", simState = "), simState, "WifiSettings/AdvancedWifiSettings/SettingsSearchUtils");
            }
        } else {
            simState = telephonyManager.getSimState();
        }
        boolean dataRoaming = (telephonyManager == null || (serviceState = telephonyManager.getServiceState()) == null) ? false : serviceState.getDataRoaming();
        int i = (simState == 5 || (z3 && SystemProperties.get("SimCheck.disable").equals("1"))) ? z ? 3 : z2 ? dataRoaming ? 5 : 1 : 4 : 2;
        if (z3) {
            StringBuilder sb = new StringBuilder("Checkbox - Airplane Mode Off / Mobile Data Enabled / SIM State-Ready / isMobilePolicyDataEnable / !mobileDataBlockedByRoaming / result : ");
            sb.append(!z);
            sb.append(" / ");
            sb.append(z2);
            sb.append(" / ");
            sb.append(simState == 5);
            sb.append(" / true / ");
            sb.append(!dataRoaming);
            sb.append(" / ");
            sb.append(i);
            Log.d("WifiSettings/AdvancedWifiSettings/SettingsSearchUtils", sb.toString());
        }
        return i;
    }

    public static boolean isIntentAvailable(Context context, Intent intent) {
        List<ResolveInfo> queryIntentActivities;
        PackageManager packageManager = context.getPackageManager();
        return (packageManager == null || (queryIntentActivities = packageManager.queryIntentActivities(intent, 65536)) == null || queryIntentActivities.size() <= 0) ? false : true;
    }

    public static Intent getContactUsIntent(String str, String str2, String str3) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("voc://view/contactUs"));
        intent.putExtra("packageName", str);
        intent.putExtra("appId", str3);
        intent.putExtra("appName", str2);
        return intent;
    }

    public static boolean isProfileOrDeviceOwner(DevicePolicyManager devicePolicyManager, String str, int i) {
        if (devicePolicyManager.getDeviceOwnerUserId() == i && devicePolicyManager.isDeviceOwnerApp(str)) {
            return true;
        }
        ComponentName profileOwnerAsUser = devicePolicyManager.getProfileOwnerAsUser(i);
        return profileOwnerAsUser != null && profileOwnerAsUser.getPackageName().equals(str);
    }

    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v5, types: [boolean] */
    /* JADX WARN: Type inference failed for: r8v7 */
    public static int getEnterprisePolicyEnabled(Context context, String str, String str2, String[] strArr) {
        Trace.beginSection("Utils#getEnterprisePolicyEnabled");
        Uri parse = Uri.parse(str);
        int i = -1;
        ?? r8 = -1;
        if (context == null) {
            Log.e("Settings", "getEnterprisePolicyEnabled: context is null");
            Trace.endSection();
            return -1;
        }
        Cursor query = context.getContentResolver().query(parse, null, str2, strArr, null);
        if (query != null) {
            try {
                query.moveToFirst();
                r8 = query.getString(query.getColumnIndex(str2)).equals("true");
            } catch (Exception unused) {
            } catch (Throwable th) {
                query.close();
                throw th;
            }
            query.close();
            i = r8;
        }
        Log.w("SettingsEdm", "projectionArgs:" + str2 + "/" + i);
        Trace.endSection();
        return i;
    }

    public static boolean hasPackage(Context context, String str, int i) {
        if (context == null) {
            return false;
        }
        try {
            context.getPackageManager().getApplicationInfoAsUser(str, 128, i);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d("Settings", "Package not found : " + str + " / UserID : " + i);
            return false;
        }
    }

    public static float getContentFrameWidthRatio(int i, int i2) {
        float f;
        float f2;
        if (i >= 589 && i <= 959 && i2 >= 411) {
            return 0.86f;
        }
        if (i >= 960) {
            f2 = 840.0f;
            f = i;
        } else {
            f = i;
            f2 = f - 20.0f;
        }
        return f2 / f;
    }
}
