// CLIJ example macro: labeling.ijm
//
// This macro shows how to apply an automatic 
// threshold method and connected components labeling
// to an image on the GPU
//
// Author: Robert Haase
// June 2019
// ---------------------------------------------


// Get test data
run("Blobs (25K)");
run("32-bit");

//open("C:/structure/data/blobs.gif");
getDimensions(width, height, channels, slices, frames);
input = getTitle();

mask = "mask";
labelmap = "labelmap";
singleLabelMask = "singleLabelMask";

// Init GPU
run("CLIJ Macro Extensions", "cl_device=");
Ext.CLIJ_clear();

// push data to GPU
Ext.CLIJ_push(input);

// cleanup ImageJ
run("Close All");

// create a mask using a fixed threshold
Ext.CLIJ_automaticThreshold(input, mask, "Otsu");

Ext.CLIJx_connectedComponentsLabeling(mask, labelmap);

Ext.CLIJ_maximumOfAllPixels(labelmap);
numberOfObjects = getResult("Max", nResults() - 1);

for (i = 0; i < numberOfObjects; i++) {
	Ext.CLIJx_labelToMask(labelmap, singleLabelMask, i + 1);
	
	Ext.CLIJ_sumOfAllPixels(singleLabelMask);
	num_of_pixels = getResult("Sum", nResults() - 1);
	
	IJ.log("Object " + i + ": pixelcount: " + num_of_pixels );
}

// show result
Ext.CLIJ_pull(mask);



