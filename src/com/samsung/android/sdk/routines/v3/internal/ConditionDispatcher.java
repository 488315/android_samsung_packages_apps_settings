package com.samsung.android.sdk.routines.v3.internal;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settingslib.Utils;

import com.google.gson.Gson;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.sdk.routines.v3.data.ConditionValidity$Valid;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.sdk.routines.v3.data.SatisfactionStatus;
import com.samsung.android.sdk.routines.v3.data.SupportStatus;
import com.samsung.android.sdk.routines.v3.data.TargetInstanceInfo;
import com.samsung.android.sdk.routines.v3.interfaces.ResponseCallback;
import com.samsung.android.sdk.routines.v3.interfaces.RoutineConditionHandler;
import com.samsung.android.settings.wifi.mobileap.routine.WifiApConditionHandler;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ConditionDispatcher extends Dispatcher {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.sdk.routines.v3.internal.ConditionDispatcher$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[ConditionMethod.values().length];
            a = iArr;
            try {
                iArr[1] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[2] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[3] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[4] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[5] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[6] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[7] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                a[8] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                a[9] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    @Override // com.samsung.android.sdk.routines.v3.internal.Dispatcher
    public final String a() {
        return "ConditionDispatcher";
    }

    public final Bundle a(final Context context, String str, Bundle bundle) {
        String str2;
        ConditionMethod conditionMethod;
        Bundle bundle2;
        Bundle c$2;
        Bundle bundle3;
        String str3;
        Bundle bundle4;
        final String string = bundle.getString(ExtraKey.TAG.a);
        if (string == null) {
            Log.a("ConditionDispatcher", "callConditionHandler - tag is null");
            return null;
        }
        final RoutineConditionHandler routineConditionHandler =
                (RoutineConditionHandler) RoutineSdkImpl.LazyHolder.a.d.getWithTimeout(string);
        if (routineConditionHandler == null) {
            Log.a(
                    "ConditionDispatcher",
                    "callConditionHandler - conditionHandler is null. tag=".concat(string));
            return null;
        }
        Log.b(
                "ConditionDispatcher",
                "callConditionHandler start - tag=" + string + ", method=" + str);
        String str4 = ExtraKey.PARAMETER_VALUES.a;
        String str5 = ApnSettings.MVNO_NONE;
        final ParameterValues fromJsonString =
                ParameterValues.fromJsonString(bundle.getString(str4, ApnSettings.MVNO_NONE));
        final long j = bundle.getLong(ExtraKey.INSTANCE_ID.a, 0L);
        int[] iArr = AnonymousClass1.a;
        ConditionMethod[] values = ConditionMethod.values();
        int length = values.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                str2 = str5;
                Log.a("ConditionMethod", "ConditionMethod.fromValue - not supported value: " + str);
                conditionMethod = ConditionMethod.UNKNOWN;
                break;
            }
            str2 = str5;
            conditionMethod = values[i];
            ConditionMethod[] conditionMethodArr = values;
            if (conditionMethod.a.equals(str)) {
                break;
            }
            i++;
            str5 = str2;
            values = conditionMethodArr;
        }
        int i2 = iArr[conditionMethod.ordinal()];
        ExtraKey extraKey = ExtraKey.RESULT_INT;
        switch (i2) {
            case 1:
                final Bundle bundle5 = new Bundle();
                final Object obj = new Object();
                bundle2 = bundle5;
                new Thread(
                                new Runnable(
                                        context,
                                        string,
                                        fromJsonString,
                                        j,
                                        bundle5,
                                        obj) { // from class:
                                               // com.samsung.android.sdk.routines.v3.internal.ConditionDispatcher$$ExternalSyntheticLambda0
                                    public final /* synthetic */ Context f$1;
                                    public final /* synthetic */ ParameterValues f$3;
                                    public final /* synthetic */ long f$4;
                                    public final /* synthetic */ Bundle f$5;
                                    public final /* synthetic */ Object f$6;

                                    {
                                        this.f$3 = fromJsonString;
                                        this.f$4 = j;
                                        this.f$5 = bundle5;
                                        this.f$6 = obj;
                                    }

                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        RoutineConditionHandler routineConditionHandler2 =
                                                RoutineConditionHandler.this;
                                        Context context2 = this.f$1;
                                        ParameterValues parameterValues = this.f$3;
                                        long j2 = this.f$4;
                                        Bundle bundle6 = this.f$5;
                                        Object obj2 = this.f$6;
                                        ((WifiApConditionHandler) routineConditionHandler2)
                                                .getClass();
                                        boolean booleanValue =
                                                parameterValues
                                                        .getBoolean(
                                                                "toggle_config_key", Boolean.FALSE)
                                                        .booleanValue();
                                        boolean isWifiApStateEnabled =
                                                WifiApFrameworkUtils.isWifiApStateEnabled(context2);
                                        android.util.Log.d(
                                                "WifiApConditionHandler",
                                                "isSatisfied() - instanceId : "
                                                        + j2
                                                        + ", apStatus: "
                                                        + (isWifiApStateEnabled ? 1 : 0)
                                                        + ", paramVal: "
                                                        + booleanValue);
                                        if (!(booleanValue && isWifiApStateEnabled)
                                                && (booleanValue || isWifiApStateEnabled)) {
                                            bundle6.putInt(
                                                    ExtraKey.RESULT_INT.a,
                                                    SatisfactionStatus.NOT_SATISFIED.a);
                                            synchronized (obj2) {
                                                obj2.notify();
                                            }
                                            return;
                                        }
                                        bundle6.putInt(
                                                ExtraKey.RESULT_INT.a,
                                                SatisfactionStatus.SATISFIED.a);
                                        synchronized (obj2) {
                                            obj2.notify();
                                        }
                                    }
                                })
                        .start();
                if (!a(obj)) {
                    Log.a("ConditionDispatcher", "isSatisfied: timeout");
                    c$2 = Dispatcher.c$2();
                    bundle4 = c$2;
                    break;
                }
                bundle4 = bundle2;
                break;
            case 2:
                bundle3 = null;
                WifiApConditionHandler wifiApConditionHandler =
                        (WifiApConditionHandler) routineConditionHandler;
                boolean booleanValue =
                        fromJsonString
                                .getBoolean("toggle_config_key", Boolean.FALSE)
                                .booleanValue();
                String string2 =
                        Settings.Secure.getString(
                                context.getContentResolver(), "wifi_ap_routine_instances");
                android.util.Log.d(
                        "WifiApConditionHandler",
                        "onEnabled() - toggleVal: "
                                + booleanValue
                                + ", instanceId: "
                                + j
                                + ", previousInstances: "
                                + string2);
                if (TextUtils.isEmpty(string2)) {
                    wifiApConditionHandler.pendingIntentRegistration(context, true);
                    str3 = str2;
                } else {
                    str3 = string2;
                }
                Settings.Secure.putString(
                        context.getContentResolver(), "wifi_ap_routine_instances", str3 + " " + j);
                bundle4 = bundle3;
                break;
            case 3:
                bundle3 = null;
                WifiApConditionHandler wifiApConditionHandler2 =
                        (WifiApConditionHandler) routineConditionHandler;
                String string3 =
                        Settings.Secure.getString(
                                context.getContentResolver(), "wifi_ap_routine_instances");
                android.util.Log.d(
                        "WifiApConditionHandler",
                        "onDisabled() -  instanceId: " + j + ", previousInstances: " + string3);
                String[] split = string3.split("\\s+");
                StringBuilder sb = new StringBuilder();
                for (String str6 : split) {
                    String trim = str6.trim();
                    if (trim.equals(String.valueOf(j))) {
                        android.util.Log.d(
                                "WifiApConditionHandler", "Removing instance : ".concat(trim));
                    } else {
                        sb.append(trim);
                    }
                }
                if (TextUtils.isEmpty(sb.toString())) {
                    sb = new StringBuilder();
                    wifiApConditionHandler2.pendingIntentRegistration(context, false);
                }
                Settings.Secure.putString(
                        context.getContentResolver(), "wifi_ap_routine_instances", sb.toString());
                bundle4 = bundle3;
                break;
            case 4:
                final Bundle bundle6 = new Bundle();
                final Object obj2 = new Object();
                bundle2 = bundle6;
                new Thread(
                                new Runnable(
                                        context,
                                        string,
                                        fromJsonString,
                                        j,
                                        bundle6,
                                        obj2) { // from class:
                                                // com.samsung.android.sdk.routines.v3.internal.ConditionDispatcher$$ExternalSyntheticLambda2
                                    public final /* synthetic */ Context f$1;
                                    public final /* synthetic */ ParameterValues f$3;
                                    public final /* synthetic */ Bundle f$5;
                                    public final /* synthetic */ Object f$6;

                                    {
                                        this.f$3 = fromJsonString;
                                        this.f$5 = bundle6;
                                        this.f$6 = obj2;
                                    }

                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        RoutineConditionHandler routineConditionHandler2 =
                                                RoutineConditionHandler.this;
                                        Context context2 = this.f$1;
                                        ParameterValues parameterValues = this.f$3;
                                        Bundle bundle7 = this.f$5;
                                        Object obj3 = this.f$6;
                                        ((WifiApConditionHandler) routineConditionHandler2)
                                                .getClass();
                                        android.util.Log.d(
                                                "WifiApConditionHandler", "getParameterLabel ");
                                        if (parameterValues
                                                .getBoolean("toggle_config_key", Boolean.FALSE)
                                                .booleanValue()) {
                                            bundle7.putString(
                                                    ExtraKey.CONFIG_LABEL_PARAMS.a,
                                                    context2.getResources()
                                                            .getString(R.string.switch_on_text));
                                            synchronized (obj3) {
                                                obj3.notify();
                                            }
                                            return;
                                        }
                                        bundle7.putString(
                                                ExtraKey.CONFIG_LABEL_PARAMS.a,
                                                context2.getResources()
                                                        .getString(R.string.switch_off_text));
                                        synchronized (obj3) {
                                            obj3.notify();
                                        }
                                    }
                                })
                        .start();
                if (!a(obj2)) {
                    Log.a("ConditionDispatcher", "getParameterLabel: timeout");
                    c$2 = Dispatcher.c$2();
                    bundle4 = c$2;
                    break;
                }
                bundle4 = bundle2;
                break;
            case 5:
                final Bundle bundle7 = new Bundle();
                final Object obj3 = new Object();
                bundle2 = bundle7;
                new Thread(
                                new Runnable(
                                        context,
                                        string,
                                        fromJsonString,
                                        j,
                                        bundle7,
                                        obj3) { // from class:
                                                // com.samsung.android.sdk.routines.v3.internal.ConditionDispatcher$$ExternalSyntheticLambda1
                                    public final /* synthetic */ Bundle f$5;
                                    public final /* synthetic */ Object f$6;

                                    {
                                        this.f$5 = bundle7;
                                        this.f$6 = obj3;
                                    }

                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        RoutineConditionHandler routineConditionHandler2 =
                                                RoutineConditionHandler.this;
                                        final Bundle bundle8 = this.f$5;
                                        final Object obj4 = this.f$6;
                                        ResponseCallback responseCallback =
                                                new ResponseCallback() { // from class:
                                                                         // com.samsung.android.sdk.routines.v3.internal.ConditionDispatcher$$ExternalSyntheticLambda3
                                                    @Override // com.samsung.android.sdk.routines.v3.interfaces.ResponseCallback
                                                    public final void setResponse(Object obj5) {
                                                        Bundle bundle9 = bundle8;
                                                        Object obj6 = obj4;
                                                        ((ConditionValidity$Valid) obj5).getClass();
                                                        bundle9.putInt(ExtraKey.RESULT_INT.a, 1);
                                                        synchronized (obj6) {
                                                            obj6.notify();
                                                        }
                                                    }
                                                };
                                        ((WifiApConditionHandler) routineConditionHandler2)
                                                .getClass();
                                        responseCallback.setResponse(new ConditionValidity$Valid());
                                    }
                                })
                        .start();
                if (!a(obj3)) {
                    Log.a("ConditionDispatcher", "checkValidity: timeout");
                    c$2 = Dispatcher.c$2();
                    bundle4 = c$2;
                    break;
                }
                bundle4 = bundle2;
                break;
            case 6:
                bundle4 = new Bundle();
                String str7 = extraKey.a;
                boolean z = !Utils.isWifiOnly(context);
                AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                        "isSupported: ", "WifiApConditionHandler", z);
                bundle4.putInt(str7, (z ? SupportStatus.SUPPORTED : SupportStatus.NOT_SUPPORTED).a);
                break;
            case 7:
                bundle4 = new Bundle();
                String str8 = ExtraKey.CONFIG_TEMPLATE.a;
                android.util.Log.d("WifiApConditionHandler", "onRequestTemplateContents ");
                Bundle bundle8 = new Bundle();
                bundle8.putString(
                        UniversalCredentialUtil.AGENT_TITLE,
                        context.getResources().getString(R.string.mobileap));
                bundle8.putString(
                        "label_on", context.getResources().getString(R.string.switch_on_text));
                bundle8.putString(
                        "label_off", context.getResources().getString(R.string.switch_off_text));
                bundle8.putBoolean("default_selection", true);
                bundle4.putBundle(str8, bundle8);
                break;
            case 8:
                bundle.getInt(extraKey.a, 0);
                bundle4 = new Bundle();
                String str9 = ExtraKey.ERROR_DIALOG_CONTENTS.a;
                android.util.Log.d("WifiApConditionHandler", "onRequestErrorDialogContents ");
                Bundle bundle9 = new Bundle();
                bundle9.putString(
                        ExtraKey.ERROR_DIALOG_MESSAGE.a,
                        "Action valid state is not checked, Make Action state valid");
                if (!TextUtils.isEmpty("Action Not Performed")) {
                    bundle9.putString(ExtraKey.ERROR_DIALOG_TITLE.a, "Action Not Performed");
                }
                bundle4.putBundle(str9, bundle9);
                break;
            case 9:
                Bundle bundle10 = new Bundle();
                String str10 = ExtraKey.MIGRATED_PARAMETER.a;
                ArrayList<String> stringArrayList =
                        bundle.getStringArrayList(ExtraKey.TARGET_INSTANCES.a);
                if (stringArrayList == null) {
                    Log.a("ConditionDispatcher", "getTargetInstances() targetInstances is null");
                    Collections.emptyList();
                } else {
                    ArrayList arrayList = new ArrayList();
                    Gson gson = new Gson();
                    Iterator<String> it = stringArrayList.iterator();
                    while (it.hasNext()) {
                        arrayList.add(
                                (TargetInstanceInfo)
                                        gson.fromJson(it.next(), TargetInstanceInfo.class));
                    }
                }
                android.util.Log.e(
                        "RoutineConditionHandler",
                        "onMigrate: this should not be called without overriding!!!");
                bundle10.putString(str10, null);
                bundle4 = bundle10;
                Log.a("ConditionDispatcher", "callConditionHandler - not supported method: " + str);
                break;
            default:
                bundle4 = null;
                Log.a("ConditionDispatcher", "callConditionHandler - not supported method: " + str);
                break;
        }
        Log.b(
                "ConditionDispatcher",
                "callConditionHandler end - tag=" + string + ", method=" + str);
        return bundle4;
    }
}
