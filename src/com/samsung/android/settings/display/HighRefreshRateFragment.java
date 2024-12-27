package com.samsung.android.settings.display;

import android.content.Context;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.datetime.timezone.TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0;
import com.android.settingslib.widget.LayoutPreference;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentService;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecButtonPreference;
import com.samsung.android.settings.widget.SecRadioButtonPreference;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class HighRefreshRateFragment extends SettingsPreferenceFragment
        implements SecRadioButtonPreference.OnClickListener {
    public SecButtonPreference mApplyButton;
    public Configuration mConfig;
    public AnonymousClass1 mContentObserver;
    public Context mContext;
    public LottieAnimationView mLottie120f;
    public LottieAnimationView mLottie60f;
    public SecRadioButtonPreference mNormalMode;
    public SecRadioButtonPreference mPowerfulMode;
    public int mSeamless;
    public View mlayoutSecHrr;
    public int mFlags = 0;
    public int mMode = 0;
    public boolean mIsLowPowerMotionSmoothness = false;

    public final void finishIfNotSupported() {
        if (!SecDisplayUtils.canSetHighRefreshRate(this.mContext)) {
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            finish();
            return;
        }
        String string =
                getArguments().getString(":settings:fragment_args_key", ApnSettings.MVNO_NONE);
        if (string.equals("sec_high_refresh_rate_powerful_on")
                || string.equals("sec_high_refresh_rate_adaptive_on")) {
            boolean z = this.mSeamless == 1;
            if (string.equals(
                    z
                            ? "sec_high_refresh_rate_powerful_on"
                            : "sec_high_refresh_rate_adaptive_on")) {
                return;
            }
            Toast.makeText(
                            this.mContext,
                            z
                                    ? "Adaptive option can only be used on main screen"
                                    : "High option can only be used on sub screen",
                            0)
                    .show();
            finish();
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 10410;
    }

    public final int getRefreshRateMode() {
        return this.mMode != SecDisplayUtils.getIntRefreshRate(this.mContext, 999)
                ? this.mMode
                : SecDisplayUtils.getIntRefreshRate(this.mContext, 999);
    }

    public final void initUI$4() {
        DisplayMetrics displayMetrics;
        addPreferencesFromResource(R.xml.sec_high_refresh_rate);
        LayoutPreference layoutPreference =
                (LayoutPreference) findPreference("sec_high_refresh_rate_preview");
        LayoutPreference layoutPreference2 =
                (LayoutPreference) findPreference("sec_high_refresh_rate_preview_power_saving");
        SecRadioButtonPreference secRadioButtonPreference =
                (SecRadioButtonPreference)
                        findPreference(
                                this.mSeamless == 1
                                        ? "sec_high_refresh_rate_powerful_on"
                                        : "sec_high_refresh_rate_adaptive_on");
        this.mPowerfulMode = secRadioButtonPreference;
        secRadioButtonPreference.setVisible(true);
        this.mPowerfulMode.mListener = this;
        SecRadioButtonPreference secRadioButtonPreference2 =
                (SecRadioButtonPreference) findPreference("sec_high_refresh_rate_standard_off");
        this.mNormalMode = secRadioButtonPreference2;
        secRadioButtonPreference2.mListener = this;
        LinearLayout linearLayout =
                (LinearLayout)
                        layoutPreference.mRootView.findViewById(R.id.sec_hrr_preview_lottie_ll);
        if (linearLayout != null) {
            linearLayout.semSetRoundedCorners(15);
            linearLayout.semSetRoundedCornerColor(
                    15, this.mContext.getColor(R.color.sec_widget_round_and_bgcolor));
        }
        this.mLottie60f =
                (LottieAnimationView)
                        layoutPreference.mRootView.findViewById(R.id.sec_hrr_preview_60f_image);
        this.mLottie120f =
                (LottieAnimationView)
                        layoutPreference.mRootView.findViewById(R.id.sec_hrr_preview_120f_image);
        if (Rune.supportHighRefreshRate(this.mContext, 1)) {
            TextView textView =
                    (TextView) layoutPreference.mRootView.findViewById(R.id.sec_hrr_preview_desc);
            if (Utils.isNightMode(this.mContext)) {
                this.mLottie60f.setAnimation("sec_hrr_preview_60f_dark.json");
                this.mLottie120f.setAnimation("sec_hrr_preview_120f_dark.json");
            } else {
                this.mLottie60f.setAnimation("sec_hrr_preview_60f_light.json");
                this.mLottie120f.setAnimation("sec_hrr_preview_120f_light.json");
            }
            StringBuilder sb = new StringBuilder();
            TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0.m(
                    this.mContext, R.string.sec_high_refresh_rate_main_desc, sb, "\n");
            sb.append(this.mContext.getString(R.string.sec_high_refresh_rate_main_desc_info));
            textView.setText(sb.toString());
        } else if (Utils.isNightMode(this.mContext)) {
            this.mLottie60f.setAnimation("sec_hrr_preview_60f_dark.json");
            this.mLottie120f.setAnimation("sec_hrr_preview_120f_dark.json");
        } else {
            this.mLottie60f.setAnimation("sec_hrr_preview_60f_light.json");
            this.mLottie120f.setAnimation("sec_hrr_preview_120f_light.json");
        }
        this.mLottie60f.setRepeatCount(5);
        this.mLottie120f.setRepeatCount(5);
        if (this.mLottie60f != null
                && this.mLottie120f != null
                && (displayMetrics = this.mContext.getResources().getDisplayMetrics()) != null) {
            int min =
                    Math.min(
                            (int)
                                    ((displayMetrics.densityDpi / 160.0d)
                                            * (Utils.isTablet() ? 172 : 97)),
                            ((((int)
                                                            (Utils.getContentFrameWidthRatio(
                                                                            this.mContext)
                                                                    * displayMetrics.widthPixels))
                                                    - this.mContext
                                                            .getResources()
                                                            .getDimensionPixelOffset(
                                                                    R.dimen
                                                                            .sec_high_refresh_rate_vi_padding_between))
                                            - (this.mContext
                                                            .getResources()
                                                            .getDimensionPixelOffset(
                                                                    R.dimen
                                                                            .sec_high_refresh_rate_vi_padding)
                                                    * 2))
                                    / 2);
            int i = (int) (min * 1.79f);
            ViewGroup.LayoutParams layoutParams = this.mLottie60f.getLayoutParams();
            this.mLottie120f.getLayoutParams().width = min;
            layoutParams.width = min;
            ViewGroup.LayoutParams layoutParams2 = this.mLottie60f.getLayoutParams();
            this.mLottie120f.getLayoutParams().height = i;
            layoutParams2.height = i;
        }
        TextView textView2 =
                (TextView) layoutPreference.mRootView.findViewById(R.id.sec_hrr_preview_60f_text);
        TextView textView3 =
                (TextView) layoutPreference.mRootView.findViewById(R.id.sec_hrr_preview_120f_text);
        textView2.setText(this.mContext.getString(R.string.sec_high_refresh_rate_standard_title));
        textView3.setText(
                this.mSeamless == 1
                        ? this.mContext.getString(R.string.sec_high_refresh_rate_best_display_title)
                        : this.mContext.getString(R.string.sec_high_refresh_rate_dynamic_title));
        this.mlayoutSecHrr =
                layoutPreference2.mRootView.findViewById(
                        R.id.sec_hrr_preview_desc_power_saving_layout);
        TextView textView4 =
                (TextView)
                        layoutPreference2.mRootView.findViewById(
                                R.id.sec_hrr_preview_desc_power_saving);
        if (textView4 != null) {
            String string =
                    this.mContext.getString(
                            R.string.sec_settings_you_can_change_this_in_power_saving,
                            DATA.DM_FIELD_INDEX.SMS_WRITE_UICC,
                            DATA.DM_FIELD_INDEX.EMERGENCY_CONTROL_PREF);
            int indexOf = TextUtils.indexOf(string, DATA.DM_FIELD_INDEX.SMS_WRITE_UICC);
            int indexOf2 = TextUtils.indexOf(string, DATA.DM_FIELD_INDEX.EMERGENCY_CONTROL_PREF);
            if (indexOf != -1 && indexOf2 != -1) {
                int i2 = indexOf2 + 2;
                if (indexOf <= i2) {
                    i2 = indexOf;
                    indexOf = i2;
                }
                String replaceAll =
                        string.replaceAll(DATA.DM_FIELD_INDEX.SMS_WRITE_UICC, ApnSettings.MVNO_NONE)
                                .replaceAll(
                                        DATA.DM_FIELD_INDEX.EMERGENCY_CONTROL_PREF,
                                        ApnSettings.MVNO_NONE);
                int i3 = indexOf - 4;
                if (i3 > replaceAll.length()) {
                    textView4.setText(replaceAll);
                } else {
                    ClickableSpan clickableSpan = new ClickableSpan() { // from class:
                                // com.samsung.android.settings.display.HighRefreshRateFragment.2
                                @Override // android.text.style.ClickableSpan
                                public final void onClick(View view) {
                                    view.playSoundEffect(0);
                                    Bundle call =
                                            HighRefreshRateFragment.this
                                                    .getContentResolver()
                                                    .call(
                                                            Uri.parse(
                                                                    "content://com.samsung.android.sm.dcapi"),
                                                            "psm_start_power_saving_activity",
                                                            (String) null,
                                                            new Bundle());
                                    boolean z = call.getBoolean("result");
                                    boolean z2 = call.getBoolean("changeable");
                                    if (!z) {
                                        Log.d("HighRefreshRateFragment", "API call has failed");
                                    }
                                    if (z2) {
                                        Log.d("HighRefreshRateFragment", "Enter in power saving");
                                    }
                                }
                            };
                    SpannableStringBuilder spannableStringBuilder =
                            new SpannableStringBuilder(replaceAll);
                    spannableStringBuilder.setSpan(clickableSpan, i2, i3, 0);
                    spannableStringBuilder.setSpan(
                            new ForegroundColorSpan(
                                    this.mContext.getColor(
                                            R.color.sec_tips_description_link_text_color)),
                            i2,
                            i3,
                            0);
                    textView4.setMovementMethod(LinkMovementMethod.getInstance());
                    textView4.setText(spannableStringBuilder);
                }
            }
        }
        SecButtonPreference secButtonPreference =
                (SecButtonPreference) findPreference("sec_high_refresh_rate_apply");
        this.mApplyButton = secButtonPreference;
        secButtonPreference.mTitle =
                this.mContext.getResources().getString(R.string.sec_high_refresh_rate_apply_button);
        this.mApplyButton.mOnClickListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.display.HighRefreshRateFragment$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        HighRefreshRateFragment highRefreshRateFragment =
                                HighRefreshRateFragment.this;
                        highRefreshRateFragment.getClass();
                        LoggingHelper.insertEventLogging(10410, 10416);
                        SecDisplayUtils.putIntRefreshRate(
                                highRefreshRateFragment.mContext,
                                highRefreshRateFragment.getRefreshRateMode(),
                                999);
                        highRefreshRateFragment.finish();
                    }
                };
        refreshUI$3();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        finishIfNotSupported();
        getPreferenceScreen().removeAll();
        if ((configuration.diff(this.mConfig) & UcmAgentService.ERROR_APPLET_UNKNOWN) != 0) {
            this.mMode = SecDisplayUtils.getIntRefreshRate(this.mContext, 999);
            this.mSeamless = SecDisplayUtils.getHighRefreshRateSeamlessType(999);
            this.mConfig = new Configuration(configuration);
        }
        initUI$4();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context context = getContext();
        this.mContext = context;
        this.mMode = SecDisplayUtils.getIntRefreshRate(context, 999);
        this.mSeamless = SecDisplayUtils.getHighRefreshRateSeamlessType(999);
        this.mConfig = this.mContext.getResources().getConfiguration();
        finishIfNotSupported();
        initUI$4();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(this.mContentObserver);
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonPreference.OnClickListener
    public final void onRadioButtonClicked(SecRadioButtonPreference secRadioButtonPreference) {
        int refreshRateMode = getRefreshRateMode();
        if (this.mSeamless == 1
                && secRadioButtonPreference.equals(this.mPowerfulMode)
                && refreshRateMode != 2) {
            this.mMode = 2;
        } else {
            int i = this.mSeamless;
            if ((i == 2 || i == 3 || i == 4)
                    && secRadioButtonPreference.equals(this.mPowerfulMode)
                    && refreshRateMode != 1) {
                this.mMode = 1;
            } else if (secRadioButtonPreference.equals(this.mNormalMode) && refreshRateMode != 0) {
                this.mMode = 0;
            }
        }
        refreshUI$3();
    }

    /* JADX WARN: Type inference failed for: r0v17, types: [com.samsung.android.settings.display.HighRefreshRateFragment$1] */
    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (this.mContentObserver == null) {
            this.mContentObserver =
                    new ContentObserver(
                            new Handler()) { // from class:
                                             // com.samsung.android.settings.display.HighRefreshRateFragment.1
                        @Override // android.database.ContentObserver
                        public final void onChange(boolean z, Uri uri) {
                            onChange(z);
                            HighRefreshRateFragment highRefreshRateFragment =
                                    HighRefreshRateFragment.this;
                            highRefreshRateFragment.mMode =
                                    SecDisplayUtils.getIntRefreshRate(
                                            highRefreshRateFragment.mContext, 999);
                            HighRefreshRateFragment highRefreshRateFragment2 =
                                    HighRefreshRateFragment.this;
                            highRefreshRateFragment2.mIsLowPowerMotionSmoothness =
                                    Settings.Global.getInt(
                                                    highRefreshRateFragment2.mContext
                                                            .getContentResolver(),
                                                    "pms_settings_refresh_rate_enabled",
                                                    0)
                                            != 0;
                            if (uri.equals(Settings.Global.getUriFor("low_power"))
                                    || uri.equals(
                                            Settings.Global.getUriFor(
                                                    "pms_settings_refresh_rate_enabled"))) {
                                HighRefreshRateFragment highRefreshRateFragment3 =
                                        HighRefreshRateFragment.this;
                                if (highRefreshRateFragment3.mMode != 0
                                        && Utils.isMediumPowerSavingModeEnabled(
                                                highRefreshRateFragment3.mContext)) {
                                    HighRefreshRateFragment highRefreshRateFragment4 =
                                            HighRefreshRateFragment.this;
                                    if (highRefreshRateFragment4.mIsLowPowerMotionSmoothness) {
                                        highRefreshRateFragment4.mMode = 0;
                                        SecDisplayUtils.putIntRefreshRate(
                                                highRefreshRateFragment4.mContext, 0, 999);
                                        Log.d(
                                                "HighRefreshRateFragment",
                                                "onChange: change to normal mode");
                                    }
                                }
                            }
                            HighRefreshRateFragment.this.refreshUI$3();
                        }
                    };
        }
        this.mMode = SecDisplayUtils.getIntRefreshRate(this.mContext, 999);
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("low_power"), false, this.mContentObserver);
        getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("pms_settings_refresh_rate_enabled"),
                        false,
                        this.mContentObserver);
        this.mIsLowPowerMotionSmoothness =
                Settings.Global.getInt(
                                this.mContext.getContentResolver(),
                                "pms_settings_refresh_rate_enabled",
                                0)
                        != 0;
        if (Utils.isMediumPowerSavingModeEnabled(this.mContext)
                && this.mIsLowPowerMotionSmoothness) {
            int refreshRateMode = getRefreshRateMode();
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    refreshRateMode,
                    "adjustRefreshRateMode : currentRefreshRateMode = ",
                    "HighRefreshRateFragment");
            if (refreshRateMode != 0) {
                SecDisplayUtils.putIntRefreshRate(this.mContext, 0, 999);
                Log.d(
                        "HighRefreshRateFragment",
                        "adjustRefreshRateMode : change to 0 from " + refreshRateMode);
            }
        }
        refreshUI$3();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("HIGH_REFRESH_RATE_FLAG", this.mFlags);
        bundle.putInt("HIGH_REFRESH_RATE_MODE", this.mMode);
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        bundle.putInt("PREV_DISPLAY_DEVICE_TYPE", -1);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewStateRestored(Bundle bundle) {
        super.onViewStateRestored(bundle);
        if (bundle != null) {
            Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            if (-1 == bundle.getInt("PREV_DISPLAY_DEVICE_TYPE")) {
                this.mFlags = bundle.getInt("HIGH_REFRESH_RATE_FLAG");
                this.mMode = bundle.getInt("HIGH_REFRESH_RATE_MODE");
            }
        }
    }

    public final void refreshUI$3() {
        this.mFlags = 0;
        if (SecDisplayUtils.getScreenResolution(this.mContext) == 2
                && !SecDisplayUtils.canSetHighRefreshRateAboveWQHD(this.mContext)) {
            this.mFlags |= 1;
        }
        if (Utils.isMediumPowerSavingModeEnabled(this.mContext)
                && Settings.Global.getInt(
                                this.mContext.getContentResolver(),
                                "pms_settings_refresh_rate_enabled",
                                0)
                        != 0) {
            this.mFlags |= 2;
        }
        if (this.mFlags != 0 && this.mMode != 0) {
            this.mMode = SecDisplayUtils.getIntRefreshRate(this.mContext, 999);
        }
        this.mApplyButton.setEnabled(
                this.mMode != SecDisplayUtils.getIntRefreshRate(this.mContext, 999));
        int refreshRateMode = getRefreshRateMode();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        for (int i = 0; i < preferenceScreen.getPreferenceCount(); i++) {
            Preference preference = preferenceScreen.getPreference(i);
            if (preference instanceof SecRadioButtonPreference) {
                ((SecRadioButtonPreference) preference).setChecked(false);
            }
        }
        if (refreshRateMode == 0) {
            this.mNormalMode.setChecked(true);
        } else if (refreshRateMode == 1 || refreshRateMode == 2) {
            this.mPowerfulMode.setChecked(true);
        }
        if (SecDisplayUtils.isSupportMaxHS60RefreshRate(999)) {
            this.mNormalMode.setSummary(
                    this.mContext.getString(
                            R.string.sec_high_refresh_rate_standard_max_hs60_summary));
        } else {
            this.mNormalMode.setSummary(
                    this.mContext.getString(R.string.sec_high_refresh_rate_standard_summary));
        }
        int i2 =
                this.mSeamless == 1
                        ? R.string.sec_high_refresh_rate_best_display_pd_summary
                        : R.string.sec_high_refresh_rate_best_display_seamless_pd_summary;
        int i3 = this.mFlags & CustomDeviceManager.QUICK_PANEL_ALL;
        if (i3 == 1) {
            this.mPowerfulMode.setSummary(
                    this.mContext.getString(
                            R.string
                                    .sec_high_refresh_rate_best_display_not_supported_summary_wqhd));
            this.mPowerfulMode.setEnabled(false);
            return;
        }
        if (i3 == 2) {
            this.mPowerfulMode.setSummary(
                    String.format(
                            this.mContext.getString(i2),
                            Integer.valueOf(SecDisplayUtils.getHighRefreshRateMaxValue())));
            this.mPowerfulMode.setEnabled(false);
            this.mNormalMode.setEnabled(false);
            this.mlayoutSecHrr.setVisibility(0);
            return;
        }
        if (i3 == 3) {
            this.mPowerfulMode.setSummary(
                    this.mContext.getString(
                            R.string
                                    .sec_high_refresh_rate_best_display_not_supported_summary_wqhd_psd));
            this.mPowerfulMode.setEnabled(false);
        } else {
            this.mPowerfulMode.setSummary(
                    String.format(
                            this.mContext.getString(i2),
                            Integer.valueOf(SecDisplayUtils.getHighRefreshRateMaxValue())));
            this.mPowerfulMode.setEnabled(true);
            this.mNormalMode.setEnabled(true);
            this.mlayoutSecHrr.setVisibility(8);
        }
    }
}
