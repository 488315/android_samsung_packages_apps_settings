package com.samsung.android.settings.languagepack.data;

import android.content.Context;
import android.text.TextUtils;

import com.android.settings.R;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.languagepack.logger.Log;
import com.samsung.android.settings.languagepack.service.LanguagePackDownloadService;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class LanguageInfo implements Comparable {
    public Context mContext;
    public long mCurrentDownloadedSize;
    public int mDisplayOrder;
    public boolean mIsStandardTranslationLanguage;
    public String mLanguageCode;
    public String mLanguageDisplayName;
    public CopyOnWriteArrayList mList;
    public int mOrder;
    public boolean mShowLocale;
    public LanguagePackDownloadService.Status mStatus;
    public int mSupportType;
    public int mToastType;
    public long mTotalLanguagePackSize;

    public final boolean checkDownloadedInSettings(final Context context, List list) {
        final int i = 0;
        List list2 =
                (List)
                        this.mList.stream()
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.samsung.android.settings.languagepack.data.LanguageInfo$$ExternalSyntheticLambda1
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                int i2 = i;
                                                Context context2 = context;
                                                PackageInfo packageInfo = (PackageInfo) obj;
                                                switch (i2) {
                                                }
                                                return packageInfo.isUnInstallablePackage(context2);
                                            }
                                        })
                                .collect(Collectors.toList());
        List<LanguageInfo> list3 =
                (List)
                        ((List)
                                        list.stream()
                                                .filter(
                                                        new LanguageInfo$$ExternalSyntheticLambda0(
                                                                this, 0))
                                                .collect(Collectors.toList()))
                                .stream()
                                        .filter(new LanguageInfo$$ExternalSyntheticLambda0(this, 1))
                                        .collect(Collectors.toList());
        Iterator it = list2.iterator();
        while (true) {
            int i2 = 0;
            if (!it.hasNext()) {
                return false;
            }
            int i3 = ((PackageInfo) it.next()).mType;
            if (i3 == 1 || i3 == 2 || i3 == 4 || i3 == 16) {
                break;
            }
            if (i3 == 8) {
                if (list3.size() < 2) {
                    return true;
                }
                for (LanguageInfo languageInfo : list3) {
                    if (!TextUtils.equals(this.mLanguageCode, languageInfo.mLanguageCode)) {
                        final int i4 = 1;
                        i2 +=
                                (int)
                                        languageInfo.mList.stream()
                                                .filter(
                                                        new LanguageInfo$$ExternalSyntheticLambda2(
                                                                0))
                                                .filter(
                                                        new Predicate() { // from class:
                                                                          // com.samsung.android.settings.languagepack.data.LanguageInfo$$ExternalSyntheticLambda1
                                                            @Override // java.util.function.Predicate
                                                            public final boolean test(Object obj) {
                                                                int i22 = i4;
                                                                Context context2 = context;
                                                                PackageInfo packageInfo =
                                                                        (PackageInfo) obj;
                                                                switch (i22) {
                                                                }
                                                                return packageInfo
                                                                        .isUnInstallablePackage(
                                                                                context2);
                                                            }
                                                        })
                                                .count();
                    }
                }
                if (i2 == 0 && this.mIsStandardTranslationLanguage) {
                    break;
                }
            }
        }
        return true;
    }

    public final boolean checkDownloadedWithType(Context context, int i, List list) {
        if (i == 0) {
            return checkDownloadedInSettings(context, list);
        }
        Iterator it = this.mList.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            PackageInfo packageInfo = (PackageInfo) it.next();
            if (packageInfo.isPackageInstalled(context)) {
                int i3 = packageInfo.mType;
                i2 = (i3 == 2 || i3 == 4) ? i2 | 6 : i2 | i3;
            }
        }
        return (i & i2) == i;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return getDisplayName().compareTo(((LanguageInfo) obj).getDisplayName());
    }

    public final String getDisplayName() {
        if ("zh-CN".equals(this.mLanguageCode)) {
            return Rune.SUPPORT_TEXT_REQUEST_REGION_CHINA_TO_CHINA_MAINLAND
                    ? this.mContext
                            .getResources()
                            .getString(R.string.langpack_name_chinese_china_mainland)
                    : this.mContext.getResources().getString(R.string.langpack_name_chinese_china);
        }
        if ("zh-HK".equals(this.mLanguageCode)) {
            return Rune.SUPPORT_TEXT_REQUEST_REGION_HONGKONG_TO_CHINA
                    ? this.mContext
                            .getResources()
                            .getString(R.string.langpack_name_chinese_hongkong_china)
                    : this.mContext
                            .getResources()
                            .getString(R.string.langpack_name_chinese_hongkong);
        }
        if ("zh-TW".equals(this.mLanguageCode)) {
            return Rune.SUPPORT_TEXT_REQUEST_REGION_TAIWAN_TO_CHINA
                    ? this.mContext
                            .getResources()
                            .getString(R.string.langpack_name_chinese_taiwan_china)
                    : this.mContext.getResources().getString(R.string.langpack_name_chinese_taiwan);
        }
        Locale forLanguageTag = Locale.forLanguageTag(this.mLanguageCode);
        String displayName =
                this.mShowLocale
                        ? forLanguageTag.getDisplayName()
                        : forLanguageTag.getDisplayLanguage();
        return TextUtils.isEmpty(displayName) ? this.mLanguageDisplayName : displayName;
    }

    public final String getTranslationPackageName() {
        PackageInfo packageInfo =
                (PackageInfo)
                        this.mList.stream()
                                .filter(new LanguageInfo$$ExternalSyntheticLambda2(6))
                                .findFirst()
                                .orElse(null);
        if (packageInfo != null) {
            return packageInfo.mPkgName;
        }
        return null;
    }

    public final boolean hasUninstallablePackage(Context context, int i) {
        boolean z;
        if (i == 0) {
            i = 31;
        }
        Iterator it = this.mList.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            PackageInfo packageInfo = (PackageInfo) it.next();
            if (packageInfo.isUnInstallablePackage(context) && (packageInfo.mType & i) > 0) {
                z = true;
                break;
            }
        }
        Log.d("LanguageInfo", "hasUninstallablePackage() : " + z);
        return z;
    }

    public final boolean isAllPackageInstalled(Context context) {
        Iterator it = this.mList.iterator();
        while (it.hasNext()) {
            PackageInfo packageInfo = (PackageInfo) it.next();
            if (packageInfo.mType == 1 && packageInfo.needToUpdate(context)) {
                return false;
            }
            int i = packageInfo.mType;
            if (i == 2 && packageInfo.needToUpdate(context)) {
                return false;
            }
            if (i == 4 && packageInfo.needToUpdate(context)) {
                return false;
            }
            if (i == 8 && packageInfo.needToUpdate(context)) {
                return false;
            }
            if (i == 16 && packageInfo.needToUpdate(context)) {
                return false;
            }
        }
        return true;
    }

    public final boolean showItemOnListWithType(int i) {
        boolean z = i == 0 || (this.mSupportType & i) == i;
        Log.d(
                "LanguageInfo",
                "availableListItemWithType() : "
                        + this.mLanguageCode
                        + " : "
                        + Integer.toBinaryString(i)
                        + " : "
                        + Integer.toBinaryString(this.mSupportType)
                        + " : "
                        + z);
        return z;
    }
}
