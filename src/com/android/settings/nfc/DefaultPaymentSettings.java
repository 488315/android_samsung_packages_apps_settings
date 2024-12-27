package com.android.settings.nfc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.Layout;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AlignmentSpan;
import android.text.style.BulletSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.applications.defaultapps.DefaultAppPickerFragment;
import com.android.settingslib.widget.CandidateInfo;
import com.android.settingslib.widget.FooterPreference;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DefaultPaymentSettings extends DefaultAppPickerFragment {
    public List mAppInfos;
    public FooterPreference mFooterPreference;
    public PaymentBackend mPaymentBackend;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class NfcPaymentCandidateInfo extends CandidateInfo {
        public final Drawable mDrawable;
        public final boolean mIsManagedProfile;
        public final String mKey;
        public final CharSequence mLabel;
        public final int mUserId;

        public NfcPaymentCandidateInfo(
                String str, CharSequence charSequence, Drawable drawable, int i, boolean z) {
            super(true);
            this.mKey = str;
            this.mLabel = charSequence;
            this.mDrawable = drawable;
            this.mUserId = i;
            this.mIsManagedProfile = z;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final String getKey() {
            return this.mKey + " " + this.mUserId;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final Drawable loadIcon() {
            return this.mDrawable;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final CharSequence loadLabel() {
            return this.mLabel;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NfcPaymentCandidateInfoComparator implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            NfcPaymentCandidateInfo nfcPaymentCandidateInfo = (NfcPaymentCandidateInfo) obj2;
            CharSequence charSequence = ((NfcPaymentCandidateInfo) obj).mLabel;
            CharSequence charSequence2 = nfcPaymentCandidateInfo.mLabel;
            if (charSequence == charSequence2) {
                return 0;
            }
            if (charSequence == null) {
                return -1;
            }
            if (charSequence2 == null) {
                return 1;
            }
            return charSequence.toString().compareTo(nfcPaymentCandidateInfo.mLabel.toString());
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final void addStaticPreferences(PreferenceScreen preferenceScreen) {
        if (this.mFooterPreference == null) {
            FooterPreference footerPreference = new FooterPreference(getContext());
            this.mFooterPreference = footerPreference;
            footerPreference.setTitle(
                    getResources().getString(R.string.nfc_default_payment_footer));
            this.mFooterPreference.setIcon(R.drawable.ic_info_outline_24dp);
            this.mFooterPreference.setLearnMoreAction(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.nfc.DefaultPaymentSettings$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            DefaultPaymentSettings defaultPaymentSettings =
                                    DefaultPaymentSettings.this;
                            defaultPaymentSettings.getClass();
                            defaultPaymentSettings
                                    .getContext()
                                    .startActivity(
                                            new Intent(
                                                    defaultPaymentSettings.getActivity(),
                                                    (Class<?>) HowItWorks.class));
                        }
                    });
        }
        preferenceScreen.addPreference(this.mFooterPreference);
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPickerFragment,
              // com.android.settings.widget.RadioButtonPickerFragment
    public final void bindPreferenceExtra(
            SelectorWithWidgetPreference selectorWithWidgetPreference,
            String str,
            CandidateInfo candidateInfo,
            String str2) {
        if (((NfcPaymentCandidateInfo) candidateInfo).mIsManagedProfile) {
            selectorWithWidgetPreference.setSummary(getContext().getString(R.string.nfc_work_text));
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final List getCandidates() {
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) this.mAppInfos).iterator();
        while (it.hasNext()) {
            PaymentBackend.PaymentAppInfo paymentAppInfo =
                    (PaymentBackend.PaymentAppInfo) it.next();
            boolean isManagedProfile =
                    ((UserManager)
                                    getContext()
                                            .createContextAsUser(paymentAppInfo.userHandle, 0)
                                            .getSystemService(UserManager.class))
                            .isManagedProfile(paymentAppInfo.userHandle.getIdentifier());
            arrayList.add(
                    new NfcPaymentCandidateInfo(
                            paymentAppInfo.componentName.flattenToString(),
                            paymentAppInfo.label,
                            paymentAppInfo.icon,
                            paymentAppInfo.userHandle.getIdentifier(),
                            isManagedProfile));
        }
        Collections.sort(arrayList, new NfcPaymentCandidateInfoComparator());
        return arrayList;
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPickerFragment
    public final CharSequence getConfirmationMessage(CandidateInfo candidateInfo) {
        if (candidateInfo == null) {
            return null;
        }
        Context context = getContext();
        int i = ((NfcPaymentCandidateInfo) candidateInfo).mUserId;
        if (!((UserManager)
                        context.createContextAsUser(UserHandle.of(i), 0)
                                .getSystemService(UserManager.class))
                .isManagedProfile(i)) {
            return null;
        }
        String string =
                getContext().getString(R.string.nfc_default_payment_workapp_confirmation_title);
        String string2 =
                getContext()
                        .getString(R.string.nfc_default_payment_workapp_confirmation_message_title);
        String string3 =
                getContext().getString(R.string.nfc_default_payment_workapp_confirmation_message_1);
        String string4 =
                getContext().getString(R.string.nfc_default_payment_workapp_confirmation_message_2);
        SpannableString spannableString = new SpannableString(string);
        SpannableString spannableString2 = new SpannableString(string2);
        SpannableString spannableString3 = new SpannableString(string3);
        SpannableString spannableString4 = new SpannableString(string4);
        spannableString.setSpan(new RelativeSizeSpan(1.5f), 0, string.length(), 33);
        Layout.Alignment alignment = Layout.Alignment.ALIGN_CENTER;
        spannableString.setSpan(new AlignmentSpan.Standard(alignment), 0, string.length(), 33);
        spannableString2.setSpan(new AlignmentSpan.Standard(alignment), 0, string2.length(), 33);
        spannableString3.setSpan(new BulletSpan(20), 0, string3.length(), 33);
        spannableString4.setSpan(new BulletSpan(20), 0, string4.length(), 33);
        return TextUtils.concat(
                spannableString,
                "\n\n",
                spannableString2,
                "\n\n",
                spannableString3,
                "\n",
                spannableString4);
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final String getDefaultKey() {
        PaymentBackend.PaymentAppInfo paymentAppInfo = this.mPaymentBackend.mDefaultAppInfo;
        if (paymentAppInfo == null) {
            return null;
        }
        return paymentAppInfo.componentName.flattenToString()
                + " "
                + paymentAppInfo.userHandle.getIdentifier();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 70;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.nfc_default_payment_settings;
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPickerFragment,
              // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        PaymentBackend paymentBackend = new PaymentBackend(getActivity());
        this.mPaymentBackend = paymentBackend;
        this.mAppInfos = paymentBackend.mAppInfos;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mPaymentBackend.mSettingsPackageMonitor.unregister();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        PaymentBackend paymentBackend = this.mPaymentBackend;
        Context context = paymentBackend.mContext;
        paymentBackend.mSettingsPackageMonitor.register(context, context.getMainLooper(), false);
        paymentBackend.refresh();
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean setDefaultKey(String str) {
        String[] split = str.split(" ");
        if (split.length >= 2) {
            this.mPaymentBackend.setDefaultPaymentApp(
                    ComponentName.unflattenFromString(split[0]), Integer.parseInt(split[1]));
        }
        return true;
    }
}
