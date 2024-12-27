package com.android.settingslib.core.lifecycle;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ObservableActivity extends FragmentActivity {
    public final Lifecycle mLifecycle = new Lifecycle(this);

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        Lifecycle lifecycle = this.mLifecycle;
        lifecycle.onAttach();
        lifecycle.onCreate(bundle);
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        super.onCreate(bundle);
    }

    @Override // android.app.Activity
    public final boolean onCreateOptionsMenu(Menu menu) {
        if (!super.onCreateOptionsMenu(menu)) {
            return false;
        }
        this.mLifecycle.onCreateOptionsMenu(menu, null);
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        this.mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        boolean onOptionsItemSelected = this.mLifecycle.onOptionsItemSelected(menuItem);
        return !onOptionsItemSelected
                ? super.onOptionsItemSelected(menuItem)
                : onOptionsItemSelected;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        this.mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        super.onPause();
    }

    @Override // android.app.Activity
    public final boolean onPrepareOptionsMenu(Menu menu) {
        if (!super.onPrepareOptionsMenu(menu)) {
            return false;
        }
        this.mLifecycle.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        this.mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        super.onResume();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        this.mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START);
        super.onStart();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        this.mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        super.onStop();
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle, PersistableBundle persistableBundle) {
        Lifecycle lifecycle = this.mLifecycle;
        lifecycle.onAttach();
        lifecycle.onCreate(bundle);
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        super.onCreate(bundle, persistableBundle);
    }
}
