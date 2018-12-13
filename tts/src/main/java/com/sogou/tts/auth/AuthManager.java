// Copyright 2018 Sogou Inc. All rights reserved. 
// Use of this source code is governed by the Apache 2.0 
// license that can be found in the LICENSE file. 
package com.sogou.tts.auth;

import android.content.Context;



public class AuthManager {
    private static AuthManager sAuthManager;

    private boolean mOnlineStatus = false;
    private boolean mOfflineStatus = false;
    private int mErrorOfflineStatus = 0;

    public static AuthManager getInstance() {
        if (sAuthManager == null) {
            synchronized (AuthManager.class) {
                if (sAuthManager == null) {
                    sAuthManager = new AuthManager();
                }
            }
        }
        return sAuthManager;
    }

    public int getErrorStatus(){
        return mErrorOfflineStatus;
    }

    public boolean isAuthPassed(int mode){
        return mOnlineStatus;

    }

    private AuthManager(){}

    public void init(Context context){
        if (!mOfflineStatus) {
            mOfflineStatus = false;
        }

        if (!mOnlineStatus) {
            TokenFetchTask task = new TokenFetchTask(context, new TokenFetchTask.TokenFetchListener() {
                @Override
                public void onTokenFetchSucc(String result) {
                    mOnlineStatus = true;
                }

                @Override
                public void onTokenFetchFailed(String errMsg) {
                    mOnlineStatus = false;
                }
            });
            task.execute();
        }
    }
}