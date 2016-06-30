package utils;

import java.io.File;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;

public enum FilePathSearcher {
	INSTANCE;
	private ExecutorService threadPool;
	
	/**
	 * 获取文件列表
	 * @param directory
	 * @param fileFilter
	 * @param dirFilter
	 * @param depthLimit
	 * @return
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public Collection<File> getFileList(final File directory, final IOFileFilter fileFilter, final IOFileFilter dirFilter, final int depthLimit) throws InterruptedException, ExecutionException {
		this.threadPool = Executors.newFixedThreadPool(1);
		try {
			return this.threadPool.submit(new Callable<Collection<File>>() {
				public Collection<File> call() throws Exception {
					return LimitFileUtils.listFiles(directory, fileFilter, dirFilter, depthLimit);
				}
			}).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			throw e;
		} finally {
			threadPool.shutdown();
		}
	}
	/**
	 * 获取文件和目录列表
	 * @param directory
	 * @param fileFilter
	 * @param dirFilter
	 * @param depthLimit
	 * @return
	 * @throws Exception 
	 */
	public Collection<File> getFileAndDirList(final File directory, final IOFileFilter fileFilter, final IOFileFilter dirFilter, final int depthLimit) throws InterruptedException, ExecutionException {
		this.threadPool = Executors.newFixedThreadPool(1);
		try {
			return this.threadPool.submit(new Callable<Collection<File>>() {
				public Collection<File> call() throws Exception {
					return LimitFileUtils.listFilesAndDirs(directory, fileFilter, dirFilter, depthLimit);
				}
			}).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			throw e;
		} finally {
			threadPool.shutdown();
		}
	}
	/**
	 * 获取目录列表
	 * @param directory
	 * @param dirFilter
	 * @param depthLimit
	 * @return
	 */
	public Collection<File> getDirList(final File directory, final IOFileFilter dirFilter, final int depthLimit) throws InterruptedException, ExecutionException {
		this.threadPool = Executors.newFixedThreadPool(1);
		try {
			return this.threadPool.submit(new Callable<Collection<File>>() {
				public Collection<File> call() throws Exception {
					return getFileAndDirList(directory, FalseFileFilter.INSTANCE, dirFilter, depthLimit);
				}
			}).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			throw e;
		} finally {
			threadPool.shutdown();
		}
	}
	
	public ExecutorService getThreadPool() {
		return threadPool;
	}
	
}
