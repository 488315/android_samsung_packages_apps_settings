package com.android.settings.slices;

import android.app.slice.SliceManager;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Binder;
import android.os.StrictMode;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.KeyValueListParser;
import android.util.Log;

import androidx.slice.Slice;
import androidx.slice.SliceItem;
import androidx.slice.SliceProvider;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.bluetooth.BluetoothSliceBuilder;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.network.telephony.Enhanced4gLteSliceHelper;
import com.android.settings.notification.VolumeSeekBarPreferenceController;
import com.android.settings.notification.zen.ZenModeSliceBuilder;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.wifi.calling.WifiCallingSliceHelper;
import com.android.settingslib.SliceBroadcastRelay;
import com.android.settingslib.utils.ThreadUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SettingsSliceProvider extends SliceProvider {
    public boolean mFirstSliceBound;
    public boolean mFirstSlicePinned;
    public Boolean mNightMode;
    final Map<Uri, SliceBackgroundWorker> mPinnedWorkers;
    Map<Uri, SliceData> mSliceWeakDataCache;
    SlicesDatabaseAccessor mSlicesDatabaseAccessor;
    public static final List PUBLICLY_SUPPORTED_CUSTOM_SLICE_URIS =
            Arrays.asList(
                    CustomSliceRegistry.BLUETOOTH_URI,
                    CustomSliceRegistry.FLASHLIGHT_SLICE_URI,
                    CustomSliceRegistry.LOCATION_SLICE_URI,
                    CustomSliceRegistry.MOBILE_DATA_SLICE_URI,
                    CustomSliceRegistry.WIFI_CALLING_URI,
                    CustomSliceRegistry.WIFI_SLICE_URI,
                    CustomSliceRegistry.ZEN_MODE_SLICE_URI);
    public static final KeyValueListParser KEY_VALUE_LIST_PARSER = new KeyValueListParser(',');

    public SettingsSliceProvider() {
        super("android.permission.READ_SEARCH_INDEXABLES");
        this.mPinnedWorkers = new ArrayMap();
        Log.d("SettingsSliceProvider", "init");
    }

    public static Slice getSliceStub(Uri uri) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList2.addAll(Arrays.asList("partial"));
        String[] strArr = (String[]) arrayList2.toArray(new String[arrayList2.size()]);
        Slice slice = new Slice();
        slice.mSpec = null;
        slice.mItems = Slice.NO_ITEMS;
        slice.mUri = null;
        slice.mHints = strArr;
        slice.mItems = (SliceItem[]) arrayList.toArray(new SliceItem[arrayList.size()]);
        slice.mUri = uri.toString();
        slice.mSpec = null;
        return slice;
    }

    public static void grantAllowlistedPackagePermissions(Context context, List<Uri> list) {
        if (list == null) {
            Log.d("SettingsSliceProvider", "No descendants to grant permission with, skipping.");
        }
        String[] stringArray =
                context.getResources().getStringArray(R.array.slice_allowlist_package_names);
        if (stringArray == null || stringArray.length == 0) {
            Log.d("SettingsSliceProvider", "No packages to allowlist, skipping.");
            return;
        }
        Log.d(
                "SettingsSliceProvider",
                String.format(
                        "Allowlisting %d uris to %d pkgs.",
                        Integer.valueOf(list.size()), Integer.valueOf(stringArray.length)));
        SliceManager sliceManager = (SliceManager) context.getSystemService(SliceManager.class);
        for (Uri uri : list) {
            for (String str : stringArray) {
                sliceManager.grantSlicePermission(str, uri);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0021, code lost:

       if (r3.length > 0) goto L9;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.Set<java.lang.String> getBlockedKeys() {
        /*
            r3 = this;
            android.content.Context r3 = r3.getContext()
            android.content.ContentResolver r3 = r3.getContentResolver()
            java.lang.String r0 = "blocked_slices"
            java.lang.String r3 = android.provider.Settings.Global.getString(r3, r0)
            androidx.collection.ArraySet r0 = new androidx.collection.ArraySet
            r0.<init>()
            android.util.KeyValueListParser r1 = com.android.settings.slices.SettingsSliceProvider.KEY_VALUE_LIST_PARSER     // Catch: java.lang.IllegalArgumentException -> L2b
            r1.setString(r3)     // Catch: java.lang.IllegalArgumentException -> L2b
            if (r3 == 0) goto L24
            java.lang.String r1 = ":"
            java.lang.String[] r3 = r3.split(r1)
            int r1 = r3.length
            if (r1 <= 0) goto L24
            goto L27
        L24:
            r3 = 0
            java.lang.String[] r3 = new java.lang.String[r3]
        L27:
            java.util.Collections.addAll(r0, r3)
            return r0
        L2b:
            r3 = move-exception
            java.lang.String r1 = "SettingsSliceProvider"
            java.lang.String r2 = "Bad Settings Slices Allowlist flags"
            android.util.Log.e(r1, r2, r3)
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.slices.SettingsSliceProvider.getBlockedKeys():java.util.Set");
    }

    public boolean isPrivateSlicesNeeded(Uri uri) {
        Context context = getContext();
        String string = context.getString(R.string.config_non_public_slice_query_uri);
        if (TextUtils.isEmpty(string) || !TextUtils.equals(uri.toString(), string)) {
            return false;
        }
        int callingUid = Binder.getCallingUid();
        boolean z =
                context.checkPermission(
                                "android.permission.READ_SEARCH_INDEXABLES",
                                Binder.getCallingPid(),
                                callingUid)
                        == 0;
        String[] packagesForUid = context.getPackageManager().getPackagesForUid(callingUid);
        return z
                && TextUtils.equals(
                        (packagesForUid == null || packagesForUid.length <= 0)
                                ? null
                                : packagesForUid[0],
                        context.getString(R.string.config_settingsintelligence_package_name));
    }

    public void loadSlice(Uri uri) {
        BasePreferenceController createInstance;
        if (this.mSliceWeakDataCache.containsKey(uri)) {
            Log.d("SettingsSliceProvider", uri + " loaded from cache");
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        try {
            SliceData sliceDataFromUri = this.mSlicesDatabaseAccessor.getSliceDataFromUri(uri);
            Context context = getContext();
            String str = sliceDataFromUri.mPreferenceController;
            String str2 = sliceDataFromUri.mKey;
            try {
                createInstance = BasePreferenceController.createInstance(context, str);
            } catch (IllegalStateException unused) {
                createInstance = BasePreferenceController.createInstance(context, str, str2);
            }
            IntentFilter intentFilter = createInstance.getIntentFilter();
            if (intentFilter != null) {
                if (createInstance instanceof VolumeSeekBarPreferenceController) {
                    Context context2 = getContext();
                    int audioStream =
                            ((VolumeSeekBarPreferenceController) createInstance).getAudioStream();
                    Map<Uri, Integer> map = VolumeSliceHelper.sRegisteredUri;
                    Log.d("VolumeSliceHelper", "Registering uri for broadcast relay: " + uri);
                    synchronized (VolumeSliceHelper.sRegisteredUri) {
                        try {
                            if (VolumeSliceHelper.sRegisteredUri.isEmpty()) {
                                SliceBroadcastRelay.registerReceiver(
                                        context2,
                                        CustomSliceRegistry.VOLUME_SLICES_URI,
                                        VolumeSliceRelayReceiver.class,
                                        intentFilter);
                                VolumeSliceHelper.sIntentFilter = intentFilter;
                            }
                            VolumeSliceHelper.sRegisteredUri.put(uri, Integer.valueOf(audioStream));
                        } finally {
                        }
                    }
                } else {
                    registerIntentToUri(intentFilter, uri);
                }
            }
            ThreadUtils.postOnMainThread(
                    new SettingsSliceProvider$$ExternalSyntheticLambda0(
                            this, createInstance, uri, 0));
            this.mSliceWeakDataCache.put(uri, sliceDataFromUri);
            getContext().getContentResolver().notifyChange(uri, null);
            Log.d(
                    "SettingsSliceProvider",
                    "Built slice ("
                            + uri
                            + ") in: "
                            + (System.currentTimeMillis() - currentTimeMillis));
        } catch (IllegalStateException e) {
            Log.d("SettingsSliceProvider", "Could not create slicedata for uri: " + uri, e);
        }
    }

    public void loadSliceInBackground(Uri uri) {
        ThreadUtils.postOnBackgroundThread(
                new SettingsSliceProvider$$ExternalSyntheticLambda5(this, uri, 1));
    }

    @Override // androidx.slice.SliceProvider
    public final Slice onBindSlice(Uri uri) {
        if (!this.mFirstSliceBound) {
            Log.d("SettingsSliceProvider", "onBindSlice start: " + uri);
        }
        StrictMode.ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
        try {
            if (!ThreadUtils.isMainThread()) {
                StrictMode.setThreadPolicy(
                        new StrictMode.ThreadPolicy.Builder().permitAll().build());
            }
            if (getBlockedKeys().contains(uri.getLastPathSegment())) {
                Log.e("SettingsSliceProvider", "Requested blocked slice with Uri: " + uri);
                StrictMode.setThreadPolicy(threadPolicy);
                if (!this.mFirstSliceBound) {
                    Log.v("SettingsSliceProvider", "onBindSlice end");
                    this.mFirstSliceBound = true;
                }
                return null;
            }
            boolean isNightMode = Utils.isNightMode(getContext());
            Boolean bool = this.mNightMode;
            if (bool == null) {
                this.mNightMode = Boolean.valueOf(isNightMode);
                getContext().setTheme(2132084373);
            } else if (bool.booleanValue() != isNightMode) {
                Log.d("SettingsSliceProvider", "Night mode changed, reload theme");
                this.mNightMode = Boolean.valueOf(isNightMode);
                getContext().getTheme().rebase();
            }
            if (((UserManager) getContext().getSystemService(UserManager.class)).isGuestUser()
                    && (RestrictedSliceUtils.AUTO_TURN_ON_WIFI_SLICE_URI.equals(uri)
                            || RestrictedSliceUtils.NOTIFY_OPEN_NETWORKS_SLICE_URI.equals(uri)
                            || RestrictedSliceUtils.BLUETOOTH_TETHERING_SLICE_URI.equals(uri)
                            || RestrictedSliceUtils.USB_TETHERING_SLICE_URI.equals(uri)
                            || CustomSliceRegistry.MOBILE_DATA_SLICE_URI.equals(uri))) {
                Log.i("SettingsSliceProvider", "Guest user access denied.");
                StrictMode.setThreadPolicy(threadPolicy);
                if (!this.mFirstSliceBound) {
                    Log.v("SettingsSliceProvider", "onBindSlice end");
                    this.mFirstSliceBound = true;
                }
                return null;
            }
            if (CustomSliceRegistry.sUriToSlice.containsKey(uri.buildUpon().clearQuery().build())) {
                Context context = getContext();
                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                if (featureFactoryImpl == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                featureFactoryImpl.getSlicesFeatureProvider().getClass();
                Slice slice =
                        SlicesFeatureProviderImpl.getSliceableFromUri(context, uri).getSlice();
                StrictMode.setThreadPolicy(threadPolicy);
                if (!this.mFirstSliceBound) {
                    Log.v("SettingsSliceProvider", "onBindSlice end");
                    this.mFirstSliceBound = true;
                }
                return slice;
            }
            if (CustomSliceRegistry.WIFI_CALLING_URI.equals(uri)) {
                FeatureFactoryImpl featureFactoryImpl2 = FeatureFactoryImpl._factory;
                if (featureFactoryImpl2 == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                SlicesFeatureProviderImpl slicesFeatureProvider =
                        featureFactoryImpl2.getSlicesFeatureProvider();
                Context context2 = getContext();
                slicesFeatureProvider.getClass();
                Slice createWifiCallingSlice =
                        new WifiCallingSliceHelper(context2).createWifiCallingSlice(uri);
                StrictMode.setThreadPolicy(threadPolicy);
                if (!this.mFirstSliceBound) {
                    Log.v("SettingsSliceProvider", "onBindSlice end");
                    this.mFirstSliceBound = true;
                }
                return createWifiCallingSlice;
            }
            if (CustomSliceRegistry.ZEN_MODE_SLICE_URI.equals(uri)) {
                Slice slice2 = ZenModeSliceBuilder.getSlice(getContext());
                StrictMode.setThreadPolicy(threadPolicy);
                if (!this.mFirstSliceBound) {
                    Log.v("SettingsSliceProvider", "onBindSlice end");
                    this.mFirstSliceBound = true;
                }
                return slice2;
            }
            if (CustomSliceRegistry.BLUETOOTH_URI.equals(uri)) {
                Slice slice3 = BluetoothSliceBuilder.getSlice(getContext());
                StrictMode.setThreadPolicy(threadPolicy);
                if (!this.mFirstSliceBound) {
                    Log.v("SettingsSliceProvider", "onBindSlice end");
                    this.mFirstSliceBound = true;
                }
                return slice3;
            }
            if (CustomSliceRegistry.ENHANCED_4G_SLICE_URI.equals(uri)) {
                FeatureFactoryImpl featureFactoryImpl3 = FeatureFactoryImpl._factory;
                if (featureFactoryImpl3 == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                SlicesFeatureProviderImpl slicesFeatureProvider2 =
                        featureFactoryImpl3.getSlicesFeatureProvider();
                Context context3 = getContext();
                slicesFeatureProvider2.getClass();
                Slice createEnhanced4gLteSlice =
                        new Enhanced4gLteSliceHelper(context3).createEnhanced4gLteSlice(uri);
                StrictMode.setThreadPolicy(threadPolicy);
                if (!this.mFirstSliceBound) {
                    Log.v("SettingsSliceProvider", "onBindSlice end");
                    this.mFirstSliceBound = true;
                }
                return createEnhanced4gLteSlice;
            }
            if (CustomSliceRegistry.WIFI_CALLING_PREFERENCE_URI.equals(uri)) {
                FeatureFactoryImpl featureFactoryImpl4 = FeatureFactoryImpl._factory;
                if (featureFactoryImpl4 == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                SlicesFeatureProviderImpl slicesFeatureProvider3 =
                        featureFactoryImpl4.getSlicesFeatureProvider();
                Context context4 = getContext();
                slicesFeatureProvider3.getClass();
                Slice createWifiCallingPreferenceSlice =
                        new WifiCallingSliceHelper(context4).createWifiCallingPreferenceSlice(uri);
                StrictMode.setThreadPolicy(threadPolicy);
                if (!this.mFirstSliceBound) {
                    Log.v("SettingsSliceProvider", "onBindSlice end");
                    this.mFirstSliceBound = true;
                }
                return createWifiCallingPreferenceSlice;
            }
            SliceData sliceData = this.mSliceWeakDataCache.get(uri);
            if (sliceData != null) {
                Slice buildSlice = SliceBuilderUtils.buildSlice(getContext(), sliceData);
                StrictMode.setThreadPolicy(threadPolicy);
                if (!this.mFirstSliceBound) {
                    Log.v("SettingsSliceProvider", "onBindSlice end");
                    this.mFirstSliceBound = true;
                }
                return buildSlice;
            }
            loadSliceInBackground(uri);
            Slice sliceStub = getSliceStub(uri);
            StrictMode.setThreadPolicy(threadPolicy);
            if (!this.mFirstSliceBound) {
                Log.v("SettingsSliceProvider", "onBindSlice end");
                this.mFirstSliceBound = true;
            }
            return sliceStub;
        } catch (Throwable th) {
            StrictMode.setThreadPolicy(threadPolicy);
            if (!this.mFirstSliceBound) {
                Log.v("SettingsSliceProvider", "onBindSlice end");
                this.mFirstSliceBound = true;
            }
            throw th;
        }
    }

    @Override // androidx.slice.SliceProvider
    public final void onCreateSliceProvider() {
        Log.d("SettingsSliceProvider", "onCreateSliceProvider");
        this.mSlicesDatabaseAccessor = new SlicesDatabaseAccessor(getContext());
        this.mSliceWeakDataCache = new WeakHashMap();
    }

    @Override // androidx.slice.SliceProvider
    public final Collection onGetSliceDescendants(Uri uri) {
        ArrayList arrayList = new ArrayList();
        if (isPrivateSlicesNeeded(uri)) {
            arrayList.addAll(this.mSlicesDatabaseAccessor.getSliceUris(uri.getAuthority(), false));
            Log.d("SettingsSliceProvider", "provide " + arrayList.size() + " non-public slices");
            uri = new Uri.Builder().scheme("content").authority(uri.getAuthority()).build();
        }
        if (SliceBuilderUtils.getPathData(uri) != null) {
            arrayList.add(uri);
            return arrayList;
        }
        final String authority = uri.getAuthority();
        String path = uri.getPath();
        boolean isEmpty = path.isEmpty();
        if (!isEmpty && !TextUtils.equals(path, "/action") && !TextUtils.equals(path, "/intent")) {
            return arrayList;
        }
        arrayList.addAll(this.mSlicesDatabaseAccessor.getSliceUris(authority, true));
        if (isEmpty && TextUtils.isEmpty(authority)) {
            arrayList.addAll(PUBLICLY_SUPPORTED_CUSTOM_SLICE_URIS);
        } else {
            arrayList.addAll(
                    (List)
                            PUBLICLY_SUPPORTED_CUSTOM_SLICE_URIS.stream()
                                    .filter(
                                            new Predicate() { // from class:
                                                              // com.android.settings.slices.SettingsSliceProvider$$ExternalSyntheticLambda3
                                                @Override // java.util.function.Predicate
                                                public final boolean test(Object obj) {
                                                    String str = authority;
                                                    List list =
                                                            SettingsSliceProvider
                                                                    .PUBLICLY_SUPPORTED_CUSTOM_SLICE_URIS;
                                                    return TextUtils.equals(
                                                            str, ((Uri) obj).getAuthority());
                                                }
                                            })
                                    .collect(Collectors.toList()));
        }
        grantAllowlistedPackagePermissions(getContext(), arrayList);
        return arrayList;
    }

    @Override // androidx.slice.SliceProvider
    public final void onSlicePinned(Uri uri) {
        if (!this.mFirstSlicePinned) {
            Log.d("SettingsSliceProvider", "onSlicePinned: " + uri);
            this.mFirstSlicePinned = true;
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl
                .getMetricsFeatureProvider()
                .action(0, 1371, 0, 0, uri.getLastPathSegment());
        if (!CustomSliceRegistry.sUriToSlice.containsKey(uri.buildUpon().clearQuery().build())) {
            if (CustomSliceRegistry.ZEN_MODE_SLICE_URI.equals(uri)) {
                registerIntentToUri(ZenModeSliceBuilder.INTENT_FILTER, uri);
                return;
            } else if (CustomSliceRegistry.BLUETOOTH_URI.equals(uri)) {
                registerIntentToUri(BluetoothSliceBuilder.INTENT_FILTER, uri);
                return;
            } else {
                loadSliceInBackground(uri);
                return;
            }
        }
        Context context = getContext();
        FeatureFactoryImpl featureFactoryImpl2 = FeatureFactoryImpl._factory;
        if (featureFactoryImpl2 == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl2.getSlicesFeatureProvider().getClass();
        CustomSliceable sliceableFromUri =
                SlicesFeatureProviderImpl.getSliceableFromUri(context, uri);
        IntentFilter intentFilter = sliceableFromUri.getIntentFilter();
        if (intentFilter != null) {
            registerIntentToUri(intentFilter, uri);
        }
        ThreadUtils.postOnMainThread(
                new SettingsSliceProvider$$ExternalSyntheticLambda0(
                        this, sliceableFromUri, uri, 1));
    }

    @Override // androidx.slice.SliceProvider
    public final void onSliceUnpinned(Uri uri) {
        this.mSliceWeakDataCache.remove(uri);
        Context context = getContext();
        if (VolumeSliceHelper.sRegisteredUri.containsKey(uri)) {
            Log.d("VolumeSliceHelper", "Unregistering uri broadcast relay: " + uri);
            synchronized (VolumeSliceHelper.sRegisteredUri) {
                try {
                    VolumeSliceHelper.sRegisteredUri.remove(uri);
                    if (VolumeSliceHelper.sRegisteredUri.isEmpty()) {
                        VolumeSliceHelper.sIntentFilter = null;
                        SliceBroadcastRelay.unregisterReceivers(
                                context, CustomSliceRegistry.VOLUME_SLICES_URI);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } else {
            SliceBroadcastRelay.unregisterReceivers(context, uri);
        }
        ThreadUtils.postOnMainThread(
                new SettingsSliceProvider$$ExternalSyntheticLambda5(this, uri, 0));
    }

    public void registerIntentToUri(IntentFilter intentFilter, Uri uri) {
        SliceBroadcastRelay.registerReceiver(
                getContext(), uri, SliceRelayReceiver.class, intentFilter);
    }

    @Override // android.content.ContentProvider
    public final void shutdown() {
        ThreadUtils.postOnMainThread(new SettingsSliceProvider$$ExternalSyntheticLambda2());
    }

    public final void startBackgroundWorker(Sliceable sliceable, Uri uri) {
        if (sliceable.getBackgroundWorkerClass() == null || this.mPinnedWorkers.containsKey(uri)) {
            return;
        }
        Log.d("SettingsSliceProvider", "Starting background worker for: " + uri);
        Context context = getContext();
        SliceBackgroundWorker sliceBackgroundWorker = SliceBackgroundWorker.getInstance(uri);
        if (sliceBackgroundWorker == null) {
            Class backgroundWorkerClass = sliceable.getBackgroundWorkerClass();
            Context applicationContext = context.getApplicationContext();
            Log.d("SliceBackgroundWorker", "create instance: " + backgroundWorkerClass);
            try {
                sliceBackgroundWorker =
                        (SliceBackgroundWorker)
                                backgroundWorkerClass
                                        .getConstructor(Context.class, Uri.class)
                                        .newInstance(applicationContext, uri);
                ((ArrayMap) SliceBackgroundWorker.LIVE_WORKERS).put(uri, sliceBackgroundWorker);
            } catch (IllegalAccessException
                    | InstantiationException
                    | NoSuchMethodException
                    | InvocationTargetException e) {
                throw new IllegalStateException(
                        "Invalid slice background worker: " + backgroundWorkerClass, e);
            }
        }
        this.mPinnedWorkers.put(uri, sliceBackgroundWorker);
        sliceBackgroundWorker.onSlicePinned();
    }
}
