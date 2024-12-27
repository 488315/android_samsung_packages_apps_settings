package com.android.settings.connecteddevice.usb;

import android.content.Context;
import android.net.TetheringManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.connecteddevice.usb.UsbDetailsFunctionsController.OnStartTetheringCallback;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.RestrictedLockUtilsInternal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UsbDetailsFragment extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.usb_details_fragment);
    public List mControllers;
    public UsbBackend mUsbBackend;
    UsbConnectionBroadcastReceiver mUsbReceiver;
    public boolean mUserAuthenticated = false;
    public final UsbDetailsFragment$$ExternalSyntheticLambda0 mUsbConnectionListener =
            new UsbConnectionBroadcastReceiver
                    .UsbConnectionListener() { // from class:
                                               // com.android.settings.connecteddevice.usb.UsbDetailsFragment$$ExternalSyntheticLambda0
                @Override // com.android.settings.connecteddevice.usb.UsbConnectionBroadcastReceiver.UsbConnectionListener
                public final void onUsbConnectionChanged(
                        boolean z, long j, int i, int i2, boolean z2) {
                    Iterator it = ((ArrayList) UsbDetailsFragment.this.mControllers).iterator();
                    while (it.hasNext()) {
                        UsbDetailsController usbDetailsController =
                                (UsbDetailsController) it.next();
                        Log.i(
                                "UsbDetailsFragment",
                                "controller.refresh : connected : "
                                        + z
                                        + " functions : "
                                        + j
                                        + " powerRole : "
                                        + i
                                        + " dataRole : "
                                        + i2);
                        usbDetailsController.refresh(z, j, i, i2);
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.connecteddevice.usb.UsbDetailsFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return new ArrayList(
                    UsbDetailsFragment.createControllerList(
                            context, new UsbBackend(context), null));
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return RestrictedLockUtilsInternal.checkIfUsbDataSignalingIsDisabled(
                            context, UserHandle.myUserId())
                    == null;
        }
    }

    public static List createControllerList(
            Context context, UsbBackend usbBackend, UsbDetailsFragment usbDetailsFragment) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new UsbDetailsDataRoleController(context, usbBackend, usbDetailsFragment));
        UsbDetailsFunctionsController usbDetailsFunctionsController =
                new UsbDetailsFunctionsController(context, usbBackend, usbDetailsFragment);
        usbDetailsFunctionsController.mTetheringManager =
                (TetheringManager) context.getSystemService(TetheringManager.class);
        usbDetailsFunctionsController.mOnStartTetheringCallback =
                usbDetailsFunctionsController.new OnStartTetheringCallback();
        usbDetailsFunctionsController.mPreviousFunction =
                usbDetailsFunctionsController.mUsbBackend.mUsbManager.getCurrentFunctions();
        usbDetailsFunctionsController.mHandler = new Handler(context.getMainLooper());
        arrayList.add(usbDetailsFunctionsController);
        arrayList.add(new UsbDetailsPowerRoleController(context, usbBackend, usbDetailsFragment));
        arrayList.add(
                new UsbDetailsTranscodeMtpController(context, usbBackend, usbDetailsFragment));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        Log.d("UsbDetailsFragment", "createPreferenceControllers");
        UsbBackend usbBackend = new UsbBackend(context);
        this.mUsbBackend = usbBackend;
        this.mControllers = createControllerList(context, usbBackend, this);
        this.mUsbReceiver =
                new UsbConnectionBroadcastReceiver(
                        context, this.mUsbConnectionListener, this.mUsbBackend);
        getSettingsLifecycle().addObserver(this.mUsbReceiver);
        return new ArrayList(this.mControllers);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "UsbDetailsFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1291;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.usb_details_fragment;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (getListView() != null) {
            getListView().mDrawLastRoundedCorner = false;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        this.mUserAuthenticated = false;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        Utils.setActionBarShadowAnimation(getActivity(), getSettingsLifecycle(), getListView());
    }
}
