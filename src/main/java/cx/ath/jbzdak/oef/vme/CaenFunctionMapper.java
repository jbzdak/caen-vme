package cx.ath.jbzdak.oef.vme;

import com.sun.jna.FunctionMapper;
import com.sun.jna.NativeLibrary;

import java.lang.reflect.Method;

/**
 * User: Jacek Bzdak jbzdak@gmail.com
 * Date: May 25, 2010
 */
public class CaenFunctionMapper implements FunctionMapper{

   static final String FUNCTION_PREFIX = "CAENVME_";

   public String getFunctionName(NativeLibrary library, Method method) {

      String methodName = method.getName();
      if(!methodName.startsWith(FUNCTION_PREFIX)){
         methodName = FUNCTION_PREFIX + methodName;
      }
      
      return methodName;
   }

}
