package cx.ath.jbzdak.oef.vme.caen;

/**
 * User: Jacek Bzdak jbzdak@gmail.com
 * Date: May 25, 2010
 */
public enum DataWidth {
   BYTE(8),
   TWO_BYTES(16),
   FOUR_BYTES(32),
   EIGHT_BYTES(64);

   final int len;

   final int numberOfBytes;

   DataWidth(int len) {
      this.len = len;
      numberOfBytes = len/8;
   }

   public int getNumberOfBytes() {
      return numberOfBytes;
   }

   public int getLen() {
      return len;
   }
}
