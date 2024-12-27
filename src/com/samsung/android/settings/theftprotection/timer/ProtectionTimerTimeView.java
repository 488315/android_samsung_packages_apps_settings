package com.samsung.android.settings.theftprotection.timer;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.settings.R;

import com.samsung.android.settings.theftprotection.TheftProtectionConstants;
import com.samsung.android.settings.theftprotection.logging.Log;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ProtectionTimerTimeView extends LinearLayout {
    public final StringBuilder mBuilder;
    public TextView mColonMS;
    public final Context mContext;
    public int mMinute;
    public RelativeLayout mMinuteBackground;
    public TextView mMinutePostfix;
    public TextView mMinutePrefix;
    public int mSecond;
    public RelativeLayout mSecondBackground;
    public TextView mSecondPostfix;
    public TextView mSecondPrefix;

    public ProtectionTimerTimeView(Context context) {
        super(context);
        this.mBuilder = new StringBuilder();
        this.mContext = context;
    }

    public static int scaleFontSizeForLanguage(int i) {
        return TextUtils.equals(Locale.getDefault().getLanguage(), "my") ? (int) (i * 0.8f) : i;
    }

    public final void setNumber(int i, TextView textView) {
        if (textView != null) {
            try {
                Object[] objArr = {Integer.valueOf(i)};
                StringBuilder sb = this.mBuilder;
                sb.delete(0, sb.length());
                this.mBuilder.append(String.format("%d", objArr));
                textView.setText(this.mBuilder.toString());
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.e("ProtectionTimerTimeView", "Exception : " + e.toString());
            }
        }
    }

    public final void setTimeTextView(long j) {
        String ch;
        RelativeLayout.LayoutParams layoutParams;
        RelativeLayout.LayoutParams layoutParams2;
        Log.d("ProtectionTimerTimeView", "setTimeTextView()");
        LayoutInflater layoutInflater =
                (LayoutInflater) this.mContext.getSystemService("layout_inflater");
        removeAllViews();
        layoutInflater.inflate(R.layout.protection_timer_time_view, this);
        this.mMinuteBackground = (RelativeLayout) findViewById(R.id.timer_minute_bg);
        this.mSecondBackground = (RelativeLayout) findViewById(R.id.timer_second_bg);
        this.mMinutePrefix = (TextView) findViewById(R.id.timer_minute_prefix);
        this.mMinutePostfix = (TextView) findViewById(R.id.timer_minute_postfix);
        this.mSecondPrefix = (TextView) findViewById(R.id.timer_second_prefix);
        this.mSecondPostfix = (TextView) findViewById(R.id.timer_second_postfix);
        this.mColonMS = (TextView) findViewById(R.id.timer_ms_colon);
        String bestDateTimePattern =
                DateFormat.getBestDateTimePattern(
                        Locale.getDefault(),
                        DateFormat.is24HourFormat(this.mContext) ? "Hm" : "hm");
        int lastIndexOf = bestDateTimePattern.lastIndexOf(72);
        if (lastIndexOf == -1) {
            lastIndexOf = bestDateTimePattern.lastIndexOf(104);
        }
        if (lastIndexOf == -1) {
            ch = ":";
        } else {
            int i = lastIndexOf + 1;
            int indexOf = bestDateTimePattern.indexOf(109, i);
            ch =
                    indexOf == -1
                            ? Character.toString(bestDateTimePattern.charAt(i))
                            : bestDateTimePattern.substring(i, indexOf);
        }
        this.mColonMS.setText(ch);
        Log.d("ProtectionTimerTimeView", "initTimeView() / remainMillis = " + j);
        long j2 = TheftProtectionConstants.SECOND_MILLIS;
        int i2 = (int) (j % j2);
        if (i2 > 200) {
            j += j2 - i2;
        }
        long j3 = TheftProtectionConstants.MINUTE_MILLIS;
        int i3 = (int) (j / j3);
        this.mMinute = i3;
        this.mSecond = (int) ((j % j3) / j2);
        setTimeToView(this.mMinutePrefix, i3, this.mMinutePostfix);
        setTimeToView(this.mSecondPrefix, this.mSecond, this.mSecondPostfix);
        RelativeLayout relativeLayout = this.mMinuteBackground;
        if (relativeLayout != null
                && (layoutParams2 = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams())
                        != null) {
            layoutParams2.width = -2;
            layoutParams2.height = -2;
        }
        RelativeLayout relativeLayout2 = this.mSecondBackground;
        if (relativeLayout2 != null
                && (layoutParams = (RelativeLayout.LayoutParams) relativeLayout2.getLayoutParams())
                        != null) {
            layoutParams.width = -2;
            layoutParams.height = -2;
        }
        TextView textView = this.mColonMS;
        if (textView != null) {
            Resources resources = getResources();
            RelativeLayout.LayoutParams layoutParams3 =
                    (RelativeLayout.LayoutParams) textView.getLayoutParams();
            if (layoutParams3 != null) {
                layoutParams3.width =
                        resources.getDimensionPixelSize(R.dimen.protection_timer_time_colon_width);
                layoutParams3.height = -2;
            }
            textView.setTextSize(
                    0,
                    scaleFontSizeForLanguage(
                            getResources()
                                    .getDimensionPixelSize(
                                            R.dimen.protection_timer_time_colon_text_size)));
        }
        setTimeViewContentDescription();
    }

    public final void setTimeToView(TextView textView, int i, TextView textView2) {
        if (textView != null) {
            setNumber(i / 10, textView);
            textView.setTextSize(
                    0,
                    scaleFontSizeForLanguage(
                            getResources()
                                    .getDimensionPixelSize(
                                            R.dimen.protection_timer_time_colon_text_size)));
        }
        if (textView2 != null) {
            setNumber(i % 10, textView2);
            textView2.setTextSize(
                    0,
                    scaleFontSizeForLanguage(
                            getResources()
                                    .getDimensionPixelSize(
                                            R.dimen.protection_timer_time_colon_text_size)));
        }
    }

    public final void setTimeViewContentDescription() {
        setContentDescription(
                Integer.toString(this.mMinute)
                        + ' '
                        + getResources().getString(R.string.protection_timer_minute)
                        + ' '
                        + Integer.toString(this.mSecond)
                        + ' '
                        + getResources().getString(R.string.protection_timer_second));
    }

    public ProtectionTimerTimeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mBuilder = new StringBuilder();
        this.mContext = context;
    }

    public ProtectionTimerTimeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mBuilder = new StringBuilder();
        this.mContext = context;
    }
}
