package com.android.settings.display;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ContrastSelectorPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop, UiModeManager.ContrastChangeListener {
    private static final String KEY_COLOR_CONTRAST_SELECTOR = "color_contrast_selector";
    private Map<Integer, LinearLayout> mContrastButtons;
    private final Executor mMainExecutor;
    private final UiModeManager mUiModeManager;

    public ContrastSelectorPreferenceController(Context context, String str) {
        super(context, str);
        this.mContrastButtons = new HashMap();
        this.mMainExecutor = this.mContext.getMainExecutor();
        this.mUiModeManager = (UiModeManager) this.mContext.getSystemService(UiModeManager.class);
    }

    private void highlightContrast(final int i) {
        this.mContrastButtons.forEach(
                new BiConsumer() { // from class:
                                   // com.android.settings.display.ContrastSelectorPreferenceController$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ContrastSelectorPreferenceController.lambda$highlightContrast$1(
                                i, (Integer) obj, (LinearLayout) obj2);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$displayPreference$0(
            final Integer num, LinearLayout linearLayout) {
        linearLayout.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.display.ContrastSelectorPreferenceController.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        Settings.Secure.putFloat(
                                ((AbstractPreferenceController)
                                                ContrastSelectorPreferenceController.this)
                                        .mContext.getContentResolver(),
                                "contrast_level",
                                UiModeManager.ContrastUtils.fromContrastLevel(num.intValue()));
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$highlightContrast$1(
            int i, Integer num, LinearLayout linearLayout) {
        linearLayout.setSelected(num.intValue() == i);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference(KEY_COLOR_CONTRAST_SELECTOR);
        Map<Integer, LinearLayout> ofEntries =
                Map.ofEntries(
                        Map.entry(
                                0,
                                (LinearLayout)
                                        layoutPreference.mRootView.findViewById(
                                                R.id.contrast_button_default)),
                        Map.entry(
                                1,
                                (LinearLayout)
                                        layoutPreference.mRootView.findViewById(
                                                R.id.contrast_button_medium)),
                        Map.entry(
                                2,
                                (LinearLayout)
                                        layoutPreference.mRootView.findViewById(
                                                R.id.contrast_button_high)));
        this.mContrastButtons = ofEntries;
        ofEntries.forEach(
                new BiConsumer() { // from class:
                                   // com.android.settings.display.ContrastSelectorPreferenceController$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ContrastSelectorPreferenceController.this.lambda$displayPreference$0(
                                (Integer) obj, (LinearLayout) obj2);
                    }
                });
        highlightContrast(
                UiModeManager.ContrastUtils.toContrastLevel(this.mUiModeManager.getContrast()));
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

    @Override // android.app.UiModeManager.ContrastChangeListener
    public void onContrastChanged(float f) {
        highlightContrast(UiModeManager.ContrastUtils.toContrastLevel(f));
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mUiModeManager.addContrastChangeListener(this.mMainExecutor, this);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mUiModeManager.removeContrastChangeListener(this);
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
