package cx.ath.jbzdak.oef.vme;

import net.miginfocom.swing.MigLayout;
import org.jdesktop.beansbinding.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.AbstractXYDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jbzdak
 * Date: Jun 8, 2010
 * Time: 3:07:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ADCFrame extends JFrame{

   ADCController controller = new ADCController();

   MYDataset dataset = new MYDataset();

   boolean started;

   public ADCFrame(){
      super("ADC");
      setLayout(new MigLayout("wrap 1", "", "fill"));
      JFreeChart chart =
         ChartFactory.createXYLineChart("Wyniki", "Próbka", "Napięcie", dataset, PlotOrientation.HORIZONTAL,true, false, false);
      add(new ChartPanel(chart), "growx, growy");
      JTextField sampleTime = new JTextField();
      add(decoratePanel("Czas próbkowania", sampleTime));
      sampleTime.setText("16");
      JTextField blockNum = new JTextField();
      blockNum.setText("25");
      add(decoratePanel("Liczba bloków", blockNum));
      JTextField blocksBuff = new JTextField();
      blocksBuff.setText("25");
      add(decoratePanel("Liczba bloków", blocksBuff));
      final JButton startStopButton = new JButton();
      startStopButton.setText(getButtonLabel());
      add(startStopButton);
      startStopButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            if(started){
               controller.close();
            }else{
               controller.start();
            }
            started = !started;
            startStopButton.setText(getButtonLabel());
         }
      });

      BindingGroup bindingGroup = new BindingGroup();
      Binding blockNumBinding = Bindings.createAutoBinding(
              AutoBinding.UpdateStrategy.READ,
              sampleTime,
              BeanProperty.<Object, Object>create("text"),
              controller,
              BeanProperty.<Object, Object>create("sampleTime")
      );
      Binding sampleTimeBinding = Bindings.createAutoBinding(
              AutoBinding.UpdateStrategy.READ,
              blockNum,
              BeanProperty.<Object, Object>create("text"),
              controller,
              BeanProperty.<Object, Object>create("numberOfBlocks")
      );
      Binding blocksBuffBinding = Bindings.createAutoBinding(
              AutoBinding.UpdateStrategy.READ,
              sampleTime,
              BeanProperty.<Object, Object>create("text"),
              dataset,
              BeanProperty.<Object, Object>create("blocksBuff")
      );
      bindingGroup.addBinding(blockNumBinding);
      bindingGroup.addBinding(sampleTimeBinding);
      bindingGroup.addBinding(blocksBuffBinding);
      bindingGroup.bind();
   }

   public static void main(String[] args){
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            ADCFrame frame = new ADCFrame();
            frame.setVisible(true);
            frame.setSize(640, 480);
         }
      });
   }

   private String getButtonLabel(){
      return started?"Zakończ":"Rozpocznij";
   }

   private JPanel decoratePanel(String foo, JComponent jComponent){
      JPanel panel = new JPanel(new BorderLayout());
      panel.setBorder(BorderFactory.createTitledBorder(foo));
      panel.add(jComponent, BorderLayout.CENTER);
      return panel;
   }

   class MYDataset extends AbstractXYDataset implements DataListener<IntBuffer>{

      List<Integer> results = new ArrayList();

      int blocksBuffered;

      public void onNewData(IntBuffer buffer) {
         int blocksArr = buffer.capacity()/64;
         int blocksRem = results.size()/64 - blocksBuffered;
         List<Integer> newResults = new ArrayList();
         if(blocksRem > 0){
            newResults.addAll(results.subList(blocksRem * 64, results.size()));
         }
         for(int ii : buffer.array()){
            newResults.add(ii);
         }
         results = newResults;
         fireDatasetChanged();
      }

      public void setBlocksBuffered(int blocksBuffered) {
         this.blocksBuffered = blocksBuffered;
      }

      public int getItemCount(int i) {
         return results.size();
      }

      public Number getX(int i, int i1) {
         return i1;
      }

      public Number getY(int i, int i1) {
         return results.get(i1);
      }

      @Override
      public int getSeriesCount() {
         return 1;
      }

      @Override
      public Comparable getSeriesKey(int i) {
         return "Wyniki";
      }
   }
}
