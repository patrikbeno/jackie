package org.jackie.tools;

import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.filemanager.FileObject;

import java.util.Set;
import java.nio.charset.Charset;
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
		System.out.println();
	}


	class ProgressInfo {

		private int toRead;
		private int read;
		private int written;

		void read() {
			read++;
			update();
		}

		void written() {
			written++;
			update();
		}

		void toRead(int count) {
			toRead = count;
			update();
		}

		void update() {
			System.out.printf("\rRead %d of %d files. Written %d files", read, toRead, written);
		}
	}


	class FileManagerDelegate implements FileManager {

		FileManager fm;

		public FileManagerDelegate(FileManager fm) {
			this.fm = fm;
		}

		public Set<String> getPathNames() {
			return fm.getPathNames();
		}

		public FileObject getFileObject(String pathname) {
			return fm.getFileObject(pathname);
		}

		public FileObject create(String pathname) {
			return fm.create(pathname);
		}

		public void remove(String pathname) {
			fm.remove(pathname);
		}

		public Iterable<FileObject> getFileObjects() {
			return fm.getFileObjects();
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
			return new WFileObject(super.create(pathname)); 
		}
	}

	class FileObjectDelegate implements FileObject {

		FileObject fo;

		FileObjectDelegate(FileObject fo) {
			this.fo = fo;
		}

		public String getPathName() {
			return fo.getPathName();
		}

		public Charset getCharset() {
			return fo.getCharset();
		}

		public long getSize() {
			return fo.getSize();
		}

		public long getLastModified() {
			return fo.getLastModified();
		}

		public ReadableByteChannel getInputChannel() {
			return fo.getInputChannel();
		}

		public WritableByteChannel getOutputChannel() {
			return fo.getOutputChannel();
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

	class WFileObject extends FileObjectDelegate {
		WFileObject(FileObject fo) {
			super(fo);
		}

		public WritableByteChannel getOutputChannel() {
			progress.written();
			return super.getOutputChannel();
		}
	}

}