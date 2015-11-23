package com.github.guitoun3.updatereminder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class UpdateReminder implements Callback<ApiResult> {

    private final static String TAG = "UpdateReminder";

    private Context mContext;
    private String mCurrentVersion;
    private String mBaseUrl;
    private String mPath;
    private Boolean mDebug = false;

    private UpdateReminder(Context context) {
        mContext = context;

        try {
            mCurrentVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            if (mDebug) {
                Log.d(TAG, e.getMessage());
            }
        }

        if (mDebug) {
            Log.i(TAG, "Current version: " + mCurrentVersion);
        }
    }

    public void checkUpdate() {
        if (mDebug) {
            Log.i(TAG, mBaseUrl + mPath);
        }

        Call<ApiResult> call = RestClient.getInstance(mBaseUrl).checkUpdate(mBaseUrl + mPath);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Response<ApiResult> response, Retrofit retrofit) {
        if (response.isSuccess()) {
            ApiResult result = response.body();

            if (mDebug) {
                Log.d(TAG, result.toString());
            }

            if (result.enabled && !mCurrentVersion.equals(result.version)) {
                showDialog(result.force_update);
            }
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if (mDebug) {
            Log.d(TAG, "Failure : " + t.getMessage());
        }
    }


    private void showDialog(Boolean forceUpdate) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

        alertDialogBuilder.setTitle(mContext.getString(R.string.updatereminder_title));

        alertDialogBuilder
                .setMessage(mContext.getString(R.string.updatereminder_message))
                .setCancelable(false)
                .setPositiveButton(mContext.getString(R.string.updatereminder_update_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        openPlayStore();
                    }
                });

        if (!forceUpdate) {
            alertDialogBuilder.setNegativeButton(mContext.getString(R.string.updatereminder_no_button), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
        }

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void openPlayStore() {
        final String appPackageName = mContext.getPackageName();
        try {
            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            if (mDebug) {
                Log.d(TAG, anfe.getMessage());
            }
        }
    }

    public static class Builder {

        private final UpdateReminder U;

        public Builder(Context context) {
            U = new UpdateReminder(context);
        }

        public Builder setBaseUrl(String baseUrl) {
            U.mBaseUrl = baseUrl;
            return this;
        }

        public Builder setPath(String path) {
            U.mPath = path;
            return this;
        }

        public Builder debug() {
            U.mDebug = true;
            return this;
        }

        public UpdateReminder build() {
            return U;
        }
    }
}
