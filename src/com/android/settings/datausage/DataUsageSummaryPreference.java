package com.android.settings.datausage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Typeface;
import android.os.UserHandle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.text.style.CharacterStyle;
import android.text.style.RelativeSizeSpan;
import android.text.style.TypefaceSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.datausage.DataUsageFeatureProviderImpl;
import com.samsung.android.settings.datausage.widget.ProgressBar;
import com.samsung.android.view.animation.SineInOut90;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DataUsageSummaryPreference extends Preference {
    static final Typeface SANS_SERIF_MEDIUM;
    public static boolean isViewUpdated;
    public final String TAG;
    public boolean mChartEnabled;
    public TextView mCurrentDataSimTitle;
    public Long mCycleEndTimeMs;
    public long mCycleStartTimeMs;
    public final DataUsageFeatureProviderImpl mDataUsageFeatureProvider;
    public int mDataUsageTemplate;
    public long mDataplanUse;
    public CharSequence mEndLabel;
    public CharSequence mLimitInfoText;
    public float mProgress;
    public CharSequence mStartLabel;
    public TextView mUsageTitle;
    public CharSequence mWarningInfoText;
    public boolean mWifiMode;

    static {
        TimeUnit.DAYS.toMillis(1L);
        TimeUnit.HOURS.toMillis(6L);
        SANS_SERIF_MEDIUM = Typeface.create("sans-serif-medium", 0);
    }

    public DataUsageSummaryPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mChartEnabled = true;
        this.TAG = "DataUsageSummaryPreference";
        setLayoutResource(R.layout.sec_data_usage_summary_preference);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mDataUsageFeatureProvider = featureFactoryImpl.getDataUsageFeatureProvider();
        isViewUpdated = false;
    }

    public static void smallSpanExcept$1(
            SpannableString spannableString, CharSequence charSequence) {
        int indexOf = TextUtils.indexOf(spannableString, charSequence);
        if (indexOf == -1) {
            spannableString.setSpan(
                    new RelativeSizeSpan(0.4642857f), 0, spannableString.length(), 18);
            return;
        }
        if (indexOf > 0) {
            spannableString.setSpan(new RelativeSizeSpan(0.4642857f), 0, indexOf, 18);
        }
        int length = ((String) charSequence).length() + indexOf;
        if (length < spannableString.length()) {
            spannableString.setSpan(
                    new RelativeSizeSpan(0.4642857f), length, spannableString.length(), 18);
        }
        spannableString.setSpan(CharacterStyle.wrap(new TypefaceSpan("sec")), indexOf, length, 18);
    }

    public TextView getCarrierInfo(PreferenceViewHolder preferenceViewHolder) {
        return (TextView) preferenceViewHolder.findViewById(R.id.carrier_and_update);
    }

    public TextView getCycleTime(PreferenceViewHolder preferenceViewHolder) {
        return (TextView) preferenceViewHolder.findViewById(R.id.cycle_left_time);
    }

    public TextView getDataLimits(PreferenceViewHolder preferenceViewHolder) {
        return (TextView) preferenceViewHolder.findViewById(R.id.data_limits);
    }

    public TextView getDataRemaining(PreferenceViewHolder preferenceViewHolder) {
        return (TextView) preferenceViewHolder.findViewById(R.id.data_remaining_view);
    }

    public TextView getDataUsed(PreferenceViewHolder preferenceViewHolder) {
        return (TextView) preferenceViewHolder.findViewById(R.id.data_usage_view);
    }

    public TextView getLabel1(PreferenceViewHolder preferenceViewHolder) {
        return (TextView) preferenceViewHolder.findViewById(android.R.id.text1);
    }

    public TextView getLabel2(PreferenceViewHolder preferenceViewHolder) {
        return (TextView) preferenceViewHolder.findViewById(android.R.id.text2);
    }

    public LinearLayout getLabelBar(PreferenceViewHolder preferenceViewHolder) {
        return (LinearLayout) preferenceViewHolder.findViewById(R.id.label_bar);
    }

    public MeasurableLinearLayout getLayout(PreferenceViewHolder preferenceViewHolder) {
        return (MeasurableLinearLayout) preferenceViewHolder.findViewById(R.id.usage_layout);
    }

    public ProgressBar getProgressBar(PreferenceViewHolder preferenceViewHolder) {
        return (ProgressBar) preferenceViewHolder.findViewById(R.id.determinateBar);
    }

    public TextView getUsageTitle(PreferenceViewHolder preferenceViewHolder) {
        return (TextView) preferenceViewHolder.findViewById(R.id.usage_title);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        final ProgressBar progressBar =
                (ProgressBar) preferenceViewHolder.findViewById(R.id.determinateBar);
        if (!this.mChartEnabled
                || (TextUtils.isEmpty(this.mStartLabel) && TextUtils.isEmpty(this.mEndLabel))) {
            progressBar.setVisibility(8);
            preferenceViewHolder.findViewById(R.id.label_bar).setVisibility(8);
        } else if (SemPersonaManager.isSecureFolderId(UserHandle.semGetMyUserId())) {
            progressBar.setVisibility(8);
            preferenceViewHolder.findViewById(R.id.label_bar).setVisibility(8);
        } else {
            progressBar.setVisibility(0);
            preferenceViewHolder.findViewById(R.id.label_bar).setVisibility(0);
            float f = progressBar.mScore;
            float f2 = this.mProgress;
            if (f != f2) {
                progressBar.setProgress(f2 * 100.0f);
                int i = (int) (this.mProgress * 100.0f);
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        i, "startProgressAnim:", "ProgressBar_ANIM");
                progressBar.mAnimType = 2;
                progressBar.mAlphaValue = 0;
                progressBar.setProgress(0.0f);
                float f3 = i;
                float f4 = (f3 * 2000.0f) / 100.0f;
                ValueAnimator valueAnimator = progressBar.mProgressAnim;
                if (valueAnimator == null) {
                    progressBar.mProgressAnim = ValueAnimator.ofFloat(0.0f, f3 * 10.0f);
                } else {
                    valueAnimator.setFloatValues(0.0f, f3 * 10.0f);
                }
                if (progressBar.mProgressAnim.isRunning()) {
                    progressBar.mProgressAnim.cancel();
                }
                progressBar.mProgressAnim.setDuration((long) f4);
                progressBar.mProgressAnim.setInterpolator(new SineInOut90(0.33f, 0.0f, 0.1f, 1.0f));
                progressBar.mProgressAnim.addUpdateListener(
                        new ValueAnimator
                                .AnimatorUpdateListener() { // from class:
                                                            // com.samsung.android.settings.datausage.widget.ProgressBar$$ExternalSyntheticLambda0
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                                ProgressBar progressBar2 = ProgressBar.this;
                                int i2 = ProgressBar.$r8$clinit;
                                progressBar2.getClass();
                                progressBar2.setProgress(
                                        Math.round(
                                                        ((Float) valueAnimator2.getAnimatedValue())
                                                                .floatValue())
                                                / 10.0f);
                            }
                        });
                progressBar.mProgressAnim.addListener(
                        new AnimatorListenerAdapter() { // from class:
                                                        // com.samsung.android.settings.datausage.widget.ProgressBar.3
                            public AnonymousClass3() {}

                            @Override // android.animation.AnimatorListenerAdapter,
                                      // android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                ProgressBar progressBar2 = ProgressBar.this;
                                int i2 = ProgressBar.$r8$clinit;
                                progressBar2.getClass();
                                ProgressBar.this.mAnimType = 0;
                            }
                        });
                progressBar.mProgressAnim.start();
            }
            ((TextView) preferenceViewHolder.findViewById(android.R.id.text1))
                    .setText(this.mStartLabel);
            ((TextView) preferenceViewHolder.findViewById(android.R.id.text2))
                    .setText(this.mEndLabel);
        }
        this.mUsageTitle = (TextView) preferenceViewHolder.findViewById(R.id.usage_title);
        this.mCurrentDataSimTitle =
                (TextView) preferenceViewHolder.findViewById(R.id.current_data_sim_title);
        if (!isViewUpdated) {
            Utils.setMaxFontScale(getContext(), this.mUsageTitle);
            isViewUpdated = true;
        }
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.data_warnings);
        TextView textView2 = (TextView) preferenceViewHolder.findViewById(R.id.data_limits);
        if (SemPersonaManager.isSecureFolderId(UserHandle.semGetMyUserId())) {
            this.mUsageTitle.setVisibility(8);
            textView2.setVisibility(8);
            textView.setVisibility(8);
            ((TextView) preferenceViewHolder.findViewById(R.id.cycle_left_time)).setVisibility(8);
            return;
        }
        if (this.mWifiMode) {
            this.mDataUsageTemplate = R.string.sec_datausage_preference_summary_title_wifi;
            this.mUsageTitle.setVisibility(0);
            updateCycleTimeText(preferenceViewHolder);
            textView.setVisibility(8);
            textView2.setVisibility(8);
            this.mCurrentDataSimTitle.setVisibility(8);
        } else {
            this.mDataUsageTemplate = R.string.sec_datausage_preference_summary_title_mobile;
            this.mUsageTitle.setVisibility(0);
            updateCycleTimeText(preferenceViewHolder);
            textView.setVisibility(TextUtils.isEmpty(this.mWarningInfoText) ? 8 : 0);
            textView.setText(this.mWarningInfoText);
            textView2.setVisibility(TextUtils.isEmpty(this.mLimitInfoText) ? 8 : 0);
            textView2.setText(this.mLimitInfoText);
        }
        if (this.mUsageTitle != null) {
            final Formatter.BytesResult formatBytes =
                    this.mDataUsageFeatureProvider.formatBytes(
                            getContext().getResources(), this.mDataplanUse, 4);
            try {
                final ValueAnimator ofInt =
                        ValueAnimator.ofInt(0, Integer.parseInt(formatBytes.value));
                ofInt.setInterpolator(
                        new com.samsung.android.graphics.spr.animation.interpolator.SineInOut90());
                ofInt.setDuration((int) (this.mProgress * 2000.0f));
                ofInt.addUpdateListener(
                        new ValueAnimator
                                .AnimatorUpdateListener() { // from class:
                                                            // com.android.settings.datausage.DataUsageSummaryPreference.1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                                Context context = DataUsageSummaryPreference.this.getContext();
                                DataUsageSummaryPreference dataUsageSummaryPreference =
                                        DataUsageSummaryPreference.this;
                                SpannableString spannableString =
                                        new SpannableString(
                                                context.getString(
                                                        dataUsageSummaryPreference
                                                                .mDataUsageTemplate,
                                                        dataUsageSummaryPreference
                                                                .getContext()
                                                                .getString(
                                                                        R.string.fileSizeSuffix,
                                                                        String.format(
                                                                                "%d",
                                                                                ofInt
                                                                                        .getAnimatedValue()),
                                                                        ApnSettings.MVNO_NONE,
                                                                        formatBytes.units)));
                                DataUsageSummaryPreference.smallSpanExcept$1(
                                        spannableString,
                                        DataUsageSummaryPreference.this
                                                .getContext()
                                                .getString(
                                                        R.string.fileSizeSuffix,
                                                        String.format(
                                                                "%d", ofInt.getAnimatedValue()),
                                                        ApnSettings.MVNO_NONE,
                                                        formatBytes.units));
                                DataUsageSummaryPreference.this.mUsageTitle.setText(
                                        spannableString);
                            }
                        });
                ofInt.start();
            } catch (NumberFormatException e) {
                Log.i(this.TAG, "NumberFormatException " + e);
                SpannableString spannableString =
                        new SpannableString(
                                getContext()
                                        .getString(
                                                this.mDataUsageTemplate,
                                                getContext()
                                                        .getString(
                                                                R.string.fileSizeSuffix,
                                                                formatBytes.value,
                                                                ApnSettings.MVNO_NONE,
                                                                formatBytes.units)));
                smallSpanExcept$1(
                        spannableString,
                        getContext()
                                .getString(
                                        R.string.fileSizeSuffix,
                                        formatBytes.value,
                                        ApnSettings.MVNO_NONE,
                                        formatBytes.units));
                this.mUsageTitle.setText(spannableString);
            }
        }
        List<SubscriptionInfo> completeActiveSubscriptionInfoList =
                ((SubscriptionManager)
                                getContext().getSystemService("telephony_subscription_service"))
                        .getCompleteActiveSubscriptionInfoList();
        if (completeActiveSubscriptionInfoList != null
                && completeActiveSubscriptionInfoList.size() <= 0) {
            this.mCurrentDataSimTitle.setVisibility(8);
            return;
        }
        if (!DataUsageUtils.hasActiveSim(getContext())) {
            this.mCurrentDataSimTitle.setVisibility(8);
            return;
        }
        int slotIndex =
                SubscriptionManager.getSlotIndex(
                        SubscriptionManager.getDefaultDataSubscriptionId());
        if (slotIndex == -1) {
            this.mCurrentDataSimTitle.setVisibility(8);
        } else {
            this.mCurrentDataSimTitle.setText(DataUsageUtils.getSimName(getContext(), slotIndex));
            this.mCurrentDataSimTitle.setVisibility(0);
        }
    }

    public final void setLimitInfo(CharSequence charSequence) {
        if (Objects.equals(charSequence, this.mLimitInfoText)) {
            return;
        }
        this.mLimitInfoText = charSequence;
        notifyChanged();
    }

    public final void setUsageInfo(Long l, Long l2) {
        this.mCycleStartTimeMs = l.longValue();
        this.mCycleEndTimeMs =
                Long.valueOf(l2.longValue() > 0 ? l2.longValue() - 1 : l2.longValue());
        notifyChanged();
    }

    public final void setWarningInfo(CharSequence charSequence) {
        if (Objects.equals(charSequence, this.mWarningInfoText)) {
            return;
        }
        this.mWarningInfoText = charSequence;
        notifyChanged();
    }

    public final void updateCycleTimeText(PreferenceViewHolder preferenceViewHolder) {
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.cycle_left_time);
        if (this.mCycleEndTimeMs == null) {
            textView.setVisibility(8);
            return;
        }
        textView.setVisibility(0);
        Context context = getContext();
        long j = this.mCycleStartTimeMs;
        String formatDateRange = Utils.formatDateRange(context, j, j);
        String formatDateRange2 =
                Utils.formatDateRange(
                        getContext(),
                        this.mCycleEndTimeMs.longValue(),
                        this.mCycleEndTimeMs.longValue());
        textView.setText(
                getContext()
                        .getString(
                                R.string.sec_data_usage_summary_preference_cycle_label,
                                formatDateRange,
                                formatDateRange2));
        textView.setContentDescription(
                getContext()
                        .getString(
                                R.string.sec_data_usage_summary_preference_cycle_label_tts,
                                formatDateRange,
                                formatDateRange2));
    }
}
