package com.samsung.android.settings.analyzestorage.presenter.managers;

import android.content.Context;
import android.os.SemSystemProperties;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.analyzestorage.data.model.RecommendCardInfo;
import com.samsung.android.settings.analyzestorage.presenter.controllers.RecommendCardController;
import com.samsung.android.settings.analyzestorage.presenter.feature.SepFeatures$DeviceFeature;
import com.samsung.android.settings.analyzestorage.presenter.utils.StringConverter;

import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AsRecommendCardInfoManager {
    public static final Companion Companion = new Companion();
    public static ArrayList appList;
    public static ArrayList cloudList;
    public static volatile AsRecommendCardInfoManager instance;
    public final Context context;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {
        public final AsRecommendCardInfoManager getInstance(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            AsRecommendCardInfoManager asRecommendCardInfoManager =
                    AsRecommendCardInfoManager.instance;
            if (asRecommendCardInfoManager == null) {
                synchronized (this) {
                    asRecommendCardInfoManager = AsRecommendCardInfoManager.instance;
                    if (asRecommendCardInfoManager == null) {
                        Context applicationContext = context.getApplicationContext();
                        Intrinsics.checkNotNullExpressionValue(
                                applicationContext, "getApplicationContext(...)");
                        asRecommendCardInfoManager =
                                new AsRecommendCardInfoManager(applicationContext);
                        AsRecommendCardInfoManager.instance = asRecommendCardInfoManager;
                    }
                }
            }
            return asRecommendCardInfoManager;
        }
    }

    public AsRecommendCardInfoManager(Context context) {
        this.context = context;
    }

    public final RecommendCardInfo getAsRecommendCardInfo(
            int i, RecommendCardController recommendCardController) {
        Long l;
        Integer num;
        String str;
        String string;
        List list;
        HashMap hashMap;
        HashMap hashMap2;
        HashMap hashMap3;
        if (recommendCardController == null
                || (hashMap3 = recommendCardController.fileSizeCard) == null
                || (l = (Long) hashMap3.get(Integer.valueOf(i))) == null) {
            l = -1L;
        }
        long longValue = l.longValue();
        if (recommendCardController == null
                || (hashMap2 = recommendCardController.fileTypeCard) == null
                || (num = (Integer) hashMap2.get(Integer.valueOf(i))) == null) {
            num = -1;
        }
        int intValue = num.intValue();
        if (recommendCardController == null
                || (hashMap = recommendCardController.pathCard) == null
                || (str = (String) hashMap.get(Integer.valueOf(i))) == null) {
            str = ApnSettings.MVNO_NONE;
        }
        String formatFileSize = StringConverter.formatFileSize(0, longValue, this.context);
        String string2 =
                this.context.getString(
                        intValue != 1
                                ? intValue != 2
                                        ? intValue != 3
                                                ? (intValue < 410
                                                                || intValue
                                                                        > FileType
                                                                                .LAST_ARCHIVE_FILE_TYPE)
                                                        ? R.string.as_recommend_apk_type_title
                                                        : R.string.as_recommend_comppress_type_title
                                                : R.string.as_recommend_video_type_title
                                        : R.string.as_recommend_audio_type_title
                                : i == 2
                                        ? R.string.as_recommend_screenshot_type_title
                                        : R.string.as_recommend_image_type_title);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        int i2 = recommendCardController != null ? recommendCardController.numberApps : 0;
        switch (i) {
            case 0:
            case 1:
            case 2:
                if (str.length() <= 0) {
                    string =
                            this.context.getString(
                                    R.string
                                            .as_recommend_card_description_delete_old_media_file_without_folder_name,
                                    formatFileSize,
                                    string2);
                    Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                    break;
                } else {
                    string =
                            this.context.getString(
                                    R.string.as_recommend_card_description_delete_old_media_file,
                                    formatFileSize,
                                    string2,
                                    str);
                    Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                    break;
                }
            case 3:
                string =
                        this.context.getString(
                                R.string.as_recommend_card_description_clean_download_folder,
                                formatFileSize);
                Intrinsics.checkNotNull(string);
                break;
            case 4:
                string =
                        this.context.getString(
                                R.string
                                        .as_recommend_card_description_nondestructive_original_files,
                                formatFileSize);
                Intrinsics.checkNotNull(string);
                break;
            case 5:
                string =
                        this.context.getString(
                                R.string
                                        .as_recommend_card_description_recommendation_of_cloud_connection);
                Intrinsics.checkNotNull(string);
                break;
            case 6:
            default:
                string =
                        this.context.getString(
                                R.string
                                        .as_recommend_card_description_top_three_app_with_large_size);
                Intrinsics.checkNotNull(string);
                break;
            case 7:
                Context context = this.context;
                synchronized (SepFeatures$DeviceFeature.class) {
                    try {
                        if (SepFeatures$DeviceFeature.sIsTablet == null) {
                            SepFeatures$DeviceFeature.sIsTablet =
                                    Boolean.valueOf(
                                            SemSystemProperties.get("ro.build.characteristics")
                                                    .contains("tablet"));
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                string =
                        context.getString(
                                SepFeatures$DeviceFeature.sIsTablet.booleanValue()
                                        ? R.string
                                                .as_recommend_card_description_tablet_storage_space_running_out
                                        : R.string
                                                .as_recommend_card_description_phone_storage_space_running_out);
                Intrinsics.checkNotNull(string);
                break;
            case 8:
                string =
                        this.context.getString(
                                R.string.as_recommend_card_description_extracted_decompressed_files,
                                formatFileSize);
                Intrinsics.checkNotNull(string);
                break;
            case 9:
                string =
                        this.context
                                .getResources()
                                .getQuantityString(
                                        R.plurals.as_recommend_card_description_archive_apps,
                                        i2,
                                        formatFileSize,
                                        Integer.valueOf(i2));
                Intrinsics.checkNotNull(string);
                break;
        }
        String str2 = null;
        if (i == 5) {
            if (i == 5) {
                list = cloudList;
            }
            list = null;
        } else if (i != 6) {
            list = EmptyList.INSTANCE;
        } else {
            if (i == 6) {
                list = appList;
            }
            list = null;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
                str2 = this.context.getString(R.string.as_recommend_card_review_old_files_button);
                break;
            case 4:
            case 8:
                str2 = this.context.getString(R.string.as_recommend_card_select_files_button);
                break;
            case 5:
            case 7:
                break;
            case 6:
            default:
                str2 = this.context.getString(R.string.as_recommend_card_view_all_button);
                break;
            case 9:
                str2 = this.context.getString(R.string.as_recommend_card_archive_apps_button);
                break;
        }
        return new RecommendCardInfo(string, str2, i, list);
    }
}
