package com.android.settings.biometrics2.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;

import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintEnrollErrorDialogViewModel extends AndroidViewModel {
    public final AtomicBoolean _isDialogShown;
    public final SharedFlowImpl _newDialogFlow;
    public final SharedFlowImpl _setResultFlow;
    public final SharedFlowImpl _triggerRetryFlow;
    public final boolean isSuw;

    public FingerprintEnrollErrorDialogViewModel(Application application, boolean z) {
        super(application);
        this.isSuw = z;
        this._isDialogShown = new AtomicBoolean(false);
        this._newDialogFlow = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._triggerRetryFlow = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._setResultFlow = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
    }

    public final Object newDialog(int i, Continuation continuation) {
        this._isDialogShown.compareAndSet(false, true);
        Object emit = this._newDialogFlow.emit(new Integer(i), continuation);
        return emit == CoroutineSingletons.COROUTINE_SUSPENDED ? emit : Unit.INSTANCE;
    }
}
