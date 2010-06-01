//package cx.ath.jbzdak.oef.vme.caen;
//
//import com.sun.jna.*;
//import com.sun.jna.ptr.IntByReference;
//import com.sun.jna.ptr.PointerByReference;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * User: Jacek Bzdak jbzdak@gmail.com
// * Date: May 25, 2010
// */
//public class CaenVmeLibNative implements CaenVmeInterface{
//
//
//   static{
//      Native.register(CaenUtils.createNativeLib());
//   }
//   /*
//      #define CAENVME_API CVErrorCodes
//   */
//
//   //public native String decodeError(int error);
//
//   /*
//      CAENVME_API
//      CAENVME_Init(CVBoardTypes BdType, short Link, short BdNum, int32_t *Handle);
//   */
//   public native int Init(int BdType, short link, short bdNum, IntByReference handle);
//
//   public int Init(short BdType, short link, short bdNum, IntByReference handle) {
//      return 0;  //To change body of implemented methods use File | Settings | File Templates.
//   }
//
//   /*
//  CAENVME_API
//        CAENVME_WriteCycle(int32_t Handle, uint32_t Address, void *Data,
//                  CVAddressModifier AM, CVDataWidth DW);
//   */
//   public native int WriteCycle(Pointer handle, int address, byte[] data, int addressModifier, int dataWidth);
//
//   /*
//   CAENVME_API
//      CAENVME_ReadCycle(int32_t Handle, uint32_t Address, void *Data, CVAddressModifier AM, CVDataWidth DW);
//    */
//
//   public native int ReadCycle(Pointer handle, int address, byte[] data, int addressModifier, int dataWidth);
//
//   public native int End(Pointer handle);
//
//}
