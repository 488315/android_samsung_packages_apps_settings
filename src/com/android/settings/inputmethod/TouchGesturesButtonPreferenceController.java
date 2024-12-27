package com.android.settings.inputmethod;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.FeatureFlagUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.widget.ButtonPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TouchGesturesButtonPreferenceController extends BasePreferenceController {
    private static final String GESTURE_DIALOG_TAG = "GESTURE_DIALOG_TAG";
    private static final int ORDER_BOTTOM = 100;
    private static final int ORDER_TOP = 0;
    private static final String PREFERENCE_KEY = "trackpad_touch_gesture";
    private MetricsFeatureProvider mMetricsFeatureProvider;
    private Fragment mParent;

    public TouchGesturesButtonPreferenceController(Context context, String str) {
        super(context, str);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$displayPreference$0(View view) {
        showTouchpadGestureEducation();
    }

    private void showTouchpadGestureEducation() {
        this.mMetricsFeatureProvider.action(this.mContext, 1855, new Pair[0]);
        TrackpadGestureDialogFragment trackpadGestureDialogFragment =
                new TrackpadGestureDialogFragment();
        trackpadGestureDialogFragment.setTargetFragment(this.mParent, 0);
        trackpadGestureDialogFragment.show(
                this.mParent.getActivity().getSupportFragmentManager(), GESTURE_DIALOG_TAG);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        ButtonPreference buttonPreference =
                (ButtonPreference) preferenceScreen.findPreference(getPreferenceKey());
        boolean isEnabled =
                FeatureFlagUtils.isEnabled(this.mContext, "settings_new_keyboard_trackpad_gesture");
        if (getPreferenceKey().equals(PREFERENCE_KEY)) {
            if (isEnabled) {
                buttonPreference.setOrder(0);
            } else {
                buttonPreference.setOrder(100);
            }
        }
        View.OnClickListener onClickListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.inputmethod.TouchGesturesButtonPreferenceController$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        TouchGesturesButtonPreferenceController.this.lambda$displayPreference$0(
                                view);
                    }
                };
        buttonPreference.mClickListener = onClickListener;
        Button button = buttonPreference.mButton;
        if (button != null) {
            button.setOnClickListener(onClickListener);
        }
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

    public void setFragment(Fragment fragment) {
        this.mParent = fragment;
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
