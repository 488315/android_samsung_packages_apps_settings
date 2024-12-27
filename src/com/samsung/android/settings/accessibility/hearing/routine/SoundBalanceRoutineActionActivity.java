package com.samsung.android.settings.accessibility.hearing.routine;

import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.SeslSeekBar;

import com.android.settings.R;

import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.widget.A11ySeekBar;
import com.samsung.android.settings.accessibility.routine.AccessibilityRoutineActionActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SoundBalanceRoutineActionActivity extends AccessibilityRoutineActionActivity
        implements SeslSeekBar.OnSeekBarChangeListener {
    public A11ySeekBar connectedAudioSeekBar;
    public A11ySeekBar phoneSpeakersSeekBar;
    public boolean trackingTouch = false;

    @Override // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineActionActivity
    public final View buildContentView() {
        View inflate =
                LayoutInflater.from(this)
                        .inflate(R.layout.sound_balance_routine_action_dialog, (ViewGroup) null);
        ParameterValues fromIntent = ParameterValues.fromIntent(getIntent());
        ViewGroup viewGroup = (ViewGroup) inflate.findViewById(R.id.connected_audio_layout);
        float floatValue =
                fromIntent
                        .getNumber(
                                "connected_audio",
                                Float.valueOf(
                                        Settings.System.getFloat(
                                                getContentResolver(), "master_balance", 0.0f)))
                        .floatValue();
        ((TextView) viewGroup.findViewById(R.id.sound_balance_routine_dialog_subtitle))
                .setText(getText(R.string.mono_audio_type_connected_audio));
        A11ySeekBar a11ySeekBar =
                (A11ySeekBar) viewGroup.findViewById(R.id.sound_balance_routine_dialog_seekbar);
        this.connectedAudioSeekBar = a11ySeekBar;
        if (a11ySeekBar != null) {
            int i = ((int) (floatValue * 100.0f)) + 100;
            a11ySeekBar.setMax(200);
            a11ySeekBar.setOnSeekBarChangeListener(this);
            a11ySeekBar.setProgress(i);
            setSeekBarState(a11ySeekBar, i);
        }
        ViewGroup viewGroup2 = (ViewGroup) inflate.findViewById(R.id.phone_speakers_layout);
        if (SecAccessibilityUtils.isSupportStereoSpeaker()) {
            ((TextView) viewGroup2.findViewById(R.id.sound_balance_routine_dialog_subtitle))
                    .setText(getText(R.string.sound_balance_speakers));
            float floatValue2 =
                    fromIntent
                            .getNumber(
                                    "phone_speakers",
                                    Float.valueOf(
                                            Settings.System.getFloat(
                                                    getContentResolver(), "speaker_balance", 0.0f)))
                            .floatValue();
            A11ySeekBar a11ySeekBar2 =
                    (A11ySeekBar)
                            viewGroup2.findViewById(R.id.sound_balance_routine_dialog_seekbar);
            this.phoneSpeakersSeekBar = a11ySeekBar2;
            if (a11ySeekBar2 != null) {
                int i2 = ((int) (floatValue2 * 100.0f)) + 100;
                a11ySeekBar2.setMax(200);
                a11ySeekBar2.setOnSeekBarChangeListener(this);
                a11ySeekBar2.setProgress(i2);
                setSeekBarState(a11ySeekBar2, i2);
            }
        } else {
            viewGroup2.setVisibility(8);
        }
        return inflate;
    }

    @Override // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineActionActivity
    public final int getTitleResId() {
        return R.string.sound_balance_title;
    }

    @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
    public final void onProgressChanged(SeslSeekBar seslSeekBar, int i, boolean z) {
        if (!this.trackingTouch) {
            SecAccessibilityUtils.applySoundBalanceCenterPositionThreshold(seslSeekBar);
        }
        setSeekBarState(seslSeekBar, i);
    }

    @Override // android.app.Activity
    public final void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        if (this.connectedAudioSeekBar != null) {
            int round = Math.round(bundle.getFloat("connected_audio"));
            this.connectedAudioSeekBar.setProgress(round);
            setSeekBarState(this.connectedAudioSeekBar, round);
        }
        if (!SecAccessibilityUtils.isSupportStereoSpeaker() || this.phoneSpeakersSeekBar == null) {
            return;
        }
        int round2 = Math.round(bundle.getFloat("phone_speakers"));
        this.phoneSpeakersSeekBar.setProgress(round2);
        setSeekBarState(this.phoneSpeakersSeekBar, round2);
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        A11ySeekBar a11ySeekBar;
        if (this.connectedAudioSeekBar != null) {
            bundle.putFloat("connected_audio", r0.getProgress());
        }
        if (SecAccessibilityUtils.isSupportStereoSpeaker()
                && (a11ySeekBar = this.phoneSpeakersSeekBar) != null) {
            bundle.putFloat("phone_speakers", a11ySeekBar.getProgress());
        }
        super.onSaveInstanceState(bundle);
    }

    @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
    public final void onStartTrackingTouch(SeslSeekBar seslSeekBar) {
        this.trackingTouch = true;
    }

    @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
    public final void onStopTrackingTouch(SeslSeekBar seslSeekBar) {
        SecAccessibilityUtils.applySoundBalanceCenterPositionThreshold(seslSeekBar);
        this.trackingTouch = false;
    }

    @Override // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineActionActivity
    public final ParameterValues sendParameterValues() {
        A11ySeekBar a11ySeekBar;
        ParameterValues parameterValues = new ParameterValues();
        if (this.connectedAudioSeekBar != null) {
            parameterValues.put(
                    "connected_audio", Float.valueOf((r1.getProgress() - 100) / 100.0f));
        }
        if (SecAccessibilityUtils.isSupportStereoSpeaker()
                && (a11ySeekBar = this.phoneSpeakersSeekBar) != null) {
            parameterValues.put(
                    "phone_speakers", Float.valueOf((a11ySeekBar.getProgress() - 100) / 100.0f));
        }
        return parameterValues;
    }

    public final void setSeekBarState(SeslSeekBar seslSeekBar, int i) {
        if (seslSeekBar == null) {
            return;
        }
        if (i != 100) {
            seslSeekBar.setTickMarkTintList(
                    getColorStateList(R.color.sound_balance_seek_bar_tick_mark_color));
            seslSeekBar.setProgressTintList(
                    getColorStateList(R.color.sound_balance_seek_bar_not_balanced_bg_color));
        } else {
            seslSeekBar.setTickMarkTintList(
                    getColorStateList(R.color.sesl_tick_mark_seekbar_level));
            seslSeekBar.setProgressTintList(
                    getColorStateList(R.color.a11y_slider_progress_normal_color));
        }
    }
}
