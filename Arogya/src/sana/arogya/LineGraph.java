package sana.arogya;
import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Toast;
public class LineGraph {
	public Intent getIntent(Context context,double h[],double w[],int height,double weight)
	{
		
	Toast.makeText(context,"Arogya Graph Loading...",Toast.LENGTH_LONG).show();
		
		
		int a[]={10,50,100,10};
		TimeSeries urcond = new TimeSeries("Your Condition              ");
		TimeSeries urline = new TimeSeries("");
		TimeSeries exl = new TimeSeries("Extreme Conditions    ");
		TimeSeries avgl = new TimeSeries("Average Conditions     ");
		TimeSeries good = new TimeSeries("Ideal Condition     ");
		TimeSeries idline = new TimeSeries("");
		TimeSeries avgh = new TimeSeries("");
		TimeSeries exh = new TimeSeries("");
	
		urcond.add(height, weight);
		urcond.add(height+height*.001, weight+weight*.001);
		urcond.add(height-height*.001,weight-weight*.001);
		urline.add(height, weight);
		urline.add(height, 0);
		urline.add(0, weight);
		exl.add(h[0]-h[0]*.1,w[0]-w[0]*.1);
		exl.add(h[0]-h[0]*.2,w[0]-w[0]*.2);
		exl.add(h[0],w[0]);
		avgl.add(h[0],w[0]);
		avgl.add(h[1],w[1]);
		good.add(h[1],w[1]);
		good.add(h[2],w[2]);
		good.add(h[3],w[3]);
		idline.add(h[2], w[2]);
		idline.add(h[2], 0);
		idline.add(0, w[2]);
		avgh.add(h[3],w[3]);
		avgh.add(h[4],w[4]);
		exh.add(h[4],w[4]);
		exh.add(h[4]+h[4]*.1,w[4]+w[4]*.1);
		exh.add(h[4]+h[4]*.2,w[4]+w[4]*.2);
		
		XYMultipleSeriesDataset ds=  new XYMultipleSeriesDataset();
		ds.addSeries(urcond);
		ds.addSeries(urline);
		ds.addSeries(exl);
		ds.addSeries(avgl);
		ds.addSeries(good);
		ds.addSeries(idline);
		ds.addSeries(avgh);
		ds.addSeries(exh);
		
		XYSeriesRenderer rurcond= new XYSeriesRenderer();
		rurcond.setColor(Color.BLUE);
		rurcond.setLineWidth(20);
		rurcond.setPointStyle(PointStyle.SQUARE);
        rurcond.setFillPoints(true);
        rurcond.setPointStrokeWidth(1000);
        
        XYSeriesRenderer rurline= new XYSeriesRenderer();
		rurline.setColor(Color.BLUE);
		rurline.setLineWidth(5);
		rurline.setPointStyle(PointStyle.SQUARE);
        rurline.setFillPoints(true);
        rurline.setPointStrokeWidth(1000);
        rurline.setShowLegendItem(false);
       
		XYSeriesRenderer rexl= new XYSeriesRenderer();
		rexl.setColor(Color.RED);
		rexl.setLineWidth(10);
		rexl.setPointStyle(PointStyle.SQUARE);
        rexl.setFillPoints(true);
		rexl.setPointStrokeWidth(100);
		 		
		XYSeriesRenderer ravgl= new XYSeriesRenderer();
		ravgl.setColor(Color.YELLOW);
		ravgl.setLineWidth(10);
		ravgl.setPointStyle(PointStyle.SQUARE);
        ravgl.setFillPoints(true);
        ravgl.setPointStrokeWidth(100);
   
		XYSeriesRenderer rgood= new XYSeriesRenderer();
		rgood.setColor(Color.GREEN);
		rgood.setLineWidth(10);
		rgood.setPointStyle(PointStyle.SQUARE);
        rgood.setFillPoints(true);
        rgood.setPointStrokeWidth(100);
		
        XYSeriesRenderer ridline= new XYSeriesRenderer();
		ridline.setColor(Color.GREEN);
		ridline.setLineWidth(5);
		ridline.setPointStyle(PointStyle.SQUARE);
        ridline.setFillPoints(true);
        ridline.setPointStrokeWidth(100);
        ridline.setShowLegendItem(false);
		
		XYSeriesRenderer ravgh= new XYSeriesRenderer();
		ravgh.setColor(Color.YELLOW);
		ravgh.setLineWidth(10);
		ravgh.setPointStyle(PointStyle.SQUARE);
        ravgh.setFillPoints(true);
        ravgh.setPointStrokeWidth(100);
       ravgh.setShowLegendItem(false);
        
        
        XYSeriesRenderer rexh= new XYSeriesRenderer();
		rexh.setColor(Color.RED);
		rexh.setLineWidth(10);
		rexh.setPointStyle(PointStyle.SQUARE);
        rexh.setFillPoints(true);
        rexh.setPointStrokeWidth(100);
        rexh.setShowLegendItem(false);
       
		
		
		XYMultipleSeriesRenderer sr= new XYMultipleSeriesRenderer();
		sr.addSeriesRenderer(rurcond);
		sr.addSeriesRenderer(rurline);
		sr.addSeriesRenderer(rexl);
		sr.addSeriesRenderer(ravgl);
		sr.addSeriesRenderer(rgood);
		sr.addSeriesRenderer(ridline);
		sr.addSeriesRenderer(ravgh);
		sr.addSeriesRenderer(rexh);
		sr.setBackgroundColor(Color.BLACK);
		sr.setShowLegend(true);
		sr.setZoomEnabled(true);
		sr.setPanEnabled(true,true);
		sr.setApplyBackgroundColor(true);
		sr.setChartTitle("Height vs Weight Graph");
		sr.setYTitle("Weight");
		sr.setXTitle("Height");
		sr.setShowGrid(true);
		sr.setShowAxes(true);
		sr.setLabelsTextSize(25);
		sr.setChartTitleTextSize(30);
		sr.setAxisTitleTextSize(30);
		sr.setXLabelsPadding(20);
		sr.setYLabelsPadding(20);
		sr.setAxesColor(Color.YELLOW);
		sr.setMargins(a);
		sr.setShowLabels(true);
		sr.setFitLegend(true);
		sr.setLegendTextSize(25);
		
		
		
		Intent in = ChartFactory.getLineChartIntent(context, ds, sr, "Arogya Graph");
		return in;
	}

}