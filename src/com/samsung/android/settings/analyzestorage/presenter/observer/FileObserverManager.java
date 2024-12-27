package com.samsung.android.settings.analyzestorage.presenter.observer;

import android.content.Context;

import androidx.core.view.DifferentialMotionFlingController$$ExternalSyntheticLambda0;

import com.samsung.android.settings.analyzestorage.presenter.page.PageType;

import java.util.EnumMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class FileObserverManager {
    public static final EnumMap FILE_OBSERVER_MAPPER;
    public IContentObserver mContentObserver;
    public final Context mContext;
    public PageType mPageType;
    public CategoryContentObserver mStorageObserver;

    static {
        EnumMap enumMap = new EnumMap(PageType.class);
        FILE_OBSERVER_MAPPER = enumMap;
        DifferentialMotionFlingController$$ExternalSyntheticLambda0
                differentialMotionFlingController$$ExternalSyntheticLambda0 =
                        new DifferentialMotionFlingController$$ExternalSyntheticLambda0();
        enumMap.put(
                (EnumMap) PageType.ANALYZE_STORAGE_HOME,
                (PageType) differentialMotionFlingController$$ExternalSyntheticLambda0);
        enumMap.put(
                (EnumMap) PageType.ANALYZE_STORAGE_DUPLICATED_FILES,
                (PageType) differentialMotionFlingController$$ExternalSyntheticLambda0);
        enumMap.put(
                (EnumMap) PageType.ANALYZE_STORAGE_LARGE_FILES,
                (PageType) differentialMotionFlingController$$ExternalSyntheticLambda0);
        enumMap.put(
                (EnumMap) PageType.ANALYZE_STORAGE_CACHED_FILES,
                (PageType) differentialMotionFlingController$$ExternalSyntheticLambda0);
    }

    public FileObserverManager(Context context, IContentObserver iContentObserver) {
        this.mContext = context;
        this.mContentObserver = iContentObserver;
    }
}
