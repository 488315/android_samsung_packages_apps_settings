package com.samsung.android.settings.inputmethod;

import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.widget.SecDividerItemDecorator;
import com.samsung.android.settings.widget.SecRadioButtonPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ShowKeyboardButtonSettings extends SettingsPreferenceFragment
        implements SecRadioButtonPreference.OnClickListener,
                CompoundButton.OnCheckedChangeListener {
    public SecRadioButtonPreference mButtonLeft;
    public SecRadioButtonPreference mButtonRight;
    public SettingsMainSwitchBar mSwitchBar;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return FileType.ODS;
    }

    public final void initPreference$6() {
        addPreferencesFromResource(R.xml.sec_virtual_keyboard_settings_position);
        setAutoRemoveInsetCategory(false);
        this.mButtonLeft =
                (SecRadioButtonPreference) findPreference("SETTINGS_KEYBOARD_BUTTON_LEFT");
        SecRadioButtonPreference secRadioButtonPreference =
                (SecRadioButtonPreference) findPreference("SETTINGS_KEYBOARD_BUTTON_RIGHT");
        this.mButtonRight = secRadioButtonPreference;
        this.mButtonLeft.mListener = this;
        secRadioButtonPreference.mListener = this;
        secRadioButtonPreference.seslSetRoundedBg(12);
        if (Settings.Secure.getInt(getContentResolver(), "show_keyboard_button_position", 0) == 0) {
            this.mButtonLeft.setChecked(true);
            this.mButtonRight.setChecked(false);
        } else {
            this.mButtonLeft.setChecked(false);
            this.mButtonRight.setChecked(true);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.addOnSwitchChangeListener(this);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        Settings.Secure.putInt(getContentResolver(), "show_keyboard_button", z ? 1 : 0);
        updateSwitchAndPreferenceState();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        getPreferenceScreen().removeAll();
        initPreference$6();
        updateSwitchAndPreferenceState();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initPreference$6();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
            this.mSwitchBar.hide();
        }
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonPreference.OnClickListener
    public final void onRadioButtonClicked(SecRadioButtonPreference secRadioButtonPreference) {
        updateRadioButtons$1(secRadioButtonPreference);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        updateSwitchAndPreferenceState();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setDivider(null);
        getListView()
                .addItemDecoration(
                        new SecDividerItemDecorator(
                                (int)
                                        getResources()
                                                .getDimension(
                                                        R.dimen.sec_widget_list_item_padding_start),
                                getContext(),
                                getResources().getDrawable(R.drawable.sec_top_level_list_divider)));
    }

    public final void updateRadioButtons$1(SecRadioButtonPreference secRadioButtonPreference) {
        if (secRadioButtonPreference.equals(this.mButtonLeft)) {
            this.mButtonLeft.setChecked(true);
            this.mButtonRight.setChecked(false);
            Settings.Secure.putInt(getContentResolver(), "show_keyboard_button_position", 0);
        } else {
            this.mButtonLeft.setChecked(false);
            this.mButtonRight.setChecked(true);
            Settings.Secure.putInt(getContentResolver(), "show_keyboard_button_position", 1);
        }
    }

    public final void updateSwitchAndPreferenceState() {
        boolean z = Settings.Secure.getInt(getContentResolver(), "show_keyboard_button", 1) > 0;
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setChecked(z);
            this.mSwitchBar.show();
        }
        this.mButtonLeft.setEnabled(z);
        this.mButtonRight.setEnabled(z);
        updateRadioButtons$1(
                Settings.Secure.getInt(getContentResolver(), "show_keyboard_button_position", 0)
                                == 0
                        ? this.mButtonLeft
                        : this.mButtonRight);
    }
}
