package com.android.settings.applications.defaultapps;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.fuelgauge.BatteryUtils;
import com.android.settings.widget.RadioButtonPickerFragment;
import com.android.settingslib.applications.DefaultAppInfo;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.widget.CandidateInfo;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class DefaultAppPickerFragment extends RadioButtonPickerFragment {
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
                DefaultAppPickerFragment defaultAppPickerFragment,
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
            setTargetFragment(defaultAppPickerFragment, 0);
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            Fragment targetFragment = getTargetFragment();
            if (targetFragment instanceof DefaultAppPickerFragment) {
                ((DefaultAppPickerFragment) targetFragment)
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
            builder.setPositiveButton(getContext().getString(R.string.ok), this);
            builder.setNegativeButton(R.string.cancel, this.mCancelListener);
            return builder.create();
        }
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
            CharSequence charSequence, CharSequence charSequence2, String str) {
        ConfirmationDialogFragment confirmationDialogFragment = new ConfirmationDialogFragment();
        confirmationDialogFragment.init(this, str, charSequence, charSequence2);
        return confirmationDialogFragment;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
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
            if (!TextUtils.isEmpty(confirmationTitle)) {
                newConfirmationDialogFragment(confirmationMessage, confirmationTitle, key)
                        .show(activity.getSupportFragmentManager(), "DefaultAppConfirm");
                return;
            }
            ConfirmationDialogFragment confirmationDialogFragment =
                    new ConfirmationDialogFragment();
            confirmationDialogFragment.init(this, key, confirmationMessage, null);
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
                MetricsFeatureProvider.getAttribution(activity),
                1000,
                getMetricsCategory(),
                0,
                str);
        super.onRadioButtonConfirmed(str);
    }
}
