package com.android.settings.wifi;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SimpleClock;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.core.InstrumentedFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.wifitrackerlib.NetworkDetailsTracker;
import com.android.wifitrackerlib.WifiEntry;

import java.time.ZoneOffset;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ConfigureWifiEntryFragment extends InstrumentedFragment implements WifiConfigUiBase2 {
    public Button mCancelBtn;
    NetworkDetailsTracker mNetworkDetailsTracker;
    public Button mSubmitBtn;
    public WifiConfigController2 mUiController;
    public WifiEntry mWifiEntry;
    public HandlerThread mWorkerThread;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wifi.ConfigureWifiEntryFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends SimpleClock {
        public final long millis() {
            return SystemClock.elapsedRealtime();
        }
    }

    @Override // com.android.settings.wifi.WifiConfigUiBase2
    public final void dispatchSubmit() {
        handleSubmitAction();
    }

    @Override // com.android.settings.wifi.WifiConfigUiBase2
    public final Button getForgetButton() {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1800;
    }

    @Override // com.android.settings.wifi.WifiConfigUiBase2
    public final Button getSubmitButton() {
        return this.mSubmitBtn;
    }

    public void handleCancelAction() {
        getActivity().finish();
    }

    public void handleSubmitAction() {
        Intent intent = new Intent();
        FragmentActivity activity = getActivity();
        intent.putExtra("network_config_key", this.mUiController.getConfig());
        activity.setResult(-1, intent);
        activity.finish();
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        if (this.mNetworkDetailsTracker == null) {
            Context context2 = getContext();
            HandlerThread handlerThread =
                    new HandlerThread(
                            "ConfigureWifiEntryFragment{"
                                    + Integer.toHexString(System.identityHashCode(this))
                                    + "}",
                            10);
            this.mWorkerThread = handlerThread;
            handlerThread.start();
            SimpleClock anonymousClass1 = new AnonymousClass1(ZoneOffset.UTC);
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            WifiTrackerLibProviderImpl wifiTrackerLibProvider =
                    featureFactoryImpl.getWifiTrackerLibProvider();
            Lifecycle lifecycle = this.mLifecycle;
            Handler handler = new Handler(Looper.getMainLooper());
            Handler threadHandler = this.mWorkerThread.getThreadHandler();
            String string = getArguments().getString("key_chosen_wifientry_key");
            wifiTrackerLibProvider.getClass();
            this.mNetworkDetailsTracker =
                    WifiTrackerLibProviderImpl.createNetworkDetailsTracker(
                            lifecycle, context2, handler, threadHandler, anonymousClass1, string);
        }
        this.mWifiEntry = this.mNetworkDetailsTracker.getWifiEntry();
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.wifi_add_network_view, viewGroup, false);
        Button button = (Button) inflate.findViewById(android.R.id.button3);
        if (button != null) {
            button.setVisibility(8);
        }
        this.mSubmitBtn = (Button) inflate.findViewById(android.R.id.button1);
        this.mCancelBtn = (Button) inflate.findViewById(android.R.id.button2);
        final int i = 0;
        this.mSubmitBtn.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.wifi.ConfigureWifiEntryFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ ConfigureWifiEntryFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i2 = i;
                        ConfigureWifiEntryFragment configureWifiEntryFragment = this.f$0;
                        switch (i2) {
                            case 0:
                                configureWifiEntryFragment.handleSubmitAction();
                                break;
                            default:
                                configureWifiEntryFragment.handleCancelAction();
                                break;
                        }
                    }
                });
        final int i2 = 1;
        this.mCancelBtn.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.wifi.ConfigureWifiEntryFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ ConfigureWifiEntryFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i22 = i2;
                        ConfigureWifiEntryFragment configureWifiEntryFragment = this.f$0;
                        switch (i22) {
                            case 0:
                                configureWifiEntryFragment.handleSubmitAction();
                                break;
                            default:
                                configureWifiEntryFragment.handleCancelAction();
                                break;
                        }
                    }
                });
        this.mUiController = new WifiConfigController2(this, inflate, this.mWifiEntry, 1, false);
        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }
        getActivity().getWindow().setSoftInputMode(16);
        return inflate;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        HandlerThread handlerThread = this.mWorkerThread;
        if (handlerThread != null) {
            handlerThread.quit();
        }
        super.onDestroy();
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mUiController.showSecurityFields(false, true);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewStateRestored(Bundle bundle) {
        super.onViewStateRestored(bundle);
        this.mUiController.updatePassword();
    }

    @Override // com.android.settings.wifi.WifiConfigUiBase2
    public final void setCancelButton(CharSequence charSequence) {
        this.mCancelBtn.setText(charSequence);
    }

    @Override // com.android.settings.wifi.WifiConfigUiBase2
    public final void setSubmitButton(CharSequence charSequence) {
        this.mSubmitBtn.setText(charSequence);
    }

    @Override // com.android.settings.wifi.WifiConfigUiBase2
    public final void setTitle(int i) {
        getActivity().setTitle(i);
    }

    @Override // com.android.settings.wifi.WifiConfigUiBase2
    public final void setTitle(CharSequence charSequence) {
        getActivity().setTitle(charSequence);
    }

    @Override // com.android.settings.wifi.WifiConfigUiBase2
    public final void setForgetButton(CharSequence charSequence) {}
}
