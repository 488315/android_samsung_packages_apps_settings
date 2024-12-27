package com.android.settings.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;

import com.android.settings.SubSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SubSettingLauncher {
    public final Context mContext;
    public final LaunchRequest mLaunchRequest;
    public boolean mLaunched;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LaunchRequest {
        public Bundle mArguments;
        public String mDestinationName;
        public Bundle mExtras;
        public int mFlags;
        public boolean mIsSecondLayerPage;
        public int mRequestCode;
        public Fragment mResultListener;
        public int mSourceMetricsCategory;
        public CharSequence mTitle;
        public int mTitleResId;
        public String mTitleResPackageName;
        public int mTransitionType;
        public UserHandle mUserHandle;
    }

    public SubSettingLauncher(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context must be non-null.");
        }
        this.mContext = context;
        LaunchRequest launchRequest = new LaunchRequest();
        launchRequest.mSourceMetricsCategory = -100;
        this.mLaunchRequest = launchRequest;
        launchRequest.mTransitionType = 0;
    }

    public final void addFlags(int i) {
        LaunchRequest launchRequest = this.mLaunchRequest;
        launchRequest.mFlags = i | launchRequest.mFlags;
    }

    public final void launch() {
        launchWithIntent(toIntent());
    }

    public void launchAsUser(Intent intent, UserHandle userHandle) {
        this.mContext.startActivityAsUser(intent, userHandle);
    }

    public void launchForResult(Fragment fragment, Intent intent, int i) {
        fragment.startActivityForResult(intent, i);
    }

    public void launchForResultAsUser(
            Intent intent, UserHandle userHandle, Fragment fragment, int i) {
        fragment.getActivity().startActivityForResultAsUser(intent, i, userHandle);
    }

    public final void launchWithIntent(Intent intent) {
        verifyIntent(intent);
        if (this.mLaunched) {
            throw new IllegalStateException(
                    "This launcher has already been executed. Do not reuse");
        }
        this.mLaunched = true;
        LaunchRequest launchRequest = this.mLaunchRequest;
        UserHandle userHandle = launchRequest.mUserHandle;
        boolean z =
                (userHandle == null || userHandle.getIdentifier() == UserHandle.myUserId())
                        ? false
                        : true;
        Fragment fragment = launchRequest.mResultListener;
        boolean z2 = fragment != null;
        if (z && z2) {
            launchForResultAsUser(
                    intent, launchRequest.mUserHandle, fragment, launchRequest.mRequestCode);
            return;
        }
        if (z && !z2) {
            launchAsUser(intent, launchRequest.mUserHandle);
        } else if (z || !z2) {
            launch(intent);
        } else {
            launchForResult(fragment, intent, launchRequest.mRequestCode);
        }
    }

    public final void setResultListener(Fragment fragment, int i) {
        LaunchRequest launchRequest = this.mLaunchRequest;
        launchRequest.mRequestCode = i;
        launchRequest.mResultListener = fragment;
    }

    public final void setTitleRes(int i, String str) {
        LaunchRequest launchRequest = this.mLaunchRequest;
        launchRequest.mTitleResPackageName = str;
        launchRequest.mTitleResId = i;
        launchRequest.mTitle = null;
    }

    public final Intent toIntent() {
        Intent intent = new Intent("android.intent.action.MAIN");
        LaunchRequest launchRequest = this.mLaunchRequest;
        Bundle bundle = launchRequest.mExtras;
        if (bundle != null) {
            intent.replaceExtras(bundle);
        }
        intent.setClass(this.mContext, SubSettings.class);
        if (TextUtils.isEmpty(launchRequest.mDestinationName)) {
            throw new IllegalArgumentException("Destination fragment must be set");
        }
        intent.putExtra(":settings:show_fragment", launchRequest.mDestinationName);
        int i = launchRequest.mSourceMetricsCategory;
        if (i < 0) {
            throw new IllegalArgumentException("Source metrics category must be set");
        }
        intent.putExtra(":settings:source_metrics", i);
        intent.putExtra(":settings:show_fragment_args", launchRequest.mArguments);
        intent.putExtra(
                ":settings:show_fragment_title_res_package_name",
                launchRequest.mTitleResPackageName);
        intent.putExtra(":settings:show_fragment_title_resid", launchRequest.mTitleResId);
        intent.putExtra(":settings:show_fragment_title", launchRequest.mTitle);
        intent.addFlags(launchRequest.mFlags);
        intent.putExtra("page_transition_type", launchRequest.mTransitionType);
        intent.putExtra(":settings:is_second_layer_page", launchRequest.mIsSecondLayerPage);
        return intent;
    }

    public void verifyIntent(Intent intent) {
        String name = SubSettings.class.getName();
        ComponentName component = intent.getComponent();
        String stringExtra = intent.getStringExtra(":settings:show_fragment");
        int intExtra = intent.getIntExtra(":settings:source_metrics", -1);
        if (component != null && !TextUtils.equals(name, component.getClassName())) {
            throw new IllegalArgumentException("Class must be: ".concat(name));
        }
        if (TextUtils.isEmpty(stringExtra)) {
            throw new IllegalArgumentException("Destination fragment must be set");
        }
        if (intExtra < 0) {
            throw new IllegalArgumentException("Source metrics category must be set");
        }
    }

    public void launch(Intent intent) {
        this.mContext.startActivity(intent);
    }
}
