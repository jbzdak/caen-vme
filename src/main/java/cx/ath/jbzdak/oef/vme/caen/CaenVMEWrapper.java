package cx.ath.jbzdak.oef.vme.caen;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

import java.util.Arrays;

/**
 * User: Jacek Bzdak jbzdak@gmail.com
 * Date: May 25, 2010
 */
public class CaenVMEWrapper {

   private CaenVmeInterface caenVmeLibNative;

   private int handle;

    public CaenVMEWrapper() {
        this.caenVmeLibNative = (CaenVmeInterface) Native.loadLibrary("CAENVME", CaenVmeInterface.class, CaenUtils.createLibOptions());
    }

    private final int getDataWidth(byte[] data){
      final int len = data.length;
      if(len > 4){
         throw new InvalidDataLenght("Data to long, should be maximum 4.");
      }
      if((len & (len-1)) != 0){
         throw new InvalidDataLenght("Data length is not power of 2. It is '" + len + "'");
      }
      return len*8;
   }

   public int getHandle() {
      return handle;
   }

   public CaenVmeInterface getNat() {
      return caenVmeLibNative;
   }

   public void open(BoardType boardType, int link, int bdNum){
      IntByReference result = new IntByReference(0);
      checkError(caenVmeLibNative.Init(boardType.getValue(), (short)0, (short)0, result));
      handle = result.getValue();
   }

   public void close(){
      checkError(caenVmeLibNative.End(handle));
   }

   public void write(int address, byte[] data, AdressModifier modifier){    
      checkError(caenVmeLibNative.WriteCycle(handle, address, data, modifier.getValue(), getDataWidth(data)));
   }

   public byte[] readWrite(int address, byte[] data, AdressModifier modifier){
      byte[] result = Arrays.copyOf(data, data.length);
      checkError(caenVmeLibNative.ReadCycle(handle, address, result, modifier.getValue(), getDataWidth(data)));
      return result;
   }

   public byte[] read(int address, DataWidth dataWidth, AdressModifier modifier){
      byte[] results = new byte[dataWidth.getNumberOfBytes()];
      checkError(caenVmeLibNative.ReadCycle(handle, address, results, modifier.getValue(), dataWidth.getLen()));
      return results;
   }

   private void checkError(int errorCode){
      if(errorCode != 0){
         throw new VmeException(ErrorCodes.getCodeFromInt(errorCode));
      }

   }
}
