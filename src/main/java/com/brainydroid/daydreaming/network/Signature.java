package com.brainydroid.daydreaming.network;


import com.google.gson.annotations.Expose;

public class Signature {

    @Expose private String protected_;
    @Expose private String signature;

    public Signature(String protected_, String signature) {
        this.protected_ = protected_;
        this.signature = signature;
    }

    public synchronized String getProtected() {
        return protected_;
    }

    public synchronized String getSignature() {
        return signature;
    }
}