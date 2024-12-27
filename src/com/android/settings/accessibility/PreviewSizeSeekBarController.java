package com.android.settings.accessibility;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.SeekBar;

import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.widget.LabeledSeekBarPreference;
import com.android.settings.widget.SeekBarPreference;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnCreate;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnSaveInstanceState;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
abstract class PreviewSizeSeekBarController extends BasePreferenceController
        implements TextReadingResetController.ResetStateListener,
                LifecycleObserver,
                OnCreate,
                OnDestroy,
                OnSaveInstanceState {
    private static final String KEY_SAVED_QS_TOOLTIP_RESHOW = "qs_tooltip_reshow";
    private final Handler mHandler;
    private Optional<ProgressInteractionListener> mInteractionListener;
    private int mLastProgress;
    private boolean mNeedsQSTooltipReshow;
    private final SeekBar.OnSeekBarChangeListener mSeekBarChangeListener;
    private LabeledSeekBarPreference mSeekBarPreference;
    private boolean mSeekByTouch;
    private final PreviewSizeData mSizeData;
    private String[] mStateLabels;
    private AccessibilityQuickSettingsTooltipWindow mTooltipWindow;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ProgressInteractionListener {
        void notifyPreferenceChanged();

        void onEndTrackingTouch();

        void onProgressChanged();
    }

    public PreviewSizeSeekBarController(
            Context context, String str, PreviewSizeData previewSizeData) {
        super(context, str);
        this.mInteractionListener = Optional.empty();
        this.mNeedsQSTooltipReshow = false;
        this.mStateLabels = null;
        this.mSeekBarChangeListener =
                new SeekBar
                        .OnSeekBarChangeListener() { // from class:
                                                     // com.android.settings.accessibility.PreviewSizeSeekBarController.1
                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                        PreviewSizeSeekBarController.this.setSeekbarStateDescription(i);
                        if (PreviewSizeSeekBarController.this.mInteractionListener.isEmpty()) {
                            return;
                        }
                        ProgressInteractionListener progressInteractionListener =
                                (ProgressInteractionListener)
                                        PreviewSizeSeekBarController.this.mInteractionListener
                                                .get();
                        Objects.requireNonNull(progressInteractionListener);
                        seekBar.post(
                                new PreviewSizeSeekBarController$$ExternalSyntheticLambda1(
                                        1, progressInteractionListener));
                        if (PreviewSizeSeekBarController.this.mSeekByTouch) {
                            return;
                        }
                        progressInteractionListener.onProgressChanged();
                        PreviewSizeSeekBarController.this.onProgressFinalized();
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onStartTrackingTouch(SeekBar seekBar) {
                        PreviewSizeSeekBarController.this.mSeekByTouch = true;
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onStopTrackingTouch(SeekBar seekBar) {
                        PreviewSizeSeekBarController.this.mSeekByTouch = false;
                        PreviewSizeSeekBarController.this.mInteractionListener.ifPresent(
                                new PreviewSizeSeekBarController$$ExternalSyntheticLambda0(1));
                        PreviewSizeSeekBarController.this.onProgressFinalized();
                    }
                };
        this.mSizeData = previewSizeData;
        this.mHandler = new Handler(context.getMainLooper());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onProgressFinalized() {
        int i = this.mSeekBarPreference.mProgress;
        if (i != this.mLastProgress) {
            showQuickSettingsTooltipIfNeeded();
            this.mLastProgress = i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSeekbarStateDescription(int i) {
        String[] strArr = this.mStateLabels;
        if (strArr == null) {
            return;
        }
        LabeledSeekBarPreference labeledSeekBarPreference = this.mSeekBarPreference;
        String str = i < strArr.length ? strArr[i] : ApnSettings.MVNO_NONE;
        labeledSeekBarPreference.mSeekBarStateDescription = str;
        SeekBar seekBar = ((SeekBarPreference) labeledSeekBarPreference).mSeekBar;
        if (seekBar != null) {
            seekBar.setStateDescription(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showQuickSettingsTooltipIfNeeded() {
        ComponentName tileComponentName = getTileComponentName();
        if (tileComponentName == null) {
            return;
        }
        Context context = this.mContext;
        if ((context instanceof Activity)
                && WizardManagerHelper.isAnySetupWizard(((Activity) context).getIntent())) {
            return;
        }
        if (!this.mNeedsQSTooltipReshow) {
            TextUtils.SimpleStringSplitter simpleStringSplitter =
                    AccessibilityQuickSettingUtils.sStringColonSplitter;
            return;
        }
        if (this.mSeekBarPreference.mSeekBar != null) {
            AccessibilityQuickSettingsTooltipWindow accessibilityQuickSettingsTooltipWindow =
                    new AccessibilityQuickSettingsTooltipWindow(this.mContext);
            this.mTooltipWindow = accessibilityQuickSettingsTooltipWindow;
            accessibilityQuickSettingsTooltipWindow.setup(getTileTooltipContent());
            this.mTooltipWindow.showAtLocation(this.mSeekBarPreference.mSeekBar, 49, 0, 0);
        }
        AccessibilityQuickSettingUtils.optInValueToSharedPreferences(
                this.mContext, tileComponentName);
        this.mNeedsQSTooltipReshow = false;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        int size = this.mSizeData.mValues.size();
        int i = this.mSizeData.mInitialIndex;
        this.mLastProgress = i;
        LabeledSeekBarPreference labeledSeekBarPreference =
                (LabeledSeekBarPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mSeekBarPreference = labeledSeekBarPreference;
        labeledSeekBarPreference.setMax(size - 1);
        this.mSeekBarPreference.setProgress(i, true);
        LabeledSeekBarPreference labeledSeekBarPreference2 = this.mSeekBarPreference;
        labeledSeekBarPreference2.mContinuousUpdates = true;
        labeledSeekBarPreference2.mSeekBarChangeListener = this.mSeekBarChangeListener;
        if (this.mNeedsQSTooltipReshow) {
            this.mHandler.post(new PreviewSizeSeekBarController$$ExternalSyntheticLambda1(0, this));
        }
        setSeekbarStateDescription(this.mSeekBarPreference.mProgress);
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

    public abstract ComponentName getTileComponentName();

    public abstract CharSequence getTileTooltipContent();

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

    @Override // com.android.settingslib.core.lifecycle.events.OnCreate
    public void onCreate(Bundle bundle) {
        if (bundle == null || !bundle.containsKey(KEY_SAVED_QS_TOOLTIP_RESHOW)) {
            return;
        }
        this.mNeedsQSTooltipReshow = bundle.getBoolean(KEY_SAVED_QS_TOOLTIP_RESHOW);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public void onDestroy() {
        this.mHandler.removeCallbacksAndMessages(null);
        AccessibilityQuickSettingsTooltipWindow accessibilityQuickSettingsTooltipWindow =
                this.mTooltipWindow;
        if (accessibilityQuickSettingsTooltipWindow == null
                || !accessibilityQuickSettingsTooltipWindow.isShowing()) {
            return;
        }
        this.mTooltipWindow.dismiss();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnSaveInstanceState
    public void onSaveInstanceState(Bundle bundle) {
        AccessibilityQuickSettingsTooltipWindow accessibilityQuickSettingsTooltipWindow =
                this.mTooltipWindow;
        boolean z =
                accessibilityQuickSettingsTooltipWindow != null
                        && accessibilityQuickSettingsTooltipWindow.isShowing();
        if (this.mNeedsQSTooltipReshow || z) {
            bundle.putBoolean(KEY_SAVED_QS_TOOLTIP_RESHOW, true);
        }
    }

    @Override // com.android.settings.accessibility.TextReadingResetController.ResetStateListener
    public void resetState() {
        PreviewSizeData previewSizeData = this.mSizeData;
        this.mSeekBarPreference.setProgress(
                previewSizeData.mValues.indexOf(previewSizeData.mDefaultValue), true);
        this.mInteractionListener.ifPresent(
                new PreviewSizeSeekBarController$$ExternalSyntheticLambda0(0));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setInteractionListener(ProgressInteractionListener progressInteractionListener) {
        this.mInteractionListener = Optional.ofNullable(progressInteractionListener);
    }

    public void setProgressStateLabels(String[] strArr) {
        this.mStateLabels = strArr;
        if (strArr == null) {
            return;
        }
        updateState(this.mSeekBarPreference);
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
