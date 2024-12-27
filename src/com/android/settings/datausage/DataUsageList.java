package com.android.settings.datausage;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.NetworkTemplate;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.preference.Preference;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.datausage.lib.BillingCycleRepository;
import com.android.settings.datausage.lib.BillingCycleRepository$isModifiableFlow$$inlined$map$1;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.LoadingViewController;
import com.android.settingslib.spa.framework.util.FlowsKt;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.connection.ConnectionsUtils;
import com.samsung.android.settings.datausage.DataUsageFeatureProviderImpl;
import com.samsung.android.settings.datausage.MTRPreference;
import com.samsung.android.settings.widget.SecCustomDividerItemDecorator;
import com.sec.ims.settings.ImsProfile;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Optional;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0006\b\u0017\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003R.\u0010\u0006\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00048\u0006@BX\u0087\u000e¢\u0006\u0012\n\u0004\b\u0006\u0010\u0007\u0012\u0004\b\n\u0010\u0003\u001a\u0004\b\b\u0010\tR*\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u000b8\u0006@BX\u0087\u000e¢\u0006\u0012\n\u0004\b\f\u0010\r\u0012\u0004\b\u0010\u0010\u0003\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0011"}, d2 = {"Lcom/android/settings/datausage/DataUsageList;", "Lcom/android/settings/dashboard/DashboardFragment;", "<init>", "()V", "Landroid/net/NetworkTemplate;", "<set-?>", "template", "Landroid/net/NetworkTemplate;", "getTemplate", "()Landroid/net/NetworkTemplate;", "getTemplate$annotations", ApnSettings.MVNO_NONE, "subId", ImsProfile.TIMER_NAME_I, "getSubId", "()I", "getSubId$annotations", "applications__sources__apps__SecSettings__android_common__SecSettings-core"}, k = 1, mv = {1, 9, 0})
/* loaded from: classes2.dex */
public class DataUsageList extends DashboardFragment {
    public static boolean mBound;
    public final String INTENT_ACTION_SIMHOTSWAP;
    public BillingCycleRepository billingCycleRepository;
    public ChartDataUsagePreferenceController chartDataUsagePreferenceController;
    public DataUsageListAppsController dataUsageListAppsController;
    public DataUsageListHeaderController dataUsageListHeaderController;
    public LoadingViewController loadingViewController;
    public final DataUsageList$mAlertTotalSentListener$1 mAlertTotalReceivedListener;
    public final DataUsageList$mAlertTotalSentListener$1 mAlertTotalSentListener;
    public DataUsageFeatureProviderImpl mDataUsageFeatureProvider;
    public Handler mHandler;
    public boolean mIsRoaming;
    public MTRPreference mLifetime;
    public final DataUsageList$mReceiver$1 mReceiver;
    public final DataUsageList$mSecPhoneServiceConnectionTMB$1 mSecPhoneServiceConnectionTMB;
    public Messenger mServiceMessenger;
    public MTRPreference mTotalReceive;
    public MTRPreference mTotalSent;
    public final Messenger mlifetimeMessenger;
    public final DataUsageList$rilHandler$1 rilHandler;
    public int subId = -1;
    public NetworkTemplate template;
    public Preference usageAmount;
    public final ViewModelLazy viewModel$delegate;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.datausage.DataUsageList$special$$inlined$viewModels$default$1] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.settings.datausage.DataUsageList$mReceiver$1] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.settings.datausage.DataUsageList$mSecPhoneServiceConnectionTMB$1] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.settings.datausage.DataUsageList$mAlertTotalSentListener$1] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.settings.datausage.DataUsageList$mAlertTotalSentListener$1] */
    public DataUsageList() {
        final ?? r0 = new Function0() { // from class: com.android.settings.datausage.DataUsageList$special$$inlined$viewModels$default$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return Fragment.this;
            }
        };
        final Lazy lazy = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, new Function0() { // from class: com.android.settings.datausage.DataUsageList$special$$inlined$viewModels$default$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return (ViewModelStoreOwner) r0.mo1068invoke();
            }
        });
        this.viewModel$delegate = new ViewModelLazy(Reflection.factory.getOrCreateKotlinClass(DataUsageListViewModel.class), new Function0() { // from class: com.android.settings.datausage.DataUsageList$special$$inlined$viewModels$default$3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return ((ViewModelStoreOwner) Lazy.this.getValue()).getViewModelStore();
            }
        }, new Function0() { // from class: com.android.settings.datausage.DataUsageList$special$$inlined$viewModels$default$5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory;
                ViewModelStoreOwner viewModelStoreOwner = (ViewModelStoreOwner) lazy.getValue();
                HasDefaultViewModelProviderFactory hasDefaultViewModelProviderFactory = viewModelStoreOwner instanceof HasDefaultViewModelProviderFactory ? (HasDefaultViewModelProviderFactory) viewModelStoreOwner : null;
                return (hasDefaultViewModelProviderFactory == null || (defaultViewModelProviderFactory = hasDefaultViewModelProviderFactory.getDefaultViewModelProviderFactory()) == null) ? Fragment.this.getDefaultViewModelProviderFactory() : defaultViewModelProviderFactory;
            }
        }, new Function0() { // from class: com.android.settings.datausage.DataUsageList$special$$inlined$viewModels$default$4
            final /* synthetic */ Function0 $extrasProducer = null;

            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                CreationExtras creationExtras;
                Function0 function0 = this.$extrasProducer;
                if (function0 != null && (creationExtras = (CreationExtras) function0.mo1068invoke()) != null) {
                    return creationExtras;
                }
                ViewModelStoreOwner viewModelStoreOwner = (ViewModelStoreOwner) Lazy.this.getValue();
                HasDefaultViewModelProviderFactory hasDefaultViewModelProviderFactory = viewModelStoreOwner instanceof HasDefaultViewModelProviderFactory ? (HasDefaultViewModelProviderFactory) viewModelStoreOwner : null;
                return hasDefaultViewModelProviderFactory != null ? hasDefaultViewModelProviderFactory.getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
            }
        });
        this.INTENT_ACTION_SIMHOTSWAP = "com.samsung.intent.action.SIMHOTSWAP";
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.settings.datausage.DataUsageList$mReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(intent, "intent");
                if (Intrinsics.areEqual(DataUsageList.this.INTENT_ACTION_SIMHOTSWAP, intent.getAction())) {
                    Log.d("DataUsageList", "FINISH : HOTSWAP");
                    DataUsageList.this.finish();
                }
            }
        };
        this.mSecPhoneServiceConnectionTMB = new ServiceConnection() { // from class: com.android.settings.datausage.DataUsageList$mSecPhoneServiceConnectionTMB$1
            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName className, IBinder service) {
                Intrinsics.checkNotNullParameter(className, "className");
                Intrinsics.checkNotNullParameter(service, "service");
                Log.d("DataUsageList", "onServiceConnected()");
                DataUsageList.this.mServiceMessenger = new Messenger(service);
                DataUsageList dataUsageList = DataUsageList.this;
                dataUsageList.getClass();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                try {
                    dataOutputStream.writeByte(81);
                    dataOutputStream.writeByte(11);
                    dataOutputStream.writeByte(0);
                    dataOutputStream.writeByte(4);
                    dataOutputStream.close();
                    byteArrayOutputStream.close();
                    Log.i("DataUsageList", " getOemData with 11");
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
                    Handler handler = dataUsageList.mHandler;
                    Intrinsics.checkNotNull(handler);
                    Message obtainMessage = handler.obtainMessage(11);
                    Intrinsics.checkNotNullExpressionValue(obtainMessage, "obtainMessage(...)");
                    dataUsageList.invokeOemRilRequestRaw(byteArray, obtainMessage);
                } catch (IOException unused) {
                    Log.i("DataUsageList", "getOemData(int, int).. exception occured during operation11");
                }
                DataUsageList.mBound = true;
            }

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName className) {
                Intrinsics.checkNotNullParameter(className, "className");
                Log.d("DataUsageList", "onServiceDisconnected()");
                DataUsageList.this.mServiceMessenger = null;
                DataUsageList.mBound = false;
            }
        };
        this.mlifetimeMessenger = new Messenger(new Handler() { // from class: com.android.settings.datausage.DataUsageList$rilHandler$1
            @Override // android.os.Handler
            public final void handleMessage(Message msg) {
                Intrinsics.checkNotNullParameter(msg, "msg");
                Log.i("DataUsageList", "handleMessage");
                int i = msg.what;
                if (i == 11) {
                    Log.i("DataUsageList", "msg.what : " + i);
                    if (msg.getData().getInt("error") == 0) {
                        Log.i("DataUsageList", "error=0");
                    } else {
                        Log.i("DataUsageList", "error response");
                    }
                    try {
                        byte[] byteArray = msg.getData().getByteArray("response");
                        if (byteArray != null && byteArray.length != 0) {
                            Log.i("DataUsageList", "OEM_HIDDEN_GET_LIFEBYTE :" + byteArray.length);
                            DataUsageList.access$setSummaryfortimedata(DataUsageList.this, byteArray);
                            return;
                        }
                        Log.i("DataUsageList", "response is null");
                    } catch (Exception e) {
                        Log.i("DataUsageList", "NULL VALUE :" + e);
                    }
                }
            }
        });
        final int i = 1;
        this.mAlertTotalReceivedListener = new View.OnClickListener(this) { // from class: com.android.settings.datausage.DataUsageList$mAlertTotalSentListener$1
            public final /* synthetic */ DataUsageList this$0;

            {
                this.this$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i) {
                    case 0:
                        AlertDialog.Builder builder = new AlertDialog.Builder(this.this$0.getActivity());
                        builder.setTitle(R.string.total_sent_data);
                        builder.setMessage(R.string.reset_received_data_sent_body);
                        final DataUsageList dataUsageList = this.this$0;
                        final int i2 = 0;
                        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.android.settings.datausage.DataUsageList$mAlertTotalSentListener$1.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i3) {
                                switch (i2) {
                                    case 0:
                                        DataUsageList.access$resetTimeData(dataUsageList, 1);
                                        break;
                                    default:
                                        DataUsageList.access$resetTimeData(dataUsageList, 0);
                                        break;
                                }
                            }
                        });
                        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
                        builder.show();
                        break;
                    default:
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(this.this$0.getActivity());
                        builder2.setTitle(R.string.reset_received_data);
                        builder2.setMessage(R.string.reset_received_data_received_body);
                        final DataUsageList dataUsageList2 = this.this$0;
                        final int i3 = 1;
                        builder2.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.android.settings.datausage.DataUsageList$mAlertTotalSentListener$1.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i32) {
                                switch (i3) {
                                    case 0:
                                        DataUsageList.access$resetTimeData(dataUsageList2, 1);
                                        break;
                                    default:
                                        DataUsageList.access$resetTimeData(dataUsageList2, 0);
                                        break;
                                }
                            }
                        });
                        builder2.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
                        builder2.show();
                        break;
                }
            }
        };
        final int i2 = 0;
        this.mAlertTotalSentListener = new View.OnClickListener(this) { // from class: com.android.settings.datausage.DataUsageList$mAlertTotalSentListener$1
            public final /* synthetic */ DataUsageList this$0;

            {
                this.this$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i2) {
                    case 0:
                        AlertDialog.Builder builder = new AlertDialog.Builder(this.this$0.getActivity());
                        builder.setTitle(R.string.total_sent_data);
                        builder.setMessage(R.string.reset_received_data_sent_body);
                        final DataUsageList dataUsageList = this.this$0;
                        final int i22 = 0;
                        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.android.settings.datausage.DataUsageList$mAlertTotalSentListener$1.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i32) {
                                switch (i22) {
                                    case 0:
                                        DataUsageList.access$resetTimeData(dataUsageList, 1);
                                        break;
                                    default:
                                        DataUsageList.access$resetTimeData(dataUsageList, 0);
                                        break;
                                }
                            }
                        });
                        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
                        builder.show();
                        break;
                    default:
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(this.this$0.getActivity());
                        builder2.setTitle(R.string.reset_received_data);
                        builder2.setMessage(R.string.reset_received_data_received_body);
                        final DataUsageList dataUsageList2 = this.this$0;
                        final int i3 = 1;
                        builder2.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.android.settings.datausage.DataUsageList$mAlertTotalSentListener$1.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i32) {
                                switch (i3) {
                                    case 0:
                                        DataUsageList.access$resetTimeData(dataUsageList2, 1);
                                        break;
                                    default:
                                        DataUsageList.access$resetTimeData(dataUsageList2, 0);
                                        break;
                                }
                            }
                        });
                        builder2.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
                        builder2.show();
                        break;
                }
            }
        };
    }

    public static final void access$resetTimeData(DataUsageList dataUsageList, int i) {
        dataUsageList.getClass();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeByte(81);
            dataOutputStream.writeByte(11);
            dataOutputStream.writeByte(0);
            dataOutputStream.writeByte(5);
            if (i == 0) {
                dataOutputStream.writeByte(0);
            } else {
                dataOutputStream.writeByte(1);
            }
            dataOutputStream.close();
            byteArrayOutputStream.close();
            Log.i("DataUsageList", " resetTimeData with " + i);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
            Handler handler = dataUsageList.mHandler;
            Intrinsics.checkNotNull(handler);
            Message obtainMessage = handler.obtainMessage(11);
            Intrinsics.checkNotNullExpressionValue(obtainMessage, "obtainMessage(...)");
            dataUsageList.invokeOemRilRequestRaw(byteArray, obtainMessage);
        } catch (IOException unused) {
        }
    }

    public static final void access$setSummaryfortimedata(DataUsageList dataUsageList, byte[] bArr) {
        MTRPreference mTRPreference;
        dataUsageList.getClass();
        long byteToLong = byteToLong(bArr, 0, 4);
        long byteToLong2 = byteToLong(bArr, 4, 8);
        long byteToLong3 = byteToLong(bArr, 8, 16);
        long byteToLong4 = byteToLong(bArr, 16, 24);
        StringBuilder m = SnapshotStateObserver$$ExternalSyntheticOutline0.m(byteToLong, "setSummaryfortimedata :", ";");
        m.append(byteToLong2);
        m.append(";");
        m.append(byteToLong3);
        m.append(";");
        m.append(byteToLong4);
        Log.i("DataUsageList", m.toString());
        MTRPreference mTRPreference2 = dataUsageList.mTotalReceive;
        if (mTRPreference2 != null) {
            DataUsageFeatureProviderImpl dataUsageFeatureProviderImpl = dataUsageList.mDataUsageFeatureProvider;
            mTRPreference2.setSummary(dataUsageFeatureProviderImpl != null ? dataUsageFeatureProviderImpl.formatFileSize(dataUsageList.getActivity(), byteToLong) : null);
        }
        MTRPreference mTRPreference3 = dataUsageList.mTotalSent;
        if (mTRPreference3 != null) {
            DataUsageFeatureProviderImpl dataUsageFeatureProviderImpl2 = dataUsageList.mDataUsageFeatureProvider;
            mTRPreference3.setSummary(dataUsageFeatureProviderImpl2 != null ? dataUsageFeatureProviderImpl2.formatFileSize(dataUsageList.getActivity(), byteToLong2) : null);
        }
        if (byteToLong3 <= 0 || byteToLong4 <= 0 || (mTRPreference = dataUsageList.mLifetime) == null) {
            return;
        }
        DataUsageFeatureProviderImpl dataUsageFeatureProviderImpl3 = dataUsageList.mDataUsageFeatureProvider;
        mTRPreference.setSummary(dataUsageFeatureProviderImpl3 != null ? dataUsageFeatureProviderImpl3.formatFileSize(dataUsageList.getActivity(), byteToLong3 + byteToLong4) : null);
    }

    public static long byteToLong(byte[] bArr, int i, int i2) {
        long j = 0;
        if (bArr.length < i2) {
            return 0L;
        }
        int i3 = i2 - 1;
        if (i <= i3) {
            while (true) {
                j = (j << 8) + (bArr[i3] & 255);
                if (i3 == i) {
                    break;
                }
                i3--;
            }
        }
        return j;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "DataUsageList";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        processArgument();
        NetworkTemplate networkTemplate = this.template;
        if (networkTemplate == null) {
            return FileType.HCDT;
        }
        if (networkTemplate != null && networkTemplate.getMatchRule() == 4) {
            return FileType.TSV;
        }
        NetworkTemplate networkTemplate2 = this.template;
        if (networkTemplate2 != null && networkTemplate2.getMatchRule() == 1 && this.mIsRoaming) {
            return 7136;
        }
        return FileType.HCDT;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_data_usage_list;
    }

    public final void invokeOemRilRequestRaw(byte[] bArr, Message message) {
        Bundle data = message.getData();
        data.putByteArray("request", bArr);
        message.setData(data);
        message.replyTo = this.mlifetimeMessenger;
        try {
            Messenger messenger = this.mServiceMessenger;
            if (messenger != null) {
                messenger.send(message);
            } else {
                Log.d("DataUsageList", "mServiceMessenger is null. Do nothing.");
            }
        } catch (RemoteException unused) {
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        boolean z;
        super.onCreate(bundle);
        Context requireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(requireContext, "requireContext(...)");
        BillingCycleRepository billingCycleRepository = new BillingCycleRepository(requireContext);
        this.billingCycleRepository = billingCycleRepository;
        try {
            z = billingCycleRepository.networkService.isBandwidthControlEnabled();
        } catch (Exception e) {
            Log.w("BillingCycleRepository", "problem talking with INetworkManagementService: ", e);
            z = false;
        }
        if (!z) {
            Log.w("DataUsageList", "No bandwidth control; leaving");
            finish();
            return;
        }
        if (SemPersonaManager.isSecureFolderId(UserHandle.myUserId())) {
            removePreference("chart_data");
        }
        this.usageAmount = findPreference("usage_amount");
        processArgument();
        NetworkTemplate networkTemplate = this.template;
        if (networkTemplate == null) {
            Log.e("DataUsageList", "No template; leaving");
            finish();
            return;
        }
        Log.i("DataUsageList", "Template : " + networkTemplate.getMatchRule());
        if (!ConnectionsUtils.isMetroPCS() || networkTemplate.getMatchRule() == 4 || SemPersonaManager.isKnoxId(UserHandle.myUserId())) {
            removePreference("total_received_data");
            removePreference("total_sent_data");
            removePreference("lifetime_data");
        } else {
            this.mHandler = new Handler();
            Log.d("DataUsageList", "connect To Secphone service");
            Intent intent = new Intent();
            intent.setClassName("com.sec.phone", "com.sec.phone.SecPhoneService");
            requireActivity().bindService(intent, this.mSecPhoneServiceConnectionTMB, 1);
            MTRPreference mTRPreference = (MTRPreference) findPreference("total_received_data");
            this.mTotalReceive = mTRPreference;
            Intrinsics.checkNotNull(mTRPreference);
            mTRPreference.mListener = this.mAlertTotalReceivedListener;
            MTRPreference mTRPreference2 = (MTRPreference) findPreference("total_sent_data");
            this.mTotalSent = mTRPreference2;
            Intrinsics.checkNotNull(mTRPreference2);
            mTRPreference2.mListener = this.mAlertTotalSentListener;
            this.mLifetime = (MTRPreference) findPreference("lifetime_data");
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mDataUsageFeatureProvider = featureFactoryImpl.getDataUsageFeatureProvider();
        DataUsageListAppsController dataUsageListAppsController = (DataUsageListAppsController) use(DataUsageListAppsController.class);
        dataUsageListAppsController.init(networkTemplate);
        this.dataUsageListAppsController = dataUsageListAppsController;
        ChartDataUsagePreferenceController chartDataUsagePreferenceController = (ChartDataUsagePreferenceController) use(ChartDataUsagePreferenceController.class);
        chartDataUsagePreferenceController.init(networkTemplate);
        this.chartDataUsagePreferenceController = chartDataUsagePreferenceController;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        if (!mBound || this.mSecPhoneServiceConnectionTMB == null) {
            return;
        }
        requireActivity().unbindService(this.mSecPhoneServiceConnectionTMB);
        mBound = false;
        Log.d("DataUsageList", "onDestroy: unbindService");
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        NetworkTemplate networkTemplate = this.template;
        if (networkTemplate == null || networkTemplate.getMatchRule() != 1) {
            return;
        }
        requireContext().unregisterReceiver(this.mReceiver);
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        NetworkTemplate networkTemplate = this.template;
        if (networkTemplate != null && networkTemplate.getMatchRule() == 1) {
            requireContext().registerReceiver(this.mReceiver, new IntentFilter(this.INTENT_ACTION_SIMHOTSWAP), 4);
        }
        LoadingViewController loadingViewController = this.loadingViewController;
        if (loadingViewController != null) {
            loadingViewController.mFgHandler.postDelayed(loadingViewController.mShowLoadingContainerRunnable, 100L);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View v, Bundle bundle) {
        Intrinsics.checkNotNullParameter(v, "v");
        super.onViewCreated(v, bundle);
        BillingCycleRepository billingCycleRepository = this.billingCycleRepository;
        if (billingCycleRepository == null) {
            Intrinsics.throwUninitializedPropertyAccessException("billingCycleRepository");
            throw null;
        }
        Flow flowOn = FlowKt.flowOn(FlowKt.buffer$default(new BillingCycleRepository$isModifiableFlow$$inlined$map$1(billingCycleRepository.telephonyRepository.isDataEnabledFlow(this.subId), billingCycleRepository), -1), Dispatchers.Default);
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        Intrinsics.checkNotNullExpressionValue(viewLifecycleOwner, "getViewLifecycleOwner(...)");
        FlowsKt.collectLatestWithLifecycle(flowOn, viewLifecycleOwner, Lifecycle.State.STARTED, new DataUsageList$onViewCreated$1(2, this, DataUsageList.class, "updatePolicy", "updatePolicy(Z)V", 4));
        NetworkTemplate networkTemplate = this.template;
        if (networkTemplate == null) {
            return;
        }
        View pinnedHeaderView = setPinnedHeaderView(R.layout.sec_data_usage_list_spinner);
        ViewGroup viewGroup = (ViewGroup) v.findViewById(R.id.pinned_header);
        Context context = getContext();
        if (context != null && viewGroup != null) {
            viewGroup.setBackgroundColor(context.getColor(R.color.sec_round_and_bgcolor));
        }
        int listHorizontalPadding = Utils.getListHorizontalPadding(getContext());
        if (viewGroup != null) {
            viewGroup.setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
        }
        pinnedHeaderView.semSetRoundedCorners(15);
        pinnedHeaderView.semSetRoundedCornerColor(15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        ((DataUsageListViewModel) this.viewModel$delegate.getValue()).templateFlow.updateState(null, networkTemplate);
        int metricsCategory = getMetricsCategory();
        LifecycleOwner viewLifecycleOwner2 = getViewLifecycleOwner();
        Intrinsics.checkNotNullExpressionValue(viewLifecycleOwner2, "getViewLifecycleOwner(...)");
        int i = this.subId;
        DataUsageListViewModel dataUsageListViewModel = (DataUsageListViewModel) this.viewModel$delegate.getValue();
        this.dataUsageListHeaderController = new DataUsageListHeaderController(pinnedHeaderView, networkTemplate, metricsCategory, viewLifecycleOwner2, i, dataUsageListViewModel.cyclesFlow, new DataUsageList$onViewCreated$3(1, this, DataUsageList.class, "updateSelectedCycle", "updateSelectedCycle(Lcom/android/settings/datausage/lib/NetworkUsageData;)V", 0));
        DataUsageListViewModel dataUsageListViewModel2 = (DataUsageListViewModel) this.viewModel$delegate.getValue();
        LifecycleOwner viewLifecycleOwner3 = getViewLifecycleOwner();
        Intrinsics.checkNotNullExpressionValue(viewLifecycleOwner3, "getViewLifecycleOwner(...)");
        FlowsKt.collectLatestWithLifecycle(dataUsageListViewModel2.cyclesFlow, viewLifecycleOwner3, Lifecycle.State.STARTED, new DataUsageList$onViewCreated$4(this, null));
        DataUsageListViewModel dataUsageListViewModel3 = (DataUsageListViewModel) this.viewModel$delegate.getValue();
        LifecycleOwner viewLifecycleOwner4 = getViewLifecycleOwner();
        Intrinsics.checkNotNullExpressionValue(viewLifecycleOwner4, "getViewLifecycleOwner(...)");
        FlowsKt.collectLatestWithLifecycle(dataUsageListViewModel3.chartDataFlow, viewLifecycleOwner4, Lifecycle.State.STARTED, new DataUsageList$onViewCreated$5(this, null));
        View findViewById = requireView().findViewById(R.id.loading_container);
        Intrinsics.checkNotNull(findViewById);
        findViewById.semSetRoundedCorners(15);
        findViewById.semSetRoundedCornerColor(15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.loadingViewController = new LoadingViewController(findViewById, getListView(), null);
        if (getListView() != null) {
            getListView().seslSetGoToTopEnabled(true);
        }
        setDivider(null);
        getListView().addItemDecoration(new SecCustomDividerItemDecorator(getResources().getDrawable(R.drawable.sec_top_level_list_divider), getContext(), (int) (getResources().getDimension(R.dimen.sec_app_list_item_icon_min_width) + getResources().getDimension(R.dimen.sec_widget_list_item_padding_start)), R.id.icon_frame, android.R.id.icon));
    }

    public final void processArgument() {
        if (this.template != null) {
            return;
        }
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.subId = arguments.getInt("sub_id", -1);
            this.template = (NetworkTemplate) arguments.getParcelable("network_template", NetworkTemplate.class);
            this.mIsRoaming = arguments.getBoolean("is_roaming");
        }
        if (this.template == null && this.subId == -1) {
            this.subId = getIntent().getIntExtra("android.provider.extra.SUB_ID", -1);
            NetworkTemplate networkTemplate = (NetworkTemplate) getIntent().getParcelableExtra("network_template", NetworkTemplate.class);
            if (networkTemplate == null) {
                Optional mobileNetworkTemplateFromSubId = DataUsageUtils.getMobileNetworkTemplateFromSubId(getContext(), getIntent());
                Intrinsics.checkNotNullExpressionValue(mobileNetworkTemplateFromSubId, "getMobileNetworkTemplateFromSubId(...)");
                networkTemplate = (NetworkTemplate) mobileNetworkTemplateFromSubId.orElse(null);
            }
            this.template = networkTemplate;
            this.mIsRoaming = getIntent().getBooleanExtra("is_roaming", false);
        }
        DataUsageListHeaderController dataUsageListHeaderController = this.dataUsageListHeaderController;
        if (dataUsageListHeaderController != null) {
            dataUsageListHeaderController.isRoaming = this.mIsRoaming;
        }
    }

    public static /* synthetic */ void getSubId$annotations() {
    }

    public static /* synthetic */ void getTemplate$annotations() {
    }
}
