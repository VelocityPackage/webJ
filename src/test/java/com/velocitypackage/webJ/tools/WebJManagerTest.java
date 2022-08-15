package com.velocitypackage.webJ.tools;

import java.io.IOException;

class WebJManagerTest
{
    public static void main(String[] args) throws IOException
    {
        new WebJManager(8080, new TestApplication()).start();
    }
}