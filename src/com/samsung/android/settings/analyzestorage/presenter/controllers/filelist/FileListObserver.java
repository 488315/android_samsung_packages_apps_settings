package com.samsung.android.settings.analyzestorage.presenter.controllers.filelist;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;

import androidx.core.view.DifferentialMotionFlingController$$ExternalSyntheticLambda0;
import androidx.preference.PreferenceManager;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.PageControllerInterface;
import com.samsung.android.settings.analyzestorage.presenter.observer.CategoryContentObserver;
import com.samsung.android.settings.analyzestorage.presenter.observer.FileObserverManager;
import com.samsung.android.settings.analyzestorage.presenter.observer.IContentObserver;
import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;

import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class FileListObserver implements IContentObserver {
    public IContentObserver mContentObserver;
    public final WeakReference mContext;
    public final WeakReference mControllerWeakReference;
    public FileObserverManager mFileObserver;
    public boolean mIsPaused;
    public boolean mNeedRefresh;
    public final AnonymousClass1 mObserverHandler =
            new Handler(
                    Looper
                            .getMainLooper()) { // from class:
                                                // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.FileListObserver.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    String str = (String) message.obj;
                    FileListObserver fileListObserver = FileListObserver.this;
                    fileListObserver.getClass();
                    Log.i("FileListObserver", "startObserver() ] path = " + Log.getEncodedMsg(str));
                    if (fileListObserver.mContext != null) {
                        fileListObserver.mFileObserver =
                                new FileObserverManager(
                                        (Context) fileListObserver.mContext.get(),
                                        fileListObserver);
                        if (fileListObserver.mControllerWeakReference.get() == null) {
                            Log.i(
                                    "FileListObserver",
                                    "startObserver() ] - mControllerWeakReference.get() is null");
                            return;
                        }
                        final FileObserverManager fileObserverManager =
                                fileListObserver.mFileObserver;
                        final PageInfo pageInfo =
                                ((AbsPageController)
                                                ((PageControllerInterface)
                                                        fileListObserver.mControllerWeakReference
                                                                .get()))
                                        .mPageInfo;
                        fileObserverManager.getClass();
                        if (pageInfo != null) {
                            PageType pageType = pageInfo.mPageType;
                            fileObserverManager.mPageType = pageType;
                            CategoryContentObserver categoryContentObserver =
                                    (CategoryContentObserver)
                                            Optional.ofNullable(
                                                            (DifferentialMotionFlingController$$ExternalSyntheticLambda0)
                                                                    FileObserverManager
                                                                            .FILE_OBSERVER_MAPPER
                                                                            .get(pageType))
                                                    .map(
                                                            new Function(
                                                                    pageInfo) { // from class:
                                                                                // com.samsung.android.settings.analyzestorage.presenter.observer.FileObserverManager$$ExternalSyntheticLambda0
                                                                @Override // java.util.function.Function
                                                                public final Object apply(
                                                                        Object obj) {
                                                                    FileObserverManager
                                                                            fileObserverManager2 =
                                                                                    FileObserverManager
                                                                                            .this;
                                                                    Context context =
                                                                            fileObserverManager2
                                                                                    .mContext;
                                                                    IContentObserver
                                                                            iContentObserver =
                                                                                    fileObserverManager2
                                                                                            .mContentObserver;
                                                                    ((DifferentialMotionFlingController$$ExternalSyntheticLambda0)
                                                                                    obj)
                                                                            .getClass();
                                                                    return new CategoryContentObserver(
                                                                            context,
                                                                            iContentObserver);
                                                                }
                                                            })
                                                    .orElse(null);
                            fileObserverManager.mStorageObserver = categoryContentObserver;
                            if (categoryContentObserver != null) {
                                PageType pageType2 = fileObserverManager.mPageType;
                                if (categoryContentObserver.mSharedPreferences == null) {
                                    SharedPreferences defaultSharedPreferences =
                                            PreferenceManager.getDefaultSharedPreferences(
                                                    categoryContentObserver.mContext);
                                    categoryContentObserver.mSharedPreferences =
                                            defaultSharedPreferences;
                                    defaultSharedPreferences
                                            .registerOnSharedPreferenceChangeListener(
                                                    categoryContentObserver);
                                }
                                CategoryContentObserver.MediaProviderObserver
                                        mediaProviderObserver =
                                                categoryContentObserver.mMediaProviderObserver;
                                mediaProviderObserver.getClass();
                                int ordinal = pageType2.ordinal();
                                try {
                                    mediaProviderObserver.mContentResolver.registerContentObserver(
                                            ordinal != 2
                                                    ? ordinal != 3
                                                            ? ordinal != 4
                                                                    ? MediaStore.Files
                                                                            .getContentUri(
                                                                                    "external")
                                                                    : MediaStore.Video.Media
                                                                            .EXTERNAL_CONTENT_URI
                                                            : MediaStore.Audio.Media
                                                                    .EXTERNAL_CONTENT_URI
                                                    : MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                            true,
                                            mediaProviderObserver);
                                    return;
                                } catch (SecurityException e) {
                                    e.printStackTrace();
                                    return;
                                }
                            }
                        }
                        fileListObserver.mFileObserver = null;
                    }
                }
            };

    /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.FileListObserver$1] */
    public FileListObserver(Context context, PageControllerInterface pageControllerInterface) {
        this.mContext = new WeakReference(context);
        this.mControllerWeakReference = new WeakReference(pageControllerInterface);
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.observer.IContentObserver
    public final void onContentChanged() {
        if (this.mIsPaused) {
            this.mNeedRefresh = true;
            return;
        }
        WeakReference weakReference = this.mControllerWeakReference;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        IContentObserver iContentObserver = this.mContentObserver;
        if (iContentObserver != null) {
            iContentObserver.onContentChanged();
        } else {
            ((PageControllerInterface) this.mControllerWeakReference.get()).refresh(true);
        }
    }

    public final void removeObserver() {
        FileObserverManager fileObserverManager = this.mFileObserver;
        if (fileObserverManager != null) {
            CategoryContentObserver categoryContentObserver = fileObserverManager.mStorageObserver;
            if (categoryContentObserver != null) {
                CategoryContentObserver.MediaProviderObserver mediaProviderObserver =
                        categoryContentObserver.mMediaProviderObserver;
                ContentResolver contentResolver = mediaProviderObserver.mContentResolver;
                if (contentResolver != null) {
                    contentResolver.unregisterContentObserver(mediaProviderObserver);
                    mediaProviderObserver.mContentResolver = null;
                }
                SharedPreferences sharedPreferences = categoryContentObserver.mSharedPreferences;
                if (sharedPreferences != null) {
                    sharedPreferences.unregisterOnSharedPreferenceChangeListener(
                            categoryContentObserver);
                }
                categoryContentObserver.mUpdaterListener = null;
                categoryContentObserver.mContext = null;
            }
            fileObserverManager.mContentObserver = null;
        }
        this.mFileObserver = null;
        this.mContentObserver = null;
    }
}
