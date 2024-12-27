package com.android.settings.wifi.dpp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.net.wifi.EasyConnectStatusCallback;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SimpleClock;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.applications.intentpicker.ProgressDialogFragment$$ExternalSyntheticOutline0;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.wifi.WifiTrackerLibProviderImpl;
import com.android.settings.wifi.dpp.WifiDppInitiatorViewModel.EasyConnectDelegateCallback;
import com.android.settings.wifi.dpp.WifiDppQrCodeScannerFragment.EasyConnectEnrolleeStatusCallback;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.qrcode.QrCamera;
import com.android.settingslib.qrcode.QrDecorateView;
import com.android.settingslib.wifi.WifiPermissionChecker;
import com.android.wifitrackerlib.WifiEntry;
import com.android.wifitrackerlib.WifiPickerTracker;

import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

import java.time.Duration;
import java.time.ZoneOffset;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiDppQrCodeScannerFragment extends WifiDppQrCodeBaseFragment
        implements TextureView.SurfaceTextureListener,
                QrCamera.ScannerCallback,
                WifiManager.ActionListener {
    public QrCamera mCamera;
    public QrDecorateView mDecorateView;
    public WifiConfiguration mEnrolleeWifiConfiguration;
    public TextView mErrorMessage;
    public OnScanWifiDppSuccessListener mScanWifiDppSuccessListener;
    public final String mSsid;
    public TextureView mTextureView;
    public WifiPermissionChecker mWifiPermissionChecker;
    public WifiPickerTracker mWifiPickerTracker;
    public WifiQrCode mWifiQrCode;
    public HandlerThread mWorkerThread;
    public int mLatestStatusCode = 0;
    public final AnonymousClass1 mHandler =
            new Handler() { // from class:
                            // com.android.settings.wifi.dpp.WifiDppQrCodeScannerFragment.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    int i = message.what;
                    WifiDppQrCodeScannerFragment wifiDppQrCodeScannerFragment =
                            WifiDppQrCodeScannerFragment.this;
                    if (i == 1) {
                        wifiDppQrCodeScannerFragment.mErrorMessage.setVisibility(4);
                        return;
                    }
                    if (i == 2) {
                        String str = (String) message.obj;
                        wifiDppQrCodeScannerFragment.mErrorMessage.setVisibility(0);
                        wifiDppQrCodeScannerFragment.mErrorMessage.setText(str);
                        wifiDppQrCodeScannerFragment.mErrorMessage.sendAccessibilityEvent(32);
                        removeMessages(1);
                        sendEmptyMessageDelayed(1, 10000L);
                        if (message.arg1 == 1) {
                            wifiDppQrCodeScannerFragment.setProgressBarShown(false);
                            wifiDppQrCodeScannerFragment.mDecorateView.setFocused(false);
                            wifiDppQrCodeScannerFragment.restartCamera$1();
                            return;
                        }
                        return;
                    }
                    if (i == 3) {
                        OnScanWifiDppSuccessListener onScanWifiDppSuccessListener =
                                wifiDppQrCodeScannerFragment.mScanWifiDppSuccessListener;
                        if (onScanWifiDppSuccessListener == null) {
                            return;
                        }
                        onScanWifiDppSuccessListener.onScanWifiDppSuccess((WifiQrCode) message.obj);
                        if (!wifiDppQrCodeScannerFragment.mIsConfiguratorMode) {
                            wifiDppQrCodeScannerFragment.setProgressBarShown(true);
                            WifiDppQrCodeScannerFragment.m1026$$Nest$mstartWifiDppEnrolleeInitiator(
                                    wifiDppQrCodeScannerFragment, (WifiQrCode) message.obj);
                            wifiDppQrCodeScannerFragment.updateEnrolleeSummary();
                            wifiDppQrCodeScannerFragment.mSummary.sendAccessibilityEvent(32);
                        }
                        QrCamera qrCamera = wifiDppQrCodeScannerFragment.mCamera;
                        if (qrCamera != null) {
                            qrCamera.stop();
                        }
                        wifiDppQrCodeScannerFragment.mDecorateView.setFocused(true);
                        wifiDppQrCodeScannerFragment.mErrorMessage.setVisibility(4);
                        Context context = wifiDppQrCodeScannerFragment.getContext();
                        Duration duration = WifiDppUtils.VIBRATE_DURATION_QR_CODE_RECOGNITION;
                        Vibrator vibrator = (Vibrator) context.getSystemService("vibrator");
                        if (vibrator == null) {
                            return;
                        }
                        vibrator.vibrate(
                                VibrationEffect.createOneShot(
                                        WifiDppUtils.VIBRATE_DURATION_QR_CODE_RECOGNITION
                                                .toMillis(),
                                        -1));
                        return;
                    }
                    if (i != 4) {
                        return;
                    }
                    Context context2 = wifiDppQrCodeScannerFragment.getContext();
                    if (context2 == null) {
                        Log.d("WifiDppQrCodeScanner", "Scan success but context is null");
                        return;
                    }
                    WifiManager wifiManager =
                            (WifiManager) context2.getSystemService(WifiManager.class);
                    WifiConfiguration wifiConfiguration = (WifiConfiguration) message.obj;
                    int addNetwork = wifiManager.addNetwork(wifiConfiguration);
                    if (addNetwork != -1
                            && wifiDppQrCodeScannerFragment.canConnectWifi(
                                    wifiConfiguration.SSID)) {
                        wifiManager.enableNetwork(addNetwork, false);
                        if (!wifiConfiguration.hiddenSSID) {
                            List<WifiEntry> wifiEntries =
                                    wifiDppQrCodeScannerFragment.mWifiPickerTracker
                                            .getWifiEntries();
                            WifiEntry wifiEntry =
                                    wifiDppQrCodeScannerFragment
                                            .mWifiPickerTracker
                                            .mConnectedWifiEntry;
                            if (wifiEntry != null) {
                                wifiEntries.add(wifiEntry);
                            }
                            for (WifiEntry wifiEntry2 : wifiEntries) {
                                if (TextUtils.equals(
                                        wifiEntry2.getSsid(),
                                        WifiInfo.sanitizeSsid(wifiConfiguration.SSID))) {
                                    Duration duration2 =
                                            WifiDppUtils.VIBRATE_DURATION_QR_CODE_RECOGNITION;
                                    int i2 =
                                            wifiConfiguration.allowedKeyManagement.get(8)
                                                    ? 5
                                                    : wifiConfiguration.allowedKeyManagement.get(1)
                                                            ? 2
                                                            : wifiConfiguration.allowedKeyManagement
                                                                            .get(10)
                                                                    ? 6
                                                                    : (wifiConfiguration
                                                                                            .allowedKeyManagement
                                                                                            .get(2)
                                                                                    || wifiConfiguration
                                                                                            .allowedKeyManagement
                                                                                            .get(3))
                                                                            ? 3
                                                                            : wifiConfiguration
                                                                                            .allowedKeyManagement
                                                                                            .get(9)
                                                                                    ? 4
                                                                                    : wifiConfiguration
                                                                                                            .wepKeys[
                                                                                                            0]
                                                                                                    != null
                                                                                            ? 1
                                                                                            : 0;
                                    if (i2 != wifiEntry2.getSecurity$1()) {
                                        if (i2 == 5 && wifiEntry2.getSecurity$1() == 2) {}
                                    }
                                }
                            }
                            wifiDppQrCodeScannerFragment.showErrorMessageAndRestartCamera(
                                    R.string.wifi_dpp_check_connection_try_again);
                            return;
                        }
                        wifiDppQrCodeScannerFragment.mEnrolleeWifiConfiguration = wifiConfiguration;
                        wifiManager.connect(addNetwork, wifiDppQrCodeScannerFragment);
                        SettingsMetricsFeatureProvider settingsMetricsFeatureProvider =
                                wifiDppQrCodeScannerFragment.mMetricsFeatureProvider;
                        FragmentActivity activity = wifiDppQrCodeScannerFragment.getActivity();
                        settingsMetricsFeatureProvider.getClass();
                        settingsMetricsFeatureProvider.action(
                                MetricsFeatureProvider.getAttribution(activity),
                                1711,
                                1596,
                                Integer.MIN_VALUE,
                                null);
                        QrCamera qrCamera2 = wifiDppQrCodeScannerFragment.mCamera;
                        if (qrCamera2 != null) {
                            qrCamera2.stop();
                        }
                        wifiDppQrCodeScannerFragment.mDecorateView.setFocused(true);
                        wifiDppQrCodeScannerFragment.mErrorMessage.setVisibility(4);
                        Context context3 = wifiDppQrCodeScannerFragment.getContext();
                        Duration duration3 = WifiDppUtils.VIBRATE_DURATION_QR_CODE_RECOGNITION;
                        Vibrator vibrator2 = (Vibrator) context3.getSystemService("vibrator");
                        if (vibrator2 == null) {
                            return;
                        }
                        vibrator2.vibrate(
                                VibrationEffect.createOneShot(
                                        WifiDppUtils.VIBRATE_DURATION_QR_CODE_RECOGNITION
                                                .toMillis(),
                                        -1));
                    }
                }
            };
    public boolean mIsConfiguratorMode = true;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wifi.dpp.WifiDppQrCodeScannerFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends SimpleClock {
        public final long millis() {
            return SystemClock.elapsedRealtime();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnScanWifiDppSuccessListener {
        void onScanWifiDppSuccess(WifiQrCode wifiQrCode);
    }

    /* renamed from: -$$Nest$mstartWifiDppEnrolleeInitiator, reason: not valid java name */
    public static void m1026$$Nest$mstartWifiDppEnrolleeInitiator(
            WifiDppQrCodeScannerFragment wifiDppQrCodeScannerFragment, WifiQrCode wifiQrCode) {
        ViewModelStore store = wifiDppQrCodeScannerFragment.getViewModelStore();
        ViewModelProvider.Factory factory =
                wifiDppQrCodeScannerFragment.getDefaultViewModelProviderFactory();
        CreationExtras defaultViewModelCreationExtras =
                wifiDppQrCodeScannerFragment.getDefaultViewModelCreationExtras();
        Intrinsics.checkNotNullParameter(store, "store");
        Intrinsics.checkNotNullParameter(factory, "factory");
        ViewModelProviderImpl m =
                ProgressDialogFragment$$ExternalSyntheticOutline0.m(
                        defaultViewModelCreationExtras,
                        "defaultCreationExtras",
                        store,
                        factory,
                        defaultViewModelCreationExtras);
        KClass kotlinClass = JvmClassMappingKt.getKotlinClass(WifiDppInitiatorViewModel.class);
        String qualifiedName = kotlinClass.getQualifiedName();
        if (qualifiedName == null) {
            throw new IllegalArgumentException(
                    "Local and anonymous classes can not be ViewModels".toString());
        }
        WifiDppInitiatorViewModel wifiDppInitiatorViewModel =
                (WifiDppInitiatorViewModel)
                        m.getViewModel$lifecycle_viewmodel_release(
                                kotlinClass,
                                "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                        .concat(qualifiedName));
        String str = wifiQrCode.mQrCode;
        wifiDppInitiatorViewModel.mIsWifiDppHandshaking = true;
        ((WifiManager)
                        wifiDppInitiatorViewModel
                                .getApplication()
                                .getSystemService(WifiManager.class))
                .startEasyConnectAsEnrolleeInitiator(
                        str,
                        wifiDppInitiatorViewModel.getApplication().getMainExecutor(),
                        wifiDppInitiatorViewModel.new EasyConnectDelegateCallback());
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.wifi.dpp.WifiDppQrCodeScannerFragment$1] */
    public WifiDppQrCodeScannerFragment() {}

    public boolean canConnectWifi(String str) {
        for (WifiEntry wifiEntry : this.mWifiPickerTracker.getWifiEntries()) {
            if (TextUtils.equals(wifiEntry.getSsid(), WifiInfo.sanitizeSsid(str))
                    && !wifiEntry.canConnect()) {
                Log.w(
                        "WifiDppQrCodeScanner",
                        "Wi-Fi is not allowed to connect by your organization. SSID:" + str);
                showErrorMessageAndRestartCamera(R.string.not_allowed_by_ent);
                return false;
            }
        }
        return true;
    }

    @Override // com.android.settingslib.qrcode.QrCamera.ScannerCallback
    public final Rect getFramePosition(Size size) {
        return new Rect(0, 0, size.getHeight(), size.getHeight());
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return this.mIsConfiguratorMode ? 1595 : 1596;
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
        int uriScheme = this.mWifiQrCode.mUriParserResults.getUriScheme();
        if (uriScheme == 1) {
            Message obtainMessage = obtainMessage(4);
            obtainMessage.obj =
                    new WifiQrCode(this.mWifiQrCode.mQrCode)
                            .mUriParserResults.getWifiConfiguration();
            sendMessageDelayed(obtainMessage, 1000L);
        } else {
            if (uriScheme != 2) {
                return;
            }
            Message obtainMessage2 = obtainMessage(3);
            obtainMessage2.obj = new WifiQrCode(this.mWifiQrCode.mQrCode);
            sendMessageDelayed(obtainMessage2, 1000L);
        }
    }

    public boolean isDecodeTaskAlive() {
        QrCamera qrCamera = this.mCamera;
        return (qrCamera == null || qrCamera.mDecodeTask == null) ? false : true;
    }

    @Override // com.android.settings.wifi.dpp.WifiDppQrCodeBaseFragment
    public final boolean isFooterAvailable() {
        return false;
    }

    @Override // com.android.settingslib.qrcode.QrCamera.ScannerCallback
    public final boolean isValid(String str) {
        try {
            WifiQrCode wifiQrCode = new WifiQrCode(str);
            this.mWifiQrCode = wifiQrCode;
            if (!this.mIsConfiguratorMode || wifiQrCode.mUriParserResults.getUriScheme() != 1) {
                return true;
            }
            obtainMessage(2, getString(R.string.wifi_dpp_qr_code_is_not_valid_format))
                    .sendToTarget();
            return false;
        } catch (IllegalArgumentException unused) {
            obtainMessage(2, getString(R.string.wifi_dpp_qr_code_is_not_valid_format))
                    .sendToTarget();
            return false;
        }
    }

    public final boolean isWifiDppHandshaking$1() {
        ViewModelStore store = getViewModelStore();
        ViewModelProvider.Factory factory = getDefaultViewModelProviderFactory();
        CreationExtras defaultViewModelCreationExtras = getDefaultViewModelCreationExtras();
        Intrinsics.checkNotNullParameter(store, "store");
        Intrinsics.checkNotNullParameter(factory, "factory");
        ViewModelProviderImpl m =
                ProgressDialogFragment$$ExternalSyntheticOutline0.m(
                        defaultViewModelCreationExtras,
                        "defaultCreationExtras",
                        store,
                        factory,
                        defaultViewModelCreationExtras);
        KClass modelClass = JvmClassMappingKt.getKotlinClass(WifiDppInitiatorViewModel.class);
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        String qualifiedName = modelClass.getQualifiedName();
        if (qualifiedName != null) {
            return ((WifiDppInitiatorViewModel)
                            m.getViewModel$lifecycle_viewmodel_release(
                                    modelClass,
                                    "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                            .concat(qualifiedName)))
                    .mIsWifiDppHandshaking;
        }
        throw new IllegalArgumentException(
                "Local and anonymous classes can not be ViewModels".toString());
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        HandlerThread handlerThread =
                new HandlerThread(
                        "WifiDppQrCodeScanner{"
                                + Integer.toHexString(System.identityHashCode(this))
                                + "}",
                        10);
        this.mWorkerThread = handlerThread;
        handlerThread.start();
        SimpleClock anonymousClass2 = new AnonymousClass2(ZoneOffset.UTC);
        Context context = getContext();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        WifiTrackerLibProviderImpl wifiTrackerLibProvider =
                featureFactoryImpl.getWifiTrackerLibProvider();
        Lifecycle lifecycle = this.mLifecycle;
        Handler handler = new Handler(Looper.getMainLooper());
        Handler threadHandler = this.mWorkerThread.getThreadHandler();
        wifiTrackerLibProvider.getClass();
        this.mWifiPickerTracker =
                WifiTrackerLibProviderImpl.createWifiPickerTracker(
                        lifecycle, context, handler, threadHandler, anonymousClass2, null);
        if (this.mIsConfiguratorMode) {
            getActivity().setTitle(R.string.wifi_dpp_add_device_to_network);
        } else {
            getActivity().setTitle(R.string.wifi_dpp_scan_qr_code);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mScanWifiDppSuccessListener = (OnScanWifiDppSuccessListener) context;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.mIsConfiguratorMode = bundle.getBoolean("key_is_configurator_mode");
            this.mLatestStatusCode = bundle.getInt("key_latest_error_code");
            this.mEnrolleeWifiConfiguration =
                    (WifiConfiguration) bundle.getParcelable("key_wifi_configuration");
        }
        ViewModelStore store = getViewModelStore();
        ViewModelProvider.Factory factory = getDefaultViewModelProviderFactory();
        CreationExtras defaultViewModelCreationExtras = getDefaultViewModelCreationExtras();
        Intrinsics.checkNotNullParameter(store, "store");
        Intrinsics.checkNotNullParameter(factory, "factory");
        ViewModelProviderImpl m =
                ProgressDialogFragment$$ExternalSyntheticOutline0.m(
                        defaultViewModelCreationExtras,
                        "defaultCreationExtras",
                        store,
                        factory,
                        defaultViewModelCreationExtras);
        KClass kotlinClass = JvmClassMappingKt.getKotlinClass(WifiDppInitiatorViewModel.class);
        String qualifiedName = kotlinClass.getQualifiedName();
        if (qualifiedName == null) {
            throw new IllegalArgumentException(
                    "Local and anonymous classes can not be ViewModels".toString());
        }
        final WifiDppInitiatorViewModel wifiDppInitiatorViewModel =
                (WifiDppInitiatorViewModel)
                        m.getViewModel$lifecycle_viewmodel_release(
                                kotlinClass,
                                "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                        .concat(qualifiedName));
        if (wifiDppInitiatorViewModel.mEnrolleeSuccessNetworkId == null) {
            wifiDppInitiatorViewModel.mEnrolleeSuccessNetworkId = new MutableLiveData();
        }
        final int i = 0;
        wifiDppInitiatorViewModel.mEnrolleeSuccessNetworkId.observe(
                this,
                new Observer(
                        this) { // from class:
                                // com.android.settings.wifi.dpp.WifiDppQrCodeScannerFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ WifiDppQrCodeScannerFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        Integer num = (Integer) obj;
                        switch (i) {
                            case 0:
                                WifiDppQrCodeScannerFragment wifiDppQrCodeScannerFragment =
                                        this.f$0;
                                wifiDppQrCodeScannerFragment.getClass();
                                if (!wifiDppInitiatorViewModel.mIsWifiDppHandshaking) {
                                    wifiDppQrCodeScannerFragment
                                            .new EasyConnectEnrolleeStatusCallback()
                                            .onEnrolleeSuccess(num.intValue());
                                    break;
                                }
                                break;
                            default:
                                WifiDppQrCodeScannerFragment wifiDppQrCodeScannerFragment2 =
                                        this.f$0;
                                wifiDppQrCodeScannerFragment2.getClass();
                                if (!wifiDppInitiatorViewModel.mIsWifiDppHandshaking) {
                                    int intValue = num.intValue();
                                    Log.d(
                                            "WifiDppQrCodeScanner",
                                            "Easy connect enrollee callback onFailure " + intValue);
                                    wifiDppQrCodeScannerFragment2
                                            .new EasyConnectEnrolleeStatusCallback()
                                            .onFailure(intValue);
                                    break;
                                }
                                break;
                        }
                    }
                });
        if (wifiDppInitiatorViewModel.mStatusCode == null) {
            wifiDppInitiatorViewModel.mStatusCode = new MutableLiveData();
        }
        final int i2 = 1;
        wifiDppInitiatorViewModel.mStatusCode.observe(
                this,
                new Observer(
                        this) { // from class:
                                // com.android.settings.wifi.dpp.WifiDppQrCodeScannerFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ WifiDppQrCodeScannerFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        Integer num = (Integer) obj;
                        switch (i2) {
                            case 0:
                                WifiDppQrCodeScannerFragment wifiDppQrCodeScannerFragment =
                                        this.f$0;
                                wifiDppQrCodeScannerFragment.getClass();
                                if (!wifiDppInitiatorViewModel.mIsWifiDppHandshaking) {
                                    wifiDppQrCodeScannerFragment
                                            .new EasyConnectEnrolleeStatusCallback()
                                            .onEnrolleeSuccess(num.intValue());
                                    break;
                                }
                                break;
                            default:
                                WifiDppQrCodeScannerFragment wifiDppQrCodeScannerFragment2 =
                                        this.f$0;
                                wifiDppQrCodeScannerFragment2.getClass();
                                if (!wifiDppInitiatorViewModel.mIsWifiDppHandshaking) {
                                    int intValue = num.intValue();
                                    Log.d(
                                            "WifiDppQrCodeScanner",
                                            "Easy connect enrollee callback onFailure " + intValue);
                                    wifiDppQrCodeScannerFragment2
                                            .new EasyConnectEnrolleeStatusCallback()
                                            .onFailure(intValue);
                                    break;
                                }
                                break;
                        }
                    }
                });
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.removeItem(1);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.wifi_dpp_qrcode_scanner_fragment, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDestroyView() {
        this.mWorkerThread.quit();
        super.onDestroyView();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDetach() {
        this.mScanWifiDppSuccessListener = null;
        super.onDetach();
    }

    public final void onFailure(int i) {
        Log.d("WifiDppQrCodeScanner", "Wi-Fi connect onFailure reason - " + i);
        showErrorMessageAndRestartCamera(R.string.wifi_dpp_check_connection_try_again);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        QrCamera qrCamera = this.mCamera;
        if (qrCamera != null) {
            qrCamera.stop();
        }
        super.onPause();
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (isWifiDppHandshaking$1()) {
            return;
        }
        restartCamera$1();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean("key_is_configurator_mode", this.mIsConfiguratorMode);
        bundle.putInt("key_latest_error_code", this.mLatestStatusCode);
        bundle.putParcelable("key_wifi_configuration", this.mEnrolleeWifiConfiguration);
        super.onSaveInstanceState(bundle);
    }

    public final void onSuccess() {
        Intent intent = new Intent();
        intent.putExtra("key_wifi_configuration", this.mEnrolleeWifiConfiguration);
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        if (this.mWifiPermissionChecker == null) {
            this.mWifiPermissionChecker = new WifiPermissionChecker(activity);
        }
        if (!this.mWifiPermissionChecker.checkPermission("android.permission.ACCESS_WIFI_STATE")) {
            Log.w(
                    "WifiDppQrCodeScanner",
                    "Calling package does not have ACCESS_WIFI_STATE permission for result.");
            EventLog.writeEvent(
                    1397638484,
                    "187176859",
                    this.mWifiPermissionChecker.mLaunchedPackage,
                    "no ACCESS_WIFI_STATE permission");
            activity.finish();
        } else if (this.mWifiPermissionChecker.checkPermission(
                "android.permission.ACCESS_FINE_LOCATION")) {
            activity.setResult(-1, intent);
            activity.finish();
        } else {
            Log.w(
                    "WifiDppQrCodeScanner",
                    "Calling package does not have ACCESS_FINE_LOCATION permission for result.");
            EventLog.writeEvent(
                    1397638484,
                    "187176859",
                    this.mWifiPermissionChecker.mLaunchedPackage,
                    "no ACCESS_FINE_LOCATION permission");
            activity.finish();
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public final void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        if (this.mCamera == null) {
            this.mCamera = new QrCamera(getContext(), this);
            if (!isWifiDppHandshaking$1()) {
                this.mCamera.start(surfaceTexture);
                return;
            }
            QrDecorateView qrDecorateView = this.mDecorateView;
            if (qrDecorateView != null) {
                qrDecorateView.setFocused(true);
            }
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
        setProgressBarShown(isWifiDppHandshaking$1());
        if (this.mIsConfiguratorMode) {
            setHeaderTitle(R.string.wifi_dpp_add_device_to_network, new Object[0]);
            WifiNetworkConfig wifiNetworkConfig =
                    ((WifiDppConfiguratorActivity) ((WifiNetworkConfig.Retriever) getActivity()))
                            .mWifiNetworkConfig;
            if (!WifiNetworkConfig.isValidConfig(wifiNetworkConfig)) {
                throw new IllegalStateException("Invalid Wi-Fi network for configuring");
            }
            this.mSummary.setText(
                    getString(R.string.wifi_dpp_center_qr_code, wifiNetworkConfig.mSsid));
        } else {
            setHeaderTitle(R.string.wifi_dpp_scan_qr_code, new Object[0]);
            updateEnrolleeSummary();
        }
        this.mErrorMessage = (TextView) view.findViewById(R.id.error_message);
    }

    public final void restartCamera$1() {
        QrCamera qrCamera = this.mCamera;
        if (qrCamera == null) {
            Log.d("WifiDppQrCodeScanner", "mCamera is not available for restarting camera");
            return;
        }
        if (qrCamera.mDecodeTask != null) {
            qrCamera.stop();
        }
        SurfaceTexture surfaceTexture = this.mTextureView.getSurfaceTexture();
        if (surfaceTexture == null) {
            throw new IllegalStateException("SurfaceTexture is not ready for restarting camera");
        }
        this.mCamera.start(surfaceTexture);
    }

    @Override // com.android.settingslib.qrcode.QrCamera.ScannerCallback
    public final void setTransform(Matrix matrix) {
        this.mTextureView.setTransform(matrix);
    }

    public void showErrorMessageAndRestartCamera(int i) {
        Message obtainMessage = obtainMessage(2, getString(i));
        obtainMessage.arg1 = 1;
        obtainMessage.sendToTarget();
    }

    public final void updateEnrolleeSummary() {
        if (isWifiDppHandshaking$1()) {
            this.mSummary.setText(R.string.wifi_dpp_connecting);
        } else {
            this.mSummary.setText(
                    TextUtils.isEmpty(this.mSsid)
                            ? getString(
                                    R.string.wifi_dpp_scan_qr_code_join_unknown_network, this.mSsid)
                            : getString(R.string.wifi_dpp_scan_qr_code_join_network, this.mSsid));
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.settings.wifi.dpp.WifiDppQrCodeScannerFragment$1] */
    public WifiDppQrCodeScannerFragment(String str) {
        this.mSsid = str;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EasyConnectEnrolleeStatusCallback extends EasyConnectStatusCallback {
        public EasyConnectEnrolleeStatusCallback() {}

        public final void onEnrolleeSuccess(int i) {
            WifiManager wifiManager =
                    (WifiManager)
                            WifiDppQrCodeScannerFragment.this
                                    .getContext()
                                    .getSystemService(WifiManager.class);
            for (WifiConfiguration wifiConfiguration :
                    wifiManager.getPrivilegedConfiguredNetworks()) {
                if (wifiConfiguration.networkId == i) {
                    WifiDppQrCodeScannerFragment wifiDppQrCodeScannerFragment =
                            WifiDppQrCodeScannerFragment.this;
                    wifiDppQrCodeScannerFragment.mLatestStatusCode = 1;
                    wifiDppQrCodeScannerFragment.mEnrolleeWifiConfiguration = wifiConfiguration;
                    if (wifiDppQrCodeScannerFragment.canConnectWifi(wifiConfiguration.SSID)) {
                        wifiManager.connect(wifiConfiguration, WifiDppQrCodeScannerFragment.this);
                        return;
                    }
                    return;
                }
            }
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(i, "Invalid networkId ", "WifiDppQrCodeScanner");
            WifiDppQrCodeScannerFragment wifiDppQrCodeScannerFragment2 =
                    WifiDppQrCodeScannerFragment.this;
            wifiDppQrCodeScannerFragment2.mLatestStatusCode = -7;
            wifiDppQrCodeScannerFragment2.updateEnrolleeSummary();
            WifiDppQrCodeScannerFragment.this.showErrorMessageAndRestartCamera(
                    R.string.wifi_dpp_check_connection_try_again);
        }

        public final void onFailure(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "EasyConnectEnrolleeStatusCallback.onFailure ", "WifiDppQrCodeScanner");
            int i2 = R.string.wifi_dpp_failure_authentication_or_configuration;
            switch (i) {
                case -9:
                    throw new IllegalStateException(
                            "EASY_CONNECT_EVENT_FAILURE_INVALID_NETWORK should be a configurator"
                                + " only error");
                case -8:
                    throw new IllegalStateException(
                            "EASY_CONNECT_EVENT_FAILURE_NOT_SUPPORTED should be a configurator only"
                                + " error");
                case -7:
                    i2 = R.string.wifi_dpp_failure_generic;
                    break;
                case -6:
                    i2 = R.string.wifi_dpp_failure_timeout;
                    break;
                case -5:
                    WifiDppQrCodeScannerFragment wifiDppQrCodeScannerFragment =
                            WifiDppQrCodeScannerFragment.this;
                    if (i == wifiDppQrCodeScannerFragment.mLatestStatusCode) {
                        throw new IllegalStateException(
                                "stopEasyConnectSession and try again"
                                    + " forEASY_CONNECT_EVENT_FAILURE_BUSY but still failed");
                    }
                    wifiDppQrCodeScannerFragment.mLatestStatusCode = i;
                    ((WifiManager)
                                    wifiDppQrCodeScannerFragment
                                            .getContext()
                                            .getSystemService(WifiManager.class))
                            .stopEasyConnectSession();
                    WifiDppQrCodeScannerFragment wifiDppQrCodeScannerFragment2 =
                            WifiDppQrCodeScannerFragment.this;
                    WifiDppQrCodeScannerFragment.m1026$$Nest$mstartWifiDppEnrolleeInitiator(
                            wifiDppQrCodeScannerFragment2,
                            wifiDppQrCodeScannerFragment2.mWifiQrCode);
                    return;
                case -4:
                case -2:
                    break;
                case -3:
                    i2 = R.string.wifi_dpp_failure_not_compatible;
                    break;
                case -1:
                    i2 = R.string.wifi_dpp_qr_code_is_not_valid_format;
                    break;
                default:
                    throw new IllegalStateException("Unexpected Wi-Fi DPP error");
            }
            WifiDppQrCodeScannerFragment wifiDppQrCodeScannerFragment3 =
                    WifiDppQrCodeScannerFragment.this;
            wifiDppQrCodeScannerFragment3.mLatestStatusCode = i;
            wifiDppQrCodeScannerFragment3.updateEnrolleeSummary();
            WifiDppQrCodeScannerFragment.this.showErrorMessageAndRestartCamera(i2);
        }

        public final void onConfiguratorSuccess(int i) {}

        public final void onProgress(int i) {}
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public final void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {}

    @Override // android.view.TextureView.SurfaceTextureListener
    public final void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {}
}
