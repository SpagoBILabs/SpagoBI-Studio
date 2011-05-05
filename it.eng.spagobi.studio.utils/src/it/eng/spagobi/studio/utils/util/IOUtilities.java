package it.eng.spagobi.studio.utils.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtilities {

	public static void flushFromInputStreamToOutputStream(InputStream is, OutputStream os, 
			boolean closeStreams) throws Exception  {
		try{	
			int c = 0;
			byte[] b = new byte[1024];
			while ((c = is.read(b)) != -1) {
				if (c == 1024)
					os.write(b);
				else
					os.write(b, 0, c);
			}
			os.flush();
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			if (closeStreams) {
				try {
					if (os != null) os.close();
					if (is != null) is.close();
				} catch (IOException e) {
					throw e;
				}

			}
		}
	}
	
}
