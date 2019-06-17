/*
 * Copyright(c)2019-
 *    项目名称:SSIS
 *    文件名称:ExampleInstrumentedTest.java
 *    Date:19-6-3 下午9:04
 *    Author:Fanwj
 */

package com.fwj.ssis;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.fwj.ssis", appContext.getPackageName());
    }
}
