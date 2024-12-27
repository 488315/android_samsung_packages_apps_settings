package com.android.settings.development;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentLifecycleCallbacksDispatcher;
import androidx.fragment.app.FragmentManager;

import com.android.settings.SettingsActivity;
import com.android.settingslib.development.DevelopmentSettingsEnabler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DeveloperOptionsActivityLifecycle
        implements Application.ActivityLifecycleCallbacks {
    public AnonymousClass1 mFragmentCallback;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.development.DeveloperOptionsActivityLifecycle$1, reason: invalid class name */
    public final class AnonymousClass1 extends FragmentManager.FragmentLifecycleCallbacks {
        @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
        public final void onFragmentResumed(FragmentManager fragmentManager, Fragment fragment) {
            FragmentActivity activity;
            if (!(fragment instanceof DeveloperOptionAwareMixin)
                    || (activity = fragment.getActivity()) == null
                    || DevelopmentSettingsEnabler.isDevelopmentSettingsEnabled(activity)) {
                return;
            }
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();
            } else {
                activity.finish();
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) {
        if (activity instanceof SettingsActivity) {
            ((SettingsActivity) activity)
                    .getSupportFragmentManager()
                    .mLifecycleCallbacksDispatcher
                    .mLifecycleCallbacks
                    .add(
                            new FragmentLifecycleCallbacksDispatcher
                                    .FragmentLifecycleCallbacksHolder(
                                    this.mFragmentCallback, true));
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {}

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPaused(Activity activity) {}

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityResumed(Activity activity) {}

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {}

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {}

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {}
}
