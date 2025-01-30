package com.toaha.file.manipulator;

/*
 @File Manipulator
 @By Constant T (Hasin Israk Toaha)(SSC - 2025)

 @ Library written on about 10PM, 13/01/2025 
 @ Written & Tested on AIDE , Java N-IDE & OpenJDK-21 in Termux in Android 11 Operating System!

 @@Java has too many classes and trick to manipulate file, fo this, programmers often get 😕 . To solve this problem, I write this library.

 @Use library "File Manipulator" Freely and you can edit functions, see or modify source code. But i have a request, if you use this on any project, please give me credit ?��. I have no computer & I made this library in a big struggle.

 @Contact:

 $Email: toaha.banaripara@gmail.com
 $Phone: +8801317936503


 */


import com.toaha.file.manipulator.FileManipulator;
import java.io.*;
import java.lang.*;
import java.nio.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import java.util.regex.*;

public class FileManipulator 
{
    private File file = null;
    private BufferedWriter writer = null;
    private BufferedReader reader = null;
    private RandomAccessFile randomAccessFile = null;

    private static String currentFile = null;


    public FileManipulator(String filePath, String mode)
	{
        this.file = new File(filePath);
        this.currentFile = filePath;
        try
		{
            switch (mode)
			{
                case "w":
                    writer = new BufferedWriter(new FileWriter(file));
                    break;
                case "a":
                    writer = new BufferedWriter(new FileWriter(file, true));
                    break;
                case "r":
                    reader = new BufferedReader(new FileReader(file));
                    break;
                case "rb":
                    randomAccessFile = new RandomAccessFile(file, "r");
                    break;
                case "ab":
                    writer = new BufferedWriter(new FileWriter(file, true));
                    break;
                case "wb":
                    writer = new BufferedWriter(new FileWriter(file));
                    break;
                case "r+":
                    randomAccessFile = new RandomAccessFile(file, "rw");
                    break;
                case "rb+":
                    randomAccessFile = new RandomAccessFile(file, "rw");
                    break;
                case "w+":
                    randomAccessFile = new RandomAccessFile(file, "rw");
                    break;
                case "wb+":
                    randomAccessFile = new RandomAccessFile(file, "rw");
                    break;
                case "a+":
                    randomAccessFile = new RandomAccessFile(file, "rw");
                    break;
                case "ab+":
                    randomAccessFile = new RandomAccessFile(file, "rw");
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported mode: " + mode);
            }
        }
		catch (Exception e)
		{
            e.printStackTrace();
        }
    }


    public FileManipulator(String filePath)
	{
        this.file = new File(filePath);
        try
		{
			writer = new BufferedWriter(new FileWriter(file));
        }
		catch (Exception e)
		{
            e.printStackTrace();
        }
    }

    public FileManipulator()
    {

    }



    public void write(String data)
	{
        try
		{
            if ((writer != null))
			{
                writer.write(data);
                writer.flush();
            }
			else if (randomAccessFile != null)
			{
                randomAccessFile.writeBytes(data + "\n");
            }
            else
            {

            }
        }
		catch (Exception e)
		{
            e.printStackTrace();
        }
    }

    public void write(String filePath, String mode, String data)
    {
		new FileManipulator(filePath, mode).write(data);
    }

    public void write(int data)
	{
        write(String.valueOf(data));
    }

    public void write()
	{
        write("");
    }

    public void write(long data)
	{
        write(String.valueOf(data));
    }

    public void write(double data)
	{
        write(String.valueOf(data));
    }

    public void write(char data)
	{
        write(String.valueOf(data));
    }

    public void write(char[] data)
	{
        write(new String(data));
    }

    public void write(Date data)
    {
		write(data.toString());
    }
    public void write(StringBuilder data)
	{
        write(data.toString());
    }

    public void write(HashMap<String, String> data)
	{
        for (String key : data.keySet())
		{
            write(key + ": " + data.get(key));
        }
    }

    
    public void write(StringJoiner data)
	{
        write(data.toString());
    }

    public String read()
	{
        return read(1024);
    }


    public String read(String filePath, int chunkSize)
	{
        FileManipulator tempFileManipulator = new FileManipulator(filePath, "r");
        return tempFileManipulator.read(chunkSize);
    }

    public String read(String filePath)
	{
        return read(filePath, 64 * 1024);
    }

    public String readFileInPoint(int start, int end)
	{
        if (start >= end || start < 0)
		{
            return "-1";
        }

        String content = read();
        if (start >= content.length())
		{
            return "-1";
        }

        return content.substring(start, Math.min(end, content.length())) + (Math.min(end, content.length()) < end ? " -1" : "");
    }



    public String read(String filePathName, int start, int end)
	{
        if (start >= end || start < 0)
		{
            return "-1";
        }

        String content = read(filePathName);
        if (start >= content.length())
		{
            return "-1";
        }

        return content.substring(start, Math.min(end, content.length())) + (Math.min(end, content.length()) < end ? " -1" : "");
    }

    public String read(int start, int end)
	{
        if (start >= end || start < 0)
		{
            return "-1";
		}
        String content = read();
        if (start >= content.length())
		{
            return "-1";
        }

        return content.substring(start, Math.min(end, content.length())) + (Math.min(end, content.length()) < end ? " -1" : "");
    }

    public String read(String filePathName, int start, int end, int chunkSizeInBytes)
	{
        if (start >= end || start < 0)
		{
            return "-1";
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader tempReader = new BufferedReader(new FileReader(filePathName))) {
            char[] buffer = new char[chunkSizeInBytes];
            int bytesRead;
            int currentPosition = 0;

            while ((bytesRead = tempReader.read(buffer)) != -1)
			{
                if (currentPosition + bytesRead > start)
				{
                    int startIndex = Math.max(0, start - currentPosition);
                    int endIndex = Math.min(bytesRead, end - currentPosition);
                    content.append(buffer, startIndex, endIndex);
                }
                currentPosition += bytesRead;
                if (currentPosition >= end)
				{
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    public String read(int chunkSize)
     {
        StringBuilder content = new StringBuilder();
        try
        {
            if (reader != null)
             {
                char[] buffer = new char[chunkSize];
                int bytesRead;
    
                while ((bytesRead = reader.read(buffer, 0, chunkSize)) != -1)
               {
                    content.append(buffer, 0, bytesRead);
                }
                reader.close();
                reader = null; 
            } else if (randomAccessFile != null) {
                randomAccessFile.seek(0);
                byte[] buffer = new byte[chunkSize];
                int bytesRead;
    
            
                while ((bytesRead = randomAccessFile.read(buffer)) != -1)
               {
                    content.append(new String(buffer, 0, bytesRead));
                }
    
             
                randomAccessFile.close();
                randomAccessFile = null;
            }
        } catch (Exception e)
       {
            e.printStackTrace();
        }
        return content.toString();
    }
    
    public int rename(String newName)
	{
        while ((file.renameTo(new File(file.getParent(), newName))))
		{
            file = new File(file.getParent(), newName);
            return 0;
        }
		return -1;
    }

    public int delete(String pathStr) throws Exception
	{
        Path path = Paths.get(pathStr);

        if (Files.exists(path))
		{
            try
			{
                if (Files.isDirectory(path))
				{
                    List<Path> paths = new ArrayList<Path>();
                    DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path);
                    for (Path p : directoryStream)
					{
                        paths.add(p);
                    }
                    Collections.sort(paths, Collections.reverseOrder());

                    for (Path p : paths)
					{
                        delete(p.toString());
                    }
                    Files.delete(path);
                }
				else
				{
                    Files.delete(path);
                }
                return 0;
            }
			catch (Exception e)
			{
                throw new Exception("Error occurred while deleting: " + pathStr, e);
            }
        }
		else
		{
            throw new Exception("Path does not exist: " + pathStr);
        }
    }
    public int delete() throws Exception
    {
		return delete(currentFile);
    }

    public int copyFile(File sourceFile, File destinationFile)
	{
        return copyFile(sourceFile, destinationFile, 128 * 1024);
    }

    public int copyFile(File sourceFile, File destinationFile, int chunkSizeInBytes)
	{
		if (Math.signum(chunkSizeInBytes) < 0)
		{
			return -1;
		}

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try
		{
            inputStream = new BufferedInputStream(new FileInputStream(sourceFile));
            outputStream = new BufferedOutputStream(new FileOutputStream(destinationFile));

            byte[] buffer = new byte[chunkSizeInBytes];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1)
			{
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.flush();
            return 0;
        }
		catch (Exception e)
		{
            return -1;
        }
		finally
		{
            try
			{
                if (inputStream != null)
				{
                    inputStream.close();
                }
            }
			catch (Exception e)
			{

            }
            try
			{
                if (outputStream != null)
				{
                    outputStream.close();
                }
            }
			catch (Exception e)
			{

            }
        }
    }

    public int copyFile(String sourcePath, String destinationPath)
	{
        return copyFile(sourcePath, destinationPath, 128 * 1024);
    }

    public int copyFile(String sourcePath, String destinationPath, int chunkSizeInBytes)
	{
        return copyFile(new File(sourcePath), new File(destinationPath), chunkSizeInBytes);
    }

	public long getSize(String filePath) throws Exception
	{
        Path path = Paths.get(filePath);
        long totalSize = 0;

        if (Files.exists(path))
		{
            if (Files.isDirectory(path))
			{
                LinkedList<Path> stack = new LinkedList<Path>();
                stack.push(path);

                while (!stack.isEmpty())
				{
                    Path currentPath = stack.pop();
                    File[] files = currentPath.toFile().listFiles();

                    if (files != null)
					{
                        for (File file : files)
						{
                            if (file.isDirectory())
							{
                                stack.push(file.toPath()); // Add subdirectory to stack
                            }
							else
							{
                                totalSize += file.length(); // Add file size
                            }
                        }
                    }
                }
            }
			else if (Files.isRegularFile(path))
			{
                totalSize = Files.size(path); // Get size of the file
            }
        }
		else
		{
            throw new Exception("Path does not exist: " + filePath);
        }
        return totalSize;
    }

    public long getSize(File file) throws Exception
	{
        return getSize(file.toPath().toString());
    }

    public long getSize() throws Exception
	{
        return getSize(currentFile);
    }


    public final boolean isExist()
	{
        return file.exists();
    }

    public int fileLenght()
    {
		return read().length();
    }

    public String getEncoding()
	{
        // Default encoding
        return StandardCharsets.UTF_8.name();
    }

    public char getFirstChar()
	{
        String content = read();
        return content.isEmpty() ? (char) -1 : content.charAt(0);
    }



    public boolean isEmpty()
	{
        return file.length() == 0;
    }

    public boolean search(String searchString)
	{
        String content = read();
        return content.contains(searchString);
    }

    public void close()
	{
        try
		{
            if (writer != null)
			{
                writer.close();
            }
            if (reader != null)
			{
                reader.close();
            }
            if (randomAccessFile != null)
			{
                randomAccessFile.close();
            }
        }
		catch (Exception e)
		{
            e.printStackTrace();
        }
    }
}