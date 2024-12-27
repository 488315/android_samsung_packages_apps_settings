package com.android.settings.security;

import android.app.Activity;
import android.app.admin.DevicePolicyEventLogger;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserManager;
import android.security.AppUriAuthenticationPolicy;
import android.security.KeyChain;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RequestManageCredentials extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AppUriAuthenticationPolicy mAuthenticationPolicy;
    public LinearLayout mButtonPanel;
    public String mCredentialManagerPackage;
    public ExtendedFloatingActionButton mExtendedFab;
    public KeyChain.KeyChainConnection mKeyChainConnection;
    public HandlerThread mKeyChainTread;
    public LinearLayoutManager mLayoutManager;
    public RecyclerView mRecyclerView;
    public boolean mDisplayingButtonPanel = false;
    public boolean mIsLandscapeMode = false;

    public KeyChain.KeyChainConnection getKeyChainConnection(
            Context context, HandlerThread handlerThread) {
        try {
            return KeyChain.bindAsUser(
                    context, new Handler(handlerThread.getLooper()), Process.myUserHandle());
        } catch (InterruptedException e) {
            throw new RuntimeException("Faile to bind to KeyChain", e);
        }
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!"android.security.MANAGE_CREDENTIALS".equals(getIntent().getAction())) {
            Log.e(
                    "ManageCredentials",
                    "Unable to start activity because intent action is not"
                        + " android.security.MANAGE_CREDENTIALS");
            DevicePolicyEventLogger.createEvent(182).write();
            setResult(0);
            finish();
            return;
        }
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) getSystemService(DevicePolicyManager.class);
        if (devicePolicyManager.getDeviceOwnerUser() == null
                && devicePolicyManager.getProfileOwner() == null) {
            Iterator it =
                    ((UserManager) getSystemService(UserManager.class))
                            .getProfiles(getUserId())
                            .iterator();
            while (it.hasNext()) {
                if (((UserInfo) it.next()).isManagedProfile()) {}
            }
            String launchedFromPackage = getLaunchedFromPackage();
            this.mCredentialManagerPackage = launchedFromPackage;
            if (TextUtils.isEmpty(launchedFromPackage)) {
                Log.e("ManageCredentials", "Unknown credential manager app");
                DevicePolicyEventLogger.createEvent(182).write();
                setResult(0);
                finish();
                return;
            }
            DevicePolicyEventLogger.createEvent(178)
                    .setStrings(new String[] {this.mCredentialManagerPackage})
                    .write();
            setContentView(R.layout.request_manage_credentials);
            getWindow().addSystemFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
            this.mIsLandscapeMode = getResources().getConfiguration().orientation == 2;
            HandlerThread handlerThread = new HandlerThread("KeyChainConnection");
            this.mKeyChainTread = handlerThread;
            handlerThread.start();
            this.mKeyChainConnection = getKeyChainConnection(this, this.mKeyChainTread);
            AppUriAuthenticationPolicy appUriAuthenticationPolicy =
                    (AppUriAuthenticationPolicy)
                            getIntent()
                                    .getParcelableExtra(
                                            "android.security.extra.AUTHENTICATION_POLICY");
            if (appUriAuthenticationPolicy != null
                    && !appUriAuthenticationPolicy.getAppAndUriMappings().isEmpty()) {
                try {
                    Iterator it2 = appUriAuthenticationPolicy.getAliases().iterator();
                    while (it2.hasNext()) {
                        if (this.mKeyChainConnection
                                        .getService()
                                        .requestPrivateKey((String) it2.next())
                                != null) {}
                    }
                    this.mAuthenticationPolicy = appUriAuthenticationPolicy;
                    DevicePolicyEventLogger createEvent = DevicePolicyEventLogger.createEvent(179);
                    String valueOf =
                            String.valueOf(
                                    this.mAuthenticationPolicy.getAppAndUriMappings().size());
                    Iterator<Map.Entry<String, Map<Uri, String>>> it3 =
                            this.mAuthenticationPolicy.getAppAndUriMappings().entrySet().iterator();
                    int i = 0;
                    while (it3.hasNext()) {
                        i += it3.next().getValue().size();
                    }
                    createEvent.setStrings(new String[] {valueOf, String.valueOf(i)}).write();
                    if (this.mIsLandscapeMode) {
                        ImageView imageView =
                                (ImageView) findViewById(R.id.credential_management_app_icon);
                        TextView textView =
                                (TextView) findViewById(R.id.credential_management_app_title);
                        try {
                            ApplicationInfo applicationInfo =
                                    getPackageManager()
                                            .getApplicationInfo(this.mCredentialManagerPackage, 0);
                            imageView.setImageDrawable(
                                    getPackageManager().getApplicationIcon(applicationInfo));
                            textView.setText(
                                    TextUtils.expandTemplate(
                                            getText(R.string.request_manage_credentials_title),
                                            applicationInfo.loadLabel(getPackageManager())));
                        } catch (PackageManager.NameNotFoundException unused) {
                            imageView.setImageDrawable(null);
                            textView.setText(
                                    TextUtils.expandTemplate(
                                            getText(R.string.request_manage_credentials_title),
                                            this.mCredentialManagerPackage));
                        }
                    }
                    this.mLayoutManager = new LinearLayoutManager(1);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.apps_list);
                    this.mRecyclerView = recyclerView;
                    recyclerView.setLayoutManager(this.mLayoutManager);
                    this.mRecyclerView.setAdapter(
                            new CredentialManagementAppAdapter(
                                    this,
                                    this.mCredentialManagerPackage,
                                    this.mAuthenticationPolicy.getAppAndUriMappings(),
                                    !this.mIsLandscapeMode,
                                    false));
                    this.mButtonPanel = (LinearLayout) findViewById(R.id.button_panel);
                    Button button = (Button) findViewById(R.id.dont_allow_button);
                    button.setFilterTouchesWhenObscured(true);
                    Button button2 = (Button) findViewById(R.id.allow_button);
                    button2.setFilterTouchesWhenObscured(true);
                    final int i2 = 1;
                    button.setOnClickListener(
                            new View.OnClickListener(
                                    this) { // from class:
                                            // com.android.settings.security.RequestManageCredentials$$ExternalSyntheticLambda0
                                public final /* synthetic */ RequestManageCredentials f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    int i3 = i2;
                                    RequestManageCredentials requestManageCredentials = this.f$0;
                                    switch (i3) {
                                        case 0:
                                            requestManageCredentials.mRecyclerView.scrollToPosition(
                                                    requestManageCredentials.mIsLandscapeMode
                                                            ? requestManageCredentials
                                                                            .mAuthenticationPolicy
                                                                            .getAppAndUriMappings()
                                                                            .size()
                                                                    - 1
                                                            : requestManageCredentials
                                                                    .mAuthenticationPolicy
                                                                    .getAppAndUriMappings()
                                                                    .size());
                                            requestManageCredentials.mExtendedFab.performMotion(1);
                                            requestManageCredentials.showButtonPanel();
                                            break;
                                        case 1:
                                            int i4 = RequestManageCredentials.$r8$clinit;
                                            requestManageCredentials.getClass();
                                            DevicePolicyEventLogger.createEvent(181).write();
                                            requestManageCredentials.setResult(0);
                                            requestManageCredentials.finish();
                                            break;
                                        default:
                                            int i5 = RequestManageCredentials.$r8$clinit;
                                            requestManageCredentials.getClass();
                                            try {
                                                requestManageCredentials
                                                        .mKeyChainConnection
                                                        .getService()
                                                        .setCredentialManagementApp(
                                                                requestManageCredentials
                                                                        .mCredentialManagerPackage,
                                                                requestManageCredentials
                                                                        .mAuthenticationPolicy);
                                                DevicePolicyEventLogger.createEvent(180).write();
                                                requestManageCredentials.setResult(-1);
                                            } catch (RemoteException e) {
                                                Log.e(
                                                        "ManageCredentials",
                                                        "Unable to set credential manager app",
                                                        e);
                                                DevicePolicyEventLogger.createEvent(182).write();
                                            }
                                            requestManageCredentials.finish();
                                            break;
                                    }
                                }
                            });
                    final int i3 = 2;
                    button2.setOnClickListener(
                            new View.OnClickListener(
                                    this) { // from class:
                                            // com.android.settings.security.RequestManageCredentials$$ExternalSyntheticLambda0
                                public final /* synthetic */ RequestManageCredentials f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    int i32 = i3;
                                    RequestManageCredentials requestManageCredentials = this.f$0;
                                    switch (i32) {
                                        case 0:
                                            requestManageCredentials.mRecyclerView.scrollToPosition(
                                                    requestManageCredentials.mIsLandscapeMode
                                                            ? requestManageCredentials
                                                                            .mAuthenticationPolicy
                                                                            .getAppAndUriMappings()
                                                                            .size()
                                                                    - 1
                                                            : requestManageCredentials
                                                                    .mAuthenticationPolicy
                                                                    .getAppAndUriMappings()
                                                                    .size());
                                            requestManageCredentials.mExtendedFab.performMotion(1);
                                            requestManageCredentials.showButtonPanel();
                                            break;
                                        case 1:
                                            int i4 = RequestManageCredentials.$r8$clinit;
                                            requestManageCredentials.getClass();
                                            DevicePolicyEventLogger.createEvent(181).write();
                                            requestManageCredentials.setResult(0);
                                            requestManageCredentials.finish();
                                            break;
                                        default:
                                            int i5 = RequestManageCredentials.$r8$clinit;
                                            requestManageCredentials.getClass();
                                            try {
                                                requestManageCredentials
                                                        .mKeyChainConnection
                                                        .getService()
                                                        .setCredentialManagementApp(
                                                                requestManageCredentials
                                                                        .mCredentialManagerPackage,
                                                                requestManageCredentials
                                                                        .mAuthenticationPolicy);
                                                DevicePolicyEventLogger.createEvent(180).write();
                                                requestManageCredentials.setResult(-1);
                                            } catch (RemoteException e) {
                                                Log.e(
                                                        "ManageCredentials",
                                                        "Unable to set credential manager app",
                                                        e);
                                                DevicePolicyEventLogger.createEvent(182).write();
                                            }
                                            requestManageCredentials.finish();
                                            break;
                                    }
                                }
                            });
                    ExtendedFloatingActionButton extendedFloatingActionButton =
                            (ExtendedFloatingActionButton) findViewById(R.id.extended_fab);
                    this.mExtendedFab = extendedFloatingActionButton;
                    final int i4 = 0;
                    extendedFloatingActionButton.setOnClickListener(
                            new View.OnClickListener(
                                    this) { // from class:
                                            // com.android.settings.security.RequestManageCredentials$$ExternalSyntheticLambda0
                                public final /* synthetic */ RequestManageCredentials f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    int i32 = i4;
                                    RequestManageCredentials requestManageCredentials = this.f$0;
                                    switch (i32) {
                                        case 0:
                                            requestManageCredentials.mRecyclerView.scrollToPosition(
                                                    requestManageCredentials.mIsLandscapeMode
                                                            ? requestManageCredentials
                                                                            .mAuthenticationPolicy
                                                                            .getAppAndUriMappings()
                                                                            .size()
                                                                    - 1
                                                            : requestManageCredentials
                                                                    .mAuthenticationPolicy
                                                                    .getAppAndUriMappings()
                                                                    .size());
                                            requestManageCredentials.mExtendedFab.performMotion(1);
                                            requestManageCredentials.showButtonPanel();
                                            break;
                                        case 1:
                                            int i42 = RequestManageCredentials.$r8$clinit;
                                            requestManageCredentials.getClass();
                                            DevicePolicyEventLogger.createEvent(181).write();
                                            requestManageCredentials.setResult(0);
                                            requestManageCredentials.finish();
                                            break;
                                        default:
                                            int i5 = RequestManageCredentials.$r8$clinit;
                                            requestManageCredentials.getClass();
                                            try {
                                                requestManageCredentials
                                                        .mKeyChainConnection
                                                        .getService()
                                                        .setCredentialManagementApp(
                                                                requestManageCredentials
                                                                        .mCredentialManagerPackage,
                                                                requestManageCredentials
                                                                        .mAuthenticationPolicy);
                                                DevicePolicyEventLogger.createEvent(180).write();
                                                requestManageCredentials.setResult(-1);
                                            } catch (RemoteException e) {
                                                Log.e(
                                                        "ManageCredentials",
                                                        "Unable to set credential manager app",
                                                        e);
                                                DevicePolicyEventLogger.createEvent(182).write();
                                            }
                                            requestManageCredentials.finish();
                                            break;
                                    }
                                }
                            });
                    this.mRecyclerView.addOnScrollListener(
                            new RecyclerView
                                    .OnScrollListener() { // from class:
                                                          // com.android.settings.security.RequestManageCredentials.1
                                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                                public final void onScrolled(
                                        RecyclerView recyclerView2, int i5, int i6) {
                                    RequestManageCredentials requestManageCredentials =
                                            RequestManageCredentials.this;
                                    if (requestManageCredentials.mDisplayingButtonPanel) {
                                        return;
                                    }
                                    if (i6 > 0
                                            && requestManageCredentials.mExtendedFab.getVisibility()
                                                    == 0) {
                                        requestManageCredentials.mExtendedFab.performMotion(2);
                                    }
                                    if (requestManageCredentials.mLayoutManager != null
                                            && requestManageCredentials.mRecyclerView.getAdapter()
                                                    != null) {
                                        LinearLayoutManager linearLayoutManager =
                                                requestManageCredentials.mLayoutManager;
                                        View findOneVisibleChild =
                                                linearLayoutManager.findOneVisibleChild(
                                                        linearLayoutManager.getChildCount() - 1,
                                                        -1,
                                                        true,
                                                        false);
                                        if ((findOneVisibleChild != null
                                                        ? RecyclerView.LayoutManager.getPosition(
                                                                findOneVisibleChild)
                                                        : -1)
                                                < requestManageCredentials
                                                                .mRecyclerView
                                                                .getAdapter()
                                                                .getItemCount()
                                                        - 1) {
                                            requestManageCredentials.mExtendedFab.performMotion(0);
                                            requestManageCredentials.mRecyclerView.setPadding(
                                                    0, 0, 0, 0);
                                            requestManageCredentials.mButtonPanel.setVisibility(8);
                                            return;
                                        }
                                    }
                                    requestManageCredentials.mExtendedFab.performMotion(1);
                                    requestManageCredentials.showButtonPanel();
                                }
                            });
                    return;
                } catch (RemoteException e) {
                    Log.e("ManageCredentials", "Invalid authentication policy", e);
                }
            }
            Log.e("ManageCredentials", "Invalid authentication policy");
            DevicePolicyEventLogger.createEvent(182).write();
            setResult(0);
            finish();
            return;
        }
        Log.e(
                "ManageCredentials",
                "Credential management on managed devices should be done by the Device Policy"
                    + " Controller, not a credential management app");
        DevicePolicyEventLogger.createEvent(182).write();
        setResult(0);
        finish();
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        KeyChain.KeyChainConnection keyChainConnection = this.mKeyChainConnection;
        if (keyChainConnection != null) {
            keyChainConnection.close();
            this.mKeyChainConnection = null;
            this.mKeyChainTread.quitSafely();
        }
    }

    public final void showButtonPanel() {
        this.mRecyclerView.setPadding(
                0, 0, 0, (int) ((getResources().getDisplayMetrics().density * 60.0f) + 0.5f));
        this.mButtonPanel.setVisibility(0);
        this.mDisplayingButtonPanel = true;
    }
}
