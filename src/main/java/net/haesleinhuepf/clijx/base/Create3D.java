package net.haesleinhuepf.clijx.base;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.coremem.enums.NativeTypeEnum;
import net.haesleinhuepf.clij.macro.AbstractCLIJPlugin;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clijx.utilities.AbstractCLIJxPlugin;
import org.scijava.plugin.Plugin;

/**
 * Release
 * <p>
 * <p>
 * <p>
 * Author: @haesleinhuepf
 * 12 2018
 */

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_create3D")
public class Create3D extends AbstractCLIJxPlugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation {

    @Override
    public boolean executeCL() {
        return true;
    }

    @Override
    public String getParameterHelpText() {
        return "Image destination, Number width, Number height, Number depth, Number bitDepth";
    }

    @Override
    public String getDescription() {
        return "Allocated memory for a new 3D image in the GPU memory. BitDepth must be 8 (unsigned byte), 16 (unsigned short) or 32 (float).";
    }

    @Override
    public String getAvailableForDimensions() {
        return "3D";
    }

    @Override
    public ClearCLBuffer createOutputBufferFromSource(ClearCLBuffer input) {
        NativeTypeEnum typeEnum = NativeTypeEnum.Float;
        if (asInteger(args[4]) == 8) {
            typeEnum = NativeTypeEnum.UnsignedByte;
        } else if (asInteger(args[4]) == 16) {
            typeEnum = NativeTypeEnum.UnsignedShort;
        }
        return getCLIJx().create(new long[]{asInteger(args[1]), asInteger(args[2]), asInteger(args[3])}, typeEnum);
    }

}