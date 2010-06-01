package cx.ath.jbzdak.oef.vme.caen;

import com.sun.jna.Library;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jbzdak
 * Date: Jun 1, 2010
 * Time: 1:45:53 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CaenVmeInterface extends Library{


    public int Init(int BdType, short link, short bdNum, IntByReference handle);

     /*
   CAENVME_API
         CAENVME_WriteCycle(int32_t Handle, uint32_t Address, void *Data,
                   CVAddressModifier AM, CVDataWidth DW);
    */
   public int WriteCycle(int handle, int address, byte[] data, int addressModifier, int dataWidth);

   /*
   CAENVME_API
      CAENVME_ReadCycle(int32_t Handle, uint32_t Address, void *Data, CVAddressModifier AM, CVDataWidth DW);
    */

   public int ReadCycle(int handle, int address, byte[] data, int addressModifier, int dataWidth);

   public int End(int handle); 
}
