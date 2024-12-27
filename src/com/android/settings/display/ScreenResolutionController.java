package com.android.settings.display;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.util.Log;
import android.view.Display;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ScreenResolutionController extends BasePreferenceController {
    static final int FULLRESOLUTION_IDX = 1;
    static final int HIGHRESOLUTION_IDX = 0;
    private static final String TAG = "ScreenResolutionController";
    private Display mDisplay;
    private int mFullHeight;
    private int mFullWidth;
    private int mHighHeight;
    private int mHighWidth;
    private Set<Point> mSupportedResolutions;

    public ScreenResolutionController(Context context, String str) {
        super(context, str);
        this.mSupportedResolutions = null;
        this.mHighWidth = 0;
        this.mFullWidth = 0;
        this.mHighHeight = 0;
        this.mFullHeight = 0;
        this.mDisplay =
                ((DisplayManager) this.mContext.getSystemService(DisplayManager.class))
                        .getDisplay(0);
        initSupportedResolutionData();
    }

    private void initSupportedResolutionData() {
        HashSet hashSet = new HashSet();
        for (Display.Mode mode : getSupportedModes()) {
            hashSet.add(new Point(mode.getPhysicalWidth(), mode.getPhysicalHeight()));
        }
        this.mSupportedResolutions = hashSet;
        ArrayList arrayList = new ArrayList(hashSet);
        if (arrayList.size() != 2) {
            Log.e(TAG, "No support");
            return;
        }
        Collections.sort(arrayList, new ScreenResolutionController$$ExternalSyntheticLambda0());
        this.mHighWidth = ((Point) arrayList.get(0)).x;
        this.mHighHeight = ((Point) arrayList.get(0)).y;
        this.mFullWidth = ((Point) arrayList.get(1)).x;
        this.mFullHeight = ((Point) arrayList.get(1)).y;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$initSupportedResolutionData$0(
            Point point, Point point2) {
        return (point.x * point.y) - (point2.x * point2.y);
    }

    public boolean checkSupportedResolutions() {
        return (getHighWidth() == 0 || getFullWidth() == 0) ? false : true;
    }

    public Set<Point> getAllSupportedResolutions() {
        return this.mSupportedResolutions;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return checkSupportedResolutions() ? 0 : 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public int getDisplayWidth() {
        return this.mDisplay.getMode().getPhysicalWidth();
    }

    public int getFullHeight() {
        return this.mFullHeight;
    }

    public int getFullWidth() {
        return this.mFullWidth;
    }

    public int getHighHeight() {
        return this.mHighHeight;
    }

    public int getHighWidth() {
        return this.mHighWidth;
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
        int displayWidth = getDisplayWidth();
        return displayWidth == this.mHighWidth
                ? this.mContext.getString(R.string.screen_resolution_option_high)
                : displayWidth == this.mFullWidth
                        ? this.mContext.getString(R.string.screen_resolution_option_full)
                        : this.mContext.getString(R.string.screen_resolution_title);
    }

    public Display.Mode[] getSupportedModes() {
        return this.mDisplay.getSupportedModes();
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
