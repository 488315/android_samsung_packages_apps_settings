package com.android.settings.remoteauth.enrolling;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;

import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RemoteAuthEnrollEnrollingUiState {
    public final List discoveredDeviceUiStates;
    public final EnrollmentUiState enrollmentUiState;
    public final String errorMsg;

    public RemoteAuthEnrollEnrollingUiState(
            List discoveredDeviceUiStates, EnrollmentUiState enrollmentUiState, String str) {
        Intrinsics.checkNotNullParameter(discoveredDeviceUiStates, "discoveredDeviceUiStates");
        Intrinsics.checkNotNullParameter(enrollmentUiState, "enrollmentUiState");
        this.discoveredDeviceUiStates = discoveredDeviceUiStates;
        this.enrollmentUiState = enrollmentUiState;
        this.errorMsg = str;
    }

    public static RemoteAuthEnrollEnrollingUiState copy$default(
            RemoteAuthEnrollEnrollingUiState remoteAuthEnrollEnrollingUiState,
            List discoveredDeviceUiStates,
            EnrollmentUiState enrollmentUiState,
            int i) {
        if ((i & 1) != 0) {
            discoveredDeviceUiStates = remoteAuthEnrollEnrollingUiState.discoveredDeviceUiStates;
        }
        String str = remoteAuthEnrollEnrollingUiState.errorMsg;
        remoteAuthEnrollEnrollingUiState.getClass();
        Intrinsics.checkNotNullParameter(discoveredDeviceUiStates, "discoveredDeviceUiStates");
        return new RemoteAuthEnrollEnrollingUiState(
                discoveredDeviceUiStates, enrollmentUiState, str);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RemoteAuthEnrollEnrollingUiState)) {
            return false;
        }
        RemoteAuthEnrollEnrollingUiState remoteAuthEnrollEnrollingUiState =
                (RemoteAuthEnrollEnrollingUiState) obj;
        return Intrinsics.areEqual(
                        this.discoveredDeviceUiStates,
                        remoteAuthEnrollEnrollingUiState.discoveredDeviceUiStates)
                && this.enrollmentUiState == remoteAuthEnrollEnrollingUiState.enrollmentUiState
                && Intrinsics.areEqual(this.errorMsg, remoteAuthEnrollEnrollingUiState.errorMsg);
    }

    public final int hashCode() {
        int hashCode =
                (this.enrollmentUiState.hashCode()
                                + (this.discoveredDeviceUiStates.hashCode() * 31))
                        * 31;
        String str = this.errorMsg;
        return hashCode + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        List list = this.discoveredDeviceUiStates;
        StringBuilder sb =
                new StringBuilder("RemoteAuthEnrollEnrollingUiState(discoveredDeviceUiStates=");
        sb.append(list);
        sb.append(", enrollmentUiState=");
        sb.append(this.enrollmentUiState);
        sb.append(", errorMsg=");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.errorMsg, ")");
    }
}
