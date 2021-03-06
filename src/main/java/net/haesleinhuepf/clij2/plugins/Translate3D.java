package net.haesleinhuepf.clij2.plugins;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.clearcl.ClearCLImage;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clij.utilities.AffineTransform;
import net.haesleinhuepf.clij2.AbstractCLIJ2Plugin;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clij2.utilities.CLIJUtilities;
import net.imglib2.realtransform.AffineTransform3D;
import org.scijava.plugin.Plugin;

/**
 * Author: @haesleinhuepf
 * 12 2018
 */

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJ2_translate3D")
public class Translate3D extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation {

    @Override
    public boolean executeCL() {
        float translateX = -asFloat(args[2]);
        float translateY = -asFloat(args[3]);
        float translateZ = -asFloat(args[4]);


        ClearCLBuffer input = ((ClearCLBuffer) args[0]);
        ClearCLBuffer output = ((ClearCLBuffer) args[1]);
        CLIJ2 clij2 = getCLIJ2();

        return translate3D(clij2, input, output, translateX, translateY, translateZ);
    }


    public static boolean translate3D(CLIJ2 clij2, ClearCLBuffer input, ClearCLBuffer output, Float translateX, Float translateY, Float translateZ) {

        AffineTransform3D at = new AffineTransform3D();

        at.translate(translateX, translateY, translateZ);
        if (!clij2.hasImageSupport()) {
            return clij2.affineTransform3D(input, output, AffineTransform.matrixToFloatArray(at));
        } else {
            ClearCLImage image = clij2.create(input.getDimensions(), CLIJUtilities.nativeToChannelType(input.getNativeType()));
            clij2.copy(input, image);
            clij2.affineTransform3D(image, output, AffineTransform.matrixToFloatArray(at));
            clij2.release(image);
            return true;
        }
        /*
        Translate3D translate3D = new Translate3D();

        translate3D.setClij(clij2.getClij());
        translate3D.setArgs(new Object[]{input, output, translateX, translateY, translateZ});
        return translate3D.executeCL();
        */
    }

    @Override
    public String getParameterHelpText() {
        return "Image source, Image destination, Number translateX, Number translateY, Number translateZ";
    }

    @Override
    public String getDescription() {
        return "Translate an image stack in X, Y and Z.";
    }

    @Override
    public String getAvailableForDimensions() {
        return "3D";
    }
}
