package com.aplication.onlinepharmacy.Others;

import android.os.Binder;

public class ObjectWraperForBinder extends Binder {
    private final Object mData;

        public ObjectWraperForBinder(Object data) {
            mData = data;
        }

        public Object getData() {
            return mData;
        }

}


