package com.samsung.android.settings.datausage;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.net.NetworkPolicy;
import android.net.NetworkTemplate;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.picker.widget.SeslNumberPicker;
import androidx.picker.widget.SeslNumberPickerSpinnerDelegate;
import androidx.preference.SecPreference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settingslib.NetworkPolicyEditor;

import com.samsung.android.settings.logging.LoggingHelper;

import java.time.ZoneId;
import java.util.TimeZone;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecCycleEditorFragment extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SecPreference mAnchorView;
    public SeslNumberPicker mCycleDayPicker;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 549;
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        if (getArguments().getBoolean("is_set_cycle", false)) {
            SecBillingCycleManager.getInstance(getContext()).updateScreen();
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        NetworkTemplate parcelable = getArguments().getParcelable("template");
        this.mCycleDayPicker.clearFocus();
        int i2 = this.mCycleDayPicker.mDelegate.mValue;
        if (parcelable == null) {
            SecBillingCycleManager secBillingCycleManager =
                    SecBillingCycleManager.getInstance(getContext());
            NetworkTemplate networkTemplate = secBillingCycleManager.mNetworkTemplate;
            String id = TimeZone.getDefault().getID();
            NetworkPolicyEditor networkPolicyEditor =
                    secBillingCycleManager.mServices.mPolicyEditor;
            NetworkPolicy orCreatePolicy = networkPolicyEditor.getOrCreatePolicy(networkTemplate);
            orCreatePolicy.cycleRule = NetworkPolicy.buildRule(i2, ZoneId.of(id));
            orCreatePolicy.inferred = false;
            orCreatePolicy.clearSnooze();
            networkPolicyEditor.writeAsync();
            secBillingCycleManager.updateScreen();
        } else {
            SecBillingCycleManager secBillingCycleManager2 =
                    SecBillingCycleManager.getInstance(getContext());
            secBillingCycleManager2.getClass();
            String id2 = TimeZone.getDefault().getID();
            NetworkPolicyEditor networkPolicyEditor2 =
                    secBillingCycleManager2.mServices.mPolicyEditor;
            NetworkPolicy orCreatePolicy2 = networkPolicyEditor2.getOrCreatePolicy(parcelable);
            orCreatePolicy2.cycleRule = NetworkPolicy.buildRule(i2, ZoneId.of(id2));
            orCreatePolicy2.inferred = false;
            orCreatePolicy2.clearSnooze();
            networkPolicyEditor2.writeAsync();
            secBillingCycleManager2.updateScreen();
        }
        LoggingHelper.insertEventLogging(549, 7120);
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        int cycleDay;
        FragmentActivity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View inflate =
                LayoutInflater.from(builder.P.mContext)
                        .inflate(R.layout.sec_data_usage_cycle_editor, (ViewGroup) null, false);
        this.mCycleDayPicker = (SeslNumberPicker) inflate.findViewById(R.id.cycle_day);
        TextView textView = (TextView) inflate.findViewById(R.id.text_cycle_day);
        Utils.setMaxFontScale(activity, textView);
        textView.setMovementMethod(new ScrollingMovementMethod());
        NetworkTemplate parcelable = getArguments().getParcelable("template");
        if (bundle != null) {
            cycleDay = bundle.getInt("CYCLE_DAY");
        } else {
            SecBillingCycleManager secBillingCycleManager =
                    SecBillingCycleManager.getInstance(activity);
            cycleDay =
                    parcelable == null
                            ? secBillingCycleManager.getCycleDay(
                                    secBillingCycleManager.mNetworkTemplate)
                            : secBillingCycleManager.getCycleDay(parcelable);
        }
        Log.d(
                "SecBillingCycleSettings.SecCycleEditorFragment",
                "onCreateDialog()template:" + parcelable + " cycleDay" + cycleDay);
        this.mCycleDayPicker.setMinValue(1);
        this.mCycleDayPicker.setMaxValue(31);
        this.mCycleDayPicker.setValue(cycleDay);
        SeslNumberPickerSpinnerDelegate seslNumberPickerSpinnerDelegate =
                this.mCycleDayPicker.mDelegate;
        seslNumberPickerSpinnerDelegate.mWrapSelectorWheelPreferred = true;
        seslNumberPickerSpinnerDelegate.updateWrapSelectorWheel();
        EditText editText = this.mCycleDayPicker.mDelegate.mInputText;
        if (editText != null) {
            editText.setImeOptions(6);
            editText.setPrivateImeOptions("inputType=YearDateTime_edittext");
            editText.setOnEditorActionListener(
                    new TextView
                            .OnEditorActionListener() { // from class:
                                                        // com.samsung.android.settings.datausage.SecCycleEditorFragment.1
                        @Override // android.widget.TextView.OnEditorActionListener
                        public final boolean onEditorAction(
                                TextView textView2, int i, KeyEvent keyEvent) {
                            if (i == 6) {
                                SecCycleEditorFragment.this.mCycleDayPicker.setEditTextMode(false);
                            }
                            return false;
                        }
                    });
        }
        builder.setTitle(
                getArguments().getBoolean("is_set_cycle", false)
                        ? R.string.sec_data_usage_set_cycle
                        : R.string.data_usage_cycle_editor_title);
        builder.setView(inflate);
        builder.setPositiveButton(R.string.data_usage_cycle_editor_positive, this);
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        final AlertDialog create = builder.create();
        SecPreference secPreference = this.mAnchorView;
        if (secPreference != null) {
            Rect rect = new Rect();
            secPreference.seslGetPreferenceBounds(rect);
            create.semSetAnchor((rect.width() / 2) + rect.left, rect.bottom);
        }
        inflate.addOnLayoutChangeListener(
                new View
                        .OnLayoutChangeListener() { // from class:
                                                    // com.samsung.android.settings.datausage.SecCycleEditorFragment.2
                    @Override // android.view.View.OnLayoutChangeListener
                    public final void onLayoutChange(
                            View view,
                            int i,
                            int i2,
                            int i3,
                            int i4,
                            int i5,
                            int i6,
                            int i7,
                            int i8) {
                        SecPreference secPreference2 = SecCycleEditorFragment.this.mAnchorView;
                        AlertDialog alertDialog = create;
                        if (secPreference2 == null || alertDialog == null) {
                            return;
                        }
                        Rect rect2 = new Rect();
                        secPreference2.seslGetPreferenceBounds(rect2);
                        alertDialog.semSetAnchor((rect2.width() / 2) + rect2.left, rect2.bottom);
                    }
                });
        create.getWindow().setSoftInputMode(16);
        return create;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("CYCLE_DAY", this.mCycleDayPicker.getValue());
    }
}
