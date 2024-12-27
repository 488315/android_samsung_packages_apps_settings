package com.samsung.android.settings.datausage;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.SecPreference;

import com.android.settings.R;
import com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeatureImpl;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

import com.samsung.android.settings.logging.LoggingHelper;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecBytesEditorFragment extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener {
    public SecPreference mAnchorView;
    public EditText mByteText;
    public View mView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.datausage.SecBytesEditorFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends NumberKeyListener {
        @Override // android.text.method.NumberKeyListener
        public final char[] getAcceptedChars() {
            return new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ',', '.'};
        }

        @Override // android.text.method.KeyListener
        public final int getInputType() {
            return 8194;
        }
    }

    public static void show(
            SecBillingCycleSettings secBillingCycleSettings,
            boolean z,
            SecPreference secPreference,
            boolean z2) {
        if (secBillingCycleSettings.isAdded()) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("limit", z);
            bundle.putBoolean("tethering", z2);
            SecBytesEditorFragment secBytesEditorFragment = new SecBytesEditorFragment();
            secBytesEditorFragment.mAnchorView = secPreference;
            secBytesEditorFragment.setArguments(bundle);
            secBytesEditorFragment.setTargetFragment(secBillingCycleSettings, 0);
            secBytesEditorFragment.show(
                    secBillingCycleSettings.getFragmentManager(), "warningEditor");
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return SfpsEnrollmentFeatureImpl.HELP_ANIMATOR_DURATION;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        long j;
        if (i != -1) {
            return;
        }
        SecBillingCycleManager secBillingCycleManager =
                SecBillingCycleManager.getInstance(getContext());
        boolean z = getArguments().getBoolean("limit");
        boolean z2 = getArguments().getBoolean("tethering");
        EditText editText = (EditText) this.mView.findViewById(R.id.bytes);
        Spinner spinner = (Spinner) this.mView.findViewById(R.id.size_spinner);
        String editable = editText.getText().toString();
        if (editable.isEmpty() || editable.equals(".")) {
            editable = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
        }
        try {
            j =
                    (long)
                            (Float.valueOf(editable).floatValue()
                                    * (spinner.getSelectedItemPosition() == 0
                                            ? 1000000L
                                            : 1000000000L));
        } catch (NumberFormatException unused) {
            Log.i("SecBillingCycleSettings.SecBytesEditorFragment", "NumberFormatException");
            j = 0;
        }
        if (j > 1.0E12f) {
            j = 1000000000000L;
        }
        if (z) {
            LoggingHelper.insertEventLogging(
                    SfpsEnrollmentFeatureImpl.HELP_ANIMATOR_DURATION, 7126);
            long policyWarningBytes = secBillingCycleManager.getPolicyWarningBytes();
            if (policyWarningBytes != -1 && policyWarningBytes > j) {
                secBillingCycleManager.setPolicyWarningBytes(j);
                Toast.makeText(getContext(), R.string.data_warning_set_toast, 0).show();
            }
            Log.d(
                    "SecBillingCycleSettings.SecBytesEditorFragment",
                    "setPolicyLimitBytes() limitBytes : " + j);
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getContext().getSystemService(InputMethodManager.class);
            if (inputMethodManager != null) {
                inputMethodManager.semForceHideSoftInput();
            }
            secBillingCycleManager.setPolicyLimitBytes(j);
        } else if (z2) {
            secBillingCycleManager.setTetheringDataWarningValue(Long.valueOf(j));
        } else {
            LoggingHelper.insertEventLogging(
                    SfpsEnrollmentFeatureImpl.HELP_ANIMATOR_DURATION, 7125);
            long policyLimitBytes = secBillingCycleManager.getPolicyLimitBytes();
            if (policyLimitBytes != -1 && policyLimitBytes < j) {
                Toast.makeText(getContext(), R.string.data_warning_set_toast, 0).show();
                j = policyLimitBytes;
            }
            Log.d(
                    "SecBillingCycleSettings.SecBytesEditorFragment",
                    "setPolicyWarningBytes() WarningBytes : " + j);
            secBillingCycleManager.setPolicyWarningBytes(j);
        }
        ((InputMethodManager) getContext().getSystemService("input_method"))
                .hideSoftInputFromWindow(this.mByteText.getWindowToken(), 0);
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        FragmentActivity activity = getActivity();
        LayoutInflater from = LayoutInflater.from(activity);
        boolean z = getArguments().getBoolean("limit");
        View inflate = from.inflate(R.layout.sec_data_usage_bytes_editor, (ViewGroup) null, false);
        this.mView = inflate;
        Spinner spinner = (Spinner) inflate.findViewById(R.id.size_spinner);
        spinner.getBackground()
                .setTint(activity.getColor(R.color.sec_sub_app_bar_spinner_arrow_color));
        EditText editText = (EditText) this.mView.findViewById(R.id.bytes);
        SecBillingCycleManager secBillingCycleManager =
                SecBillingCycleManager.getInstance(getContext());
        editText.setKeyListener(new AnonymousClass1());
        long longValue =
                getArguments().getBoolean("tethering")
                        ? secBillingCycleManager.getTetheringDataWarningValue().longValue()
                        : getArguments().getBoolean("limit")
                                ? secBillingCycleManager.getPolicyLimitBytes()
                                : secBillingCycleManager.getPolicyWarningBytes();
        int i = longValue >= 1000000000 ? 1 : 0;
        if (longValue > 1.0E12f) {
            longValue = 1000000000000L;
        }
        double d = longValue;
        double d2 = i != 0 ? 1.0E9d : 1000000.0d;
        NumberFormat numberInstance = NumberFormat.getNumberInstance();
        numberInstance.setMaximumFractionDigits(2);
        String replace = numberInstance.format(d / d2).replace(",", ".");
        editText.setText(replace);
        editText.setSelection(0, replace.length());
        spinner.setSelection(i);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(
                z
                        ? R.string.data_usage_limit_editor_title
                        : R.string.data_usage_warning_editor_title);
        builder.setView(this.mView);
        builder.setPositiveButton(R.string.data_usage_cycle_editor_positive, this);
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        AlertDialog create = builder.create();
        SecPreference secPreference = this.mAnchorView;
        if (secPreference != null) {
            Rect rect = new Rect();
            secPreference.seslGetPreferenceBounds(rect);
            create.semSetAnchor((rect.width() / 2) + rect.left, rect.bottom);
        }
        EditText editText2 = (EditText) this.mView.findViewById(R.id.bytes);
        this.mByteText = editText2;
        editText2.setPrivateImeOptions(
                "disableCommaKey=true;disableEmoticonInput=true;disableImage=true;ignoreImeInternalFlagAppWindowPortrait=true;disableAutoReplacement=true");
        this.mByteText.requestFocus();
        create.getWindow().setSoftInputMode(21);
        return create;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        EditText editText = this.mByteText;
        if (editText != null) {
            editText.requestFocus();
        }
    }
}
