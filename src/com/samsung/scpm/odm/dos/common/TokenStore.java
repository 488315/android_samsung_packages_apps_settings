package com.samsung.scpm.odm.dos.common;

import android.content.Context;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class TokenStore {
    public static String load(Context context, String str) {
        return context.getSharedPreferences("scpm.token.store", 0).getString(str + "_token", null);
    }
}
