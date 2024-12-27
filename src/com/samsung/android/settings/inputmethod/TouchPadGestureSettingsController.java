package com.samsung.android.settings.inputmethod;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.input.InputManager;
import android.os.SystemProperties;
import android.view.InputDevice;

import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TouchPadGestureSettingsController extends BasePreferenceController {
    public static final String FIRST_API_LEVEL = "ro.product.first_api_level";
    private static final String KEY_TOUCHPAD_GESTURES = "key_touchpad_gestures";
    private static final String TAG = "TouchPadGestureSettingsController";

    public TouchPadGestureSettingsController(Context context, String str) {
        super(context, KEY_TOUCHPAD_GESTURES);
    }

    public static boolean isPogoKeyboardConnected(Context context) {
        InputManager inputManager = (InputManager) context.getSystemService("input");
        int[] inputDeviceIds = inputManager.getInputDeviceIds();
        int length = inputDeviceIds.length;
        boolean z = false;
        int i = 0;
        while (true) {
            if (i < length) {
                InputDevice inputDevice = inputManager.getInputDevice(inputDeviceIds[i]);
                if (inputDevice != null && "sec_touchpad_pogo".equals(inputDevice.getName())) {
                    z = true;
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        SemLog.i(TAG, "POGO Keyboard is ".concat(z ? "connected" : "not connected"));
        return z;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (Utils.isTablet()
                        && isPogoKeyboardConnected(this.mContext)
                        && SystemProperties.getInt(FIRST_API_LEVEL, -1) >= 29)
                ? 0
                : 3;
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
