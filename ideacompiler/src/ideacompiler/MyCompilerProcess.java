package ideacompiler;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class MyCompilerProcess extends Process {
  @Override
  public OutputStream getOutputStream() {
    return null;
  }

  @Override
  public InputStream getInputStream() {
    return null;
  }

  @Override
  public InputStream getErrorStream() {
    return null;
  }

  @Override
  public int waitFor() throws InterruptedException {
    return 0;
  }

  @Override
  public int exitValue() {
    return 0;
  }

  @Override
  public void destroy() {
  }
}
