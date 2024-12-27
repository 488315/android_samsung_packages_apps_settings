package com.android.settings.remoteauth.settings;

import androidx.lifecycle.ViewModel;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RemoteAuthSettingsViewModel extends ViewModel {
    public final StateFlowImpl _uiState;
    public final ReadonlyStateFlow uiState =
            FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(new RemoteAuthSettingsUiState()));
}
