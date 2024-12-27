package com.samsung.android.settings.display;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Debug;
import android.os.FactoryTest;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.IWindowManager;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.network.telephony.EnabledNetworkModePreferenceController$PreferenceEntriesBuilder$$ExternalSyntheticLambda3;
import com.android.settingslib.dream.DreamBackend;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.display.controller.SecShowChargingInfoPreferenceController;
import com.samsung.android.settings.inputmethod.TouchPadGestureSettingsController;
import com.samsung.android.settings.widget.SecRestrictedPreference;
import com.sec.ims.configuration.DATA;
import com.sec.ims.im.ImIntent;
import com.sec.ims.volte2.data.VolteConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SecDisplayUtils {
    public static final String[] APK_SYSTEM_FLIPFONT_NAME_LIST;
    public static final Signature[] SIGNATURES;
    public static File mPreviewFontFile;

    static {
        SystemProperties.get("ro.product.device", "NONE").getClass();
        SIGNATURES =
                new Signature[] {
                    new Signature(
                            "308204d4308203bca003020102020900e5eff0a8f66d92b3300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531335a170d3338313130373132323531335a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100e9f1edb42423201dce62e68f2159ed8ea766b43a43d348754841b72e9678ce6b03d06d31532d88f2ef2d5ba39a028de0857983cd321f5b7786c2d3699df4c0b40c8d856f147c5dc54b9d1d671d1a51b5c5364da36fc5b0fe825afb513ec7a2db862c48a6046c43c3b71a1e275155f6c30aed2a68326ac327f60160d427cf55b617230907a84edbff21cc256c628a16f15d55d49138cdf2606504e1591196ed0bdc25b7cc4f67b33fb29ec4dbb13dbe6f3467a0871a49e620067755e6f095c3bd84f8b7d1e66a8c6d1e5150f7fa9d95475dc7061a321aaf9c686b09be23ccc59b35011c6823ffd5874d8fa2a1e5d276ee5aa381187e26112c7d5562703b36210b020103a382010b30820107301d0603551d0e041604145b115b23db35655f9f77f78756961006eebe3a9e3081d70603551d230481cf3081cc80145b115b23db35655f9f77f78756961006eebe3a9ea181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900e5eff0a8f66d92b3300c0603551d13040530030101ff300d06092a864886f70d0101050500038201010039c91877eb09c2c84445443673c77a1219c5c02e6552fa2fbad0d736bc5ab6ebaf0375e520fe9799403ecb71659b23afda1475a34ef4b2e1ffcba8d7ff385c21cb6482540bce3837e6234fd4f7dd576d7fcfe9cfa925509f772c494e1569fe44e6fcd4122e483c2caa2c639566dbcfe85ed7818d5431e73154ad453289fb56b607643919cf534fbeefbdc2009c7fcb5f9b1fa97490462363fa4bedc5e0b9d157e448e6d0e7cfa31f1a2faa9378d03c8d1163d3803bc69bf24ec77ce7d559abcaf8d345494abf0e3276f0ebd2aa08e4f4f6f5aaea4bc523d8cc8e2c9200ba551dd3d4e15d5921303ca9333f42f992ddb70c2958e776c12d7e3b7bd74222eb5c7a")
                };
        APK_SYSTEM_FLIPFONT_NAME_LIST =
                new String[] {
                    ApnSettings.MVNO_NONE,
                    "com.monotype.android.font.chococooky",
                    "com.monotype.android.font.cooljazz",
                    "com.monotype.android.font.samsungsans",
                    "com.monotype.android.font.rosemary",
                    "com.monotype.android.font.applemint",
                    "com.monotype.android.font.tinkerbell",
                    "com.monotype.android.font.kaiti",
                    "com.monotype.android.font.shaonv",
                    "com.monotype.android.font.miao",
                    "com.monotype.android.font.samsungone",
                    "com.monotype.android.font.foundation",
                    "com.monotype.android.font.roboto"
                };
    }

    public static void applyFlipFonts(Context context, String str) {
        Intent intent = new Intent();
        intent.setAction("samsung.settings.flipfont.APPLY_NEW_FONT");
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                "com.samsung.android.settings.flipfont.FlipFontReceiver");
        intent.putExtra("flipfontName", str);
        intent.setFlags(268435456);
        context.sendBroadcast(intent);
    }

    public static void applyForcedDisplayDensity(int i, int i2, int i3) {
        try {
            try {
                WindowManagerGlobal.getWindowManagerService()
                        .setForcedDisplaySizeDensity(0, i, i2, i3, true, -1);
            } catch (RemoteException e) {
                Log.e(
                        "SecDisplayUtils",
                        "applyForcedDisplayDensity: density="
                                + i3
                                + " callers="
                                + Debug.getCallers(5)
                                + " ex="
                                + e);
            }
        } finally {
            StringBuilder m =
                    ListPopupWindow$$ExternalSyntheticOutline0.m(
                            i3, "applyForcedDisplayDensity: density=", " callers=");
            m.append(Debug.getCallers(5));
            Log.i("SecDisplayUtils", m.toString());
        }
    }

    public static boolean canChangeNightMode(Context context) {
        return Utils.isCurrentThemeSupportNightTheme(context)
                && !Utils.isMinimalBatteryUseEnabled(context);
    }

    public static boolean canSetHighRefreshRate(Context context) {
        return !Rune.isSamsungDexMode(context)
                || Utils.isDesktopStandaloneMode(context)
                || Utils.isNewDexMode(context);
    }

    public static boolean canSetHighRefreshRateAboveWQHD(Context context) {
        Display defaultDisplay =
                ((WindowManager) context.getSystemService(WindowManager.class)).getDefaultDisplay();
        DisplayInfo displayInfo = new DisplayInfo();
        defaultDisplay.getDisplayInfo(displayInfo);
        for (Display.Mode mode : displayInfo.supportedModes) {
            if (mode.getPhysicalWidth() >= 1440 && ((int) mode.getRefreshRate()) > 60) {
                return true;
            }
        }
        return false;
    }

    public static String getAccessibilityVisionMessage(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        return Settings.System.getInt(contentResolver, "greyscale_mode", 0) != 0
                ? context.getString(R.string.direct_access_actions_greyscale_title)
                : Settings.System.getInt(contentResolver, "high_contrast", 0) != 0
                        ? context.getString(R.string.negative_color_title)
                        : Settings.System.getInt(contentResolver, "color_blind", 0) != 0
                                ? context.getString(R.string.colour_adjustment_title)
                                : Settings.Secure.getInt(contentResolver, "color_lens_switch", 0)
                                                != 0
                                        ? context.getString(R.string.colour_lens_title)
                                        : Settings.Secure.getInt(
                                                                contentResolver,
                                                                "accessibility_display_inversion_enabled",
                                                                0)
                                                        != 0
                                                ? context.getString(
                                                        R.string
                                                                .accessibility_display_inversion_preference_title)
                                                : ApnSettings.MVNO_NONE;
    }

    public static int getAutoBrightnessDefaultValue(Context context) {
        if (!SemFloatingFeature.getInstance()
                .getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_AUTO_BRIGTNESS")) {
            return 0;
        }
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        if ("MTR".equals(Utils.getSalesCode())) {
            return 1;
        }
        return (Rune.supportLightSensor(context)
                        && context.getResources()
                                .getBoolean(
                                        android.R.bool.config_batterySaverStickyBehaviourDisabled))
                ? 1
                : 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0059, code lost:

       if (r3 == null) goto L23;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getChargingInfoAlways(android.content.Context r11) {
        /*
            java.lang.String r0 = "Show_charging_infomation Set First chargingInfoAlways: "
            android.content.ContentResolver r1 = r11.getContentResolver()
            java.lang.String r2 = "charging_info_always"
            r3 = 2
            int r1 = android.provider.Settings.System.getInt(r1, r2, r3)
            java.lang.String r2 = "getChargingInfoAlways = "
            java.lang.String r4 = "SecDisplayUtils"
            androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0.m1m(r1, r2, r4)
            if (r1 != r3) goto L62
            java.lang.String r2 = "content://com.samsung.android.app.clockpack.provider/clock_pack_settings/charging_info_always_enabled"
            r3 = 0
            android.content.ContentResolver r5 = r11.getContentResolver()     // Catch: java.lang.Throwable -> L4c java.lang.UnsupportedOperationException -> L4e
            android.net.Uri r6 = android.net.Uri.parse(r2)     // Catch: java.lang.Throwable -> L4c java.lang.UnsupportedOperationException -> L4e
            r9 = 0
            r10 = 0
            r7 = 0
            r8 = 0
            android.database.Cursor r3 = r5.query(r6, r7, r8, r9, r10)     // Catch: java.lang.Throwable -> L4c java.lang.UnsupportedOperationException -> L4e
            if (r3 == 0) goto L50
            int r2 = r3.getCount()     // Catch: java.lang.Throwable -> L4c java.lang.UnsupportedOperationException -> L4e
            if (r2 <= 0) goto L50
            r3.moveToFirst()     // Catch: java.lang.Throwable -> L4c java.lang.UnsupportedOperationException -> L4e
            r2 = 0
            int r1 = r3.getInt(r2)     // Catch: java.lang.Throwable -> L4c java.lang.UnsupportedOperationException -> L4e
            setChargingInfoAlways(r11, r1)     // Catch: java.lang.Throwable -> L4c java.lang.UnsupportedOperationException -> L4e
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4c java.lang.UnsupportedOperationException -> L4e
            r11.<init>(r0)     // Catch: java.lang.Throwable -> L4c java.lang.UnsupportedOperationException -> L4e
            r11.append(r1)     // Catch: java.lang.Throwable -> L4c java.lang.UnsupportedOperationException -> L4e
            java.lang.String r11 = r11.toString()     // Catch: java.lang.Throwable -> L4c java.lang.UnsupportedOperationException -> L4e
            android.util.Log.d(r4, r11)     // Catch: java.lang.Throwable -> L4c java.lang.UnsupportedOperationException -> L4e
            goto L50
        L4c:
            r11 = move-exception
            goto L5c
        L4e:
            r11 = move-exception
            goto L56
        L50:
            if (r3 == 0) goto L62
        L52:
            r3.close()
            goto L62
        L56:
            r11.printStackTrace()     // Catch: java.lang.Throwable -> L4c
            if (r3 == 0) goto L62
            goto L52
        L5c:
            if (r3 == 0) goto L61
            r3.close()
        L61:
            throw r11
        L62:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.display.SecDisplayUtils.getChargingInfoAlways(android.content.Context):int");
    }

    public static int getCurrentDensity(Context context) {
        int intForUser =
                Settings.Secure.getIntForUser(
                        context.getContentResolver(), "display_density_forced", -1, 0);
        if (intForUser == -1) {
            try {
                intForUser =
                        WindowManagerGlobal.getWindowManagerService().getInitialDisplayDensity(0);
            } catch (RemoteException unused) {
                Log.secD("SecDisplayUtils", "getInitialDisplaySize() exception!!!");
                return -1;
            }
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                intForUser, "currentDensity = ", "SecDisplayUtils");
        return intForUser;
    }

    public static String getCurrentFontName(Context context) {
        if (context == null) {
            return ApnSettings.MVNO_NONE;
        }
        String semGetFontPathOfCurrentFontStyle =
                Typeface.semGetFontPathOfCurrentFontStyle(context, 1);
        String[] split = semGetFontPathOfCurrentFontStyle.split("/");
        if (split.length - 1 > 0) {
            semGetFontPathOfCurrentFontStyle = split[split.length - 1];
        }
        if ("monotype".equalsIgnoreCase(semGetFontPathOfCurrentFontStyle)
                || "default".equalsIgnoreCase(semGetFontPathOfCurrentFontStyle)
                || ApnSettings.MVNO_NONE.equals(semGetFontPathOfCurrentFontStyle)) {
            semGetFontPathOfCurrentFontStyle =
                    (String) context.getResources().getText(R.string.sec_monotype_default);
        }
        if (TextUtils.isEmpty(semGetFontPathOfCurrentFontStyle)) {
            return ApnSettings.MVNO_NONE;
        }
        Resources resources = context.getResources();
        return semGetFontPathOfCurrentFontStyle.equals(
                        resources.getText(R.string.sec_monotype_default))
                ? semGetFontPathOfCurrentFontStyle
                : semGetFontPathOfCurrentFontStyle.equals("Rosemary")
                        ? (String) resources.getText(R.string.sec_monotype_dialog_font_rose)
                        : semGetFontPathOfCurrentFontStyle.equals("Chococooky")
                                ? (String)
                                        resources.getText(R.string.sec_monotype_dialog_font_choco)
                                : semGetFontPathOfCurrentFontStyle.equals("Cooljazz")
                                        ? (String)
                                                resources.getText(
                                                        R.string.sec_monotype_dialog_font_cool)
                                        : semGetFontPathOfCurrentFontStyle.equals("Samsungsans")
                                                ? "Samsung Sans"
                                                : semGetFontPathOfCurrentFontStyle.equals(
                                                                "SamsungOneUI-Regular")
                                                        ? "SamsungOne"
                                                        : semGetFontPathOfCurrentFontStyle.equals(
                                                                        "Applemint")
                                                                ? (String)
                                                                        resources.getText(
                                                                                R.string
                                                                                        .sec_monotype_dialog_font_applemint)
                                                                : semGetFontPathOfCurrentFontStyle
                                                                                .equals(
                                                                                        "Tinkerbell")
                                                                        ? (String)
                                                                                resources.getText(
                                                                                        R.string
                                                                                                .sec_monotype_dialog_font_tinkerbell)
                                                                        : semGetFontPathOfCurrentFontStyle
                                                                                        .equals(
                                                                                                "Shaonv")
                                                                                ? (String)
                                                                                        resources
                                                                                                .getText(
                                                                                                        R
                                                                                                                .string
                                                                                                                .sec_monotype_dialog_font_girl)
                                                                                : semGetFontPathOfCurrentFontStyle
                                                                                                .equals(
                                                                                                        "Kaiti")
                                                                                        ? (String)
                                                                                                resources
                                                                                                        .getText(
                                                                                                                R
                                                                                                                        .string
                                                                                                                        .sec_monotype_dialog_font_kaiti)
                                                                                        : semGetFontPathOfCurrentFontStyle
                                                                                                        .equals(
                                                                                                                "Miao")
                                                                                                ? (String)
                                                                                                        resources
                                                                                                                .getText(
                                                                                                                        R
                                                                                                                                .string
                                                                                                                                .sec_monotype_dialog_font_miao)
                                                                                                : semGetFontPathOfCurrentFontStyle
                                                                                                                .equals(
                                                                                                                        "Cool")
                                                                                                        ? (String)
                                                                                                                resources
                                                                                                                        .getText(
                                                                                                                                R
                                                                                                                                        .string
                                                                                                                                        .sec_monotype_dialog_font_cool)
                                                                                                        : semGetFontPathOfCurrentFontStyle
                                                                                                                        .equals(
                                                                                                                                "Rose")
                                                                                                                ? (String)
                                                                                                                        resources
                                                                                                                                .getText(
                                                                                                                                        R
                                                                                                                                                .string
                                                                                                                                                .sec_monotype_dialog_font_rose)
                                                                                                                : semGetFontPathOfCurrentFontStyle
                                                                                                                                .equals(
                                                                                                                                        "Choco")
                                                                                                                        ? (String)
                                                                                                                                resources
                                                                                                                                        .getText(
                                                                                                                                                R
                                                                                                                                                        .string
                                                                                                                                                        .sec_monotype_dialog_font_choco)
                                                                                                                        : semGetFontPathOfCurrentFontStyle
                                                                                                                                        .equals(
                                                                                                                                                "Foundation")
                                                                                                                                ? (String)
                                                                                                                                        resources
                                                                                                                                                .getText(
                                                                                                                                                        R
                                                                                                                                                                .string
                                                                                                                                                                .sec_monotype_dialog_font_gothicbold)
                                                                                                                                : Typeface
                                                                                                                                        .getFontNameFlipFont();
    }

    public static CharSequence[] getDexScreenTimeoutEntryandValue(int i, long j, Context context) {
        int i2;
        int i3 = 0;
        String[] stringArray = context.getResources().getStringArray(R.array.dex_timeout_entries);
        String[] stringArray2 = context.getResources().getStringArray(R.array.dex_timeout_values);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (isInDefaultTimeoutList(stringArray2, j)) {
            if (i == 1) {
                return stringArray;
            }
            if (i == 2) {
                return stringArray2;
            }
            return null;
        }
        int i4 = -1;
        for (int i5 = 0; i5 < stringArray2.length; i5++) {
            arrayList.add(stringArray[i5]);
            arrayList2.add(stringArray2[i5]);
            if (j >= Long.parseLong(stringArray2[i5].toString())) {
                i4 = i5 + 1;
            } else if (i4 == -1) {
                i4 = i5;
            }
        }
        if (i4 != -1) {
            StringBuilder sb = new StringBuilder("(");
            int[] iArr = {
                R.plurals.dex_timeout_week,
                R.plurals.dex_timeout_day,
                R.plurals.dex_timeout_hour,
                R.plurals.dex_timeout_minute,
                R.plurals.dex_timeout_second
            };
            long j2 = j / 1000;
            long j3 = j2 / 60;
            long j4 = j3 / 60;
            long j5 = j4 / 24;
            long[] jArr = {j5 / 7, j5 % 7, j4 % 24, j3 % 60, j2 % 60};
            String str = ApnSettings.MVNO_NONE;
            for (int i6 = 5; i3 < i6; i6 = 5) {
                long j6 = jArr[i3];
                if (j6 > 0) {
                    if (!str.isEmpty()) {
                        str = str.concat(" ");
                    }
                    StringBuilder m =
                            EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0
                                    .m(str);
                    int i7 = (int) j6;
                    m.append(
                            String.format(
                                    context.getResources().getQuantityString(iArr[i3], i7),
                                    Integer.valueOf(i7)));
                    str = m.toString();
                }
                i3++;
            }
            Log.d("SecDisplayUtils", "getTimeoutNewEntry : " + str);
            sb.append(str);
            sb.append(")");
            arrayList.add(i4, sb.toString());
            arrayList2.add(i4, String.valueOf(j));
            i2 = 1;
        } else {
            i2 = 1;
        }
        if (i == i2) {
            return (CharSequence[]) arrayList.toArray(new CharSequence[arrayList.size()]);
        }
        if (i == 2) {
            return (CharSequence[]) arrayList2.toArray(new CharSequence[arrayList2.size()]);
        }
        return null;
    }

    public static Intent getFontDownloadMarketIntent(Context context) {
        String str =
                (String) context.getResources().getText(R.string.sec_monotype_android_market_uri);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        intent.addFlags(335544352);
        return intent;
    }

    public static float getFontScale(Context context, int i) {
        if (i < 0) {
            i = 0;
        } else if (i > 7) {
            i = 7;
        }
        return Float.parseFloat(
                context.getResources().getStringArray(R.array.sec_entryvalues_8_step_font_size)[i]);
    }

    public static Intent getFontSizeChangedIntent() {
        Intent intent = new Intent("com.samsung.settings.FONT_SIZE_CHANGED");
        intent.addFlags(16777216);
        return intent;
    }

    public static int getHighRefreshRateDefaultValue(Context context, int i) {
        int highRefreshRateSeamlessType = getHighRefreshRateSeamlessType(i);
        if (highRefreshRateSeamlessType == 0) {
            return 0;
        }
        int parseInt = Integer.parseInt(DATA.DM_FIELD_INDEX.RCS);
        if (highRefreshRateSeamlessType == 1) {
            if (parseInt > 60 || Rune.isLDUModel() || Rune.isShopDemo(context)) {
                return 2;
            }
        } else if ((highRefreshRateSeamlessType == 2
                        || highRefreshRateSeamlessType == 3
                        || highRefreshRateSeamlessType == 4)
                && parseInt > 60) {
            return !FactoryTest.isFactoryBinary() ? 1 : 2;
        }
        return 0;
    }

    public static int getHighRefreshRateMaxValue() {
        int i = Integer.MIN_VALUE;
        for (String str : getHighRefreshRateSupportedValues(999)) {
            if (!str.isEmpty()) {
                i = Math.max(i, Integer.parseInt(str));
            }
        }
        return i;
    }

    public static int getHighRefreshRateSeamlessType(int i) {
        if (i == 1) {
            return Integer.parseInt(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
        }
        if (i != 999) {
            return Integer.parseInt(DATA.DM_FIELD_INDEX.PUBLIC_USER_ID);
        }
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return getHighRefreshRateSeamlessType(-1);
    }

    public static String[] getHighRefreshRateSupportedValues(int i) {
        if (i == 1) {
            return ApnSettings.MVNO_NONE.split(",");
        }
        if (i != 999) {
            return "24,10,30,48,60,80,120".split(",");
        }
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return getHighRefreshRateSupportedValues(-1);
    }

    public static int getIntRefreshRate(Context context, int i) {
        String refreshRateKey = getRefreshRateKey(i);
        int highRefreshRateSeamlessType = getHighRefreshRateSeamlessType(i);
        int highRefreshRateDefaultValue = getHighRefreshRateDefaultValue(context, i);
        if (highRefreshRateSeamlessType != 0) {
            return Settings.Secure.getInt(
                    context.getContentResolver(), refreshRateKey, highRefreshRateDefaultValue);
        }
        return 0;
    }

    public static Typeface getPreviewFont(Context context, Uri uri) {
        try {
            mPreviewFontFile = File.createTempFile("preview_font_", ".ttf");
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = mPreviewFontFile;
        try {
            ParcelFileDescriptor openFileDescriptor =
                    context.getContentResolver().openFileDescriptor(uri, "r");
            try {
                FileInputStream fileInputStream =
                        new FileInputStream(openFileDescriptor.getFileDescriptor());
                try {
                    FileOutputStream fileOutputStream =
                            new FileOutputStream(file.getAbsoluteFile(), true);
                    try {
                        byte[] bArr = new byte[8192];
                        while (true) {
                            int read = fileInputStream.read(bArr);
                            if (read == -1) {
                                fileOutputStream.close();
                                fileInputStream.close();
                                openFileDescriptor.close();
                                return Typeface.createFromFile(mPreviewFontFile);
                            }
                            fileOutputStream.write(bArr, 0, read);
                        }
                    } finally {
                    }
                } finally {
                }
            } catch (Throwable th) {
                if (openFileDescriptor != null) {
                    try {
                        openFileDescriptor.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (IOException | NullPointerException e2) {
            Log.d("SecDisplayUtils", "Failed to copy preview font file. " + e2);
            return Typeface.DEFAULT;
        }
    }

    public static int[] getProperDensities(Context context, float[] fArr) {
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        int i = 0;
        if (Utils.isTablet()) {
            ArrayList properTabletDensities = getProperTabletDensities();
            if (properTabletDensities == null) {
                return null;
            }
            int[] iArr = new int[properTabletDensities.size()];
            while (i < properTabletDensities.size()) {
                iArr[i] = ((Integer) properTabletDensities.get(i)).intValue();
                i++;
            }
            return iArr;
        }
        int[] iArr2 = new int[fArr.length];
        int screenResolution = getScreenResolution(context);
        float[] fArr2 = {0.5f, 0.75f, 1.0f};
        try {
            int initialDisplayDensity =
                    (int)
                            (WindowManagerGlobal.getWindowManagerService()
                                            .getInitialDisplayDensity(0)
                                    * fArr2[screenResolution]);
            Log.d("SecDisplayUtils", "baseDensity : " + initialDisplayDensity);
            int i2 = 1;
            for (int i3 = 0; i3 < fArr.length; i3++) {
                iArr2[i3] = (int) (fArr[i3] * 160.0f * fArr2[screenResolution]);
                Log.d("SecDisplayUtils", "convertedDensity[" + i3 + "] : " + iArr2[i3]);
                if (iArr2[i3] == initialDisplayDensity) {
                    i2 = i3;
                }
            }
            ArrayList arrayList = new ArrayList();
            double d = iArr2[i2];
            double d2 = 0.85d * d;
            double d3 = d * 1.5d;
            int length = fArr.length;
            if (d2 <= iArr2[0] && d3 >= iArr2[length - 1]) {
                return iArr2;
            }
            for (int i4 = 0; i4 < length; i4++) {
                int i5 = iArr2[i4];
                double d4 = i5;
                if (d4 >= d2 && d4 <= d3) {
                    arrayList.add(Integer.valueOf(i5));
                }
            }
            int[] iArr3 = new int[arrayList.size()];
            while (i < arrayList.size()) {
                iArr3[i] = ((Integer) arrayList.get(i)).intValue();
                StringBuilder m =
                        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "resultDensity[", "] : ");
                m.append(arrayList.get(i));
                Log.d("SecDisplayUtils", m.toString());
                i++;
            }
            return iArr3;
        } catch (RemoteException unused) {
            Log.d("SecDisplayUtils", "getInitialDisplaySize() exception!!!");
            return null;
        }
    }

    public static ArrayList getProperTabletDensities() {
        int i;
        int[] iArr = {
            213,
            IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp,
            IKnoxCustomManager.Stub.TRANSACTION_getForceSingleView,
            FileType.XLSX,
            360,
            VolteConstants.ErrorCode.BAD_EXTENSION,
            VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE,
            540
        };
        int[] iArr2 = {140, 160, 180, 210, IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp};
        int[] iArr3 = {
            260,
            300,
            FileType.CELL,
            VolteConstants.ErrorCode.ALTERNATIVE_SERVICES,
            VolteConstants.ErrorCode.BAD_EXTENSION
        };
        int[] iArr4 = {
            300,
            FileType.CELL,
            VolteConstants.ErrorCode.ALTERNATIVE_SERVICES,
            VolteConstants.ErrorCode.BAD_EXTENSION,
            VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE
        };
        int[] iArr5 = {
            IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp,
            IKnoxCustomManager.Stub.TRANSACTION_getForceSingleView,
            FileType.XLSX,
            360,
            400
        };
        int[] iArr6 = {
            IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp,
            IKnoxCustomManager.Stub.TRANSACTION_getForceSingleView,
            FileType.XLSX,
            360,
            VolteConstants.ErrorCode.BAD_EXTENSION
        };
        int[] iArr7 = {
            213,
            IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp,
            IKnoxCustomManager.Stub.TRANSACTION_getForceSingleView
        };
        ArrayList arrayList = new ArrayList();
        try {
            int initialDisplayDensity =
                    WindowManagerGlobal.getWindowManagerService().getInitialDisplayDensity(0);
            String trim = SystemProperties.get("ro.product.device", "NONE").trim();
            if (initialDisplayDensity == 160) {
                i = 1;
                for (int i2 = 0; i2 < 5; i2++) {
                    int i3 = iArr2[i2];
                    if (i3 == initialDisplayDensity) {
                        i = i2;
                    }
                    arrayList.add(Integer.valueOf(i3));
                }
            } else if (initialDisplayDensity == 300) {
                i = 1;
                for (int i4 = 0; i4 < 5; i4++) {
                    int i5 = iArr3[i4];
                    if (i5 == initialDisplayDensity) {
                        i = i4;
                    }
                    arrayList.add(Integer.valueOf(i5));
                }
            } else if (initialDisplayDensity == 340) {
                i = 1;
                for (int i6 = 0; i6 < 5; i6++) {
                    int i7 = iArr4[i6];
                    if (i7 == initialDisplayDensity) {
                        i = i6;
                    }
                    arrayList.add(Integer.valueOf(i7));
                }
            } else if (trim.startsWith("gts9fe")) {
                i = 1;
                for (int i8 = 0; i8 < 5; i8++) {
                    int i9 = iArr5[i8];
                    if (i9 == initialDisplayDensity) {
                        i = i8;
                    }
                    arrayList.add(Integer.valueOf(i9));
                }
            } else if (trim.startsWith("gtmotorwifi")) {
                int i10 = 1;
                for (int i11 = 0; i11 < 3; i11++) {
                    int i12 = iArr7[i11];
                    if (i12 == initialDisplayDensity) {
                        i10 = i11;
                    }
                    arrayList.add(Integer.valueOf(i12));
                }
                i = i10;
            } else {
                if (!trim.startsWith("gts9u") && !trim.startsWith("gts10u")) {
                    int i13 =
                            initialDisplayDensity == 280
                                    ? IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp
                                    : initialDisplayDensity;
                    i = 1;
                    for (int i14 = 0; i14 < 8; i14++) {
                        int i15 = iArr[i14];
                        if (i15 == i13) {
                            i = i14;
                        }
                        arrayList.add(Integer.valueOf(i15));
                    }
                    initialDisplayDensity = i13;
                }
                i = 1;
                for (int i16 = 0; i16 < 5; i16++) {
                    int i17 = iArr6[i16];
                    if (i17 == initialDisplayDensity) {
                        i = i16;
                    }
                    arrayList.add(Integer.valueOf(i17));
                }
            }
            ArrayList arrayList2 = new ArrayList();
            double intValue = ((Integer) arrayList.get(i)).intValue() * 0.85d;
            double intValue2 = ((Integer) arrayList.get(i)).intValue() * 1.5d;
            int i18 =
                    SemSystemProperties.getInt(
                            TouchPadGestureSettingsController.FIRST_API_LEVEL, 0);
            for (int i19 = 0; i19 < arrayList.size(); i19++) {
                if (((Integer) arrayList.get(i19)).intValue() >= intValue
                        && ((Integer) arrayList.get(i19)).intValue() <= intValue2) {
                    int intValue3 =
                            (((Integer) arrayList.get(i19)).intValue() * initialDisplayDensity)
                                    / ((Integer) arrayList.get(i)).intValue();
                    if (intValue3 < 540 || i18 >= 28) {
                        arrayList2.add(Integer.valueOf(intValue3));
                        StringBuilder sb = new StringBuilder("convertedDensity[");
                        sb.append(i19);
                        sb.append("] : ");
                        Preference$$ExternalSyntheticOutline0.m(sb, intValue3, "SecDisplayUtils");
                    }
                }
            }
            return arrayList2;
        } catch (RemoteException unused) {
            Log.d("SecDisplayUtils", "getInitialDisplaySize() exception!!!");
            return null;
        }
    }

    public static String getRefreshRateKey(int i) {
        if (i == 1) {
            return "refresh_rate_mode_cover";
        }
        if (i != 999) {
            return "refresh_rate_mode";
        }
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return getRefreshRateKey(-1);
    }

    public static int getScreenResolution(Context context) {
        int i;
        int i2;
        String[] split;
        String string =
                Settings.Global.getString(context.getContentResolver(), "display_size_forced");
        int i3 = 1;
        if (string == null
                || ApnSettings.MVNO_NONE.equals(string)
                || (split = string.split(",")) == null
                || split.length <= 1) {
            i = 0;
            i2 = 0;
        } else {
            i2 = Integer.parseInt(split[0]);
            i = Integer.parseInt(split[1]);
        }
        if (i2 == 0 || i == 0) {
            Point point = new Point();
            try {
                WindowManagerGlobal.getWindowManagerService().getInitialDisplaySize(0, point);
                Log.secD("SecDisplayUtils", "getScreenSizeInformation() size : " + point);
                i2 = point.x;
            } catch (RemoteException unused) {
                Log.secD("SecDisplayUtils", "getInitialDisplaySize() exception!!!");
                return -1;
            }
        }
        if (i2 >= 1440) {
            i3 = 2;
        } else if (i2 <= 720 || i2 > 1080) {
            i3 = 0;
        }
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                "getScreenResolution : width = ",
                " screenResolution = ",
                i2,
                i3,
                "SecDisplayUtils");
        return i3;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0150 A[EDGE_INSN: B:47:0x0150->B:48:0x0150 BREAK  A[LOOP:1: B:40:0x00f8->B:44:0x014d], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01e6  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01f3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.CharSequence[] getScreenTimeoutEntryandValue(
            android.content.Context r19, java.lang.Long r20, int r21) {
        /*
            Method dump skipped, instructions count: 517
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.display.SecDisplayUtils.getScreenTimeoutEntryandValue(android.content.Context,"
                    + " java.lang.Long, int):java.lang.CharSequence[]");
    }

    public static int[] getScreenZoomInfo(Context context) {
        ArrayList arrayList;
        int[] iArr = new int[2];
        int i = 0;
        if (Utils.isTablet()) {
            arrayList = getProperTabletDensities();
            if (arrayList == null) {
                return null;
            }
        } else {
            arrayList = new ArrayList();
            for (int i2 :
                    getProperDensities(
                            context, SecScreenZoomPreferenceFragment.DENSITY_BASE_PIXEL)) {
                arrayList.add(Integer.valueOf(i2));
            }
        }
        iArr[0] = arrayList.size();
        int currentDensity = getCurrentDensity(context);
        while (true) {
            if (i >= arrayList.size()) {
                break;
            }
            if (currentDensity == ((Integer) arrayList.get(i)).intValue()) {
                iArr[1] = i;
                break;
            }
            i++;
        }
        return iArr;
    }

    public static String getStringFromMillis(Context context, long j) {
        if (j < 0 || j >= 1440) {
            return DateFormat.getTimeFormat(context).format(Long.valueOf(j));
        }
        Calendar calendar = Calendar.getInstance();
        int i = (int) j;
        calendar.set(11, i / 60);
        calendar.set(12, i % 60);
        return DateFormat.getTimeFormat(context).format(calendar.getTime());
    }

    public static CharSequence getTimeoutDescription(
            long j, CharSequence[] charSequenceArr, CharSequence[] charSequenceArr2) {
        if (j < 0
                || charSequenceArr2 == null
                || charSequenceArr2.length != charSequenceArr.length) {
            return null;
        }
        int i = -1;
        for (int i2 = 0; i2 < charSequenceArr2.length; i2++) {
            if (j >= Long.parseLong(charSequenceArr2[i2].toString())) {
                i = i2;
            }
        }
        if (i > -1) {
            return charSequenceArr[i];
        }
        return null;
    }

    public static String getTimeoutNewEntry(Context context, long j) {
        String str;
        long j2 = j / 1000;
        long j3 = j2 / 60;
        long j4 = j3 / 60;
        long j5 = j2 % 60;
        if (j4 > 0) {
            int i = (int) j4;
            str =
                    String.format(
                            context.getResources()
                                    .getQuantityString(R.plurals.lock_timeout_hours, i),
                            Integer.valueOf(i));
            j3 %= 60;
        } else {
            str = ApnSettings.MVNO_NONE;
        }
        if (j4 > 0 && j3 > 0) {
            str = str.concat(" ");
        }
        if (j3 > 0) {
            StringBuilder m =
                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(str);
            int i2 = (int) j3;
            m.append(
                    String.format(
                            context.getResources()
                                    .getQuantityString(R.plurals.lock_timeout_minutes, i2),
                            Integer.valueOf(i2)));
            str = m.toString();
        }
        if (j3 > 0 && j5 > 0) {
            str = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, " ");
        }
        if (j5 > 0) {
            StringBuilder m2 =
                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(str);
            int i3 = (int) j5;
            m2.append(
                    String.format(
                            context.getResources()
                                    .getQuantityString(R.plurals.lock_timeout_seconds, i3),
                            Integer.valueOf(i3)));
            str = m2.toString();
        }
        Log.secD("SecDisplayUtils", "getTimeoutNewEntry : " + str);
        return str;
    }

    public static boolean isAccessibilityVisionEnabled(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        return (Settings.System.getInt(contentResolver, "greyscale_mode", 0) == 0
                        && Settings.System.getInt(contentResolver, "high_contrast", 0) == 0
                        && Settings.System.getInt(contentResolver, "color_blind", 0) == 0
                        && Settings.Secure.getInt(contentResolver, "color_lens_switch", 0) == 0)
                ? false
                : true;
    }

    public static boolean isDefaultLauncher(PackageManager packageManager) {
        if (packageManager != null) {
            ArrayList arrayList = new ArrayList();
            ComponentName homeActivities = packageManager.getHomeActivities(arrayList);
            if (homeActivities != null) {
                Log.secD(
                        "SecDisplayUtils",
                        "isDefaultLauncher : "
                                + homeActivities
                                        .getPackageName()
                                        .equals("com.sec.android.app.launcher"));
                return homeActivities.getPackageName().equals("com.sec.android.app.launcher");
            }
            if (arrayList.size() == 1) {
                ResolveInfo resolveInfo = (ResolveInfo) arrayList.get(0);
                if (resolveInfo.activityInfo != null) {
                    Log.secD(
                            "SecDisplayUtils",
                            "isDefaultLauncher : "
                                    + resolveInfo.activityInfo.packageName.equals(
                                            "com.sec.android.app.launcher"));
                    return resolveInfo.activityInfo.packageName.equals(
                            "com.sec.android.app.launcher");
                }
            }
        }
        Log.secD("SecDisplayUtils", "isDefaultLauncher : true");
        return true;
    }

    public static boolean isInDefaultTimeoutList(CharSequence[] charSequenceArr, long j) {
        if (charSequenceArr == null) {
            return false;
        }
        for (CharSequence charSequence : charSequenceArr) {
            if (Long.valueOf(charSequence.toString()).longValue() == j) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00ab A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isInvalidFont(android.content.Context r16, java.lang.String r17) {
        /*
            Method dump skipped, instructions count: 465
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.display.SecDisplayUtils.isInvalidFont(android.content.Context,"
                    + " java.lang.String):boolean");
    }

    public static boolean isScreenSaverEnabled(Context context) {
        try {
            return DreamBackend.getInstance(context).isEnabled();
        } catch (SecurityException unused) {
            Log.d("SecDisplayUtils", " isScreenSaverEnabled security exception");
            return false;
        }
    }

    public static boolean isSharedFont(PackageManager packageManager, String str) {
        ApplicationInfo applicationInfo;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            applicationInfo = packageManager.getApplicationInfo(str, 128);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            applicationInfo = null;
        }
        if (applicationInfo != null) {
            String installerPackageName = packageManager.getInstallerPackageName(str);
            boolean z = (applicationInfo.flags & 1) != 0;
            boolean z2 =
                    installerPackageName == null
                            || installerPackageName.contains("packageinstaller");
            android.util.secutil.Log.d(
                    "SecDisplayUtils", "Flip font is preload : " + z + " , isSharedFont : " + z2);
            if (!z && z2) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSupportMaxHS60RefreshRate(int i) {
        if (i == 1) {
            return Integer.parseInt(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN) >= 2;
        }
        if (i == 999) {
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            return isSupportMaxHS60RefreshRate(-1);
        }
        if (Integer.parseInt(DATA.DM_FIELD_INDEX.PUBLIC_USER_ID) >= 2) {
            return (TextUtils.isEmpty("24,10,30,48,60,80,120")
                            ? 60
                            : Arrays.stream("24,10,30,48,60,80,120".split(","))
                                    .mapToInt(
                                            new EnabledNetworkModePreferenceController$PreferenceEntriesBuilder$$ExternalSyntheticLambda3())
                                    .boxed()
                                    .min(new SecDisplayUtils$$ExternalSyntheticLambda0())
                                    .get()
                                    .intValue())
                    < 60;
        }
        return false;
    }

    public static void putIntRefreshRate(Context context, int i, int i2) {
        String refreshRateKey = getRefreshRateKey(i2);
        if (getHighRefreshRateSeamlessType(i2) != 0) {
            Settings.Secure.putInt(context.getContentResolver(), refreshRateKey, i);
        }
    }

    public static void setAutoBrightnessWithCamera(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        if (!Rune.supportCameraSensor(context)
                || Settings.System.getInt(contentResolver, "adaptive_brightness", 0) == 1) {
            return;
        }
        Settings.System.putInt(contentResolver, "adaptive_brightness", 1);
        Settings.System.putInt(contentResolver, "adaptive_brightness_toggled", 1);
        Intent intent = new Intent();
        intent.setAction("samsung.intent.action.START_AUTO_BRIGHTNESS_ROOT");
        intent.setComponent(
                new ComponentName(
                        "com.samsung.adaptivebrightnessgo",
                        "com.samsung.adaptivebrightnessgo.RootService"));
        intent.putExtra(ImIntent.Extras.EXTRA_FROM, "settings");
        try {
            context.startService(intent);
        } catch (Exception e) {
            Log.e("SecDisplayUtils", "Error while calling adaptive brightness = ", e);
        }
    }

    public static void setChargingInfoAlways(Context context, int i) {
        Settings.System.putInt(
                context.getContentResolver(),
                SecShowChargingInfoPreferenceController.SETTINGS_KEY_CHARGING_INFO_ALWAYS,
                i);
        Log.d("SecDisplayUtils", "setChargingInfoAlways : " + i);
    }

    public static void setDisplayColor(int i) {
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_VIVIDPLUS")
                        == 2
                || SemFloatingFeature.getInstance()
                                .getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_VIVIDPLUS")
                        == 3) {
            SystemProperties.set("persist.sys.sf.vividplus_mode", Integer.toString(i));
            i = 0;
        }
        try {
            WindowManagerGlobal.getWindowManagerService().setDisplayColorToSystemProperties(i);
        } catch (Exception e) {
            Log.e("SecDisplayUtils", "Failed to set display color to system properties", e);
        }
    }

    public static void setDisplayEasyModeDensity(Context context, int i) {
        int i2;
        int i3;
        Point point = new Point();
        IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
        try {
            windowManagerService.getInitialDisplaySize(0, point);
            if (i == 0) {
                String string =
                        Settings.Global.getString(
                                context.getContentResolver(), "display_size_forced");
                if (string == null || ApnSettings.MVNO_NONE.equals(string)) {
                    i3 = point.x;
                } else {
                    String[] split = string.split(",");
                    i3 =
                            (split == null || split.length <= 1)
                                    ? point.x
                                    : Integer.parseInt(split[0]);
                }
                int currentDensity = getCurrentDensity(context);
                int i4 = (int) ((point.x / i3) * currentDensity);
                Settings.System.putInt(
                        context.getContentResolver(), "preserved_density_standard_mode", i4);
                Log.d(
                        "SecDisplayUtils",
                        "set Display to Easy mode : current density = "
                                + currentDensity
                                + ", preserved density = "
                                + i4);
                int[] properDensities =
                        getProperDensities(
                                context, SecScreenZoomPreferenceFragment.DENSITY_BASE_PIXEL);
                i2 = properDensities[properDensities.length - 1];
            } else {
                int i5 =
                        Settings.System.getInt(
                                context.getContentResolver(), "preserved_density_standard_mode", 0);
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        i5,
                        "set Display to Standard mode : restored density = ",
                        "SecDisplayUtils");
                if (i5 == 0 || i5 == -1) {
                    try {
                        i5 = windowManagerService.getInitialDisplayDensity(0);
                    } catch (RemoteException unused) {
                        Log.d("SecDisplayUtils", "getInitialDisplayDensity() exception!!!");
                    }
                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                            i5,
                            "(Protection) set Display to Standard mode : restored density = ",
                            "SecDisplayUtils");
                }
                String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                int i6 = point.x / 360;
                int screenResolution = getScreenResolution(context) + 2;
                float f = screenResolution / i6;
                StringBuilder m =
                        RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                                "targetPx = ",
                                ", basePx = ",
                                screenResolution,
                                i6,
                                ", convertRatio = ");
                m.append(f);
                Log.d("SecDisplayUtils", m.toString());
                i2 = (int) (i5 * f);
            }
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    "setDisplayEasyModeDensity -> reqMode : ",
                    ", density : ",
                    i,
                    i2,
                    "SecDisplayUtils");
            applyForcedDisplayDensity(-1, -1, i2);
        } catch (RemoteException unused2) {
            Log.secD("SecDisplayUtils", "getInitialDisplaySize() exception!!!");
        }
    }

    public static void setScreenZoomInfo(Context context, int[] iArr) {
        ArrayList arrayList;
        if (Utils.isTablet()) {
            arrayList = getProperTabletDensities();
            if (arrayList == null) {
                return;
            }
        } else {
            ArrayList arrayList2 = new ArrayList();
            for (int i :
                    getProperDensities(
                            context, SecScreenZoomPreferenceFragment.DENSITY_BASE_PIXEL)) {
                arrayList2.add(Integer.valueOf(i));
            }
            arrayList = arrayList2;
        }
        int i2 = iArr[1];
        int size = arrayList.size();
        int i3 = iArr[0];
        if (size < i3) {
            Log.d(
                    "SecDisplayUtils",
                    "setScreenZoomInfo(), screenZoomRange is smaller than origin device, blocked"
                        + " restoring");
            return;
        }
        if (size == 5 && i3 == 3) {
            i2 *= 2;
        }
        if (size - 1 < i2) {
            i2 = arrayList.size() - 1;
        }
        applyForcedDisplayDensity(-1, -1, ((Integer) arrayList.get(i2)).intValue());
    }

    public static void setSelectedScreenResolution(Context context, int i) {
        int i2;
        int i3;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        Point point = new Point();
        try {
            IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
            windowManagerService.getInitialDisplaySize(0, point);
            int initialDisplayDensity = windowManagerService.getInitialDisplayDensity(0);
            Log.secD("SecDisplayUtils", "getInitialDisplaySize() size : " + point);
            int intForUser =
                    Settings.Secure.getIntForUser(
                            context.getContentResolver(),
                            "display_density_forced",
                            initialDisplayDensity,
                            0);
            Log.d("SecDisplayUtils", "currentDensity = " + intForUser);
            Log.d("SecDisplayUtils", "defaultDensity = " + initialDisplayDensity);
            String string =
                    Settings.Global.getString(context.getContentResolver(), "display_size_forced");
            if (string == null || ApnSettings.MVNO_NONE.equals(string)) {
                i2 = point.x;
            } else {
                String[] split = string.split(",");
                i2 = (split == null || split.length <= 1) ? point.x : Integer.parseInt(split[0]);
            }
            Log.d("SecDisplayUtils", "currentWidth = " + i2);
            if (Settings.System.getInt(context.getContentResolver(), "easy_mode_switch", 1) == 0) {
                i3 = (int) ((point.x / 1440.0f) * 720.0f);
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        i3, "easymode)defaultDensity = ", "SecDisplayUtils");
            } else {
                i3 = (int) ((point.x / i2) * intForUser);
            }
            Log.secD("SecDisplayUtils", "setSelectedScreenResolution() density : " + i3);
            int i4 = point.x / 360;
            int i5 = i != 0 ? i != 1 ? i != 2 ? i4 : 4 : 3 : 2;
            float f = i5 / i4;
            Log.d("SecDisplayUtils", "basePx : " + i4 + "   targetPx : " + i5);
            StringBuilder sb = new StringBuilder("convertRatio :  ");
            sb.append(f);
            Log.d("SecDisplayUtils", sb.toString());
            if (((int) (point.x / (i3 / 160.0d))) < 320) {
                Log.d(
                        "SecDisplayUtils",
                        "smallestWidth is less then 320!!!!! so set default smallestWidth value");
            } else {
                initialDisplayDensity = i3;
            }
            int i6 = (int) (point.x * f);
            int i7 = (int) (point.y * f);
            int i8 = (int) (initialDisplayDensity * f);
            StringBuilder m =
                    RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                            "set width : ", "  height : ", i6, i7, "  density : ");
            m.append(i8);
            m.append("  setDefault : true");
            Log.secD("SecDisplayUtils", m.toString());
            if (i6 <= 0 || i7 <= 0 || i8 <= 0) {
                return;
            }
            applyForcedDisplayDensity(i6, i7, i8);
        } catch (RemoteException unused) {
            Log.secD("SecDisplayUtils", "getInitialDisplaySize() exception!!!");
        }
    }

    public static String updateTimeoutPreferenceDescription(
            Context context, SecRestrictedPreference secRestrictedPreference, long j) {
        CharSequence timeoutDescription;
        CharSequence[] screenTimeoutEntryandValue =
                getScreenTimeoutEntryandValue(context, Long.valueOf(j), 1);
        return (secRestrictedPreference == null
                        || !secRestrictedPreference.mHelper.mDisabledByAdmin)
                ? (j < 0
                                || screenTimeoutEntryandValue == null
                                || screenTimeoutEntryandValue.length == 0
                                || (timeoutDescription =
                                                getTimeoutDescription(
                                                        j,
                                                        screenTimeoutEntryandValue,
                                                        getScreenTimeoutEntryandValue(
                                                                context, Long.valueOf(j), 2)))
                                        == null)
                        ? ApnSettings.MVNO_NONE
                        : timeoutDescription.toString()
                : context.getString(R.string.disabled_by_policy_title);
    }

    public static void writeFontScaleDBAllUser(Context context, float f) {
        for (UserInfo userInfo : ((UserManager) context.getSystemService("user")).getUsers()) {
            Settings.System.putFloatForUser(
                    context.getContentResolver(), "font_scale", f, userInfo.id);
            Log.d(
                    "SecDisplayUtils",
                    "writeFontScaleDBAllUser() user.id : " + userInfo.id + " / value : " + f);
        }
    }

    public static void writeFontWeightDBAllUser(Context context, int i) {
        for (UserInfo userInfo : ((UserManager) context.getSystemService("user")).getUsers()) {
            Settings.Secure.putIntForUser(
                    context.getContentResolver(), "font_weight_adjustment", i, userInfo.id);
            Log.d(
                    "SecDisplayUtils",
                    "writeFontWeightDBAllUser() user.id : " + userInfo.id + " / value : " + i);
        }
    }
}
