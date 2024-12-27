package com.android.settings.bluetooth;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.utils.ThreadUtils;

import com.google.common.collect.ImmutableList;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothDetailsExtraOptionsController extends BluetoothDetailsController {
    PreferenceCategory mOptionsContainer;
    public PreferenceScreen mPreferenceScreen;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bt_extra_options";
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void init(PreferenceScreen preferenceScreen) {
        this.mPreferenceScreen = preferenceScreen;
        this.mOptionsContainer =
                (PreferenceCategory) preferenceScreen.findPreference("bt_extra_options");
        refresh();
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void refresh() {
        ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                                 // com.android.settings.bluetooth.BluetoothDetailsExtraOptionsController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        final BluetoothDetailsExtraOptionsController
                                bluetoothDetailsExtraOptionsController =
                                        BluetoothDetailsExtraOptionsController.this;
                        bluetoothDetailsExtraOptionsController.getClass();
                        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                        if (featureFactoryImpl == null) {
                            throw new UnsupportedOperationException(
                                    "No feature factory configured");
                        }
                        featureFactoryImpl.getBluetoothFeatureProvider().getClass();
                        final ImmutableList of = ImmutableList.of();
                        ThreadUtils.postOnMainThread(
                                new Runnable() { // from class:
                                                 // com.android.settings.bluetooth.BluetoothDetailsExtraOptionsController$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        BluetoothDetailsExtraOptionsController
                                                bluetoothDetailsExtraOptionsController2 =
                                                        BluetoothDetailsExtraOptionsController.this;
                                        List list = of;
                                        PreferenceCategory preferenceCategory =
                                                bluetoothDetailsExtraOptionsController2
                                                        .mOptionsContainer;
                                        if (preferenceCategory != null) {
                                            preferenceCategory.removeAll();
                                            Iterator it = list.iterator();
                                            while (it.hasNext()) {
                                                bluetoothDetailsExtraOptionsController2
                                                        .mOptionsContainer.addPreference(
                                                        (Preference) it.next());
                                            }
                                            PreferenceScreen preferenceScreen =
                                                    bluetoothDetailsExtraOptionsController2
                                                            .mPreferenceScreen;
                                            preferenceScreen.getClass();
                                            bluetoothDetailsExtraOptionsController2.setVisible(
                                                    preferenceScreen,
                                                    "bt_extra_options",
                                                    !list.isEmpty());
                                        }
                                    }
                                });
                    }
                });
    }
}
