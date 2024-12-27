package com.samsung.android.settings.analyzestorage.presenter.managers;

import android.os.UserHandle;

import com.samsung.android.settings.analyzestorage.domain.log.Log;

import kotlin.jvm.internal.Intrinsics;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class UserInfoCheckerImpl$getWorkProfileUserId$1 implements Consumer {
    public static final UserInfoCheckerImpl$getWorkProfileUserId$1 INSTANCE =
            new UserInfoCheckerImpl$getWorkProfileUserId$1();

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        UserHandle e = (UserHandle) obj;
        Intrinsics.checkNotNullParameter(e, "e");
        Log.e("UserInfoCheckerImpl", "getWorkProfileUserId userId : " + e.semGetIdentifier());
    }
}
