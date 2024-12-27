package com.samsung.android.settings.accessibility.bixby.action.visibility;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction;
import com.samsung.android.settings.accessibility.bixby.data.ParsedBundle;

import java.util.HashSet;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AddColorFilterAction extends BixbyControllerAction {
    public final String[] COLORS = {
        "Blue",
        "Azure",
        "Cyan",
        "SpringGreen",
        "Green",
        "ChartreuseGreen",
        "Yellow",
        "Orange",
        "Red",
        "Rose",
        "Magenta",
        "Violet"
    };

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

       if (r0 == 8) goto L7;
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
            java.lang.String r1 = "Opacity"
            boolean r0 = r1.equals(r0)
            java.lang.String r1 = "color_lens_opacity"
            r2 = 2
            java.lang.String r3 = "fail"
            java.lang.String r4 = "AddColorFilterAction"
            if (r0 == 0) goto Lba
            android.content.ContentResolver r0 = r13.getContentResolver()
            int r0 = android.provider.Settings.Secure.getInt(r0, r1, r2)
            java.lang.String r5 = "Increase"
            java.lang.String r6 = r14.level
            boolean r5 = r5.equals(r6)
            java.lang.String r7 = "Maximum"
            r8 = 8
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
            java.lang.String r0 = "ColorLens Opacity : "
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
                    + " com.samsung.android.settings.accessibility.bixby.action.visibility.AddColorFilterAction.doChangeAction(android.content.Context,"
                    + " com.samsung.android.settings.accessibility.bixby.data.ParsedBundle):android.os.Bundle");
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction,
              // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doEnableAction(Context context, ParsedBundle parsedBundle, boolean z) {
        Bundle bundle = new Bundle();
        if (!z) {
            HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
            Settings.Secure.putInt(context.getContentResolver(), "color_lens_switch", 0);
            bundle.putString("result", "success");
        }
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction,
              // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doGetCurrentStatus(Context context, ParsedBundle parsedBundle) {
        Bundle doGetCurrentStatus = super.doGetCurrentStatus(context, parsedBundle);
        doGetCurrentStatus.putString(
                "levelValue",
                Integer.toString(
                        Settings.Secure.getInt(
                                context.getContentResolver(), "color_lens_opacity", 2)));
        return doGetCurrentStatus;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doGetExclusivePopup(Context context) {
        String string;
        String str;
        Bundle bundle = new Bundle();
        String string2 = context.getString(R.string.color_adjustment_title);
        String string3 = context.getString(R.string.colour_lens_title);
        ContentResolver contentResolver = context.getContentResolver();
        boolean z = Settings.System.getInt(contentResolver, "greyscale_mode", 0) != 0;
        boolean z2 = Settings.System.getInt(contentResolver, "color_blind", 0) != 0;
        if (z || z2) {
            string = context.getString(R.string.single_exclusive_feature_message, string3, string2);
            str = "true";
        } else {
            string = ApnSettings.MVNO_NONE;
            str = "false";
        }
        bundle.putString("description", string);
        bundle.putString("result", str);
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doSetAction(Context context, final ParsedBundle parsedBundle) {
        String str;
        Bundle bundle = new Bundle();
        ContentResolver contentResolver = context.getContentResolver();
        int i = Settings.Secure.getInt(contentResolver, "color_lens_type", 0);
        int i2 = Settings.Secure.getInt(contentResolver, "color_lens_switch", 0);
        String[] strArr = this.COLORS;
        String str2 = ApnSettings.MVNO_NONE;
        if (i2 == 0 || !strArr[i].equals(parsedBundle.menuValue)) {
            int orElse =
                    IntStream.range(0, strArr.length)
                            .filter(
                                    new IntPredicate() { // from class:
                                                         // com.samsung.android.settings.accessibility.bixby.action.visibility.AddColorFilterAction$$ExternalSyntheticLambda0
                                        @Override // java.util.function.IntPredicate
                                        public final boolean test(int i3) {
                                            return AddColorFilterAction.this.COLORS[i3].equals(
                                                    parsedBundle.menuValue);
                                        }
                                    })
                            .findFirst()
                            .orElse(-1);
            if (orElse == -1) {
                Log.d("AddColorFilterAction", "menuValue is wrong value");
                str = "fail";
            } else {
                Settings.Secure.putInt(contentResolver, "color_lens_type", orElse);
                str = "success";
            }
            if (str.equals("success")) {
                if (Settings.System.getInt(contentResolver, "color_blind", 0) != 0) {
                    SecAccessibilityUtils.setColorAdjustment(context);
                }
                HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
                Settings.Secure.putInt(context.getContentResolver(), "color_lens_switch", 1);
                str2 =
                        Integer.toString(
                                Settings.Secure.getInt(
                                        context.getContentResolver(), "color_lens_opacity", 2));
            }
        } else {
            str = "already_set";
        }
        bundle.putString("result", str);
        bundle.putString("levelValue", str2);
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction
    public final String getControllerName(Context context) {
        return "com.samsung.android.settings.accessibility.vision.controllers.ColorLensPreferenceController";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final String getDestinationFragment() {
        return "com.samsung.android.settings.accessibility.vision.color.ColorLensFragment";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final int getTitleRes() {
        return R.string.colour_lens_title;
    }
}
