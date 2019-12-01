## drawSphere
![Image](images/mini_clijx_logo.png)

Draws a sphere around a given point with given radii in x, y and z (if 3D). All pixels other than in the sphere are untouched. Consider using clij.op.set(buffer, 0); in advance.

### Usage in ImageJ macro
```
Ext.CLIJx_drawSphere(Image destination, Number x, Number y, Number z, Number radius_x, Number radius_y, Number radius_z);
```


### Usage in Java
```
// init CLIJ and GPU
import net.haesleinhuepf.clijx.CLIJ;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
CLIJx clijx = CLIJx.getInstance();

// get input parameters
ClearCLBuffer arg1 = clijx.push(arg1ImagePlus);
float arg2 = 1.0;
float arg3 = 2.0;
float arg4 = 3.0;
float arg5 = 4.0;
```

```
// Execute operation on GPU
clijx.drawSphere(clij, arg1, arg2, arg3, arg4, arg5);
```

```
//show result

// cleanup memory on GPU
arg1.close();
```


[Back to CLIJ documentation](https://clij.github.io/)

[Imprint](https://clij.github.io/imprint)