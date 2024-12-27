package com.android.settings.deviceinfo.storage;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.widget.SettingsSpinnerAdapter;
import com.android.settingslib.widget.SettingsSpinnerPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class StorageSelectionPreferenceController extends BasePreferenceController
        implements AdapterView.OnItemSelectedListener {
    private OnItemSelectedListener mOnItemSelectedListener;
    SettingsSpinnerPreference mSpinnerPreference;
    StorageAdapter mStorageAdapter;
    private final List<StorageEntry> mStorageEntries;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnItemSelectedListener {
        void onItemSelected(StorageEntry storageEntry);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class StorageAdapter extends SettingsSpinnerAdapter {
        @Override // android.widget.ArrayAdapter, android.widget.BaseAdapter,
                  // android.widget.SpinnerAdapter
        public final View getDropDownView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view =
                        this.mDefaultInflater.inflate(
                                R.layout.settings_spinner_dropdown_view, viewGroup, false);
            }
            try {
                TextView textView = (TextView) view;
                textView.setText(((StorageEntry) getItem(i)).getDescription());
                return textView;
            } catch (ClassCastException e) {
                throw new IllegalStateException("Default drop down view should be a TextView, ", e);
            }
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view =
                        this.mDefaultInflater.inflate(
                                R.layout.settings_spinner_view, viewGroup, false);
            }
            try {
                TextView textView = (TextView) view;
                textView.setText(((StorageEntry) getItem(i)).getDescription());
                return textView;
            } catch (ClassCastException e) {
                throw new IllegalStateException("Default view should be a TextView, ", e);
            }
        }
    }

    public StorageSelectionPreferenceController(Context context, String str) {
        super(context, str);
        this.mStorageEntries = new ArrayList();
        this.mStorageAdapter = new StorageAdapter(context);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        SettingsSpinnerPreference settingsSpinnerPreference =
                (SettingsSpinnerPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mSpinnerPreference = settingsSpinnerPreference;
        settingsSpinnerPreference.mAdapter = this.mStorageAdapter;
        settingsSpinnerPreference.notifyChanged();
        SettingsSpinnerPreference settingsSpinnerPreference2 = this.mSpinnerPreference;
        settingsSpinnerPreference2.mListener = this;
        settingsSpinnerPreference2.setVisible(this.mStorageAdapter.getCount() > 1);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 1;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        OnItemSelectedListener onItemSelectedListener = this.mOnItemSelectedListener;
        if (onItemSelectedListener == null) {
            return;
        }
        SettingsSpinnerPreference settingsSpinnerPreference = this.mSpinnerPreference;
        SettingsSpinnerAdapter settingsSpinnerAdapter = settingsSpinnerPreference.mAdapter;
        onItemSelectedListener.onItemSelected(
                (StorageEntry)
                        (settingsSpinnerAdapter == null
                                ? null
                                : settingsSpinnerAdapter.getItem(
                                        settingsSpinnerPreference.mPosition)));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.mOnItemSelectedListener = onItemSelectedListener;
    }

    public void setSelectedStorageEntry(StorageEntry storageEntry) {
        if (this.mSpinnerPreference == null || !this.mStorageEntries.contains(storageEntry)) {
            return;
        }
        SettingsSpinnerPreference settingsSpinnerPreference = this.mSpinnerPreference;
        int position = this.mStorageAdapter.getPosition(storageEntry);
        if (settingsSpinnerPreference.mPosition == position) {
            return;
        }
        settingsSpinnerPreference.mPosition = position;
        settingsSpinnerPreference.notifyChanged();
    }

    public void setStorageEntries(List<StorageEntry> list) {
        this.mStorageAdapter.clear();
        this.mStorageEntries.clear();
        if (list == null || list.isEmpty()) {
            return;
        }
        Collections.sort(this.mStorageEntries);
        this.mStorageEntries.addAll(list);
        this.mStorageAdapter.addAll(list);
        SettingsSpinnerPreference settingsSpinnerPreference = this.mSpinnerPreference;
        if (settingsSpinnerPreference != null) {
            settingsSpinnerPreference.setVisible(this.mStorageAdapter.getCount() > 1);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> adapterView) {}
}
