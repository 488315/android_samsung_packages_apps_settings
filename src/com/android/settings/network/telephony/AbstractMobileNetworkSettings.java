package com.android.settings.network.telephony;

import android.os.SystemClock;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.dashboard.RestrictedDashboardFragment;
import com.android.settingslib.core.AbstractPreferenceController;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AbstractMobileNetworkSettings extends RestrictedDashboardFragment {
    public List mHiddenControllerList;
    public boolean mIsRedrawRequired;

    public final List getPreferenceControllersAsList() {
        ArrayList arrayList = new ArrayList();
        getPreferenceControllers()
                .forEach(new AbstractMobileNetworkSettings$$ExternalSyntheticLambda0(0, arrayList));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final void onExpandButtonClick() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        this.mHiddenControllerList.stream()
                .filter(new AbstractMobileNetworkSettings$$ExternalSyntheticLambda2())
                .forEach(
                        new AbstractMobileNetworkSettings$$ExternalSyntheticLambda0(
                                1, getPreferenceScreen()));
        super.onExpandButtonClick();
        this.mMetricsFeatureProvider.action(
                1571,
                1571,
                0,
                (int) (SystemClock.elapsedRealtime() - elapsedRealtime),
                "onExpandButtonClick");
    }

    public final void redrawPreferenceControllers() {
        ((ArrayList) this.mHiddenControllerList).clear();
        if (!isResumed()) {
            this.mIsRedrawRequired = true;
            return;
        }
        this.mIsRedrawRequired = false;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        List preferenceControllersAsList = getPreferenceControllersAsList();
        TelephonyStatusControlSession telephonyStatusControlSession =
                new TelephonyStatusControlSession(preferenceControllersAsList, getLifecycle());
        ((ArrayList) preferenceControllersAsList)
                .forEach(
                        new AbstractMobileNetworkSettings$$ExternalSyntheticLambda1(
                                this, getPreferenceScreen(), 1));
        this.mMetricsFeatureProvider.action(
                1571,
                1571,
                0,
                (int) (SystemClock.elapsedRealtime() - elapsedRealtime),
                "redrawPreferenceControllers");
        telephonyStatusControlSession.close();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final void updatePreferenceStates() {
        ((ArrayList) this.mHiddenControllerList).clear();
        if (this.mIsRedrawRequired) {
            redrawPreferenceControllers();
            return;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        ((ArrayList) getPreferenceControllersAsList())
                .forEach(
                        new AbstractMobileNetworkSettings$$ExternalSyntheticLambda1(
                                this, preferenceScreen, 0));
        this.mMetricsFeatureProvider.action(
                1571,
                1571,
                0,
                (int) (SystemClock.elapsedRealtime() - elapsedRealtime),
                "updatePreferenceStates");
    }

    public final void updateVisiblePreferenceControllers(
            PreferenceScreen preferenceScreen,
            AbstractPreferenceController abstractPreferenceController) {
        String preferenceKey = abstractPreferenceController.getPreferenceKey();
        Preference findPreference =
                TextUtils.isEmpty(preferenceKey)
                        ? null
                        : preferenceScreen.findPreference(preferenceKey);
        if (findPreference == null) {
            return;
        }
        if (!isPreferenceExpanded(findPreference)) {
            ((ArrayList) this.mHiddenControllerList).add(abstractPreferenceController);
        } else if (abstractPreferenceController.isAvailable()) {
            abstractPreferenceController.updateState(findPreference);
        }
    }
}
