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

/**
 * Author: @haesleinhuepf
 *         September 2019
 */
@Plugin(type = CLIJMacroPlugin.class, name = "CLIJ2_binarySubtract")
public class BinarySubtract extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation {

    @Override
    public boolean executeCL() {
        Object[] args = openCLBufferArgs();

        ClearCLBuffer src1 = (ClearCLBuffer)( args[0]);
        ClearCLBuffer src2  = (ClearCLBuffer)( args[1]);
        ClearCLBuffer dst = (ClearCLBuffer)( args[2]);

        binarySubtract(getCLIJ2(), src1, src2, dst);

        return true;
    }

    public static boolean binarySubtract(CLIJ2 clij2, ClearCLImageInterface src1, ClearCLImageInterface src2, ClearCLImageInterface dst) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("src1", src1);
        parameters.put("src2", src2);
        parameters.put("dst", dst);


        clij2.execute(BinarySubtract.class, "binarySubtract" + dst.getDimension() + "d_x.cl", "binary_subtract_image" + dst.getDimension() +  "d", dst.getDimensions(), dst.getDimensions(), parameters);

        return true;
    }

    @Override
    public String getParameterHelpText() {
        return "Image minuend, Image subtrahend, Image destination";
    }

    @Override
    public String getDescription() {
        return "Subtracts one binary image from another.";
    }

    @Override
    public String getAvailableForDimensions() {
        return "2D, 3D";
    }

}
