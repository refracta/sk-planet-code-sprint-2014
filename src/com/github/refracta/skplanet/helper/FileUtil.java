package com.github.refracta.skplanet.helper;

import java.io.*;
import java.util.ArrayList;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-15 오후 7:57
 */
public class FileUtil {
	public static final int bufferSize = 1024 * 24;

	public static void copyFile(String filePath, String copyFilePath) throws IOException {
		File file = new File(filePath);
		File copyDir = new File(copyFilePath);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(copyDir));
		byte[] buf = new byte[bufferSize];
		int read;
		while ((read = bufferedInputStream.read(buf, 0, buf.length)) != -1) {
			bufferedOutputStream.write(buf,0,read);
		}
		bufferedOutputStream.flush();
		bufferedInputStream.close();
		bufferedOutputStream.close();
	}
	public static boolean cutPaste(String cut,String paste) throws IOException {
		File cutFile = new File(cut);
		File pasteFile = new File(paste);

		if(cutFile.isFile()){
			copyFile(cut,paste);
			return cutFile.delete();
		}else if (cutFile.isDirectory()){
			copyDirectory(cut,paste);
			return  deleteDirectory(cut);
		}else{
			throw  new IOException("cut path is strange.");
		}
	}


	public static void copyDirectory(String directoryPath,String copyDirectoryPath) throws IOException {
		File directory = new File(directoryPath);
		File copyDirectory = new File(copyDirectoryPath);
		copyDirectory.mkdir();
		ArrayList<File> folderList = getFileArrayList(directory.listFiles());
		ArrayList<File> fileList = getFileList(folderList);

		for (int i = 0; i < fileList.size(); i++) {
			File currentFile = fileList.get(i);
			copyFile(currentFile.getAbsolutePath(),copyDirectoryPath+"/"+currentFile.getName());
		}
		ArrayList<File> directoryList = getDirectoryList(folderList);

		for (int i = 0; i < directoryList.size(); i++) {
			File currentDirectory = directoryList.get(i);

			copyDirectory(currentDirectory.getAbsolutePath(),copyDirectoryPath+"/"+currentDirectory.getName());
		}

	}
	public static boolean deleteDirectory(String deleteDirectoryPath){
		File directory = new File(deleteDirectoryPath);
		ArrayList<File> folderList = getFileArrayList(directory.listFiles());
		ArrayList<File> fileList = getFileList(folderList);
		ArrayList<File> directoryList = getDirectoryList(folderList);
		for (int i = 0; i < fileList.size(); i++) {
			File currentFile =fileList.get(i);
			currentFile.delete();
		}
		for (int i = 0; i < directoryList.size(); i++) {
			File currentDir = directoryList.get(i);
			deleteDirectory(currentDir.getAbsolutePath());
			currentDir.delete();
		}
		return directory.delete();

	}
	public static ArrayList<File> getFileArrayList(File[] array){
		ArrayList<File> returnList = new ArrayList<File>();
		for (int i = 0; i < array.length; i++) {
				returnList.add(array[i]);
		}
		return returnList;
	}

	public static ArrayList<File> getDirectoryList(ArrayList<File> targetList){
		ArrayList<File> returnList = new ArrayList<File>();
		for (int i = 0; i < targetList.size(); i++) {
			File currentFile = targetList.get(i);
			if(currentFile.isDirectory()) {
				returnList.add(currentFile);
			}
		}
		return returnList;
	}
	public static ArrayList<File> getFileList(ArrayList<File> targetList){
		ArrayList<File> returnList = new ArrayList<File>();
		for (int i = 0; i < targetList.size(); i++) {
			File currentFile = targetList.get(i);
			if(currentFile.isFile()) {
				returnList.add(currentFile);
			}
		}
		return returnList;
	}
	public static boolean checkByteReadable(File file){
		try {
			FileInputStream inputStream = new FileInputStream(file);
			inputStream.close();
			return true;
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
		return false;

	}
}
