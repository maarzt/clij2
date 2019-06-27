package net.haesleinhuepf.clij.advancedfilters;

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.gui.NewImage;
import ij.gui.WaitForUserDialog;
import net.haesleinhuepf.clij.CLIJ;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.coremem.enums.NativeTypeEnum;
import org.junit.Test;

import static net.haesleinhuepf.clij.advancedfilters.ConnectedComponentsLabeling.connectedComponentsLabeling;
import static net.haesleinhuepf.clij.advancedfilters.MaskLabel.maskLabel;
import static org.junit.Assert.assertEquals;

/**
 * MaskLabelTest
 * <p>
 * <p>
 * <p>
 * Author: @haesleinhuepf
 * 06 2019
 */
public class MaskLabelTest {
    @Test
    public void testLabelMaskingFloat() {
        ImagePlus imp = NewImage.createFloatImage("img", 100, 100, 1, NewImage.FILL_BLACK);

        imp.setRoi(10, 10, 10, 10);
        IJ.run(imp, "Add...", "value=1");
        imp.setRoi(10, 30, 10, 10);
        IJ.run(imp, "Add...", "value=1");

        imp.show();

        CLIJ clij = CLIJ.getInstance();

        ClearCLBuffer input = clij.push(imp);

        ClearCLBuffer labelmap = clij.createCLBuffer(input.getDimensions(), NativeTypeEnum.Float);
        ClearCLBuffer singleLabel = clij.createCLBuffer(input.getDimensions(), NativeTypeEnum.Float);

        connectedComponentsLabeling(clij, input, labelmap);
        maskLabel(clij, input, labelmap, singleLabel, 2f);


        assertEquals(clij.op().maximumOfAllPixels(singleLabel), 1.0, 0.1);
        assertEquals(clij.op().sumPixels(singleLabel), 100.0, 0.1);
        //clij.show(singleLabel, "result");
        //new WaitForUserDialog("hello").show();

        input.close();
        labelmap.close();

    }

    @Test
    public void testLabelMaskingByte() {
        //new ImageJ();

        ImagePlus imp = NewImage.createByteImage("img", 100, 100, 1, NewImage.FILL_BLACK);

        imp.setRoi(10, 10, 10, 10);
        IJ.run(imp, "Add...", "value=1");
        imp.setRoi(10, 30, 10, 10);
        IJ.run(imp, "Add...", "value=1");

        imp.show();

        CLIJ clij = CLIJ.getInstance();

        ClearCLBuffer input = clij.push(imp);

        ClearCLBuffer labelmap = clij.createCLBuffer(input.getDimensions(), NativeTypeEnum.Float);
        ClearCLBuffer singleLabel = clij.createCLBuffer(input.getDimensions(), NativeTypeEnum.Float);

        connectedComponentsLabeling(clij, input, labelmap);
        //CLIJ.debug = true;
        System.out.println(input);
        System.out.println(labelmap);
        System.out.println(singleLabel);
        maskLabel(clij, input, labelmap, singleLabel, 2f);


        //clij.show(singleLabel, "result");
        //new WaitForUserDialog("hello").show();


        assertEquals(clij.op().maximumOfAllPixels(singleLabel), 1.0, 0.1);
        assertEquals(clij.op().sumPixels(singleLabel), 100.0, 0.1);

        input.close();
        labelmap.close();

    }

}
