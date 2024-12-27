package com.android.settingslib.core.lifecycle;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.lifecycle.Lifecycle;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.android.settingslib.Utils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settingslib.SettingsLibRune;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ObservablePreferenceFragment extends PreferenceFragmentCompat {
    private final Lifecycle mLifecycle = new Lifecycle(this);

    public Lifecycle getSettingsLifecycle() {
        return this.mLifecycle;
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mLifecycle.onAttach();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        if (SettingsLibRune.MENU_SHOW_EXTERNAL_CODE
                && Utils.isVisibleExternalCode(getContext())
                && getContext() != null) {
            Toast.makeText(getContext(), getClass() + ApnSettings.MVNO_NONE, 0).show();
        }
        this.mLifecycle.onCreate(bundle);
        this.mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        super.onCreate(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        this.mLifecycle.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        this.mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        super.onDestroy();
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        boolean onOptionsItemSelected = this.mLifecycle.onOptionsItemSelected(menuItem);
        if (onOptionsItemSelected) {
            return onOptionsItemSelected;
        }
        return false;
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        this.mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        super.onPause();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        this.mLifecycle.onPrepareOptionsMenu(menu);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        this.mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        super.onResume();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.mLifecycle.onSaveInstanceState(bundle);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onStart() {
        this.mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START);
        super.onStart();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onStop() {
        this.mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        super.onStop();
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public void setPreferenceScreen(PreferenceScreen preferenceScreen) {
        Lifecycle lifecycle = this.mLifecycle;
        int size = ((ArrayList) lifecycle.mObservers).size();
        for (int i = 0; i < size; i++) {}
        super.setPreferenceScreen(preferenceScreen);
    }
}
