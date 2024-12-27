package com.samsung.android.settings.analyzestorage.presenter.managers;

import android.content.Context;
import android.os.UserHandle;
import android.os.UserManager;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.analyzestorage.domain.log.Log;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class UserInfoCheckerImpl {
    public final int getMyUserId() {
        try {
            return UserHandle.semGetMyUserId();
        } catch (NoSuchMethodError e) {
            Log.e("UserInfoCheckerImpl", "getMyUserId() ] Exception e : " + e.getMessage());
            return 0;
        }
    }

    public final int getSecureFolderUserId(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Iterator it = getUserInfoList(context).iterator();
        while (it.hasNext()) {
            int semGetIdentifier = ((UserHandle) it.next()).semGetIdentifier();
            if (SemPersonaManager.isSecureFolderId(semGetIdentifier)) {
                Log.d(
                        "UserInfoCheckerImpl",
                        "getSecureFolderUserId ] Secure folder is enabled for user "
                                + semGetIdentifier);
                return semGetIdentifier;
            }
        }
        Log.w("UserInfoCheckerImpl", "getSecureFolderUserId ] Secure folder is not enabled.");
        return -1;
    }

    public final List getUserInfoList(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        ArrayList arrayList = new ArrayList();
        try {
            Object systemService = context.getSystemService("user");
            Intrinsics.checkNotNull(
                    systemService, "null cannot be cast to non-null type android.os.UserManager");
            Object collect =
                    ((UserManager) systemService)
                            .semGetUsers().stream()
                                    .map(UserInfoCheckerImpl$getUserInfoList$1.INSTANCE)
                                    .collect(Collectors.toList());
            Intrinsics.checkNotNullExpressionValue(collect, "collect(...)");
            return (List) collect;
        } catch (NoSuchMethodError e) {
            Log.e("UserInfoCheckerImpl", "getUserInfoList() ] Exception e : " + e.getMessage());
            return arrayList;
        }
    }

    public final int getWorkProfileUserId(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService("user");
        Intrinsics.checkNotNull(
                systemService, "null cannot be cast to non-null type android.os.UserManager");
        List<UserHandle> userProfiles = ((UserManager) systemService).getUserProfiles();
        Intrinsics.checkNotNullExpressionValue(userProfiles, "getUserProfiles(...)");
        List list =
                (List)
                        userProfiles.stream()
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoCheckerImpl$getWorkProfileUserId$filteredList$1
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                UserHandle user = (UserHandle) obj;
                                                Intrinsics.checkNotNullParameter(user, "user");
                                                UserInfoCheckerImpl userInfoCheckerImpl =
                                                        UserInfoCheckerImpl.this;
                                                if (userInfoCheckerImpl.getMyUserId()
                                                        != user.semGetIdentifier()) {
                                                    UserInfoCheckerImpl userInfoCheckerImpl2 =
                                                            UserInfoCheckerImpl.this;
                                                    int semGetIdentifier = user.semGetIdentifier();
                                                    userInfoCheckerImpl2.getClass();
                                                    if (!SemPersonaManager.isSecureFolderId(
                                                            semGetIdentifier)) {
                                                        UserInfoCheckerImpl userInfoCheckerImpl3 =
                                                                UserInfoCheckerImpl.this;
                                                        int semGetIdentifier2 =
                                                                user.semGetIdentifier();
                                                        userInfoCheckerImpl3.getClass();
                                                        if (!SemDualAppManager.isDualAppId(
                                                                semGetIdentifier2)) {
                                                            return true;
                                                        }
                                                    }
                                                }
                                                return false;
                                            }
                                        })
                                .collect(Collectors.toList());
        if (list.isEmpty()) {
            Log.w("UserInfoCheckerImpl", "getWorkProfileUserId ] work profile is not enabled.");
            return -1;
        }
        if (list.size() == 1) {
            return ((UserHandle) list.get(0)).semGetIdentifier();
        }
        list.forEach(UserInfoCheckerImpl$getWorkProfileUserId$1.INSTANCE);
        throw new IllegalStateException(
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                        list.size(), "getWorkProfileUserId filteredList size : "));
    }
}
