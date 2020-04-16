package boundary;

import java.awt.Font;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

//����һ��������ͳ��ͼ

public class saleroom_view {

	public static void main(String[] args){
		saleroom_view bar = new saleroom_view();
		bar.Bar();
	}

	/*
	 * create a chart
	 */
	public void Bar() {
		
		entity.DataProcessor user = new entity.DataProcessor("data/order.csv");
		ArrayList<String> number1 = user.Read();
		//get size of the file
		int monday = 0;
		int tuesday= 0;
		int wednesday = 0;
		int thursday = 0;
		int friday = 0;
		int saturday = 0;
		int sunday = 0;
		for(int j=0; j<number1.size(); j++) {
				String temp = number1.get(j);
				String[] str = temp.split(",");
				System.out.println(temp + " " + str[2]);
				if(str.length == 5){
					System.out.println(str[1]);
					if(str[1].equals("1")) {
						monday = monday + Integer.parseInt(str[3]);
					}else if(str[1].equals("2")) {
						tuesday = tuesday + Integer.parseInt(str[3]);
					}else if(str[1].equals("3")) {
						wednesday = wednesday + Integer.parseInt(str[3]);
					}else if(str[1].equals("4")) {
						thursday = thursday + Integer.parseInt(str[3]);
					}else if(str[1].equals("5")) {
						friday = friday + Integer.parseInt(str[3]);
					}else if(str[1].equals("6")) {
						saturday = saturday + Integer.parseInt(str[3]);
					}else {
						sunday = sunday + Integer.parseInt(str[3]);
					}
				}
				System.out.println(j);
		}
		//create dataset
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		dataset.addValue(monday,"monday","week" );

		dataset.addValue(tuesday, "tuesday","week");

		dataset.addValue(wednesday, "wednesday","week");

		dataset.addValue(thursday, "thursday","week");
		
		dataset.addValue(friday, "friday","week");
		
		dataset.addValue(saturday, "saturday","week");
		
		dataset.addValue(sunday, "sunday","week");
		

		// ����JFreeChart����

		JFreeChart barChart =  ChartFactory.createBarChart3D(

				"saleroom",//����

				"variety",     //x���ǩ

				"quantity" ,    //y���ǩ

				dataset,    //���ݼ�

				PlotOrientation.VERTICAL,//����Y��ķ���ֱ

				true,       //��ʾͼ��

				true,       //�����ɹ���

				true        //����������

				);

		

		//������������

		CategoryPlot barPlot = barChart.getCategoryPlot();

		barChart.getLegend().setItemFont(new Font("����", Font.PLAIN, 12)); //�ײ���ǩ

       

		CategoryAxis domainAxis = barPlot.getDomainAxis(); //x���ǩ����

		 domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));  

	     domainAxis.setLabelFont(new Font("����", Font.PLAIN, 12)); 

	        

        ValueAxis rAxis = barPlot.getRangeAxis();        //y���ǩ����

        rAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));  

        rAxis.setLabelFont(new Font("����", Font.PLAIN, 12));  

		

        TextTitle textTitle = barChart.getTitle();  //��ǩ����

        textTitle.setFont(new Font("����", Font.PLAIN, 20));  

        
		//��swing����ʽ���ͼ��

		ChartFrame barChartFrameframe = new ChartFrame("barChartFrame",barChart);

		barChartFrameframe.pack();

		barChartFrameframe.setVisible(true);

	}

 


}