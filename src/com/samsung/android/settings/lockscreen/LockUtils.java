package com.samsung.android.settings.lockscreen;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.trust.TrustManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.hardware.display.DisplayManager;
import android.hardware.display.SemWifiDisplayStatus;
import android.media.MediaRouter;
import android.net.Uri;
import android.os.Build;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.window.embedding.ActivityEmbeddingController;

import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.calendar.secfeature.CHN_SECCalendarFeatures;
import com.samsung.android.calendar.secfeature.HKTW_SECCalendarFeatures;
import com.samsung.android.calendar.secfeature.JPN_SECCalendarFeatures;
import com.samsung.android.calendar.secfeature.KOR_SECCalendarFeatures;
import com.samsung.android.calendar.secfeature.SECCalendarFeatures;
import com.samsung.android.calendar.secfeature.VI_SECCalendarFeatures;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.devicesecurity.PasswordPolicy;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.sdk.cover.ScoverManager;
import com.samsung.android.sdk.cover.ScoverState;
import com.samsung.android.service.EngineeringMode.EngineeringModeManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.knox.KnoxUtils;
import com.sec.ims.configuration.DATA;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class LockUtils {
    public static final String CONFIG_LOCK_SCREEN_CLOCK_DISPLAY_POLICY =
            SemCscFeature.getInstance().getString("CscFeature_LockScreen_ConfigClockDisplayPolicy");
    public static EngineeringModeManager mEmm;

    public static void addHorizontalSpacing(
            Context context, LinearLayout linearLayout, FrameLayout frameLayout) {
        if (linearLayout == null || frameLayout == null) {
            return;
        }
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(Utils.getListHorizontalPadding(context), -1);
        View view = new View(context);
        view.setLayoutParams(layoutParams);
        view.setBackgroundColor(context.getColor(R.color.sec_widget_round_and_bgcolor));
        linearLayout.addView(view, 0);
        View view2 = new View(context);
        view2.setLayoutParams(layoutParams);
        view2.setBackgroundColor(context.getColor(R.color.sec_widget_round_and_bgcolor));
        linearLayout.addView(view2);
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, -1, 1.0f));
    }

    public static Intent getChooseLockHintSettingsIntent(FragmentActivity fragmentActivity, int i) {
        int i2 = ChooseLockHintSettings.$r8$clinit;
        if (!Rune.isDomesticModel()
                || SemPersonaManager.isKnoxId(i)
                || SemPersonaManager.isDoEnabled(i)) {
            return null;
        }
        return new Intent(fragmentActivity, (Class<?>) ChooseLockHintSettings.class)
                .putExtra("android.intent.extra.USER_ID", i);
    }

    public static int getColorFromAttribute(Context context) {
        Resources.Theme theme;
        if (context == null) {
            return 0;
        }
        try {
            Resources resources = context.getResources();
            if ((resources == null
                            || resources
                                    .getResourceTypeName(android.R.attr.textColorPrimary)
                                    .equals("attr"))
                    && (theme = context.getTheme()) != null) {
                TypedValue typedValue = new TypedValue();
                theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true);
                return context.obtainStyledAttributes(
                                typedValue.data, new int[] {android.R.attr.textColorPrimary})
                        .getColor(0, 0);
            }
        } catch (Exception unused) {
        }
        return 0;
    }

    public static Intent getFmmDialogIntent(Context context) {
        if (Settings.System.getInt(context.getContentResolver(), "fmm_unlock_recovery", 0) != 0
                || !isSupportFMM(context)) {
            return null;
        }
        Intent intent = new Intent();
        intent.setPackage("com.samsung.android.fmm");
        intent.setAction("com.samsung.android.fmm.action.UserpppDialog");
        intent.putExtra("type", "Lock");
        return intent;
    }

    public static Intent getFmmService(LockscreenCredential lockscreenCredential, Context context) {
        if (!isSupportFMM(context)) {
            return null;
        }
        Intent intent = new Intent();
        intent.setPackage("com.samsung.android.fmm");
        intent.setAction("com.sec.pcw.device.FMM_PPP_SERVICE");
        intent.putExtra("usrPPP", lockscreenCredential.getCredential());
        return intent;
    }

    public static boolean isAODBlockonSmartView(Context context) {
        boolean z;
        SemWifiDisplayStatus semGetWifiDisplayStatus =
                ((DisplayManager) context.getSystemService("display")).semGetWifiDisplayStatus();
        boolean z2 =
                (semGetWifiDisplayStatus == null
                                || semGetWifiDisplayStatus.getActiveDisplayState() != 2
                                || semGetWifiDisplayStatus.getActiveDisplay() == null
                                || semGetWifiDisplayStatus.getConnectedState() == 3)
                        ? false
                        : true;
        boolean z3 =
                semGetWifiDisplayStatus != null
                        && (semGetWifiDisplayStatus.getConnectedState() == 3
                                || semGetWifiDisplayStatus.getConnectedState() == 2);
        if (!z2 || z3) {
            boolean z4 =
                    ((DisplayManager) context.getSystemService("display")).semGetActiveDlnaState()
                            == 1;
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "isDlnaDeviceConnected : ", "LockUtils", z4);
            if (!z4) {
                MediaRouter.RouteInfo selectedRoute =
                        ((MediaRouter) context.getSystemService("media_router"))
                                .getSelectedRoute(4);
                boolean z5 =
                        (selectedRoute.getSupportedTypes() & 4) != 0
                                && selectedRoute.semGetDeviceAddress() == null
                                && (selectedRoute.getPresentationDisplay() != null
                                        || "Audio Mirroring"
                                                .equalsIgnoreCase(
                                                        selectedRoute.getDescription().toString()));
                AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                        "isGoogleCastConnected : ", "LockUtils", z5);
                if (!z5) {
                    z = false;
                    return z
                            && !Utils.getStringFromDeXSettings(
                                            context.getContentResolver(),
                                            "wireless_dex",
                                            DATA.DM_FIELD_INDEX.PCSCF_DOMAIN)
                                    .equalsIgnoreCase(Integer.toString(4));
                }
            }
        }
        z = true;
        if (z) {
            return false;
        }
    }

    public static boolean isAODDisabledInPSM(Context context) {
        String string =
                Settings.Global.getString(
                        context.getContentResolver(), "psm_always_on_display_mode");
        if (string != null) {
            return "1".equals(string.split(",")[0]);
        }
        return true;
    }

    public static boolean isAlwaysOnDisplayEnabled(Context context) {
        return Settings.System.getIntForUser(
                        context.getContentResolver(), "aod_mode", 0, UserHandle.myUserId())
                == 1;
    }

    public static boolean isApplyingSetupTheme(Context context) {
        return !isApplyingTabletGUI(context)
                && context.getResources().getConfiguration().orientation == 1;
    }

    public static boolean isApplyingTabletGUI(Context context) {
        return !Rune.isSamsungDexMode(context)
                && context.getResources().getConfiguration().smallestScreenWidthDp >= 600;
    }

    public static boolean isCameraCoverAttached(Context context) {
        try {
            ScoverState coverState = new ScoverManager(context).getCoverState();
            if (coverState == null || !coverState.attached) {
                return false;
            }
            return coverState.type == 17;
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isEmTokenAllowed(Context context) {
        if (mEmm == null) {
            mEmm = new EngineeringModeManager(context.getApplicationContext());
        }
        return mEmm.isConnected() && mEmm.getStatus(64) == 1;
    }

    public static boolean isFmmUnlockAllowed(Context context, int i, boolean z) {
        PasswordPolicy passwordPolicy;
        boolean z2 = true;
        if (SemPersonaManager.isDoEnabled(i)) {
            EnterpriseDeviceManager enterpriseDeviceManager =
                    EnterpriseDeviceManager.getInstance(context);
            if (enterpriseDeviceManager != null
                    && (passwordPolicy = enterpriseDeviceManager.getPasswordPolicy()) != null) {
                z2 = passwordPolicy.isClearLockAllowed();
            }
        } else if (z || i != 0) {
            z2 = false;
        }
        if (!z2) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "FMM unlock is not allowed for user ", "LockUtils");
        }
        return z2;
    }

    public static boolean isInMultiWindow(Activity activity) {
        return activity.isInMultiWindowMode()
                && !ActivityEmbeddingController.getInstance(activity).isActivityEmbedded(activity);
    }

    public static boolean isLockMenuDisabledByEdm(Context context) {
        int enterprisePolicyEnabled =
                Utils.getEnterprisePolicyEnabled(
                        context,
                        "content://com.sec.knox.provider/RestrictionPolicy2",
                        "isLockScreenEnabled",
                        new String[] {"false"});
        return enterprisePolicyEnabled != -1 && enterprisePolicyEnabled == 0;
    }

    public static boolean isLockSettingsBlockonDexMode(Context context) {
        return (!Rune.isSamsungDexMode(context)
                        || Utils.isDesktopStandaloneMode(context)
                        || Utils.isNewDexMode(context))
                ? false
                : true;
    }

    public static boolean isSupportActionBarButton(FragmentActivity fragmentActivity) {
        return (!(fragmentActivity.getResources().getConfiguration().orientation == 2)
                        || isApplyingTabletGUI(fragmentActivity)
                        || (WizardManagerHelper.isDeviceProvisioned(fragmentActivity) ^ true))
                ? false
                : true;
    }

    public static boolean isSupportAodService() {
        return SemFloatingFeature.getInstance()
                .getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM", ApnSettings.MVNO_NONE)
                .contains("aodversion");
    }

    public static boolean isSupportFMM(Context context) {
        Cursor query;
        if (!context.getPackageManager().isPackageAvailable("com.samsung.android.fmm")
                || (query =
                                context.getContentResolver()
                                        .query(
                                                Uri.parse(
                                                        "content://com.samsung.android.fmm/fmmsupport"),
                                                null,
                                                null,
                                                null,
                                                null))
                        == null) {
            return false;
        }
        try {
            int columnIndex = query.getColumnIndex("fmmsupport");
            while (query.moveToNext()) {
                if (Boolean.valueOf(query.getString(columnIndex)).booleanValue()) {
                    Log.d("LockUtils", "Support FMM : " + query.getString(columnIndex));
                    query.close();
                    return true;
                }
            }
        } catch (Exception unused) {
        } catch (Throwable th) {
            query.close();
            throw th;
        }
        query.close();
        return false;
    }

    public static boolean isSupportHijriCalendar(Context context) {
        Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        if (locale == null) {
            locale = Locale.getDefault();
        }
        String language = locale.getLanguage();
        return (language.equals("en") || language.equals("ar"))
                && SemCscFeature.getInstance()
                        .getBoolean("CscFeature_Common_SupportHijriLunarCalendar", false);
    }

    public static boolean isSupportLunarCalendarMenu(Context context) {
        boolean z = false;
        Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        if (locale == null) {
            locale = Locale.getDefault();
        }
        String language = locale.getLanguage();
        if (SECCalendarFeatures.sInstance == null) {
            String string =
                    SemCscFeature.getInstance()
                            .getString("CscFeature_Calendar_EnableLocalHolidayDisplay");
            if ("KOREA".equals(string)) {
                SECCalendarFeatures.sInstance = new KOR_SECCalendarFeatures();
            } else if ("CHINA".equals(string)) {
                SECCalendarFeatures.sInstance = new CHN_SECCalendarFeatures();
            } else if ("HKTW".equals(string)) {
                SECCalendarFeatures.sInstance = new HKTW_SECCalendarFeatures();
            } else if ("JAPAN".equals(string)) {
                SECCalendarFeatures.sInstance = new JPN_SECCalendarFeatures();
            } else if ("VI".equals(string)) {
                SECCalendarFeatures.sInstance = new VI_SECCalendarFeatures();
            } else {
                SECCalendarFeatures.sInstance = new SECCalendarFeatures();
            }
        }
        boolean isLunarCalendarSupported = SECCalendarFeatures.sInstance.isLunarCalendarSupported();
        boolean z2 = language.equals("en") || language.equals("zh") || language.equals("vi");
        if (isLunarCalendarSupported
                && z2
                && "VI"
                        .equals(
                                SemCscFeature.getInstance()
                                        .getString(
                                                "CscFeature_Calendar_EnableLocalHolidayDisplay",
                                                (String) null))) {
            z = true;
        }
        StringBuilder sb = new StringBuilder("isSupportLunarCalendarMenu : isSupportLunar : ");
        sb.append(isLunarCalendarSupported);
        sb.append(", Locale : ");
        sb.append(language);
        sb.append(", isSupportLunarInVietnam : ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, z, "LockUtils");
        return z;
    }

    public static boolean isSupportSubLockscreen() {
        return SemFloatingFeature.getInstance()
                .getString(
                        "SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY",
                        ApnSettings.MVNO_NONE)
                .contains("LOCKSCREEN");
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x003b A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:8:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isUsingContentHomeButtonInAOD(android.content.Context r6) {
        /*
            android.content.ContentResolver r0 = r6.getContentResolver()
            java.lang.String r6 = "content://com.samsung.android.app.aodservice.provider/settings/aod_content_to_show"
            android.net.Uri r1 = android.net.Uri.parse(r6)
            r4 = 0
            r5 = 0
            r2 = 0
            r3 = 0
            android.database.Cursor r6 = r0.query(r1, r2, r3, r4, r5)
            r0 = 0
            if (r6 == 0) goto L37
            int r1 = r6.getCount()     // Catch: java.lang.Throwable -> L23 android.database.SQLException -> L25
            if (r1 <= 0) goto L27
            r6.moveToFirst()     // Catch: java.lang.Throwable -> L23 android.database.SQLException -> L25
            int r1 = r6.getInt(r0)     // Catch: java.lang.Throwable -> L23 android.database.SQLException -> L25
            goto L28
        L23:
            r0 = move-exception
            goto L33
        L25:
            r1 = move-exception
            goto L2c
        L27:
            r1 = r0
        L28:
            r6.close()
            goto L38
        L2c:
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L23
            r6.close()
            goto L37
        L33:
            r6.close()
            throw r0
        L37:
            r1 = r0
        L38:
            r6 = 2
            if (r1 != r6) goto L3c
            r0 = 1
        L3c:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.lockscreen.LockUtils.isUsingContentHomeButtonInAOD(android.content.Context):boolean");
    }

    public static boolean readGoogleFRPFlag(Context context) {
        if (!Rune.isChinaModel() || Build.VERSION.SEM_FIRST_SDK_INT < 31) {
            return Utils.isFrpChallengeRequired(context);
        }
        return false;
    }

    public static void resetHint(Context context, int i) {
        if (!Rune.isDomesticModel()
                || SemPersonaManager.isKnoxId(i)
                || SemPersonaManager.isDoEnabled(i)) {
            Log.d("LockUtils", "Hint doesn't be supported");
        } else {
            new LockPatternUtils(context).setPasswordHint((String) null, i);
        }
    }

    public static String updateAttemptLockoutDesc(Context context, long j) {
        int round = (Math.round(j / 1000) % 60) + 1;
        int floor = (((int) Math.floor(j / 60000)) % 60) + 1;
        int floor2 = (int) Math.floor(j / 3600000);
        if (floor2 <= 0) {
            return floor > 1
                    ? context.getResources()
                            .getQuantityString(
                                    R.plurals
                                            .sec_confirm_lock_too_many_failed_attempts_countdown_min,
                                    floor,
                                    Integer.valueOf(floor))
                    : context.getResources()
                            .getQuantityString(
                                    R.plurals
                                            .sec_confirm_lock_too_many_failed_attempts_countdown_sec,
                                    round,
                                    Integer.valueOf(round));
        }
        if (floor != 60) {
            return floor2 == 1
                    ? context.getResources()
                            .getQuantityString(
                                    R.plurals
                                            .sec_confirm_lock_too_many_failed_attempts_countdown_1_hour_and_min,
                                    floor,
                                    Integer.valueOf(floor))
                    : (floor2 <= 1 || floor != 1)
                            ? context.getString(
                                    R.string
                                            .sec_confirm_lock_too_many_failed_attempts_countdown_hour_and_min,
                                    Integer.valueOf(floor2),
                                    Integer.valueOf(floor))
                            : context.getResources()
                                    .getQuantityString(
                                            R.plurals
                                                    .sec_confirm_lock_too_many_failed_attempts_countdown_hour_and_1_min,
                                            floor2,
                                            Integer.valueOf(floor2));
        }
        int i = floor2 + 1;
        return context.getResources()
                .getQuantityString(
                        R.plurals.sec_confirm_lock_too_many_failed_attempts_countdown_hour,
                        i,
                        Integer.valueOf(i));
    }

    public static void updateSetUpCredentialIfNeeded(Context context, int i) {
        int i2;
        PasswordPolicy passwordPolicy;
        PasswordPolicy passwordPolicy2;
        if (context == null) {
            return;
        }
        String str = KnoxUtils.mDeviceType;
        if (DualDarManager.isOnDeviceOwnerEnabled()
                && KnoxUtils.isDualDarDoInnerAuthUser(context, i)) {
            if (KnoxUtils.isChangeRequestedForInner(context) > 0) {
                Log.e("LockUtils", "Setting password change requested inner");
                PasswordPolicy passwordPolicy3 = KnoxUtils.getPasswordPolicy(context, 0);
                if (passwordPolicy3 != null) {
                    passwordPolicy3.setPwdChangeRequested(0);
                }
                EnterpriseDeviceManager enterpriseDeviceManager =
                        EnterpriseDeviceManager.getInstance(context);
                if (enterpriseDeviceManager == null
                        || (passwordPolicy2 = enterpriseDeviceManager.getPasswordPolicy())
                                == null) {
                    return;
                }
                passwordPolicy2.setPwdChangeRequestedForInner(0);
                return;
            }
            return;
        }
        Settings.System.putInt(context.getContentResolver(), "enable_one_lock_ongoing", 0);
        if (SemPersonaManager.isKnoxId(i) || KnoxUtils.isKnoxOrganizationOwnedDevice(context, i)) {
            try {
                i2 =
                        context.getPackageManager()
                                .getPackageUidAsUser(
                                        SemPersonaManager.getAdminComponentName(i).getPackageName(),
                                        i);
            } catch (Exception e) {
                CloneBackend$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("Error fetching admin uid "), "LockUtils");
                i2 = i;
            }
            UserManager userManager = UserManager.get(context);
            if (userManager == null) {
                throw new IllegalStateException("Unable to load UserManager");
            }
            UserInfo userInfo = userManager.getUserInfo(i);
            try {
                if (SemPersonaManager.isKnoxId(i)) {
                    passwordPolicy =
                            EnterpriseKnoxManager.getInstance()
                                    .getKnoxContainerManager(context, new ContextInfo(i2, i))
                                    .getPasswordPolicy();
                } else {
                    EnterpriseDeviceManager enterpriseDeviceManager2 =
                            EnterpriseDeviceManager.getInstance(context);
                    passwordPolicy =
                            enterpriseDeviceManager2 != null
                                    ? enterpriseDeviceManager2.getPasswordPolicy()
                                    : null;
                }
                if (!DualDarManager.isOnDeviceOwnerEnabled()
                        && passwordPolicy != null
                        && passwordPolicy.isChangeRequested() > 0) {
                    Log.e("LockUtils", "Setting password change requested");
                    passwordPolicy.setPwdChangeRequested(0);
                }
                if (userInfo.needSetupCredential()) {
                    SemPersonaManager.clearAttributes(i, 536870912);
                }
                LockPatternUtils lockPatternUtils = new LockPatternUtils(context);
                if (((KeyguardManager) context.getSystemService("keyguard")).isDeviceLocked(i)
                        && !SemPersonaManager.isSecureFolderId(i)) {
                    ((TrustManager) context.getSystemService("trust"))
                            .setDeviceLockedForUser(i, false);
                    lockPatternUtils.requireStrongAuth(0, i);
                }
                int passwordType =
                        KnoxUtils.getPasswordType(lockPatternUtils.getActivePasswordQuality(i));
                Log.d(
                        "LockUtils",
                        "KNOX Analytics Logging Event for Auth change : P/P/P Type= "
                                + passwordType);
                KnoxUtils.insertStatusLogForKnox(context, i, passwordType);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
