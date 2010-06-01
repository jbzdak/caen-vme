package cx.ath.jbzdak.oef.vme.caen;

import com.sun.jna.Library;
import com.sun.jna.NativeLibrary;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jbzdak
 * Date: Jun 1, 2010
 * Time: 1:48:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class CaenUtils {

   public static Map<String, Object> createLibOptions() {
      Map<String, Object> result = new HashMap<String, Object>();
      result.put(Library.OPTION_FUNCTION_MAPPER, new CaenFunctionMapper());
      return result;
   }

   public static NativeLibrary createNativeLib(){
      return NativeLibrary.getInstance("CAENVME", createLibOptions());
   }
}
