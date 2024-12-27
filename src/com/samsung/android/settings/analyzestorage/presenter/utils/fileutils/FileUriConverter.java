package com.samsung.android.settings.analyzestorage.presenter.utils.fileutils;

import android.net.Uri;
import android.text.TextUtils;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoCheckerImpl;
import com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoManager;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class FileUriConverter {
    public static Uri addUserIdToUri(Uri uri, int i) {
        if (uri == null) {
            return null;
        }
        UserInfoCheckerImpl userInfoCheckerImpl = UserInfoManager.sUserInfoChecker;
        if (userInfoCheckerImpl == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sUserInfoChecker");
            throw null;
        }
        if (userInfoCheckerImpl.getMyUserId() == i || UserInfoManager.isCloneProfile(i)) {
            Log.d(
                    "FileUriConverter",
                    "addUserIdToUri() ] userId(" + i + ") does not need to add UserInfo");
            return uri;
        }
        if (!"content".equals(uri.getScheme()) || !TextUtils.isEmpty(uri.getUserInfo())) {
            return uri;
        }
        Uri.Builder buildUpon = uri.buildUpon();
        buildUpon.encodedAuthority(i + "@" + uri.getEncodedAuthority());
        return buildUpon.build();
    }
}
