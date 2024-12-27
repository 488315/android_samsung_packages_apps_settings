package com.android.settings.inputmethod;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.input.InputDeviceIdentifier;
import android.hardware.input.InputManager;
import android.hardware.input.KeyboardLayout;
import android.hardware.input.KeyboardLayoutSelectionResult;
import android.os.Bundle;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.TickButtonPreference;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NewKeyboardLayoutPickerController extends BasePreferenceController
        implements InputManager.InputDeviceListener, LifecycleObserver, OnStart, OnStop {
    private String mFinalSelectedLayout;
    private final InputManager mIm;
    private int mInputDeviceId;
    private InputDeviceIdentifier mInputDeviceIdentifier;
    private InputMethodInfo mInputMethodInfo;
    private InputMethodSubtype mInputMethodSubtype;
    private KeyboardLayoutSelectedCallback mKeyboardLayoutSelectedCallback;
    private KeyboardLayout[] mKeyboardLayouts;
    private String mLayout;
    private MetricsFeatureProvider mMetricsFeatureProvider;
    private Fragment mParent;
    private final Map<TickButtonPreference, KeyboardLayout> mPreferenceMap;
    private String mPreviousSelection;
    private PreferenceScreen mScreen;
    private CharSequence mTitle;
    private int mUserId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface KeyboardLayoutSelectedCallback {}

    public NewKeyboardLayoutPickerController(Context context, String str) {
        super(context, str);
        this.mIm = (InputManager) context.getSystemService(InputManager.class);
        this.mInputDeviceId = -1;
        this.mPreferenceMap = new HashMap();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    private void createPreferenceHierarchy() {
        KeyboardLayout[] keyboardLayoutArr = this.mKeyboardLayouts;
        if (keyboardLayoutArr == null) {
            return;
        }
        for (KeyboardLayout keyboardLayout : keyboardLayoutArr) {
            TickButtonPreference tickButtonPreference =
                    new TickButtonPreference(this.mScreen.getContext());
            tickButtonPreference.setTitle(keyboardLayout.getLabel());
            if (this.mLayout.equals(keyboardLayout.getLabel())) {
                KeyboardLayoutSelectedCallback keyboardLayoutSelectedCallback =
                        this.mKeyboardLayoutSelectedCallback;
                if (keyboardLayoutSelectedCallback != null) {
                    ((NewKeyboardLayoutPickerFragment.AnonymousClass1)
                                    keyboardLayoutSelectedCallback)
                            .onSelected(keyboardLayout);
                }
                tickButtonPreference.setSelected(true);
                this.mPreviousSelection = keyboardLayout.getDescriptor();
            }
            tickButtonPreference.setKey(keyboardLayout.getDescriptor());
            this.mScreen.addPreference(tickButtonPreference);
            this.mPreferenceMap.put(tickButtonPreference, keyboardLayout);
        }
    }

    private String getSelectedLayoutLabel() {
        String string = this.mContext.getString(R.string.keyboard_default_layout);
        KeyboardLayoutSelectionResult keyboardLayoutForInputDevice =
                this.mIm.getKeyboardLayoutForInputDevice(
                        this.mInputDeviceIdentifier,
                        this.mUserId,
                        this.mInputMethodInfo,
                        this.mInputMethodSubtype);
        KeyboardLayout[] keyboardLayoutListForInputDevice =
                this.mIm.getKeyboardLayoutListForInputDevice(
                        this.mInputDeviceIdentifier,
                        this.mUserId,
                        this.mInputMethodInfo,
                        this.mInputMethodSubtype);
        if (keyboardLayoutForInputDevice.getLayoutDescriptor() == null) {
            return string;
        }
        for (KeyboardLayout keyboardLayout : keyboardLayoutListForInputDevice) {
            if (keyboardLayout
                    .getDescriptor()
                    .equals(keyboardLayoutForInputDevice.getLayoutDescriptor())) {
                return keyboardLayout.getLabel();
            }
        }
        return string;
    }

    private void setLayout(TickButtonPreference tickButtonPreference) {
        this.mIm.setKeyboardLayoutForInputDevice(
                this.mInputDeviceIdentifier,
                this.mUserId,
                this.mInputMethodInfo,
                this.mInputMethodSubtype,
                this.mPreferenceMap.get(tickButtonPreference).getDescriptor());
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
        if (!(preference instanceof TickButtonPreference)) {
            return false;
        }
        TickButtonPreference tickButtonPreference = (TickButtonPreference) preference;
        if (this.mKeyboardLayoutSelectedCallback != null
                && this.mPreferenceMap.containsKey(preference)) {
            ((NewKeyboardLayoutPickerFragment.AnonymousClass1) this.mKeyboardLayoutSelectedCallback)
                    .onSelected(this.mPreferenceMap.get(preference));
        }
        tickButtonPreference.setSelected(true);
        String str = this.mPreviousSelection;
        if (str != null && !str.equals(preference.getKey())) {
            ((TickButtonPreference) this.mScreen.findPreference(this.mPreviousSelection))
                    .setSelected(false);
        }
        setLayout(tickButtonPreference);
        this.mPreviousSelection = preference.getKey();
        this.mFinalSelectedLayout = tickButtonPreference.getTitle().toString();
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void initialize(Fragment fragment) {
        this.mParent = fragment;
        Bundle arguments = fragment.getArguments();
        this.mTitle = arguments.getCharSequence("keyboard_layout_picker_title");
        this.mUserId = arguments.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID);
        this.mInputDeviceIdentifier = arguments.getParcelable("input_device_identifier");
        this.mInputMethodInfo = (InputMethodInfo) arguments.getParcelable("input_method_info");
        this.mInputMethodSubtype =
                (InputMethodSubtype) arguments.getParcelable("input_method_subtype");
        String selectedLayoutLabel = getSelectedLayoutLabel();
        this.mLayout = selectedLayoutLabel;
        this.mFinalSelectedLayout = selectedLayoutLabel;
        KeyboardLayout[] keyboardLayoutListForInputDevice =
                this.mIm.getKeyboardLayoutListForInputDevice(
                        this.mInputDeviceIdentifier,
                        this.mUserId,
                        this.mInputMethodInfo,
                        this.mInputMethodSubtype);
        this.mKeyboardLayouts = keyboardLayoutListForInputDevice;
        Arrays.sort(
                keyboardLayoutListForInputDevice,
                Comparator.comparing(new NewKeyboardSettingsUtils$$ExternalSyntheticLambda0()));
        fragment.getActivity().setTitle(this.mTitle);
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
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        String str = this.mLayout;
        if (str != null && !str.equals(this.mFinalSelectedLayout)) {
            this.mMetricsFeatureProvider.action(
                    this.mContext,
                    1840,
                    "From:" + this.mLayout + ", to:" + this.mFinalSelectedLayout);
        }
        this.mIm.unregisterInputDeviceListener(this);
        this.mInputDeviceId = -1;
    }

    public void registerKeyboardSelectedCallback(
            KeyboardLayoutSelectedCallback keyboardLayoutSelectedCallback) {
        this.mKeyboardLayoutSelectedCallback = keyboardLayoutSelectedCallback;
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

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceChanged(int i) {}
}
