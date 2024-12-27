package com.samsung.android.settings.display.bixbyroutines;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SeslSeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.DefaultRingtonePreference$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.notification.zen.lifestyle.LifeStyleDND$$ExternalSyntheticOutline0;

import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EyeComfortRoutineActivity extends AppCompatActivity {
    public static final Uri BLUE_LIGHT_FILTER_OPACITY_URI =
            Settings.System.getUriFor("blue_light_filter_opacity");
    public static int sEyeComfortInitialAdaptiveMode;
    public static int sEyeComfortInitialScheduled;
    public static boolean sEyeComfortInitialState;
    public static int sEyeComfortInitialType;
    public static int sEyeComfortInitialValue;
    public RadioButton mAdaptiveButton;
    public float mBlueLightFilterAdaptive;
    public float mBlueLightFilterOpacity;
    public float mBlueLightFilterScheduled;
    public float mBlueLightFilterSwitch;
    public float mBlueLightFilterType;
    public Context mContext;
    public RadioButton mCustomButton;
    public final AnonymousClass1 mEyeComfortColorTempObserver =
            new ContentObserver(
                    new Handler()) { // from class:
                                     // com.samsung.android.settings.display.bixbyroutines.EyeComfortRoutineActivity.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    int i =
                            Settings.System.getInt(
                                    EyeComfortRoutineActivity.this.mContext.getContentResolver(),
                                    "blue_light_filter_opacity",
                                    5);
                    SeslSeekBar seslSeekBar = EyeComfortRoutineActivity.this.mEyeComfortSeekBar;
                    if (seslSeekBar == null || seslSeekBar.getProgress() == i) {
                        return;
                    }
                    Log.d("EyeComfortRoutineActivity", "mEyeComfortColorTempObserver: ");
                    EyeComfortRoutineActivity.this.mEyeComfortSeekBar.setProgress(i);
                }
            };
    public SeslSeekBar mEyeComfortSeekBar;
    public TextView mLeftLabel;
    public RadioButton mOffButton;
    public ParameterValues mParameterValues;
    public RadioGroup mRadioGroup;
    public TextView mRightLabel;
    public ConstraintLayout mSeekbarContainer;

    @Override // android.app.Activity
    public final void finish() {
        super.finish();
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                "blue_light_filter",
                sEyeComfortInitialState ? 1 : 0);
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                "blue_light_filter_adaptive_mode",
                sEyeComfortInitialAdaptiveMode);
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                "blue_light_filter_scheduled",
                sEyeComfortInitialScheduled);
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                "blue_light_filter_type",
                sEyeComfortInitialType);
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                "blue_light_filter_opacity",
                sEyeComfortInitialValue);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        final int i = 0;
        super.onCreate(bundle);
        final int i2 = 1;
        requestWindowFeature(1);
        this.mContext = getApplicationContext();
        this.mParameterValues = ParameterValues.fromIntent(getIntent());
        sEyeComfortInitialValue =
                Settings.System.getInt(
                        this.mContext.getContentResolver(), "blue_light_filter_opacity", 5);
        sEyeComfortInitialState =
                Settings.System.getInt(this.mContext.getContentResolver(), "blue_light_filter", 0)
                        != 0;
        sEyeComfortInitialType =
                Settings.System.getInt(
                        this.mContext.getContentResolver(), "blue_light_filter_type", 0);
        sEyeComfortInitialScheduled =
                Settings.System.getInt(
                        this.mContext.getContentResolver(),
                        "blue_light_filter_scheduled",
                        !Rune.supportBlueLightFilterAdaptiveMode() ? 1 : 0);
        sEyeComfortInitialAdaptiveMode =
                Settings.System.getInt(
                        this.mContext.getContentResolver(),
                        "blue_light_filter_adaptive_mode",
                        Rune.supportBlueLightFilterAdaptiveMode() ? 1 : 0);
        this.mBlueLightFilterSwitch =
                LifeStyleDND$$ExternalSyntheticOutline0.m(
                        1.0f, this.mParameterValues, "blue_light_filter_switch");
        if (Rune.supportBlueLightFilterAdaptiveMode()) {
            this.mBlueLightFilterAdaptive =
                    LifeStyleDND$$ExternalSyntheticOutline0.m(
                            1.0f, this.mParameterValues, "blue_light_filter_adaptive_mode");
            this.mBlueLightFilterScheduled =
                    LifeStyleDND$$ExternalSyntheticOutline0.m(
                            0.0f, this.mParameterValues, "blue_light_filter_scheduled");
        } else {
            this.mBlueLightFilterAdaptive = 0.0f;
            this.mBlueLightFilterScheduled = 1.0f;
        }
        this.mBlueLightFilterType =
                LifeStyleDND$$ExternalSyntheticOutline0.m(
                        0.0f, this.mParameterValues, "blue_light_filter_type");
        this.mBlueLightFilterOpacity =
                LifeStyleDND$$ExternalSyntheticOutline0.m(
                        Settings.System.getInt(
                                this.mContext.getContentResolver(), "blue_light_filter_opacity", 5),
                        this.mParameterValues,
                        "blue_light_filter_opacity");
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                "blue_light_filter",
                (int) this.mBlueLightFilterSwitch);
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                "blue_light_filter_adaptive_mode",
                (int) this.mBlueLightFilterAdaptive);
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                "blue_light_filter_scheduled",
                (int) this.mBlueLightFilterScheduled);
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                "blue_light_filter_type",
                (int) this.mBlueLightFilterType);
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                "blue_light_filter_opacity",
                (int) this.mBlueLightFilterOpacity);
        setEyeComfortState(((int) this.mBlueLightFilterSwitch) == 1);
        View inflate =
                getLayoutInflater()
                        .inflate(R.layout.sec_eye_comfort_routine_handler_dialog, (ViewGroup) null);
        this.mRadioGroup = (RadioGroup) inflate.findViewById(R.id.eye_comfort_routine_radio);
        this.mAdaptiveButton =
                (RadioButton) inflate.findViewById(R.id.eye_comfort_routine_radio_button_adaptive);
        this.mCustomButton =
                (RadioButton) inflate.findViewById(R.id.eye_comfort_routine_radio_button_custom);
        this.mOffButton =
                (RadioButton) inflate.findViewById(R.id.eye_comfort_routine_radio_button_off);
        this.mSeekbarContainer =
                (ConstraintLayout)
                        inflate.findViewById(R.id.eye_comfort_routine_custom_seekbar_container);
        this.mEyeComfortSeekBar =
                (SeslSeekBar) inflate.findViewById(R.id.eye_comfort_routine_seekbar);
        this.mLeftLabel = (TextView) inflate.findViewById(R.id.left_label);
        this.mRightLabel = (TextView) inflate.findViewById(R.id.right_label);
        if (!Rune.supportBlueLightFilterAdaptiveMode()) {
            this.mAdaptiveButton.setVisibility(8);
            this.mCustomButton.setText(R.string.switch_on_text);
        }
        if (((int) this.mBlueLightFilterSwitch) == 0) {
            this.mOffButton.setChecked(true);
        } else if (((int) this.mBlueLightFilterScheduled) == 1) {
            this.mCustomButton.setChecked(true);
        } else {
            this.mAdaptiveButton.setChecked(true);
        }
        setEyeComfortSeekbarContainer(this.mCustomButton.isChecked());
        this.mRightLabel.setText(
                this.mContext.getString(
                        R.string.sec_lockscreen_noti_card_seekbar_percentage,
                        Integer.valueOf((int) (this.mBlueLightFilterOpacity * 10.0f))));
        SeslSeekBar seslSeekBar = this.mEyeComfortSeekBar;
        synchronized (seslSeekBar) {
            seslSeekBar.setProgress(seslSeekBar.mProgress + 1);
        }
        this.mEyeComfortSeekBar.setMax(10);
        this.mEyeComfortSeekBar.setMode(5);
        this.mEyeComfortSeekBar.setSeamless(true);
        this.mEyeComfortSeekBar.setProgress((int) this.mBlueLightFilterOpacity);
        this.mEyeComfortSeekBar.setOnSeekBarChangeListener(
                new SeslSeekBar
                        .OnSeekBarChangeListener() { // from class:
                                                     // com.samsung.android.settings.display.bixbyroutines.EyeComfortRoutineActivity.3
                    @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
                    public final void onProgressChanged(
                            SeslSeekBar seslSeekBar2, int i3, boolean z) {
                        EyeComfortRoutineActivity eyeComfortRoutineActivity =
                                EyeComfortRoutineActivity.this;
                        Settings.System.putInt(
                                eyeComfortRoutineActivity.mContext.getContentResolver(),
                                "blue_light_filter_opacity",
                                i3);
                        eyeComfortRoutineActivity.mRightLabel.setText(
                                eyeComfortRoutineActivity.mContext.getString(
                                        R.string.sec_lockscreen_noti_card_seekbar_percentage,
                                        Integer.valueOf(i3 * 10)));
                        Preference$$ExternalSyntheticOutline0.m(
                                new StringBuilder("eye comfort shield value: "),
                                i3,
                                "EyeComfortRoutineActivity");
                    }

                    @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
                    public final void onStopTrackingTouch(SeslSeekBar seslSeekBar2) {
                        EyeComfortRoutineActivity eyeComfortRoutineActivity =
                                EyeComfortRoutineActivity.this;
                        Settings.System.putInt(
                                eyeComfortRoutineActivity.mContext.getContentResolver(),
                                "blue_light_filter_opacity",
                                seslSeekBar2.getProgress());
                        eyeComfortRoutineActivity.mRightLabel.setText(
                                eyeComfortRoutineActivity.mContext.getString(
                                        R.string.sec_lockscreen_noti_card_seekbar_percentage,
                                        Integer.valueOf(seslSeekBar2.getProgress() * 10)));
                    }

                    @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
                    public final void onStartTrackingTouch(SeslSeekBar seslSeekBar2) {}
                });
        this.mRadioGroup.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() { // from class:
                                                     // com.samsung.android.settings.display.bixbyroutines.EyeComfortRoutineActivity$$ExternalSyntheticLambda2
                    @Override // android.widget.RadioGroup.OnCheckedChangeListener
                    public final void onCheckedChanged(RadioGroup radioGroup, int i3) {
                        EyeComfortRoutineActivity eyeComfortRoutineActivity =
                                EyeComfortRoutineActivity.this;
                        if (i3 == R.id.eye_comfort_routine_radio_button_adaptive) {
                            Settings.System.putInt(
                                    eyeComfortRoutineActivity.mContext.getContentResolver(),
                                    "blue_light_filter",
                                    1);
                            Settings.System.putInt(
                                    eyeComfortRoutineActivity.mContext.getContentResolver(),
                                    "blue_light_filter_adaptive_mode",
                                    1);
                            Settings.System.putInt(
                                    eyeComfortRoutineActivity.mContext.getContentResolver(),
                                    "blue_light_filter_scheduled",
                                    0);
                            eyeComfortRoutineActivity.setEyeComfortSeekbarContainer(false);
                            eyeComfortRoutineActivity.setEyeComfortState(true);
                            return;
                        }
                        if (i3 != R.id.eye_comfort_routine_radio_button_custom) {
                            Settings.System.putInt(
                                    eyeComfortRoutineActivity.mContext.getContentResolver(),
                                    "blue_light_filter",
                                    0);
                            eyeComfortRoutineActivity.setEyeComfortSeekbarContainer(false);
                            eyeComfortRoutineActivity.setEyeComfortState(false);
                            return;
                        }
                        Settings.System.putInt(
                                eyeComfortRoutineActivity.mContext.getContentResolver(),
                                "blue_light_filter",
                                1);
                        Settings.System.putInt(
                                eyeComfortRoutineActivity.mContext.getContentResolver(),
                                "blue_light_filter_adaptive_mode",
                                0);
                        Settings.System.putInt(
                                eyeComfortRoutineActivity.mContext.getContentResolver(),
                                "blue_light_filter_scheduled",
                                1);
                        Settings.System.putInt(
                                eyeComfortRoutineActivity.mContext.getContentResolver(),
                                "blue_light_filter_type",
                                0);
                        Settings.System.putInt(
                                eyeComfortRoutineActivity.mContext.getContentResolver(),
                                "blue_light_filter_opacity",
                                eyeComfortRoutineActivity.mEyeComfortSeekBar.getProgress());
                        eyeComfortRoutineActivity.setEyeComfortSeekbarContainer(true);
                        eyeComfortRoutineActivity.setEyeComfortState(true);
                    }
                });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.sec_eye_comfort_title);
        builder.setView(inflate);
        builder.setPositiveButton(
                R.string.done,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.display.bixbyroutines.EyeComfortRoutineActivity$$ExternalSyntheticLambda0
                    public final /* synthetic */ EyeComfortRoutineActivity f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i3) {
                        int i4 = i;
                        EyeComfortRoutineActivity eyeComfortRoutineActivity = this.f$0;
                        switch (i4) {
                            case 0:
                                eyeComfortRoutineActivity.mParameterValues.put(
                                        "blue_light_filter_switch",
                                        Float.valueOf(
                                                Settings.System.getInt(
                                                        eyeComfortRoutineActivity.mContext
                                                                .getContentResolver(),
                                                        "blue_light_filter",
                                                        0)));
                                if (Rune.supportBlueLightFilterAdaptiveMode()) {
                                    eyeComfortRoutineActivity.mParameterValues.put(
                                            "blue_light_filter_adaptive_mode",
                                            Float.valueOf(
                                                    Settings.System.getInt(
                                                            eyeComfortRoutineActivity.mContext
                                                                    .getContentResolver(),
                                                            "blue_light_filter_adaptive_mode",
                                                            1)));
                                    eyeComfortRoutineActivity.mParameterValues.put(
                                            "blue_light_filter_scheduled",
                                            Float.valueOf(
                                                    Settings.System.getInt(
                                                            eyeComfortRoutineActivity.mContext
                                                                    .getContentResolver(),
                                                            "blue_light_filter_scheduled",
                                                            0)));
                                } else {
                                    eyeComfortRoutineActivity.mParameterValues.put(
                                            "blue_light_filter_adaptive_mode", Float.valueOf(0.0f));
                                    eyeComfortRoutineActivity.mParameterValues.put(
                                            "blue_light_filter_scheduled", Float.valueOf(1.0f));
                                }
                                eyeComfortRoutineActivity.mParameterValues.put(
                                        "blue_light_filter_type",
                                        Float.valueOf(
                                                Settings.System.getInt(
                                                        eyeComfortRoutineActivity.mContext
                                                                .getContentResolver(),
                                                        "blue_light_filter_type",
                                                        0)));
                                eyeComfortRoutineActivity.mParameterValues.put(
                                        "blue_light_filter_opacity",
                                        Float.valueOf(
                                                Settings.System.getInt(
                                                        eyeComfortRoutineActivity.mContext
                                                                .getContentResolver(),
                                                        "blue_light_filter_opacity",
                                                        5)));
                                ParameterValues parameterValues =
                                        eyeComfortRoutineActivity.mParameterValues;
                                Intent intent = new Intent();
                                intent.putExtra("intent_params", parameterValues.toJsonString());
                                eyeComfortRoutineActivity.setResult(-1, intent);
                                eyeComfortRoutineActivity.finish();
                                break;
                            default:
                                Uri uri = EyeComfortRoutineActivity.BLUE_LIGHT_FILTER_OPACITY_URI;
                                eyeComfortRoutineActivity.finish();
                                break;
                        }
                    }
                });
        builder.setNegativeButton(
                R.string.cancel,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.display.bixbyroutines.EyeComfortRoutineActivity$$ExternalSyntheticLambda0
                    public final /* synthetic */ EyeComfortRoutineActivity f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i3) {
                        int i4 = i2;
                        EyeComfortRoutineActivity eyeComfortRoutineActivity = this.f$0;
                        switch (i4) {
                            case 0:
                                eyeComfortRoutineActivity.mParameterValues.put(
                                        "blue_light_filter_switch",
                                        Float.valueOf(
                                                Settings.System.getInt(
                                                        eyeComfortRoutineActivity.mContext
                                                                .getContentResolver(),
                                                        "blue_light_filter",
                                                        0)));
                                if (Rune.supportBlueLightFilterAdaptiveMode()) {
                                    eyeComfortRoutineActivity.mParameterValues.put(
                                            "blue_light_filter_adaptive_mode",
                                            Float.valueOf(
                                                    Settings.System.getInt(
                                                            eyeComfortRoutineActivity.mContext
                                                                    .getContentResolver(),
                                                            "blue_light_filter_adaptive_mode",
                                                            1)));
                                    eyeComfortRoutineActivity.mParameterValues.put(
                                            "blue_light_filter_scheduled",
                                            Float.valueOf(
                                                    Settings.System.getInt(
                                                            eyeComfortRoutineActivity.mContext
                                                                    .getContentResolver(),
                                                            "blue_light_filter_scheduled",
                                                            0)));
                                } else {
                                    eyeComfortRoutineActivity.mParameterValues.put(
                                            "blue_light_filter_adaptive_mode", Float.valueOf(0.0f));
                                    eyeComfortRoutineActivity.mParameterValues.put(
                                            "blue_light_filter_scheduled", Float.valueOf(1.0f));
                                }
                                eyeComfortRoutineActivity.mParameterValues.put(
                                        "blue_light_filter_type",
                                        Float.valueOf(
                                                Settings.System.getInt(
                                                        eyeComfortRoutineActivity.mContext
                                                                .getContentResolver(),
                                                        "blue_light_filter_type",
                                                        0)));
                                eyeComfortRoutineActivity.mParameterValues.put(
                                        "blue_light_filter_opacity",
                                        Float.valueOf(
                                                Settings.System.getInt(
                                                        eyeComfortRoutineActivity.mContext
                                                                .getContentResolver(),
                                                        "blue_light_filter_opacity",
                                                        5)));
                                ParameterValues parameterValues =
                                        eyeComfortRoutineActivity.mParameterValues;
                                Intent intent = new Intent();
                                intent.putExtra("intent_params", parameterValues.toJsonString());
                                eyeComfortRoutineActivity.setResult(-1, intent);
                                eyeComfortRoutineActivity.finish();
                                break;
                            default:
                                Uri uri = EyeComfortRoutineActivity.BLUE_LIGHT_FILTER_OPACITY_URI;
                                eyeComfortRoutineActivity.finish();
                                break;
                        }
                    }
                });
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(true);
        create.setOnDismissListener(
                new DialogInterface
                        .OnDismissListener() { // from class:
                                               // com.samsung.android.settings.display.bixbyroutines.EyeComfortRoutineActivity.2
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        BixbyRoutineActionHandler bixbyRoutineActionHandler =
                                BixbyRoutineActionHandler.sInstance;
                        EyeComfortRoutineActivity.this.finish();
                    }
                });
        create.show();
        BixbyRoutineActionHandler bixbyRoutineActionHandler = BixbyRoutineActionHandler.sInstance;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        this.mContext
                .getContentResolver()
                .unregisterContentObserver(this.mEyeComfortColorTempObserver);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        super.onPause();
        this.mContext
                .getContentResolver()
                .unregisterContentObserver(this.mEyeComfortColorTempObserver);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        BLUE_LIGHT_FILTER_OPACITY_URI, false, this.mEyeComfortColorTempObserver);
    }

    public final void setEyeComfortSeekbarContainer(boolean z) {
        this.mSeekbarContainer.setEnabled(z);
        this.mEyeComfortSeekBar.setEnabled(z);
        this.mLeftLabel.setEnabled(z);
        this.mLeftLabel.setAlpha(z ? 1.0f : 0.4f);
        this.mRightLabel.setEnabled(z);
        this.mRightLabel.setAlpha(z ? 1.0f : 0.4f);
    }

    public final void setEyeComfortState(boolean z) {
        Settings.System.putInt(getContentResolver(), "blue_light_filter", z ? 1 : 0);
        Intent intent = new Intent();
        DefaultRingtonePreference$$ExternalSyntheticOutline0.m(
                "com.samsung.android.bluelightfilter",
                "com.samsung.android.bluelightfilter.BlueLightFilterService",
                intent);
        intent.putExtra("BLUE_LIGHT_FILTER_SERVICE_TYPE", z ? 24 : 25);
        this.mContext.startService(intent);
    }
}
