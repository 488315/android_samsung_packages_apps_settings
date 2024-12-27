package com.android.settings.bluetooth;

import com.android.settingslib.bluetooth.CachedBluetoothDevice;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class DeviceListPreferenceFragment$onDeviceAdded$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ CachedBluetoothDevice $cachedDevice;
    int label;
    final /* synthetic */ DeviceListPreferenceFragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceListPreferenceFragment$onDeviceAdded$1(
            DeviceListPreferenceFragment deviceListPreferenceFragment,
            CachedBluetoothDevice cachedBluetoothDevice,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceListPreferenceFragment;
        this.$cachedDevice = cachedBluetoothDevice;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DeviceListPreferenceFragment$onDeviceAdded$1(
                this.this$0, this.$cachedDevice, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceListPreferenceFragment$onDeviceAdded$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        Unit unit = Unit.INSTANCE;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            DeviceListPreferenceFragment deviceListPreferenceFragment = this.this$0;
            CachedBluetoothDevice cachedBluetoothDevice = this.$cachedDevice;
            this.label = 1;
            deviceListPreferenceFragment.getClass();
            Object withContext =
                    BuildersKt.withContext(
                            Dispatchers.Default,
                            new DeviceListPreferenceFragment$addDevice$2(
                                    deviceListPreferenceFragment, cachedBluetoothDevice, null),
                            this);
            if (withContext != coroutineSingletons) {
                withContext = unit;
            }
            if (withContext == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return unit;
    }
}
