package net.haesleinhuepf.clijx.plugins;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clijx.CLIJx;
import net.haesleinhuepf.clijx.utilities.AbstractCLIJxPlugin;
import org.scijava.plugin.Plugin;

import java.util.HashMap;

import static net.haesleinhuepf.clij.utilities.CLIJUtilities.assertDifferent;
import static net.haesleinhuepf.clijx.utilities.CLIJUtilities.checkDimensions;

/**
 * Author: @haesleinhuepf
 * 12 2018
 */

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_gradientY")
public class GradientY extends AbstractCLIJxPlugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation {

    @Override
    public boolean executeCL() {
        return gradientY(getCLIJx(), (ClearCLBuffer)( args[0]), (ClearCLBuffer)(args[1]));
    }


    public static boolean gradientY(CLIJx clijx, ClearCLBuffer src, ClearCLBuffer dst) {
        assertDifferent(src, dst);

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("src", src);
        parameters.put("dst", dst);
        if (!checkDimensions(src.getDimension(), dst.getDimension())) {
            throw new IllegalArgumentException("Error: number of dimensions don't match! (copy)");
        }
        clijx.execute(GradientX.class, "gradient_y_" + dst.getDimension() + "d_x.cl", "gradient_y_" + src.getDimension() + "d", dst.getDimensions(), dst.getDimensions(), parameters);
        return true;
    }

    @Override
    public String getParameterHelpText() {
        return "Image source, Image destination";
    }

    @Override
    public String getDescription() {
        return "Computes the gradient of gray values along Y. Assuming a, b and c are three adjacent\n " +
                "pixels in Y direction. In the target image will be saved as: " +
                "<pre>b' = c - a;</pre>";
    }

    @Override
    public String getAvailableForDimensions() {
        return "2D, 3D";
    }
}