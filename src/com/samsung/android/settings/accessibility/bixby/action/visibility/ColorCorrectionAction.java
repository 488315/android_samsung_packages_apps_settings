package com.samsung.android.settings.accessibility.bixby.action.visibility;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import com.android.settings.R;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.bixby.BixbyUtils;
import com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction;
import com.samsung.android.settings.accessibility.bixby.data.ParsedBundle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.BiFunction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ColorCorrectionAction extends BixbyControllerAction {
    public final Map mAccessibilityDaltonizerKeyToValueMap = new HashMap();
    public final String[] COLOR_CORRECTION_OPTION = {"Deutan", "Protan", "Tritan", "Grayscale"};

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final void addCustomAction(Map map) {
        final int i = 0;
        map.put(
                "viv.accessibilityApp.GetColorAdjustmentCurrentValue",
                new BiFunction(
                        this) { // from class:
                                // com.samsung.android.settings.accessibility.bixby.action.visibility.ColorCorrectionAction$$ExternalSyntheticLambda1
                    public final /* synthetic */ ColorCorrectionAction f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        int i2 = i;
                        ColorCorrectionAction colorCorrectionAction = this.f$0;
                        Context context = (Context) obj;
                        ParsedBundle parsedBundle = (ParsedBundle) obj2;
                        colorCorrectionAction.getClass();
                        switch (i2) {
                            case 0:
                                Bundle bundle = new Bundle();
                                String str =
                                        colorCorrectionAction
                                                .COLOR_CORRECTION_OPTION[
                                                colorCorrectionAction.getColorCorrectionType(
                                                        context)];
                                bundle.putString("result", "true");
                                bundle.putString("accessibilityMenu", parsedBundle.paramValue);
                                bundle.putString("menuValue", str);
                                return bundle;
                            default:
                                Bundle bundle2 = new Bundle();
                                bundle2.putString("result", "false");
                                return bundle2;
                        }
                    }
                });
        final int i2 = 1;
        map.put(
                "viv.accessibilityApp.GetPersonalizedColorSetUpStatus",
                new BiFunction(
                        this) { // from class:
                                // com.samsung.android.settings.accessibility.bixby.action.visibility.ColorCorrectionAction$$ExternalSyntheticLambda1
                    public final /* synthetic */ ColorCorrectionAction f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        int i22 = i2;
                        ColorCorrectionAction colorCorrectionAction = this.f$0;
                        Context context = (Context) obj;
                        ParsedBundle parsedBundle = (ParsedBundle) obj2;
                        colorCorrectionAction.getClass();
                        switch (i22) {
                            case 0:
                                Bundle bundle = new Bundle();
                                String str =
                                        colorCorrectionAction
                                                .COLOR_CORRECTION_OPTION[
                                                colorCorrectionAction.getColorCorrectionType(
                                                        context)];
                                bundle.putString("result", "true");
                                bundle.putString("accessibilityMenu", parsedBundle.paramValue);
                                bundle.putString("menuValue", str);
                                return bundle;
                            default:
                                Bundle bundle2 = new Bundle();
                                bundle2.putString("result", "false");
                                return bundle2;
                        }
                    }
                });
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x004f, code lost:

       if (r8 < 0) goto L19;
    */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0051, code lost:

       r3 = "success";
    */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0059, code lost:

       if (r0 == 0) goto L15;
    */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0062, code lost:

       if (r0 == 10) goto L7;
    */
    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle doChangeAction(
            android.content.Context r13,
            com.samsung.android.settings.accessibility.bixby.data.ParsedBundle r14) {
        /*
            r12 = this;
            android.os.Bundle r12 = new android.os.Bundle
            r12.<init>()
            java.lang.String r0 = r14.unit
            java.lang.String r1 = "Intensity"
            boolean r0 = r1.equals(r0)
            java.lang.String r1 = "accessibility_display_daltonizer_saturation_level"
            r2 = 7
            java.lang.String r3 = "fail"
            java.lang.String r4 = "ColorCorrectionAction"
            if (r0 == 0) goto Lba
            android.content.ContentResolver r0 = r13.getContentResolver()
            int r0 = android.provider.Settings.Secure.getInt(r0, r1, r2)
            java.lang.String r5 = "Increase"
            java.lang.String r6 = r14.level
            boolean r5 = r5.equals(r6)
            java.lang.String r7 = "Maximum"
            r8 = 10
            java.lang.String r9 = "success"
            if (r5 == 0) goto L3d
            if (r0 != r8) goto L34
        L31:
            r3 = r7
            goto L93
        L34:
            int r0 = r0 + 1
            if (r0 <= r8) goto L3b
        L38:
            r3 = r9
            goto L93
        L3b:
            r8 = r0
            goto L38
        L3d:
            java.lang.String r5 = "Decrease"
            boolean r5 = r5.equals(r6)
            java.lang.String r10 = "Minimum"
            r11 = 0
            if (r5 == 0) goto L53
            if (r0 != 0) goto L4d
        L4a:
            r3 = r10
        L4b:
            r8 = r11
            goto L93
        L4d:
            int r8 = r0 + (-1)
            if (r8 >= 0) goto L38
        L51:
            r3 = r9
            goto L4b
        L53:
            boolean r5 = r10.equals(r6)
            if (r5 == 0) goto L5c
            if (r0 != 0) goto L51
            goto L4a
        L5c:
            boolean r5 = r7.equals(r6)
            if (r5 == 0) goto L65
            if (r0 != r8) goto L38
            goto L31
        L65:
            java.lang.String r14 = r14.levelValue
            boolean r5 = android.text.TextUtils.isEmpty(r14)
            if (r5 != 0) goto L8d
            java.lang.String r5 = "undefined"
            boolean r5 = r14.equals(r5)
            if (r5 != 0) goto L8d
            int r14 = java.lang.Integer.parseInt(r14)
            if (r14 > r8) goto L87
            if (r14 >= 0) goto L7f
            goto L87
        L7f:
            if (r0 != r14) goto L85
            java.lang.String r3 = "already_set"
        L83:
            r8 = r14
            goto L93
        L85:
            r8 = r14
            goto L38
        L87:
            java.lang.String r0 = "invalid value"
            android.util.Log.d(r4, r0)
            goto L83
        L8d:
            java.lang.String r14 = "Level is wrong"
            android.util.Log.d(r4, r14)
            goto L4b
        L93:
            boolean r14 = r3.equals(r9)
            if (r14 == 0) goto La0
            android.content.ContentResolver r14 = r13.getContentResolver()
            android.provider.Settings.Secure.putInt(r14, r1, r8)
        La0:
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            java.lang.String r0 = "ColorCorrection Intensity : "
            r14.<init>(r0)
            r14.append(r8)
            java.lang.String r0 = " Result : "
            r14.append(r0)
            r14.append(r3)
            java.lang.String r14 = r14.toString()
            android.util.Log.d(r4, r14)
            goto Lbf
        Lba:
            java.lang.String r14 = "Unit is wrong"
            android.util.Log.d(r4, r14)
        Lbf:
            android.content.ContentResolver r13 = r13.getContentResolver()
            int r13 = android.provider.Settings.Secure.getInt(r13, r1, r2)
            java.lang.String r13 = java.lang.Integer.toString(r13)
            java.lang.String r14 = "result"
            r12.putString(r14, r3)
            java.lang.String r14 = "levelValue"
            r12.putString(r14, r13)
            return r12
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.accessibility.bixby.action.visibility.ColorCorrectionAction.doChangeAction(android.content.Context,"
                    + " com.samsung.android.settings.accessibility.bixby.data.ParsedBundle):android.os.Bundle");
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction,
              // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doEnableAction(Context context, ParsedBundle parsedBundle, boolean z) {
        String str;
        Bundle bundle = new Bundle();
        if (z) {
            str = "fail";
        } else {
            HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
            Settings.Secure.putInt(
                    context.getContentResolver(), "accessibility_display_daltonizer_enabled", 0);
            str = "success";
        }
        bundle.putString("result", str);
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction,
              // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doGetCurrentStatus(Context context, ParsedBundle parsedBundle) {
        Bundle bundle = new Bundle();
        String stateAlreadyChecked =
                BixbyUtils.getStateAlreadyChecked(
                        parsedBundle.menuValue,
                        Settings.Secure.getInt(
                                        context.getContentResolver(),
                                        "accessibility_display_daltonizer_enabled",
                                        0)
                                != 0);
        String num =
                Integer.toString(
                        Settings.Secure.getInt(
                                context.getContentResolver(),
                                "accessibility_display_daltonizer_saturation_level",
                                7));
        bundle.putString("result", stateAlreadyChecked);
        bundle.putString("levelValue", num);
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doGetExclusivePopup(Context context) {
        String str;
        String str2;
        Bundle bundle = new Bundle();
        ContentResolver contentResolver = context.getContentResolver();
        String string =
                context.getString(R.string.accessibility_display_daltonizer_preference_title);
        String string2 = context.getString(R.string.colour_lens_title);
        if (Settings.Secure.getInt(contentResolver, "color_lens_switch", 0) == 1) {
            str2 = context.getString(R.string.single_exclusive_feature_message, string, string2);
            str = "true";
        } else {
            str = "false";
            str2 = null;
        }
        bundle.putString("result", str);
        bundle.putString("description", str2);
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction,
              // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doGetSupportFeature(Context context, ParsedBundle parsedBundle) {
        if (SecAccessibilityUtils.isSupportColorCorrection(context)) {
            Bundle doGetSupportFeature = super.doGetSupportFeature(context, parsedBundle);
            doGetSupportFeature.putString("accessibilityMenu", "ColorCorrection");
            return doGetSupportFeature;
        }
        Bundle bundle = new Bundle();
        bundle.putString("result", "false");
        bundle.putString("accessibilityMenu", "ColorCorrection");
        return bundle;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0077  */
    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle doSetAction(
            android.content.Context r9,
            final com.samsung.android.settings.accessibility.bixby.data.ParsedBundle r10) {
        /*
            r8 = this;
            android.os.Bundle r0 = new android.os.Bundle
            r0.<init>()
            android.content.ContentResolver r1 = r9.getContentResolver()
            int r2 = r8.getColorCorrectionType(r9)
            java.lang.String r3 = "accessibility_display_daltonizer_enabled"
            r4 = 0
            int r5 = android.provider.Settings.Secure.getInt(r1, r3, r4)
            java.lang.String[] r6 = r8.COLOR_CORRECTION_OPTION
            java.lang.String r7 = "00"
            if (r5 == 0) goto L28
            r2 = r6[r2]
            java.lang.String r5 = r10.menuValue
            boolean r2 = r2.equals(r5)
            if (r2 == 0) goto L28
            java.lang.String r8 = "already_set"
            goto La1
        L28:
            int r2 = r6.length
            java.util.stream.IntStream r2 = java.util.stream.IntStream.range(r4, r2)
            com.samsung.android.settings.accessibility.bixby.action.visibility.ColorCorrectionAction$$ExternalSyntheticLambda0 r5 = new com.samsung.android.settings.accessibility.bixby.action.visibility.ColorCorrectionAction$$ExternalSyntheticLambda0
            r5.<init>()
            java.util.stream.IntStream r8 = r2.filter(r5)
            java.util.OptionalInt r8 = r8.findFirst()
            r10 = -1
            int r8 = r8.orElse(r10)
            java.lang.String r2 = "success"
            if (r8 != r10) goto L4c
            java.lang.String r8 = "ColorCorrectionAction"
            java.lang.String r10 = "menuValue is wrong value"
            android.util.Log.d(r8, r10)
            goto L6f
        L4c:
            r10 = 3
            if (r8 > r10) goto L6f
            android.content.res.Resources r10 = r9.getResources()
            r5 = 2130903229(0x7f0300bd, float:1.741327E38)
            int[] r10 = r10.getIntArray(r5)
            if (r8 < 0) goto L62
            int r5 = r10.length
            if (r8 >= r5) goto L62
            r8 = r10[r8]
            goto L64
        L62:
            r8 = 12
        L64:
            android.content.ContentResolver r10 = r9.getContentResolver()
            java.lang.String r5 = "accessibility_display_daltonizer"
            android.provider.Settings.Secure.putInt(r10, r5, r8)
            r8 = r2
            goto L71
        L6f:
            java.lang.String r8 = "fail"
        L71:
            boolean r10 = r8.equals(r2)
            if (r10 == 0) goto La1
            java.lang.String r10 = "color_lens_switch"
            int r1 = android.provider.Settings.Secure.getInt(r1, r10, r4)
            r2 = 1
            if (r1 != r2) goto L89
            java.util.HashSet r1 = com.samsung.android.settings.accessibility.SecAccessibilityUtils.excludeDeviceNameSet
            android.content.ContentResolver r1 = r9.getContentResolver()
            android.provider.Settings.Secure.putInt(r1, r10, r4)
        L89:
            java.util.HashSet r10 = com.samsung.android.settings.accessibility.SecAccessibilityUtils.excludeDeviceNameSet
            android.content.ContentResolver r10 = r9.getContentResolver()
            android.provider.Settings.Secure.putInt(r10, r3, r2)
            android.content.ContentResolver r9 = r9.getContentResolver()
            java.lang.String r10 = "accessibility_display_daltonizer_saturation_level"
            r1 = 7
            int r9 = android.provider.Settings.Secure.getInt(r9, r10, r1)
            java.lang.String r7 = java.lang.Integer.toString(r9)
        La1:
            java.lang.String r9 = "result"
            r0.putString(r9, r8)
            java.lang.String r8 = "levelValue"
            r0.putString(r8, r7)
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.accessibility.bixby.action.visibility.ColorCorrectionAction.doSetAction(android.content.Context,"
                    + " com.samsung.android.settings.accessibility.bixby.data.ParsedBundle):android.os.Bundle");
    }

    public final int getColorCorrectionType(Context context) {
        int i =
                Settings.Secure.getInt(
                        context.getContentResolver(), "accessibility_display_daltonizer", 12);
        if (((HashMap) this.mAccessibilityDaltonizerKeyToValueMap).size() == 0) {
            int[] intArray = context.getResources().getIntArray(R.array.daltonizer_type_values);
            int length = intArray.length;
            for (int i2 = 0; i2 < length; i2++) {
                ((HashMap) this.mAccessibilityDaltonizerKeyToValueMap)
                        .put(Integer.valueOf(intArray[i2]), Integer.valueOf(i2));
            }
        }
        return ((Integer)
                        ((HashMap) this.mAccessibilityDaltonizerKeyToValueMap)
                                .get(Integer.valueOf(i)))
                .intValue();
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction
    public final String getControllerName(Context context) {
        return "com.android.settings.accessibility.DaltonizerPreferenceController";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Intent getPunchoutIntent(Context context) {
        return new Intent("com.android.settings.ACCESSIBILITY_COLOR_SPACE_SETTINGS");
    }
}
