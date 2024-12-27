package com.samsung.android.settings.asbase.routine.action.ui;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SeslSeekBar;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.notification.zen.lifestyle.LifeStyleDND$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.sdk.routines.v3.data.ParameterValues$$ExternalSyntheticLambda0;
import com.samsung.android.settings.asbase.widget.SecSoundMode;
import com.samsung.android.settings.asbase.widget.SecVolumeIcon;
import com.samsung.android.settings.asbase.widget.SecVolumeIconMotion;
import com.samsung.android.settings.asbase.widget.SecVolumeValues;
import com.samsung.android.util.SemLog;
import com.sec.ims.presence.ServiceTuple;

import kotlin.jvm.internal.Intrinsics;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class VolumeActionActivity extends AppCompatActivity {
    public AudioManager audioManager;
    public boolean isVolumeOptionSelected;
    public ViewGroup modeContainer;
    public ViewGroup rowContainer;
    public SecSoundMode soundMode;
    public final List rowList = new ArrayList();
    public final VolumeActionActivity$onModeChangedListener$1 onButtonSelectedListener =
            new VolumeActionActivity$onModeChangedListener$1(this);
    public final VolumeActionActivity$onModeChangedListener$1 onModeChangedListener =
            new VolumeActionActivity$onModeChangedListener$1(this);

    public static String getSuffix(int i, String str) {
        return str.length() > 0
                ? "_".concat(SecVolumeValues.rowStreamTypeToString(i))
                : ApnSettings.MVNO_NONE;
    }

    public final void addVolumeRow(int i, String header, boolean z) {
        int i2;
        int i3;
        Intrinsics.checkNotNullParameter(header, "header");
        View inflate =
                LayoutInflater.from(this).inflate(R.layout.sec_volume_dialog_row, (ViewGroup) null);
        Intrinsics.checkNotNull(
                inflate,
                "null cannot be cast to non-null type"
                    + " com.samsung.android.settings.asbase.routine.action.ui.VolumeActionRow");
        final VolumeActionRow volumeActionRow = (VolumeActionRow) inflate;
        int m =
                (int)
                        LifeStyleDND$$ExternalSyntheticOutline0.m(
                                -1.0f,
                                ParameterValues.fromIntent(getIntent()),
                                AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                                        "volume_level", getSuffix(i, header)));
        int currentMode = getCurrentMode();
        Object systemService =
                volumeActionRow.getContext().getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        Intrinsics.checkNotNull(
                systemService, "null cannot be cast to non-null type android.media.AudioManager");
        volumeActionRow.audioManager = (AudioManager) systemService;
        View requireViewById = volumeActionRow.requireViewById(R.id.volume_row_header);
        Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(...)");
        volumeActionRow.titleView = (TextView) requireViewById;
        View requireViewById2 = volumeActionRow.requireViewById(R.id.volume_percent);
        Intrinsics.checkNotNullExpressionValue(requireViewById2, "requireViewById(...)");
        volumeActionRow.levelText = (TextView) requireViewById2;
        View requireViewById3 = volumeActionRow.requireViewById(R.id.volume_default_icon);
        Intrinsics.checkNotNullExpressionValue(requireViewById3, "requireViewById(...)");
        volumeActionRow.icon = (SecVolumeIcon) requireViewById3;
        View requireViewById4 = volumeActionRow.requireViewById(R.id.volume_row_slider);
        Intrinsics.checkNotNullExpressionValue(requireViewById4, "requireViewById(...)");
        volumeActionRow.seekBar = (SeslSeekBar) requireViewById4;
        volumeActionRow.streamType = i;
        volumeActionRow.ringerModeInt = currentMode;
        if (i > 100) {
            int audioStream = SecVolumeValues.getAudioStream(i);
            switch (volumeActionRow.streamType) {
                case 101:
                    i3 = 2;
                    break;
                case 102:
                    i3 = 22;
                    break;
                case 103:
                    i3 = 3;
                    break;
                case 104:
                    i3 = 4;
                    break;
                case 105:
                    i3 = 8;
                    break;
                default:
                    i3 = 0;
                    break;
            }
            AudioManager audioManager = volumeActionRow.audioManager;
            if (audioManager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("audioManager");
                throw null;
            }
            volumeActionRow.volumeMin = audioManager.getStreamMinVolume(audioStream);
            AudioManager audioManager2 = volumeActionRow.audioManager;
            if (audioManager2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("audioManager");
                throw null;
            }
            volumeActionRow.volumeMax = audioManager2.getStreamMaxVolume(audioStream) * 10;
            if (m == -1) {
                AudioManager audioManager3 = volumeActionRow.audioManager;
                if (audioManager3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("audioManager");
                    throw null;
                }
                m = audioManager3.semGetFineVolume(audioStream, i3);
            }
            volumeActionRow.volumeLevel = m;
        } else {
            AudioManager audioManager4 = volumeActionRow.audioManager;
            if (audioManager4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("audioManager");
                throw null;
            }
            volumeActionRow.volumeMin = audioManager4.getStreamMinVolumeInt(i);
            AudioManager audioManager5 = volumeActionRow.audioManager;
            if (audioManager5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("audioManager");
                throw null;
            }
            volumeActionRow.volumeMax =
                    audioManager5.getStreamMaxVolume(volumeActionRow.streamType);
            if (m == -1) {
                m = volumeActionRow.getDefaultVolume();
            }
            volumeActionRow.volumeLevel = m;
            int i4 = volumeActionRow.streamType;
            int i5 = SecVolumeValues.EAR_PROTECT_LIMIT_LEVEL_FOR_ROUTINE;
            if ((i4 == 2 || i4 == 5 || i4 == 1) && volumeActionRow.ringerModeInt != 2) {
                volumeActionRow.volumeLevel = 0;
            }
        }
        SecVolumeIconMotion secVolumeIconMotion =
                new SecVolumeIconMotion(volumeActionRow.getContext());
        SecVolumeIcon secVolumeIcon = volumeActionRow.icon;
        if (secVolumeIcon == null) {
            Intrinsics.throwUninitializedPropertyAccessException("icon");
            throw null;
        }
        secVolumeIcon.initialize(secVolumeIconMotion, volumeActionRow.streamType);
        SecVolumeIcon secVolumeIcon2 = volumeActionRow.icon;
        if (secVolumeIcon2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("icon");
            throw null;
        }
        secVolumeIcon2.updateLayoutForRoutine(
                volumeActionRow.volumeLevel, volumeActionRow.ringerModeInt);
        int i6 = volumeActionRow.volumeLevel;
        TextView textView = volumeActionRow.levelText;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("levelText");
            throw null;
        }
        textView.setText(((int) (((i6 * 1.0f) / volumeActionRow.volumeMax) * 100.0f)) + "%");
        SeslSeekBar seslSeekBar = volumeActionRow.seekBar;
        if (seslSeekBar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("seekBar");
            throw null;
        }
        seslSeekBar.setMin(0);
        SeslSeekBar seslSeekBar2 = volumeActionRow.seekBar;
        if (seslSeekBar2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("seekBar");
            throw null;
        }
        seslSeekBar2.setMax(volumeActionRow.volumeMax);
        SeslSeekBar seslSeekBar3 = volumeActionRow.seekBar;
        if (seslSeekBar3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("seekBar");
            throw null;
        }
        seslSeekBar3.setSeamless(true);
        SeslSeekBar seslSeekBar4 = volumeActionRow.seekBar;
        if (seslSeekBar4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("seekBar");
            throw null;
        }
        seslSeekBar4.setProgress(i6);
        SeslSeekBar seslSeekBar5 = volumeActionRow.seekBar;
        if (seslSeekBar5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("seekBar");
            throw null;
        }
        seslSeekBar5.setMode(5);
        SecVolumeIcon secVolumeIcon3 = volumeActionRow.icon;
        if (secVolumeIcon3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("icon");
            throw null;
        }
        int i7 = secVolumeIcon3.mStream;
        if (i7 == 102 || i7 == 105) {
            SeslSeekBar seslSeekBar6 = volumeActionRow.seekBar;
            if (seslSeekBar6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("seekBar");
                throw null;
            }
            seslSeekBar6.setOverlapPointForDualColor(
                    SecVolumeValues.EAR_PROTECT_LIMIT_LEVEL_FOR_ROUTINE);
        }
        SeslSeekBar seslSeekBar7 = volumeActionRow.seekBar;
        if (seslSeekBar7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("seekBar");
            throw null;
        }
        seslSeekBar7.setOnSeekBarChangeListener(
                new SeslSeekBar
                        .OnSeekBarChangeListener() { // from class:
                                                     // com.samsung.android.settings.asbase.routine.action.ui.VolumeActionRow$initSeekBar$1
                    @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
                    public final void onProgressChanged(SeslSeekBar seekBar, int i8, boolean z2) {
                        Intrinsics.checkNotNullParameter(seekBar, "seekBar");
                        VolumeActionRow volumeActionRow2 = VolumeActionRow.this;
                        if (z2
                                && (i8 == volumeActionRow2.volumeMax
                                        || i8 == volumeActionRow2.volumeMin)) {
                            seekBar.performHapticFeedback(
                                    HapticFeedbackConstants.semGetVibrationIndex(41));
                        }
                        int i9 = volumeActionRow2.volumeMin;
                        if (i8 < i9) {
                            seekBar.setProgress(i9);
                            volumeActionRow2.volumeLevel = volumeActionRow2.volumeMin;
                        } else {
                            int i10 = volumeActionRow2.streamType;
                            int i11 = SecVolumeValues.EAR_PROTECT_LIMIT_LEVEL_FOR_ROUTINE;
                            if (i10 == 2) {
                                int i12 = volumeActionRow2.ringerModeInt;
                                int i13 = (i12 == 2 && i8 == 0) ? 1 : i8 != 0 ? 2 : i12;
                                volumeActionRow2.ringerModeInt = i13;
                                if (i12 != i13) {
                                    VolumeActionActivity$onModeChangedListener$1
                                            volumeActionActivity$onModeChangedListener$1 =
                                                    volumeActionRow2.listener;
                                    if (volumeActionActivity$onModeChangedListener$1 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException(
                                                "listener");
                                        throw null;
                                    }
                                    VolumeActionActivity volumeActionActivity =
                                            volumeActionActivity$onModeChangedListener$1.this$0;
                                    SecSoundMode secSoundMode = volumeActionActivity.soundMode;
                                    if (secSoundMode != null) {
                                        CheckBox checkBox = secSoundMode.checkBox;
                                        if (checkBox == null) {
                                            Intrinsics.throwUninitializedPropertyAccessException(
                                                    "checkBox");
                                            throw null;
                                        }
                                        checkBox.setChecked(false);
                                        int i14 = i13 != 0 ? i13 != 1 ? 0 : 1 : 2;
                                        secSoundMode.selectedPosition = i14;
                                        secSoundMode.updateButtonStatus(i14);
                                    }
                                    Iterator it =
                                            ((ArrayList) volumeActionActivity.rowList).iterator();
                                    while (it.hasNext()) {
                                        VolumeActionRow volumeActionRow3 =
                                                (VolumeActionRow) it.next();
                                        int i15 = volumeActionRow3.streamType;
                                        if (i15 == 5 || i15 == 1) {
                                            volumeActionRow3.updateFromRinger(
                                                    i13 != 0 ? i13 != 1 ? 0 : 1 : 2);
                                        }
                                    }
                                }
                            }
                            volumeActionRow2.volumeLevel = i8;
                            SecVolumeIcon secVolumeIcon4 = volumeActionRow2.icon;
                            if (secVolumeIcon4 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("icon");
                                throw null;
                            }
                            secVolumeIcon4.updateLayoutForRoutine(
                                    i8, volumeActionRow2.ringerModeInt);
                        }
                        TextView textView2 = volumeActionRow2.levelText;
                        if (textView2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("levelText");
                            throw null;
                        }
                        textView2.setText(
                                ((int)
                                                (((volumeActionRow2.volumeLevel * 1.0f)
                                                                / volumeActionRow2.volumeMax)
                                                        * 100.0f))
                                        + "%");
                    }

                    @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
                    public final void onStartTrackingTouch(SeslSeekBar seekBar) {
                        Intrinsics.checkNotNullParameter(seekBar, "seekBar");
                    }

                    @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
                    public final void onStopTrackingTouch(SeslSeekBar seekBar) {
                        Intrinsics.checkNotNullParameter(seekBar, "seekBar");
                    }
                });
        if (volumeActionRow.ringerModeInt != 2
                && ((i2 = volumeActionRow.streamType) == 5 || i2 == 1)) {
            SeslSeekBar seslSeekBar8 = volumeActionRow.seekBar;
            if (seslSeekBar8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("seekBar");
                throw null;
            }
            seslSeekBar8.setEnabled(false);
        }
        TextView textView2 = volumeActionRow.titleView;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("titleView");
            throw null;
        }
        textView2.setText(header);
        TextView textView3 = volumeActionRow.titleView;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("titleView");
            throw null;
        }
        textView3.setVisibility(header.length() != 0 ? 0 : 8);
        if (this.soundMode != null) {
            VolumeActionActivity$onModeChangedListener$1 onModeChangedListener =
                    this.onModeChangedListener;
            Intrinsics.checkNotNullParameter(onModeChangedListener, "onModeChangedListener");
            volumeActionRow.listener = onModeChangedListener;
            ViewGroup viewGroup = this.rowContainer;
            if (viewGroup == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rowContainer");
                throw null;
            }
            View requireViewById5 = viewGroup.requireViewById(R.id.volume_title);
            Intrinsics.checkNotNull(
                    requireViewById5, "null cannot be cast to non-null type android.view.View");
            requireViewById5.setVisibility(0);
        }
        ((ArrayList) this.rowList).add(volumeActionRow);
        ViewGroup viewGroup2 = this.rowContainer;
        if (viewGroup2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rowContainer");
            throw null;
        }
        viewGroup2.addView(volumeActionRow);
        if (z) {
            return;
        }
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(
                        -2, getResources().getDimensionPixelSize(R.dimen.sec_volume_row_space));
        View inflate2 =
                LayoutInflater.from(this).inflate(R.layout.sec_routine_space, (ViewGroup) null);
        Intrinsics.checkNotNull(
                inflate2, "null cannot be cast to non-null type android.widget.Space");
        Space space = (Space) inflate2;
        space.setLayoutParams(layoutParams);
        ViewGroup viewGroup3 = this.rowContainer;
        if (viewGroup3 != null) {
            viewGroup3.addView(space);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("rowContainer");
            throw null;
        }
    }

    public final int getCurrentMode() {
        ParameterValues fromIntent = ParameterValues.fromIntent(getIntent());
        if (this.audioManager != null) {
            return (int)
                    LifeStyleDND$$ExternalSyntheticOutline0.m(
                            r2.semGetRingerModeInternal(), fromIntent, "as_sound_mode");
        }
        Intrinsics.throwUninitializedPropertyAccessException("audioManager");
        throw null;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        SemLog.d("VolumeActionActivity", "OnCreate");
        Object systemService =
                getApplicationContext().getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        Intrinsics.checkNotNull(
                systemService, "null cannot be cast to non-null type android.media.AudioManager");
        this.audioManager = (AudioManager) systemService;
        requestWindowFeature(1);
        setContentView(R.layout.routine_volume_action_layout);
        getWindow().setGravity(80);
        View requireViewById = requireViewById(R.id.btn_done);
        Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(...)");
        View requireViewById2 = requireViewById(R.id.btn_cancel);
        Intrinsics.checkNotNullExpressionValue(requireViewById2, "requireViewById(...)");
        View requireViewById3 = requireViewById(R.id.sound_mode_container);
        Intrinsics.checkNotNullExpressionValue(requireViewById3, "requireViewById(...)");
        this.modeContainer = (ViewGroup) requireViewById3;
        View requireViewById4 = requireViewById(R.id.volume_row_container);
        Intrinsics.checkNotNullExpressionValue(requireViewById4, "requireViewById(...)");
        this.rowContainer = (ViewGroup) requireViewById4;
        ((ArrayList) this.rowList).clear();
        final int i = 0;
        ((Button) requireViewById)
                .setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.asbase.routine.action.ui.VolumeActionActivity$onCreate$1
                            public final /* synthetic */ VolumeActionActivity this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                boolean z;
                                switch (i) {
                                    case 0:
                                        HashMap hashMap = new HashMap();
                                        SecSoundMode secSoundMode = this.this$0.soundMode;
                                        if (secSoundMode != null) {
                                            hashMap.put(
                                                    "as_sound_mode",
                                                    new ParameterValues.ParameterValue(
                                                            Float.valueOf(
                                                                    secSoundMode.ringerMode)));
                                            CheckBox checkBox = secSoundMode.checkBox;
                                            if (checkBox == null) {
                                                Intrinsics
                                                        .throwUninitializedPropertyAccessException(
                                                                "checkBox");
                                                throw null;
                                            }
                                            if (checkBox.isChecked()) {
                                                int i2 = secSoundMode.ringerMode;
                                                int i3 =
                                                        SecVolumeValues
                                                                .EAR_PROTECT_LIMIT_LEVEL_FOR_ROUTINE;
                                                if (i2 == 2) {
                                                    z = true;
                                                    hashMap.put(
                                                            "as_do_not_change_volume",
                                                            new ParameterValues.ParameterValue(
                                                                    Boolean.valueOf(z)));
                                                }
                                            }
                                            z = false;
                                            hashMap.put(
                                                    "as_do_not_change_volume",
                                                    new ParameterValues.ParameterValue(
                                                            Boolean.valueOf(z)));
                                        }
                                        Iterator it = ((ArrayList) this.this$0.rowList).iterator();
                                        while (it.hasNext()) {
                                            VolumeActionRow volumeActionRow =
                                                    (VolumeActionRow) it.next();
                                            hashMap.put(
                                                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0
                                                            .m(
                                                                    "volume_level",
                                                                    VolumeActionActivity.getSuffix(
                                                                            volumeActionRow
                                                                                    .streamType,
                                                                            volumeActionRow
                                                                                    .getTitle())),
                                                    new ParameterValues.ParameterValue(
                                                            Float.valueOf(
                                                                    volumeActionRow.volumeLevel)));
                                        }
                                        VolumeActionActivity volumeActionActivity = this.this$0;
                                        Intent intent = new Intent();
                                        HashMap hashMap2 = new HashMap();
                                        hashMap.entrySet()
                                                .forEach(
                                                        new ParameterValues$$ExternalSyntheticLambda0(
                                                                hashMap2));
                                        intent.putExtra(
                                                "intent_params",
                                                new JSONObject(hashMap2).toString());
                                        volumeActionActivity.setResult(-1, intent);
                                        this.this$0.finish();
                                        return;
                                    default:
                                        this.this$0.finish();
                                        return;
                                }
                            }
                        });
        final int i2 = 1;
        ((Button) requireViewById2)
                .setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.asbase.routine.action.ui.VolumeActionActivity$onCreate$1
                            public final /* synthetic */ VolumeActionActivity this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                boolean z;
                                switch (i2) {
                                    case 0:
                                        HashMap hashMap = new HashMap();
                                        SecSoundMode secSoundMode = this.this$0.soundMode;
                                        if (secSoundMode != null) {
                                            hashMap.put(
                                                    "as_sound_mode",
                                                    new ParameterValues.ParameterValue(
                                                            Float.valueOf(
                                                                    secSoundMode.ringerMode)));
                                            CheckBox checkBox = secSoundMode.checkBox;
                                            if (checkBox == null) {
                                                Intrinsics
                                                        .throwUninitializedPropertyAccessException(
                                                                "checkBox");
                                                throw null;
                                            }
                                            if (checkBox.isChecked()) {
                                                int i22 = secSoundMode.ringerMode;
                                                int i3 =
                                                        SecVolumeValues
                                                                .EAR_PROTECT_LIMIT_LEVEL_FOR_ROUTINE;
                                                if (i22 == 2) {
                                                    z = true;
                                                    hashMap.put(
                                                            "as_do_not_change_volume",
                                                            new ParameterValues.ParameterValue(
                                                                    Boolean.valueOf(z)));
                                                }
                                            }
                                            z = false;
                                            hashMap.put(
                                                    "as_do_not_change_volume",
                                                    new ParameterValues.ParameterValue(
                                                            Boolean.valueOf(z)));
                                        }
                                        Iterator it = ((ArrayList) this.this$0.rowList).iterator();
                                        while (it.hasNext()) {
                                            VolumeActionRow volumeActionRow =
                                                    (VolumeActionRow) it.next();
                                            hashMap.put(
                                                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0
                                                            .m(
                                                                    "volume_level",
                                                                    VolumeActionActivity.getSuffix(
                                                                            volumeActionRow
                                                                                    .streamType,
                                                                            volumeActionRow
                                                                                    .getTitle())),
                                                    new ParameterValues.ParameterValue(
                                                            Float.valueOf(
                                                                    volumeActionRow.volumeLevel)));
                                        }
                                        VolumeActionActivity volumeActionActivity = this.this$0;
                                        Intent intent = new Intent();
                                        HashMap hashMap2 = new HashMap();
                                        hashMap.entrySet()
                                                .forEach(
                                                        new ParameterValues$$ExternalSyntheticLambda0(
                                                                hashMap2));
                                        intent.putExtra(
                                                "intent_params",
                                                new JSONObject(hashMap2).toString());
                                        volumeActionActivity.setResult(-1, intent);
                                        this.this$0.finish();
                                        return;
                                    default:
                                        this.this$0.finish();
                                        return;
                                }
                            }
                        });
    }

    @Override // android.app.Activity
    public final void onRestoreInstanceState(Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(savedInstanceState, "savedInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
        Iterator it = ((ArrayList) this.rowList).iterator();
        while (it.hasNext()) {
            VolumeActionRow volumeActionRow = (VolumeActionRow) it.next();
            int i =
                    savedInstanceState.getInt(
                            AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                                    "volume_level",
                                    getSuffix(
                                            volumeActionRow.streamType,
                                            volumeActionRow.getTitle())),
                            volumeActionRow.volumeLevel);
            SeslSeekBar seslSeekBar = volumeActionRow.seekBar;
            if (seslSeekBar == null) {
                Intrinsics.throwUninitializedPropertyAccessException("seekBar");
                throw null;
            }
            seslSeekBar.setProgress(i);
        }
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public final void onSaveInstanceState(Bundle outState) {
        Intrinsics.checkNotNullParameter(outState, "outState");
        super.onSaveInstanceState(outState);
        Iterator it = ((ArrayList) this.rowList).iterator();
        while (it.hasNext()) {
            VolumeActionRow volumeActionRow = (VolumeActionRow) it.next();
            outState.putInt(
                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                            "volume_level",
                            getSuffix(volumeActionRow.streamType, volumeActionRow.getTitle())),
                    volumeActionRow.volumeLevel);
        }
    }
}
