package cx.ath.jbzdak.oef.vme;

import com.sun.jna.Library;
import com.sun.jna.ptr.IntByReference;

/**
 * User: Jacek Bzdak jbzdak@gmail.com
 * Date: May 18, 2010
 */
public interface CaenVMElib extends Library{

//                                    CAENVME_API
//CAENVME_Init(CVBoardTypes BdType, short Link, short BdNum, int32_t *Handle);
   int Init(int type, short link, short bdum, IntByReference handle);
   
   
}
