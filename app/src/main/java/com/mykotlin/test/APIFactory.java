package com.mykotlin.test;

/**
 * Created by qiqi on 17/2/25.
 */

public class APIFactory {
    private APIFactory() {
    }

    private static class Holder {
        private static APIFactory instance = new APIFactory();
    }

    public APIFactory getInstance() {
        return Holder.instance;
    }
}
