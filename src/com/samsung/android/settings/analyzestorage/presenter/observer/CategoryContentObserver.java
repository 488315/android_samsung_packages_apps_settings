package com.samsung.android.settings.analyzestorage.presenter.observer;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.samsung.android.settings.analyzestorage.domain.log.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class CategoryContentObserver
        implements SharedPreferences.OnSharedPreferenceChangeListener {
    public Context mContext;
    public final Handler mHandler =
            new Handler(
                    new Handler
                            .Callback() { // from class:
                                          // com.samsung.android.settings.analyzestorage.presenter.observer.AbsContentObserver$$ExternalSyntheticLambda0
                        @Override // android.os.Handler.Callback
                        public final boolean handleMessage(Message message) {
                            CategoryContentObserver categoryContentObserver =
                                    CategoryContentObserver.this;
                            if (categoryContentObserver.mUpdaterListener == null) {
                                return true;
                            }
                            Log.i("CategoryContentObserver", "contentChanged");
                            categoryContentObserver.mUpdaterListener.onContentChanged();
                            return true;
                        }
                    });
    public final MediaProviderObserver mMediaProviderObserver;
    public SharedPreferences mSharedPreferences;
    public IContentObserver mUpdaterListener;

    public CategoryContentObserver(Context context, IContentObserver iContentObserver) {
        this.mUpdaterListener = iContentObserver;
        this.mContext = context.getApplicationContext();
        this.mMediaProviderObserver =
                new MediaProviderObserver(context, new Handler(Looper.getMainLooper()));
    }

    @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
    public final void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
        if ("show_hidden_files_pref_key".equals(str)) {
            Log.d("AbsContentObserver", "onSharedPreferenceChanged() - Show Hidden state changed");
            if (this.mUpdaterListener != null) {
                Log.i("CategoryContentObserver", "contentChanged");
                this.mUpdaterListener.onContentChanged();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MediaProviderObserver extends ContentObserver {
        public ContentResolver mContentResolver;

        public MediaProviderObserver(Context context, Handler handler) {
            super(handler);
            this.mContentResolver = context.getContentResolver();
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            StringBuilder sb = new StringBuilder("onChange uri ");
            sb.append(uri != null ? uri.toString() : "uri is null");
            Log.d("MediaProviderObserver", sb.toString());
            onChange(z);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            CategoryContentObserver categoryContentObserver = CategoryContentObserver.this;
            if (categoryContentObserver.mHandler.hasMessages(1)) {
                categoryContentObserver.mHandler.removeMessages(1);
            }
            categoryContentObserver.mHandler.sendEmptyMessageDelayed(1, 1500L);
        }
    }
}
