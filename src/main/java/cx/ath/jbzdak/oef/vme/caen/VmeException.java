package cx.ath.jbzdak.oef.vme.caen;

/**
 * User: Jacek Bzdak jbzdak@gmail.com
 * Date: May 25, 2010
 */
public class VmeException extends RuntimeException{

   ErrorCodes errorCode;

   public VmeException(String message) {
      super(message);
   }

   public VmeException(String message, ErrorCodes errorCode) {
      super("Error code: '" + errorCode + "' " + message);

      this.errorCode = errorCode;
   }

   public VmeException(ErrorCodes errorCode) {
      super("Error code: '" + errorCode + "'");
      this.errorCode = errorCode;
   }

   public ErrorCodes getErrorCode() {
      return errorCode;
   }


}
