package com.samsung.android.settings.analyzestorage.presenter.managers;

import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.provider.MediaStore;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;

import com.samsung.android.settings.analyzestorage.data.constant.DomainType;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.constant.CloudConstants$CloudType;
import com.samsung.android.settings.analyzestorage.presenter.controllers.SettingsCloudAccountManager;
import com.samsung.android.settings.analyzestorage.presenter.utils.StoragePathUtils;
import com.samsung.android.settings.analyzestorage.presenter.utils.fileutils.FileWrapper;

import java.io.IOException;
import java.util.EnumMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class StorageUsageManager {
    public static final Uri MEDIA_PROVIDER_URI = MediaStore.Files.getContentUri("external");

    public static long correctionStorageSize(long j) {
        long j2;
        String path = Environment.getRootDirectory().getPath();
        long totalBytes = new StatFs(path).getTotalBytes() + j;
        Log.d(
                "StorageUsageManager",
                "correctionStorageSize() ] rootDirectoryPath : "
                        + Log.getEncodedMsg(path)
                        + " , totalSize : "
                        + j
                        + " , realTotalSize : "
                        + totalBytes);
        int i = 2;
        do {
            j2 = (1 << i) * 1000000000;
            i++;
            if (totalBytes <= j2) {
                break;
            }
        } while (i < 63);
        return j2;
    }

    public static long getExternalStorageTotalSize(
            StorageStatsManager storageStatsManager, UserHandle userHandle) {
        long j;
        try {
            j =
                    storageStatsManager
                            .queryExternalStatsForUser(StorageManager.UUID_DEFAULT, userHandle)
                            .getTotalBytes();
        } catch (IOException | SecurityException e) {
            Log.e(
                    "StorageUsageManager",
                    "getExternalStorageTotalSize ] Exception : "
                            + e
                            + " \n userHandle : "
                            + userHandle);
            j = 0L;
        }
        if (j >= 0) {
            return j;
        }
        Log.w(
                "StorageUsageManager",
                "getExternalStorageTotalSize ] The negative value was returned and corrected to"
                    + " zero. userHandle : "
                        + userHandle);
        return 0L;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0052, code lost:

       if (r1.equals(com.sec.ims.configuration.DATA.DM_FIELD_INDEX.AMR_WB_OCTET_ALIGNED) == false) goto L4;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static float getLowMemoryMinPercent(android.content.Context r1, long r2) {
        /*
            android.content.res.Resources r1 = r1.getResources()
            r0 = 1
            com.samsung.android.settings.analyzestorage.presenter.utils.StringConverter$SizeString r1 = com.samsung.android.settings.analyzestorage.presenter.utils.StringConverter.formatBytes(r1, r2, r0)
            java.util.Optional r1 = java.util.Optional.ofNullable(r1)
            com.samsung.android.settings.analyzestorage.presenter.managers.StorageUsageManager$$ExternalSyntheticLambda0 r2 = new com.samsung.android.settings.analyzestorage.presenter.managers.StorageUsageManager$$ExternalSyntheticLambda0
            r2.<init>()
            java.util.Optional r1 = r1.map(r2)
            java.lang.String r2 = ""
            java.lang.Object r1 = r1.orElse(r2)
            java.lang.String r1 = (java.lang.String) r1
            r1.getClass()
            r2 = -1
            int r3 = r1.hashCode()
            switch(r3) {
                case 1631: goto L55;
                case 1726: goto L4c;
                case 48695: goto L41;
                case 49747: goto L36;
                case 52502: goto L2b;
                default: goto L29;
            }
        L29:
            r0 = r2
            goto L5f
        L2b:
            java.lang.String r3 = "512"
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L34
            goto L29
        L34:
            r0 = 4
            goto L5f
        L36:
            java.lang.String r3 = "256"
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L3f
            goto L29
        L3f:
            r0 = 3
            goto L5f
        L41:
            java.lang.String r3 = "128"
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L4a
            goto L29
        L4a:
            r0 = 2
            goto L5f
        L4c:
            java.lang.String r3 = "64"
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L5f
            goto L29
        L55:
            java.lang.String r3 = "32"
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L5e
            goto L29
        L5e:
            r0 = 0
        L5f:
            switch(r0) {
                case 0: goto L68;
                case 1: goto L65;
                case 2: goto L65;
                case 3: goto L68;
                case 4: goto L68;
                default: goto L62;
            }
        L62:
            r1 = 1075838976(0x40200000, float:2.5)
            goto L6a
        L65:
            r1 = 1092616192(0x41200000, float:10.0)
            goto L6a
        L68:
            r1 = 1084227584(0x40a00000, float:5.0)
        L6a:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.analyzestorage.presenter.managers.StorageUsageManager.getLowMemoryMinPercent(android.content.Context,"
                    + " long):float");
    }

    public static long getStorageFreeSpace(int i) {
        if (!DomainType.isCloud(i)) {
            return getStorageFreeSpace$1(i);
        }
        long storageTotalSizeFromCloud =
                getStorageTotalSizeFromCloud(i) - getStorageUsedSizeFromCloud(i);
        if (storageTotalSizeFromCloud > 0) {
            return storageTotalSizeFromCloud;
        }
        return Long.MAX_VALUE;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static long getStorageFreeSpace$1(int r5) {
        /*
            boolean r0 = com.samsung.android.settings.analyzestorage.data.constant.DomainType.isInternalStorage(r5)
            if (r0 == 0) goto L9
            java.lang.String r5 = "/data"
            goto Ld
        L9:
            java.lang.String r5 = com.samsung.android.settings.analyzestorage.presenter.utils.StoragePathUtils.getRootPath(r5)
        Ld:
            java.lang.String r0 = "StorageUsageManager"
            if (r5 == 0) goto L2e
            android.os.StatFs r1 = new android.os.StatFs     // Catch: java.lang.Exception -> L17
            r1.<init>(r5)     // Catch: java.lang.Exception -> L17
            goto L2f
        L17:
            r1 = move-exception
            java.lang.String r2 = "getStatFS ] path = "
            java.lang.String r3 = ", "
            java.lang.StringBuilder r2 = androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0.m(r2, r5, r3)
            java.lang.String r1 = r1.getMessage()
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            com.samsung.android.settings.analyzestorage.domain.log.Log.e(r0, r1)
        L2e:
            r1 = 0
        L2f:
            if (r1 == 0) goto L36
            long r1 = r1.getAvailableBytes()
            goto L38
        L36:
            r1 = -1
        L38:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "getStorageFreeSpace() ] path : "
            r3.<init>(r4)
            java.lang.String r5 = com.samsung.android.settings.analyzestorage.domain.log.Log.getEncodedMsg(r5)
            r3.append(r5)
            java.lang.String r5 = " , capacity : "
            r3.append(r5)
            r3.append(r1)
            java.lang.String r5 = r3.toString()
            com.samsung.android.settings.analyzestorage.domain.log.Log.d(r0, r5)
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.analyzestorage.presenter.managers.StorageUsageManager.getStorageFreeSpace$1(int):long");
    }

    public static long getStorageTotalSize(int i) {
        if (DomainType.isCloud(i)) {
            return getStorageTotalSizeFromCloud(i);
        }
        String rootPath = StoragePathUtils.getRootPath(i);
        long totalSpace = rootPath != null ? new FileWrapper(rootPath).getTotalSpace() : 0L;
        if ((DomainType.isInternalStorage(i) || 2 == i) && totalSpace > 0) {
            totalSpace = correctionStorageSize(totalSpace);
        }
        StringBuilder m =
                ListPopupWindow$$ExternalSyntheticOutline0.m(
                        i, "getStorageTotalSize() ] domainType : ", " , path : ");
        m.append(Log.getEncodedMsg(rootPath));
        m.append(" , capacity : ");
        m.append(totalSpace);
        Log.d("StorageUsageManager", m.toString());
        return totalSpace;
    }

    public static long getStorageTotalSizeFromCloud(int i) {
        if (SettingsCloudAccountManager.sInstance == null) {
            synchronized (SettingsCloudAccountManager.class) {
                if (SettingsCloudAccountManager.sInstance == null) {
                    SettingsCloudAccountManager settingsCloudAccountManager =
                            new SettingsCloudAccountManager();
                    settingsCloudAccountManager.accountInfoMap =
                            new EnumMap(CloudConstants$CloudType.class);
                    SettingsCloudAccountManager.sInstance = settingsCloudAccountManager;
                }
            }
        }
        SettingsCloudAccountManager settingsCloudAccountManager2 =
                SettingsCloudAccountManager.sInstance;
        CloudConstants$CloudType.Companion.getClass();
        SettingsCloudAccountManager.AccountInfo accountInfo =
                (SettingsCloudAccountManager.AccountInfo)
                        ((EnumMap) settingsCloudAccountManager2.accountInfoMap)
                                .get(CloudConstants$CloudType.Companion.fromDomainType(i));
        if (accountInfo != null) {
            return accountInfo.quotaInfo.totalSize;
        }
        return -1L;
    }

    public static long getStorageUsedSize(Context context, int i) {
        if (!DomainType.isLocalStorage(i)) {
            if (DomainType.isCloud(i)) {
                return getStorageUsedSizeFromCloud(i);
            }
            Log.w(
                    "StorageUsageManager",
                    "getStorageUsedSize ] domainType(" + i + ") is not supported.");
            return -1L;
        }
        if (i == 0) {
            long storageTotalSize = getStorageTotalSize(i);
            return (storageTotalSize != 0 ? storageTotalSize - getStorageFreeSpace(i) : 0L)
                    - getStorageUsedSizeFromExternalStorageStats(context, 2);
        }
        if (i == 2 || i == 3 || i == 4) {
            return getStorageUsedSizeFromExternalStorageStats(context, i);
        }
        long storageTotalSize2 = getStorageTotalSize(i);
        if (storageTotalSize2 == 0) {
            return 0L;
        }
        return storageTotalSize2 - getStorageFreeSpace(i);
    }

    public static long getStorageUsedSizeFromCloud(int i) {
        if (SettingsCloudAccountManager.sInstance == null) {
            synchronized (SettingsCloudAccountManager.class) {
                if (SettingsCloudAccountManager.sInstance == null) {
                    SettingsCloudAccountManager settingsCloudAccountManager =
                            new SettingsCloudAccountManager();
                    settingsCloudAccountManager.accountInfoMap =
                            new EnumMap(CloudConstants$CloudType.class);
                    SettingsCloudAccountManager.sInstance = settingsCloudAccountManager;
                }
            }
        }
        SettingsCloudAccountManager settingsCloudAccountManager2 =
                SettingsCloudAccountManager.sInstance;
        CloudConstants$CloudType.Companion.getClass();
        SettingsCloudAccountManager.AccountInfo accountInfo =
                (SettingsCloudAccountManager.AccountInfo)
                        ((EnumMap) settingsCloudAccountManager2.accountInfoMap)
                                .get(CloudConstants$CloudType.Companion.fromDomainType(i));
        if (accountInfo != null) {
            return accountInfo.quotaInfo.usedSize;
        }
        return -1L;
    }

    public static long getStorageUsedSizeFromExternalStorageStats(
            final Context context, final int i) {
        String str;
        if (2 == i && !StorageVolumeManager.mounted(i)) {
            return 0L;
        }
        final long[] jArr = {-1};
        final StorageStatsManager storageStatsManager =
                (StorageStatsManager) context.getSystemService(StorageStatsManager.class);
        ((ConcurrentHashMap) UserInfoManager.getUserInfoMap(context))
                .forEach(
                        new BiConsumer() { // from class:
                            // com.samsung.android.settings.analyzestorage.presenter.managers.StorageUsageManager$$ExternalSyntheticLambda1
                            /* JADX WARN: Code restructure failed: missing block: B:13:0x0031, code lost:

                               if (r3 == r6.getWorkProfileUserId(r1)) goto L21;
                            */
                            @Override // java.util.function.BiConsumer
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final void accept(java.lang.Object r8, java.lang.Object r9) {
                                /*
                                    r7 = this;
                                    int r0 = r1
                                    android.content.Context r1 = r2
                                    long[] r2 = r3
                                    android.app.usage.StorageStatsManager r7 = r4
                                    android.os.UserHandle r8 = (android.os.UserHandle) r8
                                    java.lang.Integer r9 = (java.lang.Integer) r9
                                    r3 = 2
                                    if (r3 != r0) goto L19
                                    int r3 = r9.intValue()
                                    boolean r3 = com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoManager.isCloneProfile(r3)
                                    if (r3 != 0) goto L49
                                L19:
                                    r3 = 3
                                    r4 = 0
                                    java.lang.String r5 = "sUserInfoChecker"
                                    if (r3 != r0) goto L38
                                    int r3 = r9.intValue()
                                    java.lang.String r6 = "context"
                                    kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r1, r6)
                                    com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoCheckerImpl r6 = com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoManager.sUserInfoChecker
                                    if (r6 == 0) goto L34
                                    int r1 = r6.getWorkProfileUserId(r1)
                                    if (r3 != r1) goto L38
                                    goto L49
                                L34:
                                    kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r5)
                                    throw r4
                                L38:
                                    r1 = 4
                                    if (r1 != r0) goto L55
                                    int r9 = r9.intValue()
                                    com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoCheckerImpl r0 = com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoManager.sUserInfoChecker
                                    if (r0 == 0) goto L51
                                    boolean r9 = com.samsung.android.knox.SemPersonaManager.isSecureFolderId(r9)
                                    if (r9 == 0) goto L55
                                L49:
                                    r9 = 0
                                    long r7 = com.samsung.android.settings.analyzestorage.presenter.managers.StorageUsageManager.getExternalStorageTotalSize(r7, r8)
                                    r2[r9] = r7
                                    goto L55
                                L51:
                                    kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r5)
                                    throw r4
                                L55:
                                    return
                                */
                                throw new UnsupportedOperationException(
                                        "Method not decompiled:"
                                            + " com.samsung.android.settings.analyzestorage.presenter.managers.StorageUsageManager$$ExternalSyntheticLambda1.accept(java.lang.Object,"
                                            + " java.lang.Object):void");
                            }
                        });
        StringBuilder sb = new StringBuilder("getExternalStorageTotalSize() ] ");
        if (jArr[0] == -1) {
            str =
                    LazyListMeasuredItem$$ExternalSyntheticOutline0.m(
                            i, "domainType(", ") is not supported.");
        } else {
            str = "size : " + jArr[0];
        }
        sb.append(str);
        Log.d("StorageUsageManager", sb.toString());
        return jArr[0];
    }

    public static boolean needToShowAppCacheList(Context context) {
        long storageTotalSize = getStorageTotalSize(0);
        return getStorageFreeSpace$1(0)
                <= ((long)
                                (getLowMemoryMinPercent(context, storageTotalSize)
                                        * ((float) storageTotalSize)))
                        / 100;
    }
}
