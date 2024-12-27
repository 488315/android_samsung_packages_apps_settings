package com.android.settings.bluetooth;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.collection.ArraySet;
import androidx.core.graphics.drawable.IconCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.slice.ArrayUtils;
import androidx.slice.Slice;
import androidx.slice.SliceItem;
import androidx.slice.builders.SliceAction;
import androidx.slice.widget.SliceLiveData;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.sec.ims.IMSParameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BlockingPrefWithSliceController extends BasePreferenceController
        implements LifecycleObserver,
                OnStart,
                OnStop,
                Observer,
                BasePreferenceController.UiBlocker {
    private static final String PREFIX_KEY = "slice_preference_item_";
    private static final String TAG = "BlockingPrefWithSliceController";
    private List<Preference> mCurrentPreferencesList;
    String mExtraIntent;
    String mExtraPendingIntent;
    LiveData mLiveData;
    PreferenceCategory mPreferenceCategory;
    String mSliceIntentAction;
    String mSlicePendingIntentAction;
    private Uri mUri;

    public BlockingPrefWithSliceController(Context context, String str) {
        super(context, str);
        this.mCurrentPreferencesList = new ArrayList();
        this.mSliceIntentAction = ApnSettings.MVNO_NONE;
        this.mSlicePendingIntentAction = ApnSettings.MVNO_NONE;
        this.mExtraIntent = ApnSettings.MVNO_NONE;
        this.mExtraPendingIntent = ApnSettings.MVNO_NONE;
    }

    private Optional<Preference> createPreferenceItem(
            Optional<CharSequence> optional,
            Optional<CharSequence> optional2,
            Optional<SliceAction> optional3,
            int i) {
        Log.d(
                TAG,
                "Title: "
                        + ((Object) optional.orElse("no title"))
                        + ", Subtitle: "
                        + ((Object) optional2.orElse("no Subtitle"))
                        + ", Action: "
                        + optional3.orElse(null));
        if (!optional.isPresent()) {
            return Optional.empty();
        }
        String str = PREFIX_KEY + ((Object) optional.get());
        Preference findPreference = this.mPreferenceCategory.findPreference(str);
        if (findPreference == null) {
            findPreference = new Preference(this.mContext);
            findPreference.setKey(str);
            this.mPreferenceCategory.addPreference(findPreference);
        }
        findPreference.setTitle(optional.get());
        findPreference.setOrder(i);
        if (optional2.isPresent()) {
            findPreference.setSummary(optional2.get());
        }
        if (optional3.isPresent()) {
            findPreference.setIcon(optional3.get().mSliceAction.mIcon.loadDrawable(this.mContext));
            Intent intent = optional3.get().mSliceAction.getAction().getIntent();
            Log.d(TAG, "SliceAction: intent's Action:" + intent.getAction());
            if (intent.getAction().equals(this.mSliceIntentAction)) {
                intent = (Intent) intent.getParcelableExtra(this.mExtraIntent, Intent.class);
            } else if (intent.getAction().equals(this.mSlicePendingIntentAction)) {
                PendingIntent pendingIntent =
                        (PendingIntent)
                                intent.getParcelableExtra(
                                        this.mExtraPendingIntent, PendingIntent.class);
                intent = pendingIntent != null ? pendingIntent.getIntent() : null;
            }
            if (intent == null
                    || intent.resolveActivity(this.mContext.getPackageManager()) == null) {
                Log.d(TAG, "setIntent: Intent is null");
                findPreference.setSelectable(false);
            } else {
                Log.d(TAG, "setIntent: ActivityIntent" + intent);
                intent.removeFlags(268435456);
                findPreference.setIntent(intent);
            }
        }
        return Optional.of(findPreference);
    }

    private Optional<SliceAction> extractActionFromSlice(Slice slice) {
        for (SliceItem sliceItem : Arrays.asList(slice.mItems)) {
            if (sliceItem.mFormat.equals("slice")) {
                if (ArrayUtils.contains(UniversalCredentialUtil.AGENT_TITLE, sliceItem.mHints)) {
                    Optional<SliceAction> extractActionFromSlice =
                            extractActionFromSlice(sliceItem.getSlice());
                    if (extractActionFromSlice.isPresent()) {
                        return extractActionFromSlice;
                    }
                } else {
                    continue;
                }
            } else if (sliceItem.mFormat.equals(IMSParameter.CALL.ACTION)) {
                Optional<IconCompat> extractIconFromSlice =
                        extractIconFromSlice(sliceItem.getSlice());
                Optional<CharSequence> extractTitleFromSlice =
                        extractTitleFromSlice(sliceItem.getSlice());
                if (extractIconFromSlice.isPresent()) {
                    return Optional.of(
                            new SliceAction(
                                    sliceItem.getAction(),
                                    extractIconFromSlice.get(),
                                    0,
                                    extractTitleFromSlice.orElse(ApnSettings.MVNO_NONE)));
                }
            } else {
                continue;
            }
        }
        return Optional.empty();
    }

    private Optional<IconCompat> extractIconFromSlice(Slice slice) {
        for (SliceItem sliceItem : Arrays.asList(slice.mItems)) {
            if (sliceItem.mFormat.equals("image")) {
                return Optional.of((IconCompat) sliceItem.mObj);
            }
        }
        return Optional.empty();
    }

    private Optional<CharSequence> extractSubtitleFromSlice(Slice slice) {
        return extractTextFromSlice(slice, null);
    }

    private Optional<CharSequence> extractTextFromSlice(Slice slice, String str) {
        for (SliceItem sliceItem : Arrays.asList(slice.mItems)) {
            if (sliceItem.mFormat.equals("text")
                    && ((TextUtils.isEmpty(str) && Arrays.asList(sliceItem.mHints).isEmpty())
                            || (!TextUtils.isEmpty(str)
                                    && ArrayUtils.contains(str, sliceItem.mHints)))) {
                return Optional.ofNullable((CharSequence) sliceItem.mObj);
            }
        }
        return Optional.empty();
    }

    private Optional<CharSequence> extractTitleFromSlice(Slice slice) {
        return extractTextFromSlice(slice, UniversalCredentialUtil.AGENT_TITLE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removePreferenceListFromPreferenceCategory$1(
            Preference preference) {
        this.mPreferenceCategory.removePreference(preference);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setSliceUri$0(Uri uri, int i, Throwable th) {
        Log.w(TAG, "Slice may be null. uri = " + uri + ", error = " + i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$updatePreferenceListAndPreferenceCategory$2(
            Preference preference, Preference preference2) {
        return preference.compareTo(preference2) == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePreferenceListAndPreferenceCategory$3(
            Preference preference) {
        this.mPreferenceCategory.removePreference(preference);
    }

    private List<Preference> parseSliceToPreferenceList(Slice slice) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (SliceItem sliceItem : Arrays.asList(slice.mItems)) {
            if (sliceItem.mFormat.equals("slice")) {
                Optional<Preference> createPreferenceItem =
                        createPreferenceItem(
                                extractTitleFromSlice(sliceItem.getSlice()),
                                extractSubtitleFromSlice(sliceItem.getSlice()),
                                extractActionFromSlice(sliceItem.getSlice()),
                                i);
                if (createPreferenceItem.isPresent()) {
                    i++;
                    arrayList.add(createPreferenceItem.get());
                }
            }
        }
        return arrayList;
    }

    private void removePreferenceListFromPreferenceCategory() {
        this.mCurrentPreferencesList.stream()
                .forEach(new BlockingPrefWithSliceController$$ExternalSyntheticLambda1(this, 1));
        this.mCurrentPreferencesList.clear();
    }

    private void updatePreferenceListAndPreferenceCategory(List<Preference> list) {
        ArrayList arrayList = new ArrayList(this.mCurrentPreferencesList);
        for (final Preference preference : this.mCurrentPreferencesList) {
            if (list.stream()
                    .anyMatch(
                            new Predicate() { // from class:
                                              // com.android.settings.bluetooth.BlockingPrefWithSliceController$$ExternalSyntheticLambda0
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    boolean lambda$updatePreferenceListAndPreferenceCategory$2;
                                    lambda$updatePreferenceListAndPreferenceCategory$2 =
                                            BlockingPrefWithSliceController
                                                    .lambda$updatePreferenceListAndPreferenceCategory$2(
                                                            Preference.this, (Preference) obj);
                                    return lambda$updatePreferenceListAndPreferenceCategory$2;
                                }
                            })) {
                arrayList.remove(preference);
            }
        }
        arrayList.stream()
                .forEach(new BlockingPrefWithSliceController$$ExternalSyntheticLambda1(this, 0));
        this.mCurrentPreferencesList = list;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreferenceCategory =
                (PreferenceCategory) preferenceScreen.findPreference(getPreferenceKey());
        this.mSliceIntentAction =
                this.mContext.getResources().getString(R.string.config_bt_slice_intent_action);
        this.mSlicePendingIntentAction =
                this.mContext
                        .getResources()
                        .getString(R.string.config_bt_slice_pending_intent_action);
        this.mExtraIntent =
                this.mContext.getResources().getString(R.string.config_bt_slice_extra_intent);
        this.mExtraPendingIntent =
                this.mContext
                        .getResources()
                        .getString(R.string.config_bt_slice_extra_pending_intent);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mUri != null ? 0 : 3;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        LiveData liveData = this.mLiveData;
        if (liveData == null) {
            return;
        }
        try {
            liveData.observeForever(this);
        } catch (SecurityException unused) {
            Log.w(TAG, "observeForever - no permission");
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        LiveData liveData = this.mLiveData;
        if (liveData == null) {
            return;
        }
        try {
            liveData.removeObserver(this);
        } catch (SecurityException unused) {
            Log.w(TAG, "removeObserver - no permission");
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setSliceUri(final Uri uri) {
        this.mUri = uri;
        Context context = this.mContext;
        SliceLiveData.OnErrorListener onErrorListener =
                new SliceLiveData
                        .OnErrorListener() { // from class:
                                             // com.android.settings.bluetooth.BlockingPrefWithSliceController$$ExternalSyntheticLambda2
                    @Override // androidx.slice.widget.SliceLiveData.OnErrorListener
                    public final void onSliceError(int i, Throwable th) {
                        BlockingPrefWithSliceController.lambda$setSliceUri$0(uri, i, th);
                    }
                };
        ArraySet arraySet = SliceLiveData.SUPPORTED_SPECS;
        SliceLiveData.SliceLiveDataImpl sliceLiveDataImpl =
                new SliceLiveData.SliceLiveDataImpl(
                        context.getApplicationContext(), uri, onErrorListener);
        this.mLiveData = sliceLiveDataImpl;
        sliceLiveDataImpl.removeObserver(this);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void updatePreferenceFromSlice(Slice slice) {
        if (TextUtils.isEmpty(this.mSliceIntentAction)
                || TextUtils.isEmpty(this.mExtraIntent)
                || TextUtils.isEmpty(this.mSlicePendingIntentAction)
                || TextUtils.isEmpty(this.mExtraPendingIntent)) {
            Log.d(TAG, "No configs");
            return;
        }
        if (slice != null && !ArrayUtils.contains("permission_request", slice.mHints)) {
            updatePreferenceListAndPreferenceCategory(parseSliceToPreferenceList(slice));
            return;
        }
        Log.d(TAG, "Current slice: " + slice);
        removePreferenceListFromPreferenceCategory();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // androidx.lifecycle.Observer
    public void onChanged(Slice slice) {
        updatePreferenceFromSlice(slice);
        BasePreferenceController.UiBlockListener uiBlockListener = this.mUiBlockListener;
        if (uiBlockListener != null) {
            uiBlockListener.onBlockerWorkFinished(this);
        }
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
