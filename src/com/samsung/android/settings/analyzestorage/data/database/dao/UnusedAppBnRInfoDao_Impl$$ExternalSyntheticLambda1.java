package com.samsung.android.settings.analyzestorage.data.database.dao;

import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class UnusedAppBnRInfoDao_Impl$$ExternalSyntheticLambda1
        implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        SQLiteStatement prepare = ((SQLiteConnection) obj).prepare("DELETE FROM bnr_unused_apps");
        try {
            prepare.step();
            prepare.close();
            return null;
        } catch (Throwable th) {
            prepare.close();
            throw th;
        }
    }
}
