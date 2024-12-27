package com.samsung.android.settings.usefulfeature.motionandgestures;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.android.settings.MainClear$$ExternalSyntheticOutline0;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class SecMotionAndGestureBaseController extends TogglePreferenceController
        implements LifecycleObserver {
    private static final String TAG = "SecMotionAndGestureBaseController";
    private ContentObserver mBaseObserver;
    public ContentResolver mContentResolver;
    public String mPreferenceKey;
    private ArrayList<Uri> mUriList;

    public SecMotionAndGestureBaseController(Context context, String str) {
        super(context, str);
        this.mBaseObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        SecMotionAndGestureBaseController.this.updatePreference();
                    }
                };
        this.mPreferenceKey = str;
        this.mContentResolver = this.mContext.getContentResolver();
    }

    private void registerObserver() {
        ArrayList<Uri> uriListRequiringObservation = getUriListRequiringObservation();
        this.mUriList = uriListRequiringObservation;
        if (uriListRequiringObservation != null) {
            Iterator<Uri> it = uriListRequiringObservation.iterator();
            while (it.hasNext()) {
                this.mContentResolver.registerContentObserver(it.next(), false, this.mBaseObserver);
            }
            return;
        }
        StringBuilder sb = new StringBuilder("registerObserver() mUriList is null - key = ");
        String str = this.mPreferenceKey;
        if (str == null) {
            str = "null";
        }
        MainClear$$ExternalSyntheticOutline0.m(sb, str, TAG);
    }

    private void unRegisterObserver() {
        if (this.mUriList != null) {
            this.mContentResolver.unregisterContentObserver(this.mBaseObserver);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    public abstract ArrayList<Uri> getUriListRequiringObservation();

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        updatePreference();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        registerObserver();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        unRegisterObserver();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void startActivity(Intent intent) {
        Context context = this.mContext;
        if (context == null) {
            StringBuilder sb = new StringBuilder("startActivity() mContext is null - key = ");
            String str = this.mPreferenceKey;
            MainClear$$ExternalSyntheticOutline0.m(sb, str != null ? str : "null", TAG);
        } else if (intent == null) {
            StringBuilder sb2 = new StringBuilder("startActivity() intent is null - key = ");
            String str2 = this.mPreferenceKey;
            MainClear$$ExternalSyntheticOutline0.m(sb2, str2 != null ? str2 : "null", TAG);
        } else {
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void updatePreference();

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {}
}
