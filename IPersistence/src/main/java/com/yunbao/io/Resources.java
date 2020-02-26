package com.yunbao.io;

import java.io.InputStream;

/**
 * Created by louisyuu on 2020/2/26 上午11:27
 */
public class Resources {


    public static InputStream getResourceAsStream(String path) {
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }
}
