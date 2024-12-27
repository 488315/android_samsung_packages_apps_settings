package com.samsung.scpm.odm.dos.product;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.scpm.odm.dos.common.ScpmDataSet;
import com.samsung.scpm.odm.dos.common.TokenStore;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ScpmProduct {
    public final Context context;
    public final String appId = "s5d189ajvs";
    public final String TAG = "[SCPMSDK][0.9.0800][Product]";

    public ScpmProduct(Context context) {
        this.context = context;
    }

    public final Bundle call(String str, String str2, Bundle bundle) {
        String str3 = this.appId;
        String str4 = this.TAG;
        try {
            Log.i(str4, "call : Method = " + str + ", arg = " + str2);
            String load = TokenStore.load(this.context, str3);
            Log.d(str4, "call appId : " + str3 + ", token : " + load);
            if (load != null) {
                bundle.putString("token", load);
            }
            bundle.putString("appId", str3);
            return this.context
                    .getContentResolver()
                    .call(
                            Uri.parse("content://com.samsung.android.scpm.product/"),
                            str,
                            str2,
                            bundle);
        } catch (Throwable th) {
            Log.e(str4, "Unknown exception");
            th.printStackTrace();
            return new Bundle();
        }
    }

    public final Bundle callForPam(Bundle bundle) {
        String str = this.appId;
        String str2 = this.TAG;
        try {
            Log.i(str2, "callForPam : Method = register, arg = ");
            String load = TokenStore.load(this.context, str);
            Log.d(str2, "call appId : " + str + ", token : " + load);
            if (load != null) {
                bundle.putString("token", load);
            }
            bundle.putString("appId", str);
            return this.context
                    .getContentResolver()
                    .call(
                            Uri.parse("content://com.samsung.android.scpm.pam/"),
                            "register",
                            ApnSettings.MVNO_NONE,
                            bundle);
        } catch (Throwable th) {
            Log.e(str2, "Unknown exception : " + th);
            return new Bundle();
        }
    }

    public final ParcelFileDescriptor openFile(String str) {
        String m =
                AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                        "openFile : parameter = ", str);
        String str2 = this.TAG;
        Log.i(str2, m);
        try {
            return this.context
                    .getContentResolver()
                    .openFileDescriptor(
                            Uri.parse(
                                    "content://com.samsung.android.scpm.product/"
                                            + TokenStore.load(this.context, this.appId)
                                            + "/"
                                            + str),
                            "r");
        } catch (Throwable th) {
            Log.e(str2, "Unknown exception : " + th.getMessage());
            return null;
        }
    }

    public final ScpmDataSet register() {
        StringBuilder sb = new StringBuilder("register : ");
        String str = this.appId;
        sb.append(str);
        String sb2 = sb.toString();
        String str2 = this.TAG;
        Log.i(str2, sb2);
        try {
            Bundle bundle = new Bundle();
            bundle.putString("packageName", this.context.getPackageName());
            new Bundle();
            Bundle callForPam =
                    this.context
                                            .getPackageManager()
                                            .resolveContentProvider(
                                                    "com.samsung.android.scpm.pam", 0)
                                    != null
                            ? callForPam(bundle)
                            : call("register", ApnSettings.MVNO_NONE, bundle);
            String string = callForPam.getString("token");
            Log.d(str2, "register token : " + string);
            this.context
                    .getSharedPreferences("scpm.token.store", 0)
                    .edit()
                    .putString(str + "_token", string)
                    .apply();
            return ScpmDataSet.create(callForPam);
        } catch (Exception e) {
            Log.e(str2, "cannot register package : " + e.getMessage());
            return new ScpmDataSet(
                    2, 90000000, "There is an exception, please check  { " + e.getMessage() + "}");
        }
    }
}
