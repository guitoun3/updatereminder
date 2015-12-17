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
import retrofit.http.GET;
import retrofit.http.Url;

public class UpdateReminder implements Callback<ApiResult> {

    private final static String TAG = "UpdateReminder";

    private Context mContext;
    private int mCurrentVersion;
    private String mBaseUrl;
    private String mPath;
    private Boolean mDebug = false;

    public interface ApiMethods {
        @GET
        Call<ApiResult> checkUpdate(@Url String url);
    }


    private UpdateReminder(Context context) {
        mContext = context;

        try {
            mCurrentVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
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

            if (result != null) {
                if (mDebug) {
                    Log.d(TAG, result.toString());
                }

                Integer remoteVersionCode = result.getVersionCode();

                if (result.enabled() && remoteVersionCode != null && remoteVersionCode > mCurrentVersion) {
                    showDialog(result.forceUpdate(), result.getMessage());
                }
            }
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if (mDebug) {
            Log.d(TAG, "Failure : " + t.getMessage());
        }
    }


    private void showDialog(Boolean forceUpdate, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

        alertDialogBuilder.setTitle(mContext.getString(R.string.updatereminder_title));

        String finalMessage = mContext.getString(R.string.updatereminder_message);
        if (message != null && !message.isEmpty()) {
            finalMessage += "\n\n" + message;
        }

        alertDialogBuilder
                .setMessage(finalMessage)
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
