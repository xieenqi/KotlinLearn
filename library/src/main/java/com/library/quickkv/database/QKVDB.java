/**
 * QucikKV
 * Copyright 2014-2016 Sumi Makito
 * Licensed under Apache License 2.0.
 *
 * @author sumimakito<sumimakito@hotmail.com>
 */

package com.library.quickkv.database;

import android.content.Context;
import com.library.quickkv.QuickKV;


public abstract class QKVDB {
    protected Context pContext;
    protected QuickKV pInstance;
    protected String dbAlias;
    protected String pKey;
    protected boolean isGZipEnabled = false;

    public QKVDB(QuickKV quickKV, Context context) {
        this.pInstance = quickKV;
        this.pContext = context;
    }
}
