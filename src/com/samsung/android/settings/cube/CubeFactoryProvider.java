package com.samsung.android.settings.cube;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.index.ControlData;
import com.samsung.android.settings.cube.index.ControlDataManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class CubeFactoryProvider {
    private static final String TAG = "CubeFactoryProvider";
    protected final Context mContext;
    private final ControlDataManager mControlDataManager;

    public CubeFactoryProvider(Context context) {
        this.mContext = context.getApplicationContext();
        this.mControlDataManager = new ControlDataManager(context);
        CubeCallbackManager.LazyHolder.INSTANCE.mCubeFactoryHashMap.put(getCubeLabel(), this);
    }

    public static BasePreferenceController getPreferenceController(
            Context context, String str, String str2) {
        try {
            return BasePreferenceController.createInstance(context, str);
        } catch (IllegalStateException e) {
            Log.w(TAG, "getPreferenceController() " + e.toString());
            try {
                return BasePreferenceController.createInstance(context, str, str2);
            } catch (IllegalStateException e2) {
                Log.w(TAG, "getPreferenceController() " + e2.toString());
                return null;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x0034, code lost:

       if (r3 != null) goto L7;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.HashMap getAllIndexedControlData() {
        /*
            r3 = this;
            com.samsung.android.settings.cube.index.ControlDataManager r3 = r3.mControlDataManager
            r3.getClass()
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            r1 = 0
            android.database.Cursor r3 = r3.getIndexedControlData(r1, r1)     // Catch: java.lang.IllegalStateException -> L28
            if (r3 == 0) goto L34
        L11:
            com.samsung.android.settings.cube.index.ControlData r1 = com.samsung.android.settings.cube.index.ControlDataManager.buildControlData(r3)     // Catch: java.lang.Throwable -> L2a
            java.lang.String r1 = r1.mKey     // Catch: java.lang.Throwable -> L2a
            com.samsung.android.settings.cube.index.ControlData r2 = com.samsung.android.settings.cube.index.ControlDataManager.buildControlData(r3)     // Catch: java.lang.Throwable -> L2a
            r0.put(r1, r2)     // Catch: java.lang.Throwable -> L2a
            boolean r1 = r3.moveToNext()     // Catch: java.lang.Throwable -> L2a
            if (r1 != 0) goto L11
        L24:
            r3.close()     // Catch: java.lang.IllegalStateException -> L28
            goto L4e
        L28:
            r3 = move-exception
            goto L37
        L2a:
            r1 = move-exception
            r3.close()     // Catch: java.lang.Throwable -> L2f
            goto L33
        L2f:
            r3 = move-exception
            r1.addSuppressed(r3)     // Catch: java.lang.IllegalStateException -> L28
        L33:
            throw r1     // Catch: java.lang.IllegalStateException -> L28
        L34:
            if (r3 == 0) goto L4e
            goto L24
        L37:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "getAllIndexedControlData() "
            r1.<init>(r2)
            java.lang.String r3 = r3.getMessage()
            r1.append(r3)
            java.lang.String r3 = r1.toString()
            java.lang.String r1 = "ControlDataManager"
            android.util.Log.e(r1, r3)
        L4e:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.cube.CubeFactoryProvider.getAllIndexedControlData():java.util.HashMap");
    }

    public abstract String getCubeLabel();

    public final ControlValue getValue(String str) {
        ControlData controlDataFromKey = this.mControlDataManager.getControlDataFromKey(str);
        if (controlDataFromKey != null) {
            try {
                BasePreferenceController preferenceController =
                        getPreferenceController(
                                this.mContext, controlDataFromKey.mControllerClassName, str);
                if (preferenceController == null) {
                    return null;
                }
                return preferenceController.getValue();
            } catch (Exception e) {
                Log.w(TAG, "getValue() " + e.toString());
            }
        }
        return null;
    }

    public abstract void notifyControlResult(String str, ControlResult controlResult);

    public final ControlResult setValue(String str, ControlValue controlValue) {
        ControlData controlDataFromKey = this.mControlDataManager.getControlDataFromKey(str);
        ControlResult.ResultCode resultCode = ControlResult.ResultCode.FAIL;
        if (controlDataFromKey == null) {
            ControlResult.Builder builder = new ControlResult.Builder(str);
            builder.mResultCode = resultCode;
            builder.setErrorMsg("can not found controller by key : " + str);
            return new ControlResult(builder);
        }
        if (controlValue.getValue() == null) {
            ControlResult.Builder builder2 = new ControlResult.Builder(str);
            builder2.mResultCode = resultCode;
            builder2.mErrorCode = ControlResult.ErrorCode.INVALID_DATA;
            return new ControlResult(builder2);
        }
        Context context = this.mContext;
        String str2 = controlDataFromKey.mControllerClassName;
        BasePreferenceController preferenceController = getPreferenceController(context, str2, str);
        if (preferenceController == null) {
            ControlResult.Builder builder3 = new ControlResult.Builder(str);
            builder3.mResultCode = resultCode;
            builder3.mErrorCode = ControlResult.ErrorCode.NOT_FOUND_CONTROLLER;
            return new ControlResult(builder3);
        }
        preferenceController.setControlId(controlValue);
        if (preferenceController.needUserInteraction(controlValue.getTypedValue())
                        == Controllable$ControllableType.NO_INTERACTION
                || controlValue.mForceChange) {
            return preferenceController.setValue(controlValue);
        }
        Intent intent = new Intent(this.mContext, (Class<?>) CubeInteractionActivity.class);
        intent.setFlags(268435456);
        intent.putExtra("controllerName", str2);
        intent.putExtra("controlData", controlValue.toString());
        String str3 = controlValue.mKey;
        intent.putExtra("prefKey", str3);
        intent.putExtra("controlId", controlValue.mControlId);
        try {
            this.mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            StringBuilder m =
                    ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                            "requestUserInteraction() prefKey = ", str3, " / ");
            m.append(e.toString());
            Log.e(TAG, m.toString());
        }
        ControlResult.Builder builder4 = new ControlResult.Builder(str);
        builder4.mResultCode = ControlResult.ResultCode.REQUEST_SUCCESS;
        return new ControlResult(builder4);
    }
}
