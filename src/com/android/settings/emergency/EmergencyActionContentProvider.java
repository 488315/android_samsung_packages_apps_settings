package com.android.settings.emergency;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settingslib.emergencynumber.EmergencyNumberUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EmergencyActionContentProvider extends ContentProvider {
    @Override // android.content.ContentProvider
    public final Bundle call(String str, String str2, String str3, Bundle bundle) {
        int callingUid = Binder.getCallingUid();
        Log.d(
                "EmergencyActionContentP",
                "calling pid/uid" + Binder.getCallingPid() + "/" + callingUid);
        Context context = getContext();
        if (!TextUtils.equals(
                context.getPackageManager().getPackagesForUid(Binder.getCallingUid())[0],
                context.getString(R.string.config_aosp_emergency_package_name))) {
            throw new SecurityException(
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                            callingUid, "Uid is not allowed: "));
        }
        if (!TextUtils.equals(str2, "com.android.settings.emergency.MAKE_EMERGENCY_CALL")) {
            throw new IllegalArgumentException("Unsupported operation");
        }
        Context context2 = getContext();
        if (context2.getPackageManager().hasSystemFeature("android.hardware.telephony")) {
            Bundle bundle2 = new Bundle();
            bundle2.putBoolean("android.telecom.extra.IS_USER_INTENT_EMERGENCY_CALL", true);
            bundle2.putInt("android.telecom.extra.CALL_SOURCE", 2);
            ((TelecomManager) context2.getSystemService(TelecomManager.class))
                    .placeCall(
                            Uri.fromParts(
                                    "tel",
                                    new EmergencyNumberUtils(context2).getPoliceNumber(),
                                    null),
                            bundle2);
        } else {
            Log.i("EmergencyActionContentP", "Telephony is not supported, skipping.");
        }
        return new Bundle();
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
        return true;
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
