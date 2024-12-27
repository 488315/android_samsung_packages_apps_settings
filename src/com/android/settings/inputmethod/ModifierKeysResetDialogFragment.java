package com.android.settings.inputmethod;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ModifierKeysResetDialogFragment extends DialogFragment {
    public SettingsMetricsFeatureProvider mMetricsFeatureProvider;

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        super.onCreateDialog(bundle);
        final FragmentActivity activity = getActivity();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        final InputManager inputManager =
                (InputManager) activity.getSystemService(InputManager.class);
        View inflate =
                LayoutInflater.from(activity)
                        .inflate(R.layout.modifier_key_reset_dialog, (ViewGroup) null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(inflate);
        AlertDialog create = builder.create();
        ((Button) inflate.findViewById(R.id.modifier_key_reset_restore_button))
                .setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.android.settings.inputmethod.ModifierKeysResetDialogFragment$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                ModifierKeysResetDialogFragment modifierKeysResetDialogFragment =
                                        ModifierKeysResetDialogFragment.this;
                                Activity activity2 = activity;
                                InputManager inputManager2 = inputManager;
                                modifierKeysResetDialogFragment.mMetricsFeatureProvider.action(
                                        activity2, 1845, new Pair[0]);
                                inputManager2.clearAllModifierKeyRemappings();
                                modifierKeysResetDialogFragment.dismissInternal(false, false);
                                activity2.recreate();
                            }
                        });
        ((Button) inflate.findViewById(R.id.modifier_key_reset_cancel_button))
                .setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.android.settings.inputmethod.ModifierKeysResetDialogFragment$$ExternalSyntheticLambda1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                ModifierKeysResetDialogFragment.this.dismissInternal(false, false);
                            }
                        });
        create.getWindow().setType(2008);
        return create;
    }
}
