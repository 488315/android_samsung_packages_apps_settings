package com.android.settings.applications.defaultapps;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;

import com.android.internal.content.PackageMonitor;
import com.android.settings.R;
import com.android.settingslib.applications.DefaultAppInfo;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.widget.CandidateInfo;

import com.samsung.android.settings.widget.SecDividerItemDecorator;
import com.samsung.android.settings.widget.SecUnclickablePreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DefaultAutofillPicker extends DefaultAppPickerFragment {
    public static final Intent AUTOFILL_PROBE =
            new Intent("android.service.autofill.AutofillService");
    public DefaultAutofillPicker$$ExternalSyntheticLambda0 mCancelListener;
    public final AnonymousClass1 mSettingsPackageMonitor = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.defaultapps.DefaultAutofillPicker$1, reason: invalid class name */
    public final class AnonymousClass1 extends PackageMonitor {
        public AnonymousClass1() {}

        public final void onPackageAdded(String str, int i) {
            ThreadUtils.postOnMainThread(
                    new DefaultAutofillPicker$1$$ExternalSyntheticLambda0(this, 1));
        }

        public final void onPackageModified(String str) {
            ThreadUtils.postOnMainThread(
                    new DefaultAutofillPicker$1$$ExternalSyntheticLambda0(this, 2));
        }

        public final void onPackageRemoved(String str, int i) {
            ThreadUtils.postOnMainThread(
                    new DefaultAutofillPicker$1$$ExternalSyntheticLambda0(this, 0));
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class AutofillPickerConfirmationDialogFragment
            extends DefaultAppPickerFragment.ConfirmationDialogFragment {
        @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
                  // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
        public final void onCreate(Bundle bundle) {
            this.mCancelListener = ((DefaultAutofillPicker) getTargetFragment()).mCancelListener;
            super.onCreate(bundle);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AutofillSettingIntentProvider {
        public final Context mContext;
        public final String mSelectedKey;
        public final int mUserId;

        public AutofillSettingIntentProvider(Context context, int i, String str) {
            this.mSelectedKey = str;
            this.mContext = context;
            this.mUserId = i;
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x0049, code lost:

           return null;
        */
        /* JADX WARN: Code restructure failed: missing block: B:13:0x005c, code lost:

           return new android.content.Intent("android.intent.action.MAIN").setComponent(new android.content.ComponentName(r1.packageName, r6));
        */
        /* JADX WARN: Code restructure failed: missing block: B:7:0x0038, code lost:

           r6 = new android.service.autofill.AutofillServiceInfo(r6.mContext, r1).getSettingsActivity();
        */
        /* JADX WARN: Code restructure failed: missing block: B:9:0x0047, code lost:

           if (android.text.TextUtils.isEmpty(r6) == false) goto L11;
        */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final android.content.Intent getIntent() {
            /*
                r6 = this;
                android.content.Context r0 = r6.mContext
                android.content.pm.PackageManager r0 = r0.getPackageManager()
                android.content.Intent r1 = com.android.settings.applications.defaultapps.DefaultAutofillPicker.AUTOFILL_PROBE
                r2 = 128(0x80, float:1.794E-43)
                int r3 = r6.mUserId
                java.util.List r0 = r0.queryIntentServicesAsUser(r1, r2, r3)
                java.util.Iterator r0 = r0.iterator()
            L14:
                boolean r1 = r0.hasNext()
                r2 = 0
                if (r1 == 0) goto L79
                java.lang.Object r1 = r0.next()
                android.content.pm.ResolveInfo r1 = (android.content.pm.ResolveInfo) r1
                android.content.pm.ServiceInfo r1 = r1.serviceInfo
                android.content.ComponentName r3 = new android.content.ComponentName
                java.lang.String r4 = r1.packageName
                java.lang.String r5 = r1.name
                r3.<init>(r4, r5)
                java.lang.String r3 = r3.flattenToString()
                java.lang.String r4 = r6.mSelectedKey
                boolean r3 = android.text.TextUtils.equals(r4, r3)
                if (r3 == 0) goto L14
                android.service.autofill.AutofillServiceInfo r0 = new android.service.autofill.AutofillServiceInfo     // Catch: java.lang.SecurityException -> L5d
                android.content.Context r6 = r6.mContext     // Catch: java.lang.SecurityException -> L5d
                r0.<init>(r6, r1)     // Catch: java.lang.SecurityException -> L5d
                java.lang.String r6 = r0.getSettingsActivity()     // Catch: java.lang.SecurityException -> L5d
                boolean r0 = android.text.TextUtils.isEmpty(r6)
                if (r0 == 0) goto L4a
                return r2
            L4a:
                android.content.Intent r0 = new android.content.Intent
                java.lang.String r2 = "android.intent.action.MAIN"
                r0.<init>(r2)
                android.content.ComponentName r2 = new android.content.ComponentName
                java.lang.String r1 = r1.packageName
                r2.<init>(r1, r6)
                android.content.Intent r6 = r0.setComponent(r2)
                return r6
            L5d:
                r6 = move-exception
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                java.lang.String r3 = "Error getting info for "
                r0.<init>(r3)
                r0.append(r1)
                java.lang.String r1 = ": "
                r0.append(r1)
                r0.append(r6)
                java.lang.String r6 = r0.toString()
                java.lang.String r0 = "DefaultAutofillPicker"
                android.util.Log.w(r0, r6)
            L79:
                return r2
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.settings.applications.defaultapps.DefaultAutofillPicker.AutofillSettingIntentProvider.getIntent():android.content.Intent");
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final List getCandidates() {
        ArrayList arrayList = new ArrayList();
        List<ResolveInfo> queryIntentServicesAsUser =
                this.mPm.queryIntentServicesAsUser(AUTOFILL_PROBE, 128, this.mUserId);
        Context context = getContext();
        for (ResolveInfo resolveInfo : queryIntentServicesAsUser) {
            String str = resolveInfo.serviceInfo.permission;
            if ("android.permission.BIND_AUTOFILL_SERVICE".equals(str)) {
                PackageManager packageManager = this.mPm;
                int i = this.mUserId;
                ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                arrayList.add(
                        new DefaultAppInfo(
                                context,
                                packageManager,
                                i,
                                new ComponentName(serviceInfo.packageName, serviceInfo.name)));
            }
            if ("android.permission.BIND_AUTOFILL".equals(str)) {
                Log.w(
                        "DefaultAutofillPicker",
                        "AutofillService from '"
                                + resolveInfo.serviceInfo.packageName
                                + "' uses unsupported permission android.permission.BIND_AUTOFILL."
                                + " It works for now, but might not be supported on future"
                                + " releases");
                PackageManager packageManager2 = this.mPm;
                int i2 = this.mUserId;
                ServiceInfo serviceInfo2 = resolveInfo.serviceInfo;
                arrayList.add(
                        new DefaultAppInfo(
                                context,
                                packageManager2,
                                i2,
                                new ComponentName(serviceInfo2.packageName, serviceInfo2.name)));
            }
        }
        return arrayList;
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPickerFragment
    public final CharSequence getConfirmationMessage(CandidateInfo candidateInfo) {
        if (candidateInfo == null) {
            return null;
        }
        return Html.fromHtml(
                getContext()
                        .getString(
                                R.string.autofill_confirmation_message,
                                Html.escapeHtml(candidateInfo.loadLabel())));
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPickerFragment
    public final CharSequence getConfirmationTitle(CandidateInfo candidateInfo) {
        if (candidateInfo == null) {
            return null;
        }
        return getContext().getString(R.string.sec_autofill_select_popup_title);
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final String getDefaultKey() {
        ComponentName unflattenFromString;
        Context context = getContext();
        int i = this.mUserId;
        if (context == null || context.getContentResolver() == null) {
            Log.e("DefaultAutofillPicker", "Invalid context or content-resolver");
            return null;
        }
        String stringForUser =
                Settings.Secure.getStringForUser(
                        context.getContentResolver(), "autofill_service", i);
        if (stringForUser == null
                || (unflattenFromString = ComponentName.unflattenFromString(stringForUser))
                        == null) {
            return null;
        }
        return unflattenFromString.flattenToString();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 792;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.default_autofill_settings;
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPickerFragment
    public final DefaultAppPickerFragment.ConfirmationDialogFragment newConfirmationDialogFragment(
            CharSequence charSequence, CharSequence charSequence2, String str) {
        AutofillPickerConfirmationDialogFragment autofillPickerConfirmationDialogFragment =
                new AutofillPickerConfirmationDialogFragment();
        autofillPickerConfirmationDialogFragment.init(this, str, charSequence, charSequence2);
        return autofillPickerConfirmationDialogFragment;
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settings.applications.defaultapps.DefaultAutofillPicker$$ExternalSyntheticLambda0] */
    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        final FragmentActivity activity = getActivity();
        if (activity != null && activity.getIntent().getStringExtra("package_name") != null) {
            this.mCancelListener =
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.android.settings.applications.defaultapps.DefaultAutofillPicker$$ExternalSyntheticLambda0
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            Activity activity2 = activity;
                            Intent intent = DefaultAutofillPicker.AUTOFILL_PROBE;
                            activity2.setResult(0);
                            activity2.finish();
                        }
                    };
            this.mUserId = UserHandle.myUserId();
        }
        this.mSettingsPackageMonitor.register(activity, activity.getMainLooper(), false);
        update$6$1();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        this.mSettingsPackageMonitor.unregister();
        super.onDestroy();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setDivider(null);
        getListView()
                .addItemDecoration(
                        new SecDividerItemDecorator(
                                (int)
                                        (getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_with_checkbox_margin_end)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_with_checkbox_size)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_with_checkbox_margin_start)),
                                getContext(),
                                getResources().getDrawable(R.drawable.sec_top_level_list_divider)));
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean setDefaultKey(String str) {
        String stringExtra;
        Settings.Secure.putStringForUser(
                getContext().getContentResolver(), "autofill_service", str, this.mUserId);
        FragmentActivity activity = getActivity();
        if (activity == null
                || (stringExtra = activity.getIntent().getStringExtra("package_name")) == null) {
            return true;
        }
        activity.setResult((str == null || !str.startsWith(stringExtra)) ? 0 : -1);
        activity.finish();
        return true;
    }

    public final void update$6$1() {
        Preference preference;
        updateCandidates();
        String stringForUser =
                Settings.Secure.getStringForUser(
                        getActivity().getContentResolver(),
                        "autofill_service_search_uri",
                        this.mUserId);
        if (TextUtils.isEmpty(stringForUser)) {
            preference = null;
        } else {
            final Intent intent =
                    new Intent("android.intent.action.VIEW", Uri.parse(stringForUser));
            final Context prefContext = getPrefContext();
            preference = new Preference(prefContext);
            preference.setOnPreferenceClickListener(
                    new Preference
                            .OnPreferenceClickListener() { // from class:
                                                           // com.android.settings.applications.defaultapps.DefaultAutofillPicker$$ExternalSyntheticLambda1
                        @Override // androidx.preference.Preference.OnPreferenceClickListener
                        public final boolean onPreferenceClick(Preference preference2) {
                            Context context = prefContext;
                            Intent intent2 = intent;
                            Intent intent3 = DefaultAutofillPicker.AUTOFILL_PROBE;
                            context.startActivityAsUser(
                                    intent2, UserHandle.of(DefaultAutofillPicker.this.mUserId));
                            return true;
                        }
                    });
            preference.setTitle(R.string.sec_autofill_add_service);
            preference.setLayoutResource(R.layout.sec_preference_radio);
            preference.setWidgetLayoutResource(R.layout.sec_preference_widget_add);
            preference.setIconSpaceReserved(false);
            preference.setOrder(2147483646);
            preference.setPersistent();
        }
        if (preference != null) {
            getPreferenceScreen().addPreference(preference);
        }
        SecUnclickablePreference secUnclickablePreference =
                new SecUnclickablePreference(getPrefContext(), null);
        secUnclickablePreference.setTitle(R.string.sec_default_autofill_picker_description);
        secUnclickablePreference.setKey("description");
        secUnclickablePreference.setOrder(-1);
        secUnclickablePreference.mPositionMode = 1;
        getPreferenceScreen().addPreference(secUnclickablePreference);
    }
}
