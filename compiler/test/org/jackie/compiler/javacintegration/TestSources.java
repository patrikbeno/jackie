package org.jackie.compiler.javacintegration;

import org.jackie.compiler.filemanager.FileObject;
import org.jackie.compiler.filemanager.InMemoryFileManager;
import org.jackie.utils.Assert;
import org.jackie.utils.IOHelper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author Patrik Beno
 */
public class TestSources {

   static public void load(InMemoryFileManager fm) {
      try {
         Class cls = TestSources.class;
         ZipInputStream zip = new ZipInputStream(cls.getResourceAsStream(cls.getSimpleName()+".zip"));
         ByteBuffer buf = ByteBuffer.allocate(1024*1024);
         while (true) {
            ZipEntry entry = zip.getNextEntry();
            if (entry == null) {
               break;
            }
            if (entry.isDirectory()) {
               continue;
            }

            FileObject fobject = fm.create(entry.getName());
            WritableByteChannel out = fobject.getOutputChannel();
            ReadableByteChannel in = Channels.newChannel(zip);
            buf.clear();

            try {
               int read = in.read(buf);
               if (read == -1) {
                  break;
               }

               Assert.doAssert(read == entry.getSize(), "partial read");
               buf.flip();

               int written = out.write(buf);
               Assert.doAssert(written == read, "partial write");

            } finally {
               IOHelper.close(out);
            }
         }
      } catch (IOException e) {
         throw Assert.notYetHandled(e);
      }
   }

}
