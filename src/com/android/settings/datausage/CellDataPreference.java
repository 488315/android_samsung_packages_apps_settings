package com.android.settings.datausage;

import android.content.Context;
import android.content.DialogInterface;
import android.net.NetworkTemplate;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.network.MobileDataEnabledListener;
import com.android.settings.network.ProxySubscriptionManager;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.CustomDialogPreferenceCompat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CellDataPreference extends CustomDialogPreferenceCompat
        implements TemplatePreference, MobileDataEnabledListener.Client {
    public boolean mChecked;
    public final MobileDataEnabledListener mDataStateListener;
    public boolean mMultiSimDialog;
    final ProxySubscriptionManager.OnActiveSubscriptionChangedListener
            mOnSubscriptionsChangeListener;
    public int mSubId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.datausage.CellDataPreference$1, reason: invalid class name */
    public final class AnonymousClass1
            implements ProxySubscriptionManager.OnActiveSubscriptionChangedListener {
        public AnonymousClass1() {}
    }

    public CellDataPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.switchPreferenceCompatStyle);
        this.mSubId = -1;
        this.mOnSubscriptionsChangeListener = new AnonymousClass1();
        this.mDataStateListener = new MobileDataEnabledListener(context, this);
    }

    public SubscriptionInfo getActiveSubscriptionInfo(int i) {
        return getProxySubscriptionManager().mSubscriptionMonitor.getActiveSubscriptionInfo(i);
    }

    public ProxySubscriptionManager getProxySubscriptionManager() {
        return ProxySubscriptionManager.getInstance(getContext());
    }

    @Override // androidx.preference.Preference
    public final void onAttached() {
        registerDependency();
        this.mDataStateListener.start(this.mSubId);
        getProxySubscriptionManager()
                .addActiveSubscriptionsListener(this.mOnSubscriptionsChangeListener);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        CompoundButton compoundButton =
                (CompoundButton) preferenceViewHolder.findViewById(android.R.id.switch_widget);
        compoundButton.setClickable(false);
        compoundButton.setChecked(this.mChecked);
    }

    @Override // com.android.settingslib.CustomDialogPreferenceCompat
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i != -1) {
            return;
        }
        if (!this.mMultiSimDialog) {
            setMobileDataEnabled(false);
            return;
        }
        getProxySubscriptionManager()
                .mSubscriptionMonitor
                .getSubscriptionManager()
                .setDefaultDataSubId(this.mSubId);
        setMobileDataEnabled(true);
        int i2 = this.mSubId;
        if (getActiveSubscriptionInfo(i2) != null) {
            ((TelephonyManager) getContext().getSystemService(TelephonyManager.class))
                    .setDataEnabled(i2, false);
        }
    }

    @Override // androidx.preference.Preference
    public final void onDetached() {
        this.mDataStateListener.stop();
        getProxySubscriptionManager()
                .removeActiveSubscriptionsListener(this.mOnSubscriptionsChangeListener);
        super.onDetached();
    }

    @Override // com.android.settings.network.MobileDataEnabledListener.Client
    public void onMobileDataEnabledChange() {
        boolean dataEnabled =
                ((TelephonyManager) getContext().getSystemService(TelephonyManager.class))
                        .getDataEnabled(this.mSubId);
        if (this.mChecked == dataEnabled) {
            return;
        }
        this.mChecked = dataEnabled;
        notifyChanged();
    }

    @Override // com.android.settingslib.CustomDialogPreferenceCompat
    public final void onPrepareDialogBuilder(
            AlertDialog.Builder builder, DialogInterface.OnClickListener onClickListener) {
        boolean z = this.mMultiSimDialog;
        AlertController.AlertParams alertParams = builder.P;
        if (!z) {
            alertParams.mTitle = null;
            builder.setMessage(R.string.data_usage_disable_mobile);
            builder.setPositiveButton(android.R.string.ok, onClickListener);
            builder.setNegativeButton(
                    android.R.string.cancel, (DialogInterface.OnClickListener) null);
            return;
        }
        SubscriptionInfo activeSubscriptionInfo = getActiveSubscriptionInfo(this.mSubId);
        SubscriptionInfo activeSubscriptionInfo2 =
                getActiveSubscriptionInfo(SubscriptionManager.getDefaultDataSubscriptionId());
        String string =
                activeSubscriptionInfo2 == null
                        ? getContext()
                                .getResources()
                                .getString(R.string.sim_selection_required_pref)
                        : SubscriptionUtil.getUniqueSubscriptionDisplayName(
                                        getContext(), activeSubscriptionInfo2)
                                .toString();
        builder.setTitle(R.string.sim_change_data_title);
        alertParams.mMessage =
                getContext()
                        .getString(
                                R.string.sim_change_data_message,
                                String.valueOf(
                                        activeSubscriptionInfo != null
                                                ? SubscriptionUtil.getUniqueSubscriptionDisplayName(
                                                        getContext(), activeSubscriptionInfo)
                                                : null),
                                string);
        builder.setPositiveButton(R.string.okay, onClickListener);
        builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
    }

    @Override // androidx.preference.Preference
    public final void onRestoreInstanceState(Parcelable parcelable) {
        CellDataState cellDataState = (CellDataState) parcelable;
        super.onRestoreInstanceState(cellDataState.getSuperState());
        this.mChecked = cellDataState.mChecked;
        this.mMultiSimDialog = cellDataState.mMultiSimDialog;
        if (this.mSubId == -1) {
            this.mSubId = cellDataState.mSubId;
            setKey(getKey() + this.mSubId);
        }
        notifyChanged();
    }

    @Override // androidx.preference.Preference
    public final Parcelable onSaveInstanceState() {
        CellDataState cellDataState = new CellDataState(super.onSaveInstanceState());
        cellDataState.mChecked = this.mChecked;
        cellDataState.mMultiSimDialog = this.mMultiSimDialog;
        cellDataState.mSubId = this.mSubId;
        return cellDataState;
    }

    @Override // androidx.preference.Preference
    public final void performClick(View view) {
        Context context = getContext();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getMetricsFeatureProvider().action(context, 178, !this.mChecked);
        SubscriptionInfo activeSubscriptionInfo = getActiveSubscriptionInfo(this.mSubId);
        SubscriptionInfo activeSubscriptionInfo2 =
                getActiveSubscriptionInfo(SubscriptionManager.getDefaultDataSubscriptionId());
        if (!this.mChecked) {
            setMobileDataEnabled(true);
            return;
        }
        setMobileDataEnabled(false);
        if (activeSubscriptionInfo2 == null
                || activeSubscriptionInfo == null
                || activeSubscriptionInfo.getSubscriptionId()
                        != activeSubscriptionInfo2.getSubscriptionId()) {
            return;
        }
        int i = this.mSubId;
        if (getActiveSubscriptionInfo(i) != null) {
            ((TelephonyManager) getContext().getSystemService(TelephonyManager.class))
                    .setDataEnabled(i, false);
        }
    }

    public final void setMobileDataEnabled(boolean z) {
        StringBuilder m = RowView$$ExternalSyntheticOutline0.m("setDataEnabledForReason (", ",", z);
        m.append(this.mSubId);
        m.append(")");
        Log.d("CellDataPreference", m.toString());
        ((TelephonyManager) getContext().getSystemService(TelephonyManager.class))
                .setDataEnabled(this.mSubId, z);
        if (this.mChecked == z) {
            return;
        }
        this.mChecked = z;
        notifyChanged();
    }

    @Override // com.android.settings.datausage.TemplatePreference
    public final void setTemplate(NetworkTemplate networkTemplate, int i) {
        if (i == -1) {
            throw new IllegalArgumentException("CellDataPreference needs a SubscriptionInfo");
        }
        getProxySubscriptionManager()
                .addActiveSubscriptionsListener(this.mOnSubscriptionsChangeListener);
        if (this.mSubId == -1) {
            this.mSubId = i;
            setKey(getKey() + i);
        }
        setEnabled(getActiveSubscriptionInfo(this.mSubId) != null);
        boolean dataEnabled =
                ((TelephonyManager) getContext().getSystemService(TelephonyManager.class))
                        .getDataEnabled(this.mSubId);
        if (this.mChecked == dataEnabled) {
            return;
        }
        this.mChecked = dataEnabled;
        notifyChanged();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CellDataState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<CellDataState> CREATOR = new AnonymousClass1();
        public boolean mChecked;
        public boolean mMultiSimDialog;
        public int mSubId;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.datausage.CellDataPreference$CellDataState$1, reason: invalid class name */
        public final class AnonymousClass1 implements Parcelable.Creator {
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new CellDataState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new CellDataState[i];
            }
        }

        public CellDataState(Parcel parcel) {
            super(parcel);
            this.mChecked = parcel.readByte() != 0;
            this.mMultiSimDialog = parcel.readByte() != 0;
            this.mSubId = parcel.readInt();
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeByte(this.mChecked ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.mMultiSimDialog ? (byte) 1 : (byte) 0);
            parcel.writeInt(this.mSubId);
        }

        public CellDataState(Parcelable parcelable) {
            super(parcelable);
        }
    }
}
