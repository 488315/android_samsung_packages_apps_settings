package com.samsung.android.settings.analyzestorage.ui;

import android.app.Activity;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.SemSystemProperties;
import android.provider.Settings;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.settings.R;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.application.ApplicationPolicy;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Trace;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.analyzestorage.data.model.RecommendCardState;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.external.injection.ControllerFactory;
import com.samsung.android.settings.analyzestorage.presenter.ActivityInfo$ActivityInfoListener;
import com.samsung.android.settings.analyzestorage.presenter.ActivityInfoStore;
import com.samsung.android.settings.analyzestorage.presenter.clipboard.Clipboard;
import com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.HomeController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.PageControllerInterface;
import com.samsung.android.settings.analyzestorage.presenter.controllers.RecommendCardController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.SubFileListController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.FileListObserver;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ListItemHandler;
import com.samsung.android.settings.analyzestorage.presenter.feature.Features$Knox;
import com.samsung.android.settings.analyzestorage.presenter.feature.SepFeatures$FloatingFeature;
import com.samsung.android.settings.analyzestorage.presenter.managers.AsRecommendCardInfoManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.FeatureManager$UiFeature;
import com.samsung.android.settings.analyzestorage.presenter.managers.KnoxManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.MediaFileManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageOperationManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageUsageManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageVolumeManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.TrashManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoCheckerImpl;
import com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.application.AppDataManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastReceiveCenter;
import com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastReceiveCenter$$ExternalSyntheticLambda1;
import com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastReceiveCenter$$ExternalSyntheticLambda3;
import com.samsung.android.settings.analyzestorage.presenter.observer.IContentObserver;
import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;
import com.samsung.android.settings.analyzestorage.presenter.utils.preference.PreferenceUtils;
import com.samsung.android.settings.analyzestorage.ui.exception.ExceptionHandler;
import com.samsung.android.settings.analyzestorage.ui.manager.LayoutWidthManager;
import com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.AsIndicatorAdapter;
import com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.RecommendCardViewPagerAdapter;
import com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.ViewPagerAdapter;
import com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AnalyzeStorageSubList;
import com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AnalyzeStorageSubList$observeList$1;
import com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AsSubAppList;
import com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AsSubFileList;
import com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AsSubListFactory;
import com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.OverView;
import com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.RecommendCardView;
import com.samsung.android.settings.analyzestorage.ui.utils.UiUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.sec.ims.volte2.data.VolteConstants;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0018\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B\u0007¢\u0006\u0004\b\u0005\u0010\u0006¨\u0006\u0007"
        },
        d2 = {
            "Lcom/samsung/android/settings/analyzestorage/ui/MainActivity;",
            "Landroidx/appcompat/app/AppCompatActivity;",
            "Lcom/samsung/android/settings/analyzestorage/ui/manager/LayoutWidthManager$OnWidthChangedListener;",
            "Lcom/samsung/android/settings/analyzestorage/presenter/observer/IContentObserver;",
            "Lcom/samsung/android/settings/analyzestorage/presenter/ActivityInfo$ActivityInfoListener;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class MainActivity extends AppCompatActivity
        implements LayoutWidthManager.OnWidthChangedListener,
                IContentObserver,
                ActivityInfo$ActivityInfoListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int instanceId;
    public boolean isRecreated;
    public int layoutState;
    public OverView overView;
    public RecommendCardView recommendCardView;
    public final Lazy controller$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.samsung.android.settings.analyzestorage.ui.MainActivity$controller$2
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            MainActivity mainActivity = MainActivity.this;
                            int i = MainActivity.$r8$clinit;
                            int intExtra = mainActivity.getIntent().getIntExtra("instanceId", -1);
                            if (intExtra > 0) {
                                mainActivity.instanceId = intExtra;
                            }
                            Log.d(
                                    "MainActivity",
                                    "initInstanceId] instanceId : " + mainActivity.instanceId);
                            ControllerFactory controllerFactory =
                                    new ControllerFactory(
                                            mainActivity.getApplication(), mainActivity.instanceId);
                            controllerFactory.mOwner = mainActivity;
                            ViewModelStore store = mainActivity.getViewModelStore();
                            CreationExtras defaultViewModelCreationExtras =
                                    mainActivity.getDefaultViewModelCreationExtras();
                            Intrinsics.checkNotNullParameter(store, "store");
                            ViewModelProviderImpl viewModelProviderImpl =
                                    new ViewModelProviderImpl(
                                            store,
                                            controllerFactory,
                                            defaultViewModelCreationExtras);
                            KClass kotlinClass =
                                    JvmClassMappingKt.getKotlinClass(HomeController.class);
                            String qualifiedName = kotlinClass.getQualifiedName();
                            if (qualifiedName != null) {
                                return (HomeController)
                                        viewModelProviderImpl
                                                .getViewModel$lifecycle_viewmodel_release(
                                                        kotlinClass,
                                                        "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                                                .concat(qualifiedName));
                            }
                            throw new IllegalArgumentException(
                                    "Local and anonymous classes can not be ViewModels".toString());
                        }
                    });
    public final Handler handler = new Handler();
    public final SparseArray subListArray = new SparseArray();
    public final Lazy layoutWidthManager$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.samsung.android.settings.analyzestorage.ui.MainActivity$layoutWidthManager$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            LayoutWidthManager.Companion companion = LayoutWidthManager.Companion;
                            LayoutWidthManager layoutWidthManager = LayoutWidthManager.instance;
                            if (layoutWidthManager == null) {
                                synchronized (companion) {
                                    layoutWidthManager = new LayoutWidthManager();
                                    LayoutWidthManager.instance = layoutWidthManager;
                                }
                            }
                            return layoutWidthManager;
                        }
                    });
    public final PageInfo pageInfo = new PageInfo(PageType.ANALYZE_STORAGE_HOME);
    public final ActivityResultRegistry.AnonymousClass2 activityResult =
            (ActivityResultRegistry.AnonymousClass2)
                    registerForActivityResult(
                            new ActivityResultContracts$StartActivityForResult(0),
                            new ActivityResultCallback() { // from class:
                                                           // com.samsung.android.settings.analyzestorage.ui.MainActivity$activityResult$1
                                @Override // androidx.activity.result.ActivityResultCallback
                                public final void onActivityResult(Object obj) {
                                    ActivityResult result = (ActivityResult) obj;
                                    Intrinsics.checkNotNullParameter(result, "result");
                                    int i = result.mResultCode;
                                    MainActivity mainActivity = MainActivity.this;
                                    if (i != 1000) {
                                        if (i == 1001) {
                                            ((AnalyzeStorageSubList)
                                                            mainActivity.subListArray.get(5))
                                                    .refresh();
                                            RecommendCardController recommendCardController =
                                                    mainActivity.getController()
                                                            .recommendCardController;
                                            if (recommendCardController != null) {
                                                recommendCardController.updateOnlyCard(6);
                                                return;
                                            } else {
                                                Intrinsics
                                                        .throwUninitializedPropertyAccessException(
                                                                "recommendCardController");
                                                throw null;
                                            }
                                        }
                                        return;
                                    }
                                    OverView overView = mainActivity.overView;
                                    if (overView != null) {
                                        overView.controller
                                                .getCloudInfoByProviderQueryWithContentObserver();
                                    }
                                    OverViewController overViewController =
                                            mainActivity.getController().overViewController;
                                    if (overViewController == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException(
                                                "overViewController");
                                        throw null;
                                    }
                                    overViewController.updateUsageInfo(true, false);
                                    RecommendCardController recommendCardController2 =
                                            mainActivity.getController().recommendCardController;
                                    if (recommendCardController2 != null) {
                                        recommendCardController2.updateOnlyCard(5);
                                    } else {
                                        Intrinsics.throwUninitializedPropertyAccessException(
                                                "recommendCardController");
                                        throw null;
                                    }
                                }
                            });
    public final MainActivity$backPressCallBack$1 backPressCallBack =
            new OnBackPressedCallback() { // from class:
                                          // com.samsung.android.settings.analyzestorage.ui.MainActivity$backPressCallBack$1
                {
                    super(true);
                }

                @Override // androidx.activity.OnBackPressedCallback
                public final void handleOnBackPressed() {
                    MainActivity mainActivity = MainActivity.this;
                    boolean booleanExtra =
                            mainActivity.getIntent().getBooleanExtra("from_shortcut", false);
                    if (Intrinsics.areEqual(
                                    mainActivity.getIntent().getStringExtra("calling_package"),
                                    "com.sec.android.app.myfiles")
                            && booleanExtra) {
                        mainActivity.startActivity(
                                "samsung.myfiles.intent.action.LAUNCH_MY_FILES",
                                "com.sec.android.app.myfiles");
                    }
                    mainActivity.getClass();
                    mainActivity.finish();
                }
            };

    @Override // com.samsung.android.settings.analyzestorage.presenter.ActivityInfo$ActivityInfoListener
    public final ActivityResultLauncher getActivityResultLauncher() {
        return this.activityResult;
    }

    public final HomeController getController() {
        return (HomeController) this.controller$delegate.getValue();
    }

    public final LayoutWidthManager getLayoutWidthManager() {
        return (LayoutWidthManager) this.layoutWidthManager$delegate.getValue();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity,
              // android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        getLayoutWidthManager().setContentWidthDp(newConfig.screenWidthDp, true);
        View requireViewById = requireViewById(R.id.contents_container);
        Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(...)");
        UiUtils.setFlexibleSpacing(requireViewById);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity,
              // android.view.Window.Callback
    public final void onContentChanged() {
        SparseArray sparseArray = this.subListArray;
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            sparseArray.keyAt(i);
            AnalyzeStorageSubList analyzeStorageSubList =
                    (AnalyzeStorageSubList) sparseArray.valueAt(i);
            if (analyzeStorageSubList instanceof AsSubAppList) {
                final AsSubAppList asSubAppList = (AsSubAppList) analyzeStorageSubList;
                this.handler.post(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.analyzestorage.ui.MainActivity$onContentChanged$1$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                asSubAppList.refresh();
                            }
                        });
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        boolean z;
        AnalyzeStorageSubList asSubAppList;
        AbsPageController controller;
        MutableLiveData mutableLiveData;
        MutableLiveData mutableLiveData2;
        final int i = 0;
        final int i2 = 1;
        Trace.beginSection("MainActivity_onCreate");
        super.onCreate(bundle);
        Context context = ActivityInfoStore.context;
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        ActivityInfoStore.context = applicationContext;
        KnoxManager.Companion companion = KnoxManager.Companion;
        KnoxManager knoxManager = KnoxManager.instance;
        if (knoxManager == null) {
            synchronized (companion) {
                knoxManager = KnoxManager.instance;
                if (knoxManager == null) {
                    knoxManager = new KnoxManager(applicationContext);
                    KnoxManager.instance = knoxManager;
                }
            }
        }
        boolean z2 = knoxManager.isKnoxMode;
        Features$Knox.isKnox = z2;
        if (z2) {
            ReentrantLock reentrantLock = (ReentrantLock) KnoxManager.knoxInfoLocker;
            reentrantLock.lock();
            if (KnoxManager.knoxInfoBundle == null) {
                KnoxManager.knoxInfoBundle =
                        SemPersonaManager.getKnoxInfoForApp(
                                knoxManager.context, "isBlockExternalSD");
            }
            z =
                    KnoxManager.knoxInfoBundle != null
                            ? !"true".equals(r4.getString("isBlockExternalSD"))
                            : true;
            reentrantLock.unlock();
        } else {
            z = true;
        }
        Log.d("KnoxManager", "isSupportExternalStorage() ] " + z);
        Features$Knox.isSupportExternalStorage = z;
        "eng".equals(SemSystemProperties.get("ro.build.type"));
        if (!Debug.semIsProductDev()) {
            "eng".equals(SemSystemProperties.get("ro.build.type"));
        }
        String salesCode = SemSystemProperties.getSalesCode();
        Features$Knox.isRetailMode =
                ("PAP".equals(salesCode) || "FOP".equals(salesCode) || "LDU".equals(salesCode))
                        || (Settings.Secure.getInt(
                                        applicationContext.getContentResolver(), "shopdemo", 0)
                                == 1);
        UserInfoManager.sUserInfoChecker = new UserInfoCheckerImpl();
        this.isRecreated = bundle != null;
        HashMap hashMap = MediaFileManager.extensionToMediaFileTypeMap;
        MediaFileManager.addFileType(FileType.M4A, "M4A", "audio/mp4");
        MediaFileManager.addFileType(FileType.AMR, "AMR", "audio/amr");
        MediaFileManager.addFileType(FileType.AWB, "AWB", "audio/amr-wb");
        MediaFileManager.addFileType(FileType._3GA, "3GA", "audio/3gpp");
        MediaFileManager.addFileType(400, "APK", "application/vnd.android.package-archive");
        MediaFileManager.addFileType(FileType.VCF, "VCF", "text/x-vcard");
        MediaFileManager.addFileType(FileType.EML, "EML", "message/rfc822");
        MediaFileManager.addFileType(FileType.JPG, "JPG", "image/jpeg");
        MediaFileManager.addFileType(10, "JPEG", "image/jpeg");
        MediaFileManager.addFileType(FileType.MY5, "MY5", "image/vnd.tmo.my5");
        MediaFileManager.addFileType(FileType.GIF, "GIF", "image/gif");
        MediaFileManager.addFileType(FileType.PNG, "PNG", "image/png");
        MediaFileManager.addFileType(FileType.XBMP, "BMP", "image/x-ms-bmp");
        MediaFileManager.addFileType(FileType.BMP, "BMP", "image/bmp");
        MediaFileManager.addFileType(FileType.WBMP, "WBMP", "image/vnd.wap.wbmp");
        MediaFileManager.addFileType(FileType.WEBP, "WEBP", "image/webp");
        int i3 = FileType.HEIC;
        MediaFileManager.addFileType(i3, "HEIC", "image/heif");
        MediaFileManager.addFileType(i3, "HEIF", "image/heif");
        MediaFileManager.addFileType(FileType.HTML, "HTML", "text/html");
        MediaFileManager.addFileType(FileType.HTM, "HTM", "text/html");
        MediaFileManager.addFileType(FileType.XHTML, "XHTML", "text/html");
        MediaFileManager.addFileType(FileType.XML, "XML", "application/xhtml+xml");
        MediaFileManager.addFileType(FileType.WGT, "WGT", "application/vnd.samsung.widget");
        MediaFileManager.addFileType(FileType.HWP, "HWP", "application/x-hwp");
        MediaFileManager.addFileType(FileType.HWPX, "HWPX", "application/vnd.hancom.hwpx");
        MediaFileManager.addFileType(FileType.HWT, "HWT", "application/haansofthwt");
        MediaFileManager.addFileType(FileType.HWTX, "HWTX", "application/haansofthwt");
        MediaFileManager.addFileType(FileType.OWPML, "OWPML", "application/haansofthwt");
        MediaFileManager.addFileType(FileType.HWDT, "HWDT", "application/hancomhwdt");
        MediaFileManager.addFileType(FileType.MEMO, "MEMO", "application/memo");
        MediaFileManager.addFileType(100, "MP3", "audio/mpeg");
        MediaFileManager.addFileType(FileType.WAV, "WAV", "audio/x-wav");
        MediaFileManager.addFileType(FileType.WMA, "WMA", "audio/x-ms-wma");
        MediaFileManager.addFileType(FileType.OGG, "OGG", "audio/ogg");
        MediaFileManager.addFileType(FileType.OGA, "OGA", "application/ogg");
        MediaFileManager.addFileType(FileType.AAC, "AAC", "audio/aac");
        MediaFileManager.addFileType(FileType.FLAC, "FLAC", "audio/flac");
        MediaFileManager.addFileType(FileType.MP4_A, "MP4_A", "audio/mp4");
        MediaFileManager.addFileType(FileType.MP4_AUDIO, "MP4A", "audio/mp4");
        MediaFileManager.addFileType(FileType.MPGA, "MPGA", "audio/mpeg");
        MediaFileManager.addFileType(FileType._3GP_AUDIO, "3GP_A", "audio/3gpp");
        MediaFileManager.addFileType(FileType._3G2_AUDIO, "3G2_A", "audio/3gpp2");
        MediaFileManager.addFileType(FileType.ASF_AUDIO, "ASF_A", "audio/x-ms-asf");
        MediaFileManager.addFileType(FileType._3GPP_AUDIO, "3GPP_A", "audio/3gpp");
        MediaFileManager.addFileType(150, "MID", "audio/midi");
        MediaFileManager.addFileType(FileType.MID_A, "MID_A", "audio/mid");
        MediaFileManager.addFileType(FileType.MIDI, "MIDI", "audio/midi");
        MediaFileManager.addFileType(FileType.RTX, "RTX", "audio/midi");
        MediaFileManager.addFileType(FileType.OTA, "OTA", "audio/midi");
        MediaFileManager.addFileType(FileType.XMF, "XMF", "audio/midi");
        MediaFileManager.addFileType(FileType.MXMF, "MXMF", "audio/midi");
        MediaFileManager.addFileType(FileType.RTL, "RTTTL", "audio/midi");
        MediaFileManager.addFileType(FileType.SMF, "SMF", "audio/sp-midi");
        MediaFileManager.addFileType(FileType.SPMID, "SPMID", "audio/sp-midi");
        MediaFileManager.addFileType(FileType.IMY, "IMY", "audio/imelody");
        MediaFileManager.addFileType(FileType.MKA, "MKA", "audio/x-matroska");
        MediaFileManager.addFileType(FileType.PYA, "PYA", "audio/vnd.ms-playready.media.pya");
        MediaFileManager.addFileType(FileType.QCP, "QCP", "audio/qcelp");
        MediaFileManager.addFileType(FileType.PDF, "PDF", ApplicationPolicy.DEFAULT_TYPE_PDF);
        MediaFileManager.addFileType(FileType.PPS, "PPS", "application/vnd.ms-powerpoint");
        int i4 = FileType.PPT;
        MediaFileManager.addFileType(i4, "PPT", "application/vnd.ms-powerpoint");
        MediaFileManager.addFileType(
                FileType.PPTX,
                "PPTX",
                "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        MediaFileManager.addFileType(
                FileType.PPTM,
                "PPTM",
                "application/vnd.ms-powerpoint.presentation.macroEnabled.12");
        MediaFileManager.addFileType(FileType.POT, "POT", "application/vnd.ms-powerpoint");
        MediaFileManager.addFileType(
                FileType.POTX,
                "POTX",
                "application/vnd.openxmlformats-officedocument.presentationml.template");
        MediaFileManager.addFileType(
                FileType.PPSX,
                "PPSX",
                "application/vnd.openxmlformats-officedocument.presentationml.slideshow");
        MediaFileManager.addFileType(
                i4, ApnSettings.MVNO_NONE, "application/vnd.google-apps.presentation");
        MediaFileManager.addFileType(FileType.VCS, "VCS", "text/x-vCalendar");
        MediaFileManager.addFileType(FileType.ICS, "ICS", "text/calendar");
        MediaFileManager.addFileType(
                FileType.SCC_SCRAP, "SCC", "application/vnd.samsung.scc.pinall");
        MediaFileManager.addFileType(FileType.SNB, "SNB", "application/snb");
        MediaFileManager.addFileType(300, "SPD", "application/spd");
        MediaFileManager.addFileType(FileType.SCC, "SCC", "application/vnd.samsung.scc.storyalbum");
        MediaFileManager.addFileType(FileType.SFF, "SFF", "application/vnd.samsung.scc.storyalbum");
        MediaFileManager.addFileType(FileType.SSF, "SSF", "application/ssf");
        MediaFileManager.addFileType(FileType.VTS, "VTS", "text/x-vtodo");
        MediaFileManager.addFileType(FileType.ASC, "ASC", "text/plain");
        MediaFileManager.addFileType(FileType.TXT, "TXT", "text/plain");
        MediaFileManager.addFileType(FileType.EPUB, "EPUB", "application/epub+zip");
        MediaFileManager.addFileType(FileType.ACSM, "ACSM", "application/vnd.adobe.adept+xml");
        MediaFileManager.addFileType(FileType.MOBI, "MOBI", "application/mobi");
        MediaFileManager.addFileType(FileType.CHM, "CHM", "application/vnd.ms-htmlhelp");
        MediaFileManager.addFileType(FileType.MPEG, "MPEG", "video/mpeg");
        MediaFileManager.addFileType(FileType.MPG, "MPG", "video/mpeg");
        MediaFileManager.addFileType(200, "MP4", "video/mp4");
        MediaFileManager.addFileType(FileType.M4V, "M4V", "video/mp4");
        MediaFileManager.addFileType(FileType._3GP, "3GP", "video/3gpp");
        MediaFileManager.addFileType(FileType._3GPP, "3GPP", "video/3gpp");
        MediaFileManager.addFileType(FileType._3G2, "3G2", "video/3gpp2");
        MediaFileManager.addFileType(FileType._3GPP2, "3GPP2", "video/3gpp2");
        MediaFileManager.addFileType(FileType.WMV, "WMV", "video/x-ms-wmv");
        MediaFileManager.addFileType(FileType.ASF, "ASF", "video/x-ms-asf");
        MediaFileManager.addFileType(FileType.AVI, "AVI", "video/avi");
        MediaFileManager.addFileType(FileType.DIVX, "DIVX", "video/divx");
        MediaFileManager.addFileType(FileType.FLV, "FLV", "video/flv");
        MediaFileManager.addFileType(FileType.MKV, "MKV", "video/x-matroska");
        MediaFileManager.addFileType(FileType.SDP, "SDP", "application/sdp");
        MediaFileManager.addFileType(FileType.TS, "TS", "video/mp2ts");
        MediaFileManager.addFileType(FileType.PYV, "PYV", "video/vnd.ms-playready.media.pyv");
        MediaFileManager.addFileType(FileType.MOV, "MOV", "video/quicktime");
        MediaFileManager.addFileType(FileType.SKM, "SKM", "video/skm");
        MediaFileManager.addFileType(FileType.K3G, "K3G", "video/k3g");
        MediaFileManager.addFileType(FileType.AK3G, "AK3G", "video/ak3g");
        MediaFileManager.addFileType(FileType.WEBM, "WEBM", "video/webm");
        MediaFileManager.addFileType(FileType.MTS, "MTS", "video/mp2ts");
        MediaFileManager.addFileType(FileType.M2TS, "M2TS", "video/mp2ts");
        MediaFileManager.addFileType(FileType.M2T, "M2T", "video/mp2ts");
        MediaFileManager.addFileType(FileType.TRP, "TRP", "video/mp2ts");
        MediaFileManager.addFileType(FileType.TP, "TP", "video/mp2ts");
        MediaFileManager.addFileType(FileType.VNT, "VNT", "text/x-vnote");
        MediaFileManager.addFileType(FileType.RTF, "RTF", "application/rtf");
        int i5 = FileType.DOC;
        MediaFileManager.addFileType(i5, "DOC", "application/msword");
        MediaFileManager.addFileType(
                FileType.DOCX,
                "DOCX",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        MediaFileManager.addFileType(
                FileType.DOCM, "DOCM", "application/vnd.ms-word.document.macroEnabled.12");
        MediaFileManager.addFileType(FileType.DOT, "DOT", "application/msword");
        MediaFileManager.addFileType(
                FileType.DOTX,
                "DOTX",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.template");
        MediaFileManager.addFileType(
                i5, ApnSettings.MVNO_NONE, "application/vnd.google-apps.document");
        MediaFileManager.addFileType(FileType.CSV, "CSV", "text/comma-separated-values");
        int i6 = FileType.XLS;
        MediaFileManager.addFileType(i6, "XLS", "application/vnd.ms-excel");
        MediaFileManager.addFileType(
                FileType.XLSX,
                "XLSX",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        MediaFileManager.addFileType(FileType.XLT, "XLT", "application/vnd.ms-excel");
        MediaFileManager.addFileType(
                FileType.XLTX,
                "XLTX",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.template");
        MediaFileManager.addFileType(
                FileType.XLSM, "XLSM", "application/vnd.ms-excel.sheet.macroEnabled.12");
        MediaFileManager.addFileType(
                i6, ApnSettings.MVNO_NONE, "application/vnd.google-apps.spreadsheet");
        MediaFileManager.addFileType(410, "ZIP", "application/zip");
        MediaFileManager.addFileType(FileType.RAR, "RAR", "application/x-rar-compressed");
        MediaFileManager.addFileType(FileType.SEVEN_Z, "7Z", "application/x-7z-compressed");
        MediaFileManager.addFileType(FileType.SDOC, "SDOC", "application/sdoc");
        MediaFileManager.addFileType(FileType.SDOCX, "SDOCX", "application/sdoc");
        MediaFileManager.addFileType(FileType.ENC, "ENC", "application/enc");
        MediaFileManager.addFileType(FileType.LOC, "LOC", "application/loc");
        MediaFileManager.addFileType(180, "M3U", "audio/x-mpegurl");
        MediaFileManager.addFileType(FileType.PLS, "PLS", "audio/x-scpls");
        MediaFileManager.addFileType(FileType.WPL, "WPL", "application/vnd.ms-wpl");
        MediaFileManager.addFileType(500, "SWF", "application/x-shockwave-flash");
        MediaFileManager.addFileType(FileType.SVG, "SVG", "image/svg+xml");
        MediaFileManager.addFileType(
                VolteConstants.ErrorCode.BAD_EXTENSION, "DCF", "application/vnd.oma.drm.content");
        MediaFileManager.addFileType(FileType.ODF, "ODF", "application/vnd.oma.drm.content");
        MediaFileManager.addFileType(FileType.SM4, "SM4", "video/vnd.sdrm-media.sm4");
        MediaFileManager.addFileType(FileType.JAD, "JAD", "text/vnd.sun.j2me.app-descriptor");
        MediaFileManager.addFileType(FileType.JAR, "JAR", "application/java-archive");
        MediaFileManager.addFileType(FileType.SASF, "SASF", "application/x-sasf");
        MediaFileManager.addFileType(FileType.SOL, "SOL", "application/com.sec.kidspiano");
        MediaFileManager.addFileType(
                FileType.ODS, "ODS", "application/vnd.oasis.opendocument.spreadsheet");
        MediaFileManager.addFileType(430, "P12", "application/x-pkcs12");
        MediaFileManager.addFileType(FileType.PFX, "PFX", "application/x-pkcs12");
        MediaFileManager.addFileType(FileType.CRT, "CRT", "application/x-x509-ca-cert");
        MediaFileManager.addFileType(FileType.DER, "DER", "application/x-x509-ca-cert");
        MediaFileManager.addFileType(FileType.PEM, "PEM", "application/x-pem-file");
        MediaFileManager.addFileType(FileType.CER, "CER", "application/pkix-cert");
        MediaFileManager.addFileType(0, ApnSettings.MVNO_NONE, "application/octet-stream");
        MediaFileManager.addFileType(FileType.PAGES, "PAGES", "application/x-iwork-pages-sffpages");
        MediaFileManager.addFileType(FileType.KEY, "KEY", "application/x-iwork-keynote-sffkey");
        MediaFileManager.addFileType(
                FileType.NUMBERS, "NUMBERS", "application/x-iwork-numbers-sffnumbers");
        MediaFileManager.addFileType(FileType.LA, "LA", "application/octet-stream");
        MediaFileManager.addFileType(FileType.GPX, "GPX", "application/gpx+xml");
        MediaFileManager.addFileType(FileType.SHOW, "SHOW", "application/hshow");
        MediaFileManager.addFileType(
                FileType.XLSB, "XLSB", "application/vnd.ms-excel.sheet.binary.macroenabled.12");
        MediaFileManager.addFileType(
                FileType.XLTM, "XLTM", "application/vnd.ms-excel.template.macroenabled.12");
        MediaFileManager.addFileType(FileType.CELL, "CELL", "application/haansoftcell");
        MediaFileManager.addFileType(FileType.HCDT, "HCDT", "application/haansofthcdt");
        MediaFileManager.addFileType(FileType.PRN, "PRN", "text/space-separated-values");
        MediaFileManager.addFileType(FileType.TSV, "TSV", "text/tab-separated-values");
        MediaFileManager.addFileType(FileType.RAF, "RAF", "image/x-fuji-raf");
        MediaFileManager.addFileType(FileType.ORF, "ORF", "image/x-olympus-orf");
        MediaFileManager.addFileType(FileType.ERF, "ERF", "image/x-raw-epson");
        MediaFileManager.addFileType(FileType.FFF, "FFF", "image/x-fff");
        MediaFileManager.addFileType(FileType.CRW, "CRW", "image/x-canon-crw");
        MediaFileManager.addFileType(FileType.CR2, "CR2", "image/x-canon-cr2");
        MediaFileManager.addFileType(FileType.CR3, "CR3", "image/x-canon-cr3");
        MediaFileManager.addFileType(FileType.DNG, "DNG", "image/x-adobe-dng");
        MediaFileManager.addFileType(FileType.MEF, "MEF", "image/x-mef");
        MediaFileManager.addFileType(FileType.MOS, "MOS", "image/x-raw-leaf");
        MediaFileManager.addFileType(FileType.PXN, "PXN", "image/x-pxn");
        MediaFileManager.addFileType(FileType.SRW, "SRW", "image/x-samsung-srw");
        MediaFileManager.addFileType(FileType.PTX, "PTX", "image/x-ptx");
        MediaFileManager.addFileType(FileType.PEF, "PEF", "image/x-pef");
        MediaFileManager.addFileType(FileType.RW2, "RW2", "image/x-panasonic-rw2");
        MediaFileManager.addFileType(FileType.BAY, "BAY", "image/x-bay");
        MediaFileManager.addFileType(FileType.TIF, "TIF", "image/tiff");
        MediaFileManager.addFileType(FileType.K25, "K25", "image/x-k25");
        MediaFileManager.addFileType(FileType.KDC, "KDC", "image/x-kdc");
        MediaFileManager.addFileType(FileType.DCS, "DCS", "image/x-dcs");
        MediaFileManager.addFileType(FileType.DCR, "DCR", "image/x-dcr");
        MediaFileManager.addFileType(FileType.DRF, "DRF", "image/x-drf");
        MediaFileManager.addFileType(FileType.ARW, "ARW", "image/x-sony-arw");
        MediaFileManager.addFileType(FileType.SRF, "SRF", "image/x-sony-srf");
        MediaFileManager.addFileType(FileType.SR2, "SR2", "image/x-sony-sr2");
        MediaFileManager.addFileType(FileType.CAP, "CAP", "image/x-cap");
        MediaFileManager.addFileType(FileType.IIQ, "IIQ", "image/x-iiq");
        MediaFileManager.addFileType(FileType.MRW, "MRW", "image/x-minolta-mrw");
        MediaFileManager.addFileType(FileType.X3F, "X3F", "image/x-sigma-x3f");
        MediaFileManager.addFileType(FileType.R3D, "R3D", "image/x-r3d");
        MediaFileManager.addFileType(FileType.NEF, "NEF", "image/x-nikon-nef");
        MediaFileManager.addFileType(FileType.NRW, "NRW", "image/x-nrw");
        MediaFileManager.addFileType(FileType.PASSBOOK, "PKPASS", "application/vnd.apple.pkpass");
        MediaFileManager.addFileType(FileType.TORRENT, "TORRENT", "application/x-bittorrent");
        MediaFileManager.addFileType(FileType.PNK, "PNK", "application/pnk");
        MediaFileManager.addFileType(FileType.XDW, "XDW", "application/vnd.fujixerox.docuworks");
        MediaFileManager.addFileType(FileType.DWG, "DWG", "application/acad");
        MediaFileManager.addFileType(FileType.XMIND, "XMIND", "text/plain");
        MediaFileManager.addFileType(FileType.JSON, "JSON", "application/json");
        MediaFileManager.addFileType(FileType.GLTF, "GLTF", "model/gltf+json");
        MediaFileManager.addFileType(FileType.EMC, "EMC", "application/octet-stream");
        StorageVolumeManager.updateStorageMountState(getApplicationContext());
        Trace.beginSection("controller_onCreate");
        HomeController controller2 = getController();
        controller2.getClass();
        PageInfo curPageInfo = this.pageInfo;
        Intrinsics.checkNotNullParameter(curPageInfo, "curPageInfo");
        controller2.mPageInfo = curPageInfo;
        Context context2 = controller2.mContext;
        Intrinsics.checkNotNull(
                context2, "null cannot be cast to non-null type android.app.Application");
        ControllerFactory controllerFactory =
                new ControllerFactory((Application) context2, controller2.mInstanceId);
        ViewModelStoreOwner owner = controller2.owner;
        Intrinsics.checkNotNullParameter(owner, "owner");
        ViewModelStore store = owner.getViewModelStore();
        boolean z3 = owner instanceof HasDefaultViewModelProviderFactory;
        CreationExtras defaultCreationExtras =
                z3
                        ? ((HasDefaultViewModelProviderFactory) owner)
                                .getDefaultViewModelCreationExtras()
                        : CreationExtras.Empty.INSTANCE;
        Intrinsics.checkNotNullParameter(store, "store");
        Intrinsics.checkNotNullParameter(defaultCreationExtras, "defaultCreationExtras");
        ViewModelProviderImpl viewModelProviderImpl =
                new ViewModelProviderImpl(store, controllerFactory, defaultCreationExtras);
        KClass kotlinClass = JvmClassMappingKt.getKotlinClass(OverViewController.class);
        String qualifiedName = kotlinClass.getQualifiedName();
        if (qualifiedName == null) {
            throw new IllegalArgumentException(
                    "Local and anonymous classes can not be ViewModels".toString());
        }
        OverViewController overViewController =
                (OverViewController)
                        viewModelProviderImpl.getViewModel$lifecycle_viewmodel_release(
                                kotlinClass,
                                "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                        .concat(qualifiedName));
        controller2.overViewController = overViewController;
        overViewController.onCreate(controller2.mPageInfo);
        ViewModelStore store2 = owner.getViewModelStore();
        CreationExtras defaultCreationExtras2 =
                z3
                        ? ((HasDefaultViewModelProviderFactory) owner)
                                .getDefaultViewModelCreationExtras()
                        : CreationExtras.Empty.INSTANCE;
        Intrinsics.checkNotNullParameter(store2, "store");
        Intrinsics.checkNotNullParameter(defaultCreationExtras2, "defaultCreationExtras");
        ViewModelProviderImpl viewModelProviderImpl2 =
                new ViewModelProviderImpl(store2, controllerFactory, defaultCreationExtras2);
        KClass kotlinClass2 = JvmClassMappingKt.getKotlinClass(RecommendCardController.class);
        String qualifiedName2 = kotlinClass2.getQualifiedName();
        if (qualifiedName2 == null) {
            throw new IllegalArgumentException(
                    "Local and anonymous classes can not be ViewModels".toString());
        }
        controller2.recommendCardController =
                (RecommendCardController)
                        viewModelProviderImpl2.getViewModel$lifecycle_viewmodel_release(
                                kotlinClass2,
                                "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                        .concat(qualifiedName2));
        BroadcastReceiveCenter broadcastReceiveCenter =
                BroadcastReceiveCenter.InstanceHolder.INSTANCE;
        Context applicationContext2 = controller2.application.getApplicationContext();
        broadcastReceiveCenter.getClass();
        try {
            broadcastReceiveCenter.mStorageBroadcastHandler.mNotifyBroadcastListener =
                    broadcastReceiveCenter;
            ArrayList arrayList = new ArrayList();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.os.storage.action.VOLUME_STATE_CHANGED");
            arrayList.add(intentFilter);
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("android.intent.action.MEDIA_REMOVED");
            intentFilter2.addDataScheme("file");
            arrayList.add(intentFilter2);
            arrayList.stream()
                    .filter(new BroadcastReceiveCenter$$ExternalSyntheticLambda3())
                    .forEach(
                            new BroadcastReceiveCenter$$ExternalSyntheticLambda1(
                                    2, broadcastReceiveCenter, applicationContext2));
        } catch (IllegalArgumentException e) {
            Log.e("BroadcastReceiveCenter", "init() ] IllegalArgumentException:" + e);
        }
        FileListObserver fileListObserver = controller2.listObserver;
        fileListObserver.mContentObserver = controller2;
        if (fileListObserver.mContext.get() != null) {
            String path =
                    ((AbsPageController)
                                    ((PageControllerInterface)
                                            fileListObserver.mControllerWeakReference.get()))
                            .mPageInfo.getPath();
            Log.d("FileListObserver", "addObserver() ] Encoded path = " + Log.getEncodedMsg(path));
            FileListObserver.AnonymousClass1 anonymousClass1 = fileListObserver.mObserverHandler;
            anonymousClass1.sendMessageDelayed(anonymousClass1.obtainMessage(0, path), (long) 0);
        }
        OverViewController overViewController2 = getController().overViewController;
        if (overViewController2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("overViewController");
            throw null;
        }
        overViewController2.updateUsageInfo(false, false);
        Context context3 = ActivityInfoStore.context;
        ActivityInfoStore.Companion.getInstance(this.instanceId).register(this);
        Trace.endSection();
        setContentView(R.layout.as_activity_main);
        View requireViewById = requireViewById(R.id.toolbar);
        Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(...)");
        setSupportActionBar((Toolbar) requireViewById);
        String string = getString(R.string.as_app_title);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeButtonEnabled();
            supportActionBar.setDisplayShowTitleEnabled(true);
            supportActionBar.setTitle(string);
            supportActionBar.setBackgroundDrawable(null);
        }
        View requireViewById2 = requireViewById(R.id.collapsing_app_bar);
        Intrinsics.checkNotNullExpressionValue(requireViewById2, "requireViewById(...)");
        ((CollapsingToolbarLayout) requireViewById2).setTitle(string);
        UiUtils.initStatusBar(this);
        getLayoutWidthManager()
                .setContentWidthDp(getResources().getConfiguration().screenWidthDp, false);
        LayoutWidthManager layoutWidthManager = getLayoutWidthManager();
        layoutWidthManager.getClass();
        if (!((ArrayList) layoutWidthManager.listenerList).contains(this)) {
            ((ArrayList) layoutWidthManager.listenerList).add(this);
        }
        this.layoutState = getLayoutWidthManager().contentWidthDp >= 589 ? 1 : 0;
        if (SepFeatures$FloatingFeature.SUPPORT_TRASH) {
            TrashManager.isLoadCompleted.observe(
                    this,
                    new Observer(
                            this) { // from class:
                                    // com.samsung.android.settings.analyzestorage.ui.MainActivity$initAppTrashObserver$1
                        public final /* synthetic */ MainActivity this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // androidx.lifecycle.Observer
                        public final void onChanged(Object obj) {
                            MainActivity mainActivity = this.this$0;
                            switch (i) {
                                case 0:
                                    Boolean bool = (Boolean) obj;
                                    Intrinsics.checkNotNull(bool);
                                    if (bool.booleanValue()) {
                                        int i7 = MainActivity.$r8$clinit;
                                        OverViewController overViewController3 =
                                                mainActivity.getController().overViewController;
                                        if (overViewController3 != null) {
                                            overViewController3.updateUsageInfo(true, true);
                                            return;
                                        } else {
                                            Intrinsics.throwUninitializedPropertyAccessException(
                                                    "overViewController");
                                            throw null;
                                        }
                                    }
                                    return;
                                default:
                                    Long l = (Long) obj;
                                    SparseArray sparseArray = mainActivity.subListArray;
                                    int size = sparseArray.size();
                                    for (int i8 = 0; i8 < size; i8++) {
                                        sparseArray.keyAt(i8);
                                        AnalyzeStorageSubList analyzeStorageSubList =
                                                (AnalyzeStorageSubList) sparseArray.valueAt(i8);
                                        Intrinsics.checkNotNull(l);
                                        long longValue = l.longValue();
                                        MutableLiveData listItemTotalSizeData =
                                                analyzeStorageSubList.getListItemTotalSizeData();
                                        Long l2 =
                                                listItemTotalSizeData != null
                                                        ? (Long) listItemTotalSizeData.getValue()
                                                        : null;
                                        boolean z4 =
                                                l2 != null
                                                        && l2.longValue() >= longValue
                                                        && StorageUsageManager.getStorageFreeSpace(
                                                                        0)
                                                                < 1000000000;
                                        View view = analyzeStorageSubList.rootView;
                                        ImageView imageView =
                                                view != null
                                                        ? (ImageView)
                                                                view.findViewById(R.id.error_icon)
                                                        : null;
                                        if (imageView != null) {
                                            imageView.setVisibility(z4 ? 0 : 8);
                                        }
                                    }
                                    return;
                            }
                        }
                    });
        }
        if (this.isRecreated) {
            Clipboard clipboard = Clipboard.ClipboardHolder.instance;
            ArrayList arrayList2 = (ArrayList) clipboard.recommendAppList.get(this.instanceId);
            if (arrayList2 == null) {
                arrayList2 = new ArrayList();
            }
            AsRecommendCardInfoManager.appList = arrayList2;
            ArrayList arrayList3 = (ArrayList) clipboard.recommendCloudList.get(this.instanceId);
            if (arrayList3 == null) {
                arrayList3 = new ArrayList();
            }
            AsRecommendCardInfoManager.cloudList = arrayList3;
        }
        int intExtra = getIntent().getIntExtra("domainType", -1);
        View requireViewById3 = requireViewById(R.id.overview_container);
        Intrinsics.checkNotNullExpressionValue(requireViewById3, "requireViewById(...)");
        OverViewController overViewController3 = getController().overViewController;
        if (overViewController3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("overViewController");
            throw null;
        }
        final OverView overView =
                new OverView(requireViewById3, this, overViewController3, intExtra);
        overView.layoutState = this.layoutState;
        Trace.beginSection("OverView_initLayout");
        overViewController3.checkSupportedStorageList();
        MutableLiveData mutableLiveData3 = overViewController3.mQuotaStorageList;
        Intrinsics.checkNotNullExpressionValue(mutableLiveData3, "getSupportedStorageList(...)");
        overView.supportedStorageList = mutableLiveData3;
        int parseInt = Integer.parseInt(PageType.ANALYZE_STORAGE_HOME.getScreenIdForSA());
        ArrayList arrayList4 = (ArrayList) overView.supportedStorageList.getValue();
        LoggingHelper.insertEventLogging(
                parseInt,
                8815,
                String.valueOf(arrayList4 != null ? Integer.valueOf(arrayList4.size()) : null));
        overView.initIndicator();
        ArrayList arrayList5 = (ArrayList) overView.supportedStorageList.getValue();
        if (arrayList5 != null) {
            AsIndicatorAdapter indicatorAdapter = overView.getIndicatorAdapter();
            indicatorAdapter.getClass();
            indicatorAdapter.supportedList = arrayList5;
            overView.indicator.setNumColumns(arrayList5.size());
            overView.indicator.setAdapter((ListAdapter) overView.getIndicatorAdapter());
        }
        ArrayList arrayList6 = (ArrayList) overView.supportedStorageList.getValue();
        if (arrayList6 != null) {
            ViewPagerAdapter viewPagerAdapter =
                    new ViewPagerAdapter(this, arrayList6, overViewController3);
            viewPagerAdapter.setLayoutState(overView.layoutState);
            overView.adapter = viewPagerAdapter;
        }
        ViewPagerAdapter viewPagerAdapter2 = overView.adapter;
        ViewPager2 viewPager2 = overView.viewPager;
        viewPager2.setAdapter(viewPagerAdapter2);
        View childAt = viewPager2.getChildAt(0);
        RecyclerView recyclerView = childAt instanceof RecyclerView ? (RecyclerView) childAt : null;
        if (recyclerView != null) {
            recyclerView.setNestedScrollingEnabled(false);
        }
        StorageOperationManager.StorageOperationManagerHolder.INSTANCE.mUpdateListenerSet.add(
                overView);
        if (intExtra <= 0) {
            intExtra = 0;
        }
        ArrayList arrayList7 = (ArrayList) overView.supportedStorageList.getValue();
        int indexOf = arrayList7 != null ? arrayList7.indexOf(Integer.valueOf(intExtra)) : 0;
        if (indexOf > 0) {
            viewPager2.setCurrentItem(indexOf, false);
            overView.getIndicatorAdapter().currentIndex = indexOf;
            overView.getIndicatorAdapter().notifyDataSetChanged();
        }
        viewPager2.registerOnPageChangeCallback(overView.pageChangeCallback);
        overView.supportedStorageList.observe(
                this,
                new Observer() { // from class:
                                 // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.OverView$observeSupportedStorageList$1
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        ArrayList arrayList8 = (ArrayList) obj;
                        Intrinsics.checkNotNull(arrayList8);
                        if (!arrayList8.isEmpty()) {
                            OverView overView2 = OverView.this;
                            overView2.initIndicator();
                            AsIndicatorAdapter indicatorAdapter2 = overView2.getIndicatorAdapter();
                            indicatorAdapter2.getClass();
                            indicatorAdapter2.supportedList = arrayList8;
                            overView2.indicator.setNumColumns(arrayList8.size());
                            overView2.indicator.setVisibility(arrayList8.size() < 2 ? 4 : 0);
                            overView2.indicator.setImportantForAccessibility(
                                    arrayList8.size() >= 2 ? 0 : 2);
                            overView2.indicator.setAdapter(
                                    (ListAdapter) overView2.getIndicatorAdapter());
                            ViewPagerAdapter viewPagerAdapter3 = overView2.adapter;
                            if (viewPagerAdapter3 != null) {
                                viewPagerAdapter3.storageList = arrayList8;
                                viewPagerAdapter3.notifyDataSetChanged();
                            }
                            overView2.controller.updateUsageInfo(true, false);
                        }
                    }
                });
        Trace.endSection();
        this.overView = overView;
        View requireViewById4 = requireViewById(R.id.recommend_card_container);
        Intrinsics.checkNotNullExpressionValue(requireViewById4, "requireViewById(...)");
        final RecommendCardView recommendCardView = new RecommendCardView(requireViewById4, this);
        RecommendCardController recommendCardController = getController().recommendCardController;
        if (recommendCardController == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recommendCardController");
            throw null;
        }
        recommendCardView.controller = recommendCardController;
        recommendCardView.layoutState = this.layoutState;
        boolean z4 = this.isRecreated;
        Trace.beginSection("RecommendCardView_initLayout");
        recommendCardView.updateLoadingLayout(true);
        RecommendCardController recommendCardController2 = recommendCardView.controller;
        if (recommendCardController2 != null
                && (mutableLiveData2 = recommendCardController2.isLoading) != null) {
            mutableLiveData2.observe(
                    this,
                    new Observer() { // from class:
                        // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.RecommendCardView$observeLoadingState$1

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                d1 = {
                                    "\u0000\n\n"
                                        + "\u0000\n"
                                        + "\u0002\u0010\u0002\n"
                                        + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
                                },
                                d2 = {
                                    "<anonymous>",
                                    ApnSettings.MVNO_NONE,
                                    "Lkotlinx/coroutines/CoroutineScope;"
                                },
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.RecommendCardView$observeLoadingState$1$1, reason: invalid class name */
                        final class AnonymousClass1 extends SuspendLambda implements Function2 {
                            int label;
                            final /* synthetic */ RecommendCardView this$0;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            public AnonymousClass1(
                                    RecommendCardView recommendCardView,
                                    Continuation continuation) {
                                super(2, continuation);
                                this.this$0 = recommendCardView;
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Continuation create(
                                    Object obj, Continuation continuation) {
                                return new AnonymousClass1(this.this$0, continuation);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                return ((AnonymousClass1)
                                                create((CoroutineScope) obj, (Continuation) obj2))
                                        .invokeSuspend(Unit.INSTANCE);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                CoroutineSingletons coroutineSingletons =
                                        CoroutineSingletons.COROUTINE_SUSPENDED;
                                int i = this.label;
                                if (i == 0) {
                                    ResultKt.throwOnFailure(obj);
                                    this.label = 1;
                                    if (DelayKt.delay(1000L, this) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                } else {
                                    if (i != 1) {
                                        throw new IllegalStateException(
                                                "call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj);
                                }
                                RecommendCardView recommendCardView = this.this$0;
                                ShimmerFrameLayout shimmerFrameLayout =
                                        recommendCardView.loadingView;
                                if (shimmerFrameLayout != null) {
                                    shimmerFrameLayout.setVisibility(0);
                                }
                                LinearLayout linearLayout = recommendCardView.loadingViewPager;
                                if (linearLayout != null) {
                                    linearLayout.setVisibility(0);
                                }
                                recommendCardView.updateLoadingLayout(true);
                                return Unit.INSTANCE;
                            }
                        }

                        @Override // androidx.lifecycle.Observer
                        public final void onChanged(Object obj) {
                            MutableLiveData mutableLiveData4;
                            RecommendCardState recommendCardState;
                            ArrayList arrayList8;
                            MutableLiveData mutableLiveData5;
                            RecommendCardState recommendCardState2;
                            ArrayList arrayList9;
                            final RecommendCardView recommendCardView2 = recommendCardView;
                            switch (i) {
                                case 0:
                                    Boolean bool = (Boolean) obj;
                                    Intrinsics.checkNotNull(bool);
                                    if (!bool.booleanValue()) {
                                        Job job = recommendCardView2.delayLoadingJob;
                                        if (job != null) {
                                            job.cancel(null);
                                        }
                                        recommendCardView2.delayLoadingJob = null;
                                        ShimmerFrameLayout shimmerFrameLayout =
                                                recommendCardView2.loadingView;
                                        if (shimmerFrameLayout != null) {
                                            shimmerFrameLayout.setVisibility(8);
                                        }
                                        recommendCardView2.viewPagerLayout.setVisibility(0);
                                        break;
                                    } else if (!recommendCardView2.inLoadingProgress) {
                                        recommendCardView2.inLoadingProgress = true;
                                        if (recommendCardView2.delayLoadingJob == null) {
                                            DefaultScheduler defaultScheduler = Dispatchers.Default;
                                            recommendCardView2.delayLoadingJob =
                                                    BuildersKt.launch$default(
                                                            CoroutineScopeKt.CoroutineScope(
                                                                    MainDispatcherLoader
                                                                            .dispatcher),
                                                            null,
                                                            null,
                                                            new AnonymousClass1(
                                                                    recommendCardView2, null),
                                                            3);
                                            break;
                                        }
                                    }
                                    break;
                                default:
                                    RecommendCardState recommendCardState3 =
                                            (RecommendCardState) obj;
                                    ArrayList arrayList10 = recommendCardState3.supportedCardList;
                                    if (!arrayList10.isEmpty()) {
                                        RecommendCardController recommendCardController3 =
                                                recommendCardView2.controller;
                                        int size =
                                                (recommendCardController3 == null
                                                                || (mutableLiveData5 =
                                                                                recommendCardController3
                                                                                        .recommendCardState)
                                                                        == null
                                                                || (recommendCardState2 =
                                                                                (RecommendCardState)
                                                                                        mutableLiveData5
                                                                                                .getValue())
                                                                        == null
                                                                || (arrayList9 =
                                                                                recommendCardState2
                                                                                        .supportedCardList)
                                                                        == null)
                                                        ? 0
                                                        : arrayList9.size();
                                        FragmentActivity fragmentActivity =
                                                recommendCardView2.activity;
                                        int dimensionPixelSize =
                                                fragmentActivity
                                                        .getResources()
                                                        .getDimensionPixelSize(
                                                                R.dimen
                                                                        .as_home_viewpager_indicator_width);
                                        ViewGroup.LayoutParams layoutParams =
                                                recommendCardView2.indicator.getLayoutParams();
                                        layoutParams.width = dimensionPixelSize * size;
                                        recommendCardView2.indicator.setLayoutParams(layoutParams);
                                        recommendCardView2.indicator.setOnItemClickListener(
                                                new AdapterView
                                                        .OnItemClickListener() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.RecommendCardView$initRecommendCardIndicator$1
                                                    @Override // android.widget.AdapterView.OnItemClickListener
                                                    public final void onItemClick(
                                                            AdapterView adapterView,
                                                            View view,
                                                            int i7,
                                                            long j) {
                                                        RecommendCardView recommendCardView3 =
                                                                RecommendCardView.this;
                                                        ViewPager2 viewPager22 =
                                                                recommendCardView3.viewPager;
                                                        FragmentActivity context4 =
                                                                recommendCardView3.activity;
                                                        Intrinsics.checkNotNullParameter(
                                                                context4, "context");
                                                        viewPager22.setCurrentItem(
                                                                i7,
                                                                Settings.System.getInt(
                                                                                context4
                                                                                        .getContentResolver(),
                                                                                "remove_animations",
                                                                                0)
                                                                        != 1);
                                                    }
                                                });
                                        AsIndicatorAdapter recommendCardIndicatorAdapter =
                                                recommendCardView2
                                                        .getRecommendCardIndicatorAdapter();
                                        recommendCardIndicatorAdapter.getClass();
                                        recommendCardIndicatorAdapter.supportedList = arrayList10;
                                        recommendCardView2.indicator.setNumColumns(
                                                arrayList10.size());
                                        recommendCardView2.indicator.setAdapter(
                                                (ListAdapter)
                                                        recommendCardView2
                                                                .getRecommendCardIndicatorAdapter());
                                        recommendCardView2.indicator.setVisibility(
                                                arrayList10.size() < 2 ? 4 : 0);
                                        recommendCardView2.indicator.setImportantForAccessibility(
                                                arrayList10.size() < 2 ? 2 : 0);
                                        RecommendCardViewPagerAdapter
                                                recommendCardViewPagerAdapter =
                                                        recommendCardView2.adapter;
                                        ViewPager2 viewPager22 = recommendCardView2.viewPager;
                                        if (recommendCardViewPagerAdapter == null) {
                                            RecommendCardViewPagerAdapter
                                                    recommendCardViewPagerAdapter2 =
                                                            new RecommendCardViewPagerAdapter(
                                                                    fragmentActivity,
                                                                    recommendCardView2.controller);
                                            recommendCardViewPagerAdapter2.isLandScape =
                                                    recommendCardView2.layoutState == 1;
                                            recommendCardView2.adapter =
                                                    recommendCardViewPagerAdapter2;
                                            viewPager22.setAdapter(recommendCardViewPagerAdapter2);
                                        }
                                        Context baseContext = fragmentActivity.getBaseContext();
                                        Intrinsics.checkNotNullExpressionValue(
                                                baseContext, "getBaseContext(...)");
                                        if (!FeatureManager$UiFeature.isDefaultTheme(baseContext)) {
                                            viewPager22.setBackground(
                                                    fragmentActivity
                                                            .getBaseContext()
                                                            .getDrawable(
                                                                    R.drawable
                                                                            .as_theme_viewpager_round_stroke));
                                        }
                                        View childAt2 = viewPager22.getChildAt(0);
                                        RecyclerView recyclerView2 =
                                                childAt2 instanceof RecyclerView
                                                        ? (RecyclerView) childAt2
                                                        : null;
                                        if (recyclerView2 != null) {
                                            recyclerView2.setNestedScrollingEnabled(false);
                                        }
                                        RecommendCardController recommendCardController4 =
                                                recommendCardView2.controller;
                                        int indexOf2 =
                                                (recommendCardController4 == null
                                                                || (mutableLiveData4 =
                                                                                recommendCardController4
                                                                                        .recommendCardState)
                                                                        == null
                                                                || (recommendCardState =
                                                                                (RecommendCardState)
                                                                                        mutableLiveData4
                                                                                                .getValue())
                                                                        == null
                                                                || (arrayList8 =
                                                                                recommendCardState
                                                                                        .supportedCardList)
                                                                        == null)
                                                        ? 0
                                                        : arrayList8.indexOf(-1);
                                        if (indexOf2 > 0) {
                                            viewPager22.setCurrentItem(indexOf2, false);
                                            recommendCardView2.getRecommendCardIndicatorAdapter()
                                                            .currentIndex =
                                                    indexOf2;
                                            recommendCardView2
                                                    .getRecommendCardIndicatorAdapter()
                                                    .notifyDataSetChanged();
                                        }
                                        viewPager22.registerOnPageChangeCallback(
                                                recommendCardView2.pageChangeCallback);
                                        viewPager22.setPageTransformer(
                                                new ViewPager2.PageTransformer() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.RecommendCardView$bindRecommendCardViewPager$2
                                                    @Override // androidx.viewpager2.widget.ViewPager2.PageTransformer
                                                    public final void transformPage(
                                                            View view, float f) {
                                                        RecommendCardView recommendCardView3 =
                                                                RecommendCardView.this;
                                                        recommendCardView3.updateHeightCard(
                                                                recommendCardView3
                                                                        .viewPager
                                                                        .mCurrentItem);
                                                    }
                                                });
                                    } else {
                                        recommendCardView2.root.setVisibility(8);
                                    }
                                    int i7 = recommendCardState3.type;
                                    if (i7 == 0) {
                                        RecommendCardViewPagerAdapter
                                                recommendCardViewPagerAdapter3 =
                                                        recommendCardView2.adapter;
                                        if (recommendCardViewPagerAdapter3 != null) {
                                            RecommendCardController recommendCardController5 =
                                                    recommendCardViewPagerAdapter3.controller;
                                            if (recommendCardController5 != null) {
                                                recommendCardController5
                                                                .supportedRecommendCardListTemp =
                                                        arrayList10;
                                            }
                                            recommendCardViewPagerAdapter3.notifyDataSetChanged();
                                        }
                                        LoggingHelper.insertEventLogging(
                                                Integer.parseInt(
                                                        PageType.ANALYZE_STORAGE_HOME
                                                                .getScreenIdForSA()),
                                                8816,
                                                String.valueOf(arrayList10.size()));
                                    } else if (i7 == 1) {
                                        RecommendCardViewPagerAdapter
                                                recommendCardViewPagerAdapter4 =
                                                        recommendCardView2.adapter;
                                        if (recommendCardViewPagerAdapter4 != null) {
                                            recommendCardViewPagerAdapter4.notifyItemRemoved(
                                                    recommendCardState3.removedCardPosition);
                                        }
                                    } else if (i7 == 2) {
                                        RecommendCardViewPagerAdapter
                                                recommendCardViewPagerAdapter5 =
                                                        recommendCardView2.adapter;
                                        if (recommendCardViewPagerAdapter5 != null) {
                                            recommendCardViewPagerAdapter5.notifyItemChanged(
                                                    recommendCardState3.updatedCardPosition);
                                        }
                                        RecommendCardViewPagerAdapter
                                                recommendCardViewPagerAdapter6 =
                                                        recommendCardView2.adapter;
                                        if (recommendCardViewPagerAdapter6 != null) {
                                            recommendCardViewPagerAdapter6.notifyItemChanged(
                                                    arrayList10.size() - 1);
                                        }
                                    }
                                    recommendCardView2.updateHeightCard(
                                            recommendCardView2.getRecommendCardIndicatorAdapter()
                                                    .currentIndex);
                                    break;
                            }
                        }
                    });
        }
        RecommendCardController recommendCardController3 = recommendCardView.controller;
        if (recommendCardController3 != null
                && (mutableLiveData = recommendCardController3.recommendCardState) != null) {
            mutableLiveData.observe(
                    this,
                    new Observer() { // from class:
                        // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.RecommendCardView$observeLoadingState$1

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                d1 = {
                                    "\u0000\n\n"
                                        + "\u0000\n"
                                        + "\u0002\u0010\u0002\n"
                                        + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
                                },
                                d2 = {
                                    "<anonymous>",
                                    ApnSettings.MVNO_NONE,
                                    "Lkotlinx/coroutines/CoroutineScope;"
                                },
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.RecommendCardView$observeLoadingState$1$1, reason: invalid class name */
                        final class AnonymousClass1 extends SuspendLambda implements Function2 {
                            int label;
                            final /* synthetic */ RecommendCardView this$0;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            public AnonymousClass1(
                                    RecommendCardView recommendCardView,
                                    Continuation continuation) {
                                super(2, continuation);
                                this.this$0 = recommendCardView;
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Continuation create(
                                    Object obj, Continuation continuation) {
                                return new AnonymousClass1(this.this$0, continuation);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                return ((AnonymousClass1)
                                                create((CoroutineScope) obj, (Continuation) obj2))
                                        .invokeSuspend(Unit.INSTANCE);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                CoroutineSingletons coroutineSingletons =
                                        CoroutineSingletons.COROUTINE_SUSPENDED;
                                int i = this.label;
                                if (i == 0) {
                                    ResultKt.throwOnFailure(obj);
                                    this.label = 1;
                                    if (DelayKt.delay(1000L, this) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                } else {
                                    if (i != 1) {
                                        throw new IllegalStateException(
                                                "call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj);
                                }
                                RecommendCardView recommendCardView = this.this$0;
                                ShimmerFrameLayout shimmerFrameLayout =
                                        recommendCardView.loadingView;
                                if (shimmerFrameLayout != null) {
                                    shimmerFrameLayout.setVisibility(0);
                                }
                                LinearLayout linearLayout = recommendCardView.loadingViewPager;
                                if (linearLayout != null) {
                                    linearLayout.setVisibility(0);
                                }
                                recommendCardView.updateLoadingLayout(true);
                                return Unit.INSTANCE;
                            }
                        }

                        @Override // androidx.lifecycle.Observer
                        public final void onChanged(Object obj) {
                            MutableLiveData mutableLiveData4;
                            RecommendCardState recommendCardState;
                            ArrayList arrayList8;
                            MutableLiveData mutableLiveData5;
                            RecommendCardState recommendCardState2;
                            ArrayList arrayList9;
                            final RecommendCardView recommendCardView2 = recommendCardView;
                            switch (i2) {
                                case 0:
                                    Boolean bool = (Boolean) obj;
                                    Intrinsics.checkNotNull(bool);
                                    if (!bool.booleanValue()) {
                                        Job job = recommendCardView2.delayLoadingJob;
                                        if (job != null) {
                                            job.cancel(null);
                                        }
                                        recommendCardView2.delayLoadingJob = null;
                                        ShimmerFrameLayout shimmerFrameLayout =
                                                recommendCardView2.loadingView;
                                        if (shimmerFrameLayout != null) {
                                            shimmerFrameLayout.setVisibility(8);
                                        }
                                        recommendCardView2.viewPagerLayout.setVisibility(0);
                                        break;
                                    } else if (!recommendCardView2.inLoadingProgress) {
                                        recommendCardView2.inLoadingProgress = true;
                                        if (recommendCardView2.delayLoadingJob == null) {
                                            DefaultScheduler defaultScheduler = Dispatchers.Default;
                                            recommendCardView2.delayLoadingJob =
                                                    BuildersKt.launch$default(
                                                            CoroutineScopeKt.CoroutineScope(
                                                                    MainDispatcherLoader
                                                                            .dispatcher),
                                                            null,
                                                            null,
                                                            new AnonymousClass1(
                                                                    recommendCardView2, null),
                                                            3);
                                            break;
                                        }
                                    }
                                    break;
                                default:
                                    RecommendCardState recommendCardState3 =
                                            (RecommendCardState) obj;
                                    ArrayList arrayList10 = recommendCardState3.supportedCardList;
                                    if (!arrayList10.isEmpty()) {
                                        RecommendCardController recommendCardController32 =
                                                recommendCardView2.controller;
                                        int size =
                                                (recommendCardController32 == null
                                                                || (mutableLiveData5 =
                                                                                recommendCardController32
                                                                                        .recommendCardState)
                                                                        == null
                                                                || (recommendCardState2 =
                                                                                (RecommendCardState)
                                                                                        mutableLiveData5
                                                                                                .getValue())
                                                                        == null
                                                                || (arrayList9 =
                                                                                recommendCardState2
                                                                                        .supportedCardList)
                                                                        == null)
                                                        ? 0
                                                        : arrayList9.size();
                                        FragmentActivity fragmentActivity =
                                                recommendCardView2.activity;
                                        int dimensionPixelSize =
                                                fragmentActivity
                                                        .getResources()
                                                        .getDimensionPixelSize(
                                                                R.dimen
                                                                        .as_home_viewpager_indicator_width);
                                        ViewGroup.LayoutParams layoutParams =
                                                recommendCardView2.indicator.getLayoutParams();
                                        layoutParams.width = dimensionPixelSize * size;
                                        recommendCardView2.indicator.setLayoutParams(layoutParams);
                                        recommendCardView2.indicator.setOnItemClickListener(
                                                new AdapterView
                                                        .OnItemClickListener() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.RecommendCardView$initRecommendCardIndicator$1
                                                    @Override // android.widget.AdapterView.OnItemClickListener
                                                    public final void onItemClick(
                                                            AdapterView adapterView,
                                                            View view,
                                                            int i7,
                                                            long j) {
                                                        RecommendCardView recommendCardView3 =
                                                                RecommendCardView.this;
                                                        ViewPager2 viewPager22 =
                                                                recommendCardView3.viewPager;
                                                        FragmentActivity context4 =
                                                                recommendCardView3.activity;
                                                        Intrinsics.checkNotNullParameter(
                                                                context4, "context");
                                                        viewPager22.setCurrentItem(
                                                                i7,
                                                                Settings.System.getInt(
                                                                                context4
                                                                                        .getContentResolver(),
                                                                                "remove_animations",
                                                                                0)
                                                                        != 1);
                                                    }
                                                });
                                        AsIndicatorAdapter recommendCardIndicatorAdapter =
                                                recommendCardView2
                                                        .getRecommendCardIndicatorAdapter();
                                        recommendCardIndicatorAdapter.getClass();
                                        recommendCardIndicatorAdapter.supportedList = arrayList10;
                                        recommendCardView2.indicator.setNumColumns(
                                                arrayList10.size());
                                        recommendCardView2.indicator.setAdapter(
                                                (ListAdapter)
                                                        recommendCardView2
                                                                .getRecommendCardIndicatorAdapter());
                                        recommendCardView2.indicator.setVisibility(
                                                arrayList10.size() < 2 ? 4 : 0);
                                        recommendCardView2.indicator.setImportantForAccessibility(
                                                arrayList10.size() < 2 ? 2 : 0);
                                        RecommendCardViewPagerAdapter
                                                recommendCardViewPagerAdapter =
                                                        recommendCardView2.adapter;
                                        ViewPager2 viewPager22 = recommendCardView2.viewPager;
                                        if (recommendCardViewPagerAdapter == null) {
                                            RecommendCardViewPagerAdapter
                                                    recommendCardViewPagerAdapter2 =
                                                            new RecommendCardViewPagerAdapter(
                                                                    fragmentActivity,
                                                                    recommendCardView2.controller);
                                            recommendCardViewPagerAdapter2.isLandScape =
                                                    recommendCardView2.layoutState == 1;
                                            recommendCardView2.adapter =
                                                    recommendCardViewPagerAdapter2;
                                            viewPager22.setAdapter(recommendCardViewPagerAdapter2);
                                        }
                                        Context baseContext = fragmentActivity.getBaseContext();
                                        Intrinsics.checkNotNullExpressionValue(
                                                baseContext, "getBaseContext(...)");
                                        if (!FeatureManager$UiFeature.isDefaultTheme(baseContext)) {
                                            viewPager22.setBackground(
                                                    fragmentActivity
                                                            .getBaseContext()
                                                            .getDrawable(
                                                                    R.drawable
                                                                            .as_theme_viewpager_round_stroke));
                                        }
                                        View childAt2 = viewPager22.getChildAt(0);
                                        RecyclerView recyclerView2 =
                                                childAt2 instanceof RecyclerView
                                                        ? (RecyclerView) childAt2
                                                        : null;
                                        if (recyclerView2 != null) {
                                            recyclerView2.setNestedScrollingEnabled(false);
                                        }
                                        RecommendCardController recommendCardController4 =
                                                recommendCardView2.controller;
                                        int indexOf2 =
                                                (recommendCardController4 == null
                                                                || (mutableLiveData4 =
                                                                                recommendCardController4
                                                                                        .recommendCardState)
                                                                        == null
                                                                || (recommendCardState =
                                                                                (RecommendCardState)
                                                                                        mutableLiveData4
                                                                                                .getValue())
                                                                        == null
                                                                || (arrayList8 =
                                                                                recommendCardState
                                                                                        .supportedCardList)
                                                                        == null)
                                                        ? 0
                                                        : arrayList8.indexOf(-1);
                                        if (indexOf2 > 0) {
                                            viewPager22.setCurrentItem(indexOf2, false);
                                            recommendCardView2.getRecommendCardIndicatorAdapter()
                                                            .currentIndex =
                                                    indexOf2;
                                            recommendCardView2
                                                    .getRecommendCardIndicatorAdapter()
                                                    .notifyDataSetChanged();
                                        }
                                        viewPager22.registerOnPageChangeCallback(
                                                recommendCardView2.pageChangeCallback);
                                        viewPager22.setPageTransformer(
                                                new ViewPager2.PageTransformer() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.RecommendCardView$bindRecommendCardViewPager$2
                                                    @Override // androidx.viewpager2.widget.ViewPager2.PageTransformer
                                                    public final void transformPage(
                                                            View view, float f) {
                                                        RecommendCardView recommendCardView3 =
                                                                RecommendCardView.this;
                                                        recommendCardView3.updateHeightCard(
                                                                recommendCardView3
                                                                        .viewPager
                                                                        .mCurrentItem);
                                                    }
                                                });
                                    } else {
                                        recommendCardView2.root.setVisibility(8);
                                    }
                                    int i7 = recommendCardState3.type;
                                    if (i7 == 0) {
                                        RecommendCardViewPagerAdapter
                                                recommendCardViewPagerAdapter3 =
                                                        recommendCardView2.adapter;
                                        if (recommendCardViewPagerAdapter3 != null) {
                                            RecommendCardController recommendCardController5 =
                                                    recommendCardViewPagerAdapter3.controller;
                                            if (recommendCardController5 != null) {
                                                recommendCardController5
                                                                .supportedRecommendCardListTemp =
                                                        arrayList10;
                                            }
                                            recommendCardViewPagerAdapter3.notifyDataSetChanged();
                                        }
                                        LoggingHelper.insertEventLogging(
                                                Integer.parseInt(
                                                        PageType.ANALYZE_STORAGE_HOME
                                                                .getScreenIdForSA()),
                                                8816,
                                                String.valueOf(arrayList10.size()));
                                    } else if (i7 == 1) {
                                        RecommendCardViewPagerAdapter
                                                recommendCardViewPagerAdapter4 =
                                                        recommendCardView2.adapter;
                                        if (recommendCardViewPagerAdapter4 != null) {
                                            recommendCardViewPagerAdapter4.notifyItemRemoved(
                                                    recommendCardState3.removedCardPosition);
                                        }
                                    } else if (i7 == 2) {
                                        RecommendCardViewPagerAdapter
                                                recommendCardViewPagerAdapter5 =
                                                        recommendCardView2.adapter;
                                        if (recommendCardViewPagerAdapter5 != null) {
                                            recommendCardViewPagerAdapter5.notifyItemChanged(
                                                    recommendCardState3.updatedCardPosition);
                                        }
                                        RecommendCardViewPagerAdapter
                                                recommendCardViewPagerAdapter6 =
                                                        recommendCardView2.adapter;
                                        if (recommendCardViewPagerAdapter6 != null) {
                                            recommendCardViewPagerAdapter6.notifyItemChanged(
                                                    arrayList10.size() - 1);
                                        }
                                    }
                                    recommendCardView2.updateHeightCard(
                                            recommendCardView2.getRecommendCardIndicatorAdapter()
                                                    .currentIndex);
                                    break;
                            }
                        }
                    });
        }
        RecommendCardController recommendCardController4 = recommendCardView.controller;
        if (recommendCardController4 != null) {
            recommendCardController4.chooseAccountListener = recommendCardView;
        }
        if (!z4 && recommendCardController4 != null) {
            recommendCardController4.updateSupportedRecommendCardList();
        }
        Trace.endSection();
        this.recommendCardView = recommendCardView;
        for (AsSubListFactory asSubListFactory : AsSubListFactory.values()) {
            if (asSubListFactory.isFileType()) {
                Context baseContext = getBaseContext();
                Intrinsics.checkNotNullExpressionValue(baseContext, "getBaseContext(...)");
                asSubAppList = new AsSubFileList(baseContext, this, asSubListFactory.getSaType());
                asSubAppList.logTag = "AsSubFileList";
                controller = getController().getController(asSubListFactory.getSaType());
                asSubAppList.controller = (SubFileListController) controller;
            } else {
                Context baseContext2 = getBaseContext();
                Intrinsics.checkNotNullExpressionValue(baseContext2, "getBaseContext(...)");
                asSubAppList = new AsSubAppList(baseContext2, this, asSubListFactory.getSaType());
                asSubAppList.logTag = "AsSubAppList";
                controller = getController().getController(asSubListFactory.getSaType());
                asSubAppList.controller = (AppListController) controller;
            }
            controller.mListener.mExceptionListener = new ExceptionHandler();
            this.subListArray.append(asSubListFactory.getSaType(), asSubAppList);
        }
        final HomeController controller3 = getController();
        controller3.getClass();
        Iterator it = controller3.subFileListController.entrySet().iterator();
        while (it.hasNext()) {
            ((SubFileListController) ((Map.Entry) it.next()).getValue())
                    .mListItemTotalSizeData.observe(
                            this,
                            new Observer() { // from class:
                                             // com.samsung.android.settings.analyzestorage.presenter.controllers.HomeController$observeSubLists$1$1
                                @Override // androidx.lifecycle.Observer
                                public final void onChanged(Object obj) {
                                    switch (i) {
                                        case 0:
                                            HomeController.access$updateThirdBigSize(controller3);
                                            break;
                                        default:
                                            HomeController.access$updateThirdBigSize(controller3);
                                            break;
                                    }
                                }
                            });
        }
        Iterator it2 = controller3.appListController.entrySet().iterator();
        while (it2.hasNext()) {
            ((AppListController) ((Map.Entry) it2.next()).getValue())
                    .mAppListTotalSizeData.observe(
                            this,
                            new Observer() { // from class:
                                             // com.samsung.android.settings.analyzestorage.presenter.controllers.HomeController$observeSubLists$1$1
                                @Override // androidx.lifecycle.Observer
                                public final void onChanged(Object obj) {
                                    switch (i2) {
                                        case 0:
                                            HomeController.access$updateThirdBigSize(controller3);
                                            break;
                                        default:
                                            HomeController.access$updateThirdBigSize(controller3);
                                            break;
                                    }
                                }
                            });
        }
        getController()
                .mThirdBigSpace
                .observe(
                        this,
                        new Observer(
                                this) { // from class:
                                        // com.samsung.android.settings.analyzestorage.ui.MainActivity$initAppTrashObserver$1
                            public final /* synthetic */ MainActivity this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                MainActivity mainActivity = this.this$0;
                                switch (i2) {
                                    case 0:
                                        Boolean bool = (Boolean) obj;
                                        Intrinsics.checkNotNull(bool);
                                        if (bool.booleanValue()) {
                                            int i7 = MainActivity.$r8$clinit;
                                            OverViewController overViewController32 =
                                                    mainActivity.getController().overViewController;
                                            if (overViewController32 != null) {
                                                overViewController32.updateUsageInfo(true, true);
                                                return;
                                            } else {
                                                Intrinsics
                                                        .throwUninitializedPropertyAccessException(
                                                                "overViewController");
                                                throw null;
                                            }
                                        }
                                        return;
                                    default:
                                        Long l = (Long) obj;
                                        SparseArray sparseArray = mainActivity.subListArray;
                                        int size = sparseArray.size();
                                        for (int i8 = 0; i8 < size; i8++) {
                                            sparseArray.keyAt(i8);
                                            AnalyzeStorageSubList analyzeStorageSubList =
                                                    (AnalyzeStorageSubList) sparseArray.valueAt(i8);
                                            Intrinsics.checkNotNull(l);
                                            long longValue = l.longValue();
                                            MutableLiveData listItemTotalSizeData =
                                                    analyzeStorageSubList
                                                            .getListItemTotalSizeData();
                                            Long l2 =
                                                    listItemTotalSizeData != null
                                                            ? (Long)
                                                                    listItemTotalSizeData.getValue()
                                                            : null;
                                            boolean z42 =
                                                    l2 != null
                                                            && l2.longValue() >= longValue
                                                            && StorageUsageManager
                                                                            .getStorageFreeSpace(0)
                                                                    < 1000000000;
                                            View view = analyzeStorageSubList.rootView;
                                            ImageView imageView =
                                                    view != null
                                                            ? (ImageView)
                                                                    view.findViewById(
                                                                            R.id.error_icon)
                                                            : null;
                                            if (imageView != null) {
                                                imageView.setVisibility(z42 ? 0 : 8);
                                            }
                                        }
                                        return;
                                }
                            }
                        });
        SparseArray sparseArray = this.subListArray;
        int size = sparseArray.size();
        while (i < size) {
            sparseArray.keyAt(i);
            final AnalyzeStorageSubList analyzeStorageSubList =
                    (AnalyzeStorageSubList) sparseArray.valueAt(i);
            this.handler.post(
                    new Runnable() { // from class:
                                     // com.samsung.android.settings.analyzestorage.ui.MainActivity$initLayout$4$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            AbsPageController absPageController;
                            ListItemHandler listItemHandler;
                            MutableLiveData mutableLiveData4;
                            AnalyzeStorageSubList analyzeStorageSubList2 =
                                    AnalyzeStorageSubList.this;
                            View requireViewById5 = this.requireViewById(R.id.contents_container);
                            Intrinsics.checkNotNullExpressionValue(
                                    requireViewById5, "requireViewById(...)");
                            analyzeStorageSubList2.getClass();
                            Log.d(analyzeStorageSubList2.logTag, "onCreate()");
                            View findViewById =
                                    requireViewById5.findViewById(
                                            analyzeStorageSubList2
                                                    .getAsSubList()
                                                    .getItemViewResId());
                            if (findViewById == null) {
                                return;
                            }
                            analyzeStorageSubList2.rootView = findViewById;
                            findViewById.setVisibility(
                                    analyzeStorageSubList2
                                                    .getAsSubList()
                                                    .visibleSubList(analyzeStorageSubList2.context)
                                            ? 0
                                            : 8);
                            analyzeStorageSubList2.headerSize =
                                    (TextView) findViewById.findViewById(R.id.size);
                            analyzeStorageSubList2.progressBar =
                                    (ProgressBar) findViewById.findViewById(R.id.progress_bar);
                            final TextView textView = analyzeStorageSubList2.headerSize;
                            LifecycleOwner lifecycleOwner = analyzeStorageSubList2.lifecycleOwner;
                            if (textView != null) {
                                AnalyzeStorageSubList.SizeInfo sizeInfo =
                                        analyzeStorageSubList2.sizeInfo;
                                final int i7 = 0;
                                sizeInfo.supportSize.observe(
                                        lifecycleOwner,
                                        new Observer() { // from class:
                                                         // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AnalyzeStorageSubList$onCreate$1$1$1
                                            @Override // androidx.lifecycle.Observer
                                            public final void onChanged(Object obj) {
                                                switch (i7) {
                                                    case 0:
                                                        Boolean bool = (Boolean) obj;
                                                        TextView textView2 = textView;
                                                        Intrinsics.checkNotNull(bool);
                                                        textView2.setVisibility(
                                                                bool.booleanValue() ? 0 : 8);
                                                        break;
                                                    default:
                                                        textView.setText((String) obj);
                                                        break;
                                                }
                                            }
                                        });
                                final int i8 = 1;
                                sizeInfo.totalSize.observe(
                                        lifecycleOwner,
                                        new Observer() { // from class:
                                                         // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AnalyzeStorageSubList$onCreate$1$1$1
                                            @Override // androidx.lifecycle.Observer
                                            public final void onChanged(Object obj) {
                                                switch (i8) {
                                                    case 0:
                                                        Boolean bool = (Boolean) obj;
                                                        TextView textView2 = textView;
                                                        Intrinsics.checkNotNull(bool);
                                                        textView2.setVisibility(
                                                                bool.booleanValue() ? 0 : 8);
                                                        break;
                                                    default:
                                                        textView.setText((String) obj);
                                                        break;
                                                }
                                            }
                                        });
                            }
                            View requireViewById6 = findViewById.requireViewById(R.id.divider);
                            Intrinsics.checkNotNullExpressionValue(
                                    requireViewById6, "requireViewById(...)");
                            requireViewById6.setVisibility(
                                    analyzeStorageSubList2
                                                    .getAsSubList()
                                                    .needDivider(analyzeStorageSubList2.context)
                                            ? 0
                                            : 8);
                            analyzeStorageSubList2.initLayout();
                            analyzeStorageSubList2.observeTotalSize();
                            MutableLiveData listLoading = analyzeStorageSubList2.getListLoading();
                            if (listLoading != null) {
                                listLoading.observe(
                                        lifecycleOwner,
                                        new AnalyzeStorageSubList$observeList$1(
                                                analyzeStorageSubList2, 1));
                            }
                            if (analyzeStorageSubList2.type == 0
                                    && (absPageController = analyzeStorageSubList2.controller)
                                            != null
                                    && (listItemHandler = absPageController.mListItemHandler)
                                            != null
                                    && (mutableLiveData4 = listItemHandler.mGroupInfo) != null) {
                                mutableLiveData4.observe(
                                        lifecycleOwner,
                                        new AnalyzeStorageSubList$observeList$1(
                                                analyzeStorageSubList2, 2));
                            }
                            if (FeatureManager$UiFeature.isDefaultTheme(
                                    analyzeStorageSubList2.context)) {
                                return;
                            }
                            int color =
                                    analyzeStorageSubList2.context.getColor(
                                            R.color.as_theme_main_text_color);
                            int color2 =
                                    analyzeStorageSubList2.context.getColor(
                                            R.color.as_theme_sub_text_color);
                            TextView textView2 = analyzeStorageSubList2.mainText;
                            if (textView2 != null) {
                                textView2.setTextColor(color);
                            }
                            TextView textView3 = analyzeStorageSubList2.headerSize;
                            if (textView3 != null) {
                                textView3.setTextColor(color);
                            }
                            ImageView imageView = analyzeStorageSubList2.icon;
                            if (imageView != null) {
                                imageView.setColorFilter(color);
                            }
                            TextView textView4 = analyzeStorageSubList2.subText;
                            if (textView4 != null) {
                                textView4.setTextColor(color2);
                            }
                        }
                    });
            i++;
        }
        View requireViewById5 = requireViewById(R.id.contents_container);
        Intrinsics.checkNotNullExpressionValue(requireViewById5, "requireViewById(...)");
        UiUtils.setFlexibleSpacing(requireViewById5);
        getOnBackPressedDispatcher().addCallback(this, this.backPressCallBack);
        Trace.endSection();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onDestroy() {
        LayoutWidthManager layoutWidthManager = getLayoutWidthManager();
        layoutWidthManager.getClass();
        ((ArrayList) layoutWidthManager.listenerList).remove(this);
        LayoutWidthManager layoutWidthManager2 = getLayoutWidthManager();
        if (!((ArrayList) layoutWidthManager2.listenerList).isEmpty()) {
            ((ArrayList) layoutWidthManager2.listenerList).clear();
        }
        OverView overView = this.overView;
        if (overView != null) {
            overView.indicator.setAdapter((ListAdapter) null);
            ViewPager2 viewPager2 = overView.viewPager;
            viewPager2.setAdapter(null);
            ((ArrayList) viewPager2.mExternalPageChangeCallbacks.mCallbacks)
                    .remove(overView.pageChangeCallback);
            StorageOperationManager.StorageOperationManagerHolder.INSTANCE.mUpdateListenerSet
                    .remove(overView);
        }
        RecommendCardView recommendCardView = this.recommendCardView;
        if (recommendCardView != null) {
            Job job = recommendCardView.delayLoadingJob;
            if (job != null) {
                job.cancel(null);
            }
            recommendCardView.delayLoadingJob = null;
            recommendCardView.indicator.setAdapter((ListAdapter) null);
            ViewPager2 viewPager22 = recommendCardView.viewPager;
            viewPager22.setAdapter(null);
            ((ArrayList) viewPager22.mExternalPageChangeCallbacks.mCallbacks)
                    .remove(recommendCardView.pageChangeCallback);
            RecommendCardController recommendCardController = recommendCardView.controller;
            if (recommendCardController != null) {
                recommendCardController.chooseAccountListener = null;
            }
            AsRecommendCardInfoManager.Companion companion = AsRecommendCardInfoManager.Companion;
            Context baseContext = recommendCardView.activity.getBaseContext();
            Intrinsics.checkNotNullExpressionValue(baseContext, "getBaseContext(...)");
            companion.getInstance(baseContext);
            AsRecommendCardInfoManager.cloudList = null;
            AsRecommendCardInfoManager.appList = null;
        }
        this.overView = null;
        this.recommendCardView = null;
        SparseArray sparseArray = this.subListArray;
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            sparseArray.keyAt(i);
            AnalyzeStorageSubList analyzeStorageSubList =
                    (AnalyzeStorageSubList) sparseArray.valueAt(i);
            Job job2 = analyzeStorageSubList.delayJob;
            if (job2 != null) {
                job2.cancel(null);
            }
            analyzeStorageSubList.delayJob = null;
        }
        this.subListArray.clear();
        PreferenceUtils.resetPrevMpGenerationForAnalyzeStorage(this, UserInfoManager.getMyUserId());
        Context context = ActivityInfoStore.context;
        ((ArrayList) ActivityInfoStore.Companion.getInstance(this.instanceId).activityListenerList)
                .remove(this);
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        LoggingHelper.insertEventLogging(PageType.ANALYZE_STORAGE_HOME.getScreenIdForSA(), 8822);
        ComponentName callingActivity = getCallingActivity();
        String packageName = callingActivity != null ? callingActivity.getPackageName() : null;
        boolean booleanExtra = getIntent().getBooleanExtra("from_shortcut", false);
        String stringExtra = getIntent().getStringExtra("calling_package");
        Log.d(
                "MainActivity",
                "callingPackageName is " + packageName + " - isFromShortcut is " + booleanExtra);
        if (packageName != null && packageName.equals("com.samsung.android.lool") && booleanExtra) {
            startActivity("com.samsung.android.sm.ACTION_DASHBOARD", "com.samsung.android.lool");
        } else if (Intrinsics.areEqual(stringExtra, "com.sec.android.app.myfiles")
                && booleanExtra) {
            startActivity(
                    "samsung.myfiles.intent.action.LAUNCH_MY_FILES", "com.sec.android.app.myfiles");
            finish();
        } else {
            getOnBackPressedDispatcher().onBackPressed();
        }
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        WeakReference weakReference;
        super.onResume();
        SparseArray sparseArray = this.subListArray;
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            sparseArray.keyAt(i);
            ((AnalyzeStorageSubList) sparseArray.valueAt(i)).onResume();
        }
        HomeController controller = getController();
        FileListObserver fileListObserver = controller.listObserver;
        fileListObserver.mIsPaused = false;
        if (fileListObserver.mNeedRefresh
                && (weakReference = fileListObserver.mControllerWeakReference) != null
                && weakReference.get() != null) {
            IContentObserver iContentObserver = fileListObserver.mContentObserver;
            if (iContentObserver != null) {
                iContentObserver.onContentChanged();
            } else {
                ((PageControllerInterface) fileListObserver.mControllerWeakReference.get())
                        .refresh(true);
            }
        }
        Iterator it = controller.subFileListController.entrySet().iterator();
        while (it.hasNext()) {
            SubFileListController subFileListController =
                    (SubFileListController) ((Map.Entry) it.next()).getValue();
            if (!subFileListController.mNeedUpdate) {
                subFileListController.refresh(false);
            }
            subFileListController.mNeedUpdate = true;
        }
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public final void onSaveInstanceState(Bundle outState) {
        Intrinsics.checkNotNullParameter(outState, "outState");
        super.onSaveInstanceState(outState);
        Clipboard clipboard = Clipboard.ClipboardHolder.instance;
        AsRecommendCardInfoManager.Companion companion = AsRecommendCardInfoManager.Companion;
        Context baseContext = getBaseContext();
        Intrinsics.checkNotNullExpressionValue(baseContext, "getBaseContext(...)");
        companion.getInstance(baseContext);
        clipboard.recommendAppList.put(this.instanceId, AsRecommendCardInfoManager.appList);
        clipboard.recommendCloudList.put(this.instanceId, AsRecommendCardInfoManager.cloudList);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onStop() {
        super.onStop();
        HomeController controller = getController();
        FileListObserver fileListObserver = controller.listObserver;
        fileListObserver.mIsPaused = true;
        fileListObserver.mNeedRefresh = false;
        if (controller.overViewController == null) {
            Intrinsics.throwUninitializedPropertyAccessException("overViewController");
            throw null;
        }
        AppDataManager.applicationInfo.clear();
        AppDataManager.appsDataMap.clear();
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.manager.LayoutWidthManager.OnWidthChangedListener
    public final void onWidthChanged() {
        int i = getLayoutWidthManager().contentWidthDp >= 589 ? 1 : 0;
        if (this.layoutState != i) {
            this.layoutState = i;
            OverView overView = this.overView;
            if (overView != null) {
                overView.layoutState = i;
                ViewPagerAdapter viewPagerAdapter = overView.adapter;
                if (viewPagerAdapter != null) {
                    viewPagerAdapter.setLayoutState(i);
                    viewPagerAdapter.updateLayout(
                            overView.getIndicatorAdapter().getCurrentSupportedType());
                }
            }
            RecommendCardView recommendCardView = this.recommendCardView;
            if (recommendCardView != null) {
                int i2 = this.layoutState;
                recommendCardView.layoutState = i2;
                RecommendCardViewPagerAdapter recommendCardViewPagerAdapter =
                        recommendCardView.adapter;
                if (recommendCardViewPagerAdapter != null) {
                    recommendCardViewPagerAdapter.isLandScape = i2 == 1;
                }
                if (recommendCardViewPagerAdapter != null) {
                    recommendCardViewPagerAdapter.updateLayout(
                            recommendCardView
                                    .getRecommendCardIndicatorAdapter()
                                    .getCurrentSupportedType());
                }
                recommendCardView.updateHeightCard(
                        recommendCardView.getRecommendCardIndicatorAdapter().currentIndex);
            }
        }
    }

    public final void startActivity(String str, String str2) {
        Intent intent = new Intent(str);
        intent.setFlags(268468224);
        intent.setPackage(str2);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.w("MainActivity", "startActivity() - Fail to start activity " + e);
        }
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.ActivityInfo$ActivityInfoListener
    public final Activity getActivity() {
        return this;
    }
}
