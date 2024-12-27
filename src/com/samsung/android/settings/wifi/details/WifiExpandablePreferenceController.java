package com.samsung.android.settings.wifi.details;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnCreate;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.core.lifecycle.events.OnSaveInstanceState;

import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiExpandablePreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin,
                Preference.OnPreferenceClickListener,
                LifecycleObserver,
                OnCreate,
                OnResume,
                OnSaveInstanceState {
    public PreferenceCategory mAdvancedPref;
    public WifiExpandListener mExpandListener;
    public boolean mExpanded;
    public WifiExpandablePreference mExpanderPref;
    public final Fragment mFragment;
    public final WifiImeHelper mImeHelper;
    public final String mSAScreenId;
    public boolean mSavedExpandedState;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface WifiExpandListener {
        void onSpread();
    }

    public WifiExpandablePreferenceController(
            Context context,
            Fragment fragment,
            Lifecycle lifecycle,
            WifiImeHelper wifiImeHelper,
            String str) {
        super(context);
        this.mExpanded = false;
        this.mFragment = fragment;
        lifecycle.addObserver(this);
        this.mImeHelper = wifiImeHelper;
        this.mSAScreenId = str;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        WifiExpandablePreference wifiExpandablePreference =
                (WifiExpandablePreference) preferenceScreen.findPreference("expander");
        this.mExpanderPref = wifiExpandablePreference;
        wifiExpandablePreference.setOnPreferenceClickListener(this);
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) preferenceScreen.findPreference("advanced");
        this.mAdvancedPref = preferenceCategory;
        preferenceCategory.setVisible(this.mExpanded);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "expander";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return !this.mExpanded;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnCreate
    public final void onCreate(Bundle bundle) {
        if (bundle != null) {
            this.mSavedExpandedState = bundle.getBoolean("saved_is_expanded");
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        SALogging.insertSALog(0L, this.mSAScreenId, "1025");
        this.mExpanded = true;
        this.mExpanderPref.setVisible(!true);
        this.mAdvancedPref.setVisible(this.mExpanded);
        WifiExpandListener wifiExpandListener = this.mExpandListener;
        if (wifiExpandListener != null) {
            wifiExpandListener.onSpread();
        }
        View currentFocus = this.mFragment.getActivity().getCurrentFocus();
        if (currentFocus == null || !currentFocus.isShown()) {
            this.mImeHelper.hideSoftKeyboard(currentFocus);
        } else {
            Log.d("WifiExpandablePrefCtrl", "A view is focused, do not hide keypad");
        }
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        if (this.mSavedExpandedState) {
            this.mExpanderPref.setVisible(false);
            this.mAdvancedPref.setVisible(true);
            WifiExpandListener wifiExpandListener = this.mExpandListener;
            if (wifiExpandListener != null) {
                wifiExpandListener.onSpread();
            }
            this.mExpanded = true;
            this.mSavedExpandedState = false;
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnSaveInstanceState
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean("saved_is_expanded", this.mExpanded);
    }
}
