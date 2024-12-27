package com.samsung.android.settings.analyzestorage.presenter.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.SparseArray;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoCheckerImpl;
import com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoManager;

import kotlin.jvm.internal.Intrinsics;

import java.io.File;
import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class StoragePathUtils {
    public static String sExtSdCardPath = "/storage/extSdCard";
    public static String sInternalAppCloneStorageRoot;
    public static String sInternalStorageRoot;
    public static final SparseArray sNetworkDomainTypeArray;
    public static SparseArray sUsbStoragePathArray = new SparseArray();

    static {
        SparseArray sparseArray = new SparseArray();
        sNetworkDomainTypeArray = sparseArray;
        sparseArray.append(101, "/GoogleDrive");
        sparseArray.append(102, "/OneDrive");
    }

    public static int getDomainType(String str) {
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        if (str.startsWith("/sdcard")) {
            try {
                str = Paths.get(str, new String[0]).toRealPath(new LinkOption[0]).toString();
            } catch (IOException unused) {
                Log.d(
                        "StoragePathUtils",
                        "getDomainType() - problem with invocation toRealPath() on path ");
            }
        }
        if (isUnderPath(getInternalStoragePath(), str)) {
            return 0;
        }
        if (isUnderPath(sInternalAppCloneStorageRoot, str)) {
            return 2;
        }
        if (isUnderPath(sExtSdCardPath, str)) {
            return 1;
        }
        if (str != null
                && (str.startsWith("/Image")
                        || str.startsWith("/Video")
                        || str.startsWith("/Audio")
                        || str.startsWith("/Document")
                        || str.startsWith("/Downloads")
                        || str.startsWith("/Apk"))) {
            return 302;
        }
        if (str.startsWith("/SearchHistory")) {
            return 401;
        }
        if (str.startsWith("/GoogleDrive")) {
            return 101;
        }
        if (str.startsWith("/OneDrive")) {
            return 102;
        }
        int size = sNetworkDomainTypeArray.size();
        for (int i = 0; i < size; i++) {
            SparseArray sparseArray = sNetworkDomainTypeArray;
            if (str.startsWith((String) sparseArray.valueAt(i))) {
                return sparseArray.keyAt(i);
            }
        }
        SparseArray sparseArray2 = sUsbStoragePathArray;
        if (sparseArray2 != null && sparseArray2.size() > 0) {
            int size2 = sUsbStoragePathArray.size();
            for (int i2 = 0; i2 < size2; i2++) {
                if (isUnderPath((String) sUsbStoragePathArray.valueAt(i2), str)) {
                    return sUsbStoragePathArray.keyAt(i2);
                }
            }
        }
        return -1;
    }

    public static String getInternalKnoxStoragePath(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        UserInfoCheckerImpl userInfoCheckerImpl = UserInfoManager.sUserInfoChecker;
        if (userInfoCheckerImpl == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sUserInfoChecker");
            throw null;
        }
        int workProfileUserId = userInfoCheckerImpl.getWorkProfileUserId(context);
        StringBuilder sb = new StringBuilder();
        String str = sInternalStorageRoot;
        sb.append(str.substring(0, str.lastIndexOf(File.separatorChar) + 1));
        sb.append(workProfileUserId);
        return sb.toString();
    }

    public static String getInternalSecureFolderStoragePath(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        UserInfoCheckerImpl userInfoCheckerImpl = UserInfoManager.sUserInfoChecker;
        if (userInfoCheckerImpl == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sUserInfoChecker");
            throw null;
        }
        int secureFolderUserId = userInfoCheckerImpl.getSecureFolderUserId(context);
        StringBuilder sb = new StringBuilder();
        String str = sInternalStorageRoot;
        sb.append(str.substring(0, str.lastIndexOf(File.separatorChar) + 1));
        sb.append(secureFolderUserId);
        return sb.toString();
    }

    public static String getInternalStoragePath() {
        if (TextUtils.isEmpty(sInternalStorageRoot)
                || !sInternalStorageRoot.startsWith("/storage/emulated")) {
            sInternalStorageRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return sInternalStorageRoot;
    }

    public static String getRootPath(int i) {
        if (i == 0) {
            return getInternalStoragePath();
        }
        if (i == 1) {
            return sExtSdCardPath;
        }
        if (i == 2) {
            return sInternalAppCloneStorageRoot;
        }
        if (i == 303) {
            return "/DownloadHistory";
        }
        if (i == 306) {
            return "/AnalyzeStorage";
        }
        switch (i) {
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                return (String) sUsbStoragePathArray.get(i);
            default:
                return (String) sNetworkDomainTypeArray.get(i);
        }
    }

    public static boolean isUnderPath(String str, String str2) {
        if (str2 == null || str == null) {
            return false;
        }
        boolean startsWith = str2.startsWith(str);
        return (!startsWith || str.length() == str2.length())
                ? startsWith
                : str2.charAt(str.length()) == File.separatorChar;
    }
}
