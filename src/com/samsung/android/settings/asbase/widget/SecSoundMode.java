package com.samsung.android.settings.asbase.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.asbase.routine.action.ui.VolumeActionActivity;
import com.samsung.android.settings.asbase.routine.action.ui.VolumeActionActivity$onModeChangedListener$1;
import com.samsung.android.settings.asbase.routine.action.ui.VolumeActionRow;
import com.samsung.android.settings.asbase.utils.SelectionColorUpdater;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001:\u0002\f\r"
                + "B\u0013\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002¢\u0006\u0004\b\u0004\u0010\u0005B\u001d\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\u0004\b\u0004\u0010\bB%\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\n"
                + "\u001a\u00020\t¢\u0006\u0004\b\u0004\u0010\u000b¨\u0006\u000e"
        },
        d2 = {
            "Lcom/samsung/android/settings/asbase/widget/SecSoundMode;",
            "Landroid/widget/LinearLayout;",
            "Landroid/content/Context;",
            "context",
            "<init>",
            "(Landroid/content/Context;)V",
            "Landroid/util/AttributeSet;",
            "attrs",
            "(Landroid/content/Context;Landroid/util/AttributeSet;)V",
            ApnSettings.MVNO_NONE,
            "defStyleAttr",
            "(Landroid/content/Context;Landroid/util/AttributeSet;I)V",
            "MultiButtonItem",
            "1",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class SecSoundMode extends LinearLayout {
    public MultiButtonItem btnMute;
    public MultiButtonItem btnSound;
    public MultiButtonItem btnVibrate;
    public int btnVibrateVisible;
    public CheckBox checkBox;
    public View dividerView;
    public SecVolumeIconMotion iconMotion;
    public VolumeActionActivity$onModeChangedListener$1 listener;
    public int ringerMode;
    public int selectedPosition;
    public ViewGroup soundModeContainer;
    public String textMute;
    public String textSound;
    public String textVibrate;
    public View volumeOptionView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MultiButtonItem {
        public View button;
        public RadioButton dot;
        public ImageView imgIcon;
        public ImageView splashView;
        public TextView textView;
        public ImageView waveL;
        public ImageView waveS;

        public final void bindView(SecSoundMode secSoundMode, int i) {
            if (i == 0) {
                View requireViewById = secSoundMode.requireViewById(R.id.low_button);
                Intrinsics.checkNotNull(
                        requireViewById, "null cannot be cast to non-null type android.view.View");
                this.button = requireViewById;
                View requireViewById2 = secSoundMode.requireViewById(R.id.low_button_text);
                Intrinsics.checkNotNullExpressionValue(requireViewById2, "requireViewById(...)");
                this.textView = (TextView) requireViewById2;
                this.imgIcon = (ImageView) secSoundMode.requireViewById(R.id.volume_normal_icon);
                this.waveS =
                        (ImageView) secSoundMode.requireViewById(R.id.volume_sound_icon_wave_s);
                this.waveL =
                        (ImageView) secSoundMode.requireViewById(R.id.volume_sound_icon_wave_l);
                View requireViewById3 = secSoundMode.requireViewById(R.id.low_button_dot);
                Intrinsics.checkNotNullExpressionValue(requireViewById3, "requireViewById(...)");
                this.dot = (RadioButton) requireViewById3;
                return;
            }
            if (i == 1) {
                View requireViewById4 = secSoundMode.requireViewById(R.id.mid_button);
                Intrinsics.checkNotNull(
                        requireViewById4, "null cannot be cast to non-null type android.view.View");
                this.button = requireViewById4;
                View requireViewById5 = secSoundMode.requireViewById(R.id.mid_button_text);
                Intrinsics.checkNotNullExpressionValue(requireViewById5, "requireViewById(...)");
                this.textView = (TextView) requireViewById5;
                this.imgIcon = (ImageView) secSoundMode.requireViewById(R.id.vibrate_image_view);
                View requireViewById6 = secSoundMode.requireViewById(R.id.mid_button_dot);
                Intrinsics.checkNotNullExpressionValue(requireViewById6, "requireViewById(...)");
                this.dot = (RadioButton) requireViewById6;
                return;
            }
            if (i != 2) {
                return;
            }
            View requireViewById7 = secSoundMode.requireViewById(R.id.high_button);
            Intrinsics.checkNotNull(
                    requireViewById7, "null cannot be cast to non-null type android.view.View");
            this.button = requireViewById7;
            View requireViewById8 = secSoundMode.requireViewById(R.id.high_button_text);
            Intrinsics.checkNotNullExpressionValue(requireViewById8, "requireViewById(...)");
            this.textView = (TextView) requireViewById8;
            this.imgIcon = (ImageView) secSoundMode.requireViewById(R.id.mute_image_view);
            this.splashView =
                    (ImageView) secSoundMode.requireViewById(R.id.volume_icon_mute_splash);
            View requireViewById9 = secSoundMode.requireViewById(R.id.high_button_dot);
            Intrinsics.checkNotNullExpressionValue(requireViewById9, "requireViewById(...)");
            this.dot = (RadioButton) requireViewById9;
        }

        public final RadioButton getDot() {
            RadioButton radioButton = this.dot;
            if (radioButton != null) {
                return radioButton;
            }
            Intrinsics.throwUninitializedPropertyAccessException("dot");
            throw null;
        }

        public final void setSelected(boolean z) {
            TextView textView = this.textView;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("textView");
                throw null;
            }
            textView.setTypeface(Typeface.create(Typeface.create("sec", 0), z ? 600 : 400, false));
            TextView textView2 = this.textView;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("textView");
                throw null;
            }
            SelectionColorUpdater.updateColor(textView2, z);
            SelectionColorUpdater.updateColor(this.imgIcon, z);
            SelectionColorUpdater.updateColor(this.waveS, z);
            SelectionColorUpdater.updateColor(this.waveL, z);
            SelectionColorUpdater.updateColor(this.splashView, z);
            getDot().setChecked(z);
        }

        public final void setText(String str) {
            Intrinsics.checkNotNullParameter(str, "str");
            TextView textView = this.textView;
            if (textView != null) {
                textView.setText(str);
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("textView");
                throw null;
            }
        }
    }

    public SecSoundMode(Context context) {
        super(context);
        this.textSound = ApnSettings.MVNO_NONE;
        this.textVibrate = ApnSettings.MVNO_NONE;
        this.textMute = ApnSettings.MVNO_NONE;
        this.ringerMode = 2;
    }

    public static final void access$callModeSelectListener(SecSoundMode secSoundMode, int i) {
        VolumeActionActivity$onModeChangedListener$1 volumeActionActivity$onModeChangedListener$1 =
                secSoundMode.listener;
        if (volumeActionActivity$onModeChangedListener$1 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("listener");
            throw null;
        }
        VolumeActionActivity volumeActionActivity =
                volumeActionActivity$onModeChangedListener$1.this$0;
        Iterator it = ((ArrayList) volumeActionActivity.rowList).iterator();
        while (it.hasNext()) {
            ((VolumeActionRow) it.next()).updateFromRinger(i);
            if (i != 0) {
                ViewGroup viewGroup = volumeActionActivity.rowContainer;
                if (viewGroup == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rowContainer");
                    throw null;
                }
                viewGroup.setVisibility(0);
            } else {
                ViewGroup viewGroup2 = volumeActionActivity.rowContainer;
                if (viewGroup2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rowContainer");
                    throw null;
                }
                viewGroup2.setVisibility(
                        volumeActionActivity.isVolumeOptionSelected ^ true ? 0 : 8);
            }
        }
    }

    public final void startModeChangeAnimation(boolean z) {
        ImageView imageView;
        ImageView imageView2;
        MultiButtonItem multiButtonItem = this.btnSound;
        if (multiButtonItem == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnSound");
            throw null;
        }
        ImageView imageView3 = multiButtonItem.imgIcon;
        if (imageView3 == null
                || (imageView = multiButtonItem.waveS) == null
                || (imageView2 = multiButtonItem.waveL) == null) {
            return;
        }
        SecVolumeIconMotion secVolumeIconMotion = this.iconMotion;
        if (secVolumeIconMotion != null) {
            secVolumeIconMotion.startSoundAnimation(1, imageView3, imageView, imageView2, z);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("iconMotion");
            throw null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:115:0x0144, code lost:

       if (r11.getDot().isChecked() != false) goto L119;
    */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0041, code lost:

       if (r11.getDot().isChecked() != false) goto L23;
    */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00c6, code lost:

       if (r11.getDot().isChecked() != false) goto L72;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateButtonStatus(int r11) {
        /*
            Method dump skipped, instructions count: 390
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.asbase.widget.SecSoundMode.updateButtonStatus(int):void");
    }

    public SecSoundMode(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.textSound = ApnSettings.MVNO_NONE;
        this.textVibrate = ApnSettings.MVNO_NONE;
        this.textMute = ApnSettings.MVNO_NONE;
        this.ringerMode = 2;
    }

    public SecSoundMode(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.textSound = ApnSettings.MVNO_NONE;
        this.textVibrate = ApnSettings.MVNO_NONE;
        this.textMute = ApnSettings.MVNO_NONE;
        this.ringerMode = 2;
    }
}
