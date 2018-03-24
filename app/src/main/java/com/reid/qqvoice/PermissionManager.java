package com.reid.qqvoice;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yang.li2 on 2018/3/22.
 */

public class PermissionManager {
    private static final String TAG = PermissionManager.class.getSimpleName();
    private static final int CAM_REQUEST_CODE_ASK_LAUNCH_PERMISSIONS = 100;

    private List<String> mPermissionList = new ArrayList<>();
    private Activity mActivity;

    public PermissionManager(Activity activity) {
        mActivity = activity;
        initPermissionList();
    }

    private void initPermissionList() {
        mPermissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        mPermissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public boolean checkCameraLaunchPermissions() {
        List<String> needCheckPermissionsList = getNeedCheckPermissionList(mPermissionList);
        if (needCheckPermissionsList.size() > 0) {
            return false;
        }
        Log.d(TAG, "CheckCameraPermissions(), all on");
        return true;
    }

    private List<String> getNeedCheckPermissionList(List<String> permissionList) {
        // all needed permissions, may be on or off
        if (permissionList.size() <= 0) {
            return permissionList;
        }
        List<String> needCheckPermissionsList = new ArrayList<>();
        for (String permission : permissionList) {
            if (ContextCompat.checkSelfPermission(mActivity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "getNeedCheckPermissionList() permission ="
                        + permission);
                needCheckPermissionsList.add(permission);
            }
        }
        Log.d(TAG, "getNeedCheckPermissionList() listSize ="
                + needCheckPermissionsList.size());
        return needCheckPermissionsList;
    }

    public boolean requestCameraAllPermissions() {
        List<String> needCheckPermissionsList = getNeedCheckPermissionList(mPermissionList);
        if (needCheckPermissionsList.size() > 0) {
            // should show dialog
            Log.d(TAG, "requestCameraAllPermissions(), user check");
            ActivityCompat.requestPermissions(
                    mActivity,
                    needCheckPermissionsList
                            .toArray(new String[needCheckPermissionsList
                                    .size()]), CAM_REQUEST_CODE_ASK_LAUNCH_PERMISSIONS);
            return false;
        }
        Log.d(TAG, "requestCameraAllPermissions(), all on");
        return true;
    }

    public int getCameraLaunchPermissionRequestCode() {
        return CAM_REQUEST_CODE_ASK_LAUNCH_PERMISSIONS;
    }

    public boolean isCameraLaunchPermissionsResultReady(
            String permissions[], int[] grantResults) {
        Map<String, Integer> perms = new HashMap<>();
        perms.put(Manifest.permission.READ_EXTERNAL_STORAGE,
                PackageManager.PERMISSION_GRANTED);
        perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                PackageManager.PERMISSION_GRANTED);

        for (int i = 0; i < permissions.length; i++) {
            perms.put(permissions[i], grantResults[i]);
        }

        if (perms.get(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
                && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        return false;
    }
}
