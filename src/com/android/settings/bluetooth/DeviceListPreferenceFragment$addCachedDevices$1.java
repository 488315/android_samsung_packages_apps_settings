package com.android.settings.bluetooth;

import com.android.settingslib.bluetooth.BluetoothDeviceFilter;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.scheduling.DefaultScheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
final class DeviceListPreferenceFragment$addCachedDevices$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ BluetoothDeviceFilter.Filter $filterForCachedDevices;
    int label;
    final /* synthetic */ DeviceListPreferenceFragment this$0;

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
    /* renamed from: com.android.settings.bluetooth.DeviceListPreferenceFragment$addCachedDevices$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ BluetoothDeviceFilter.Filter $filterForCachedDevices;
        int label;
        final /* synthetic */ DeviceListPreferenceFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                DeviceListPreferenceFragment deviceListPreferenceFragment,
                BluetoothDeviceFilter.Filter filter,
                Continuation continuation) {
            super(2, continuation);
            this.this$0 = deviceListPreferenceFragment;
            this.$filterForCachedDevices = filter;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, this.$filterForCachedDevices, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 =
                    (AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2);
            Unit unit = Unit.INSTANCE;
            anonymousClass1.invokeSuspend(unit);
            return unit;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager =
                    this.this$0.mCachedDeviceManager;
            Intrinsics.checkNotNull(cachedBluetoothDeviceManager);
            Collection cachedDevicesCopy = cachedBluetoothDeviceManager.getCachedDevicesCopy();
            BluetoothDeviceFilter.Filter filter = this.$filterForCachedDevices;
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : cachedDevicesCopy) {
                CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj2;
                if (filter == null || filter.matches(cachedBluetoothDevice.mDevice)) {
                    arrayList.add(obj2);
                }
            }
            DeviceListPreferenceFragment deviceListPreferenceFragment = this.this$0;
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                deviceListPreferenceFragment.onDeviceAdded((CachedBluetoothDevice) it.next());
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceListPreferenceFragment$addCachedDevices$1(
            DeviceListPreferenceFragment deviceListPreferenceFragment,
            BluetoothDeviceFilter.Filter filter,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceListPreferenceFragment;
        this.$filterForCachedDevices = filter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DeviceListPreferenceFragment$addCachedDevices$1(
                this.this$0, this.$filterForCachedDevices, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceListPreferenceFragment$addCachedDevices$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            DefaultScheduler defaultScheduler = Dispatchers.Default;
            AnonymousClass1 anonymousClass1 =
                    new AnonymousClass1(this.this$0, this.$filterForCachedDevices, null);
            this.label = 1;
            if (BuildersKt.withContext(defaultScheduler, anonymousClass1, this)
                    == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
