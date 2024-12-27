package com.samsung.android.settings.accessibility.bixby.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.bixby.action.BixbyActionData;
import com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget;
import com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.BaseColorAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.ColorAdjustmentAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.ColorCorrectionAction;
import com.samsung.android.settings.accessibility.bixby.data.ParsedBundle;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AccessibilityBixbyProvider extends ContentProvider {
    @Override // android.content.ContentProvider
    public final Bundle call(String str, String str2, Bundle bundle) {
        Context context = getContext();
        Bundle bundle2 = null;
        if (bundle == null) {
            Log.w(
                    "AccessibilityActionCommand",
                    "Bundle is null. Maybe Bixby capsule passed wrong data.");
        } else {
            ParsedBundle parsedBundle = new ParsedBundle(bundle);
            String str3 = ApnSettings.MVNO_NONE;
            String str4 = parsedBundle.bixbyLocale;
            if (ApnSettings.MVNO_NONE.equals(str4)) {
                str4 = context.getResources().getConfiguration().getLocales().get(0).getLanguage();
            } else {
                int indexOf = str4.indexOf("-");
                if (indexOf != -1) {
                    str4 = str4.substring(0, indexOf);
                }
            }
            try {
                Configuration configuration =
                        new Configuration(context.getResources().getConfiguration());
                configuration.setLocale(new Locale(str4));
                context = context.createConfigurationContext(configuration);
            } catch (ClassCastException e) {
                Log.e(
                        "AccessibilityActionCommand",
                        "Unexpected ClassCastException happened. Will return original context.",
                        e);
            }
            StringBuilder m =
                    ActivityResultRegistry$$ExternalSyntheticOutline0.m("Action=", str, ", ");
            m.append(parsedBundle.toString());
            Log.d("AccessibilityActionCommand", m.toString());
            String str5 = parsedBundle.paramValue;
            if (str5.isEmpty()) {
                String str6 = parsedBundle.actionName;
                if ("viv.accessibilityApp.GetColorAdjustmentCurrentValue".equals(str6)
                        || "viv.accessibilityApp.GetPersonalizedColorSetUpStatus".equals(str6)) {
                    str3 = "ColorAdjustment";
                } else if ("viv.accessibilityApp.TurnOnMuteAllSounds".equals(str6)) {
                    str3 = "MuteAllSounds";
                } else if ("viv.accessibilityApp.GetUniversalSwitchCurrentstatus".equals(str6)
                        || "viv.accessibilityApp.TurnOnUniversalSwitch".equals(str6)) {
                    str3 = "UniversalSwitch";
                } else if ("viv.accessibilityApp.GetVoiceRecorderStatus".equals(str6)) {
                    str3 = "VoiceLabel";
                } else if ("viv.accessibilityApp.TurnOnVoiceAssistant".equals(str6)) {
                    str3 = "VoiceAssistant";
                }
                str5 = str3;
            }
            Log.d("BixbyActionFactory", "paramValue : ".concat(str5));
            BixbyActionTarget targetMenu = BixbyActionData.getTargetMenu(str5);
            if (targetMenu instanceof BaseColorAction) {
                ((BaseColorAction) targetMenu).getClass();
                targetMenu =
                        SecAccessibilityUtils.isSupportColorAdjustment(context)
                                ? new ColorAdjustmentAction()
                                : new ColorCorrectionAction();
            }
            if (targetMenu instanceof BixbyControllerAction) {
                BixbyControllerAction bixbyControllerAction = (BixbyControllerAction) targetMenu;
                if (bixbyControllerAction.controller == null) {
                    bixbyControllerAction.controller =
                            SecAccessibilityUtils.getPreferenceController(
                                    context,
                                    str5,
                                    bixbyControllerAction.getControllerName(context));
                }
            }
            if (targetMenu != null) {
                bundle2 = targetMenu.doAction(str, parsedBundle, context);
            }
        }
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                new StringBuilder("Result - AccessibilityBixbyProvider "),
                bundle2 == null ? "null" : bundle2.toString(),
                "AccessibilityBixbyProvider");
        return bundle2;
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return false;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
