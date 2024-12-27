package com.android.settings.bluetooth;

import com.android.settingslib.bluetooth.CachedBluetoothDevice;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;

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
final class DeviceListPreferenceFragment$addDevice$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ CachedBluetoothDevice $cachedDevice;
    int label;
    final /* synthetic */ DeviceListPreferenceFragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceListPreferenceFragment$addDevice$2(
            DeviceListPreferenceFragment deviceListPreferenceFragment,
            CachedBluetoothDevice cachedBluetoothDevice,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceListPreferenceFragment;
        this.$cachedDevice = cachedBluetoothDevice;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DeviceListPreferenceFragment$addDevice$2(
                this.this$0, this.$cachedDevice, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceListPreferenceFragment$addDevice$2)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x008d, code lost:

       if (r6 == r0) goto L27;
    */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            r3 = 1
            if (r1 == 0) goto L18
            if (r1 != r3) goto L10
            kotlin.ResultKt.throwOnFailure(r7)
            goto L92
        L10:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L18:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.settings.bluetooth.DeviceListPreferenceFragment r7 = r6.this$0
            android.bluetooth.BluetoothAdapter r7 = r7.mBluetoothAdapter
            kotlin.jvm.internal.Intrinsics.checkNotNull(r7)
            int r7 = r7.getState()
            r1 = 12
            if (r7 == r1) goto L2b
            return r2
        L2b:
            com.android.settings.bluetooth.DeviceListPreferenceFragment r7 = r6.this$0
            com.android.settings.bluetooth.DeviceListPreferenceFragment$ScanType r4 = r7.scanType
            com.android.settings.bluetooth.DeviceListPreferenceFragment$ScanType r5 = com.android.settings.bluetooth.DeviceListPreferenceFragment.ScanType.LE
            if (r4 == r5) goto L40
            com.android.settings.bluetooth.DeviceListPreferenceFragment$ScanType r5 = com.android.settings.bluetooth.DeviceListPreferenceFragment.ScanType.CLASSIC
            if (r4 != r5) goto L92
            com.android.settingslib.bluetooth.BluetoothDeviceFilter$AllFilter r7 = r7.filter
            com.android.settingslib.bluetooth.CachedBluetoothDevice r4 = r6.$cachedDevice
            android.bluetooth.BluetoothDevice r4 = r4.mDevice
            r7.getClass()
        L40:
            com.android.settings.bluetooth.DeviceListPreferenceFragment r7 = r6.this$0
            com.android.settingslib.bluetooth.CachedBluetoothDevice r4 = r6.$cachedDevice
            r6.label = r3
            androidx.preference.PreferenceCategory r3 = r7.mDeviceListGroup
            java.lang.String r5 = "DeviceListPreferenceFragment"
            if (r3 != 0) goto L53
            java.lang.String r6 = "Trying to create a device preference before the list group/category exists!"
            android.util.Log.w(r5, r6)
        L51:
            r6 = r2
            goto L8f
        L53:
            android.bluetooth.BluetoothDevice r3 = r4.mDevice
            int r3 = r3.getBondState()
            if (r3 != r1) goto L6d
            android.content.Context r1 = r7.getPrefContext()
            android.bluetooth.BluetoothDevice r3 = r4.mDevice
            boolean r1 = com.android.settingslib.bluetooth.BluetoothUtils.isExclusivelyManagedBluetoothDevice(r1, r3)
            if (r1 == 0) goto L6d
            java.lang.String r6 = "Trying to create preference for a exclusively managed device"
            android.util.Log.d(r5, r6)
            goto L51
        L6d:
            java.util.concurrent.ConcurrentHashMap r1 = r7.devicePreferenceMap
            com.android.settings.bluetooth.DeviceListPreferenceFragment$createDevicePreference$preference$1 r3 = new com.android.settings.bluetooth.DeviceListPreferenceFragment$createDevicePreference$preference$1
            r3.<init>()
            java.lang.Object r1 = r1.computeIfAbsent(r4, r3)
            java.lang.String r3 = "computeIfAbsent(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r3)
            com.android.settings.bluetooth.BluetoothDevicePreference r1 = (com.android.settings.bluetooth.BluetoothDevicePreference) r1
            kotlinx.coroutines.scheduling.DefaultScheduler r3 = kotlinx.coroutines.Dispatchers.Default
            kotlinx.coroutines.android.HandlerContext r3 = kotlinx.coroutines.internal.MainDispatcherLoader.dispatcher
            com.android.settings.bluetooth.DeviceListPreferenceFragment$createDevicePreference$2 r4 = new com.android.settings.bluetooth.DeviceListPreferenceFragment$createDevicePreference$2
            r5 = 0
            r4.<init>(r7, r1, r5)
            java.lang.Object r6 = kotlinx.coroutines.BuildersKt.withContext(r3, r4, r6)
            if (r6 != r0) goto L51
        L8f:
            if (r6 != r0) goto L92
            return r0
        L92:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.bluetooth.DeviceListPreferenceFragment$addDevice$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
