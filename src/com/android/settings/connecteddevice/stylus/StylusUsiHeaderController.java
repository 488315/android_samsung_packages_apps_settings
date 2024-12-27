package com.android.settings.connecteddevice.stylus;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.BatteryState;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.view.InputDevice;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnCreate;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class StylusUsiHeaderController extends BasePreferenceController
        implements InputManager.InputDeviceBatteryListener, LifecycleObserver, OnCreate, OnDestroy {
    private static final String KEY_STYLUS_USI_HEADER = "stylus_usi_header";
    private static final String TAG = "StylusUsiHeaderController";
    private LayoutPreference mHeaderPreference;
    private final InputDevice mInputDevice;
    private final InputManager mInputManager;

    public StylusUsiHeaderController(Context context, InputDevice inputDevice) {
        super(context, KEY_STYLUS_USI_HEADER);
        this.mInputDevice = inputDevice;
        this.mInputManager = (InputManager) context.getSystemService(InputManager.class);
    }

    private boolean isValidBatteryState(BatteryState batteryState) {
        return batteryState != null
                && batteryState.isPresent()
                && batteryState.getCapacity() > 0.0f;
    }

    private void refresh() {
        BatteryState batteryState = this.mInputDevice.getBatteryState();
        TextView textView =
                (TextView)
                        this.mHeaderPreference
                                .mRootView
                                .findViewById(R.id.entity_header)
                                .findViewById(R.id.entity_header_summary);
        if (!isValidBatteryState(batteryState)) {
            textView.setVisibility(4);
        } else {
            textView.setVisibility(0);
            textView.setText(NumberFormat.getPercentInstance().format(batteryState.getCapacity()));
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mHeaderPreference = layoutPreference;
        ((TextView)
                        layoutPreference
                                .mRootView
                                .findViewById(R.id.entity_header)
                                .findViewById(R.id.entity_header_title))
                .setText(R.string.stylus_connected_devices_title);
        ImageView imageView =
                (ImageView) this.mHeaderPreference.mRootView.findViewById(R.id.entity_header_icon);
        if (imageView != null) {
            imageView.setImageResource(R.drawable.ic_stylus);
            imageView.setContentDescription("Icon for stylus");
        }
        refresh();
        super.displayPreference(preferenceScreen);
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_STYLUS_USI_HEADER;
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

    public void onBatteryStateChanged(int i, long j, BatteryState batteryState) {
        refresh();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnCreate
    public void onCreate(Bundle bundle) {
        this.mInputManager.addInputDeviceBatteryListener(
                this.mInputDevice.getId(), this.mContext.getMainExecutor(), this);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public void onDestroy() {
        this.mInputManager.removeInputDeviceBatteryListener(this.mInputDevice.getId(), this);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        refresh();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
