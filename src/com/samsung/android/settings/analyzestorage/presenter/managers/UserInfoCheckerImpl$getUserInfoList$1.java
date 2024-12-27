package com.samsung.android.settings.analyzestorage.presenter.managers;

import android.content.pm.SemUserInfo;

import kotlin.jvm.internal.Intrinsics;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class UserInfoCheckerImpl$getUserInfoList$1 implements Function {
    public static final UserInfoCheckerImpl$getUserInfoList$1 INSTANCE =
            new UserInfoCheckerImpl$getUserInfoList$1();

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        SemUserInfo obj2 = (SemUserInfo) obj;
        Intrinsics.checkNotNullParameter(obj2, "obj");
        return obj2.getUserHandle();
    }
}
