package cx.ath.jbzdak.oef.vme.caen;

/**
 * User: Jacek Bzdak jbzdak@gmail.com
 * Date: May 25, 2010
 */
public enum DataWidth {
   BYTE(1),
   TWO_BYTES(2),
   FOUR_BYTES(4),
   EIGHT_BYTES(8);

   final int len;


   DataWidth(int len) {
      this.len = len;
   }

   public int getNumberOfBytes() {
      return len;
   }

   public int getLen() {
      return len;
   }
}
