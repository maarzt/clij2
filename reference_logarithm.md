## logarithm
![Image](images/mini_clijx_logo.png)

Computes baseE logarithm of all pixels values.

f(x) = logarithm(x)

### Usage in ImageJ macro
```
Ext.CLIJx_logarithm(Image source, Image destination);
```


### Usage in Java
```
// init CLIJ and GPU
import net.haesleinhuepf.clijx.CLIJ;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
CLIJx clijx = CLIJx.getInstance();

// get input parameters
ClearCLBuffer source = clijx.push(sourceImagePlus);
destination = clij.create(source);
```

```
// Execute operation on GPU
clijx.logarithm(clij, source, destination);
```

```
//show result
destinationImagePlus = clij.pull(destination);
destinationImagePlus.show();

// cleanup memory on GPU
source.close();
destination.close();
```


[Back to CLIJ documentation](https://clij.github.io/)

[Imprint](https://clij.github.io/imprint)