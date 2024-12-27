package com.android.settings.localepicker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.LocaleList;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.RecyclerView;
import androidx.reflect.view.SeslViewReflector;

import com.android.internal.app.LocalePicker;
import com.android.internal.app.LocaleStore;
import com.android.settings.R;
import com.android.settings.RestrictedSettingsFragment;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.core.OnActivityResultListener;
import com.android.settings.core.SettingsBaseActivity;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.general.GeneralDeviceSettings;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecRoundButtonView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LocaleListEditor extends RestrictedSettingsFragment
        implements View.OnClickListener,
                LocaleDragAndDropAdapter.OnCheckChangeListener,
                OnActivityResultListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass15();
    public LocaleDragAndDropAdapter mAdapter;
    public View mAddLanguage;
    public LinearLayout mAddLanguageLayout;
    public SecRoundButtonView mApplyButton;
    public LinearLayout mApplyButtonLayout;
    public boolean mIsUiRestricted;
    public boolean mLocaleAdditionMode;
    public LocaleHelperPreferenceController mLocaleHelperPreferenceController;
    public Menu mMenu;
    public AlertDialog mNofiticationDialog;
    public AlertDialog mRemoveAlertDialog;
    public boolean mRemoveMode;
    public SettingsBaseActivity mSettingsBaseActivity;
    public boolean mShowingRemoveDialog;
    public RelativeLayout removeButtonLayout;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.localepicker.LocaleListEditor$15, reason: invalid class name */
    public final class AnonymousClass15 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            Resources resources = context.getResources();
            ArrayList arrayList = new ArrayList();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            ((SearchIndexableData) searchIndexableRaw).key = "add_language";
            searchIndexableRaw.title = resources.getString(R.string.add_a_language);
            searchIndexableRaw.screenTitle = resources.getString(R.string.language_picker_title);
            arrayList.add(searchIndexableRaw);
            return arrayList;
        }
    }

    public LocaleListEditor() {
        super("no_config_locale");
        this.mLocaleAdditionMode = false;
        this.mNofiticationDialog = null;
        this.mRemoveAlertDialog = null;
    }

    public static LocaleStore.LocaleInfo mayAppendUnicodeTags(
            LocaleStore.LocaleInfo localeInfo, String str) {
        if (TextUtils.isEmpty(str) || TextUtils.equals("und", str)) {
            return localeInfo;
        }
        final Locale forLanguageTag = Locale.forLanguageTag(str);
        final Locale.Builder locale = new Locale.Builder().setLocale(localeInfo.getLocale());
        forLanguageTag
                .getUnicodeLocaleKeys()
                .forEach(
                        new Consumer() { // from class:
                                         // com.android.settings.localepicker.LocaleListEditor$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                Locale.Builder builder = locale;
                                Locale locale2 = forLanguageTag;
                                String str2 = (String) obj;
                                BaseSearchIndexProvider baseSearchIndexProvider =
                                        LocaleListEditor.SEARCH_INDEX_DATA_PROVIDER;
                                builder.setUnicodeLocaleKeyword(
                                        str2, locale2.getUnicodeLocaleType(str2));
                            }
                        });
        LocaleStore.LocaleInfo fromLocale = LocaleStore.fromLocale(locale.build());
        fromLocale.setTranslated(localeInfo.isTranslated());
        return fromLocale;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return GeneralDeviceSettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return FileType.XDW;
    }

    public NotificationController getNotificationController() {
        return NotificationController.getInstance(getContext());
    }

    public String[] getSupportedLocales() {
        return LocalePicker.getSupportedLocales(getContext());
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_general";
    }

    public final boolean handleOnBackPressed() {
        LocaleDragAndDropAdapter localeDragAndDropAdapter = this.mAdapter;
        if (localeDragAndDropAdapter.mSelectMode) {
            localeDragAndDropAdapter.updateLanguageViewState(1);
            this.mAdapter.initializeSelectBoxState();
            return false;
        }
        if (!localeDragAndDropAdapter.mEditMode) {
            return false;
        }
        localeDragAndDropAdapter.doTheUpdate();
        this.mAdapter.updateLanguageViewState(1);
        this.mAdapter.initializeSelectBoxState();
        updateVisibilityOfRemoveMenu();
        onCheckedChange();
        return true;
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getView()
                .addOnLayoutChangeListener(
                        new View
                                .OnLayoutChangeListener() { // from class:
                                                            // com.android.settings.localepicker.LocaleListEditor.2
                            @Override // android.view.View.OnLayoutChangeListener
                            public final void onLayoutChange(
                                    View view,
                                    int i,
                                    int i2,
                                    int i3,
                                    int i4,
                                    int i5,
                                    int i6,
                                    int i7,
                                    int i8) {
                                LinearLayout linearLayout;
                                AlertDialog alertDialog =
                                        LocaleListEditor.this.mAdapter.mNofiticationDialog;
                                if (alertDialog == null
                                        || !alertDialog.isShowing()
                                        || (linearLayout = LocaleListEditor.this.mAddLanguageLayout)
                                                == null) {
                                    return;
                                }
                                alertDialog.semSetAnchor(linearLayout);
                            }
                        });
        this.mSettingsBaseActivity
                .getLifecycle()
                .removeObserver(this.mSettingsBaseActivity.mCategoryMixin);
    }

    @Override // com.android.settings.RestrictedSettingsFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        if (i == 0 && i2 == -1 && intent != null) {
            LocaleStore.LocaleInfo serializableExtra = intent.getSerializableExtra("localeInfo");
            if (serializableExtra != null) {
                String string =
                        Settings.System.getString(
                                getContext().getContentResolver(), "locale_preferences");
                LocaleDragAndDropAdapter localeDragAndDropAdapter = this.mAdapter;
                LocaleStore.LocaleInfo mayAppendUnicodeTags =
                        mayAppendUnicodeTags(serializableExtra, string);
                localeDragAndDropAdapter.getClass();
                localeDragAndDropAdapter.mTempAddLocaleId = mayAppendUnicodeTags.getId();
                localeDragAndDropAdapter.showSetDefaultLocaleDialog(mayAppendUnicodeTags);
                updateVisibilityOfRemoveMenu();
            }
            this.mMetricsFeatureProvider.action(getContext(), 1831, new Pair[0]);
        }
        super.onActivityResult(i, i2, intent);
    }

    public final void onCheckedChange() {
        if (this.mAdapter.getCheckedCount$1() < 1 || !this.mAdapter.mEditMode) {
            this.removeButtonLayout.setVisibility(8);
        } else {
            this.removeButtonLayout.setVisibility(0);
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int id = view.getId();
        if (id == R.id.apply_button) {
            this.mAdapter.doTheUpdate();
            this.mAdapter.updateLanguageViewState(1);
            LoggingHelper.insertEventLogging(FileType.XDW, 8023);
        } else if (id == R.id.button_bar) {
            this.mAdapter.removeChecked();
        }
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        addPreferencesFromResource(R.xml.languages);
        FragmentActivity activity = getActivity();
        this.mLocaleHelperPreferenceController = new LocaleHelperPreferenceController(activity);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        this.mLocaleHelperPreferenceController.displayPreference(preferenceScreen);
        new TermsOfAddressCategoryController(activity, "key_category_terms_of_address")
                .displayPreference(preferenceScreen);
        LocaleStore.fillCache(getContext());
        ArrayList arrayList = new ArrayList();
        LocaleList locales = LocalePicker.getLocales();
        for (int i = 0; i < locales.size(); i++) {
            arrayList.add(LocaleStore.getLocaleInfo(locales.get(i)));
        }
        this.mAdapter = new LocaleDragAndDropAdapter(this, arrayList);
        boolean isShopDemo = Rune.isShopDemo(getContext());
        boolean isLDUModel = Rune.isLDUModel();
        if (isShopDemo || isLDUModel) {
            Context context = getContext();
            AlertDialog alertDialog = this.mNofiticationDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
                this.mNofiticationDialog = null;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.status_bar_latest_events_title);
            builder.setMessage(R.string.language_settings_noti_desc);
            builder.setPositiveButton(R.string.dlg_ok, new AnonymousClass5(1));
            AlertDialog create = builder.create();
            this.mNofiticationDialog = create;
            create.show();
        }
        this.mSettingsBaseActivity = (SettingsBaseActivity) getActivity();
        this.mAdapter.initializeSelectBoxState();
        requireActivity()
                .getOnBackPressedDispatcher()
                .addCallback(
                        this,
                        new OnBackPressedCallback() { // from class:
                                                      // com.android.settings.localepicker.LocaleListEditor.1
                            @Override // androidx.activity.OnBackPressedCallback
                            public final void handleOnBackPressed() {
                                LocaleListEditor localeListEditor = LocaleListEditor.this;
                                LocaleDragAndDropAdapter localeDragAndDropAdapter =
                                        localeListEditor.mAdapter;
                                if (localeDragAndDropAdapter.mEditMode
                                        || localeDragAndDropAdapter.mSelectMode) {
                                    localeListEditor.handleOnBackPressed();
                                } else {
                                    setEnabled(false);
                                    localeListEditor
                                            .requireActivity()
                                            .getOnBackPressedDispatcher()
                                            .onBackPressed();
                                }
                            }
                        });
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        this.mMenu = menu;
        this.mAdapter.mMenu = menu;
        MenuItem add = menu.add(0, 2, 0, R.string.common_edit);
        if (!add.isVisible()) {
            add.setShowAsAction(2);
        }
        updateVisibilityOfRemoveMenu();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        View inflate =
                layoutInflater.inflate(R.layout.sec_locale_order_list, (ViewGroup) onCreateView);
        this.mApplyButton = (SecRoundButtonView) inflate.findViewById(R.id.apply_button);
        this.mApplyButtonLayout = (LinearLayout) inflate.findViewById(R.id.apply_btn_layout);
        this.mAddLanguageLayout = (LinearLayout) inflate.findViewById(R.id.add_language_layout);
        this.mApplyButton.setOnClickListener(this);
        LocaleDragAndDropAdapter localeDragAndDropAdapter = this.mAdapter;
        localeDragAndDropAdapter.mApplyBtnLayout = this.mApplyButtonLayout;
        localeDragAndDropAdapter.mAddLanguageBtnLayout = this.mAddLanguageLayout;
        localeDragAndDropAdapter.mListener = this;
        this.removeButtonLayout =
                (RelativeLayout) this.mSettingsBaseActivity.findViewById(R.id.button_bar);
        BottomNavigationView bottomNavigationView =
                (BottomNavigationView)
                        ((LayoutInflater)
                                        this.mSettingsBaseActivity.getSystemService(
                                                "layout_inflater"))
                                .inflate(
                                        R.layout.sec_bottom_naviagtion_delete_layout,
                                        (ViewGroup) this.removeButtonLayout,
                                        false);
        bottomNavigationView.selectedListener =
                new BottomNavigationView
                        .OnNavigationItemSelectedListener() { // from class:
                                                              // com.android.settings.localepicker.LocaleListEditor.3
                    @Override // com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
                    public final boolean onNavigationItemSelected(MenuItem menuItem) {
                        LocaleListEditor.this.showRemoveLocaleWarningDialog();
                        return false;
                    }
                };
        this.removeButtonLayout.addView(bottomNavigationView);
        NestedScrollView nestedScrollView =
                (NestedScrollView) inflate.findViewById(R.id.nested_scroll_view);
        nestedScrollView.semSetRoundedCorners(15);
        nestedScrollView.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        nestedScrollView.semSetRoundedCornerOffset(Utils.getListHorizontalPadding(getContext()));
        int dimensionPixelSize =
                getContext()
                        .getResources()
                        .getDimensionPixelSize(R.dimen.sec_nested_view_scroll_padding);
        nestedScrollView.mScrollbarTopPadding = dimensionPixelSize;
        nestedScrollView.mScrollbarBottomPadding = dimensionPixelSize;
        SeslViewReflector.semSetScrollBarTopPadding(nestedScrollView, dimensionPixelSize);
        SeslViewReflector.semSetScrollBarBottomPadding(
                nestedScrollView, nestedScrollView.mScrollbarBottomPadding);
        ((AppCompatButton) this.mAddLanguageLayout.findViewById(R.id.add_language))
                .setStateListAnimator(null);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.locale_recycler_view);
        LocaleLinearLayoutManager localeLinearLayoutManager =
                new LocaleLinearLayoutManager(getContext(), this.mAdapter);
        localeLinearLayoutManager.mAutoMeasure = true;
        recyclerView.setLayoutManager(localeLinearLayoutManager);
        recyclerView.setHasFixedSize(true);
        LocaleDragAndDropAdapter localeDragAndDropAdapter2 = this.mAdapter;
        localeDragAndDropAdapter2.mParentView = recyclerView;
        localeDragAndDropAdapter2.mItemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(this.mAdapter);
        recyclerView.requestFocus();
        recyclerView.semSetRoundedCorners(3);
        recyclerView.semSetRoundedCornerColor(
                3, getContext().getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mAdapter.updateRecyclerViewDivider(1);
        this.mAddLanguageLayout.semSetRoundedCorners(12);
        this.mAddLanguageLayout.semSetRoundedCornerColor(
                12, getContext().getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        View findViewById = inflate.findViewById(R.id.add_language);
        this.mAddLanguage = findViewById;
        findViewById.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.localepicker.LocaleListEditor.13
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                        if (featureFactoryImpl == null) {
                            throw new UnsupportedOperationException(
                                    "No feature factory configured");
                        }
                        SettingsMetricsFeatureProvider metricsFeatureProvider =
                                featureFactoryImpl.getMetricsFeatureProvider();
                        LocaleListEditor.this.getClass();
                        metricsFeatureProvider.logSettingsTileClick(FileType.XDW, "add_language");
                        Intent intent =
                                new Intent(
                                        LocaleListEditor.this.getActivity(),
                                        (Class<?>) LocalePickerWithRegionActivity.class);
                        intent.putExtras(
                                LocaleListEditor.this.getActivity().getIntent().getExtras());
                        LocaleListEditor.this.startActivityForResult(intent, 0);
                    }
                });
        return onCreateView;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId != 2) {
            return itemId != 16908332
                    ? super.onOptionsItemSelected(menuItem)
                    : handleOnBackPressed();
        }
        if (this.mRemoveMode) {
            showRemoveLocaleWarningDialog();
        } else {
            LoggingHelper.insertEventLogging(FileType.XDW, 8011);
            setEditMode$1(true);
        }
        return true;
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        boolean z = this.mIsUiRestricted;
        this.mIsUiRestricted = isUiRestricted();
        TextView textView = (TextView) getActivity().findViewById(R.id.locale_empty_view);
        boolean z2 = this.mIsUiRestricted;
        if (z2 && !z) {
            textView.setText(R.string.language_empty_list_user_restricted);
            textView.setVisibility(0);
            updateVisibilityOfRemoveMenu();
        } else if (!z2 && z) {
            textView.setVisibility(8);
            updateVisibilityOfRemoveMenu();
        }
        ((SettingsActivity) getActivity()).setTitle(R.string.phone_language);
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("localeEditMode", this.mAdapter.mEditMode);
        bundle.putBoolean("showingLocaleRemoveDialog", this.mShowingRemoveDialog);
        bundle.putBoolean("localeAdded", this.mLocaleAdditionMode);
        LocaleDragAndDropAdapter localeDragAndDropAdapter = this.mAdapter;
        localeDragAndDropAdapter.getClass();
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayList2 = new ArrayList<>();
        for (LocaleStore.LocaleInfo localeInfo : localeDragAndDropAdapter.mFeedItemList) {
            if (localeInfo.getChecked()) {
                arrayList.add(localeInfo.getId());
            }
            if (localeDragAndDropAdapter.mEditMode) {
                arrayList2.add(localeInfo.getId());
            }
        }
        bundle.putStringArrayList("selectedLocales", arrayList);
        if (localeDragAndDropAdapter.mEditMode) {
            bundle.putStringArrayList("order_locales", arrayList2);
        }
        if (localeDragAndDropAdapter.mEditMode) {
            return;
        }
        bundle.putBoolean("showingLocaleAddDialog", localeDragAndDropAdapter.mShowingAddDialog);
        if (!localeDragAndDropAdapter.mShowingAddDialog
                || localeDragAndDropAdapter.mTempAddLocaleId.isEmpty()) {
            return;
        }
        bundle.putString("tempAddLocaleID", localeDragAndDropAdapter.mTempAddLocaleId);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (bundle != null) {
            setEditMode$1(bundle.getBoolean("localeEditMode"));
            this.mShowingRemoveDialog = bundle.getBoolean("showingLocaleRemoveDialog", false);
            this.mLocaleAdditionMode = bundle.getBoolean("localeAdded", false);
        }
        this.mAdapter.restoreState$1(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewStateRestored(Bundle bundle) {
        super.onViewStateRestored(bundle);
        if (bundle != null) {
            bundle.getBoolean("localeEditMode", false);
            this.mShowingRemoveDialog = bundle.getBoolean("showingLocaleRemoveDialog", false);
            this.mLocaleAdditionMode = bundle.getBoolean("localeAdded", false);
        }
        boolean z = this.mRemoveMode;
        this.mRemoveMode = z;
        LocaleDragAndDropAdapter localeDragAndDropAdapter = this.mAdapter;
        localeDragAndDropAdapter.mRemoveMode = z;
        int size = localeDragAndDropAdapter.mFeedItemList.size();
        for (int i = 0; i < size; i++) {
            ((LocaleStore.LocaleInfo) localeDragAndDropAdapter.mFeedItemList.get(i))
                    .setChecked(false);
            localeDragAndDropAdapter.notifyItemChanged(i);
        }
        this.mAddLanguage.setVisibility(z ? 4 : 0);
        updateVisibilityOfRemoveMenu();
        this.mAdapter.restoreState$1(bundle);
        if (this.mShowingRemoveDialog) {
            showRemoveLocaleWarningDialog();
        } else {
            onCheckedChange();
        }
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("LocaleAdditionMode:"),
                this.mLocaleAdditionMode,
                "LocaleListEditor");
        if (this.mLocaleAdditionMode) {
            return;
        }
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra("system_locale_dialog_type");
        String stringExtra2 = intent.getStringExtra("app_locale");
        if (getContext().getPackageName().equals(getActivity().getCallingPackage())
                && "locale_suggestion".equals(stringExtra)
                && !TextUtils.isEmpty(stringExtra2)) {
            for (String str : getSupportedLocales()) {
                if (str.equals(stringExtra2)) {
                    if (LocaleUtils.isInSystemLocale(stringExtra2)) {
                        return;
                    }
                    Log.d("LocaleListEditor", "show confirmation dialog");
                    Intent intent2 = getIntent();
                    intent2.getStringExtra("system_locale_dialog_type");
                    LocaleStore.getLocaleInfo(
                            Locale.forLanguageTag(intent2.getStringExtra("app_locale")));
                    this.mLocaleAdditionMode = true;
                    return;
                }
            }
        }
    }

    public final void setEditMode$1(boolean z) {
        if (z) {
            this.mAdapter.updateLanguageViewState(3);
        }
        updateVisibilityOfRemoveMenu();
    }

    public void showRemoveLocaleWarningDialog() {
        String quantityString;
        String string;
        String str;
        int checkedCount$1 = this.mAdapter.getCheckedCount$1();
        if (checkedCount$1 == 0) {
            setEditMode$1(!this.mAdapter.mEditMode);
            return;
        }
        if (checkedCount$1 == this.mAdapter.getItemCount()) {
            this.mShowingRemoveDialog = true;
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.dlg_remove_locales_error_title);
            builder.setMessage(R.string.dlg_remove_locales_error_message);
            builder.setPositiveButton(android.R.string.ok, new AnonymousClass5(0));
            final int i = 0;
            builder.P.mOnDismissListener =
                    new DialogInterface.OnDismissListener(
                            this) { // from class:
                                    // com.android.settings.localepicker.LocaleListEditor.4
                        public final /* synthetic */ LocaleListEditor this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnDismissListener
                        public final void onDismiss(DialogInterface dialogInterface) {
                            switch (i) {
                                case 0:
                                    LocaleListEditor localeListEditor = this.this$0;
                                    localeListEditor.mShowingRemoveDialog = false;
                                    localeListEditor.updateVisibilityOfRemoveMenu();
                                    this.this$0.onCheckedChange();
                                    break;
                                default:
                                    LocaleListEditor localeListEditor2 = this.this$0;
                                    localeListEditor2.mShowingRemoveDialog = false;
                                    localeListEditor2.updateVisibilityOfRemoveMenu();
                                    this.this$0.onCheckedChange();
                                    break;
                            }
                        }
                    };
            builder.create().show();
            return;
        }
        if (((LocaleStore.LocaleInfo) this.mAdapter.mFeedItemList.get(0)).getChecked()) {
            if (checkedCount$1 == 1) {
                quantityString =
                        getResources()
                                .getString(
                                        R.string.remove_language_title_single,
                                        ((LocaleStore.LocaleInfo)
                                                        this.mAdapter.mFeedItemList.get(0))
                                                .getSecFullNameNative());
                string =
                        getResources()
                                .getString(
                                        R.string.remove_language_message,
                                        this.mAdapter.getDefaultLangugeNameAfterDelete());
            } else {
                if (checkedCount$1 > 1) {
                    quantityString =
                            getResources()
                                    .getQuantityString(
                                            R.plurals.remove_language_title_multiple_plurals,
                                            checkedCount$1,
                                            Integer.valueOf(checkedCount$1));
                    string =
                            getResources()
                                    .getString(
                                            R.string.remove_language_message,
                                            this.mAdapter.getDefaultLangugeNameAfterDelete());
                }
                quantityString = ApnSettings.MVNO_NONE;
                string = ApnSettings.MVNO_NONE;
            }
        } else if (checkedCount$1 == 1) {
            Resources resources = getResources();
            LocaleDragAndDropAdapter localeDragAndDropAdapter = this.mAdapter;
            int size = localeDragAndDropAdapter.mFeedItemList.size();
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    str = null;
                    break;
                } else {
                    if (((LocaleStore.LocaleInfo) localeDragAndDropAdapter.mFeedItemList.get(i2))
                            .getChecked()) {
                        str =
                                ((LocaleStore.LocaleInfo)
                                                localeDragAndDropAdapter.mFeedItemList.get(i2))
                                        .getSecFullNameNative();
                        break;
                    }
                    i2++;
                }
            }
            quantityString = resources.getString(R.string.remove_language_title_single, str);
            string =
                    getResources()
                            .getString(
                                    R.string.remove_language_message_continue,
                                    ((LocaleStore.LocaleInfo) this.mAdapter.mFeedItemList.get(0))
                                            .getSecFullNameNative());
        } else {
            if (checkedCount$1 > 1) {
                quantityString =
                        getResources()
                                .getQuantityString(
                                        R.plurals.remove_language_title_multiple_plurals,
                                        checkedCount$1,
                                        Integer.valueOf(checkedCount$1));
                string =
                        getResources()
                                .getString(
                                        R.string.remove_language_message_continue,
                                        ((LocaleStore.LocaleInfo)
                                                        this.mAdapter.mFeedItemList.get(0))
                                                .getSecFullNameNative());
            }
            quantityString = ApnSettings.MVNO_NONE;
            string = ApnSettings.MVNO_NONE;
        }
        this.mShowingRemoveDialog = true;
        AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
        AlertController.AlertParams alertParams = builder2.P;
        alertParams.mTitle = quantityString;
        alertParams.mMessage = string;
        builder2.setNegativeButton(android.R.string.no, new AnonymousClass5(2));
        builder2.setPositiveButton(
                R.string.common_remove,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.localepicker.LocaleListEditor.7
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i3) {
                        LocaleListEditor localeListEditor = LocaleListEditor.this;
                        localeListEditor.mShowingRemoveDialog = false;
                        localeListEditor.mAdapter.removeChecked();
                        LocaleListEditor.this.setEditMode$1(false);
                        LocaleListEditor.this.mAdapter.updateLanguageViewState(1);
                        LocaleListEditor.this.getClass();
                        LoggingHelper.insertEventLogging(FileType.XDW, 8013);
                    }
                });
        final int i3 = 1;
        alertParams.mOnDismissListener =
                new DialogInterface.OnDismissListener(
                        this) { // from class: com.android.settings.localepicker.LocaleListEditor.4
                    public final /* synthetic */ LocaleListEditor this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        switch (i3) {
                            case 0:
                                LocaleListEditor localeListEditor = this.this$0;
                                localeListEditor.mShowingRemoveDialog = false;
                                localeListEditor.updateVisibilityOfRemoveMenu();
                                this.this$0.onCheckedChange();
                                break;
                            default:
                                LocaleListEditor localeListEditor2 = this.this$0;
                                localeListEditor2.mShowingRemoveDialog = false;
                                localeListEditor2.updateVisibilityOfRemoveMenu();
                                this.this$0.onCheckedChange();
                                break;
                        }
                    }
                };
        AlertDialog create = builder2.create();
        this.mRemoveAlertDialog = create;
        create.setOnShowListener(
                new DialogInterface
                        .OnShowListener() { // from class:
                                            // com.android.settings.localepicker.LocaleListEditor.9
                    @Override // android.content.DialogInterface.OnShowListener
                    public final void onShow(DialogInterface dialogInterface) {
                        TextView textView;
                        int identifier =
                                LocaleListEditor.this
                                        .getResources()
                                        .getIdentifier(
                                                "alertTitle",
                                                "id",
                                                LocaleListEditor.this
                                                        .getActivity()
                                                        .getPackageName());
                        if (identifier > 0
                                && (textView =
                                                (TextView)
                                                        LocaleListEditor.this.mRemoveAlertDialog
                                                                .findViewById(identifier))
                                        != null) {
                            textView.setTextDirection(5);
                        }
                        TextView textView2 =
                                (TextView)
                                        LocaleListEditor.this.mRemoveAlertDialog.findViewById(
                                                android.R.id.message);
                        if (textView2 != null) {
                            textView2.setTextDirection(5);
                        }
                    }
                });
        this.mRemoveAlertDialog.show();
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001e, code lost:

       if (r4.getItemCount() > 1) goto L13;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateVisibilityOfRemoveMenu() {
        /*
            r4 = this;
            android.view.Menu r0 = r4.mMenu
            if (r0 != 0) goto L5
            return
        L5:
            r1 = 2
            android.view.MenuItem r0 = r0.findItem(r1)
            if (r0 == 0) goto L2c
            r2 = 2132019635(0x7f1409b3, float:1.967761E38)
            r0.setTitle(r2)
            com.android.settings.localepicker.LocaleDragAndDropAdapter r4 = r4.mAdapter
            boolean r2 = r4.mEditMode
            r3 = 0
            if (r2 != 0) goto L21
            int r4 = r4.getItemCount()
            r2 = 1
            if (r4 <= r2) goto L21
            goto L22
        L21:
            r2 = r3
        L22:
            if (r2 == 0) goto L25
            goto L26
        L25:
            r1 = r3
        L26:
            r0.setShowAsAction(r1)
            r0.setVisible(r2)
        L2c:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.localepicker.LocaleListEditor.updateVisibilityOfRemoveMenu():void");
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.localepicker.LocaleListEditor$5, reason: invalid class name */
    public final class AnonymousClass5 implements DialogInterface.OnClickListener {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ AnonymousClass5(int i) {
            this.$r8$classId = i;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            int i2 = this.$r8$classId;
        }

        private final void onClick$com$android$settings$localepicker$LocaleListEditor$14(
                DialogInterface dialogInterface, int i) {}

        private final void onClick$com$android$settings$localepicker$LocaleListEditor$5(
                DialogInterface dialogInterface, int i) {}

        private final void onClick$com$android$settings$localepicker$LocaleListEditor$8(
                DialogInterface dialogInterface, int i) {}
    }
}
