package com.android.settings.remoteauth.settings;

import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RemoteAuthSettingsUiState {
    public final String errorMsg;
    public final List registeredAuthenticatorUiStates;

    public RemoteAuthSettingsUiState() {
        EmptyList registeredAuthenticatorUiStates = EmptyList.INSTANCE;
        Intrinsics.checkNotNullParameter(
                registeredAuthenticatorUiStates, "registeredAuthenticatorUiStates");
        this.registeredAuthenticatorUiStates = registeredAuthenticatorUiStates;
        this.errorMsg = null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RemoteAuthSettingsUiState)) {
            return false;
        }
        RemoteAuthSettingsUiState remoteAuthSettingsUiState = (RemoteAuthSettingsUiState) obj;
        return Intrinsics.areEqual(
                        this.registeredAuthenticatorUiStates,
                        remoteAuthSettingsUiState.registeredAuthenticatorUiStates)
                && Intrinsics.areEqual(this.errorMsg, remoteAuthSettingsUiState.errorMsg);
    }

    public final int hashCode() {
        int hashCode = this.registeredAuthenticatorUiStates.hashCode() * 31;
        String str = this.errorMsg;
        return hashCode + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        return "RemoteAuthSettingsUiState(registeredAuthenticatorUiStates="
                + this.registeredAuthenticatorUiStates
                + ", errorMsg="
                + this.errorMsg
                + ")";
    }
}
