package com.samsung.android.settings.accessibility.bixby.action.visibility;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.accessibility.AccessibilityRune;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.bixby.BixbyUtils;
import com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction;
import com.samsung.android.settings.accessibility.bixby.data.ParsedBundle;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ColorAdjustmentAction extends BixbyControllerAction {
    public final String[] COLOR_ADUSTMENT_OPTION = {
        "Grayscale", "Protan", "Deutan", "Tritan", "PersonalizedColor"
    };

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final void addCustomAction(Map map) {
        final int i = 0;
        map.put(
                "viv.accessibilityApp.GetColorAdjustmentCurrentValue",
                new BiFunction(
                        this) { // from class:
                                // com.samsung.android.settings.accessibility.bixby.action.visibility.ColorAdjustmentAction$$ExternalSyntheticLambda0
                    public final /* synthetic */ ColorAdjustmentAction f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        String str;
                        String str2;
                        int i2 = i;
                        ColorAdjustmentAction colorAdjustmentAction = this.f$0;
                        Context context = (Context) obj;
                        ParsedBundle parsedBundle = (ParsedBundle) obj2;
                        colorAdjustmentAction.getClass();
                        switch (i2) {
                            case 0:
                                Bundle bundle = new Bundle();
                                if (AccessibilityRune.getFloatingFeatureBooleanValue(
                                        "SEC_FLOATING_FEATURE_LCD_SUPPORT_MDNIE_HW")) {
                                    str2 =
                                            colorAdjustmentAction
                                                    .COLOR_ADUSTMENT_OPTION[
                                                    Settings.Secure.getInt(
                                                            context.getContentResolver(),
                                                            "color_adjustment_type",
                                                            2)];
                                    str = "true";
                                } else {
                                    str = "false";
                                    str2 = null;
                                }
                                bundle.putString("result", str);
                                bundle.putString("accessibilityMenu", parsedBundle.paramValue);
                                bundle.putString("menuValue", str2);
                                return bundle;
                            default:
                                Bundle bundle2 = new Bundle();
                                bundle2.putString(
                                        "result",
                                        Settings.System.getInt(
                                                                context.getContentResolver(),
                                                                "color_blind_test",
                                                                0)
                                                        == 1
                                                ? "true"
                                                : "false");
                                return bundle2;
                        }
                    }
                });
        final int i2 = 1;
        map.put(
                "viv.accessibilityApp.GetPersonalizedColorSetUpStatus",
                new BiFunction(
                        this) { // from class:
                                // com.samsung.android.settings.accessibility.bixby.action.visibility.ColorAdjustmentAction$$ExternalSyntheticLambda0
                    public final /* synthetic */ ColorAdjustmentAction f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        String str;
                        String str2;
                        int i22 = i2;
                        ColorAdjustmentAction colorAdjustmentAction = this.f$0;
                        Context context = (Context) obj;
                        ParsedBundle parsedBundle = (ParsedBundle) obj2;
                        colorAdjustmentAction.getClass();
                        switch (i22) {
                            case 0:
                                Bundle bundle = new Bundle();
                                if (AccessibilityRune.getFloatingFeatureBooleanValue(
                                        "SEC_FLOATING_FEATURE_LCD_SUPPORT_MDNIE_HW")) {
                                    str2 =
                                            colorAdjustmentAction
                                                    .COLOR_ADUSTMENT_OPTION[
                                                    Settings.Secure.getInt(
                                                            context.getContentResolver(),
                                                            "color_adjustment_type",
                                                            2)];
                                    str = "true";
                                } else {
                                    str = "false";
                                    str2 = null;
                                }
                                bundle.putString("result", str);
                                bundle.putString("accessibilityMenu", parsedBundle.paramValue);
                                bundle.putString("menuValue", str2);
                                return bundle;
                            default:
                                Bundle bundle2 = new Bundle();
                                bundle2.putString(
                                        "result",
                                        Settings.System.getInt(
                                                                context.getContentResolver(),
                                                                "color_blind_test",
                                                                0)
                                                        == 1
                                                ? "true"
                                                : "false");
                                return bundle2;
                        }
                    }
                });
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0240  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0251  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0203  */
    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle doChangeAction(
            android.content.Context r23,
            com.samsung.android.settings.accessibility.bixby.data.ParsedBundle r24) {
        /*
            Method dump skipped, instructions count: 617
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.accessibility.bixby.action.visibility.ColorAdjustmentAction.doChangeAction(android.content.Context,"
                    + " com.samsung.android.settings.accessibility.bixby.data.ParsedBundle):android.os.Bundle");
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction,
              // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doEnableAction(Context context, ParsedBundle parsedBundle, boolean z) {
        String str;
        Bundle bundle = new Bundle();
        ContentResolver contentResolver = context.getContentResolver();
        if (z
                || !AccessibilityRune.getFloatingFeatureBooleanValue(
                        "SEC_FLOATING_FEATURE_LCD_SUPPORT_MDNIE_HW")) {
            str = "fail";
        } else {
            Settings.System.putInt(contentResolver, "color_blind", 0);
            SecAccessibilityUtils.setColorAdjustment(context);
            str = "success";
        }
        bundle.putString("result", str);
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction,
              // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doGetCurrentStatus(Context context, ParsedBundle parsedBundle) {
        String str;
        Bundle bundle = new Bundle();
        if (AccessibilityRune.getFloatingFeatureBooleanValue(
                "SEC_FLOATING_FEATURE_LCD_SUPPORT_MDNIE_HW")) {
            str =
                    String.valueOf(
                            SecAccessibilityUtils.getColorAdjustmentIntensity(
                                    context,
                                    Settings.Secure.getInt(
                                            context.getContentResolver(),
                                            "color_adjustment_type",
                                            2)));
            if (Settings.System.getInt(context.getContentResolver(), "color_blind", 0) == 0) {
                r1 = false;
            }
        } else {
            r1 =
                    Settings.Secure.getInt(
                                    context.getContentResolver(),
                                    "accessibility_display_daltonizer_enabled",
                                    0)
                            != 0;
            str = ApnSettings.MVNO_NONE;
        }
        bundle.putString("result", BixbyUtils.getStateAlreadyChecked(parsedBundle.menuValue, r1));
        bundle.putString("levelValue", str);
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doGetExclusivePopup(Context context) {
        String str;
        String str2;
        Bundle bundle = new Bundle();
        if (AccessibilityRune.getFloatingFeatureBooleanValue(
                "SEC_FLOATING_FEATURE_LCD_SUPPORT_MDNIE_HW")) {
            ContentResolver contentResolver = context.getContentResolver();
            String string = context.getString(R.string.color_adjustment_title);
            String string2 = context.getString(R.string.colour_lens_title);
            if (Settings.Secure.getInt(contentResolver, "color_lens_switch", 0) == 1) {
                str2 =
                        context.getString(
                                R.string.single_exclusive_feature_message, string, string2);
                str = "true";
                bundle.putString("result", str);
                bundle.putString("description", str2);
                return bundle;
            }
        }
        str = "false";
        str2 = null;
        bundle.putString("result", str);
        bundle.putString("description", str2);
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction,
              // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doGetSupportFeature(Context context, ParsedBundle parsedBundle) {
        if (SecAccessibilityUtils.isSupportColorAdjustment(context)) {
            Bundle doGetSupportFeature = super.doGetSupportFeature(context, parsedBundle);
            doGetSupportFeature.putString("accessibilityMenu", "ColorAdjustment");
            return doGetSupportFeature;
        }
        Bundle bundle = new Bundle();
        bundle.putString("result", "false");
        bundle.putString("accessibilityMenu", "ColorAdjustment");
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doSetAction(Context context, final ParsedBundle parsedBundle) {
        Bundle bundle = new Bundle();
        boolean floatingFeatureBooleanValue =
                AccessibilityRune.getFloatingFeatureBooleanValue(
                        "SEC_FLOATING_FEATURE_LCD_SUPPORT_MDNIE_HW");
        String str = "fail";
        String str2 = ApnSettings.MVNO_NONE;
        if (floatingFeatureBooleanValue) {
            ContentResolver contentResolver = context.getContentResolver();
            int i = Settings.Secure.getInt(contentResolver, "color_adjustment_type", 2);
            int i2 = Settings.System.getInt(contentResolver, "color_blind", 0);
            String[] strArr = this.COLOR_ADUSTMENT_OPTION;
            if (i2 == 0 || !strArr[i].equals(parsedBundle.menuValue)) {
                int orElse =
                        IntStream.range(0, strArr.length)
                                .filter(
                                        new IntPredicate() { // from class:
                                                             // com.samsung.android.settings.accessibility.bixby.action.visibility.ColorAdjustmentAction$$ExternalSyntheticLambda2
                                            @Override // java.util.function.IntPredicate
                                            public final boolean test(int i3) {
                                                return ColorAdjustmentAction.this
                                                        .COLOR_ADUSTMENT_OPTION[i3].equals(
                                                        parsedBundle.menuValue);
                                            }
                                        })
                                .findFirst()
                                .orElse(-1);
                if (orElse == -1) {
                    Log.d("ColorAdjustmentAction", "menuValue is wrong value");
                } else {
                    if (orElse <= 3) {
                        Settings.Secure.putInt(contentResolver, "color_adjustment_type", orElse);
                    } else if (Settings.System.getInt(contentResolver, "color_blind_test", 0)
                            == 0) {
                        Log.d(
                                "ColorAdjustmentAction",
                                "Personalize color is not tested, Can't change it");
                    } else {
                        Settings.Secure.putInt(contentResolver, "color_adjustment_type", orElse);
                    }
                    str = "success";
                }
                if (str.equals("success")) {
                    Log.d(
                            "ColorAdjustmentAction",
                            "ColorAdjustment set : "
                                    + Settings.Secure.getInt(
                                            contentResolver, "color_adjustment_type", 2));
                    Settings.System.putInt(contentResolver, "color_blind", 1);
                    SecAccessibilityUtils.setColorAdjustment(context);
                    ContentResolver contentResolver2 = context.getContentResolver();
                    String string =
                            Settings.Secure.getString(
                                    contentResolver2, "predefined_color_blind_intensity");
                    float f =
                            Settings.Secure.getFloat(
                                    contentResolver2, "color_blind_user_parameter", 0.0f);
                    int i3 = Settings.Secure.getInt(contentResolver2, "color_adjustment_type", 2);
                    Log.i(
                            "BixbyUtils",
                            "personalizeParameter : " + f + ", customIntensity : " + string);
                    if (TextUtils.isEmpty(string)) {
                        string = "00,00,00";
                    }
                    str2 =
                            i3 == 0
                                    ? "00"
                                    : i3 == 4
                                            ? String.valueOf((int) (f * 10.0f))
                                            : string.split(",")[i3 - 1];
                }
            } else {
                str = "already_set";
            }
        }
        bundle.putString("result", str);
        bundle.putString("levelValue", str2);
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction
    public final String getControllerName(Context context) {
        return "com.samsung.android.settings.accessibility.vision.controllers.ColorAdjustmentPreferenceController";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final String getDestinationFragment() {
        return "com.samsung.android.settings.accessibility.vision.color.ColorAdjustmentMainFragment";
    }
}
