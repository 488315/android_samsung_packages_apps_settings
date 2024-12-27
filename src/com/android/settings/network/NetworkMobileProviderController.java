package com.android.settings.network;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NetworkMobileProviderController extends BasePreferenceController {
    private static final int PREFERENCE_START_ORDER = 10;
    public static final String PREF_KEY_PROVIDER_MOBILE_NETWORK = "provider_model_mobile_network";
    private static final String TAG = "NetworkMobileProviderController";
    private boolean mHide;
    private int mOriginalExpandedChildrenCount;
    private PreferenceCategory mPreferenceCategory;
    private PreferenceScreen mPreferenceScreen;
    private SubscriptionsPreferenceController mSubscriptionsController;

    public NetworkMobileProviderController(Context context, String str) {
        super(context, str);
    }

    public SubscriptionsPreferenceController createSubscriptionsController(Lifecycle lifecycle) {
        SubscriptionsPreferenceController subscriptionsPreferenceController =
                this.mSubscriptionsController;
        return subscriptionsPreferenceController == null
                ? new SubscriptionsPreferenceController(this.mContext, lifecycle, this)
                : subscriptionsPreferenceController;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (this.mSubscriptionsController == null) {
            Log.e(TAG, "[displayPreference] SubscriptionsController is null.");
            return;
        }
        this.mPreferenceScreen = preferenceScreen;
        this.mOriginalExpandedChildrenCount = preferenceScreen.mInitialExpandedChildrenCount;
        PreferenceCategory preferenceCategory =
                (PreferenceCategory)
                        preferenceScreen.findPreference(PREF_KEY_PROVIDER_MOBILE_NETWORK);
        this.mPreferenceCategory = preferenceCategory;
        preferenceCategory.setVisible(isAvailable());
        this.mSubscriptionsController.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        SubscriptionsPreferenceController subscriptionsPreferenceController;
        return (this.mHide
                        || (subscriptionsPreferenceController = this.mSubscriptionsController)
                                == null
                        || !subscriptionsPreferenceController.isAvailable())
                ? 2
                : 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void hidePreference(boolean z, boolean z2) {
        this.mHide = z;
        if (z2) {
            this.mPreferenceCategory.setVisible(z ? false : isAvailable());
        }
    }

    public void init(Lifecycle lifecycle) {
        this.mSubscriptionsController = createSubscriptionsController(lifecycle);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    public void onChildrenUpdated() {
        boolean isAvailable = isAvailable();
        int i = this.mOriginalExpandedChildrenCount;
        if (i != Integer.MAX_VALUE) {
            if (isAvailable) {
                this.mPreferenceScreen.setInitialExpandedChildrenCount(
                        this.mPreferenceCategory.getPreferenceCount() + i);
            } else {
                this.mPreferenceScreen.setInitialExpandedChildrenCount(i);
            }
        }
        this.mPreferenceCategory.setVisible(isAvailable);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
