package cx.ath.jbzdak.oef.vme;

import cx.ath.jbzdak.oef.vme.caen.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.ActionListener;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ADCController {

   private static final int READINGS_PER_BLOCK = 64;

   private static final Logger LOGGER = LoggerFactory.getLogger(ADCController.class);

   final Lock waitLock = new ReentrantLock();
   final Condition condition = waitLock.newCondition();
   final CaenVMEWrapper wrapper = new CaenVMEWrapper();
   final int baseaddr;

   final List<DataListener<IntBuffer>> dataListeners = new CopyOnWriteArrayList<DataListener<IntBuffer>>();

   ReaderThread readerThread = null;

   volatile int sampleTime;
   volatile int numberOfBlocks;

   public ADCController(BoardType boardType, int addr, int link, int bdnum) {
      wrapper.open(boardType, link, bdnum);
      baseaddr = addr;
   }

   public void addListener(DataListener<IntBuffer> listener){
      dataListeners.add(listener);
   }

   protected void onNewData(IntBuffer buffer){
      for (DataListener<IntBuffer> listener : dataListeners){
         listener.onNewData(buffer);
      }
   }

   public void start(){
      readerThread = new ReaderThread();
      readerThread.start();
   }

   public void close(){
      readerThread.shutdown();
      wrapper.close();
   }

   public ADCController() {
      this(BoardType.V2718, 0xFFF000, 0, 0);
   }

   public void setSampleTime(int sampleTime) {
      if(sampleTime < 2 || sampleTime > 256){
         throw new IllegalArgumentException("Bad sample time");
      }
      this.sampleTime = sampleTime;
   }

   public void setNumberOfBlocks(int numberOfBlocks) {
      if(numberOfBlocks < 0 || numberOfBlocks > 255){
         throw new IllegalArgumentException("Bad number of blocks");
      }
      this.numberOfBlocks = numberOfBlocks;
   }

   static final byte intToUnsignedByte(int ii){
      if(ii < 128)
         return (byte) ii;
      return (byte)(ii - 256);
   }

   private class ReaderThread extends CloseableThread{
      @Override
      protected void executeOneIteration() {
         CaenVMEWrapper wrapper = new CaenVMEWrapper();
         int numberOfBlocks = ADCController.this.numberOfBlocks;
         int readings = READINGS_PER_BLOCK * numberOfBlocks;
         IntBuffer results = IntBuffer.allocate(readings);
         try {
            wrapper.write(0xFFF006, new byte[]{0,-128}, AdressModifier.NO_PRIVILEGED_DATA_24);

            wrapper.write(0xFFF002, new byte[]{0x1C,0}, AdressModifier.NO_PRIVILEGED_DATA_24);
            byte [] testR = wrapper.read(0xFFF002, DataWidth.TWO_BYTES, AdressModifier.NO_PRIVILEGED_DATA_24);
                  LOGGER.info("R1: {}", Arrays.toString(testR));                  

            wrapper.write(0xFFF004, new byte[]{intToUnsignedByte(sampleTime),intToUnsignedByte(numberOfBlocks)}, AdressModifier.NO_PRIVILEGED_DATA_24);
            wrapper.write(0xFFF006, new byte[]{0,0}, AdressModifier.NO_PRIVILEGED_DATA_24);     
            testR = wrapper.read(0xFFF002, DataWidth.TWO_BYTES, AdressModifier.NO_PRIVILEGED_DATA_24);
                  LOGGER.info("R1: {}", Arrays.toString(testR));
            wrapper.write(0xFFF000, new byte[]{0,0}, AdressModifier.NO_PRIVILEGED_DATA_24);
            byte [] result = wrapper.read(0xFFF002, DataWidth.TWO_BYTES, AdressModifier.NO_PRIVILEGED_DATA_24);
            while((result[0] & 32) == 0){
               try{                                                                
                  waitLock.lock();
                  condition.await(100, TimeUnit.MILLISECONDS);
                  result  = wrapper.read(0xFFF002, DataWidth.TWO_BYTES, AdressModifier.NO_PRIVILEGED_DATA_24);
                  LOGGER.info("got results {}", Arrays.toString(result));                  
               } catch (InterruptedException e) {
                  e.printStackTrace();
               } finally {
                  waitLock.unlock();
               }
            }
            LOGGER.info("!!!!!!!!!!!!!!!!");
            for (int ii=0;ii<readings;ii++){
                results.put(wrapper.readInt(0xFFF000, AdressModifier.NO_PRIVILEGED_DATA_24));
            }
            onNewData(results);
         } catch (VmeException e) {
            e.printStackTrace();
         }
      }
   }
}
