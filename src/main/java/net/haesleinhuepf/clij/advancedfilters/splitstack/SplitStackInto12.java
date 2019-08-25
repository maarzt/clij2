package net.haesleinhuepf.clij.advancedfilters.splitstack;

import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJ_splitStackInto12")
public class SplitStackInto12 extends AbstractSplitStack {

    @Override
    public String getParameterHelpText() {
        return getParameterHelpText(12);
    }


}