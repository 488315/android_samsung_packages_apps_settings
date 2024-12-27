package com.samsung.android.settings.bluetooth.lebroadcast;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothDump;
import android.bluetooth.BluetoothLeBroadcastAssistant;
import android.bluetooth.BluetoothLeBroadcastChannel;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.bluetooth.BluetoothLeBroadcastSubgroup;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorPicker$16$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.bluetooth.Utils;
import com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsHelper$$ExternalSyntheticLambda3;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.BluetoothEventManager;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;

import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.textfield.TextInputLayout;
import com.samsung.android.knox.container.EnterpriseContainerConstants;
import com.samsung.android.settings.logging.SALogging;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecBluetoothLeBroadcastAssistantSettings extends AppCompatActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public LinearLayout mAvailableBroadcastsLayout;
    public SecBluetoothLeBroadcastAssistantSourceItem mBroadcastCodeDialogItem;
    public EditText mBroadcastCodeEditText;
    public CheckableImageButton mBroadcastCodeShowImageButton;
    public RecyclerView mBroadcastsListView;
    public CachedBluetoothDevice mCachedDevice;
    public FrameLayout mConnectedCardLayout;
    public Context mContext;
    public LinearLayout mDescriptionLayout;
    public String mDeviceAddress;
    public Executor mExecutor;
    public boolean mIsDecrypting;
    public boolean mIsRemovingByAddFailed;
    public boolean mIsRemovingSource;
    public LocalBluetoothLeBroadcastAssistant mLeBroadcastAssistant;
    public LocalBluetoothManager mManager;
    public LinearLayout mNoBroadcastsLayout;
    public SecBluetoothLeBroadcastAssistantSourceItem mPendingSourceItem;
    public MenuItem mProgressBarItem;
    public String mScreenId;
    public SecBluetoothLeBroadcastAssistantSourceItem mSelectedSourceItem;
    public int mSourceId;
    public SecBluetoothLeBroadcastAssistantListAdapter mSourceListAdapter;
    public final ArrayList mSourceItems = new ArrayList();
    public final Map mActiveSinkMap = new ConcurrentHashMap();
    public AlertDialog mBroadcastCodeDialog = null;
    public final Set mMemberDevices = new HashSet();
    public final BroadcastItemDecoration mBroadcastItemDecoration = new BroadcastItemDecoration();
    public final AnonymousClass1 mBroadcastAssistantCallback = new AnonymousClass1();
    public final AnonymousClass2 mAssistantCallback = new AnonymousClass2();
    public final AnonymousClass3 mEventManagerCallback = new AnonymousClass3();
    public final AnonymousClass7 mHandler =
            new Handler(
                    Looper
                            .getMainLooper()) { // from class:
                                                // com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastAssistantSettings.7
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    int i = message.what;
                    SecBluetoothLeBroadcastAssistantSettings
                            secBluetoothLeBroadcastAssistantSettings =
                                    SecBluetoothLeBroadcastAssistantSettings.this;
                    if (i == 1) {
                        Log.d(
                                "SecBluetoothLeBroadcastAssistantSettings",
                                "broadcast code input timeout 30000ms");
                        BluetoothDump.BtLog("BCST-USER_INPUT timeout 30000ms");
                        AlertDialog alertDialog =
                                secBluetoothLeBroadcastAssistantSettings.mBroadcastCodeDialog;
                        if (alertDialog != null && alertDialog.isShowing()) {
                            secBluetoothLeBroadcastAssistantSettings.mBroadcastCodeDialog.cancel();
                        }
                        secBluetoothLeBroadcastAssistantSettings.mBroadcastCodeDialogItem = null;
                        return;
                    }
                    if (i != 2) {
                        return;
                    }
                    int i2 = message.arg1;
                    int i3 = message.arg2;
                    StringBuilder sb = new StringBuilder();
                    int i4 = SecBluetoothLeBroadcastAssistantSettings.$r8$clinit;
                    sb.append(i2 != 1 ? i2 != 2 ? Integer.toString(i2) : "REMOVE SRC" : "ADD SRC");
                    sb.append(" operation timeout ");
                    sb.append(i3);
                    sb.append("ms");
                    Log.d("SecBluetoothLeBroadcastAssistantSettings", sb.toString());
                    StringBuilder sb2 = new StringBuilder("BCST-");
                    sb2.append(i2 != 1 ? i2 != 2 ? Integer.toString(i2) : "REMOVE SRC" : "ADD SRC");
                    sb2.append(" timeout ");
                    sb2.append(i3);
                    sb2.append("ms");
                    BluetoothDump.BtLog(sb2.toString());
                    if (i2 != 1
                            || secBluetoothLeBroadcastAssistantSettings.mPendingSourceItem
                                    == null) {
                        secBluetoothLeBroadcastAssistantSettings.leaveBroadcastSession$1();
                    } else {
                        secBluetoothLeBroadcastAssistantSettings.mPendingSourceItem = null;
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastAssistantSettings$1, reason: invalid class name */
    public final class AnonymousClass1 implements BluetoothLeBroadcastAssistant.Callback {
        public AnonymousClass1() {}

        public final void onReceiveStateChanged(
                final BluetoothDevice bluetoothDevice,
                int i,
                final BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
            if (!SecBluetoothLeBroadcastAssistantSettings.m1128$$Nest$misSameGroupDevice(
                    SecBluetoothLeBroadcastAssistantSettings.this, bluetoothDevice)) {
                Log.w(
                        "SecBluetoothLeBroadcastAssistantSettings",
                        "onReceiveStateChanged : recv unexpected callback for " + bluetoothDevice);
            } else {
                Log.d(
                        "SecBluetoothLeBroadcastAssistantSettings",
                        "onReceiveStateChanged : " + i + " for " + bluetoothDevice);
                SecBluetoothLeBroadcastAssistantSettings.this.runOnUiThread(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastAssistantSettings$1$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                SecBluetoothLeBroadcastAssistantSettings.AnonymousClass1
                                        anonymousClass1 =
                                                SecBluetoothLeBroadcastAssistantSettings
                                                        .AnonymousClass1.this;
                                BluetoothDevice bluetoothDevice2 = bluetoothDevice;
                                BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState2 =
                                        bluetoothLeBroadcastReceiveState;
                                SecBluetoothLeBroadcastAssistantSettings
                                        secBluetoothLeBroadcastAssistantSettings =
                                                SecBluetoothLeBroadcastAssistantSettings.this;
                                int i2 = SecBluetoothLeBroadcastAssistantSettings.$r8$clinit;
                                secBluetoothLeBroadcastAssistantSettings
                                        .updateListItemFromBroadcastReceiveState(
                                                bluetoothDevice2,
                                                bluetoothLeBroadcastReceiveState2);
                            }
                        });
            }
        }

        public final void onSearchStartFailed(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "onSearchStartFailed : ", "SecBluetoothLeBroadcastAssistantSettings");
        }

        public final void onSearchStarted(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "onSearchStarted : ", "SecBluetoothLeBroadcastAssistantSettings");
            SecBluetoothLeBroadcastAssistantSettings.this.runOnUiThread(
                    new SecBluetoothLeBroadcastAssistantSettings$1$$ExternalSyntheticLambda0(
                            this, 4));
        }

        public final void onSearchStopFailed(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "onSearchStopFailed : ", "SecBluetoothLeBroadcastAssistantSettings");
        }

        public final void onSearchStopped(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "onSearchStopped : ", "SecBluetoothLeBroadcastAssistantSettings");
            SecBluetoothLeBroadcastAssistantSettings.this.runOnUiThread(
                    new SecBluetoothLeBroadcastAssistantSettings$1$$ExternalSyntheticLambda0(
                            this, 5));
        }

        public final void onSourceAddFailed(
                BluetoothDevice bluetoothDevice,
                BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata,
                int i) {
            if (!SecBluetoothLeBroadcastAssistantSettings.m1128$$Nest$misSameGroupDevice(
                    SecBluetoothLeBroadcastAssistantSettings.this, bluetoothDevice)) {
                Log.w(
                        "SecBluetoothLeBroadcastAssistantSettings",
                        "onSourceAddFailed : recv unexpected callback for " + bluetoothDevice);
                return;
            }
            Log.d(
                    "SecBluetoothLeBroadcastAssistantSettings",
                    "onSourceAddFailed : reason " + i + " for " + bluetoothDevice);
            SecBluetoothLeBroadcastAssistantSettings secBluetoothLeBroadcastAssistantSettings =
                    SecBluetoothLeBroadcastAssistantSettings.this;
            if (secBluetoothLeBroadcastAssistantSettings.mSelectedSourceItem == null) {
                Log.d(
                        "SecBluetoothLeBroadcastAssistantSettings",
                        "onSourceAddFailed : selected source item is null");
                return;
            }
            if (i == 25) {
                return;
            }
            SecBluetoothLeBroadcastAssistantSourceItem secBluetoothLeBroadcastAssistantSourceItem =
                    secBluetoothLeBroadcastAssistantSettings.mPendingSourceItem;
            if (secBluetoothLeBroadcastAssistantSourceItem != null
                    && secBluetoothLeBroadcastAssistantSourceItem.mBroadcastId
                            == bluetoothLeBroadcastMetadata.getBroadcastId()) {
                if (i != 1206) {
                    Log.d(
                            "SecBluetoothLeBroadcastAssistantSettings",
                            "onSourceAddFailed : handover case");
                    return;
                } else {
                    Log.d(
                            "SecBluetoothLeBroadcastAssistantSettings",
                            "onSourceAddFailed : audio quality not supported in handover case");
                    SecBluetoothLeBroadcastAssistantSettings.this.runOnUiThread(
                            new SecBluetoothLeBroadcastAssistantSettings$1$$ExternalSyntheticLambda0(
                                    this, 6));
                    return;
                }
            }
            byte[] broadcastCode = bluetoothLeBroadcastMetadata.getBroadcastCode();
            if (i == 1201
                    || (i == 21
                            && broadcastCode != null
                            && (broadcastCode.length < 4 || broadcastCode.length > 16))) {
                SecBluetoothLeBroadcastAssistantSettings secBluetoothLeBroadcastAssistantSettings2 =
                        SecBluetoothLeBroadcastAssistantSettings.this;
                if (secBluetoothLeBroadcastAssistantSettings2.mIsDecrypting) {
                    return;
                }
                secBluetoothLeBroadcastAssistantSettings2.mIsDecrypting = true;
                Log.d("SecBluetoothLeBroadcastAssistantSettings", "onSourceAddFailed : retry");
                SecBluetoothLeBroadcastAssistantSettings.this.runOnUiThread(
                        new SecBluetoothLeBroadcastAssistantSettings$1$$ExternalSyntheticLambda0(
                                this, 7));
                return;
            }
            if (i == 1116) {
                Log.d(
                        "SecBluetoothLeBroadcastAssistantSettings",
                        "onSourceAddFailed : audio already connected");
                SecBluetoothLeBroadcastAssistantSettings secBluetoothLeBroadcastAssistantSettings3 =
                        SecBluetoothLeBroadcastAssistantSettings.this;
                secBluetoothLeBroadcastAssistantSettings3.mSelectedSourceItem = null;
                secBluetoothLeBroadcastAssistantSettings3.runOnUiThread(
                        new SecBluetoothLeBroadcastAssistantSettings$1$$ExternalSyntheticLambda0(
                                this, 8));
                SecBluetoothLeBroadcastAssistantSettings.this.runOnUiThread(
                        new SecBluetoothLeBroadcastAssistantSettings$1$$ExternalSyntheticLambda0(
                                this, 1));
                return;
            }
            if (i == 1206) {
                Log.d(
                        "SecBluetoothLeBroadcastAssistantSettings",
                        "onSourceAddFailed : audio quality not supported");
                SecBluetoothLeBroadcastAssistantSettings secBluetoothLeBroadcastAssistantSettings4 =
                        SecBluetoothLeBroadcastAssistantSettings.this;
                secBluetoothLeBroadcastAssistantSettings4.mSelectedSourceItem = null;
                secBluetoothLeBroadcastAssistantSettings4.runOnUiThread(
                        new SecBluetoothLeBroadcastAssistantSettings$1$$ExternalSyntheticLambda0(
                                this, 2));
                return;
            }
            SecBluetoothLeBroadcastAssistantSettings secBluetoothLeBroadcastAssistantSettings5 =
                    SecBluetoothLeBroadcastAssistantSettings.this;
            if (secBluetoothLeBroadcastAssistantSettings5.mIsRemovingByAddFailed) {
                Log.d(
                        "SecBluetoothLeBroadcastAssistantSettings",
                        "onSourceAddFailed : already removing");
                return;
            }
            secBluetoothLeBroadcastAssistantSettings5.runOnUiThread(
                    new SecBluetoothLeBroadcastAssistantSettings$1$$ExternalSyntheticLambda0(
                            this, 3));
            AlertDialog alertDialog =
                    SecBluetoothLeBroadcastAssistantSettings.this.mBroadcastCodeDialog;
            if (alertDialog != null && alertDialog.isShowing()) {
                SecBluetoothLeBroadcastAssistantSettings secBluetoothLeBroadcastAssistantSettings6 =
                        SecBluetoothLeBroadcastAssistantSettings.this;
                secBluetoothLeBroadcastAssistantSettings6.mIsRemovingByAddFailed = true;
                secBluetoothLeBroadcastAssistantSettings6.mBroadcastCodeDialog.cancel();
            } else if (((ConcurrentHashMap)
                            SecBluetoothLeBroadcastAssistantSettings.this.mActiveSinkMap)
                    .containsKey(bluetoothDevice)) {
                SecBluetoothLeBroadcastAssistantSettings secBluetoothLeBroadcastAssistantSettings7 =
                        SecBluetoothLeBroadcastAssistantSettings.this;
                secBluetoothLeBroadcastAssistantSettings7.mIsRemovingByAddFailed = true;
                secBluetoothLeBroadcastAssistantSettings7.leaveBroadcastSession$1();
            }
        }

        public final void onSourceAdded(BluetoothDevice bluetoothDevice, int i, int i2) {
            if (!SecBluetoothLeBroadcastAssistantSettings.m1128$$Nest$misSameGroupDevice(
                    SecBluetoothLeBroadcastAssistantSettings.this, bluetoothDevice)) {
                Log.w(
                        "SecBluetoothLeBroadcastAssistantSettings",
                        "onSourceAdded : recv unexpected callback for " + bluetoothDevice);
                return;
            }
            SecBluetoothLeBroadcastAssistantSettings secBluetoothLeBroadcastAssistantSettings =
                    SecBluetoothLeBroadcastAssistantSettings.this;
            secBluetoothLeBroadcastAssistantSettings.mSourceId = i;
            SecBluetoothLeBroadcastAssistantSourceItem secBluetoothLeBroadcastAssistantSourceItem =
                    secBluetoothLeBroadcastAssistantSettings.mSelectedSourceItem;
            if (secBluetoothLeBroadcastAssistantSourceItem == null) {
                Log.w(
                        "SecBluetoothLeBroadcastAssistantSettings",
                        "onSourceAdded : mSelectedSourceItem == null");
                return;
            }
            final BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata =
                    secBluetoothLeBroadcastAssistantSourceItem.mMetadata;
            if (bluetoothLeBroadcastMetadata == null) {
                Log.w(
                        "SecBluetoothLeBroadcastAssistantSettings",
                        "onSourceAdded : selectedSourceItemMetadata is null");
                return;
            }
            final int i3 = secBluetoothLeBroadcastAssistantSourceItem.mRssi;
            Log.d(
                    "SecBluetoothLeBroadcastAssistantSettings",
                    "onSourceAdded : " + i + " for " + bluetoothDevice);
            SecBluetoothLeBroadcastAssistantSettings.this.runOnUiThread(
                    new Runnable() { // from class:
                                     // com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastAssistantSettings$1$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            SecBluetoothLeBroadcastAssistantSettings.AnonymousClass1
                                    anonymousClass1 =
                                            SecBluetoothLeBroadcastAssistantSettings.AnonymousClass1
                                                    .this;
                            SecBluetoothLeBroadcastAssistantSettings
                                    .m1129$$Nest$mupdateListItemFromBroadcastMetadata(
                                            SecBluetoothLeBroadcastAssistantSettings.this,
                                            bluetoothLeBroadcastMetadata,
                                            i3);
                        }
                    });
        }

        public final void onSourceFound(BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
            Log.d(
                    "SecBluetoothLeBroadcastAssistantSettings",
                    "onSourceFound : "
                            + bluetoothLeBroadcastMetadata.getBroadcastId()
                            + " rssi "
                            + bluetoothLeBroadcastMetadata.getRssi());
            SecBluetoothLeBroadcastAssistantSettings.this.runOnUiThread(
                    new SecBluetoothLeBroadcastAssistantSettings$1$$ExternalSyntheticLambda3(
                            this, bluetoothLeBroadcastMetadata, 0));
        }

        public final void onSourceLost(final int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "onSourceLost : ", "SecBluetoothLeBroadcastAssistantSettings");
            SecBluetoothLeBroadcastAssistantSettings.this.runOnUiThread(
                    new Runnable() { // from class:
                                     // com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastAssistantSettings$1$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            SecBluetoothLeBroadcastAssistantSettings.AnonymousClass1
                                    anonymousClass1 =
                                            SecBluetoothLeBroadcastAssistantSettings.AnonymousClass1
                                                    .this;
                            int i2 = i;
                            SecBluetoothLeBroadcastAssistantSettings
                                    secBluetoothLeBroadcastAssistantSettings =
                                            SecBluetoothLeBroadcastAssistantSettings.this;
                            int i3 = SecBluetoothLeBroadcastAssistantSettings.$r8$clinit;
                            List<SecBluetoothLeBroadcastAssistantSourceItem>
                                    bluetoothBroadcastSourceItem =
                                            secBluetoothLeBroadcastAssistantSettings
                                                    .getBluetoothBroadcastSourceItem(i2);
                            boolean z = false;
                            if (!bluetoothBroadcastSourceItem.isEmpty()) {
                                boolean z2 = false;
                                for (SecBluetoothLeBroadcastAssistantSourceItem
                                        secBluetoothLeBroadcastAssistantSourceItem :
                                                bluetoothBroadcastSourceItem) {
                                    if (secBluetoothLeBroadcastAssistantSettings.mSourceItems
                                            .contains(secBluetoothLeBroadcastAssistantSourceItem)) {
                                        int indexOf =
                                                secBluetoothLeBroadcastAssistantSettings
                                                        .mSourceItems.indexOf(
                                                        secBluetoothLeBroadcastAssistantSourceItem);
                                        secBluetoothLeBroadcastAssistantSettings.mSourceItems
                                                .remove(secBluetoothLeBroadcastAssistantSourceItem);
                                        secBluetoothLeBroadcastAssistantSettings.mSourceListAdapter
                                                .notifyItemRemoved(indexOf);
                                        secBluetoothLeBroadcastAssistantSettings.mSourceListAdapter
                                                .notifyItemRangeRemoved(
                                                        indexOf,
                                                        secBluetoothLeBroadcastAssistantSettings
                                                                        .mSourceItems.size()
                                                                - indexOf);
                                        Preference$$ExternalSyntheticOutline0.m(
                                                new StringBuilder("removeListItemById - bid "),
                                                i2,
                                                "SecBluetoothLeBroadcastAssistantSettings");
                                        z2 = true;
                                    }
                                    if (secBluetoothLeBroadcastAssistantSourceItem.equals(
                                            secBluetoothLeBroadcastAssistantSettings
                                                    .mSelectedSourceItem)) {
                                        Log.d(
                                                "SecBluetoothLeBroadcastAssistantSettings",
                                                "removeListItemById : metadata set null");
                                        secBluetoothLeBroadcastAssistantSourceItem.updateMetadata(
                                                null, false);
                                    }
                                    if (secBluetoothLeBroadcastAssistantSettings.mHandler
                                            .hasEqualMessages(
                                                    1,
                                                    secBluetoothLeBroadcastAssistantSourceItem)) {
                                        AlertDialog alertDialog =
                                                secBluetoothLeBroadcastAssistantSettings
                                                        .mBroadcastCodeDialog;
                                        if (alertDialog != null && alertDialog.isShowing()) {
                                            secBluetoothLeBroadcastAssistantSettings
                                                    .mBroadcastCodeDialog.dismiss();
                                        }
                                        secBluetoothLeBroadcastAssistantSettings
                                                .clearCodeInputDialogTimeout();
                                    }
                                }
                                z = z2;
                            }
                            if (z) {
                                secBluetoothLeBroadcastAssistantSettings.updateActivityUi();
                            }
                        }
                    });
        }

        public final void onSourceModified(BluetoothDevice bluetoothDevice, int i, int i2) {
            if (SecBluetoothLeBroadcastAssistantSettings.m1128$$Nest$misSameGroupDevice(
                    SecBluetoothLeBroadcastAssistantSettings.this, bluetoothDevice)) {
                return;
            }
            Log.w(
                    "SecBluetoothLeBroadcastAssistantSettings",
                    "onSourceModified : recv unexpected callback for " + bluetoothDevice);
        }

        public final void onSourceModifyFailed(BluetoothDevice bluetoothDevice, int i, int i2) {
            if (SecBluetoothLeBroadcastAssistantSettings.m1128$$Nest$misSameGroupDevice(
                    SecBluetoothLeBroadcastAssistantSettings.this, bluetoothDevice)) {
                return;
            }
            Log.w(
                    "SecBluetoothLeBroadcastAssistantSettings",
                    "onSourceModifyFailed : recv unexpected callback for " + bluetoothDevice);
        }

        public final void onSourceRemoveFailed(BluetoothDevice bluetoothDevice, int i, int i2) {
            if (!SecBluetoothLeBroadcastAssistantSettings.m1128$$Nest$misSameGroupDevice(
                    SecBluetoothLeBroadcastAssistantSettings.this, bluetoothDevice)) {
                Log.w(
                        "SecBluetoothLeBroadcastAssistantSettings",
                        "onSourceRemoveFailed : recv unexpected callback for " + bluetoothDevice);
                return;
            }
            Log.d(
                    "SecBluetoothLeBroadcastAssistantSettings",
                    "onSourceRemoveFailed : " + i + " for " + bluetoothDevice);
            SecBluetoothLeBroadcastAssistantSettings secBluetoothLeBroadcastAssistantSettings =
                    SecBluetoothLeBroadcastAssistantSettings.this;
            secBluetoothLeBroadcastAssistantSettings.mIsRemovingSource = false;
            secBluetoothLeBroadcastAssistantSettings.runOnUiThread(
                    new SecBluetoothLeBroadcastAssistantSettings$1$$ExternalSyntheticLambda0(
                            this, 0));
        }

        public final void onSourceRemoved(BluetoothDevice bluetoothDevice, int i, int i2) {
            if (!SecBluetoothLeBroadcastAssistantSettings.m1128$$Nest$misSameGroupDevice(
                    SecBluetoothLeBroadcastAssistantSettings.this, bluetoothDevice)) {
                Log.w(
                        "SecBluetoothLeBroadcastAssistantSettings",
                        "onSourceRemoved : recv unexpected callback for " + bluetoothDevice);
            } else {
                Log.d(
                        "SecBluetoothLeBroadcastAssistantSettings",
                        "onSourceRemoved : " + i + " for " + bluetoothDevice);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastAssistantSettings$2, reason: invalid class name */
    public final class AnonymousClass2 implements BluetoothLeBroadcastAssistant.AssistantCallback {
        public AnonymousClass2() {}

        public final void onSourceInfoChanged(
                BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
            Log.d(
                    "SecBluetoothLeBroadcastAssistantSettings",
                    "onSourceInfoChanged : " + bluetoothLeBroadcastMetadata.getBroadcastId());
            SecBluetoothLeBroadcastAssistantSettings.this.runOnUiThread(
                    new SecBluetoothLeBroadcastAssistantSettings$1$$ExternalSyntheticLambda3(
                            this, bluetoothLeBroadcastMetadata, 1));
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastAssistantSettings$8, reason: invalid class name */
    public final class AnonymousClass8 {
        public AnonymousClass8() {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BroadcastItemDecoration extends RecyclerView.ItemDecoration {
        public BroadcastItemDecoration() {}

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void getItemOffsets(
                Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            super.getItemOffsets(rect, view, recyclerView, state);
            rect.top = 0;
            rect.bottom =
                    (int)
                            TypedValue.applyDimension(
                                    1,
                                    20.0f,
                                    SecBluetoothLeBroadcastAssistantSettings.this
                                            .getResources()
                                            .getDisplayMetrics());
            rect.left = 0;
            rect.right = 0;
        }
    }

    /* renamed from: -$$Nest$misSameGroupDevice, reason: not valid java name */
    public static boolean m1128$$Nest$misSameGroupDevice(
            SecBluetoothLeBroadcastAssistantSettings secBluetoothLeBroadcastAssistantSettings,
            final BluetoothDevice bluetoothDevice) {
        return secBluetoothLeBroadcastAssistantSettings.mMemberDevices.stream()
                .map(new AudioStreamsHelper$$ExternalSyntheticLambda3(1))
                .anyMatch(
                        new Predicate() { // from class:
                                          // com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastAssistantSettings$$ExternalSyntheticLambda4
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                BluetoothDevice bluetoothDevice2 = bluetoothDevice;
                                int i = SecBluetoothLeBroadcastAssistantSettings.$r8$clinit;
                                return ((BluetoothDevice) obj).equals(bluetoothDevice2);
                            }
                        });
    }

    /* renamed from: -$$Nest$mupdateListItemFromBroadcastMetadata, reason: not valid java name */
    public static void m1129$$Nest$mupdateListItemFromBroadcastMetadata(
            SecBluetoothLeBroadcastAssistantSettings secBluetoothLeBroadcastAssistantSettings,
            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata,
            int i) {
        secBluetoothLeBroadcastAssistantSettings.getClass();
        Log.d(
                "SecBluetoothLeBroadcastAssistantSettings",
                "updateListItemFromBroadcastMetadata : "
                        + bluetoothLeBroadcastMetadata.getBroadcastId());
        List<SecBluetoothLeBroadcastAssistantSourceItem> bluetoothBroadcastSourceItem =
                secBluetoothLeBroadcastAssistantSettings.getBluetoothBroadcastSourceItem(
                        bluetoothLeBroadcastMetadata.getBroadcastId());
        if (bluetoothBroadcastSourceItem.isEmpty()) {
            Log.i(
                    "SecBluetoothLeBroadcastAssistantSettings",
                    "createBluetoothBroadcastSourceItemList by metadata "
                            + bluetoothLeBroadcastMetadata.getBroadcastId());
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < bluetoothLeBroadcastMetadata.getSubgroups().size(); i2++) {
                arrayList.add(
                        new SecBluetoothLeBroadcastAssistantSourceItem(
                                secBluetoothLeBroadcastAssistantSettings.mContext,
                                bluetoothLeBroadcastMetadata.getBroadcastId(),
                                i2));
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                SecBluetoothLeBroadcastAssistantSourceItem
                        secBluetoothLeBroadcastAssistantSourceItem =
                                (SecBluetoothLeBroadcastAssistantSourceItem) it.next();
                secBluetoothLeBroadcastAssistantSourceItem.updateMetadata(
                        bluetoothLeBroadcastMetadata, false);
                secBluetoothLeBroadcastAssistantSourceItem.mRssi = 0 - i;
                Iterator it2 = secBluetoothLeBroadcastAssistantSettings.mSourceItems.iterator();
                int i3 = 0;
                while (it2.hasNext()) {
                    if (secBluetoothLeBroadcastAssistantSourceItem.mRssi
                            < ((SecBluetoothLeBroadcastAssistantSourceItem) it2.next()).mRssi) {
                        break;
                    } else {
                        i3++;
                    }
                }
                secBluetoothLeBroadcastAssistantSettings.mSourceItems.add(
                        i3, secBluetoothLeBroadcastAssistantSourceItem);
                secBluetoothLeBroadcastAssistantSettings.mSourceListAdapter.notifyItemInserted(i3);
            }
        } else {
            for (SecBluetoothLeBroadcastAssistantSourceItem
                    secBluetoothLeBroadcastAssistantSourceItem2 : bluetoothBroadcastSourceItem) {
                secBluetoothLeBroadcastAssistantSourceItem2.updateMetadata(
                        bluetoothLeBroadcastMetadata, false);
                if (secBluetoothLeBroadcastAssistantSourceItem2.isConnected()) {
                    secBluetoothLeBroadcastAssistantSettings.updateConnectedCardUi(
                            secBluetoothLeBroadcastAssistantSourceItem2);
                } else if (secBluetoothLeBroadcastAssistantSettings.mSourceItems.contains(
                        secBluetoothLeBroadcastAssistantSourceItem2)) {
                    secBluetoothLeBroadcastAssistantSettings.mSourceListAdapter.notifyItemChanged(
                            secBluetoothLeBroadcastAssistantSettings.mSourceItems.indexOf(
                                    secBluetoothLeBroadcastAssistantSourceItem2));
                }
            }
        }
        secBluetoothLeBroadcastAssistantSettings.updateActivityUi();
    }

    public static BluetoothLeBroadcastMetadata updateMetadataBisChannel(
            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata, int i, boolean z) {
        int size = bluetoothLeBroadcastMetadata.getSubgroups().size();
        Log.d(
                "SecBluetoothLeBroadcastAssistantSettings",
                "updateMetadataBisChannel : size " + size + ", selected " + i);
        BluetoothLeBroadcastMetadata.Builder builder =
                new BluetoothLeBroadcastMetadata.Builder(bluetoothLeBroadcastMetadata);
        ArrayList arrayList = new ArrayList(bluetoothLeBroadcastMetadata.getSubgroups());
        builder.clearSubgroup();
        for (int i2 = 0; i2 < size; i2++) {
            BluetoothLeBroadcastSubgroup.Builder builder2 =
                    new BluetoothLeBroadcastSubgroup.Builder(
                            (BluetoothLeBroadcastSubgroup) arrayList.get(i2));
            if (i2 != i) {
                builder.addSubgroup(builder2.build());
            } else {
                ArrayList arrayList2 =
                        new ArrayList(
                                ((BluetoothLeBroadcastSubgroup) arrayList.get(i2)).getChannels());
                builder2.clearChannel();
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    BluetoothLeBroadcastChannel.Builder builder3 =
                            new BluetoothLeBroadcastChannel.Builder(
                                    (BluetoothLeBroadcastChannel) it.next());
                    builder3.setSelected(z);
                    builder2.addChannel(builder3.build());
                }
                builder.addSubgroup(builder2.build());
            }
        }
        return builder.build();
    }

    public final void addSource(
            SecBluetoothLeBroadcastAssistantSourceItem secBluetoothLeBroadcastAssistantSourceItem) {
        Log.d("SecBluetoothLeBroadcastAssistantSettings", "addSource by found source");
        if (this.mLeBroadcastAssistant == null || this.mCachedDevice == null) {
            Log.w(
                    "SecBluetoothLeBroadcastAssistantSettings",
                    "addSource: LeBroadcastAssistant or CachedDevice is null!");
            return;
        }
        BluetoothLeBroadcastMetadata updateMetadataBisChannel =
                updateMetadataBisChannel(
                        secBluetoothLeBroadcastAssistantSourceItem.mMetadata,
                        secBluetoothLeBroadcastAssistantSourceItem.mSubgroupIndex,
                        true);
        secBluetoothLeBroadcastAssistantSourceItem.updateMetadata(
                updateMetadataBisChannel, secBluetoothLeBroadcastAssistantSourceItem.isConnected());
        if (this.mPendingSourceItem == null) {
            this.mSelectedSourceItem = secBluetoothLeBroadcastAssistantSourceItem;
        }
        clearOperationTimeout$1();
        AnonymousClass7 anonymousClass7 = this.mHandler;
        Message obtainMessage = anonymousClass7.obtainMessage(2);
        obtainMessage.arg1 = 1;
        obtainMessage.arg2 = EnterpriseContainerConstants.SYSTEM_SIGNED_APP;
        anonymousClass7.sendMessageDelayed(obtainMessage, 10000L);
        if (this.mCachedDevice.getProfileConnectionState(this.mLeBroadcastAssistant) != 2) {
            Iterator it = ((HashSet) this.mCachedDevice.mMemberDevices).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) it.next();
                if (cachedBluetoothDevice.getProfileConnectionState(this.mLeBroadcastAssistant)
                        == 2) {
                    this.mLeBroadcastAssistant.addSource(
                            cachedBluetoothDevice.mDevice, updateMetadataBisChannel, true);
                    break;
                }
            }
        } else {
            this.mLeBroadcastAssistant.addSource(
                    this.mCachedDevice.mDevice, updateMetadataBisChannel, true);
        }
        secBluetoothLeBroadcastAssistantSourceItem.updateMetadata(
                new BluetoothLeBroadcastMetadata.Builder(
                                secBluetoothLeBroadcastAssistantSourceItem.mMetadata)
                        .setBroadcastCode((byte[]) null)
                        .build(),
                false);
    }

    public final void clearCodeInputDialogTimeout() {
        AnonymousClass7 anonymousClass7 = this.mHandler;
        if (anonymousClass7.hasMessages(1)) {
            anonymousClass7.removeMessages(1);
            this.mBroadcastCodeDialogItem = null;
        }
    }

    public final void clearListItem() {
        int size = this.mSourceItems.size();
        if (size > 0) {
            this.mSourceItems.clear();
            this.mSourceListAdapter.notifyItemRangeRemoved(0, size);
        }
    }

    public final void clearOperationTimeout$1() {
        AnonymousClass7 anonymousClass7 = this.mHandler;
        if (anonymousClass7.hasMessages(2)) {
            anonymousClass7.removeMessages(2);
        }
    }

    public final void doInitUi() {
        this.mDescriptionLayout.setVisibility(0);
        this.mConnectedCardLayout.setVisibility(8);
        this.mAvailableBroadcastsLayout.setVisibility(8);
        this.mNoBroadcastsLayout.setVisibility(0);
        this.mBroadcastsListView.setVisibility(8);
    }

    public final void finishActivity$1() {
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        if (cachedBluetoothDevice.mBondState == 10) {
            Log.i("SecBluetoothLeBroadcastAssistantSettings", "finishActivity : unbonded");
            finish();
            return;
        }
        if (cachedBluetoothDevice.getProfileConnectionState(this.mLeBroadcastAssistant) == 2) {
            Log.d(
                    "SecBluetoothLeBroadcastAssistantSettings",
                    "finishActivity : skip by cached device connected, "
                            + this.mCachedDevice.mDevice);
            return;
        }
        Iterator it = ((HashSet) this.mCachedDevice.mMemberDevices).iterator();
        while (it.hasNext()) {
            CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) it.next();
            if (cachedBluetoothDevice2.getProfileConnectionState(this.mLeBroadcastAssistant) == 2) {
                Log.d(
                        "SecBluetoothLeBroadcastAssistantSettings",
                        "finishActivity : member device connected, "
                                + cachedBluetoothDevice2.mDevice);
                return;
            }
        }
        Log.i(
                "SecBluetoothLeBroadcastAssistantSettings",
                "finishActivity : all of BASS devices are disconnected");
        clearOperationTimeout$1();
        finish();
    }

    public final List getBluetoothBroadcastSourceItem(final int i) {
        ArrayList arrayList = new ArrayList();
        SecBluetoothLeBroadcastAssistantSourceItem secBluetoothLeBroadcastAssistantSourceItem =
                this.mSelectedSourceItem;
        if (secBluetoothLeBroadcastAssistantSourceItem == null
                || secBluetoothLeBroadcastAssistantSourceItem.mBroadcastId != i) {
            return (List)
                    this.mSourceItems.stream()
                            .filter(
                                    new Predicate() { // from class:
                                                      // com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastAssistantSettings$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            int i2 = i;
                                            int i3 =
                                                    SecBluetoothLeBroadcastAssistantSettings
                                                            .$r8$clinit;
                                            return ((SecBluetoothLeBroadcastAssistantSourceItem)
                                                                    obj)
                                                            .mBroadcastId
                                                    == i2;
                                        }
                                    })
                            .collect(Collectors.toList());
        }
        arrayList.add(secBluetoothLeBroadcastAssistantSourceItem);
        return arrayList;
    }

    public final void launchBroadcastCodeDialog(
            final SecBluetoothLeBroadcastAssistantSourceItem
                    secBluetoothLeBroadcastAssistantSourceItem,
            boolean z) {
        View inflate =
                LayoutInflater.from(this)
                        .inflate(
                                R.layout.sec_bluetooth_broadcast_assistant_password_dialog,
                                (ViewGroup) null);
        CheckableImageButton checkableImageButton =
                (CheckableImageButton)
                        ((TextInputLayout) inflate.findViewById(R.id.input_broadcast_code))
                                .findViewById(
                                        this.mContext
                                                .getResources()
                                                .getIdentifier(
                                                        "text_input_end_icon",
                                                        "id",
                                                        this.mContext.getPackageName()));
        this.mBroadcastCodeShowImageButton = checkableImageButton;
        checkableImageButton.setChecked(false);
        this.mBroadcastCodeShowImageButton.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastAssistantSettings$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        EditText editText;
                        EditText editText2;
                        SecBluetoothLeBroadcastAssistantSettings
                                secBluetoothLeBroadcastAssistantSettings =
                                        SecBluetoothLeBroadcastAssistantSettings.this;
                        CheckableImageButton checkableImageButton2 =
                                secBluetoothLeBroadcastAssistantSettings
                                        .mBroadcastCodeShowImageButton;
                        if (checkableImageButton2 == null
                                || (editText =
                                                secBluetoothLeBroadcastAssistantSettings
                                                        .mBroadcastCodeEditText)
                                        == null) {
                            Log.w(
                                    "SecBluetoothLeBroadcastAssistantSettings",
                                    "setClickListenerOnBroadcastCodeShowButton : view is null");
                            return;
                        }
                        if (checkableImageButton2.checked) {
                            editText.setTransformationMethod(
                                    PasswordTransformationMethod.getInstance());
                            editText2 =
                                    secBluetoothLeBroadcastAssistantSettings.mBroadcastCodeEditText;
                        } else {
                            editText.setTransformationMethod(null);
                            editText2 =
                                    secBluetoothLeBroadcastAssistantSettings.mBroadcastCodeEditText;
                        }
                        SeslColorPicker$16$$ExternalSyntheticOutline0.m(editText2);
                    }
                });
        EditText editText = (EditText) inflate.findViewById(R.id.broadcast_edit_text);
        this.mBroadcastCodeEditText = editText;
        editText.setOnEditorActionListener(
                new TextView
                        .OnEditorActionListener() { // from class:
                                                    // com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastAssistantSettings.4
                    @Override // android.widget.TextView.OnEditorActionListener
                    public final boolean onEditorAction(
                            TextView textView, int i, KeyEvent keyEvent) {
                        if (i == 6) {
                            Log.d("SecBluetoothLeBroadcastAssistantSettings", "ACTION_DONE");
                            SecBluetoothLeBroadcastAssistantSettings
                                    secBluetoothLeBroadcastAssistantSettings =
                                            SecBluetoothLeBroadcastAssistantSettings.this;
                            SecBluetoothLeBroadcastAssistantSourceItem
                                    secBluetoothLeBroadcastAssistantSourceItem2 =
                                            secBluetoothLeBroadcastAssistantSourceItem;
                            int i2 = SecBluetoothLeBroadcastAssistantSettings.$r8$clinit;
                            secBluetoothLeBroadcastAssistantSettings.processCodeDialogButtonClick(
                                    secBluetoothLeBroadcastAssistantSourceItem2, true);
                            AlertDialog alertDialog =
                                    SecBluetoothLeBroadcastAssistantSettings.this
                                            .mBroadcastCodeDialog;
                            if (alertDialog != null && alertDialog.isShowing()) {
                                SecBluetoothLeBroadcastAssistantSettings.this.mBroadcastCodeDialog
                                        .dismiss();
                            }
                        }
                        return true;
                    }
                });
        this.mBroadcastCodeEditText.addTextChangedListener(
                new TextWatcher() { // from class:
                                    // com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastAssistantSettings.5
                    @Override // android.text.TextWatcher
                    public final void afterTextChanged(Editable editable) {
                        if (hasMessages(1)) {
                            removeMessages(1);
                            Message obtainMessage = obtainMessage(1);
                            SecBluetoothLeBroadcastAssistantSourceItem
                                    secBluetoothLeBroadcastAssistantSourceItem2 =
                                            SecBluetoothLeBroadcastAssistantSettings.this
                                                    .mBroadcastCodeDialogItem;
                            obtainMessage.obj = secBluetoothLeBroadcastAssistantSourceItem2;
                            if (secBluetoothLeBroadcastAssistantSourceItem2 != null) {
                                Preference$$ExternalSyntheticOutline0.m(
                                        new StringBuilder(
                                                "afterTextChanged, refresh timeout. bid "),
                                        SecBluetoothLeBroadcastAssistantSettings.this
                                                .mBroadcastCodeDialogItem
                                                .mBroadcastId,
                                        "SecBluetoothLeBroadcastAssistantSettings");
                            } else {
                                Log.d(
                                        "SecBluetoothLeBroadcastAssistantSettings",
                                        "afterTextChanged, refresh timeout. but item is null");
                            }
                            sendMessageDelayed(obtainMessage, 30000L);
                        }
                    }

                    @Override // android.text.TextWatcher
                    public final void beforeTextChanged(
                            CharSequence charSequence, int i, int i2, int i3) {}

                    @Override // android.text.TextWatcher
                    public final void onTextChanged(
                            CharSequence charSequence, int i, int i2, int i3) {}
                });
        final int i = 0;
        AlertDialog.Builder neutralButton =
                new AlertDialog.Builder(this)
                        .setTitle(R.string.sec_bluetooth_find_broadcast_password_title)
                        .setView(inflate)
                        .setNeutralButton(
                                R.string.cancel,
                                new DialogInterface.OnClickListener(
                                        this) { // from class:
                                                // com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastAssistantSettings$$ExternalSyntheticLambda2
                                    public final /* synthetic */
                                    SecBluetoothLeBroadcastAssistantSettings f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i2) {
                                        switch (i) {
                                            case 0:
                                                SecBluetoothLeBroadcastAssistantSettings
                                                        secBluetoothLeBroadcastAssistantSettings =
                                                                this.f$0;
                                                SecBluetoothLeBroadcastAssistantSourceItem
                                                        secBluetoothLeBroadcastAssistantSourceItem2 =
                                                                secBluetoothLeBroadcastAssistantSourceItem;
                                                SALogging.insertSALog(
                                                        secBluetoothLeBroadcastAssistantSettings
                                                                .mScreenId,
                                                        secBluetoothLeBroadcastAssistantSettings
                                                                .getResources()
                                                                .getString(
                                                                        R.string
                                                                                .event_bluetooth_bass_password_dialog_negative));
                                                secBluetoothLeBroadcastAssistantSettings
                                                        .processCodeDialogButtonClick(
                                                                secBluetoothLeBroadcastAssistantSourceItem2,
                                                                false);
                                                break;
                                            default:
                                                SecBluetoothLeBroadcastAssistantSettings
                                                        secBluetoothLeBroadcastAssistantSettings2 =
                                                                this.f$0;
                                                SecBluetoothLeBroadcastAssistantSourceItem
                                                        secBluetoothLeBroadcastAssistantSourceItem3 =
                                                                secBluetoothLeBroadcastAssistantSourceItem;
                                                SALogging.insertSALog(
                                                        secBluetoothLeBroadcastAssistantSettings2
                                                                .mScreenId,
                                                        secBluetoothLeBroadcastAssistantSettings2
                                                                .getResources()
                                                                .getString(
                                                                        R.string
                                                                                .event_bluetooth_bass_password_dialog_positive));
                                                secBluetoothLeBroadcastAssistantSettings2
                                                        .processCodeDialogButtonClick(
                                                                secBluetoothLeBroadcastAssistantSourceItem3,
                                                                true);
                                                break;
                                        }
                                    }
                                });
        final int i2 = 1;
        AlertDialog create =
                neutralButton
                        .setPositiveButton(
                                R.string.sec_bluetooth_connect,
                                new DialogInterface.OnClickListener(
                                        this) { // from class:
                                                // com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastAssistantSettings$$ExternalSyntheticLambda2
                                    public final /* synthetic */
                                    SecBluetoothLeBroadcastAssistantSettings f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i22) {
                                        switch (i2) {
                                            case 0:
                                                SecBluetoothLeBroadcastAssistantSettings
                                                        secBluetoothLeBroadcastAssistantSettings =
                                                                this.f$0;
                                                SecBluetoothLeBroadcastAssistantSourceItem
                                                        secBluetoothLeBroadcastAssistantSourceItem2 =
                                                                secBluetoothLeBroadcastAssistantSourceItem;
                                                SALogging.insertSALog(
                                                        secBluetoothLeBroadcastAssistantSettings
                                                                .mScreenId,
                                                        secBluetoothLeBroadcastAssistantSettings
                                                                .getResources()
                                                                .getString(
                                                                        R.string
                                                                                .event_bluetooth_bass_password_dialog_negative));
                                                secBluetoothLeBroadcastAssistantSettings
                                                        .processCodeDialogButtonClick(
                                                                secBluetoothLeBroadcastAssistantSourceItem2,
                                                                false);
                                                break;
                                            default:
                                                SecBluetoothLeBroadcastAssistantSettings
                                                        secBluetoothLeBroadcastAssistantSettings2 =
                                                                this.f$0;
                                                SecBluetoothLeBroadcastAssistantSourceItem
                                                        secBluetoothLeBroadcastAssistantSourceItem3 =
                                                                secBluetoothLeBroadcastAssistantSourceItem;
                                                SALogging.insertSALog(
                                                        secBluetoothLeBroadcastAssistantSettings2
                                                                .mScreenId,
                                                        secBluetoothLeBroadcastAssistantSettings2
                                                                .getResources()
                                                                .getString(
                                                                        R.string
                                                                                .event_bluetooth_bass_password_dialog_positive));
                                                secBluetoothLeBroadcastAssistantSettings2
                                                        .processCodeDialogButtonClick(
                                                                secBluetoothLeBroadcastAssistantSourceItem3,
                                                                true);
                                                break;
                                        }
                                    }
                                })
                        .setOnCancelListener(
                                new DialogInterface
                                        .OnCancelListener() { // from class:
                                                              // com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastAssistantSettings.6
                                    @Override // android.content.DialogInterface.OnCancelListener
                                    public final void onCancel(DialogInterface dialogInterface) {
                                        Log.d(
                                                "SecBluetoothLeBroadcastAssistantSettings",
                                                "onCancel");
                                        SecBluetoothLeBroadcastAssistantSettings
                                                secBluetoothLeBroadcastAssistantSettings =
                                                        SecBluetoothLeBroadcastAssistantSettings
                                                                .this;
                                        SecBluetoothLeBroadcastAssistantSourceItem
                                                secBluetoothLeBroadcastAssistantSourceItem2 =
                                                        secBluetoothLeBroadcastAssistantSourceItem;
                                        int i3 =
                                                SecBluetoothLeBroadcastAssistantSettings.$r8$clinit;
                                        secBluetoothLeBroadcastAssistantSettings
                                                .processCodeDialogButtonClick(
                                                        secBluetoothLeBroadcastAssistantSourceItem2,
                                                        false);
                                    }
                                })
                        .create();
        this.mBroadcastCodeDialog = create;
        create.getWindow().setType(2009);
        this.mBroadcastCodeDialog.show();
        clearCodeInputDialogTimeout();
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("set code input timeout, bid "),
                secBluetoothLeBroadcastAssistantSourceItem.mBroadcastId,
                "SecBluetoothLeBroadcastAssistantSettings");
        AnonymousClass7 anonymousClass7 = this.mHandler;
        Message obtainMessage = anonymousClass7.obtainMessage(1);
        obtainMessage.obj = secBluetoothLeBroadcastAssistantSourceItem;
        anonymousClass7.sendMessageDelayed(obtainMessage, 30000L);
        this.mBroadcastCodeDialogItem = secBluetoothLeBroadcastAssistantSourceItem;
        this.mBroadcastCodeEditText.requestFocus();
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.mContext.getSystemService("input_method");
        if (inputMethodManager != null) {
            this.mBroadcastCodeDialog.getWindow().setSoftInputMode(5);
            inputMethodManager.showSoftInput(this.mBroadcastCodeEditText, 1);
        }
        TextView textView = (TextView) inflate.requireViewById(R.id.assistant_code_error_text);
        if (z) {
            ((TextView) inflate.requireViewById(R.id.password_name_text))
                    .setTextColor(
                            getResources()
                                    .getColor(
                                            R.color
                                                    .sec_bluetooth_assistant_code_error_message_color));
            this.mBroadcastCodeEditText.setBackgroundTintList(
                    getResources()
                            .getColorStateList(
                                    R.color.sec_bluetooth_assistant_code_error_message_color));
            textView.setVisibility(0);
        } else {
            textView.setVisibility(8);
        }
        clearOperationTimeout$1();
    }

    public final void leaveBroadcastSession$1() {
        Log.d("SecBluetoothLeBroadcastAssistantSettings", "leaveBroadcastSession");
        if (this.mLeBroadcastAssistant == null || this.mCachedDevice == null) {
            Log.w(
                    "SecBluetoothLeBroadcastAssistantSettings",
                    "leaveBroadcastSession : LeBroadcastAssistant or CachedDevice is null");
            return;
        }
        this.mIsRemovingSource = true;
        clearOperationTimeout$1();
        AnonymousClass7 anonymousClass7 = this.mHandler;
        Message obtainMessage = anonymousClass7.obtainMessage(2);
        obtainMessage.arg1 = 2;
        obtainMessage.arg2 = EnterpriseContainerConstants.SYSTEM_SIGNED_APP;
        anonymousClass7.sendMessageDelayed(obtainMessage, 10000L);
        if (this.mCachedDevice.getProfileConnectionState(this.mLeBroadcastAssistant) == 2) {
            this.mLeBroadcastAssistant.removeSource(this.mCachedDevice.mDevice, this.mSourceId);
            return;
        }
        Iterator it = ((HashSet) this.mCachedDevice.mMemberDevices).iterator();
        while (it.hasNext()) {
            CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) it.next();
            if (cachedBluetoothDevice.getProfileConnectionState(this.mLeBroadcastAssistant) == 2) {
                this.mLeBroadcastAssistant.removeSource(
                        cachedBluetoothDevice.mDevice, this.mSourceId);
                return;
            }
        }
    }

    public void onClickLeaveButton(View view) {
        SALogging.insertSALog(
                this.mScreenId,
                getResources().getString(R.string.event_bluetooth_bass_leave_source_button));
        leaveBroadcastSession$1();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        CachedBluetoothDevice findDevice;
        super.onCreate(bundle);
        setContentView(R.layout.sec_bluetooth_broadcast_assistant_settings_layout);
        this.mContext = getApplicationContext();
        String stringExtra = getIntent().getStringExtra("device_address");
        this.mDeviceAddress = stringExtra;
        if (stringExtra == null) {
            Log.w("SecBluetoothLeBroadcastAssistantSettings", "onCreate : DeviceAddress is null");
            finish();
            return;
        }
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(this.mContext, Utils.mOnInitCallback);
        this.mManager = localBluetoothManager;
        String str = this.mDeviceAddress;
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant = null;
        if (localBluetoothManager == null) {
            Log.w(
                    "SecBluetoothLeBroadcastAssistantSettings",
                    "getCachedDevice: LocalBluetoothManager is null!");
            findDevice = null;
        } else {
            findDevice =
                    this.mManager.mCachedDeviceManager.findDevice(
                            localBluetoothManager.mLocalAdapter.mAdapter.getRemoteDevice(str));
        }
        this.mCachedDevice = findDevice;
        LocalBluetoothManager localBluetoothManager2 = this.mManager;
        if (localBluetoothManager2 == null) {
            Log.w(
                    "SecBluetoothLeBroadcastAssistantSettings",
                    "getLeBroadcastAssistant: LocalBluetoothManager is null!");
        } else {
            LocalBluetoothProfileManager localBluetoothProfileManager =
                    localBluetoothManager2.mProfileManager;
            if (localBluetoothProfileManager == null) {
                Log.w(
                        "SecBluetoothLeBroadcastAssistantSettings",
                        "getLeBroadcastAssistant: LocalBluetoothProfileManager is null!");
            } else {
                localBluetoothLeBroadcastAssistant =
                        localBluetoothProfileManager.mLeAudioBroadcastAssistant;
            }
        }
        this.mLeBroadcastAssistant = localBluetoothLeBroadcastAssistant;
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mScreenId = getResources().getString(R.string.screen_find_a_broadcast_list);
        if (this.mCachedDevice == null || this.mLeBroadcastAssistant == null) {
            Log.w(
                    "SecBluetoothLeBroadcastAssistantSettings",
                    "onCreate : CachedDevice or LeBroadcastAssistant is null");
            finish();
            return;
        }
        Log.d(
                "SecBluetoothLeBroadcastAssistantSettings",
                "onCreate : " + this.mCachedDevice.mDevice);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        Iterator it = ((HashSet) this.mCachedDevice.mMemberDevices).iterator();
        while (it.hasNext()) {
            ((HashSet) this.mMemberDevices).add((CachedBluetoothDevice) it.next());
        }
        ((HashSet) this.mMemberDevices).add(this.mCachedDevice);
        setDefaultUiResources();
    }

    @Override // android.app.Activity
    public final boolean onCreateOptionsMenu(Menu menu) {
        MenuItem add = menu.add(0, 1, 0, (CharSequence) null);
        this.mProgressBarItem = add;
        add.setShowAsAction(2);
        this.mProgressBarItem.setActionView(R.layout.sec_bluetooth_broadcast_progressbar);
        this.mProgressBarItem.setEnabled(true);
        this.mProgressBarItem.setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onNewIntent(Intent intent) {
        CachedBluetoothDevice findDevice;
        super.onNewIntent(intent);
        String stringExtra = intent.getStringExtra("device_address");
        this.mDeviceAddress = stringExtra;
        LocalBluetoothManager localBluetoothManager = this.mManager;
        if (localBluetoothManager == null) {
            Log.w(
                    "SecBluetoothLeBroadcastAssistantSettings",
                    "getCachedDevice: LocalBluetoothManager is null!");
            findDevice = null;
        } else {
            findDevice =
                    this.mManager.mCachedDeviceManager.findDevice(
                            localBluetoothManager.mLocalAdapter.mAdapter.getRemoteDevice(
                                    stringExtra));
        }
        this.mCachedDevice = findDevice;
        if (findDevice == null) {
            Log.w("SecBluetoothLeBroadcastAssistantSettings", "onNewIntent : CachedDevice is null");
            finish();
            return;
        }
        Log.d(
                "SecBluetoothLeBroadcastAssistantSettings",
                "onNewIntent : " + this.mCachedDevice.mDevice);
        AlertDialog alertDialog = this.mBroadcastCodeDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mBroadcastCodeDialog.cancel();
        }
        ((HashSet) this.mMemberDevices).clear();
        Iterator it = ((HashSet) this.mCachedDevice.mMemberDevices).iterator();
        while (it.hasNext()) {
            ((HashSet) this.mMemberDevices).add((CachedBluetoothDevice) it.next());
        }
        ((HashSet) this.mMemberDevices).add(this.mCachedDevice);
        setDefaultUiResources();
        doInitUi();
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            SALogging.insertSALog(
                    this.mScreenId,
                    getResources().getString(R.string.event_bluetooth_setting_navigate_button));
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        BluetoothEventManager bluetoothEventManager;
        super.onPause();
        Log.d("SecBluetoothLeBroadcastAssistantSettings", "onPause");
        AlertDialog alertDialog = this.mBroadcastCodeDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            SecBluetoothLeBroadcastAssistantSourceItem secBluetoothLeBroadcastAssistantSourceItem =
                    this.mBroadcastCodeDialogItem;
            if (secBluetoothLeBroadcastAssistantSourceItem != null) {
                processCodeDialogButtonClick(secBluetoothLeBroadcastAssistantSourceItem, false);
            } else {
                this.mBroadcastCodeDialog.dismiss();
            }
        }
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                this.mLeBroadcastAssistant;
        if (localBluetoothLeBroadcastAssistant != null) {
            BluetoothDevice bluetoothDevice = this.mCachedDevice.mDevice;
            Log.d("LocalBluetoothLeBroadcastAssistant", "stopSearchingForSources()");
            if (localBluetoothLeBroadcastAssistant.mService == null) {
                Log.d(
                        "LocalBluetoothLeBroadcastAssistant",
                        "The BluetoothLeBroadcastAssistant is null");
            } else if (localBluetoothLeBroadcastAssistant.isSearchInProgress()) {
                localBluetoothLeBroadcastAssistant.mService.stopSearchingForSources(
                        bluetoothDevice);
            } else {
                Log.d("LocalBluetoothLeBroadcastAssistant", "a search has not been started");
            }
        }
        clearCodeInputDialogTimeout();
        clearOperationTimeout$1();
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant2 =
                this.mLeBroadcastAssistant;
        if (localBluetoothLeBroadcastAssistant2 != null) {
            localBluetoothLeBroadcastAssistant2.unregisterServiceCallBack(
                    this.mBroadcastAssistantCallback);
            LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant3 =
                    this.mLeBroadcastAssistant;
            AnonymousClass2 anonymousClass2 = this.mAssistantCallback;
            BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant =
                    localBluetoothLeBroadcastAssistant3.mService;
            if (bluetoothLeBroadcastAssistant == null) {
                Log.d("LocalBluetoothLeBroadcastAssistant", "The BluetoothLeBroadcast is null.");
            } else {
                try {
                    bluetoothLeBroadcastAssistant.unregisterAssistantCallback(anonymousClass2);
                } catch (Exception e) {
                    Log.d(
                            "LocalBluetoothLeBroadcastAssistant",
                            "unregisterAssistantCallBack : Failed to unregister assistant callback."
                                + " "
                                    + e);
                }
            }
        }
        LocalBluetoothManager localBluetoothManager = this.mManager;
        if (localBluetoothManager == null
                || (bluetoothEventManager = localBluetoothManager.mEventManager) == null) {
            return;
        }
        bluetoothEventManager.unregisterCallback(this.mEventManagerCallback);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        BluetoothEventManager bluetoothEventManager;
        super.onResume();
        Log.d("SecBluetoothLeBroadcastAssistantSettings", "onResume");
        finishActivity$1();
        this.mIsDecrypting = false;
        this.mIsRemovingSource = false;
        this.mIsRemovingByAddFailed = false;
        this.mSourceId = -1;
        ((ConcurrentHashMap) this.mActiveSinkMap).clear();
        this.mSelectedSourceItem = null;
        this.mPendingSourceItem = null;
        clearListItem();
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                this.mLeBroadcastAssistant;
        if (localBluetoothLeBroadcastAssistant != null) {
            localBluetoothLeBroadcastAssistant.registerServiceCallBack(
                    this.mExecutor, this.mBroadcastAssistantCallback);
            LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant2 =
                    this.mLeBroadcastAssistant;
            Executor executor = this.mExecutor;
            AnonymousClass2 anonymousClass2 = this.mAssistantCallback;
            BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant =
                    localBluetoothLeBroadcastAssistant2.mService;
            if (bluetoothLeBroadcastAssistant == null) {
                Log.d("LocalBluetoothLeBroadcastAssistant", "The BluetoothLeBroadcast is null.");
            } else {
                try {
                    bluetoothLeBroadcastAssistant.registerAssistantCallback(
                            executor, anonymousClass2);
                } catch (Exception e) {
                    Log.d(
                            "LocalBluetoothLeBroadcastAssistant",
                            "registerAssistantCallBack : Failed to register assistant callback. "
                                    + e);
                }
            }
        }
        LocalBluetoothManager localBluetoothManager = this.mManager;
        if (localBluetoothManager != null
                && (bluetoothEventManager = localBluetoothManager.mEventManager) != null) {
            bluetoothEventManager.registerCallback(this.mEventManagerCallback);
        }
        if (this.mLeBroadcastAssistant != null) {
            Iterator it = ((HashSet) this.mMemberDevices).iterator();
            while (it.hasNext()) {
                CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) it.next();
                Iterator it2 =
                        this.mLeBroadcastAssistant
                                .getAllSources(cachedBluetoothDevice.mDevice)
                                .iterator();
                if (it2.hasNext()) {
                    BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState =
                            (BluetoothLeBroadcastReceiveState) it2.next();
                    Log.d(
                            "SecBluetoothLeBroadcastAssistantSettings",
                            "loadBroadcastReceiveState : " + cachedBluetoothDevice.mDevice);
                    updateListItemFromBroadcastReceiveState(
                            cachedBluetoothDevice.mDevice, bluetoothLeBroadcastReceiveState);
                }
            }
        } else {
            Log.e(
                    "SecBluetoothLeBroadcastAssistantSettings",
                    "loadBroadcastReceiveState : mLeBroadcastAssistant null");
        }
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant3 =
                this.mLeBroadcastAssistant;
        if (localBluetoothLeBroadcastAssistant3 != null
                && !localBluetoothLeBroadcastAssistant3.isSearchInProgress()) {
            LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant4 =
                    this.mLeBroadcastAssistant;
            List emptyList = Collections.emptyList();
            BluetoothDevice bluetoothDevice = this.mCachedDevice.mDevice;
            localBluetoothLeBroadcastAssistant4.getClass();
            Log.d("LocalBluetoothLeBroadcastAssistant", "startSearchingForSources()");
            BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant2 =
                    localBluetoothLeBroadcastAssistant4.mService;
            if (bluetoothLeBroadcastAssistant2 == null) {
                Log.d(
                        "LocalBluetoothLeBroadcastAssistant",
                        "The BluetoothLeBroadcastAssistant is null");
            } else {
                bluetoothLeBroadcastAssistant2.startSearchingForSources(emptyList, bluetoothDevice);
            }
        }
        SALogging.insertSALog(this.mScreenId);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onStart() {
        super.onStart();
        clearListItem();
        doInitUi();
    }

    public final void processCodeDialogButtonClick(
            SecBluetoothLeBroadcastAssistantSourceItem secBluetoothLeBroadcastAssistantSourceItem,
            boolean z) {
        Log.d("SecBluetoothLeBroadcastAssistantSettings", "processCodeDialogButtonClick : " + z);
        clearCodeInputDialogTimeout();
        this.mIsDecrypting = false;
        if (secBluetoothLeBroadcastAssistantSourceItem.mMetadata == null) {
            Log.d(
                    "SecBluetoothLeBroadcastAssistantSettings",
                    "BluetoothLeBroadcastMetadata is null, do nothing.");
            AlertDialog alertDialog = this.mBroadcastCodeDialog;
            if (alertDialog == null || !alertDialog.isShowing()) {
                return;
            }
            this.mBroadcastCodeDialog.dismiss();
            return;
        }
        if (z) {
            secBluetoothLeBroadcastAssistantSourceItem.updateMetadata(
                    new BluetoothLeBroadcastMetadata.Builder(
                                    secBluetoothLeBroadcastAssistantSourceItem.mMetadata)
                            .setEncrypted(true)
                            .setBroadcastCode(
                                    this.mBroadcastCodeEditText
                                            .getText()
                                            .toString()
                                            .getBytes(StandardCharsets.UTF_8))
                            .build(),
                    false);
            addSource(secBluetoothLeBroadcastAssistantSourceItem);
        } else {
            SecBluetoothLeBroadcastAssistantSourceItem secBluetoothLeBroadcastAssistantSourceItem2 =
                    this.mPendingSourceItem;
            if (secBluetoothLeBroadcastAssistantSourceItem2 != null
                    && secBluetoothLeBroadcastAssistantSourceItem.equals(
                            secBluetoothLeBroadcastAssistantSourceItem2)) {
                this.mPendingSourceItem = null;
            }
            if (secBluetoothLeBroadcastAssistantSourceItem.equals(this.mSelectedSourceItem)) {
                leaveBroadcastSession$1();
            }
        }
        AlertDialog alertDialog2 = this.mBroadcastCodeDialog;
        if (alertDialog2 == null || !alertDialog2.isShowing()) {
            return;
        }
        this.mBroadcastCodeDialog.dismiss();
    }

    public final void removeListItemByItem(
            SecBluetoothLeBroadcastAssistantSourceItem secBluetoothLeBroadcastAssistantSourceItem) {
        if (secBluetoothLeBroadcastAssistantSourceItem == null
                || !this.mSourceItems.contains(secBluetoothLeBroadcastAssistantSourceItem)) {
            return;
        }
        int indexOf = this.mSourceItems.indexOf(secBluetoothLeBroadcastAssistantSourceItem);
        this.mSourceItems.remove(secBluetoothLeBroadcastAssistantSourceItem);
        this.mSourceListAdapter.notifyItemRemoved(indexOf);
        this.mSourceListAdapter.notifyItemRangeRemoved(indexOf, this.mSourceItems.size() - indexOf);
    }

    public final void setDefaultUiResources() {
        String string = getResources().getString(R.string.sec_bluetooth_broadcast_summary);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(
                string,
                new ForegroundColorSpan(
                        Color.parseColor(
                                this.mContext
                                        .getResources()
                                        .getString(
                                                R.color.sec_bluetooth_auracast_header_icon_color))),
                33);
        spannableStringBuilder.setSpan(new StyleSpan(1), 0, string.length(), 33);
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(30, true), 0, string.length(), 33);
        ((TextView) findViewById(R.id.description_text)).setText(spannableStringBuilder);
        ((TextView) findViewById(R.id.device_information_text))
                .setText(
                        getResources()
                                .getString(
                                        R.string
                                                .bluetooth_broadcast_connected_stream_category_title,
                                        this.mCachedDevice.mDevice.getName()));
        ((TextView) findViewById(R.id.available_broadcasts_text))
                .setText(
                        getResources()
                                .getString(
                                        R.string
                                                .bluetooth_broadcast_available_stream_category_title));
        this.mAvailableBroadcastsLayout =
                (LinearLayout) findViewById(R.id.available_broadcasts_layout);
        ((TextView) findViewById(R.id.no_broadcasts_text))
                .setText(
                        getResources()
                                .getString(R.string.sec_bluetooth_broadcast_no_streams_found));
        this.mNoBroadcastsLayout = (LinearLayout) findViewById(R.id.no_broadcasts_layout);
        this.mDescriptionLayout = (LinearLayout) findViewById(R.id.description_layout);
        this.mConnectedCardLayout = (FrameLayout) findViewById(R.id.connected_card_layout);
        ArrayList arrayList = this.mSourceItems;
        SecBluetoothLeBroadcastAssistantListAdapter secBluetoothLeBroadcastAssistantListAdapter =
                new SecBluetoothLeBroadcastAssistantListAdapter();
        secBluetoothLeBroadcastAssistantListAdapter.mSourceItems = arrayList;
        secBluetoothLeBroadcastAssistantListAdapter.notifyDataSetChanged();
        this.mSourceListAdapter = secBluetoothLeBroadcastAssistantListAdapter;
        secBluetoothLeBroadcastAssistantListAdapter.mItemClickListener = new AnonymousClass8();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.source_list_layout);
        this.mBroadcastsListView = recyclerView;
        recyclerView.setHasFixedSize(true);
        this.mBroadcastsListView.setAdapter(this.mSourceListAdapter);
        this.mBroadcastsListView.setLayoutManager(new LinearLayoutManager(1));
        RecyclerView recyclerView2 = this.mBroadcastsListView;
        BroadcastItemDecoration broadcastItemDecoration = this.mBroadcastItemDecoration;
        recyclerView2.removeItemDecoration(broadcastItemDecoration);
        this.mBroadcastsListView.addItemDecoration(broadcastItemDecoration);
    }

    public final void updateActivityUi() {
        SecBluetoothLeBroadcastAssistantSourceItem secBluetoothLeBroadcastAssistantSourceItem =
                this.mSelectedSourceItem;
        if (secBluetoothLeBroadcastAssistantSourceItem == null
                || !secBluetoothLeBroadcastAssistantSourceItem.isConnected()) {
            this.mDescriptionLayout.setVisibility(0);
            this.mConnectedCardLayout.setVisibility(8);
            this.mAvailableBroadcastsLayout.setVisibility(8);
        } else {
            this.mDescriptionLayout.setVisibility(8);
            this.mConnectedCardLayout.setVisibility(0);
            this.mAvailableBroadcastsLayout.setVisibility(0);
        }
        if (this.mSourceItems.isEmpty()) {
            this.mNoBroadcastsLayout.setVisibility(0);
            this.mBroadcastsListView.setVisibility(8);
        } else {
            this.mNoBroadcastsLayout.setVisibility(8);
            this.mBroadcastsListView.setVisibility(0);
        }
    }

    public final void updateConnectedCardUi(
            SecBluetoothLeBroadcastAssistantSourceItem secBluetoothLeBroadcastAssistantSourceItem) {
        LinearLayout linearLayout =
                (LinearLayout) findViewById(R.id.connected_card_single_title_layout);
        LinearLayout linearLayout2 =
                (LinearLayout) findViewById(R.id.connected_card_multi_title_layout);
        if (secBluetoothLeBroadcastAssistantSourceItem.mSummary == null) {
            ((TextView) findViewById(R.id.connected_card_single_title))
                    .setText(secBluetoothLeBroadcastAssistantSourceItem.mTitle);
            linearLayout.setVisibility(0);
            linearLayout2.setVisibility(8);
        } else {
            ((TextView) findViewById(R.id.connected_card_multi_title))
                    .setText(secBluetoothLeBroadcastAssistantSourceItem.mTitle);
            ((TextView) findViewById(R.id.connected_card_multi_summary))
                    .setText(secBluetoothLeBroadcastAssistantSourceItem.mSummary);
            linearLayout.setVisibility(8);
            linearLayout2.setVisibility(0);
        }
    }

    public final void updateListItemFromBroadcastReceiveState(
            BluetoothDevice bluetoothDevice,
            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
        SecBluetoothLeBroadcastAssistantSourceItem secBluetoothLeBroadcastAssistantSourceItem;
        Log.i(
                "SecBluetoothLeBroadcastAssistantSettings",
                "updateListItemFromBroadcastReceiveState : "
                        + bluetoothLeBroadcastReceiveState.getBroadcastId()
                        + " from "
                        + bluetoothDevice);
        BluetoothDevice sourceDevice = bluetoothLeBroadcastReceiveState.getSourceDevice();
        int i = -1;
        if (sourceDevice != null && !"00:00:00:00:00:00".equals(sourceDevice.getAddress())) {
            List bluetoothBroadcastSourceItem =
                    getBluetoothBroadcastSourceItem(
                            bluetoothLeBroadcastReceiveState.getBroadcastId());
            if (bluetoothBroadcastSourceItem.isEmpty()) {
                Log.i(
                        "SecBluetoothLeBroadcastAssistantSettings",
                        "createBluetoothBroadcastSourceItem by recvState "
                                + bluetoothLeBroadcastReceiveState.getBroadcastId());
                secBluetoothLeBroadcastAssistantSourceItem =
                        new SecBluetoothLeBroadcastAssistantSourceItem(
                                this.mContext,
                                bluetoothLeBroadcastReceiveState.getBroadcastId(),
                                0);
            } else {
                secBluetoothLeBroadcastAssistantSourceItem =
                        (SecBluetoothLeBroadcastAssistantSourceItem)
                                bluetoothBroadcastSourceItem.get(0);
            }
            boolean isSync = secBluetoothLeBroadcastAssistantSourceItem.isSync();
            secBluetoothLeBroadcastAssistantSourceItem.updateReceiveState(
                    bluetoothDevice, bluetoothLeBroadcastReceiveState);
            this.mSourceId = bluetoothLeBroadcastReceiveState.getSourceId();
            ((ConcurrentHashMap) this.mActiveSinkMap)
                    .put(bluetoothDevice, secBluetoothLeBroadcastAssistantSourceItem);
            if (!isSync || secBluetoothLeBroadcastAssistantSourceItem.isSync()) {
                if (SecBluetoothLeBroadcastUtils.isPaSyncOrBisSync(
                        bluetoothLeBroadcastReceiveState)) {
                    if (SecBluetoothLeBroadcastUtils.isBisSync(bluetoothLeBroadcastReceiveState)) {
                        clearOperationTimeout$1();
                    }
                    this.mSelectedSourceItem = secBluetoothLeBroadcastAssistantSourceItem;
                    if (secBluetoothLeBroadcastAssistantSourceItem.isConnected()) {
                        if (this.mSourceItems.contains(this.mSelectedSourceItem)) {
                            removeListItemByItem(this.mSelectedSourceItem);
                        }
                        if (!this.mIsRemovingSource) {
                            updateConnectedCardUi(secBluetoothLeBroadcastAssistantSourceItem);
                        }
                    }
                    updateActivityUi();
                    return;
                }
                return;
            }
            Log.d(
                    "SecBluetoothLeBroadcastAssistantSettings",
                    "updateListItemFromBroadcastReceiveState : unexpected PA and BIS sync lost from"
                        + " "
                            + bluetoothDevice);
            if (secBluetoothLeBroadcastAssistantSourceItem.mMetadata == null) {
                removeListItemByItem(secBluetoothLeBroadcastAssistantSourceItem);
            } else if (!this.mSourceItems.contains(secBluetoothLeBroadcastAssistantSourceItem)) {
                LocalBluetoothManager localBluetoothManager = this.mManager;
                if (localBluetoothManager == null) {
                    Log.w(
                            "SecBluetoothLeBroadcastAssistantSettings",
                            "getLocalBroadcastId: LocalBluetoothManager is null");
                } else {
                    LocalBluetoothProfileManager localBluetoothProfileManager =
                            localBluetoothManager.mProfileManager;
                    if (localBluetoothProfileManager == null) {
                        Log.w(
                                "SecBluetoothLeBroadcastAssistantSettings",
                                "getLocalBroadcastId: LocalBluetoothProfileManager is null");
                    } else {
                        LocalBluetoothLeBroadcast localBluetoothLeBroadcast =
                                localBluetoothProfileManager.mLeAudioBroadcast;
                        if (localBluetoothLeBroadcast != null) {
                            i = localBluetoothLeBroadcast.mBroadcastId;
                        }
                    }
                }
                if (secBluetoothLeBroadcastAssistantSourceItem.mBroadcastId != i) {
                    this.mSourceItems.add(0, secBluetoothLeBroadcastAssistantSourceItem);
                    this.mSourceListAdapter.notifyItemInserted(0);
                }
            }
            this.mSelectedSourceItem = null;
            updateActivityUi();
            return;
        }
        Log.d(
                "SecBluetoothLeBroadcastAssistantSettings",
                "updateListItemFromBroadcastReceiveState : empty BRS from " + bluetoothDevice);
        ((ConcurrentHashMap) this.mActiveSinkMap).remove(bluetoothDevice);
        if (!((ConcurrentHashMap) this.mActiveSinkMap).isEmpty()) {
            SecBluetoothLeBroadcastAssistantSourceItem secBluetoothLeBroadcastAssistantSourceItem2 =
                    this.mSelectedSourceItem;
            if (secBluetoothLeBroadcastAssistantSourceItem2 != null) {
                secBluetoothLeBroadcastAssistantSourceItem2.updateReceiveState(
                        bluetoothDevice, null);
                return;
            }
            return;
        }
        this.mIsRemovingSource = false;
        clearOperationTimeout$1();
        this.mSourceId = -1;
        if (this.mIsRemovingByAddFailed) {
            this.mIsRemovingByAddFailed = false;
            Toast.makeText(
                            this,
                            getResources()
                                    .getString(R.string.sec_bluetooth_broadcast_add_source_failed),
                            0)
                    .show();
        }
        SecBluetoothLeBroadcastAssistantSourceItem secBluetoothLeBroadcastAssistantSourceItem3 =
                this.mSelectedSourceItem;
        if (secBluetoothLeBroadcastAssistantSourceItem3 != null) {
            if (secBluetoothLeBroadcastAssistantSourceItem3.mRecvState != null) {
                secBluetoothLeBroadcastAssistantSourceItem3.mRecvState = null;
                ((HashMap) secBluetoothLeBroadcastAssistantSourceItem3.mRecvStates).clear();
                BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata =
                        secBluetoothLeBroadcastAssistantSourceItem3.mMetadata;
                if (bluetoothLeBroadcastMetadata != null) {
                    List subgroups = bluetoothLeBroadcastMetadata.getSubgroups();
                    if (!subgroups.isEmpty()) {
                        secBluetoothLeBroadcastAssistantSourceItem3.mProgramInfo =
                                ((BluetoothLeBroadcastSubgroup)
                                                subgroups.get(
                                                        secBluetoothLeBroadcastAssistantSourceItem3
                                                                .mSubgroupIndex))
                                        .getContentMetadata()
                                        .getProgramInfo();
                    }
                }
                secBluetoothLeBroadcastAssistantSourceItem3.updateTitleAndSummary();
            }
            SecBluetoothLeBroadcastAssistantSourceItem secBluetoothLeBroadcastAssistantSourceItem4 =
                    this.mSelectedSourceItem;
            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata2 =
                    secBluetoothLeBroadcastAssistantSourceItem4.mMetadata;
            if (bluetoothLeBroadcastMetadata2 == null) {
                removeListItemByItem(secBluetoothLeBroadcastAssistantSourceItem4);
            } else {
                this.mSelectedSourceItem.updateMetadata(
                        updateMetadataBisChannel(
                                bluetoothLeBroadcastMetadata2,
                                secBluetoothLeBroadcastAssistantSourceItem4.mSubgroupIndex,
                                false),
                        false);
                if (!this.mSourceItems.contains(this.mSelectedSourceItem)) {
                    int i2 = this.mSelectedSourceItem.mBroadcastId;
                    LocalBluetoothManager localBluetoothManager2 = this.mManager;
                    if (localBluetoothManager2 == null) {
                        Log.w(
                                "SecBluetoothLeBroadcastAssistantSettings",
                                "getLocalBroadcastId: LocalBluetoothManager is null");
                    } else {
                        LocalBluetoothProfileManager localBluetoothProfileManager2 =
                                localBluetoothManager2.mProfileManager;
                        if (localBluetoothProfileManager2 == null) {
                            Log.w(
                                    "SecBluetoothLeBroadcastAssistantSettings",
                                    "getLocalBroadcastId: LocalBluetoothProfileManager is null");
                        } else {
                            LocalBluetoothLeBroadcast localBluetoothLeBroadcast2 =
                                    localBluetoothProfileManager2.mLeAudioBroadcast;
                            if (localBluetoothLeBroadcast2 != null) {
                                i = localBluetoothLeBroadcast2.mBroadcastId;
                            }
                        }
                    }
                    if (i2 != i) {
                        this.mSourceItems.add(0, this.mSelectedSourceItem);
                        this.mSourceListAdapter.notifyItemInserted(0);
                    }
                }
            }
            this.mSelectedSourceItem = null;
        }
        if (this.mPendingSourceItem != null) {
            Log.d(
                    "SecBluetoothLeBroadcastAssistantSettings",
                    "handleSourceRemoved : source handover");
            if (((ConcurrentHashMap) this.mActiveSinkMap).isEmpty()) {
                updateConnectedCardUi(this.mPendingSourceItem);
            }
            this.mSelectedSourceItem = this.mPendingSourceItem;
            this.mPendingSourceItem = null;
        }
        updateActivityUi();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastAssistantSettings$3, reason: invalid class name */
    public final class AnonymousClass3 implements BluetoothCallback {
        public AnonymousClass3() {}

        @Override // com.android.settingslib.bluetooth.BluetoothCallback
        public final void onBluetoothStateChanged(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "onBluetoothStateChanged : ", "SecBluetoothLeBroadcastAssistantSettings");
            if (i == 13) {
                SecBluetoothLeBroadcastAssistantSettings.this.runOnUiThread(
                        new SecBluetoothLeBroadcastAssistantSettings$3$$ExternalSyntheticLambda0(
                                this, 0));
            }
        }

        @Override // com.android.settingslib.bluetooth.BluetoothCallback
        public final void onProfileConnectionStateChanged(
                CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {
            if (i2 == 29) {
                StringBuilder sb = new StringBuilder("onProfileConnectionStateChanged : ");
                sb.append(cachedBluetoothDevice.mDevice);
                sb.append(", profile ");
                sb.append(i2);
                sb.append(", state ");
                Preference$$ExternalSyntheticOutline0.m(
                        sb, i, "SecBluetoothLeBroadcastAssistantSettings");
                if (i == 0) {
                    SecBluetoothLeBroadcastAssistantSettings.this.runOnUiThread(
                            new SecBluetoothLeBroadcastAssistantSettings$3$$ExternalSyntheticLambda0(
                                    this, 1));
                }
            }
        }

        @Override // com.android.settingslib.bluetooth.BluetoothCallback
        public final void onAudioModeChanged() {}

        @Override // com.android.settingslib.bluetooth.BluetoothCallback
        public final void onDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {}

        @Override // com.android.settingslib.bluetooth.BluetoothCallback
        public final void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice) {}

        @Override // com.android.settingslib.bluetooth.BluetoothCallback
        public final void onScanningStateChanged(boolean z) {}

        @Override // com.android.settingslib.bluetooth.BluetoothCallback
        public final void onAclConnectionStateChanged(
                CachedBluetoothDevice cachedBluetoothDevice, int i) {}

        @Override // com.android.settingslib.bluetooth.BluetoothCallback
        public final void onActiveDeviceChanged(
                CachedBluetoothDevice cachedBluetoothDevice, int i) {}

        @Override // com.android.settingslib.bluetooth.BluetoothCallback
        public final void onConnectionStateChanged(
                CachedBluetoothDevice cachedBluetoothDevice, int i) {}

        @Override // com.android.settingslib.bluetooth.BluetoothCallback
        public final void onDeviceBondStateChanged(
                CachedBluetoothDevice cachedBluetoothDevice, int i) {}
    }
}
