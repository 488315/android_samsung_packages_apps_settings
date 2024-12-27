package com.samsung.android.settings.logging;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.google.gson.Gson;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingUtil;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingUtil$$ExternalSyntheticLambda1;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingUtil$$ExternalSyntheticLambda3;
import com.samsung.android.settings.logging.status.SettingDBData;
import com.samsung.android.settings.logging.status.SharedPreferencesHelper;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusLoggingUtils;
import com.samsung.context.sdk.samsunganalytics.AnalyticsException;
import com.samsung.context.sdk.samsunganalytics.LogBuilders$EventBuilder;
import com.samsung.context.sdk.samsunganalytics.LogBuilders$QuickSettingBuilder;
import com.samsung.context.sdk.samsunganalytics.SamsungAnalytics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SALogging {
    public static final Boolean DBG = Boolean.valueOf(Utils.DBG);
    public static Context mContext;
    public static SALogging sSAInstance;

    public static void doGettingData() {
        String str;
        String string;
        Class<?> cls;
        StatusLogger$StatusLoggingProvider statusLoggingProvider;
        List<StatusData> statusLoggingData;
        final int i = 1;
        final int i2 = 0;
        Log.i("Settings_SA_SALogging", "doGettingData start");
        Context context = mContext;
        int i3 = StatusLoggingUtils.$r8$clinit;
        String[] strArr = SAStatusClassList.mListStatusSupportClass;
        for (int i4 = 0; i4 < 79; i4++) {
            String str2 = strArr[i4];
            try {
                try {
                    cls = Class.forName(str2);
                } catch (ClassNotFoundException unused) {
                    Log.d("Settings_SA_Utils", "Cannot find class: " + str2);
                    cls = null;
                }
                if (cls != null
                        && (statusLoggingProvider =
                                        StatusLoggingUtils.getStatusLoggingProvider(cls))
                                != null
                        && (statusLoggingData = statusLoggingProvider.getStatusLoggingData(context))
                                != null) {
                    for (StatusData statusData : statusLoggingData) {
                        if (Utils.DBG) {
                            Log.i(
                                    "Settings_SA_Utils",
                                    "getStatusLoggingAPIData : "
                                            + statusData.toString()
                                            + " / className : "
                                            + str2);
                        }
                        SharedPreferencesHelper.savePreferences(
                                context, statusData.mStatusKey, statusData.mStatusValue);
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        Context context2 = mContext;
        HashMap hashMap = SAStatusDBList.mSettingsStatusDBList;
        if (hashMap != null) {
            for (Object obj : hashMap.keySet()) {
                SettingDBData settingDBData = (SettingDBData) hashMap.get(obj);
                if (settingDBData != null) {
                    String str3 = settingDBData.mSettingsDBCategory;
                    str3.getClass();
                    str = settingDBData.mSettingsDBName;
                    switch (str3) {
                        case "global":
                            string = Settings.Global.getString(context2.getContentResolver(), str);
                            break;
                        case "secure":
                            string = Settings.Secure.getString(context2.getContentResolver(), str);
                            break;
                        case "system":
                            string = Settings.System.getString(context2.getContentResolver(), str);
                            break;
                        default:
                            string = ApnSettings.MVNO_NONE;
                            break;
                    }
                    if (string == null) {
                        string = "null";
                    }
                    boolean z = Utils.DBG;
                    if (z) {
                        Log.i("Settings_SA_Utils", "getSettingValue " + string + " name : " + str);
                    }
                    if (z) {
                        Log.i(
                                "Settings_SA_Utils",
                                "getStatusLoggingDBData : key " + obj + " value : " + string);
                    }
                    SharedPreferencesHelper.savePreferences(context2, String.valueOf(obj), string);
                }
            }
        }
        final Context context3 = mContext;
        A11yStatusLoggingUtil.PROVIDER_CLASSNAME_SET.stream()
                .map(
                        new Function() { // from class:
                            // com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingUtil$$ExternalSyntheticLambda0
                            @Override // java.util.function.Function
                            public final Object apply(Object obj2) {
                                int i5 = i2;
                                Context context4 = context3;
                                switch (i5) {
                                    case 0:
                                        String str4 = (String) obj2;
                                        A11yStatusLoggingProvider a11yStatusLoggingProvider = null;
                                        try {
                                            Object newInstance =
                                                    Class.forName(str4)
                                                            .getConstructor(
                                                                    Context.class, String.class)
                                                            .newInstance(context4, "dummyKey");
                                            if (!(newInstance instanceof BasePreferenceController)
                                                    || ((BasePreferenceController) newInstance)
                                                                    .getAvailabilityStatus()
                                                            != 3) {
                                                if (newInstance
                                                        instanceof A11yStatusLoggingProvider) {
                                                    a11yStatusLoggingProvider =
                                                            (A11yStatusLoggingProvider) newInstance;
                                                } else {
                                                    Log.w(
                                                            "A11yStatusLoggingUtil",
                                                            str4
                                                                    + " is not inherited"
                                                                    + " A11yStatusLoggingProvider");
                                                }
                                            }
                                        } catch (ClassNotFoundException
                                                | IllegalAccessException
                                                | InstantiationException
                                                | NoSuchMethodException
                                                | InvocationTargetException e2) {
                                            Log.w(
                                                    "A11yStatusLoggingUtil",
                                                    "getHandlerInstance occurs exception. className"
                                                        + " : "
                                                            + str4,
                                                    e2);
                                        }
                                        return a11yStatusLoggingProvider;
                                    default:
                                        return ((A11yStatusLoggingProvider) obj2)
                                                .getStatusLoggingData(context4)
                                                .entrySet();
                                }
                            }
                        })
                .filter(new A11yStatusLoggingUtil$$ExternalSyntheticLambda1())
                .map(
                        new Function() { // from class:
                            // com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingUtil$$ExternalSyntheticLambda0
                            @Override // java.util.function.Function
                            public final Object apply(Object obj2) {
                                int i5 = i;
                                Context context4 = context3;
                                switch (i5) {
                                    case 0:
                                        String str4 = (String) obj2;
                                        A11yStatusLoggingProvider a11yStatusLoggingProvider = null;
                                        try {
                                            Object newInstance =
                                                    Class.forName(str4)
                                                            .getConstructor(
                                                                    Context.class, String.class)
                                                            .newInstance(context4, "dummyKey");
                                            if (!(newInstance instanceof BasePreferenceController)
                                                    || ((BasePreferenceController) newInstance)
                                                                    .getAvailabilityStatus()
                                                            != 3) {
                                                if (newInstance
                                                        instanceof A11yStatusLoggingProvider) {
                                                    a11yStatusLoggingProvider =
                                                            (A11yStatusLoggingProvider) newInstance;
                                                } else {
                                                    Log.w(
                                                            "A11yStatusLoggingUtil",
                                                            str4
                                                                    + " is not inherited"
                                                                    + " A11yStatusLoggingProvider");
                                                }
                                            }
                                        } catch (ClassNotFoundException
                                                | IllegalAccessException
                                                | InstantiationException
                                                | NoSuchMethodException
                                                | InvocationTargetException e2) {
                                            Log.w(
                                                    "A11yStatusLoggingUtil",
                                                    "getHandlerInstance occurs exception. className"
                                                        + " : "
                                                            + str4,
                                                    e2);
                                        }
                                        return a11yStatusLoggingProvider;
                                    default:
                                        return ((A11yStatusLoggingProvider) obj2)
                                                .getStatusLoggingData(context4)
                                                .entrySet();
                                }
                            }
                        })
                .flatMap(new A11yStatusLoggingUtil$$ExternalSyntheticLambda3())
                .forEach(
                        new Consumer() { // from class:
                            // com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingUtil$$ExternalSyntheticLambda4
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj2) {
                                Context context4 = context3;
                                Map.Entry entry = (Map.Entry) obj2;
                                String str4 = (String) entry.getKey();
                                String str5 = (String) entry.getValue();
                                if (TextUtils.isEmpty(str4) || TextUtils.isEmpty(str5)) {
                                    return;
                                }
                                SharedPreferencesHelper.savePreferences(context4, str4, str5);
                            }
                        });
        context3.sendBroadcast(
                new Intent("com.samsung.accessibility.logging.SEND_ACCESSIBILITY_STATUS_LOG")
                        .setComponent(A11yStatusLoggingUtil.A11Y_LOGGING_RECEIVER_COMPONENT_NAME));
        Log.i("Settings_SA_SALogging", "doGettingData end");
    }

    public static void doSendingData(Map map) {
        Log.i("Settings_SA_SALogging", "doSendingData start");
        LogBuilders$QuickSettingBuilder logBuilders$QuickSettingBuilder =
                new LogBuilders$QuickSettingBuilder();
        while (true) {
            int i = 0;
            for (String str : map.keySet()) {
                String string =
                        mContext.getSharedPreferences("settings_sa_status_shared_prefs", 0)
                                .getString(str, "not_set");
                if (!"not_set".equalsIgnoreCase(string)) {
                    Log.i("Settings_SA_SALogging", "send log key : " + str + " value : " + string);
                    i++;
                    if (TextUtils.isEmpty(str)) {
                        com.samsung.context.sdk.samsunganalytics.internal.util.Utils.throwException(
                                "Failure to build logs [setting] : Key cannot be null.");
                    } else if (str.equalsIgnoreCase("t")) {
                        com.samsung.context.sdk.samsunganalytics.internal.util.Utils.throwException(
                                "Failure to build logs [setting] : 't' is reserved word, choose"
                                    + " another word.");
                    } else {
                        ((HashMap) logBuilders$QuickSettingBuilder.map).put(str, string);
                    }
                    if (i == 20) {
                        break;
                    }
                }
            }
            SamsungAnalytics.getInstance().sendLog(logBuilders$QuickSettingBuilder.build());
            Log.i("Settings_SA_SALogging", "doSendingData end");
            return;
            SamsungAnalytics.getInstance().sendLog(logBuilders$QuickSettingBuilder.build());
            logBuilders$QuickSettingBuilder = new LogBuilders$QuickSettingBuilder();
        }
    }

    public static void doWriteData(Map map) {
        Log.i("Settings_SA_SALogging", "doWriteData start");
        HashMap hashMap = new HashMap();
        for (String str : map.keySet()) {
            String string =
                    mContext.getSharedPreferences("settings_sa_status_shared_prefs", 0)
                            .getString(str, "not_set");
            if (!"not_set".equalsIgnoreCase(string)) {
                Log.i("Settings_SA_SALogging", "log key : " + str + " value : " + string);
                hashMap.put(str, string);
            }
        }
        String json = new Gson().toJson(hashMap);
        File file =
                new File(Environment.getExternalStorageDirectory(), "settings_status_logging.json");
        if (file.exists()) {
            file.delete();
        }
        FileWriter fileWriter = null;
        try {
            try {
                try {
                    FileWriter fileWriter2 = new FileWriter(file);
                    try {
                        fileWriter2.write(json);
                        fileWriter2.flush();
                        fileWriter2.close();
                    } catch (IOException unused) {
                        fileWriter = fileWriter2;
                        Log.i("Settings_SA_SALogging", "doWriteData ERROR write file");
                        if (fileWriter != null) {
                            fileWriter.close();
                        }
                        Log.i("Settings_SA_SALogging", "doWriteData end");
                    } catch (Throwable th) {
                        th = th;
                        fileWriter = fileWriter2;
                        if (fileWriter != null) {
                            try {
                                fileWriter.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (IOException unused2) {
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            Log.i("Settings_SA_SALogging", "doWriteData end");
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static synchronized SALogging getInstance(Context context) {
        SALogging sALogging;
        synchronized (SALogging.class) {
            try {
                if (sSAInstance == null) {
                    SALogging sALogging2 = new SALogging();
                    mContext = context;
                    context.getResources();
                    sSAInstance = sALogging2;
                }
                sALogging = sSAInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return sALogging;
    }

    public static void insertSALog(String str) {
        insertSALog(-9999L, str, (String) null, (String) null);
    }

    public final synchronized void processStatusLogging(boolean z) {
        Map<String, ?> map;
        SharedPreferences.Editor edit =
                mContext.getSharedPreferences("settings_sa_status_shared_prefs", 0).edit();
        edit.clear();
        edit.apply();
        doGettingData();
        try {
            map = mContext.getSharedPreferences("settings_sa_status_shared_prefs", 0).getAll();
        } catch (Exception unused) {
            map = null;
        }
        if (map == null) {
            Log.i("Settings_SA_SALogging", "Logging data is null");
            return;
        }
        if (z) {
            doWriteData(map);
        } else {
            doSendingData(map);
        }
    }

    public static void insertSALog(String str, String str2) {
        insertSALog(-9999L, str, str2, (String) null);
    }

    public static void insertSALog(long j, String str, String str2) {
        insertSALog(j, str, str2, (String) null);
    }

    public static void insertSALog(String str, String str2, String str3) {
        insertSALog(-9999L, str, str2, str3);
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x009b, code lost:

       if (r12 == false) goto L37;
    */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x009e, code lost:

       if (r18 == null) goto L34;
    */
    /* JADX WARN: Removed duplicated region for block: B:19:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00aa A[Catch: NullPointerException -> 0x00b7, TRY_LEAVE, TryCatch #2 {NullPointerException -> 0x00b7, blocks: (B:30:0x00a4, B:32:0x00aa), top: B:29:0x00a4 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x009e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void insertSALog(
            long r15, java.lang.String r17, java.lang.String r18, java.lang.String r19) {
        /*
            Method dump skipped, instructions count: 279
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.logging.SALogging.insertSALog(long,"
                    + " java.lang.String, java.lang.String, java.lang.String):void");
    }

    public static void insertSALog(String str, String str2, Map map, int i) {
        try {
            LogBuilders$EventBuilder logBuilders$EventBuilder = new LogBuilders$EventBuilder();
            logBuilders$EventBuilder.setScreenView(str);
            logBuilders$EventBuilder.setEventName(str2);
            logBuilders$EventBuilder.setDimension(map);
            if (i == 1) {
                logBuilders$EventBuilder.set("et", String.valueOf(1));
            }
            Log.d(
                    "Settings_SA_SALogging",
                    "Event SA Logging screenID: "
                            + str
                            + " eventID: "
                            + str2
                            + " value: "
                            + map.toString());
            SamsungAnalytics.getInstance().sendLog(logBuilders$EventBuilder.build());
        } catch (AnalyticsException e) {
            Log.e("Settings_SA_SALogging", "Exception occurred while SA logging");
            if (DBG.booleanValue()) {
                e.printStackTrace();
            }
        }
    }
}
