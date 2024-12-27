package com.samsung.android.settings.inputmethod;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;

import androidx.preference.Preference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecCustomDividerItemDecorator;
import com.samsung.android.settings.widget.SecRadioButtonGearPreference;
import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class CustomMousePreference extends SettingsPreferenceFragment
        implements SecRadioButtonGearPreference.OnClickListener,
                SecRadioButtonGearPreference.OnGearClickListener {
    public static final String TAG = CustomMousePreference.class.getName();
    public Context mContext;
    public int mMouseButtonSAConstantValue = -1;
    public int mMouseButtonType;
    public String[] mMouseButtonValue;

    public abstract int getButtonType();

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
        int buttonType = getButtonType();
        Context context = this.mContext;
        Uri uri = InputMethodUtils.HOME_CONTENT_URI;
        Bundle call =
                context.getContentResolver()
                        .call(
                                InputMethodUtils.HOME_CONTENT_URI,
                                "get_home_mode",
                                "home_mode",
                                (Bundle) null);
        boolean equals =
                call != null
                        ? call.getString("home_mode", ApnSettings.MVNO_NONE)
                                .equals("home_only_mode")
                        : false;
        for (int i = 0; i < this.mMouseButtonValue.length; i++) {
            SecRadioButtonGearPreference secRadioButtonGearPreference =
                    new SecRadioButtonGearPreference(this.mContext, null);
            secRadioButtonGearPreference.setKey(this.mMouseButtonValue[i]);
            int parseInt = Integer.parseInt(this.mMouseButtonValue[i]);
            secRadioButtonGearPreference.setTitle(
                    InputMethodUtils.getMouseButtonFeatureTitle(this.mContext, parseInt));
            secRadioButtonGearPreference.setChecked(parseInt == buttonType);
            if (equals && parseInt == 2) {
                secRadioButtonGearPreference.mEnableState = false;
            }
            secRadioButtonGearPreference.mListener = this;
            if (parseInt >= 10) {
                secRadioButtonGearPreference.mOnGearClickListener = this;
                secRadioButtonGearPreference.notifyChanged();
                setOpenAppSummary(secRadioButtonGearPreference);
            }
            secRadioButtonGearPreference.mButtonEnableState = buttonType >= 10;
            getPreferenceScreen().addPreference(secRadioButtonGearPreference);
        }
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        String valueOf;
        super.onDestroyView();
        if (getButtonType() >= 10) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.valueOf(getButtonType()));
            sb.append(" ");
            MouseFunctionKeyInfo.MouseFunctionKeyAppInfo mouseFunctionKeyAppInfo =
                    MouseFunctionKeyInfo.getMouseFunctionKeyAppInfo(
                            this.mContext, this.mMouseButtonType);
            sb.append(
                    (mouseFunctionKeyAppInfo == null
                                    || TextUtils.isEmpty(mouseFunctionKeyAppInfo.mLabel))
                            ? ApnSettings.MVNO_NONE
                            : ((String) mouseFunctionKeyAppInfo.mLabel).toString());
            valueOf = sb.toString();
        } else {
            valueOf = String.valueOf(getButtonType());
        }
        if (this.mMouseButtonSAConstantValue != -1) {
            LoggingHelper.insertEventLogging(
                    getMetricsCategory(), this.mMouseButtonSAConstantValue, valueOf);
        }
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonGearPreference.OnGearClickListener
    public final void onGearClick() {
        Bundle bundle = new Bundle();
        bundle.putInt("mouseButtonType", this.mMouseButtonType);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = MouseButtonAppGridView.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = bundle;
        subSettingLauncher.setTitleRes(R.string.sec_open_apps_title, null);
        launchRequest.mSourceMetricsCategory = getMetricsCategory();
        subSettingLauncher.launch();
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonGearPreference.OnClickListener
    public final void onRadioButtonClicked(
            SecRadioButtonGearPreference secRadioButtonGearPreference) {
        setButtonType(Integer.parseInt(secRadioButtonGearPreference.getKey()));
        String key = secRadioButtonGearPreference.getKey();
        for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
            Preference preference = getPreferenceScreen().getPreference(i);
            if (preference instanceof SecRadioButtonGearPreference) {
                SecRadioButtonGearPreference secRadioButtonGearPreference2 =
                        (SecRadioButtonGearPreference) preference;
                if (TextUtils.equals(secRadioButtonGearPreference2.getKey(), key)) {
                    secRadioButtonGearPreference2.setChecked(true);
                    if (Integer.parseInt(key) >= 10) {
                        secRadioButtonGearPreference2.mButtonEnableState = true;
                        String string =
                                Settings.System.getString(
                                        this.mContext.getContentResolver(),
                                        MouseFunctionKeyInfo.getMouseFunctionDBKey(
                                                Integer.parseInt(key)));
                        if (TextUtils.isEmpty(string)) {
                            onGearClick();
                        } else {
                            int indexOf = string.indexOf(47, 0);
                            String substring = string.substring(0, indexOf);
                            String substring2 = string.substring(indexOf + 1, string.length());
                            SemLog.i(
                                    TAG,
                                    "package_name = "
                                            + substring
                                            + "  activity_name = "
                                            + substring2);
                            MouseFunctionKeyInfo.setKeyCustomizationInfo(
                                    this.mMouseButtonType, substring, substring2);
                        }
                    } else {
                        secRadioButtonGearPreference2.mButtonEnableState = false;
                        MouseFunctionKeyInfo.releaseKeyCustomizationInfo(this.mMouseButtonType);
                    }
                } else {
                    secRadioButtonGearPreference2.setChecked(false);
                }
            }
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
            Preference preference = getPreferenceScreen().getPreference(i);
            if (preference instanceof SecRadioButtonGearPreference) {
                SecRadioButtonGearPreference secRadioButtonGearPreference =
                        (SecRadioButtonGearPreference) preference;
                if (Integer.parseInt(secRadioButtonGearPreference.getKey()) >= 10) {
                    setOpenAppSummary(secRadioButtonGearPreference);
                }
            }
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setDivider(null);
        getListView()
                .addItemDecoration(
                        new SecCustomDividerItemDecorator(
                                getResources().getDrawable(R.drawable.sec_top_level_list_divider),
                                this.mContext,
                                (int)
                                        (getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_with_checkbox_margin_end)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_with_checkbox_size)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_with_checkbox_margin_start)),
                                android.R.id.widget_frame,
                                android.R.id.checkbox));
    }

    public abstract void setButtonType(int i);

    public final void setOpenAppSummary(SecRadioButtonGearPreference secRadioButtonGearPreference) {
        String str;
        MouseFunctionKeyInfo.MouseFunctionKeyAppInfo mouseFunctionKeyAppInfo =
                MouseFunctionKeyInfo.getMouseFunctionKeyAppInfo(
                        this.mContext, this.mMouseButtonType);
        if (mouseFunctionKeyAppInfo != null
                && (str = mouseFunctionKeyAppInfo.mDB) != null
                && !ApnSettings.MVNO_NONE.equals(str)) {
            CharSequence charSequence = mouseFunctionKeyAppInfo.mLabel;
            if (charSequence == null || ApnSettings.MVNO_NONE.equals(charSequence)) {
                secRadioButtonGearPreference.setSummary(R.string.lock_app_shortcut_deleted_app);
            } else if (mouseFunctionKeyAppInfo.mIsEnabled) {
                secRadioButtonGearPreference.setSummary(mouseFunctionKeyAppInfo.mLabel);
            } else {
                secRadioButtonGearPreference.setSummary(
                        getResources()
                                .getString(
                                        R.string.lock_app_shortcut_disabled_app,
                                        mouseFunctionKeyAppInfo.mLabel));
            }
        }
        secRadioButtonGearPreference.mIsSummaryColorPrimaryDark = true;
        SecPreferenceUtils.applySummaryColor(secRadioButtonGearPreference, true);
    }
}
