package com.samsung.android.settings.accessibility.base.widget;

import android.content.Context;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.RadioButtonPickerFragment;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.widget.CandidateInfo;

import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AccessibilityRadioButtonPickerFragment extends RadioButtonPickerFragment {
    public final AnonymousClass1 contentObserver =
            new ContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.samsung.android.settings.accessibility.base.widget.AccessibilityRadioButtonPickerFragment.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    AccessibilityRadioButtonPickerFragment.this.updatePreferenceStates();
                }
            };
    public SettingsMainSwitchBar mainSwitchBar;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RadioButtonCandidateInfo extends CandidateInfo {
        public final String mKey;
        public final CharSequence mLabel;

        public RadioButtonCandidateInfo(String str, CharSequence charSequence) {
            super(true);
            this.mKey = str;
            this.mLabel = charSequence;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final String getKey() {
            return this.mKey;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final Drawable loadIcon() {
            return null;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final CharSequence loadLabel() {
            return this.mLabel;
        }
    }

    public CompoundButton.OnCheckedChangeListener getMainSwitchChangeListener() {
        return null;
    }

    public List getObservableUriList() {
        return List.of();
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accessibility_radio_button_picker_basic;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final int getRadioButtonPreferenceCustomLayoutResId() {
        return R.layout.accessibility_preference_selector_with_widget;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        enableAutoFlowLogging(false);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getA11ySettingsMetricsFeatureProvider();
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        SettingsActivity settingsActivity;
        CompoundButton.OnCheckedChangeListener mainSwitchChangeListener =
                getMainSwitchChangeListener();
        if (mainSwitchChangeListener != null
                && (settingsActivity = (SettingsActivity) getActivity()) != null) {
            SettingsMainSwitchBar settingsMainSwitchBar = settingsActivity.mMainSwitch;
            this.mainSwitchBar = settingsMainSwitchBar;
            settingsMainSwitchBar.show();
            this.mainSwitchBar.addOnSwitchChangeListener(mainSwitchChangeListener);
            this.mainSwitchBar.setSessionDescription(settingsActivity.getTitle().toString());
        }
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            this.mMetricsFeatureProvider.clicked(4013, "A11Y0001");
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        updatePreferenceStates();
        this.mMetricsFeatureProvider.visible(null, 0, 4013, 0);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        getObservableUriList()
                .forEach(
                        new Consumer() { // from class:
                                         // com.samsung.android.settings.accessibility.base.widget.AccessibilityRadioButtonPickerFragment$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                AccessibilityRadioButtonPickerFragment
                                        accessibilityRadioButtonPickerFragment =
                                                AccessibilityRadioButtonPickerFragment.this;
                                accessibilityRadioButtonPickerFragment
                                        .getPrefContext()
                                        .getContentResolver()
                                        .registerContentObserver(
                                                (Uri) obj,
                                                false,
                                                accessibilityRadioButtonPickerFragment
                                                        .contentObserver);
                            }
                        });
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        if (getObservableUriList().isEmpty()) {
            return;
        }
        getPrefContext().getContentResolver().unregisterContentObserver(this.contentObserver);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setDivider(null);
        Context context = view.getContext();
        getListView()
                .addItemDecoration(
                        new DividerItemDecorator(
                                context,
                                context.getResources()
                                        .getDimensionPixelSize(R.dimen.controller_item_area)));
    }

    public abstract void updatePreferenceStates();
}
