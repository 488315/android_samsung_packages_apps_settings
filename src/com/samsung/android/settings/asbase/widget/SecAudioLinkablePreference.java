package com.samsung.android.settings.asbase.widget;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreference;

import com.android.settings.R;
import com.android.settings.R$styleable;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecAudioLinkablePreference extends SecPreference {
    public final AnonymousClass1 mClickableSpanLink;
    public final Context mContext;
    public final int mPositionMode;
    public TextView mTextView;

    /* renamed from: -$$Nest$mlaunchLink, reason: not valid java name */
    public static void m1122$$Nest$mlaunchLink(
            SecAudioLinkablePreference secAudioLinkablePreference) {
        String destinationName = secAudioLinkablePreference.getDestinationName();
        Bundle bundle = secAudioLinkablePreference.getBundle();
        int titleResource$1 = secAudioLinkablePreference.getTitleResource$1();
        if (ApnSettings.MVNO_NONE.equals(destinationName)) {
            secAudioLinkablePreference.mContext.startActivity(
                    secAudioLinkablePreference.getIntent());
            return;
        }
        SubSettingLauncher subSettingLauncher =
                new SubSettingLauncher(secAudioLinkablePreference.mContext);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = destinationName;
        subSettingLauncher.setTitleRes(titleResource$1, null);
        launchRequest.mSourceMetricsCategory = FileType.SDOCX;
        launchRequest.mArguments = bundle;
        subSettingLauncher.launch();
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.samsung.android.settings.asbase.widget.SecAudioLinkablePreference$1] */
    public SecAudioLinkablePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPositionMode = 0;
        this.mContext = context;
        setSelectable(false);
        this.mClickableSpanLink =
                new ClickableSpan() { // from class:
                                      // com.samsung.android.settings.asbase.widget.SecAudioLinkablePreference.1
                    @Override // android.text.style.ClickableSpan
                    public final void onClick(View view) {
                        try {
                            view.playSoundEffect(0);
                            SecAudioLinkablePreference.m1122$$Nest$mlaunchLink(
                                    SecAudioLinkablePreference.this);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                };
        if (attributeSet == null) {
            setLayoutResource(R.layout.sec_widget_preference_unclickable);
            seslSetSubheaderRoundedBackground(15);
            return;
        }
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.SecUnclickable);
        this.mPositionMode = obtainStyledAttributes.getInt(0, 0);
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 =
                context.obtainStyledAttributes(attributeSet, R$styleable.Preference, i, i2);
        setLayoutResource(
                obtainStyledAttributes2.getResourceId(
                        3, R.layout.sec_widget_preference_unclickable));
        obtainStyledAttributes2.recycle();
        TypedArray obtainStyledAttributes3 =
                context.obtainStyledAttributes(attributeSet, R$styleable.SecCategory);
        seslSetSubheaderRoundedBackground(obtainStyledAttributes3.getInt(0, 15));
        obtainStyledAttributes3.recycle();
    }

    public Bundle getBundle() {
        return new Bundle();
    }

    public String getDestinationName() {
        return ApnSettings.MVNO_NONE;
    }

    public int getGuideTextResource() {
        return 0;
    }

    @Override // androidx.preference.Preference
    public Intent getIntent() {
        return new Intent();
    }

    public int getTitleResource$1() {
        return 0;
    }

    public int getTopMargin() {
        return this.mContext
                .getResources()
                .getDimensionPixelSize(R.dimen.sec_widget_preference_unclickable_margin_top);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.title);
        this.mTextView = textView;
        if (textView != null && this.mContext != null) {
            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) textView.getLayoutParams();
            int dimensionPixelSize =
                    this.mContext
                            .getResources()
                            .getDimensionPixelSize(R.dimen.sec_widget_body_text_padding_start_end);
            int topMargin = getTopMargin();
            int dimensionPixelSize2 =
                    this.mContext
                            .getResources()
                            .getDimensionPixelSize(
                                    R.dimen.sec_widget_preference_unclickable_margin_bottom);
            int i = this.mPositionMode;
            if (i == 1) {
                topMargin =
                        this.mContext
                                .getResources()
                                .getDimensionPixelSize(
                                        R.dimen.sec_widget_preference_unclickable_first_margin_top);
                dimensionPixelSize2 =
                        this.mContext
                                .getResources()
                                .getDimensionPixelSize(
                                        R.dimen
                                                .sec_widget_preference_unclickable_first_margin_bottom);
            } else if (i == 2) {
                topMargin =
                        this.mContext
                                .getResources()
                                .getDimensionPixelSize(
                                        R.dimen
                                                .sec_widget_preference_unclickable_subheader_margin_top);
                dimensionPixelSize2 =
                        this.mContext
                                .getResources()
                                .getDimensionPixelSize(
                                        R.dimen
                                                .sec_widget_preference_unclickable_subheader_margin_bottom);
            }
            layoutParams.setMargins(
                    dimensionPixelSize, topMargin, dimensionPixelSize, dimensionPixelSize2);
            this.mTextView.setLayoutParams(layoutParams);
            this.mTextView.setText(getTitle());
            this.mTextView.setVisibility(0);
            this.mTextView.setSingleLine(false);
            TextView textView2 = this.mTextView;
            String replace =
                    this.mContext
                            .getString(getGuideTextResource())
                            .replace("%1$s", DATA.DM_FIELD_INDEX.SMS_WRITE_UICC)
                            .replace("%2$s", DATA.DM_FIELD_INDEX.EMERGENCY_CONTROL_PREF);
            int indexOf = TextUtils.indexOf(replace, DATA.DM_FIELD_INDEX.SMS_WRITE_UICC);
            int indexOf2 = TextUtils.indexOf(replace, DATA.DM_FIELD_INDEX.EMERGENCY_CONTROL_PREF);
            if (indexOf != -1 && indexOf2 != -1) {
                int i2 = indexOf2 + 2;
                if (indexOf <= i2) {
                    i2 = indexOf;
                    indexOf = i2;
                }
                String replaceAll =
                        replace.replaceAll(
                                        DATA.DM_FIELD_INDEX.SMS_WRITE_UICC, ApnSettings.MVNO_NONE)
                                .replaceAll(
                                        DATA.DM_FIELD_INDEX.EMERGENCY_CONTROL_PREF,
                                        ApnSettings.MVNO_NONE);
                int i3 = indexOf - 4;
                if (i3 > replaceAll.length()) {
                    textView2.setText(replaceAll);
                } else {
                    SpannableStringBuilder spannableStringBuilder =
                            new SpannableStringBuilder(replaceAll);
                    spannableStringBuilder.setSpan(this.mClickableSpanLink, i2, i3, 0);
                    spannableStringBuilder.setSpan(new StyleSpan(1), i2, i3, 0);
                    spannableStringBuilder.setSpan(
                            new ForegroundColorSpan(
                                    getContext()
                                            .getColor(R.color.sec_widget_relative_link_text_color)),
                            i2,
                            i3,
                            0);
                    textView2.setMovementMethod(LinkMovementMethod.getInstance());
                    textView2.setText(spannableStringBuilder);
                }
            }
        }
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
    }

    public SecAudioLinkablePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0);
    }
}
