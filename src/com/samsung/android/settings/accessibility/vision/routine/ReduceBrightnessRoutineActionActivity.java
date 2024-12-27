package com.samsung.android.settings.accessibility.vision.routine;

import android.hardware.display.ColorDisplayManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SeslSeekBar;

import com.android.settings.R;
import com.android.settingslib.Utils;

import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.settings.accessibility.routine.AccessibilityRoutineActionActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ReduceBrightnessRoutineActionActivity extends AccessibilityRoutineActionActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ColorDisplayManager mColorDisplayManager;
    public RadioButton radioButtonOff;
    public RadioButton radioButtonOn;
    public SeslSeekBar seekBar;

    @Override // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineActionActivity
    public final View buildContentView() {
        View inflate =
                LayoutInflater.from(this)
                        .inflate(
                                R.layout.reduce_brightness_routine_action_dialog_view,
                                (ViewGroup) null);
        ParameterValues fromIntent = ParameterValues.fromIntent(getIntent());
        Boolean bool =
                fromIntent.getBoolean(
                        ReduceBrightnessRoutineActionHandler.KEY_SWITCH, Boolean.TRUE);
        boolean booleanValue = bool.booleanValue();
        ViewGroup viewGroup = (ViewGroup) inflate.findViewById(R.id.radio_on_list);
        ViewGroup viewGroup2 = (ViewGroup) inflate.findViewById(R.id.radio_off_list);
        this.radioButtonOn = (RadioButton) inflate.findViewById(R.id.radio_on);
        RadioButton radioButton = (RadioButton) inflate.findViewById(R.id.radio_off);
        this.radioButtonOff = radioButton;
        if (viewGroup != null
                && viewGroup2 != null
                && this.radioButtonOn != null
                && radioButton != null) {
            View.OnClickListener onClickListener =
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.accessibility.vision.routine.ReduceBrightnessRoutineActionActivity$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            ReduceBrightnessRoutineActionActivity
                                    reduceBrightnessRoutineActionActivity =
                                            ReduceBrightnessRoutineActionActivity.this;
                            RadioButton radioButton2 =
                                    reduceBrightnessRoutineActionActivity.radioButtonOn;
                            RadioButton radioButton3 =
                                    reduceBrightnessRoutineActionActivity.radioButtonOff;
                            if (view.getId() == R.id.radio_on_list) {
                                radioButton2.toggle();
                                radioButton3.setChecked(!radioButton2.isChecked());
                                reduceBrightnessRoutineActionActivity.setEnableSeekBarArea(true);
                            } else if (view.getId() == R.id.radio_off_list) {
                                radioButton3.toggle();
                                radioButton2.setChecked(!radioButton3.isChecked());
                                reduceBrightnessRoutineActionActivity.setEnableSeekBarArea(false);
                            }
                        }
                    };
            viewGroup.setOnClickListener(onClickListener);
            viewGroup2.setOnClickListener(onClickListener);
            RadioButton radioButton2 = this.radioButtonOn;
            if (radioButton2 != null && this.radioButtonOff != null) {
                radioButton2.setChecked(bool.booleanValue());
                this.radioButtonOff.setChecked(!bool.booleanValue());
            }
        }
        TextView textView = (TextView) inflate.findViewById(R.id.left_label);
        if (textView != null) {
            textView.setText(R.string.intensity_seekbar_title);
            textView.setEnabled(booleanValue);
        }
        final TextView textView2 = (TextView) inflate.findViewById(R.id.right_label);
        SeslSeekBar seslSeekBar = (SeslSeekBar) inflate.findViewById(R.id.seekbar);
        this.seekBar = seslSeekBar;
        if (seslSeekBar != null) {
            int round =
                    Math.round(
                            fromIntent
                                    .getNumber(
                                            ReduceBrightnessRoutineActionHandler.KEY_INTENSITY,
                                            Float.valueOf(
                                                    this.mColorDisplayManager
                                                            .getReduceBrightColorsStrength()))
                                    .floatValue());
            this.seekBar.setMax(ColorDisplayManager.getMaximumReduceBrightColorsStrength(this));
            this.seekBar.setMin(ColorDisplayManager.getMinimumReduceBrightColorsStrength(this));
            this.seekBar.setProgress(round);
            this.seekBar.setEnabled(booleanValue);
            if (textView2 != null) {
                textView2.setEnabled(booleanValue);
                textView2.setText(Utils.formatPercentage(this.seekBar.getProgress()));
                this.seekBar.setOnSeekBarChangeListener(
                        new SeslSeekBar
                                .OnSeekBarChangeListener() { // from class:
                                                             // com.samsung.android.settings.accessibility.vision.routine.ReduceBrightnessRoutineActionActivity.1
                            @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
                            public final void onProgressChanged(
                                    SeslSeekBar seslSeekBar2, int i, boolean z) {
                                TextView textView3 = textView2;
                                int i2 = ReduceBrightnessRoutineActionActivity.$r8$clinit;
                                ReduceBrightnessRoutineActionActivity.this.getClass();
                                textView3.setText(Utils.formatPercentage(i));
                            }

                            @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
                            public final void onStartTrackingTouch(SeslSeekBar seslSeekBar2) {}

                            @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
                            public final void onStopTrackingTouch(SeslSeekBar seslSeekBar2) {}
                        });
            }
        }
        return inflate;
    }

    @Override // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineActionActivity
    public final int getTitleResId() {
        return R.string.reduce_bright_colors_preference_title;
    }

    @Override // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineActionActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        this.mColorDisplayManager =
                (ColorDisplayManager) getSystemService(ColorDisplayManager.class);
        super.onCreate(bundle);
    }

    @Override // android.app.Activity
    public final void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        boolean z = bundle.getBoolean(ReduceBrightnessRoutineActionHandler.KEY_SWITCH);
        int round = Math.round(bundle.getFloat(ReduceBrightnessRoutineActionHandler.KEY_INTENSITY));
        RadioButton radioButton = this.radioButtonOn;
        if (radioButton != null && this.radioButtonOff != null) {
            radioButton.setChecked(z);
            this.radioButtonOff.setChecked(!z);
        }
        SeslSeekBar seslSeekBar = this.seekBar;
        if (seslSeekBar != null) {
            seslSeekBar.setProgress(round);
            this.seekBar.setEnabled(z);
        }
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        RadioButton radioButton = this.radioButtonOn;
        if (radioButton != null) {
            bundle.putBoolean(
                    ReduceBrightnessRoutineActionHandler.KEY_SWITCH, radioButton.isChecked());
        }
        if (this.seekBar != null) {
            bundle.putFloat(ReduceBrightnessRoutineActionHandler.KEY_INTENSITY, r0.getProgress());
        }
        super.onSaveInstanceState(bundle);
    }

    @Override // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineActionActivity
    public final ParameterValues sendParameterValues() {
        ParameterValues parameterValues = new ParameterValues();
        if (this.routineActionDialog != null) {
            RadioButton radioButton = this.radioButtonOn;
            if (radioButton != null) {
                parameterValues.put(
                        ReduceBrightnessRoutineActionHandler.KEY_SWITCH,
                        Boolean.valueOf(radioButton.isChecked()));
            }
            if (this.seekBar != null) {
                parameterValues.put(
                        ReduceBrightnessRoutineActionHandler.KEY_INTENSITY,
                        Float.valueOf(r3.getProgress()));
            }
        }
        return parameterValues;
    }

    public final void setEnableSeekBarArea(boolean z) {
        AlertDialog alertDialog = this.routineActionDialog;
        if (alertDialog == null) {
            return;
        }
        View findViewById = alertDialog.findViewById(R.id.left_label);
        if (findViewById != null) {
            findViewById.setEnabled(z);
        }
        View findViewById2 = this.routineActionDialog.findViewById(R.id.right_label);
        if (findViewById2 != null) {
            findViewById2.setEnabled(z);
        }
        SeslSeekBar seslSeekBar = this.seekBar;
        if (seslSeekBar != null) {
            seslSeekBar.setEnabled(z);
        }
    }
}
