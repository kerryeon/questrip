package com.levelup.Questrip.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 외부 파일을 불러오거나 저장하는 클래스입니다.
 *
 * 담당자: 김호
 *
 * 역할: 외부 파일을 불러오거나 저장하는 메소드를 구현합니다.
 */
public final class FileManager {

    /**
     * 외부 파일을 불러옵니다.
     * @param path 파일 경로
     * @return 바이너리
     */
    public static byte[] getBytes(String path) {
        File file = new File(path);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

}
