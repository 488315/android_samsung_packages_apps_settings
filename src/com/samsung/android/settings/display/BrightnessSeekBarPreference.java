package com.samsung.android.settings.display;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.database.ContentObserver;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SeslAbsSeekBar;
import androidx.appcompat.widget.SeslSeekBar;
import androidx.core.util.Consumer;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.SliderUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BrightnessSeekBarPreference extends DisplayCustomPreference
        implements View.OnFocusChangeListener {
    public static final int START_POINT_FOR_LIMIT_WARNING;
    public final AnonymousClass1 mAutoBrightnessDetailObserver;
    public LottieAnimationView mBrightnessIcon;
    public final BrightnessManager mBrightnessManager;
    public final AnonymousClass1 mBrightnessModeObserver;
    public SeslSeekBar mBrightnessSeekBar;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public AlertDialog mDialogForMaxBrightness;
    public TextView mHBMText;
    public boolean mInitFinish;
    public boolean mInternalChange;
    public boolean mIsAutomaticMode;
    public boolean mIsMaxBrightnessDialogShown;
    public boolean mIsRestricted;
    public final AnonymousClass1 mMaxBrightnessDialogObserver;
    public TextView mOverheatTextView;
    public ValueAnimator mSliderAnimator;

    static {
        START_POINT_FOR_LIMIT_WARNING =
                (int)
                        Math.floor(
                                ((Rune.SUPPORT_REDUCED_BRIGHTNESS_LIMIT ? 85 : 90) * 2.6738688E8d)
                                        / 100.0d);
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.samsung.android.settings.display.BrightnessSeekBarPreference$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.samsung.android.settings.display.BrightnessSeekBarPreference$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r8v1, types: [com.samsung.android.settings.display.BrightnessSeekBarPreference$1] */
    /* JADX WARN: Type inference failed for: r8v2, types: [com.samsung.android.settings.display.BrightnessSeekBarPreference$1] */
    /* JADX WARN: Type inference failed for: r8v3, types: [com.samsung.android.settings.display.BrightnessSeekBarPreference$1] */
    public BrightnessSeekBarPreference(Context context, AttributeSet attributeSet) {
        super(
                context,
                attributeSet,
                0,
                R.layout.sec_preference_seekbar_brightness,
                R.id.brightness_seekbar);
        final int i = 0;
        this.mBrightnessModeObserver =
                new ContentObserver(
                        this,
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.display.BrightnessSeekBarPreference.1
                    public final /* synthetic */ BrightnessSeekBarPreference this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i) {
                            case 0:
                                this.this$0.onBrightnessModeChanged();
                                break;
                            case 1:
                                this.this$0.onBrightnessModeChanged();
                                break;
                            default:
                                BrightnessSeekBarPreference brightnessSeekBarPreference =
                                        this.this$0;
                                brightnessSeekBarPreference.mIsMaxBrightnessDialogShown =
                                        Settings.System.getInt(
                                                        brightnessSeekBarPreference.mContext
                                                                .getContentResolver(),
                                                        "shown_max_brightness_dialog",
                                                        0)
                                                != 0;
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mAutoBrightnessDetailObserver =
                new ContentObserver(
                        this,
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.display.BrightnessSeekBarPreference.1
                    public final /* synthetic */ BrightnessSeekBarPreference this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i2) {
                            case 0:
                                this.this$0.onBrightnessModeChanged();
                                break;
                            case 1:
                                this.this$0.onBrightnessModeChanged();
                                break;
                            default:
                                BrightnessSeekBarPreference brightnessSeekBarPreference =
                                        this.this$0;
                                brightnessSeekBarPreference.mIsMaxBrightnessDialogShown =
                                        Settings.System.getInt(
                                                        brightnessSeekBarPreference.mContext
                                                                .getContentResolver(),
                                                        "shown_max_brightness_dialog",
                                                        0)
                                                != 0;
                                break;
                        }
                    }
                };
        final int i3 = 2;
        this.mMaxBrightnessDialogObserver =
                new ContentObserver(
                        this,
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.display.BrightnessSeekBarPreference.1
                    public final /* synthetic */ BrightnessSeekBarPreference this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i3) {
                            case 0:
                                this.this$0.onBrightnessModeChanged();
                                break;
                            case 1:
                                this.this$0.onBrightnessModeChanged();
                                break;
                            default:
                                BrightnessSeekBarPreference brightnessSeekBarPreference =
                                        this.this$0;
                                brightnessSeekBarPreference.mIsMaxBrightnessDialogShown =
                                        Settings.System.getInt(
                                                        brightnessSeekBarPreference.mContext
                                                                .getContentResolver(),
                                                        "shown_max_brightness_dialog",
                                                        0)
                                                != 0;
                                break;
                        }
                    }
                };
        this.mInitFinish = false;
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        final int i4 = 0;
        final int i5 = 1;
        this.mBrightnessManager =
                new BrightnessManager(
                        context,
                        new Consumer(
                                this) { // from class:
                                        // com.samsung.android.settings.display.BrightnessSeekBarPreference$$ExternalSyntheticLambda0
                            public final /* synthetic */ BrightnessSeekBarPreference f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // androidx.core.util.Consumer
                            public final void accept(Object obj) {
                                TextView textView;
                                BrightnessSeekBarPreference brightnessSeekBarPreference = this.f$0;
                                switch (i4) {
                                    case 0:
                                        int i6 =
                                                BrightnessSeekBarPreference
                                                        .START_POINT_FOR_LIMIT_WARNING;
                                        brightnessSeekBarPreference.getClass();
                                        Log.d(
                                                "BrightnessSeekBarPreference",
                                                "onBrightnessChanged()  brightnessValue: "
                                                        + ((Integer) obj));
                                        brightnessSeekBarPreference.mInternalChange = false;
                                        if (brightnessSeekBarPreference.mBrightnessSeekBar
                                                != null) {
                                            brightnessSeekBarPreference.animateSliderTo(
                                                    brightnessSeekBarPreference
                                                            .getCurrentProgress());
                                            break;
                                        }
                                        break;
                                    default:
                                        int i7 =
                                                BrightnessSeekBarPreference
                                                        .START_POINT_FOR_LIMIT_WARNING;
                                        brightnessSeekBarPreference.getClass();
                                        if (!((Boolean) obj).booleanValue()
                                                && (textView = brightnessSeekBarPreference.mHBMText)
                                                        != null) {
                                            textView.setVisibility(8);
                                            break;
                                        }
                                        break;
                                }
                            }
                        },
                        new Consumer(
                                this) { // from class:
                                        // com.samsung.android.settings.display.BrightnessSeekBarPreference$$ExternalSyntheticLambda0
                            public final /* synthetic */ BrightnessSeekBarPreference f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // androidx.core.util.Consumer
                            public final void accept(Object obj) {
                                TextView textView;
                                BrightnessSeekBarPreference brightnessSeekBarPreference = this.f$0;
                                switch (i5) {
                                    case 0:
                                        int i6 =
                                                BrightnessSeekBarPreference
                                                        .START_POINT_FOR_LIMIT_WARNING;
                                        brightnessSeekBarPreference.getClass();
                                        Log.d(
                                                "BrightnessSeekBarPreference",
                                                "onBrightnessChanged()  brightnessValue: "
                                                        + ((Integer) obj));
                                        brightnessSeekBarPreference.mInternalChange = false;
                                        if (brightnessSeekBarPreference.mBrightnessSeekBar
                                                != null) {
                                            brightnessSeekBarPreference.animateSliderTo(
                                                    brightnessSeekBarPreference
                                                            .getCurrentProgress());
                                            break;
                                        }
                                        break;
                                    default:
                                        int i7 =
                                                BrightnessSeekBarPreference
                                                        .START_POINT_FOR_LIMIT_WARNING;
                                        brightnessSeekBarPreference.getClass();
                                        if (!((Boolean) obj).booleanValue()
                                                && (textView = brightnessSeekBarPreference.mHBMText)
                                                        != null) {
                                            textView.setVisibility(8);
                                            break;
                                        }
                                        break;
                                }
                            }
                        });
    }

    public static void updateDualColorRange(SeslSeekBar seslSeekBar) {
        if (seslSeekBar == null) {
            return;
        }
        StringBuilder sb = new StringBuilder("updateDualColorRange >> limit : ");
        int i = START_POINT_FOR_LIMIT_WARNING;
        sb.append(i);
        Log.d("BrightnessSeekBarPreference", sb.toString());
        int parseColor = Color.parseColor("#f7c0bd");
        int parseColor2 = Color.parseColor("#f1462f");
        ColorStateList colorToColorStateList = SeslAbsSeekBar.colorToColorStateList(parseColor);
        ColorStateList colorToColorStateList2 = SeslAbsSeekBar.colorToColorStateList(parseColor2);
        if (!colorToColorStateList.equals(seslSeekBar.mOverlapNormalProgressColor)) {
            seslSeekBar.mOverlapNormalProgressColor = colorToColorStateList;
        }
        if (!colorToColorStateList2.equals(seslSeekBar.mOverlapActivatedProgressColor)) {
            seslSeekBar.mOverlapActivatedProgressColor = colorToColorStateList2;
        }
        seslSeekBar.updateDualColorMode();
        seslSeekBar.invalidate();
        seslSeekBar.setOverlapPointForDualColor(i - 1);
    }

    public final void animateSliderTo(int i) {
        if (this.mBrightnessSeekBar != null ? this.mInitFinish : false) {
            ValueAnimator valueAnimator = this.mSliderAnimator;
            if (valueAnimator != null && valueAnimator.isStarted()) {
                this.mSliderAnimator.cancel();
            }
            int progress = this.mBrightnessSeekBar.getProgress();
            if (progress == i) {
                return;
            }
            ValueAnimator ofInt = ValueAnimator.ofInt(progress, i);
            this.mSliderAnimator = ofInt;
            ofInt.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // com.samsung.android.settings.display.BrightnessSeekBarPreference$$ExternalSyntheticLambda7
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            BrightnessSeekBarPreference brightnessSeekBarPreference =
                                    BrightnessSeekBarPreference.this;
                            int i2 = BrightnessSeekBarPreference.START_POINT_FOR_LIMIT_WARNING;
                            brightnessSeekBarPreference.getClass();
                            int intValue = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                            brightnessSeekBarPreference.updateProgress(
                                    MathUtils.lerpInv(0.0f, 2.6738688E8f, intValue), intValue);
                        }
                    });
            this.mSliderAnimator.setDuration(
                    Settings.System.getInt(
                                            this.mBrightnessManager.mContentResolver,
                                            "auto_brightness_transition_time",
                                            3000)
                                    >= 0
                            ? r3
                            : 3000);
            this.mSliderAnimator.start();
        }
    }

    public final int getCurrentProgress() {
        BrightnessManager brightnessManager = this.mBrightnessManager;
        return (int)
                MathUtils.lerp(
                        0,
                        267386880,
                        MathUtils.lerpInv(
                                brightnessManager.mScreenBrightnessMinimum,
                                brightnessManager.mScreenBrightnessMaximum,
                                Settings.System.getInt(
                                        brightnessManager.mContentResolver,
                                        "screen_brightness",
                                        100)));
    }

    @Override // com.samsung.android.settings.display.DisplayCustomPreference,
              // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        this.mIsAutomaticMode =
                Settings.System.getInt(this.mContentResolver, "screen_brightness_mode", 0) == 1;
        super.onBindViewHolder(preferenceViewHolder);
        SeslSeekBar seslSeekBar =
                (SeslSeekBar) preferenceViewHolder.findViewById(R.id.brightness_seekbar);
        seslSeekBar.setEnabled(isEnabled() && !this.mIsRestricted);
        seslSeekBar.setOnSeekBarChangeListener(this);
        if (seslSeekBar != this.mBrightnessSeekBar) {
            if (preferenceViewHolder.mIsViewHolderRecoilEffectEnabled) {
                preferenceViewHolder.mIsViewHolderRecoilEffectEnabled = false;
            }
            this.mBrightnessSeekBar = seslSeekBar;
            seslSeekBar.setMode(5);
            this.mBrightnessSeekBar.setOnFocusChangeListener(this);
            this.mBrightnessSeekBar.setSoundEffectsEnabled(true);
            this.mBrightnessSeekBar.setOnKeyListener(this);
            this.mBrightnessSeekBar.addOnLayoutChangeListener(
                    new View
                            .OnLayoutChangeListener() { // from class:
                                                        // com.samsung.android.settings.display.BrightnessSeekBarPreference$$ExternalSyntheticLambda2
                        @Override // android.view.View.OnLayoutChangeListener
                        public final void onLayoutChange(
                                View view,
                                int i,
                                int i2,
                                int i3,
                                int i4,
                                int i5,
                                int i6,
                                int i7,
                                int i8) {
                            final BrightnessSeekBarPreference brightnessSeekBarPreference =
                                    BrightnessSeekBarPreference.this;
                            int i9 = BrightnessSeekBarPreference.START_POINT_FOR_LIMIT_WARNING;
                            brightnessSeekBarPreference.getClass();
                            view.post(
                                    new Runnable() { // from class:
                                                     // com.samsung.android.settings.display.BrightnessSeekBarPreference$$ExternalSyntheticLambda8
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            BrightnessSeekBarPreference
                                                    brightnessSeekBarPreference2 =
                                                            BrightnessSeekBarPreference.this;
                                            AlertDialog alertDialog =
                                                    brightnessSeekBarPreference2
                                                            .mDialogForMaxBrightness;
                                            if (alertDialog == null || !alertDialog.isShowing()) {
                                                return;
                                            }
                                            Log.d(
                                                    "BrightnessSeekBarPreference",
                                                    "Update brightness dialog");
                                            brightnessSeekBarPreference2.mDialogForMaxBrightness
                                                    .semSetAnchor(
                                                            brightnessSeekBarPreference2
                                                                    .mBrightnessSeekBar);
                                        }
                                    });
                        }
                    });
            this.mBrightnessSeekBar.setMin(0);
            this.mBrightnessSeekBar.setMax(267386880);
            updateDualColorRange(this.mBrightnessSeekBar);
            int currentProgress = getCurrentProgress();
            updateProgress(MathUtils.lerpInv(0.0f, 2.6738688E8f, currentProgress), currentProgress);
        }
        LottieAnimationView lottieAnimationView =
                (LottieAnimationView) preferenceViewHolder.findViewById(R.id.brightness_icon);
        this.mBrightnessIcon = lottieAnimationView;
        lottieAnimationView.setAnimation(
                Rune.SUPPORT_REDUCED_BRIGHTNESS_LIMIT
                        ? "Brightness_icon_85.json"
                        : "Brightness_icon_90.json");
        preferenceViewHolder.itemView.setOnKeyListener(this);
        this.mOverheatTextView =
                (TextView)
                        preferenceViewHolder.findViewById(R.id.brightnesslevel_and_overheatwarning);
        this.mHBMText = (TextView) preferenceViewHolder.findViewById(R.id.hbm_mode_text);
        StringBuilder sb = new StringBuilder("onbindview mIsAutomaticMode : ");
        sb.append(this.mIsAutomaticMode);
        sb.append(", mIsHBMOnOff : ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                sb, this.mBrightnessManager.mIsHBMOnOff, "BrightnessSeekBarPreference");
        this.mInitFinish = true;
        this.mIsMaxBrightnessDialogShown =
                Settings.System.getInt(this.mContentResolver, "shown_max_brightness_dialog", 0)
                        != 0;
    }

    public final void onBrightnessModeChanged() {
        TextView textView;
        int i =
                Settings.System.getInt(this.mContentResolver, "screen_brightness_mode", 0) == 1
                        ? 1
                        : 0;
        if (!Rune.isDualFolderType(this.mContext)
                || this.mContext.getResources().getConfiguration().hardKeyboardHidden != 2) {
            this.mIsAutomaticMode = i == 1;
            if (Settings.System.getInt(this.mContentResolver, "screen_brightness_mode", 0) != i) {
                if (this.mIsAutomaticMode) {
                    BrightnessManager brightnessManager = this.mBrightnessManager;
                    int integer =
                            brightnessManager
                                    .mContext
                                    .getResources()
                                    .getInteger(
                                            android.R.integer
                                                    .config_vibratorControlServiceDumpSizeLimit);
                    if (Rune.supportAutoBrightness(brightnessManager.mContext)
                            && integer > brightnessManager.mScreenBrightnessMaximum) {
                        SecDisplayUtils.setAutoBrightnessWithCamera(this.mContext);
                    }
                }
                Settings.System.putInt(this.mContentResolver, "screen_brightness_mode", i);
            }
        }
        if (!this.mIsAutomaticMode && (textView = this.mHBMText) != null) {
            textView.setVisibility(8);
        }
        SeslSeekBar seslSeekBar = this.mBrightnessSeekBar;
        if (seslSeekBar != null) {
            updateDualColorRange(seslSeekBar);
            int currentProgress = getCurrentProgress();
            if (this.mInternalChange) {
                updateProgress(
                        MathUtils.lerpInv(0.0f, 2.6738688E8f, currentProgress), currentProgress);
            } else {
                animateSliderTo(currentProgress);
            }
        }
    }

    @Override // androidx.preference.Preference
    public final void onClick() {
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced;
        if (!this.mIsRestricted
                || (checkIfRestrictionEnforced =
                                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                                        this.mContext,
                                        UserHandle.myUserId(),
                                        "no_config_brightness"))
                        == null) {
            return;
        }
        RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                this.mContext, checkIfRestrictionEnforced);
    }

    @Override // androidx.preference.Preference
    public final void onDetached() {
        AlertDialog alertDialog = this.mDialogForMaxBrightness;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mDialogForMaxBrightness.dismiss();
        }
        super.onDetached();
    }

    @Override // android.view.View.OnFocusChangeListener
    public final void onFocusChange(View view, boolean z) {
        if (z || this.mBrightnessSeekBar == null) {
            return;
        }
        StringBuilder sb = new StringBuilder("on stop tracking touch progress = ");
        sb.append(this.mBrightnessSeekBar.getProgress());
        sb.append(", brightness = ");
        BrightnessManager brightnessManager = this.mBrightnessManager;
        sb.append(
                (int)
                        MathUtils.lerp(
                                brightnessManager.mScreenBrightnessMinimum,
                                brightnessManager.mScreenBrightnessMaximum,
                                MathUtils.lerpInv(
                                        0.0f,
                                        2.6738688E8f,
                                        this.mBrightnessSeekBar.getProgress())));
        Log.d("BrightnessSeekBarPreference", sb.toString());
        updateBrightness();
    }

    @Override // androidx.preference.Preference
    public final void onInitializeAccessibilityNodeInfo(
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        accessibilityNodeInfoCompat.mInfo.removeAction(
                (AccessibilityNodeInfo.AccessibilityAction)
                        new AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                                        16, (CharSequence) null)
                                .mAction);
        accessibilityNodeInfoCompat.setClickable(false);
    }

    @Override // com.samsung.android.settings.display.DisplayCustomPreference,
              // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 && keyEvent.getAction() != 0) {
            return false;
        }
        if ((i != 21 && i != 22) || this.mBrightnessManager == null) {
            return false;
        }
        if (keyEvent.getAction() == 1) {
            BrightnessManager brightnessManager = this.mBrightnessManager;
            Settings.System.putInt(
                    brightnessManager.mContentResolver,
                    "screen_brightness",
                    (int)
                            MathUtils.lerp(
                                    brightnessManager.mScreenBrightnessMinimum,
                                    brightnessManager.mScreenBrightnessMaximum,
                                    MathUtils.lerpInv(
                                            0.0f,
                                            2.6738688E8f,
                                            this.mBrightnessSeekBar.getProgress())));
        }
        return this.mBrightnessSeekBar.onKeyDown(i, keyEvent);
    }

    @Override // com.samsung.android.settings.display.DisplayCustomPreference,
              // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
    public final void onProgressChanged(SeslSeekBar seslSeekBar, int i, boolean z) {
        AlertDialog alertDialog;
        super.onProgressChanged(seslSeekBar, i, z);
        if (Settings.System.getInt(this.mContentResolver, "screen_brightness_mode", 0) != 1) {
            boolean z2 = this.mIsMaxBrightnessDialogShown;
            if (!z2 && i > START_POINT_FOR_LIMIT_WARNING && z) {
                if (this.mDialogForMaxBrightness != null) {
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
                BrightnessManager brightnessManager = this.mBrightnessManager;
                int integer =
                        brightnessManager
                                .mContext
                                .getResources()
                                .getInteger(
                                        android.R.integer
                                                .config_vibratorControlServiceDumpSizeLimit);
                if (!Rune.supportAutoBrightness(brightnessManager.mContext)
                        || integer <= brightnessManager.mScreenBrightnessMaximum) {
                    builder.setTitle(ApnSettings.MVNO_NONE)
                            .setMessage(R.string.sec_brightness_maximum_alert_msg)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(
                                    android.R.string.ok,
                                    new BrightnessSeekBarPreference$$ExternalSyntheticLambda4())
                            .setOnDismissListener(
                                    new DialogInterface
                                            .OnDismissListener() { // from class:
                                                                   // com.samsung.android.settings.display.BrightnessSeekBarPreference$$ExternalSyntheticLambda6
                                        @Override // android.content.DialogInterface.OnDismissListener
                                        public final void onDismiss(
                                                DialogInterface dialogInterface) {
                                            BrightnessSeekBarPreference
                                                    brightnessSeekBarPreference =
                                                            BrightnessSeekBarPreference.this;
                                            brightnessSeekBarPreference.mDialogForMaxBrightness =
                                                    null;
                                            Settings.System.putInt(
                                                    brightnessSeekBarPreference.mContentResolver,
                                                    "shown_max_brightness_dialog",
                                                    1);
                                            brightnessSeekBarPreference
                                                            .mIsMaxBrightnessDialogShown =
                                                    true;
                                            StringBuilder sb =
                                                    new StringBuilder(
                                                            "setPositiveButton >> limit : ");
                                            int i2 =
                                                    BrightnessSeekBarPreference
                                                            .START_POINT_FOR_LIMIT_WARNING;
                                            Preference$$ExternalSyntheticOutline0.m(
                                                    sb, i2, "BrightnessSeekBarPreference");
                                            float lerpInv =
                                                    MathUtils.lerpInv(0.0f, 2.6738688E8f, i2);
                                            BrightnessManager brightnessManager2 =
                                                    brightnessSeekBarPreference.mBrightnessManager;
                                            brightnessManager2.mDisplayManager
                                                    .semSetTemporaryBrightness(
                                                            (int)
                                                                    MathUtils.lerp(
                                                                            brightnessManager2
                                                                                    .mScreenBrightnessMinimum,
                                                                            brightnessManager2
                                                                                    .mScreenBrightnessMaximum,
                                                                            lerpInv));
                                            brightnessSeekBarPreference.updateProgress(lerpInv, i2);
                                            brightnessSeekBarPreference.updateBrightness();
                                        }
                                    });
                } else {
                    final int autoBrightnessDefaultValue =
                            SecDisplayUtils.getAutoBrightnessDefaultValue(this.mContext);
                    builder.setTitle(
                                    R.string
                                            .sec_brightness_maximum_alert_turn_on_auto_brightness_title)
                            .setMessage(
                                    R.string
                                            .sec_brightness_maximum_alert_turn_on_auto_brightness_msg)
                            .setPositiveButton(
                                    R.string
                                            .sec_brightness_maximum_alert_turn_on_auto_brightness_ok,
                                    new DialogInterface
                                            .OnClickListener() { // from class:
                                                                 // com.samsung.android.settings.display.BrightnessSeekBarPreference$$ExternalSyntheticLambda3
                                        @Override // android.content.DialogInterface.OnClickListener
                                        public final void onClick(
                                                DialogInterface dialogInterface, int i2) {
                                            BrightnessSeekBarPreference
                                                    brightnessSeekBarPreference =
                                                            BrightnessSeekBarPreference.this;
                                            Settings.System.putInt(
                                                    brightnessSeekBarPreference.mContentResolver,
                                                    "screen_brightness_mode",
                                                    1);
                                            SecDisplayUtils.setAutoBrightnessWithCamera(
                                                    brightnessSeekBarPreference.mContext);
                                        }
                                    })
                            .setNegativeButton(
                                    R.string
                                            .sec_brightness_maximum_alert_turn_on_auto_brightness_cancel,
                                    new BrightnessSeekBarPreference$$ExternalSyntheticLambda4())
                            .setOnDismissListener(
                                    new DialogInterface
                                            .OnDismissListener() { // from class:
                                                                   // com.samsung.android.settings.display.BrightnessSeekBarPreference$$ExternalSyntheticLambda5
                                        @Override // android.content.DialogInterface.OnDismissListener
                                        public final void onDismiss(
                                                DialogInterface dialogInterface) {
                                            BrightnessSeekBarPreference
                                                    brightnessSeekBarPreference =
                                                            BrightnessSeekBarPreference.this;
                                            int i2 = autoBrightnessDefaultValue;
                                            Settings.System.putInt(
                                                    brightnessSeekBarPreference.mContentResolver,
                                                    "shown_max_brightness_dialog",
                                                    1);
                                            brightnessSeekBarPreference.mDialogForMaxBrightness =
                                                    null;
                                            brightnessSeekBarPreference
                                                            .mIsMaxBrightnessDialogShown =
                                                    true;
                                            if (Settings.System.getInt(
                                                            brightnessSeekBarPreference
                                                                    .mContentResolver,
                                                            "screen_brightness_mode",
                                                            i2)
                                                    != 1) {
                                                StringBuilder sb =
                                                        new StringBuilder(
                                                                " setNegativeButton >> limit : ");
                                                int i3 =
                                                        BrightnessSeekBarPreference
                                                                .START_POINT_FOR_LIMIT_WARNING;
                                                Preference$$ExternalSyntheticOutline0.m(
                                                        sb, i3, "BrightnessSeekBarPreference");
                                                float lerpInv =
                                                        MathUtils.lerpInv(0.0f, 2.6738688E8f, i3);
                                                BrightnessManager brightnessManager2 =
                                                        brightnessSeekBarPreference
                                                                .mBrightnessManager;
                                                brightnessManager2.mDisplayManager
                                                        .semSetTemporaryBrightness(
                                                                (int)
                                                                        MathUtils.lerp(
                                                                                brightnessManager2
                                                                                        .mScreenBrightnessMinimum,
                                                                                brightnessManager2
                                                                                        .mScreenBrightnessMaximum,
                                                                                lerpInv));
                                                brightnessSeekBarPreference.updateProgress(
                                                        lerpInv, i3);
                                                brightnessSeekBarPreference.updateBrightness();
                                            }
                                        }
                                    });
                }
                if (this.mDialogForMaxBrightness == null) {
                    this.mDialogForMaxBrightness = builder.create();
                }
                this.mDialogForMaxBrightness.semSetAnchor(this.mBrightnessSeekBar);
                this.mDialogForMaxBrightness.show();
                return;
            }
            if ((z2 || (i <= START_POINT_FOR_LIMIT_WARNING && z))
                    && (alertDialog = this.mDialogForMaxBrightness) != null) {
                alertDialog.dismiss();
            }
        }
        float lerpInv = MathUtils.lerpInv(0.0f, 2.6738688E8f, i);
        if (this.mOverheatTextView != null) {
            BrightnessManager brightnessManager2 = this.mBrightnessManager;
            int lerp =
                    (int)
                            MathUtils.lerp(
                                    brightnessManager2.mScreenBrightnessMinimum,
                                    brightnessManager2.mScreenBrightnessMaximum,
                                    lerpInv);
            int i2 = brightnessManager2.mMaxBrightness;
            if (i2 >= lerp || brightnessManager2.mChangeType != 0 || i2 == -1) {
                this.mOverheatTextView.setVisibility(8);
            } else {
                this.mOverheatTextView.setVisibility(0);
            }
        }
        if (z) {
            SeslSeekBar seslSeekBar2 = this.mBrightnessSeekBar;
            if (seslSeekBar2 != null) {
                seslSeekBar2.setProgress(i);
                seslSeekBar.setContentDescription(
                        this.mContext.getResources().getString(R.string.brightness_title));
                seslSeekBar.setStateDescription(
                        SliderUtils.formatStateDescription(this.mContext, seslSeekBar));
                if (this.mBrightnessSeekBar != null && !Utils.isTalkBackEnabled(this.mContext)) {
                    this.mBrightnessSeekBar.setContentDescription(Integer.toString(i));
                }
            }
            BrightnessManager brightnessManager3 = this.mBrightnessManager;
            brightnessManager3.mDisplayManager.semSetTemporaryBrightness(
                    (int)
                            MathUtils.lerp(
                                    brightnessManager3.mScreenBrightnessMinimum,
                                    brightnessManager3.mScreenBrightnessMaximum,
                                    lerpInv));
            if (this.mBrightnessSeekBar != null) {
                this.mInternalChange = true;
                ValueAnimator valueAnimator = this.mSliderAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
            }
            if (UsefulfeatureUtils.isUniversalSwitchEnabled(this.mContext)) {
                updateBrightness();
            }
            Log.d("BrightnessSeekBarPreference", "animationValue : " + lerpInv);
            LottieAnimationView lottieAnimationView = this.mBrightnessIcon;
            if (lottieAnimationView != null) {
                lottieAnimationView.setProgressInternal(lerpInv, true);
            }
        }
    }

    @Override // com.samsung.android.settings.display.DisplayCustomPreference,
              // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
    public final void onStartTrackingTouch(SeslSeekBar seslSeekBar) {
        TextView textView = this.mHBMText;
        if (textView != null && this.mIsAutomaticMode && this.mBrightnessManager.mIsHBMOnOff) {
            textView.setVisibility(0);
        }
    }

    @Override // com.samsung.android.settings.display.DisplayCustomPreference,
              // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
    public final void onStopTrackingTouch(SeslSeekBar seslSeekBar) {
        AlertDialog alertDialog = this.mDialogForMaxBrightness;
        if (alertDialog != null && alertDialog.isShowing()) {
            int i = START_POINT_FOR_LIMIT_WARNING;
            float lerpInv = MathUtils.lerpInv(0.0f, 2.6738688E8f, i);
            BrightnessManager brightnessManager = this.mBrightnessManager;
            brightnessManager.mDisplayManager.semSetTemporaryBrightness(
                    (int)
                            MathUtils.lerp(
                                    brightnessManager.mScreenBrightnessMinimum,
                                    brightnessManager.mScreenBrightnessMaximum,
                                    lerpInv));
            updateProgress(lerpInv, i);
            updateBrightness();
        }
        LoggingHelper.insertEventLogging(46, 4200);
        if (Rune.isJapanDCMModel() && !this.mIsAutomaticMode) {
            Settings.System.putInt(this.mContentResolver, "brightness_user_touch", 1);
        }
        updateBrightness();
        StringBuilder sb = new StringBuilder("onstoptracking, mIsAutomaticMode : ");
        sb.append(this.mIsAutomaticMode);
        sb.append(", mIsHBMOnOff : ");
        sb.append(this.mBrightnessManager.mIsHBMOnOff);
        sb.append(", progress : ");
        sb.append(seslSeekBar.getProgress());
        sb.append(", brightness : ");
        BrightnessManager brightnessManager2 = this.mBrightnessManager;
        sb.append(
                (int)
                        MathUtils.lerp(
                                brightnessManager2.mScreenBrightnessMinimum,
                                brightnessManager2.mScreenBrightnessMaximum,
                                MathUtils.lerpInv(
                                        0.0f,
                                        2.6738688E8f,
                                        this.mBrightnessSeekBar.getProgress())));
        sb.append(", brightness_user_touch : ");
        sb.append(Settings.System.getInt(this.mContentResolver, "brightness_user_touch", 0));
        Log.d("BrightnessSeekBarPreference", sb.toString());
    }

    public final void updateBrightness() {
        if (this.mBrightnessSeekBar != null) {
            this.mInternalChange = true;
            ValueAnimator valueAnimator = this.mSliderAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            Settings.System.putInt(
                    this.mBrightnessManager.mContentResolver,
                    "auto_brightness_transition_time",
                    -1);
            BrightnessManager brightnessManager = this.mBrightnessManager;
            Settings.System.putInt(
                    brightnessManager.mContentResolver,
                    "screen_brightness",
                    (int)
                            MathUtils.lerp(
                                    brightnessManager.mScreenBrightnessMinimum,
                                    brightnessManager.mScreenBrightnessMaximum,
                                    MathUtils.lerpInv(
                                            0.0f,
                                            2.6738688E8f,
                                            this.mBrightnessSeekBar.getProgress())));
        }
    }

    public final void updateProgress(float f, int i) {
        SeslSeekBar seslSeekBar = this.mBrightnessSeekBar;
        if (seslSeekBar != null) {
            seslSeekBar.setProgress(i);
            this.seekBar.setContentDescription(
                    this.mContext.getResources().getString(R.string.brightness_title));
            SeslSeekBar seslSeekBar2 = this.seekBar;
            seslSeekBar2.setStateDescription(
                    SliderUtils.formatStateDescription(this.mContext, seslSeekBar2));
        }
        if (this.mBrightnessSeekBar != null && !Utils.isTalkBackEnabled(this.mContext)) {
            this.mBrightnessSeekBar.setContentDescription(Integer.toString(i));
        }
        Log.d("BrightnessSeekBarPreference", "animationValue : " + f);
        LottieAnimationView lottieAnimationView = this.mBrightnessIcon;
        if (lottieAnimationView != null) {
            lottieAnimationView.setProgressInternal(f, true);
        }
    }
}
