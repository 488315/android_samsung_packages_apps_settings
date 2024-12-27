package com.samsung.android.settings.wifi.managenetwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SimpleClock;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.util.SeslRoundedCorner;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.RestrictedSettingsFragment;
import com.android.settings.Utils;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.wifitrackerlib.SavedNetworkFilter;
import com.samsung.android.wifitrackerlib.SemSavedNetworkTracker;

import java.time.ZoneOffset;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SavedWifiEntrySettings extends RestrictedSettingsFragment
        implements SemSavedNetworkTracker.SavedNetworkTrackerCallback,
                AppBarLayout.OnOffsetChangedListener {
    public ActionBar mActionBar;
    public SavedWifiEntryListAdapter mAdapter;
    public BottomNavigationView mBottomNavigation;
    public CollapsingToolbarLayout mCollapsingToolbarLayout;
    public AlertDialog mConfirmDialog;
    public Context mContext;
    public MenuItem mDeleteMenu;
    public int mDragStartPosition;
    public TextView mEmptyView;
    public boolean mIsRestricted;
    public int mMode;
    public final AnonymousClass1 mOnBackPressedCallback;
    public ProgressDialog mProgressDialog;
    public RecyclerView mRecyclerView;
    public SemSavedNetworkTracker mSavedNetworkTracker;
    public CheckBox mSelectAll;
    public TextView mSelectedCount;
    public HandlerThread mWorkerThread;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.managenetwork.SavedWifiEntrySettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends SimpleClock {
        public final long millis() {
            return SystemClock.elapsedRealtime();
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.android.settings.wifi.managenetwork.SavedWifiEntrySettings$1] */
    public SavedWifiEntrySettings() {
        super("no_config_wifi");
        this.mDragStartPosition = 0;
        this.mMode = 1;
        this.mOnBackPressedCallback =
                new OnBackPressedCallback() { // from class:
                                              // com.samsung.android.settings.wifi.managenetwork.SavedWifiEntrySettings.1
                    @Override // androidx.activity.OnBackPressedCallback
                    public final void handleOnBackPressed() {
                        SavedWifiEntrySettings savedWifiEntrySettings = SavedWifiEntrySettings.this;
                        if (savedWifiEntrySettings.mMode != 0) {
                            savedWifiEntrySettings.getActivity().finish();
                        } else {
                            SALogging.insertSALog("WIFI_220", "1265");
                            savedWifiEntrySettings.setMode$2(1);
                        }
                    }
                };
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 106;
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (bundle == null || !bundle.containsKey("wifi_ap_state")) {
            return;
        }
        bundle.getBundle("wifi_ap_state");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        setupActionBar();
        updateSelectedItemsCount();
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
        HandlerThread handlerThread =
                new HandlerThread(
                        "SavedWifiEntrySettings{"
                                + Integer.toHexString(System.identityHashCode(this))
                                + "}",
                        10);
        this.mWorkerThread = handlerThread;
        handlerThread.start();
        SimpleClock anonymousClass2 = new AnonymousClass2(ZoneOffset.UTC);
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        Context context = this.mContext;
        this.mSavedNetworkTracker =
                new SemSavedNetworkTracker(
                        settingsLifecycle,
                        context,
                        (WifiManager) context.getSystemService(WifiManager.class),
                        (ConnectivityManager)
                                this.mContext.getSystemService(ConnectivityManager.class),
                        new Handler(Looper.getMainLooper()),
                        this.mWorkerThread.getThreadHandler(),
                        anonymousClass2,
                        this,
                        SavedNetworkFilter.MANAGE_NETWORK_COMPARATOR);
        this.mIsRestricted = isUiRestricted();
        getActivity().getOnBackPressedDispatcher().addCallback(this.mOnBackPressedCallback);
        SALogging.insertSALog("WIFI_200", "1250");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        View inflate =
                layoutInflater.inflate(
                        R.layout.sec_saved_access_points_list, (ViewGroup) onCreateView);
        this.mAdapter =
                new SavedWifiEntryListAdapter(
                        this.mContext, this.mSavedNetworkTracker.getSavedWifiEntries());
        RecyclerView recyclerView =
                (RecyclerView) inflate.findViewById(R.id.saved_access_point_recycle);
        this.mRecyclerView = recyclerView;
        getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(1));
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setHasFixedSize(true);
        int listHorizontalPadding = Utils.getListHorizontalPadding(getContext());
        this.mRecyclerView.setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
        this.mRecyclerView.setScrollBarStyle(33554432);
        this.mRecyclerView.seslSetFillHorizontalPaddingEnabled(true);
        this.mRecyclerView.seslSetGoToTopEnabled(true);
        this.mRecyclerView.seslSetFillBottomEnabled(true);
        RecyclerView recyclerView2 = this.mRecyclerView;
        recyclerView2.mLongPressMultiSelectionListener =
                new RecyclerView
                        .SeslLongPressMultiSelectionListener() { // from class:
                                                                 // com.samsung.android.settings.wifi.managenetwork.SavedWifiEntrySettings.4
                    @Override // androidx.recyclerview.widget.RecyclerView.SeslLongPressMultiSelectionListener
                    public final void onItemSelected(int i) {
                        SavedWifiEntrySettings savedWifiEntrySettings = SavedWifiEntrySettings.this;
                        if (i != savedWifiEntrySettings.mDragStartPosition) {
                            savedWifiEntrySettings.mAdapter.setChecked(i);
                        }
                        savedWifiEntrySettings.updateComponentsStatus(savedWifiEntrySettings.mMode);
                    }

                    @Override // androidx.recyclerview.widget.RecyclerView.SeslLongPressMultiSelectionListener
                    public final void onLongPressMultiSelectionEnded() {
                        SavedWifiEntrySettings savedWifiEntrySettings = SavedWifiEntrySettings.this;
                        savedWifiEntrySettings.updateSelectedItemsCount();
                        savedWifiEntrySettings.mDragStartPosition = 0;
                    }

                    @Override // androidx.recyclerview.widget.RecyclerView.SeslLongPressMultiSelectionListener
                    public final void onLongPressMultiSelectionStarted(int i, int i2) {
                        SavedWifiEntrySettings savedWifiEntrySettings = SavedWifiEntrySettings.this;
                        SALogging.insertSALog(
                                "WIFI_220",
                                "1264",
                                String.valueOf(savedWifiEntrySettings.mDragStartPosition));
                        savedWifiEntrySettings.updateComponentsStatus(savedWifiEntrySettings.mMode);
                    }
                };
        Context context = recyclerView2.getContext();
        getContext();
        recyclerView2.addItemDecoration(
                new SavedWifiEntryDividerItemDecoration(
                        context, new LinearLayoutManager(1).mOrientation));
        this.mRecyclerView.addItemDecoration(
                new RecyclerView
                        .ItemDecoration() { // from class:
                                            // com.samsung.android.settings.wifi.managenetwork.SavedWifiEntrySettings.3
                    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                    public final void seslOnDispatchDraw(
                            Canvas canvas, RecyclerView recyclerView3, RecyclerView.State state) {
                        SeslRoundedCorner seslRoundedCorner =
                                new SeslRoundedCorner(SavedWifiEntrySettings.this.mContext);
                        seslRoundedCorner.setRoundedCorners(3);
                        seslRoundedCorner.drawRoundedCorner(
                                canvas,
                                Insets.of(
                                        recyclerView3.getPaddingStart(),
                                        0,
                                        recyclerView3.getPaddingEnd(),
                                        0));
                    }
                });
        this.mAdapter.mListener = this;
        this.mEmptyView = (TextView) inflate.findViewById(R.id.no_saved_ap_text);
        this.mEmptyView.setVisibility(this.mAdapter.getItemCount() > 0 ? 8 : 0);
        this.mCollapsingToolbarLayout =
                (CollapsingToolbarLayout) getActivity().findViewById(R.id.collapsing_app_bar);
        ((AppBarLayout) getActivity().getWindow().getDecorView().findViewById(R.id.app_bar))
                .addOnOffsetChangedListener(this);
        this.mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getActivity().getString(R.string.wifi_deleting_networks));
        this.mProgressDialog = progressDialog;
        RelativeLayout relativeLayout =
                (RelativeLayout) getActivity().findViewById(R.id.button_bar);
        if (relativeLayout != null) {
            relativeLayout.setVisibility(0);
            BottomNavigationView bottomNavigationView =
                    (BottomNavigationView)
                            ((LayoutInflater) getActivity().getSystemService("layout_inflater"))
                                    .inflate(
                                            R.layout.sec_wifi_saved_access_points_bottom,
                                            (ViewGroup) relativeLayout,
                                            false);
            this.mBottomNavigation = bottomNavigationView;
            bottomNavigationView.reselectedListener =
                    new SavedWifiEntrySettings$$ExternalSyntheticLambda0(this);
            relativeLayout.addView(bottomNavigationView);
            MenuItem findItem =
                    this.mBottomNavigation.menu.findItem(R.id.saved_access_points_bottom);
            this.mDeleteMenu = findItem;
            findItem.setEnabled(false);
        }
        setupActionBar();
        return onCreateView;
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        this.mWorkerThread.quit();
        remove();
        AlertDialog alertDialog = this.mConfirmDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mConfirmDialog.dismiss();
        }
        super.onDestroy();
    }

    @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
    public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        TextView textView;
        float abs = Math.abs(i) / appBarLayout.getTotalScrollRange();
        if (this.mMode != 0 || (textView = this.mSelectedCount) == null) {
            return;
        }
        textView.setAlpha(abs);
    }

    @Override // com.samsung.android.wifitrackerlib.SemSavedNetworkTracker.SavedNetworkTrackerCallback
    public final void onSavedWifiEntriesChanged() {
        if (isFinishingOrDestroyed() || this.mMode == 0) {
            return;
        }
        this.mAdapter.updateAllWifiEntries(this.mSavedNetworkTracker.getSavedWifiEntries());
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        if (this.mIsRestricted) {
            return;
        }
        onSavedWifiEntriesChanged();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker.BaseWifiTrackerCallback
    public final void onWifiStateChanged() {
        if (isFinishingOrDestroyed() || this.mIsRestricted) {
            return;
        }
        int wifiState = this.mSavedNetworkTracker.getWifiState();
        if (wifiState == 0 || wifiState == 1) {
            getActivity().finish();
        } else if (this.mMode == 1) {
            updateWifiEntries$1();
        }
    }

    public final void setMode$2(int i) {
        this.mMode = i;
        if (i == 0) {
            this.mSavedNetworkTracker.onStop();
            if (this.mAdapter.isAutoSelectAllEnabled()) {
                this.mAdapter.setRemoveAllState(true);
            }
        } else {
            this.mSavedNetworkTracker.onStart();
            updateWifiEntries$1();
        }
        SavedWifiEntryListAdapter savedWifiEntryListAdapter = this.mAdapter;
        if (savedWifiEntryListAdapter.mMode != i) {
            savedWifiEntryListAdapter.mMode = i;
            savedWifiEntryListAdapter.notifyDataSetChanged();
        }
        setupActionBar();
        updateComponentsStatus(i);
    }

    public final void setupActionBar() {
        if (this.mMode != 0) {
            this.mActionBar.setDisplayHomeAsUpEnabled(true);
            this.mActionBar.setDisplayShowCustomEnabled(false);
            this.mActionBar.setTitle(R.string.wifi_manage_network_title);
            String string =
                    this.mContext.getResources().getString(R.string.wifi_manage_network_title);
            if (this.mCollapsingToolbarLayout == null || TextUtils.isEmpty(string)) {
                return;
            }
            this.mCollapsingToolbarLayout.setTitle(string);
            return;
        }
        this.mActionBar.setDisplayHomeAsUpEnabled(false);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(8388627);
        View inflate =
                getActivity()
                        .getLayoutInflater()
                        .inflate(
                                R.layout.sec_saved_access_points_custom_action_bar,
                                (ViewGroup) null);
        CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.select_all_checkbox);
        this.mSelectAll = checkBox;
        checkBox.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.wifi.managenetwork.SavedWifiEntrySettings$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SavedWifiEntrySettings savedWifiEntrySettings = SavedWifiEntrySettings.this;
                        savedWifiEntrySettings.mAdapter.setRemoveAllState(
                                savedWifiEntrySettings.mSelectAll.isChecked());
                        savedWifiEntrySettings.updateComponentsStatus(savedWifiEntrySettings.mMode);
                    }
                });
        this.mSelectedCount = (TextView) inflate.findViewById(R.id.selected_items_count);
        this.mActionBar.setDisplayShowCustomEnabled(true);
        this.mActionBar.setCustomView(inflate, layoutParams);
        ((Toolbar) inflate.getParent()).setContentInsetsAbsolute(0, 0);
    }

    public final void updateComponentsStatus(int i) {
        boolean anyMatch;
        if (i == 0) {
            this.mDeleteMenu.setEnabled(this.mAdapter.getCheckedCount$1() > 0);
        } else {
            MenuItem menuItem = this.mDeleteMenu;
            SavedWifiEntryListAdapter savedWifiEntryListAdapter = this.mAdapter;
            synchronized (savedWifiEntryListAdapter.mLock) {
                anyMatch =
                        savedWifiEntryListAdapter.mSavedWifiEntries.stream()
                                .anyMatch(
                                        new SavedWifiEntryListAdapter$$ExternalSyntheticLambda0(2));
            }
            menuItem.setEnabled(anyMatch);
        }
        updateSelectedItemsCount();
    }

    public final void updateSelectedItemsCount() {
        if (this.mMode == 1) {
            return;
        }
        int checkedCount$1 = this.mAdapter.getCheckedCount$1();
        String quantityString =
                checkedCount$1 > 0
                        ? this.mContext
                                .getResources()
                                .getQuantityString(
                                        R.plurals.wifi_select_item_network,
                                        checkedCount$1,
                                        Integer.valueOf(checkedCount$1))
                        : this.mContext
                                .getResources()
                                .getString(R.string.wifi_select_item_network_zero);
        this.mSelectedCount.setText(quantityString);
        if (this.mCollapsingToolbarLayout != null && !TextUtils.isEmpty(quantityString)) {
            this.mCollapsingToolbarLayout.setTitle(quantityString);
        }
        if (checkedCount$1 == 0 && this.mSelectAll.isChecked()) {
            this.mSelectAll.setChecked(false);
        } else {
            this.mSelectAll.setChecked(
                    checkedCount$1 > 0
                            && checkedCount$1 == this.mAdapter.getRemovableEntriesCount());
        }
    }

    public final void updateWifiEntries$1() {
        this.mAdapter.updateAllWifiEntries(this.mSavedNetworkTracker.getSavedWifiEntries());
        this.mEmptyView.setVisibility(this.mAdapter.getItemCount() > 0 ? 8 : 0);
        this.mDragStartPosition = 0;
        updateComponentsStatus(this.mMode);
    }
}
