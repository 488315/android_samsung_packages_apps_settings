package com.samsung.android.settings.languagepack.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SeslProgressBar;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.preference.SecPreference;

import com.android.settings.R;
import com.android.settings.accessibility.ScreenFlashNotificationColorDialogFragment$$ExternalSyntheticLambda0;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.settings.languagepack.LanguagePackDetailsFragment;
import com.samsung.android.settings.languagepack.LanguagePackSettingsFragment;
import com.samsung.android.settings.languagepack.data.LanguageInfo;
import com.samsung.android.settings.languagepack.logger.Log;
import com.samsung.android.settings.languagepack.service.LanguagePackDownloadService;
import com.samsung.android.sivs.ai.sdkcommon.asr.SpeechRecognitionConst;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LanguagePackPreference extends SecPreference {
    public ImageButton mCancelButton;
    public boolean mChecked;
    public final Context mContext;
    public ImageButton mDownloadButton;
    public SeslProgressBar mDownloadProgressBar;
    public ProgressBar mInstallProgressBar;
    public boolean mIsInstall;
    public boolean mIsSelectMode;
    public LanguageInfo mLanguageInfo;
    public OnClickListener mListener;
    public ImageButton mPauseButton;
    public RadioButton mRadioButton;
    public ImageButton mResumeButton;
    public LinearLayout mSummaryContainer;
    public TextView mSummaryTextView;
    public ProgressBar mWaitProgressBar;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnClickListener {}

    public LanguagePackPreference(Context context) {
        super(context, null);
        this.mListener = null;
        this.mIsSelectMode = false;
        this.mContext = context;
        setLayoutResource(R.layout.lang_pack_preference_layout);
    }

    public static String formatSize(long j) {
        return String.format("%.1f", Double.valueOf(j / 1048576.0d));
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0166  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0190  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x019e  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01c0  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01ab  */
    @Override // androidx.preference.Preference
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onBindViewHolder(androidx.preference.PreferenceViewHolder r12) {
        /*
            Method dump skipped, instructions count: 549
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.languagepack.widget.LanguagePackPreference.onBindViewHolder(androidx.preference.PreferenceViewHolder):void");
    }

    @Override // androidx.preference.Preference
    public final void onClick() {
        OnClickListener onClickListener = this.mListener;
        if (onClickListener != null) {
            LanguagePackSettingsFragment languagePackSettingsFragment =
                    (LanguagePackSettingsFragment) onClickListener;
            if (languagePackSettingsFragment.mSelectMode
                    && this.mRadioButton.getVisibility() == 0) {
                String key = getKey();
                if (!key.equals(languagePackSettingsFragment.mCurrentLangCode)) {
                    languagePackSettingsFragment.mCurrentLangCode = key;
                    languagePackSettingsFragment.updateRadioButtons();
                    if (languagePackSettingsFragment.mCallingPackage == null) {
                        Log.d(
                                "LanguagePackSettingsFragment",
                                "Calling package is null, skip the intent  : "
                                        + languagePackSettingsFragment.mCurrentLangCode);
                        return;
                    }
                    Intent intent =
                            new Intent(
                                    "com.samsung.android.settings.action.LANGUAGE_PACK_SELECTED");
                    intent.putExtra(
                            SpeechRecognitionConst.Key.LOCALE,
                            languagePackSettingsFragment.mCurrentLangCode);
                    intent.setPackage(languagePackSettingsFragment.mCallingPackage);
                    languagePackSettingsFragment.getContext().sendBroadcast(intent);
                    Log.d(
                            "LanguagePackSettingsFragment",
                            "ACTION_LANG_PACK_SELECTED : "
                                    + languagePackSettingsFragment.mCurrentLangCode
                                    + " : "
                                    + languagePackSettingsFragment.mCallingPackage);
                }
            }
        }
    }

    @Override // androidx.preference.Preference
    public final void onInitializeAccessibilityNodeInfo(
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        RadioButton radioButton = this.mRadioButton;
        if (radioButton == null || radioButton.getVisibility() != 0) {
            return;
        }
        accessibilityNodeInfoCompat.setClassName(this.mRadioButton.getAccessibilityClassName());
        accessibilityNodeInfoCompat.setCheckable(true);
        accessibilityNodeInfoCompat.setChecked(this.mRadioButton.isChecked());
    }

    @Override // androidx.preference.Preference
    public final void performClick(View view) {
        performClick();
        if (!this.mIsInstall || this.mIsSelectMode) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(SpeechRecognitionConst.Key.LOCALE, this.mLanguageInfo.mLanguageCode);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
        String name = LanguagePackDetailsFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = bundle;
        launchRequest.mTitle = getTitle();
        launchRequest.mSourceMetricsCategory = 0;
        subSettingLauncher.launch();
    }

    public final void performDownloadButton() {
        this.mLanguageInfo.mStatus = LanguagePackDownloadService.Status.STATUS_WAITING;
        this.mResumeButton.setVisibility(8);
        this.mCancelButton.setVisibility(8);
        this.mDownloadButton.setVisibility(8);
        this.mWaitProgressBar.setVisibility(0);
        setSummary(this.mContext.getString(R.string.sec_offline_lang_pack_waiting_summary));
        OnClickListener onClickListener = this.mListener;
        if (onClickListener != null) {
            LanguageInfo languageInfo = this.mLanguageInfo;
            LanguagePackSettingsFragment languagePackSettingsFragment =
                    (LanguagePackSettingsFragment) onClickListener;
            LanguagePackDownloadService.LanguagePackServiceBinder languagePackServiceBinder =
                    LanguagePackDownloadService.mLanguagePackService;
            if (languagePackServiceBinder != null) {
                String str = languagePackSettingsFragment.mCallingPackage;
                LanguagePackDownloadService.this.enqueueDownloadTask(languageInfo);
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                LanguagePackDownloadService.this.mResultBroadcastPackage = str;
            }
        }
    }

    @Override // androidx.preference.Preference
    public final void setSummary(CharSequence charSequence) {
        if (this.mSummaryTextView == null) {
            return;
        }
        if (TextUtils.isEmpty(charSequence)) {
            this.mSummaryTextView.setVisibility(8);
            return;
        }
        this.mSummaryContainer.setVisibility(0);
        this.mSummaryTextView.setVisibility(0);
        this.mSummaryTextView.setText(charSequence);
    }

    public final void showMobileNetworkWarningPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(
                this.mIsInstall
                        ? R.string.sec_offline_lang_pack_update_warning
                        : R.string.sec_offline_lang_pack_download_warning);
        builder.setNegativeButton(
                R.string.cancel,
                new ScreenFlashNotificationColorDialogFragment$$ExternalSyntheticLambda0());
        builder.setPositiveButton(
                this.mIsInstall
                        ? R.string.update_button_text
                        : R.string.sec_offline_lang_pack_dialog_positive_btn_text,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.languagepack.widget.LanguagePackPreference$$ExternalSyntheticLambda5
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        LanguagePackPreference.this.performDownloadButton();
                    }
                });
        builder.show();
    }

    public final void updateProgress$1$1() {
        LanguageInfo languageInfo = this.mLanguageInfo;
        long j = languageInfo.mCurrentDownloadedSize;
        long j2 = languageInfo.mTotalLanguagePackSize;
        if (this.mDownloadProgressBar != null) {
            this.mDownloadProgressBar.setProgress((int) Math.round((j / j2) * 100.0d));
            this.mDownloadProgressBar.invalidate();
        }
        setSummary(
                TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1
                        ? this.mContext.getString(
                                R.string.sec_offline_lang_pack_progress_bar_progress,
                                formatSize(j2),
                                formatSize(j))
                        : this.mContext.getString(
                                R.string.sec_offline_lang_pack_progress_bar_progress,
                                formatSize(j),
                                formatSize(j2)));
    }
}
