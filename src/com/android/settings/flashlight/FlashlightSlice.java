package com.android.settings.flashlight;

import android.R;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;

import com.android.settings.slices.CustomSliceRegistry;
import com.android.settings.slices.CustomSliceable;
import com.android.settingslib.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FlashlightSlice implements CustomSliceable {
    public final Context mContext;

    public FlashlightSlice(Context context) {
        this.mContext = context;
    }

    public static String getCameraId(Context context) {
        CameraManager cameraManager = (CameraManager) context.getSystemService(CameraManager.class);
        for (String str : cameraManager.getCameraIdList()) {
            CameraCharacteristics cameraCharacteristics =
                    cameraManager.getCameraCharacteristics(str);
            Boolean bool =
                    (Boolean) cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
            Integer num = (Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
            if (bool != null && bool.booleanValue() && num != null && num.intValue() == 1) {
                return str;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isFlashlightAvailable(android.content.Context r5) {
        /*
            r0 = 1
            r1 = 0
            java.lang.String r2 = getCameraId(r5)     // Catch: android.hardware.camera2.CameraAccessException -> La
            if (r2 == 0) goto L12
            r2 = r0
            goto L13
        La:
            r2 = move-exception
            java.lang.String r3 = "FlashlightSlice"
            java.lang.String r4 = "Error getting camera id."
            android.util.Log.e(r3, r4, r2)
        L12:
            r2 = r1
        L13:
            android.content.ContentResolver r5 = r5.getContentResolver()
            java.lang.String r3 = "flashlight_available"
            int r5 = android.provider.Settings.Secure.getInt(r5, r3, r2)
            if (r5 != r0) goto L20
            goto L21
        L20:
            r0 = r1
        L21:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.flashlight.FlashlightSlice.isFlashlightAvailable(android.content.Context):boolean");
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Intent getIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public final IntentFilter getIntentFilter() {
        return new IntentFilter("com.android.settings.flashlight.action.FLASHLIGHT_CHANGED");
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Slice getSlice() {
        if (!isFlashlightAvailable(this.mContext)) {
            return null;
        }
        PendingIntent broadcastIntent = getBroadcastIntent(this.mContext);
        int colorAttrDefaultColor =
                Utils.getColorAttrDefaultColor(this.mContext, R.attr.colorAccent);
        IconCompat createWithResource =
                IconCompat.createWithResource(
                        this.mContext, com.android.settings.R.drawable.ic_signal_flashlight);
        ListBuilder listBuilder =
                new ListBuilder(this.mContext, CustomSliceRegistry.FLASHLIGHT_SLICE_URI);
        listBuilder.mImpl.setColor(colorAttrDefaultColor);
        ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder();
        rowBuilder.mTitle = this.mContext.getText(com.android.settings.R.string.power_flashlight);
        rowBuilder.mTitleLoading = false;
        rowBuilder.setTitleItem(createWithResource);
        rowBuilder.mPrimaryAction =
                new SliceAction(
                        broadcastIntent,
                        null,
                        Settings.Secure.getInt(
                                        this.mContext.getContentResolver(), "flashlight_enabled", 0)
                                == 1);
        listBuilder.mImpl.addRow(rowBuilder);
        return listBuilder.build();
    }

    @Override // com.android.settings.slices.Sliceable
    public final int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Uri getUri$1() {
        return CustomSliceRegistry.FLASHLIGHT_SLICE_URI;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final void onNotifyChange(Intent intent) {
        try {
            String cameraId = getCameraId(this.mContext);
            if (cameraId != null) {
                ((CameraManager) this.mContext.getSystemService(CameraManager.class))
                        .setTorchMode(
                                cameraId,
                                intent.getBooleanExtra(
                                        "android.app.slice.extra.TOGGLE_STATE",
                                        Settings.Secure.getInt(
                                                        this.mContext.getContentResolver(),
                                                        "flashlight_enabled",
                                                        0)
                                                == 1));
            }
        } catch (CameraAccessException e) {
            Log.e("FlashlightSlice", "Camera couldn't set torch mode.", e);
        }
        this.mContext
                .getContentResolver()
                .notifyChange(CustomSliceRegistry.FLASHLIGHT_SLICE_URI, null);
    }
}
