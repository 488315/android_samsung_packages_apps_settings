package com.android.settings.privatespace.delete;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.FragmentActivity;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.R;
import com.android.settings.core.InstrumentedFragment;
import com.android.settings.privatespace.PrivateSpaceMaintainer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrivateSpaceDeletionProgressFragment extends InstrumentedFragment {
    public final AnonymousClass1 mDeletePrivateSpace =
            new Runnable() { // from class:
                             // com.android.settings.privatespace.delete.PrivateSpaceDeletionProgressFragment.1
                @Override // java.lang.Runnable
                public final void run() {
                    PrivateSpaceDeletionProgressFragment.this.deletePrivateSpace();
                    PrivateSpaceDeletionProgressFragment.this.getActivity().finish();
                }
            };
    public Handler mHandler;
    public PrivateSpaceMaintainer mPrivateSpaceMaintainer;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class Injector {}

    @VisibleForTesting
    public void deletePrivateSpace() {
        PrivateSpaceMaintainer.ErrorDeletingPrivateSpace deletePrivateSpace =
                this.mPrivateSpaceMaintainer.deletePrivateSpace();
        if (deletePrivateSpace
                == PrivateSpaceMaintainer.ErrorDeletingPrivateSpace.DELETE_PS_ERROR_NONE) {
            showSuccessfulDeletionToast();
        } else if (deletePrivateSpace
                == PrivateSpaceMaintainer.ErrorDeletingPrivateSpace.DELETE_PS_ERROR_INTERNAL) {
            showDeletionInternalErrorToast();
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2040;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        if (Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures()) {
            super.onCreate(bundle);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        PrivateSpaceMaintainer privateSpaceMaintainer =
                PrivateSpaceMaintainer.getInstance(getActivity().getApplicationContext());
        this.mPrivateSpaceMaintainer = privateSpaceMaintainer;
        if (!privateSpaceMaintainer.doesPrivateSpaceExist()) {
            Log.e("PrivateSpaceDeleteProg", "Unexpected attempt to delete non-existent PS");
            getActivity().finish();
        }
        View inflate =
                layoutInflater.inflate(R.layout.private_space_confirm_deletion, viewGroup, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new AnonymousClass2(true));
        Handler handler = new Handler(Looper.getMainLooper());
        this.mHandler = handler;
        handler.postDelayed(this.mDeletePrivateSpace, 1000L);
        return inflate;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        this.mHandler.removeCallbacks(this.mDeletePrivateSpace);
        super.onDestroy();
    }

    @VisibleForTesting
    public void setPrivateSpaceMaintainer(Injector injector) {
        FragmentActivity activity = getActivity();
        injector.getClass();
        this.mPrivateSpaceMaintainer = PrivateSpaceMaintainer.getInstance(activity);
    }

    @VisibleForTesting
    public void showDeletionInternalErrorToast() {
        Toast.makeCustomToastWithIcon(
                        getContext(),
                        null,
                        getContext().getString(R.string.private_space_delete_failed),
                        0,
                        getContext().getDrawable(R.drawable.ic_private_space_icon))
                .show();
    }

    @VisibleForTesting
    public void showSuccessfulDeletionToast() {
        Toast.makeCustomToastWithIcon(
                        getContext(),
                        null,
                        getContext().getString(R.string.private_space_deleted),
                        0,
                        getContext().getDrawable(R.drawable.ic_private_space_icon))
                .show();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.privatespace.delete.PrivateSpaceDeletionProgressFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends OnBackPressedCallback {
        @Override // androidx.activity.OnBackPressedCallback
        public final void handleOnBackPressed() {}
    }
}
