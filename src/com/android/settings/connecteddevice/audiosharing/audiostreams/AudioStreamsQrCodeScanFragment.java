package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.bluetooth.Utils;
import com.android.settings.core.InstrumentedFragment;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.qrcode.QrCamera;

import java.time.Duration;
import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioStreamsQrCodeScanFragment extends InstrumentedFragment
        implements TextureView.SurfaceTextureListener, QrCamera.ScannerCallback {
    static final long SHOW_ERROR_MESSAGE_INTERVAL = 10000;
    static final long SHOW_SUCCESS_SQUARE_INTERVAL = 1000;
    public static final Duration VIBRATE_DURATION_QR_CODE_RECOGNITION = Duration.ofMillis(3);
    public String mBroadcastMetadata;
    public QrCamera mCamera;
    public Context mContext;
    public int mCornerRadius;
    public TextView mErrorMessage;
    public final AnonymousClass1 mHandler =
            new Handler(
                    Looper
                            .getMainLooper()) { // from class:
                                                // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsQrCodeScanFragment.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    int i = message.what;
                    AudioStreamsQrCodeScanFragment audioStreamsQrCodeScanFragment =
                            AudioStreamsQrCodeScanFragment.this;
                    if (i == 1) {
                        audioStreamsQrCodeScanFragment.mErrorMessage.setVisibility(4);
                        return;
                    }
                    if (i == 2) {
                        String str = (String) message.obj;
                        audioStreamsQrCodeScanFragment.mErrorMessage.setVisibility(0);
                        audioStreamsQrCodeScanFragment.mErrorMessage.setText(str);
                        audioStreamsQrCodeScanFragment.mErrorMessage.sendAccessibilityEvent(32);
                        removeMessages(1);
                        sendEmptyMessageDelayed(
                                1, AudioStreamsQrCodeScanFragment.SHOW_ERROR_MESSAGE_INTERVAL);
                        return;
                    }
                    if (i != 3) {
                        return;
                    }
                    Log.d("AudioStreamsQrCodeScanFragment", "scan success");
                    Intent intent = new Intent();
                    intent.putExtra(
                            "key_broadcast_metadata",
                            audioStreamsQrCodeScanFragment.mBroadcastMetadata);
                    if (audioStreamsQrCodeScanFragment.getActivity() != null) {
                        audioStreamsQrCodeScanFragment.getActivity().setResult(-1, intent);
                        QrCamera qrCamera = audioStreamsQrCodeScanFragment.mCamera;
                        if (qrCamera != null) {
                            qrCamera.stop();
                        }
                        audioStreamsQrCodeScanFragment.mErrorMessage.setVisibility(4);
                        audioStreamsQrCodeScanFragment.mTextureView.setVisibility(4);
                        Vibrator vibrator =
                                (Vibrator)
                                        audioStreamsQrCodeScanFragment.mContext.getSystemService(
                                                Vibrator.class);
                        if (vibrator != null) {
                            vibrator.vibrate(
                                    VibrationEffect.createOneShot(
                                            AudioStreamsQrCodeScanFragment
                                                    .VIBRATE_DURATION_QR_CODE_RECOGNITION
                                                    .toMillis(),
                                            -1));
                        }
                        if (audioStreamsQrCodeScanFragment.getActivity() != null) {
                            audioStreamsQrCodeScanFragment.getActivity().finish();
                        }
                    }
                }
            };
    public LocalBluetoothManager mLocalBluetoothManager;
    public TextureView mTextureView;

    @Override // com.android.settingslib.qrcode.QrCamera.ScannerCallback
    public final Rect getFramePosition(Size size) {
        return new Rect(0, 0, size.getHeight(), size.getHeight());
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2095;
    }

    @Override // com.android.settingslib.qrcode.QrCamera.ScannerCallback
    public final Size getViewSize() {
        return new Size(this.mTextureView.getWidth(), this.mTextureView.getHeight());
    }

    @Override // com.android.settingslib.qrcode.QrCamera.ScannerCallback
    public final void handleCameraFailure() {
        QrCamera qrCamera = this.mCamera;
        if (qrCamera != null) {
            qrCamera.stop();
            this.mCamera = null;
        }
    }

    @Override // com.android.settingslib.qrcode.QrCamera.ScannerCallback
    public final void handleSuccessfulResult(String str) {
        Log.d(
                "AudioStreamsQrCodeScanFragment",
                "handleSuccessfulResult(), get the qr code string.");
        this.mBroadcastMetadata = str;
        sendMessageDelayed(obtainMessage(3), 1000L);
    }

    @Override // com.android.settingslib.qrcode.QrCamera.ScannerCallback
    public final boolean isValid(String str) {
        if (str.startsWith("BLUETOOTH:UUID:184F;")) {
            return true;
        }
        obtainMessage(2, getString(R.string.audio_streams_qr_code_is_not_valid_format))
                .sendToTarget();
        return false;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context context = getContext();
        this.mContext = context;
        this.mLocalBluetoothManager = Utils.getLocalBluetoothManager(context);
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.qrcode_scanner_fragment, viewGroup, false);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public final void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        if (this.mCamera == null) {
            QrCamera qrCamera = new QrCamera(this.mContext, this);
            this.mCamera = qrCamera;
            qrCamera.start(surfaceTexture);
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public final boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        QrCamera qrCamera = this.mCamera;
        if (qrCamera == null) {
            return true;
        }
        qrCamera.stop();
        this.mCamera = null;
        return true;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        this.mTextureView = (TextureView) view.findViewById(R.id.preview_view);
        this.mCornerRadius =
                this.mContext
                        .getResources()
                        .getDimensionPixelSize(R.dimen.audio_streams_qrcode_preview_radius);
        this.mTextureView.setSurfaceTextureListener(this);
        this.mTextureView.setOutlineProvider(
                new ViewOutlineProvider() { // from class:
                                            // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsQrCodeScanFragment.2
                    @Override // android.view.ViewOutlineProvider
                    public final void getOutline(View view2, Outline outline) {
                        outline.setRoundRect(
                                0,
                                0,
                                view2.getWidth(),
                                view2.getHeight(),
                                AudioStreamsQrCodeScanFragment.this.mCornerRadius);
                    }
                });
        this.mTextureView.setClipToOutline(true);
        this.mErrorMessage = (TextView) view.findViewById(R.id.error_message);
        Optional cachedBluetoothDeviceInSharingOrLeConnected =
                AudioStreamsHelper.getCachedBluetoothDeviceInSharingOrLeConnected(
                        this.mLocalBluetoothManager);
        TextView textView = (TextView) view.findViewById(android.R.id.summary);
        if (textView == null || !cachedBluetoothDeviceInSharingOrLeConnected.isPresent()) {
            return;
        }
        textView.setText(
                getString(
                        R.string.audio_streams_main_page_qr_code_scanner_summary,
                        ((CachedBluetoothDevice) cachedBluetoothDeviceInSharingOrLeConnected.get())
                                .getName()));
    }

    @Override // com.android.settingslib.qrcode.QrCamera.ScannerCallback
    public final void setTransform(Matrix matrix) {
        this.mTextureView.setTransform(matrix);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public final void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {}

    @Override // android.view.TextureView.SurfaceTextureListener
    public final void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {}
}
