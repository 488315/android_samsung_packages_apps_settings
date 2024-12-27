package com.samsung.android.sdk.command.template;

import android.os.Bundle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SliderTemplate extends CommandTemplate {
    public final float mCurrentValue;
    public final CharSequence mFormatString;
    public final float mMaxValue;
    public final float mMinValue;
    public final float mStepValue;

    public SliderTemplate(float f, float f2, float f3) {
        super("range");
        this.mMinValue = f;
        this.mMaxValue = f2;
        this.mCurrentValue = f3;
        this.mStepValue = 1.0f;
        this.mFormatString = "%.1f";
        validate();
    }

    @Override // com.samsung.android.sdk.command.template.CommandTemplate
    public final Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putFloat("key_min_value", this.mMinValue);
        dataBundle.putFloat("key_max_value", this.mMaxValue);
        dataBundle.putFloat("key_current_value", this.mCurrentValue);
        dataBundle.putFloat("key_step_value", this.mStepValue);
        dataBundle.putCharSequence("key_format_string", this.mFormatString);
        return dataBundle;
    }

    @Override // com.samsung.android.sdk.command.template.CommandTemplate
    public final int getTemplateType() {
        return 3;
    }

    public final void validate() {
        float f = this.mMinValue;
        float f2 = this.mMaxValue;
        if (Float.compare(f, f2) > 0) {
            throw new IllegalArgumentException(
                    String.format(
                            "minValue=%f > maxValue=%f", Float.valueOf(f), Float.valueOf(f2)));
        }
        float f3 = this.mCurrentValue;
        if (Float.compare(f, f3) > 0) {
            throw new IllegalArgumentException(
                    String.format(
                            "minValue=%f > currentValue=%f", Float.valueOf(f), Float.valueOf(f3)));
        }
        if (Float.compare(f3, f2) > 0) {
            throw new IllegalArgumentException(
                    String.format(
                            "currentValue=%f > maxValue=%f", Float.valueOf(f3), Float.valueOf(f2)));
        }
        float f4 = this.mStepValue;
        if (f4 <= 0.0f) {
            throw new IllegalArgumentException(
                    String.format("stepValue=%f <= 0", Float.valueOf(f4)));
        }
    }

    public SliderTemplate(Bundle bundle) {
        super(bundle);
        this.mMinValue = bundle.getFloat("key_min_value");
        this.mMaxValue = bundle.getFloat("key_max_value");
        this.mCurrentValue = bundle.getFloat("key_current_value");
        this.mStepValue = bundle.getFloat("key_step_value");
        this.mFormatString = bundle.getCharSequence("key_format_string", "%.1f");
        validate();
    }
}
