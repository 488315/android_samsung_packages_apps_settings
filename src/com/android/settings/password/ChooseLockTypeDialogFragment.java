package com.android.settings.password;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

import com.google.android.setupcompat.util.WizardManagerHelper;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ChooseLockTypeDialogFragment extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener {
    public ScreenLockAdapter mAdapter;
    public ChooseLockGenericController mController;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnLockTypeSelectedListener {
        static void startChooseLockActivity(
                ScreenLockType screenLockType, FragmentActivity fragmentActivity) {
            Intent intent = fragmentActivity.getIntent();
            Intent intent2 = new Intent(fragmentActivity, (Class<?>) SetupChooseLockGeneric.class);
            intent2.addFlags(33554432);
            intent2.putExtra(
                    "request_gk_pw_handle", intent.getBooleanExtra("request_gk_pw_handle", false));
            intent2.putExtra(
                    "show_options_button", intent.getBooleanExtra("show_options_button", false));
            if (intent.hasExtra("choose_lock_generic_extras")) {
                intent2.putExtras(intent.getBundleExtra("choose_lock_generic_extras"));
            }
            intent2.putExtra("lockscreen.password_type", screenLockType.defaultQuality);
            WizardManagerHelper.copyWizardManagerExtras(intent, intent2);
            fragmentActivity.startActivity(intent2);
            fragmentActivity.finish();
        }

        void onLockTypeSelected(ScreenLockType screenLockType);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ScreenLockAdapter extends ArrayAdapter {
        public final ChooseLockGenericController mController;

        public ScreenLockAdapter(
                Context context,
                List list,
                ChooseLockGenericController chooseLockGenericController) {
            super(context, R.layout.choose_lock_dialog_item, list);
            this.mController = chooseLockGenericController;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            Context context = viewGroup.getContext();
            if (view == null) {
                view =
                        LayoutInflater.from(context)
                                .inflate(R.layout.choose_lock_dialog_item, viewGroup, false);
            }
            ScreenLockType screenLockType = (ScreenLockType) getItem(i);
            TextView textView = (TextView) view;
            textView.setText(this.mController.getTitle(screenLockType));
            int ordinal = screenLockType.ordinal();
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    ordinal != 2
                            ? ordinal != 3
                                    ? ordinal != 4
                                            ? null
                                            : context.getDrawable(R.drawable.ic_password)
                                    : context.getDrawable(R.drawable.ic_pin)
                            : context.getDrawable(R.drawable.ic_pattern),
                    (Drawable) null,
                    (Drawable) null,
                    (Drawable) null);
            return view;
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 990;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        OnLockTypeSelectedListener onLockTypeSelectedListener;
        LifecycleOwner parentFragment = getParentFragment();
        if (parentFragment instanceof OnLockTypeSelectedListener) {
            onLockTypeSelectedListener = (OnLockTypeSelectedListener) parentFragment;
        } else {
            Object context = getContext();
            onLockTypeSelectedListener =
                    context instanceof OnLockTypeSelectedListener
                            ? (OnLockTypeSelectedListener) context
                            : null;
        }
        if (onLockTypeSelectedListener != null) {
            onLockTypeSelectedListener.onLockTypeSelected(
                    (ScreenLockType) this.mAdapter.getItem(i));
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ChooseLockGenericController.Builder builder =
                new ChooseLockGenericController.Builder(
                        getContext(), getArguments().getInt("userId"));
        builder.mHideInsecureScreenLockTypes = true;
        this.mController = builder.build();
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        Context context = getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ScreenLockAdapter screenLockAdapter =
                new ScreenLockAdapter(
                        context,
                        this.mController.getVisibleAndEnabledScreenLockTypes(),
                        this.mController);
        this.mAdapter = screenLockAdapter;
        builder.setAdapter(screenLockAdapter, this);
        builder.setTitle(R.string.setup_lock_settings_options_dialog_title);
        return builder.create();
    }
}
