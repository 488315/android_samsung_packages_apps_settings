package com.android.settings.inputmethod;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.input.InputDeviceIdentifier;
import android.hardware.input.InputManager;
import android.hardware.input.KeyboardLayout;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;
import androidx.preference.TwoStatePreference;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class KeyboardLayoutPickerController extends BasePreferenceController
        implements InputManager.InputDeviceListener, LifecycleObserver, OnStart, OnStop {
    private final InputManager mIm;
    private int mInputDeviceId;
    private InputDeviceIdentifier mInputDeviceIdentifier;
    private KeyboardLayout[] mKeyboardLayouts;
    private Fragment mParent;
    private final Map<TwoStatePreference, KeyboardLayout> mPreferenceMap;
    private PreferenceScreen mScreen;

    public KeyboardLayoutPickerController(Context context, String str) {
        super(context, str);
        this.mIm = (InputManager) context.getSystemService("input");
        this.mInputDeviceId = -1;
        this.mPreferenceMap = new HashMap();
    }

    private void createPreferenceHierarchy() {
        KeyboardLayout[] keyboardLayoutArr = this.mKeyboardLayouts;
        if (keyboardLayoutArr == null) {
            return;
        }
        for (KeyboardLayout keyboardLayout : keyboardLayoutArr) {
            SwitchPreferenceCompat switchPreferenceCompat =
                    new SwitchPreferenceCompat(this.mScreen.getContext());
            switchPreferenceCompat.setTitle(keyboardLayout.getLabel());
            switchPreferenceCompat.setSummary(keyboardLayout.getCollection());
            switchPreferenceCompat.setKey(keyboardLayout.getDescriptor());
            this.mScreen.addPreference(switchPreferenceCompat);
            this.mPreferenceMap.put(switchPreferenceCompat, keyboardLayout);
        }
    }

    private void updateCheckedState() {
        String[] enabledKeyboardLayoutsForInputDevice =
                this.mIm.getEnabledKeyboardLayoutsForInputDevice(this.mInputDeviceIdentifier);
        Arrays.sort(enabledKeyboardLayoutsForInputDevice);
        for (Map.Entry<TwoStatePreference, KeyboardLayout> entry : this.mPreferenceMap.entrySet()) {
            entry.getKey()
                    .setChecked(
                            Arrays.binarySearch(
                                            enabledKeyboardLayoutsForInputDevice,
                                            entry.getValue().getDescriptor())
                                    >= 0);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mScreen = preferenceScreen;
        createPreferenceHierarchy();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!(preference instanceof TwoStatePreference)) {
            return false;
        }
        TwoStatePreference twoStatePreference = (TwoStatePreference) preference;
        KeyboardLayout keyboardLayout = this.mPreferenceMap.get(twoStatePreference);
        if (keyboardLayout == null) {
            return true;
        }
        if (twoStatePreference.isChecked()) {
            this.mIm.addKeyboardLayoutForInputDevice(
                    this.mInputDeviceIdentifier, keyboardLayout.getDescriptor());
            return true;
        }
        this.mIm.removeKeyboardLayoutForInputDevice(
                this.mInputDeviceIdentifier, keyboardLayout.getDescriptor());
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void initialize(Fragment fragment, InputDeviceIdentifier inputDeviceIdentifier) {
        this.mParent = fragment;
        this.mInputDeviceIdentifier = inputDeviceIdentifier;
        KeyboardLayout[] keyboardLayoutsForInputDevice =
                this.mIm.getKeyboardLayoutsForInputDevice(inputDeviceIdentifier);
        this.mKeyboardLayouts = keyboardLayoutsForInputDevice;
        Arrays.sort(keyboardLayoutsForInputDevice);
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

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceChanged(int i) {
        int i2 = this.mInputDeviceId;
        if (i2 < 0 || i != i2) {
            return;
        }
        updateCheckedState();
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceRemoved(int i) {
        int i2 = this.mInputDeviceId;
        if (i2 < 0 || i != i2) {
            return;
        }
        this.mParent.getActivity().finish();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mIm.registerInputDeviceListener(this, null);
        InputDeviceIdentifier inputDeviceIdentifier = this.mInputDeviceIdentifier;
        if (inputDeviceIdentifier == null
                || NewKeyboardSettingsUtils.getInputDevice(this.mIm, inputDeviceIdentifier)
                        == null) {
            return;
        }
        this.mInputDeviceId =
                NewKeyboardSettingsUtils.getInputDevice(this.mIm, this.mInputDeviceIdentifier)
                        .getId();
        updateCheckedState();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mIm.unregisterInputDeviceListener(this);
        this.mInputDeviceId = -1;
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

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceAdded(int i) {}
}
