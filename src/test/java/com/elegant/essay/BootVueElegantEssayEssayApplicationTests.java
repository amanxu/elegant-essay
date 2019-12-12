package com.elegant.essay;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BootVueElegantEssayEssayApplicationTests {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        Future<String> stringFuture = null;
        try {
            stringFuture.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw e;
        }
    }
}
