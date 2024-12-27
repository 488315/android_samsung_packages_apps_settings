package com.samsung.android.settings.notification.app;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.notification.zen.ZenModeContactExceptionController;
import com.android.settings.notification.zen.ZenModePeopleSettings;
import com.android.settings.notification.zen.ZenModeSettingsBase;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.notification.zen.SecZenModeBypassingAppsController;
import com.samsung.android.settings.voiceinput.samsungaccount.contract.SaContract;
import com.samsung.android.settings.widget.SecRecyclerViewPreference;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AppNotificationTypeAdapter extends RecyclerView.Adapter {
    public ArrayList mAppNotificationTypeInfoList;
    public NotificationBackend mBackend;
    public ClickListener mListener;
    public ZenModeAllBypassingAddAppsListener mZenModeAllBypassingAddAppsListener;
    public ZenModeCallExceptionListener mZenModeCallExceptionListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ClickListener {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DndAppViewHolder extends RecyclerView.ViewHolder {
        public ImageView deleteIcon;
        public TextView dndAppTitle;
        public ImageView dndAppView;
        public RelativeLayout mDndBypassingApps_View;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public final LinearLayout mNotiMain;
        public final TextView mNotiType;
        public final CheckBox mcheckbox;
        public final ImageView noti_type_image;

        public ViewHolder(View view) {
            super(view);
            this.mNotiMain = (LinearLayout) view.findViewById(R.id.noti_main);
            this.noti_type_image = (ImageView) view.findViewById(R.id.noti_type_image);
            this.mNotiType = (TextView) view.findViewById(R.id.noti_type);
            this.mcheckbox = (CheckBox) view.findViewById(R.id.radio_button);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ZenCallViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout contactView;
        public ImageView deleteItemImage;
        public ImageView dndTypeImage;
        public TextView dndTypeTitle;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ZenModeAllBypassingAddAppsListener {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ZenModeCallExceptionListener {}

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mAppNotificationTypeInfoList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        ArrayList arrayList = this.mAppNotificationTypeInfoList;
        if (arrayList != null
                && arrayList.get(i) != null
                && ((AppNotificationTypeInfo) this.mAppNotificationTypeInfoList.get(i))
                        .isAddAppException.booleanValue()) {
            return 2;
        }
        ArrayList arrayList2 = this.mAppNotificationTypeInfoList;
        return (arrayList2 == null
                        || arrayList2.get(i) == null
                        || ((AppNotificationTypeInfo) this.mAppNotificationTypeInfoList.get(i))
                                        .mPackage
                                != ApnSettings.MVNO_NONE)
                ? 1
                : 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        char c;
        ArrayList arrayList = this.mAppNotificationTypeInfoList;
        if (arrayList == null
                || arrayList.get(i) == null
                || !((AppNotificationTypeInfo) this.mAppNotificationTypeInfoList.get(i))
                        .isAddAppException.booleanValue()) {
            ArrayList arrayList2 = this.mAppNotificationTypeInfoList;
            c =
                    (arrayList2 == null
                                    || arrayList2.get(i) == null
                                    || ((AppNotificationTypeInfo)
                                                            this.mAppNotificationTypeInfoList.get(
                                                                    i))
                                                    .mPackage
                                            != ApnSettings.MVNO_NONE)
                            ? (char) 1
                            : (char) 0;
        } else {
            c = 2;
        }
        if (c == 0) {
            ZenCallViewHolder zenCallViewHolder = (ZenCallViewHolder) viewHolder;
            zenCallViewHolder.deleteItemImage.setVisibility(i == 0 ? 8 : 0);
            final int i2 = 0;
            zenCallViewHolder.contactView.setOnClickListener(
                    new View.OnClickListener(this) { // from class:
                        // com.samsung.android.settings.notification.app.AppNotificationTypeAdapter.1
                        public final /* synthetic */ AppNotificationTypeAdapter this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            List list;
                            switch (i2) {
                                case 0:
                                    if (i == 0) {
                                        ZenModePeopleSettings zenModePeopleSettings =
                                                (ZenModePeopleSettings)
                                                        this.this$0.mZenModeCallExceptionListener;
                                        zenModePeopleSettings.getClass();
                                        Intent intent =
                                                new Intent("intent.action.INTERACTION_TOPMENU");
                                        intent.putExtra(
                                                SaContract.ExtraKey.ADDITIONAL, "phone-multi");
                                        intent.putExtra("disable_selectall", true);
                                        intent.putExtra("has_additional", true);
                                        intent.putExtra("maxRecipientCount", 50);
                                        intent.putExtra("excludeProfile", true);
                                        intent.putExtra("block-tab", true);
                                        final ArrayList<String> arrayList3 = new ArrayList<>();
                                        try {
                                            if (ZenModePeopleSettings.mIsFromDnd) {
                                                list =
                                                        ((NotificationManager)
                                                                        ((ZenModeSettingsBase)
                                                                                        zenModePeopleSettings)
                                                                                .mContext
                                                                                        .getSystemService(
                                                                                                NotificationManager
                                                                                                        .class))
                                                                .getNotificationPolicy()
                                                                .getExceptionContacts();
                                            } else {
                                                String[] split =
                                                        BixbyRoutineActionHandler.contact_exist_list
                                                                .split(",");
                                                ZenModePeopleSettings.mContactAllowList =
                                                        new ArrayList();
                                                for (String str : split) {
                                                    ((ArrayList)
                                                                    ZenModePeopleSettings
                                                                            .mContactAllowList)
                                                            .add(str);
                                                }
                                                list = ZenModePeopleSettings.mContactAllowList;
                                            }
                                            list.forEach(
                                                    new Consumer() { // from class:
                                                        // com.android.settings.notification.zen.ZenModePeopleSettings$$ExternalSyntheticLambda1
                                                        @Override // java.util.function.Consumer
                                                        public final void accept(Object obj) {
                                                            ArrayList arrayList4 = arrayList3;
                                                            BixbyRoutineActionHandler
                                                                    bixbyRoutineActionHandler =
                                                                            ZenModePeopleSettings
                                                                                    .mRSHandler;
                                                            String str2 =
                                                                    ((String) obj).split(";")[0];
                                                            if (TextUtils.isEmpty(str2)) {
                                                                return;
                                                            }
                                                            arrayList4.add(str2 + ";");
                                                        }
                                                    });
                                            intent.putStringArrayListExtra(
                                                    "selected_id_list", arrayList3);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        zenModePeopleSettings.startActivityForResult(intent, 1);
                                        LoggingHelper.insertEventLogging(36034, "NSTE0304");
                                        break;
                                    }
                                    break;
                                default:
                                    ZenModeCallExceptionListener zenModeCallExceptionListener =
                                            this.this$0.mZenModeCallExceptionListener;
                                    int i3 = i;
                                    ZenModeContactExceptionController
                                            zenModeContactExceptionController =
                                                    ((ZenModePeopleSettings)
                                                                    zenModeCallExceptionListener)
                                                            .mZenModeContactExceptionController;
                                    int i4 = 1;
                                    if (zenModeContactExceptionController.mIsFromDnd) {
                                        SecRecyclerViewPreference secRecyclerViewPreference =
                                                zenModeContactExceptionController.mPreference;
                                        secRecyclerViewPreference.mAppNotificationTypeInfoList
                                                .remove(i3);
                                        ArrayList arrayList4 =
                                                secRecyclerViewPreference
                                                        .mAppNotificationTypeInfoList;
                                        Context context = secRecyclerViewPreference.mContext;
                                        ArrayList arrayList5 = new ArrayList();
                                        while (i4 < arrayList4.size()) {
                                            AppNotificationTypeInfo appNotificationTypeInfo =
                                                    (AppNotificationTypeInfo) arrayList4.get(i4);
                                            arrayList5.add(
                                                    appNotificationTypeInfo.contactId
                                                            + ";"
                                                            + appNotificationTypeInfo
                                                                    .contactNumber);
                                            i4++;
                                        }
                                        try {
                                            NotificationManager notificationManager =
                                                    (NotificationManager)
                                                            context.getSystemService(
                                                                    NotificationManager.class);
                                            NotificationManager.Policy notificationPolicy =
                                                    notificationManager.getNotificationPolicy();
                                            notificationManager.setNotificationPolicy(
                                                    new NotificationManager.Policy(
                                                            notificationPolicy.priorityCategories,
                                                            notificationPolicy.priorityCallSenders,
                                                            notificationPolicy
                                                                    .priorityMessageSenders,
                                                            notificationPolicy
                                                                    .suppressedVisualEffects,
                                                            notificationPolicy.state,
                                                            notificationPolicy
                                                                    .priorityConversationSenders,
                                                            Settings.Global.getInt(
                                                                    context.getContentResolver(),
                                                                    "zen_selected_exception_contacts_allowed",
                                                                    0),
                                                            arrayList5,
                                                            Settings.Global.getInt(
                                                                    context.getContentResolver(),
                                                                    SecZenModeBypassingAppsController
                                                                            .ZEN_MODE_BYPASSING_APPS_DB_KEY,
                                                                    0),
                                                            notificationPolicy
                                                                    .getAppBypassDndList()));
                                        } catch (Exception e2) {
                                            e2.printStackTrace();
                                        }
                                        secRecyclerViewPreference.mAppNotificationTypeAdapter
                                                .notifyDataSetChanged();
                                        break;
                                    } else {
                                        SecRecyclerViewPreference secRecyclerViewPreference2 =
                                                zenModeContactExceptionController.mPreference;
                                        secRecyclerViewPreference2.mAppNotificationTypeInfoList
                                                .remove(i3);
                                        ArrayList arrayList6 =
                                                secRecyclerViewPreference2
                                                        .mAppNotificationTypeInfoList;
                                        BixbyRoutineActionHandler bixbyRoutineActionHandler =
                                                BixbyRoutineActionHandler.getInstance();
                                        ArrayList arrayList7 = new ArrayList();
                                        while (i4 < arrayList6.size()) {
                                            AppNotificationTypeInfo appNotificationTypeInfo2 =
                                                    (AppNotificationTypeInfo) arrayList6.get(i4);
                                            arrayList7.add(
                                                    appNotificationTypeInfo2.contactId
                                                            + ";"
                                                            + appNotificationTypeInfo2
                                                                    .contactNumber);
                                            i4++;
                                        }
                                        bixbyRoutineActionHandler.getClass();
                                        BixbyRoutineActionHandler.setContact_exist_list(arrayList7);
                                        BixbyRoutineActionHandler.setPeoplesummary(
                                                secRecyclerViewPreference2.mContext);
                                        secRecyclerViewPreference2.mAppNotificationTypeAdapter
                                                .notifyDataSetChanged();
                                        break;
                                    }
                            }
                        }
                    });
            if (i == 0) {
                zenCallViewHolder.dndTypeImage.setImageResource(
                        ((AppNotificationTypeInfo) this.mAppNotificationTypeInfoList.get(i))
                                .imageEntry);
            } else if (((AppNotificationTypeInfo) this.mAppNotificationTypeInfoList.get(i))
                            .contactPhoto
                    != null) {
                zenCallViewHolder.dndTypeImage.setImageBitmap(
                        ((AppNotificationTypeInfo) this.mAppNotificationTypeInfoList.get(i))
                                .contactPhoto);
            } else {
                zenCallViewHolder.dndTypeImage.setImageResource(R.drawable.ic_no_photo);
            }
            zenCallViewHolder.dndTypeTitle.setText(
                    ((AppNotificationTypeInfo) this.mAppNotificationTypeInfoList.get(i)).title);
            final int i3 = 1;
            zenCallViewHolder.deleteItemImage.setOnClickListener(
                    new View.OnClickListener(this) { // from class:
                        // com.samsung.android.settings.notification.app.AppNotificationTypeAdapter.1
                        public final /* synthetic */ AppNotificationTypeAdapter this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            List list;
                            switch (i3) {
                                case 0:
                                    if (i == 0) {
                                        ZenModePeopleSettings zenModePeopleSettings =
                                                (ZenModePeopleSettings)
                                                        this.this$0.mZenModeCallExceptionListener;
                                        zenModePeopleSettings.getClass();
                                        Intent intent =
                                                new Intent("intent.action.INTERACTION_TOPMENU");
                                        intent.putExtra(
                                                SaContract.ExtraKey.ADDITIONAL, "phone-multi");
                                        intent.putExtra("disable_selectall", true);
                                        intent.putExtra("has_additional", true);
                                        intent.putExtra("maxRecipientCount", 50);
                                        intent.putExtra("excludeProfile", true);
                                        intent.putExtra("block-tab", true);
                                        final ArrayList arrayList3 = new ArrayList<>();
                                        try {
                                            if (ZenModePeopleSettings.mIsFromDnd) {
                                                list =
                                                        ((NotificationManager)
                                                                        ((ZenModeSettingsBase)
                                                                                        zenModePeopleSettings)
                                                                                .mContext
                                                                                        .getSystemService(
                                                                                                NotificationManager
                                                                                                        .class))
                                                                .getNotificationPolicy()
                                                                .getExceptionContacts();
                                            } else {
                                                String[] split =
                                                        BixbyRoutineActionHandler.contact_exist_list
                                                                .split(",");
                                                ZenModePeopleSettings.mContactAllowList =
                                                        new ArrayList();
                                                for (String str : split) {
                                                    ((ArrayList)
                                                                    ZenModePeopleSettings
                                                                            .mContactAllowList)
                                                            .add(str);
                                                }
                                                list = ZenModePeopleSettings.mContactAllowList;
                                            }
                                            list.forEach(
                                                    new Consumer() { // from class:
                                                        // com.android.settings.notification.zen.ZenModePeopleSettings$$ExternalSyntheticLambda1
                                                        @Override // java.util.function.Consumer
                                                        public final void accept(Object obj) {
                                                            ArrayList arrayList4 = arrayList3;
                                                            BixbyRoutineActionHandler
                                                                    bixbyRoutineActionHandler =
                                                                            ZenModePeopleSettings
                                                                                    .mRSHandler;
                                                            String str2 =
                                                                    ((String) obj).split(";")[0];
                                                            if (TextUtils.isEmpty(str2)) {
                                                                return;
                                                            }
                                                            arrayList4.add(str2 + ";");
                                                        }
                                                    });
                                            intent.putStringArrayListExtra(
                                                    "selected_id_list", arrayList3);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        zenModePeopleSettings.startActivityForResult(intent, 1);
                                        LoggingHelper.insertEventLogging(36034, "NSTE0304");
                                        break;
                                    }
                                    break;
                                default:
                                    ZenModeCallExceptionListener zenModeCallExceptionListener =
                                            this.this$0.mZenModeCallExceptionListener;
                                    int i32 = i;
                                    ZenModeContactExceptionController
                                            zenModeContactExceptionController =
                                                    ((ZenModePeopleSettings)
                                                                    zenModeCallExceptionListener)
                                                            .mZenModeContactExceptionController;
                                    int i4 = 1;
                                    if (zenModeContactExceptionController.mIsFromDnd) {
                                        SecRecyclerViewPreference secRecyclerViewPreference =
                                                zenModeContactExceptionController.mPreference;
                                        secRecyclerViewPreference.mAppNotificationTypeInfoList
                                                .remove(i32);
                                        ArrayList arrayList4 =
                                                secRecyclerViewPreference
                                                        .mAppNotificationTypeInfoList;
                                        Context context = secRecyclerViewPreference.mContext;
                                        ArrayList arrayList5 = new ArrayList();
                                        while (i4 < arrayList4.size()) {
                                            AppNotificationTypeInfo appNotificationTypeInfo =
                                                    (AppNotificationTypeInfo) arrayList4.get(i4);
                                            arrayList5.add(
                                                    appNotificationTypeInfo.contactId
                                                            + ";"
                                                            + appNotificationTypeInfo
                                                                    .contactNumber);
                                            i4++;
                                        }
                                        try {
                                            NotificationManager notificationManager =
                                                    (NotificationManager)
                                                            context.getSystemService(
                                                                    NotificationManager.class);
                                            NotificationManager.Policy notificationPolicy =
                                                    notificationManager.getNotificationPolicy();
                                            notificationManager.setNotificationPolicy(
                                                    new NotificationManager.Policy(
                                                            notificationPolicy.priorityCategories,
                                                            notificationPolicy.priorityCallSenders,
                                                            notificationPolicy
                                                                    .priorityMessageSenders,
                                                            notificationPolicy
                                                                    .suppressedVisualEffects,
                                                            notificationPolicy.state,
                                                            notificationPolicy
                                                                    .priorityConversationSenders,
                                                            Settings.Global.getInt(
                                                                    context.getContentResolver(),
                                                                    "zen_selected_exception_contacts_allowed",
                                                                    0),
                                                            arrayList5,
                                                            Settings.Global.getInt(
                                                                    context.getContentResolver(),
                                                                    SecZenModeBypassingAppsController
                                                                            .ZEN_MODE_BYPASSING_APPS_DB_KEY,
                                                                    0),
                                                            notificationPolicy
                                                                    .getAppBypassDndList()));
                                        } catch (Exception e2) {
                                            e2.printStackTrace();
                                        }
                                        secRecyclerViewPreference.mAppNotificationTypeAdapter
                                                .notifyDataSetChanged();
                                        break;
                                    } else {
                                        SecRecyclerViewPreference secRecyclerViewPreference2 =
                                                zenModeContactExceptionController.mPreference;
                                        secRecyclerViewPreference2.mAppNotificationTypeInfoList
                                                .remove(i32);
                                        ArrayList arrayList6 =
                                                secRecyclerViewPreference2
                                                        .mAppNotificationTypeInfoList;
                                        BixbyRoutineActionHandler bixbyRoutineActionHandler =
                                                BixbyRoutineActionHandler.getInstance();
                                        ArrayList arrayList7 = new ArrayList();
                                        while (i4 < arrayList6.size()) {
                                            AppNotificationTypeInfo appNotificationTypeInfo2 =
                                                    (AppNotificationTypeInfo) arrayList6.get(i4);
                                            arrayList7.add(
                                                    appNotificationTypeInfo2.contactId
                                                            + ";"
                                                            + appNotificationTypeInfo2
                                                                    .contactNumber);
                                            i4++;
                                        }
                                        bixbyRoutineActionHandler.getClass();
                                        BixbyRoutineActionHandler.setContact_exist_list(arrayList7);
                                        BixbyRoutineActionHandler.setPeoplesummary(
                                                secRecyclerViewPreference2.mContext);
                                        secRecyclerViewPreference2.mAppNotificationTypeAdapter
                                                .notifyDataSetChanged();
                                        break;
                                    }
                            }
                        }
                    });
            return;
        }
        if (c == 1) {
            ViewHolder viewHolder2 = (ViewHolder) viewHolder;
            final int i4 = 0;
            viewHolder2.mNotiMain.setOnClickListener(
                    new View.OnClickListener(this) { // from class:
                        // com.samsung.android.settings.notification.app.AppNotificationTypeAdapter$$ExternalSyntheticLambda0
                        public final /* synthetic */ AppNotificationTypeAdapter f$0;

                        {
                            this.f$0 = this;
                        }

                        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                        /* JADX WARN: Code restructure failed: missing block: B:98:0x02d2, code lost:

                           if (r8.equals("lockscreen") == false) goto L74;
                        */
                        @Override // android.view.View.OnClickListener
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final void onClick(android.view.View r14) {
                            /*
                                Method dump skipped, instructions count: 970
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.samsung.android.settings.notification.app.AppNotificationTypeAdapter$$ExternalSyntheticLambda0.onClick(android.view.View):void");
                        }
                    });
            viewHolder2.noti_type_image.setImageResource(
                    ((AppNotificationTypeInfo) this.mAppNotificationTypeInfoList.get(i))
                            .imageEntry);
            viewHolder2.mNotiType.setText(
                    ((AppNotificationTypeInfo) this.mAppNotificationTypeInfoList.get(i)).title);
            viewHolder2.mcheckbox.setChecked(
                    ((AppNotificationTypeInfo) this.mAppNotificationTypeInfoList.get(i))
                            .selected.booleanValue());
            return;
        }
        if (c != 2) {
            return;
        }
        DndAppViewHolder dndAppViewHolder = (DndAppViewHolder) viewHolder;
        dndAppViewHolder.deleteIcon.setVisibility(i == 0 ? 8 : 0);
        if (i != 0) {
            dndAppViewHolder.dndAppView.setImageDrawable(
                    ((AppNotificationTypeInfo) this.mAppNotificationTypeInfoList.get(i))
                            .mPackageIcon);
        } else {
            dndAppViewHolder.dndAppView.setImageResource(
                    ((AppNotificationTypeInfo) this.mAppNotificationTypeInfoList.get(i))
                            .imageEntry);
        }
        dndAppViewHolder.dndAppTitle.setText(
                ((AppNotificationTypeInfo) this.mAppNotificationTypeInfoList.get(i)).title);
        final int i5 = 1;
        dndAppViewHolder.mDndBypassingApps_View.setOnClickListener(
                new View.OnClickListener(this) { // from class:
                    // com.samsung.android.settings.notification.app.AppNotificationTypeAdapter$$ExternalSyntheticLambda0
                    public final /* synthetic */ AppNotificationTypeAdapter f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        /*
                            Method dump skipped, instructions count: 970
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.settings.notification.app.AppNotificationTypeAdapter$$ExternalSyntheticLambda0.onClick(android.view.View):void");
                    }
                });
        final int i6 = 2;
        dndAppViewHolder.deleteIcon.setOnClickListener(
                new View.OnClickListener(this) { // from class:
                    // com.samsung.android.settings.notification.app.AppNotificationTypeAdapter$$ExternalSyntheticLambda0
                    public final /* synthetic */ AppNotificationTypeAdapter f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        /*
                            Method dump skipped, instructions count: 970
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.settings.notification.app.AppNotificationTypeAdapter$$ExternalSyntheticLambda0.onClick(android.view.View):void");
                    }
                });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            View m =
                    MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                            viewGroup, R.xml.zen_mode_contact_exception, viewGroup, false);
            ZenCallViewHolder zenCallViewHolder = new ZenCallViewHolder(m);
            zenCallViewHolder.contactView = (RelativeLayout) m.findViewById(R.id.dnd_view_layout);
            zenCallViewHolder.dndTypeImage = (ImageView) m.findViewById(R.id.dnd_contact_image);
            zenCallViewHolder.dndTypeTitle = (TextView) m.findViewById(R.id.dnd_contact_title);
            zenCallViewHolder.deleteItemImage = (ImageView) m.findViewById(R.id.delete_item_image);
            return zenCallViewHolder;
        }
        if (i == 1) {
            return new ViewHolder(
                    MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                            viewGroup, R.layout.sec_app_notificatontype_adapter, viewGroup, false));
        }
        if (i != 2) {
            return new ViewHolder(
                    MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                            viewGroup, R.layout.empty_view, viewGroup, false));
        }
        View m2 =
                MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                        viewGroup, R.xml.zen_mode_add_app_exception, viewGroup, false);
        DndAppViewHolder dndAppViewHolder = new DndAppViewHolder(m2);
        dndAppViewHolder.mDndBypassingApps_View =
                (RelativeLayout) m2.findViewById(R.id.dnd_view_layout);
        dndAppViewHolder.dndAppView = (ImageView) m2.findViewById(R.id.dnd_app_view);
        dndAppViewHolder.dndAppTitle = (TextView) m2.findViewById(R.id.dnd_app_title);
        dndAppViewHolder.deleteIcon = (ImageView) m2.findViewById(R.id.delete_icon);
        return dndAppViewHolder;
    }
}
