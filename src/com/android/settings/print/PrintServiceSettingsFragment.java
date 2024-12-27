package com.android.settings.print;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.print.PrintManager;
import android.print.PrinterDiscoverySession;
import android.print.PrinterInfo;
import android.printservice.PrintServiceInfo;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.widget.SeslSwitchBar;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.widget.SettingsMainSwitchBar;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrintServiceSettingsFragment extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener, LoaderManager.LoaderCallbacks {
    public Intent mAddPrintersIntent;
    public ComponentName mComponentName;
    public final AnonymousClass1 mDataObserver =
            new RecyclerView
                    .AdapterDataObserver() { // from class:
                                             // com.android.settings.print.PrintServiceSettingsFragment.1
                @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                public final void onChanged() {
                    PrintServiceSettingsFragment printServiceSettingsFragment =
                            PrintServiceSettingsFragment.this;
                    int size =
                            ((ArrayList) printServiceSettingsFragment.mPrintersAdapter.mPrinters)
                                    .size();
                    int i = printServiceSettingsFragment.mLastUnfilteredItemCount;
                    if ((i <= 0 && size > 0) || (i > 0 && size <= 0)) {
                        printServiceSettingsFragment.getActivity().invalidateOptionsMenu();
                    }
                    printServiceSettingsFragment.mLastUnfilteredItemCount = size;
                    printServiceSettingsFragment.updateEmptyView$1();
                }
            };
    public final Handler mHandler = new Handler();
    public int mLastUnfilteredItemCount;
    public String mPreferenceKey;
    public PrintersAdapter mPrintersAdapter;
    public SearchView mSearchView;
    public boolean mServiceEnabled;
    public Intent mSettingsIntent;
    public SettingsMainSwitchBar mSwitchBar;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PrintersAdapter extends RecyclerView.Adapter
            implements LoaderManager.LoaderCallbacks, Filterable {
        public CharSequence mLastSearchString;
        public final Object mLock = new Object();
        public final List mPrinters = new ArrayList();
        public final List mFilteredPrinters = new ArrayList();

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.print.PrintServiceSettingsFragment$PrintersAdapter$1, reason: invalid class name */
        public final class AnonymousClass1 extends Filter {
            public AnonymousClass1() {}

            @Override // android.widget.Filter
            public final Filter.FilterResults performFiltering(CharSequence charSequence) {
                synchronized (PrintersAdapter.this.mLock) {
                    try {
                        if (TextUtils.isEmpty(charSequence)) {
                            return null;
                        }
                        Filter.FilterResults filterResults = new Filter.FilterResults();
                        ArrayList arrayList = new ArrayList();
                        String lowerCase = charSequence.toString().toLowerCase();
                        int size = ((ArrayList) PrintersAdapter.this.mPrinters).size();
                        for (int i = 0; i < size; i++) {
                            PrinterInfo printerInfo =
                                    (PrinterInfo)
                                            ((ArrayList) PrintersAdapter.this.mPrinters).get(i);
                            String name = printerInfo.getName();
                            if (name != null && name.toLowerCase().contains(lowerCase)) {
                                arrayList.add(printerInfo);
                            }
                        }
                        filterResults.values = arrayList;
                        filterResults.count = arrayList.size();
                        return filterResults;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.widget.Filter
            public final void publishResults(
                    CharSequence charSequence, Filter.FilterResults filterResults) {
                synchronized (PrintersAdapter.this.mLock) {
                    try {
                        PrintersAdapter printersAdapter = PrintersAdapter.this;
                        printersAdapter.mLastSearchString = charSequence;
                        ((ArrayList) printersAdapter.mFilteredPrinters).clear();
                        if (filterResults == null) {
                            PrintersAdapter printersAdapter2 = PrintersAdapter.this;
                            ((ArrayList) printersAdapter2.mFilteredPrinters)
                                    .addAll(printersAdapter2.mPrinters);
                        } else {
                            ((ArrayList) PrintersAdapter.this.mFilteredPrinters)
                                    .addAll((List) filterResults.values);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                PrintersAdapter.this.notifyDataSetChanged();
            }
        }

        public PrintersAdapter() {}

        @Override // android.widget.Filterable
        public final Filter getFilter() {
            return new AnonymousClass1();
        }

        public final Object getItem(int i) {
            Object obj;
            synchronized (this.mLock) {
                obj = ((ArrayList) this.mFilteredPrinters).get(i);
            }
            return obj;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            int size;
            synchronized (this.mLock) {
                size = ((ArrayList) this.mFilteredPrinters).size();
            }
            return size;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
            ViewHolder viewHolder2 = (ViewHolder) viewHolder;
            viewHolder2.itemView.setEnabled(((PrinterInfo) getItem(i)).getStatus() != 3);
            final PrinterInfo printerInfo = (PrinterInfo) getItem(i);
            String name = printerInfo.getName();
            String description = printerInfo.getDescription();
            Drawable loadIcon =
                    printerInfo.loadIcon(PrintServiceSettingsFragment.this.getActivity());
            ((TextView) viewHolder2.itemView.findViewById(R.id.title)).setText(name);
            TextView textView = (TextView) viewHolder2.itemView.findViewById(R.id.subtitle);
            if (TextUtils.isEmpty(description)) {
                textView.setText((CharSequence) null);
                textView.setVisibility(8);
            } else {
                textView.setText(description);
                textView.setVisibility(0);
            }
            LinearLayout linearLayout =
                    (LinearLayout) viewHolder2.itemView.findViewById(R.id.more_info);
            if (printerInfo.getInfoIntent() != null) {
                linearLayout.setVisibility(0);
                linearLayout.setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.android.settings.print.PrintServiceSettingsFragment.PrintersAdapter.2
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                try {
                                    PrintServiceSettingsFragment.this
                                            .getActivity()
                                            .startIntentSender(
                                                    printerInfo.getInfoIntent().getIntentSender(),
                                                    null,
                                                    0,
                                                    0,
                                                    0,
                                                    ActivityOptions.makeBasic()
                                                            .setPendingIntentBackgroundActivityStartMode(
                                                                    1)
                                                            .toBundle());
                                } catch (IntentSender.SendIntentException e) {
                                    Log.e(
                                            "PrintServiceSettings",
                                            "Could not execute pending info intent: %s",
                                            e);
                                }
                            }
                        });
            } else {
                linearLayout.setVisibility(8);
            }
            ImageView imageView = (ImageView) viewHolder2.itemView.findViewById(R.id.icon);
            if (loadIcon != null) {
                imageView.setVisibility(0);
                if (((PrinterInfo) getItem(i)).getStatus() == 3) {
                    loadIcon.mutate();
                    TypedValue typedValue = new TypedValue();
                    PrintServiceSettingsFragment.this
                            .getActivity()
                            .getTheme()
                            .resolveAttribute(android.R.attr.disabledAlpha, typedValue, true);
                    loadIcon.setAlpha((int) (typedValue.getFloat() * 255.0f));
                }
                imageView.setImageDrawable(loadIcon);
            } else {
                imageView.setVisibility(8);
            }
            viewHolder2.itemView.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.print.PrintServiceSettingsFragment$PrintersAdapter$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            PrintServiceSettingsFragment.PrintersAdapter printersAdapter =
                                    PrintServiceSettingsFragment.PrintersAdapter.this;
                            PrinterInfo printerInfo2 = (PrinterInfo) printersAdapter.getItem(i);
                            if (printerInfo2.getInfoIntent() != null) {
                                try {
                                    PrintServiceSettingsFragment.this
                                            .getActivity()
                                            .startIntentSender(
                                                    printerInfo2.getInfoIntent().getIntentSender(),
                                                    null,
                                                    0,
                                                    0,
                                                    0);
                                } catch (IntentSender.SendIntentException e) {
                                    Log.e(
                                            "PrintServiceSettings",
                                            "Could not execute info intent: %s",
                                            e);
                                }
                            }
                        }
                    });
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final Loader onCreateLoader(int i, Bundle bundle) {
            if (i == 1) {
                return new PrintersLoader(PrintServiceSettingsFragment.this.getContext());
            }
            return null;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(
                    MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                            viewGroup, R.layout.printer_dropdown_item, viewGroup, false));
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final void onLoadFinished(Loader loader, Object obj) {
            List list = (List) obj;
            synchronized (this.mLock) {
                try {
                    ((ArrayList) this.mPrinters).clear();
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        PrinterInfo printerInfo = (PrinterInfo) list.get(i);
                        if (printerInfo
                                .getId()
                                .getServiceName()
                                .equals(PrintServiceSettingsFragment.this.mComponentName)) {
                            ((ArrayList) this.mPrinters).add(printerInfo);
                        }
                    }
                    ((ArrayList) this.mFilteredPrinters).clear();
                    ((ArrayList) this.mFilteredPrinters).addAll(this.mPrinters);
                    if (!TextUtils.isEmpty(this.mLastSearchString)) {
                        new AnonymousClass1().filter(this.mLastSearchString);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            notifyDataSetChanged();
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final void onLoaderReset(Loader loader) {
            synchronized (this.mLock) {
                ((ArrayList) this.mPrinters).clear();
                ((ArrayList) this.mFilteredPrinters).clear();
                this.mLastSearchString = null;
            }
            notifyDataSetChanged();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PrintersLoader extends Loader {
        public PrinterDiscoverySession mDiscoverySession;
        public final Map mPrinters;

        public PrintersLoader(Context context) {
            super(context);
            this.mPrinters = new LinkedHashMap();
        }

        @Override // androidx.loader.content.Loader
        public final void onAbandon() {
            onCancelLoad();
        }

        @Override // androidx.loader.content.Loader
        public final boolean onCancelLoad() {
            PrinterDiscoverySession printerDiscoverySession = this.mDiscoverySession;
            if (printerDiscoverySession == null
                    || !printerDiscoverySession.isPrinterDiscoveryStarted()) {
                return false;
            }
            this.mDiscoverySession.stopPrinterDiscovery();
            return true;
        }

        @Override // androidx.loader.content.Loader
        public final void onForceLoad() {
            if (this.mDiscoverySession == null) {
                PrinterDiscoverySession createPrinterDiscoverySession =
                        ((PrintManager) this.mContext.getSystemService("print"))
                                .createPrinterDiscoverySession();
                this.mDiscoverySession = createPrinterDiscoverySession;
                createPrinterDiscoverySession.setOnPrintersChangeListener(
                        new PrinterDiscoverySession
                                .OnPrintersChangeListener() { // from class:
                                                              // com.android.settings.print.PrintServiceSettingsFragment.PrintersLoader.1
                            public final void onPrintersChanged() {
                                PrintersLoader printersLoader = PrintersLoader.this;
                                ArrayList arrayList =
                                        new ArrayList(
                                                PrintersLoader.this.mDiscoverySession
                                                        .getPrinters());
                                if (printersLoader.mStarted) {
                                    printersLoader.deliverResult(arrayList);
                                }
                            }
                        });
            }
            this.mDiscoverySession.startPrinterDiscovery((List) null);
        }

        @Override // androidx.loader.content.Loader
        public final void onReset() {
            onCancelLoad();
            ((LinkedHashMap) this.mPrinters).clear();
            PrinterDiscoverySession printerDiscoverySession = this.mDiscoverySession;
            if (printerDiscoverySession != null) {
                printerDiscoverySession.destroy();
                this.mDiscoverySession = null;
            }
        }

        @Override // androidx.loader.content.Loader
        public final void onStartLoading() {
            if (!this.mPrinters.isEmpty()) {
                ArrayList arrayList = new ArrayList(((LinkedHashMap) this.mPrinters).values());
                if (this.mStarted) {
                    deliverResult(arrayList);
                }
            }
            onForceLoad();
        }

        @Override // androidx.loader.content.Loader
        public final void onStopLoading() {
            onCancelLoad();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ViewHolder extends RecyclerView.ViewHolder {}

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 79;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        updateEmptyView$1();
        ((PrintManager) getContext().getSystemService("print"))
                .setPrintServiceEnabled(this.mComponentName, z);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        String string = getArguments().getString("EXTRA_TITLE");
        if (TextUtils.isEmpty(string)) {
            return;
        }
        getActivity().setTitle(string);
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final Loader onCreateLoader(int i, Bundle bundle) {
        return new SettingsPrintServicesLoader(
                (PrintManager) getContext().getSystemService("print"), getContext());
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        Intent intent;
        Intent intent2;
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.print_service_settings, menu);
        MenuItem findItem = menu.findItem(R.id.print_menu_item_add_printer);
        if (!this.mServiceEnabled || (intent2 = this.mAddPrintersIntent) == null) {
            menu.removeItem(R.id.print_menu_item_add_printer);
        } else {
            findItem.setIntent(intent2);
        }
        MenuItem findItem2 = menu.findItem(R.id.print_menu_item_settings);
        if (!this.mServiceEnabled || (intent = this.mSettingsIntent) == null) {
            menu.removeItem(R.id.print_menu_item_settings);
        } else {
            findItem2.setIntent(intent);
        }
        MenuItem findItem3 = menu.findItem(R.id.print_menu_item_search);
        if (!this.mServiceEnabled || ((ArrayList) this.mPrintersAdapter.mPrinters).size() <= 0) {
            menu.removeItem(R.id.print_menu_item_search);
            return;
        }
        SearchView searchView = (SearchView) findItem3.getActionView();
        this.mSearchView = searchView;
        if (searchView != null) {
            searchView.setMaxWidth(Preference.DEFAULT_ORDER);
            this.mSearchView.setOnQueryTextListener(
                    new SearchView
                            .OnQueryTextListener() { // from class:
                                                     // com.android.settings.print.PrintServiceSettingsFragment.3
                        @Override // android.widget.SearchView.OnQueryTextListener
                        public final boolean onQueryTextChange(String str) {
                            PrintersAdapter printersAdapter =
                                    PrintServiceSettingsFragment.this.mPrintersAdapter;
                            printersAdapter.getClass();
                            printersAdapter.new AnonymousClass1().filter(str);
                            return true;
                        }

                        @Override // android.widget.SearchView.OnQueryTextListener
                        public final boolean onQueryTextSubmit(String str) {
                            return true;
                        }
                    });
            this.mSearchView.addOnAttachStateChangeListener(
                    new View
                            .OnAttachStateChangeListener() { // from class:
                                                             // com.android.settings.print.PrintServiceSettingsFragment.4
                        @Override // android.view.View.OnAttachStateChangeListener
                        public final void onViewAttachedToWindow(View view) {
                            if (AccessibilityManager.getInstance(
                                            PrintServiceSettingsFragment.this.getActivity())
                                    .isEnabled()) {
                                view.announceForAccessibility(
                                        PrintServiceSettingsFragment.this.getString(
                                                R.string.print_search_box_shown_utterance));
                            }
                        }

                        @Override // android.view.View.OnAttachStateChangeListener
                        public final void onViewDetachedFromWindow(View view) {
                            FragmentActivity activity =
                                    PrintServiceSettingsFragment.this.getActivity();
                            if (activity == null
                                    || activity.isFinishing()
                                    || !AccessibilityManager.getInstance(activity).isEnabled()) {
                                return;
                            }
                            view.announceForAccessibility(
                                    PrintServiceSettingsFragment.this.getString(
                                            R.string.print_search_box_hidden_utterance));
                        }
                    });
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.roundedCornerColor, typedValue, true);
        onCreateView.semSetRoundedCornerColor(
                15,
                typedValue.resourceId > 0
                        ? getActivity().getResources().getColor(typedValue.resourceId)
                        : R.color.white);
        onCreateView.semSetRoundedCorners(15);
        this.mServiceEnabled = getArguments().getBoolean("EXTRA_CHECKED");
        return onCreateView;
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final void onLoadFinished(Loader loader, Object obj) {
        PrintServiceInfo printServiceInfo;
        List list = (List) obj;
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (((PrintServiceInfo) list.get(i))
                        .getComponentName()
                        .equals(this.mComponentName)) {
                    printServiceInfo = (PrintServiceInfo) list.get(i);
                    break;
                }
            }
        }
        printServiceInfo = null;
        if (printServiceInfo == null) {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.settings.print.PrintServiceSettingsFragment.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            PrintServiceSettingsFragment.this.finishFragment();
                        }
                    });
            return;
        }
        this.mServiceEnabled = printServiceInfo.isEnabled();
        if (printServiceInfo.getSettingsActivityName() != null) {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setComponent(
                    new ComponentName(
                            printServiceInfo.getComponentName().getPackageName(),
                            printServiceInfo.getSettingsActivityName()));
            List<ResolveInfo> queryIntentActivities =
                    getPackageManager().queryIntentActivities(intent, 0);
            if (!queryIntentActivities.isEmpty()
                    && queryIntentActivities.get(0).activityInfo.exported) {
                this.mSettingsIntent = intent;
            }
        } else {
            this.mSettingsIntent = null;
        }
        if (printServiceInfo.getAddPrintersActivityName() != null) {
            Intent intent2 = new Intent("android.intent.action.MAIN");
            intent2.setComponent(
                    new ComponentName(
                            printServiceInfo.getComponentName().getPackageName(),
                            printServiceInfo.getAddPrintersActivityName()));
            List<ResolveInfo> queryIntentActivities2 =
                    getPackageManager().queryIntentActivities(intent2, 0);
            if (!queryIntentActivities2.isEmpty()
                    && queryIntentActivities2.get(0).activityInfo.exported) {
                this.mAddPrintersIntent = intent2;
            }
        } else {
            this.mAddPrintersIntent = null;
        }
        updateUiForServiceState();
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final void onLoaderReset(Loader loader) {
        updateUiForServiceState();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        SearchView searchView = this.mSearchView;
        if (searchView != null) {
            searchView.setOnQueryTextListener(null);
        }
        super.onPause();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        PrintersAdapter printersAdapter = new PrintersAdapter();
        this.mPrintersAdapter = printersAdapter;
        printersAdapter.registerAdapterDataObserver(this.mDataObserver);
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.addOnSwitchChangeListener(this);
        this.mSwitchBar.show();
        this.mSwitchBar.setOnBeforeCheckedChangeListener(
                new SettingsMainSwitchBar
                        .OnBeforeCheckedChangeListener() { // from class:
                                                           // com.android.settings.print.PrintServiceSettingsFragment$$ExternalSyntheticLambda0
                    @Override // com.android.settings.widget.SettingsMainSwitchBar.OnBeforeCheckedChangeListener
                    public final boolean onBeforeCheckedChanged(boolean z) {
                        PrintServiceSettingsFragment printServiceSettingsFragment =
                                PrintServiceSettingsFragment.this;
                        String str = printServiceSettingsFragment.mPreferenceKey;
                        ((PrintManager)
                                        printServiceSettingsFragment
                                                .getContext()
                                                .getSystemService("print"))
                                .setPrintServiceEnabled(
                                        printServiceSettingsFragment.mComponentName, z);
                        return false;
                    }
                });
        getListView().setAdapter(this.mPrintersAdapter);
        Bundle arguments = getArguments();
        ComponentName unflattenFromString =
                ComponentName.unflattenFromString(
                        arguments.getString("EXTRA_SERVICE_COMPONENT_NAME"));
        this.mComponentName = unflattenFromString;
        this.mPreferenceKey = unflattenFromString.flattenToString();
        this.mSwitchBar.setCheckedInternal(arguments.getBoolean("EXTRA_CHECKED"));
        getLoaderManager().initLoader(2, null, this);
        setHasOptionsMenu(true);
        updateEmptyView$1();
        updateUiForServiceState();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        this.mSwitchBar.removeOnSwitchChangeListener(this);
        this.mSwitchBar.hide();
        this.mPrintersAdapter.unregisterAdapterDataObserver(this.mDataObserver);
    }

    public final void updateEmptyView$1() {
        ViewGroup viewGroup = (ViewGroup) getListView().getParent();
        View emptyView = getEmptyView();
        if (!((SeslSwitchBar) this.mSwitchBar).mSwitch.isChecked()) {
            if (emptyView != null) {
                viewGroup.removeView(emptyView);
                emptyView = null;
            }
            if (emptyView == null) {
                View inflate =
                        getActivity()
                                .getLayoutInflater()
                                .inflate(R.layout.empty_print_state, viewGroup, false);
                ((TextView) inflate.findViewById(R.id.message))
                        .setText(R.string.print_service_disabled);
                viewGroup.addView(inflate);
                setEmptyView(inflate);
                return;
            }
            return;
        }
        if (((ArrayList) this.mPrintersAdapter.mPrinters).size() <= 0) {
            if (emptyView != null) {
                viewGroup.removeView(emptyView);
                emptyView = null;
            }
            if (emptyView == null) {
                View inflate2 =
                        getActivity()
                                .getLayoutInflater()
                                .inflate(
                                        R.layout.empty_printers_list_service_enabled,
                                        viewGroup,
                                        false);
                viewGroup.addView(inflate2);
                setEmptyView(inflate2);
                return;
            }
            return;
        }
        if (this.mPrintersAdapter.getItemCount() > 0) {
            if (this.mPrintersAdapter.getItemCount() <= 0 || emptyView == null) {
                return;
            }
            viewGroup.removeView(emptyView);
            return;
        }
        if (emptyView != null) {
            viewGroup.removeView(emptyView);
            emptyView = null;
        }
        if (emptyView == null) {
            View inflate3 =
                    getActivity()
                            .getLayoutInflater()
                            .inflate(R.layout.empty_print_state, viewGroup, false);
            ((TextView) inflate3.findViewById(R.id.message))
                    .setText(R.string.print_no_printers_found);
            viewGroup.addView(inflate3);
            setEmptyView(inflate3);
        }
    }

    public final void updateUiForServiceState() {
        if (this.mServiceEnabled) {
            this.mSwitchBar.setCheckedInternal(true);
            PrintersAdapter printersAdapter = this.mPrintersAdapter;
            PrintServiceSettingsFragment.this
                    .getLoaderManager()
                    .initLoader(1, null, printersAdapter);
        } else {
            this.mSwitchBar.setCheckedInternal(false);
            PrintersAdapter printersAdapter2 = this.mPrintersAdapter;
            PrintServiceSettingsFragment.this.getLoaderManager().destroyLoader(1);
            ((ArrayList) printersAdapter2.mPrinters).clear();
        }
        getActivity().invalidateOptionsMenu();
    }
}
