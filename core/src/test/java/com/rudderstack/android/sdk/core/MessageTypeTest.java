package com.rudderstack.android.sdk.core;

import org.junit.Assert;
import org.junit.Test;

public class MessageTypeTest {
    private final String TRACK = "track";
    private final String PAGE = "page";
    private final String SCREEN = "screen";
    private final String IDENTIFY = "identify";
    private final String ALIAS = "alias";
    private final String GROUP = "group";

    @Test
    public void MessageType() {
        Assert.assertEquals(TRACK, MessageType.TRACK);
        Assert.assertEquals(PAGE, MessageType.PAGE);
        Assert.assertEquals(SCREEN, MessageType.SCREEN);
        Assert.assertEquals(IDENTIFY, MessageType.IDENTIFY);
        Assert.assertEquals(ALIAS, MessageType.ALIAS);
        Assert.assertEquals(GROUP, MessageType.GROUP);
    }
}
