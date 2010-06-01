package cx.ath.jbzdak.oef.vme.caen;

/**
 * User: Jacek Bzdak jbzdak@gmail.com
 * Date: May 25, 2010
 */
public enum ErrorCodes {
   OK(0, "OK"),
   BusError(-1, "VME bus error during the cycle"),
   CommError(-2, "Communication error "),
   GenericError(-3, "Unspecified error "),
   InvalidParam(-4, "Invalid parameter"),
   TimeoutError(-5, "Timeout error");

   private static final ErrorCodes[] errorsByCode;

   static{
      errorsByCode = new ErrorCodes[6];
      for(ErrorCodes code : values()){
         errorsByCode[-code.getCode()] = code;
      }
   }

   static ErrorCodes getCodeFromInt(int code){
      return errorsByCode[-code];
   }

   private final int code;
   private final String description;


   ErrorCodes(int code, String description) {
      this.code = code;
      this.description = description;
   }

   public int getCode() {
      return code;
   }

   public String getDescription() {
      return description;
   }
}
