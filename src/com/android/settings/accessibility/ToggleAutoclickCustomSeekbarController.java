package com.android.settings.accessibility;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ToggleAutoclickCustomSeekbarController extends BasePreferenceController
        implements LifecycleObserver,
                OnStart,
                OnStop,
                SharedPreferences.OnSharedPreferenceChangeListener {
    private final ContentResolver mContentResolver;
    private TextView mDelayLabel;
    private ImageView mLonger;
    private SeekBar mSeekBar;
    final SeekBar.OnSeekBarChangeListener mSeekBarChangeListener;
    private final SharedPreferences mSharedPreferences;
    private ImageView mShorter;

    public ToggleAutoclickCustomSeekbarController(Context context, String str) {
        super(context, str);
        this.mSeekBarChangeListener =
                new SeekBar
                        .OnSeekBarChangeListener() { // from class:
                                                     // com.android.settings.accessibility.ToggleAutoclickCustomSeekbarController.1
                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                        ToggleAutoclickCustomSeekbarController
                                toggleAutoclickCustomSeekbarController =
                                        ToggleAutoclickCustomSeekbarController.this;
                        toggleAutoclickCustomSeekbarController.updateCustomDelayValue(
                                toggleAutoclickCustomSeekbarController.seekBarProgressToDelay(i));
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onStopTrackingTouch(SeekBar seekBar) {}
                };
        this.mSharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        this.mContentResolver = context.getContentResolver();
    }

    private CharSequence delayTimeToString(int i) {
        return AutoclickUtils.getAutoclickDelaySummary(
                this.mContext, R.string.accessibilty_autoclick_delay_unit_second, i);
    }

    private int delayToSeekBarProgress(int i) {
        return (i - 200) / 100;
    }

    private int getSharedPreferenceForDelayValue() {
        return this.mSharedPreferences.getInt(
                "custom_delay_value",
                Settings.Secure.getInt(
                        this.mContentResolver, "accessibility_autoclick_delay", 600));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$displayPreference$0(View view) {
        minusDelayByImageView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$displayPreference$1(View view) {
        plusDelayByImageView();
    }

    private void minusDelayByImageView() {
        int sharedPreferenceForDelayValue = getSharedPreferenceForDelayValue();
        if (sharedPreferenceForDelayValue > 200) {
            updateCustomDelayValue(sharedPreferenceForDelayValue - 100);
        }
    }

    private void plusDelayByImageView() {
        int sharedPreferenceForDelayValue = getSharedPreferenceForDelayValue();
        if (sharedPreferenceForDelayValue < 1000) {
            updateCustomDelayValue(sharedPreferenceForDelayValue + 100);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int seekBarProgressToDelay(int i) {
        return (i * 100) + 200;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCustomDelayValue(int i) {
        Settings.Secure.putInt(this.mContentResolver, "accessibility_autoclick_delay", i);
        this.mSharedPreferences.edit().putInt("custom_delay_value", i).apply();
        this.mSeekBar.setProgress(delayToSeekBarProgress(i));
        this.mDelayLabel.setText(delayTimeToString(i));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference(getPreferenceKey());
        if (isAvailable()) {
            int sharedPreferenceForDelayValue = getSharedPreferenceForDelayValue();
            SeekBar seekBar =
                    (SeekBar) layoutPreference.mRootView.findViewById(R.id.autoclick_delay);
            this.mSeekBar = seekBar;
            seekBar.setMax(delayToSeekBarProgress(1000));
            this.mSeekBar.setProgress(delayToSeekBarProgress(sharedPreferenceForDelayValue));
            this.mSeekBar.setOnSeekBarChangeListener(this.mSeekBarChangeListener);
            TextView textView =
                    (TextView) layoutPreference.mRootView.findViewById(R.id.current_label);
            this.mDelayLabel = textView;
            textView.setText(delayTimeToString(sharedPreferenceForDelayValue));
            ImageView imageView = (ImageView) layoutPreference.mRootView.findViewById(R.id.shorter);
            this.mShorter = imageView;
            final int i = 0;
            imageView.setOnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.android.settings.accessibility.ToggleAutoclickCustomSeekbarController$$ExternalSyntheticLambda0
                        public final /* synthetic */ ToggleAutoclickCustomSeekbarController f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            int i2 = i;
                            ToggleAutoclickCustomSeekbarController
                                    toggleAutoclickCustomSeekbarController = this.f$0;
                            switch (i2) {
                                case 0:
                                    toggleAutoclickCustomSeekbarController
                                            .lambda$displayPreference$0(view);
                                    break;
                                default:
                                    toggleAutoclickCustomSeekbarController
                                            .lambda$displayPreference$1(view);
                                    break;
                            }
                        }
                    });
            ImageView imageView2 = (ImageView) layoutPreference.mRootView.findViewById(R.id.longer);
            this.mLonger = imageView2;
            final int i2 = 1;
            imageView2.setOnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.android.settings.accessibility.ToggleAutoclickCustomSeekbarController$$ExternalSyntheticLambda0
                        public final /* synthetic */ ToggleAutoclickCustomSeekbarController f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            int i22 = i2;
                            ToggleAutoclickCustomSeekbarController
                                    toggleAutoclickCustomSeekbarController = this.f$0;
                            switch (i22) {
                                case 0:
                                    toggleAutoclickCustomSeekbarController
                                            .lambda$displayPreference$0(view);
                                    break;
                                default:
                                    toggleAutoclickCustomSeekbarController
                                            .lambda$displayPreference$1(view);
                                    break;
                            }
                        }
                    });
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

    @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
        if ("delay_mode".equals(str)) {
            updateCustomDelayValue(getSharedPreferenceForDelayValue());
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        SharedPreferences sharedPreferences = this.mSharedPreferences;
        if (sharedPreferences != null) {
            sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        SharedPreferences sharedPreferences = this.mSharedPreferences;
        if (sharedPreferences != null) {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        }
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
