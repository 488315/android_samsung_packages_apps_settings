package com.samsung.android.settings.applications.defaultapps;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.fuelgauge.BatteryUtils;
import com.android.settings.widget.RadioButtonPickerFragment;
import com.android.settingslib.applications.DefaultAppInfo;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.widget.CandidateInfo;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import com.samsung.android.settings.widget.SecCustomDividerItemDecorator;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class DefaultCombinedProviderAppPickerFragment extends RadioButtonPickerFragment {
    public PackageManager mPm;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class ConfirmationDialogFragment extends InstrumentedDialogFragment
            implements DialogInterface.OnClickListener {
        public DialogInterface.OnClickListener mCancelListener;

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 791;
        }

        public final void init(
                DefaultCombinedProviderAppPickerFragment defaultCombinedProviderAppPickerFragment,
                String str,
                CharSequence charSequence,
                CharSequence charSequence2) {
            Bundle bundle = new Bundle();
            bundle.putString("extra_key", str);
            bundle.putCharSequence("extra_message", charSequence);
            if (!TextUtils.isEmpty(charSequence2)) {
                bundle.putCharSequence("extra_title", charSequence2);
            }
            setArguments(bundle);
            setTargetFragment(defaultCombinedProviderAppPickerFragment, 0);
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            Fragment targetFragment = getTargetFragment();
            if (targetFragment instanceof DefaultCombinedProviderAppPickerFragment) {
                ((DefaultCombinedProviderAppPickerFragment) targetFragment)
                        .onRadioButtonConfirmed(getArguments().getString("extra_key"));
            }
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            Bundle arguments = getArguments();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            CharSequence charSequence = arguments.getCharSequence("extra_title");
            AlertController.AlertParams alertParams = builder.P;
            alertParams.mTitle = charSequence;
            alertParams.mMessage = arguments.getCharSequence("extra_message");
            builder.setPositiveButton(R.string.ok, this);
            builder.setNegativeButton(R.string.cancel, this.mCancelListener);
            return builder.create();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class CredentialManagerDialogFragment extends DialogFragment
            implements DialogInterface.OnClickListener {
        @Override // androidx.fragment.app.DialogFragment,
                  // android.content.DialogInterface.OnCancelListener
        public final void onCancel(DialogInterface dialogInterface) {
            throw null;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class ErrorDialogFragment extends CredentialManagerDialogFragment {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            throw null;
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            String string =
                    getContext()
                            .getString(com.android.settings.R.string.credman_error_message_title);
            AlertController.AlertParams alertParams = builder.P;
            alertParams.mTitle = string;
            alertParams.mMessage =
                    getContext().getString(com.android.settings.R.string.credman_error_message);
            builder.setPositiveButton(
                    getContext()
                            .getString(
                                    com.android.settings.R.string
                                            .sec_credman_services_limit_dialog_positive_button_text),
                    this);
            builder.setNegativeButton(
                    getContext()
                            .getString(
                                    com.android.settings.R.string
                                            .sec_credman_services_limit_dialog_negative_button_text),
                    (DialogInterface.OnClickListener) null);
            return builder.create();
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final void bindPreference(
            SelectorWithWidgetPreference selectorWithWidgetPreference,
            String str,
            CandidateInfo candidateInfo,
            String str2) {
        if (str.equals("com.samsung.android.samsungpassautofill")) {
            selectorWithWidgetPreference.setTitle(
                    com.android.settings.R.string.sec_samsung_pass_title);
        } else {
            selectorWithWidgetPreference.setTitle(candidateInfo.loadLabel());
        }
        selectorWithWidgetPreference.setKey(str);
        if (TextUtils.equals(str2, str)) {
            selectorWithWidgetPreference.setChecked(true);
        }
        selectorWithWidgetPreference.setEnabled(candidateInfo.enabled);
        selectorWithWidgetPreference.mListener = this;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public void bindPreferenceExtra(
            SelectorWithWidgetPreference selectorWithWidgetPreference,
            String str,
            CandidateInfo candidateInfo,
            String str2) {
        if (candidateInfo instanceof DefaultAppInfo) {
            if (TextUtils.equals(null, str)) {
                selectorWithWidgetPreference.setSummary(com.android.settings.R.string.system_app);
                return;
            }
            String str3 = ((DefaultAppInfo) candidateInfo).summary;
            if (TextUtils.isEmpty(str3)) {
                return;
            }
            selectorWithWidgetPreference.setSummary(str3);
        }
    }

    public CharSequence getConfirmationMessage(CandidateInfo candidateInfo) {
        return null;
    }

    public CharSequence getConfirmationTitle(CandidateInfo candidateInfo) {
        return null;
    }

    public ConfirmationDialogFragment newConfirmationDialogFragment(
            String str, CharSequence charSequence) {
        ConfirmationDialogFragment confirmationDialogFragment = new ConfirmationDialogFragment();
        confirmationDialogFragment.init(this, str, charSequence, null);
        return confirmationDialogFragment;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mPm = context.getPackageManager();
        BatteryUtils.getInstance(context);
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settingslib.widget.SelectorWithWidgetPreference.OnClickListener
    public final void onRadioButtonClicked(
            SelectorWithWidgetPreference selectorWithWidgetPreference) {
        String key = selectorWithWidgetPreference.getKey();
        CharSequence confirmationMessage =
                getConfirmationMessage((CandidateInfo) ((ArrayMap) this.mCandidates).get(key));
        FragmentActivity activity = getActivity();
        if (TextUtils.isEmpty(confirmationMessage)) {
            onRadioButtonConfirmed(selectorWithWidgetPreference.getKey());
            return;
        }
        if (activity != null) {
            CharSequence confirmationTitle =
                    getConfirmationTitle((CandidateInfo) ((ArrayMap) this.mCandidates).get(key));
            if (TextUtils.isEmpty(confirmationTitle)) {
                newConfirmationDialogFragment(key, confirmationMessage)
                        .show(activity.getSupportFragmentManager(), "DefaultAppConfirm");
                return;
            }
            ConfirmationDialogFragment confirmationDialogFragment =
                    new ConfirmationDialogFragment();
            confirmationDialogFragment.init(this, key, confirmationMessage, confirmationTitle);
            confirmationDialogFragment.show(
                    activity.getSupportFragmentManager(), "DefaultAppConfirm");
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final void onRadioButtonConfirmed(String str) {
        MetricsFeatureProvider metricsFeatureProvider = this.mMetricsFeatureProvider;
        FragmentActivity activity = getActivity();
        metricsFeatureProvider.getClass();
        metricsFeatureProvider.action(
                MetricsFeatureProvider.getAttribution(activity), 1000, 792, 0, str);
        super.onRadioButtonConfirmed(str);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setDivider(null);
        getListView()
                .addItemDecoration(
                        new SecCustomDividerItemDecorator(
                                getResources()
                                        .getDrawable(
                                                com.android.settings.R.drawable
                                                        .sec_top_level_list_divider),
                                getContext(),
                                (int)
                                        (getResources()
                                                        .getDimension(
                                                                com.android.settings.R.dimen
                                                                        .sec_widget_list_with_checkbox_margin_end)
                                                + getResources()
                                                        .getDimension(
                                                                com.android.settings.R.dimen
                                                                        .sec_divider_default_indent)
                                                + getResources()
                                                        .getDimension(
                                                                com.android.settings.R.dimen
                                                                        .sec_widget_list_with_checkbox_size)
                                                + getResources()
                                                        .getDimension(
                                                                com.android.settings.R.dimen
                                                                        .sec_widget_list_with_checkbox_margin_start)),
                                R.id.widget_frame,
                                R.id.checkbox));
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final void updateCandidates() {
        ArrayMap arrayMap = (ArrayMap) this.mCandidates;
        arrayMap.clear();
        List<CandidateInfo> candidates = getCandidates();
        if (candidates != null) {
            for (CandidateInfo candidateInfo : candidates) {
                arrayMap.put(candidateInfo.getKey(), candidateInfo);
            }
        }
        String defaultKey = getDefaultKey();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        preferenceScreen.removeAll();
        if (this.mIllustrationId != 0) {
            addIllustration(preferenceScreen);
        }
        if (candidates != null) {
            for (CandidateInfo candidateInfo2 : candidates) {
                SelectorWithWidgetPreference selectorWithWidgetPreference =
                        new SelectorWithWidgetPreference(getPrefContext());
                bindPreference(
                        selectorWithWidgetPreference,
                        candidateInfo2.getKey(),
                        candidateInfo2,
                        defaultKey);
                preferenceScreen.addPreference(selectorWithWidgetPreference);
            }
        }
        if (shouldShowItemNone()) {
            SelectorWithWidgetPreference selectorWithWidgetPreference2 =
                    new SelectorWithWidgetPreference(getPrefContext());
            selectorWithWidgetPreference2.setTitle(
                    com.android.settings.R.string.app_list_preference_none);
            selectorWithWidgetPreference2.setChecked(TextUtils.isEmpty(defaultKey));
            selectorWithWidgetPreference2.mListener = this;
            preferenceScreen.addPreference(selectorWithWidgetPreference2);
        }
        mayCheckOnlyRadioButton();
    }
}
