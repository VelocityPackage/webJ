package com.velocitypackage.webj.services.file;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This is the utility for files
 *
 * @author maxmielchen
 */
public class FileService
{
    /**
     * gets a file from the resource folder and read the internals
     *
     * @param path path in the resource folder
     * @return content of resource
     * @throws IOException          if the file did not exist
     * @throws NullPointerException if the file is empty
     */
    public static String getContentOfResource(@NotNull String path) throws IOException, NullPointerException
    {
        try
        {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            StringBuilder content = new StringBuilder();
            try (InputStream inputStream = classloader.getResourceAsStream(path))
            {
                if (inputStream == null)
                {
                    throw new NullPointerException("File reader is null");
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                for (String line; (line = reader.readLine()) != null; )
                {
                    content.append(line).append("\n");
                }
            }
            return new String(content);
        } catch (Exception e)
        {
            throw new IOException("File did not exist");
        }
    }
}
