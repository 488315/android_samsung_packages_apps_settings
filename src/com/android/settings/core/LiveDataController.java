package com.android.settings.core;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class LiveDataController extends BasePreferenceController {
    private MutableLiveData mData;
    private Preference mPreference;
    protected CharSequence mSummary;

    public LiveDataController(Context context, String str) {
        super(context, str);
        this.mData = new MutableLiveData();
        this.mSummary = context.getText(R.string.summary_placeholder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initLifeCycleOwner$0(CharSequence charSequence) {
        this.mSummary = charSequence;
        refreshSummary(this.mPreference);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initLifeCycleOwner$1() {
        this.mData.postValue(getSummaryTextInBackground());
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return this.mSummary;
    }

    public abstract CharSequence getSummaryTextInBackground();

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void initLifeCycleOwner(Fragment fragment) {
        this.mData.observe(
                fragment,
                new Observer() { // from class:
                                 // com.android.settings.core.LiveDataController$$ExternalSyntheticLambda0
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        LiveDataController.this.lambda$initLifeCycleOwner$0((CharSequence) obj);
                    }
                });
        ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                                 // com.android.settings.core.LiveDataController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        LiveDataController.this.lambda$initLifeCycleOwner$1();
                    }
                });
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
