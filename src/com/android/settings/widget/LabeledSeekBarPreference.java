package com.android.settings.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.internal.util.Preconditions;
import com.android.settings.R;
import com.android.settings.R$styleable;
import com.android.settings.fuelgauge.batterysaver.BatterySaverScheduleSeekBarController;
import com.android.settingslib.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LabeledSeekBarPreference extends SeekBarPreference {
    public final int mIconEndContentDescriptionId;
    public final int mIconEndId;
    public final int mIconStartContentDescriptionId;
    public final int mIconStartId;
    public SeekBar mSeekBar;
    public SeekBar.OnSeekBarChangeListener mSeekBarChangeListener;
    public Preference.OnPreferenceChangeListener mStopListener;
    public final int mTextEndId;
    public final int mTextStartId;
    public final int mTickMarkId;

    public LabeledSeekBarPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setLayoutResource(R.layout.preference_labeled_slider);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.LabeledSeekBarPreference);
        this.mTextStartId = obtainStyledAttributes.getResourceId(5, 0);
        this.mTextEndId = obtainStyledAttributes.getResourceId(4, 0);
        this.mTickMarkId = obtainStyledAttributes.getResourceId(6, 0);
        int resourceId = obtainStyledAttributes.getResourceId(2, 0);
        this.mIconStartId = resourceId;
        int resourceId2 = obtainStyledAttributes.getResourceId(0, 0);
        this.mIconEndId = resourceId2;
        int resourceId3 = obtainStyledAttributes.getResourceId(3, 0);
        this.mIconStartContentDescriptionId = resourceId3;
        Preconditions.checkArgument(
                resourceId3 == 0 || resourceId != 0,
                "The resource of the iconStart attribute may be invalid or not set, you should set"
                    + " the iconStart attribute and have the valid resource.");
        int resourceId4 = obtainStyledAttributes.getResourceId(1, 0);
        this.mIconEndContentDescriptionId = resourceId4;
        Preconditions.checkArgument(
                resourceId4 == 0 || resourceId2 != 0,
                "The resource of the iconEnd attribute may be invalid or not set, you should set"
                    + " the iconEnd attribute and have the valid resource.");
        obtainStyledAttributes.recycle();
    }

    @Override // com.android.settings.widget.SeekBarPreference,
              // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.findViewById(android.R.id.summary);
        boolean z = textView != null && textView.getVisibility() == 0;
        TextView textView2 = (TextView) preferenceViewHolder.findViewById(android.R.id.title);
        if (textView2 != null && !isSelectable() && isEnabled() && z) {
            textView2.setTextColor(
                    Utils.getColorAttr(getContext(), android.R.attr.textColorPrimary));
        }
        TextView textView3 = (TextView) preferenceViewHolder.findViewById(android.R.id.text1);
        int i = this.mTextStartId;
        if (i > 0) {
            textView3.setText(i);
        }
        TextView textView4 = (TextView) preferenceViewHolder.findViewById(android.R.id.text2);
        int i2 = this.mTextEndId;
        if (i2 > 0) {
            textView4.setText(i2);
        }
        preferenceViewHolder
                .findViewById(R.id.label_frame)
                .setVisibility((this.mTextStartId > 0 || this.mTextEndId > 0) ? 0 : 8);
        this.mSeekBar = (SeekBar) preferenceViewHolder.findViewById(android.R.id.textClassifier);
        if (this.mTickMarkId != 0) {
            this.mSeekBar.setTickMark(getContext().getDrawable(this.mTickMarkId));
        }
        ViewGroup viewGroup = (ViewGroup) preferenceViewHolder.findViewById(R.id.icon_start_frame);
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R.id.icon_start);
        SeekBar seekBar = this.mSeekBar;
        if (this.mIconStartId != 0) {
            if (imageView.getDrawable() == null) {
                imageView.setImageResource(this.mIconStartId);
            }
            if (this.mIconStartContentDescriptionId != 0) {
                viewGroup.setContentDescription(
                        viewGroup.getContext().getString(this.mIconStartContentDescriptionId));
            }
            final int i3 = 1;
            viewGroup.setOnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.android.settings.widget.LabeledSeekBarPreference$$ExternalSyntheticLambda0
                        public final /* synthetic */ LabeledSeekBarPreference f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            int i4 = i3;
                            LabeledSeekBarPreference labeledSeekBarPreference = this.f$0;
                            switch (i4) {
                                case 0:
                                    int i5 = labeledSeekBarPreference.mProgress;
                                    if (i5 < labeledSeekBarPreference.mMax) {
                                        labeledSeekBarPreference.setProgress(i5 + 1, true);
                                        break;
                                    }
                                    break;
                                default:
                                    int i6 = labeledSeekBarPreference.mProgress;
                                    if (i6 > 0) {
                                        labeledSeekBarPreference.setProgress(i6 - 1, true);
                                        break;
                                    }
                                    break;
                            }
                        }
                    });
            viewGroup.setVisibility(0);
            boolean z2 = seekBar.getProgress() > 0;
            imageView.setEnabled(z2);
            ((ViewGroup) imageView.getParent()).setEnabled(z2);
        }
        ViewGroup viewGroup2 = (ViewGroup) preferenceViewHolder.findViewById(R.id.icon_end_frame);
        ImageView imageView2 = (ImageView) preferenceViewHolder.findViewById(R.id.icon_end);
        SeekBar seekBar2 = this.mSeekBar;
        if (this.mIconEndId == 0) {
            return;
        }
        if (imageView2.getDrawable() == null) {
            imageView2.setImageResource(this.mIconEndId);
        }
        if (this.mIconEndContentDescriptionId != 0) {
            viewGroup2.setContentDescription(
                    viewGroup2.getContext().getString(this.mIconEndContentDescriptionId));
        }
        final int i4 = 0;
        viewGroup2.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.widget.LabeledSeekBarPreference$$ExternalSyntheticLambda0
                    public final /* synthetic */ LabeledSeekBarPreference f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i42 = i4;
                        LabeledSeekBarPreference labeledSeekBarPreference = this.f$0;
                        switch (i42) {
                            case 0:
                                int i5 = labeledSeekBarPreference.mProgress;
                                if (i5 < labeledSeekBarPreference.mMax) {
                                    labeledSeekBarPreference.setProgress(i5 + 1, true);
                                    break;
                                }
                                break;
                            default:
                                int i6 = labeledSeekBarPreference.mProgress;
                                if (i6 > 0) {
                                    labeledSeekBarPreference.setProgress(i6 - 1, true);
                                    break;
                                }
                                break;
                        }
                    }
                });
        viewGroup2.setVisibility(0);
        boolean z3 = seekBar2.getProgress() < seekBar2.getMax();
        imageView2.setEnabled(z3);
        ((ViewGroup) imageView2.getParent()).setEnabled(z3);
    }

    @Override // com.android.settings.widget.SeekBarPreference,
              // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        super.onProgressChanged(seekBar, i, z);
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = this.mSeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onProgressChanged(seekBar, i, z);
        }
    }

    @Override // com.android.settings.widget.SeekBarPreference,
              // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onStartTrackingTouch(SeekBar seekBar) {
        super.onStartTrackingTouch(seekBar);
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = this.mSeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onStartTrackingTouch(seekBar);
        }
    }

    @Override // com.android.settings.widget.SeekBarPreference,
              // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onStopTrackingTouch(SeekBar seekBar) {
        super.onStopTrackingTouch(seekBar);
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = this.mSeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onStopTrackingTouch(seekBar);
        }
        Preference.OnPreferenceChangeListener onPreferenceChangeListener = this.mStopListener;
        if (onPreferenceChangeListener != null) {
            onPreferenceChangeListener.onPreferenceChange(
                    this, Integer.valueOf(seekBar.getProgress()));
        }
        notifyChanged();
    }

    @Override // com.android.settings.widget.SeekBarPreference
    public final void setOnSeekBarChangeListener(
            BatterySaverScheduleSeekBarController batterySaverScheduleSeekBarController) {
        this.mSeekBarChangeListener = batterySaverScheduleSeekBarController;
    }

    public LabeledSeekBarPreference(Context context, AttributeSet attributeSet) {
        this(
                context,
                attributeSet,
                TypedArrayUtils.getAttr(context, R.attr.seekBarPreferenceStyle, 17957213),
                0);
    }
}
