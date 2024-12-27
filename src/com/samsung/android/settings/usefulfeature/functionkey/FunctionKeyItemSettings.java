package com.samsung.android.settings.usefulfeature.functionkey;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.android.settings.R;
import com.android.settings.accessibility.shortcuts.EditShortcutsPreferenceFragment$$ExternalSyntheticLambda3;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.usefulfeature.functionkey.item.FunctionKeyAction;
import com.samsung.android.settings.usefulfeature.functionkey.item.FunctionKeyApplication;
import com.samsung.android.settings.usefulfeature.functionkey.item.FunctionKeyItem;
import com.samsung.android.settings.widget.SecCustomDividerItemDecorator;
import com.samsung.android.settings.widget.SecRadioButtonGearPreference;
import com.samsung.android.settings.widget.SecRadioButtonGearPreferenceControllersHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class FunctionKeyItemSettings extends DashboardFragment
        implements SecRadioButtonGearPreferenceControllersHandler.OnChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Context mContext;
    public SecRadioButtonGearPreferenceControllersHandler mControllersHandler;
    public int mPressType = -1;
    public String mApplicationKey = null;
    public List mFunctionKeyItems = new ArrayList();
    public final SettingsObserver mSettingsObserver = new SettingsObserver(new Handler());

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public final List mUris;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.mUris = new ArrayList();
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            SecRadioButtonGearPreferenceControllersHandler
                    secRadioButtonGearPreferenceControllersHandler =
                            FunctionKeyItemSettings.this.mControllersHandler;
            if (secRadioButtonGearPreferenceControllersHandler != null) {
                secRadioButtonGearPreferenceControllersHandler.updateStates(true);
            }
        }

        public final void setListening(boolean z) {
            FunctionKeyItemSettings functionKeyItemSettings = FunctionKeyItemSettings.this;
            int i = FunctionKeyItemSettings.$r8$clinit;
            ContentResolver contentResolver = functionKeyItemSettings.getContentResolver();
            if (!z) {
                contentResolver.unregisterContentObserver(this);
                return;
            }
            Iterator it = ((ArrayList) this.mUris).iterator();
            while (it.hasNext()) {
                contentResolver.registerContentObserver((Uri) it.next(), false, this);
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(final Context context) {
        final ArrayList arrayList = new ArrayList();
        List list = this.mFunctionKeyItems;
        if (list != null) {
            list.forEach(
                    new Consumer() { // from class:
                                     // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemSettings$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            FunctionKeyItemSettings functionKeyItemSettings =
                                    FunctionKeyItemSettings.this;
                            ArrayList arrayList2 = arrayList;
                            Context context2 = context;
                            FunctionKeyItem functionKeyItem = (FunctionKeyItem) obj;
                            int i = FunctionKeyItemSettings.$r8$clinit;
                            functionKeyItemSettings.getClass();
                            if (functionKeyItem instanceof FunctionKeyApplication) {
                                arrayList2.add(
                                        new FunctionKeyApplicationPreferenceController(
                                                context2,
                                                functionKeyItem.key,
                                                (FunctionKeyApplication) functionKeyItem,
                                                functionKeyItemSettings));
                            } else if (functionKeyItem instanceof FunctionKeyAction) {
                                arrayList2.add(
                                        new FunctionKeyActionPreferenceController(
                                                context2,
                                                functionKeyItem.key,
                                                (FunctionKeyAction) functionKeyItem));
                            }
                        }
                    });
        }
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 7613;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_function_key_item_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mPressType = arguments.getInt("pressType", -1);
            this.mApplicationKey = arguments.getString("applicationKey", null);
        }
        this.mFunctionKeyItems =
                FunctionKeyUtils.getFunctionKeyItems(
                        context, this.mPressType, this.mApplicationKey);
        super.onAttach(context);
    }

    public void onCheckedChanged(
            SecRadioButtonGearPreferenceControllersHandler
                    secRadioButtonGearPreferenceControllersHandler) {
        this.mControllersHandler.updateStates(true);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
        final SecRadioButtonGearPreferenceControllersHandler
                secRadioButtonGearPreferenceControllersHandler =
                        new SecRadioButtonGearPreferenceControllersHandler();
        this.mControllersHandler = secRadioButtonGearPreferenceControllersHandler;
        List list =
                getPreferenceControllers().stream()
                        .flatMap(new EditShortcutsPreferenceFragment$$ExternalSyntheticLambda3())
                        .filter(new FunctionKeyItemSettings$$ExternalSyntheticLambda0())
                        .toList();
        ((ArrayList) secRadioButtonGearPreferenceControllersHandler.mChildren).clear();
        if (list != null) {
            ((ArrayList) secRadioButtonGearPreferenceControllersHandler.mChildren).addAll(list);
            ((ArrayList) secRadioButtonGearPreferenceControllersHandler.mChildren)
                    .forEach(
                            new Consumer() { // from class:
                                             // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceControllersHandler$$ExternalSyntheticLambda1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    SecRadioButtonGearPreferenceControllersHandler
                                            secRadioButtonGearPreferenceControllersHandler2 =
                                                    SecRadioButtonGearPreferenceControllersHandler
                                                            .this;
                                    AbstractPreferenceController abstractPreferenceController =
                                            (AbstractPreferenceController) obj;
                                    secRadioButtonGearPreferenceControllersHandler2.getClass();
                                    if (abstractPreferenceController
                                            instanceof SecRadioButtonGearPreferenceController) {
                                        ((SecRadioButtonGearPreferenceController)
                                                        abstractPreferenceController)
                                                .setOnChangeListener(
                                                        secRadioButtonGearPreferenceControllersHandler2);
                                    }
                                }
                            });
        }
        this.mControllersHandler.mOnChangeListener = this;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        List list = this.mFunctionKeyItems;
        if (list != null) {
            ((ArrayList) list)
                    .forEach(
                            new Consumer() { // from class:
                                             // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemSettings$$ExternalSyntheticLambda2
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    FunctionKeyItemSettings functionKeyItemSettings =
                                            FunctionKeyItemSettings.this;
                                    int i = FunctionKeyItemSettings.$r8$clinit;
                                    functionKeyItemSettings.getClass();
                                    SecRadioButtonGearPreference secRadioButtonGearPreference =
                                            new SecRadioButtonGearPreference(
                                                    functionKeyItemSettings.getPrefContext(), null);
                                    secRadioButtonGearPreference.setKey(
                                            ((FunctionKeyItem) obj).key);
                                    functionKeyItemSettings
                                            .getPreferenceScreen()
                                            .addPreference(secRadioButtonGearPreference);
                                }
                            });
            displayResourceTilesToScreen(getPreferenceScreen());
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        SecRadioButtonGearPreferenceControllersHandler
                secRadioButtonGearPreferenceControllersHandler = this.mControllersHandler;
        if (secRadioButtonGearPreferenceControllersHandler != null) {
            secRadioButtonGearPreferenceControllersHandler.updateStates(true);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        this.mSettingsObserver.setListening(true);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        this.mSettingsObserver.setListening(false);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setDivider(null);
        getListView()
                .addItemDecoration(
                        new SecCustomDividerItemDecorator(
                                getResources().getDrawable(R.drawable.sec_top_level_list_divider),
                                this.mContext,
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
                                android.R.id.widget_frame,
                                android.R.id.checkbox));
    }
}
