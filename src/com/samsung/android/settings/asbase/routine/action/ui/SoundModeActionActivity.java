package com.samsung.android.settings.asbase.routine.action.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.android.settings.R;

import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.settings.asbase.utils.VibUtils;
import com.samsung.android.settings.asbase.widget.SecSoundMode;
import com.samsung.android.settings.asbase.widget.SecVolumeIconMotion;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/samsung/android/settings/asbase/routine/action/ui/SoundModeActionActivity;",
            "Lcom/samsung/android/settings/asbase/routine/action/ui/VolumeActionActivity;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class SoundModeActionActivity extends VolumeActionActivity {
    @Override // com.samsung.android.settings.asbase.routine.action.ui.VolumeActionActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTitle(R.string.sec_sound_mode_control_action_label);
        View inflate =
                LayoutInflater.from(this)
                        .inflate(R.layout.sec_routine_sound_mode, (ViewGroup) null);
        Intrinsics.checkNotNull(
                inflate,
                "null cannot be cast to non-null type"
                    + " com.samsung.android.settings.asbase.widget.SecSoundMode");
        final SecSoundMode secSoundMode = (SecSoundMode) inflate;
        this.soundMode = secSoundMode;
        if (!VibUtils.hasVibrator(secSoundMode.getContext())) {
            secSoundMode.btnVibrateVisible = 8;
        }
        String string = getString(R.string.sec_sound_mode_sound);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        String string2 = getString(R.string.sec_sound_mode_vibrate);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        String string3 = getString(R.string.sec_sound_mode_mute);
        Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
        secSoundMode.textSound = string;
        secSoundMode.textVibrate = string2;
        secSoundMode.textMute = string3;
        VolumeActionActivity$onModeChangedListener$1 onButtonSelectedListener =
                this.onButtonSelectedListener;
        Intrinsics.checkNotNullParameter(onButtonSelectedListener, "onButtonSelectedListener");
        secSoundMode.listener = onButtonSelectedListener;
        int currentMode = getCurrentMode();
        int i = currentMode != 0 ? currentMode != 1 ? 0 : 1 : 2;
        Boolean bool =
                ParameterValues.fromIntent(getIntent())
                        .getBoolean("as_do_not_change_volume", Boolean.FALSE);
        Intrinsics.checkNotNullExpressionValue(bool, "getBoolean(...)");
        boolean booleanValue = bool.booleanValue();
        Intrinsics.checkNotNullExpressionValue(secSoundMode.getContext(), "getContext(...)");
        secSoundMode.btnSound = new SecSoundMode.MultiButtonItem();
        Intrinsics.checkNotNullExpressionValue(secSoundMode.getContext(), "getContext(...)");
        secSoundMode.btnVibrate = new SecSoundMode.MultiButtonItem();
        Intrinsics.checkNotNullExpressionValue(secSoundMode.getContext(), "getContext(...)");
        secSoundMode.btnMute = new SecSoundMode.MultiButtonItem();
        secSoundMode.iconMotion = new SecVolumeIconMotion(secSoundMode.getContext());
        secSoundMode.selectedPosition = i;
        SecSoundMode.MultiButtonItem multiButtonItem = secSoundMode.btnSound;
        if (multiButtonItem == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnSound");
            throw null;
        }
        multiButtonItem.bindView(secSoundMode, 0);
        SecSoundMode.MultiButtonItem multiButtonItem2 = secSoundMode.btnVibrate;
        if (multiButtonItem2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnVibrate");
            throw null;
        }
        multiButtonItem2.bindView(secSoundMode, 1);
        SecSoundMode.MultiButtonItem multiButtonItem3 = secSoundMode.btnMute;
        if (multiButtonItem3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnMute");
            throw null;
        }
        multiButtonItem3.bindView(secSoundMode, 2);
        secSoundMode.startModeChangeAnimation(true);
        View requireViewById = secSoundMode.requireViewById(R.id.sound_mode_container);
        Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(...)");
        secSoundMode.soundModeContainer = (ViewGroup) requireViewById;
        View inflate2 =
                LayoutInflater.from(secSoundMode.getContext())
                        .inflate(R.layout.sec_volume_option_view, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(inflate2, "inflate(...)");
        secSoundMode.volumeOptionView = inflate2;
        ViewGroup viewGroup = secSoundMode.soundModeContainer;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("soundModeContainer");
            throw null;
        }
        viewGroup.addView(inflate2);
        View view = secSoundMode.volumeOptionView;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("volumeOptionView");
            throw null;
        }
        View requireViewById2 = view.requireViewById(R.id.volume_option_check_box);
        Intrinsics.checkNotNull(
                requireViewById2, "null cannot be cast to non-null type android.widget.CheckBox");
        secSoundMode.checkBox = (CheckBox) requireViewById2;
        View view2 = secSoundMode.volumeOptionView;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("volumeOptionView");
            throw null;
        }
        final int i2 = 1;
        view2.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.asbase.widget.SecSoundMode$onBind$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view3) {
                        switch (i2) {
                            case 0:
                                SecSoundMode secSoundMode2 = secSoundMode;
                                secSoundMode2.selectedPosition = 0;
                                secSoundMode2.updateButtonStatus(0);
                                SecSoundMode secSoundMode3 = secSoundMode;
                                SecSoundMode.access$callModeSelectListener(
                                        secSoundMode3, secSoundMode3.selectedPosition);
                                return;
                            case 1:
                                CheckBox checkBox = secSoundMode.checkBox;
                                if (checkBox == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException(
                                            "checkBox");
                                    throw null;
                                }
                                if (checkBox.isEnabled()) {
                                    CheckBox checkBox2 = secSoundMode.checkBox;
                                    if (checkBox2 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException(
                                                "checkBox");
                                        throw null;
                                    }
                                    if (checkBox2 != null) {
                                        checkBox2.setChecked(!checkBox2.isChecked());
                                        return;
                                    } else {
                                        Intrinsics.throwUninitializedPropertyAccessException(
                                                "checkBox");
                                        throw null;
                                    }
                                }
                                return;
                            case 2:
                                SecSoundMode secSoundMode4 = secSoundMode;
                                secSoundMode4.selectedPosition = 1;
                                secSoundMode4.updateButtonStatus(1);
                                SecSoundMode secSoundMode5 = secSoundMode;
                                SecSoundMode.access$callModeSelectListener(
                                        secSoundMode5, secSoundMode5.selectedPosition);
                                return;
                            default:
                                SecSoundMode secSoundMode6 = secSoundMode;
                                secSoundMode6.selectedPosition = 2;
                                secSoundMode6.updateButtonStatus(2);
                                SecSoundMode secSoundMode7 = secSoundMode;
                                SecSoundMode.access$callModeSelectListener(
                                        secSoundMode7, secSoundMode7.selectedPosition);
                                return;
                        }
                    }
                });
        CheckBox checkBox = secSoundMode.checkBox;
        if (checkBox == null) {
            Intrinsics.throwUninitializedPropertyAccessException("checkBox");
            throw null;
        }
        checkBox.setOnCheckedChangeListener(
                new CompoundButton
                        .OnCheckedChangeListener() { // from class:
                                                     // com.samsung.android.settings.asbase.widget.SecSoundMode$addVolumeOptionView$2
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        SecSoundMode secSoundMode2;
                        Intrinsics.checkNotNullParameter(compoundButton, "<anonymous parameter 0>");
                        VolumeActionActivity$onModeChangedListener$1
                                volumeActionActivity$onModeChangedListener$1 =
                                        SecSoundMode.this.listener;
                        if (volumeActionActivity$onModeChangedListener$1 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("listener");
                            throw null;
                        }
                        VolumeActionActivity volumeActionActivity =
                                volumeActionActivity$onModeChangedListener$1.this$0;
                        volumeActionActivity.isVolumeOptionSelected = z;
                        ViewGroup viewGroup2 = volumeActionActivity.rowContainer;
                        if (viewGroup2 != null) {
                            viewGroup2.setVisibility(
                                    (z
                                                    && (secSoundMode2 =
                                                                    volumeActionActivity.soundMode)
                                                            != null
                                                    && secSoundMode2.ringerMode == 2)
                                            ? 8
                                            : 0);
                        } else {
                            Intrinsics.throwUninitializedPropertyAccessException("rowContainer");
                            throw null;
                        }
                    }
                });
        View requireViewById3 = secSoundMode.requireViewById(R.id.divider2);
        Intrinsics.checkNotNullExpressionValue(requireViewById3, "requireViewById(...)");
        secSoundMode.dividerView = requireViewById3;
        requireViewById3.setVisibility(secSoundMode.btnVibrateVisible);
        View requireViewById4 = secSoundMode.requireViewById(R.id.low_button);
        Intrinsics.checkNotNullExpressionValue(requireViewById4, "requireViewById(...)");
        final int i3 = 0;
        ((LinearLayout) requireViewById4)
                .setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.asbase.widget.SecSoundMode$onBind$1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view3) {
                                switch (i3) {
                                    case 0:
                                        SecSoundMode secSoundMode2 = secSoundMode;
                                        secSoundMode2.selectedPosition = 0;
                                        secSoundMode2.updateButtonStatus(0);
                                        SecSoundMode secSoundMode3 = secSoundMode;
                                        SecSoundMode.access$callModeSelectListener(
                                                secSoundMode3, secSoundMode3.selectedPosition);
                                        return;
                                    case 1:
                                        CheckBox checkBox2 = secSoundMode.checkBox;
                                        if (checkBox2 == null) {
                                            Intrinsics.throwUninitializedPropertyAccessException(
                                                    "checkBox");
                                            throw null;
                                        }
                                        if (checkBox2.isEnabled()) {
                                            CheckBox checkBox22 = secSoundMode.checkBox;
                                            if (checkBox22 == null) {
                                                Intrinsics
                                                        .throwUninitializedPropertyAccessException(
                                                                "checkBox");
                                                throw null;
                                            }
                                            if (checkBox22 != null) {
                                                checkBox22.setChecked(!checkBox22.isChecked());
                                                return;
                                            } else {
                                                Intrinsics
                                                        .throwUninitializedPropertyAccessException(
                                                                "checkBox");
                                                throw null;
                                            }
                                        }
                                        return;
                                    case 2:
                                        SecSoundMode secSoundMode4 = secSoundMode;
                                        secSoundMode4.selectedPosition = 1;
                                        secSoundMode4.updateButtonStatus(1);
                                        SecSoundMode secSoundMode5 = secSoundMode;
                                        SecSoundMode.access$callModeSelectListener(
                                                secSoundMode5, secSoundMode5.selectedPosition);
                                        return;
                                    default:
                                        SecSoundMode secSoundMode6 = secSoundMode;
                                        secSoundMode6.selectedPosition = 2;
                                        secSoundMode6.updateButtonStatus(2);
                                        SecSoundMode secSoundMode7 = secSoundMode;
                                        SecSoundMode.access$callModeSelectListener(
                                                secSoundMode7, secSoundMode7.selectedPosition);
                                        return;
                                }
                            }
                        });
        View requireViewById5 = secSoundMode.requireViewById(R.id.mid_button);
        Intrinsics.checkNotNullExpressionValue(requireViewById5, "requireViewById(...)");
        final int i4 = 2;
        ((LinearLayout) requireViewById5)
                .setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.asbase.widget.SecSoundMode$onBind$1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view3) {
                                switch (i4) {
                                    case 0:
                                        SecSoundMode secSoundMode2 = secSoundMode;
                                        secSoundMode2.selectedPosition = 0;
                                        secSoundMode2.updateButtonStatus(0);
                                        SecSoundMode secSoundMode3 = secSoundMode;
                                        SecSoundMode.access$callModeSelectListener(
                                                secSoundMode3, secSoundMode3.selectedPosition);
                                        return;
                                    case 1:
                                        CheckBox checkBox2 = secSoundMode.checkBox;
                                        if (checkBox2 == null) {
                                            Intrinsics.throwUninitializedPropertyAccessException(
                                                    "checkBox");
                                            throw null;
                                        }
                                        if (checkBox2.isEnabled()) {
                                            CheckBox checkBox22 = secSoundMode.checkBox;
                                            if (checkBox22 == null) {
                                                Intrinsics
                                                        .throwUninitializedPropertyAccessException(
                                                                "checkBox");
                                                throw null;
                                            }
                                            if (checkBox22 != null) {
                                                checkBox22.setChecked(!checkBox22.isChecked());
                                                return;
                                            } else {
                                                Intrinsics
                                                        .throwUninitializedPropertyAccessException(
                                                                "checkBox");
                                                throw null;
                                            }
                                        }
                                        return;
                                    case 2:
                                        SecSoundMode secSoundMode4 = secSoundMode;
                                        secSoundMode4.selectedPosition = 1;
                                        secSoundMode4.updateButtonStatus(1);
                                        SecSoundMode secSoundMode5 = secSoundMode;
                                        SecSoundMode.access$callModeSelectListener(
                                                secSoundMode5, secSoundMode5.selectedPosition);
                                        return;
                                    default:
                                        SecSoundMode secSoundMode6 = secSoundMode;
                                        secSoundMode6.selectedPosition = 2;
                                        secSoundMode6.updateButtonStatus(2);
                                        SecSoundMode secSoundMode7 = secSoundMode;
                                        SecSoundMode.access$callModeSelectListener(
                                                secSoundMode7, secSoundMode7.selectedPosition);
                                        return;
                                }
                            }
                        });
        View requireViewById6 = secSoundMode.requireViewById(R.id.high_button);
        Intrinsics.checkNotNullExpressionValue(requireViewById6, "requireViewById(...)");
        final int i5 = 3;
        ((LinearLayout) requireViewById6)
                .setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.asbase.widget.SecSoundMode$onBind$1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view3) {
                                switch (i5) {
                                    case 0:
                                        SecSoundMode secSoundMode2 = secSoundMode;
                                        secSoundMode2.selectedPosition = 0;
                                        secSoundMode2.updateButtonStatus(0);
                                        SecSoundMode secSoundMode3 = secSoundMode;
                                        SecSoundMode.access$callModeSelectListener(
                                                secSoundMode3, secSoundMode3.selectedPosition);
                                        return;
                                    case 1:
                                        CheckBox checkBox2 = secSoundMode.checkBox;
                                        if (checkBox2 == null) {
                                            Intrinsics.throwUninitializedPropertyAccessException(
                                                    "checkBox");
                                            throw null;
                                        }
                                        if (checkBox2.isEnabled()) {
                                            CheckBox checkBox22 = secSoundMode.checkBox;
                                            if (checkBox22 == null) {
                                                Intrinsics
                                                        .throwUninitializedPropertyAccessException(
                                                                "checkBox");
                                                throw null;
                                            }
                                            if (checkBox22 != null) {
                                                checkBox22.setChecked(!checkBox22.isChecked());
                                                return;
                                            } else {
                                                Intrinsics
                                                        .throwUninitializedPropertyAccessException(
                                                                "checkBox");
                                                throw null;
                                            }
                                        }
                                        return;
                                    case 2:
                                        SecSoundMode secSoundMode4 = secSoundMode;
                                        secSoundMode4.selectedPosition = 1;
                                        secSoundMode4.updateButtonStatus(1);
                                        SecSoundMode secSoundMode5 = secSoundMode;
                                        SecSoundMode.access$callModeSelectListener(
                                                secSoundMode5, secSoundMode5.selectedPosition);
                                        return;
                                    default:
                                        SecSoundMode secSoundMode6 = secSoundMode;
                                        secSoundMode6.selectedPosition = 2;
                                        secSoundMode6.updateButtonStatus(2);
                                        SecSoundMode secSoundMode7 = secSoundMode;
                                        SecSoundMode.access$callModeSelectListener(
                                                secSoundMode7, secSoundMode7.selectedPosition);
                                        return;
                                }
                            }
                        });
        SecSoundMode.MultiButtonItem multiButtonItem4 = secSoundMode.btnSound;
        if (multiButtonItem4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnSound");
            throw null;
        }
        multiButtonItem4.setText(secSoundMode.textSound);
        SecSoundMode.MultiButtonItem multiButtonItem5 = secSoundMode.btnVibrate;
        if (multiButtonItem5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnVibrate");
            throw null;
        }
        multiButtonItem5.setText(secSoundMode.textVibrate);
        SecSoundMode.MultiButtonItem multiButtonItem6 = secSoundMode.btnMute;
        if (multiButtonItem6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnMute");
            throw null;
        }
        multiButtonItem6.setText(secSoundMode.textMute);
        SecSoundMode.MultiButtonItem multiButtonItem7 = secSoundMode.btnSound;
        if (multiButtonItem7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnSound");
            throw null;
        }
        View view3 = multiButtonItem7.button;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("button");
            throw null;
        }
        view3.setVisibility(0);
        SecSoundMode.MultiButtonItem multiButtonItem8 = secSoundMode.btnVibrate;
        if (multiButtonItem8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnVibrate");
            throw null;
        }
        int i6 = secSoundMode.btnVibrateVisible;
        View view4 = multiButtonItem8.button;
        if (view4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("button");
            throw null;
        }
        view4.setVisibility(i6);
        SecSoundMode.MultiButtonItem multiButtonItem9 = secSoundMode.btnMute;
        if (multiButtonItem9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnMute");
            throw null;
        }
        View view5 = multiButtonItem9.button;
        if (view5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("button");
            throw null;
        }
        view5.setVisibility(0);
        secSoundMode.updateButtonStatus(secSoundMode.selectedPosition);
        CheckBox checkBox2 = secSoundMode.checkBox;
        if (checkBox2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("checkBox");
            throw null;
        }
        checkBox2.setChecked(booleanValue);
        ViewGroup viewGroup2 = this.modeContainer;
        if (viewGroup2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("modeContainer");
            throw null;
        }
        viewGroup2.addView(secSoundMode);
        CheckBox checkBox3 = secSoundMode.checkBox;
        if (checkBox3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("checkBox");
            throw null;
        }
        this.isVolumeOptionSelected = checkBox3.isChecked();
        String string4 = getResources().getString(R.string.sec_ringtone_title);
        Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
        addVolumeRow(2, string4, false);
        String string5 = getResources().getString(R.string.sec_vibration_notification);
        Intrinsics.checkNotNullExpressionValue(string5, "getString(...)");
        addVolumeRow(5, string5, false);
        String string6 = getResources().getString(R.string.sec_vibration_system);
        Intrinsics.checkNotNullExpressionValue(string6, "getString(...)");
        addVolumeRow(1, string6, true);
    }
}
