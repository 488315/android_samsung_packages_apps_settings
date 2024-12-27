package com.samsung.android.settings.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.AccessibilityShortcutInfo;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SeslSeekBar;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.internal.accessibility.util.ShortcutUtils;
import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.accessibility.AccessibilityUtil;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.Utils;
import com.android.settingslib.accessibility.AccessibilityUtils;
import com.android.settingslib.applications.RecentAppOpsAccess;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.google.common.collect.ImmutableSet;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.inputmethod.TouchPadGestureSettingsController;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SecAccessibilityUtils {
    public static final String COMPONENT_NAME_SEPARATOR_STRING;
    public static final HashSet excludeDeviceNameSet;
    public static final TextUtils.SimpleStringSplitter sStringColonSplitter;

    static {
        HashSet hashSet = new HashSet();
        excludeDeviceNameSet = hashSet;
        hashSet.add("beyond");
        hashSet.add("d1");
        hashSet.add("d2");
        COMPONENT_NAME_SEPARATOR_STRING = String.valueOf(':');
        sStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
    }

    public static void applySoundBalanceCenterPositionThreshold(SeslSeekBar seslSeekBar) {
        int progress = seslSeekBar.getProgress();
        if (progress != 100) {
            float f = progress;
            float f2 = 100;
            if (f <= f2 - 6.0f || f >= f2 + 6.0f) {
                return;
            }
            seslSeekBar.setProgressInternal(100, true, true);
        }
    }

    public static boolean checkAutoAnsweringMemo(boolean z, final Context context, DialogInterface.OnDismissListener onDismissListener, DialogInterface.OnClickListener onClickListener) {
        if (!z || !SemCscFeature.getInstance().getString("CscFeature_VoiceCall_SupportAutoAnsweringMemo").equalsIgnoreCase("TRUE")) {
            return true;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.turn_off_all_sounds_conflict_popup_title);
        builder.setMessage(R.string.turn_off_all_sounds_conflict_popup_contents);
        builder.setPositiveButton(R.string.accessibility_turn_off, new DialogInterface.OnClickListener() { // from class: com.samsung.android.settings.accessibility.SecAccessibilityUtils.1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                Settings.System.putInt(context.getContentResolver(), "callsettings_answer_memo", 2);
                SecAccessibilityUtils.enableMuteAllSounds(context, true);
            }
        });
        builder.setNegativeButton(R.string.common_cancel, onClickListener);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mCancelable = false;
        if (onDismissListener != null) {
            alertParams.mOnDismissListener = onDismissListener;
        }
        builder.create().show();
        return false;
    }

    public static void enableMuteAllSounds(Context context, boolean z) {
        Settings.Global.putInt(context.getContentResolver(), "all_sound_off", z ? 1 : 0);
        Intent intent = new Intent("android.settings.ALL_SOUND_MUTE");
        intent.putExtra("mute", z ? 1 : 0);
        context.sendBroadcastAsUser(intent, UserHandle.SEM_ALL);
    }

    public static CharSequence getAccessibilityButtonShortcutSummary(Context context, String str, CharSequence charSequence) {
        if (AccessibilityUtil.isFloatingMenuEnabled(context)) {
            return isStartStopShortcut(context, str) ? context.getString(R.string.accessibility_shortcut_edit_description_floating_button_start_stop, charSequence, charSequence) : context.getString(R.string.accessibility_shortcut_edit_description_floating_button, charSequence);
        }
        if (AccessibilityUtil.isGestureNavigateEnabled(context)) {
            return AccessibilityUtil.isTouchExploreEnabled(context) ? isStartStopShortcut(context, str) ? context.getString(R.string.accessibility_shortcut_edit_description_software_gesture_talkback_start_stop, charSequence) : context.getString(R.string.accessibility_shortcut_edit_description_software_gesture_talkback) : isStartStopShortcut(context, str) ? context.getString(R.string.accessibility_shortcut_edit_description_software_gesture_start_stop, charSequence) : context.getString(R.string.accessibility_shortcut_edit_description_software_gesture);
        }
        int lineHeight = new TextView(context).getLineHeight();
        Drawable drawable = context.getDrawable(R.drawable.a11y_enable_warning_popup_item_icon);
        String string = isStartStopShortcut(context, str) ? context.getString(R.string.accessibility_shortcut_edit_description_software_start_stop, charSequence, "%s") : context.getString(R.string.accessibility_shortcut_edit_description_software);
        SpannableString valueOf = SpannableString.valueOf(string);
        int indexOf = string.indexOf("%s");
        int i = indexOf + 2;
        if (drawable != null) {
            ImageSpan imageSpan = new ImageSpan(drawable);
            drawable.setTint(Utils.getColorAttrDefaultColor(context, android.R.attr.textColorSecondary));
            drawable.setBounds(0, 0, lineHeight, lineHeight);
            valueOf.setSpan(imageSpan, indexOf, i, 33);
        }
        return valueOf;
    }

    public static AccessibilityServiceInfo getAccessibilityServiceInfo(Context context, ComponentName componentName) {
        List<AccessibilityServiceInfo> installedAccessibilityServiceList = AccessibilityManager.getInstance(context).getInstalledAccessibilityServiceList();
        if (installedAccessibilityServiceList == null) {
            return null;
        }
        for (AccessibilityServiceInfo accessibilityServiceInfo : installedAccessibilityServiceList) {
            ResolveInfo resolveInfo = accessibilityServiceInfo.getResolveInfo();
            if (componentName.getPackageName().equals(resolveInfo.serviceInfo.packageName) && componentName.getClassName().equals(resolveInfo.serviceInfo.name)) {
                return accessibilityServiceInfo;
            }
        }
        return null;
    }

    public static SharedPreferences getAccessibilitySharedPreferences(Context context) {
        return context.getSharedPreferences("accessibility_prefs", 0);
    }

    public static AccessibilityShortcutInfo getAccessibilityShortcutInfo(Context context, ComponentName componentName) {
        List installedAccessibilityShortcutListAsUser = AccessibilityManager.getInstance(context).getInstalledAccessibilityShortcutListAsUser(context, UserHandle.myUserId());
        int size = installedAccessibilityShortcutListAsUser.size();
        for (int i = 0; i < size; i++) {
            AccessibilityShortcutInfo accessibilityShortcutInfo = (AccessibilityShortcutInfo) installedAccessibilityShortcutListAsUser.get(i);
            ActivityInfo activityInfo = accessibilityShortcutInfo.getActivityInfo();
            if (componentName.getPackageName().equals(activityInfo.packageName) && componentName.getClassName().equals(activityInfo.name)) {
                return accessibilityShortcutInfo;
            }
        }
        return null;
    }

    public static int getColorAdjustmentIntensity(Context context, int i) {
        if (i < 1 || i > 4) {
            Log.d("A11yUtils", "getColorAdjustmentIntensity - wrong type entered.");
            return 0;
        }
        if (i == 4) {
            return (int) (Settings.Secure.getFloat(context.getContentResolver(), "color_blind_user_parameter", 0.0f) * 10.0f);
        }
        String string = Settings.Secure.getString(context.getContentResolver(), "predefined_color_blind_intensity");
        if (TextUtils.isEmpty(string)) {
            return 0;
        }
        return Integer.parseInt(string.split(",")[i - 1]);
    }

    public static BasePreferenceController getPreferenceController(Context context, String str, String str2) {
        try {
            return BasePreferenceController.createInstance(context, str2, str);
        } catch (IllegalStateException e) {
            Log.w("A11yUtils", "getPreferenceController() - exception occurred. key : " + str + ", className : " + str2, e);
            return null;
        }
    }

    public static String getShortcutService(Context context, int i) {
        int i2 = AccessibilityUtil.$r8$clinit;
        String convertToKey = ShortcutUtils.convertToKey(i);
        String string = i == 4 ? Settings.Secure.getInt(context.getContentResolver(), convertToKey, 0) == 1 ? "com.android.server.accessibility.MagnificationController" : ApnSettings.MVNO_NONE : Settings.Secure.getString(context.getContentResolver(), convertToKey);
        return ((i == 2 || i == 512) && string == null && !com.android.settings.Utils.isDeviceProvisioned(context)) ? AccessibilityConstant.COMPONENT_NAME_SAMSUNG_TALKBACK.flattenToString() : string;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v4, types: [int] */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    public static int getValuesInSettings(Context context, String str) {
        boolean hasValueInSettings = hasValueInSettings(context, 1, str);
        boolean z = hasValueInSettings;
        if (hasValueInSettings(context, 2, str)) {
            z = (hasValueInSettings ? 1 : 0) | 2;
        }
        boolean z2 = z;
        if (hasValueInSettings(context, 512, str)) {
            z2 = (z ? 1 : 0) | 512;
        }
        ?? r0 = z2;
        if (hasValueInSettings(context, 4, str)) {
            r0 = (z2 ? 1 : 0) | 4;
        }
        return hasValueInSettings(context, 16, str) ? r0 | 16 : r0;
    }

    public static boolean hasValueInSettings(Context context, int i, String str) {
        String str2;
        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        if (unflattenFromString != null) {
            return AccessibilityUtil.hasValueInSettings(context, i, unflattenFromString);
        }
        String shortcutService = getShortcutService(context, i);
        if (TextUtils.isEmpty(shortcutService)) {
            return false;
        }
        sStringColonSplitter.setString(shortcutService);
        do {
            TextUtils.SimpleStringSplitter simpleStringSplitter = sStringColonSplitter;
            if (!simpleStringSplitter.hasNext()) {
                return false;
            }
            try {
                str2 = simpleStringSplitter.next();
            } catch (StringIndexOutOfBoundsException unused) {
                Log.e("hasValueInSettings", "StringIndexOutOfBoundsException :" + shortcutService);
                str2 = null;
            }
        } while (!str.equals(str2));
        return true;
    }

    public static boolean hasValuesInSettings(Context context, String str) {
        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        if (unflattenFromString != null) {
            return AccessibilityUtil.hasValuesInSettings(context, 543, unflattenFromString);
        }
        return hasValueInSettings(context, 16, str) | hasValueInSettings(context, 1, str) | hasValueInSettings(context, 2, str) | hasValueInSettings(context, 512, str) | hasValueInSettings(context, 4, str);
    }

    public static boolean isAccessibilityButtonEmpty(Context context) {
        return TextUtils.isEmpty(Settings.Secure.getString(context.getContentResolver(), "accessibility_button_targets"));
    }

    public static boolean isDesktopDualModeMonitorDisplay(Context context) {
        return Rune.isSamsungDexMode(context) && !com.android.settings.Utils.isDesktopStandaloneMode(context) && ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getDisplayId() == 2;
    }

    public static boolean isDexMode(Context context) {
        return Rune.isSamsungDexMode(context) || com.android.settings.Utils.isDesktopStandaloneMode(context);
    }

    public static boolean isHearingAidSupported(Context context) {
        if (!SemCscFeature.getInstance().getBoolean("CscFeature_Setting_SupportHAC") || Utils.isWifiOnly(context) || !Utils.isVoiceCapable(context)) {
            Uri uri = AccessibilityConstant.URI_ACCESSIBILITY_PROVIDER;
            if (!AccessibilityRune.getFloatingFeatureBooleanValue("SEC_FLOATING_FEATURE_ACCESSIBILITY_SUPPORT_HEARING_AIDS")) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0026, code lost:
    
        if (r0.contains(";;;") != false) goto L29;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isStartStopShortcut(android.content.Context r3, java.lang.String r4) {
        /*
            android.content.ComponentName r0 = android.content.ComponentName.unflattenFromString(r4)
            if (r0 != 0) goto L7
            goto L29
        L7:
            android.accessibilityservice.AccessibilityShortcutInfo r0 = getAccessibilityShortcutInfo(r3, r0)
            android.content.pm.PackageManager r1 = r3.getPackageManager()
            if (r0 == 0) goto L16
            java.lang.String r0 = r0.loadSummary(r1)
            goto L18
        L16:
            java.lang.String r0 = ""
        L18:
            if (r0 == 0) goto L29
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L29
            java.lang.String r1 = ";;;"
            boolean r0 = r0.contains(r1)
            if (r0 == 0) goto L29
            goto L6c
        L29:
            android.content.ComponentName r0 = android.content.ComponentName.unflattenFromString(r4)
            if (r0 != 0) goto L30
            goto L5e
        L30:
            java.lang.String r1 = "accessibility"
            java.lang.Object r3 = r3.getSystemService(r1)
            android.view.accessibility.AccessibilityManager r3 = (android.view.accessibility.AccessibilityManager) r3
            java.util.List r3 = r3.getInstalledAccessibilityServiceList()
            if (r3 != 0) goto L3f
            goto L5e
        L3f:
            java.util.Iterator r3 = r3.iterator()
        L43:
            boolean r1 = r3.hasNext()
            if (r1 == 0) goto L5e
            java.lang.Object r1 = r3.next()
            android.accessibilityservice.AccessibilityServiceInfo r1 = (android.accessibilityservice.AccessibilityServiceInfo) r1
            java.lang.String r1 = r1.getId()
            java.lang.String r2 = r0.getPackageName()
            boolean r1 = r1.contains(r2)
            if (r1 == 0) goto L43
            goto L6c
        L5e:
            java.util.Map r3 = com.android.internal.accessibility.AccessibilityShortcutController.getFrameworkShortcutFeaturesMap()
            android.content.ComponentName r4 = android.content.ComponentName.unflattenFromString(r4)
            boolean r3 = r3.containsKey(r4)
            if (r3 == 0) goto L6e
        L6c:
            r3 = 1
            goto L6f
        L6e:
            r3 = 0
        L6f:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.accessibility.SecAccessibilityUtils.isStartStopShortcut(android.content.Context, java.lang.String):boolean");
    }

    public static boolean isSupportColorAdjustment(Context context) {
        if (SystemProperties.getInt(TouchPadGestureSettingsController.FIRST_API_LEVEL, 20) >= 33 || !AccessibilityRune.getFloatingFeatureBooleanValue("SEC_FLOATING_FEATURE_LCD_SUPPORT_MDNIE_HW")) {
            return false;
        }
        return !isDesktopDualModeMonitorDisplay(context);
    }

    public static boolean isSupportColorCorrection(Context context) {
        if (!AccessibilityRune.getFloatingFeatureBooleanValue("SEC_FLOATING_FEATURE_LCD_SUPPORT_MDNIE_HW")) {
            return true;
        }
        if (SystemProperties.getInt(TouchPadGestureSettingsController.FIRST_API_LEVEL, 20) < 33 || Rune.isShopDemo(context)) {
            return false;
        }
        return !isDesktopDualModeMonitorDisplay(context);
    }

    public static boolean isSupportQuickSettings(String str) {
        return str.equals(AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME.flattenToString()) || str.equals(AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_COMPONENT_NAME.flattenToString()) || str.equals(AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME.flattenToString()) || str.equals(AccessibilityConstant.COMPONENT_NAME_HIGH_CONTRAST_FONT_SHORTCUT.flattenToString()) || str.equals(AccessibilityConstant.COMPONENT_NAME_COLOR_LENS_SHORTCUT.flattenToString()) || str.equals(AccessibilityConstant.COMPONENT_NAME_GOOGLE_SOUND_NOTIFICATION_SHORTCUT.flattenToString()) || str.equals(AccessibilityConstant.COMPONENT_NAME_LIVE_TRANSCRIBE.flattenToString()) || str.equals(AccessibilityConstant.COMPONENT_NAME_GOOGLE_LIVE_TRANSCRIBE_SHORTCUT.flattenToString());
    }

    public static boolean isSupportReduceBrightness(Context context) {
        boolean z;
        Resources resources = context.getResources();
        int identifier = Resources.getSystem().getIdentifier("config_setColorTransformAccelerated", "bool", RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME);
        if (SystemProperties.getBoolean("ro.surface_flinger.protected_contents", false)) {
            String str = SystemProperties.get("ro.product.vendor.device");
            if (str != null) {
                Iterator it = excludeDeviceNameSet.iterator();
                while (it.hasNext()) {
                    if (str.startsWith((String) it.next())) {
                    }
                }
            }
            z = true;
            return identifier <= 0 ? false : false;
        }
        z = false;
        return identifier <= 0 ? false : false;
    }

    public static boolean isSupportRelumino() {
        if (!AccessibilityRune.isAtLeastOneUI_6_1()) {
            return false;
        }
        Uri uri = AccessibilityConstant.URI_ACCESSIBILITY_PROVIDER;
        return AccessibilityRune.getFloatingFeatureBooleanValue("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_RELUMINO_EFFECT_FLAG");
    }

    public static boolean isSupportStereoSpeaker() {
        Uri uri = AccessibilityConstant.URI_ACCESSIBILITY_PROVIDER;
        int i = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_AUDIO_NUMBER_OF_SPEAKER");
        boolean floatingFeatureBooleanValue = AccessibilityRune.getFloatingFeatureBooleanValue("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_DUAL_SPEAKER");
        Log.i("A11yUtils", "numberOfSpeaker - " + i + "  isDualSpeaker - " + floatingFeatureBooleanValue);
        return i >= 2 || floatingFeatureBooleanValue;
    }

    public static boolean needTalkbackDefaultShortcut(Context context, ComponentName componentName) {
        return !WizardManagerHelper.isUserSetupComplete(context) && AccessibilityConstant.COMPONENT_NAME_SAMSUNG_TALKBACK.equals(componentName) && Settings.Secure.getString(context.getContentResolver(), "accessibility_direct_access_target_service") == null && Settings.Secure.getString(context.getContentResolver(), "accessibility_shortcut_target_service") == null;
    }

    public static void optInAllValuesToSettings(Context context, int i, String str) {
        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        if (unflattenFromString != null) {
            str = unflattenFromString.flattenToString();
        }
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        if (accessibilityManager != null) {
            accessibilityManager.enableShortcutsForTargets(true, i, Set.of(str), UserHandle.myUserId());
        }
    }

    public static void optOutAllValuesFromSettings(Context context, int i, String str) {
        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        String flattenToString = unflattenFromString != null ? unflattenFromString.flattenToString() : str;
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        if (accessibilityManager != null) {
            accessibilityManager.enableShortcutsForTargets(false, i, Set.of(flattenToString), UserHandle.myUserId());
        }
        ComponentName unflattenFromString2 = ComponentName.unflattenFromString(str);
        if (unflattenFromString2 != null) {
            AccessibilityUtil.optOutAllValuesFromSettings(context, i, unflattenFromString2);
            return;
        }
        if ((i & 1) == 1) {
            optOutValueFromSettings(context, 1, str);
        }
        if ((i & 2) == 2) {
            optOutValueFromSettings(context, 2, str);
        }
        if ((i & 512) == 512) {
            optOutValueFromSettings(context, 512, str);
        }
        if ((i & 4) == 4) {
            optOutValueFromSettings(context, 4, str);
        }
        if ((i & 16) == 16) {
            optOutValueFromSettings(context, 16, str);
        }
    }

    public static void optOutValueFromSettings(Context context, int i, String str) {
        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        if (unflattenFromString != null) {
            AccessibilityUtil.optOutValueFromSettings(context, i, unflattenFromString);
            return;
        }
        StringJoiner stringJoiner = new StringJoiner(String.valueOf(':'));
        String shortcutService = getShortcutService(context, i);
        if (TextUtils.isEmpty(shortcutService)) {
            return;
        }
        sStringColonSplitter.setString(shortcutService);
        while (true) {
            TextUtils.SimpleStringSplitter simpleStringSplitter = sStringColonSplitter;
            if (!simpleStringSplitter.hasNext()) {
                break;
            }
            String next = simpleStringSplitter.next();
            if (!TextUtils.isEmpty(next) && !str.equals(next)) {
                stringJoiner.add(next);
            }
        }
        String stringJoiner2 = stringJoiner.toString();
        int i2 = AccessibilityUtil.$r8$clinit;
        String convertToKey = ShortcutUtils.convertToKey(i);
        if ("accessibility_display_magnification_enabled".equals(convertToKey)) {
            Settings.Secure.putInt(context.getContentResolver(), convertToKey, !TextUtils.isEmpty(stringJoiner2) ? 1 : 0);
        } else {
            Settings.Secure.putString(context.getContentResolver(), convertToKey, stringJoiner2);
        }
    }

    public static String readDataFromAccessibilityProvider(Context context, String str) {
        String str2 = null;
        try {
            Cursor query = context.getContentResolver().query(AccessibilityConstant.URI_ACCESSIBILITY_PROVIDER, new String[]{"_id", "name", "value"}, "name=?", new String[]{str}, null);
            if (query != null) {
                try {
                    query.moveToFirst();
                    str2 = query.getString(2);
                } finally {
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e) {
            Log.w("A11yUtils", "readDataFromAccessibilityProvider - exception occurred.", e);
        }
        return str2;
    }

    public static void setAccessibilityServiceState(Context context, String str, boolean z) {
        AccessibilityServiceInfo accessibilityServiceInfo;
        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        if (unflattenFromString == null || (accessibilityServiceInfo = getAccessibilityServiceInfo(context, unflattenFromString)) == null || AccessibilityUtil.getAccessibilityServiceFragmentType(accessibilityServiceInfo) != 1) {
            return;
        }
        AccessibilityUtils.setAccessibilityServiceState(context, ComponentName.unflattenFromString(str), z);
    }

    public static void setButtonShape(Context context, boolean z) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        Configuration configuration = new Configuration();
        configuration.semButtonShapeEnabled = z ? 1 : 0;
        activityManager.semUpdateConfiguration(configuration);
    }

    public static void setButtonsColor(Context context, Button button) {
        Drawable mutate = context.getDrawable(R.drawable.a11y_body_contained_button_default).mutate();
        mutate.setTintList(context.getColorStateList(R.color.sec_widget_multi_button_selected_icon_color));
        button.setBackground(mutate);
        button.setTextColor(context.getColorStateList(R.color.a11y_colored_button_text_color));
    }

    public static void setColorAdjustment(Context context) {
        setColorAdjustment(context, Settings.System.getInt(context.getContentResolver(), "color_blind", 0) == 1);
    }

    public static void setRelumino(Context context) {
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m("setRelumino :", "A11yUtils", Settings.Secure.getInt(context.getContentResolver(), "relumino_switch", 0) == 1);
    }

    public static void setUserShortcutType(Context context, int i, final String str) {
        if (str == null) {
            return;
        }
        final ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        Set<String> stringSet = getAccessibilitySharedPreferences(context).getStringSet("user_shortcut_type", ImmutableSet.of());
        if (stringSet.isEmpty()) {
            stringSet = new HashSet<>();
        } else {
            stringSet.removeAll((Set) stringSet.stream().filter(new Predicate() { // from class: com.samsung.android.settings.accessibility.SecAccessibilityUtils$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    ComponentName componentName = unflattenFromString;
                    String str2 = str;
                    String str3 = (String) obj;
                    if (componentName != null) {
                        str2 = componentName.flattenToString();
                    }
                    return str3.contains(str2);
                }
            }).collect(Collectors.toSet()));
        }
        StringJoiner stringJoiner = new StringJoiner(String.valueOf(':'));
        stringJoiner.add(str);
        stringJoiner.add(String.valueOf(i));
        stringSet.add(stringJoiner.toString());
        SharedPreferences.Editor edit = context.getSharedPreferences("accessibility_prefs", 0).edit();
        edit.remove("user_shortcut_type").apply();
        edit.putStringSet("user_shortcut_type", stringSet).apply();
    }

    public static void showVoiceRecorderDownloadDialog(Context context) {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getA11ySettingsMetricsFeatureProvider().visible(null, 0, VolteConstants.ErrorCode.OTHER_SECONDARY_DEVICE_IN_USE, 0);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String string = context.getString(R.string.download_dialog_title_ps, context.getString(R.string.appname_voice_recorder_title));
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = string;
        alertParams.mMessage = context.getString(R.string.download_dialog_message_ps, context.getString(R.string.appname_voice_recorder_title));
        builder.setPositiveButton(R.string.monotype_dialog_button, new SecAccessibilityUtils$$ExternalSyntheticLambda2(context, 1));
        builder.setNegativeButton(R.string.common_cancel, new SecAccessibilityUtils$$ExternalSyntheticLambda4());
        builder.show();
    }

    public static void setColorAdjustment(Context context, boolean z) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        if (accessibilityManager == null) {
            return;
        }
        if (Settings.Secure.getInt(context.getContentResolver(), "color_adjustment_type", 2) != 0) {
            AccessibilityManager accessibilityManager2 = (AccessibilityManager) context.getSystemService("accessibility");
            if (accessibilityManager2 != null) {
                accessibilityManager2.semSetMdnieAccessibilityMode(1, false);
            }
            accessibilityManager.semSetMdnieColorBlind(z, getColorAdjustmentIntensity(context, r2) / 10.0f);
            return;
        }
        AccessibilityManager accessibilityManager3 = (AccessibilityManager) context.getSystemService("accessibility");
        if (accessibilityManager3 != null) {
            if (z) {
                accessibilityManager3.semSetMdnieAccessibilityMode(4, true);
            } else {
                accessibilityManager3.semSetMdnieAccessibilityMode(1, false);
            }
        }
    }
}
