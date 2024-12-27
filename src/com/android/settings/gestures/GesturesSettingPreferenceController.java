package com.android.settings.gestures;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.AmbientDisplayConfiguration;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class GesturesSettingPreferenceController extends BasePreferenceController {
    private static final String FAKE_PREF_KEY = "fake_key_only_for_get_available";
    private List<AbstractPreferenceController> mGestureControllers;

    public GesturesSettingPreferenceController(Context context, String str) {
        super(context, str);
    }

    private static List<AbstractPreferenceController> buildAllPreferenceControllers(
            Context context) {
        AmbientDisplayConfiguration ambientDisplayConfiguration =
                new AmbientDisplayConfiguration(context);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new SwipeToNotificationPreferenceController(context, FAKE_PREF_KEY));
        arrayList.add(new DoubleTwistPreferenceController(context, FAKE_PREF_KEY));
        arrayList.add(new DoubleTapPowerPreferenceController(context, FAKE_PREF_KEY));
        arrayList.add(
                new PickupGesturePreferenceController(context, FAKE_PREF_KEY)
                        .setConfig(ambientDisplayConfiguration));
        arrayList.add(
                new DoubleTapScreenPreferenceController(context, FAKE_PREF_KEY)
                        .setConfig(ambientDisplayConfiguration));
        arrayList.add(new PreventRingingParentPreferenceController(context, FAKE_PREF_KEY));
        return arrayList;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        boolean z;
        if (this.mGestureControllers == null) {
            this.mGestureControllers = buildAllPreferenceControllers(this.mContext);
        }
        loop0:
        while (true) {
            for (AbstractPreferenceController abstractPreferenceController :
                    this.mGestureControllers) {
                z = z || abstractPreferenceController.isAvailable();
            }
        }
        return z ? 0 : 3;
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
