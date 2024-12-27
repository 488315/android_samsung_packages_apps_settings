package com.android.settings.datausage;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.NetworkPolicy;
import android.net.NetworkTemplate;
import android.os.Bundle;
import android.telephony.SubscriptionManager;
import android.text.format.Formatter;
import android.text.method.NumberKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeatureImpl;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.NetworkPolicyEditor;
import com.android.settingslib.net.DataUsageController;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import kotlin.jvm.internal.Intrinsics;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.ZoneId;
import java.util.Optional;
import java.util.TimeZone;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BillingCycleSettings extends DataUsageBaseFragment
        implements Preference.OnPreferenceChangeListener, DataUsageEditController {
    static final String KEY_SET_DATA_LIMIT = "set_data_limit";
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.billing_cycle);
    public Preference mBillingCycle;
    public Preference mDataLimit;
    public DataUsageController mDataUsageController;
    public Preference mDataWarning;
    public TwoStatePreference mEnableDataLimit;
    public TwoStatePreference mEnableDataWarning;
    NetworkTemplate mNetworkTemplate;
    public EnterpriseDeviceManager mEDM = null;
    public RestrictionPolicy mRestrictionPolicy = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.datausage.BillingCycleSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            if (Rune.SUPPORT_SMARTMANAGER_CN
                    || MobileNetworkUtils.isMobileNetworkUserRestricted(context)) {
                return false;
            }
            Pattern pattern = SubscriptionUtil.REGEX_DISPLAY_NAME_SUFFIX_PATTERN;
            return context.getResources().getBoolean(R.bool.config_show_sim_info)
                    && DataUsageUtils.hasMobileData(context);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class BytesEditorFragment extends InstrumentedDialogFragment
            implements DialogInterface.OnClickListener {
        public View mView;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.datausage.BillingCycleSettings$BytesEditorFragment$1, reason: invalid class name */
        public final class AnonymousClass1 extends NumberKeyListener {
            @Override // android.text.method.NumberKeyListener
            public final char[] getAcceptedChars() {
                return new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ',', '.'};
            }

            @Override // android.text.method.KeyListener
            public final int getInputType() {
                return 8194;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static void show(DataUsageEditController dataUsageEditController, boolean z) {
            if (dataUsageEditController instanceof Fragment) {
                Fragment fragment = (Fragment) dataUsageEditController;
                if (fragment.isAdded()) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(
                            "template",
                            ((BillingCycleSettings) dataUsageEditController).mNetworkTemplate);
                    bundle.putBoolean("limit", z);
                    BytesEditorFragment bytesEditorFragment = new BytesEditorFragment();
                    bytesEditorFragment.setArguments(bundle);
                    bytesEditorFragment.setTargetFragment(fragment, 0);
                    bytesEditorFragment.show(fragment.getFragmentManager(), "warningEditor");
                }
            }
        }

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return SfpsEnrollmentFeatureImpl.HELP_ANIMATOR_DURATION;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            Number number;
            long j;
            if (i != -1) {
                return;
            }
            DataUsageEditController dataUsageEditController =
                    (DataUsageEditController) getTargetFragment();
            NetworkPolicyEditor networkPolicyEditor =
                    ((BillingCycleSettings) dataUsageEditController).services.mPolicyEditor;
            NetworkTemplate networkTemplate =
                    (NetworkTemplate) getArguments().getParcelable("template");
            boolean z = getArguments().getBoolean("limit");
            EditText editText = (EditText) this.mView.findViewById(R.id.bytes);
            Spinner spinner = (Spinner) this.mView.findViewById(R.id.size_spinner);
            try {
                number = NumberFormat.getNumberInstance().parse(editText.getText().toString());
            } catch (ParseException unused) {
                number = null;
            }
            if (number != null) {
                j =
                        (long)
                                (number.floatValue()
                                        * (spinner.getSelectedItemPosition() == 0
                                                ? 1048576L
                                                : 1073741824L));
            } else {
                j = 0;
            }
            long min = Math.min(53687091200000L, j);
            if (z) {
                networkPolicyEditor.setPolicyLimitBytes(networkTemplate, min);
            } else {
                networkPolicyEditor.setPolicyWarningBytes(networkTemplate, min);
            }
            ((BillingCycleSettings) dataUsageEditController).updatePrefs();
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            FragmentActivity activity = getActivity();
            LayoutInflater from = LayoutInflater.from(activity);
            boolean z = getArguments().getBoolean("limit");
            View inflate = from.inflate(R.layout.data_usage_bytes_editor, (ViewGroup) null, false);
            this.mView = inflate;
            EditText editText = (EditText) inflate.findViewById(R.id.bytes);
            Spinner spinner = (Spinner) this.mView.findViewById(R.id.size_spinner);
            NetworkPolicyEditor networkPolicyEditor =
                    ((BillingCycleSettings) ((DataUsageEditController) getTargetFragment()))
                            .services
                            .mPolicyEditor;
            editText.setKeyListener(new AnonymousClass1());
            NetworkTemplate networkTemplate =
                    (NetworkTemplate) getArguments().getParcelable("template");
            long j = -1;
            if (getArguments().getBoolean("limit")) {
                NetworkPolicy policy = networkPolicyEditor.getPolicy(networkTemplate);
                if (policy != null) {
                    j = policy.limitBytes;
                }
            } else {
                NetworkPolicy policy2 = networkPolicyEditor.getPolicy(networkTemplate);
                if (policy2 != null) {
                    j = policy2.warningBytes;
                }
            }
            Resources resources = getResources();
            Intrinsics.checkNotNullParameter(resources, "<this>");
            String units = Formatter.formatBytes(resources, 1048576L, 8).units;
            Intrinsics.checkNotNullExpressionValue(units, "units");
            Resources resources2 = getResources();
            Intrinsics.checkNotNullParameter(resources2, "<this>");
            String units2 = Formatter.formatBytes(resources2, 1073741824L, 8).units;
            Intrinsics.checkNotNullExpressionValue(units2, "units");
            spinner.setAdapter(
                    (SpinnerAdapter)
                            new ArrayAdapter(
                                    getContext(),
                                    android.R.layout.simple_spinner_item,
                                    new String[] {units, units2}));
            int i = ((float) j) > 1.61061274E9f ? 1 : 0;
            double d = j;
            double d2 = i != 0 ? 1.073741824E9d : 1048576.0d;
            NumberFormat numberInstance = NumberFormat.getNumberInstance();
            numberInstance.setMaximumFractionDigits(2);
            String format = numberInstance.format(d / d2);
            editText.setText(format);
            editText.setSelection(0, format.length());
            spinner.setSelection(i);
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(
                    z
                            ? R.string.data_usage_limit_editor_title
                            : R.string.data_usage_warning_editor_title);
            builder.setView(this.mView);
            builder.setPositiveButton(R.string.data_usage_cycle_editor_positive, this);
            AlertDialog create = builder.create();
            create.setCanceledOnTouchOutside(false);
            return create;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class ConfirmLimitFragment extends InstrumentedDialogFragment
            implements DialogInterface.OnClickListener {
        static final String EXTRA_LIMIT_BYTES = "limitBytes";

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 551;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            BillingCycleSettings billingCycleSettings = (BillingCycleSettings) getTargetFragment();
            if (i != -1) {
                return;
            }
            long j = getArguments().getLong(EXTRA_LIMIT_BYTES);
            if (billingCycleSettings != null) {
                billingCycleSettings.setPolicyLimitBytes(j);
            }
            billingCycleSettings
                    .getPreferenceManager()
                    .getSharedPreferences()
                    .edit()
                    .putBoolean(BillingCycleSettings.KEY_SET_DATA_LIMIT, true)
                    .apply();
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.data_usage_limit_dialog_title);
            builder.setMessage(R.string.data_usage_limit_dialog_mobile);
            builder.setPositiveButton(android.R.string.ok, this);
            builder.setNegativeButton(
                    android.R.string.cancel, (DialogInterface.OnClickListener) null);
            AlertDialog create = builder.create();
            create.setCanceledOnTouchOutside(false);
            return create;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class CycleEditorFragment extends InstrumentedDialogFragment
            implements DialogInterface.OnClickListener {
        public NumberPicker mCycleDayPicker;

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 549;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            NetworkTemplate parcelable = getArguments().getParcelable("template");
            BillingCycleSettings billingCycleSettings =
                    (BillingCycleSettings) ((DataUsageEditController) getTargetFragment());
            NetworkPolicyEditor networkPolicyEditor = billingCycleSettings.services.mPolicyEditor;
            this.mCycleDayPicker.clearFocus();
            int value = this.mCycleDayPicker.getValue();
            String id = TimeZone.getDefault().getID();
            NetworkPolicy orCreatePolicy = networkPolicyEditor.getOrCreatePolicy(parcelable);
            orCreatePolicy.cycleRule = NetworkPolicy.buildRule(value, ZoneId.of(id));
            orCreatePolicy.inferred = false;
            orCreatePolicy.clearSnooze();
            networkPolicyEditor.writeAsync();
            billingCycleSettings.updatePrefs();
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            FragmentActivity activity = getActivity();
            NetworkPolicyEditor networkPolicyEditor =
                    ((BillingCycleSettings) ((DataUsageEditController) getTargetFragment()))
                            .services
                            .mPolicyEditor;
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            View inflate =
                    LayoutInflater.from(builder.P.mContext)
                            .inflate(R.layout.data_usage_cycle_editor, (ViewGroup) null, false);
            this.mCycleDayPicker = (NumberPicker) inflate.findViewById(R.id.cycle_day);
            int policyCycleDay =
                    networkPolicyEditor.getPolicyCycleDay(
                            (NetworkTemplate) getArguments().getParcelable("template"));
            this.mCycleDayPicker.setMinValue(1);
            this.mCycleDayPicker.setMaxValue(31);
            this.mCycleDayPicker.setValue(policyCycleDay);
            this.mCycleDayPicker.setWrapSelectorWheel(true);
            builder.setTitle(R.string.data_usage_cycle_editor_title);
            builder.setView(inflate);
            builder.setPositiveButton(R.string.data_usage_cycle_editor_positive, this);
            AlertDialog create = builder.create();
            create.setCanceledOnTouchOutside(false);
            return create;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "BillingCycleSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return FileType.PRN;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.billing_cycle;
    }

    @Override // com.android.settings.datausage.DataUsageBaseFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context context = getContext();
        if (!SubscriptionUtil.isSimHardwareVisible(context)) {
            finish();
            return;
        }
        this.mDataUsageController = new DataUsageController(context);
        NetworkTemplate parcelable = getArguments().getParcelable("network_template");
        this.mNetworkTemplate = parcelable;
        if (parcelable == null && getIntent() != null) {
            this.mNetworkTemplate = getIntent().getParcelableExtra("network_template");
        }
        if (this.mNetworkTemplate == null) {
            Optional mobileNetworkTemplateFromSubId =
                    DataUsageUtils.getMobileNetworkTemplateFromSubId(context, getIntent());
            if (mobileNetworkTemplateFromSubId.isPresent()) {
                this.mNetworkTemplate = (NetworkTemplate) mobileNetworkTemplateFromSubId.get();
            }
        }
        if (this.mNetworkTemplate == null) {
            NetworkTemplate defaultTemplate =
                    DataUsageUtils.getDefaultTemplate(
                            context, SubscriptionManager.getDefaultDataSubscriptionId());
            Intrinsics.checkNotNullExpressionValue(defaultTemplate, "getDefaultTemplate(...)");
            this.mNetworkTemplate = defaultTemplate;
        }
        this.mBillingCycle = findPreference("billing_cycle");
        TwoStatePreference twoStatePreference =
                (TwoStatePreference) findPreference("set_data_warning");
        this.mEnableDataWarning = twoStatePreference;
        twoStatePreference.setOnPreferenceChangeListener(this);
        this.mDataWarning = findPreference("data_warning");
        TwoStatePreference twoStatePreference2 =
                (TwoStatePreference) findPreference(KEY_SET_DATA_LIMIT);
        this.mEnableDataLimit = twoStatePreference2;
        twoStatePreference2.setOnPreferenceChangeListener(this);
        this.mDataLimit = findPreference("data_limit");
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(context);
        }
        EnterpriseDeviceManager enterpriseDeviceManager = this.mEDM;
        if (enterpriseDeviceManager != null) {
            this.mRestrictionPolicy = enterpriseDeviceManager.getRestrictionPolicy();
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        NetworkPolicy policy;
        if (this.mEnableDataLimit != preference) {
            if (this.mEnableDataWarning != preference) {
                return false;
            }
            if (((Boolean) obj).booleanValue()) {
                this.services.mPolicyEditor.setPolicyWarningBytes(
                        this.mNetworkTemplate,
                        this.mDataUsageController
                                        .mContext
                                        .getResources()
                                        .getInteger(
                                                android.R.integer.lock_pattern_line_fade_out_delay)
                                * 1048576);
                updatePrefs();
            } else {
                this.services.mPolicyEditor.setPolicyWarningBytes(this.mNetworkTemplate, -1L);
                updatePrefs();
            }
            return true;
        }
        if (!((Boolean) obj).booleanValue()) {
            setPolicyLimitBytes(-1L);
            return true;
        }
        if (isAdded()
                && (policy = this.services.mPolicyEditor.getPolicy(this.mNetworkTemplate))
                        != null) {
            getResources();
            long max = Math.max(5368709120L, (long) (policy.warningBytes * 1.2f));
            Bundle bundle = new Bundle();
            bundle.putLong("limitBytes", max);
            ConfirmLimitFragment confirmLimitFragment = new ConfirmLimitFragment();
            confirmLimitFragment.setArguments(bundle);
            confirmLimitFragment.setTargetFragment(this, 0);
            confirmLimitFragment.show(getFragmentManager(), "confirmLimit");
        }
        return false;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (preference == this.mBillingCycle) {
            writePreferenceClickMetric(preference);
            if (isAdded()) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("template", this.mNetworkTemplate);
                CycleEditorFragment cycleEditorFragment = new CycleEditorFragment();
                cycleEditorFragment.setArguments(bundle);
                cycleEditorFragment.setTargetFragment(this, 0);
                cycleEditorFragment.show(getFragmentManager(), "cycleEditor");
            }
            return true;
        }
        if (preference == this.mDataWarning) {
            writePreferenceClickMetric(preference);
            BytesEditorFragment.show((DataUsageEditController) this, false);
            return true;
        }
        if (preference != this.mDataLimit) {
            return super.onPreferenceTreeClick(preference);
        }
        writePreferenceClickMetric(preference);
        BytesEditorFragment.show((DataUsageEditController) this, true);
        return true;
    }

    @Override // com.android.settings.datausage.DataUsageBaseFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        updatePrefs();
    }

    public void setPolicyLimitBytes(long j) {
        this.services.mPolicyEditor.setPolicyLimitBytes(this.mNetworkTemplate, j);
        updatePrefs();
    }

    public void setUpForTest(
            NetworkPolicyEditor networkPolicyEditor,
            Preference preference,
            Preference preference2,
            Preference preference3,
            TwoStatePreference twoStatePreference,
            TwoStatePreference twoStatePreference2) {
        this.services.mPolicyEditor = networkPolicyEditor;
        this.mBillingCycle = preference;
        this.mDataLimit = preference2;
        this.mDataWarning = preference3;
        this.mEnableDataLimit = twoStatePreference;
        this.mEnableDataWarning = twoStatePreference2;
    }

    public void updatePrefs() {
        this.mBillingCycle.setSummary((CharSequence) null);
        NetworkPolicy policy = this.services.mPolicyEditor.getPolicy(this.mNetworkTemplate);
        long j = policy != null ? policy.warningBytes : -1L;
        if (j != -1) {
            this.mDataWarning.setSummary(DataUsageUtils.formatDataUsage(getContext(), j));
            this.mDataWarning.setEnabled(true);
            this.mEnableDataWarning.setChecked(true);
        } else {
            this.mDataWarning.setSummary((CharSequence) null);
            this.mDataWarning.setEnabled(false);
            this.mEnableDataWarning.setChecked(false);
        }
        RestrictionPolicy restrictionPolicy = this.mRestrictionPolicy;
        if (restrictionPolicy != null && !restrictionPolicy.isUserMobileDataLimitAllowed()) {
            this.mEnableDataLimit.setEnabled(false);
            this.mEnableDataLimit.setChecked(false);
            this.mDataLimit.setEnabled(false);
            return;
        }
        this.mEnableDataLimit.setEnabled(true);
        NetworkPolicy policy2 = this.services.mPolicyEditor.getPolicy(this.mNetworkTemplate);
        long j2 = policy2 != null ? policy2.limitBytes : -1L;
        if (j2 != -1) {
            this.mDataLimit.setSummary(DataUsageUtils.formatDataUsage(getContext(), j2));
            this.mDataLimit.setEnabled(true);
            this.mEnableDataLimit.setChecked(true);
        } else {
            this.mDataLimit.setSummary((CharSequence) null);
            this.mDataLimit.setEnabled(false);
            this.mEnableDataLimit.setChecked(false);
        }
    }
}
