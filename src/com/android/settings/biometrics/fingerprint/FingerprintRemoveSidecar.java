package com.android.settings.biometrics.fingerprint;

import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.core.InstrumentedFragment;

import java.util.LinkedList;
import java.util.Queue;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FingerprintRemoveSidecar extends InstrumentedFragment {
    public Fingerprint mFingerprintRemoving;
    public FingerprintUpdater mFingerprintUpdater;
    public Listener mListener;
    public final AnonymousClass1 mRemoveCallback =
            new FingerprintManager
                    .RemovalCallback() { // from class:
                                         // com.android.settings.biometrics.fingerprint.FingerprintRemoveSidecar.1
                public final void onRemovalError(
                        Fingerprint fingerprint, int i, CharSequence charSequence) {
                    FingerprintRemoveSidecar fingerprintRemoveSidecar =
                            FingerprintRemoveSidecar.this;
                    Listener listener = fingerprintRemoveSidecar.mListener;
                    if (listener != null) {
                        FingerprintSettings.FingerprintSettingsFragment.AnonymousClass3
                                anonymousClass3 =
                                        (FingerprintSettings.FingerprintSettingsFragment
                                                        .AnonymousClass3)
                                                listener;
                        FragmentActivity activity =
                                FingerprintSettings.FingerprintSettingsFragment.this.getActivity();
                        if (activity != null) {
                            Toast.makeText(activity, charSequence, 0);
                        }
                        anonymousClass3.updateDialog();
                    } else {
                        Queue queue = fingerprintRemoveSidecar.mFingerprintsRemoved;
                        RemovalError removalError = new RemovalError();
                        removalError.fingerprint = fingerprint;
                        removalError.errString = charSequence;
                        ((LinkedList) queue).add(removalError);
                    }
                    FingerprintRemoveSidecar.this.mFingerprintRemoving = null;
                }

                public final void onRemovalSucceeded(Fingerprint fingerprint, int i) {
                    FingerprintRemoveSidecar fingerprintRemoveSidecar =
                            FingerprintRemoveSidecar.this;
                    Listener listener = fingerprintRemoveSidecar.mListener;
                    if (listener != null) {
                        FingerprintSettings.FingerprintSettingsFragment.AnonymousClass3
                                anonymousClass3 =
                                        (FingerprintSettings.FingerprintSettingsFragment
                                                        .AnonymousClass3)
                                                listener;
                        FingerprintSettings.FingerprintSettingsFragment.this
                                .mHandler
                                .obtainMessage(1000, fingerprint.getBiometricId(), 0)
                                .sendToTarget();
                        anonymousClass3.updateDialog();
                    } else {
                        ((LinkedList) fingerprintRemoveSidecar.mFingerprintsRemoved)
                                .add(fingerprint);
                    }
                    FingerprintRemoveSidecar.this.mFingerprintRemoving = null;
                }
            };
    public final Queue mFingerprintsRemoved = new LinkedList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Listener {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RemovalError {
        public int errMsgId;
        public CharSequence errString;
        public Fingerprint fingerprint;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 934;
    }

    public final boolean inProgress() {
        return this.mFingerprintRemoving != null;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    public final void setListener(
            FingerprintSettings.FingerprintSettingsFragment.AnonymousClass3 anonymousClass3) {
        if (this.mListener == null && anonymousClass3 != null) {
            while (!this.mFingerprintsRemoved.isEmpty()) {
                Object poll = ((LinkedList) this.mFingerprintsRemoved).poll();
                boolean z = poll instanceof Fingerprint;
                FingerprintSettings.FingerprintSettingsFragment fingerprintSettingsFragment =
                        FingerprintSettings.FingerprintSettingsFragment.this;
                if (z) {
                    fingerprintSettingsFragment
                            .mHandler
                            .obtainMessage(1000, ((Fingerprint) poll).getBiometricId(), 0)
                            .sendToTarget();
                    anonymousClass3.updateDialog();
                } else if (poll instanceof RemovalError) {
                    RemovalError removalError = (RemovalError) poll;
                    Fingerprint fingerprint = removalError.fingerprint;
                    CharSequence charSequence = removalError.errString;
                    FragmentActivity activity = fingerprintSettingsFragment.getActivity();
                    if (activity != null) {
                        Toast.makeText(activity, charSequence, 0);
                    }
                    anonymousClass3.updateDialog();
                }
            }
        }
        this.mListener = anonymousClass3;
    }
}
