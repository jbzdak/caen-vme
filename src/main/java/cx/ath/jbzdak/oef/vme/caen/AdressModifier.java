package cx.ath.jbzdak.oef.vme.caen;

/**
 * User: Jacek Bzdak jbzdak@gmail.com
 * Date: May 25, 2010
 */
public enum AdressModifier {
   NO_MODIFIER(0),
   /**
    * cvA24_U_DATA
    */
   NO_PRIVILEGED_DATA_24(0x39);


   private final int value;

   AdressModifier(int value) {
      this.value = value;
   }

   public int getValue() {
      return value;
   }
}
