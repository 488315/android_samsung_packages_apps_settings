package com.android.settings.slices;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.util.Log;

import androidx.collection.ArraySet;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.preference.PreferenceScreen;
import androidx.slice.Slice;
import androidx.slice.widget.SliceLiveData;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SlicePreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop, Observer {
    private static final String TAG = "SlicePreferenceController";
    LiveData mLiveData;
    SlicePreference mSlicePreference;
    private Uri mUri;

    public SlicePreferenceController(Context context, String str) {
        super(context, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setSliceUri$0(Uri uri, int i, Throwable th) {
        Log.w(TAG, "Slice may be null. uri = " + uri + ", error = " + i);
    }

    private void removeLiveDataObserver() {
        LiveData liveData = this.mLiveData;
        if (liveData == null) {
            return;
        }
        try {
            liveData.removeObserver(this);
        } catch (SecurityException unused) {
            Log.w(TAG, "removeLiveDataObserver - no permission");
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mSlicePreference =
                (SlicePreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mUri != null ? 0 : 3;
    }

    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        LiveData liveData = this.mLiveData;
        if (liveData == null) {
            return;
        }
        try {
            liveData.observeForever(this);
        } catch (SecurityException unused) {
            Log.w(TAG, "observeForever - no permission");
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        removeLiveDataObserver();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setSliceUri(final Uri uri) {
        this.mUri = uri;
        Context context = this.mContext;
        SliceLiveData.OnErrorListener onErrorListener =
                new SliceLiveData
                        .OnErrorListener() { // from class:
                                             // com.android.settings.slices.SlicePreferenceController$$ExternalSyntheticLambda0
                    @Override // androidx.slice.widget.SliceLiveData.OnErrorListener
                    public final void onSliceError(int i, Throwable th) {
                        SlicePreferenceController.lambda$setSliceUri$0(uri, i, th);
                    }
                };
        ArraySet arraySet = SliceLiveData.SUPPORTED_SPECS;
        this.mLiveData =
                new SliceLiveData.SliceLiveDataImpl(
                        context.getApplicationContext(), uri, onErrorListener);
        removeLiveDataObserver();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // androidx.lifecycle.Observer
    public void onChanged(Slice slice) {
        SlicePreference slicePreference = this.mSlicePreference;
        if (slice == null) {
            slicePreference.mSliceView.setVisibility(8);
        } else {
            slicePreference.mSliceView.setVisibility(0);
        }
        slicePreference.mSliceView.setSlice(slice);
        slicePreference.notifyChanged();
        Log.w(TAG, "Slice UI updated, uri: " + this.mUri + ", slice content: " + slice);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
