package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.settings.R;
import com.android.settings.core.InstrumentedFragment;
import com.android.settingslib.utils.ThreadUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioStreamsQrCodeFragment extends InstrumentedFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2094;
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.xml.bluetooth_audio_streams_qr_code, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(final View view, Bundle bundle) {
        ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                    // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsQrCodeFragment$$ExternalSyntheticLambda0
                    /* JADX WARN: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
                    /* JADX WARN: Removed duplicated region for block: B:6:0x0048  */
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void run() {
                        /*
                            r7 = this;
                            com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsQrCodeFragment r0 = com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsQrCodeFragment.this
                            android.view.View r7 = r2
                            androidx.fragment.app.FragmentActivity r1 = r0.getActivity()
                            com.android.settings.bluetooth.Utils$2 r2 = com.android.settings.bluetooth.Utils.mOnInitCallback
                            com.android.settingslib.bluetooth.LocalBluetoothManager r1 = com.android.settingslib.bluetooth.LocalBluetoothManager.getInstance(r1, r2)
                            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r1 = r1.mProfileManager
                            com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast r1 = r1.mLeAudioBroadcast
                            java.lang.String r2 = "AudioStreamsQrCodeFragment"
                            r3 = 0
                            if (r1 != 0) goto L1e
                            java.lang.String r1 = "getBroadcastMetadataQrCode: localBluetoothLeBroadcast is null!"
                            android.util.Log.d(r2, r1)
                        L1c:
                            r1 = r3
                            goto L45
                        L1e:
                            android.bluetooth.BluetoothLeBroadcast r1 = r1.mServiceBroadcast
                            if (r1 != 0) goto L2e
                            java.lang.String r1 = "LocalBluetoothLeBroadcast"
                            java.lang.String r4 = "The BluetoothLeBroadcast is null."
                            android.util.Log.d(r1, r4)
                            java.util.List r1 = java.util.Collections.emptyList()
                            goto L32
                        L2e:
                            java.util.List r1 = r1.getAllBroadcastMetadata()
                        L32:
                            boolean r4 = r1.isEmpty()
                            if (r4 == 0) goto L3e
                            java.lang.String r1 = "getBroadcastMetadataQrCode: metadata is null!"
                            android.util.Log.d(r2, r1)
                            goto L1c
                        L3e:
                            r4 = 0
                            java.lang.Object r1 = r1.get(r4)
                            android.bluetooth.BluetoothLeBroadcastMetadata r1 = (android.bluetooth.BluetoothLeBroadcastMetadata) r1
                        L45:
                            if (r1 != 0) goto L48
                            goto Lb0
                        L48:
                            java.lang.String r4 = com.android.settingslib.bluetooth.BluetoothLeBroadcastMetadataExt.toQrCodeString(r1)
                            boolean r5 = r4.isEmpty()
                            if (r5 == 0) goto L5c
                            java.lang.String r4 = "getQrCodeBitmap: metadataStr is empty!"
                            android.util.Log.d(r2, r4)
                            java.util.Optional r2 = java.util.Optional.empty()
                            goto L9f
                        L5c:
                            java.lang.StringBuilder r5 = new java.lang.StringBuilder
                            java.lang.String r6 = "getQrCodeBitmap: metadata : "
                            r5.<init>(r6)
                            r5.append(r1)
                            java.lang.String r5 = r5.toString()
                            android.util.Log.d(r2, r5)
                            android.content.res.Resources r5 = r0.getResources()     // Catch: com.google.zxing.WriterException -> L81
                            r6 = 2131165559(0x7f070177, float:1.7945339E38)
                            int r5 = r5.getDimensionPixelSize(r6)     // Catch: com.google.zxing.WriterException -> L81
                            android.graphics.Bitmap r4 = com.android.settingslib.qrcode.QrCodeGenerator.encodeQrCode$default(r5, r4)     // Catch: com.google.zxing.WriterException -> L81
                            java.util.Optional r2 = java.util.Optional.of(r4)     // Catch: com.google.zxing.WriterException -> L81
                            goto L9f
                        L81:
                            r4 = move-exception
                            java.lang.StringBuilder r5 = new java.lang.StringBuilder
                            java.lang.String r6 = "getQrCodeBitmap: broadcastMetadata "
                            r5.<init>(r6)
                            r5.append(r1)
                            java.lang.String r6 = " qrCode generation exception "
                            r5.append(r6)
                            r5.append(r4)
                            java.lang.String r4 = r5.toString()
                            android.util.Log.d(r2, r4)
                            java.util.Optional r2 = java.util.Optional.empty()
                        L9f:
                            java.lang.Object r2 = r2.orElse(r3)
                            android.graphics.Bitmap r2 = (android.graphics.Bitmap) r2
                            if (r2 != 0) goto La8
                            goto Lb0
                        La8:
                            com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsQrCodeFragment$$ExternalSyntheticLambda1 r3 = new com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsQrCodeFragment$$ExternalSyntheticLambda1
                            r3.<init>()
                            com.android.settingslib.utils.ThreadUtils.postOnMainThread(r3)
                        Lb0:
                            return
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsQrCodeFragment$$ExternalSyntheticLambda0.run():void");
                    }
                });
    }
}
