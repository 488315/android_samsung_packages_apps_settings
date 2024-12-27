package com.android.settings.datetime.timezone.model;

import android.content.Context;
import android.os.Bundle;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.android.i18n.timezone.TimeZoneFinder;
import com.android.settingslib.utils.AsyncLoaderCompat;

import java.lang.ref.WeakReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TimeZoneDataLoader extends AsyncLoaderCompat {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnDataReadyCallback {
        void onTimeZoneDataReady(TimeZoneData timeZoneData);
    }

    @Override // androidx.loader.content.AsyncTaskLoader
    public final Object loadInBackground() {
        TimeZoneData timeZoneData;
        synchronized (TimeZoneData.class) {
            WeakReference weakReference = TimeZoneData.sCache;
            timeZoneData = weakReference == null ? null : (TimeZoneData) weakReference.get();
            if (timeZoneData == null) {
                timeZoneData =
                        new TimeZoneData(TimeZoneFinder.getInstance().getCountryZonesFinder());
                TimeZoneData.sCache = new WeakReference(timeZoneData);
            }
        }
        return timeZoneData;
    }

    @Override // com.android.settingslib.utils.AsyncLoaderCompat
    public final /* bridge */ /* synthetic */ void onDiscardResult(Object obj) {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LoaderCreator implements LoaderManager.LoaderCallbacks {
        public final OnDataReadyCallback mCallback;
        public final Context mContext;

        public LoaderCreator(Context context, OnDataReadyCallback onDataReadyCallback) {
            this.mContext = context;
            this.mCallback = onDataReadyCallback;
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final Loader onCreateLoader(int i, Bundle bundle) {
            return new TimeZoneDataLoader(this.mContext);
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final void onLoadFinished(Loader loader, Object obj) {
            TimeZoneData timeZoneData = (TimeZoneData) obj;
            OnDataReadyCallback onDataReadyCallback = this.mCallback;
            if (onDataReadyCallback != null) {
                onDataReadyCallback.onTimeZoneDataReady(timeZoneData);
            }
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final void onLoaderReset(Loader loader) {}
    }
}
