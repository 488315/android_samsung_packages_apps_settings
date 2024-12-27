package com.samsung.android.settings.bixbyroutinehandler;

import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.fragment.app.BackStackRecord$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.internal.net.LegacyVpnInfo;
import com.android.internal.net.VpnProfile;
import com.android.internal.widget.LockPatternUtils;
import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.DefaultRingtonePreference$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.notification.zen.ZenModeBackend;
import com.android.settings.notification.zen.ZenModeRepeatCallersPreferenceController;
import com.android.settings.notification.zen.lifestyle.LifeStyleDND;
import com.android.settings.notification.zen.lifestyle.LifeStyleDND$$ExternalSyntheticOutline0;
import com.android.settings.vpn2.VpnUtils;

import com.samsung.android.bio.face.SemBioFaceManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentService;
import com.samsung.android.sdk.routines.v3.data.ActionResult$Error;
import com.samsung.android.sdk.routines.v3.data.ActionResult$ResultCode;
import com.samsung.android.sdk.routines.v3.data.ErrorContents;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.sdk.routines.v3.data.ParameterValues$$ExternalSyntheticLambda0;
import com.samsung.android.sdk.routines.v3.data.SupportStatus;
import com.samsung.android.sdk.routines.v3.interfaces.ActionResultCallback;
import com.samsung.android.sdk.routines.v3.interfaces.ResponseCallback;
import com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler;
import com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda6;
import com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda8;
import com.samsung.android.sdk.routines.v3.internal.ExtraKey;
import com.samsung.android.sdk.routines.v3.template.ToggleTemplate;
import com.samsung.android.sdk.routines.v3.template.UiTemplate;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.accessibility.routine.AccessibilityRoutineActionProxy;
import com.samsung.android.settings.asbase.routine.action.SoundSettingsActionInteractor;
import com.samsung.android.settings.bixbyroutinehandler.interactor.ActionInteractor;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.flipfont.FontListAdapter;
import com.samsung.android.settings.security.SecurityUtils;
import com.samsung.android.settings.vpn.bixby.RoutineUtils;
import com.samsung.android.settings.vpn.bixby.VpnHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BixbyRoutineActionHandler implements RoutineActionHandler {
    public static int sEyeComfortInitialAdaptiveMode;
    public static int sEyeComfortInitialScheduled;
    public static boolean sEyeComfortInitialState;
    public static int sEyeComfortInitialType;
    public static int sEyeComfortInitialValue;
    public final List mActionInteractors;
    public ActionResultCallback mCallback;
    public ContentResolver mContentResolver;
    public SemBioFaceManager mFaceManager;
    public FingerprintManager mFingerprintManager;
    public LockPatternUtils mLockPatternUtils;
    public AnonymousClass1 mNetworkCallback;
    public VpnHelper mVpnHelper;
    public AnonymousClass2 mVpnStateReceiver;
    public ZenModeBackend mZenModeBackend;
    public ZenModeRepeatCallersPreferenceController zenModeRepeatCallersPreferenceController;
    public static BixbyRoutineActionHandler sInstance = new BixbyRoutineActionHandler();
    public static boolean LifeStyleZenState = false;
    public static int CallOption = -1;
    public static float MessageOption = -1.0f;
    public static boolean repeatedCaller = false;
    public static String people_summary = ApnSettings.MVNO_NONE;
    public static String app_description = ApnSettings.MVNO_NONE;
    public static int AppFlag = -1;
    public static int ContactFlag = -1;
    public static String app_exist_list = ApnSettings.MVNO_NONE;
    public static String contact_exist_list = ApnSettings.MVNO_NONE;

    public BixbyRoutineActionHandler() {
        ArrayList arrayList = new ArrayList();
        this.mActionInteractors = arrayList;
        arrayList.add(new AccessibilityRoutineActionProxy());
        arrayList.add(new SoundSettingsActionInteractor());
    }

    public static String calculatePeopleSummary(Context context) {
        int i = CallOption;
        int i2 = (int) MessageOption;
        boolean z = i == -1 && i2 == -1 && !repeatedCaller && contact_exist_list.length() == 0;
        boolean z2 = i == 0 && i2 == 0 && contact_exist_list.length() > 0;
        boolean z3 = i == -1 && i2 == -1 && !repeatedCaller && contact_exist_list.length() > 0;
        if (z) {
            return context.getString(R.string.sec_zen_people_summery_allow_none);
        }
        if (z2) {
            return context.getString(R.string.sec_zen_people_summery_allow_all);
        }
        if (!z3) {
            return context.getString(R.string.sec_zen_people_summery_allow_some);
        }
        ArrayList arrayList = new ArrayList();
        if (!contact_exist_list.equals(ApnSettings.MVNO_NONE)) {
            for (String str : contact_exist_list.split(",")) {
                arrayList.add(str);
            }
        }
        int size = arrayList.size();
        return context.getResources()
                .getQuantityString(R.plurals.sec_dnd_select_contact, size, Integer.valueOf(size));
    }

    public static float getAppFlag() {
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("getAppFlag: "),
                AppFlag,
                "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler");
        return AppFlag;
    }

    public static float getCallOption() {
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("getCallOption: "),
                CallOption,
                "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler");
        return CallOption;
    }

    public static float getContactFlag() {
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("getContactFlag: "),
                ContactFlag,
                "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler");
        return ContactFlag;
    }

    public static synchronized BixbyRoutineActionHandler getInstance() {
        BixbyRoutineActionHandler bixbyRoutineActionHandler;
        synchronized (BixbyRoutineActionHandler.class) {
            try {
                if (sInstance == null) {
                    sInstance = new BixbyRoutineActionHandler();
                }
                bixbyRoutineActionHandler = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return bixbyRoutineActionHandler;
    }

    public static float getMessageOption() {
        Log.d(
                "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                "getMessageOption: " + MessageOption);
        return MessageOption;
    }

    public static void setAppFlag(int i) {
        AppFlag = i;
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("setAppFlag: "),
                AppFlag,
                "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler");
    }

    public static String setAppListMigration(String str) {
        String str2;
        if (str.length() == 0) {
            return ApnSettings.MVNO_NONE;
        }
        if (str.contains("/")) {
            String[] split = str.split(";");
            str2 = ApnSettings.MVNO_NONE;
            for (String str3 : split) {
                if (str3.contains("/")) {
                    String m =
                            ComponentActivity$1$$ExternalSyntheticOutline0.m(
                                    new StringBuilder(), str3.split("/")[0], ":0");
                    str2 =
                            str2.equals(ApnSettings.MVNO_NONE)
                                    ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, m)
                                    : AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(
                                            str2, ";", m);
                }
            }
        } else {
            str2 = str;
        }
        Log.d(
                "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                "setAppListMigration, migration List= " + str2 + ", appList= " + str);
        return str2;
    }

    public static void setAppSummary(Context context) {
        String string;
        String str = app_exist_list;
        if (TextUtils.isEmpty(str)) {
            string = context.getResources().getString(R.string.sec_zen_items_none_allowed);
        } else {
            String[] split = str.split(";");
            ArrayList arrayList = new ArrayList();
            for (String str2 : split) {
                try {
                    PackageManager packageManager = context.getPackageManager();
                    if (packageManager != null) {
                        arrayList.add(
                                packageManager
                                        .getPackageInfoAsUser(
                                                str2.split(":")[0],
                                                0,
                                                Integer.parseInt(str2.split(":")[1]))
                                        .applicationInfo
                                        .loadLabel(packageManager)
                                        .toString());
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
            string =
                    arrayList.size() == 0
                            ? context.getResources().getString(R.string.sec_zen_items_none_allowed)
                            : arrayList.size() == 1
                                    ? context.getResources()
                                            .getString(
                                                    R.string.sec_zen_items_one_allowed,
                                                    arrayList.get(0))
                                    : arrayList.size() == 2
                                            ? context.getResources()
                                                    .getString(
                                                            R.string.sec_zen_items_two_allowed,
                                                            arrayList.get(0),
                                                            arrayList.get(1))
                                            : arrayList.size() > 2
                                                    ? context.getResources()
                                                            .getString(
                                                                    R.string
                                                                            .sec_zen_items_more_allowed,
                                                                    arrayList.get(0),
                                                                    arrayList.get(1),
                                                                    Integer.valueOf(
                                                                            arrayList.size() - 2))
                                                    : ApnSettings.MVNO_NONE;
        }
        app_description = string;
    }

    public static void setApp_exist_list(HashSet hashSet) {
        app_exist_list = ApnSettings.MVNO_NONE;
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (app_exist_list.equals(ApnSettings.MVNO_NONE)) {
                app_exist_list =
                        ComponentActivity$1$$ExternalSyntheticOutline0.m(
                                new StringBuilder(), app_exist_list, str);
            } else {
                app_exist_list =
                        BackStackRecord$$ExternalSyntheticOutline0.m(
                                new StringBuilder(), app_exist_list, ";", str);
            }
        }
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                new StringBuilder("setApp_exist_list: "),
                app_exist_list,
                "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler");
    }

    public static void setCallOption(int i) {
        CallOption = i;
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i,
                "setCallOption: ",
                "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler");
    }

    public static void setContactFlag(int i) {
        ContactFlag = i;
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("setContactFlag: "),
                ContactFlag,
                "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler");
    }

    public static void setContact_exist_list(List list) {
        contact_exist_list = ApnSettings.MVNO_NONE;
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (contact_exist_list.equals(ApnSettings.MVNO_NONE)) {
                contact_exist_list =
                        ComponentActivity$1$$ExternalSyntheticOutline0.m(
                                new StringBuilder(), contact_exist_list, str);
            } else {
                contact_exist_list =
                        BackStackRecord$$ExternalSyntheticOutline0.m(
                                new StringBuilder(), contact_exist_list, ",", str);
            }
        }
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                new StringBuilder("setContact_exist_list: "),
                contact_exist_list,
                "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler");
    }

    public static void setMessageOption(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i,
                "setMessageOption: ",
                "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler");
        MessageOption = i;
    }

    public static void setPeoplesummary(Context context) {
        people_summary = calculatePeopleSummary(context);
    }

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public final void getCurrentParameterValues(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ResponseCallback responseCallback) {
        int i;
        int i2;
        String str2;
        i2 = 0;
        i = 0;
        i = 0;
        switch (str) {
            case "LifeStyle/DND":
                HashMap hashMap = new HashMap();
                hashMap.put("toggle_value", new ParameterValues.ParameterValue(Boolean.TRUE));
                ActionDispatcher$$ExternalSyntheticLambda6
                        actionDispatcher$$ExternalSyntheticLambda6 =
                                (ActionDispatcher$$ExternalSyntheticLambda6) responseCallback;
                Bundle bundle = actionDispatcher$$ExternalSyntheticLambda6.f$0;
                Object obj = actionDispatcher$$ExternalSyntheticLambda6.f$1;
                String str3 = ExtraKey.PARAMETER_VALUES.a;
                HashMap hashMap2 = new HashMap();
                hashMap.entrySet().forEach(new ParameterValues$$ExternalSyntheticLambda0(hashMap2));
                bundle.putString(str3, new JSONObject(hashMap2).toString());
                synchronized (obj) {
                    obj.notify();
                }
                return;
            case "sec_unlock_with_biometrics":
                LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
                if (lockPatternUtils == null) {
                    if (lockPatternUtils == null) {
                        this.mLockPatternUtils = new LockPatternUtils(context);
                    }
                    this.mLockPatternUtils = this.mLockPatternUtils;
                }
                ParameterValues parameterValues2 = new ParameterValues();
                Boolean bool = Boolean.FALSE;
                Boolean bool2 = parameterValues.getBoolean("unlock_with_fingerprint", bool);
                boolean booleanValue = bool2.booleanValue();
                Boolean bool3 = parameterValues.getBoolean("unlock_with_face", bool);
                boolean booleanValue2 = bool3.booleanValue();
                boolean z = this.mLockPatternUtils.getBiometricState(1, UserHandle.myUserId()) == 1;
                boolean z2 =
                        this.mLockPatternUtils.getBiometricState(256, UserHandle.myUserId()) == 1;
                parameterValues2.put("fingerprint_default_value", Boolean.valueOf(z));
                parameterValues2.put("face_default_value", Boolean.valueOf(z2));
                parameterValues2.put("unlock_with_fingerprint", bool2);
                parameterValues2.put("unlock_with_face", bool3);
                Log.d(
                        "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                        "getCurrentParameterValues");
                StringBuilder m =
                        Utils$$ExternalSyntheticOutline0.m(
                                "recovery FP : ",
                                z,
                                " perform FP : ",
                                booleanValue,
                                " recovery Face : ");
                m.append(z2);
                m.append(" perform Face : ");
                m.append(booleanValue2);
                Log.d(
                        "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                        m.toString());
                ((ActionDispatcher$$ExternalSyntheticLambda6) responseCallback)
                        .setResponse(parameterValues2);
                return;
            case "accidental_touch_protection":
            case "adaptive_color_tone":
            case "sec_touch_sensitivity":
                int i3 =
                        "sec_touch_sensitivity".equals(str)
                                ? Settings.System.getInt(
                                        context.getContentResolver(), "auto_adjust_touch", 0)
                                : "adaptive_color_tone".equals(str)
                                        ? Settings.System.getInt(
                                                context.getContentResolver(), "ead_enabled", 0)
                                        : Settings.System.getInt(
                                                context.getContentResolver(),
                                                "screen_off_pocket",
                                                0);
                HashMap hashMap3 = new HashMap();
                hashMap3.put(
                        "toggle_value",
                        new ParameterValues.ParameterValue(Boolean.valueOf(i3 == 1)));
                ActionDispatcher$$ExternalSyntheticLambda6
                        actionDispatcher$$ExternalSyntheticLambda62 =
                                (ActionDispatcher$$ExternalSyntheticLambda6) responseCallback;
                Bundle bundle2 = actionDispatcher$$ExternalSyntheticLambda62.f$0;
                Object obj2 = actionDispatcher$$ExternalSyntheticLambda62.f$1;
                String str4 = ExtraKey.PARAMETER_VALUES.a;
                HashMap hashMap4 = new HashMap();
                hashMap3.entrySet()
                        .forEach(new ParameterValues$$ExternalSyntheticLambda0(hashMap4));
                bundle2.putString(str4, new JSONObject(hashMap4).toString());
                synchronized (obj2) {
                    obj2.notify();
                }
                return;
            case "disconnect_vpn":
            case "connect_vpn":
                this.mVpnHelper = new VpnHelper(context);
                unregisterNetworkCallback(context);
                unregisterVpnStateReceiver(context);
                LegacyVpnInfo legacyVpnInfo =
                        this.mVpnHelper.mService.getLegacyVpnInfo(UserHandle.myUserId());
                String str5 = null;
                String str6 =
                        (legacyVpnInfo == null || legacyVpnInfo.state != 3)
                                ? null
                                : legacyVpnInfo.key;
                this.mVpnHelper.getClass();
                if (str6 != null && str6.equals(VpnUtils.getLockdownVpn())) {
                    i = 1;
                }
                if (str6 != null) {
                    str5 = i + "_" + str6;
                }
                Log.d(
                        "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                        "getCurrentParameterValues: tag=" + str + ", param=" + str5);
                HashMap hashMap5 = new HashMap();
                hashMap5.put("key_vpn_param", new ParameterValues.ParameterValue(str5));
                ActionDispatcher$$ExternalSyntheticLambda6
                        actionDispatcher$$ExternalSyntheticLambda63 =
                                (ActionDispatcher$$ExternalSyntheticLambda6) responseCallback;
                Bundle bundle3 = actionDispatcher$$ExternalSyntheticLambda63.f$0;
                Object obj3 = actionDispatcher$$ExternalSyntheticLambda63.f$1;
                String str7 = ExtraKey.PARAMETER_VALUES.a;
                HashMap hashMap6 = new HashMap();
                hashMap5.entrySet()
                        .forEach(new ParameterValues$$ExternalSyntheticLambda0(hashMap6));
                bundle3.putString(str7, new JSONObject(hashMap6).toString());
                synchronized (obj3) {
                    obj3.notify();
                }
                return;
            case "sec_eye_comfort_shield_routine":
                ParameterValues parameterValues3 = new ParameterValues();
                sEyeComfortInitialValue =
                        Settings.System.getInt(
                                context.getContentResolver(), "blue_light_filter_opacity", 5);
                sEyeComfortInitialState =
                        Settings.System.getInt(context.getContentResolver(), "blue_light_filter", 0)
                                != 0;
                sEyeComfortInitialType =
                        Settings.System.getInt(
                                context.getContentResolver(), "blue_light_filter_type", 0);
                sEyeComfortInitialScheduled =
                        Settings.System.getInt(
                                context.getContentResolver(),
                                "blue_light_filter_scheduled",
                                !Rune.supportBlueLightFilterAdaptiveMode() ? 1 : 0);
                sEyeComfortInitialAdaptiveMode =
                        Settings.System.getInt(
                                context.getContentResolver(),
                                "blue_light_filter_adaptive_mode",
                                Rune.supportBlueLightFilterAdaptiveMode() ? 1 : 0);
                parameterValues3.put(
                        "blue_light_filter_switch",
                        Float.valueOf(sEyeComfortInitialState ? 1.0f : 0.0f));
                parameterValues3.put(
                        "blue_light_filter_adaptive_mode",
                        Float.valueOf(
                                Rune.supportBlueLightFilterAdaptiveMode()
                                        ? sEyeComfortInitialAdaptiveMode
                                        : 0.0f));
                parameterValues3.put(
                        "blue_light_filter_scheduled",
                        Float.valueOf(
                                Rune.supportBlueLightFilterAdaptiveMode()
                                        ? sEyeComfortInitialScheduled
                                        : 1.0f));
                parameterValues3.put(
                        "blue_light_filter_type", Float.valueOf(sEyeComfortInitialType));
                parameterValues3.put(
                        "blue_light_filter_opacity", Float.valueOf(sEyeComfortInitialValue));
                StringBuilder m2 =
                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                new StringBuilder("getCurrentParameterValues: "),
                                sEyeComfortInitialState,
                                "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                                "getCurrentParameterValues: ");
                m2.append(sEyeComfortInitialAdaptiveMode);
                Log.d(
                        "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                        m2.toString());
                Log.d(
                        "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                        "getCurrentParameterValues: " + sEyeComfortInitialScheduled);
                Log.d(
                        "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                        "getCurrentParameterValues: " + sEyeComfortInitialType);
                Preference$$ExternalSyntheticOutline0.m(
                        new StringBuilder("getCurrentParameterValues: "),
                        sEyeComfortInitialValue,
                        "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler");
                ((ActionDispatcher$$ExternalSyntheticLambda6) responseCallback)
                        .setResponse(parameterValues3);
                return;
            case "routine_font_style_tag":
                String currentFontName = SecDisplayUtils.getCurrentFontName(context);
                FontListAdapter instanceFontListAdapter =
                        FontListAdapter.getInstanceFontListAdapter(context);
                while (true) {
                    if (i2 >= instanceFontListAdapter.mFontNames.size()) {
                        str2 = "default";
                    } else if (currentFontName.equals(instanceFontListAdapter.getFontName(i2))) {
                        str2 = (String) instanceFontListAdapter.mFontPackageNames.get(i2);
                    } else {
                        i2++;
                    }
                }
                String str8 = str2.isEmpty() ? "default" : str2;
                ParameterValues parameterValues4 = new ParameterValues();
                parameterValues4.put(
                        "font_style_key",
                        parameterValues.getString("font_style_key", ApnSettings.MVNO_NONE));
                parameterValues4.put(
                        "font_package_name_key",
                        parameterValues.getString("font_package_name_key", ApnSettings.MVNO_NONE));
                parameterValues4.put("original_font_style_key", str8);
                ((ActionDispatcher$$ExternalSyntheticLambda6) responseCallback)
                        .setResponse(parameterValues4);
                return;
            default:
                Iterator it = ((ArrayList) this.mActionInteractors).iterator();
                while (it.hasNext()) {
                    ((ActionInteractor) it.next())
                            .getCurrentParameterValues(
                                    context,
                                    str,
                                    parameterValues,
                                    j,
                                    (ActionDispatcher$$ExternalSyntheticLambda6) responseCallback);
                }
                return;
        }
    }

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public final void getParameterLabel(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ResponseCallback responseCallback) {
        switch (str) {
            case "LifeStyle/DND":
                Log.d(
                        "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                        "getParameterLabel: ".concat(str));
                Float valueOf = Float.valueOf(-1.0f);
                float floatValue =
                        parameterValues.getNumber("messages_options", valueOf).floatValue();
                float floatValue2 = parameterValues.getNumber("call_options", valueOf).floatValue();
                String string = parameterValues.getString("app_options", ApnSettings.MVNO_NONE);
                boolean booleanValue =
                        parameterValues.getBoolean("repeat_options", Boolean.FALSE).booleanValue();
                String string2 =
                        parameterValues.getString("contact_options", ApnSettings.MVNO_NONE);
                setMessageOption((int) floatValue);
                setCallOption((int) floatValue2);
                repeatedCaller = booleanValue;
                setApp_exist_list(new HashSet(Arrays.asList(setAppListMigration(string))));
                setContact_exist_list(new ArrayList(Arrays.asList(string2)));
                setAppSummary(context);
                setPeoplesummary(context);
                ((ActionDispatcher$$ExternalSyntheticLambda6) responseCallback)
                        .setResponse(people_summary + ";" + app_description);
                break;
            case "sec_unlock_with_biometrics":
                Boolean bool = Boolean.FALSE;
                boolean booleanValue2 =
                        parameterValues.getBoolean("unlock_with_fingerprint", bool).booleanValue();
                boolean booleanValue3 =
                        parameterValues.getBoolean("unlock_with_face", bool).booleanValue();
                StringBuilder sb = new StringBuilder();
                if (isSupportedFingerprint(context) && isSupportedFace(context)) {
                    sb.append(context.getString(R.string.sec_fingerprint) + " : ");
                    sb.append(
                            booleanValue2
                                    ? context.getString(R.string.switch_on_text)
                                    : context.getString(R.string.switch_off_text));
                    sb.append("\n");
                    sb.append(context.getString(R.string.sec_face_title) + " : ");
                    sb.append(
                            booleanValue3
                                    ? context.getString(R.string.switch_on_text)
                                    : context.getString(R.string.switch_off_text));
                } else {
                    if (isSupportedFingerprint(context)) {
                        sb.append(context.getString(R.string.sec_fingerprint) + " : ");
                        sb.append(
                                booleanValue2
                                        ? context.getString(R.string.switch_on_text)
                                        : context.getString(R.string.switch_off_text));
                    }
                    if (isSupportedFace(context)) {
                        sb.append(context.getString(R.string.sec_face_title) + " : ");
                        sb.append(
                                booleanValue3
                                        ? context.getString(R.string.switch_on_text)
                                        : context.getString(R.string.switch_off_text));
                    }
                }
                ((ActionDispatcher$$ExternalSyntheticLambda6) responseCallback)
                        .setResponse(sb.toString());
                break;
            case "accidental_touch_protection":
            case "adaptive_color_tone":
            case "sec_touch_sensitivity":
                this.mContentResolver = context.getContentResolver();
                if (!parameterValues.getBoolean("toggle_value", Boolean.TRUE).booleanValue()) {
                    ((ActionDispatcher$$ExternalSyntheticLambda6) responseCallback)
                            .setResponse(context.getString(R.string.routine_switch_off_text));
                    break;
                } else {
                    ((ActionDispatcher$$ExternalSyntheticLambda6) responseCallback)
                            .setResponse(context.getString(R.string.routine_switch_on_text));
                    break;
                }
            case "disconnect_vpn":
            case "connect_vpn":
                String string3 = parameterValues.getString("key_vpn_param", ApnSettings.MVNO_NONE);
                VpnProfile findVpnProfile =
                        VpnHelper.findVpnProfile(RoutineUtils.getVpnProfileKey(string3));
                StringBuilder m =
                        ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                "getParameterLabel: tag=", str, ",");
                m.append(findVpnProfile == null ? "null" : findVpnProfile.name);
                m.append(",");
                m.append(string3);
                Log.d(
                        "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                        m.toString());
                ((ActionDispatcher$$ExternalSyntheticLambda6) responseCallback)
                        .setResponse(
                                findVpnProfile == null
                                        ? context.getString(
                                                R.string.vpn_routine_label_param_none_selected)
                                        : findVpnProfile.name);
                break;
            case "sec_eye_comfort_shield_routine":
                float m2 =
                        LifeStyleDND$$ExternalSyntheticOutline0.m(
                                0.0f, parameterValues, "blue_light_filter_switch");
                float m3 =
                        LifeStyleDND$$ExternalSyntheticOutline0.m(
                                1.0f, parameterValues, "blue_light_filter_adaptive_mode");
                parameterValues
                        .getNumber("blue_light_filter_scheduled", Float.valueOf(0.0f))
                        .getClass();
                if (!Rune.supportBlueLightFilterAdaptiveMode()) {
                    m3 = 0.0f;
                }
                parameterValues.getNumber("blue_light_filter_type", Float.valueOf(0.0f)).getClass();
                float floatValue3 =
                        parameterValues
                                .getNumber("blue_light_filter_opacity", Float.valueOf(5.0f))
                                .floatValue();
                StringBuilder sb2 = new StringBuilder();
                if (((int) m2) == 0) {
                    sb2.append(context.getString(R.string.switch_off_text));
                } else if (!Rune.supportBlueLightFilterAdaptiveMode()) {
                    sb2.append(context.getString(R.string.switch_on_text) + ": ");
                    sb2.append(
                            context.getString(
                                    R.string.sec_lockscreen_noti_card_seekbar_percentage,
                                    Integer.valueOf((int) (floatValue3 * 10.0f))));
                } else if (((int) m3) == 1) {
                    sb2.append(context.getString(R.string.sec_eye_comfort_adaptive));
                } else {
                    sb2.append(context.getString(R.string.sec_eye_comfort_custom) + ": ");
                    sb2.append(
                            context.getString(
                                    R.string.sec_lockscreen_noti_card_seekbar_percentage,
                                    Integer.valueOf((int) (floatValue3 * 10.0f))));
                }
                ((ActionDispatcher$$ExternalSyntheticLambda6) responseCallback)
                        .setResponse(sb2.toString());
                break;
            case "routine_font_style_tag":
                ((ActionDispatcher$$ExternalSyntheticLambda6) responseCallback)
                        .setResponse(
                                parameterValues.getString("font_style_key", ApnSettings.MVNO_NONE));
                break;
            default:
                Iterator it = ((ArrayList) this.mActionInteractors).iterator();
                while (it.hasNext()) {
                    ((ActionInteractor) it.next())
                            .getParameterLabel(
                                    context,
                                    str,
                                    parameterValues,
                                    j,
                                    (ActionDispatcher$$ExternalSyntheticLambda6) responseCallback);
                }
                break;
        }
    }

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public final SupportStatus isSupported(Context context, String str) {
        SupportStatus supportStatus;
        str.getClass();
        supportStatus = SupportStatus.NOT_SUPPORTED;
        switch (str) {
            case "sec_unlock_with_biometrics":
                if (!isSupportedFingerprint(context) && !isSupportedFace(context)) {
                    return supportStatus;
                }
                break;
            case "accidental_touch_protection":
                if (!Rune.supportPocketMode(context)) {
                    return supportStatus;
                }
                break;
            case "adaptive_color_tone":
                String str2 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                break;
            case "sec_touch_sensitivity":
                String str3 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                break;
        }
        Iterator it = ((ArrayList) this.mActionInteractors).iterator();
        while (it.hasNext()) {
            ActionInteractor actionInteractor = (ActionInteractor) it.next();
            if (actionInteractor.getSupportTagList().contains(str)) {
                return actionInteractor.isSupported(context, str);
            }
        }
        return SupportStatus.SUPPORTED;
    }

    public final boolean isSupportedFace(Context context) {
        if (this.mFaceManager == null) {
            this.mFaceManager = SemBioFaceManager.createInstance(context);
        }
        SemBioFaceManager semBioFaceManager = this.mFaceManager;
        return semBioFaceManager != null && semBioFaceManager.isHardwareDetected();
    }

    public final boolean isSupportedFingerprint(Context context) {
        if (this.mFingerprintManager == null) {
            this.mFingerprintManager = Utils.getFingerprintManagerOrNull(context);
        }
        FingerprintManager fingerprintManager = this.mFingerprintManager;
        return fingerprintManager != null && fingerprintManager.isHardwareDetected();
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0544  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0554  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x058d  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x05b0  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x05ee  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x05bc  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x0599  */
    /* JADX WARN: Type inference failed for: r1v62, types: [com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler$1] */
    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onPerformAction(
            final android.content.Context r27,
            java.lang.String r28,
            com.samsung.android.sdk.routines.v3.data.ParameterValues r29,
            long r30,
            com.samsung.android.sdk.routines.v3.interfaces.ActionResultCallback r32) {
        /*
            Method dump skipped, instructions count: 1650
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler.onPerformAction(android.content.Context,"
                    + " java.lang.String, com.samsung.android.sdk.routines.v3.data.ParameterValues,"
                    + " long,"
                    + " com.samsung.android.sdk.routines.v3.interfaces.ActionResultCallback):void");
    }

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public final void onPerformReverseAction(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ActionResultCallback actionResultCallback) {
        switch (str) {
            case "LifeStyle/DND":
                StringBuilder sb = new StringBuilder("onPerformReverseAction KEY_DND_STATE = ");
                Boolean bool = Boolean.FALSE;
                sb.append(parameterValues.getBoolean("dnd_switch", bool));
                Log.d(
                        "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                        sb.toString());
                parameterValues.getBoolean("dnd_switch", bool).getClass();
                LifeStyleZenState = false;
                if (this.mZenModeBackend == null) {
                    this.mZenModeBackend = ZenModeBackend.getInstance(context);
                }
                this.mZenModeBackend.setLifeStyleZenModeoff();
                break;
            case "sec_unlock_with_biometrics":
                LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
                if (lockPatternUtils == null) {
                    if (lockPatternUtils == null) {
                        this.mLockPatternUtils = new LockPatternUtils(context);
                    }
                    this.mLockPatternUtils = this.mLockPatternUtils;
                }
                if (!this.mLockPatternUtils.isSecure(UserHandle.myUserId())) {
                    Log.e(
                            "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                            "onPerformReverseAction can not be started because PPP isn't"
                                + " registered");
                    break;
                } else {
                    Boolean bool2 = Boolean.FALSE;
                    boolean booleanValue =
                            parameterValues
                                    .getBoolean("fingerprint_default_value", bool2)
                                    .booleanValue();
                    boolean booleanValue2 =
                            parameterValues.getBoolean("face_default_value", bool2).booleanValue();
                    if (this.mFingerprintManager == null) {
                        this.mFingerprintManager = Utils.getFingerprintManagerOrNull(context);
                    }
                    FingerprintManager fingerprintManager = this.mFingerprintManager;
                    if (fingerprintManager == null
                            || !fingerprintManager.hasEnrolledFingerprints(UserHandle.myUserId())) {
                        Log.d(
                                "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                                "There is no fingerprint.");
                    } else {
                        unlockWithFingerprint(context, booleanValue);
                    }
                    if (this.mFaceManager == null) {
                        this.mFaceManager = SemBioFaceManager.createInstance(context);
                    }
                    SemBioFaceManager semBioFaceManager = this.mFaceManager;
                    if (semBioFaceManager != null
                            && semBioFaceManager.hasEnrolledFaces(UserHandle.myUserId())) {
                        unlockWithFace(context, booleanValue2);
                        break;
                    } else {
                        Log.d(
                                "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                                "There is no face.");
                        break;
                    }
                }
                break;
            case "accidental_touch_protection":
            case "adaptive_color_tone":
            case "sec_touch_sensitivity":
                boolean booleanValue3 =
                        parameterValues.getBoolean("toggle_value", Boolean.TRUE).booleanValue();
                this.mContentResolver = context.getContentResolver();
                if (!"sec_touch_sensitivity".equals(str)) {
                    if (!"adaptive_color_tone".equals(str)) {
                        Settings.System.putInt(
                                this.mContentResolver, "screen_off_pocket", booleanValue3 ? 1 : 0);
                        break;
                    } else {
                        Settings.System.putInt(
                                this.mContentResolver, "ead_enabled", booleanValue3 ? 1 : 0);
                        break;
                    }
                } else {
                    Settings.System.putInt(
                            this.mContentResolver, "auto_adjust_touch", booleanValue3 ? 1 : 0);
                    break;
                }
            case "disconnect_vpn":
            case "connect_vpn":
                this.mVpnHelper = new VpnHelper(context);
                unregisterNetworkCallback(context);
                unregisterVpnStateReceiver(context);
                String string = parameterValues.getString("key_vpn_param", ApnSettings.MVNO_NONE);
                Log.d(
                        "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                        "onPerformReverseAction: tag=" + str + ",param=" + string);
                VpnProfile findVpnProfile =
                        VpnHelper.findVpnProfile(RoutineUtils.getVpnProfileKey(string));
                if (findVpnProfile != null) {
                    boolean z = string != null && string.startsWith("1");
                    AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                            "onPerformReverseAction: connecting vpn, lockdown=",
                            "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                            z);
                    this.mVpnHelper.connect(findVpnProfile, z);
                    break;
                } else {
                    Log.d(
                            "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                            "onPerformReverseAction: disconnecting vpn");
                    VpnUtils.disconnectLegacyVpn(context);
                    break;
                }
                break;
            case "sec_eye_comfort_shield_routine":
                float m =
                        LifeStyleDND$$ExternalSyntheticOutline0.m(
                                0.0f, parameterValues, "blue_light_filter_switch");
                float f = 1.0f;
                float m2 =
                        LifeStyleDND$$ExternalSyntheticOutline0.m(
                                1.0f, parameterValues, "blue_light_filter_adaptive_mode");
                float m3 =
                        LifeStyleDND$$ExternalSyntheticOutline0.m(
                                0.0f, parameterValues, "blue_light_filter_scheduled");
                if (Rune.supportBlueLightFilterAdaptiveMode()) {
                    f = m3;
                } else {
                    m2 = 0.0f;
                }
                float m4 =
                        LifeStyleDND$$ExternalSyntheticOutline0.m(
                                0.0f, parameterValues, "blue_light_filter_type");
                float m5 =
                        LifeStyleDND$$ExternalSyntheticOutline0.m(
                                5.0f, parameterValues, "blue_light_filter_opacity");
                Log.d(
                        "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                        "blueLightFilterSwitch onPerformReverseAction: " + m);
                Log.d(
                        "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                        "blueLightFilterAdaptive onPerformReverseAction: " + m2);
                Log.d(
                        "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                        "blueLightFilterScheduled onPerformReverseAction: " + f);
                Log.d(
                        "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                        "blueLightFilterType onPerformReverseAction: " + m4);
                Log.d(
                        "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                        "blueLightFilterOpacity onPerformReverseAction: " + m5);
                ContentResolver contentResolver = context.getContentResolver();
                this.mContentResolver = contentResolver;
                Settings.System.putInt(contentResolver, "blue_light_filter_scheduled", (int) f);
                Settings.System.putInt(
                        this.mContentResolver, "blue_light_filter_opacity", (int) m5);
                Settings.System.putInt(this.mContentResolver, "blue_light_filter_type", (int) m4);
                int i = (int) m;
                Settings.System.putInt(this.mContentResolver, "blue_light_filter", i);
                Settings.System.putInt(
                        this.mContentResolver, "blue_light_filter_adaptive_mode", (int) m2);
                Intent intent = new Intent();
                DefaultRingtonePreference$$ExternalSyntheticOutline0.m(
                        "com.samsung.android.bluelightfilter",
                        "com.samsung.android.bluelightfilter.BlueLightFilterService",
                        intent);
                intent.putExtra("BLUE_LIGHT_FILTER_SERVICE_TYPE", i == 1 ? 24 : 25);
                context.startService(intent);
                break;
            case "routine_font_style_tag":
                String string2 =
                        parameterValues.getString("original_font_style_key", ApnSettings.MVNO_NONE);
                if (!string2.isEmpty()) {
                    SecDisplayUtils.applyFlipFonts(context, string2);
                    break;
                } else {
                    SecDisplayUtils.applyFlipFonts(
                            context,
                            parameterValues.getString(
                                    "font_package_name_key", ApnSettings.MVNO_NONE));
                    break;
                }
            default:
                Iterator it = ((ArrayList) this.mActionInteractors).iterator();
                while (it.hasNext()) {
                    ((ActionInteractor) it.next())
                            .onPerformReverseAction(
                                    context,
                                    str,
                                    parameterValues,
                                    j,
                                    (ActionDispatcher$$ExternalSyntheticLambda8)
                                            actionResultCallback);
                }
                break;
        }
        ((ActionDispatcher$$ExternalSyntheticLambda8) actionResultCallback)
                .actionFinished(new ActionResult$Error(ActionResult$ResultCode.SUCCESS, false));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public final ErrorContents onRequestErrorDialogContents(
            Context context, String str, int i, long j) {
        char c;
        switch (str.hashCode()) {
            case -1915289632:
                if (str.equals("LifeStyle/DND")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1469866873:
                if (str.equals("sec_unlock_with_biometrics")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -369843503:
                if (str.equals("disconnect_vpn")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 145367869:
                if (str.equals("sec_eye_comfort_shield_routine")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1724312607:
                if (str.equals("connect_vpn")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return i != -1
                        ? i != 1000
                                ? new ErrorContents(
                                        "Error", "Action not executed due to some reason", null)
                                : new ErrorContents(
                                        "Action not performed",
                                        "Currently your account deactivated. Want to activate your"
                                            + " account?",
                                        new ErrorContents.DialogButton(
                                                PendingIntent.getActivity(
                                                        context,
                                                        0,
                                                        new Intent(
                                                                context,
                                                                (Class<?>) LifeStyleDND.class),
                                                        UcmAgentService.ERROR_APPLET_UNKNOWN)))
                        : new ErrorContents(null, "This Action not available", null);
            case 1:
                if (i == 1000) {
                    return new ErrorContents(
                            null,
                            context.getString(
                                    R.string.sec_unlock_with_biometrics_not_perform_err_msg),
                            null);
                }
                if (i == 1001) {
                    return new ErrorContents(
                            null,
                            context.getString(
                                    R.string
                                            .sec_unlock_with_biometrics_not_perform_because_biometrics_data_was_removed_err_msg),
                            null);
                }
                break;
            case 2:
            case 4:
                break;
            case 3:
                if (i != 100) {
                    return new ErrorContents(
                            "Error", "Action not executed due to some reason", null);
                }
                return new ErrorContents(
                        context.getString(R.string.routine_error_dialog_title),
                        context.getString(
                                R.string.sec_eye_comfort_disable_reason,
                                SecDisplayUtils.getAccessibilityVisionMessage(context),
                                context.getString(R.string.sec_eye_comfort_title)),
                        null);
            default:
                Iterator it = ((ArrayList) this.mActionInteractors).iterator();
                while (it.hasNext()) {
                    ActionInteractor actionInteractor = (ActionInteractor) it.next();
                    if (actionInteractor.getSupportTagList().contains(str)) {
                        return actionInteractor.onRequestErrorDialogContents(context, str, i, j);
                    }
                }
                return null;
        }
        Log.d(
                "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                "onRequestErrorDialogContents: tag=" + str + ",errorCode: " + i);
        return new ErrorContents(
                context.getString(R.string.routine_error_dialog_title),
                context.getString(R.string.vpn_routine_dialog_message_could_not_perform),
                null);
    }

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public final UiTemplate onRequestTemplateContents(Context context, String str) {
        String string;
        String string2;
        str.getClass();
        switch (str) {
            case "LifeStyle/DND":
                Bundle bundle = new Bundle();
                bundle.putString(UniversalCredentialUtil.AGENT_TITLE, "Action Title");
                bundle.putString("label_on", "Option1");
                bundle.putString("label_off", "Option2");
                bundle.putBoolean("default_selection", true);
                return new ToggleTemplate(bundle);
            case "accidental_touch_protection":
            case "adaptive_color_tone":
            case "sec_touch_sensitivity":
                this.mContentResolver = context.getContentResolver();
                if ("sec_touch_sensitivity".equals(str)) {
                    string = context.getString(R.string.increase_touch_sensetivity_title);
                    string2 =
                            String.format(
                                    context.getString(
                                            R.string
                                                    .increase_touch_sensetivity_summary_screen_protectors),
                                    context.getString(R.string.screen_protectors));
                } else if ("adaptive_color_tone".equals(str)) {
                    string = context.getString(R.string.sec_ead);
                    StringBuilder sb = new StringBuilder();
                    sb.append(context.getString(R.string.sec_ead_summary));
                    if (Settings.System.getInt(
                                    context.getContentResolver(),
                                    "notified_ead_camera_use_routine",
                                    0)
                            != 1) {
                        sb.append("\n\n");
                        sb.append(context.getString(R.string.sec_ead_notice_camera_use));
                        Settings.System.putInt(
                                context.getContentResolver(), "notified_ead_camera_use_routine", 1);
                    }
                    string2 = sb.toString();
                } else {
                    string = context.getString(R.string.sec_block_accidental_touches_title);
                    string2 = context.getString(R.string.sec_block_accidental_touches_summary);
                }
                Bundle m =
                        AbsAdapter$1$$ExternalSyntheticOutline0.m(
                                UniversalCredentialUtil.AGENT_TITLE, string);
                m.putString("label_on", context.getString(R.string.routine_switch_on_text));
                m.putString("label_off", context.getString(R.string.routine_switch_off_text));
                m.putString("description", string2);
                m.putBoolean("default_selection", true);
                return new ToggleTemplate(m);
            default:
                Iterator it = ((ArrayList) this.mActionInteractors).iterator();
                while (it.hasNext()) {
                    ActionInteractor actionInteractor = (ActionInteractor) it.next();
                    if (actionInteractor.getSupportTagList().contains(str)) {
                        return actionInteractor.onRequestTemplateContents(context, str);
                    }
                }
                return new UiTemplate(new Bundle());
        }
    }

    public final void unlockWithFace(Context context, boolean z) {
        Log.d(
                "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                "unlockWithFace : " + z);
        if (isSupportedFace(context)) {
            if (z) {
                SecurityUtils.setBiometricLock(
                        context, this.mLockPatternUtils, 256, UserHandle.myUserId());
            } else {
                SecurityUtils.removeBiometricLock(
                        context, this.mLockPatternUtils, 256, UserHandle.myUserId());
            }
        }
    }

    public final void unlockWithFingerprint(Context context, boolean z) {
        Log.d(
                "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                "unlockWithFingerprint : " + z);
        if (isSupportedFingerprint(context)) {
            if (z) {
                SecurityUtils.setBiometricLock(
                        context, this.mLockPatternUtils, 1, UserHandle.myUserId());
            } else {
                SecurityUtils.removeBiometricLock(
                        context, this.mLockPatternUtils, 1, UserHandle.myUserId());
            }
        }
    }

    public final void unregisterNetworkCallback(Context context) {
        if (this.mNetworkCallback != null) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null) {
                connectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
                Log.d(
                        "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                        "unregisterNetworkCallback");
            }
            this.mNetworkCallback = null;
        }
    }

    public final void unregisterVpnStateReceiver(Context context) {
        AnonymousClass2 anonymousClass2 = this.mVpnStateReceiver;
        if (anonymousClass2 != null) {
            try {
                context.unregisterReceiver(anonymousClass2);
            } catch (IllegalArgumentException unused) {
            }
            this.mVpnStateReceiver = null;
            Log.d(
                    "com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler",
                    "unregisterVpnStateReceiver");
        }
    }
}
