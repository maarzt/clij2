package net.haesleinhuepf.clij2.plugins;

import ij.measure.ResultsTable;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clij2.AbstractCLIJ2Plugin;
import net.haesleinhuepf.clij2.CLIJ2;
import org.scijava.plugin.Plugin;

import java.nio.FloatBuffer;

/**
 * Author: @haesleinhuepf
 * December 2018
 */
@Plugin(type = CLIJMacroPlugin.class, name = "CLIJ2_centroidsOfLabels")
public class CentroidsOfLabels extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation {

    @Override
    public boolean executeCL() {

        ResultsTable table = ResultsTable.getResultsTable();
        table.incrementCounter();

        ClearCLBuffer labelmap = (ClearCLBuffer)( args[0]);
        ClearCLBuffer pointlist = (ClearCLBuffer)( args[0]);

        centroidsOfLabels(getCLIJ2(), labelmap, pointlist);

        return true;
    }

    public static boolean centroidsOfLabels(CLIJ2 clij2, ClearCLBuffer labelMap, ClearCLBuffer pointlist) {
        double[][] statistics = clij2.statisticsOfLabelledPixels(labelMap, labelMap);

        float[] coordinates = new float[(int) (pointlist.getWidth() * pointlist.getHeight())];

        int centroidXindex = StatisticsOfLabelledPixels.STATISTICS_ENTRY.CENTROID_X.value;
        int centroidYindex = StatisticsOfLabelledPixels.STATISTICS_ENTRY.CENTROID_Y.value;
        int centroidZindex = StatisticsOfLabelledPixels.STATISTICS_ENTRY.CENTROID_Z.value;

        for (int i = 0; i < statistics.length; i++) {
            coordinates[i + (int) pointlist.getWidth() * 0] = (float) statistics[i][centroidXindex];
            coordinates[i + (int) pointlist.getWidth() * 1] = (float) statistics[i][centroidYindex];

            if (pointlist.getHeight() == 3) {
                coordinates[i + (int) pointlist.getWidth() * 2] = (float) statistics[i][centroidZindex];
            }
        }

        FloatBuffer floatBuffer = FloatBuffer.wrap(coordinates);
        ClearCLBuffer temp = clij2.create(pointlist.getDimensions(), clij2.Float);
        temp.readFrom(floatBuffer, true);

        clij2.copy(temp, pointlist);
        clij2.release(temp);

        return true;
    }

    @Override
    public ClearCLBuffer createOutputBufferFromSource(ClearCLBuffer input) {
        int numberOfLabels = (int)getCLIJ2().maximumOfAllPixels(input);
        int numberOfDimensions = (int)input.getDimension();
        return getCLIJ2().create(new long[]{numberOfLabels, numberOfDimensions});
    }

    @Override
    public String getParameterHelpText() {
        return "Image source";
    }

    @Override
    public String getDescription() {
        return "Determines the centroids of all labels in a label image or image stack and writes the resulting " +
                " coordinates in a pointlist image. Depending on the dimensionality d of the labelmap and the number " +
                " of labels n, the pointlist image will have n*d pixels.";
    }

    @Override
    public String getAvailableForDimensions() {
        return "2D, 3D";
    }

}
