package com.android.settings.dream;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.dream.DreamBackend;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DreamPickerController extends BasePreferenceController {
    public static final String PREF_KEY = "dream_picker";
    private DreamBackend.DreamInfo mActiveDream;
    private DreamAdapter mAdapter;
    private final DreamBackend mBackend;
    private final HashSet<Callback> mCallbacks;
    private final List<DreamBackend.DreamInfo> mDreamInfos;
    private final MetricsFeatureProvider mMetricsFeatureProvider;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Callback {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DreamItem {
        public final DreamBackend.DreamInfo mDreamInfo;

        public DreamItem(DreamBackend.DreamInfo dreamInfo) {
            this.mDreamInfo = dreamInfo;
        }

        public final boolean isActive() {
            DreamPickerController dreamPickerController = DreamPickerController.this;
            if (!dreamPickerController.mAdapter.mEnabled
                    || dreamPickerController.mActiveDream == null) {
                return false;
            }
            return this.mDreamInfo.componentName.equals(
                    dreamPickerController.mActiveDream.componentName);
        }
    }

    public DreamPickerController(Context context) {
        this(context, DreamBackend.getInstance(context));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ DreamItem lambda$displayPreference$0(DreamBackend.DreamInfo dreamInfo) {
        return new DreamItem(dreamInfo);
    }

    public void addCallback(Callback callback) {
        this.mCallbacks.add(callback);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        DreamAdapter dreamAdapter =
                new DreamAdapter(
                        (List)
                                this.mDreamInfos.stream()
                                        .map(
                                                new Function() { // from class:
                                                                 // com.android.settings.dream.DreamPickerController$$ExternalSyntheticLambda0
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        DreamPickerController.DreamItem
                                                                lambda$displayPreference$0;
                                                        lambda$displayPreference$0 =
                                                                DreamPickerController.this
                                                                        .lambda$displayPreference$0(
                                                                                (DreamBackend
                                                                                                .DreamInfo)
                                                                                        obj);
                                                        return lambda$displayPreference$0;
                                                    }
                                                })
                                        .collect(Collectors.toList()));
        this.mAdapter = dreamAdapter;
        boolean isEnabled = this.mBackend.isEnabled();
        if (dreamAdapter.mEnabled != isEnabled) {
            dreamAdapter.mEnabled = isEnabled;
            dreamAdapter.notifyDataSetChanged();
        }
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference(getPreferenceKey());
        if (layoutPreference == null) {
            return;
        }
        RecyclerView recyclerView =
                (RecyclerView) layoutPreference.mRootView.findViewById(R.id.dream_list);
        recyclerView.setLayoutManager(new AutoFitGridLayoutManager(this.mContext));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(this.mContext));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(this.mAdapter);
    }

    public DreamBackend.DreamInfo getActiveDreamInfo() {
        return this.mActiveDream;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mDreamInfos.size() > 0 ? 0 : 2;
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

    public void removeCallback(Callback callback) {
        this.mCallbacks.remove(callback);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        boolean isEnabled;
        super.updateState(preference);
        DreamAdapter dreamAdapter = this.mAdapter;
        if (dreamAdapter == null || dreamAdapter.mEnabled == (isEnabled = preference.isEnabled())) {
            return;
        }
        dreamAdapter.mEnabled = isEnabled;
        dreamAdapter.notifyDataSetChanged();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public DreamPickerController(Context context, DreamBackend dreamBackend) {
        super(context, PREF_KEY);
        this.mCallbacks = new HashSet<>();
        this.mBackend = dreamBackend;
        List<DreamBackend.DreamInfo> dreamInfos = dreamBackend.getDreamInfos();
        this.mDreamInfos = dreamInfos;
        this.mActiveDream = getActiveDreamInfo(dreamInfos);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    private static DreamBackend.DreamInfo getActiveDreamInfo(List<DreamBackend.DreamInfo> list) {
        return list.stream()
                .filter(new DreamPickerController$$ExternalSyntheticLambda1())
                .findFirst()
                .orElse(null);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
