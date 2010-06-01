package cx.ath.jbzdak.oef.vme.caen;

/**
 * User: Jacek Bzdak jbzdak@gmail.com
 * Date: May 25, 2010
 */
public enum BoardType {
   V1718(0),
   V2718(1),
   A2818(2),
   A2719(3);

//   typedef enum CVBoardTypes {
//           cvV1718 = 0,                    /* CAEN V1718 USB-VME bridge                    */
//           cvV2718 = 1,                    /* V2718 PCI-VME bridge with optical link       */
//           cvA2818 = 2,                    /* PCI board with optical link                  */
//           cvA2719 = 3                     /* Optical link piggy-back                      */
//   } CVBoardTypes;


   private final int value;

   BoardType(int value) {
      this.value = value;
   }

   public int getValue() {
      return value;
   }
}
