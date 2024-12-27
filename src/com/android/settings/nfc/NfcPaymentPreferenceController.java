package com.android.settings.nfc;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.UserManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NfcPaymentPreferenceController extends BasePreferenceController
        implements PaymentBackend.Callback,
                View.OnClickListener,
                LifecycleObserver,
                OnStart,
                OnStop {
    private static final String TAG = "NfcPaymentController";
    private final NfcPaymentAdapter mAdapter;
    private PaymentBackend mPaymentBackend;
    private NfcPaymentPreference mPreference;
    private ImageView mSettingsButtonView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NfcPaymentAdapter extends BaseAdapter
            implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
        public PaymentBackend.PaymentAppInfo[] appInfos;
        public final LayoutInflater mLayoutInflater;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class ViewHolder {
            public RadioButton radioButton;
        }

        public NfcPaymentAdapter(Context context) {
            this.mLayoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            PaymentBackend.PaymentAppInfo[] paymentAppInfoArr = this.appInfos;
            if (paymentAppInfoArr != null) {
                return paymentAppInfoArr.length;
            }
            return 0;
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return this.appInfos[i];
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return this.appInfos[i].componentName.hashCode();
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            PaymentBackend.PaymentAppInfo paymentAppInfo = this.appInfos[i];
            if (view == null) {
                view = this.mLayoutInflater.inflate(R.layout.nfc_payment_option, viewGroup, false);
                viewHolder = new ViewHolder();
                viewHolder.radioButton = (RadioButton) view.findViewById(R.id.button);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            UserManager userManager =
                    (UserManager)
                            ((AbstractPreferenceController) NfcPaymentPreferenceController.this)
                                    .mContext
                                    .createContextAsUser(paymentAppInfo.userHandle, 0)
                                    .getSystemService(UserManager.class);
            viewHolder.radioButton.setOnCheckedChangeListener(null);
            viewHolder.radioButton.setChecked(paymentAppInfo.isDefault);
            viewHolder.radioButton.setContentDescription(
                    ((Object) paymentAppInfo.label) + " (" + userManager.getUserName() + ")");
            viewHolder.radioButton.setOnCheckedChangeListener(this);
            viewHolder.radioButton.setTag(paymentAppInfo);
            viewHolder.radioButton.setText(
                    ((Object) paymentAppInfo.label) + " (" + userManager.getUserName() + ")");
            return view;
        }

        public final void makeDefault(PaymentBackend.PaymentAppInfo paymentAppInfo) {
            if (!paymentAppInfo.isDefault) {
                NfcPaymentPreferenceController.this.mPaymentBackend.setDefaultPaymentApp(
                        paymentAppInfo.componentName, paymentAppInfo.userHandle.getIdentifier());
            }
            Dialog dialog = NfcPaymentPreferenceController.this.mPreference.getDialog();
            if (dialog != null) {
                dialog.dismiss();
            }
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            makeDefault((PaymentBackend.PaymentAppInfo) compoundButton.getTag());
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            makeDefault((PaymentBackend.PaymentAppInfo) view.getTag());
        }
    }

    public NfcPaymentPreferenceController(Context context, String str) {
        super(context, str);
        this.mAdapter = new NfcPaymentAdapter(context);
    }

    private void updateSettingsVisibility() {
        ImageView imageView = this.mSettingsButtonView;
        if (imageView != null) {
            PaymentBackend.PaymentAppInfo paymentAppInfo = this.mPaymentBackend.mDefaultAppInfo;
            if (paymentAppInfo == null || paymentAppInfo.settingsComponent == null) {
                imageView.setVisibility(8);
            } else {
                imageView.setVisibility(0);
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        NfcPaymentPreference nfcPaymentPreference =
                (NfcPaymentPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = nfcPaymentPreference;
        if (nfcPaymentPreference != null) {
            nfcPaymentPreference.mListener = this;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.nfc")
                || NfcAdapter.getDefaultAdapter(this.mContext) == null) {
            return 3;
        }
        if (this.mPaymentBackend == null) {
            this.mPaymentBackend = new PaymentBackend(this.mContext);
        }
        ArrayList arrayList = this.mPaymentBackend.mAppInfos;
        return (arrayList == null || arrayList.isEmpty()) ? 3 : 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        PaymentBackend.PaymentAppInfo paymentAppInfo = this.mPaymentBackend.mDefaultAppInfo;
        if (paymentAppInfo == null) {
            return this.mContext.getText(R.string.nfc_payment_default_not_set);
        }
        return ((Object) paymentAppInfo.label)
                + " ("
                + ((UserManager)
                                this.mContext
                                        .createContextAsUser(paymentAppInfo.userHandle, 0)
                                        .getSystemService(UserManager.class))
                        .getUserName()
                + ")";
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R.id.settings_button);
        this.mSettingsButtonView = imageView;
        imageView.setOnClickListener(this);
        updateSettingsVisibility();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        PaymentBackend.PaymentAppInfo paymentAppInfo = this.mPaymentBackend.mDefaultAppInfo;
        if (paymentAppInfo == null || paymentAppInfo.settingsComponent == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setComponent(paymentAppInfo.settingsComponent);
        intent.addFlags(268435456);
        try {
            this.mContext.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            Log.e(TAG, "Settings activity not found.");
        }
    }

    @Override // com.android.settings.nfc.PaymentBackend.Callback
    public void onPaymentAppsChanged() {
        updateState(this.mPreference);
    }

    public void onPrepareDialogBuilder(
            AlertDialog.Builder builder, DialogInterface.OnClickListener onClickListener) {
        builder.setSingleChoiceItems(this.mAdapter, 0, onClickListener);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        PaymentBackend paymentBackend = this.mPaymentBackend;
        if (paymentBackend != null) {
            paymentBackend.mCallbacks.add(this);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        PaymentBackend paymentBackend = this.mPaymentBackend;
        if (paymentBackend != null) {
            paymentBackend.mCallbacks.remove(this);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setPaymentBackend(PaymentBackend paymentBackend) {
        this.mPaymentBackend = paymentBackend;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        ArrayList arrayList = this.mPaymentBackend.mAppInfos;
        if (arrayList != null) {
            PaymentBackend.PaymentAppInfo[] paymentAppInfoArr =
                    (PaymentBackend.PaymentAppInfo[])
                            arrayList.toArray(new PaymentBackend.PaymentAppInfo[arrayList.size()]);
            NfcPaymentAdapter nfcPaymentAdapter = this.mAdapter;
            nfcPaymentAdapter.appInfos = paymentAppInfoArr;
            nfcPaymentAdapter.notifyDataSetChanged();
        }
        super.updateState(preference);
        updateSettingsVisibility();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
