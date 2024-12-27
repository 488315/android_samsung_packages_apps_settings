package com.android.settings.notification;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.Spatializer;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SpatialAudioParentPreferenceController extends BasePreferenceController {
    private SpatialAudioPreferenceController mSpatialAudioPreferenceController;
    private SpatialAudioWiredHeadphonesController mSpatialAudioWiredHeadphonesController;
    private final Spatializer mSpatializer;
    private static final String TAG = "SpatialAudioSetting";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);

    public SpatialAudioParentPreferenceController(Context context, String str) {
        super(context, str);
        this.mSpatializer =
                ((AudioManager) context.getSystemService(AudioManager.class)).getSpatializer();
        this.mSpatialAudioPreferenceController =
                new SpatialAudioPreferenceController(context, "unused");
        this.mSpatialAudioWiredHeadphonesController =
                new SpatialAudioWiredHeadphonesController(context, "unused");
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        int immersiveAudioLevel = this.mSpatializer.getImmersiveAudioLevel();
        if (DEBUG) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    immersiveAudioLevel, "spatialization level: ", TAG);
        }
        return immersiveAudioLevel == 0 ? 3 : 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        boolean z = false;
        boolean z2 =
                this.mSpatialAudioPreferenceController.isAvailable()
                        && this.mSpatialAudioPreferenceController.getThreadEnabled();
        if (this.mSpatialAudioWiredHeadphonesController.isAvailable()
                && this.mSpatialAudioWiredHeadphonesController.getThreadEnabled()) {
            z = true;
        }
        if (z2 && z) {
            Context context = this.mContext;
            return context.getString(
                    R.string.spatial_summary_on_two,
                    context.getString(R.string.spatial_audio_speaker),
                    this.mContext.getString(R.string.spatial_audio_wired_headphones));
        }
        if (z2) {
            Context context2 = this.mContext;
            return context2.getString(
                    R.string.spatial_summary_on_one,
                    context2.getString(R.string.spatial_audio_speaker));
        }
        if (!z) {
            return this.mContext.getString(R.string.spatial_summary_off);
        }
        Context context3 = this.mContext;
        return context3.getString(
                R.string.spatial_summary_on_one,
                context3.getString(R.string.spatial_audio_wired_headphones));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
