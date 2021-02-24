import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.*;
import java.util.ArrayList;

public class Charts extends JFrame{

        public Charts(String title,ArrayList<SnmpInfo> array) {

            initUI(title,array);

        }

        private void initUI(String title,ArrayList<SnmpInfo> array) {

            CategoryDataset dataset = createDataset(array);

            JFreeChart chart = createChart(dataset);
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            chartPanel.setBackground(Color.white);

            add(chartPanel);

            pack();
            setTitle(title);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        private CategoryDataset createDataset(ArrayList<SnmpInfo> info ) {
          DefaultCategoryDataset dataset = new DefaultCategoryDataset();

           for(int i=0;i<info.size();i++){
               dataset.addValue(Integer.parseInt(info.get(i).getProcessAllocatedMem()),
                       "Percentagem de ram utilizada do "/*+ s.getProcessName() */, "ram");

           }
    /*
            dataset.setValue(Integer.parseInt(info.get(0).getProcessAllocatedMem()),
                    "Percentagem de ram utilizada do "+ info.get(0).getProcessName(), info.get(0).getProcessName());

            dataset.setValue(Integer.parseInt(info.get(1).getProcessAllocatedMem()),
                    "Percentagem de ram utilizada do "+ info.get(1).getProcessName(), info.get(1).getProcessName());
            dataset.setValue(Integer.parseInt(info.get(2).getProcessAllocatedMem()),
                    "Percentagem de ram utilizada do "+ info.get(2).getProcessName(), info.get(2).getProcessName());

            dataset.setValue(Integer.parseInt(info.get(3).getProcessAllocatedMem()),
                    "Percentagem de ram utilizada do "+ info.get(3).getProcessName(), info.get(3).getProcessName());

            dataset.setValue(Integer.parseInt(info.get(4).getProcessAllocatedMem()),
                    "Percentagem de ram utilizada do "+ info.get(4).getProcessName(), info.get(4).getProcessName());

            dataset.setValue(Integer.parseInt(info.get(5).getProcessAllocatedMem()),
                    "Percentagem de ram utilizada do "+ info.get(5).getProcessName(), info.get(5).getProcessName());

            dataset.setValue(Integer.parseInt(info.get(6).getProcessAllocatedMem()),
                    "Percentagem de ram utilizada do "+ info.get(6).getProcessName(), info.get(6).getProcessName());

            dataset.setValue(Integer.parseInt(info.get(7).getProcessAllocatedMem()),
                    "Percentagem de ram utilizada do "+ info.get(7).getProcessName(), info.get(7).getProcessName());

            dataset.setValue(Integer.parseInt(info.get(8).getProcessAllocatedMem()),
                    "Percentagem de ram utilizada do "+ info.get(8).getProcessName(), info.get(8).getProcessName());

            dataset.setValue(Integer.parseInt(info.get(9).getProcessAllocatedMem()),
                    "Percentagem de ram utilizada do "+ info.get(9).getProcessName(), info.get(9).getProcessName());



     */

            return dataset;

        }

        private JFreeChart createChart(CategoryDataset dataset) {

            JFreeChart barChart = ChartFactory.createBarChart(
                    "10 PROCESSOS QUE UTILIZAM MAIS RAM",
                    "process name",
                    "%",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true, true, false);


            return barChart;
        }

        public void runChart(String title,ArrayList<SnmpInfo> array) {

            EventQueue.invokeLater(() -> {

                var ex = new Charts(title,array);
                ex.setVisible(true);
            });
        }
    }

