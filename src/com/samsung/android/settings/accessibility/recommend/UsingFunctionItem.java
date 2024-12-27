package com.samsung.android.settings.accessibility.recommend;

import android.provider.Settings;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class UsingFunctionItem {
    public final BasePreferenceController controllable;
    public final String key;
    public final List uriList;
    public final AccessibilityUsingFunction usingFunction;

    /* JADX WARN: Multi-variable type inference failed */
    public UsingFunctionItem(BasePreferenceController basePreferenceController) {
        this.key = basePreferenceController.getPreferenceKey();
        AccessibilityUsingFunction accessibilityUsingFunction =
                (AccessibilityUsingFunction) basePreferenceController;
        this.usingFunction = accessibilityUsingFunction;
        this.controllable = basePreferenceController;
        if (accessibilityUsingFunction.getUsingFunctionType() == 200) {
            this.uriList =
                    List.of(
                            Settings.Secure.getUriFor("accessibility_button_targets"),
                            Settings.Secure.getUriFor("accessibility_shortcut_target_service"),
                            Settings.Secure.getUriFor(
                                    "accessibility_display_magnification_enabled"),
                            Settings.Secure.getUriFor(
                                    "accessibility_magnification_two_finger_triple_tap_enabled"),
                            Settings.Secure.getUriFor("accessibility_qs_targets"),
                            Settings.Secure.getUriFor(
                                    "accessibility_direct_access_target_service"));
        } else if (basePreferenceController instanceof AccessibilityObservableController) {
            this.uriList =
                    ((AccessibilityObservableController) basePreferenceController).getUriList();
        } else {
            this.uriList = List.of();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0032, code lost:

       if (r4 != 200) goto L10;
    */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isTurnedOn(android.content.Context r5) {
        /*
            r4 = this;
            com.android.settings.core.BasePreferenceController r0 = r4.controllable
            com.samsung.android.settings.cube.ControlValue r0 = r0.getValue()
            r1 = 0
            if (r0 != 0) goto La
            return r1
        La:
            r2 = 1
            int r3 = r0.mAvailabilityStatus
            if (r3 == 0) goto L11
            if (r3 != r2) goto L53
        L11:
            com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction r4 = r4.usingFunction
            java.lang.Object r5 = r4.getDefaultValue(r5)
            java.lang.String r0 = r0.getValue()
            boolean r3 = android.text.TextUtils.isEmpty(r0)
            if (r3 == 0) goto L23
        L21:
            r4 = r1
            goto L50
        L23:
            int r4 = r4.getUsingFunctionType()
            if (r4 == r2) goto L43
            r3 = 2
            if (r4 == r3) goto L35
            r3 = 101(0x65, float:1.42E-43)
            if (r4 == r3) goto L35
            r3 = 200(0xc8, float:2.8E-43)
            if (r4 == r3) goto L43
            goto L21
        L35:
            int r4 = java.lang.Integer.parseInt(r0)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            boolean r4 = r4.equals(r5)
        L41:
            r4 = r4 ^ r2
            goto L50
        L43:
            boolean r4 = java.lang.Boolean.parseBoolean(r0)
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)
            boolean r4 = r4.equals(r5)
            goto L41
        L50:
            if (r4 == 0) goto L53
            r1 = r2
        L53:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.accessibility.recommend.UsingFunctionItem.isTurnedOn(android.content.Context):boolean");
    }
}
