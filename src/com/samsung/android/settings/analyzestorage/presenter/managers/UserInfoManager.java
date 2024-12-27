package com.samsung.android.settings.analyzestorage.presenter.managers;

import android.content.Context;
import android.content.pm.SemUserInfo;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.domain.log.Log;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt___StringsKt;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class UserInfoManager {
    public static UserInfoCheckerImpl sUserInfoChecker;

    public static final int getMyUserId() {
        UserInfoCheckerImpl userInfoCheckerImpl = sUserInfoChecker;
        if (userInfoCheckerImpl != null) {
            return userInfoCheckerImpl.getMyUserId();
        }
        Intrinsics.throwUninitializedPropertyAccessException("sUserInfoChecker");
        throw null;
    }

    public static final int getUserIdFromDomainType(Context context, int i) {
        Intrinsics.checkNotNullParameter(context, "context");
        UserInfoCheckerImpl userInfoCheckerImpl = sUserInfoChecker;
        if (userInfoCheckerImpl != null) {
            return i != 3
                    ? i != 4
                            ? userInfoCheckerImpl.getMyUserId()
                            : userInfoCheckerImpl.getSecureFolderUserId(context)
                    : userInfoCheckerImpl.getWorkProfileUserId(context);
        }
        Intrinsics.throwUninitializedPropertyAccessException("sUserInfoChecker");
        throw null;
    }

    public static final int getUserIdFromPath(String rootPath) {
        Intrinsics.checkNotNullParameter(rootPath, "rootPath");
        int lastIndexOf$default =
                !TextUtils.isEmpty(rootPath)
                        ? StringsKt___StringsKt.lastIndexOf$default(rootPath, File.separatorChar)
                        : -1;
        if (lastIndexOf$default == -1) {
            return -1;
        }
        String substring = rootPath.substring(lastIndexOf$default + 1);
        Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
        return Integer.parseInt(substring);
    }

    public static final int getUserIdFromVolumeId(String volumeId) {
        String str;
        Intrinsics.checkNotNullParameter(volumeId, "volumeId");
        int i = -1;
        if (TextUtils.isEmpty(volumeId)) {
            return -1;
        }
        int lastIndexOf$default = StringsKt___StringsKt.lastIndexOf$default(volumeId, ';');
        if (lastIndexOf$default >= 0) {
            str = volumeId.substring(lastIndexOf$default + 1);
            Intrinsics.checkNotNullExpressionValue(str, "substring(...)");
        } else {
            str = ApnSettings.MVNO_NONE;
        }
        try {
            i = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            Log.e(
                    "UserInfoManager",
                    "getUserIdFromVolumeId() ] userIdToken : "
                            + str
                            + " , Exception e : "
                            + e.getMessage());
        }
        Log.d(
                "UserInfoManager",
                "getUserIdFromVolumeId() ] userIdToken : " + str + " , userId : " + i);
        return i;
    }

    public static final Map getUserInfoMap(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (sUserInfoChecker == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sUserInfoChecker");
            throw null;
        }
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        try {
            Object systemService = context.getSystemService("user");
            Intrinsics.checkNotNull(
                    systemService, "null cannot be cast to non-null type android.os.UserManager");
            for (SemUserInfo semUserInfo : ((UserManager) systemService).semGetUsers()) {
                UserHandle userHandle = semUserInfo.getUserHandle();
                Intrinsics.checkNotNullExpressionValue(userHandle, "getUserHandle(...)");
                concurrentHashMap.put(
                        userHandle,
                        Integer.valueOf(semUserInfo.getUserHandle().semGetIdentifier()));
            }
        } catch (NoSuchMethodError e) {
            Log.e("UserInfoCheckerImpl", "getUserInfoMap() ] Exception e : " + e.getMessage());
        }
        return concurrentHashMap;
    }

    public static final boolean isCloneProfile(int i) {
        if (sUserInfoChecker != null) {
            return SemDualAppManager.isDualAppId(i);
        }
        Intrinsics.throwUninitializedPropertyAccessException("sUserInfoChecker");
        throw null;
    }
}
