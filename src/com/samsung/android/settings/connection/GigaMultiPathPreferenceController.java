package com.samsung.android.settings.connection;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class GigaMultiPathPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    private static final String PACKAGE_LGU_SETTINGS = "com.lguplus.lgusetting";
    private static final String TAG = "GigaMultiPathPreferenceController";
    private boolean mIsSecondaryUser;
    private ContentObserver mMPTCPObserver;
    private int mMyUserId;
    private SecPreferenceScreen mPreference;

    public GigaMultiPathPreferenceController(Context context, String str) {
        super(context, str);
        int myUserId = UserHandle.myUserId();
        this.mMyUserId = myUserId;
        this.mIsSecondaryUser = myUserId != 0;
    }

    private ContentObserver getmMPTCPObserver() {
        if (this.mMPTCPObserver == null) {
            this.mMPTCPObserver =
                    new ContentObserver(
                            new Handler()) { // from class:
                                             // com.samsung.android.settings.connection.GigaMultiPathPreferenceController.1
                        @Override // android.database.ContentObserver
                        public final void onChange(boolean z) {
                            if (GigaMultiPathPreferenceController.this.mPreference != null) {
                                GigaMultiPathPreferenceController
                                        gigaMultiPathPreferenceController =
                                                GigaMultiPathPreferenceController.this;
                                gigaMultiPathPreferenceController.refreshSummary(
                                        gigaMultiPathPreferenceController.mPreference);
                            }
                        }
                    };
        }
        return this.mMPTCPObserver;
    }

    private void registerForObserver() {
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("mptcp_value"), false, getmMPTCPObserver());
    }

    private void unregisterForObserver() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mMPTCPObserver);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecPreferenceScreen secPreferenceScreen =
                (SecPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = secPreferenceScreen;
        if (secPreferenceScreen != null) {
            if (ConnectionsUtils.isSupportMptcp()
                    && ConnectionsUtils.isFastWebSIMValid(this.mContext)
                    && !this.mIsSecondaryUser) {
                this.mPreference.setTitle(R.string.fastweb_mptcp_title);
                this.mPreference.setFragment(
                        "com.samsung.android.settings.connection.GigaLteSettings");
            } else if (Rune.isDomesticSKTModel()) {
                if (ConnectionsUtils.isSupport5GConcept()) {
                    this.mPreference.setTitle(R.string.band_lte_title_5G);
                } else {
                    this.mPreference.setTitle(R.string.band_lte_title);
                }
                this.mPreference.setFragment(
                        "com.samsung.android.settings.connection.GigaLteSettings");
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!ConnectionsUtils.isSupportMptcp()) {
            return 3;
        }
        if (Rune.isDomesticLGTModel() || Rune.isDomesticSKTModel()) {
            return ((Rune.isDomesticLGTModel()
                                    && Utils.hasPackage(this.mContext, PACKAGE_LGU_SETTINGS))
                            || this.mIsSecondaryUser)
                    ? 3
                    : 0;
        }
        return 3;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        boolean z =
                Settings.System.getInt(this.mContext.getContentResolver(), "mptcp_value", 0) != 0;
        SecPreferenceScreen secPreferenceScreen = this.mPreference;
        if (secPreferenceScreen != null) {
            secPreferenceScreen.getClass();
            SecPreferenceUtils.applySummaryColor(secPreferenceScreen, z);
        }
        return this.mContext.getString(z ? R.string.switch_on_text : R.string.switch_off_text);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        unregisterForObserver();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        registerForObserver();
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
