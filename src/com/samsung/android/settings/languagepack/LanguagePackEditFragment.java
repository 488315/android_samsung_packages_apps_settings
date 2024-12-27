package com.samsung.android.settings.languagepack;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.SecPreferenceCategory;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.samsung.android.settings.languagepack.LanguagePackEditFragment.AnonymousClass2;
import com.samsung.android.settings.languagepack.data.LanguageInfo;
import com.samsung.android.settings.languagepack.data.LanguageInfo$$ExternalSyntheticLambda0;
import com.samsung.android.settings.languagepack.logger.Log;
import com.samsung.android.settings.languagepack.manager.LanguagePackManager;
import com.samsung.android.settings.languagepack.service.LanguagePackDownloadService;
import com.samsung.android.settings.logging.LoggingHelper;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LanguagePackEditFragment extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public List mBaseList;
    public LanguagePackManager mLanguagePackManager;
    public int mLanguagePackType;
    public RelativeLayout mRemoveButtonLayout;
    public List mList = null;
    public ArrayList mSelectionChecklist = null;
    public final AnonymousClass1 mServiceConnection = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.languagepack.LanguagePackEditFragment$1, reason: invalid class name */
    public final class AnonymousClass1 implements ServiceConnection {
        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            int i = LanguagePackEditFragment.$r8$clinit;
            Log.d("LanguagePackEditFragment", "onServiceConnected() : " + iBinder);
            LanguagePackDownloadService.mLanguagePackService =
                    (LanguagePackDownloadService.LanguagePackServiceBinder) iBinder;
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            int i = LanguagePackEditFragment.$r8$clinit;
            Log.d("LanguagePackEditFragment", "onServiceDisconnected() : " + componentName);
            LanguagePackDownloadService.mLanguagePackService = null;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.languagepack.LanguagePackEditFragment$2, reason: invalid class name */
    public final class AnonymousClass2 implements DialogInterface.OnClickListener {
        public AnonymousClass2() {}

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            LanguagePackEditFragment.this.getClass();
            LoggingHelper.insertEventLogging(8141, 8144);
            final int i2 = 0;
            final List list =
                    (List)
                            LanguagePackEditFragment.this.mSelectionChecklist.stream()
                                    .filter(
                                            new Predicate() { // from class:
                                                              // com.samsung.android.settings.languagepack.LanguagePackEditFragment$2$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Predicate
                                                public final boolean test(Object obj) {
                                                    CheckBoxPreference checkBoxPreference =
                                                            (CheckBoxPreference) obj;
                                                    switch (i2) {
                                                        case 0:
                                                            return !checkBoxPreference.mChecked;
                                                        default:
                                                            return checkBoxPreference.mChecked;
                                                    }
                                                }
                                            })
                                    .map(
                                            new Function() { // from class:
                                                             // com.samsung.android.settings.languagepack.LanguagePackEditFragment$2$$ExternalSyntheticLambda1
                                                @Override // java.util.function.Function
                                                public final Object apply(Object obj) {
                                                    return LanguagePackEditFragment.this
                                                            .mLanguagePackManager
                                                            .getLanguageInfo(
                                                                    ((CheckBoxPreference) obj)
                                                                            .getKey())
                                                            .getTranslationPackageName();
                                                }
                                            })
                                    .collect(Collectors.toList());
            final int i3 = 1;
            LanguagePackEditFragment.this.mSelectionChecklist.stream()
                    .filter(
                            new Predicate() { // from class:
                                              // com.samsung.android.settings.languagepack.LanguagePackEditFragment$2$$ExternalSyntheticLambda0
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    CheckBoxPreference checkBoxPreference =
                                            (CheckBoxPreference) obj;
                                    switch (i3) {
                                        case 0:
                                            return !checkBoxPreference.mChecked;
                                        default:
                                            return checkBoxPreference.mChecked;
                                    }
                                }
                            })
                    .forEach(
                            new Consumer() { // from class:
                                             // com.samsung.android.settings.languagepack.LanguagePackEditFragment$2$$ExternalSyntheticLambda3
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    LanguagePackEditFragment.AnonymousClass2 anonymousClass2 =
                                            LanguagePackEditFragment.AnonymousClass2.this;
                                    List list2 = list;
                                    LanguageInfo languageInfo =
                                            LanguagePackEditFragment.this.mLanguagePackManager
                                                    .getLanguageInfo(
                                                            ((CheckBoxPreference) obj).getKey());
                                    boolean contains =
                                            list2.contains(
                                                    languageInfo.getTranslationPackageName());
                                    int i4 = LanguagePackEditFragment.$r8$clinit;
                                    Log.d(
                                            "LanguagePackEditFragment",
                                            "uninstallLangePacks() "
                                                    + languageInfo.mLanguageCode
                                                    + " : "
                                                    + languageInfo.getTranslationPackageName()
                                                    + " : "
                                                    + contains);
                                    LanguagePackDownloadService.LanguagePackServiceBinder
                                            languagePackServiceBinder =
                                                    LanguagePackDownloadService
                                                            .mLanguagePackService;
                                    String packageName =
                                            LanguagePackEditFragment.this
                                                    .getContext()
                                                    .getPackageName();
                                    LanguagePackDownloadService.this.mLanguagePackManager
                                            .startUninstall(languageInfo.mLanguageCode, contains);
                                    if (TextUtils.isEmpty(packageName)) {
                                        return;
                                    }
                                    LanguagePackDownloadService.this.mResultBroadcastPackage =
                                            packageName;
                                }
                            });
            LanguagePackEditFragment.this.finish();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.languagepack.LanguagePackEditFragment$4, reason: invalid class name */
    public final class AnonymousClass4 implements DialogInterface.OnDismissListener {
        @Override // android.content.DialogInterface.OnDismissListener
        public final void onDismiss(DialogInterface dialogInterface) {
            dialogInterface.dismiss();
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 8141;
    }

    public final int getNumOfCheckedList$1() {
        ArrayList arrayList = this.mSelectionChecklist;
        int i = 0;
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (((CheckBoxPreference) it.next()).mChecked) {
                    i++;
                }
            }
        }
        return i;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateActionbarState$2();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mLanguagePackType = getArguments().getInt("type", 0);
        this.mLanguagePackManager = LanguagePackManager.getInstance(getContext());
        ArrayList arrayList = new ArrayList();
        try {
            arrayList.addAll(this.mLanguagePackManager.makeLanguageList());
        } catch (JSONException e) {
            Log.e(
                    "LanguagePackEditFragment",
                    "failed to make language info list : " + e.getMessage());
        }
        final int i = 0;
        List list =
                (List)
                        arrayList.stream()
                                .filter(
                                        new Predicate(
                                                this) { // from class:
                                                        // com.samsung.android.settings.languagepack.LanguagePackEditFragment$$ExternalSyntheticLambda1
                                            public final /* synthetic */ LanguagePackEditFragment
                                                    f$0;

                                            {
                                                this.f$0 = this;
                                            }

                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                int i2 = i;
                                                LanguagePackEditFragment languagePackEditFragment =
                                                        this.f$0;
                                                LanguageInfo languageInfo = (LanguageInfo) obj;
                                                switch (i2) {
                                                    case 0:
                                                        return languageInfo.showItemOnListWithType(
                                                                languagePackEditFragment
                                                                        .mLanguagePackType);
                                                    case 1:
                                                        List list2 =
                                                                languagePackEditFragment.mBaseList;
                                                        languageInfo.getClass();
                                                        return ((List)
                                                                                        list2
                                                                                                .stream()
                                                                                                .filter(
                                                                                                        new LanguageInfo$$ExternalSyntheticLambda0(
                                                                                                                languageInfo,
                                                                                                                0))
                                                                                                .collect(
                                                                                                        Collectors
                                                                                                                .toList()))
                                                                                .size()
                                                                        <= 1
                                                                || languageInfo
                                                                        .checkDownloadedInSettings(
                                                                                languagePackEditFragment
                                                                                        .getContext(),
                                                                                list2);
                                                    default:
                                                        int i3 =
                                                                LanguagePackEditFragment.$r8$clinit;
                                                        return languageInfo.hasUninstallablePackage(
                                                                languagePackEditFragment
                                                                        .getContext(),
                                                                languagePackEditFragment
                                                                        .mLanguagePackType);
                                                }
                                            }
                                        })
                                .collect(Collectors.toList());
        this.mBaseList = list;
        final int i2 = 1;
        Stream filter =
                list.stream()
                        .filter(
                                new Predicate(
                                        this) { // from class:
                                                // com.samsung.android.settings.languagepack.LanguagePackEditFragment$$ExternalSyntheticLambda1
                                    public final /* synthetic */ LanguagePackEditFragment f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        int i22 = i2;
                                        LanguagePackEditFragment languagePackEditFragment =
                                                this.f$0;
                                        LanguageInfo languageInfo = (LanguageInfo) obj;
                                        switch (i22) {
                                            case 0:
                                                return languageInfo.showItemOnListWithType(
                                                        languagePackEditFragment.mLanguagePackType);
                                            case 1:
                                                List list2 = languagePackEditFragment.mBaseList;
                                                languageInfo.getClass();
                                                return ((List)
                                                                                list2.stream()
                                                                                        .filter(
                                                                                                new LanguageInfo$$ExternalSyntheticLambda0(
                                                                                                        languageInfo,
                                                                                                        0))
                                                                                        .collect(
                                                                                                Collectors
                                                                                                        .toList()))
                                                                        .size()
                                                                <= 1
                                                        || languageInfo.checkDownloadedInSettings(
                                                                languagePackEditFragment
                                                                        .getContext(),
                                                                list2);
                                            default:
                                                int i3 = LanguagePackEditFragment.$r8$clinit;
                                                return languageInfo.hasUninstallablePackage(
                                                        languagePackEditFragment.getContext(),
                                                        languagePackEditFragment.mLanguagePackType);
                                        }
                                    }
                                });
        final int i3 = 2;
        this.mList =
                (List)
                        filter.filter(
                                        new Predicate(
                                                this) { // from class:
                                                        // com.samsung.android.settings.languagepack.LanguagePackEditFragment$$ExternalSyntheticLambda1
                                            public final /* synthetic */ LanguagePackEditFragment
                                                    f$0;

                                            {
                                                this.f$0 = this;
                                            }

                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                int i22 = i3;
                                                LanguagePackEditFragment languagePackEditFragment =
                                                        this.f$0;
                                                LanguageInfo languageInfo = (LanguageInfo) obj;
                                                switch (i22) {
                                                    case 0:
                                                        return languageInfo.showItemOnListWithType(
                                                                languagePackEditFragment
                                                                        .mLanguagePackType);
                                                    case 1:
                                                        List list2 =
                                                                languagePackEditFragment.mBaseList;
                                                        languageInfo.getClass();
                                                        return ((List)
                                                                                        list2
                                                                                                .stream()
                                                                                                .filter(
                                                                                                        new LanguageInfo$$ExternalSyntheticLambda0(
                                                                                                                languageInfo,
                                                                                                                0))
                                                                                                .collect(
                                                                                                        Collectors
                                                                                                                .toList()))
                                                                                .size()
                                                                        <= 1
                                                                || languageInfo
                                                                        .checkDownloadedInSettings(
                                                                                languagePackEditFragment
                                                                                        .getContext(),
                                                                                list2);
                                                    default:
                                                        int i32 =
                                                                LanguagePackEditFragment.$r8$clinit;
                                                        return languageInfo.hasUninstallablePackage(
                                                                languagePackEditFragment
                                                                        .getContext(),
                                                                languagePackEditFragment
                                                                        .mLanguagePackType);
                                                }
                                            }
                                        })
                                .collect(Collectors.toList());
        this.mSelectionChecklist = new ArrayList();
        addPreferencesFromResource(R.xml.sec_language_pack_edit);
        SecPreferenceCategory secPreferenceCategory =
                (SecPreferenceCategory) getPreferenceScreen().findPreference("edit_langpack_list");
        if (secPreferenceCategory == null) {
            return;
        }
        secPreferenceCategory.removeAll();
        for (LanguageInfo languageInfo : this.mList) {
            CheckBoxPreference checkBoxPreference = new CheckBoxPreference(getContext(), null);
            checkBoxPreference.setLayoutResource(R.layout.sec_langpack_edit_checkbox_preference);
            checkBoxPreference.setTitle(languageInfo.getDisplayName());
            checkBoxPreference.setKey(languageInfo.mLanguageCode);
            checkBoxPreference.setPersistent();
            checkBoxPreference.setOrder(languageInfo.mDisplayOrder);
            checkBoxPreference.setOnPreferenceChangeListener(this);
            checkBoxPreference.seslSetDividerStartOffset(
                    (int)
                            getContext()
                                    .getResources()
                                    .getDimension(
                                            R.dimen.sec_widget_app_list_divider_margin_start));
            secPreferenceCategory.addPreference(checkBoxPreference);
            this.mSelectionChecklist.add(checkBoxPreference);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        updateActionbarState$2();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        getActivity().unbindService(this.mServiceConnection);
        LanguagePackDownloadService.mLanguagePackService = null;
        super.onPause();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (!(preference instanceof CheckBoxPreference)) {
            return super.onPreferenceTreeClick(preference);
        }
        updateActionbarState$2();
        LoggingHelper.insertEventLogging(8141, 8142);
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        getActivity()
                .bindService(
                        new Intent(getActivity(), (Class<?>) LanguagePackDownloadService.class),
                        this.mServiceConnection,
                        1);
        super.onResume();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        FragmentActivity activity = getActivity();
        this.mRemoveButtonLayout = (RelativeLayout) activity.findViewById(R.id.button_bar);
        BottomNavigationView bottomNavigationView =
                (BottomNavigationView)
                        ((LayoutInflater) activity.getSystemService("layout_inflater"))
                                .inflate(
                                        R.layout.sec_langpack_bottom_navigation_remove_layout,
                                        (ViewGroup) this.mRemoveButtonLayout,
                                        false);
        bottomNavigationView.selectedListener =
                new BottomNavigationView
                        .OnNavigationItemSelectedListener() { // from class:
                                                              // com.samsung.android.settings.languagepack.LanguagePackEditFragment$$ExternalSyntheticLambda0
                    @Override // com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
                    public final boolean onNavigationItemSelected(MenuItem menuItem) {
                        int i = LanguagePackEditFragment.$r8$clinit;
                        LanguagePackEditFragment languagePackEditFragment =
                                LanguagePackEditFragment.this;
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(
                                        languagePackEditFragment.getContext(),
                                        R.style.langpack_dialog_uninstall_style);
                        String quantityString =
                                languagePackEditFragment
                                        .getContext()
                                        .getResources()
                                        .getQuantityString(
                                                R.plurals.sec_offline_lang_pack_remove_dialog_title,
                                                languagePackEditFragment.getNumOfCheckedList$1(),
                                                Integer.valueOf(
                                                        languagePackEditFragment
                                                                .getNumOfCheckedList$1()));
                        AlertController.AlertParams alertParams = builder.P;
                        alertParams.mTitle = quantityString;
                        builder.setMessage(R.string.sec_offline_lang_pack_remove_dialog_summary);
                        alertParams.mOnDismissListener =
                                new LanguagePackEditFragment.AnonymousClass4();
                        builder.setNegativeButton(
                                R.string.cancel, new LanguagePackEditFragment.AnonymousClass3());
                        builder.setPositiveButton(
                                R.string.delete, languagePackEditFragment.new AnonymousClass2());
                        builder.create().show();
                        LoggingHelper.insertEventLogging(8141, 8143);
                        return false;
                    }
                };
        this.mRemoveButtonLayout.addView(bottomNavigationView);
    }

    public final void updateActionbarState$2() {
        int numOfCheckedList$1 = getNumOfCheckedList$1();
        StringBuilder m =
                ListPopupWindow$$ExternalSyntheticOutline0.m(
                        numOfCheckedList$1, "updateActionbarState checked: ", ", size: ");
        m.append(this.mSelectionChecklist.size());
        Log.d("LanguagePackEditFragment", m.toString());
        if (numOfCheckedList$1 > 0) {
            this.mRemoveButtonLayout.setVisibility(0);
            this.mRemoveButtonLayout.findViewById(R.id.delete_btn).setVisibility(0);
        } else {
            this.mRemoveButtonLayout.setVisibility(8);
            this.mRemoveButtonLayout.findViewById(R.id.delete_btn).setVisibility(8);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.languagepack.LanguagePackEditFragment$3, reason: invalid class name */
    public final class AnonymousClass3 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {}
    }
}
