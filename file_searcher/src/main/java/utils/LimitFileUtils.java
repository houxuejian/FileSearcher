package utils;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

public class LimitFileUtils {

	public static Collection<File> listFilesAndDirs(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter, int depthLimit) {
		validateListFilesParameters(directory, fileFilter);

		IOFileFilter effFileFilter = setUpEffectiveFileFilter(fileFilter);
		IOFileFilter effDirFilter = setUpEffectiveDirFilter(dirFilter);

		Collection<File> files = new java.util.LinkedList<File>();
		if (directory.isDirectory()) {
			files.add(directory);
		}
		if (depthLimit == -1) {
			innerListFiles(files, directory, FileFilterUtils.or(effFileFilter, effDirFilter), true);
		}
		innerListFiles(files, directory, FileFilterUtils.or(effFileFilter, effDirFilter), true, depthLimit);
		return files;
	}

	public static Collection<File> listFiles(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter, int depthLimit) {
		validateListFilesParameters(directory, fileFilter);

		IOFileFilter effFileFilter = setUpEffectiveFileFilter(fileFilter);
		IOFileFilter effDirFilter = setUpEffectiveDirFilter(dirFilter);

		// Find files
		Collection<File> files = new java.util.LinkedList<File>();
		if (depthLimit == -1) {
			innerListFiles(files, directory, FileFilterUtils.or(effFileFilter, effDirFilter), false);
		}
		innerListFiles(files, directory, FileFilterUtils.or(effFileFilter, effDirFilter), false, depthLimit);
		return files;
	}
	
	/**
	 * 限制递归深度
	 * @param files
	 * @param directory
	 * @param filter
	 * @param includeSubDirectories
	 * @param depthLimit
	 */
	private static void innerListFiles(Collection<File> files, File directory, IOFileFilter filter, boolean includeSubDirectories, int depthLimit) {
		File[] found = directory.listFiles((FileFilter) filter);

		if (found != null) {
			for (File file : found) {
				if (file.isDirectory()) {
					if (includeSubDirectories) {
						files.add(file);
					}
					if (depthLimit > 0) {
						innerListFiles(files, file, filter, includeSubDirectories, depthLimit - 1);
					}
				} else {
					files.add(file);
				}
			}
		}
	}

	private static void innerListFiles(Collection<File> files, File directory, IOFileFilter filter, boolean includeSubDirectories) {
		File[] found = directory.listFiles((FileFilter) filter);
		if (found != null) {
			for (File file : found) {
				if (file.isDirectory()) {
					if (includeSubDirectories) {
						files.add(file);
					}
					innerListFiles(files, file, filter, includeSubDirectories);
				} else {
					files.add(file);
				}
			}
		}
	}

	private static void validateListFilesParameters(File directory, IOFileFilter fileFilter) {
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException("Parameter 'directory' is not a directory");
		}
		if (fileFilter == null) {
			throw new NullPointerException("Parameter 'fileFilter' is null");
		}
	}

	private static IOFileFilter setUpEffectiveFileFilter(IOFileFilter fileFilter) {
		return FileFilterUtils.and(fileFilter, FileFilterUtils.notFileFilter(DirectoryFileFilter.INSTANCE));
	}

	private static IOFileFilter setUpEffectiveDirFilter(IOFileFilter dirFilter) {
		return dirFilter == null ? FalseFileFilter.INSTANCE : FileFilterUtils.and(dirFilter, DirectoryFileFilter.INSTANCE);
	}

}
