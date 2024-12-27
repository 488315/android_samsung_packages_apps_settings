package com.android.settings.privatespace;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.navigation.fragment.NavHostFragment;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.R;
import com.android.settings.core.InstrumentedFragment;

import com.google.android.setupdesign.GlifLayout;
import com.sec.ims.ImsManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrivateSpaceCreationFragment extends InstrumentedFragment {
    public static final Handler sHandler = new Handler(Looper.getMainLooper());
    public final PrivateSpaceCreationFragment$$ExternalSyntheticLambda0 mAccountLoginRunnable;
    public final AnonymousClass1 mProfileAccessReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.settings.privatespace.PrivateSpaceCreationFragment.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (action.equals("android.intent.action.PROFILE_ACCESSIBLE")) {
                        Log.i("PrivateSpaceCreateFrag", "onReceive ".concat(action));
                        Handler handler = PrivateSpaceCreationFragment.sHandler;
                        handler.removeCallbacks(
                                PrivateSpaceCreationFragment.this.mAccountLoginRunnable);
                        handler.post(PrivateSpaceCreationFragment.this.mAccountLoginRunnable);
                    }
                }
            };
    public final PrivateSpaceCreationFragment$$ExternalSyntheticLambda0 mRunnable;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.privatespace.PrivateSpaceCreationFragment$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.privatespace.PrivateSpaceCreationFragment$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settings.privatespace.PrivateSpaceCreationFragment$1] */
    public PrivateSpaceCreationFragment() {
        final int i = 0;
        this.mRunnable =
                new Runnable(
                        this) { // from class:
                                // com.android.settings.privatespace.PrivateSpaceCreationFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ PrivateSpaceCreationFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i2 = i;
                        PrivateSpaceCreationFragment privateSpaceCreationFragment = this.f$0;
                        switch (i2) {
                            case 0:
                                Handler handler = PrivateSpaceCreationFragment.sHandler;
                                if (!PrivateSpaceMaintainer.getInstance(
                                                privateSpaceCreationFragment.getActivity())
                                        .createPrivateSpace()) {
                                    privateSpaceCreationFragment.mMetricsFeatureProvider.action(
                                            privateSpaceCreationFragment.getContext(), 1886, false);
                                    NavHostFragment.Companion.findNavController(
                                                    privateSpaceCreationFragment)
                                            .navigate(R.id.action_create_profile_error);
                                    break;
                                } else {
                                    Log.i("PrivateSpaceCreateFrag", "Private Space created");
                                    privateSpaceCreationFragment.mMetricsFeatureProvider.action(
                                            privateSpaceCreationFragment.getContext(), 1886, true);
                                    NetworkInfo activeNetworkInfo =
                                            ((ConnectivityManager)
                                                            privateSpaceCreationFragment
                                                                    .getActivity()
                                                                    .getSystemService(
                                                                            "connectivity"))
                                                    .getActiveNetworkInfo();
                                    if (activeNetworkInfo != null
                                            && activeNetworkInfo.isConnectedOrConnecting()) {
                                        IntentFilter m =
                                                AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                                                        "android.intent.action.PROFILE_ACCESSIBLE");
                                        if (privateSpaceCreationFragment.getContext() != null) {
                                            privateSpaceCreationFragment
                                                    .getContext()
                                                    .registerReceiver(
                                                            privateSpaceCreationFragment
                                                                    .mProfileAccessReceiver,
                                                            m);
                                        }
                                        PrivateSpaceCreationFragment.sHandler.postDelayed(
                                                privateSpaceCreationFragment.mAccountLoginRunnable,
                                                5000L);
                                        break;
                                    } else {
                                        NavHostFragment.Companion.findNavController(
                                                        privateSpaceCreationFragment)
                                                .navigate(R.id.action_set_lock_fragment);
                                        break;
                                    }
                                }
                                break;
                            default:
                                if (privateSpaceCreationFragment.mProfileAccessReceiver != null
                                        && privateSpaceCreationFragment.isAdded()
                                        && privateSpaceCreationFragment.getContext() != null) {
                                    privateSpaceCreationFragment
                                            .getContext()
                                            .unregisterReceiver(
                                                    privateSpaceCreationFragment
                                                            .mProfileAccessReceiver);
                                }
                                if (privateSpaceCreationFragment.isAdded()
                                        && privateSpaceCreationFragment.getContext() != null
                                        && privateSpaceCreationFragment.getActivity() != null) {
                                    Intent intent =
                                            new Intent(
                                                    privateSpaceCreationFragment.getContext(),
                                                    (Class<?>)
                                                            PrivateProfileContextHelperActivity
                                                                    .class);
                                    intent.putExtra(ImsManager.INTENT_PARAM_RCS_ENABLE_TYPE, 2);
                                    privateSpaceCreationFragment.mMetricsFeatureProvider.action(
                                            privateSpaceCreationFragment.getContext(),
                                            1894,
                                            new Pair[0]);
                                    privateSpaceCreationFragment
                                            .getActivity()
                                            .startActivityForResult(intent, 2);
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mAccountLoginRunnable =
                new Runnable(
                        this) { // from class:
                                // com.android.settings.privatespace.PrivateSpaceCreationFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ PrivateSpaceCreationFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i22 = i2;
                        PrivateSpaceCreationFragment privateSpaceCreationFragment = this.f$0;
                        switch (i22) {
                            case 0:
                                Handler handler = PrivateSpaceCreationFragment.sHandler;
                                if (!PrivateSpaceMaintainer.getInstance(
                                                privateSpaceCreationFragment.getActivity())
                                        .createPrivateSpace()) {
                                    privateSpaceCreationFragment.mMetricsFeatureProvider.action(
                                            privateSpaceCreationFragment.getContext(), 1886, false);
                                    NavHostFragment.Companion.findNavController(
                                                    privateSpaceCreationFragment)
                                            .navigate(R.id.action_create_profile_error);
                                    break;
                                } else {
                                    Log.i("PrivateSpaceCreateFrag", "Private Space created");
                                    privateSpaceCreationFragment.mMetricsFeatureProvider.action(
                                            privateSpaceCreationFragment.getContext(), 1886, true);
                                    NetworkInfo activeNetworkInfo =
                                            ((ConnectivityManager)
                                                            privateSpaceCreationFragment
                                                                    .getActivity()
                                                                    .getSystemService(
                                                                            "connectivity"))
                                                    .getActiveNetworkInfo();
                                    if (activeNetworkInfo != null
                                            && activeNetworkInfo.isConnectedOrConnecting()) {
                                        IntentFilter m =
                                                AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                                                        "android.intent.action.PROFILE_ACCESSIBLE");
                                        if (privateSpaceCreationFragment.getContext() != null) {
                                            privateSpaceCreationFragment
                                                    .getContext()
                                                    .registerReceiver(
                                                            privateSpaceCreationFragment
                                                                    .mProfileAccessReceiver,
                                                            m);
                                        }
                                        PrivateSpaceCreationFragment.sHandler.postDelayed(
                                                privateSpaceCreationFragment.mAccountLoginRunnable,
                                                5000L);
                                        break;
                                    } else {
                                        NavHostFragment.Companion.findNavController(
                                                        privateSpaceCreationFragment)
                                                .navigate(R.id.action_set_lock_fragment);
                                        break;
                                    }
                                }
                                break;
                            default:
                                if (privateSpaceCreationFragment.mProfileAccessReceiver != null
                                        && privateSpaceCreationFragment.isAdded()
                                        && privateSpaceCreationFragment.getContext() != null) {
                                    privateSpaceCreationFragment
                                            .getContext()
                                            .unregisterReceiver(
                                                    privateSpaceCreationFragment
                                                            .mProfileAccessReceiver);
                                }
                                if (privateSpaceCreationFragment.isAdded()
                                        && privateSpaceCreationFragment.getContext() != null
                                        && privateSpaceCreationFragment.getActivity() != null) {
                                    Intent intent =
                                            new Intent(
                                                    privateSpaceCreationFragment.getContext(),
                                                    (Class<?>)
                                                            PrivateProfileContextHelperActivity
                                                                    .class);
                                    intent.putExtra(ImsManager.INTENT_PARAM_RCS_ENABLE_TYPE, 2);
                                    privateSpaceCreationFragment.mMetricsFeatureProvider.action(
                                            privateSpaceCreationFragment.getContext(),
                                            1894,
                                            new Pair[0]);
                                    privateSpaceCreationFragment
                                            .getActivity()
                                            .startActivityForResult(intent, 2);
                                    break;
                                }
                                break;
                        }
                    }
                };
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2059;
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
        GlifLayout glifLayout =
                (GlifLayout)
                        layoutInflater.inflate(
                                R.layout.private_space_create_screen, viewGroup, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new AnonymousClass2(true));
        return glifLayout;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        sHandler.removeCallbacks(this.mRunnable);
        super.onDestroy();
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Handler handler = sHandler;
        handler.removeCallbacks(this.mRunnable);
        handler.postDelayed(this.mRunnable, 1000L);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.privatespace.PrivateSpaceCreationFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends OnBackPressedCallback {
        @Override // androidx.activity.OnBackPressedCallback
        public final void handleOnBackPressed() {}
    }
}
