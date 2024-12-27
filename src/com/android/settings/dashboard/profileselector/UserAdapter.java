package com.android.settings.dashboard.profileselector;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResourcesManager;
import android.content.Context;
import android.content.pm.UserInfo;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.internal.util.UserIcons;
import com.android.internal.widget.RecyclerView;
import com.android.settings.R;
import com.android.settingslib.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UserAdapter extends BaseAdapter {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final LayoutInflater mInflater;
    public final ArrayList mUserDetails;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnClickListener {
        void onClick(int i);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UserDetails {
        public final Drawable mIcon;
        public final String mTitle;
        public final UserHandle mUserHandle;

        public UserDetails(UserHandle userHandle, UserManager userManager, final Context context) {
            String string;
            this.mUserHandle = userHandle;
            UserInfo userInfo = userManager.getUserInfo(userHandle.getIdentifier());
            int color = context.getColor(R.color.sec_profile_select_icon_tint);
            if (userInfo.isManagedProfile()
                    || (Flags.allowPrivateProfile()
                            && android.multiuser.Flags.enablePrivateSpaceFeatures()
                            && android.multiuser.Flags.handleInterleavedSettingsForPrivateSpace()
                            && userInfo.isPrivateProfile())) {
                Drawable userIcon = Utils.getUserIcon(context, userManager, userInfo);
                this.mIcon = userIcon;
                userIcon.setTint(color);
            } else {
                this.mIcon = UserIcons.getDefaultUserIconInColor(context.getResources(), color);
            }
            DevicePolicyManager devicePolicyManager =
                    (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
            Objects.requireNonNull(devicePolicyManager);
            DevicePolicyResourcesManager resources = devicePolicyManager.getResources();
            int identifier = userHandle.getIdentifier();
            if (identifier == -2 || identifier == ActivityManager.getCurrentUser()) {
                final int i = 0;
                string =
                        resources.getString(
                                "Settings.PERSONAL_CATEGORY_HEADER",
                                new Supplier() { // from class:
                                                 // com.android.settings.dashboard.profileselector.UserAdapter$UserDetails$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        int i2 = i;
                                        Context context2 = context;
                                        switch (i2) {
                                            case 0:
                                                return context2.getString(
                                                        R.string.category_personal);
                                            case 1:
                                                return context2.getString(R.string.category_work);
                                            case 2:
                                                return context2.getString(
                                                        R.string.category_private);
                                            default:
                                                return context2.getString(
                                                        R.string.category_personal);
                                        }
                                    }
                                });
            } else if (userManager.isManagedProfile(identifier)) {
                final int i2 = 1;
                string =
                        resources.getString(
                                "Settings.WORK_CATEGORY_HEADER",
                                new Supplier() { // from class:
                                                 // com.android.settings.dashboard.profileselector.UserAdapter$UserDetails$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        int i22 = i2;
                                        Context context2 = context;
                                        switch (i22) {
                                            case 0:
                                                return context2.getString(
                                                        R.string.category_personal);
                                            case 1:
                                                return context2.getString(R.string.category_work);
                                            case 2:
                                                return context2.getString(
                                                        R.string.category_private);
                                            default:
                                                return context2.getString(
                                                        R.string.category_personal);
                                        }
                                    }
                                });
            } else if (Flags.allowPrivateProfile()
                    && android.multiuser.Flags.enablePrivateSpaceFeatures()
                    && android.multiuser.Flags.handleInterleavedSettingsForPrivateSpace()
                    && userManager.getUserInfo(identifier).isPrivateProfile()) {
                final int i3 = 2;
                string =
                        resources.getString(
                                "Settings.PRIVATE_CATEGORY_HEADER",
                                new Supplier() { // from class:
                                                 // com.android.settings.dashboard.profileselector.UserAdapter$UserDetails$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        int i22 = i3;
                                        Context context2 = context;
                                        switch (i22) {
                                            case 0:
                                                return context2.getString(
                                                        R.string.category_personal);
                                            case 1:
                                                return context2.getString(R.string.category_work);
                                            case 2:
                                                return context2.getString(
                                                        R.string.category_private);
                                            default:
                                                return context2.getString(
                                                        R.string.category_personal);
                                        }
                                    }
                                });
            } else {
                Log.w("UserAdapter", "title requested for unexpected user id " + identifier);
                final int i4 = 3;
                string =
                        resources.getString(
                                "Settings.PERSONAL_CATEGORY_HEADER",
                                new Supplier() { // from class:
                                                 // com.android.settings.dashboard.profileselector.UserAdapter$UserDetails$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        int i22 = i4;
                                        Context context2 = context;
                                        switch (i22) {
                                            case 0:
                                                return context2.getString(
                                                        R.string.category_personal);
                                            case 1:
                                                return context2.getString(R.string.category_work);
                                            case 2:
                                                return context2.getString(
                                                        R.string.category_private);
                                            default:
                                                return context2.getString(
                                                        R.string.category_personal);
                                        }
                                    }
                                });
            }
            this.mTitle = string;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public final View mButtonView;
        public final ImageView mIconView;
        public final TextView mTitleView;

        public ViewHolder(View view) {
            super(view);
            this.mIconView = (ImageView) view.findViewById(android.R.id.icon);
            this.mTitleView = (TextView) view.findViewById(android.R.id.title);
            this.mButtonView = view.findViewById(R.id.button);
        }
    }

    public UserAdapter(Context context, ArrayList arrayList) {
        this.mUserDetails = arrayList;
        this.mInflater = (LayoutInflater) context.getSystemService(LayoutInflater.class);
    }

    public static UserAdapter createUserAdapter(
            UserManager userManager, Context context, List list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            if (com.android.settings.Utils.shouldHideUser(
                    (UserHandle) list.get(size), userManager)) {
                list.remove(size);
            }
        }
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            UserHandle userHandle = (UserHandle) it.next();
            if (!userManager.getUserInfo(userHandle.getIdentifier()).isSecureFolder()) {
                arrayList.add(new UserDetails(userHandle, userManager, context));
            }
        }
        return new UserAdapter(context, arrayList);
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        return this.mUserDetails.size();
    }

    @Override // android.widget.Adapter
    public final Object getItem(int i) {
        return (UserDetails) this.mUserDetails.get(i);
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i) {
        return ((UserDetails) this.mUserDetails.get(i)).mUserHandle.getIdentifier();
    }

    @Override // android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view != null) {
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = this.mInflater.inflate(R.layout.user_preference, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        UserDetails userDetails = (UserDetails) this.mUserDetails.get(i);
        viewHolder.mIconView.setImageDrawable(userDetails.mIcon);
        TextView textView = viewHolder.mTitleView;
        String str = userDetails.mTitle;
        textView.setText(str);
        View view2 = viewHolder.mButtonView;
        if (view2 != null) {
            view2.setContentDescription(str);
        }
        return view;
    }
}
