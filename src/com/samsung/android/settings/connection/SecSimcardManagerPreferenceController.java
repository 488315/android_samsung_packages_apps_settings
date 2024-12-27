package com.samsung.android.settings.connection;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserManager;
import android.util.Log;

import androidx.glance.session.SessionManagerImpl$scope$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.lifecycle.LifecycleObserver;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSimcardManagerPreferenceController extends BasePreferenceController
        implements LifecycleObserver {
    private static final String KEY_SIM_CARD_MANAGER = "simcard_management";
    static final String TAG = "SecSimcardManagerPreferenceController";
    private final boolean mIsSecondaryUser;
    private Preference mPreference;
    private final UserManager mUserManager;

    public SecSimcardManagerPreferenceController(Context context, String str) {
        super(context, str);
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mIsSecondaryUser = !r1.isAdminUser();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (Rune.isChinaCTCModel()) {
            this.mPreference.setTitle(R.string.sim_card_management_ctc);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        if (((SecSimFeatureProviderImpl) featureFactoryImpl.getSecSimFeatureProvider())
                        .isMultiSimModel()
                && !this.mIsSecondaryUser
                && !Rune.isLDUModel()) {
            Log.i(TAG, "visible sim card manager menu");
            return 0;
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder("Hide sim card manager menu. MultiSimModel: ");
        sb.append(!r0.isMultiSimModel());
        sb.append(" SecondaryUser: ");
        sb.append(this.mIsSecondaryUser);
        sb.append(" LDUModel: ");
        sb.append(Rune.isLDUModel());
        Log.i(str, sb.toString());
        return 2;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        Log.d(TAG, "handlePreferenceTreeClick: " + preference);
        if (!KEY_SIM_CARD_MANAGER.equals(preference.getKey())) {
            return false;
        }
        LoggingHelper.insertFlowLogging(88);
        try {
            this.mContext.startActivity(preference.getIntent());
            return true;
        } catch (Exception e) {
            SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(
                    "Can't Start Activity: ", e, TAG);
            return true;
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
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
