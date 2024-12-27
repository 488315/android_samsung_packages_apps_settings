package com.samsung.android.settings.eternal.provider.items;

import android.app.LocaleManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.LocaleList;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateFormat;

import com.android.internal.app.LocalePicker;
import com.android.internal.app.LocaleStore;
import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.lib.episode.Scene;
import com.samsung.android.lib.episode.SceneResult;
import com.samsung.android.lib.episode.SourceInfo;
import com.samsung.android.settings.inputmethod.AppShortcutsConstants;
import com.samsung.android.settings.inputmethod.MouseFunctionKeyInfo;
import com.samsung.android.sivs.ai.sdkcommon.asr.SpeechRecognitionConst;
import com.samsung.android.util.SemLog;
import com.sec.ims.configuration.DATA;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class GeneralItem implements Recoverable {
    public final List APP_SHORTCUTS_COMMAND_KEY_LIST =
            new ArrayList(
                    Arrays.asList(
                            "/Settings/General/AppShortcutsCommandA",
                            "/Settings/General/AppShortcutsCommandB",
                            "/Settings/General/AppShortcutsCommandC",
                            "/Settings/General/AppShortcutsCommandD",
                            "/Settings/General/AppShortcutsCommandE",
                            "/Settings/General/AppShortcutsCommandF",
                            "/Settings/General/AppShortcutsCommandH",
                            "/Settings/General/AppShortcutsCommandI",
                            "/Settings/General/AppShortcutsCommandJ",
                            "/Settings/General/AppShortcutsCommandK",
                            "/Settings/General/AppShortcutsCommandM",
                            "/Settings/General/AppShortcutsCommandP",
                            "/Settings/General/AppShortcutsCommandR",
                            "/Settings/General/AppShortcutsCommandS",
                            "/Settings/General/AppShortcutsCommandZ"));

    /* JADX WARN: Removed duplicated region for block: B:16:0x0075 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0030 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getAppLanguageInfo(android.content.Context r11) {
        /*
            android.content.pm.PackageManager r0 = r11.getPackageManager()
            java.lang.String r1 = "GeneralItem"
            r2 = 0
            if (r0 != 0) goto Lf
            java.lang.String r11 = "PackageManager is null"
            com.samsung.android.util.SemLog.e(r1, r11)
            return r2
        Lf:
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            org.json.JSONArray r3 = new org.json.JSONArray
            r3.<init>()
            org.json.JSONArray r4 = new org.json.JSONArray
            r4.<init>()
            android.content.pm.PackageManager r5 = r11.getPackageManager()
            r6 = 0
            android.content.pm.PackageManager$ApplicationInfoFlags r6 = android.content.pm.PackageManager.ApplicationInfoFlags.of(r6)
            java.util.List r5 = r5.getInstalledApplications(r6)
            java.util.Iterator r5 = r5.iterator()
        L30:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L85
            java.lang.Object r6 = r5.next()
            android.content.pm.ApplicationInfo r6 = (android.content.pm.ApplicationInfo) r6
            java.lang.String r7 = r6.packageName
            java.lang.Class<android.app.LocaleManager> r8 = android.app.LocaleManager.class
            java.lang.Object r8 = r11.getSystemService(r8)
            android.app.LocaleManager r8 = (android.app.LocaleManager) r8
            if (r8 != 0) goto L4a
            r8 = r2
            goto L4e
        L4a:
            android.os.LocaleList r8 = r8.getApplicationLocales(r7)     // Catch: java.lang.IllegalArgumentException -> L57
        L4e:
            if (r8 != 0) goto L51
            goto L72
        L51:
            r9 = 0
            java.util.Locale r7 = r8.get(r9)     // Catch: java.lang.IllegalArgumentException -> L57
            goto L73
        L57:
            r8 = move-exception
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "package name : "
            r9.<init>(r10)
            r9.append(r7)
            java.lang.String r7 = " is not correct. "
            r9.append(r7)
            r9.append(r8)
            java.lang.String r7 = r9.toString()
            com.samsung.android.util.SemLog.w(r1, r7)
        L72:
            r7 = r2
        L73:
            if (r7 == 0) goto L30
            java.lang.String r6 = r6.packageName
            android.text.TextUtils.isEmpty(r6)
            r3.put(r6)
            java.lang.String r6 = r7.toLanguageTag()
            r4.put(r6)
            goto L30
        L85:
            java.lang.String r11 = "package"
            r0.put(r11, r3)     // Catch: org.json.JSONException -> L91
            java.lang.String r11 = "locale"
            r0.put(r11, r4)     // Catch: org.json.JSONException -> L91
            goto La7
        L91:
            r11 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "JSONException(getAppLanguageInfo) = "
            r3.<init>(r4)
            java.lang.String r11 = r11.getMessage()
            r3.append(r11)
            java.lang.String r11 = r3.toString()
            com.samsung.android.util.SemLog.e(r1, r11)
        La7:
            int r11 = r0.length()
            if (r11 != 0) goto Lae
            goto Lb2
        Lae:
            java.lang.String r2 = r0.toString()
        Lb2:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.provider.items.GeneralItem.getAppLanguageInfo(android.content.Context):java.lang.String");
    }

    public static void setAppDefaultLocale(Context context, String str, String str2) {
        try {
            ((LocaleManager) context.getSystemService(LocaleManager.class))
                    .setApplicationLocales(str, LocaleList.forLanguageTags(str2));
        } catch (IllegalArgumentException e) {
            SemLog.w("GeneralItem", "package name : " + str + " is not correct. " + e);
        }
    }

    public static void setAppLanguageInfo(Context context, String str) {
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONArray("package");
            JSONArray jSONArray2 =
                    new JSONObject(str).getJSONArray(SpeechRecognitionConst.Key.LOCALE);
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                SemLog.e("GeneralItem", "PackageManager is null");
                return;
            }
            for (ApplicationInfo applicationInfo :
                    packageManager.getInstalledApplications(
                            PackageManager.ApplicationInfoFlags.of(0L))) {
                for (int i = 0; i < jSONArray.length(); i++) {
                    String obj = jSONArray.get(i).toString();
                    try {
                        Integer.parseInt(obj);
                        obj = null;
                    } catch (NumberFormatException unused) {
                    }
                    if (applicationInfo.packageName.equalsIgnoreCase(obj)) {
                        setAppDefaultLocale(
                                context, applicationInfo.packageName, jSONArray2.get(i).toString());
                        SemLog.d(
                                "GeneralItem",
                                applicationInfo.packageName + " : " + jSONArray2.get(i).toString());
                    }
                }
            }
        } catch (JSONException e) {
            SemLog.e("GeneralItem", "JSONException(setAppLanguageInfo) = " + e.getMessage());
        } catch (Exception e2) {
            SemLog.e("GeneralItem", "Exception : " + e2.getMessage());
        }
    }

    public static void setKeyCustomization(int i, String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        int indexOf = str.indexOf(47, 0);
        MouseFunctionKeyInfo.setKeyCustomizationInfo(
                i, str.substring(0, indexOf), str.substring(indexOf + 1, str.length()));
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final boolean followRestoreSkipPolicy(Scene scene) {
        return true;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final List getTestScenes(Context context, String str) {
        return null;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final Scene.Builder getValue(Context context, SourceInfo sourceInfo, String str) {
        boolean z;
        char c;
        Scene.Builder builder = new Scene.Builder(str);
        ContentResolver contentResolver = context.getContentResolver();
        try {
            z = true;
            switch (str.hashCode()) {
                case -2115009555:
                    if (str.equals("/Settings/General/SystemLocale")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case -1962430296:
                    if (str.equals("/Settings/General/TimeFormat")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -1858028984:
                    if (str.equals("/Settings/General/CredentialServicePrimary")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -1824478758:
                    if (str.equals("/Settings/General/ShowKeyboardButton")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1764287693:
                    if (str.equals("/Settings/General/LocationTimeZoneDetection")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -1700275479:
                    if (str.equals("/Settings/General/MouseMiddleKey")) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case -1597822571:
                    if (str.equals("/Settings/General/AppLanguage")) {
                        c = '%';
                        break;
                    }
                    c = 65535;
                    break;
                case -1214950126:
                    if (str.equals("/Settings/General/ChangeLanguageShortcut")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -1024419703:
                    if (str.equals("/Settings/General/WheelScrollingSpeed")) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case -86180666:
                    if (str.equals("/Settings/General/AppShortcutsCommandA")) {
                        c = 22;
                        break;
                    }
                    c = 65535;
                    break;
                case -86180665:
                    if (str.equals("/Settings/General/AppShortcutsCommandB")) {
                        c = 23;
                        break;
                    }
                    c = 65535;
                    break;
                case -86180664:
                    if (str.equals("/Settings/General/AppShortcutsCommandC")) {
                        c = 24;
                        break;
                    }
                    c = 65535;
                    break;
                case -86180663:
                    if (str.equals("/Settings/General/AppShortcutsCommandD")) {
                        c = 25;
                        break;
                    }
                    c = 65535;
                    break;
                case -86180662:
                    if (str.equals("/Settings/General/AppShortcutsCommandE")) {
                        c = 26;
                        break;
                    }
                    c = 65535;
                    break;
                case -86180661:
                    if (str.equals("/Settings/General/AppShortcutsCommandF")) {
                        c = 27;
                        break;
                    }
                    c = 65535;
                    break;
                case -86180659:
                    if (str.equals("/Settings/General/AppShortcutsCommandH")) {
                        c = 28;
                        break;
                    }
                    c = 65535;
                    break;
                case -86180658:
                    if (str.equals("/Settings/General/AppShortcutsCommandI")) {
                        c = 29;
                        break;
                    }
                    c = 65535;
                    break;
                case -86180657:
                    if (str.equals("/Settings/General/AppShortcutsCommandJ")) {
                        c = 30;
                        break;
                    }
                    c = 65535;
                    break;
                case -86180656:
                    if (str.equals("/Settings/General/AppShortcutsCommandK")) {
                        c = 31;
                        break;
                    }
                    c = 65535;
                    break;
                case -86180654:
                    if (str.equals("/Settings/General/AppShortcutsCommandM")) {
                        c = ' ';
                        break;
                    }
                    c = 65535;
                    break;
                case -86180651:
                    if (str.equals("/Settings/General/AppShortcutsCommandP")) {
                        c = '!';
                        break;
                    }
                    c = 65535;
                    break;
                case -86180649:
                    if (str.equals("/Settings/General/AppShortcutsCommandR")) {
                        c = '\"';
                        break;
                    }
                    c = 65535;
                    break;
                case -86180648:
                    if (str.equals("/Settings/General/AppShortcutsCommandS")) {
                        c = '#';
                        break;
                    }
                    c = 65535;
                    break;
                case -86180641:
                    if (str.equals("/Settings/General/AppShortcutsCommandZ")) {
                        c = '$';
                        break;
                    }
                    c = 65535;
                    break;
                case 36672730:
                    if (str.equals("/Settings/General/PointerSize")) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case 167611347:
                    if (str.equals("/Settings/General/ShowOnScreenKeyboard")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 385057931:
                    if (str.equals("/Settings/General/EnhancePointerPrecision")) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case 708041049:
                    if (str.equals("/Settings/General/CredentialServices")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 822383980:
                    if (str.equals("/Settings/General/AutoTimeZone")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 1122244010:
                    if (str.equals("/Settings/General/PointerColor")) {
                        c = 21;
                        break;
                    }
                    c = 65535;
                    break;
                case 1137043086:
                    if (str.equals("/Settings/General/PointerSpeed")) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case 1364739736:
                    if (str.equals("/Settings/General/PrimaryMouseKey")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case 1504177682:
                    if (str.equals("/Settings/General/RedirectVibration")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 1673121184:
                    if (str.equals("/Settings/General/AutoTime")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1673657398:
                    if (str.equals("/Settings/General/Autofill")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1942705149:
                    if (str.equals("/Settings/General/AdditionalKey1")) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case 1942705150:
                    if (str.equals("/Settings/General/AdditionalKey2")) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case 2044284300:
                    if (str.equals("/Settings/General/MouseSecondaryKey")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
        } catch (Exception e) {
            SemLog.e("GeneralItem", e.getStackTrace()[0].toString());
        }
        switch (c) {
            case 0:
                builder.setValue(
                        Integer.valueOf(
                                Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1)),
                        false);
                return builder;
            case 1:
                builder.setValue(
                        Integer.valueOf(
                                Settings.System.getInt(
                                        contentResolver,
                                        "enable_language_change_combination_key",
                                        7)),
                        false);
                return builder;
            case 2:
                int i = !Utils.isTablet() ? 1 : 0;
                int i2 = Settings.Secure.getInt(contentResolver, "show_ime_with_hard_keyboard", i);
                builder.setValue(Integer.valueOf(i2), false);
                if (i != i2) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 3:
                String string = Settings.Secure.getString(contentResolver, "autofill_service");
                if (TextUtils.isEmpty(string)) {
                    builder.setValue("dummy", false);
                } else {
                    builder.setValue(string, false);
                }
                return builder;
            case 4:
                String string2 =
                        Settings.Secure.getString(contentResolver, "credential_service_primary");
                if (TextUtils.isEmpty(string2)) {
                    builder.setValue("dummy", false);
                } else {
                    builder.setValue(string2, false);
                    builder.setDefault(
                            string2.equals(
                                    "com.samsung.android.samsungpassautofill/com.samsung.android.samsungpassautofill.CredentialManagerService"));
                }
                return builder;
            case 5:
                String string3 = Settings.Secure.getString(contentResolver, "credential_service");
                if (TextUtils.isEmpty(string3)) {
                    builder.setValue("dummy", false);
                } else {
                    builder.setValue(string3, false);
                }
                return builder;
            case 6:
                String string4 = Settings.System.getString(contentResolver, "time_12_24");
                builder.setValue(string4, false);
                builder.setDefault(TextUtils.isEmpty(string4));
                return builder;
            case 7:
                int i3 = Settings.Global.getInt(contentResolver, "auto_time", 0);
                builder.setValue(String.valueOf(i3), false);
                if (1 != i3) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case '\b':
                int i4 = Settings.Global.getInt(contentResolver, "auto_time_zone", 0);
                builder.setValue(String.valueOf(i4), false);
                if (1 != i4) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case '\t':
                int i5 = Settings.System.getInt(contentResolver, "vibrate_input_devices", 0);
                builder.setValue(String.valueOf(i5), false);
                if (i5 != 0) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case '\n':
                int i6 =
                        Settings.Secure.getInt(
                                contentResolver, "location_time_zone_detection_enabled", 0);
                builder.setValue(String.valueOf(i6), false);
                if (i6 != 0) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 11:
                String string5 = Settings.System.getString(contentResolver, "system_locales");
                if (TextUtils.isEmpty(string5)) {
                    builder.setValue("dummy", false);
                    builder.setValue(Boolean.TRUE, false);
                } else {
                    builder.setValue(string5, false);
                    builder.setDefault(false);
                }
                return builder;
            case '\f':
                int i7 = Settings.System.getInt(contentResolver, "primary_mouse_button_option", 0);
                builder.setValue(String.valueOf(i7), false);
                if (i7 != 0) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case '\r':
                int i8 =
                        Settings.System.getInt(contentResolver, "mouse_secondary_button_option", 1);
                builder.setValue(String.valueOf(i8), false);
                String string6 =
                        Settings.System.getString(contentResolver, "mouse_secondary_button_app");
                if (!TextUtils.isEmpty(string6)) {
                    builder.addAttribute(string6, "appComponent");
                }
                if (1 != i8) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 14:
                int i9 = Settings.System.getInt(contentResolver, "mouse_middle_button_option", 3);
                builder.setValue(String.valueOf(i9), false);
                String string7 =
                        Settings.System.getString(contentResolver, "mouse_middle_button_app");
                if (!TextUtils.isEmpty(string7)) {
                    builder.addAttribute(string7, "appComponent");
                }
                if (3 != i9) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 15:
                int i10 = Settings.System.getInt(contentResolver, "mouse_additional_1_option", 7);
                builder.setValue(String.valueOf(i10), false);
                String string8 =
                        Settings.System.getString(contentResolver, "mouse_additional_1_app");
                if (!TextUtils.isEmpty(string8)) {
                    builder.addAttribute(string8, "appComponent");
                }
                if (7 != i10) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 16:
                int i11 = Settings.System.getInt(contentResolver, "mouse_additional_2_option", 4);
                builder.setValue(String.valueOf(i11), false);
                String string9 =
                        Settings.System.getString(contentResolver, "mouse_additional_2_app");
                if (!TextUtils.isEmpty(string9)) {
                    builder.addAttribute(string9, "appComponent");
                }
                if (4 != i11) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 17:
                int i12 = Settings.System.getInt(contentResolver, "pointer_speed", 0);
                builder.setValue(String.valueOf(i12), false);
                if (i12 != 0) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 18:
                int i13 = Settings.System.getInt(contentResolver, "mouse_scrolling_speed", 1);
                builder.setValue(String.valueOf(i13), false);
                if (1 != i13) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 19:
                int i14 = Settings.System.getInt(contentResolver, "enhance_pointer_precision", 1);
                builder.setValue(String.valueOf(i14), false);
                if (1 != i14) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 20:
                int i15 = Settings.System.getInt(contentResolver, "mouse_pointer_size_step", 1);
                builder.setValue(String.valueOf(i15), false);
                if (1 != i15) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 21:
                int[] intArray =
                        context.getResources().getIntArray(R.array.mouse_pointer_color_array);
                int i16 =
                        Settings.System.getInt(contentResolver, "mouse_pointer_color", intArray[0]);
                builder.setValue(String.valueOf(i16), false);
                if (intArray[0] != i16) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case ' ':
            case '!':
            case '\"':
            case '#':
            case '$':
                int indexOf = ((ArrayList) this.APP_SHORTCUTS_COMMAND_KEY_LIST).indexOf(str);
                String string10 =
                        Settings.System.getString(
                                contentResolver,
                                (String)
                                        ((ArrayList) AppShortcutsConstants.SETTINGS_LIST)
                                                .get(indexOf));
                if (TextUtils.isEmpty(string10)) {
                    string10 =
                            (String)
                                    ((ArrayList) AppShortcutsConstants.DEFAULT_SETTINGS_LIST)
                                            .get(indexOf);
                }
                builder.setValue(string10, false);
                builder.setDefault(
                        string10.equals(
                                ((ArrayList) AppShortcutsConstants.DEFAULT_SETTINGS_LIST)
                                        .get(indexOf)));
                return builder;
            case '%':
                Object appLanguageInfo = getAppLanguageInfo(context);
                if (appLanguageInfo != null) {
                    builder.setValue(appLanguageInfo, true);
                } else {
                    SemLog.d("GeneralItem", "[KEY_GENERAL_APP_LANGUAGE] Data is null");
                }
                return builder;
            default:
                SemLog.d("GeneralItem", "unknown key : ".concat(str));
                return builder;
        }
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final SceneResult isOpenable(Context context, String str) {
        return null;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final SceneResult setValue(
            Context context, String str, Scene scene, SourceInfo sourceInfo) {
        char c;
        ContentResolver contentResolver = context.getContentResolver();
        String value = scene.getValue(null, false);
        boolean isDefault = scene.isDefault();
        SceneResult.Builder builder = new SceneResult.Builder(str);
        builder.mResultType = SceneResult.ResultType.RESULT_OK;
        try {
            switch (str.hashCode()) {
                case -2115009555:
                    if (str.equals("/Settings/General/SystemLocale")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case -1962430296:
                    if (str.equals("/Settings/General/TimeFormat")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -1858028984:
                    if (str.equals("/Settings/General/CredentialServicePrimary")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -1824478758:
                    if (str.equals("/Settings/General/ShowKeyboardButton")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1764287693:
                    if (str.equals("/Settings/General/LocationTimeZoneDetection")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -1700275479:
                    if (str.equals("/Settings/General/MouseMiddleKey")) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case -1597822571:
                    if (str.equals("/Settings/General/AppLanguage")) {
                        c = '%';
                        break;
                    }
                    c = 65535;
                    break;
                case -1214950126:
                    if (str.equals("/Settings/General/ChangeLanguageShortcut")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -1024419703:
                    if (str.equals("/Settings/General/WheelScrollingSpeed")) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case -86180666:
                    if (str.equals("/Settings/General/AppShortcutsCommandA")) {
                        c = 22;
                        break;
                    }
                    c = 65535;
                    break;
                case -86180665:
                    if (str.equals("/Settings/General/AppShortcutsCommandB")) {
                        c = 23;
                        break;
                    }
                    c = 65535;
                    break;
                case -86180664:
                    if (str.equals("/Settings/General/AppShortcutsCommandC")) {
                        c = 24;
                        break;
                    }
                    c = 65535;
                    break;
                case -86180663:
                    if (str.equals("/Settings/General/AppShortcutsCommandD")) {
                        c = 25;
                        break;
                    }
                    c = 65535;
                    break;
                case -86180662:
                    if (str.equals("/Settings/General/AppShortcutsCommandE")) {
                        c = 26;
                        break;
                    }
                    c = 65535;
                    break;
                case -86180661:
                    if (str.equals("/Settings/General/AppShortcutsCommandF")) {
                        c = 27;
                        break;
                    }
                    c = 65535;
                    break;
                case -86180659:
                    if (str.equals("/Settings/General/AppShortcutsCommandH")) {
                        c = 28;
                        break;
                    }
                    c = 65535;
                    break;
                case -86180658:
                    if (str.equals("/Settings/General/AppShortcutsCommandI")) {
                        c = 29;
                        break;
                    }
                    c = 65535;
                    break;
                case -86180657:
                    if (str.equals("/Settings/General/AppShortcutsCommandJ")) {
                        c = 30;
                        break;
                    }
                    c = 65535;
                    break;
                case -86180656:
                    if (str.equals("/Settings/General/AppShortcutsCommandK")) {
                        c = 31;
                        break;
                    }
                    c = 65535;
                    break;
                case -86180654:
                    if (str.equals("/Settings/General/AppShortcutsCommandM")) {
                        c = ' ';
                        break;
                    }
                    c = 65535;
                    break;
                case -86180651:
                    if (str.equals("/Settings/General/AppShortcutsCommandP")) {
                        c = '!';
                        break;
                    }
                    c = 65535;
                    break;
                case -86180649:
                    if (str.equals("/Settings/General/AppShortcutsCommandR")) {
                        c = '\"';
                        break;
                    }
                    c = 65535;
                    break;
                case -86180648:
                    if (str.equals("/Settings/General/AppShortcutsCommandS")) {
                        c = '#';
                        break;
                    }
                    c = 65535;
                    break;
                case -86180641:
                    if (str.equals("/Settings/General/AppShortcutsCommandZ")) {
                        c = '$';
                        break;
                    }
                    c = 65535;
                    break;
                case 36672730:
                    if (str.equals("/Settings/General/PointerSize")) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case 167611347:
                    if (str.equals("/Settings/General/ShowOnScreenKeyboard")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 385057931:
                    if (str.equals("/Settings/General/EnhancePointerPrecision")) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case 708041049:
                    if (str.equals("/Settings/General/CredentialServices")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 822383980:
                    if (str.equals("/Settings/General/AutoTimeZone")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 1122244010:
                    if (str.equals("/Settings/General/PointerColor")) {
                        c = 21;
                        break;
                    }
                    c = 65535;
                    break;
                case 1137043086:
                    if (str.equals("/Settings/General/PointerSpeed")) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case 1364739736:
                    if (str.equals("/Settings/General/PrimaryMouseKey")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case 1504177682:
                    if (str.equals("/Settings/General/RedirectVibration")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 1673121184:
                    if (str.equals("/Settings/General/AutoTime")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1673657398:
                    if (str.equals("/Settings/General/Autofill")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1942705149:
                    if (str.equals("/Settings/General/AdditionalKey1")) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case 1942705150:
                    if (str.equals("/Settings/General/AdditionalKey2")) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case 2044284300:
                    if (str.equals("/Settings/General/MouseSecondaryKey")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            SceneResult.ResultType resultType = SceneResult.ResultType.RESULT_FAIL;
            String str2 = "/";
            switch (c) {
                case 0:
                    if (context.getResources()
                            .getBoolean(android.R.bool.config_sms_decode_gsm_8bit_data)) {
                        Settings.Secure.putInt(
                                contentResolver,
                                "show_keyboard_button",
                                Integer.valueOf(value).intValue());
                        break;
                    } else {
                        break;
                    }
                case 1:
                    Settings.System.putInt(
                            contentResolver,
                            "enable_language_change_combination_key",
                            Integer.valueOf(value).intValue());
                    break;
                case 2:
                    Settings.Secure.putInt(
                            contentResolver,
                            "show_ime_with_hard_keyboard",
                            Integer.valueOf(value).intValue());
                    break;
                case 3:
                    if (TextUtils.isEmpty(value)) {
                        builder.mResultType = resultType;
                        builder.mErrorType = SceneResult.ErrorType.INVALID_DATA;
                        break;
                    } else if (TextUtils.equals("dummy", value)) {
                        Settings.Secure.putString(contentResolver, "autofill_service", null);
                        break;
                    } else {
                        String str3 = value.split(str2)[0];
                        if (!TextUtils.isEmpty(str3) && Utils.isPackageEnabled(context, str3)) {
                            Settings.Secure.putString(contentResolver, "autofill_service", value);
                            break;
                        }
                    }
                    break;
                case 4:
                    if (value.equals("dummy")) {
                        Settings.Secure.putString(
                                contentResolver,
                                "credential_service_primary",
                                ApnSettings.MVNO_NONE);
                        break;
                    } else {
                        String str4 = value.split(str2)[0];
                        if (TextUtils.isEmpty(str4)) {
                            builder.mResultType = resultType;
                            builder.mErrorType = SceneResult.ErrorType.INVALID_DATA;
                            break;
                        } else if (Utils.isPackageEnabled(context, str4)) {
                            Settings.Secure.putString(
                                    contentResolver, "credential_service_primary", value);
                            break;
                        }
                    }
                    break;
                case 5:
                    if (!TextUtils.isEmpty(value) && !value.equals("dummy")) {
                        StringBuilder sb = new StringBuilder();
                        String[] split = value.split(":");
                        int length = split.length;
                        int i = 0;
                        while (true) {
                            if (i < length) {
                                String str5 = split[i];
                                String str6 = str2;
                                String str7 = str5.split(str6)[0];
                                if (TextUtils.isEmpty(str7)) {
                                    builder.mResultType = resultType;
                                    builder.mErrorType = SceneResult.ErrorType.INVALID_DATA;
                                } else {
                                    if (Utils.isPackageEnabled(context, str7)) {
                                        sb.append(str5);
                                        sb.append(":");
                                    }
                                    i++;
                                    str2 = str6;
                                }
                            }
                        }
                        sb.deleteCharAt(sb.length() - 1);
                        Settings.Secure.putString(
                                contentResolver, "credential_service", sb.toString());
                        break;
                    }
                    builder.mResultType = resultType;
                    builder.mErrorType = SceneResult.ErrorType.INVALID_DATA;
                    break;
                case 6:
                    if (TextUtils.isEmpty(value)) {
                        builder.mResultType = resultType;
                        builder.mErrorType = SceneResult.ErrorType.INVALID_DATA;
                        break;
                    } else {
                        String string = Settings.System.getString(contentResolver, "time_12_24");
                        if (TextUtils.isEmpty(string)) {
                            if (!(DateFormat.is24HourFormat(context)
                                    ^ (value == DATA.DM_FIELD_INDEX.SIP_TJ_TIMER))) {
                                break;
                            }
                        } else if (string.equals(value)) {
                            break;
                        }
                        Settings.System.putString(contentResolver, "time_12_24", value);
                        int i2 = value == DATA.DM_FIELD_INDEX.SIP_TJ_TIMER ? 1 : 0;
                        Intent intent = new Intent("android.intent.action.TIME_SET");
                        intent.addFlags(16777216);
                        intent.putExtra("android.intent.extra.TIME_PREF_24_HOUR_FORMAT", i2);
                        context.sendBroadcast(intent);
                        break;
                    }
                case 7:
                    Settings.Global.putInt(
                            contentResolver, "auto_time", Integer.valueOf(value).intValue());
                    break;
                case '\b':
                    Settings.Global.putInt(
                            contentResolver, "auto_time_zone", Integer.valueOf(value).intValue());
                    break;
                case '\t':
                    Settings.System.putInt(
                            contentResolver,
                            "vibrate_input_devices",
                            Integer.valueOf(value).intValue());
                    break;
                case '\n':
                    Settings.Secure.putInt(
                            contentResolver,
                            "location_time_zone_detection_enabled",
                            Integer.valueOf(value).intValue());
                    break;
                case 11:
                    String string2 = Settings.System.getString(contentResolver, "system_locales");
                    if (!TextUtils.isEmpty(value)
                            && !value.equals("dummy")
                            && !TextUtils.isEmpty(string2)) {
                        if (value.contains("sr-RS")) {
                            value = value.replace("sr-RS", "sr-Latn-RS");
                        }
                        String[] split2 = string2.split(",");
                        String[] split3 = value.split(",");
                        ArrayList arrayList = new ArrayList(Arrays.asList(split2));
                        for (int i3 = 0; i3 < split3.length; i3++) {
                            int i4 = 0;
                            for (String str8 : split2) {
                                if (split3[i3].equals(str8)) {
                                    i4++;
                                }
                            }
                            if (i4 <= 0 && !arrayList.contains(split3[i3])) {
                                arrayList.add(split3[i3]);
                            }
                        }
                        ArrayList arrayList2 = new ArrayList();
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            arrayList2.add(
                                    LocaleStore.getLocaleInfo(
                                            Locale.forLanguageTag((String) it.next())));
                        }
                        int size = arrayList2.size();
                        Locale[] localeArr = new Locale[size];
                        for (int i5 = 0; i5 < size; i5++) {
                            localeArr[i5] =
                                    ((LocaleStore.LocaleInfo) arrayList2.get(i5)).getLocale();
                        }
                        LocalePicker.updateLocales(new LocaleList(localeArr));
                        break;
                    }
                    builder.mResultType = SceneResult.ResultType.RESULT_SKIP;
                    break;
                case '\f':
                    Settings.System.putInt(
                            contentResolver,
                            "primary_mouse_button_option",
                            Integer.valueOf(value).intValue());
                    break;
                case '\r':
                    Settings.System.putInt(
                            contentResolver,
                            "mouse_secondary_button_option",
                            Integer.valueOf(value).intValue());
                    String attribute = scene.getAttribute("appComponent");
                    Settings.System.putString(
                            contentResolver, "mouse_secondary_button_app", attribute);
                    setKeyCustomization(10, attribute);
                    break;
                case 14:
                    Settings.System.putInt(
                            contentResolver,
                            "mouse_middle_button_option",
                            Integer.valueOf(value).intValue());
                    String attribute2 = scene.getAttribute("appComponent");
                    Settings.System.putString(
                            contentResolver, "mouse_middle_button_app", attribute2);
                    setKeyCustomization(11, attribute2);
                    break;
                case 15:
                    Settings.System.putInt(
                            contentResolver,
                            "mouse_additional_1_option",
                            Integer.valueOf(value).intValue());
                    String attribute3 = scene.getAttribute("appComponent");
                    Settings.System.putString(
                            contentResolver, "mouse_additional_1_app", attribute3);
                    setKeyCustomization(12, attribute3);
                    break;
                case 16:
                    Settings.System.putInt(
                            contentResolver,
                            "mouse_additional_2_option",
                            Integer.valueOf(value).intValue());
                    String attribute4 = scene.getAttribute("appComponent");
                    Settings.System.putString(
                            contentResolver, "mouse_additional_2_app", attribute4);
                    setKeyCustomization(13, attribute4);
                    break;
                case 17:
                    Settings.System.putInt(
                            contentResolver, "pointer_speed", Integer.valueOf(value).intValue());
                    break;
                case 18:
                    Settings.System.putInt(
                            contentResolver,
                            "mouse_scrolling_speed",
                            Integer.valueOf(value).intValue());
                    break;
                case 19:
                    Settings.System.putInt(
                            contentResolver,
                            "enhance_pointer_precision",
                            Integer.valueOf(value).intValue());
                    break;
                case 20:
                    Settings.System.putInt(
                            contentResolver,
                            "mouse_pointer_size_step",
                            Integer.valueOf(value).intValue());
                    break;
                case 21:
                    Settings.System.putInt(
                            contentResolver,
                            "mouse_pointer_color",
                            Integer.valueOf(value).intValue());
                    break;
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case ' ':
                case '!':
                case '\"':
                case '#':
                case '$':
                    if (!isDefault) {
                        Settings.System.putString(
                                contentResolver,
                                (String)
                                        ((ArrayList) AppShortcutsConstants.SETTINGS_LIST)
                                                .get(
                                                        ((ArrayList)
                                                                        this
                                                                                .APP_SHORTCUTS_COMMAND_KEY_LIST)
                                                                .indexOf(str)),
                                value);
                        break;
                    }
                    break;
                case '%':
                    setAppLanguageInfo(context, scene.getValue(null, true));
                    SemLog.d("GeneralItem", value);
                    break;
            }
        } catch (Exception e) {
            SemLog.e("GeneralItem", e.getStackTrace()[0].toString());
        }
        return builder.build();
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final void open(Context context, String str) {}
}
