package com.samsung.android.settings.display.controller;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.android.settingslib.core.AbstractPreferenceController;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SecDisplayPreferenceBaseController extends AbstractPreferenceController
        implements LifecycleObserver {
    public final AnonymousClass1 mBaseObserver;
    public final ContentResolver mContentResolver;
    public final String mPreferenceKey;
    public ArrayList mUriList;

    /* JADX WARN: Type inference failed for: r3v1, types: [com.samsung.android.settings.display.controller.SecDisplayPreferenceBaseController$1] */
    public SecDisplayPreferenceBaseController(Context context, String str, Lifecycle lifecycle) {
        super(context);
        this.mBaseObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.display.controller.SecDisplayPreferenceBaseController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        SecDisplayPreferenceBaseController.this.updatePreference$11();
                    }
                };
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
        this.mPreferenceKey = str;
        this.mContentResolver = this.mContext.getContentResolver();
    }

    public abstract ArrayList getUriListRequiringObservation();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        updatePreference$11();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        ArrayList uriListRequiringObservation = getUriListRequiringObservation();
        this.mUriList = uriListRequiringObservation;
        if (uriListRequiringObservation == null) {
            String str = this.mPreferenceKey;
            if (str == null) {
                str = "null";
            }
            Log.e(
                    "SecDisplayPreferenceBaseController",
                    "registerObserver() mUriList is null - key = ".concat(str));
            return;
        }
        Iterator it = uriListRequiringObservation.iterator();
        while (it.hasNext()) {
            this.mContentResolver.registerContentObserver(
                    (Uri) it.next(), false, this.mBaseObserver);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        if (this.mUriList != null) {
            this.mContentResolver.unregisterContentObserver(this.mBaseObserver);
        }
    }

    public abstract void updatePreference$11();

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {}
}
