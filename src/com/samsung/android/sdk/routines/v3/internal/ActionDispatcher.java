package com.samsung.android.sdk.routines.v3.internal;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.sdk.routines.v3.data.ErrorContents;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.sdk.routines.v3.data.TargetInstanceInfo;
import com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ActionDispatcher extends Dispatcher {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[ActionMethod.values().length];
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
            try {
                a[10] = 10;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    @Override // com.samsung.android.sdk.routines.v3.internal.Dispatcher
    public final String a() {
        return "ActionDispatcher";
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:17:0x009b. Please report as an issue. */
    public final Bundle a(final Context context, String str, Bundle bundle) {
        ActionMethod actionMethod;
        String str2;
        Bundle bundle2;
        Bundle c$2;
        Bundle bundle3;
        List list;
        final String string = bundle.getString(ExtraKey.TAG.a);
        if (string == null) {
            Log.a("ActionDispatcher", "callActionHandler - tag is null");
            return null;
        }
        final RoutineActionHandler routineActionHandler =
                (RoutineActionHandler) RoutineSdkImpl.LazyHolder.a.e.getWithTimeout(string);
        if (routineActionHandler == null) {
            Log.a(
                    "ActionDispatcher",
                    "callActionHandler - actionHandler is null. tag=".concat(string));
            return null;
        }
        Log.b("ActionDispatcher", "callActionHandler start - tag=" + string + ", method=" + str);
        final long j = bundle.getLong(ExtraKey.INSTANCE_ID.a, 0L);
        final ParameterValues fromJsonString =
                ParameterValues.fromJsonString(
                        bundle.getString(ExtraKey.PARAMETER_VALUES.a, ApnSettings.MVNO_NONE));
        int[] iArr = AnonymousClass1.a;
        ActionMethod[] values = ActionMethod.values();
        int length = values.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                Log.a("ActionMethod", "ActionMethod.fromValue - not supported value: " + str);
                actionMethod = ActionMethod.UNKNOWN;
                break;
            }
            actionMethod = values[i];
            ActionMethod[] actionMethodArr = values;
            if (actionMethod.a.equals(str)) {
                break;
            }
            i++;
            values = actionMethodArr;
        }
        int i2 = iArr[actionMethod.ordinal()];
        ExtraKey extraKey = ExtraKey.RESULT_INT;
        switch (i2) {
            case 1:
                str2 = ", method=";
                final Bundle bundle4 = new Bundle();
                final Object obj = new Object();
                final int i3 = 0;
                bundle2 = bundle4;
                new Thread(
                                new Runnable() { // from class:
                                                 // com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        switch (i3) {
                                            case 0:
                                                routineActionHandler.getCurrentParameterValues(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle4, obj, 3));
                                                break;
                                            case 1:
                                                RoutineActionHandler routineActionHandler2 =
                                                        routineActionHandler;
                                                Context context2 = context;
                                                String str3 = string;
                                                ParameterValues parameterValues = fromJsonString;
                                                long j2 = j;
                                                routineActionHandler2.onPerformReverseAction(
                                                        context2,
                                                        str3,
                                                        parameterValues,
                                                        j2,
                                                        new ActionDispatcher$$ExternalSyntheticLambda8(
                                                                bundle4, j2, obj));
                                                break;
                                            case 2:
                                                RoutineActionHandler routineActionHandler3 =
                                                        routineActionHandler;
                                                Context context3 = context;
                                                String str4 = string;
                                                ParameterValues parameterValues2 = fromJsonString;
                                                long j3 = j;
                                                routineActionHandler3.onPerformAction(
                                                        context3,
                                                        str4,
                                                        parameterValues2,
                                                        j3,
                                                        new ActionDispatcher$$ExternalSyntheticLambda8(
                                                                bundle4, j3, obj));
                                                break;
                                            case 3:
                                                routineActionHandler.getPreviewImageFileDescriptor(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle4, obj, 2));
                                                break;
                                            case 4:
                                                routineActionHandler.getParameterLabel(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle4, obj, 0));
                                                break;
                                            default:
                                                routineActionHandler.checkValidity(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle4, obj, 1));
                                                break;
                                        }
                                    }
                                })
                        .start();
                if (!a(obj)) {
                    Log.a("ActionDispatcher", "getCurrentParameterValues: timeout");
                    c$2 = Dispatcher.c$2();
                    bundle3 = c$2;
                    break;
                }
                bundle3 = bundle2;
                break;
            case 2:
                str2 = ", method=";
                final Bundle bundle5 = new Bundle();
                final Object obj2 = new Object();
                final int i4 = 2;
                bundle2 = bundle5;
                new Thread(
                                new Runnable() { // from class:
                                                 // com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        switch (i4) {
                                            case 0:
                                                routineActionHandler.getCurrentParameterValues(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle5, obj2, 3));
                                                break;
                                            case 1:
                                                RoutineActionHandler routineActionHandler2 =
                                                        routineActionHandler;
                                                Context context2 = context;
                                                String str3 = string;
                                                ParameterValues parameterValues = fromJsonString;
                                                long j2 = j;
                                                routineActionHandler2.onPerformReverseAction(
                                                        context2,
                                                        str3,
                                                        parameterValues,
                                                        j2,
                                                        new ActionDispatcher$$ExternalSyntheticLambda8(
                                                                bundle5, j2, obj2));
                                                break;
                                            case 2:
                                                RoutineActionHandler routineActionHandler3 =
                                                        routineActionHandler;
                                                Context context3 = context;
                                                String str4 = string;
                                                ParameterValues parameterValues2 = fromJsonString;
                                                long j3 = j;
                                                routineActionHandler3.onPerformAction(
                                                        context3,
                                                        str4,
                                                        parameterValues2,
                                                        j3,
                                                        new ActionDispatcher$$ExternalSyntheticLambda8(
                                                                bundle5, j3, obj2));
                                                break;
                                            case 3:
                                                routineActionHandler.getPreviewImageFileDescriptor(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle5, obj2, 2));
                                                break;
                                            case 4:
                                                routineActionHandler.getParameterLabel(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle5, obj2, 0));
                                                break;
                                            default:
                                                routineActionHandler.checkValidity(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle5, obj2, 1));
                                                break;
                                        }
                                    }
                                })
                        .start();
                if (!a(obj2)) {
                    Log.a("ActionDispatcher", "onPerformAction: timeout");
                    c$2 = Dispatcher.c$2();
                    bundle3 = c$2;
                    break;
                }
                bundle3 = bundle2;
                break;
            case 3:
                str2 = ", method=";
                final Bundle bundle6 = new Bundle();
                final Object obj3 = new Object();
                final int i5 = 1;
                bundle2 = bundle6;
                new Thread(
                                new Runnable() { // from class:
                                                 // com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        switch (i5) {
                                            case 0:
                                                routineActionHandler.getCurrentParameterValues(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle6, obj3, 3));
                                                break;
                                            case 1:
                                                RoutineActionHandler routineActionHandler2 =
                                                        routineActionHandler;
                                                Context context2 = context;
                                                String str3 = string;
                                                ParameterValues parameterValues = fromJsonString;
                                                long j2 = j;
                                                routineActionHandler2.onPerformReverseAction(
                                                        context2,
                                                        str3,
                                                        parameterValues,
                                                        j2,
                                                        new ActionDispatcher$$ExternalSyntheticLambda8(
                                                                bundle6, j2, obj3));
                                                break;
                                            case 2:
                                                RoutineActionHandler routineActionHandler3 =
                                                        routineActionHandler;
                                                Context context3 = context;
                                                String str4 = string;
                                                ParameterValues parameterValues2 = fromJsonString;
                                                long j3 = j;
                                                routineActionHandler3.onPerformAction(
                                                        context3,
                                                        str4,
                                                        parameterValues2,
                                                        j3,
                                                        new ActionDispatcher$$ExternalSyntheticLambda8(
                                                                bundle6, j3, obj3));
                                                break;
                                            case 3:
                                                routineActionHandler.getPreviewImageFileDescriptor(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle6, obj3, 2));
                                                break;
                                            case 4:
                                                routineActionHandler.getParameterLabel(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle6, obj3, 0));
                                                break;
                                            default:
                                                routineActionHandler.checkValidity(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle6, obj3, 1));
                                                break;
                                        }
                                    }
                                })
                        .start();
                if (!a(obj3)) {
                    Log.a("ActionDispatcher", "onPerformReverseAction: timeout");
                    c$2 = Dispatcher.c$2();
                    bundle3 = c$2;
                    break;
                }
                bundle3 = bundle2;
                break;
            case 4:
                str2 = ", method=";
                final Bundle bundle7 = new Bundle();
                final Object obj4 = new Object();
                final int i6 = 4;
                bundle2 = bundle7;
                new Thread(
                                new Runnable() { // from class:
                                                 // com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        switch (i6) {
                                            case 0:
                                                routineActionHandler.getCurrentParameterValues(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle7, obj4, 3));
                                                break;
                                            case 1:
                                                RoutineActionHandler routineActionHandler2 =
                                                        routineActionHandler;
                                                Context context2 = context;
                                                String str3 = string;
                                                ParameterValues parameterValues = fromJsonString;
                                                long j2 = j;
                                                routineActionHandler2.onPerformReverseAction(
                                                        context2,
                                                        str3,
                                                        parameterValues,
                                                        j2,
                                                        new ActionDispatcher$$ExternalSyntheticLambda8(
                                                                bundle7, j2, obj4));
                                                break;
                                            case 2:
                                                RoutineActionHandler routineActionHandler3 =
                                                        routineActionHandler;
                                                Context context3 = context;
                                                String str4 = string;
                                                ParameterValues parameterValues2 = fromJsonString;
                                                long j3 = j;
                                                routineActionHandler3.onPerformAction(
                                                        context3,
                                                        str4,
                                                        parameterValues2,
                                                        j3,
                                                        new ActionDispatcher$$ExternalSyntheticLambda8(
                                                                bundle7, j3, obj4));
                                                break;
                                            case 3:
                                                routineActionHandler.getPreviewImageFileDescriptor(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle7, obj4, 2));
                                                break;
                                            case 4:
                                                routineActionHandler.getParameterLabel(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle7, obj4, 0));
                                                break;
                                            default:
                                                routineActionHandler.checkValidity(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle7, obj4, 1));
                                                break;
                                        }
                                    }
                                })
                        .start();
                if (!a(obj4)) {
                    Log.a("ActionDispatcher", "getParameterLabel: timeout");
                    c$2 = Dispatcher.c$2();
                    bundle3 = c$2;
                    break;
                }
                bundle3 = bundle2;
                break;
            case 5:
                str2 = ", method=";
                final Bundle bundle8 = new Bundle();
                final Object obj5 = new Object();
                final int i7 = 3;
                bundle2 = bundle8;
                new Thread(
                                new Runnable() { // from class:
                                                 // com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        switch (i7) {
                                            case 0:
                                                routineActionHandler.getCurrentParameterValues(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle8, obj5, 3));
                                                break;
                                            case 1:
                                                RoutineActionHandler routineActionHandler2 =
                                                        routineActionHandler;
                                                Context context2 = context;
                                                String str3 = string;
                                                ParameterValues parameterValues = fromJsonString;
                                                long j2 = j;
                                                routineActionHandler2.onPerformReverseAction(
                                                        context2,
                                                        str3,
                                                        parameterValues,
                                                        j2,
                                                        new ActionDispatcher$$ExternalSyntheticLambda8(
                                                                bundle8, j2, obj5));
                                                break;
                                            case 2:
                                                RoutineActionHandler routineActionHandler3 =
                                                        routineActionHandler;
                                                Context context3 = context;
                                                String str4 = string;
                                                ParameterValues parameterValues2 = fromJsonString;
                                                long j3 = j;
                                                routineActionHandler3.onPerformAction(
                                                        context3,
                                                        str4,
                                                        parameterValues2,
                                                        j3,
                                                        new ActionDispatcher$$ExternalSyntheticLambda8(
                                                                bundle8, j3, obj5));
                                                break;
                                            case 3:
                                                routineActionHandler.getPreviewImageFileDescriptor(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle8, obj5, 2));
                                                break;
                                            case 4:
                                                routineActionHandler.getParameterLabel(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle8, obj5, 0));
                                                break;
                                            default:
                                                routineActionHandler.checkValidity(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle8, obj5, 1));
                                                break;
                                        }
                                    }
                                })
                        .start();
                if (!a(obj5)) {
                    Log.a("ActionDispatcher", "getPreviewImageFileDescriptor: timeout");
                    c$2 = Dispatcher.c$2();
                    bundle3 = c$2;
                    break;
                }
                bundle3 = bundle2;
                break;
            case 6:
                final Bundle bundle9 = new Bundle();
                final Object obj6 = new Object();
                final int i8 = 5;
                bundle2 = bundle9;
                str2 = ", method=";
                new Thread(
                                new Runnable() { // from class:
                                                 // com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        switch (i8) {
                                            case 0:
                                                routineActionHandler.getCurrentParameterValues(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle9, obj6, 3));
                                                break;
                                            case 1:
                                                RoutineActionHandler routineActionHandler2 =
                                                        routineActionHandler;
                                                Context context2 = context;
                                                String str3 = string;
                                                ParameterValues parameterValues = fromJsonString;
                                                long j2 = j;
                                                routineActionHandler2.onPerformReverseAction(
                                                        context2,
                                                        str3,
                                                        parameterValues,
                                                        j2,
                                                        new ActionDispatcher$$ExternalSyntheticLambda8(
                                                                bundle9, j2, obj6));
                                                break;
                                            case 2:
                                                RoutineActionHandler routineActionHandler3 =
                                                        routineActionHandler;
                                                Context context3 = context;
                                                String str4 = string;
                                                ParameterValues parameterValues2 = fromJsonString;
                                                long j3 = j;
                                                routineActionHandler3.onPerformAction(
                                                        context3,
                                                        str4,
                                                        parameterValues2,
                                                        j3,
                                                        new ActionDispatcher$$ExternalSyntheticLambda8(
                                                                bundle9, j3, obj6));
                                                break;
                                            case 3:
                                                routineActionHandler.getPreviewImageFileDescriptor(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle9, obj6, 2));
                                                break;
                                            case 4:
                                                routineActionHandler.getParameterLabel(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle9, obj6, 0));
                                                break;
                                            default:
                                                routineActionHandler.checkValidity(
                                                        context,
                                                        string,
                                                        fromJsonString,
                                                        j,
                                                        new ActionDispatcher$$ExternalSyntheticLambda6(
                                                                bundle9, obj6, 1));
                                                break;
                                        }
                                    }
                                })
                        .start();
                if (!a(obj6)) {
                    Log.a("ActionDispatcher", "checkValidity: timeout");
                    c$2 = Dispatcher.c$2();
                    bundle3 = c$2;
                    break;
                }
                bundle3 = bundle2;
                break;
            case 7:
                bundle3 = new Bundle();
                bundle3.putInt(extraKey.a, routineActionHandler.isSupported(context, string).a);
                str2 = ", method=";
                break;
            case 8:
                bundle3 = new Bundle();
                bundle3.putBundle(
                        ExtraKey.CONFIG_TEMPLATE.a,
                        routineActionHandler.onRequestTemplateContents(context, string).a);
                str2 = ", method=";
                break;
            case 9:
                int i9 = bundle.getInt(extraKey.a, 0);
                Bundle bundle10 = new Bundle();
                String str3 = ExtraKey.ERROR_DIALOG_CONTENTS.a;
                ErrorContents onRequestErrorDialogContents =
                        routineActionHandler.onRequestErrorDialogContents(context, string, i9, j);
                onRequestErrorDialogContents.getClass();
                Bundle bundle11 = new Bundle();
                bundle11.putString(ExtraKey.ERROR_DIALOG_MESSAGE.a, onRequestErrorDialogContents.b);
                String str4 = onRequestErrorDialogContents.a;
                if (!TextUtils.isEmpty(str4)) {
                    bundle11.putString(ExtraKey.ERROR_DIALOG_TITLE.a, str4);
                }
                ErrorContents.DialogButton dialogButton = onRequestErrorDialogContents.c;
                if (dialogButton != null) {
                    bundle11.putString(ExtraKey.ERROR_DIALOG_BUTTON_TEXT.a, dialogButton.a);
                    bundle11.putParcelable(ExtraKey.ERROR_DIALOG_BUTTON_INTENT.a, dialogButton.b);
                }
                bundle10.putBundle(str3, bundle11);
                bundle3 = bundle10;
                str2 = ", method=";
                break;
            case 10:
                bundle3 = new Bundle();
                String str5 = ExtraKey.MIGRATED_PARAMETER.a;
                ArrayList<String> stringArrayList =
                        bundle.getStringArrayList(ExtraKey.TARGET_INSTANCES.a);
                if (stringArrayList == null) {
                    Log.a("ActionDispatcher", "getTargetInstances() targetInstances is null");
                    list = Collections.emptyList();
                } else {
                    ArrayList arrayList = new ArrayList();
                    Gson gson = new Gson();
                    Iterator<String> it = stringArrayList.iterator();
                    while (it.hasNext()) {
                        arrayList.add(
                                (TargetInstanceInfo)
                                        gson.fromJson(it.next(), TargetInstanceInfo.class));
                    }
                    list = arrayList;
                }
                bundle3.putString(str5, routineActionHandler.onMigrate(context, list));
                Log.a("ActionDispatcher", "callActionHandler - not supported method: " + str);
                str2 = ", method=";
                break;
            default:
                bundle3 = null;
                Log.a("ActionDispatcher", "callActionHandler - not supported method: " + str);
                str2 = ", method=";
                break;
        }
        Log.b("ActionDispatcher", "callActionHandler end - tag=" + string + str2 + str);
        return bundle3;
    }
}
