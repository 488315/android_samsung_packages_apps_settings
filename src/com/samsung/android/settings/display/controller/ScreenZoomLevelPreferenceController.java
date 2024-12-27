package com.samsung.android.settings.display.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.Signature;
import android.provider.Settings;

import com.android.settings.Utils;
import com.android.settings.core.SliderPreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.display.SecScreenZoomPreferenceFragment;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ScreenZoomLevelPreferenceController extends SliderPreferenceController {
    private static final String KEY_SCREEN_ZOON_LEVEL = "key_screen_zoom_level";
    private int[] mDensities;

    public ScreenZoomLevelPreferenceController(Context context) {
        super(context, KEY_SCREEN_ZOON_LEVEL);
        this.mDensities =
                SecDisplayUtils.getProperDensities(
                        this.mContext, SecScreenZoomPreferenceFragment.DENSITY_BASE_PIXEL);
    }

    private int getDensityByPosition(int i) {
        return this.mDensities[i];
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (Settings.System.getInt(this.mContext.getContentResolver(), "easy_mode_switch", 1) == 0
                || Rune.isSamsungDexMode(this.mContext)
                || Utils.isDesktopStandaloneMode(this.mContext)) {
            return 2;
        }
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        return 0;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getControlType() {
        return 100;
    }

    public int[] getDensities() {
        return this.mDensities;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getMax() {
        return this.mDensities.length - 1;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getMin() {
        return 0;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getSliderPosition() {
        int currentDensity = SecDisplayUtils.getCurrentDensity(this.mContext);
        int i = 0;
        while (true) {
            int[] iArr = this.mDensities;
            if (i >= iArr.length) {
                return 0;
            }
            if (iArr[i] == currentDensity) {
                return i;
            }
            i++;
        }
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public boolean setSliderPosition(int i) {
        if (i < 0 || i >= this.mDensities.length) {
            return false;
        }
        SecDisplayUtils.applyForcedDisplayDensity(-1, -1, getDensityByPosition(i));
        return true;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public ScreenZoomLevelPreferenceController(Context context, String str) {
        super(context, str);
        this.mDensities =
                SecDisplayUtils.getProperDensities(
                        this.mContext, SecScreenZoomPreferenceFragment.DENSITY_BASE_PIXEL);
    }
}
