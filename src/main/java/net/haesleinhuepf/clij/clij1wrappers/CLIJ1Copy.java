package net.haesleinhuepf.clij.clij1wrappers;
import net.haesleinhuepf.clij.CLIJ;
import net.haesleinhuepf.clij.clearcl.ClearCLKernel;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.clearcl.ClearCLImage;
import net.haesleinhuepf.clij.clearcl.interfaces.ClearCLImageInterface;
import net.imglib2.realtransform.AffineTransform2D;
import net.imglib2.realtransform.AffineTransform3D;
import net.haesleinhuepf.clij.kernels.Kernels;
// this is generated code. See src/test/java/net/haesleinhuepf/clijx/codegenerator for details
public class CLIJ1Copy{
   
    /**
     * Copies an image.
     * 
     * <pre>f(x) = x</pre>
     */
    public boolean copy(CLIJ clij, ClearCLBuffer source, ClearCLImage destination) {
        return Kernels.copy(clij, source, destination);
    }

}