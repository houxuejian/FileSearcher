package utils;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;

public class FilePathSearcher {
	/**
	 * 获取文件列表
	 * @param directory
	 * @param fileFilter
	 * @param dirFilter
	 * @param depthLimit
	 * @return
	 */
	public static Collection<File> getFileList(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter, int depthLimit) {
		return LimitFileUtils.listFiles(directory, fileFilter, dirFilter, depthLimit);
	}
	/**
	 * 获取文件和目录列表
	 * @param directory
	 * @param fileFilter
	 * @param dirFilter
	 * @param depthLimit
	 * @return
	 */
	public static Collection<File> getFileAndDirList(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter, int depthLimit) {
		return LimitFileUtils.listFilesAndDirs(directory, fileFilter, dirFilter, depthLimit);
	}
	/**
	 * 获取目录列表
	 * @param directory
	 * @param dirFilter
	 * @param depthLimit
	 * @return
	 */
	public static Collection<File> getDirList(File directory, IOFileFilter dirFilter, int depthLimit) {
		return getFileAndDirList(directory, FalseFileFilter.INSTANCE, dirFilter, depthLimit);
	}
}
