package net.haesleinhuepf.clij2.plugins;


import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.clearcl.ClearCLKernel;
import net.haesleinhuepf.clij.clearcl.interfaces.ClearCLImageInterface;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clij2.AbstractCLIJ2Plugin;
import net.haesleinhuepf.clij2.CLIJ2;
import org.scijava.plugin.Plugin;

import java.util.HashMap;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJ2_nonzeroMinimumDiamond")
public class NonzeroMinimumDiamond extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation {

    @Override
    public String getParameterHelpText() {
        return "Image input, Image destination";
    }

    @Override
    public boolean executeCL() {
        ClearCLBuffer input = (ClearCLBuffer) (args[0]);
        ClearCLBuffer output = (ClearCLBuffer) (args[1]);

        ClearCLBuffer flag = clij.create(new long[]{1,1,1}, output.getNativeType());
        boolean result = nonzeroMinimumDiamond(getCLIJ2(), input, flag, output);
        flag.close();
        return result;
    }

    public static boolean nonzeroMinimumDiamond(CLIJ2 clij2, ClearCLImageInterface src, ClearCLImageInterface flag, ClearCLImageInterface dst) {
        ClearCLKernel kernel = nonzeroMinimumDiamond(clij2, src, flag, dst, null);
        kernel.close();
        return true;
    }

    public static ClearCLKernel nonzeroMinimumDiamond(CLIJ2 clij2, ClearCLImageInterface src, ClearCLImageInterface flag, ClearCLImageInterface dst, ClearCLKernel kernel) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("src", src);
        parameters.put("flag_dst", flag);
        parameters.put("dst", dst);

        ClearCLKernel[] workaround = {kernel};
        //ElapsedTime.measureForceOutput("diamondmin", () -> {
            workaround[0] = clij2.executeSubsequently(NonzeroMinimumDiamond.class, "nonzero_minimum_diamond_" + dst.getDimension() + "d_x.cl", "nonzero_minimum_diamond_" + dst.getDimension() +  "d", dst.getDimensions(), dst.getDimensions(), parameters, workaround[0]);
        //});
        return workaround[0];
    }

    @Override
    public String getDescription() {
        return "Apply a minimum filter (diamond shape) to the input image. The radius is fixed to 1 and pixels with value 0 are ignored.";
    }

    @Override
    public String getAvailableForDimensions() {
        return "2D, 3D";
    }
}
