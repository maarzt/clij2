package net.haesleinhuepf.clij2.plugins;

import net.haesleinhuepf.clij.clearcl.interfaces.ClearCLImageInterface;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clij2.AbstractCLIJ2Plugin;
import net.haesleinhuepf.clij2.CLIJ2;
import org.scijava.plugin.Plugin;

import java.util.HashMap;

import static net.haesleinhuepf.clij.utilities.CLIJUtilities.assertDifferent;
import static net.haesleinhuepf.clij2.plugins.AddImagesWeighted.addImagesWeighted;
import static net.haesleinhuepf.clij2.utilities.CLIJUtilities.checkDimensions;

/**
 * Author: @haesleinhuepf
 * 12 2018
 */

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJ2_subtractImageFromScalar")
public class SubtractImageFromScalar extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation {

    @Override
    public boolean executeCL() {
        return subtractImagesFromScalar(getCLIJ2(), (ClearCLImageInterface)( args[0]), (ClearCLImageInterface)(args[1]), asFloat(args[2]));
    }

    public static boolean subtractImagesFromScalar(CLIJ2 clij2, ClearCLImageInterface src, ClearCLImageInterface dst, Float scalar) {
        assertDifferent(src, dst);

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("src", src);
        parameters.put("scalar", scalar);
        parameters.put("dst", dst);

        if (!checkDimensions(src.getDimension(), src.getDimension(), dst.getDimension())) {
            throw new IllegalArgumentException("Error: number of dimensions don't match! (minimumImageAndScalar)");
        }
        clij2.execute(SubtractImageFromScalar.class, "subtract_image_from_scalar_" + src.getDimension() + "d_x.cl", "subtract_image_from_scalar_" + src.getDimension() + "d", dst.getDimensions(), dst.getDimensions(), parameters);
        return true;
    }


    @Override
    public String getParameterHelpText() {
        return "Image input, Image destination, Number scalar";
    }

    @Override
    public String getDescription() {
        return "Subtracts one image X from a scalar s pixel wise.\n\n<pre>f(x, s) = s - x</pre>";
    }

    @Override
    public String getAvailableForDimensions() {
        return "2D, 3D";
    }
}