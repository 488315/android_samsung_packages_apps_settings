package com.android.settings.development;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.debug.IAdbManager;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SetupWizardUtils;
import com.android.settings.wifi.dpp.AdbQrCode;
import com.android.settings.wifi.dpp.WifiDppQrCodeBaseFragment;
import com.android.settings.wifi.dpp.WifiDppUtils;
import com.android.settings.wifi.dpp.WifiNetworkConfig;
import com.android.settingslib.qrcode.QrCamera;
import com.android.settingslib.qrcode.QrDecorateView;

import com.google.android.setupdesign.util.ThemeHelper;
import com.sec.ims.IMSParameter;

import java.time.Duration;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AdbQrcodeScannerFragment extends WifiDppQrCodeBaseFragment
        implements TextureView.SurfaceTextureListener, QrCamera.ScannerCallback {
    public WifiNetworkConfig mAdbConfig;
    public IAdbManager mAdbManager;
    public QrCamera mCamera;
    public QrDecorateView mDecorateView;
    public TextView mErrorMessage;
    public IntentFilter mIntentFilter;
    public View mQrCameraView;
    public TextureView mTextureView;
    public TextView mVerifyingTextView;
    public View mVerifyingView;
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.settings.development.AdbQrcodeScannerFragment.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if ("com.android.server.adb.WIRELESS_DEBUG_PAIRING_RESULT"
                            .equals(intent.getAction())) {
                        Integer valueOf =
                                Integer.valueOf(intent.getIntExtra(IMSParameter.CALL.STATUS, 0));
                        if (valueOf.equals(1)) {
                            Intent intent2 = new Intent();
                            intent2.putExtra("request_type_pairing", 0);
                            AdbQrcodeScannerFragment.this.getActivity().setResult(-1, intent2);
                            AdbQrcodeScannerFragment.this.getActivity().finish();
                            return;
                        }
                        if (!valueOf.equals(0)) {
                            if (valueOf.equals(4)) {
                                MainClearConfirm$$ExternalSyntheticOutline0.m(
                                        intent.getIntExtra("adb_port", 0),
                                        "Got Qr pairing code port=",
                                        "AdbQrcodeScannerFrag");
                            }
                        } else {
                            Intent intent3 = new Intent();
                            intent3.putExtra("request_type_pairing", 1);
                            AdbQrcodeScannerFragment.this.getActivity().setResult(-1, intent3);
                            AdbQrcodeScannerFragment.this.getActivity().finish();
                        }
                    }
                }
            };
    public final AnonymousClass2 mHandler =
            new Handler() { // from class:
                            // com.android.settings.development.AdbQrcodeScannerFragment.2
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    int i = message.what;
                    AdbQrcodeScannerFragment adbQrcodeScannerFragment =
                            AdbQrcodeScannerFragment.this;
                    if (i == 1) {
                        adbQrcodeScannerFragment.mErrorMessage.setVisibility(4);
                        return;
                    }
                    if (i != 2) {
                        return;
                    }
                    String str = (String) message.obj;
                    adbQrcodeScannerFragment.mErrorMessage.setVisibility(0);
                    adbQrcodeScannerFragment.mErrorMessage.setText(str);
                    adbQrcodeScannerFragment.mErrorMessage.sendAccessibilityEvent(32);
                    removeMessages(1);
                    sendEmptyMessageDelayed(1, 10000L);
                }
            };

    @Override // com.android.settingslib.qrcode.QrCamera.ScannerCallback
    public final Rect getFramePosition(Size size) {
        return new Rect(0, 0, size.getHeight(), size.getHeight());
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
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
        QrCamera qrCamera = this.mCamera;
        if (qrCamera != null) {
            qrCamera.stop();
            this.mCamera = null;
        }
        this.mDecorateView.setFocused(true);
        this.mQrCameraView.setVisibility(8);
        this.mVerifyingView.setVisibility(0);
        Context context = getContext();
        Duration duration = WifiDppUtils.VIBRATE_DURATION_QR_CODE_RECOGNITION;
        Vibrator vibrator = (Vibrator) context.getSystemService("vibrator");
        if (vibrator != null) {
            vibrator.vibrate(
                    VibrationEffect.createOneShot(
                            WifiDppUtils.VIBRATE_DURATION_QR_CODE_RECOGNITION.toMillis(), -1));
        }
        this.mVerifyingTextView.sendAccessibilityEvent(8);
        try {
            IAdbManager iAdbManager = this.mAdbManager;
            WifiNetworkConfig wifiNetworkConfig = this.mAdbConfig;
            iAdbManager.enablePairingByQrCode(
                    wifiNetworkConfig.mSsid, wifiNetworkConfig.mPreSharedKey);
        } catch (RemoteException unused) {
            Log.e("AdbQrcodeScannerFrag", "Unable to enable QR code pairing");
            getActivity().setResult(0);
            getActivity().finish();
        }
    }

    @Override // com.android.settings.wifi.dpp.WifiDppQrCodeBaseFragment
    public final boolean isFooterAvailable() {
        return false;
    }

    @Override // com.android.settingslib.qrcode.QrCamera.ScannerCallback
    public final boolean isValid(String str) {
        try {
            this.mAdbConfig = new AdbQrCode(str).mAdbConfig;
            return true;
        } catch (IllegalArgumentException unused) {
            obtainMessage(2, getString(R.string.wifi_dpp_qr_code_is_not_valid_format))
                    .sendToTarget();
            return false;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getActivity().setTitle(R.string.wifi_dpp_scan_qr_code);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Context context = getContext();
        context.setTheme(SetupWizardUtils.getTheme(context, getActivity().getIntent()));
        ThemeHelper.trySetDynamicColor(getContext());
        super.onCreate(bundle);
        this.mIntentFilter =
                new IntentFilter("com.android.server.adb.WIRELESS_DEBUG_PAIRING_RESULT");
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.adb_qrcode_scanner_fragment, viewGroup, false);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        QrCamera qrCamera = this.mCamera;
        if (qrCamera != null) {
            qrCamera.stop();
        }
        super.onPause();
        getActivity().unregisterReceiver(this.mReceiver);
        try {
            this.mAdbManager.disablePairing();
        } catch (RemoteException unused) {
            Log.e("AdbQrcodeScannerFrag", "Unable to cancel pairing");
        }
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        QrCamera qrCamera = this.mCamera;
        if (qrCamera == null) {
            Log.d("AdbQrcodeScannerFrag", "mCamera is not available for restarting camera");
        } else {
            if (qrCamera.mDecodeTask != null) {
                qrCamera.stop();
            }
            SurfaceTexture surfaceTexture = this.mTextureView.getSurfaceTexture();
            if (surfaceTexture == null) {
                throw new IllegalStateException(
                        "SurfaceTexture is not ready for restarting camera");
            }
            this.mCamera.start(surfaceTexture);
        }
        this.mAdbManager = IAdbManager.Stub.asInterface(ServiceManager.getService("adb"));
        getActivity().registerReceiver(this.mReceiver, this.mIntentFilter, 2);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public final void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        if (this.mCamera == null) {
            QrCamera qrCamera = new QrCamera(getContext(), this);
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

    @Override // com.android.settings.wifi.dpp.WifiDppQrCodeBaseFragment,
              // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mSummary = (TextView) view.findViewById(R.id.sud_layout_subtitle);
        TextureView textureView = (TextureView) view.findViewById(R.id.preview_view);
        this.mTextureView = textureView;
        textureView.setSurfaceTextureListener(this);
        this.mDecorateView = (QrDecorateView) view.findViewById(R.id.decorate_view);
        setProgressBarShown(false);
        this.mQrCameraView = view.findViewById(R.id.camera_layout);
        this.mVerifyingView = view.findViewById(R.id.verifying_layout);
        this.mVerifyingTextView = (TextView) view.findViewById(R.id.verifying_textview);
        setHeaderTitle(R.string.wifi_dpp_scan_qr_code, new Object[0]);
        this.mSummary.setText(R.string.adb_wireless_qrcode_pairing_description);
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
