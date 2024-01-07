package ru.nntu.its.at.ifomkin.optimalfrequency.service;

import lombok.RequiredArgsConstructor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.stereotype.Service;
import ru.nntu.its.at.ifomkin.optimalfrequency.excetion.HistogramException;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ChartService {
    private final TheoreticIntervalService theoreticIntervalService;
    public String createDistributionDensityHistogram(double[] data, int intervalsCount) {
        JFreeChart histogram = createHistogram(data, intervalsCount);
        return convertChartToBase64(histogram);
    }

    private JFreeChart createHistogram(double[] data, int intervalsCount) {
        HistogramDataset histogramDataset = new HistogramDataset();
        histogramDataset.addSeries("Гистограмма", data, intervalsCount);
        JFreeChart histogram = ChartFactory.createHistogram(
                "Гистограмма плотности распределения отказов", // Заголовок
                "Пробег", // Метка по оси X
                "Частота отказов", // Метка по оси Y
                histogramDataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        setChartColor(histogram, Color.BLUE);
        return histogram;
    }

    private void setChartColor(JFreeChart histogram, Color color) {
        //Устанавливаем цвет столбцов
        XYPlot plot = (XYPlot) histogram.getPlot();
        XYItemRenderer renderer = plot.getRenderer();
        renderer.setSeriesPaint(0, color);
    }

    public String createHistogramBase64Image(double[] data, double averageFailureMileage, double standardDeviation, double optimalMileage) {
        JFreeChart chart = createChart(data, averageFailureMileage, standardDeviation, optimalMileage);
        return convertChartToBase64(chart);
    }

    private JFreeChart createChart(double[] data, double averageFailureMileage, double standardDeviation, double optimalMileage) {
        XYSeries series = new XYSeries("Функция");
        for (double val : data) {
            series.add(val, theoreticIntervalService.calculateFunctionValue(averageFailureMileage, standardDeviation, val) * 1000);
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        // Создаем график
        JFreeChart chart = ChartFactory.createXYLineChart(
                "График плотности вероятности отказов",
                "X (l), км",
                "f(x) * 1000",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                true
        );
        setChartColor(chart, Color.BLACK);

        // Получаем объект XYPlot
        XYPlot plot = (XYPlot) chart.getPlot();

        plot.setDomainCrosshairVisible(true);
        plot.setDomainCrosshairValue(optimalMileage);
        return chart;
    }

    private String convertChartToBase64(JFreeChart chart) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ChartUtils.writeChartAsJPEG(outputStream, chart, 1000, 600);
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (IOException e) {
            throw new HistogramException("Ошибка при создании гистрограммы", e);
        }
    }
}
