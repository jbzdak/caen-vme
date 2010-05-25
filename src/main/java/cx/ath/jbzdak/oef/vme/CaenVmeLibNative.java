package cx.ath.jbzdak.oef.vme;

import com.sun.jna.*;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Jacek Bzdak jbzdak@gmail.com
 * Date: May 25, 2010
 */
public class CaenVmeLibNative {

   private static Map<String, Object> createLibOptions() {
      Map<String, Object> result = new HashMap<String, Object>();
      result.put(Library.OPTION_FUNCTION_MAPPER, new CaenFunctionMapper());
      return result;
   }

   static{
      NativeLibrary caenvme = NativeLibrary.getInstance("CAENVME", createLibOptions());
      Native.register(caenvme);
   }
   /*
      #define CAENVME_API CVErrorCodes
    */



   //public native String decodeError(int error);

   /*
      CAENVME_API
      CAENVME_Init(CVBoardTypes BdType, short Link, short BdNum, int32_t *Handle);
   */
   public native int Init(int BdType, short link, short bdNum, PointerByReference handle);

   /*
   CAENVME_API
         CAENVME_WriteCycle(int32_t Handle, uint32_t Address, void *Data,
                   CVAddressModifier AM, CVDataWidth DW);
    */
   public native int WriteCycle(Pointer handle, int address, byte[] data, int addressModifier, int dataWidth);

   /*
   CAENVME_API
CAENVME_ReadCycle(int32_t Handle, uint32_t Address, void *Data,
                  CVAddressModifier AM, CVDataWidth DW);
    */

   public native int ReadCycle(Pointer handle, int address, byte[] data, int addressModifier, int dataWidth); 

   public native int End(Pointer handle); 


   

   

}
