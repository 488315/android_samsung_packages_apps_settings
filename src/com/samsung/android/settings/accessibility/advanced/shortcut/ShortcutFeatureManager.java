package com.samsung.android.settings.accessibility.advanced.shortcut;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.AccessibilityShortcutInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityManager;

import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.settings.accessibility.AccessibilityConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ShortcutFeatureManager {
    public static final Map additionalInfoMap;
    public static final List candidateBuilders;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutFeatureManager$1, reason: invalid class name */
    public final class AnonymousClass1 extends ShortcutServiceCandidate.Builder {
        @Override // com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutServiceCandidate.Builder
        public final Drawable getIcon(Context context) {
            Drawable drawable = context.getDrawable(R.drawable.ic_talkback);
            if (drawable == null) {
                return drawable;
            }
            StringBuilder sb = Utils.sBuilder;
            return !TextUtils.isEmpty(
                            Settings.System.getString(
                                    context.getContentResolver(),
                                    "current_sec_active_themepackage"))
                    ? context.getPackageManager().semGetDrawableForIconTray(drawable, 1)
                    : drawable;
        }
    }

    static {
        ArrayList arrayList = new ArrayList();
        candidateBuilders = arrayList;
        HashMap hashMap = new HashMap();
        additionalInfoMap = hashMap;
        new HashSet();
        String flattenToString =
                AccessibilityConstant.COMPONENT_NAME_ACCESSIBILITY_HOMEPAGE_SHORTCUT
                        .flattenToString();
        Info info = new Info(-999);
        info.blockedFlag = 16;
        info.category = "NONE";
        hashMap.put(flattenToString, info);
        String flattenToString2 =
                AccessibilityConstant.COMPONENT_NAME_GOOGLE_TALKBACK.flattenToString();
        Info info2 = new Info(-998);
        info2.blockedFlag = 16;
        info2.category = "talkback";
        hashMap.put(flattenToString2, info2);
        String flattenToString3 =
                AccessibilityConstant.COMPONENT_NAME_SAMSUNG_TALKBACK.flattenToString();
        Info info3 = new Info(-997);
        info3.blockedFlag = 16;
        info3.category = "talkback";
        hashMap.put(flattenToString3, info3);
        String flattenToString4 =
                AccessibilityConstant.COMPONENT_NAME_MAGNIFIER_CAMERA_SHORTCUT.flattenToString();
        Info info4 = new Info(-996);
        info4.controller =
                "com.samsung.android.settings.accessibility.vision.controllers.MagnifierCameraPreferenceController";
        info4.blockedFlag = 16;
        info4.category = "visibility";
        hashMap.put(flattenToString4, info4);
        ShortcutActivityCandidate.Builder builder =
                new ShortcutActivityCandidate.Builder(
                        "com.android.server.accessibility.MagnificationController");
        builder.labelRes = R.string.accessibility_screen_magnification_title;
        builder.iconRes = R.drawable.ic_magnification;
        builder.exclusiveFeature = "magnification_shortcut";
        builder.blockedFlag = 16;
        builder.priority = -995;
        builder.className = "com.android.settings.accessibility.MagnificationPreferenceController";
        builder.category = "visibility";
        arrayList.add(builder);
        ShortcutActivityCandidate.Builder builder2 =
                new ShortcutActivityCandidate.Builder(
                        AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME
                                .flattenToString());
        builder2.labelRes = R.string.accessibility_display_inversion_preference_title;
        builder2.iconRes = R.drawable.ic_color_inversion;
        builder2.priority = -994;
        builder2.className =
                "com.android.settings.accessibility.ColorInversionPreferenceController";
        builder2.category = "visibility";
        arrayList.add(builder2);
        String flattenToString5 =
                AccessibilityConstant.COMPONENT_NAME_HIGH_CONTRAST_FONT_SHORTCUT.flattenToString();
        Info info5 = new Info(-993);
        info5.controller =
                "com.samsung.android.settings.accessibility.vision.controllers.HighContrastFontPreferenceController";
        info5.category = "visibility";
        hashMap.put(flattenToString5, info5);
        String flattenToString6 =
                AccessibilityConstant.COMPONENT_NAME_RELUMINO_SHORTCUT.flattenToString();
        Info info6 = new Info(-992);
        info6.exclusiveFeature = "relumino";
        info6.blockedFlag = 16;
        info6.controller =
                "com.samsung.android.settings.accessibility.vision.controllers.ReluminoPreferenceController";
        info6.category = "visibility";
        hashMap.put(flattenToString6, info6);
        ShortcutActivityCandidate.Builder builder3 =
                new ShortcutActivityCandidate.Builder(
                        AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME
                                .flattenToString());
        builder3.labelRes = R.string.accessibility_display_daltonizer_preference_title;
        builder3.iconRes = R.drawable.ic_color_correction;
        builder3.priority = -991;
        builder3.className = "com.android.settings.accessibility.DaltonizerPreferenceController";
        builder3.category = "visibility";
        arrayList.add(builder3);
        String flattenToString7 =
                AccessibilityConstant.COMPONENT_NAME_COLOR_ADJUSTMENT_SHORTCUT.flattenToString();
        Info info7 = new Info(-990);
        info7.exclusiveFeature = "color_blind";
        info7.blockedFlag = 16;
        info7.controller =
                "com.samsung.android.settings.accessibility.vision.controllers.ColorAdjustmentPreferenceController";
        info7.category = "visibility";
        hashMap.put(flattenToString7, info7);
        String flattenToString8 =
                AccessibilityConstant.COMPONENT_NAME_COLOR_LENS_SHORTCUT.flattenToString();
        Info info8 = new Info(-989);
        info8.exclusiveFeature = "color_lens";
        info8.controller =
                "com.samsung.android.settings.accessibility.vision.controllers.ColorLensPreferenceController";
        info8.category = "visibility";
        hashMap.put(flattenToString8, info8);
        ShortcutActivityCandidate.Builder builder4 =
                new ShortcutActivityCandidate.Builder(
                        AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_COMPONENT_NAME
                                .flattenToString());
        builder4.labelRes = R.string.reduce_bright_colors_preference_title;
        builder4.iconRes = R.drawable.ic_extra_dim;
        builder4.priority = -988;
        builder4.className =
                "com.samsung.android.settings.accessibility.vision.controllers.ReduceBrightColorsPreferenceController";
        builder4.category = "visibility";
        arrayList.add(builder4);
        String flattenToString9 =
                AccessibilityConstant.COMPONENT_NAME_SPEAK_KEYBOARD_INPUT_ALOUD.flattenToString();
        Info info9 = new Info(-987);
        info9.blockedFlag = 16;
        info9.category = "visibility";
        hashMap.put(flattenToString9, info9);
        String flattenToString10 =
                AccessibilityConstant.COMPONENT_NAME_HEARING_AID_SUPPORT_SHORTCUT.flattenToString();
        Info info10 = new Info(-986);
        info10.controller =
                "com.samsung.android.settings.accessibility.hearing.controllers.HearingAidsPreferenceController";
        info10.blockedFlag = 16;
        info10.category = "hearing";
        hashMap.put(flattenToString10, info10);
        String flattenToString11 =
                AccessibilityConstant.COMPONENT_NAME_AMPLIFY_AMBIENT_SOUND_SHORTCUT
                        .flattenToString();
        Info info11 = new Info(-985);
        info11.controller =
                "com.samsung.android.settings.accessibility.hearing.controllers.AmplifyAmbientSoundPreferenceController";
        info11.blockedFlag = 16;
        info11.category = "hearing";
        hashMap.put(flattenToString11, info11);
        String flattenToString12 =
                AccessibilityConstant.COMPONENT_NAME_MUTE_ALL_SOUNDS_SHORTCUT.flattenToString();
        Info info12 = new Info(-984);
        info12.exclusiveFeature = "mute_all_sound";
        info12.blockedFlag = 16;
        info12.controller =
                "com.samsung.android.settings.accessibility.hearing.controllers.MuteAllSoundsPreferenceController";
        info12.category = "hearing";
        hashMap.put(flattenToString12, info12);
        String flattenToString13 =
                AccessibilityConstant.COMPONENT_NAME_GOOGLE_SOUND_NOTIFICATION_SHORTCUT
                        .flattenToString();
        Info info13 = new Info(-983);
        info13.category = "hearing";
        hashMap.put(flattenToString13, info13);
        String flattenToString14 =
                AccessibilityConstant.COMPONENT_NAME_LIVE_TRANSCRIBE.flattenToString();
        Info info14 = new Info(-982);
        info14.category = "hearing";
        hashMap.put(flattenToString14, info14);
        String flattenToString15 =
                AccessibilityConstant.COMPONENT_NAME_GOOGLE_LIVE_TRANSCRIBE_SHORTCUT
                        .flattenToString();
        Info info15 = new Info(-981);
        info15.category = "hearing";
        hashMap.put(flattenToString15, info15);
        ShortcutActivityCandidate.Builder builder5 =
                new ShortcutActivityCandidate.Builder(
                        AccessibilityConstant.COMPONENT_NAME_UNIVERSAL_SWITCH
                                .flattenToShortString());
        builder5.labelRes = R.string.universal_switch_title;
        builder5.iconRes = R.drawable.ic_universal_switch;
        builder5.exclusiveFeature = "universal_switch";
        builder5.blockedFlag = 16;
        builder5.priority = -980;
        builder5.className =
                "com.samsung.android.settings.accessibility.dexterity.UniversalSwitchPreferenceController";
        builder5.category = "dexterity";
        arrayList.add(builder5);
        ShortcutActivityCandidate.Builder builder6 =
                new ShortcutActivityCandidate.Builder(
                        AccessibilityConstant.COMPONENT_NAME_ASSISTANT_MENU.flattenToShortString());
        builder6.labelRes = R.string.assistant_menu_title;
        builder6.iconRes = R.drawable.ic_assistant_menu;
        builder6.exclusiveFeature = "assistant_menu";
        builder6.blockedFlag = 16;
        builder6.priority = -979;
        builder6.className =
                "com.samsung.android.settings.accessibility.dexterity.AssistantMenuPreferenceController";
        builder6.category = "dexterity";
        arrayList.add(builder6);
        String flattenToString16 =
                AccessibilityConstant.COMPONENT_NAME_VOICE_ACCESS.flattenToString();
        Info info16 = new Info(-978);
        info16.blockedFlag = 16;
        info16.category = "dexterity";
        hashMap.put(flattenToString16, info16);
        ShortcutActivityCandidate.Builder builder7 =
                new ShortcutActivityCandidate.Builder(
                        AccessibilityConstant.COMPONENT_NAME_INTERACTION_CONTROL_SHORTCUT
                                .flattenToShortString());
        builder7.labelRes = R.string.accessibility_access_control_title;
        builder7.iconRes = R.drawable.ic_interaction_control;
        builder7.exclusiveFeature = "interaction_control";
        builder7.blockedFlag = 17;
        builder7.priority = -977;
        builder7.className =
                "com.samsung.android.settings.accessibility.dexterity.AccessControlPreferenceController";
        builder7.category = "dexterity";
        arrayList.add(builder7);
    }

    public static List getActivityCandidateBuilders(Context context) {
        List installedAccessibilityShortcutListAsUser =
                AccessibilityManager.getInstance(context)
                        .getInstalledAccessibilityShortcutListAsUser(
                                context, UserHandle.myUserId());
        ArrayList arrayList = new ArrayList();
        Iterator it = installedAccessibilityShortcutListAsUser.iterator();
        while (it.hasNext()) {
            ShortcutActivityCandidate.Builder builder =
                    new ShortcutActivityCandidate.Builder(
                            ((AccessibilityShortcutInfo) it.next()).getActivityInfo());
            HashMap hashMap = (HashMap) additionalInfoMap;
            String str = builder.componentName;
            Info info = (Info) hashMap.get(str);
            if (info == null) {
                info = new Info();
            }
            if (!AccessibilityConstant.COMPONENT_NAME_INTERACTION_CONTROL_SHORTCUT
                    .flattenToString()
                    .equals(str)) {
                builder.priority = info.priority;
                builder.className = info.controller;
                builder.blockedFlag = info.blockedFlag;
                builder.exclusiveFeature = info.exclusiveFeature;
                builder.category = info.category;
                arrayList.add(builder);
            }
        }
        arrayList.addAll(candidateBuilders);
        return arrayList;
    }

    public static HashMap getCandidatesMap(Context context, int i) {
        HashMap hashMap = new HashMap();
        Map componentNameAccessibilityServiceInfoMap =
                getComponentNameAccessibilityServiceInfoMap(context);
        Iterator it =
                ((ArrayList) getTalkbackCandidateBuilder(componentNameAccessibilityServiceInfoMap))
                        .iterator();
        while (it.hasNext()) {
            ShortcutCandidate build = ((ShortcutCandidate.Builder) it.next()).build(context, i);
            if (build != null) {
                hashMap.put(build.getKey(), build);
            }
        }
        Iterator it2 =
                ((ArrayList) getServiceCandidateBuilder(componentNameAccessibilityServiceInfoMap))
                        .iterator();
        while (it2.hasNext()) {
            ShortcutServiceCandidate build2 =
                    ((ShortcutServiceCandidate.Builder) it2.next()).build(context, i);
            if (build2 != null) {
                hashMap.put(build2.getKey(), build2);
            }
        }
        Iterator it3 = ((ArrayList) getActivityCandidateBuilders(context)).iterator();
        while (it3.hasNext()) {
            ShortcutActivityCandidate build3 =
                    ((ShortcutActivityCandidate.Builder) it3.next()).build(context, i);
            if (build3 != null) {
                hashMap.put(build3.getKey(), build3);
            }
        }
        return hashMap;
    }

    public static Map getComponentNameAccessibilityServiceInfoMap(Context context) {
        ServiceInfo serviceInfo;
        List<AccessibilityServiceInfo> installedAccessibilityServiceList =
                ((AccessibilityManager) context.getSystemService(AccessibilityManager.class))
                        .getInstalledAccessibilityServiceList();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (AccessibilityServiceInfo accessibilityServiceInfo :
                installedAccessibilityServiceList) {
            ResolveInfo resolveInfo = accessibilityServiceInfo.getResolveInfo();
            if (resolveInfo != null
                    && (serviceInfo = resolveInfo.serviceInfo) != null
                    && (serviceInfo.flags & 4) == 0
                    && serviceInfo.packageName != null
                    && serviceInfo.name != null) {
                linkedHashMap.put(
                        new ComponentName(serviceInfo.packageName, serviceInfo.name),
                        accessibilityServiceInfo);
            }
        }
        return linkedHashMap;
    }

    public static List getServiceCandidateBuilder(Map map) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (AccessibilityServiceInfo accessibilityServiceInfo : ((LinkedHashMap) map).values()) {
            ServiceInfo serviceInfo = accessibilityServiceInfo.getResolveInfo().serviceInfo;
            if (!"com.samsung.accessibility".equals(serviceInfo.packageName)) {
                Info info =
                        (Info)
                                ((HashMap) additionalInfoMap)
                                        .get(
                                                new ComponentName(
                                                                serviceInfo.packageName,
                                                                serviceInfo.name)
                                                        .flattenToString());
                if (info == null) {
                    info = new Info(i);
                    i++;
                }
                ShortcutServiceCandidate.Builder builder =
                        new ShortcutServiceCandidate.Builder(accessibilityServiceInfo.getId());
                builder.info = accessibilityServiceInfo;
                builder.priority = info.priority;
                builder.category = info.category;
                arrayList.add(builder);
            }
        }
        return arrayList;
    }

    public static List getTalkbackCandidateBuilder(Map map) {
        ArrayList arrayList = new ArrayList();
        ComponentName componentName = AccessibilityConstant.COMPONENT_NAME_SAMSUNG_TALKBACK;
        AccessibilityServiceInfo accessibilityServiceInfo =
                (AccessibilityServiceInfo) ((LinkedHashMap) map).get(componentName);
        if (accessibilityServiceInfo != null) {
            map.remove(AccessibilityConstant.COMPONENT_NAME_GOOGLE_TALKBACK);
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(accessibilityServiceInfo.getId());
            anonymousClass1.info = accessibilityServiceInfo;
            Info info = (Info) ((HashMap) additionalInfoMap).get(componentName.flattenToString());
            if (info == null) {
                info = new Info();
            }
            anonymousClass1.priority = info.priority;
            anonymousClass1.category = info.category;
            arrayList.add(anonymousClass1);
            map.remove(componentName);
        }
        return arrayList;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Info {
        public int blockedFlag;
        public String category;
        public String controller;
        public String exclusiveFeature;
        public final int priority;

        public Info(int i) {
            this.exclusiveFeature = null;
            this.blockedFlag = 0;
            this.category = "others";
            this.priority = i;
        }

        public Info() {
            this.exclusiveFeature = null;
            this.blockedFlag = 0;
            this.category = "others";
            this.priority = 0;
        }
    }
}
