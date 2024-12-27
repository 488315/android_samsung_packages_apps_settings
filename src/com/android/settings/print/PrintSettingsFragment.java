package com.android.settings.print;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.print.PrintJob;
import android.print.PrintJobId;
import android.print.PrintJobInfo;
import android.print.PrintManager;
import android.printservice.PrintServiceInfo;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.IconDrawableFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreferenceScreen;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.print.PrintSettingsFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.connection.WirelessSettings;
import com.samsung.android.settings.voiceinput.Constants;
import com.samsung.android.settings.widget.SecDividerItemDecorator;
import com.samsung.android.settings.widget.SecRestrictedPreference;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrintSettingsFragment extends ProfileSettingsPreferenceFragment implements View.OnClickListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new BaseSearchIndexProvider(R.xml.print_settings);
    public PreferenceCategory mActivePrintJobsCategory;
    public Button mAddNewServiceButton;
    public SecRestrictedPreference mAddNewServicePreference;
    public View mEmptyView;
    boolean mIsUiRestricted;
    public PrintJobsController mPrintJobsController;
    public PreferenceCategory mPrintServicesCategory;
    public PrintJobsController mPrintServicesController;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PrintJobsController implements LoaderManager.LoaderCallbacks {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ PrintSettingsFragment this$0;

        public /* synthetic */ PrintJobsController(PrintSettingsFragment printSettingsFragment, int i) {
            this.$r8$classId = i;
            this.this$0 = printSettingsFragment;
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final Loader onCreateLoader(int i, Bundle bundle) {
            switch (this.$r8$classId) {
                case 0:
                    if (i == 1) {
                        return new PrintJobsLoader(this.this$0.getContext());
                    }
                    return null;
                default:
                    PrintSettingsFragment printSettingsFragment = this.this$0;
                    PrintManager printManager = (PrintManager) printSettingsFragment.getContext().getSystemService("print");
                    if (printManager != null) {
                        return new SettingsPrintServicesLoader(printManager, printSettingsFragment.getContext());
                    }
                    return null;
            }
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final void onLoadFinished(Loader loader, Object obj) {
            switch (this.$r8$classId) {
                case 0:
                    List<PrintJobInfo> list = (List) obj;
                    PrintSettingsFragment printSettingsFragment = this.this$0;
                    if (list != null && !list.isEmpty()) {
                        if (printSettingsFragment.getPreferenceScreen().findPreference("print_jobs_category") == null) {
                            printSettingsFragment.getPreferenceScreen().addPreference(printSettingsFragment.mActivePrintJobsCategory);
                        }
                        printSettingsFragment.mActivePrintJobsCategory.removeAll();
                        Context prefContext = printSettingsFragment.getPrefContext();
                        if (prefContext == null) {
                            Log.w("PrintSettingsFragment", "No preference context, skip adding print jobs");
                            break;
                        } else {
                            for (PrintJobInfo printJobInfo : list) {
                                Preference preference = new Preference(prefContext);
                                preference.setPersistent();
                                preference.setFragment(PrintJobSettingsFragment.class.getName());
                                preference.setKey(printJobInfo.getId().flattenToString());
                                int state = printJobInfo.getState();
                                if (state == 2 || state == 3) {
                                    if (printJobInfo.isCancelling()) {
                                        preference.setTitle(printSettingsFragment.getString(R.string.print_cancelling_state_title_template, printJobInfo.getLabel()));
                                    } else {
                                        preference.setTitle(printSettingsFragment.getString(R.string.print_printing_state_title_template, printJobInfo.getLabel()));
                                    }
                                } else if (state != 4) {
                                    if (state == 6) {
                                        preference.setTitle(printSettingsFragment.getString(R.string.print_failed_state_title_template, printJobInfo.getLabel()));
                                    }
                                } else if (printJobInfo.isCancelling()) {
                                    preference.setTitle(printSettingsFragment.getString(R.string.print_cancelling_state_title_template, printJobInfo.getLabel()));
                                } else {
                                    preference.setTitle(printSettingsFragment.getString(R.string.print_blocked_state_title_template, printJobInfo.getLabel()));
                                }
                                preference.setSummary(printSettingsFragment.getString(R.string.print_job_summary, printJobInfo.getPrinterName(), DateUtils.formatSameDayTime(printJobInfo.getCreationTime(), printJobInfo.getCreationTime(), 3, 3)));
                                TypedArray obtainStyledAttributes = printSettingsFragment.getActivity().obtainStyledAttributes(new int[]{android.R.attr.colorControlNormal});
                                int color = obtainStyledAttributes.getColor(0, 0);
                                obtainStyledAttributes.recycle();
                                int state2 = printJobInfo.getState();
                                if (state2 == 2 || state2 == 3) {
                                    Drawable drawable = printSettingsFragment.getActivity().getDrawable(android.R.drawable.ic_signal_wifi_transient_animation_drawable);
                                    drawable.setTint(color);
                                    preference.setIcon(drawable);
                                } else if (state2 == 4 || state2 == 6) {
                                    Drawable drawable2 = printSettingsFragment.getActivity().getDrawable(android.R.drawable.ic_sim_card_multi_24px_clr);
                                    drawable2.setTint(color);
                                    preference.setIcon(drawable2);
                                }
                                preference.getExtras().putString("EXTRA_PRINT_JOB_ID", printJobInfo.getId().flattenToString());
                                printSettingsFragment.mActivePrintJobsCategory.addPreference(preference);
                            }
                            break;
                        }
                    } else {
                        printSettingsFragment.getPreferenceScreen().removePreference(printSettingsFragment.mActivePrintJobsCategory);
                        break;
                    }
                    break;
                default:
                    List<PrintServiceInfo> list2 = (List) obj;
                    boolean isEmpty = list2.isEmpty();
                    PrintSettingsFragment printSettingsFragment2 = this.this$0;
                    if (isEmpty) {
                        printSettingsFragment2.getPreferenceScreen().removePreference(printSettingsFragment2.mPrintServicesCategory);
                        if (printSettingsFragment2.mAddNewServicePreference != null) {
                            printSettingsFragment2.getPreferenceScreen().removePreference(printSettingsFragment2.mAddNewServicePreference);
                            break;
                        }
                    } else {
                        if (printSettingsFragment2.getPreferenceScreen().findPreference("print_services_category") == null) {
                            printSettingsFragment2.getPreferenceScreen().addPreference(printSettingsFragment2.mPrintServicesCategory);
                            if (printSettingsFragment2.getPreferenceScreen().findPreference("print_add_plug_in") == null && printSettingsFragment2.mAddNewServicePreference != null) {
                                printSettingsFragment2.getPreferenceScreen().addPreference(printSettingsFragment2.mAddNewServicePreference);
                            }
                        }
                        printSettingsFragment2.mPrintServicesCategory.removeAll();
                        PackageManager packageManager = printSettingsFragment2.getActivity().getPackageManager();
                        Context prefContext2 = printSettingsFragment2.getPrefContext();
                        if (prefContext2 == null) {
                            Log.w("PrintSettingsFragment", "No preference context, skip adding print services");
                            break;
                        } else {
                            for (PrintServiceInfo printServiceInfo : list2) {
                                SecSwitchPreferenceScreen secSwitchPreferenceScreen = new SecSwitchPreferenceScreen(prefContext2);
                                String charSequence = printServiceInfo.getResolveInfo().loadLabel(packageManager).toString();
                                secSwitchPreferenceScreen.setTitle(charSequence);
                                ComponentName componentName = printServiceInfo.getComponentName();
                                secSwitchPreferenceScreen.setKey(componentName.flattenToString());
                                secSwitchPreferenceScreen.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: com.android.settings.print.PrintSettingsFragment$PrintServicesController$1
                                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                                    public final boolean onPreferenceChange(Preference preference2, Object obj2) {
                                        Context context = PrintSettingsFragment.PrintJobsController.this.this$0.getContext();
                                        BaseSearchIndexProvider baseSearchIndexProvider = PrintSettingsFragment.SEARCH_INDEX_DATA_PROVIDER;
                                        ((PrintManager) context.getSystemService("print")).setPrintServiceEnabled(ComponentName.unflattenFromString(preference2.getKey()), ((Boolean) obj2).booleanValue());
                                        return true;
                                    }
                                });
                                secSwitchPreferenceScreen.setFragment(PrintServiceSettingsFragment.class.getName());
                                secSwitchPreferenceScreen.setPersistent();
                                SecPreferenceUtils.applySummaryColor(secSwitchPreferenceScreen, true);
                                if (printServiceInfo.isEnabled()) {
                                    secSwitchPreferenceScreen.setChecked(true);
                                    secSwitchPreferenceScreen.setSummary(printSettingsFragment2.getString(R.string.print_feature_state_on));
                                } else {
                                    secSwitchPreferenceScreen.setChecked(false);
                                    secSwitchPreferenceScreen.setSummary(printSettingsFragment2.getString(R.string.print_feature_state_off));
                                }
                                if (printServiceInfo.getResolveInfo().loadIcon(packageManager) != null) {
                                    secSwitchPreferenceScreen.setIcon(IconDrawableFactory.newInstance(prefContext2).getBadgedIcon(printServiceInfo.getResolveInfo().serviceInfo.applicationInfo));
                                }
                                Bundle extras = secSwitchPreferenceScreen.getExtras();
                                extras.putBoolean("EXTRA_CHECKED", printServiceInfo.isEnabled());
                                extras.putString("EXTRA_TITLE", charSequence);
                                extras.putString("EXTRA_SERVICE_COMPONENT_NAME", componentName.flattenToString());
                                printSettingsFragment2.mPrintServicesCategory.addPreference(secSwitchPreferenceScreen);
                            }
                            break;
                        }
                    }
                    break;
            }
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final void onLoaderReset(Loader loader) {
            switch (this.$r8$classId) {
                case 0:
                    PrintSettingsFragment printSettingsFragment = this.this$0;
                    printSettingsFragment.getPreferenceScreen().removePreference(printSettingsFragment.mActivePrintJobsCategory);
                    break;
                default:
                    PrintSettingsFragment printSettingsFragment2 = this.this$0;
                    printSettingsFragment2.getPreferenceScreen().removePreference(printSettingsFragment2.mPrintServicesCategory);
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PrintJobsLoader extends AsyncTaskLoader {
        public AnonymousClass1 mPrintJobStateChangeListener;
        public final List mPrintJobs;
        public final PrintManager mPrintManager;

        public PrintJobsLoader(Context context) {
            super(context);
            this.mPrintJobs = new ArrayList();
            this.mPrintManager = ((PrintManager) context.getSystemService("print")).getGlobalPrintManagerForUser(context.getUserId());
        }

        @Override // androidx.loader.content.Loader
        public final void deliverResult(Object obj) {
            List list = (List) obj;
            if (this.mStarted) {
                super.deliverResult(list);
            }
        }

        @Override // androidx.loader.content.AsyncTaskLoader
        public final Object loadInBackground() {
            List<PrintJob> printJobs = this.mPrintManager.getPrintJobs();
            int size = printJobs.size();
            ArrayList arrayList = null;
            for (int i = 0; i < size; i++) {
                PrintJobInfo info = printJobs.get(i).getInfo();
                if (PrintSettingPreferenceController.shouldShowToUser(info)) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(info);
                }
            }
            return arrayList;
        }

        @Override // androidx.loader.content.Loader
        public final void onReset() {
            onCancelLoad();
            ((ArrayList) this.mPrintJobs).clear();
            AnonymousClass1 anonymousClass1 = this.mPrintJobStateChangeListener;
            if (anonymousClass1 != null) {
                this.mPrintManager.removePrintJobStateChangeListener(anonymousClass1);
                this.mPrintJobStateChangeListener = null;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v7, types: [android.print.PrintManager$PrintJobStateChangeListener, com.android.settings.print.PrintSettingsFragment$PrintJobsLoader$1] */
        @Override // androidx.loader.content.Loader
        public final void onStartLoading() {
            if (!((ArrayList) this.mPrintJobs).isEmpty()) {
                ArrayList arrayList = new ArrayList(this.mPrintJobs);
                if (this.mStarted) {
                    super.deliverResult(arrayList);
                }
            }
            if (this.mPrintJobStateChangeListener == null) {
                ?? r0 = new PrintManager.PrintJobStateChangeListener() { // from class: com.android.settings.print.PrintSettingsFragment.PrintJobsLoader.1
                    public final void onPrintJobStateChanged(PrintJobId printJobId) {
                        PrintJobsLoader.this.onForceLoad();
                    }
                };
                this.mPrintJobStateChangeListener = r0;
                this.mPrintManager.addPrintJobStateChangeListener(r0);
            }
            if (((ArrayList) this.mPrintJobs).isEmpty()) {
                onForceLoad();
            }
        }

        @Override // androidx.loader.content.Loader
        public final void onStopLoading() {
            onCancelLoad();
        }
    }

    public PrintSettingsFragment() {
        super("no_printing");
    }

    public final Intent createAddNewServiceIntentOrNull() {
        if (Rune.isChinaModel()) {
            return null;
        }
        String string = Settings.Secure.getString(getContentResolver(), "print_service_search_uri");
        return TextUtils.isEmpty(string) ? new Intent("android.intent.action.VIEW", Uri.parse("market://search?q=print service plugin")) : new Intent("android.intent.action.VIEW", Uri.parse(string));
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.print_settings;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return WirelessSettings.class.getName();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "PrintSettingsFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 80;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.print_settings;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_connections";
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        Intent createAddNewServiceIntentOrNull;
        if (this.mAddNewServiceButton != view || (createAddNewServiceIntentOrNull = createAddNewServiceIntentOrNull()) == null) {
            return;
        }
        try {
            startActivity(createAddNewServiceIntentOrNull);
        } catch (ActivityNotFoundException e) {
            Log.w("PrintSettingsFragment", "Unable to start activity", e);
        }
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment, com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mIsUiRestricted = isUiRestricted();
        setupPreferences();
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment, com.android.settings.SettingsPreferenceFragment
    public final void onDataSetChanged() {
        super.onDataSetChanged();
        View view = this.mEmptyView;
        if (view != null) {
            if (view.getVisibility() == 0) {
                getListView().setVisibility(8);
            } else {
                getListView().setVisibility(0);
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        startSettings();
    }

    @Override // com.android.settings.print.ProfileSettingsPreferenceFragment, com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setupEmptyViews();
    }

    public void setupEmptyViews() {
        if (this.mIsUiRestricted) {
            return;
        }
        ViewGroup viewGroup = (ViewGroup) getListView().getParent();
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.empty_print_state, viewGroup, false);
        this.mEmptyView = inflate;
        ((TextView) inflate.findViewById(R.id.message)).setText(R.string.print_no_services_installed);
        if (createAddNewServiceIntentOrNull() != null) {
            Button button = (Button) this.mEmptyView.findViewById(R.id.add_new_service);
            this.mAddNewServiceButton = button;
            button.setOnClickListener(this);
            this.mAddNewServiceButton.setVisibility(0);
        }
        TextView textView = (TextView) this.mEmptyView.findViewById(R.id.description);
        if (Rune.isChinaModel()) {
            if (Utils.hasPackage(getContext(), Constants.GALAXY_STORE_PACKAGE_NAME)) {
                textView.setText(R.string.print_install_print_services_dialog_message_1);
            } else {
                textView.setText(R.string.print_install_print_services_dialog_message_2);
            }
            textView.setVisibility(0);
        }
        viewGroup.addView(this.mEmptyView);
        setEmptyView(this.mEmptyView);
        setDivider(null);
        getListView().addItemDecoration(new SecDividerItemDecorator((int) (getResources().getDimension(R.dimen.sec_app_list_item_icon_min_width) + getResources().getDimension(R.dimen.sec_widget_list_item_padding_start)), getContext(), getResources().getDrawable(R.drawable.sec_top_level_list_divider)));
    }

    public void setupPreferences() {
        SecRestrictedPreference secRestrictedPreference;
        if (this.mIsUiRestricted) {
            return;
        }
        this.mActivePrintJobsCategory = (PreferenceCategory) findPreference("print_jobs_category");
        this.mPrintServicesCategory = (PreferenceCategory) findPreference("print_services_category");
        getPreferenceScreen().removePreference(this.mActivePrintJobsCategory);
        if (createAddNewServiceIntentOrNull() == null) {
            secRestrictedPreference = null;
        } else {
            secRestrictedPreference = new SecRestrictedPreference(getPrefContext());
            secRestrictedPreference.setTitle(R.string.print_menu_item_add_service);
            secRestrictedPreference.setIcon(R.drawable.sec_tw_list_icon_create_mtrl);
            secRestrictedPreference.setOrder(1073741822);
            secRestrictedPreference.setKey("print_add_plug_in");
            secRestrictedPreference.setPersistent();
            secRestrictedPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: com.android.settings.print.PrintSettingsFragment.1
                public long mLastClickTime;

                @Override // androidx.preference.Preference.OnPreferenceClickListener
                public final boolean onPreferenceClick(Preference preference) {
                    if (SystemClock.elapsedRealtime() - this.mLastClickTime < 1000) {
                        return false;
                    }
                    this.mLastClickTime = SystemClock.elapsedRealtime();
                    BaseSearchIndexProvider baseSearchIndexProvider = PrintSettingsFragment.SEARCH_INDEX_DATA_PROVIDER;
                    PrintSettingsFragment printSettingsFragment = PrintSettingsFragment.this;
                    Intent createAddNewServiceIntentOrNull = printSettingsFragment.createAddNewServiceIntentOrNull();
                    if (createAddNewServiceIntentOrNull != null) {
                        try {
                            printSettingsFragment.startActivity(createAddNewServiceIntentOrNull);
                        } catch (ActivityNotFoundException e) {
                            Log.w("PrintSettingsFragment", "Unable to start activity", e);
                        }
                    }
                    return false;
                }
            });
        }
        this.mAddNewServicePreference = secRestrictedPreference;
        if (secRestrictedPreference != null) {
            getPreferenceScreen().addPreference(this.mAddNewServicePreference);
        }
        this.mPrintJobsController = new PrintJobsController(this, 0);
        getLoaderManager().initLoader(1, null, this.mPrintJobsController);
        this.mPrintServicesController = new PrintJobsController(this, 1);
        getLoaderManager().initLoader(2, null, this.mPrintServicesController);
    }

    public void startSettings() {
        String string;
        if (this.mIsUiRestricted) {
            getPreferenceScreen().removeAll();
            return;
        }
        setHasOptionsMenu(true);
        if (getArguments() == null || (string = getArguments().getString("EXTRA_PRINT_SERVICE_COMPONENT_NAME")) == null) {
            return;
        }
        getArguments().remove("EXTRA_PRINT_SERVICE_COMPONENT_NAME");
        Preference findPreference = findPreference(string);
        if (findPreference != null) {
            findPreference.performClick();
        }
    }
}
