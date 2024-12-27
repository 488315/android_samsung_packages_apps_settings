package com.android.settings.accessibility;

import android.content.ComponentName;
import android.content.Context;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;

import com.android.internal.accessibility.common.ShortcutConstants;
import com.android.internal.accessibility.util.ShortcutUtils;

import com.samsung.android.settings.accessibility.AccessibilityConstant;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class PreferredShortcuts {
    public static void clearPreferredShortcuts(Context context) {
        context.getSharedPreferences("accessibility_prefs", 0).edit().clear().apply();
    }

    public static boolean isA11YService(String str) {
        return AccessibilityConstant.COMPONENT_NAME_SPEAK_KEYBOARD_INPUT_ALOUD
                        .flattenToString()
                        .equals(str)
                || AccessibilityConstant.COMPONENT_NAME_INTERACTION_CONTROL_SHORTCUT
                        .flattenToShortString()
                        .equals(str)
                || AccessibilityConstant.COMPONENT_NAME_ASSISTANT_MENU
                        .flattenToShortString()
                        .equals(str)
                || AccessibilityConstant.COMPONENT_NAME_UNIVERSAL_SWITCH
                        .flattenToShortString()
                        .equals(str);
    }

    public static int retrieveUserShortcutType(Context context, int i, String str) {
        int userShortcutTypesFromSettings;
        HashSet hashSet =
                new HashSet(
                        context.getSharedPreferences("accessibility_prefs", 0)
                                .getStringSet("user_shortcut_type", Set.of()));
        hashSet.removeIf(new PreferredShortcuts$$ExternalSyntheticLambda1(str, 1));
        if (hashSet.isEmpty()) {
            if (!isA11YService(str)) {
                return i;
            }
            int userShortcutTypesFromSettings2 =
                    AccessibilityUtil.getUserShortcutTypesFromSettings(
                            context, ComponentName.unflattenFromString(str));
            Log.i(
                    "PreferredShortcuts",
                    "retrieveUserShortcutType info.isEmpty() componentName : "
                            + str
                            + ", shortcutTypes : "
                            + userShortcutTypesFromSettings2);
            return userShortcutTypesFromSettings2 == 0 ? i : userShortcutTypesFromSettings2;
        }
        String str2 = (String) hashSet.stream().findFirst().get();
        TextUtils.SimpleStringSplitter simpleStringSplitter =
                PreferredShortcut.sStringColonSplitter;
        simpleStringSplitter.setString(str2);
        if (!simpleStringSplitter.hasNext()) {
            throw new IllegalArgumentException(
                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                            "Invalid PreferredShortcut string: ", str2));
        }
        simpleStringSplitter.next();
        int parseInt = Integer.parseInt(simpleStringSplitter.next());
        if (!isA11YService(str)
                || (userShortcutTypesFromSettings =
                                AccessibilityUtil.getUserShortcutTypesFromSettings(
                                        context, ComponentName.unflattenFromString(str)))
                        == 0
                || userShortcutTypesFromSettings == parseInt) {
            return parseInt;
        }
        TooltipPopup$$ExternalSyntheticOutline0.m(
                PreferredShortcuts$$ExternalSyntheticOutline0.m(
                        userShortcutTypesFromSettings,
                        "retrieveUserShortcutType componentName : ",
                        str,
                        ", shortcutTypesFromSettings : ",
                        ", shortcut.getType() : "),
                parseInt,
                "PreferredShortcuts");
        return userShortcutTypesFromSettings;
    }

    public static void saveUserShortcutType(Context context, PreferredShortcut preferredShortcut) {
        String str = preferredShortcut.mComponentName;
        if (str == null) {
            return;
        }
        HashSet hashSet =
                new HashSet(
                        context.getSharedPreferences("accessibility_prefs", 0)
                                .getStringSet("user_shortcut_type", Set.of()));
        hashSet.removeIf(new PreferredShortcuts$$ExternalSyntheticLambda1(str, 0));
        hashSet.add(preferredShortcut.toString());
        context.getSharedPreferences("accessibility_prefs", 0)
                .edit()
                .putStringSet("user_shortcut_type", hashSet)
                .apply();
    }

    public static void updatePreferredShortcutsFromSettings(Context context, Set set) {
        ArrayMap arrayMap = new ArrayMap();
        for (int i : ShortcutConstants.USER_SHORTCUT_TYPES) {
            arrayMap.put(
                    Integer.valueOf(i),
                    ShortcutUtils.getShortcutTargetsFromSettings(
                            context, i, UserHandle.myUserId()));
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            int i2 = 0;
            for (Map.Entry entry : arrayMap.entrySet()) {
                if (((Set) entry.getValue()).contains(str)) {
                    i2 |= ((Integer) entry.getKey()).intValue();
                }
            }
            if (i2 != 0) {
                saveUserShortcutType(context, new PreferredShortcut(str, i2));
            }
        }
    }
}
