package org.jackie.tools;

import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.filemanager.FileObject;
import org.jackie.compiler.filemanager.FileManagerDelegate;
import org.jackie.compiler.filemanager.FileObjectDelegate;

import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @author Patrik Beno
 */
class Progress {

	FileManager createReadableFileManager(FileManager fm) {
		int count = 0;
		for (String s : fm.getPathNames()) {
			if (s.endsWith(".java")) { count++; }
		}
		progress.toRead(count);
		return new RFileManager(fm);
	}

	FileManager createWritableFileManager(FileManager fm) {
		return new WFileManager(fm);
	}

	ProgressInfo progress = new ProgressInfo();

	public void close() {
		progress.finished = System.currentTimeMillis();
		System.out.println();
		System.out.printf("Elapsed: %s msec%n", progress.finished - progress.started);
	}


	class ProgressInfo {

		private int toRead;
		private int read;
		private int written;

		private long started = System.currentTimeMillis();
		private long finished;

		void read() {
			read++;
			update();
		}

		void written(FileManager fileManager) {
			written = fileManager.getPathNames().size();
			update();
		}

		void toRead(int count) {
			toRead = count;
			update();
		}

		void update() {
			System.out.printf(
					"\rRead %d of %d files. Written %d files.",
					read, toRead, written);
		}
	}


	class RFileManager extends FileManagerDelegate {
		RFileManager(FileManager fm) {
			super(fm);
		}

		public FileObject getFileObject(String pathname) {
			return new RFileObject(super.getFileObject(pathname));
		}
	}

	class WFileManager extends FileManagerDelegate {
		WFileManager(FileManager fm) {
			super(fm);
		}

		public FileObject create(String pathname) {
			progress.written(this);
			return super.create(pathname); 
		}
	}

	class RFileObject extends FileObjectDelegate {
		RFileObject(FileObject fo) {
			super(fo);
		}

		public ReadableByteChannel getInputChannel() {
			progress.read();
			return super.getInputChannel();
		}
	}
}