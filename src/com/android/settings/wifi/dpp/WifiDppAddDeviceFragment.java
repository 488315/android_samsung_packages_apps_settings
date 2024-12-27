package com.android.settings.wifi.dpp;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.EasyConnectStatusCallback;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;

import com.android.settings.R;
import com.android.settings.applications.intentpicker.ProgressDialogFragment$$ExternalSyntheticOutline0;
import com.android.settings.wifi.dpp.WifiDppAddDeviceFragment.EasyConnectConfiguratorStatusCallback;
import com.android.settings.wifi.dpp.WifiDppInitiatorViewModel.EasyConnectDelegateCallback;

import com.google.android.setupcompat.template.FooterButton;

import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiDppAddDeviceFragment extends WifiDppQrCodeBaseFragment {
    public Button mChooseDifferentNetwork;
    public OnClickChooseDifferentNetworkListener mClickChooseDifferentNetworkListener;
    public int mLatestStatusCode = 0;
    public ImageView mWifiApPictureView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnClickChooseDifferentNetworkListener {}

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1595;
    }

    public final boolean isEasyConnectHandshaking() {
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

    @Override // com.android.settings.wifi.dpp.WifiDppQrCodeBaseFragment
    public final boolean isFooterAvailable() {
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mClickChooseDifferentNetworkListener = (OnClickChooseDifferentNetworkListener) context;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.mLatestStatusCode = bundle.getInt("key_latest_status_code");
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
        if (wifiDppInitiatorViewModel.mStatusCode == null) {
            wifiDppInitiatorViewModel.mStatusCode = new MutableLiveData();
        }
        wifiDppInitiatorViewModel.mStatusCode.observe(
                this,
                new Observer() { // from class:
                                 // com.android.settings.wifi.dpp.WifiDppAddDeviceFragment$$ExternalSyntheticLambda0
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        Integer num = (Integer) obj;
                        WifiDppAddDeviceFragment wifiDppAddDeviceFragment =
                                WifiDppAddDeviceFragment.this;
                        wifiDppAddDeviceFragment.getClass();
                        WifiDppInitiatorViewModel wifiDppInitiatorViewModel2 =
                                wifiDppInitiatorViewModel;
                        if (wifiDppInitiatorViewModel2.mIsWifiDppHandshaking) {
                            return;
                        }
                        int intValue = num.intValue();
                        if (intValue == 1) {
                            wifiDppAddDeviceFragment.new EasyConnectConfiguratorStatusCallback()
                                    .onConfiguratorSuccess(intValue);
                        } else {
                            wifiDppAddDeviceFragment.new EasyConnectConfiguratorStatusCallback()
                                    .onFailure(
                                            intValue,
                                            wifiDppInitiatorViewModel2.mTriedSsid,
                                            wifiDppInitiatorViewModel2.mTriedChannels,
                                            wifiDppInitiatorViewModel2.mBandArray);
                        }
                    }
                });
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.wifi_dpp_add_device_fragment, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDetach() {
        this.mClickChooseDifferentNetworkListener = null;
        super.onDetach();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("key_latest_status_code", this.mLatestStatusCode);
        super.onSaveInstanceState(bundle);
    }

    @Override // com.android.settings.wifi.dpp.WifiDppQrCodeBaseFragment,
              // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setHeaderIconImageResource(R.drawable.ic_devices_other_32dp);
        String information =
                ((WifiDppConfiguratorActivity) getActivity())
                        .mWifiDppQrCode.mUriParserResults.getInformation();
        if (TextUtils.isEmpty(information)) {
            setHeaderTitle(R.string.wifi_dpp_device_found, new Object[0]);
        } else {
            this.mGlifLayout.setHeaderText(information);
        }
        updateSummary$2$1();
        this.mWifiApPictureView = (ImageView) view.findViewById(R.id.wifi_ap_picture_view);
        Button button = (Button) view.findViewById(R.id.choose_different_network);
        this.mChooseDifferentNetwork = button;
        button.setOnClickListener(new WifiDppAddDeviceFragment$$ExternalSyntheticLambda1(this, 0));
        this.mLeftButton.setText(getContext(), R.string.cancel);
        this.mLeftButton.onClickListener =
                new WifiDppAddDeviceFragment$$ExternalSyntheticLambda1(this, 1);
        this.mRightButton.setText(getContext(), R.string.wifi_dpp_share_wifi);
        this.mRightButton.onClickListener =
                new WifiDppAddDeviceFragment$$ExternalSyntheticLambda1(this, 2);
        if (bundle != null) {
            int i = this.mLatestStatusCode;
            if (i == 1) {
                showSuccessUi(true);
            } else if (i != 0) {
                showErrorUi(i, null, true);
            } else {
                setProgressBarShown(isEasyConnectHandshaking());
                this.mRightButton.setVisibility(isEasyConnectHandshaking() ? 4 : 0);
            }
        }
    }

    public final void showErrorUi(int i, final Intent intent, boolean z) {
        CharSequence text;
        switch (i) {
            case -12:
                text = getText(R.string.wifi_dpp_failure_enrollee_rejected_configuration);
                break;
            case -11:
                text = getText(R.string.wifi_dpp_failure_enrollee_authentication);
                break;
            case -10:
                text = getText(R.string.wifi_dpp_failure_cannot_find_network);
                break;
            case -9:
                throw new IllegalStateException(
                        "Wi-Fi DPP configurator used a non-PSK/non-SAEnetwork to handshake");
            case -8:
                WifiNetworkConfig wifiNetworkConfig =
                        ((WifiDppConfiguratorActivity) getActivity()).mWifiNetworkConfig;
                if (!WifiNetworkConfig.isValidConfig(wifiNetworkConfig)) {
                    throw new IllegalStateException("Invalid Wi-Fi network for configuring");
                }
                text = getString(R.string.wifi_dpp_failure_not_supported, wifiNetworkConfig.mSsid);
                break;
            case -7:
                text = getText(R.string.wifi_dpp_failure_generic);
                break;
            case -6:
                text = getText(R.string.wifi_dpp_failure_timeout);
                break;
            case -5:
                if (z) {
                    return;
                }
                if (i == this.mLatestStatusCode) {
                    throw new IllegalStateException(
                            "Tried restarting EasyConnectSession but stillreceiving"
                                + " EASY_CONNECT_EVENT_FAILURE_BUSY");
                }
                this.mLatestStatusCode = i;
                ((WifiManager) getContext().getSystemService(WifiManager.class))
                        .stopEasyConnectSession();
                startWifiDppConfiguratorInitiator();
                return;
            case -4:
                text = getText(R.string.wifi_dpp_failure_authentication_or_configuration);
                break;
            case -3:
                text = getText(R.string.wifi_dpp_failure_not_compatible);
                break;
            case -2:
                text = getText(R.string.wifi_dpp_failure_authentication_or_configuration);
                break;
            case -1:
                text = getText(R.string.wifi_dpp_qr_code_is_not_valid_format);
                break;
            default:
                throw new IllegalStateException("Unexpected Wi-Fi DPP error");
        }
        setHeaderTitle(R.string.wifi_dpp_could_not_add_device, new Object[0]);
        this.mSummary.setText(text);
        this.mWifiApPictureView.setImageResource(R.drawable.wifi_dpp_error);
        this.mChooseDifferentNetwork.setVisibility(4);
        FooterButton footerButton = this.mLeftButton;
        if (i == -3 || i == -1) {
            this.mRightButton.setText(getContext(), R.string.done);
            footerButton = this.mRightButton;
            this.mLeftButton.setVisibility(4);
        } else {
            this.mRightButton.setText(getContext(), R.string.retry);
        }
        footerButton.onClickListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.wifi.dpp.WifiDppAddDeviceFragment$$ExternalSyntheticLambda4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        WifiDppAddDeviceFragment wifiDppAddDeviceFragment =
                                WifiDppAddDeviceFragment.this;
                        wifiDppAddDeviceFragment.getActivity().setResult(0, intent);
                        wifiDppAddDeviceFragment.getActivity().finish();
                    }
                };
        if (isEasyConnectHandshaking()) {
            this.mSummary.setText(R.string.wifi_dpp_sharing_wifi_with_this_device);
        }
        setProgressBarShown(isEasyConnectHandshaking());
        this.mRightButton.setVisibility(isEasyConnectHandshaking() ? 4 : 0);
        if (z) {
            return;
        }
        this.mLatestStatusCode = i;
    }

    public final void showSuccessUi(boolean z) {
        setHeaderIconImageResource(R.drawable.ic_devices_check_circle_green_32dp);
        setHeaderTitle(R.string.wifi_dpp_wifi_shared_with_device, new Object[0]);
        setProgressBarShown(isEasyConnectHandshaking());
        this.mSummary.setVisibility(4);
        this.mWifiApPictureView.setImageResource(R.drawable.wifi_dpp_success);
        this.mChooseDifferentNetwork.setVisibility(4);
        this.mLeftButton.setText(getContext(), R.string.wifi_dpp_add_another_device);
        this.mLeftButton.onClickListener =
                new WifiDppAddDeviceFragment$$ExternalSyntheticLambda1(this, 3);
        this.mRightButton.setText(getContext(), R.string.done);
        FooterButton footerButton = this.mRightButton;
        footerButton.onClickListener =
                new WifiDppAddDeviceFragment$$ExternalSyntheticLambda1(this, 4);
        footerButton.setVisibility(0);
        if (z) {
            return;
        }
        this.mLatestStatusCode = 1;
    }

    public final void startWifiDppConfiguratorInitiator() {
        String str = ((WifiDppConfiguratorActivity) getActivity()).mWifiDppQrCode.mQrCode;
        int i = ((WifiDppConfiguratorActivity) getActivity()).mWifiNetworkConfig.mNetworkId;
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
        WifiDppInitiatorViewModel wifiDppInitiatorViewModel =
                (WifiDppInitiatorViewModel)
                        m.getViewModel$lifecycle_viewmodel_release(
                                kotlinClass,
                                "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                        .concat(qualifiedName));
        wifiDppInitiatorViewModel.mIsWifiDppHandshaking = true;
        ((WifiManager)
                        wifiDppInitiatorViewModel
                                .getApplication()
                                .getSystemService(WifiManager.class))
                .startEasyConnectAsConfiguratorInitiator(
                        str,
                        i,
                        0,
                        wifiDppInitiatorViewModel.getApplication().getMainExecutor(),
                        wifiDppInitiatorViewModel.new EasyConnectDelegateCallback());
    }

    public final void updateSummary$2$1() {
        if (isEasyConnectHandshaking()) {
            this.mSummary.setText(R.string.wifi_dpp_sharing_wifi_with_this_device);
            return;
        }
        TextView textView = this.mSummary;
        WifiNetworkConfig wifiNetworkConfig =
                ((WifiDppConfiguratorActivity) getActivity()).mWifiNetworkConfig;
        if (!WifiNetworkConfig.isValidConfig(wifiNetworkConfig)) {
            throw new IllegalStateException("Invalid Wi-Fi network for configuring");
        }
        textView.setText(getString(R.string.wifi_dpp_add_device_to_wifi, wifiNetworkConfig.mSsid));
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EasyConnectConfiguratorStatusCallback extends EasyConnectStatusCallback {
        public EasyConnectConfiguratorStatusCallback() {}

        public final void onConfiguratorSuccess(int i) {
            WifiDppAddDeviceFragment.this.showSuccessUi(false);
        }

        public final void onFailure(int i, String str, SparseArray sparseArray, int[] iArr) {
            int keyAt;
            JSONArray jSONArray;
            Log.d(
                    "WifiDppAddDeviceFragment",
                    "EasyConnectConfiguratorStatusCallback.onFailure: " + i);
            if (!TextUtils.isEmpty(str)) {
                DialogFragment$$ExternalSyntheticOutline0.m(
                        "Tried SSID: ", str, "WifiDppAddDeviceFragment");
            }
            if (sparseArray.size() != 0) {
                Log.d("WifiDppAddDeviceFragment", "Tried channels: " + sparseArray);
            }
            if (iArr != null && iArr.length > 0) {
                StringBuilder sb = new StringBuilder("Supported bands: ");
                for (int i2 : iArr) {
                    sb.append(i2 + " ");
                }
                Log.d("WifiDppAddDeviceFragment", sb.toString());
            }
            WifiDppAddDeviceFragment wifiDppAddDeviceFragment = WifiDppAddDeviceFragment.this;
            wifiDppAddDeviceFragment.getClass();
            Intent intent = new Intent();
            intent.putExtra("android.provider.extra.EASY_CONNECT_ERROR_CODE", i);
            if (!TextUtils.isEmpty(str)) {
                intent.putExtra("android.provider.extra.EASY_CONNECT_ATTEMPTED_SSID", str);
            }
            if (sparseArray.size() != 0) {
                JSONObject jSONObject = new JSONObject();
                int i3 = 0;
                while (true) {
                    try {
                        keyAt = sparseArray.keyAt(i3);
                        jSONArray = new JSONArray();
                        for (int i4 : (int[]) sparseArray.get(keyAt)) {
                            jSONArray.put(i4);
                        }
                    } catch (ArrayIndexOutOfBoundsException unused) {
                    }
                    try {
                        jSONObject.put(Integer.toString(keyAt), jSONArray);
                        i3++;
                    } catch (JSONException unused2) {
                        jSONObject = new JSONObject();
                        intent.putExtra(
                                "android.provider.extra.EASY_CONNECT_CHANNEL_LIST",
                                jSONObject.toString());
                        if (iArr != null) {
                            intent.putExtra("android.provider.extra.EASY_CONNECT_BAND_LIST", iArr);
                        }
                        wifiDppAddDeviceFragment.showErrorUi(i, intent, false);
                    }
                }
            }
            if (iArr != null && iArr.length != 0) {
                intent.putExtra("android.provider.extra.EASY_CONNECT_BAND_LIST", iArr);
            }
            wifiDppAddDeviceFragment.showErrorUi(i, intent, false);
        }

        public final void onEnrolleeSuccess(int i) {}

        public final void onProgress(int i) {}
    }
}
