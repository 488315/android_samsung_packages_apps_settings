package com.android.settings.bluetooth;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
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
import com.android.settings.core.InstrumentedFragment;
import com.android.settingslib.qrcode.QrCamera;

import java.time.Duration;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class QrCodeScanModeFragment extends InstrumentedFragment
        implements TextureView.SurfaceTextureListener, QrCamera.ScannerCallback {
    public static final Duration VIBRATE_DURATION_QR_CODE_RECOGNITION = Duration.ofMillis(3);
    public String mBroadcastMetadata;
    public QrCamera mCamera;
    public Context mContext;
    public int mCornerRadius;
    public TextView mErrorMessage;
    public final AnonymousClass2 mHandler =
            new Handler() { // from class: com.android.settings.bluetooth.QrCodeScanModeFragment.2
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    int i = message.what;
                    QrCodeScanModeFragment qrCodeScanModeFragment = QrCodeScanModeFragment.this;
                    if (i == 1) {
                        qrCodeScanModeFragment.mErrorMessage.setVisibility(4);
                        return;
                    }
                    if (i == 2) {
                        String str = (String) message.obj;
                        qrCodeScanModeFragment.mErrorMessage.setVisibility(0);
                        qrCodeScanModeFragment.mErrorMessage.setText(str);
                        qrCodeScanModeFragment.mErrorMessage.sendAccessibilityEvent(32);
                        removeMessages(1);
                        sendEmptyMessageDelayed(1, 10000L);
                        return;
                    }
                    if (i != 3) {
                        return;
                    }
                    Log.d("QrCodeScanModeFragment", "scan success");
                    Intent intent = new Intent();
                    intent.putExtra(
                            "key_broadcast_metadata", qrCodeScanModeFragment.mBroadcastMetadata);
                    qrCodeScanModeFragment.getActivity().setResult(-1, intent);
                    QrCamera qrCamera = qrCodeScanModeFragment.mCamera;
                    if (qrCamera != null) {
                        qrCamera.stop();
                    }
                    qrCodeScanModeFragment.mErrorMessage.setVisibility(4);
                    Vibrator vibrator =
                            (Vibrator)
                                    qrCodeScanModeFragment
                                            .getContext()
                                            .getSystemService(Vibrator.class);
                    if (vibrator != null) {
                        vibrator.vibrate(
                                VibrationEffect.createOneShot(
                                        QrCodeScanModeFragment.VIBRATE_DURATION_QR_CODE_RECOGNITION
                                                .toMillis(),
                                        -1));
                    }
                    qrCodeScanModeFragment.getActivity().finish();
                }
            };
    public TextureView mTextureView;

    @Override // com.android.settingslib.qrcode.QrCamera.ScannerCallback
    public final Rect getFramePosition(Size size) {
        return new Rect(0, 0, size.getHeight(), size.getHeight());
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1926;
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
        Log.d("QrCodeScanModeFragment", "handleSuccessfulResult(), get the qr code string.");
        this.mBroadcastMetadata = str;
        sendMessageDelayed(obtainMessage(3), 1000L);
    }

    @Override // com.android.settingslib.qrcode.QrCamera.ScannerCallback
    public final boolean isValid(String str) {
        if (str.startsWith("BLUETOOTH:UUID:184F;")) {
            return true;
        }
        obtainMessage(2, getString(R.string.bt_le_audio_qr_code_is_not_valid_format))
                .sendToTarget();
        return false;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
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
                this.mContext.getResources().getDimensionPixelSize(R.dimen.qrcode_preview_radius);
        this.mTextureView.setSurfaceTextureListener(this);
        this.mTextureView.setOutlineProvider(
                new ViewOutlineProvider() { // from class:
                                            // com.android.settings.bluetooth.QrCodeScanModeFragment.1
                    @Override // android.view.ViewOutlineProvider
                    public final void getOutline(View view2, Outline outline) {
                        outline.setRoundRect(
                                0,
                                0,
                                view2.getWidth(),
                                view2.getHeight(),
                                QrCodeScanModeFragment.this.mCornerRadius);
                    }
                });
        this.mTextureView.setClipToOutline(true);
        this.mErrorMessage = (TextView) view.findViewById(R.id.error_message);
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
