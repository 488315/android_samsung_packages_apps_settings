package com.android.settingslib.bluetooth;

import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;

import com.android.settingslib.utils.ThreadUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class CachedBluetoothDevice$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ CachedBluetoothDevice$$ExternalSyntheticLambda1(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj;
                if (BluetoothUtils.isAdvancedDetailsHeader(cachedBluetoothDevice.mDevice)) {
                    String stringMetaData =
                            BluetoothUtils.getStringMetaData(cachedBluetoothDevice.mDevice, 5);
                    Uri parse = stringMetaData == null ? null : Uri.parse(stringMetaData);
                    if (parse != null
                            && cachedBluetoothDevice.mDrawableCache.get(parse.toString()) == null) {
                        cachedBluetoothDevice.mDrawableCache.put(
                                parse.toString(),
                                (BitmapDrawable)
                                        BluetoothUtils.getBtDrawableWithDescription(
                                                        cachedBluetoothDevice.mContext,
                                                        cachedBluetoothDevice)
                                                .first);
                    }
                }
                ThreadUtils.postOnMainThread(
                        new CachedBluetoothDevice$$ExternalSyntheticLambda1(
                                1, cachedBluetoothDevice));
                break;
            case 1:
                ((CachedBluetoothDevice) obj).dispatchAttributesChanged(true);
                break;
            default:
                ((CachedBluetoothDevice.Callback) obj).onDeviceAttributesChanged();
                break;
        }
    }
}
