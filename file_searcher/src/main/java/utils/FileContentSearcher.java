package utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum FileContentSearcher {
	INSTANCE;
	private ExecutorService threadPool;
	private int maxPoolSize = 50;
	
	class Runner implements Callable<File> {
		private File file;
		private boolean reg;
		private Pattern pattern;
		private String patternStr;
		private Charset[] charsets;

		public Runner(File file, boolean reg, Pattern pattern, String patternStr, Charset[] charsets) {
			this.file = file;
			this.reg = reg;
			this.pattern = pattern;
			this.patternStr = patternStr;
			this.charsets = charsets;
		}

		@Override
		public File call() throws Exception {
			for (Charset charset : charsets) {
				try {
					List<String> lines = Files.readAllLines(file.toPath(), charset);
					for (String line : lines) {
						if (reg) {
							Matcher matcher = pattern.matcher(line);
							if (matcher.find()) {
								return this.file;
							}
						} else {
							if (line.indexOf(patternStr) >= 0) {
								return this.file;
							}
						}
					}
				} catch (IOException e) {
				}
			}
			return null;
		}
	}
	
	/**
	 * 多线程搜索文件内容
	 * @param files
	 * @param patternStr
	 * @param reg
	 * @param charsets
	 * @return
	 * @throws Exception 
	 */
	public List<File> parallelSearch(Collection<File> files, String patternStr, boolean reg, Charset... charsets) throws InterruptedException, ExecutionException {
		List<File> fileList = new CopyOnWriteArrayList<>();
		Pattern pattern = null;
		try {
			pattern = Pattern.compile(patternStr);
		} catch (Exception e) {
		}

		List<Callable<File>> callables = new ArrayList<>();
		for (File file : files) {
			Callable<File> callable = new Runner(file, reg, pattern, patternStr, charsets);
			callables.add(callable);
		}

		this.threadPool = Executors.newFixedThreadPool(files.size() < this.maxPoolSize ? files.size() : this.maxPoolSize);

		try {
			List<Future<File>> futures = this.threadPool.invokeAll(callables, Long.MAX_VALUE, TimeUnit.SECONDS);
			for (Future<File> future : futures) {
				File file = future.get();
				if (null != file) {
					fileList.add(file);
				}
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.threadPool.shutdown();
		}
		return fileList;
	}

	public ExecutorService getThreadPool() {
		return threadPool;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}
	
}
