package org.lwjgl.opengl;

import org.lwjgl.*;

public final class GREMEDYFrameTerminator
{
    private GREMEDYFrameTerminator() {
    }
    
    public static void glFrameTerminatorGREMEDY() {
        final ContextCapabilities caps = GLContext.getCapabilities();
        final long function_pointer = caps.glFrameTerminatorGREMEDY;
        BufferChecks.checkFunctionAddress(function_pointer);
        nglFrameTerminatorGREMEDY(function_pointer);
    }
    
    static native void nglFrameTerminatorGREMEDY(final long p0);
}
