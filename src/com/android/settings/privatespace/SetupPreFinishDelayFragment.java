package com.android.settings.privatespace;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.navigation.fragment.NavHostFragment;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.R;
import com.android.settings.accounts.ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.core.InstrumentedFragment;

import com.google.android.setupdesign.GlifLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SetupPreFinishDelayFragment extends InstrumentedFragment {
    public static final Handler sHandler = new Handler(Looper.getMainLooper());
    public boolean mActionProfileInaccessible;
    public boolean mActionProfileUnavailable;
    public final AnonymousClass1 mBroadcastReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.settings.privatespace.SetupPreFinishDelayFragment.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (intent == null) {
                        return;
                    }
                    String action = intent.getAction();
                    Log.i("SetupPreFinishDelayFrag", "Received broadcast: " + action);
                    if ("android.intent.action.PROFILE_UNAVAILABLE".equals(action)) {
                        SetupPreFinishDelayFragment.this.mActionProfileUnavailable = true;
                    } else if ("android.intent.action.PROFILE_INACCESSIBLE".equals(action)) {
                        SetupPreFinishDelayFragment.this.mActionProfileInaccessible = true;
                    }
                    SetupPreFinishDelayFragment setupPreFinishDelayFragment =
                            SetupPreFinishDelayFragment.this;
                    if (setupPreFinishDelayFragment.mActionProfileUnavailable
                            && setupPreFinishDelayFragment.mActionProfileInaccessible) {
                        SetupPreFinishDelayFragment.sHandler.removeCallbacks(
                                setupPreFinishDelayFragment.mRunnable);
                        NavHostFragment.Companion.findNavController(setupPreFinishDelayFragment)
                                .navigate(R.id.action_success_fragment);
                    }
                }
            };
    public final SetupPreFinishDelayFragment$$ExternalSyntheticLambda0 mRunnable =
            new Runnable() { // from class:
                             // com.android.settings.privatespace.SetupPreFinishDelayFragment$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SetupPreFinishDelayFragment setupPreFinishDelayFragment =
                            SetupPreFinishDelayFragment.this;
                    SetupPreFinishDelayFragment.sHandler.removeCallbacks(
                            setupPreFinishDelayFragment.mRunnable);
                    NavHostFragment.Companion.findNavController(setupPreFinishDelayFragment)
                            .navigate(R.id.action_success_fragment);
                }
            };

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2073;
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
                                R.layout.private_space_wait_screen, viewGroup, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new AnonymousClass2(true));
        if (bundle == null) {
            PrivateSpaceMaintainer privateSpaceMaintainer =
                    PrivateSpaceMaintainer.getInstance(getActivity());
            privateSpaceMaintainer.setPrivateSpaceAutoLockSetting(0);
            synchronized (privateSpaceMaintainer) {
                if (privateSpaceMaintainer.isPrivateProfileRunning()) {
                    Log.d(
                            "PrivateSpaceMaintainer",
                            "Calling requestQuietModeEnabled to enableQuietMode");
                    privateSpaceMaintainer.mUserManager.requestQuietModeEnabled(
                            true, privateSpaceMaintainer.mUserHandle);
                }
            }
        }
        return glifLayout;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        sHandler.removeCallbacks(this.mRunnable);
        super.onDestroy();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(this.mBroadcastReceiver);
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getActivity()
                .registerReceiver(
                        this.mBroadcastReceiver,
                        ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0.m(
                                "android.intent.action.PROFILE_UNAVAILABLE",
                                "android.intent.action.PROFILE_INACCESSIBLE"));
        sHandler.postDelayed(this.mRunnable, 5000L);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.privatespace.SetupPreFinishDelayFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends OnBackPressedCallback {
        @Override // androidx.activity.OnBackPressedCallback
        public final void handleOnBackPressed() {}
    }
}
