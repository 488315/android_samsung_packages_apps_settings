package com.samsung.android.settings.cube.gts;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.android.settings.MainClear$$ExternalSyntheticOutline0;

import com.google.gson.Gson;
import com.samsung.android.gtscell.ResultCallback;
import com.samsung.android.gtscell.data.GtsConfiguration;
import com.samsung.android.gtscell.data.GtsItem;
import com.samsung.android.gtscell.data.result.GtsItemResult;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.CubeFactoryProvider;
import com.samsung.android.settings.cube.index.ControlData;
import com.samsung.android.settings.gts.GtsResources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class GtsCube extends CubeFactoryProvider {
    private static final List<Integer> AVAILABLE_STATUS = Arrays.asList(0, 1, 5);
    private static final boolean DEBUG = true;
    private static final String TAG = "GtsCube";
    private final HashMap<String, ResultCallback> mAsyncResultCallback;
    private final GtsItemConverter mGtsItemConverter;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.cube.gts.GtsCube$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[]
                $SwitchMap$com$samsung$android$settings$cube$ControlResult$ErrorCode;
        static final /* synthetic */ int[]
                $SwitchMap$com$samsung$android$settings$cube$ControlResult$ResultCode;

        static {
            int[] iArr = new int[ControlResult.ErrorCode.values().length];
            $SwitchMap$com$samsung$android$settings$cube$ControlResult$ErrorCode = iArr;
            try {
                iArr[7] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$settings$cube$ControlResult$ErrorCode[0] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$settings$cube$ControlResult$ErrorCode[1] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$settings$cube$ControlResult$ErrorCode[2] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$samsung$android$settings$cube$ControlResult$ErrorCode[3] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$samsung$android$settings$cube$ControlResult$ErrorCode[6] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr2 = new int[ControlResult.ResultCode.values().length];
            $SwitchMap$com$samsung$android$settings$cube$ControlResult$ResultCode = iArr2;
            try {
                iArr2[0] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$samsung$android$settings$cube$ControlResult$ResultCode[2] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$samsung$android$settings$cube$ControlResult$ResultCode[3] = 3;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    public GtsCube(Context context) {
        super(context);
        this.mAsyncResultCallback = new HashMap<>();
        this.mGtsItemConverter = new GtsItemConverter();
    }

    public static boolean isAvailableControlValue(ControlValue controlValue) {
        if (controlValue == null) {
            Log.w(TAG, "isAvailableControlValue() controlValue is null");
            return false;
        }
        List<Integer> list = AVAILABLE_STATUS;
        int i = controlValue.mAvailabilityStatus;
        boolean contains = list.contains(Integer.valueOf(i));
        if (!contains) {
            MainClear$$ExternalSyntheticOutline0.m$1(
                    ListPopupWindow$$ExternalSyntheticOutline0.m(
                            i, "isAvailableControlValue() status : ", " / Key : "),
                    controlValue.mKey,
                    TAG);
        }
        return contains;
    }

    @Override // com.samsung.android.settings.cube.CubeFactoryProvider
    public final String getCubeLabel() {
        return TAG;
    }

    public final List getGtsItemGroups(List list) {
        GtsItemWrapper convertData;
        HashMap allIndexedControlData = getAllIndexedControlData();
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (allIndexedControlData.containsKey(str)) {
                String str2 = TAG;
                Log.d(str2, "valid key : " + str);
                ControlData controlData = (ControlData) allIndexedControlData.get(str);
                ControlValue value = getValue(controlData.mKey);
                if (isAvailableControlValue(value)) {
                    this.mGtsItemConverter.getClass();
                    convertData = GtsItemConverter.convertData(controlData, value);
                } else {
                    if (value == null) {
                        Log.w(str2, "getGtsItem() " + controlData.mKey + " controlValue is null");
                    }
                    convertData = null;
                }
                if (convertData != null) {
                    arrayList.add(convertData);
                } else {
                    DialogFragment$$ExternalSyntheticOutline0.m(
                            "failed to convert from control data to gts value : ", str, str2);
                }
            } else {
                DialogFragment$$ExternalSyntheticOutline0.m(
                        "cannot find gts key from indexed items : ", str, TAG);
            }
        }
        return arrayList;
    }

    public final void handleResult(
            String str, ControlResult controlResult, ResultCallback resultCallback) {
        GtsItemResult pass;
        int ordinal = controlResult.mResultCode.ordinal();
        String str2 = controlResult.mKey;
        if (ordinal == 0) {
            pass = new GtsItemResult.Pass(str2);
        } else if (ordinal == 2 || ordinal == 3) {
            this.mAsyncResultCallback.put(str, resultCallback);
            AbsAdapter$$ExternalSyntheticOutline0.m("buildResult() - Async ", str2, TAG);
            pass = null;
        } else {
            ControlResult.ErrorCode errorCode = ControlResult.ErrorCode.ALREADY_SET;
            ControlResult.ErrorCode errorCode2 = controlResult.mErrorCode;
            if (errorCode2 == errorCode) {
                pass = new GtsItemResult.Pass(str2);
            } else {
                GtsItemResult.ErrorReason errorReason = GtsItemResult.ErrorReason.UNKNOWN;
                if (errorCode2 != null) {
                    int ordinal2 = errorCode2.ordinal();
                    if (ordinal2 == 0
                            || ordinal2 == 1
                            || ordinal2 == 2
                            || ordinal2 == 3
                            || ordinal2 == 6) {
                        errorReason = GtsItemResult.ErrorReason.UNSUPPORTED_ITEM;
                    } else if (ordinal2 == 7) {
                        errorReason = GtsItemResult.ErrorReason.DEPENDENT_ITEM;
                    }
                }
                String str3 = controlResult.mErrorMsg;
                if (str3 == null) {
                    str3 = ApnSettings.MVNO_NONE;
                }
                pass = new GtsItemResult.Error(str2, errorReason, str3);
            }
        }
        if (pass != null) {
            resultCallback.onResult(pass);
        }
    }

    @Override // com.samsung.android.settings.cube.CubeFactoryProvider
    public final void notifyControlResult(String str, ControlResult controlResult) {
        ResultCallback remove = this.mAsyncResultCallback.remove(str);
        if (remove != null) {
            handleResult(str, controlResult, remove);
        }
    }

    public final void setGtsItem(
            GtsItem gtsItem, GtsConfiguration gtsConfiguration, ResultCallback resultCallback) {
        GtsItem gtsItem2;
        ControlResult controlResult;
        String key = gtsItem.getKey();
        Map map = GtsResources.mResourceMap;
        GtsResources.LazyHolder.INSTANCE.getClass();
        boolean contains = ((LinkedHashMap) GtsResources.mResourceMap).keySet().contains(key);
        ControlResult.ErrorCode errorCode = ControlResult.ErrorCode.NOT_SUPPORT_DEVICE;
        ControlResult.ResultCode resultCode = ControlResult.ResultCode.FAIL;
        if (!contains) {
            ControlResult.Builder builder = new ControlResult.Builder(key);
            builder.mResultCode = resultCode;
            builder.mErrorCode = errorCode;
            handleResult(key, new ControlResult(builder), resultCallback);
        }
        ControlValue.ControlValueWrapper controlValueWrapper =
                (ControlValue.ControlValueWrapper)
                        new Gson()
                                .fromJson(
                                        gtsItem.getValue(), ControlValue.ControlValueWrapper.class);
        Uri uri = null;
        ControlValue buildControlValue = null;
        uri = null;
        if (controlValueWrapper == null) {
            Log.w(TAG, "convertControlValue() " + gtsItem.getKey() + "controlValueWrapper is null");
        } else {
            String str = gtsItem.getKey() + "@@" + System.currentTimeMillis();
            Map<String, GtsItem> embeddedItems = gtsItem.getEmbeddedItems();
            if (embeddedItems != null && (gtsItem2 = embeddedItems.get("uri")) != null) {
                uri = (Uri) gtsItem2.getTypedValue();
            }
            buildControlValue =
                    controlValueWrapper.buildControlValue(str, uri, true, gtsConfiguration);
        }
        if (buildControlValue == null) {
            Log.w(TAG, "setGtsItem() " + key + "newValue is null");
            ControlResult.ErrorCode errorCode2 = ControlResult.ErrorCode.INVALID_DATA;
            ControlResult.Builder builder2 = new ControlResult.Builder(key);
            builder2.mResultCode = resultCode;
            builder2.mErrorCode = errorCode2;
            handleResult(key, new ControlResult(builder2), resultCallback);
            return;
        }
        ControlValue value = getValue(key);
        if (value == null) {
            Log.w(TAG, "setGtsItem() " + key + "currentValue is null");
            ControlResult.Builder builder3 = new ControlResult.Builder(key);
            builder3.mResultCode = resultCode;
            builder3.mErrorCode = errorCode;
            handleResult(key, new ControlResult(builder3), resultCallback);
            return;
        }
        if (isAvailableControlValue(value)) {
            int i = buildControlValue.mControlType;
            String str2 = buildControlValue.mKey;
            if (i != 102 && value.equals(buildControlValue)) {
                ControlResult.Builder builder4 = new ControlResult.Builder(str2);
                builder4.mResultCode = resultCode;
                builder4.mErrorCode = ControlResult.ErrorCode.ALREADY_SET;
                controlResult = new ControlResult(builder4);
            } else {
                controlResult = setValue(str2, buildControlValue);
            }
        } else {
            ControlResult.Builder builder5 = new ControlResult.Builder(value.mKey);
            int i2 = value.mAvailabilityStatus;
            if (i2 == 3) {
                builder5.mErrorCode = errorCode;
            } else if (i2 == 4) {
                builder5.mErrorCode = ControlResult.ErrorCode.NOT_SUPPORT_BY_POLICY;
            } else if (i2 != 5) {
                builder5.mErrorCode = ControlResult.ErrorCode.NOT_SUPPORT_TEMPORARY;
            } else {
                builder5.mErrorCode = ControlResult.ErrorCode.DEPENDENT_SETTING;
            }
            builder5.mResultCode = resultCode;
            builder5.setErrorMsg(value.mStatusCode);
            controlResult = new ControlResult(builder5);
        }
        handleResult(buildControlValue.mControlId, controlResult, resultCallback);
    }
}
