package com.samsung.android.settings.analyzestorage.external.database.repository.analyzestorage;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.lifecycle.MutableLiveData;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.analyzestorage.data.constant.AnalyzeStorageConstants$UiItemType;
import com.samsung.android.settings.analyzestorage.data.constant.DomainType;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.external.database.datasource.MediaProviderDataSource;
import com.samsung.android.settings.analyzestorage.external.database.utils.CategoryQueryUtils;
import com.samsung.android.settings.analyzestorage.presenter.feature.Features$Knox;
import com.samsung.android.settings.analyzestorage.presenter.managers.MediaFileManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.TrashManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoManager;
import com.samsung.android.settings.analyzestorage.presenter.utils.CategoryType;
import com.samsung.android.settings.analyzestorage.presenter.utils.ContentResolverWrapper;
import com.samsung.android.settings.analyzestorage.presenter.utils.fileutils.FileUriConverter;

import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class LocalStorageQueryHelper {
    public final Context mContext;
    public final MediaProviderDataSource mMediaProviderDataSource;

    public LocalStorageQueryHelper(
            Context context, MediaProviderDataSource mediaProviderDataSource) {
        this.mContext = context.getApplicationContext();
        this.mMediaProviderDataSource = mediaProviderDataSource;
    }

    public final String getCategorySizeKey(
            AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType) {
        String concat = getStorageName().concat("_");
        int ordinal = analyzeStorageConstants$UiItemType.ordinal();
        if (ordinal == 0) {
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                    concat, "category_image_size");
        }
        if (ordinal == 1) {
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                    concat, "category_video_size");
        }
        if (ordinal == 2) {
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                    concat, "category_audio_size");
        }
        if (ordinal == 3) {
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                    concat, "category_document_size");
        }
        if (ordinal == 4) {
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                    concat, "category_apk_size");
        }
        if (ordinal != 5) {
            return null;
        }
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                concat, "category_compressed_size");
    }

    public final Cursor getCursorForSizeOfCategory(CategoryType categoryType, boolean z) {
        String rootPath = getRootPath();
        int userId = getUserId();
        MediaProviderDataSource mediaProviderDataSource = this.mMediaProviderDataSource;
        mediaProviderDataSource.getClass();
        String[] strArr =
                z ? new String[] {"SUM(_size)", "COUNT(_id)"} : new String[] {"SUM(_size)"};
        Context context = mediaProviderDataSource.mContext;
        Uri addUserIdToUri =
                FileUriConverter.addUserIdToUri(MediaProviderDataSource.MEDIA_PROVIDER_URI, userId);
        StringBuilder sb = new StringBuilder(512);
        int ordinal = categoryType.ordinal();
        if (ordinal == 0) {
            sb.append("media_type=1");
        } else if (ordinal == 1) {
            sb.append("media_type=2");
        } else if (ordinal == 2) {
            sb.append("media_type=3");
        } else if (ordinal == 3) {
            String[] strArr2 = (String[]) MediaFileManager.documentExtension.toArray(new String[0]);
            Locale locale = Locale.US;
            sb.append(
                    ComposerKt$$ExternalSyntheticOutline0.m(
                                    "((media_type = 6 OR media_type = 0) AND ",
                                    CategoryQueryUtils.getRegexpExtensionPatterns(strArr2),
                                    ")")
                            + " AND mime_type IS NOT NULL");
        } else if (ordinal == 5) {
            sb.append("_data LIKE '%.apk' AND mime_type IS NOT NULL");
        } else if (ordinal != 6) {
            Log.e(
                    "MediaProviderDataSource",
                    "getCategorySelection() ] Unsupported category type - " + categoryType);
        } else {
            String[] strArr3 = (String[]) MediaFileManager.archiveExtension.toArray(new String[0]);
            Locale locale2 = Locale.US;
            sb.append(
                    ComposerKt$$ExternalSyntheticOutline0.m(
                                    "(media_type = 0 AND ",
                                    CategoryQueryUtils.getRegexpExtensionPatterns(strArr3),
                                    ")")
                            + " AND mime_type IS NOT NULL");
        }
        if (sb.length() > 0) {
            sb.append(" AND ");
        }
        ConstraintWidget$$ExternalSyntheticOutline0.m(sb, "_data", " REGEXP '", "/storage", "/.+'");
        ConstraintWidget$$ExternalSyntheticOutline0.m(sb, " AND ", "is_pending", "=0", " AND ");
        StringBuilder sb2 =
                new StringBuilder(
                        ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, "is_trashed", "=0"));
        if (!TextUtils.isEmpty(rootPath)) {
            sb2.append(" AND (_data LIKE '");
            sb2.append(rootPath);
            sb2.append("%')");
        }
        String selection = sb2.toString();
        Intrinsics.checkNotNullParameter(selection, "selection");
        List listOf =
                CollectionsKt__CollectionsKt.listOf(
                        (Object[])
                                new String[] {
                                    selection,
                                    Features$Knox.isRetailMode
                                            ? "_data NOT LIKE '%/Android/.data/%'"
                                            : ApnSettings.MVNO_NONE,
                                    "_data NOT LIKE '%/Android/.Trash/%'"
                                });
        ArrayList arrayList = new ArrayList();
        for (Object obj : listOf) {
            if (((String) obj).length() > 0) {
                arrayList.add(obj);
            }
        }
        return ContentResolverWrapper.query(
                context,
                addUserIdToUri,
                strArr,
                CollectionsKt___CollectionsKt.joinToString$default(
                        arrayList, " AND ", null, null, null, 62),
                null);
    }

    public abstract int getDomainType();

    public abstract String getRootPath();

    public abstract String getStorageName();

    public long[] getTrashCapacity() {
        int domainType = getDomainType();
        MutableLiveData mutableLiveData = TrashManager.isLoadCompleted;
        long[] jArr = new long[2];
        long j = 0;
        Arrays.fill(jArr, 0L);
        if (DomainType.isInternalStorage(domainType)) {
            j = TrashManager.appTrashInternalCapacity;
        } else if (DomainType.isSd(domainType)) {
            j = TrashManager.appTrashExternalSDCapacity;
        } else if (2 == domainType) {
            j = TrashManager.appTrashAppCloneCapacity;
        }
        jArr[0] = j;
        jArr[1] = TrashManager.appDataCapacity;
        Log.d(
                "TrashManager",
                "getTrashAppCapacity ] domainType = " + domainType + ", trashSize = " + j);
        return jArr;
    }

    public final long[] getTrashCapacityFromMyFiles() {
        long[] jArr = new long[2];
        try {
            Bundle bundle = new Bundle();
            bundle.putString("calling_package", KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
            bundle.putInt("asType", 3);
            Log.d(getStorageName(), "request trash()] ");
            Bundle call =
                    this.mContext
                            .getContentResolver()
                            .call(
                                    Uri.parse("content://" + getUserId() + "@myfiles/"),
                                    "GET_AS_SIZE",
                                    (String) null,
                                    bundle);
            long j = call.getLong("TRASH_INTERNAL_CAPACITY", 0L);
            long j2 = call.getLong("TRASH_SDCARD_CAPACITY", 0L);
            long j3 = call.getLong("TRASH_INTERNAL_APP_CLONE_CAPACITY", 0L);
            long j4 = call.getLong("TRASH_APP_DATA_CAPACITY", 0L);
            jArr[0] = j + j2 + j3;
            jArr[1] = j4;
            Log.d(
                    getStorageName(),
                    "trash size] internal - "
                            + j
                            + "  sd - "
                            + j2
                            + "  app clone - "
                            + j3
                            + "   app data - "
                            + j4
                            + "  total - "
                            + jArr[0]);
        } catch (Exception e) {
            Log.e(
                    getStorageName(),
                    "getTrashCapacityFromMyFiles() ] userId : "
                            + getUserId()
                            + " , Exception e : "
                            + e.getMessage());
        }
        return jArr;
    }

    public int getUserId() {
        return UserInfoManager.getMyUserId();
    }
}
