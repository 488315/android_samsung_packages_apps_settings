package com.android.settings.remoteauth.enrolling;

import androidx.lifecycle.ViewModel;

import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ObservableProperty;
import kotlin.reflect.KProperty;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RemoteAuthEnrollEnrollingViewModel extends ViewModel {
    public static final /* synthetic */ KProperty[] $$delegatedProperties = {
        Reflection.factory.mutableProperty1(
                new MutablePropertyReference1Impl(
                        RemoteAuthEnrollEnrollingViewModel.class,
                        "selectedDevice",
                        "getSelectedDevice()Ljava/lang/Object;",
                        0))
    };
    public final StateFlowImpl _uiState;
    public final RemoteAuthEnrollEnrollingViewModel$special$$inlined$observable$1
            selectedDevice$delegate;
    public final ReadonlyStateFlow uiState;

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settings.remoteauth.enrolling.RemoteAuthEnrollEnrollingViewModel$special$$inlined$observable$1] */
    public RemoteAuthEnrollEnrollingViewModel() {
        StateFlowImpl MutableStateFlow =
                StateFlowKt.MutableStateFlow(
                        new RemoteAuthEnrollEnrollingUiState(
                                EmptyList.INSTANCE, EnrollmentUiState.NONE, null));
        this._uiState = MutableStateFlow;
        this.uiState = FlowKt.asStateFlow(MutableStateFlow);
        this.selectedDevice$delegate =
                new ObservableProperty() { // from class:
                                           // com.android.settings.remoteauth.enrolling.RemoteAuthEnrollEnrollingViewModel$special$$inlined$observable$1
                    {
                        super(null);
                    }

                    @Override // kotlin.properties.ObservableProperty
                    public final void afterChange(KProperty property) {
                        Intrinsics.checkNotNullParameter(property, "property");
                        RemoteAuthEnrollEnrollingViewModel.this.discoverDevices();
                    }
                };
    }

    public final void discoverDevices() {
        StateFlowImpl stateFlowImpl;
        Object value;
        Object value2;
        do {
            stateFlowImpl = this._uiState;
            value = stateFlowImpl.getValue();
        } while (!stateFlowImpl.compareAndSet(
                value,
                RemoteAuthEnrollEnrollingUiState.copy$default(
                        (RemoteAuthEnrollEnrollingUiState) value,
                        null,
                        EnrollmentUiState.FINDING_DEVICES,
                        5)));
        EmptyList emptyList = EmptyList.INSTANCE;
        do {
            value2 = stateFlowImpl.getValue();
        } while (!stateFlowImpl.compareAndSet(
                value2,
                RemoteAuthEnrollEnrollingUiState.copy$default(
                        (RemoteAuthEnrollEnrollingUiState) value2,
                        emptyList,
                        EnrollmentUiState.NONE,
                        4)));
    }
}
