package com.google.android.material.internal;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;

import androidx.preference.Preference;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class StaticLayoutBuilderCompat {
    public int end;
    public boolean isRtl;
    public final TextPaint paint;
    public CharSequence source;
    public CollapsingToolbarLayout.StaticLayoutBuilderConfigurer staticLayoutBuilderConfigurer;
    public final int width;
    public Layout.Alignment alignment = Layout.Alignment.ALIGN_NORMAL;
    public int maxLines = Preference.DEFAULT_ORDER;
    public float lineSpacingAdd = 0.0f;
    public float lineSpacingMultiplier = 1.0f;
    public int hyphenationFrequency = 1;
    public boolean includePad = true;
    public TextUtils.TruncateAt ellipsize = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    abstract class StaticLayoutBuilderCompatException extends Exception {}

    public StaticLayoutBuilderCompat(CharSequence charSequence, TextPaint textPaint, int i) {
        this.source = charSequence;
        this.paint = textPaint;
        this.width = i;
        this.end = charSequence.length();
    }

    public final StaticLayout build() {
        if (this.source == null) {
            this.source = ApnSettings.MVNO_NONE;
        }
        int max = Math.max(0, this.width);
        CharSequence charSequence = this.source;
        if (this.maxLines == 1) {
            charSequence = TextUtils.ellipsize(charSequence, this.paint, max, this.ellipsize);
        }
        int min = Math.min(charSequence.length(), this.end);
        this.end = min;
        if (this.isRtl && this.maxLines == 1) {
            this.alignment = Layout.Alignment.ALIGN_OPPOSITE;
        }
        StaticLayout.Builder obtain =
                StaticLayout.Builder.obtain(charSequence, 0, min, this.paint, max);
        obtain.setAlignment(this.alignment);
        obtain.setIncludePad(this.includePad);
        obtain.setTextDirection(
                this.isRtl ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR);
        TextUtils.TruncateAt truncateAt = this.ellipsize;
        if (truncateAt != null) {
            obtain.setEllipsize(truncateAt);
        }
        obtain.setMaxLines(this.maxLines);
        float f = this.lineSpacingAdd;
        if (f != 0.0f || this.lineSpacingMultiplier != 1.0f) {
            obtain.setLineSpacing(f, this.lineSpacingMultiplier);
        }
        if (this.maxLines > 1) {
            obtain.setHyphenationFrequency(this.hyphenationFrequency);
        }
        CollapsingToolbarLayout.StaticLayoutBuilderConfigurer staticLayoutBuilderConfigurer =
                this.staticLayoutBuilderConfigurer;
        if (staticLayoutBuilderConfigurer != null) {
            staticLayoutBuilderConfigurer.configure(obtain);
        }
        return obtain.build();
    }
}
