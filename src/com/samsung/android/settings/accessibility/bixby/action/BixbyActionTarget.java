package com.samsung.android.settings.accessibility.bixby.action;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.bixby.data.ParsedBundle;
import com.samsung.android.settings.knox.KnoxUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class BixbyActionTarget {
    public final Map actionMap;

    public BixbyActionTarget() {
        HashMap hashMap = new HashMap();
        this.actionMap = hashMap;
        final int i = 0;
        hashMap.put(
                "viv.accessibilityApp.GetSupportFeature",
                new BiFunction(
                        this) { // from class:
                                // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget$$ExternalSyntheticLambda0
                    public final /* synthetic */ BixbyActionTarget f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        String str;
                        int i2 = i;
                        BixbyActionTarget bixbyActionTarget = this.f$0;
                        Context context = (Context) obj;
                        ParsedBundle parsedBundle = (ParsedBundle) obj2;
                        switch (i2) {
                            case 0:
                                return bixbyActionTarget.doGetSupportFeature(context, parsedBundle);
                            case 1:
                                return bixbyActionTarget.doGetCurrentStatus(context, parsedBundle);
                            case 2:
                                return bixbyActionTarget.doGetExclusivePopup(context);
                            case 3:
                                bixbyActionTarget.getClass();
                                Bundle bundle = new Bundle();
                                if (parsedBundle.paramValue.contains("VoiceAssistant")) {
                                    str =
                                            context.getString(
                                                            R.string.sec_enable_service_title,
                                                            context.getString(
                                                                    R.string.talkback_title))
                                                    + "\n\n"
                                                    + context.getString(
                                                            R.string
                                                                    .sec_accessibility_service_warning_description)
                                                    + "\n\n• "
                                                    + context.getString(
                                                            R.string
                                                                    .accessibility_service_screen_control_title)
                                                    + "\n"
                                                    + context.getString(
                                                            R.string
                                                                    .sec_accessibility_service_screen_control_description)
                                                    + "\n\n• "
                                                    + context.getString(
                                                            R.string
                                                                    .accessibility_service_action_perform_title)
                                                    + "\n"
                                                    + context.getString(
                                                            R.string
                                                                    .sec_accessibility_service_action_perform_description);
                                } else {
                                    str = null;
                                }
                                bundle.putString("result", "true");
                                bundle.putString("description", str);
                                return bundle;
                            case 4:
                                return bixbyActionTarget.doEnableAction(
                                        context, parsedBundle, true);
                            case 5:
                                return bixbyActionTarget.doEnableAction(
                                        context, parsedBundle, false);
                            case 6:
                                return bixbyActionTarget.doSetAction(context, parsedBundle);
                            default:
                                return bixbyActionTarget.doChangeAction(context, parsedBundle);
                        }
                    }
                });
        final int i2 = 1;
        ((HashMap) this.actionMap)
                .put(
                        "viv.accessibilityApp.GetCurrentStatus",
                        new BiFunction(
                                this) { // from class:
                                        // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget$$ExternalSyntheticLambda0
                            public final /* synthetic */ BixbyActionTarget f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.util.function.BiFunction
                            public final Object apply(Object obj, Object obj2) {
                                String str;
                                int i22 = i2;
                                BixbyActionTarget bixbyActionTarget = this.f$0;
                                Context context = (Context) obj;
                                ParsedBundle parsedBundle = (ParsedBundle) obj2;
                                switch (i22) {
                                    case 0:
                                        return bixbyActionTarget.doGetSupportFeature(
                                                context, parsedBundle);
                                    case 1:
                                        return bixbyActionTarget.doGetCurrentStatus(
                                                context, parsedBundle);
                                    case 2:
                                        return bixbyActionTarget.doGetExclusivePopup(context);
                                    case 3:
                                        bixbyActionTarget.getClass();
                                        Bundle bundle = new Bundle();
                                        if (parsedBundle.paramValue.contains("VoiceAssistant")) {
                                            str =
                                                    context.getString(
                                                                    R.string
                                                                            .sec_enable_service_title,
                                                                    context.getString(
                                                                            R.string
                                                                                    .talkback_title))
                                                            + "\n\n"
                                                            + context.getString(
                                                                    R.string
                                                                            .sec_accessibility_service_warning_description)
                                                            + "\n\n• "
                                                            + context.getString(
                                                                    R.string
                                                                            .accessibility_service_screen_control_title)
                                                            + "\n"
                                                            + context.getString(
                                                                    R.string
                                                                            .sec_accessibility_service_screen_control_description)
                                                            + "\n\n• "
                                                            + context.getString(
                                                                    R.string
                                                                            .accessibility_service_action_perform_title)
                                                            + "\n"
                                                            + context.getString(
                                                                    R.string
                                                                            .sec_accessibility_service_action_perform_description);
                                        } else {
                                            str = null;
                                        }
                                        bundle.putString("result", "true");
                                        bundle.putString("description", str);
                                        return bundle;
                                    case 4:
                                        return bixbyActionTarget.doEnableAction(
                                                context, parsedBundle, true);
                                    case 5:
                                        return bixbyActionTarget.doEnableAction(
                                                context, parsedBundle, false);
                                    case 6:
                                        return bixbyActionTarget.doSetAction(context, parsedBundle);
                                    default:
                                        return bixbyActionTarget.doChangeAction(
                                                context, parsedBundle);
                                }
                            }
                        });
        final int i3 = 2;
        ((HashMap) this.actionMap)
                .put(
                        "viv.accessibilityApp.GetExclusivePopup",
                        new BiFunction(
                                this) { // from class:
                                        // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget$$ExternalSyntheticLambda0
                            public final /* synthetic */ BixbyActionTarget f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.util.function.BiFunction
                            public final Object apply(Object obj, Object obj2) {
                                String str;
                                int i22 = i3;
                                BixbyActionTarget bixbyActionTarget = this.f$0;
                                Context context = (Context) obj;
                                ParsedBundle parsedBundle = (ParsedBundle) obj2;
                                switch (i22) {
                                    case 0:
                                        return bixbyActionTarget.doGetSupportFeature(
                                                context, parsedBundle);
                                    case 1:
                                        return bixbyActionTarget.doGetCurrentStatus(
                                                context, parsedBundle);
                                    case 2:
                                        return bixbyActionTarget.doGetExclusivePopup(context);
                                    case 3:
                                        bixbyActionTarget.getClass();
                                        Bundle bundle = new Bundle();
                                        if (parsedBundle.paramValue.contains("VoiceAssistant")) {
                                            str =
                                                    context.getString(
                                                                    R.string
                                                                            .sec_enable_service_title,
                                                                    context.getString(
                                                                            R.string
                                                                                    .talkback_title))
                                                            + "\n\n"
                                                            + context.getString(
                                                                    R.string
                                                                            .sec_accessibility_service_warning_description)
                                                            + "\n\n• "
                                                            + context.getString(
                                                                    R.string
                                                                            .accessibility_service_screen_control_title)
                                                            + "\n"
                                                            + context.getString(
                                                                    R.string
                                                                            .sec_accessibility_service_screen_control_description)
                                                            + "\n\n• "
                                                            + context.getString(
                                                                    R.string
                                                                            .accessibility_service_action_perform_title)
                                                            + "\n"
                                                            + context.getString(
                                                                    R.string
                                                                            .sec_accessibility_service_action_perform_description);
                                        } else {
                                            str = null;
                                        }
                                        bundle.putString("result", "true");
                                        bundle.putString("description", str);
                                        return bundle;
                                    case 4:
                                        return bixbyActionTarget.doEnableAction(
                                                context, parsedBundle, true);
                                    case 5:
                                        return bixbyActionTarget.doEnableAction(
                                                context, parsedBundle, false);
                                    case 6:
                                        return bixbyActionTarget.doSetAction(context, parsedBundle);
                                    default:
                                        return bixbyActionTarget.doChangeAction(
                                                context, parsedBundle);
                                }
                            }
                        });
        final int i4 = 3;
        ((HashMap) this.actionMap)
                .put(
                        "viv.accessibilityApp.GetPermissionPopup",
                        new BiFunction(
                                this) { // from class:
                                        // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget$$ExternalSyntheticLambda0
                            public final /* synthetic */ BixbyActionTarget f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.util.function.BiFunction
                            public final Object apply(Object obj, Object obj2) {
                                String str;
                                int i22 = i4;
                                BixbyActionTarget bixbyActionTarget = this.f$0;
                                Context context = (Context) obj;
                                ParsedBundle parsedBundle = (ParsedBundle) obj2;
                                switch (i22) {
                                    case 0:
                                        return bixbyActionTarget.doGetSupportFeature(
                                                context, parsedBundle);
                                    case 1:
                                        return bixbyActionTarget.doGetCurrentStatus(
                                                context, parsedBundle);
                                    case 2:
                                        return bixbyActionTarget.doGetExclusivePopup(context);
                                    case 3:
                                        bixbyActionTarget.getClass();
                                        Bundle bundle = new Bundle();
                                        if (parsedBundle.paramValue.contains("VoiceAssistant")) {
                                            str =
                                                    context.getString(
                                                                    R.string
                                                                            .sec_enable_service_title,
                                                                    context.getString(
                                                                            R.string
                                                                                    .talkback_title))
                                                            + "\n\n"
                                                            + context.getString(
                                                                    R.string
                                                                            .sec_accessibility_service_warning_description)
                                                            + "\n\n• "
                                                            + context.getString(
                                                                    R.string
                                                                            .accessibility_service_screen_control_title)
                                                            + "\n"
                                                            + context.getString(
                                                                    R.string
                                                                            .sec_accessibility_service_screen_control_description)
                                                            + "\n\n• "
                                                            + context.getString(
                                                                    R.string
                                                                            .accessibility_service_action_perform_title)
                                                            + "\n"
                                                            + context.getString(
                                                                    R.string
                                                                            .sec_accessibility_service_action_perform_description);
                                        } else {
                                            str = null;
                                        }
                                        bundle.putString("result", "true");
                                        bundle.putString("description", str);
                                        return bundle;
                                    case 4:
                                        return bixbyActionTarget.doEnableAction(
                                                context, parsedBundle, true);
                                    case 5:
                                        return bixbyActionTarget.doEnableAction(
                                                context, parsedBundle, false);
                                    case 6:
                                        return bixbyActionTarget.doSetAction(context, parsedBundle);
                                    default:
                                        return bixbyActionTarget.doChangeAction(
                                                context, parsedBundle);
                                }
                            }
                        });
        final int i5 = 4;
        ((HashMap) this.actionMap)
                .put(
                        "viv.accessibilityApp.EnableAccessibilityMenu",
                        new BiFunction(
                                this) { // from class:
                                        // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget$$ExternalSyntheticLambda0
                            public final /* synthetic */ BixbyActionTarget f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.util.function.BiFunction
                            public final Object apply(Object obj, Object obj2) {
                                String str;
                                int i22 = i5;
                                BixbyActionTarget bixbyActionTarget = this.f$0;
                                Context context = (Context) obj;
                                ParsedBundle parsedBundle = (ParsedBundle) obj2;
                                switch (i22) {
                                    case 0:
                                        return bixbyActionTarget.doGetSupportFeature(
                                                context, parsedBundle);
                                    case 1:
                                        return bixbyActionTarget.doGetCurrentStatus(
                                                context, parsedBundle);
                                    case 2:
                                        return bixbyActionTarget.doGetExclusivePopup(context);
                                    case 3:
                                        bixbyActionTarget.getClass();
                                        Bundle bundle = new Bundle();
                                        if (parsedBundle.paramValue.contains("VoiceAssistant")) {
                                            str =
                                                    context.getString(
                                                                    R.string
                                                                            .sec_enable_service_title,
                                                                    context.getString(
                                                                            R.string
                                                                                    .talkback_title))
                                                            + "\n\n"
                                                            + context.getString(
                                                                    R.string
                                                                            .sec_accessibility_service_warning_description)
                                                            + "\n\n• "
                                                            + context.getString(
                                                                    R.string
                                                                            .accessibility_service_screen_control_title)
                                                            + "\n"
                                                            + context.getString(
                                                                    R.string
                                                                            .sec_accessibility_service_screen_control_description)
                                                            + "\n\n• "
                                                            + context.getString(
                                                                    R.string
                                                                            .accessibility_service_action_perform_title)
                                                            + "\n"
                                                            + context.getString(
                                                                    R.string
                                                                            .sec_accessibility_service_action_perform_description);
                                        } else {
                                            str = null;
                                        }
                                        bundle.putString("result", "true");
                                        bundle.putString("description", str);
                                        return bundle;
                                    case 4:
                                        return bixbyActionTarget.doEnableAction(
                                                context, parsedBundle, true);
                                    case 5:
                                        return bixbyActionTarget.doEnableAction(
                                                context, parsedBundle, false);
                                    case 6:
                                        return bixbyActionTarget.doSetAction(context, parsedBundle);
                                    default:
                                        return bixbyActionTarget.doChangeAction(
                                                context, parsedBundle);
                                }
                            }
                        });
        final int i6 = 5;
        ((HashMap) this.actionMap)
                .put(
                        "viv.accessibilityApp.DisableAccessibilityMenu",
                        new BiFunction(
                                this) { // from class:
                                        // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget$$ExternalSyntheticLambda0
                            public final /* synthetic */ BixbyActionTarget f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.util.function.BiFunction
                            public final Object apply(Object obj, Object obj2) {
                                String str;
                                int i22 = i6;
                                BixbyActionTarget bixbyActionTarget = this.f$0;
                                Context context = (Context) obj;
                                ParsedBundle parsedBundle = (ParsedBundle) obj2;
                                switch (i22) {
                                    case 0:
                                        return bixbyActionTarget.doGetSupportFeature(
                                                context, parsedBundle);
                                    case 1:
                                        return bixbyActionTarget.doGetCurrentStatus(
                                                context, parsedBundle);
                                    case 2:
                                        return bixbyActionTarget.doGetExclusivePopup(context);
                                    case 3:
                                        bixbyActionTarget.getClass();
                                        Bundle bundle = new Bundle();
                                        if (parsedBundle.paramValue.contains("VoiceAssistant")) {
                                            str =
                                                    context.getString(
                                                                    R.string
                                                                            .sec_enable_service_title,
                                                                    context.getString(
                                                                            R.string
                                                                                    .talkback_title))
                                                            + "\n\n"
                                                            + context.getString(
                                                                    R.string
                                                                            .sec_accessibility_service_warning_description)
                                                            + "\n\n• "
                                                            + context.getString(
                                                                    R.string
                                                                            .accessibility_service_screen_control_title)
                                                            + "\n"
                                                            + context.getString(
                                                                    R.string
                                                                            .sec_accessibility_service_screen_control_description)
                                                            + "\n\n• "
                                                            + context.getString(
                                                                    R.string
                                                                            .accessibility_service_action_perform_title)
                                                            + "\n"
                                                            + context.getString(
                                                                    R.string
                                                                            .sec_accessibility_service_action_perform_description);
                                        } else {
                                            str = null;
                                        }
                                        bundle.putString("result", "true");
                                        bundle.putString("description", str);
                                        return bundle;
                                    case 4:
                                        return bixbyActionTarget.doEnableAction(
                                                context, parsedBundle, true);
                                    case 5:
                                        return bixbyActionTarget.doEnableAction(
                                                context, parsedBundle, false);
                                    case 6:
                                        return bixbyActionTarget.doSetAction(context, parsedBundle);
                                    default:
                                        return bixbyActionTarget.doChangeAction(
                                                context, parsedBundle);
                                }
                            }
                        });
        final int i7 = 6;
        ((HashMap) this.actionMap)
                .put(
                        "viv.accessibilityApp.SetAccessibilityMenuValue",
                        new BiFunction(
                                this) { // from class:
                                        // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget$$ExternalSyntheticLambda0
                            public final /* synthetic */ BixbyActionTarget f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.util.function.BiFunction
                            public final Object apply(Object obj, Object obj2) {
                                String str;
                                int i22 = i7;
                                BixbyActionTarget bixbyActionTarget = this.f$0;
                                Context context = (Context) obj;
                                ParsedBundle parsedBundle = (ParsedBundle) obj2;
                                switch (i22) {
                                    case 0:
                                        return bixbyActionTarget.doGetSupportFeature(
                                                context, parsedBundle);
                                    case 1:
                                        return bixbyActionTarget.doGetCurrentStatus(
                                                context, parsedBundle);
                                    case 2:
                                        return bixbyActionTarget.doGetExclusivePopup(context);
                                    case 3:
                                        bixbyActionTarget.getClass();
                                        Bundle bundle = new Bundle();
                                        if (parsedBundle.paramValue.contains("VoiceAssistant")) {
                                            str =
                                                    context.getString(
                                                                    R.string
                                                                            .sec_enable_service_title,
                                                                    context.getString(
                                                                            R.string
                                                                                    .talkback_title))
                                                            + "\n\n"
                                                            + context.getString(
                                                                    R.string
                                                                            .sec_accessibility_service_warning_description)
                                                            + "\n\n• "
                                                            + context.getString(
                                                                    R.string
                                                                            .accessibility_service_screen_control_title)
                                                            + "\n"
                                                            + context.getString(
                                                                    R.string
                                                                            .sec_accessibility_service_screen_control_description)
                                                            + "\n\n• "
                                                            + context.getString(
                                                                    R.string
                                                                            .accessibility_service_action_perform_title)
                                                            + "\n"
                                                            + context.getString(
                                                                    R.string
                                                                            .sec_accessibility_service_action_perform_description);
                                        } else {
                                            str = null;
                                        }
                                        bundle.putString("result", "true");
                                        bundle.putString("description", str);
                                        return bundle;
                                    case 4:
                                        return bixbyActionTarget.doEnableAction(
                                                context, parsedBundle, true);
                                    case 5:
                                        return bixbyActionTarget.doEnableAction(
                                                context, parsedBundle, false);
                                    case 6:
                                        return bixbyActionTarget.doSetAction(context, parsedBundle);
                                    default:
                                        return bixbyActionTarget.doChangeAction(
                                                context, parsedBundle);
                                }
                            }
                        });
        final int i8 = 7;
        ((HashMap) this.actionMap)
                .put(
                        "viv.accessibilityApp.AdjustAccessibilityMenuValue",
                        new BiFunction(
                                this) { // from class:
                                        // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget$$ExternalSyntheticLambda0
                            public final /* synthetic */ BixbyActionTarget f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.util.function.BiFunction
                            public final Object apply(Object obj, Object obj2) {
                                String str;
                                int i22 = i8;
                                BixbyActionTarget bixbyActionTarget = this.f$0;
                                Context context = (Context) obj;
                                ParsedBundle parsedBundle = (ParsedBundle) obj2;
                                switch (i22) {
                                    case 0:
                                        return bixbyActionTarget.doGetSupportFeature(
                                                context, parsedBundle);
                                    case 1:
                                        return bixbyActionTarget.doGetCurrentStatus(
                                                context, parsedBundle);
                                    case 2:
                                        return bixbyActionTarget.doGetExclusivePopup(context);
                                    case 3:
                                        bixbyActionTarget.getClass();
                                        Bundle bundle = new Bundle();
                                        if (parsedBundle.paramValue.contains("VoiceAssistant")) {
                                            str =
                                                    context.getString(
                                                                    R.string
                                                                            .sec_enable_service_title,
                                                                    context.getString(
                                                                            R.string
                                                                                    .talkback_title))
                                                            + "\n\n"
                                                            + context.getString(
                                                                    R.string
                                                                            .sec_accessibility_service_warning_description)
                                                            + "\n\n• "
                                                            + context.getString(
                                                                    R.string
                                                                            .accessibility_service_screen_control_title)
                                                            + "\n"
                                                            + context.getString(
                                                                    R.string
                                                                            .sec_accessibility_service_screen_control_description)
                                                            + "\n\n• "
                                                            + context.getString(
                                                                    R.string
                                                                            .accessibility_service_action_perform_title)
                                                            + "\n"
                                                            + context.getString(
                                                                    R.string
                                                                            .sec_accessibility_service_action_perform_description);
                                        } else {
                                            str = null;
                                        }
                                        bundle.putString("result", "true");
                                        bundle.putString("description", str);
                                        return bundle;
                                    case 4:
                                        return bixbyActionTarget.doEnableAction(
                                                context, parsedBundle, true);
                                    case 5:
                                        return bixbyActionTarget.doEnableAction(
                                                context, parsedBundle, false);
                                    case 6:
                                        return bixbyActionTarget.doSetAction(context, parsedBundle);
                                    default:
                                        return bixbyActionTarget.doChangeAction(
                                                context, parsedBundle);
                                }
                            }
                        });
        addCustomAction(this.actionMap);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final Bundle doAction(String str, ParsedBundle parsedBundle, Context context) {
        final int i;
        final int i2;
        final String str2;
        String str3;
        PackageInfo packageInfo;
        i = 1;
        i2 = 0;
        StringBuilder m =
                ActivityResultRegistry$$ExternalSyntheticOutline0.m("action = ", str, " bundle : ");
        m.append(parsedBundle.toString());
        Log.d("BixbyActionTarget", m.toString());
        str.getClass();
        String str4 = null;
        str2 = parsedBundle.actionName;
        switch (str) {
            case "enable":
            case "turn_on":
                if (((ActionConverter$ENABLE)
                                Arrays.stream(ActionConverter$ENABLE.values())
                                        .filter(
                                                new Predicate() { // from class:
                                                                  // com.samsung.android.settings.accessibility.bixby.action.ActionConverter$ENABLE$$ExternalSyntheticLambda0
                                                    @Override // java.util.function.Predicate
                                                    public final boolean test(Object obj) {
                                                        boolean equals;
                                                        boolean equals2;
                                                        int i3 = i2;
                                                        String str5 = str2;
                                                        switch (i3) {
                                                            case 0:
                                                                equals =
                                                                        str5.equals(
                                                                                ((ActionConverter$ENABLE)
                                                                                                obj)
                                                                                        .name);
                                                                return equals;
                                                            default:
                                                                equals2 =
                                                                        str5.equals(
                                                                                ((ActionConverter$DISABLE)
                                                                                                obj)
                                                                                        .name);
                                                                return equals2;
                                                        }
                                                    }
                                                })
                                        .findFirst()
                                        .orElse(null))
                        != null) {
                    str3 = "viv.accessibilityApp.EnableAccessibilityMenu";
                    break;
                }
                str3 = str2;
                break;
            case "turn_off":
            case "disable":
                if (((ActionConverter$DISABLE)
                                Arrays.stream(ActionConverter$DISABLE.values())
                                        .filter(
                                                new Predicate() { // from class:
                                                                  // com.samsung.android.settings.accessibility.bixby.action.ActionConverter$ENABLE$$ExternalSyntheticLambda0
                                                    @Override // java.util.function.Predicate
                                                    public final boolean test(Object obj) {
                                                        boolean equals;
                                                        boolean equals2;
                                                        int i3 = i;
                                                        String str5 = str2;
                                                        switch (i3) {
                                                            case 0:
                                                                equals =
                                                                        str5.equals(
                                                                                ((ActionConverter$ENABLE)
                                                                                                obj)
                                                                                        .name);
                                                                return equals;
                                                            default:
                                                                equals2 =
                                                                        str5.equals(
                                                                                ((ActionConverter$DISABLE)
                                                                                                obj)
                                                                                        .name);
                                                                return equals2;
                                                        }
                                                    }
                                                })
                                        .findFirst()
                                        .orElse(null))
                        != null) {
                    str3 = "viv.accessibilityApp.DisableAccessibilityMenu";
                    break;
                }
                str3 = str2;
                break;
            default:
                str3 = str2;
                break;
        }
        BiFunction biFunction = (BiFunction) ((HashMap) this.actionMap).get(str3);
        if (biFunction == null) {
            return AbsAdapter$1$$ExternalSyntheticOutline0.m("result", "false");
        }
        Bundle bundle = (Bundle) biFunction.apply(context, parsedBundle);
        if (bundle == null) {
            bundle = AbsAdapter$1$$ExternalSyntheticOutline0.m("result", "false");
        }
        if (!str2.equals(ApnSettings.MVNO_NONE)) {
            bundle.putString("ActionName", str2);
        }
        String str5 = parsedBundle.bixbyLocale;
        if (!str5.equals(ApnSettings.MVNO_NONE)) {
            bundle.putString("BixbyLocale", str5);
        }
        String str6 = parsedBundle.level;
        if (!str6.equals(ApnSettings.MVNO_NONE)) {
            bundle.putString("Level", str6);
        }
        String str7 = parsedBundle.levelValue;
        if (!str7.equals(ApnSettings.MVNO_NONE)) {
            bundle.putString("LevelValue", str7);
        }
        String str8 = parsedBundle.menuValue;
        if (!str8.equals(ApnSettings.MVNO_NONE)) {
            bundle.putString("menuValue", str8);
        }
        String str9 = parsedBundle.unit;
        if (!str9.equals(ApnSettings.MVNO_NONE)) {
            bundle.putString("Unit", str9);
        }
        String str10 = parsedBundle.paramValue;
        if (!str10.equals(ApnSettings.MVNO_NONE)
                && (!str10.equals("ColorAdjustment")
                        || !str2.equals("viv.accessibilityApp.GetSupportFeature"))) {
            bundle.putString("accessibilityMenu", str10);
        }
        if (str2.equals("viv.accessibilityApp.GetSupportFeature")) {
            HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
            if (!TextUtils.isEmpty("com.samsung.accessibility")) {
                try {
                    packageInfo =
                            context.getPackageManager()
                                    .getPackageInfo(
                                            "com.samsung.accessibility".toString(),
                                            PackageManager.PackageInfoFlags.of(0L));
                } catch (PackageManager.NameNotFoundException unused) {
                    packageInfo = null;
                }
                if (packageInfo == null) {
                    Log.e("A11yUtils", "Could not find package: com.samsung.accessibility");
                } else {
                    str4 = packageInfo.versionName;
                }
            }
            bundle.putString("description", str4);
        }
        return bundle;
    }

    public Bundle doChangeAction(Context context, ParsedBundle parsedBundle) {
        return null;
    }

    public Bundle doEnableAction(Context context, ParsedBundle parsedBundle, boolean z) {
        return null;
    }

    public Bundle doGetCurrentStatus(Context context, ParsedBundle parsedBundle) {
        return null;
    }

    public Bundle doGetExclusivePopup(Context context) {
        return null;
    }

    public Bundle doGetSupportFeature(Context context, ParsedBundle parsedBundle) {
        Bundle bundle = new Bundle();
        if (isApplicationRestricted(context)) {
            bundle.putString("result", "DeviceChangeNeeded");
        } else {
            bundle.putString("result", "true");
        }
        return bundle;
    }

    public Bundle doSetAction(Context context, ParsedBundle parsedBundle) {
        return null;
    }

    public String getDestinationFragment() {
        return null;
    }

    public Intent getPunchoutIntent(Context context) {
        return null;
    }

    public List getTargetRestrictionKeys() {
        return null;
    }

    public int getTitleRes() {
        return -1;
    }

    public final boolean isApplicationRestricted(Context context) {
        List targetRestrictionKeys = getTargetRestrictionKeys();
        if (targetRestrictionKeys == null) {
            return false;
        }
        KnoxUtils.updateRestrictionState(context.getApplicationContext());
        return KnoxUtils.isApplicationRestricted(context, targetRestrictionKeys);
    }

    public void addCustomAction(Map map) {}
}
