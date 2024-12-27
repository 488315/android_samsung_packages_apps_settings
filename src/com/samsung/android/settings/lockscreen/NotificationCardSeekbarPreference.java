package com.samsung.android.settings.lockscreen;

import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SeslSeekBar;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreference;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NotificationCardSeekbarPreference extends SecPreference
        implements SeslSeekBar.OnSeekBarChangeListener, View.OnKeyListener {
    public boolean isTextColorInverseEnabled;
    public final Context mContext;
    public final boolean mIsTalkBackEnabled;
    public SeslSeekBar mSeekBar;
    public RelativeLayout mSeekBarDescText;
    public TextView percentageView;

    public NotificationCardSeekbarPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsTalkBackEnabled = false;
        this.mContext = context;
        this.mIsTalkBackEnabled = Utils.isTalkBackEnabled(context);
        setLayoutResource(R.layout.sec_preference_seekbar_noticard);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.itemView.setOnKeyListener(this);
        SeslSeekBar seslSeekBar =
                (SeslSeekBar) preferenceViewHolder.findViewById(R.id.noti_card_seekbar);
        this.mSeekBar = seslSeekBar;
        seslSeekBar.refreshDrawableState();
        this.mSeekBar.setOnSeekBarChangeListener(this);
        this.mSeekBar.setOnKeyListener(this);
        this.mSeekBarDescText =
                (RelativeLayout) preferenceViewHolder.findViewById(R.id.reverse_text_layout);
        this.percentageView =
                (TextView) preferenceViewHolder.findViewById(R.id.transparency_percentage);
        int i =
                100
                        - Settings.System.getInt(
                                this.mContext.getContentResolver(),
                                "lock_noticard_opacity",
                                this.mContext
                                        .getResources()
                                        .getInteger(
                                                R.integer.config_default_lock_noticard_opacity));
        this.mSeekBar.setProgress(i);
        this.percentageView.setText(
                this.mContext.getString(
                        R.string.sec_lockscreen_noti_card_seekbar_percentage, Integer.valueOf(i)));
        String num = Integer.toString(this.mSeekBar.getProgress());
        SeslSeekBar seslSeekBar2 = this.mSeekBar;
        if (seslSeekBar2 != null) {
            if (this.mIsTalkBackEnabled) {
                seslSeekBar2.setContentDescription(null);
            } else {
                seslSeekBar2.setContentDescription(num);
            }
        }
        updateSeekBarDescVisible();
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        SeslSeekBar seslSeekBar;
        if (keyEvent.getAction() == 0 && (seslSeekBar = this.mSeekBar) != null) {
            return seslSeekBar.onKeyDown(i, keyEvent);
        }
        return false;
    }

    @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
    public final void onProgressChanged(SeslSeekBar seslSeekBar, int i, boolean z) {
        Settings.System.putInt(
                this.mContext.getContentResolver(), "lock_noticard_opacity", 100 - i);
        String num = Integer.toString(seslSeekBar.getProgress());
        SeslSeekBar seslSeekBar2 = this.mSeekBar;
        if (seslSeekBar2 != null) {
            if (this.mIsTalkBackEnabled) {
                seslSeekBar2.setContentDescription(null);
            } else {
                seslSeekBar2.setContentDescription(num);
            }
        }
        this.percentageView.setText(
                this.mContext.getString(
                        R.string.sec_lockscreen_noti_card_seekbar_percentage,
                        Integer.valueOf(this.mSeekBar.getProgress())));
    }

    @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
    public final void onStopTrackingTouch(SeslSeekBar seslSeekBar) {
        int progress = this.mSeekBar.getProgress();
        Settings.System.putInt(
                this.mContext.getContentResolver(), "lock_noticard_opacity", 100 - progress);
        LoggingHelper.insertEventLogging(4435, 4454, progress);
        updateSeekBarDescVisible();
    }

    @Override // androidx.preference.Preference
    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        RelativeLayout relativeLayout = this.mSeekBarDescText;
        if (relativeLayout != null) {
            relativeLayout.setAlpha(z ? 1.0f : 0.4f);
        }
    }

    public final void updateSeekBarDescVisible() {
        Settings.System.getInt(
                this.mContext.getContentResolver(), "notification_text_color_inversion", 1);
        Settings.System.getInt(this.mContext.getContentResolver(), "white_lockscreen_wallpaper", 0);
        Settings.System.getString(
                this.mContext.getContentResolver(), "current_sec_active_themepackage");
        this.mSeekBarDescText.setVisibility(8);
    }

    @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
    public final void onStartTrackingTouch(SeslSeekBar seslSeekBar) {}
}
