package com.android.settings.privatespace.delete;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;
import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.R;
import com.android.settings.core.InstrumentedFragment;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settings.privatespace.PrivateSpaceMaintainer;
import com.android.settingslib.accounts.AuthenticatorHelper;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;
import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrivateSpaceDeleteFragment extends InstrumentedFragment {
    public View mContentView;
    public UserHandle mPrivateUserHandle;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2040;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1 && i2 == -1) {
            NavHostFragment.Companion.findNavController(this).navigate(R.id.action_authenticate_delete);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        if (Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures()) {
            super.onCreate(bundle);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1, types: [int] */
    /* JADX WARN: Type inference failed for: r14v3 */
    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        UserHandle privateProfileHandle = PrivateSpaceMaintainer.getInstance(getContext()).getPrivateProfileHandle();
        this.mPrivateUserHandle = privateProfileHandle;
        if (privateProfileHandle == null) {
            Log.e("PrivateSpaceDeleteFrag", "Private space user handle cannot be null");
            getActivity().finish();
        }
        boolean z = false;
        View inflate = layoutInflater.inflate(R.layout.private_space_delete, viewGroup, false);
        this.mContentView = inflate;
        FooterBarMixin footerBarMixin = (FooterBarMixin) ((GlifLayout) inflate.findViewById(R.id.private_space_delete_layout)).getMixin(FooterBarMixin.class);
        final FragmentActivity activity = getActivity();
        final int i = 1;
        footerBarMixin.setPrimaryButton(new FooterButton(activity.getString(R.string.private_space_delete_button_label), new View.OnClickListener() { // from class: com.android.settings.privatespace.delete.PrivateSpaceDeleteFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i2 = i;
                Object obj = this;
                switch (i2) {
                    case 0:
                        ((Activity) obj).onBackPressed();
                        break;
                    default:
                        PrivateSpaceDeleteFragment privateSpaceDeleteFragment = (PrivateSpaceDeleteFragment) obj;
                        ChooseLockSettingsHelper.Builder builder = new ChooseLockSettingsHelper.Builder(privateSpaceDeleteFragment.getActivity(), privateSpaceDeleteFragment);
                        UserHandle userHandle = privateSpaceDeleteFragment.mPrivateUserHandle;
                        if (userHandle == null) {
                            Log.e("PrivateSpaceDeleteFrag", "Private space user handle cannot be null");
                            privateSpaceDeleteFragment.getActivity().finish();
                            break;
                        } else {
                            builder.mRequestCode = 1;
                            builder.mUserId = userHandle.getIdentifier();
                            builder.show();
                            break;
                        }
                }
            }
        }, 0, 2132083805));
        final int i2 = 0;
        footerBarMixin.setSecondaryButton(new FooterButton(activity.getString(android.R.string.cancel), new View.OnClickListener() { // from class: com.android.settings.privatespace.delete.PrivateSpaceDeleteFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i22 = i2;
                Object obj = activity;
                switch (i22) {
                    case 0:
                        ((Activity) obj).onBackPressed();
                        break;
                    default:
                        PrivateSpaceDeleteFragment privateSpaceDeleteFragment = (PrivateSpaceDeleteFragment) obj;
                        ChooseLockSettingsHelper.Builder builder = new ChooseLockSettingsHelper.Builder(privateSpaceDeleteFragment.getActivity(), privateSpaceDeleteFragment);
                        UserHandle userHandle = privateSpaceDeleteFragment.mPrivateUserHandle;
                        if (userHandle == null) {
                            Log.e("PrivateSpaceDeleteFrag", "Private space user handle cannot be null");
                            privateSpaceDeleteFragment.getActivity().finish();
                            break;
                        } else {
                            builder.mRequestCode = 1;
                            builder.mUserId = userHandle.getIdentifier();
                            builder.show();
                            break;
                        }
                }
            }
        }, 2, 2132083806), false);
        View findViewById = this.mContentView.findViewById(R.id.accounts_label);
        LinearLayout linearLayout = (LinearLayout) this.mContentView.findViewById(R.id.accounts);
        linearLayout.removeAllViews();
        FragmentActivity activity2 = getActivity();
        AccountManager accountManager = AccountManager.get(activity2);
        LayoutInflater layoutInflater2 = (LayoutInflater) activity2.getSystemService(LayoutInflater.class);
        AuthenticatorHelper authenticatorHelper = new AuthenticatorHelper(activity2, this.mPrivateUserHandle, null);
        ArrayList arrayList = authenticatorHelper.mEnabledAccountTypes;
        String[] strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
        int length = strArr.length;
        int i3 = 0;
        while (i3 < length) {
            String str = strArr[i3];
            Account[] accountsByTypeAsUser = accountManager.getAccountsByTypeAsUser(str, this.mPrivateUserHandle);
            Drawable drawableForType = authenticatorHelper.getDrawableForType(getContext(), str);
            if (drawableForType == null) {
                drawableForType = activity2.getPackageManager().getDefaultActivityIcon();
            }
            int length2 = accountsByTypeAsUser.length;
            for (?? r14 = z; r14 < length2; r14++) {
                Account account = accountsByTypeAsUser[r14];
                FragmentActivity fragmentActivity = activity2;
                View inflate2 = layoutInflater2.inflate(R.layout.main_clear_account, linearLayout, z);
                ((ImageView) inflate2.findViewById(android.R.id.icon)).setImageDrawable(drawableForType);
                ((TextView) inflate2.findViewById(android.R.id.title)).setText(account.name);
                linearLayout.addView(inflate2);
                activity2 = fragmentActivity;
                z = false;
            }
            i3++;
            z = false;
        }
        if (linearLayout.getChildCount() > 0) {
            findViewById.setVisibility(0);
            linearLayout.setVisibility(0);
        }
        return this.mContentView;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        if (PrivateSpaceMaintainer.getInstance(getContext()).isPrivateSpaceLocked()) {
            getActivity().finish();
        }
    }
}
