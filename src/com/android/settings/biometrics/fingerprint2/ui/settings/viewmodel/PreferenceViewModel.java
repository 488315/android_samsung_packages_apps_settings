package com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel;

import com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class PreferenceViewModel {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DeleteDialog extends PreferenceViewModel {
        public final FingerprintData fingerprintViewModel;

        public DeleteDialog(FingerprintData fingerprintViewModel) {
            Intrinsics.checkNotNullParameter(fingerprintViewModel, "fingerprintViewModel");
            this.fingerprintViewModel = fingerprintViewModel;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof DeleteDialog)
                    && Intrinsics.areEqual(
                            this.fingerprintViewModel, ((DeleteDialog) obj).fingerprintViewModel);
        }

        public final int hashCode() {
            return this.fingerprintViewModel.hashCode();
        }

        public final String toString() {
            return "DeleteDialog(fingerprintViewModel=" + this.fingerprintViewModel + ")";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RenameDialog extends PreferenceViewModel {
        public final FingerprintData fingerprintViewModel;

        public RenameDialog(FingerprintData fingerprintViewModel) {
            Intrinsics.checkNotNullParameter(fingerprintViewModel, "fingerprintViewModel");
            this.fingerprintViewModel = fingerprintViewModel;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof RenameDialog)
                    && Intrinsics.areEqual(
                            this.fingerprintViewModel, ((RenameDialog) obj).fingerprintViewModel);
        }

        public final int hashCode() {
            return this.fingerprintViewModel.hashCode();
        }

        public final String toString() {
            return "RenameDialog(fingerprintViewModel=" + this.fingerprintViewModel + ")";
        }
    }
}
