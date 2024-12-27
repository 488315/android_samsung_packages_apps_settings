package com.android.settings.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;

import com.android.internal.R;
import com.android.settings.R$styleable;

import com.samsung.android.knox.net.firewall.FirewallRule;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class PreferenceXmlParserUtils {
    public static final List SUPPORTED_PREF_TYPES =
            Arrays.asList(
                    "SecPreference",
                    "SecPreferenceCategory",
                    "SecPreferenceScreen",
                    "SecSwitchPreferenceScreen",
                    "com.samsung.android.settings.widget.SecRestrictedSwitchPreferenceScreen",
                    "com.samsung.android.settings.widget.SecDisabledAppearanceSwitchPreferenceScreen",
                    "com.samsung.android.settings.multidevices.SecMultiDevicesSwitchPreferenceScreen",
                    "com.samsung.android.settings.usefulfeature.intelligenceservice.SecIntelligenceSwitchPreferenceScreen",
                    "Preference",
                    "PreferenceCategory",
                    "PreferenceScreen",
                    "SwitchPreferenceCompat",
                    "com.android.settings.widget.WorkOnlyCategory");

    public static List extractMetadata(Context context, int i, int i2) {
        int next;
        ArrayList arrayList = new ArrayList();
        if (i <= 0) {
            Log.d("PreferenceXmlParserUtil", i + FirewallRule.IS_INVALID);
            return arrayList;
        }
        XmlResourceParser xml = context.getResources().getXml(i);
        do {
            next = xml.next();
            if (next == 1) {
                break;
            }
        } while (next != 2);
        int depth = xml.getDepth();
        boolean hasFlag = hasFlag(i2, 1);
        while (true) {
            if (next == 2) {
                String name = xml.getName();
                if ((hasFlag || !TextUtils.equals("PreferenceScreen", name))
                        && (SUPPORTED_PREF_TYPES.contains(name) || name.endsWith("Preference"))) {
                    Bundle bundle = new Bundle();
                    AttributeSet asAttributeSet = Xml.asAttributeSet(xml);
                    TypedArray obtainStyledAttributes =
                            context.obtainStyledAttributes(asAttributeSet, R$styleable.Preference);
                    TypedArray obtainStyledAttributes2 =
                            hasFlag
                                    ? context.obtainStyledAttributes(
                                            asAttributeSet, R$styleable.PreferenceScreen)
                                    : null;
                    if (hasFlag(i2, 4)) {
                        bundle.putString("type", name);
                    }
                    if (hasFlag(i2, 2)) {
                        bundle.putString(
                                GoodSettingsContract.EXTRA_NAME.POLICY_KEY,
                                obtainStyledAttributes.getString(6));
                    }
                    if (hasFlag(i2, 8)) {
                        bundle.putString("controller", obtainStyledAttributes.getString(18));
                    }
                    if (hasFlag(i2, 16)) {
                        bundle.putString(
                                UniversalCredentialUtil.AGENT_TITLE,
                                obtainStyledAttributes.getString(4));
                    }
                    if (hasFlag(i2, 32)) {
                        bundle.putString(
                                UniversalCredentialUtil.AGENT_SUMMARY,
                                obtainStyledAttributes.getString(7));
                    }
                    if (hasFlag(i2, 64)) {
                        bundle.putInt("icon", obtainStyledAttributes.getResourceId(0, 0));
                    }
                    if (hasFlag(i2, 256)) {
                        bundle.putString("keywords", obtainStyledAttributes.getString(31));
                    }
                    if (hasFlag(i2, 512)) {
                        bundle.putBoolean(
                                "searchable", obtainStyledAttributes.getBoolean(35, true));
                    }
                    if (hasFlag(i2, 1024) && hasFlag) {
                        bundle.putBoolean(
                                "staticPreferenceLocation",
                                obtainStyledAttributes2.getInt(1, 0) == 1);
                    }
                    if (hasFlag(i2, 2048)) {
                        bundle.putString(
                                "unavailable_slice_subtitle", obtainStyledAttributes.getString(41));
                    }
                    if (hasFlag(i2, 4096)) {
                        bundle.putBoolean("for_work", obtainStyledAttributes.getBoolean(23, false));
                    }
                    if (hasFlag(i2, 8192)) {
                        bundle.putString(
                                "highlightable_menu_key", obtainStyledAttributes.getString(25));
                    }
                    if (hasFlag(i2, NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT)) {
                        TypedArray obtainStyledAttributes3 =
                                context.obtainStyledAttributes(
                                        asAttributeSet,
                                        com.android.settingslib.R$styleable.RestrictedPreference);
                        String string = obtainStyledAttributes3.getString(1);
                        obtainStyledAttributes3.recycle();
                        bundle.putString("userRestriction", string);
                    }
                    arrayList.add(bundle);
                    obtainStyledAttributes.recycle();
                    if (obtainStyledAttributes2 != null) {
                        obtainStyledAttributes2.recycle();
                    }
                }
            }
            next = xml.next();
            if (next == 1 || (next == 3 && xml.getDepth() <= depth)) {
                break;
            }
        }
        xml.close();
        return arrayList;
    }

    public static String getDataTitle(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R.styleable.Preference);
        String string = obtainStyledAttributes.getString(4);
        obtainStyledAttributes.recycle();
        return string;
    }

    public static int getTitleResId(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R.styleable.Preference);
        int resourceId = obtainStyledAttributes.getResourceId(4, 0);
        obtainStyledAttributes.recycle();
        return resourceId;
    }

    public static boolean hasFlag(int i, int i2) {
        return (i & i2) != 0;
    }
}
