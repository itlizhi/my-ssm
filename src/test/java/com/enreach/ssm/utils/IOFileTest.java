package com.enreach.ssm.utils;

import com.enreach.ssm.utils.io.FileUtil;
import com.enreach.ssm.utils.io.IOUtil;
import com.enreach.ssm.utils.io.ResourceUtil;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

public class IOFileTest {

    @Test
    public void platforms() {

        System.out.println(Platforms.TMP_DIR);
        System.out.println(Platforms.WORKING_DIR);
        System.out.println(Platforms.USER_HOME);
        System.out.println(Platforms.JAVA_HOME);
        System.out.println(System.getProperty("catalina.base"));

    }

    /**
     * 配置文件读取
     */
    @Test
    public void properties() throws IOException {

        Properties properties = PropertiesUtil.loadFromFile("classpath:app.properties");

        assertThat(PropertiesUtil.getBoolean(properties, "conn.show", false)).isEqualTo(true);

        System.out.println(PropertiesUtil.getString(properties, "conn.ip", ""));

        System.out.println(PropertiesUtil.getString(properties, "conn.test", "abc"));//none

        Properties properties1 = PropertiesUtil.loadFromString(ResourceUtil.asString("inline/test.txt"));
        System.out.println(properties1);

    }

    /**
     * jar 包内文件读取
     */
    @Test
    public void resource() throws IOException {

        URL url = ResourceUtil.asUrl("inline/test.txt");

        System.out.println(new File(url.getPath()).getPath());

        System.out.println(FileUtil.asString(url.getPath()));

        System.out.println(ResourceUtil.asString("file.properties"));

        //  System.out.println(ResourceUtil.getResourcesQuietly("META-INF/MANIFEST.MF"));

    }

    /**
     * 文件操作
     */
    @Test
    public void file() throws IOException {

        //Path path = FileUtil.createTempFile("abc", ".txt");
        //System.out.println(path);
        //FileUtil.append("abc", path.toFile());


        FileUtil.makesureDirExists("d:\\ssm-tmp");

        File file = FileUtil.newFile("d:/ssm-tmp", "20180828.log");

        for (int i = 0; i < 5; i++) {
            FileUtil.append("line" + i + Platforms.LINE_SEPARATOR, file);
        }

        System.out.println(FileUtil.asString(file));

    }

    /**
     * io操作
     */
    @Test
    public void io() throws IOException {

        System.out.println(IOUtil.toString(ResourceUtil.asStream("inline/test.txt")));

        OutputStream out = new ByteArrayOutputStream();

        IOUtil.write("line-abc", out);

        System.out.println(out.toString());

        IOUtil.closeQuietly(out);

    }
}
