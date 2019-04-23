
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Html2Ascii extends FilterInputStream {
	protected Html2Ascii(InputStream in) {
		super(in);
	}

	public int read() throws IOException {
		int charact = this.in.read();
		if (charact == -1) {
			return -1;
		}
		
		while ((char)charact == '<') {
			while ((char)charact != '>') {
				charact = this.in.read();
				if (charact == -1) {
					return -1;
				}
			}
			charact = this.in.read();
		}
		
		return charact;
	}
}