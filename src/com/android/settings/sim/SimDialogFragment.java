package com.android.settings.sim;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.network.SubscriptionsChangeListener;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SimDialogFragment extends InstrumentedDialogFragment
        implements SubscriptionsChangeListener.SubscriptionsChangeListenerClient {
    public SubscriptionsChangeListener mChangeListener;
    public boolean mWasDismissed = false;

    public static Bundle initArguments(int i, int i2) {
        Bundle bundle = new Bundle();
        bundle.putInt("dialog_type", i);
        bundle.putInt("title_id", i2);
        return bundle;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final void dismiss() {
        if (isStateSaved()) {
            Log.d("SimDialogFragment", "fragment saved state, so bypass dismiss");
        } else {
            this.mChangeListener.stop();
            dismissInternal(false, false);
        }
    }

    public final int getDialogType() {
        return getArguments().getInt("dialog_type");
    }

    @Override // com.android.settings.core.instrumentation.InstrumentedDialogFragment,
              // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        Log.d("SimDialogFragment", "Dialog Attached.");
        this.mWasDismissed = false;
        this.mChangeListener = new SubscriptionsChangeListener(context, this);
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        Log.d("SimDialogFragment", "Dialog Dismissed.");
        this.mWasDismissed = true;
        super.onDismiss(dialogInterface);
        SimDialogActivity simDialogActivity = (SimDialogActivity) getActivity();
        if (simDialogActivity == null || simDialogActivity.isFinishing()) {
            return;
        }
        List fragments =
                simDialogActivity.getSupportFragmentManager().mFragmentStore.getFragments();
        if ((fragments.size() == 1 && fragments.get(0) == this) || getDialogType() == 6) {
            Log.d("SimDialogActivity", "onFragmentDismissed dialogType:" + getDialogType());
            simDialogActivity.finishAndRemoveTask();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mChangeListener.stop();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mChangeListener.start();
    }

    @Override // com.android.settings.network.SubscriptionsChangeListener.SubscriptionsChangeListenerClient
    public final void onSubscriptionsChanged() {
        updateDialog();
    }

    public abstract void updateDialog();

    @Override // com.android.settings.network.SubscriptionsChangeListener.SubscriptionsChangeListenerClient
    public final void onAirplaneModeChanged(boolean z) {}
}
