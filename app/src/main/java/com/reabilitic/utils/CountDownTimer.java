package com.reabilitic.utils;

/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import java.lang.ref.WeakReference;

/**
 * The calls to {@link #onTick(long)} are synchronized to this object so that
 * one call to {@link #onTick(long)} won't ever occur before the previous
 * callback is complete.  This is only relevant when the implementation of
 * {@link #onTick(long)} takes an amount of time to execute that is significant
 * compared to the countdown interval.
 */
public abstract class CountDownTimer {

    /**
     * Millis since epoch when alarm should stop.
     */
    private long mMillisInFuture;

    /**
     * The interval in millis that the user receives callbacks.
     */
    private long mCountdownInterval;

    /**
     * The time difference in millis between time since system boot and {@link #mMillisInFuture}.
     */
    private long mStopTimeInFuture;

    /**
     * boolean representing if the timer was cancelled.
     */
    private boolean mCancelled = false;

    /**
     * Handler message code.
     */
    private static final int MSG = 1;

    /**
     * Handler object.
     */
    private final Handler mHandler;

    /**
     * Callback fired on regular interval.
     * @param millisUntilFinished The amount of time until finished.
     */
    public abstract void onTick(long millisUntilFinished);

    /**
     * Callback fired when the time is up.
     */
    public abstract void onFinish();

    /**
     * Constructor with parameters.
     * @param millisInFuture The number of millis in the future from the call to {@link #start()}
     *                       until the countdown is done and {@link #onFinish()} is called.
     * @param countDownInterval The interval along the way to receive {@link #onTick(long)}
     *                          callbacks.
     */
    public CountDownTimer(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
        mHandler = new CountDownTimerHandler(this);
    }

    /**
     * Empty constructor. To be used with {@link #start(long, long)}.
     */
    public CountDownTimer() {
        mMillisInFuture = Long.MIN_VALUE;
        mCountdownInterval = Long.MIN_VALUE;
        mHandler = new CountDownTimerHandler(this);
    }

    /**
     * Cancel the countdown.
     */
    public synchronized final void cancel() {
        mCancelled = true;
        mHandler.removeMessages(MSG);
    }

    /**
     * Start the countdown.
     * @return The CountDownTime object.
     */
    public synchronized final CountDownTimer start() {
        mCancelled = false;
        if (mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }

    /**
     * Start the countdown with the specified parameters.
     * @param millisInFuture The number of millis in the future until the countdown is done
     *                       and {@link #onFinish()} is called.
     * @param countDownInterval The interval along the way to receive {@link #onTick(long)}
     *                          callbacks.
     * @return The CountDownTimer object.
     */
    public synchronized final CountDownTimer start(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
        return this.start();
    }

    /**
     * Instances of static inner classes do not hold an implicit reference to their outer class.
     * Creating this static class helps to avoid memory leaks.
     */
    private static class CountDownTimerHandler extends Handler {
        /**
         * WeakReference of the outer class CountDownTimer.
         */
        private final WeakReference<CountDownTimer> countDownTimerWeakReference;

        /**
         * Handler constructor.
         * @param countDownTimeInstance
         */
        public CountDownTimerHandler(CountDownTimer countDownTimeInstance) {
            countDownTimerWeakReference = new WeakReference<CountDownTimer>(countDownTimeInstance);
        }

        @Override
        public void handleMessage(Message msg) {
            CountDownTimer countDownTimer = countDownTimerWeakReference.get();
            if(countDownTimer instanceof CountDownTimer) {
                synchronized (countDownTimer) {
                    if (countDownTimer.mCancelled) {
                        return;
                    }
                    final long millisLeft = countDownTimer.mStopTimeInFuture - SystemClock.elapsedRealtime();
                    if (millisLeft <= 0) {
                        countDownTimer.onFinish();
                    } else if (millisLeft < countDownTimer.mCountdownInterval) {
                        // no tick, just delay until done
                        sendMessageDelayed(obtainMessage(MSG), millisLeft);
                    } else {
                        long lastTickStart = SystemClock.elapsedRealtime();
                        countDownTimer.onTick(millisLeft);
                        // take into account user's onTick taking time to execute
                        long delay = lastTickStart + countDownTimer.mCountdownInterval - SystemClock.elapsedRealtime();
                        // special case: user's onTick took more than interval to
                        // complete, skip to next interval
                        while (delay < 0) delay += countDownTimer.mCountdownInterval;
                        sendMessageDelayed(obtainMessage(MSG), delay);
                    }
                }
            }
        }
    }
}