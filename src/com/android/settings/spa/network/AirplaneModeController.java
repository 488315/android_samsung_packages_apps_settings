package com.android.settings.spa.network;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.settings.AirplaneModeEnabler;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AirplaneModeController
        implements AirplaneModeEnabler.OnAirplaneModeChangedListener {
    public final MutableLiveData _airplaneModeState;
    public final AirplaneModeEnabler airplaneModeEnabler;
    public final Context context;

    public AirplaneModeController(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.airplaneModeEnabler = new AirplaneModeEnabler(context, this);
        this._airplaneModeState = new MutableLiveData();
    }

    @Override // com.android.settings.AirplaneModeEnabler.OnAirplaneModeChangedListener
    public final void onAirplaneModeChanged(boolean z) {
        this._airplaneModeState.postValue(Boolean.valueOf(z));
    }
}
