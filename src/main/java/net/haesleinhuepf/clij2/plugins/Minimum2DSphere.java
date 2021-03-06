package net.haesleinhuepf.clij2.plugins;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.clearcl.interfaces.ClearCLImageInterface;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clij2.AbstractCLIJ2Plugin;
import net.haesleinhuepf.clij2.CLIJ2;
import org.scijava.plugin.Plugin;

import java.util.HashMap;

import static net.haesleinhuepf.clij.utilities.CLIJUtilities.assertDifferent;
import static net.haesleinhuepf.clij.utilities.CLIJUtilities.radiusToKernelSize;

/**
 * Author: @haesleinhuepf
 * 12 2018
 */
@Plugin(type = CLIJMacroPlugin.class, name = "CLIJ2_minimum2DSphere")
public class Minimum2DSphere extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation {

    @Override
    public boolean executeCL() {
        return minimum2DSphere(getCLIJ2(), (ClearCLBuffer)( args[0]), (ClearCLBuffer)(args[1]), asInteger(args[2]), asInteger(args[3]));
    }

    public static boolean minimum2DSphere(CLIJ2 clij2, ClearCLImageInterface src, ClearCLImageInterface dst, Integer radiusX, Integer radiusY) {
        int kernelSizeX = radiusToKernelSize(radiusX);
        int kernelSizeY = radiusToKernelSize(radiusY);

        assertDifferent(src, dst);

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("src", src);
        parameters.put("dst", dst);
        parameters.put("Nx", kernelSizeX);
        parameters.put("Ny", kernelSizeY);

        clij2.execute(Minimum2DSphere.class, "minimum_sphere_2d_x.cl", "minimum_sphere_2d", dst.getDimensions(), dst.getDimensions(), parameters);
        return true;
    }

    @Override
    public String getParameterHelpText() {
        return "Image source, Image destination, Number radiusX, Number radiusY";
    }

    @Override
    public String getDescription() {
        return "Computes the local minimum of a pixels ellipsoidal neighborhood. The ellipses size is specified by \n" +
                "its half-width and half-height (radius).";
    }

    @Override
    public String getAvailableForDimensions() {
        return "2D";
    }

}
